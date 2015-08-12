package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import com.motoli.apps.allsubjects.R;
import com.motoli.apps.allsubjects.R.drawable;



import android.graphics.Paint;




import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;


public class Activity_WD04 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{
	

	
	private int currentTextLocation=0;
	private int incorrectInRound=0;
	
	private String correctText="";
	
	private ArrayList<ArrayList<String>> currentText;
	
	private ArrayList<ArrayList<String>> currentWords;
	
	private ArrayList<ArrayList<ArrayList<String>>> allActivityText;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
		setContentView(R.layout.activity_wd04);
		appData.addToClassOrder(5);
		allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

		beingValidated=true;
		
		clearActivity();
		setUpListeners();
		setupFrameListens();
		
		((TextView) findViewById(R.id.Activity_Text0)).setTypeface(appData.getCurrentFontType());	

		
		getLoaderManager().initLoader(MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);
	    ///Uri todoUri = Motoli_ContentProvidor.CONTENT_URI_WRD_LTR_ACTIVITIES;

	}//end public void onCreate(Bundle savedInstanceState) {
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private long playInstructionAudio(){
		return playGeneralAudio("info_wd04.mp3");
	}
	private void clearActivity(){
		findViewById(R.id.Activity_LoadingCircle1).setVisibility(ProgressBar.VISIBLE);
		findViewById(R.id.Activity_LoadingCircle2).setVisibility(ProgressBar.VISIBLE);
		findViewById(R.id.Activity_LoadingCircle3).setVisibility(ProgressBar.VISIBLE);
		findViewById(R.id.Activity_LoadingCircle4).setVisibility(ProgressBar.VISIBLE);
		


		((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank);
		((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank);
		((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank);
		((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank);

		((ImageView) findViewById(R.id.Activity_Image1)).setBackgroundResource(R.drawable.rounded_border_unchosen);
		((ImageView) findViewById(R.id.Activity_Image2)).setBackgroundResource(R.drawable.rounded_border_unchosen);
		((ImageView) findViewById(R.id.Activity_Image3)).setBackgroundResource(R.drawable.rounded_border_unchosen);
		((ImageView) findViewById(R.id.Activity_Image4)).setBackgroundResource(R.drawable.rounded_border_unchosen);
		
		((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);
		
		clearFrames();
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void displayScreen(){
		incorrectInRound=0;
		
		currentText = new ArrayList<ArrayList<String>>(allActivityText.get(roundNumber));
		Collections.shuffle(currentText);
		
		for(int i=0;i<4;i++){
			
			if(currentText.get(i).get(0).equals("1")){
				correctLocation=i;
				correctText=currentText.get(i).get(2);
				matchingLtrWrdAudio=currentText.get(i).get(3);
			}
			
			processPlacingImage(i);
		}
		
		((TextView) findViewById(R.id.Activity_Text0)).setText(correctText);
		findViewById(R.id.Activity_LoadingCircle0).setVisibility(ProgressBar.INVISIBLE);
		
		
		long audioDuration=0;
		if(roundNumber==0)
			audioDuration=playInstructionAudio();
		
		audioHandler.postDelayed(playLtrAudio, audioDuration+20);
		
	}//end private void displayScreen(Cursor currentWordsCursor){	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void processPlacingImage(int wordNumber){
		Class<drawable> res = R.drawable.class;
		int drawableId = 0;
		Field field;
		
		try{
			String imageName=currentText.get(wordNumber).get(4).replace(".jpg", "").replace(".png", "").trim();
			field = res.getField(imageName);
			drawableId = field.getInt(null);
			
			int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
			
			switch(wordNumber){
				default:
				case 0:{
					findViewById(R.id.Activity_LoadingCircle1).setVisibility(ProgressBar.INVISIBLE);
					((ImageView) findViewById(R.id.Activity_Image1)).setTag(currentText.get(wordNumber).get(0));
					((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(resID);		
					break;
				}
				case 1:{
					findViewById(R.id.Activity_LoadingCircle2).setVisibility(ProgressBar.INVISIBLE);
					((ImageView) findViewById(R.id.Activity_Image2)).setTag(currentText.get(wordNumber).get(0));						
					((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(resID);
					break;
				}
				case 2:{
					findViewById(R.id.Activity_LoadingCircle3).setVisibility(ProgressBar.INVISIBLE);
					((ImageView) findViewById(R.id.Activity_Image3)).setTag(currentText.get(wordNumber).get(0));
					((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(resID);	
					break;
				}
				case 3:{
					findViewById(R.id.Activity_LoadingCircle4).setVisibility(ProgressBar.INVISIBLE);
					((ImageView) findViewById(R.id.Activity_Image4)).setTag(currentText.get(wordNumber).get(0));
					((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(resID);					
					break;
				}
			}
			
		}catch (Exception e) {
			Log.e("MyTag", "Failure to get drawable id.", e);
		}
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private Runnable playLtrAudio = new Runnable(){		
		
		@Override
		public void run(){
			audioHandler.removeCallbacks(playLtrAudio);
			beingValidated=false;
			//if(!matchingLtrWrdAudio.equals(""))
			//	playGeneralAudio(matchingLtrWrdAudio);			
		}
	};
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//end private void displayScreen(Cursor currentWordsCursor){	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	

	

	protected void setupFrameListens(){
		findViewById(R.id.Activity_Image1).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentText.get(0).get(7).equals("0") && !beingValidated){
					currentTextLocation=0;
					currentLtrWrdAudio=currentText.get(0).get(3);
					currentWordID=currentText.get(0).get(1);
					//playGeneralAudio(currentLtrWrdAudio);
					correctChoice=(currentText.get(0).get(0).equals("1")) ? true : false;
					setUpFrame(0);	
				}
			}
		});	
		
		findViewById(R.id.Activity_Image2).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentText.get(1).get(7).equals("0") && !beingValidated){
					currentTextLocation=1;
					currentLtrWrdAudio=currentText.get(1).get(3);
					currentWordID=currentText.get(1).get(1);
					//playGeneralAudio(currentLtrWrdAudio);
					correctChoice=(currentText.get(1).get(0).equals("1")) ? true : false;
					setUpFrame(1);	
				}
			}
		});	
		
		findViewById(R.id.Activity_Image3).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentText.get(2).get(7).equals("0") && !beingValidated){
					currentTextLocation=2;
					currentLtrWrdAudio=currentText.get(2).get(3);
					currentWordID=currentText.get(2).get(1);
					//playGeneralAudio(currentLtrWrdAudio);
					correctChoice=(currentText.get(2).get(0).equals("1")) ? true : false;
					setUpFrame(2);	
				}
			}
		});	
		
		findViewById(R.id.Activity_Image4).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentText.get(3).get(7).equals("0") && !beingValidated){
					currentTextLocation=3;
					currentLtrWrdAudio=currentText.get(3).get(3);
					currentWordID=currentText.get(3).get(1);
					//playGeneralAudio(currentLtrWrdAudio);
					correctChoice=(currentText.get(3).get(0).equals("1")) ? true : false;
					setUpFrame(3);	
				}
			}
		});	
		
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpFrame(int frameNumber){
		clearFrames();
		((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on);
		validateAvailable=true;
	//	validateFlashPosition=0;
	//	validateFlash.removeCallbacks(processValidateFlash);
	//	validateFlash.postDelayed(processValidateFlash, (long)50);
		switch(frameNumber){
			default:
			case 0:{
				((ImageView)findViewById(R.id.Activity_ImageFrame1)).setImageResource(R.drawable.frm_wd04_on);
				break;
			}
			case 1:{
				((ImageView)findViewById(R.id.Activity_ImageFrame2)).setImageResource(R.drawable.frm_wd04_on);
				break;
			}
			case 2:{
				((ImageView)findViewById(R.id.Activity_ImageFrame3)).setImageResource(R.drawable.frm_wd04_on);
				break;
			}
			case 3:{
				((ImageView)findViewById(R.id.Activity_ImageFrame4)).setImageResource(R.drawable.frm_wd04_on);
				break;
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void clearFrames(){
		//clear frames
		((ImageView)findViewById(R.id.Activity_ImageFrame1)).setImageResource(R.drawable.frm_wd04_off);
		((ImageView)findViewById(R.id.Activity_ImageFrame2)).setImageResource(R.drawable.frm_wd04_off);
		((ImageView)findViewById(R.id.Activity_ImageFrame3)).setImageResource(R.drawable.frm_wd04_off);
		((ImageView)findViewById(R.id.Activity_ImageFrame4)).setImageResource(R.drawable.frm_wd04_off);


	}
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private void validate(){
		beingValidated=true;
		 String where=" WHERE "+S4K_Database.App_Users_Words_Activities_Right_Wrong.APP_USER_ID+"="+appData.getCurrentUserID()
		    		+" AND "+S4K_Database.App_Users_Words_Activities_Right_Wrong.WORD_ID+"="+currentWordID
		    		+" AND "+S4K_Database.App_Users_Words_Activities_Right_Wrong.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
		 String[] selectionArgs=null;
		 ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

		if(correctChoice){
			//processLayoutAfterGuess(true);
			//correctWordCount++;
			

			((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
			selectionArgs = new String[]{
					"1",
					"0",
					currentGSP.get(3),
					currentWordID};
			
			
			getContentResolver().update(Motoli_ContentProvidor.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
	    	 switch(currentTextLocation){
	    	 	default:
	    	 	case 0:
					currentText.get(0).add(7, "1");
					((ImageView)findViewById(R.id.Activity_Image1)).setBackgroundResource(R.drawable.rounded_border_correct);
					((ImageView)findViewById(R.id.Activity_Image4)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView)findViewById(R.id.Activity_Image2)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView)findViewById(R.id.Activity_Image3)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank);		    		
					
					((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank);
					((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank);
	    	 		break;
	    	 	case 1:
					currentText.get(1).add(7, "1");
					((ImageView)findViewById(R.id.Activity_Image2)).setBackgroundResource(R.drawable.rounded_border_correct);
					((ImageView)findViewById(R.id.Activity_Image1)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView)findViewById(R.id.Activity_Image4)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView)findViewById(R.id.Activity_Image3)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank);		    			
					((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank);
					((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank);
	    	 		break;
	    	 	case 2:
					currentText.get(2).add(7, "1");
					((ImageView)findViewById(R.id.Activity_Image3)).setBackgroundResource(R.drawable.rounded_border_correct);
					((ImageView)findViewById(R.id.Activity_Image1)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView)findViewById(R.id.Activity_Image2)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView)findViewById(R.id.Activity_Image4)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank);		    			
					((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank);
					((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank);
	    	 		break;
	    	 	case 3:
					currentText.get(3).add(7, "1");
					((ImageView)findViewById(R.id.Activity_Image4)).setBackgroundResource(R.drawable.rounded_border_correct);
					((ImageView)findViewById(R.id.Activity_Image1)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView)findViewById(R.id.Activity_Image2)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView)findViewById(R.id.Activity_Image3)).setBackgroundResource(R.drawable.rounded_border_unchosen);
					((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank);		    		
					((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank);
					((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank);
	    	 		break;
	    	 }
			
			
		}else{
			incorrectInRound++;

			((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
			selectionArgs = new String[]{
					"0",
					"1",
					currentGSP.get(3),
					currentWordID};
			
			
			getContentResolver().update(Motoli_ContentProvidor.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
		     if(incorrectInRound>=2){
		    	 switch(correctLocation){
		    	 default:
		    	 	case 0:
						currentText.get(0).add(7, "1");
						((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank);		    			((TextView) findViewById(R.id.Activity_Text2)).setText("");
						((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank);
						((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank);
		    	 		break;
		    	 	case 1:
						currentText.get(1).add(7, "1");
						((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank);		    			((TextView) findViewById(R.id.Activity_Text2)).setText("");
						((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank);
						((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank);
		    	 		break;
		    	 	case 2:
						currentText.get(2).add(7, "1");
						((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank);		    			((TextView) findViewById(R.id.Activity_Text2)).setText("");
						((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank);
						((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank);
		    	 		break;
		    	 	case 3:
						currentText.get(3).add(7, "1");
						((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank);		    			((TextView) findViewById(R.id.Activity_Text2)).setText("");
						((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank);
						((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank);
						break;
		    	 }
		     }else{
		    	 switch(currentTextLocation){
		    	 	default:
		    	 	case 0:
		    	 		currentText.get(0).add(7, "1");
		    	 		((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank);
		    	 		break;
		    	 	case 1:
		    	 		currentText.get(1).add(7, "1");
		    	 		((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank);
		    	 		break;
		    	 	case 2:
		    	 		currentText.get(2).add(7, "1");
		    	 		((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank);
		    	 		break;
		    	 	case 3:
						currentText.get(3).add(7, "1");
						((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank);
		    	 		break;
		    	 }
		     }
			//processLayoutAfterGuess(false);

		}
		
		//processPoints();
		
		
		//processGuessPosition=0;
		
		//guessHandler.postDelayed(processGuess, 10);
		
		processGuessPosition=0;
		guessHandler.postDelayed(processGuess, 10);
		
		
		
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private Runnable processGuess = new Runnable(){
		
		//beingValidated=false;
		
		
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
					audioDuration=playGeneralAudio(currentLtrWrdAudio);
					processGuessPosition++;
					audioHandler.postDelayed(processGuess, audioDuration+10);
					break;
				}
				case 2:{
					guessHandler.removeCallbacks(processGuess);
					clearFrames();
					if(correctChoice){
						roundNumber++;
						if(roundNumber!=(allActivityText.size())){
							//beingValidated=false;
							validateAvailable=false;
							clearActivity();
							processGuessPosition++;
							guessHandler.postDelayed(processGuess, 500);
						}else{
							lastActivityData=0;
							lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
						}
					}else{
						((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);
						beingValidated=false;
						validateAvailable=false;
					}
	
					break;
				}
				case 3:{
					((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);
					displayScreen();
					guessHandler.removeCallbacks(processGuess);
					break;
				}
			}//switch(processGuessPosition){
		}//public void run(){
	};
	

	
	///////////////////////////////////////////////////////////////////////////////////////////////////


	
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
	////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void shuffleGameData(){
	
		//Collections.shuffle(currentWords);
		ArrayList<ArrayList<String>> sortedWords=new ArrayList<ArrayList<String>>(currentWords);
		String word_id="";
		allActivityText.clear();
	
		for(int currentCount=0; currentCount<currentWords.size();currentCount++){
			allActivityText.add(new ArrayList<ArrayList<String>>());
			allActivityText.get(currentCount).add(new ArrayList<String>());
			allActivityText.get(currentCount).get(0).add("1"); //0
			
			word_id=currentWords.get(currentCount).get(0);
			
			allActivityText.get(currentCount).get(0).add(currentWords.get(currentCount).get(0)); //word_id 1
			allActivityText.get(currentCount).get(0).add(currentWords.get(currentCount).get(1)); //word_text 2
			allActivityText.get(currentCount).get(0).add(currentWords.get(currentCount).get(2)); //audio_url 3
			allActivityText.get(currentCount).get(0).add(currentWords.get(currentCount).get(5));	//word_image 4
			allActivityText.get(currentCount).get(0).add(currentWords.get(currentCount).get(3)); //number correct 5
			allActivityText.get(currentCount).get(0).add(currentWords.get(currentCount).get(4)); //number incorrect 6
			allActivityText.get(currentCount).get(0).add("0"); //guess yet 6
	
			
			Collections.shuffle(sortedWords);
			
			int distCurrentCount=1;
			for(int distCount=0;distCount<sortedWords.size(); distCount++){
				
				if(!sortedWords.get(distCount).get(0).equals(word_id)){
					allActivityText.get(currentCount).add(new ArrayList<String>());
					allActivityText.get(currentCount).get(distCurrentCount).add("0");
	
					allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(0)); //word_id
					allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(1)); //word_text
					allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(2)); //audio_url
					allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(5));	//word_image
					allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(3)); //number correct
					allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(4)); //number incorrect
					allActivityText.get(currentCount).get(distCurrentCount).add("0"); //guessed yet
					
					distCurrentCount++;
					if(distCurrentCount>=4){
						break;
					}
				}//end if(!sortedWords.get(distCount).get(0).equals(word_id)){
			}//end for(int distCount=0;distCount<sortedWords.size(); distCount++){
			
			
		}//end for(int currentCount=0; currentCount<currentWords.size();currentCount++){
		
		
		for(int i=0; i<allActivityText.size(); i++){
			Collections.shuffle(allActivityText.get(i));
		}
		
		
	}//end private void shuffleGameData(){


	private void setUpListeners(){
		findViewById(R.id.Activity_CorrectText).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!matchingLtrWrdAudio.equals(""))
					playGeneralAudio(matchingLtrWrdAudio);
			}
		});
		
		
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
					validateHandler.removeCallbacks(processValidateFlash);
					validate();	
				}		
			}
		});

	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

}