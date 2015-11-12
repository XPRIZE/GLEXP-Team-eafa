package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 9/28/2015.
 */
public class ActivityRS01 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor> {

    private int mIncorrectInRound=0;
    private int mCurrentSyllableLocation=0;

    private ArrayList<ArrayList<String>> mAllSyllabeWords;
    private ArrayList<ArrayList<String>> mCurrentSyllables;
    private ArrayList<ArrayList<String>> mAllSyllables;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_rs01);
        appData.addToClassOrder(16);

        allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

        beingValidated=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();
        
        getLoaderManager().initLoader(MotoliConstants.CURRENT_SYLLABLE, null, this);


    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){

        mIncorrectInRound=0;
        
        mCurrentSyllables = new ArrayList<ArrayList<String>>(allActivityText.get(roundNumber));
        Collections.shuffle(mCurrentSyllables);

        ((TextView) findViewById(R.id.syllableText1)).setText(mCurrentSyllables.get(0).get(2));
        findViewById(R.id.progressCircle1).setVisibility(ProgressBar.INVISIBLE);
        if(mCurrentSyllables.get(0).get(0).equals("1")){
            correctLocation=0;
            matchingLtrWrdAudio=mCurrentSyllables.get(0).get(3);
        }


        ((TextView) findViewById(R.id.syllableText2)).setText(mCurrentSyllables.get(1).get(2));
        findViewById(R.id.progressCircle2).setVisibility(ProgressBar.INVISIBLE);
        if(mCurrentSyllables.get(1).get(0).equals("1")){
            correctLocation=1;
            matchingLtrWrdAudio=mCurrentSyllables.get(1).get(3);
        }

        ((TextView) findViewById(R.id.syllableText3)).setText(mCurrentSyllables.get(2).get(2));
        findViewById(R.id.progressCircle3).setVisibility(ProgressBar.INVISIBLE);
        if(mCurrentSyllables.get(2).get(0).equals("1")){
            correctLocation=2;
            matchingLtrWrdAudio=mCurrentSyllables.get(2).get(3);
        }

        ((TextView) findViewById(R.id.syllableText4)).setText(mCurrentSyllables.get(3).get(2));
        findViewById(R.id.progressCircle4).setVisibility(ProgressBar.INVISIBLE);
        if(mCurrentSyllables.get(3).get(0).equals("1")){
            correctLocation=3;
            matchingLtrWrdAudio=mCurrentSyllables.get(3).get(3);
        }
        long audioDuration=0;
        if(roundNumber==0)
            audioDuration=playInstructionAudio();

        audioHandler.postDelayed(playLtrAudio, audioDuration+20);

    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    private void clearActivity(){
        findViewById(R.id.progressCircle1).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.progressCircle2).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.progressCircle3).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.progressCircle4).setVisibility(ProgressBar.VISIBLE);

        ((TextView) findViewById(R.id.syllableText1)).setText("");
        ((TextView) findViewById(R.id.syllableText2)).setText("");
        ((TextView) findViewById(R.id.syllableText3)).setText("");
        ((TextView) findViewById(R.id.syllableText4)).setText("");

        ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText2)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText3)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText4)).setTextColor(getResources().getColor(R.color.normalBlack));


        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);

        clearFrames();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void validate(){
        beingValidated=true;
        if(correctChoice) {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
        }else{
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
        }
        processFontColorAndPoints();
        processGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    private void processFontColorAndPoints(){
        String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+currentWordID
                +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

        if(correctChoice){
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    currentGSP.get(3),
                    currentWordID,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            switch(mCurrentSyllableLocation){
                default:
                case 0:
                    mCurrentSyllables.get(0).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
                case 1:
                    mCurrentSyllables.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText2)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
                case 2:
                    mCurrentSyllables.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText3)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
                case 3:
                    mCurrentSyllables.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText4)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
            }


        }else{
            mIncorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    currentGSP.get(3),
                    currentWordID,
                    String.valueOf(mIncorrectInRound)};

            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(correctLocation){
                    default:
                    case 0:
                        mCurrentSyllables.get(1).add(6, "1");
                        mCurrentSyllables.get(2).add(6, "1");
                        mCurrentSyllables.get(3).add(6, "1");
                        break;
                    case 1:
                        mCurrentSyllables.get(0).add(6, "1");
                        mCurrentSyllables.get(2).add(6, "1");
                        mCurrentSyllables.get(3).add(6, "1");
                        break;
                    case 2:
                        mCurrentSyllables.get(1).add(6, "1");
                        mCurrentSyllables.get(0).add(6, "1");
                        mCurrentSyllables.get(3).add(6, "1");
                        break;
                    case 3:
                        mCurrentSyllables.get(1).add(6, "1");
                        mCurrentSyllables.get(2).add(6, "1");
                        mCurrentSyllables.get(0).add(6, "1");
                        break;
                }
            }
            switch(mCurrentSyllableLocation){
                default:
                case 0:
                    mCurrentSyllables.get(0).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
                case 1:
                    mCurrentSyllables.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText2)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
                case 2:
                    mCurrentSyllables.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText3)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
                case 3:
                    mCurrentSyllables.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText4)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
            }


        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    private void processChoiceView(){
        if(correctChoice){
            switch(mCurrentSyllableLocation){
                default:
                case 0:
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 1:
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 2:
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 3:
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    break;
            }
        }else{
            if(mIncorrectInRound>=2){
                switch(correctLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 2:
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        break;
                }
            }else{
                switch(mCurrentSyllableLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        break;
                    case 2:
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

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
                    processChoiceView();
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

    
    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.syllableFrame1)).setImageResource(R.drawable.frm_rs01_off);
        ((ImageView)findViewById(R.id.syllableFrame2)).setImageResource(R.drawable.frm_rs01_off);
        ((ImageView)findViewById(R.id.syllableFrame3)).setImageResource(R.drawable.frm_rs01_off);
        ((ImageView)findViewById(R.id.syllableFrame4)).setImageResource(R.drawable.frm_rs01_off);


    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    protected void setupFrameListens(){
        findViewById(R.id.syllableText1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentSyllables.get(0).get(6).equals("0") && !beingValidated){
                    mCurrentSyllableLocation=0;
                    currentLtrWrdAudio=mCurrentSyllables.get(0).get(3);
                    currentWordID=mCurrentSyllables.get(0).get(1);
                    correctChoice=(mCurrentSyllables.get(0).get(0).equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.syllableText2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentSyllables.get(1).get(6).equals("0") && !beingValidated) {
                    mCurrentSyllableLocation = 1;
                    currentLtrWrdAudio = mCurrentSyllables.get(1).get(3);
                    currentWordID = mCurrentSyllables.get(1).get(1);
                    correctChoice = (mCurrentSyllables.get(1).get(0).equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.syllableText3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentSyllables.get(2).get(6).equals("0") && !beingValidated){
                    mCurrentSyllableLocation=2;
                    currentLtrWrdAudio=mCurrentSyllables.get(2).get(3);
                    currentWordID=mCurrentSyllables.get(2).get(1);
                    correctChoice=(mCurrentSyllables.get(2).get(0).equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.syllableText4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentSyllables.get(3).get(6).equals("0") && !beingValidated) {
                    mCurrentSyllableLocation = 3;
                    currentLtrWrdAudio = mCurrentSyllables.get(3).get(3);
                    currentWordID = mCurrentSyllables.get(3).get(1);
                    correctChoice = (mCurrentSyllables.get(3).get(0).equals("1"));
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
                ((ImageView)findViewById(R.id.syllableFrame1)).setImageResource(R.drawable.frm_rs01_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.syllableFrame2)).setImageResource(R.drawable.frm_rs01_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.syllableFrame3)).setImageResource(R.drawable.frm_rs01_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.syllableFrame4)).setImageResource(R.drawable.frm_rs01_on);
                break;
            }
        }
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////
    
    private long playInstructionAudio(){
        return playGeneralAudio("info_sd01.mp3");
    }
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
    
    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.CURRENT_SYLLABLE_WRDS:{
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_SYLLABLE_WORDS,null,null,null,null);
                break;
            }
            case MotoliConstants.CURRENT_SYLLABLE:{

                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID()};

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_SYLLABLES, projection, null, null, null);
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
            case MotoliConstants.CURRENT_SYLLABLE_WRDS:{
                mAllSyllabeWords=new ArrayList<ArrayList<String>>();
                int currentPhonicWordNumber=0;

                for (data.moveToFirst(); !data.isAfterLast();data.moveToNext()){
                    mAllSyllabeWords.add(new ArrayList<String>());
                    mAllSyllabeWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("syllabe_word_id")));
                    mAllSyllabeWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("syllabe_word_text")));
                    mAllSyllabeWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("syllabe_word_audio")));
                    mAllSyllabeWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("syllabe_word_image")));
                    mAllSyllabeWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("syllabe_word_first_id")));
                    mAllSyllabeWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("syllabe_word_last_id")));
                    currentPhonicWordNumber++;
                }
                getLoaderManager().restartLoader(MotoliConstants.CURRENT_SYLLABLE, null, this);
                break;
            }
            case MotoliConstants.CURRENT_SYLLABLE:{

                processData(data);
                break;
            }
        }//end switch
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void processData(Cursor mCursor){
        int mNumberOfPhonicVariables=(mCursor.getCount()*2>MotoliConstants.NUMBER_PHONIC_VARIABLES) ?
                MotoliConstants.NUMBER_PHONIC_VARIABLES : mCursor.getCount()*2;

        mCurrentSyllables=new ArrayList<ArrayList<String>>();
        int mWordNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentSyllables.add(new ArrayList<String>());
            mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_id"))); //0
            mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_text"))); //1
            mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_audio"))); //2
            mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5


            /*
            if(mCurrentSyllables.get(mWordNumber).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT-3))){
                mWordNumber++;
                mCurrentSyllables.add(new ArrayList<String>());
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_id"))); //0
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_text"))); //1
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_audio"))); //2
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5


                mWordNumber++;
                mCurrentSyllables.add(new ArrayList<String>());
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_id"))); //0
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_text"))); //1
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_audio"))); //2
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            }else if(mCurrentSyllables.get(mWordNumber).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT-2))){
                mWordNumber++;
                mCurrentSyllables.add(new ArrayList<String>());
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_id"))); //0
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_text"))); //1
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_audio"))); //2
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mCurrentSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            }
            */
            mWordNumber++;
            if (mWordNumber >=mNumberOfPhonicVariables) {
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else{
                mCursor.moveToNext();
            }
        }


        Collections.shuffle(mCurrentSyllables);

        ArrayList<ArrayList<String>> mTempCurrentWords=new ArrayList<ArrayList<String>>();

        String mPreviousWordId="0";
        for(int i=0; i<mNumberOfPhonicVariables; i++){
            if(!mCurrentSyllables.get(i).get(0).equals(mPreviousWordId)){
                mTempCurrentWords.add(new ArrayList<String>(mCurrentSyllables.get(i)));
            }else{
                if((i+1)<mNumberOfPhonicVariables){
                    mTempCurrentWords.add(new ArrayList<String>(mCurrentSyllables.get(i+1)));
                    mTempCurrentWords.add(new ArrayList<String>(mCurrentSyllables.get(i)));
                    i++;
                }else{
                    mTempCurrentWords.add(new ArrayList<String>(mTempCurrentWords.get(0)));
                    mTempCurrentWords.set(0,mCurrentSyllables.get(i));
                }
            }
            mPreviousWordId=mCurrentSyllables.get(i).get(0);
        }

        mCurrentSyllables=new ArrayList<ArrayList<String>>(mTempCurrentWords);

        mAllSyllables=new ArrayList<ArrayList<String>>();
        mWordNumber=0;
        mCursor.moveToFirst();
        int mNumberCorrectInARow=MotoliConstants.INA_ROW_CORRECT;
        while (!mCursor.isAfterLast()) {
            mAllSyllables.add(new ArrayList<String>());
            mAllSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_id"))); //0
            mAllSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_text"))); //1
            mAllSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_audio"))); //2
            mAllSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mAllSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mAllSyllables.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(mAllSyllables.get(mWordNumber).get(5)))
                    ? Integer.parseInt(mAllSyllables.get(mWordNumber).get(5)):mNumberCorrectInARow;
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

    private void setUpListeners(){
        findViewById(R.id.btnMicDude).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!matchingLtrWrdAudio.equals(""))
                    playGeneralAudio(matchingLtrWrdAudio);
            }
        });


        findViewById(R.id.btnInfo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                long audioDuration=playInstructionAudio();
                audioHandler.postDelayed(playLtrAudio, audioDuration+20);
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
            }
        });


        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(validateAvailable && !beingValidated){
                    playClickSound();
                    validate();
                }
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void shuffleGameData(){
        String word_id;
        allActivityText.clear();

        for(int currentCount=0; currentCount<mCurrentSyllables.size();currentCount++){
            allActivityText.add(new ArrayList<ArrayList<String>>());
            allActivityText.get(currentCount).add(new ArrayList<String>());
            allActivityText.get(currentCount).get(0).add("1"); //0

            word_id=mCurrentSyllables.get(currentCount).get(0);

            allActivityText.get(currentCount).get(0).add(mCurrentSyllables.get(currentCount).get(0)); //syllables_id 1
            allActivityText.get(currentCount).get(0).add(mCurrentSyllables.get(currentCount).get(1)); //syllables_text 2
            allActivityText.get(currentCount).get(0).add(mCurrentSyllables.get(currentCount).get(2)); //syllables_audio 3
            allActivityText.get(currentCount).get(0).add(mCurrentSyllables.get(currentCount).get(3)); //number correct 4
            allActivityText.get(currentCount).get(0).add(mCurrentSyllables.get(currentCount).get(4)); //number incorrect 5
            allActivityText.get(currentCount).get(0).add("0"); //guess yet 6

            Collections.shuffle(mAllSyllables);

            int distCurrentCount=1;
            for(int distCount=0;distCount<mAllSyllables.size(); distCount++){

                if(!mAllSyllables.get(distCount).get(0).equals(word_id)){
                    for(ArrayList mTemp : allActivityText.get(currentCount) ){
                        if(!mAllSyllables.get(distCount).get(0).equals(mTemp.get(1))){
                            allActivityText.get(currentCount).add(new ArrayList<String>());
                            allActivityText.get(currentCount).get(distCurrentCount).add("0");

                            allActivityText.get(currentCount).get(distCurrentCount).add(mAllSyllables.get(distCount).get(0)); //syllables_id
                            allActivityText.get(currentCount).get(distCurrentCount).add(mAllSyllables.get(distCount).get(1)); //syllables_text
                            allActivityText.get(currentCount).get(distCurrentCount).add(mAllSyllables.get(distCount).get(2)); //syllables_audio
                            allActivityText.get(currentCount).get(distCurrentCount).add(mAllSyllables.get(distCount).get(3)); //number correct
                            allActivityText.get(currentCount).get(distCurrentCount).add(mAllSyllables.get(distCount).get(4)); //number incorrect
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


        }//end for(int currentCount=0; currentCount<mCurrentSyllables.size();currentCount++){


        for(int i=0; i<allActivityText.size(); i++){
            Collections.shuffle(allActivityText.get(i));
        }
        //Collections.shuffle(allActivityText);

    }//end private void shuffleGameData(){
}