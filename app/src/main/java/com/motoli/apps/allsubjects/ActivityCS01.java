package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/31/2015.
 */
public class ActivityCS01 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{


    private String mCurrentWordAudio;

    private int incorrectInRound=0;
    private int mNumberOfSyllables;

    private ArrayList<String> mChosenSyllables;
    private ArrayList<String> mCurrentText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_cs01);
        appData.addToClassOrder(13);


        beingValidated=true;
        setUpListeners();
        clearFrames();
        setupFrameListens();

        mChosenSyllables=new ArrayList<String>();
        mChosenSyllables.add("0");
        mChosenSyllables.add("0");
        mChosenSyllables.add("0");


        getLoaderManager().initLoader(MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);

    }


    /////////////////////////////////////////////////////////////////////////////////////////////


    private void processData(Cursor mCursor){
        mCurrentWords=new ArrayList<ArrayList<String>>();
        int currentWordNumber=0;
        int mNumberOfWords=0;

        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentWords.add(new ArrayList<String>());
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_of_syllables"))); //7

            mNumberOfWords++;

            currentWordNumber++;
            if (currentWordNumber >= MotoliConstants.NUMBER_VARIABLES) {
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else{
                mCursor.moveToNext();
            }
        }


        Collections.shuffle(mCurrentWords);

        ArrayList<ArrayList<String>> mTempCurrentWords=new ArrayList<ArrayList<String>>();

        String mPreviousWordId="0";
        for(int i=0; i<MotoliConstants.NUMBER_VARIABLES; i++){
            if(!mCurrentWords.get(i).get(0).equals(mPreviousWordId)){
                mTempCurrentWords.add(new ArrayList<String>(mCurrentWords.get(i)));
            }else{
                if((i+1)<MotoliConstants.NUMBER_VARIABLES){
                    mTempCurrentWords.add(new ArrayList<String>(mCurrentWords.get(i+1)));
                    mTempCurrentWords.add(new ArrayList<String>(mCurrentWords.get(i)));
                    i++;
                }else{
                    mTempCurrentWords.add(new ArrayList<String>(mTempCurrentWords.get(0)));
                    mTempCurrentWords.set(0,mCurrentWords.get(i));
                }
            }
            mPreviousWordId=mCurrentWords.get(i).get(0);
        }

        mCurrentWords=new ArrayList<ArrayList<String>>(mTempCurrentWords);

        displayScreen();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){
        incorrectInRound=0;
        beingValidated=false;
        mCurrentText = new ArrayList<String>(mCurrentWords.get(roundNumber));
        for(int i=0; i<mChosenSyllables.size(); i++){
            mChosenSyllables.set(i,"0");
        }
        mCurrentWordAudio=mCurrentWords.get(roundNumber).get(2);
        currentWordID=mCurrentWords.get(roundNumber).get(0);
        long audioDuration=0;
        if(roundNumber==0)
            audioDuration=playInstructionAudio();

        audioHandler.postDelayed(playWordAudio, audioDuration+20);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playWordAudio = new Runnable(){

        @Override
        public void run(){
            audioHandler.removeCallbacks(playWordAudio);
            if(!mCurrentWordAudio.equals(""))
                playGeneralAudio(mCurrentWordAudio);
        }
    };
    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    private long playInstructionAudio(){
        return playGeneralAudio("info_cs01.mp3");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void validate(){
        beingValidated=true;
        mNumberOfSyllables=0;
        for(int i=0;i<mChosenSyllables.size(); i++){
            if(mChosenSyllables.get(i).equals("1")){
                mNumberOfSyllables++;
            }else{
                break;
            }
        }
        correctChoice = (mCurrentWords.get(roundNumber).get(7).equals(String.valueOf(mNumberOfSyllables)));

        if(correctChoice) {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
            for(int i=0;i<mChosenSyllables.size(); i++) {
                switch (i) {
                    default:
                    case 0: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables1)).setImageResource(R.drawable.rounded_cs01_correct);
                        }
                        break;
                    }
                    case 1: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables2)).setImageResource(R.drawable.rounded_cs01_correct);
                        }
                        break;
                    }
                    case 2: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables3)).setImageResource(R.drawable.rounded_cs01_correct);
                        }
                        break;
                    }
                }//end   switch (i) {
            }//end for(int i=0;i<mChosenSyllables.size(); i++) {
        }else{
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
            for(int i=0;i<mChosenSyllables.size(); i++) {
                switch (i) {
                    default:
                    case 0: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables1)).setImageResource(R.drawable.rounded_cs01_wrong);
                        }
                        break;
                    }
                    case 1: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables2)).setImageResource(R.drawable.rounded_cs01_wrong);
                        }
                        break;
                    }
                    case 2: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables3)).setImageResource(R.drawable.rounded_cs01_wrong);
                        }
                        break;
                    }
                }//end   switch (i) {
            }//end for(int i=0;i<mChosenSyllables.size(); i++) {
        }

        processPoints();
        processGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processPoints(){
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
                    String.valueOf(incorrectInRound)};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
        }else{
            incorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    currentGSP.get(3),
                    currentWordID,
                    String.valueOf(incorrectInRound)};

            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
         }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens() {
        findViewById(R.id.syllables1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!beingValidated) {
                    if(mChosenSyllables.get(0).equals("0")) {
                        mChosenSyllables.set(0, "1");
                    }else{
                        mChosenSyllables.set(0, "0");
                    }
                    //mNumberOfSyllables = 1;
                    //correctChoice = (mCurrentWords.get(roundNumber).get(7).equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.syllables2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!beingValidated) {
                    if(mChosenSyllables.get(1).equals("0")) {
                        mChosenSyllables.set(1, "1");
                    }else{
                        mChosenSyllables.set(1, "0");
                    }
                    //mNumberOfSyllables = 2;
                    //correctChoice = (mCurrentWords.get(roundNumber).get(7).equals("2"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.syllables3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!beingValidated) {
                    if(mChosenSyllables.get(2).equals("0")) {
                        mChosenSyllables.set(2, "1");
                    }else{
                        mChosenSyllables.set(2, "0");
                    }
                    //mNumberOfSyllables = 3;
                    //correctChoice = (mCurrentWords.get(roundNumber).get(7).equals("3"));
                    setUpFrame(2);
                }
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        ((ImageView)findViewById(R.id.syllables1)).setImageResource(R.drawable.rounded_cs01_unchosen);
        ((ImageView)findViewById(R.id.syllables2)).setImageResource(R.drawable.rounded_cs01_unchosen);
        ((ImageView)findViewById(R.id.syllables3)).setImageResource(R.drawable.rounded_cs01_unchosen);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber) {
        //clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on);
        validateAvailable=true;
        switch(frameNumber){
            default:
            case 0:{
                if(mChosenSyllables.get(0).equals("1")) {
                    ((ImageView)findViewById(R.id.syllables1)).setImageResource(R.drawable.rounded_cs01_chosen);
                }else{
                    ((ImageView)findViewById(R.id.syllables1)).setImageResource(R.drawable.rounded_cs01_unchosen);
                }
                break;
            }
            case 1:{
                if(mChosenSyllables.get(1).equals("1")) {
                    ((ImageView)findViewById(R.id.syllables2)).setImageResource(R.drawable.rounded_cs01_chosen);
                }else{
                    ((ImageView)findViewById(R.id.syllables2)).setImageResource(R.drawable.rounded_cs01_unchosen);
                }
                break;
            }
            case 2:{
                if(mChosenSyllables.get(2).equals("1")) {
                    ((ImageView)findViewById(R.id.syllables3)).setImageResource(R.drawable.rounded_cs01_chosen);
                }else{
                    ((ImageView)findViewById(R.id.syllables3)).setImageResource(R.drawable.rounded_cs01_unchosen);
                }
                break;
            }
        }
    }

     ////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpListeners(){
        findViewById(R.id.btnMicMan0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentWordAudio.equals(""))
                    playGeneralAudio(mCurrentWordAudio);
            }
        });


        findViewById(R.id.btnInfo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                long audioDuration=playInstructionAudio();
                audioHandler.postDelayed(playWordAudio, audioDuration+20);
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
     /////////////////////////////////////////////////////////////////////////////////////////////
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
                     audioDuration=playGeneralAudio(mCurrentWordAudio);
                     processGuessPosition++;
                     audioHandler.postDelayed(processGuess, audioDuration+10);
                     break;
                 }
                 case 2:{
                     guessHandler.removeCallbacks(processGuess);
                     clearFrames();
                     if(correctChoice){
                         roundNumber++;
                         if(roundNumber!=(mCurrentWords.size())){
                             //beingValidated=false;
                             validateAvailable=false;
                             //clearActivity();
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
                     for(int i=0; i<mChosenSyllables.size(); i++){
                         mChosenSyllables.set(i,"0");
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
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS:{

                ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
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
                //NOTICE THIS IS ALWAYS DEFAULTING TO PHASE 1

                currentGSP.set(0, "4");
                currentGSP.set(1,"1");
                String selection=" words.number_of_syllables>=1 AND words.number_of_syllables<=3"+
                        " AND variable_phase_levels.group_id="+currentGSP.get(0)+
                        " AND variable_phase_levels.phase_id="+currentGSP.get(1)+
                        " AND ((variable_phase_levels.level_number="+currentGSP.get(3)+"-1 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-2 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-3"+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+" ) "+
                        " OR variable_phase_levels.level_number<="+currentGSP.get(3)+" -3)";
                String sort= "variable_phase_levels.group_id="+currentGSP.get(0)+
                        " AND variable_phase_levels.phase_id="+currentGSP.get(1)+
                        " AND ((variable_phase_levels.level_number="+currentGSP.get(3)+"-1 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-2 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-3"+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+" ) "+
                        " OR variable_phase_levels.level_number<="+currentGSP.get(3)+" -3)";
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_WORD_SYLLABLE_VALUES, projection, selection, null, sort);
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
            case MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS:{
                processData(data);
                break;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }
}
