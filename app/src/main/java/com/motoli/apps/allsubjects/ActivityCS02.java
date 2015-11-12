package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
public class ActivityCS02 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{


    private String mCurrentWordAudio;

    private int mIncorrectInRound=0;
    private int mCorrectSyllables;
    private int mNumberOfSyllables;

    private ArrayList<String> mCurrentText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_cs02);
        appData.addToClassOrder(13);


        beingValidated=true;
        setUpListeners();
        clearFrames();
        replaceHands();
        setupFrameListens();

        ((ImageView) findViewById(R.id.syllableImage)).setImageResource(R.drawable.blank_image);

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
            mCurrentWords.get(currentWordNumber).add("0"); //8-1
            mCurrentWords.get(currentWordNumber).add("0"); //9-2
            mCurrentWords.get(currentWordNumber).add("0"); //10-3
            mCurrentWords.get(currentWordNumber).add("0"); //11-4
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

        mIncorrectInRound=0;
        beingValidated=true;
        mCurrentText = new ArrayList<String>(mCurrentWords.get(roundNumber));
        mCorrectSyllables=Integer.parseInt(mCurrentWords.get(roundNumber).get(7));
        mCurrentWordAudio=mCurrentWords.get(roundNumber).get(2);
        currentWordID=mCurrentWords.get(roundNumber).get(0);

        try{
            String imageName=mCurrentWords.get(roundNumber).get(5).replace(".jpg", "").replace(".png", "").trim();
            int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
            ((ImageView) findViewById(R.id.syllableImage)).setImageResource(resID);
        }catch (Exception e) {
            ((ImageView) findViewById(R.id.syllableImage)).setImageResource(R.drawable.blank_image);
            Log.e("MyTag", "Failure to get drawable id.", e);
        }


        long audioDuration=0;
        if(roundNumber==0)
            audioDuration=playInstructionAudio();

        audioHandler.postDelayed(playWordAudio, audioDuration+20);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playWordAudio = new Runnable(){

        @Override
        public void run(){
            beingValidated=false;
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
        if(correctChoice) {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
            switch (mNumberOfSyllables){
                default:
                case 1:{
                    findViewById(R.id.syllableHand1).setBackgroundResource(R.drawable.rounded_border_correct);
                    break;
                }
                case 2:{
                    findViewById(R.id.syllableHand2).setBackgroundResource(R.drawable.rounded_border_correct);
                    break;
                }
                case 3:{
                    findViewById(R.id.syllableHand3).setBackgroundResource(R.drawable.rounded_border_correct);
                    break;
                }
                case 4:{
                    findViewById(R.id.syllableHand4).setBackgroundResource(R.drawable.rounded_border_correct);
                    break;
                }
            }
        }else{
            mIncorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
            if (mIncorrectInRound >= 2) {
                switch (mCorrectSyllables) {
                    default:
                    case 1:
                        mCurrentWords.get(roundNumber).set(9, "1");
                        mCurrentWords.get(roundNumber).set(10, "1");
                        mCurrentWords.get(roundNumber).set(11, "1");
                        findViewById(R.id.syllableHand1).setBackgroundResource(R.drawable.rounded_border_incorrect);
                        break;
                    case 2:
                        mCurrentWords.get(roundNumber).set(8, "1");
                        mCurrentWords.get(roundNumber).set(10, "1");
                        mCurrentWords.get(roundNumber).set(11, "1");
                        findViewById(R.id.syllableHand2).setBackgroundResource(R.drawable.rounded_border_incorrect);
                        break;
                    case 3:
                        mCurrentWords.get(roundNumber).set(9, "1");
                        mCurrentWords.get(roundNumber).set(8, "1");
                        mCurrentWords.get(roundNumber).set(11, "1");
                        findViewById(R.id.syllableHand3).setBackgroundResource(R.drawable.rounded_border_incorrect);
                        break;
                    case 4:
                        mCurrentWords.get(roundNumber).set(9, "1");
                        mCurrentWords.get(roundNumber).set(10, "1");
                        mCurrentWords.get(roundNumber).set(8, "1");
                        findViewById(R.id.syllableHand4).setBackgroundResource(R.drawable.rounded_border_incorrect);
                        break;
                }
            }
            switch (mNumberOfSyllables) {
                default:
                case 1:
                    mCurrentWords.get(roundNumber).set(8, "1");
                    findViewById(R.id.syllableHand1).setBackgroundResource(R.drawable.rounded_border_incorrect);
                    break;
                case 2:
                    mCurrentWords.get(roundNumber).set(9, "1");
                    findViewById(R.id.syllableHand2).setBackgroundResource(R.drawable.rounded_border_incorrect);
                    break;
                case 3:
                    mCurrentWords.get(roundNumber).set(10, "1");
                    findViewById(R.id.syllableHand3).setBackgroundResource(R.drawable.rounded_border_incorrect);
                    break;
                case 4:
                    mCurrentWords.get(roundNumber).set(11, "1");
                    findViewById(R.id.syllableHand4).setBackgroundResource(R.drawable.rounded_border_incorrect);
                    break;
            }


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
                    String.valueOf(mIncorrectInRound)};

            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
        }else {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    currentGSP.get(3),
                    currentWordID,
                    String.valueOf(mIncorrectInRound)};

            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processHandVisiblity(){
        if(correctChoice){
            switch(mCorrectSyllables) {
                default:
                case 1:
                    findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                    break;
                case 2:
                    findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                    break;
                case 3:
                    findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                    break;
                case 4:
                    findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                    break;
            }
        }else {
            if (mIncorrectInRound >= 2) {
                switch (mCorrectSyllables) {
                    default:
                    case 1:
                        findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 2:
                        findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 3:
                        findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 4:
                        findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                        break;
                }
            } else {
                switch (mNumberOfSyllables) {
                    default:
                    case 1:
                        findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 2:
                        findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 3:
                        findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 4:
                        findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                        break;
                }
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens() {
        findViewById(R.id.syllableHand1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentWords.get(roundNumber).get(8).equals("1") && !beingValidated) {
                    mNumberOfSyllables = 1;
                    correctChoice = (mCurrentWords.get(roundNumber).get(7).equals("1"));
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.syllableHand2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mCurrentWords.get(roundNumber).get(9).equals("1") && !beingValidated) {
                    mNumberOfSyllables = 2;
                    correctChoice = (mCurrentWords.get(roundNumber).get(7).equals("2"));
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.syllableHand3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mCurrentWords.get(roundNumber).get(10).equals("1") && !beingValidated) {
                    mNumberOfSyllables = 3;
                    correctChoice = (mCurrentWords.get(roundNumber).get(7).equals("3"));
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.syllableHand4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mCurrentWords.get(roundNumber).get(11).equals("1") && !beingValidated) {
                    mNumberOfSyllables = 4;
                    correctChoice = (mCurrentWords.get(roundNumber).get(7).equals("3"));
                    setUpFrame();
                }
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        ((ImageView) findViewById(R.id.syllableFrame1)).setImageResource(R.drawable.frm_cs02_off);
        ((ImageView) findViewById(R.id.syllableFrame2)).setImageResource(R.drawable.frm_cs02_off);
        ((ImageView) findViewById(R.id.syllableFrame3)).setImageResource(R.drawable.frm_cs02_off);
        ((ImageView) findViewById(R.id.syllableFrame4)).setImageResource(R.drawable.frm_cs02_off);

        findViewById(R.id.syllableHand1).setBackgroundResource(R.drawable.rounded_border_unchosen);
        findViewById(R.id.syllableHand2).setBackgroundResource(R.drawable.rounded_border_unchosen);
        findViewById(R.id.syllableHand3).setBackgroundResource(R.drawable.rounded_border_unchosen);
        findViewById(R.id.syllableHand4).setBackgroundResource(R.drawable.rounded_border_unchosen);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void replaceHands(){
        findViewById(R.id.syllableHand1).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.syllableHand2).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.syllableHand3).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.syllableHand4).setVisibility(ImageView.VISIBLE);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame() {
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on);
        switch (mNumberOfSyllables){
            default:
            case 1:{
                ((ImageView) findViewById(R.id.syllableFrame1)).setImageResource(R.drawable.frm_cs02_on);
                break;
            }
            case 2:{
                ((ImageView) findViewById(R.id.syllableFrame2)).setImageResource(R.drawable.frm_cs02_on);
                break;
            }
            case 3:{
                ((ImageView) findViewById(R.id.syllableFrame3)).setImageResource(R.drawable.frm_cs02_on);
                break;
            }
            case 4:{
                ((ImageView) findViewById(R.id.syllableFrame4)).setImageResource(R.drawable.frm_cs02_on);
                break;
            }
        }
        validateAvailable=true;
       
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpListeners(){
        findViewById(R.id.syllableImage).setOnClickListener(new View.OnClickListener() {
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
                    processHandVisiblity();
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

                    break;
                }
                case 3:{
                    replaceHands();
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
