package com.motoli.apps.allsubjects;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class Master_Parent extends Activity {
	protected  Motoli_Application appData;

	protected boolean allowSound=true;
	protected int maxVolume=100;
	
	protected Handler audioHandler = new Handler();

	protected Audio_Functions audioFunctions;
	
	
	public Master_Parent() {
		// TODO Auto-generated constructor stub
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		audioFunctions =new Audio_Functions(this, allowSound,maxVolume);
		audioFunctions.stopAudio();
		appData=((Motoli_Application) getApplicationContext());

	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////

	protected long playGeneralAudio(String audio_url){
		return audioFunctions.playGeneralAudio(audio_url);
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected void playClickSound(){
		audioFunctions.playGeneralAudio("sfx_select");
		
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean result =true;
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			moveBackwords();
		}
	    if (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
	    {
	    	result=false;
	       // DebugLog.d(TAG, "volume up");
	    }
	    else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
	    {
	    	result=false;
	       // DebugLog.d(TAG, "volume down");
	    }
	    
		return result;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected void moveBackwords(){
		int class_number=appData.getPreviousClass();
		Intent main;
		audioFunctions.stopAudio();
		
		switch(class_number){
			default:
			case 0://FRONT PAGE
				new AlertDialog.Builder(this)
			    .setTitle(getResources().getString(R.string.strLeaveApp))
			    .setMessage(getResources().getString(R.string.strWouldYouLieToLeave))
			    .setPositiveButton(getResources().getString(R.string.strYes), new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	
			    		finish();
			        }
			     })
			    .setNegativeButton(getResources().getString(R.string.strNo), new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        }
			     })
			    /* .setNeutralButton(getResources().getString(R.string.strCancel), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				})*/
			     .show();
				//finish();
				break;
			case 1://USER SELECT
				main = new Intent(getApplicationContext(),UserSelect.class);
		  		startActivity(main);
		  		finish();break;
			case 2://LAUNCH PLATFORM
				main = new Intent(getApplicationContext(),Launch_Platform.class);
		  		startActivity(main);
		  		finish();
				break;
			case 3://ACTIVITIES PLATFORM
				main = new Intent(getApplicationContext(),Activities_Platform.class);
		  		startActivity(main);
		  		finish();
				break;
			case 4://ACTIVITY LE02
				main = new Intent(getApplicationContext(),Activity_LE02.class);
		  		startActivity(main);
		  		finish();
				break;
			case 5://ACTIVITY TL01
				main = new Intent(getApplicationContext(),Activity_TL01.class);
		  		startActivity(main);
		  		finish();
		  		break;
			case 6://ACTIVITY WD07
				main = new Intent(getApplicationContext(),Activity_WD07.class);
		  		startActivity(main);
		  		finish();	
				break;
		}
	}
}
