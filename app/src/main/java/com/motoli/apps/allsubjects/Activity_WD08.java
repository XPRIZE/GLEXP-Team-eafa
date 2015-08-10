package com.motoli.apps.allsubjects;
import java.util.ArrayList;
import java.util.Collections;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;

public class Activity_WD08 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{
	
	private int currentTextLocation=0;
	private int correctWordCount=0;

	private boolean correctChoice=false;
	private boolean allowWordAudio=false;
	
	protected ArrayList<ArrayList<String>> currentText;
	protected ArrayList<String> guessedCorrectWords;
	private ArrayList<String> wordStatus;
	
	private ArrayList<ArrayList<String>> currentWords;
	
	private ArrayList<ArrayList<ArrayList<String>>> allActivityText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
		setContentView(R.layout.activity_wd08);
		appData.addToClassOrder(8);
		allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

		beingValidated=true;
		
		clearActivity();

		
		((TextView) findViewById(R.id.Activity_TextResponce1)).setTypeface(appData.getCurrentFontType());
		((TextView) findViewById(R.id.Activity_TextResponce2)).setTypeface(appData.getCurrentFontType());
		((TextView) findViewById(R.id.Activity_TextResponce3)).setTypeface(appData.getCurrentFontType());
		((TextView) findViewById(R.id.Activity_TextResponce4)).setTypeface(appData.getCurrentFontType());
		((TextView) findViewById(R.id.Activity_TextResponce5)).setTypeface(appData.getCurrentFontType());
		((TextView) findViewById(R.id.Activity_TextResponce6)).setTypeface(appData.getCurrentFontType());

