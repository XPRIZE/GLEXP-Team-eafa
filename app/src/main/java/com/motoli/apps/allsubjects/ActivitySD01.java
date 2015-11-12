package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class ActivitySD01 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{



    private int mCurrentTextLocation=0;
    private int mIncorrectInRound=0;


    private ArrayList<ArrayList<String>> mCurrentText;

    private ArrayList<ArrayList<String>> mCurrentPhonics;
    private ArrayList<ArrayList<String>> mAllPhonicWords;
    private ArrayList<ArrayList<String>> mAllPhonics;

    private ArrayList<ArrayList<ArrayList<String>>> allActivityText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sd01);
        appData.addToClassOrder(5);
        allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

        beingValidated=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();


        ((TextView) findViewById(R.id.Activity_Text1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.Activity_Text2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.Activity_Text3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.Activity_Text4)).setTypeface(appData.getCurrentFontType());

        getLoaderManager().initLoader(MotoliConstants.CURRENT_PHNC_LTRS, null, this);
        ///Uri todoUri = MotoliContentProvider.CONTENT_URI_WRD_LTR_ACTIVITIES;

    }//end public void onCreate(Bundle savedInstanceState) {


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private long playInstructionAudio(){
        return playGeneralAudio("info_sd01");
    }
    private void clearActivity(){
        findViewById(R.id.Activity_LoadingCircle1).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.Activity_LoadingCircle2).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.Activity_LoadingCircle3).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.Activity_LoadingCircle4).setVisibility(ProgressBar.VISIBLE);

        ((TextView) findViewById(R.id.Activity_Text1)).setText("");
        ((TextView) findViewById(R.id.Activity_Text2)).setText("");
        ((TextView) findViewById(R.id.Activity_Text3)).setText("");
        ((TextView) findViewById(R.id.Activity_Text4)).setText("");

        ((TextView) findViewById(R.id.Activity_Text1)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.Activity_Text2)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.Activity_Text3)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.Activity_Text4)).setTextColor(getResources().getColor(R.color.normalBlack));


        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);

        clearFrames();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){
        mIncorrectInRound=0;





        //correctOrNot.clear();
        mCurrentText = new ArrayList<ArrayList<String>>(allActivityText.get(roundNumber));
        Collections.shuffle(mCurrentText);
    //	allActivityText.get(i)

        ((TextView) findViewById(R.id.Activity_Text1)).setText(mCurrentText.get(0).get(2));
        findViewById(R.id.Activity_LoadingCircle1).setVisibility(ProgressBar.INVISIBLE);
//		mCurrentText.add(new ArrayList<String>(allActivityText.get(roundNumber).get(0)));
        if(mCurrentText.get(0).get(0).equals("1")){
            correctLocation=0;
            matchingLtrWrdAudio=mCurrentText.get(0).get(3);
        }


        ((TextView) findViewById(R.id.Activity_Text2)).setText(mCurrentText.get(1).get(2));
        findViewById(R.id.Activity_LoadingCircle2).setVisibility(ProgressBar.INVISIBLE);
    //	mCurrentText.add(new ArrayList<String>(allActivityText.get(roundNumber).get(1)));
        if(mCurrentText.get(1).get(0).equals("1")){
            correctLocation=1;
            matchingLtrWrdAudio=mCurrentText.get(1).get(3);
        }

        ((TextView) findViewById(R.id.Activity_Text3)).setText(mCurrentText.get(2).get(2));
        findViewById(R.id.Activity_LoadingCircle3).setVisibility(ProgressBar.INVISIBLE);
    //	mCurrentText.add(new ArrayList<String>(allActivityText.get(roundNumber).get(2)));
        if(mCurrentText.get(2).get(0).equals("1")){
            correctLocation=2;
            matchingLtrWrdAudio=mCurrentText.get(2).get(3);
        }

        ((TextView) findViewById(R.id.Activity_Text4)).setText(mCurrentText.get(3).get(2));
        findViewById(R.id.Activity_LoadingCircle4).setVisibility(ProgressBar.INVISIBLE);
    //	mCurrentText.add(new ArrayList<String>(allActivityText.get(roundNumber).get(3)));
        if(mCurrentText.get(3).get(0).equals("1")){
            correctLocation=3;
            matchingLtrWrdAudio=mCurrentText.get(3).get(3);
        }
        long audioDuration=0;
        if(roundNumber==0)
            audioDuration=playInstructionAudio();

        audioHandler.postDelayed(playLtrAudio, audioDuration+20);

    }//end private void displayScreen(Cursor mCurrentPhonicsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playLtrAudio = new Runnable(){

        @Override
        public void run(){
            audioHandler.removeCallbacks(playLtrAudio);
            beingValidated=false;
            if(!matchingLtrWrdAudio.equals(""))
                playGeneralAudio(matchingLtrWrdAudio);
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //end private void displayScreen(Cursor mCurrentPhonicsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens(){
        findViewById(R.id.Activity_TextFrame1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(0).get(6).equals("0") && !beingValidated){
                    mCurrentTextLocation=0;
                    currentLtrWrdAudio=mCurrentText.get(0).get(3);
                    mCurrentPhonicId=mCurrentText.get(0).get(1);
                    //playGeneralAudio(currentLtrWrdAudio);
                    correctChoice=(mCurrentText.get(0).get(0).equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.Activity_TextFrame2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(1).get(6).equals("0") && !beingValidated){
                    mCurrentTextLocation=1;
                    currentLtrWrdAudio=mCurrentText.get(1).get(3);
                    mCurrentPhonicId=mCurrentText.get(1).get(1);
                    //playGeneralAudio(currentLtrWrdAudio);
                    correctChoice=(mCurrentText.get(1).get(0).equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.Activity_TextFrame3).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(2).get(6).equals("0") && !beingValidated){
                    mCurrentTextLocation=2;
                    currentLtrWrdAudio=mCurrentText.get(2).get(3);
                    mCurrentPhonicId=mCurrentText.get(2).get(1);
                    //playGeneralAudio(currentLtrWrdAudio);
                    correctChoice=(mCurrentText.get(2).get(0).equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.Activity_TextFrame4).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(3).get(6).equals("0") && !beingValidated){
                    mCurrentTextLocation=3;
                    currentLtrWrdAudio=mCurrentText.get(3).get(3);
                    mCurrentPhonicId=mCurrentText.get(3).get(1);
                    //playGeneralAudio(currentLtrWrdAudio);
                    correctChoice=(mCurrentText.get(3).get(0).equals("1"));
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
                ((ImageView)findViewById(R.id.Activity_TextFrame1)).setImageResource(R.drawable.frm_sd01_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.Activity_TextFrame2)).setImageResource(R.drawable.frm_sd01_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.Activity_TextFrame3)).setImageResource(R.drawable.frm_sd01_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.Activity_TextFrame4)).setImageResource(R.drawable.frm_sd01_on);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.Activity_TextFrame1)).setImageResource(R.drawable.frm_sd01_off);
        ((ImageView)findViewById(R.id.Activity_TextFrame2)).setImageResource(R.drawable.frm_sd01_off);
        ((ImageView)findViewById(R.id.Activity_TextFrame3)).setImageResource(R.drawable.frm_sd01_off);
        ((ImageView)findViewById(R.id.Activity_TextFrame4)).setImageResource(R.drawable.frm_sd01_off);


    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void validate(){
        beingValidated=true;

        if(correctChoice){
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);

            switch(mCurrentTextLocation){
                default:
                case 0:
                    mCurrentText.get(0).add(6, "1");

                    ((TextView) findViewById(R.id.Activity_Text1)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.Activity_Text2)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text3)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text4)).setText("");
                    break;
                case 1:
                    mCurrentText.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.Activity_Text2)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.Activity_Text1)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text3)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text4)).setText("");
                    break;
                case 2:
                    mCurrentText.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.Activity_Text3)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.Activity_Text2)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text1)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text4)).setText("");
                    break;
                case 3:
                    mCurrentText.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.Activity_Text4)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.Activity_Text2)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text3)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text1)).setText("");
                    break;
            }


        }else{
            mIncorrectInRound++;

            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);

            if(mIncorrectInRound>=2){
                switch(correctLocation){
                    default:
                    case 0:
                        mCurrentText.get(1).add(6, "1");
                        mCurrentText.get(2).add(6, "1");
                        mCurrentText.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text3)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        break;
                    case 1:
                        mCurrentText.get(0).add(6, "1");
                        mCurrentText.get(2).add(6, "1");
                        mCurrentText.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text3)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        break;
                    case 2:
                        mCurrentText.get(0).add(6, "1");
                        mCurrentText.get(1).add(6, "1");
                        mCurrentText.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text3)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        break;
                    case 3:
                        mCurrentText.get(0).add(6, "1");
                        mCurrentText.get(1).add(6, "1");
                        mCurrentText.get(2).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text3)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        break;
                }
            }else{
                switch(mCurrentTextLocation){
                    default:
                    case 0:
                        mCurrentText.get(0).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text1)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        break;
                    case 1:
                        mCurrentText.get(1).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text2)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        break;
                    case 2:
                        mCurrentText.get(2).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text3)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        break;
                    case 3:
                        mCurrentText.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text4)).setTextColor(getResources().getColor(R.color.incorrect_red));
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

    private void processPointsAndView(){
        String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+mCurrentPhonicId
                +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
        if(correctChoice){
            //processLayoutAfterGuess(true);
            //correctWordCount++;


            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    currentGSP.get(3),
                    mCurrentPhonicId,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            switch(mCurrentTextLocation){
                default:
                case 0:
                    mCurrentText.get(0).add(6, "1");

                    ((TextView) findViewById(R.id.Activity_Text1)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.Activity_Text2)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text3)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text4)).setText("");
                    break;
                case 1:
                    mCurrentText.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.Activity_Text2)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.Activity_Text1)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text3)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text4)).setText("");
                    break;
                case 2:
                    mCurrentText.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.Activity_Text3)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.Activity_Text2)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text1)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text4)).setText("");
                    break;
                case 3:
                    mCurrentText.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.Activity_Text4)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.Activity_Text2)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text3)).setText("");
                    ((TextView) findViewById(R.id.Activity_Text1)).setText("");
                    break;
            }


        }else{
            //mIncorrectInRound++;

            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    currentGSP.get(3),
                    mCurrentPhonicId,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(correctLocation){
                    default:
                    case 0:
                        mCurrentText.get(1).add(6, "1");
                        mCurrentText.get(2).add(6, "1");
                        mCurrentText.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text2)).setText("");
                        ((TextView) findViewById(R.id.Activity_Text3)).setText("");
                        ((TextView) findViewById(R.id.Activity_Text4)).setText("");
                        break;
                    case 1:
                        mCurrentText.get(0).add(6, "1");
                        mCurrentText.get(2).add(6, "1");
                        mCurrentText.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text1)).setText("");
                        ((TextView) findViewById(R.id.Activity_Text3)).setText("");
                        ((TextView) findViewById(R.id.Activity_Text4)).setText("");
                        break;
                    case 2:
                        mCurrentText.get(0).add(6, "1");
                        mCurrentText.get(1).add(6, "1");
                        mCurrentText.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text1)).setText("");
                        ((TextView) findViewById(R.id.Activity_Text2)).setText("");
                        ((TextView) findViewById(R.id.Activity_Text4)).setText("");
                        break;
                    case 3:
                        mCurrentText.get(0).add(6, "1");
                        mCurrentText.get(1).add(6, "1");
                        mCurrentText.get(2).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text1)).setText("");
                        ((TextView) findViewById(R.id.Activity_Text2)).setText("");
                        ((TextView) findViewById(R.id.Activity_Text3)).setText("");
                        break;
                }
            }else{
                switch(mCurrentTextLocation){
                    default:
                    case 0:
                        mCurrentText.get(0).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text1)).setText("");
                        break;
                    case 1:
                        mCurrentText.get(1).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text2)).setText("");
                        break;
                    case 2:
                        mCurrentText.get(2).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text3)).setText("");
                        break;
                    case 3:
                        mCurrentText.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.Activity_Text4)).setText("");
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private Runnable processGuess = new Runnable(){

        //beingValidated=false;


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
                    audioDuration=playGeneralAudio(currentLtrWrdAudio);
                    processGuessPosition++;
                    audioHandler.postDelayed(processGuess, audioDuration+10);
                    break;
                }
                case 2:{
                    processPointsAndView();
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
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.CURRENT_PHNC_LTRS_WRDS:{
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_PHNC_LTRS_WRDS,null,null,null,null);
                break;
            }
            case MotoliConstants.CURRENT_PHNC_LTRS:{

                ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        currentGSP.get(0),
                        currentGSP.get(1),
                        currentGSP.get(2),
                        currentGSP.get(3)};
                String selection=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+Database.Variable_Phase_Levels.GROUP_ID+"="+currentGSP.get(0)
                        +" AND "+Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+Database.Variable_Phase_Levels.PHASE_ID+"="+currentGSP.get(1)
                        +" AND "+Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+
                        Database.Variable_Phase_Levels.LEVEL_NUMBER+"<="+currentGSP.get(3)
                        +" AND variable_type_relations.variable_type_id=3";

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_PHNC_LTRS, projection, selection, null, null);
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
            case MotoliConstants.CURRENT_PHNC_LTRS_WRDS:{
                mAllPhonicWords=new ArrayList<ArrayList<String>>();
                int currentPhonicWordNumber=0;

                for (data.moveToFirst(); !data.isAfterLast();data.moveToNext()){
                    mAllPhonicWords.add(new ArrayList<String>());
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_id")));
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_text")));
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_audio")));
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_image")));
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_first_id")));
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_last_id")));
                    currentPhonicWordNumber++;
                }
                getLoaderManager().restartLoader(MotoliConstants.CURRENT_PHNC_LTRS, null, this);
                break;
            }
            case MotoliConstants.CURRENT_PHNC_LTRS:{
                /*
                mCurrentPhonics=new ArrayList<ArrayList<String>>();
                int currentPhonicNumber=0;
                for(data.moveToFirst(); !data.isAfterLast(); data.moveToNext()){
                    mCurrentPhonics.add(new ArrayList<String>());
                    mCurrentPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("phonic_id")));
                    mCurrentPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("phonic_text")));
                    mCurrentPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("phonic_audio")));
                    mCurrentPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("number_correct")));
                    mCurrentPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("number_incorrect")));
                    currentPhonicNumber++;
                }
                shuffleGameData();
                displayScreen();
                */
                processData(data);
                break;
            }
        }//end switch
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void processData(Cursor mCursor){
        int mNumberOfPhonicVariables=(mCursor.getCount()*2>MotoliConstants.NUMBER_PHONIC_VARIABLES) ?
                MotoliConstants.NUMBER_PHONIC_VARIABLES : mCursor.getCount()*2;

        mCurrentPhonics=new ArrayList<ArrayList<String>>();
        int mWordNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentPhonics.add(new ArrayList<String>());
            mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_id"))); //0
            mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_text"))); //1
            mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_audio"))); //2
            mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5


            if(mCurrentPhonics.get(mWordNumber).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT-3))){
                /* This is because is first attempt the letter should appear 3 times to move to next round faster */
                mWordNumber++;
                mCurrentPhonics.add(new ArrayList<String>());
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_id"))); //0
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_text"))); //1
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_audio"))); //2
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5


                mWordNumber++;
                mCurrentPhonics.add(new ArrayList<String>());
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_id"))); //0
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_text"))); //1
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_audio"))); //2
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            }else if(mCurrentPhonics.get(mWordNumber).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT-2))){
                mWordNumber++;
                mCurrentPhonics.add(new ArrayList<String>());
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_id"))); //0
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_text"))); //1
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_audio"))); //2
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mCurrentPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            }
            mWordNumber++;
            if (mWordNumber >=mNumberOfPhonicVariables) {
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else{
                mCursor.moveToNext();
            }
        }


        Collections.shuffle(mCurrentPhonics);

        ArrayList<ArrayList<String>> mTempCurrentWords=new ArrayList<ArrayList<String>>();

        String mPreviousWordId="0";
        for(int i=0; i<mNumberOfPhonicVariables; i++){
            if(!mCurrentPhonics.get(i).get(0).equals(mPreviousWordId)){
                mTempCurrentWords.add(new ArrayList<String>(mCurrentPhonics.get(i)));
            }else{
                if((i+1)<mNumberOfPhonicVariables){
                    mTempCurrentWords.add(new ArrayList<String>(mCurrentPhonics.get(i+1)));
                    mTempCurrentWords.add(new ArrayList<String>(mCurrentPhonics.get(i)));
                    i++;
                }else{
                    mTempCurrentWords.add(new ArrayList<String>(mTempCurrentWords.get(0)));
                    mTempCurrentWords.set(0,mCurrentPhonics.get(i));
                }
            }
            mPreviousWordId=mCurrentPhonics.get(i).get(0);
        }

        mCurrentPhonics=new ArrayList<ArrayList<String>>(mTempCurrentWords);

        mAllPhonics=new ArrayList<ArrayList<String>>();
        mWordNumber=0;
        mCursor.moveToFirst();
        int mNumberCorrectInARow=MotoliConstants.INA_ROW_CORRECT;
        while (!mCursor.isAfterLast()) {
            mAllPhonics.add(new ArrayList<String>());
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_id"))); //0
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_text"))); //1
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_audio"))); //2
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(mAllPhonics.get(mWordNumber).get(5)))
                    ? Integer.parseInt(mAllPhonics.get(mWordNumber).get(5)):mNumberCorrectInARow;
            mWordNumber++;
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
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void shuffleGameData(){
        String word_id;
        allActivityText.clear();

        for(int currentCount=0; currentCount<mCurrentPhonics.size();currentCount++){
            allActivityText.add(new ArrayList<ArrayList<String>>());
            allActivityText.get(currentCount).add(new ArrayList<String>());
            allActivityText.get(currentCount).get(0).add("1"); //0

            word_id=mCurrentPhonics.get(currentCount).get(0);

            allActivityText.get(currentCount).get(0).add(mCurrentPhonics.get(currentCount).get(0)); //phonic_id 1
            allActivityText.get(currentCount).get(0).add(mCurrentPhonics.get(currentCount).get(1)); //phonic_text 2
            allActivityText.get(currentCount).get(0).add(mCurrentPhonics.get(currentCount).get(2)); //phonic_audio 3
            allActivityText.get(currentCount).get(0).add(mCurrentPhonics.get(currentCount).get(3)); //number correct 4
            allActivityText.get(currentCount).get(0).add(mCurrentPhonics.get(currentCount).get(4)); //number incorrect 5
            allActivityText.get(currentCount).get(0).add("0"); //guess yet 6

            Collections.shuffle(mAllPhonics);

            int distCurrentCount=1;
            for(int distCount=0;distCount<mAllPhonics.size(); distCount++){

                if(!mAllPhonics.get(distCount).get(0).equals(word_id)){
                    for(ArrayList mTemp : allActivityText.get(currentCount) ){
                        if(!mAllPhonics.get(distCount).get(0).equals(mTemp.get(1))){
                            allActivityText.get(currentCount).add(new ArrayList<String>());
                            allActivityText.get(currentCount).get(distCurrentCount).add("0");

                            allActivityText.get(currentCount).get(distCurrentCount).add(mAllPhonics.get(distCount).get(0)); //phonic_id
                            allActivityText.get(currentCount).get(distCurrentCount).add(mAllPhonics.get(distCount).get(1)); //phonic_text
                            allActivityText.get(currentCount).get(distCurrentCount).add(mAllPhonics.get(distCount).get(2)); //phonic_audio
                            allActivityText.get(currentCount).get(distCurrentCount).add(mAllPhonics.get(distCount).get(3)); //number correct
                            allActivityText.get(currentCount).get(distCurrentCount).add(mAllPhonics.get(distCount).get(4)); //number incorrect
                            allActivityText.get(currentCount).get(distCurrentCount).add("0"); //guessed yet

                            distCurrentCount++;
                            break;
                        }//  end if(!sortedWords.get(distCount).get(0).equals(mTemp.get(1))){
                    }//end for(ArrayList mTemp : allActivityText.get(currentCount) ){
                    if(distCurrentCount>=4){
                        break;
                    }
                }//end if(!mSortedPhonics.get(distCount).get(0).equals(word_id)){
            }//end for(int distCount=0;distCount<mSortedPhonics.size(); distCount++){


        }//end for(int currentCount=0; currentCount<mCurrentPhonics.size();currentCount++){


        for(int i=0; i<allActivityText.size(); i++){
            Collections.shuffle(allActivityText.get(i));
        }
        //Collections.shuffle(allActivityText);

    }//end private void shuffleGameData(){


    private void setUpListeners(){
        findViewById(R.id.Activity_MicDude).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!matchingLtrWrdAudio.equals(""))
                    playGeneralAudio(matchingLtrWrdAudio);
            }
        });


        findViewById(R.id.btnInfo).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                long audioDuration=playInstructionAudio();
                audioHandler.postDelayed(playLtrAudio, audioDuration+20);

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

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

}
