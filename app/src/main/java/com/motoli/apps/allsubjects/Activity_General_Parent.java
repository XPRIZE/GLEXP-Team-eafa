package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */







import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;


public abstract class Activity_General_Parent extends Master_Parent {

	
	protected String currentLtrWrdAudio="";
	protected String matchingLtrWrdAudio="";
	protected String currentWordID="";
	
	protected boolean correctChoice=false;
	
	protected boolean beingValidated=false;
	protected boolean validateAvailable=false;
	
	protected int correctLocation=0;
	
	protected int roundNumber=0;
	protected int validateFlashPosition=0;
	protected int processGuessPosition=0;
	protected int lastActivityData=0;
	
	protected Handler validateHandler = new Handler();
	protected Handler guessHandler = new Handler();
	protected Handler lastActivityDataHandler = new Handler();
	
	
	protected ArrayList<ArrayList<String>> currentText;
	protected ArrayList<ArrayList<String>> currentWords;
	protected ArrayList<ArrayList<ArrayList<String>>> allActivityText;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	

	////////////////////////////////////////////////////////////////////////////////////////////

	protected Runnable processValidateFlash = new Runnable(){
		@Override
		public void run(){
			Log.d("validate_w02","validateFlashPosition: "+validateFlashPosition);

			if(validateFlashPosition%2==0){
				((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on_h);
			}else{
				((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on);
	
			}
			validateFlashPosition++;
			
			if(validateFlashPosition<=20){
				validateHandler.postDelayed(processValidateFlash, (long)200);
			}else{
				validateHandler.removeCallbacks(processValidateFlash);
				((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on_h);
			}
		}
	};
	   
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	protected Runnable returnToActivities_Platorm = new Runnable(){
		@Override
		public void run(){
			long audioDuration=0;
			switch(lastActivityData){
				default:
				case 0:{
					//if(allowBravo){
					//	audioDuration=playPageAudio(R.raw.good_job);
					//}
					lastActivityData++;
					lastActivityDataHandler.postDelayed(returnToActivities_Platorm, audioDuration+500);
					break;
				}
				case 1:{
					moveBackwords();
				}
			}//end switch(lastPagePosition){
		}
	};//end private Runnable returnToActivitiesPage = new Runnable(){
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

}