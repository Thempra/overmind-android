#!/usr/bin/env python3
"""Sube resultados de escáneres a DefectDojo (reimport con close_old_findings).

Se ejecuta al final del workflow security.yml. Lee los JSON que encontró en el
workspace y hace reimport por cada scan encontrado bajo el engagement "CI"
del producto homónimo (env PROJECT_KEY, por defecto el nombre del repo).

Variables: DOJO_URL, DOJO_API_TOKEN, PROJECT_KEY, COMMIT_SHA (opt).
"""
import json
import mimetypes
import os
import sys
import time
import uuid
from pathlib import Path
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen

DOJO = os.environ.get("DOJO_URL", "https://dojo.thempra.net").rstrip("/")
TOKEN = os.environ["DOJO_API_TOKEN"]
PRODUCT_KEY = (
    os.environ.get("PROJECT_KEY")
    or os.environ.get("FORGEJO_REPOSITORY")
    or "unknown"
).strip().split("/")[-1]
COMMIT = os.environ.get("COMMIT_SHA", "")

SCAN_TYPES = {
    "trivy.json": "Trivy Scan",
    "semgrep.json": "Semgrep JSON Report",
    "bandit.json": "Bandit Scan",
    "gitleaks.json": "Gitleaks Scan",
}
CHECKOV_FILE = "checkov.json"


def api(method, path, body=None, multipart=None):
    url = f"{DOJO}/api/v2{path}"
    headers = {"Authorization": f"Token {TOKEN}"}
    data = None
    if body is not None:
        data = json.dumps(body).encode()
        headers["Content-Type"] = "application/json"
    elif multipart:
        boundary = uuid.uuid4().hex
        parts = []
        for k, v in multipart.items():
            if isinstance(v, tuple):
                fname, content = v
                parts.append(
                    f'--{boundary}\r\nContent-Disposition: form-data; name="{k}"; '
                    f'filename="{fname}"\r\nContent-Type: application/octet-stream\r\n\r\n'.encode()
                    + content
                    + b"\r\n"
                )
            else:
                parts.append(
                    f'--{boundary}\r\nContent-Disposition: form-data; name="{k}"\r\n\r\n{v}\r\n'.encode()
                )
        parts.append(f"--{boundary}--\r\n".encode())
        data = b"".join(parts)
        headers["Content-Type"] = f"multipart/form-data; boundary={boundary}"
    req = Request(url, data=data, method=method, headers=headers)
    last = None
    for attempt in range(4):
        try:
            with urlopen(req, timeout=120) as r:
                body = r.read()
                return r.status, (json.loads(body) if body else {})
        except HTTPError as e:
            return e.code, e.read().decode()[:400]
        except (URLError, OSError) as e:
            last = e
            time.sleep(3 * (attempt + 1))
    raise last


def get_id_of_name(path, name):
    st, d = api("GET", f"{path}?name={name}&limit=5")
    if st != 200:
        sys.exit(f"ERROR {path}: {st} {d}")
    for r in d.get("results", []):
        if r.get("name") == name:
            return r["id"]
    return None


def ensure_engagement(product_id):
    eng_id = None
    st, d = api("GET", f"/engagements/?product={product_id}&name=CI&limit=5")
    if st == 200:
        for r in d.get("results", []):
            if r.get("name") == "CI":
                eng_id = r["id"]
                break
    if eng_id is None:
        st, d = api(
            "POST",
            "/engagements/",
            {
                "product": product_id,
                "name": "CI",
                "active": True,
                "target_start": __import__("datetime").date.today().isoformat(),
                "target_end": "2099-12-31",
            },
        )
        if st not in (200, 201):
            sys.exit(f"ERROR creando engagement: {st} {d}")
        eng_id = d["id"]
    return eng_id


def reimport(eng_id, scan_type, fname, content):
    multipart = {
        "file": (fname, content),
        "engagement_name": "CI",
        "product_name": PRODUCT_KEY,
        "test_title": "CI",
        "scan_type": scan_type,
        "name": f"CI {fname}",
        "active": "true",
        "verified": "false",
        "close_old_findings": "true",
        "auto_create_context": "true",
        "dedupe_on_findings": "true",
    }
    if COMMIT:
        multipart["commit_hash"] = COMMIT
    st, d = api("POST", "/reimport-scan/", multipart=multipart)
    if st not in (200, 201):
        print(f"  ✗ reimport {fname}: {st} {d}")
        return False
    print(f"  ✓ reimport {fname} -> {d.get('url') or d.get('id')}")
    return True


def split_checkov(content):
    """checkov -o json emite por framework; el parser de Dojo quiere uno solo.
    Formatos vistos: dict{framework:{checks:[...]}}, list[ {framework results} ]."""
    try:
        doc = json.loads(content)
    except Exception:
        return []
    out = []
    if isinstance(doc, dict):
        items = doc.items()
    elif isinstance(doc, list):
        # checkov >=2.3: lista de dicts de resultado, cada uno con check_id etc.
        # Agrupar por framework si existe, si no un único blob terraform.
        groups = {}
        for r in doc:
            fw = (r.get("check_type") or r.get("framework") or "terraform").lower()
            groups.setdefault(fw, []).append(r)
        items = [(fw, {"checks": {"passed_checks": [], "failed_checks": g}})
                 for fw, g in groups.items()]
    else:
        return []
    for fw, sub in items:
        if isinstance(sub, dict) and "checks" in sub:
            out.append((f"checkov_{fw}.json", json.dumps(sub).encode()))
    return out


def main():
    st, d = api("GET", f"/products/?name={PRODUCT_KEY}&limit=5")
    product_id = None
    if st == 200:
        for r in d.get("results", []):
            if r["name"] == PRODUCT_KEY:
                product_id = r["id"]
    if product_id is None:
        sys.exit(f"Producto '{PRODUCT_KEY}' no existe en Dojo")
    eng_id = ensure_engagement(product_id)
    print(f"producto={PRODUCT_KEY}({product_id}) engagement=CI({eng_id})")

    uploaded = failed = 0
    ws = Path(os.environ.get("CI_UPLOAD_DIR", "."))
    for fname, scan in SCAN_TYPES.items():
        p = next(iter(sorted(ws.rglob(fname))), None)
        if p is None or p.stat().st_size < 3:
            print(f"– {fname}: sin resultados")
            continue
        if reimport(eng_id, scan, fname, p.read_bytes()):
            uploaded += 1
        else:
            failed += 1

    cp = next(iter(sorted(ws.rglob(CHECKOV_FILE))), None)
    if cp and cp.stat().st_size > 3:
        for fname, content in split_checkov(cp.read_text()):
            if reimport(eng_id, "Checkov Scan", fname, content):
                uploaded += 1
            else:
                failed += 1

    print(f"resumen: {uploaded} subidos, {failed} fallos")
    if failed:
        sys.exit(1)


if __name__ == "__main__":
    main()
