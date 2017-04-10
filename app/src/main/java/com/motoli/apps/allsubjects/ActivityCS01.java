package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
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
public class ActivityCS01 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{


    private String mCurrentWordAudio;

    private int mIncorrectInRound=0;
    private int mNumberOfSyllables;

    private ArrayList<String> mChosenSyllables;

    private ArrayList<ArrayList<String>> mSyllableWords;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_cs01);
        appData.addToClassOrder(13);
        appData.setActivityType(3);

        mInstructionAudio="info_cs01";
        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        mBeingValidated=true;
        setUpListeners();
        clearFrames();
        setupFrameListens();

        mChosenSyllables=new ArrayList<String>();
        mChosenSyllables.add("0");
        mChosenSyllables.add("0");
        mChosenSyllables.add("0");


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
            mSyllableWords.get(currentWordNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
            mSyllableWords.get(currentWordNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
            mSyllableWords.get(currentWordNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
            mSyllableWords.get(currentWordNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mSyllableWords.get(currentWordNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mSyllableWords.get(currentWordNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
            mSyllableWords.get(currentWordNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
            if(mCursor.getString(mCursor.getColumnIndex("number_of_syllables")).equals("0")){
                mSyllableWords.get(currentWordNumber).add("1"); //7

            }else {
                mSyllableWords.get(currentWordNumber).add(
                        mCursor.getString(mCursor.getColumnIndex("number_of_syllables"))); //7
            }

            mNumberOfWords++;

            currentWordNumber++;

            /**/
            if (currentWordNumber >= Constants.NUMBER_VARIABLES || mCursor.isLast()){
                break;
            }else{
                mCursor.moveToNext();
            }
        }


        Collections.shuffle(mSyllableWords);


        if(mSyllableWords.size()<Constants.NUMBER_VARIABLES ) {
            for (int i = 0; i < mSyllableWords.size(); i++) {
                if (!mSyllableWords.get(mSyllableWords.size() - 1)
                        .get(0).equals(mSyllableWords.get(i).get(0))) {
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
        mBeingValidated=false;
        for(int i=0; i<mChosenSyllables.size(); i++){
            mChosenSyllables.set(i,"0");
        }
        mCurrentWordAudio=mSyllableWords.get(roundNumber).get(2);
        mCurrentID=mSyllableWords.get(roundNumber).get(0);
        mCorrectID=mSyllableWords.get(roundNumber).get(0);
        long mAudioDuration=0;
        if(roundNumber==0)
            mAudioDuration=playInstructionAudio();

        mAudioHandler.postDelayed(playWordAudio, mAudioDuration+20);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playWordAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playWordAudio);
            if(!mCurrentWordAudio.equals(""))
                playGeneralAudio(mCurrentWordAudio);
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration = super.playInstructionAudio();
        mAudioHandler.postDelayed(playWordAudio, mAudioDuration+20);
        return mAudioDuration;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        mNumberOfSyllables=0;
        for(int i=0;i<mChosenSyllables.size(); i++){
            if(mChosenSyllables.get(i).equals("1")){
                mNumberOfSyllables++;
            }else{
                break;
            }
        }
        mCorrect = (mSyllableWords.get(roundNumber)
                .get(7).equals(String.valueOf(mNumberOfSyllables)));

        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok_mic);
            for(int i=0;i<mChosenSyllables.size(); i++) {
                switch (i) {
                    default:
                    case 0: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables1))
                                    .setImageResource(R.drawable.rounded_cs01_correct);
                        }
                        break;
                    }
                    case 1: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables2))
                                    .setImageResource(R.drawable.rounded_cs01_correct);
                        }
                        break;
                    }
                    case 2: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables3))
                                    .setImageResource(R.drawable.rounded_cs01_correct);
                        }
                        break;
                    }
                }//end   switch (i) {
            }//end for(int i=0;i<mChosenSyllables.size(); i++) {
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok_mic);
            for(int i=0;i<mChosenSyllables.size(); i++) {
                switch (i) {
                    default:
                    case 0: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables1))
                                    .setImageResource(R.drawable.rounded_cs01_wrong);
                        }
                        break;
                    }
                    case 1: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables2))
                                    .setImageResource(R.drawable.rounded_cs01_wrong);
                        }
                        break;
                    }
                    case 2: {
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables3))
                                    .setImageResource(R.drawable.rounded_cs01_wrong);
                        }
                        break;
                    }
                }//end   switch (i) {
            }//end for(int i=0;i<mChosenSyllables.size(); i++) {
        }

        addActivityPoints();
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens() {
        findViewById(R.id.syllables1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    if(mChosenSyllables.get(0).equals("0")) {
                        mChosenSyllables.set(0, "1");
                    }else if(mChosenSyllables.get(1).equals("0")){
                        mChosenSyllables.set(0, "0");
                    }
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.syllables2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    if(mChosenSyllables.get(1).equals("0")
                            && mChosenSyllables.get(0).equals("1")) {
                        mChosenSyllables.set(1, "1");
                    }else if(mChosenSyllables.get(2).equals("0")){
                        mChosenSyllables.set(1, "0");
                    }
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.syllables3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    if(mChosenSyllables.get(2).equals("0")
                            && mChosenSyllables.get(1).equals("1")) {
                        mChosenSyllables.set(2, "1");
                    }else{
                        mChosenSyllables.set(2, "0");
                    }
                    setUpFrame(2);
                }
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        ((ImageView)findViewById(R.id.syllables1))
                .setImageResource(R.drawable.rounded_cs01_unchosen);
        ((ImageView)findViewById(R.id.syllables2))
                .setImageResource(R.drawable.rounded_cs01_unchosen);
        ((ImageView)findViewById(R.id.syllables3))
                .setImageResource(R.drawable.rounded_cs01_unchosen);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber) {
        //clearFrames();
        if(mChosenSyllables.get(0).equals("1") || mChosenSyllables.get(1).equals("1")
                || mChosenSyllables.get(2).equals("1")) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_on_mic);
            mValidateAvailable = true;
            switch (frameNumber) {
                default:
                case 0: {
                    if (mChosenSyllables.get(0).equals("1")) {
                        ((ImageView) findViewById(R.id.syllables1))
                                .setImageResource(R.drawable.rounded_cs01_chosen);
                    } else {
                        ((ImageView) findViewById(R.id.syllables1))
                                .setImageResource(R.drawable.rounded_cs01_unchosen);
                    }
                    break;
                }
                case 1: {
                    if (mChosenSyllables.get(1).equals("1")) {
                        ((ImageView) findViewById(R.id.syllables2))
                                .setImageResource(R.drawable.rounded_cs01_chosen);
                    } else {
                        ((ImageView) findViewById(R.id.syllables2))
                                .setImageResource(R.drawable.rounded_cs01_unchosen);
                    }
                    break;
                }
                case 2: {
                    if (mChosenSyllables.get(2).equals("1")) {
                        ((ImageView) findViewById(R.id.syllables3))
                                .setImageResource(R.drawable.rounded_cs01_chosen);
                    } else {
                        ((ImageView) findViewById(R.id.syllables3))
                                .setImageResource(R.drawable.rounded_cs01_unchosen);
                    }
                    break;
                }
            }
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_off_mic);
            mValidateAvailable = false;
        }
    }

     ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        findViewById(R.id.btnMicDude).setOnClickListener(new View.OnClickListener() {
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
                                 .setImageResource(R.drawable.btn_validate_off_mic);
                         mBeingValidated=false;
                         mValidateAvailable=false;
                     }
                     for(int i=0; i<mChosenSyllables.size(); i++){
                         mChosenSyllables.set(i,"0");
                     }
                     break;
                 }
                 case 3:{
                     ((ImageView) findViewById(R.id.btnValidate))
                             .setImageResource(R.drawable.btn_validate_off_mic);
                     displayScreen();
                     guessHandler.removeCallbacks(processGuess);
                     break;
                 }
             }//switch(mProcessGuessPosition){
         }//public void run(){
     };

    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.ACTIVITY_CURRENT_WRDS_LTRS:{

                ArrayList<HashMap<String,String>> mAllGPL
                        =new ArrayList<>(appData.getAppUserCurrentGPL());

                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mAllGPL.get(3).get("app_user_id"),
                        mAllGPL.get(3).get("group_id"),
                        "8",
                        mAllGPL.get(3).get("app_user_current_level")};

                String selection=" words.number_of_syllables>=1 " +
                        "AND words.number_of_syllables<=3 " +
                        "AND variable_phase_levels.group_id="+mAllGPL.get(3).get("group_id")+" " +
                        "AND variable_phase_levels.phase_id="+mAllGPL.get(3).get("phase_id")+" ";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_WORD_SYLLABLE_VALUES,
                        projection, selection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

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
