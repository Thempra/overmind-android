package net.thempra.overmind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btnGrafica_1;
	Button btnGrafica_2;
	Button btnGame;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnGrafica_1 = (Button) findViewById(R.id.btnGrafica_1);
		btnGrafica_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent grafica1 = new Intent(MainActivity.this, Grafica_1.class);
				startActivity(grafica1);
			}
		});
		
		btnGrafica_2 = (Button) findViewById(R.id.btnGrafica_2);
		btnGrafica_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent grafica2 = new Intent(MainActivity.this, Grafica_2.class);
				startActivity(grafica2);
			}
		});
		
		btnGame = (Button) findViewById(R.id.btnGame);
		btnGame.setEnabled(false);
				
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
