package net.thempra.overmind;


import net.thempra.overgame.SimpleGame;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements android.content.DialogInterface.OnClickListener {

	private Button btnGrafica_1;
	private Button btnGrafica_2;
	private Button btnGame;
	private static TextView txtStatus;

	private Handler mHandlerTest = new Handler();

	private CommunicationManager cManager = new CommunicationManager();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		/* Codigo para no poner el titulo */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		txtStatus = (TextView) findViewById(R.id.txtStatus);
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
		//btnGame.setEnabled(false);
		btnGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent game = new Intent(MainActivity.this, SimpleGame.class);
				startActivity(game);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		boolean bool = true;
		switch (item.getItemId()) {
		case R.id.menu_connect: {
			CharSequence[] arrayOfCharSequence = new CharSequence[cManager.get_num_devices()];;
			
			arrayOfCharSequence= cManager.get_devices();
			AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
            localBuilder.setTitle("Select a device:");
            localBuilder.setItems(arrayOfCharSequence, this);
            localBuilder.create().show();
		}

		}
		return bool;
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
		
		if (cManager.connect(arg1)== CommunicationManager.OK)
		{
			txtStatus.setText("Bluetooth Connected");
			cManager.initialize_communication();
			
			
		      
		      mHandlerTest.removeCallbacks(mMuestraMensaje);
		      mHandlerTest.postDelayed(mMuestraMensaje, 1000);

			//cManager.listen();
		}
		else
			txtStatus.setText("Bluetooth can't connect");

		
	}
	
	private Runnable mMuestraMensaje = new Runnable() {
        public void run() {
      	  	txtStatus.setText(Integer.toString(cManager.geteeg().signal));
      	  	mHandlerTest.removeCallbacks(mMuestraMensaje);
      	  	mHandlerTest.postDelayed(this, 1000);
        }
       
    };

}
