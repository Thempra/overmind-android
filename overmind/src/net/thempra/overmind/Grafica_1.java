package net.thempra.overmind;

import java.util.Arrays;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Grafica_1 extends Activity {

	private XYPlot mySimpleXYPlot;
	private Handler mHandler = new Handler();
	
	private Number[] series1Numbers = { 100, 0, 0, 0, 0, 0 };
	private Number[] series2Numbers = { 100, 0, 0, 0, 0, 0 };
	
	private int itemsConnected=0;
	private int selected=-1;
	private CharSequence[] choiceList;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

	   
		AlertDialog.Builder builder = 
	            new AlertDialog.Builder(this);
	        builder.setTitle("Select device to analize");        
	       
			for(int i=0;i<MainActivity.currentEeg.length;i++)
	        	 if (MainActivity.currentEeg[i]!= null)
	        		 itemsConnected++;		
			int j=0;
	         choiceList = new CharSequence[itemsConnected];
	         for(int i=0;i<MainActivity.currentEeg.length;i++)
	        	 if (MainActivity.currentEeg[i]!= null)
	        	 {
	        		 choiceList[j]= MainActivity.currentEeg[i].getName().replace("\n", "");	
	        		 j++;
	        	 }
	        builder.setItems(choiceList, new DialogInterface.OnClickListener() {
	             
	            @Override
	            public void onClick(
	                    DialogInterface dialog, 
	                    int which) {
	            	
	            	
	            	for(int i=0;i<MainActivity.currentEeg.length;i++)
	   	        	 	if ((MainActivity.currentEeg[i]!= null) && (choiceList[which].toString().contains((MainActivity.currentEeg[i].getName().replace("\n", "")))))
	   	        	 		selected= i;	     
	            	
	                Toast.makeText(getBaseContext(), "Select "+MainActivity.currentEeg[selected].getName(), Toast.LENGTH_SHORT).show();
	                
	            }
	        });
	        AlertDialog alert = builder.create();   
	        alert.show();

	    	mHandler.removeCallbacks(mMuestraMensaje);
		    mHandler.postDelayed(mMuestraMensaje, 1000);

	        
		    
	  
	}
	
	 private Runnable mMuestraMensaje = new Runnable() {
		 
	 public void run() {
		setContentView(R.layout.grafica_1);

		// Inicializamos el objeto XYPlot búscandolo desde el layout:
		mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

		for (int i=0;i<series1Numbers.length-1;i++)
		{
			series1Numbers[i]=series1Numbers[i+1];
			
		}
		for (int i=0;i<series2Numbers.length-1;i++)
		{
			series2Numbers[i]=series2Numbers[i+1];
			
		}
		

		// Creamos dos arrays de prueba. En el caso real debemos reemplazar
		// estos datos por los que realmente queremos mostrar
		if (selected >= 0)
		{
			series1Numbers[series1Numbers.length-1] = MainActivity.currentEeg[selected].attention;
			series2Numbers[series2Numbers.length-1] = MainActivity.currentEeg[selected].meditation;
		}
		// Añadimos Línea Número UNO:
		XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers), // Array
																				// de
																				// datos

				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores
														// verticales
				"Attention"); // Nombre de la primera serie

		
		// Repetimos para la segunda serie
		XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers),
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Meditation");

		// Modificamos los colores de la primera serie
		LineAndPointFormatter series1Format = new LineAndPointFormatter(
				Color.rgb(0, 200, 0), // Color de la línea
				Color.rgb(0, 100, 0), // Color del punto
				 Color.TRANSPARENT); // Relleno

		// Una vez definida la serie (datos y estilo), la añadimos al panel
		mySimpleXYPlot.addSeries(series1, series1Format);

		// Repetimos para la segunda serie
		mySimpleXYPlot.addSeries(
				series2,
				new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0,
						100), Color.TRANSPARENT));
		
		
		
		mHandler.removeCallbacks(mMuestraMensaje);
	    mHandler.postDelayed(mMuestraMensaje, 1000);


	  }
	 };
}
