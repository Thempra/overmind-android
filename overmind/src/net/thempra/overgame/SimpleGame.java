package net.thempra.overgame;



import java.util.ArrayList;
import java.util.List;

import net.thempra.overmind.MainActivity;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.widget.Toast;


public class SimpleGame extends Activity
{
protected CCGLSurfaceView _glSurfaceView;
	

	private int itemsConnected=0;
	private ArrayList<Integer> selected=new ArrayList<Integer>();
	private CharSequence[] choiceList;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	
		_glSurfaceView = new CCGLSurfaceView(this);
		
		setContentView(_glSurfaceView);
		/*
		AlertDialog.Builder builder = 
	            new AlertDialog.Builder(this);
		builder.setTitle("Select devices to play");        
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
	         //final boolean[] flag_items = null ;
	         
	    builder.setMultiChoiceItems(choiceList,null, new OnMultiChoiceClickListener() {
	             
	            
				@Override
				public void onClick(DialogInterface arg0, int which, boolean isChecked) {
					for(int i=0;i<MainActivity.currentEeg.length;i++)
	   	        	 	if ((MainActivity.currentEeg[i]!= null) && (choiceList[which].toString().contains((MainActivity.currentEeg[i].getName().replace("\n", "")))))
	   	        	 		if (isChecked)
	   	        	 			selected.add(new Integer(i));
	   	        	 		else
	   	        	 			selected.remove(new Integer(i));
	            	

	            	//Toast.makeText(getBaseContext(), "Select "+MainActivity.currentEeg[selected].getName(), Toast.LENGTH_SHORT).show();
	                
				}
	        });
	        builder.setPositiveButton(  
	        	      "Play",   
	        	      new DialogInterface.OnClickListener() {  
	        	        public void onClick(DialogInterface dialog, int which) {  

	        	        	CCDirector.sharedDirector().resume();
	        	        }  
	        	      }  
	        	    ); 
	        
	    AlertDialog alert = builder.create();   
	    alert.show();
	    */    
		Toast.makeText(getBaseContext(), "Only touch, we are working to play with your mind", Toast.LENGTH_LONG).show();
        
	    CCDirector.sharedDirector().pause();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		CCDirector.sharedDirector().attachInView(_glSurfaceView);
		
		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		
		CCDirector.sharedDirector().setDisplayFPS(true);
		
		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);
		
		CCScene scene = GameLayer.scene();
		CCDirector.sharedDirector().runWithScene(scene);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		CCDirector.sharedDirector().pause();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		CCDirector.sharedDirector().resume();
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		
		CCDirector.sharedDirector().end();
	}
}