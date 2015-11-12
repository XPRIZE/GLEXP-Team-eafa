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
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.CursorLoader;
import android.graphics.Paint;

import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ImageView;

public class Activity_LT04 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{

    private int mCurrentTextLocation=0;
    private int correctWordCount=0;

    private int mIncorrectInRound;
    private int mRoundNumber;
    private String mCorrectId;
    private boolean correctChoice=false;

    protected ArrayList<ArrayList<String>> mCurrentText;
    private ArrayList<String> wordStatus;

    private ArrayList<ArrayList<String>> mCurrentWords;

    private ArrayList<ArrayList<ArrayList<String>>> allActivityText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_lt04);
        appData.addToClassOrder(8);
        allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

        beingValidated=true;

        clearActivity();


        findViewById(R.id.lettersBox).setVisibility(TableLayout.VISIBLE);

        ((TextView) findViewById(R.id.Activity_TextResponce1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.Activity_TextResponce2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.Activity_TextResponce3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.Activity_TextResponce4)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.Activity_TextResponce5)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.Activity_TextResponce6)).setTypeface(appData.getCurrentFontType());

        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

        if(currentGSP.get(0).equals("2") && currentGSP.get(1).equals("2")) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(params.leftMargin,
                    getResources().getDimensionPixelSize(R.dimen.lt04_text_ucase_top_margin),
                    params.rightMargin, 0);
            findViewById(R.id.Activity_TextResponce1).setLayoutParams(params);
            findViewById(R.id.Activity_TextResponce2).setLayoutParams(params);
            findViewById(R.id.Activity_TextResponce3).setLayoutParams(params);
            findViewById(R.id.Activity_TextResponce4).setLayoutParams(params);
            findViewById(R.id.Activity_TextResponce5).setLayoutParams(params);
            findViewById(R.id.Activity_TextResponce6).setLayoutParams(params);

        }
        mRoundNumber=0;
        mIncorrectInRound=0;

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


        mCurrentText = new ArrayList<ArrayList<String>>(allActivityText.get(mRoundNumber));
        Collections.shuffle(mCurrentText);



        findViewById(R.id.Activity_TextResponce1).setTag(mCurrentText.get(0));
        ((TextView)findViewById(R.id.Activity_TextResponce1)).setText(mCurrentText.get(0).get(1));

        findViewById(R.id.Activity_TextResponce2).setTag(mCurrentText.get(1));
        ((TextView)findViewById(R.id.Activity_TextResponce2)).setText(mCurrentText.get(1).get(1));

        findViewById(R.id.Activity_TextResponce3).setTag(mCurrentText.get(2));
        ((TextView)findViewById(R.id.Activity_TextResponce3)).setText(mCurrentText.get(2).get(1));

        findViewById(R.id.Activity_TextResponce4).setTag(mCurrentText.get(3));
        ((TextView)findViewById(R.id.Activity_TextResponce4)).setText(mCurrentText.get(3).get(1));

        findViewById(R.id.Activity_TextResponce5).setTag(mCurrentText.get(4));
        ((TextView)findViewById(R.id.Activity_TextResponce5)).setText(mCurrentText.get(4).get(1));

        findViewById(R.id.Activity_TextResponce6).setTag(mCurrentText.get(5));
        ((TextView)findViewById(R.id.Activity_TextResponce6)).setText(mCurrentText.get(5).get(1));


        Collections.shuffle(mCurrentText);


        setAudio();

        long audioDuration=(correctWordCount==0)? playInstructionAudio():0;

        audioHandler.postDelayed(playCurrentWord, audioDuration+10);

        validateAvailable=false;
        beingValidated=false;
        setupFrameListens();
        setUpListeners();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////



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
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);




        ((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.normalBlack));

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
        currentLtrWrdAudio=mCurrentText.get(correctWordCount).get(2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void playMatchingLtrWord(){
        //playGeneralAudio(matchingLtrWrdAudio);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens(){
        findViewById(R.id.Activity_TextResponce1).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText= (ArrayList<String>) findViewById(R.id.Activity_TextResponce1).getTag();
                currentWordID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(0).equals("0") && !beingValidated){
                    mCurrentTextLocation=0;
                    correctChoice =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.Activity_TextResponce2).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText=(ArrayList<String>) findViewById(R.id.Activity_TextResponce2).getTag();
                currentWordID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(1).equals("0") && !beingValidated){
                    mCurrentTextLocation=1;
                    correctChoice =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.Activity_TextResponce3).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText=(ArrayList<String>) findViewById(R.id.Activity_TextResponce3).getTag();
                currentWordID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(2).equals("0") && !beingValidated){
                    mCurrentTextLocation=2;
                    correctChoice =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.Activity_TextResponce4).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText=(ArrayList<String>) findViewById(R.id.Activity_TextResponce4).getTag();
                currentWordID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(3).equals("0") && !beingValidated){
                    mCurrentTextLocation=3;
                    correctChoice =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(3);
                }
            }
        });

        findViewById(R.id.Activity_TextResponce5).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText=(ArrayList<String>) findViewById(R.id.Activity_TextResponce5).getTag();
                currentWordID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(4).equals("0") && !beingValidated){
                    mCurrentTextLocation=4;
                    correctChoice =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(4);
                }
            }
        });

        findViewById(R.id.Activity_TextResponce6).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText=(ArrayList<String>) findViewById(R.id.Activity_TextResponce6).getTag();
                currentWordID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(5).equals("0") && !beingValidated){
                    mCurrentTextLocation=5;
                    correctChoice =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(5);
                }
            }
        });


    }//protected void setupFrameListens(){

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on);
        validateAvailable=true;

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




                audioHandler.postDelayed(playCurrentWord, playInstructionAudio()+10);

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
        return (int) playGeneralAudio("info_lt04");
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
         String[] selectionArgs;
         ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

        if(correctChoice){
            String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                    +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+currentWordID
                    +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);

            selectionArgs = new String[]{
                    "1",
                    "0",
                    currentGSP.get(3),
                    currentWordID,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);

            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
            correctWordCount++;
        }else{
            mIncorrectInRound++;
            String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                    +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+currentWordID
                    +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    currentGSP.get(3),
                    mCorrectId,
                    String.valueOf(mIncorrectInRound)};
             getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);

             ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);

        }

        processLayoutAfterGuess();

        processGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }//end private void validate(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processLayoutAfterGuess(){

        switch(mCurrentTextLocation){
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
            long audioDuration;

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
                        mRoundNumber++;
                        mIncorrectInRound=0;
                        if(mRoundNumber!=(MotoliConstants.INA_ROW_CORRECT)){
                            clearActivity();
                            inBetweenRounds(0);
                            processGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            lastActivityData=0;
                            findViewById(R.id.lettersBox).setVisibility(TableLayout.INVISIBLE);
                            findViewById(R.id.lettersBox).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,2000);
                        }
                    }else{
                        if(correctChoice){
                            clearInCorrects();
                            setAudio();

                            audioDuration=(correctWordCount==0)? playInstructionAudio():0;

                            audioHandler.postDelayed(playCurrentWord, audioDuration+10);

                        }
                        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);
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
            ((TextView) findViewById(R.id.Activity_TextResponce1)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.Activity_TextResponce1)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce1)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(1).equals("0") || wordStatus.get(1).equals("1")){
            wordStatus.set(1, "0");
            ((ImageView) findViewById(R.id.Activity_TextBox2)).setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.Activity_TextResponce2)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.Activity_TextResponce2)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce2)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(2).equals("0") || wordStatus.get(2).equals("1")){
            wordStatus.set(2, "0");
            ((ImageView) findViewById(R.id.Activity_TextBox3)).setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.Activity_TextResponce3)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.Activity_TextResponce3)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce3)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(3).equals("0") || wordStatus.get(3).equals("1")){
            wordStatus.set(3, "0");
            ((ImageView) findViewById(R.id.Activity_TextBox4)).setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.Activity_TextResponce4)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.Activity_TextResponce4)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce4)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(4).equals("0") || wordStatus.get(4).equals("1")){
            wordStatus.set(4, "0");
            ((ImageView) findViewById(R.id.Activity_TextBox5)).setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.Activity_TextResponce5)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.Activity_TextResponce5)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce5)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(5).equals("0") || wordStatus.get(5).equals("1")){
            wordStatus.set(5, "0");
            ((ImageView) findViewById(R.id.Activity_TextBox6)).setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.Activity_TextResponce6)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.Activity_TextResponce6)).setPaintFlags(((TextView) findViewById(R.id.Activity_TextResponce6)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

    }//end private void clearInCorrects(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void inBetweenRounds(int level){
        if(level==0){
            findViewById(R.id.Activity_MicDude).setVisibility(ImageView.INVISIBLE);
            findViewById(R.id.Activity_ProgressMicDude).setVisibility(ProgressBar.VISIBLE);
            findViewById(R.id.Activity_ProgressValidate).setVisibility(ProgressBar.VISIBLE);
        }else{
            findViewById(R.id.Activity_MicDude).setVisibility(ImageView.VISIBLE);
            findViewById(R.id.Activity_ProgressValidate).setVisibility(ProgressBar.INVISIBLE);
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);

            findViewById(R.id.Activity_ProgressMicDude).setVisibility(ProgressBar.INVISIBLE);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void shuffleGameData(){
        allActivityText.clear();

        int nCurrentCount=0;
        int nNumberRounds=MotoliConstants.INA_ROW_CORRECT;


        for(int nRoundCount=0; nRoundCount<nNumberRounds; nRoundCount++){
            allActivityText.add(new ArrayList<ArrayList<String>>());
            for(int nWordCount=0; nWordCount<6;nWordCount++){
                if(nCurrentCount==mCurrentWords.size()){
                    nCurrentCount=1;
                }

                int nPosition=(nWordCount==0) ? 0 : nCurrentCount;

                allActivityText.get(nRoundCount).add(new ArrayList<String>());
                allActivityText.get(nRoundCount).get(nWordCount).add(mCurrentWords.get(nPosition).get(0)); //word_id
                allActivityText.get(nRoundCount).get(nWordCount).add(mCurrentWords.get(nPosition).get(1)); //word_text
                allActivityText.get(nRoundCount).get(nWordCount).add(mCurrentWords.get(nPosition).get(2)); //word_audio
                allActivityText.get(nRoundCount).get(nWordCount).add(mCurrentWords.get(nPosition).get(5));    //word_image
                nCurrentCount=(nWordCount==0 && nRoundCount>0 ) ?nCurrentCount : (nCurrentCount+1) ;
            }//end for(int wordCount=0; wordCount<3;wordCount++){
        }//end for(int roundCount=0; roundCount<numberOfRoundsAvail; roundCount++){
        for(int i=0;i<nNumberRounds; i++){
            Collections.shuffle(allActivityText.get(i));
        }
    }//end private void shuffleGameData(){


    ///////////////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS:{
                ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
                String level_number=(Integer.parseInt(currentGSP.get(3))<=2) ? "3" : currentGSP.get(3);
                if(currentGSP.get(1).equals("3")){
                    currentGSP.set(1,"2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        currentGSP.get(0),
                        currentGSP.get(1),
                        currentGSP.get(2),
                        currentGSP.get(3)};


                //This is due to 6 letter and only 4 or 5 in the first two rounds

                String secondSelection="variable_phase_levels.group_id="+currentGSP.get(0)+
                        " AND variable_phase_levels.phase_id="+currentGSP.get(1)+
                        " AND ((variable_phase_levels.level_number="+currentGSP.get(3)+"-1 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-2 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-3"+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+" ) "+
                        " OR variable_phase_levels.level_number<="+currentGSP.get(3)+"-3)";

                String selection="variable_phase_levels.group_id="+currentGSP.get(0)+
                        " AND variable_phase_levels.phase_id="+currentGSP.get(1)+
                        " AND ((variable_phase_levels.level_number="+level_number+"-1 "+
                        " OR variable_phase_levels.level_number="+level_number+"-2 "+
                        " OR variable_phase_levels.level_number="+level_number+"-3"+
                        " OR variable_phase_levels.level_number="+level_number+" ) "+
                        " OR variable_phase_levels.level_number<="+level_number+"-3)";

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_ACTIVITY_CURRENT_LETTERS_WRW, projection, selection, null, secondSelection);
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

    private void processData(Cursor mCursor){
        mCurrentWords=new ArrayList<ArrayList<String>>();
        int currentWordNumber=0;
        int mNumberCorrectInARow=MotoliConstants.INA_ROW_CORRECT;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentWords.add(new ArrayList<String>());
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("letter_id")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("letter_text")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("audio_url")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("image_url")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6

            mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(mCurrentWords.get(currentWordNumber).get(6)))
                    ? Integer.parseInt(mCurrentWords.get(currentWordNumber).get(6)):mNumberCorrectInARow;

            currentWordNumber++;
            mCursor.moveToNext();
        }

        if((mNumberCorrectInARow==MotoliConstants.INA_ROW_CORRECT)){
           showCompleteLevelStar(true);
        }else{
            showCompleteLevelStar(false);
        }


        shuffleGameData();
        displayScreen();

    }
}
