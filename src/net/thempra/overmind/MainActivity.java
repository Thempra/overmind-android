package net.thempra.overmind;


import java.io.InputStream;
import java.util.Set;

import net.thempra.overgame.SimpleGame;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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

import com.gallysoft.andromind.*;

public class MainActivity extends Activity implements android.content.DialogInterface.OnClickListener, AndroMindListener {

	private Button btnGrafica_1;
	private Button btnGrafica_2;
	private Button btnGame;
	private static TextView txtStatus;
	public static Eeg currentEeg = new Eeg();

	//private Handler mHandlerTest = new Handler();

	
	BluetoothSocket cone;
	InputStream in;
	int REQUEST_ENABLE_BT = 2;
	BluetoothAdapter mBluetoothAdapter;
	Set<BluetoothDevice> pairedDevices;
	AndroMindLib devBlue;
	AndroMindListener mindListerner;



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
		
		
		 mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	     if (mBluetoothAdapter == null) {
	            
	        	//Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
	        	txtStatus.setText("Bluetooth is not available");
	            finish();
	            return;   
	        }

	      mindListerner = this;
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		/*boolean bool = true;
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
		
		*/
		int id = item.getItemId();
    	if (id == R.id.menu_connect) {
    		 if (!mBluetoothAdapter.isEnabled()) {
          	    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
          	   
  				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
  				
          	}else{
          		this.conectar();
          	}
             return true;
    	/*} else if (id == R.id.menu_desconectar) {
    		
    		dvblue.cancel();
        	
        	
        	
        	
            return true;
    	} else {
    		return super.onOptionsItemSelected(item);
    	*/}
    	
    	return false;
	}

	
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
            	
            	this.conectar();
            	
            }else{
            	
            	//Toast.makeText(this, "Bluetooth is disable", Toast.LENGTH_LONG).show();
            	txtStatus.setText("Bluetooth is disable");
            }
        }
    }
    
    
    private void conectar(){
    	//Toast.makeText(this, "Bluetooth is enable", Toast.LENGTH_LONG).show();
    	txtStatus.setText("Bluetooth is enable");
    	
    	CharSequence[] items;
    	pairedDevices = mBluetoothAdapter.getBondedDevices();
    	// If there are paired devices
    	if (pairedDevices.size() > 0) {
    		
    		items = new CharSequence[pairedDevices.size()];
    		int i=0;
    	    for (BluetoothDevice device : pairedDevices) {
    	        // Add the name and address to an array adapter to show in a ListView
    	        items[i]=(device.getName() + "\n" + device.getAddress());
    	        i++;
    	    }
    	    
    	    
    	}else{
    		items = new CharSequence[1];
    		items[0]=("No found");
    	}
    	
    	
    	
    	

    	AlertDialog.Builder pbuilder = new AlertDialog.Builder(this);
    	pbuilder.setTitle("Select a device:");
    	pbuilder.setItems(items, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	        
    	    	if(item<pairedDevices.size()){
    	    		if(mBluetoothAdapter.isDiscovering())
    	    		mBluetoothAdapter.cancelDiscovery();
    	    		BluetoothDevice tmp = (BluetoothDevice) pairedDevices.toArray()[item];
    	    		 devBlue = new AndroMindLib(tmp);
    	    		 devBlue.setNewCaptureListener(mindListerner);
    	    		devBlue.start();
    	    		/*
    	    		if (devBlue.isconected() )
    	            	txtStatus.setText("Bluetooth is connected");
    	    		else
    	            	txtStatus.setText("Bluetooth can't connected");
    	    			*/
    	    	}else{
    	    		
    	    		
    	    	}

    	    	
    	    	
    	    }
    	});
    	
    	AlertDialog palert = pbuilder.create();
    	palert.show();
    	
    	
    	
    	
    }

  /*  
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
      	  	//txtStatus.setText(Integer.toString(cManager.geteeg().signal)+" "+Integer.toString(cManager.geteeg().attention));
        	txtStatus.setText(Integer.toString(currentEeg.signal)+" "+Integer.toString(currentEeg.attention));
      	  
      	  	mHandlerTest.removeCallbacks(mMuestraMensaje);
      	  	mHandlerTest.postDelayed(this, 1000);
        }
       
    };
	*/
	@Override
	public void newCaptured(AndroMindLib andromind) {
		
		if (andromind.getSignal() > 0 )
		{
			//txtStatus.setText("Bluetooth is connected");
			currentEeg.signal=(int) andromind.getSignal()/2;
			
			currentEeg.meditation=(int) andromind.getMeditation();
			currentEeg.attention=(int) andromind.getAttention();
			
			if (andromind.getProdelta() > 0)
				currentEeg.delta=(int) andromind.getProdelta()*100/1800;
			else
				currentEeg.delta=0;
			if (andromind.getProtheta() > 0)
				currentEeg.theta=(int) andromind.getProtheta()*100/1800;
			else
				currentEeg.theta=0;
			if (andromind.getProlalpha() > 0)
				currentEeg.lalpha=(int) andromind.getProlalpha()*100/1800;
			else
				currentEeg.lalpha=0;
			if (andromind.getProhalpha() > 0)
				currentEeg.halpha=(int) andromind.getProhalpha()*100/1800;
			else
				currentEeg.halpha=0;
			if (andromind.getProlbeta() > 0)
				currentEeg.lbeta=(int) andromind.getProlbeta()*100/1800;
			else
				currentEeg.lbeta=0;
			if (andromind.getProhbeta() > 0)
				currentEeg.hbeta=(int) andromind.getProhbeta()*100/1800;
			else
				currentEeg.hbeta=0;
			if (andromind.getProlgamma() > 0)
				currentEeg.lgamma=(int) andromind.getProlgamma()*100/1800;
			else
				currentEeg.lgamma=0;
			if (andromind.getProhgamma() > 0)
				currentEeg.hgamma=(int) andromind.getProhgamma()*100/1800;
			else
				currentEeg.hgamma=0;
		
		}else
		{
			currentEeg.signal=0;
			currentEeg.attention=0;
			currentEeg.meditation=0;
			currentEeg.delta=0;
			currentEeg.theta=0;
			currentEeg.lalpha=0;
			currentEeg.halpha=0;
			currentEeg.lbeta=0;
			currentEeg.hbeta=0;
			currentEeg.lgamma=0;
			currentEeg.hgamma=0;
			
		
		}
		
		
		
		//txtStatus.setText(Integer.toString(currentEeg.signal) + " " + Integer.toString(currentEeg.meditation)+ " " + Integer.toString(currentEeg.attention));
		
	}

	 @Override
	    protected void onDestroy(){
	    	if(devBlue != null){
	    	devBlue.cancel();
	    	devBlue = null;
	    	}
	    	super.onDestroy();
	    }
	    
	 
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

}
