/*package net.thempra.overmind;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class ConnectThread extends Thread
{
  static final UUID UUID_RFCOMM_GENERIC = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  Egg lastEgg;
  
  private final BluetoothDevice mmDevice;
  private InputStream mmInStream;
  private OutputStream mmOutStream;
  private final BluetoothSocket mmSocket;

  public ConnectThread(BluetoothDevice paramBluetoothDevice,  Egg egg)
  {
    Object localObject = null;
    this.mmDevice = paramBluetoothDevice;
    this.lastEgg.signal = paramProgressBar1;
    this.bsignal.setMax(200);
    this.bmeditation = paramProgressBar2;
    this.bmeditation.setMax(100);
    this.battention = paramProgressBar3;
    this.battention.setMax(100);
    this.bdelta = paramProgressBar4;
    this.bdelta.setMax(1800);
    this.btheta = paramProgressBar5;
    this.btheta.setMax(1800);
    this.blalpha = paramProgressBar6;
    this.blalpha.setMax(1800);
    this.bhalpha = paramProgressBar7;
    this.bhalpha.setMax(1800);
    this.blbeta = paramProgressBar8;
    this.blbeta.setMax(1800);
    this.bhbeta = paramProgressBar9;
    this.bhbeta.setMax(1800);
    this.blgamma = paramProgressBar10;
    this.blgamma.setMax(1800);
    this.bhgamma = paramProgressBar11;
    this.bhgamma.setMax(1800);
  
    try
    {
      BluetoothSocket localBluetoothSocket = paramBluetoothDevice.createRfcommSocketToServiceRecord(UUID_RFCOMM_GENERIC);
      localObject = localBluetoothSocket;
      this.mmSocket = (BluetoothSocket) localObject;
      return;
    }
    catch (IOException localIOException)
    {
      
    }
  }

  public void cancel()
  {
    try
    {
      this.mmSocket.close();
     return;
    }
    catch (IOException localIOException)
    {
    	return;
    }
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 82	com/gallysoft/andromind/ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
    //   4: invokevirtual 92	android/bluetooth/BluetoothSocket:connect	()V
    //   7: aload_0
    //   8: aload_0
    //   9: getfield 82	com/gallysoft/andromind/ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
    //   12: invokevirtual 96	android/bluetooth/BluetoothSocket:getInputStream	()Ljava/io/InputStream;
    //   15: putfield 98	com/gallysoft/andromind/ConnectThread:mmInStream	Ljava/io/InputStream;
    //   18: aload_0
    //   19: aload_0
    //   20: getfield 82	com/gallysoft/andromind/ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
    //   23: invokevirtual 102	android/bluetooth/BluetoothSocket:getOutputStream	()Ljava/io/OutputStream;
    //   26: putfield 104	com/gallysoft/andromind/ConnectThread:mmOutStream	Ljava/io/OutputStream;
    //   29: bipush 36
    //   31: newarray int
    //   33: astore 4
    //   35: iconst_0
    //   36: istore 5
    //   38: aload_0
    //   39: getfield 98	com/gallysoft/andromind/ConnectThread:mmInStream	Ljava/io/InputStream;
    //   42: invokevirtual 110	java/io/InputStream:read	()I
    //   45: istore 7
    //   47: iload 5
    //   49: ifne +33 -> 82
    //   52: iload 7
    //   54: sipush 170
    //   57: if_icmpne +25 -> 82
    //   60: aload 4
    //   62: iload 5
    //   64: iload 7
    //   66: iastore
    //   67: iinc 5 1
    //   70: goto -32 -> 38
    //   73: astore_1
    //   74: aload_0
    //   75: getfield 82	com/gallysoft/andromind/ConnectThread:mmSocket	Landroid/bluetooth/BluetoothSocket;
    //   78: invokevirtual 88	android/bluetooth/BluetoothSocket:close	()V
    //   81: return
    //   82: aload 4
    //   84: iconst_0
    //   85: iaload
    //   86: sipush 170
    //   89: if_icmpne +732 -> 821
    //   92: iload 5
    //   94: iconst_1
    //   95: if_icmpne +726 -> 821
    //   98: iload 7
    //   100: sipush 170
    //   103: if_icmpne +718 -> 821
    //   106: aload 4
    //   108: iload 5
    //   110: iload 7
    //   112: iastore
    //   113: iconst_2
    //   114: istore 8
    //   116: iload 8
    //   118: bipush 36
    //   120: if_icmplt +640 -> 760
    //   123: new 112	java/lang/String
    //   126: dup
    //   127: invokespecial 113	java/lang/String:<init>	()V
    //   130: astore 9
    //   132: iconst_0
    //   133: istore 5
    //   135: iload 5
    //   137: bipush 36
    //   139: if_icmplt +639 -> 778
    //   142: ldc 115
    //   144: aload 9
    //   146: invokestatic 121	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   149: pop
    //   150: aload_0
    //   151: getfield 48	com/gallysoft/andromind/ConnectThread:bsignal	Landroid/widget/ProgressBar;
    //   154: sipush 200
    //   157: aload 4
    //   159: iconst_4
    //   160: iaload
    //   161: isub
    //   162: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   165: aload_0
    //   166: getfield 56	com/gallysoft/andromind/ConnectThread:bmeditation	Landroid/widget/ProgressBar;
    //   169: aload 4
    //   171: bipush 34
    //   173: iaload
    //   174: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   177: aload_0
    //   178: getfield 58	com/gallysoft/andromind/ConnectThread:battention	Landroid/widget/ProgressBar;
    //   181: aload 4
    //   183: bipush 32
    //   185: iaload
    //   186: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   189: ldc2_w 125
    //   192: dconst_1
    //   193: sipush 255
    //   196: aload 4
    //   198: bipush 7
    //   200: iaload
    //   201: iand
    //   202: bipush 16
    //   204: ishl
    //   205: sipush 255
    //   208: aload 4
    //   210: bipush 8
    //   212: iaload
    //   213: iand
    //   214: bipush 8
    //   216: ishl
    //   217: ior
    //   218: aload 4
    //   220: bipush 9
    //   222: iaload
    //   223: ior
    //   224: i2d
    //   225: ddiv
    //   226: dmul
    //   227: dstore 12
    //   229: aload_0
    //   230: getfield 60	com/gallysoft/andromind/ConnectThread:bdelta	Landroid/widget/ProgressBar;
    //   233: dload 12
    //   235: d2i
    //   236: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   239: ldc 128
    //   241: new 130	java/lang/StringBuilder
    //   244: dup
    //   245: dload 12
    //   247: invokestatic 134	java/lang/String:valueOf	(D)Ljava/lang/String;
    //   250: invokespecial 137	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   253: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   256: invokestatic 121	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   259: pop
    //   260: ldc2_w 125
    //   263: dconst_1
    //   264: sipush 255
    //   267: aload 4
    //   269: bipush 10
    //   271: iaload
    //   272: iand
    //   273: bipush 16
    //   275: ishl
    //   276: sipush 255
    //   279: aload 4
    //   281: bipush 11
    //   283: iaload
    //   284: iand
    //   285: bipush 8
    //   287: ishl
    //   288: ior
    //   289: aload 4
    //   291: bipush 12
    //   293: iaload
    //   294: ior
    //   295: i2d
    //   296: ddiv
    //   297: dmul
    //   298: dstore 15
    //   300: aload_0
    //   301: getfield 62	com/gallysoft/andromind/ConnectThread:btheta	Landroid/widget/ProgressBar;
    //   304: dload 15
    //   306: d2i
    //   307: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   310: ldc 143
    //   312: new 130	java/lang/StringBuilder
    //   315: dup
    //   316: dload 15
    //   318: invokestatic 134	java/lang/String:valueOf	(D)Ljava/lang/String;
    //   321: invokespecial 137	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   324: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   327: invokestatic 121	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   330: pop
    //   331: ldc2_w 125
    //   334: dconst_1
    //   335: sipush 255
    //   338: aload 4
    //   340: bipush 13
    //   342: iaload
    //   343: iand
    //   344: bipush 16
    //   346: ishl
    //   347: sipush 255
    //   350: aload 4
    //   352: bipush 14
    //   354: iaload
    //   355: iand
    //   356: bipush 8
    //   358: ishl
    //   359: ior
    //   360: aload 4
    //   362: bipush 15
    //   364: iaload
    //   365: ior
    //   366: i2d
    //   367: ddiv
    //   368: dmul
    //   369: dstore 18
    //   371: aload_0
    //   372: getfield 64	com/gallysoft/andromind/ConnectThread:blalpha	Landroid/widget/ProgressBar;
    //   375: dload 18
    //   377: d2i
    //   378: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   381: ldc 145
    //   383: new 130	java/lang/StringBuilder
    //   386: dup
    //   387: dload 18
    //   389: invokestatic 134	java/lang/String:valueOf	(D)Ljava/lang/String;
    //   392: invokespecial 137	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   395: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   398: invokestatic 121	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   401: pop
    //   402: ldc2_w 125
    //   405: dconst_1
    //   406: sipush 255
    //   409: aload 4
    //   411: bipush 16
    //   413: iaload
    //   414: iand
    //   415: bipush 16
    //   417: ishl
    //   418: sipush 255
    //   421: aload 4
    //   423: bipush 17
    //   425: iaload
    //   426: iand
    //   427: bipush 8
    //   429: ishl
    //   430: ior
    //   431: aload 4
    //   433: bipush 18
    //   435: iaload
    //   436: ior
    //   437: i2d
    //   438: ddiv
    //   439: dmul
    //   440: dstore 21
    //   442: aload_0
    //   443: getfield 66	com/gallysoft/andromind/ConnectThread:bhalpha	Landroid/widget/ProgressBar;
    //   446: dload 21
    //   448: d2i
    //   449: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   452: ldc 147
    //   454: new 130	java/lang/StringBuilder
    //   457: dup
    //   458: dload 21
    //   460: invokestatic 134	java/lang/String:valueOf	(D)Ljava/lang/String;
    //   463: invokespecial 137	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   466: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   469: invokestatic 121	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   472: pop
    //   473: ldc2_w 125
    //   476: dconst_1
    //   477: sipush 255
    //   480: aload 4
    //   482: bipush 19
    //   484: iaload
    //   485: iand
    //   486: bipush 16
    //   488: ishl
    //   489: sipush 255
    //   492: aload 4
    //   494: bipush 20
    //   496: iaload
    //   497: iand
    //   498: bipush 8
    //   500: ishl
    //   501: ior
    //   502: aload 4
    //   504: bipush 21
    //   506: iaload
    //   507: ior
    //   508: i2d
    //   509: ddiv
    //   510: dmul
    //   511: dstore 24
    //   513: aload_0
    //   514: getfield 68	com/gallysoft/andromind/ConnectThread:blbeta	Landroid/widget/ProgressBar;
    //   517: dload 24
    //   519: d2i
    //   520: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   523: ldc 149
    //   525: new 130	java/lang/StringBuilder
    //   528: dup
    //   529: dload 24
    //   531: invokestatic 134	java/lang/String:valueOf	(D)Ljava/lang/String;
    //   534: invokespecial 137	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   537: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   540: invokestatic 121	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   543: pop
    //   544: ldc2_w 125
    //   547: dconst_1
    //   548: sipush 255
    //   551: aload 4
    //   553: bipush 22
    //   555: iaload
    //   556: iand
    //   557: bipush 16
    //   559: ishl
    //   560: sipush 255
    //   563: aload 4
    //   565: bipush 23
    //   567: iaload
    //   568: iand
    //   569: bipush 8
    //   571: ishl
    //   572: ior
    //   573: aload 4
    //   575: bipush 24
    //   577: iaload
    //   578: ior
    //   579: i2d
    //   580: ddiv
    //   581: dmul
    //   582: dstore 27
    //   584: aload_0
    //   585: getfield 70	com/gallysoft/andromind/ConnectThread:bhbeta	Landroid/widget/ProgressBar;
    //   588: dload 27
    //   590: d2i
    //   591: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   594: ldc 151
    //   596: new 130	java/lang/StringBuilder
    //   599: dup
    //   600: dload 27
    //   602: invokestatic 134	java/lang/String:valueOf	(D)Ljava/lang/String;
    //   605: invokespecial 137	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   608: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   611: invokestatic 121	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   614: pop
    //   615: ldc2_w 125
    //   618: dconst_1
    //   619: sipush 255
    //   622: aload 4
    //   624: bipush 25
    //   626: iaload
    //   627: iand
    //   628: bipush 16
    //   630: ishl
    //   631: sipush 255
    //   634: aload 4
    //   636: bipush 26
    //   638: iaload
    //   639: iand
    //   640: bipush 8
    //   642: ishl
    //   643: ior
    //   644: aload 4
    //   646: bipush 27
    //   648: iaload
    //   649: ior
    //   650: i2d
    //   651: ddiv
    //   652: dmul
    //   653: dstore 30
    //   655: aload_0
    //   656: getfield 72	com/gallysoft/andromind/ConnectThread:blgamma	Landroid/widget/ProgressBar;
    //   659: dload 30
    //   661: d2i
    //   662: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   665: ldc 153
    //   667: new 130	java/lang/StringBuilder
    //   670: dup
    //   671: dload 30
    //   673: invokestatic 134	java/lang/String:valueOf	(D)Ljava/lang/String;
    //   676: invokespecial 137	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   679: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   682: invokestatic 121	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   685: pop
    //   686: ldc2_w 125
    //   689: dconst_1
    //   690: sipush 255
    //   693: aload 4
    //   695: bipush 28
    //   697: iaload
    //   698: iand
    //   699: bipush 16
    //   701: ishl
    //   702: sipush 255
    //   705: aload 4
    //   707: bipush 29
    //   709: iaload
    //   710: iand
    //   711: bipush 8
    //   713: ishl
    //   714: ior
    //   715: aload 4
    //   717: bipush 30
    //   719: iaload
    //   720: ior
    //   721: i2d
    //   722: ddiv
    //   723: dmul
    //   724: dstore 33
    //   726: aload_0
    //   727: getfield 74	com/gallysoft/andromind/ConnectThread:bhgamma	Landroid/widget/ProgressBar;
    //   730: dload 33
    //   732: d2i
    //   733: invokevirtual 124	android/widget/ProgressBar:setProgress	(I)V
    //   736: ldc 155
    //   738: new 130	java/lang/StringBuilder
    //   741: dup
    //   742: dload 33
    //   744: invokestatic 134	java/lang/String:valueOf	(D)Ljava/lang/String;
    //   747: invokespecial 137	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   750: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   753: invokestatic 121	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   756: pop
    //   757: goto -719 -> 38
    //   760: aload 4
    //   762: iload 8
    //   764: aload_0
    //   765: getfield 98	com/gallysoft/andromind/ConnectThread:mmInStream	Ljava/io/InputStream;
    //   768: invokevirtual 110	java/io/InputStream:read	()I
    //   771: iastore
    //   772: iinc 8 1
    //   775: goto -659 -> 116
    //   778: new 130	java/lang/StringBuilder
    //   781: dup
    //   782: aload 9
    //   784: invokestatic 158	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   787: invokespecial 137	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   790: aload 4
    //   792: iload 5
    //   794: iaload
    //   795: invokestatic 164	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   798: invokevirtual 168	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   801: ldc 170
    //   803: invokevirtual 168	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   806: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   809: astore 10
    //   811: aload 10
    //   813: astore 9
    //   815: iinc 5 1
    //   818: goto -683 -> 135
    //   821: iconst_0
    //   822: istore 5
    //   824: goto -786 -> 38
    //   827: astore_3
    //   828: goto -799 -> 29
    //   831: astore_2
    //   832: goto -751 -> 81
    //   835: astore 6
    //   837: goto -756 -> 81
    //
    // Exception table:
    //   from	to	target	type
    //   0	7	73	java/io/IOException
    //   7	29	827	java/io/IOException
    //   74	81	831	java/io/IOException
    //   38	67	835	java/io/IOException
    //   82	811	835	java/io/IOException
  }
}
  */