package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/31/2015.
 */
public class ActivityCS02 extends ActivitiesMasterParent implements LoaderManager.LoaderCallbacks<Cursor>{


    private String mCurrentWordAudio;

    private int mIncorrectInRound=0;
    private int mCorrectSyllables;
    private int mNumberOfSyllables;

    private ArrayList<String> mCurrentText;

    private ArrayList<ArrayList<String>> mSyllableWords;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_cs02);
        appData.addToClassOrder(13);
        appData.setActivityType(3);
        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mInstructionAudio="info_cs02";

        mBeingValidated=true;
        setUpListeners();
        clearFrames();
        replaceHands();
        setupFrameListens();

        ((ImageView) findViewById(R.id.syllableImage)).setImageResource(R.drawable.blank_image);

        startActivityHandler.postDelayed(startActivity, 500);
    }//end public void onCreate(Bundle savedInstanceState) {

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////


    protected void processData(Cursor mCursor){
        mSyllableWords=new ArrayList<ArrayList<String>>();
        int currentWordNumber=0;
        int mNumberOfWords=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mSyllableWords.add(new ArrayList<String>());
            mSyllableWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
            mSyllableWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
            mSyllableWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
            mSyllableWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mSyllableWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mSyllableWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
            mSyllableWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
            if(mCursor.getString(mCursor.getColumnIndex("number_of_syllables")).equals("0")){
                mSyllableWords.get(currentWordNumber).add("1"); //7

            }else {
                mSyllableWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_of_syllables"))); //7
            }
            mSyllableWords.get(currentWordNumber).add("0"); //8-1
            mSyllableWords.get(currentWordNumber).add("0"); //9-2
            mSyllableWords.get(currentWordNumber).add("0"); //10-3
            mSyllableWords.get(currentWordNumber).add("0"); //11-4
            mNumberOfWords++;


            currentWordNumber++;
            if (currentWordNumber >= Constants.NUMBER_VARIABLES || mCursor.isLast()){
                break;
            }else{
                mCursor.moveToNext();
            }
        }


        Collections.shuffle(mSyllableWords);

        if(mSyllableWords.size()<Constants.NUMBER_VARIABLES ) {
            for (int i = 0; i < mSyllableWords.size(); i++) {
                if (!mSyllableWords.get(mSyllableWords.size() - 1).get(0).equals(mSyllableWords.get(i).get(0))) {
                    mSyllableWords.add(new ArrayList<String>(mSyllableWords.get(i)));
                    if (mSyllableWords.size() >= Constants.NUMBER_VARIABLES) {
                        break;
                    }
                }
            }
        }

        displayScreen();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){

        mIncorrectInRound=0;
        mBeingValidated=true;
        mCurrentText = new ArrayList<String>(mSyllableWords.get(roundNumber));
        mCorrectSyllables=Integer.parseInt(mSyllableWords.get(roundNumber).get(7));
        mCurrentWordAudio=mSyllableWords.get(roundNumber).get(2);
        mCurrentID=mSyllableWords.get(roundNumber).get(0);

        try{
            String imageName=mSyllableWords.get(roundNumber).get(5).replace(".jpg", "").replace(".png", "").trim();
            int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
            ((ImageView) findViewById(R.id.syllableImage)).setImageResource(resID);
        }catch (Exception e) {
            ((ImageView) findViewById(R.id.syllableImage)).setImageResource(R.drawable.blank_image);
            Log.e("MyTag", "Failure to get drawable id.", e);
        }


        long mAudioDuration=0;
        if(roundNumber==0)
            mAudioDuration=playInstructionAudio();

        mAudioHandler.postDelayed(playWordAudio, mAudioDuration+20);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playWordAudio = new Runnable(){

        @Override
        public void run(){
            mBeingValidated=false;
            mAudioHandler.removeCallbacks(playWordAudio);
            if(!mCurrentWordAudio.equals(""))
                playGeneralAudio(mCurrentWordAudio);
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration=super.playInstructionAudio();
        mAudioHandler.postDelayed(playWordAudio, mAudioDuration+20);
        return mAudioDuration;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_ok);
            switch (mNumberOfSyllables){
                default:
                case 1:{
                    //findViewById(R.id.syllableHand1).setBackgroundResource(R.drawable.rounded_border_correct);
                    break;
                }
                case 2:{
                    //findViewById(R.id.syllableHand2).setBackgroundResource(R.drawable.rounded_border_correct);
                    break;
                }
                case 3:{
                   // findViewById(R.id.syllableHand3).setBackgroundResource(R.drawable.rounded_border_correct);
                    break;
                }
                case 4:{
                    //findViewById(R.id.syllableHand4).setBackgroundResource(R.drawable.rounded_border_correct);
                    break;
                }
            }
        }else{
            mIncorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_no_ok);
            if (mIncorrectInRound >= 2) {
                switch (mCorrectSyllables) {
                    default:
                    case 1:
                        mSyllableWords.get(roundNumber).set(9, "1");
                        mSyllableWords.get(roundNumber).set(10, "1");
                        mSyllableWords.get(roundNumber).set(11, "1");
                        //findViewById(R.id.syllableHand1).setBackgroundResource(R.drawable.rounded_border_incorrect);
                        break;
                    case 2:
                        mSyllableWords.get(roundNumber).set(8, "1");
                        mSyllableWords.get(roundNumber).set(10, "1");
                        mSyllableWords.get(roundNumber).set(11, "1");
                        //findViewById(R.id.syllableHand2).setBackgroundResource(R.drawable.rounded_border_incorrect);
                        break;
                    case 3:
                        mSyllableWords.get(roundNumber).set(9, "1");
                        mSyllableWords.get(roundNumber).set(8, "1");
                        mSyllableWords.get(roundNumber).set(11, "1");
                       //findViewById(R.id.syllableHand3).setBackgroundResource(R.drawable.rounded_border_incorrect);
                        break;
                    case 4:
                        mSyllableWords.get(roundNumber).set(9, "1");
                        mSyllableWords.get(roundNumber).set(10, "1");
                        mSyllableWords.get(roundNumber).set(8, "1");
                        findViewById(R.id.syllableHand4).setBackgroundResource(R.drawable.rounded_border_incorrect);
                        break;
                }
            }
            switch (mNumberOfSyllables) {
                default:
                case 1:
                    mSyllableWords.get(roundNumber).set(8, "1");
                   // findViewById(R.id.syllableHand1).setBackgroundResource(R.drawable.rounded_border_incorrect);
                    break;
                case 2:
                    mSyllableWords.get(roundNumber).set(9, "1");
                   // findViewById(R.id.syllableHand2).setBackgroundResource(R.drawable.rounded_border_incorrect);
                    break;
                case 3:
                    mSyllableWords.get(roundNumber).set(10, "1");
                   // findViewById(R.id.syllableHand3).setBackgroundResource(R.drawable.rounded_border_incorrect);
                    break;
                case 4:
                    mSyllableWords.get(roundNumber).set(11, "1");
                   //[ findViewById(R.id.syllableHand4).setBackgroundResource(R.drawable.rounded_border_incorrect);
                    break;
            }


        }

        processPoints();
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processPoints(){
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCurrentID
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        if(mCorrect){
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentID,
                    String.valueOf(mIncorrectInRound)};

            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
        }else {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCurrentID,
                    String.valueOf(mIncorrectInRound)};

            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processHandVisiblity(){
        if(mCorrect){
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
                if(!mSyllableWords.get(roundNumber).get(8).equals("1") && !mBeingValidated) {
                    mNumberOfSyllables = 1;
                    mCorrect = (mSyllableWords.get(roundNumber).get(7).equals("1"));
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.syllableHand2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mSyllableWords.get(roundNumber).get(9).equals("1") && !mBeingValidated) {
                    mNumberOfSyllables = 2;
                    mCorrect = (mSyllableWords.get(roundNumber).get(7).equals("2"));
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.syllableHand3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mSyllableWords.get(roundNumber).get(10).equals("1") && !mBeingValidated) {
                    mNumberOfSyllables = 3;
                    mCorrect = (mSyllableWords.get(roundNumber).get(7).equals("3"));
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.syllableHand4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mSyllableWords.get(roundNumber).get(11).equals("1") && !mBeingValidated) {
                    mNumberOfSyllables = 4;
                    mCorrect = (mSyllableWords.get(roundNumber).get(7).equals("4"));
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

        /*
        findViewById(R.id.syllableHand1).setBackgroundResource(R.drawable.rounded_border_unchosen);
        findViewById(R.id.syllableHand2).setBackgroundResource(R.drawable.rounded_border_unchosen);
        findViewById(R.id.syllableHand3).setBackgroundResource(R.drawable.rounded_border_unchosen);
        findViewById(R.id.syllableHand4).setBackgroundResource(R.drawable.rounded_border_unchosen);
    */
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
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        switch (mNumberOfSyllables){
            default:
            case 1:{
                ((ImageView) findViewById(R.id.syllableFrame1))
                        .setImageResource(R.drawable.frm_cs02_on);
                break;
            }
            case 2:{
                ((ImageView) findViewById(R.id.syllableFrame2))
                        .setImageResource(R.drawable.frm_cs02_on);
                break;
            }
            case 3:{
                ((ImageView) findViewById(R.id.syllableFrame3))
                        .setImageResource(R.drawable.frm_cs02_on);
                break;
            }
            case 4:{
                ((ImageView) findViewById(R.id.syllableFrame4))
                        .setImageResource(R.drawable.frm_cs02_on);
                break;
            }
        }
        mValidateAvailable=true;
       
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        findViewById(R.id.syllableImage).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentWordAudio.equals(""))
                    playGeneralAudio(mCurrentWordAudio);
            }
        });




        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mValidateAvailable && !mBeingValidated){
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
            long mAudioDuration=0;

            switch(mProcessGuessPosition){
                case 0:
                default:{
                    if(mCorrect){
                        mAudioDuration=playGeneralAudio("sfx_right");
                    }else{
                        mAudioDuration=playGeneralAudio("sfx_wrong");
                    }
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 1:{
                    processHandVisiblity();
                    if(!mCorrect) {
                        mAudioDuration = playGeneralAudio(mCurrentWordAudio);
                    }
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    guessHandler.removeCallbacks(processGuess);
                    clearFrames();
                    if(mCorrect){
                        roundNumber++;
                        if(roundNumber!=(mSyllableWords.size())){
                            //mBeingValidated=false;
                            mValidateAvailable=false;
                            //clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;
                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));

                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                }
                case 3:{
                    replaceHands();
                    ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
                    displayScreen();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.ACTIVITY_CURRENT_WRDS_LTRS:{

                ArrayList<HashMap<String,String>> mAllGPL
                        =new ArrayList<>(appData.getAppUserCurrentGPL());

                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mAllGPL.get(3).get("app_user_id"),
                        mAllGPL.get(3).get("group_id"),
                        "8",
                        mAllGPL.get(3).get("app_user_current_level")};

                String selection=" words.number_of_syllables>=1 " +
                        "AND words.number_of_syllables<=4 " +
                        "AND variable_phase_levels.group_id="+mAllGPL.get(3).get("group_id")+" " +
                        "AND variable_phase_levels.phase_id="+mAllGPL.get(3).get("phase_id")+" ";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_WORD_SYLLABLE_VALUES, projection, selection, null, null);
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
            case Constants.ACTIVITY_CURRENT_WRDS_LTRS:{
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
