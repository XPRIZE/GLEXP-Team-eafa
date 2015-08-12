package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
import java.util.ArrayList;
import java.util.Collections;




import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Paint;

import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Activity_WD07 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{
	
	private int currentTextLocation=0;
	
	private int correctWordCount=0;
	private int processGuessPosition=0;
	private int numberOfWordsChosen=0;
	
	private String currentWordID="";
	
	private boolean allowWordAudio=false;
	
	protected ArrayList<String> guessedCorrectWords;
	private ArrayList<String> wordStatus;
	private ArrayList<String> chosenFrames;
	//Handlers



	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
		setContentView(R.layout.activity_wd07);
		
		appData.addToClassOrder(6);
		allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

		beingValidated=true;
		
		clearActivity();
		viewProgressBars();
		
		setUpListeners();
		
		roundNumber=0;


		currentText = new ArrayList<ArrayList<String>>();
		chosenFrames=new ArrayList<String>();
		chosenFrames.add("");
		chosenFrames.add("");
		
		validateAvailable=false;
		beingValidated=false;
		
		//ArrayList<String> currentUser=new ArrayList<String>();
		
		//database
		
		getLoaderManager().initLoader(MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);

	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void displayScreen(){	
		hideProgressBars();
		loadImagesAndHideText();
		wordStatus=new ArrayList<String>();
		for(int i=0; i<6;i++){
			wordStatus.add("0");
		}
		
		numberOfWordsChosen=0;
		correctWordCount=0;

		if(roundNumber==0){
			long audioDuration=playInstructionAudio();
		}
		
		clearText();
		currentText.clear();	
		currentText = new ArrayList<ArrayList<String>>(allActivityText.get(roundNumber));
		Collections.shuffle(currentText);
		
		((TextView)findViewById(R.id.Activity_TextResponce1)).setTag(currentText.get(0).get(0));
		((TextView)findViewById(R.id.Activity_TextResponce1)).setText(currentText.get(0).get(1));

		((TextView)findViewById(R.id.Activity_TextResponce2)).setTag(currentText.get(1).get(0));
		((TextView)findViewById(R.id.Activity_TextResponce2)).setText(currentText.get(1).get(1));
		
		((TextView)findViewById(R.id.Activity_TextResponce3)).setTag(currentText.get(2).get(0));
		((TextView)findViewById(R.id.Activity_TextResponce3)).setText(currentText.get(2).get(1));
		
		((TextView)findViewById(R.id.Activity_TextResponce4)).setTag(currentText.get(3).get(0));
		((TextView)findViewById(R.id.Activity_TextResponce4)).setText(currentText.get(3).get(1));
		
		((TextView)findViewById(R.id.Activity_TextResponce5)).setTag(currentText.get(4).get(0));
		((TextView)findViewById(R.id.Activity_TextResponce5)).setText(currentText.get(4).get(1));
		
		((TextView)findViewById(R.id.Activity_TextResponce6)).setTag(currentText.get(5).get(0));
		((TextView)findViewById(R.id.Activity_TextResponce6)).setText(currentText.get(5).get(1));
		
		validateAvailable=false;
		beingValidated=false;
		setupFrameListens();	
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private void hideProgressBars(){
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle1)).setVisibility(ProgressBar.INVISIBLE);
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle2)).setVisibility(ProgressBar.INVISIBLE);
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle3)).setVisibility(ProgressBar.INVISIBLE);
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle4)).setVisibility(ProgressBar.INVISIBLE);
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle5)).setVisibility(ProgressBar.INVISIBLE);
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle6)).setVisibility(ProgressBar.INVISIBLE);
	}
	
	private void viewProgressBars(){
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle1)).setVisibility(ProgressBar.VISIBLE);
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle2)).setVisibility(ProgressBar.VISIBLE);
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle3)).setVisibility(ProgressBar.VISIBLE);
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle4)).setVisibility(ProgressBar.VISIBLE);
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle5)).setVisibility(ProgressBar.VISIBLE);
		((ProgressBar) findViewById(R.id.Activity_LoadingCircle6)).setVisibility(ProgressBar.VISIBLE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpListeners(){
			
			findViewById(R.id.btnInfo).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					playClickSound();
					playInstructionAudio();			
				}
			});
			 
				findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						playClickSound();
						moveBackwords();		
					}
				});

			


		}


	////////////////////////////////////////////////////////////////////////////////////////////

	private int playInstructionAudio(){
		/**
		 * 
		 * THE COMMENTED TEXT BELOW IS IN CASE THIS WILL BE USED IN WORDS AS WELL 
		 * AS LETTERS TO BE EDITED LATER
		 * 
		 */
		/*
		arrayCurrentUnit=appData.getCurrentUnit();

		if(arrayCurrentUnit.get(18).equals("0")){
			

		}else{
			return (int) playGeneralAudio("info_w07");		
		}
		*/
		return (int) playGeneralAudio("info_w07_lt");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////


	
	////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpRound(){

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private void loadImagesAndHideText(){
		((TextView)findViewById(R.id.Activity_TextResponce1)).setVisibility(TextView.INVISIBLE);
		((TextView)findViewById(R.id.Activity_TextResponce2)).setVisibility(TextView.INVISIBLE);
		((TextView)findViewById(R.id.Activity_TextResponce3)).setVisibility(TextView.INVISIBLE);
		((TextView)findViewById(R.id.Activity_TextResponce4)).setVisibility(TextView.INVISIBLE);
		((TextView)findViewById(R.id.Activity_TextResponce5)).setVisibility(TextView.INVISIBLE);
		((TextView)findViewById(R.id.Activity_TextResponce6)).setVisibility(TextView.INVISIBLE);

		((ImageView)findViewById(R.id.Activity_TextBox1)).setVisibility(TextView.INVISIBLE);
		((ImageView)findViewById(R.id.Activity_TextBox2)).setVisibility(TextView.INVISIBLE);
		((ImageView)findViewById(R.id.Activity_TextBox3)).setVisibility(TextView.INVISIBLE);
		((ImageView)findViewById(R.id.Activity_TextBox4)).setVisibility(TextView.INVISIBLE);
		((ImageView)findViewById(R.id.Activity_TextBox5)).setVisibility(TextView.INVISIBLE);
		((ImageView)findViewById(R.id.Activity_TextBox6)).setVisibility(TextView.INVISIBLE);
		
		
		((ImageButton)findViewById(R.id.Activity_ImageView1)).setVisibility(ImageButton.VISIBLE);
		((ImageButton)findViewById(R.id.Activity_ImageView2)).setVisibility(ImageButton.VISIBLE);
		((ImageButton)findViewById(R.id.Activity_ImageView3)).setVisibility(ImageButton.VISIBLE);
		((ImageButton)findViewById(R.id.Activity_ImageView4)).setVisibility(ImageButton.VISIBLE);
		((ImageButton)findViewById(R.id.Activity_ImageView5)).setVisibility(ImageButton.VISIBLE);
		((ImageButton)findViewById(R.id.Activity_ImageView6)).setVisibility(ImageButton.VISIBLE);

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected void setupFrameListens(){
		findViewById(R.id.Activity_ImageView1).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(wordStatus.get(0).equals("0") && !beingValidated && numberOfWordsChosen<2){
					numberOfWordsChosen++;
					currentLtrWrdAudio=currentText.get(0).get(2);
					currentWordID=currentText.get(0).get(0);
					if(!currentWordID.equals("")){
						chosenFrames.set(0, "0");
						correctChoice =(((TextView)findViewById(R.id.Activity_TextResponce1)).getTag().equals(currentWordID)) ? true : false;	
						setUpFrame(0,true);
					}else{
						chosenFrames.set(1, "0");
						currentWordID=((TextView)findViewById(R.id.Activity_TextResponce1)).getTag().toString();
						setUpFrame(0,false);
					}
				}
			}
		});	
		
		findViewById(R.id.Activity_ImageView2).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(wordStatus.get(1).equals("0") && !beingValidated && numberOfWordsChosen<2){
					numberOfWordsChosen++;
					currentLtrWrdAudio=currentText.get(1).get(2);
					currentWordID=currentText.get(1).get(0);
					if(!currentWordID.equals("")){
						chosenFrames.set(0, "1");
						correctChoice =(((TextView)findViewById(R.id.Activity_TextResponce2)).getTag().equals(currentWordID)) ? true : false;	
						setUpFrame(1,true);
					}else{
						chosenFrames.set(1, "1");
						currentWordID=((TextView)findViewById(R.id.Activity_TextResponce2)).getTag().toString();
						setUpFrame(1,false);
					}
				}
			}
		});	
		
		findViewById(R.id.Activity_ImageView3).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(wordStatus.get(2).equals("0") && !beingValidated && numberOfWordsChosen<2){
					numberOfWordsChosen++;
					currentLtrWrdAudio=currentText.get(2).get(2);
					currentWordID=currentText.get(2).get(0);
					if(!currentWordID.equals("")){
						chosenFrames.set(0, "2");
						correctChoice =(((TextView)findViewById(R.id.Activity_TextResponce3)).getTag().equals(currentWordID)) ? true : false;	
						setUpFrame(2,true);
					}else{
						chosenFrames.set(1, "2");
						
						currentWordID=((TextView)findViewById(R.id.Activity_TextResponce3)).getTag().toString();
						setUpFrame(2,false);
					}
				}
			}
		});	
		
		findViewById(R.id.Activity_ImageView4).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(wordStatus.get(3).equals("0") && !beingValidated && numberOfWordsChosen<2){
					numberOfWordsChosen++;
					currentLtrWrdAudio=currentText.get(3).get(2);
					currentWordID=currentText.get(3).get(0);
					if(!currentWordID.equals("")){
						chosenFrames.set(0, "3");
						correctChoice =(((TextView)findViewById(R.id.Activity_TextResponce4)).getTag().equals(currentWordID)) ? true : false;	
						setUpFrame(3,true);
					}else{
						chosenFrames.set(1, "3");
						
						currentWordID=((TextView)findViewById(R.id.Activity_TextResponce4)).getTag().toString();
						setUpFrame(3,false);
					}
				}
			}
		});	
		
		findViewById(R.id.Activity_ImageView5).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(wordStatus.get(4).equals("0") && !beingValidated && numberOfWordsChosen<2){
					numberOfWordsChosen++;
					currentLtrWrdAudio=currentText.get(4).get(2);
					currentWordID=currentText.get(4).get(0);
					if(!currentWordID.equals("")){
						chosenFrames.set(0, "4");
						correctChoice =(((TextView)findViewById(R.id.Activity_TextResponce5)).getTag().equals(currentWordID)) ? true : false;	
						setUpFrame(4,true);
					}else{
						chosenFrames.set(1, "4");
						
						currentWordID=((TextView)findViewById(R.id.Activity_TextResponce5)).getTag().toString();
						setUpFrame(4,false);
					}
				}
			}
		});	
		
		findViewById(R.id.Activity_ImageView6).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(wordStatus.get(5).equals("0") && !beingValidated && numberOfWordsChosen<2){
					numberOfWordsChosen++;
					currentLtrWrdAudio=currentText.get(5).get(2);
					currentWordID=currentText.get(5).get(0);
					if(!currentWordID.equals("")){
						chosenFrames.set(0, "5");
						correctChoice =(((TextView)findViewById(R.id.Activity_TextResponce6)).getTag().equals(currentWordID)) ? true : false;	
						setUpFrame(5,true);
					}else{
						chosenFrames.set(1, "5");
						currentWordID=((TextView)findViewById(R.id.Activity_TextResponce6)).getTag().toString();
						setUpFrame(5,false);
					}
				}
			}
		});	
		
		
	}//protected void setupFrameListens(){
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpFrame(int frameNumber, boolean secondGuess){
		//clearFrames();
		//((ImageView) findViewById(R.id.btnAudio)).setImageResource(R.drawable.btn_audio_on);
		if(secondGuess){
			//((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
			validateAvailable=false;
			allowWordAudio=false;
			long audioDuration=playGeneralAudio(currentLtrWrdAudio);
			validateHandler.postDelayed(processValidate, audioDuration+100);
			
		}else{
			playGeneralAudio(currentLtrWrdAudio);
		}
		switch(frameNumber){
			default:
			case 0:{
				((ImageButton)findViewById(R.id.Activity_ImageView1)).setVisibility(ImageButton.INVISIBLE);
				((TextView)findViewById(R.id.Activity_TextResponce1)).setVisibility(TextView.VISIBLE);
				((ImageView)findViewById(R.id.Activity_TextBox1)).setVisibility(TextView.VISIBLE);

				((ImageView) findViewById(R.id.Activity_TextBox1)).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
				break;
			}
			case 1:{
				((ImageButton)findViewById(R.id.Activity_ImageView2)).setVisibility(ImageButton.INVISIBLE);
				((TextView)findViewById(R.id.Activity_TextResponce2)).setVisibility(TextView.VISIBLE);
				((ImageView)findViewById(R.id.Activity_TextBox2)).setVisibility(TextView.VISIBLE);
				((ImageView) findViewById(R.id.Activity_TextBox2)).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
				break;
			}
			case 2:{
				((ImageButton)findViewById(R.id.Activity_ImageView3)).setVisibility(ImageButton.INVISIBLE);
				((TextView)findViewById(R.id.Activity_TextResponce3)).setVisibility(TextView.VISIBLE);
				((ImageView)findViewById(R.id.Activity_TextBox3)).setVisibility(TextView.VISIBLE);
				((ImageView) findViewById(R.id.Activity_TextBox3)).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
				break;
			}
			case 3:{
				((ImageButton)findViewById(R.id.Activity_ImageView4)).setVisibility(ImageButton.INVISIBLE);
				((TextView)findViewById(R.id.Activity_TextResponce4)).setVisibility(TextView.VISIBLE);
				((ImageView)findViewById(R.id.Activity_TextBox4)).setVisibility(TextView.VISIBLE);
				((ImageView) findViewById(R.id.Activity_TextBox4)).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
				break;
			}
			case 4:{
				((ImageButton)findViewById(R.id.Activity_ImageView5)).setVisibility(ImageButton.INVISIBLE);
				((TextView)findViewById(R.id.Activity_TextResponce5)).setVisibility(TextView.VISIBLE);
				((ImageView)findViewById(R.id.Activity_TextBox5)).setVisibility(TextView.VISIBLE);
				((ImageView) findViewById(R.id.Activity_TextBox5)).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
				break;
			}
			case 5:{
				((ImageButton)findViewById(R.id.Activity_ImageView6)).setVisibility(ImageButton.INVISIBLE);
				((TextView)findViewById(R.id.Activity_TextResponce6)).setVisibility(TextView.VISIBLE);
				((ImageView)findViewById(R.id.Activity_TextBox6)).setVisibility(TextView.VISIBLE);
				((ImageView) findViewById(R.id.Activity_TextBox6)).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
				break;
			}		
		}//end switch(frameNumber){
	}//end private void setUpFrame(int frameNumber){
	
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private Runnable processValidate = new Runnable(){
		@Override
		public void run(){
			validate();
		}
		
	};
	////////////////////////////////////////////////////////////////////////////////////////////

	private void validate(){
		beingValidated=true;
		allowWordAudio=false;
		 String where=" WHERE "+S4K_Database.App_Users_Words_Activities_Right_Wrong.APP_USER_ID+"="+appData.getCurrentUserID()
		    		+" AND "+S4K_Database.App_Users_Words_Activities_Right_Wrong.WORD_ID+"="+currentWordID
		    		+" AND "+S4K_Database.App_Users_Words_Activities_Right_Wrong.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
		 String[] selectionArgs=null;
		 ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

		if(correctChoice){
			selectionArgs = new String[]{
					"1",
					"0",
					currentGSP.get(3),
					currentWordID};
			
			
			getContentResolver().update(Motoli_ContentProvidor.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
		    	processLayoutAfterGuess(true);
			correctWordCount++;
		}else{
			selectionArgs = new String[]{
					"0",
					"1",
					currentGSP.get(3),
					currentWordID};
			
			
			getContentResolver().update(Motoli_ContentProvidor.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
		    	processLayoutAfterGuess(false);
		}
		
		// DONE DIFFERENTLY NOW
		//processPoints();
		
		
		processGuessPosition=0;
		guessHandler.postDelayed(processGuess, 10);
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private void processLayoutAfterGuess(boolean correctWord){
		for(int i=0;i<2;i++){
			int currentWordLocation=Integer.valueOf(chosenFrames.get(i));
		
			switch(currentWordLocation){
				default:
				case 0:{
					if(correctChoice){
						wordStatus.set(0, "2");
						((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.correct_green));
						((ImageView) findViewById(R.id.Activity_TextBox1)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
					}else{
						wordStatus.set(0, "1");
						((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.incorrect_red));
						((TextView) findViewById(R.id.Activity_TextResponce1)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce1)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
						((ImageView) findViewById(R.id.Activity_TextBox1)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
					}
					break;
				}
				case 1:{
					if(correctChoice){
						wordStatus.set(1, "2");
						((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.correct_green));
						((ImageView) findViewById(R.id.Activity_TextBox2)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
					}else{
						wordStatus.set(1, "1");
						((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.incorrect_red));
						((TextView) findViewById(R.id.Activity_TextResponce2)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce2)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
						((ImageView) findViewById(R.id.Activity_TextBox2)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
					}
					break;
				}
				case 2:{
					if(correctChoice){
						wordStatus.set(2, "2");
						((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.correct_green));
						((ImageView) findViewById(R.id.Activity_TextBox3)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
					}else{
						wordStatus.set(2, "1");
						((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.incorrect_red));
						((TextView) findViewById(R.id.Activity_TextResponce3)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce3)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
						((ImageView) findViewById(R.id.Activity_TextBox3)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
					}
					break;
				}
				case 3:{
					if(correctChoice){
						wordStatus.set(3, "2");
						((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.correct_green));
						((ImageView) findViewById(R.id.Activity_TextBox4)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
					}else{
						wordStatus.set(3, "1");
						((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.incorrect_red));
						((TextView) findViewById(R.id.Activity_TextResponce4)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce4)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
						((ImageView) findViewById(R.id.Activity_TextBox4)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
					}
					break;
				}
				case 4:{
					if(correctChoice){
						wordStatus.set(4, "2");
						((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.correct_green));
						((ImageView) findViewById(R.id.Activity_TextBox5)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
					}else{
						wordStatus.set(4, "1");
						((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.incorrect_red));
						((TextView) findViewById(R.id.Activity_TextResponce5)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce5)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
						((ImageView) findViewById(R.id.Activity_TextBox5)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
					}
					break;
				}
				case 5:{
					if(correctChoice){
						wordStatus.set(5, "2");
						((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.correct_green));
						((ImageView) findViewById(R.id.Activity_TextBox6)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
					}else{
						wordStatus.set(5, "1");
						((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.incorrect_red));
						((TextView) findViewById(R.id.Activity_TextResponce6)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce6)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
						((ImageView) findViewById(R.id.Activity_TextBox6)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
					}
					break;
				}			
			}//end switch
		}
	}//end private void processLayoutAfterGuess(boolean correctWord){
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private Runnable processGuess = new Runnable(){
		@Override
		public void run(){
			long audioDuration=0;
			
			switch(processGuessPosition){
				case 0:
				default:{
					if(correctChoice){
						audioDuration=playGeneralAudio("sfx_right");
					}else{
						audioDuration=playGeneralAudio("sfx_wrong");
					}
					processGuessPosition++;
					audioHandler.postDelayed(processGuess, audioDuration+10);
					break;
				}
				case 1:{
					if(correctChoice){
						audioDuration=playGeneralAudio(currentLtrWrdAudio);
					}
					processGuessPosition++;
					audioHandler.postDelayed(processGuess, audioDuration+10);
					break;
				}
				case 2:{
					numberOfWordsChosen=0;
					currentWordID="";
					chosenFrames.set(0, "");
					chosenFrames.set(1, "");
					guessHandler.removeCallbacks(processGuess);
					if(correctWordCount==3){
						roundNumber++;
						if(roundNumber!=(allActivityText.size())){
							clearText();
							inBetweenRounds(0);
							processGuessPosition++;
							guessHandler.postDelayed(processGuess, 500);
						}else{				
							lastActivityData=0;
							lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
						}
					}else{
						//if(correctChoice){
							clearInCorrects();
						//}
						beingValidated=false;
						validateAvailable=false;
					}
					break;
				}
				case 3:{
					inBetweenRounds(1);
					displayScreen();
					guessHandler.removeCallbacks(processGuess);
					break;
				}
			}//switch(processGuessPosition){
		}//public void run(){
	};//end private Runnable processGuess = new Runnable(){
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private void clearInCorrects(){
		if(wordStatus.get(0).equals("0") || wordStatus.get(0).equals("1")){
			wordStatus.set(0, "0");
			((ImageView) findViewById(R.id.Activity_TextBox1)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
			((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce1)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce1)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));	
			((TextView)findViewById(R.id.Activity_TextResponce1)).setVisibility(TextView.INVISIBLE);
			((ImageView)findViewById(R.id.Activity_TextBox1)).setVisibility(TextView.INVISIBLE);
			((ImageButton)findViewById(R.id.Activity_ImageView1)).setVisibility(ImageButton.VISIBLE);	
		}
		if(wordStatus.get(1).equals("0") || wordStatus.get(1).equals("1")){
			wordStatus.set(1, "0");
			((ImageView) findViewById(R.id.Activity_TextBox2)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
			((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce2)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce2)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
			((TextView)findViewById(R.id.Activity_TextResponce2)).setVisibility(TextView.INVISIBLE);
			((ImageView)findViewById(R.id.Activity_TextBox2)).setVisibility(TextView.INVISIBLE);
			((ImageButton)findViewById(R.id.Activity_ImageView2)).setVisibility(ImageButton.VISIBLE);	
		}
		if(wordStatus.get(2).equals("0") || wordStatus.get(2).equals("1")){
			wordStatus.set(2, "0");
			((ImageView) findViewById(R.id.Activity_TextBox3)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
			((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce3)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce3)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
			((TextView)findViewById(R.id.Activity_TextResponce3)).setVisibility(TextView.INVISIBLE);
			((ImageView)findViewById(R.id.Activity_TextBox3)).setVisibility(TextView.INVISIBLE);
			((ImageButton)findViewById(R.id.Activity_ImageView3)).setVisibility(ImageButton.VISIBLE);	
		}
		if(wordStatus.get(3).equals("0") || wordStatus.get(3).equals("1")){
			wordStatus.set(3, "0");
			((ImageView) findViewById(R.id.Activity_TextBox4)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
			((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce4)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce4)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
			((TextView)findViewById(R.id.Activity_TextResponce4)).setVisibility(TextView.INVISIBLE);
			((ImageView)findViewById(R.id.Activity_TextBox4)).setVisibility(TextView.INVISIBLE);
			((ImageButton)findViewById(R.id.Activity_ImageView4)).setVisibility(ImageButton.VISIBLE);	
		}
		if(wordStatus.get(4).equals("0") || wordStatus.get(4).equals("1")){
			wordStatus.set(4, "0");
			((ImageView) findViewById(R.id.Activity_TextBox5)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
			((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce5)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce5)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
			((TextView)findViewById(R.id.Activity_TextResponce5)).setVisibility(TextView.INVISIBLE);
			((ImageView)findViewById(R.id.Activity_TextBox5)).setVisibility(TextView.INVISIBLE);
			((ImageButton)findViewById(R.id.Activity_ImageView5)).setVisibility(ImageButton.VISIBLE);	
		}
		if(wordStatus.get(5).equals("0") || wordStatus.get(5).equals("1")){
			wordStatus.set(5, "0");
			((ImageView) findViewById(R.id.Activity_TextBox6)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
			((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce6)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce6)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
			((TextView)findViewById(R.id.Activity_TextResponce6)).setVisibility(TextView.INVISIBLE);
			((ImageView)findViewById(R.id.Activity_TextBox6)).setVisibility(TextView.INVISIBLE);
			((ImageButton)findViewById(R.id.Activity_ImageView6)).setVisibility(ImageButton.VISIBLE);	
		}
	
	}//end private void clearInCorrects(){
	
	////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////


	private void inBetweenRounds(int level){
		if(level==0){
			clearText();
			viewProgressBars();
		}else{
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	private void clearText(){
		((TextView)findViewById(R.id.Activity_TextResponce1)).setVisibility(TextView.INVISIBLE);
		((TextView)findViewById(R.id.Activity_TextResponce2)).setVisibility(TextView.INVISIBLE);
		((TextView)findViewById(R.id.Activity_TextResponce3)).setVisibility(TextView.INVISIBLE);
		((TextView)findViewById(R.id.Activity_TextResponce4)).setVisibility(TextView.INVISIBLE);
		((TextView)findViewById(R.id.Activity_TextResponce5)).setVisibility(TextView.INVISIBLE);
		((TextView)findViewById(R.id.Activity_TextResponce6)).setVisibility(TextView.INVISIBLE);

		((ImageView)findViewById(R.id.Activity_TextBox1)).setVisibility(TextView.INVISIBLE);
		((ImageView)findViewById(R.id.Activity_TextBox2)).setVisibility(TextView.INVISIBLE);
		((ImageView)findViewById(R.id.Activity_TextBox3)).setVisibility(TextView.INVISIBLE);
		((ImageView)findViewById(R.id.Activity_TextBox4)).setVisibility(TextView.INVISIBLE);
		((ImageView)findViewById(R.id.Activity_TextBox5)).setVisibility(TextView.INVISIBLE);
		((ImageView)findViewById(R.id.Activity_TextBox6)).setVisibility(TextView.INVISIBLE);
		
		
		//clear image
		((TextView)findViewById(R.id.Activity_TextResponce1)).setText("");
		((TextView)findViewById(R.id.Activity_TextResponce2)).setText("");
		((TextView)findViewById(R.id.Activity_TextResponce3)).setText("");
		((TextView)findViewById(R.id.Activity_TextResponce4)).setText("");
		((TextView)findViewById(R.id.Activity_TextResponce5)).setText("");
		((TextView)findViewById(R.id.Activity_TextResponce6)).setText("");
		
		((TextView)findViewById(R.id.Activity_TextResponce1)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.Activity_TextResponce2)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.Activity_TextResponce3)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.Activity_TextResponce4)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.Activity_TextResponce5)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.Activity_TextResponce6)).setTypeface(appData.getCurrentFontType());
		
		((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.reg_black));
		((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.reg_black));
		((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.reg_black));
		((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.reg_black));
		((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.reg_black));
		((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.reg_black));
		
		//remove stricktrough
		((TextView) findViewById(R.id.Activity_TextResponce1)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce1)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		((TextView) findViewById(R.id.Activity_TextResponce2)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce2)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		((TextView) findViewById(R.id.Activity_TextResponce3)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce3)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		((TextView) findViewById(R.id.Activity_TextResponce4)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce4)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		((TextView) findViewById(R.id.Activity_TextResponce5)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce5)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		((TextView) findViewById(R.id.Activity_TextResponce6)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce6)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));

		
		((ImageView) findViewById(R.id.Activity_TextBox1)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
		((ImageView) findViewById(R.id.Activity_TextBox2)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
		((ImageView) findViewById(R.id.Activity_TextBox3)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
		((ImageView) findViewById(R.id.Activity_TextBox4)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
		((ImageView) findViewById(R.id.Activity_TextBox5)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
		((ImageView) findViewById(R.id.Activity_TextBox6)).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////

	
	private void clearActivity(){
		viewProgressBars();
		loadImagesAndHideText();
		clearText();
		
	}//end private void clearText(){
	
	////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void shuffleGameData(){
		
		//Collections.shuffle(currentWords);
		ArrayList<ArrayList<String>> sortedWords=new ArrayList<ArrayList<String>>(currentWords);
		String word_id="";
		allActivityText.clear();
		int currentCount=0;
		int numberOfRoundsAvail=(int)Math.ceil(((double)currentWords.size())/3);
		
		for(int roundCount=0; roundCount<numberOfRoundsAvail;roundCount++){
			allActivityText.add(new ArrayList<ArrayList<String>>());
			for(int wordCount=0; wordCount<6;wordCount++){
				if(currentCount==currentWords.size()){
					currentCount=0;
				}
				allActivityText.get(roundCount).add(new ArrayList<String>());
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(0)); //word_id 1
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(1)); //word_text 2
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(2)); //audio_url 3
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(5));	//word_image	

				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(3)); //number correct 4
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(4)); //number incorrect 5
				
				wordCount++;
				
				allActivityText.get(roundCount).add(new ArrayList<String>());
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(0)); //word_id 1
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(1)); //word_text 2
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(2)); //audio_url 3
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(5));	//word_image	
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(3)); //number correct 4
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(4)); //number incorrect 5
				
				
				currentCount++;
			}//end for(int wordCount=0; wordCount<3;wordCount++){
		}//end for(int roundCount=0; roundCount<numberOfRoundsAvail; roundCount++){
		for(int i=0; i<allActivityText.size(); i++){
			Collections.shuffle(allActivityText.get(i));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection =null;
		CursorLoader cursorLoader=null;
		switch(id){
			default:
			case MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS:{
				
				ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
				projection = new String[]{
						appData.getCurrentActivity().get(0),
						appData.getCurrentUserID(),
						currentGSP.get(0),
						currentGSP.get(1),
						currentGSP.get(2),
						currentGSP.get(3)};
				//NOTICE THIS IS ALWAYS DEFAULTING TO PHASE 1
				
				String selection=S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.GROUP_ID+"="+currentGSP.get(0)
						+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.PHASE_ID+"="+currentGSP.get(1)
						+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.LEVEL_NUMBER+"<="+currentGSP.get(3);
				
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_ACTIVITY_CURRENT_WRDS_LTRS_WRW, projection, selection, null, null);
				break;
			}
		}
		
		return cursorLoader;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		 switch(loader.getId()) {
		 default:
			 break;
		 case MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS:{
			 processData(data);
			 break;
		 }
	 }		
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void processData(Cursor currentWordsCursor){
		currentWords=new ArrayList<ArrayList<String>>();
		int currentWordNumber=0;
		//while(currentWordsCursor.moveToNext()){
		for(currentWordsCursor.moveToFirst(); !currentWordsCursor.isAfterLast(); currentWordsCursor.moveToNext()){
			currentWords.add(new ArrayList<String>());
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("word_id")));
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("word_text")));
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("audio_url")));
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("number_correct")));
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("number_incorrect")));
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("image_url")));

			currentWordNumber++;
		}
		
		shuffleGameData();
		displayScreen();
		
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}