			roundNumber=0;

		
		getLoaderManager().initLoader(MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);

		
		
	

	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void displayScreen(){	
	//	hideProgressBars();
	//	loadImagesAndHideText();
		wordStatus=new ArrayList<String>();
		for(int i=0; i<6;i++){
			wordStatus.add("0");
		}
		
	//	numberOfWordsChosen=0;
		correctWordCount=0;

		
		currentText = new ArrayList<ArrayList<String>>(allActivityText.get(roundNumber));
		Collections.shuffle(currentText);

		
		
		((TextView)findViewById(R.id.Activity_TextResponce1)).setTag(currentText.get(0));
		((TextView)findViewById(R.id.Activity_TextResponce1)).setText(currentText.get(0).get(1));

		((TextView)findViewById(R.id.Activity_TextResponce2)).setTag(currentText.get(1));
		((TextView)findViewById(R.id.Activity_TextResponce2)).setText(currentText.get(1).get(1));
		
		((TextView)findViewById(R.id.Activity_TextResponce3)).setTag(currentText.get(2));
		((TextView)findViewById(R.id.Activity_TextResponce3)).setText(currentText.get(2).get(1));
		
		((TextView)findViewById(R.id.Activity_TextResponce4)).setTag(currentText.get(3));
		((TextView)findViewById(R.id.Activity_TextResponce4)).setText(currentText.get(3).get(1));
		
		((TextView)findViewById(R.id.Activity_TextResponce5)).setTag(currentText.get(4));
		((TextView)findViewById(R.id.Activity_TextResponce5)).setText(currentText.get(4).get(1));
		
		((TextView)findViewById(R.id.Activity_TextResponce6)).setTag(currentText.get(5));
		((TextView)findViewById(R.id.Activity_TextResponce6)).setText(currentText.get(5).get(1));
		
		
		Collections.shuffle(currentText);
		
		
		setAudio();
		
		long audioDuration=0;
		if(roundNumber==0){
			audioDuration=playInstructionAudio();
		}
		audioHandler.postDelayed(playCurrentWord, (long)(audioDuration+10));
		
		validateAvailable=false;
		beingValidated=false;
		setupFrameListens();	
		setUpListeners();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpForRound(){
		((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
	
		
	}
	
	private void clearActivity(){

		//clear image
		((TextView)findViewById(R.id.Activity_TextResponce1)).setText("");
		((TextView)findViewById(R.id.Activity_TextResponce2)).setText("");
		((TextView)findViewById(R.id.Activity_TextResponce3)).setText("");
		((TextView)findViewById(R.id.Activity_TextResponce4)).setText("");
		((TextView)findViewById(R.id.Activity_TextResponce5)).setText("");
		((TextView)findViewById(R.id.Activity_TextResponce6)).setText("");
		
		findViewById(R.id.Activity_ProgressMicDude).setVisibility(ProgressBar.INVISIBLE);
		findViewById(R.id.Activity_ProgressValidate).setVisibility(ProgressBar.INVISIBLE);
		((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);


		
		
		((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.reg_black));
		((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.reg_black));
		((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.reg_black));
		((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.reg_black));
		((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.reg_black));
		((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.reg_black));
		
		((ImageView) findViewById(R.id.Activity_TextBox1)).setImageResource(R.drawable.rounded_text_unselected);
		((ImageView) findViewById(R.id.Activity_TextBox2)).setImageResource(R.drawable.rounded_text_unselected);
		((ImageView) findViewById(R.id.Activity_TextBox3)).setImageResource(R.drawable.rounded_text_unselected);
		((ImageView) findViewById(R.id.Activity_TextBox4)).setImageResource(R.drawable.rounded_text_unselected);
		((ImageView) findViewById(R.id.Activity_TextBox5)).setImageResource(R.drawable.rounded_text_unselected);
		((ImageView) findViewById(R.id.Activity_TextBox6)).setImageResource(R.drawable.rounded_text_unselected);

		//remove stricktrough
		((TextView) findViewById(R.id.Activity_TextResponce1)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce1)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		((TextView) findViewById(R.id.Activity_TextResponce2)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce2)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		((TextView) findViewById(R.id.Activity_TextResponce3)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce3)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		((TextView) findViewById(R.id.Activity_TextResponce4)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce4)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		((TextView) findViewById(R.id.Activity_TextResponce5)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce5)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		((TextView) findViewById(R.id.Activity_TextResponce6)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce6)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));


	}//end private void clearActivity(){
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setAudio(){
		currentLtrWrdAudio=currentText.get(correctWordCount).get(2);
	}

	////////////////////////////////////////////////////////////////////////////////////////////

	private void loadImages(){
		((ImageView) findViewById(R.id.Activity_WordBox)).setVisibility(ImageView.VISIBLE);
		((ImageView) findViewById(R.id.Activity_MicDude)).setVisibility(ImageView.VISIBLE);

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpRound(){
		wordStatus=new ArrayList<String>();
		for(int i=0; i<6;i++){
			wordStatus.add("0");
		}
		
		correctWordCount=0;
		
		((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
		
		setAudio();
		
		if(roundNumber==0){
			long audioDuration=playInstructionAudio();
			audioHandler.postDelayed(playCurrentWord, (long)(audioDuration+10));	
		}
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void playMatchingLtrWord(){
		//playGeneralAudio(matchingLtrWrdAudio);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected void setupFrameListens(){
		findViewById(R.id.Activity_TextResponce1).setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				ArrayList<String> thisText=new ArrayList<String>();
				thisText= (ArrayList<String>) findViewById(R.id.Activity_TextResponce1).getTag();
				currentWordID=thisText.get(0);
				matchingLtrWrdAudio=thisText.get(2);
				playMatchingLtrWord();
				if(wordStatus.get(0).equals("0") && !beingValidated){
					currentTextLocation=0;
					correctChoice =(thisText.get(0).equals(currentText.get(correctWordCount).get(0))) ? true : false;							
					setUpFrame(0);	
				}
			}
		});	
		
		findViewById(R.id.Activity_TextResponce2).setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				ArrayList<String> thisText=new ArrayList<String>();
				thisText= (ArrayList<String>) findViewById(R.id.Activity_TextResponce2).getTag();
				currentWordID=thisText.get(0);
				matchingLtrWrdAudio=thisText.get(2);
				playMatchingLtrWord();
				if(wordStatus.get(1).equals("0") && !beingValidated){
					currentTextLocation=1;
					correctChoice =(thisText.get(0).equals(currentText.get(correctWordCount).get(0))) ? true : false;							
					setUpFrame(1);	
				}
			}
		});	
		
		findViewById(R.id.Activity_TextResponce3).setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				ArrayList<String> thisText=new ArrayList<String>();
				thisText= (ArrayList<String>) findViewById(R.id.Activity_TextResponce3).getTag();
				currentWordID=thisText.get(0);
				matchingLtrWrdAudio=thisText.get(2);
				playMatchingLtrWord();
				if(wordStatus.get(2).equals("0") && !beingValidated){
					currentTextLocation=2;
					correctChoice =(thisText.get(0).equals(currentText.get(correctWordCount).get(0))) ? true : false;							
					setUpFrame(2);	
				}
			}
		});	
		
		findViewById(R.id.Activity_TextResponce4).setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				ArrayList<String> thisText=new ArrayList<String>();
				thisText= (ArrayList<String>) findViewById(R.id.Activity_TextResponce4).getTag();
				currentWordID=thisText.get(0);
				matchingLtrWrdAudio=thisText.get(2);
				playMatchingLtrWord();
				if(wordStatus.get(3).equals("0") && !beingValidated){
					currentTextLocation=3;
					correctChoice =(thisText.get(0).equals(currentText.get(correctWordCount).get(0))) ? true : false;							
					setUpFrame(3);	
				}
			}
		});	 
		
		findViewById(R.id.Activity_TextResponce5).setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				ArrayList<String> thisText=new ArrayList<String>();
				thisText= (ArrayList<String>) findViewById(R.id.Activity_TextResponce5).getTag();
				currentWordID=thisText.get(0);
				matchingLtrWrdAudio=thisText.get(2);
				playMatchingLtrWord();
				if(wordStatus.get(4).equals("0") && !beingValidated){
					currentTextLocation=4;
					correctChoice =(thisText.get(0).equals(currentText.get(correctWordCount).get(0))) ? true : false;							
					setUpFrame(4);	
				}
			}
		});	
		
		findViewById(R.id.Activity_TextResponce6).setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				ArrayList<String> thisText=new ArrayList<String>();
				thisText= (ArrayList<String>) findViewById(R.id.Activity_TextResponce6).getTag();
				currentWordID=thisText.get(0);
				matchingLtrWrdAudio=thisText.get(2);
				playMatchingLtrWord();
				if(wordStatus.get(5).equals("0") && !beingValidated){
					currentTextLocation=5;
					correctChoice =(thisText.get(0).equals(currentText.get(correctWordCount).get(0))) ? true : false;							
					setUpFrame(5);	
				}
			}
		});	
		
		
	}//protected void setupFrameListens(){
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpFrame(int frameNumber){
		clearFrames();
		((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
		validateAvailable=true;
		
		allowWordAudio=true;
		switch(frameNumber){
			default:
			case 0:{
				((ImageView) findViewById(R.id.Activity_TextBox1)).setImageResource(R.drawable.rounded_text_selected);
				break;
			}
			case 1:{
				((ImageView) findViewById(R.id.Activity_TextBox2)).setImageResource(R.drawable.rounded_text_selected);
				break;
			}
			case 2:{
				((ImageView) findViewById(R.id.Activity_TextBox3)).setImageResource(R.drawable.rounded_text_selected);
				break;
			}
			case 3:{
				((ImageView) findViewById(R.id.Activity_TextBox4)).setImageResource(R.drawable.rounded_text_selected);
				break;
			}
			case 4:{
				((ImageView) findViewById(R.id.Activity_TextBox5)).setImageResource(R.drawable.rounded_text_selected);
				break;
			}
			case 5:{
				((ImageView) findViewById(R.id.Activity_TextBox6)).setImageResource(R.drawable.rounded_text_selected);
				break;
			}		
		}//end switch(frameNumber){
	}//end private void setUpFrame(int frameNumber){
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void clearFrames(){
		//clear frames
		if(wordStatus.get(0).equals("0"))
			((ImageView) findViewById(R.id.Activity_TextBox1)).setImageResource(R.drawable.rounded_text_unselected);
		
		if(wordStatus.get(1).equals("0"))
			((ImageView) findViewById(R.id.Activity_TextBox2)).setImageResource(R.drawable.rounded_text_unselected);
	
		if(wordStatus.get(2).equals("0"))
			((ImageView) findViewById(R.id.Activity_TextBox3)).setImageResource(R.drawable.rounded_text_unselected);
		
		if(wordStatus.get(3).equals("0"))
			((ImageView) findViewById(R.id.Activity_TextBox4)).setImageResource(R.drawable.rounded_text_unselected);
		
		if(wordStatus.get(4).equals("0"))
			((ImageView) findViewById(R.id.Activity_TextBox5)).setImageResource(R.drawable.rounded_text_unselected);
		
		if(wordStatus.get(5).equals("0"))
			((ImageView) findViewById(R.id.Activity_TextBox6)).setImageResource(R.drawable.rounded_text_unselected);

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

		
		findViewById(R.id.btnValidate).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(validateAvailable && !beingValidated){
					playClickSound();
					validate();	
				}		
			}
		});
		
		findViewById(R.id.Activity_MicDude).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!currentLtrWrdAudio.equals(""))
					playGeneralAudio(currentLtrWrdAudio);
			}
		});
		
		
	}//end private void setUpListeners(){
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private int playInstructionAudio(){
		/*arrayCurrentUnit=appData.getCurrentUnit();

		if(arrayCurrentUnit.get(18).equals("0")){
			
			*/
		return (int) playGeneralAudio("info_w08_lt");
		/*}else{
			return (int) playGeneralAudio(R.raw.info_w08);
		}*/
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private Runnable playCurrentWord = new Runnable(){
		@Override
		public void run(){
			playGeneralAudio(currentLtrWrdAudio);
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
			((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
			
			selectionArgs = new String[]{
					"1",
					"0",
					currentGSP.get(3),
					currentWordID};
			
			
			getContentResolver().update(Motoli_ContentProvidor.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
	    	
			((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_ok);
			correctWordCount++;
		}else{

			selectionArgs = new String[]{
					"0",
					"1",
					currentGSP.get(3),
					currentWordID};
		     getContentResolver().update(Motoli_ContentProvidor.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
		    
		     ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_no_ok);
			
		}
		
		processLayoutAfterGuess();
		
		processGuessPosition=0;
		guessHandler.postDelayed(processGuess, 10);
	}//end private void validate(){
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private void processLayoutAfterGuess(){
		
		switch(currentTextLocation){
			default:
			case 0:{
				if(correctChoice){
					wordStatus.set(0, "2");
					((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.correct_green));
					((ImageView) findViewById(R.id.Activity_TextBox1)).setImageResource(R.drawable.rounded_text_correct);
				}else{
					wordStatus.set(0, "1");
					((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.incorrect_red));
					((TextView) findViewById(R.id.Activity_TextResponce1)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce1)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					((ImageView) findViewById(R.id.Activity_TextBox1)).setImageResource(R.drawable.rounded_text_incorrect);
				}
				break;
			}
			case 1:{
				if(correctChoice){
					wordStatus.set(1, "2");
					((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.correct_green));
					((ImageView) findViewById(R.id.Activity_TextBox2)).setImageResource(R.drawable.rounded_text_correct);
				}else{
					wordStatus.set(1, "1");
					((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.incorrect_red));
					((TextView) findViewById(R.id.Activity_TextResponce2)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce2)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					((ImageView) findViewById(R.id.Activity_TextBox2)).setImageResource(R.drawable.rounded_text_incorrect);
				}
				break;
			}
			case 2:{
				if(correctChoice){
					wordStatus.set(2, "2");
					((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.correct_green));
					((ImageView) findViewById(R.id.Activity_TextBox3)).setImageResource(R.drawable.rounded_text_correct);
				}else{
					wordStatus.set(2, "1");
					((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.incorrect_red));
					((TextView) findViewById(R.id.Activity_TextResponce3)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce3)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					((ImageView) findViewById(R.id.Activity_TextBox3)).setImageResource(R.drawable.rounded_text_incorrect);
				}
				break;
			}
			case 3:{
				if(correctChoice){
					wordStatus.set(3, "2");
					((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.correct_green));
					((ImageView) findViewById(R.id.Activity_TextBox4)).setImageResource(R.drawable.rounded_text_correct);
				}else{
					wordStatus.set(3, "1");
					((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.incorrect_red));
					((TextView) findViewById(R.id.Activity_TextResponce4)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce4)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					((ImageView) findViewById(R.id.Activity_TextBox4)).setImageResource(R.drawable.rounded_text_incorrect);
				}
				break;
			}
			case 4:{
				if(correctChoice){
					wordStatus.set(4, "2");
					((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.correct_green));
					((ImageView) findViewById(R.id.Activity_TextBox5)).setImageResource(R.drawable.rounded_text_correct);
				}else{
					wordStatus.set(4, "1");
					((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.incorrect_red));
					((TextView) findViewById(R.id.Activity_TextResponce5)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce5)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					((ImageView) findViewById(R.id.Activity_TextBox5)).setImageResource(R.drawable.rounded_text_incorrect);
				}
				break;
			}
			case 5:{
				if(correctChoice){
					wordStatus.set(5, "2");
					((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.correct_green));
					((ImageView) findViewById(R.id.Activity_TextBox6)).setImageResource(R.drawable.rounded_text_correct);
				}else{
					wordStatus.set(5, "1");
					((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.incorrect_red));
					((TextView) findViewById(R.id.Activity_TextResponce6)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce6)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					((ImageView) findViewById(R.id.Activity_TextBox6)).setImageResource(R.drawable.rounded_text_incorrect);
				}
				break;
			}			
		}//end switch

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
					}else{
						audioDuration=playGeneralAudio(matchingLtrWrdAudio);
					}
					processGuessPosition++;
					audioHandler.postDelayed(processGuess, audioDuration+10);
					break;
				}
				case 2:{
					guessHandler.removeCallbacks(processGuess);
					if(correctWordCount==6){
						roundNumber++;
						if(roundNumber!=(allActivityText.size())){
							clearActivity();
							inBetweenRounds(0);
							processGuessPosition++;
							guessHandler.postDelayed(processGuess, 500);
						}else{				
							lastActivityData=0;
							lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
						}
					}else{
						if(correctChoice){
							clearInCorrects();
							setAudio();
							
							if(roundNumber==0){
								audioDuration=playInstructionAudio();
							}
							audioHandler.postDelayed(playCurrentWord, (long)(audioDuration+10));
							
						}
						((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
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
			((ImageView) findViewById(R.id.Activity_TextBox1)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce1)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce1)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));	
		}
		if(wordStatus.get(1).equals("0") || wordStatus.get(1).equals("1")){
			wordStatus.set(1, "0");
			((ImageView) findViewById(R.id.Activity_TextBox2)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce2)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce2)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		if(wordStatus.get(2).equals("0") || wordStatus.get(2).equals("1")){
			wordStatus.set(2, "0");
			((ImageView) findViewById(R.id.Activity_TextBox3)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce3)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce3)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		if(wordStatus.get(3).equals("0") || wordStatus.get(3).equals("1")){
			wordStatus.set(3, "0");
			((ImageView) findViewById(R.id.Activity_TextBox4)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce4)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce4)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		if(wordStatus.get(4).equals("0") || wordStatus.get(4).equals("1")){
			wordStatus.set(4, "0");
			((ImageView) findViewById(R.id.Activity_TextBox5)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce5)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce5)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		if(wordStatus.get(5).equals("0") || wordStatus.get(5).equals("1")){
			wordStatus.set(5, "0");
			((ImageView) findViewById(R.id.Activity_TextBox6)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce6)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce6)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
	
	}//end private void clearInCorrects(){
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private void clearUnSelected(){
		if(wordStatus.get(0).equals("0") ){
			((ImageView) findViewById(R.id.Activity_TextBox1)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce1)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce1)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));	
		}
		if(wordStatus.get(1).equals("0") ){
			((ImageView) findViewById(R.id.Activity_TextBox2)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce2)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce2)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		if(wordStatus.get(2).equals("0") ){
			((ImageView) findViewById(R.id.Activity_TextBox3)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce3)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce3)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		if(wordStatus.get(3).equals("0") ){
			((ImageView) findViewById(R.id.Activity_TextBox4)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce4)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce4)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		if(wordStatus.get(4).equals("0") ){
			((ImageView) findViewById(R.id.Activity_TextBox5)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce5)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce5)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		if(wordStatus.get(5).equals("0")){
			((ImageView) findViewById(R.id.Activity_TextBox6)).setImageResource(R.drawable.rounded_text_unselected);
			((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.reg_black));
			((TextView) findViewById(R.id.Activity_TextResponce6)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce6)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
	
	}//end private void clearUnSelected(){
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private void inBetweenRounds(int level){
		if(level==0){
			((ImageView) findViewById(R.id.Activity_MicDude)).setVisibility(ImageView.INVISIBLE);
			((ProgressBar) findViewById(R.id.Activity_ProgressMicDude)).setVisibility(ProgressBar.VISIBLE);
			((ProgressBar) findViewById(R.id.Activity_ProgressValidate)).setVisibility(ProgressBar.VISIBLE);
		}else{
			((ImageView) findViewById(R.id.Activity_MicDude)).setVisibility(ImageView.VISIBLE);
			((ProgressBar) findViewById(R.id.Activity_ProgressValidate)).setVisibility(ProgressBar.INVISIBLE);
			((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);

			((ProgressBar) findViewById(R.id.Activity_ProgressMicDude)).setVisibility(ProgressBar.INVISIBLE);
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void shuffleGameData(){
		Collections.shuffle(currentWords);
		allActivityText.clear();
		
		int currentCount=0;
		int numberOfRoundsAvail=(int)Math.ceil(currentWords.size()/6);
		if(numberOfRoundsAvail==0)
			numberOfRoundsAvail=1;
		
		for(int roundCount=0; roundCount<numberOfRoundsAvail; roundCount++){
			allActivityText.add(new ArrayList<ArrayList<String>>());
			for(int wordCount=0; wordCount<6;wordCount++){
				if(currentCount==currentWords.size()){
					currentCount=0;
				}
				allActivityText.get(roundCount).add(new ArrayList<String>());
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(0)); //word_id
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(1)); //word_text
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(2)); //word_audio
				allActivityText.get(roundCount).get(wordCount).add(currentWords.get(currentCount).get(5));	//word_image		
				currentCount++;
			}//end for(int wordCount=0; wordCount<3;wordCount++){
		}//end for(int roundCount=0; roundCount<numberOfRoundsAvail; roundCount++){
		for(int i=0;i<allActivityText.size(); i++){
			Collections.shuffle(allActivityText.get(i));
		}
	}//end private void shuffleGameData(){


	///////////////////////////////////////////////////////////////////////////////////////////////////


	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection =null;
		CursorLoader cursorLoader=null;
		switch(id){
			default:
			case MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS:{
				ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
				String level_number=(Integer.parseInt(currentGSP.get(3))<=2) ? "3" : currentGSP.get(3);

				projection = new String[]{
						appData.getCurrentActivity().get(0),
						appData.getCurrentUserID(),
						currentGSP.get(0),
						currentGSP.get(1),
						currentGSP.get(2),
						currentGSP.get(3)};
				
				
				//This is due to 6 letter and only 4 or 5 in the first two rounds
				String secondSelection=S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.GROUP_ID+"="+currentGSP.get(0)
						+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.PHASE_ID+"="+currentGSP.get(1)
						+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.LEVEL_NUMBER+"<="+currentGSP.get(3);
								
				
				String selection=S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.GROUP_ID+"="+currentGSP.get(0)
						+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.PHASE_ID+"="+currentGSP.get(1)
						+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.LEVEL_NUMBER+"<="+level_number;
				
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_ACTIVITY_CURRENT_WRDS_LTRS_WRW, projection, selection, null, secondSelection);
				break;
			}
		}
		
		return cursorLoader;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////

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

	///////////////////////////////////////////////////////////////////////////////////////////////////////////

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
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("word_id"))); //0
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("word_text"))); //1
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("audio_url"))); //2
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("number_correct"))); //3
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("number_incorrect"))); //4
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex("image_url"))); //5
			

			
			currentWordNumber++;
		}
		
		shuffleGameData();
		displayScreen();
		
	}
}
