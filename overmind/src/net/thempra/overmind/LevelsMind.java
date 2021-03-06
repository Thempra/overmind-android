package net.thempra.overmind;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.androidplot.Plot;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepFormatter;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

public class LevelsMind extends Activity  {

	private static final int N_VALUE = 11;
	private XYPlot mySimpleXYPlot;
	private Number[] series1Numbers={ 0, 0,0,0,0,0,0,0,0,0,0,0};
	private Handler mHandler = new Handler();
	
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
        	 setContentView(R.layout.grafica_2);
     		// initialize our XYPlot reference:
     		mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
     		
            //Toast.makeText(this, "Lanzado temporizador", Toast.LENGTH_LONG).show();
     		/*
     		Random r = new Random();
     		
     		
     		for(int i=0; i< N_VALUE; i++)
     			series1Numbers[i] = r.nextInt(100);
     		*/
     		if (selected >= 0)
    		{
	     		series1Numbers[0] = MainActivity.currentEeg[selected].signal;
	     		series1Numbers[1] = MainActivity.currentEeg[selected].attention;
	     		series1Numbers[2] = MainActivity.currentEeg[selected].meditation;
	     		series1Numbers[3] = MainActivity.currentEeg[selected].theta;
	     		series1Numbers[4] = MainActivity.currentEeg[selected].delta;
	     		series1Numbers[5] = MainActivity.currentEeg[selected].lalpha;
	     		series1Numbers[6] = MainActivity.currentEeg[selected].halpha;
	     		series1Numbers[7] = MainActivity.currentEeg[selected].lbeta;
	     		series1Numbers[8] = MainActivity.currentEeg[selected].hbeta;
	     		series1Numbers[9] = MainActivity.currentEeg[selected].lgamma;
	     		series1Numbers[10] = MainActivity.currentEeg[selected].hgamma;
	     		series1Numbers[11] = 0;
    		}
     		
     		
     	// create our series from our array of nums:
    		XYSeries series2 = new SimpleXYSeries(Arrays.asList(series1Numbers),
    				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Thread #1");

    		mySimpleXYPlot.getGraphWidget().getGridBackgroundPaint()
    				.setColor(Color.WHITE);
    		mySimpleXYPlot.getGraphWidget().getGridLinePaint()
    				.setColor(Color.BLACK);
    		mySimpleXYPlot.getGraphWidget().getGridLinePaint()
    				.setPathEffect(new DashPathEffect(new float[] { 1, 1 }, 1));
    		mySimpleXYPlot.getGraphWidget().getDomainOriginLinePaint()
    				.setColor(Color.BLACK);
    		mySimpleXYPlot.getGraphWidget().getRangeOriginLinePaint()
    				.setColor(Color.BLACK);
    		mySimpleXYPlot.getGraphWidget().setMarginRight(5);

    		mySimpleXYPlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
    		mySimpleXYPlot.getBorderPaint().setStrokeWidth(1);
    		mySimpleXYPlot.getBorderPaint().setAntiAlias(false);
    		mySimpleXYPlot.getBorderPaint().setColor(Color.WHITE);

    		// Create a formatter to use for drawing a series using
    		// LineAndPointRenderer:
    		LineAndPointFormatter series1Format = new LineAndPointFormatter(
    				Color.rgb(0, 100, 0), // line color
    				Color.rgb(0, 100, 0), // point color
    				Color.rgb(100, 200, 0)); // fill color

    		// setup our line fill paint to be a slightly transparent gradient:
    		Paint lineFill = new Paint();
    		lineFill.setAlpha(200);
    		lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE,
    				Color.BLUE, Shader.TileMode.MIRROR));

    		StepFormatter stepFormatter = new StepFormatter(Color.rgb(0, 0, 0),
    				Color.BLUE);
    		stepFormatter.getLinePaint().setStrokeWidth(1);

    		stepFormatter.getLinePaint().setAntiAlias(false);
    		stepFormatter.setFillPaint(lineFill);
    		mySimpleXYPlot.addSeries(series2, stepFormatter);

    		// adjust the domain/range ticks to make more sense; label per tick for
    		// range and label per 5 ticks domain:
    		mySimpleXYPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1);
    		mySimpleXYPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
    		mySimpleXYPlot.setTicksPerRangeLabel(10);
    		mySimpleXYPlot.setTicksPerDomainLabel(1);

    		// customize our domain/range labels
    		mySimpleXYPlot.setDomainLabel("Waves");
    		mySimpleXYPlot.setRangeLabel("Level");
    		mySimpleXYPlot.setRangeTopMax(100);
    	
    		mySimpleXYPlot.setDomainValueFormat(new WavesIndexFormat());

    		
    		// create a custom formatter to draw our state names as range tick
    		// labels:
    		//mySimpleXYPlot.setRangeTopMax(10);
    		mySimpleXYPlot.setRangeValueFormat(new DecimalFormat("0"));
    		/*
    		 *mySimpleXYPlot.setRangeValueFormat(new Format() {
    			@Override
    			public StringBuffer format(Object obj, StringBuffer toAppendTo,
    					FieldPosition pos) {
    				Number num = (Number) obj;
    				switch (num.intValue()) {
    				case 1:
    					toAppendTo.append("Init");
    					break;
    				case 2:
    					toAppendTo.append("Idle");
    					break;
    				case 3:
    					toAppendTo.append("Recv");
    					break;
    				case 4:
    					toAppendTo.append("Send");
    					break;
    				default:
    					toAppendTo.append("Unknown");
    					break;
    				}
    				return toAppendTo;
    			}

    			@Override
    			public Object parseObject(String source, ParsePosition pos) {
    				return null;
    			}
    		});
    		*/

    		// by default, AndroidPlot displays developer guides to aid in laying
    		// out your plot.
    		// To get rid of them call disableAllMarkup():
    		mySimpleXYPlot.disableAllMarkup();
    		
    		
    		
    		
            mHandler.removeCallbacks(mMuestraMensaje);
            mHandler.postDelayed(this, 1000);
         }
       };



       
       
       
}

