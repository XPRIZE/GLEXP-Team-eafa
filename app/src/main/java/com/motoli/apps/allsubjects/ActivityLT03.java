package com.motoli.apps.allsubjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;

import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
public class ActivityLT03 extends ActivityLTRoot
        implements LoaderManager.LoaderCallbacks<Cursor>{


    private int mProcessGuessPosition=0;
    private int mChosenLetterCount=0;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_lt03);

        appData.addToClassOrder(6);
        allActivityText = new ArrayList<>();
        mValidateButton=(findViewById(R.id.btnValidate)!=null);
        mActivityNumber=3;

        mInstructionAudio="info_lt03";

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        clearActivity();

        setUpListeners();

        mChosenImages=new ArrayList<>();
        mChosenImages.add("");
        mChosenImages.add("");

        mValidateAvailable=false;
        mBeingValidated=true;

        startActivityHandler.postDelayed(startActivity, 200);

    }

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_LETTERS, null, this);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mCurrentID="";
        loadImagesAndHideText();
        mLetterStatus=new ArrayList<>();
        for(int i=0; i<6;i++){
            mLetterStatus.add("0");
        }

        mChosenLetterCount=0;
        mCorrectLetterCount=0;

        if(mRoundNumber<3){
            playInstructionAudio();
        }

        clearText();
        Collections.shuffle(mRoundLetters);

        findViewById(R.id.letter1).setTag(mRoundLetters.get(0).get("letter_id"));
        ((TextView)findViewById(R.id.letter1)).setText(mRoundLetters.get(0).get("letter_text"));

        findViewById(R.id.letter2).setTag(mRoundLetters.get(1).get("letter_id"));
        ((TextView)findViewById(R.id.letter2)).setText(mRoundLetters.get(1).get("letter_text"));

        findViewById(R.id.letter3).setTag(mRoundLetters.get(2).get("letter_id"));
        ((TextView)findViewById(R.id.letter3)).setText(mRoundLetters.get(2).get("letter_text"));

        findViewById(R.id.letter4).setTag(mRoundLetters.get(3).get("letter_id"));
        ((TextView)findViewById(R.id.letter4)).setText(mRoundLetters.get(3).get("letter_text"));

        findViewById(R.id.letter5).setTag(mRoundLetters.get(4).get("letter_id"));
        ((TextView)findViewById(R.id.letter5)).setText(mRoundLetters.get(4).get("letter_text"));

        findViewById(R.id.letter6).setTag(mRoundLetters.get(5).get("letter_id"));
        ((TextView)findViewById(R.id.letter6)).setText(mRoundLetters.get(5).get("letter_text"));

        mValidateAvailable=false;
        mBeingValidated=false;
        setupFrameListens();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////////////////////////

    private void loadImagesAndHideText(){
        findViewById(R.id.letter1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letter2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letter3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letter4).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letter5).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letter6).setVisibility(TextView.INVISIBLE);

        findViewById(R.id.letterBox1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letterBox2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letterBox3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letterBox4).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letterBox5).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letterBox6).setVisibility(TextView.INVISIBLE);


        findViewById(R.id.image1).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image2).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image3).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image4).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image5).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image6).setVisibility(ImageButton.VISIBLE);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////



    protected void setupFrameListens(){
        findViewById(R.id.image1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mBeingValidated && mLetterStatus.get(0).equals("0")  && mChosenLetterCount<2){
                    mBeingValidated=true;
                    mChosenLetterCount++;
                    mCurrentAudio=mRoundLetters.get(0).get("audio_url");
                    if(!mCurrentID.equals("")){
                        mChosenImages.set(0, "0");
                        mCorrect =findViewById(R.id.letter1).getTag().equals(mCurrentID);
                        setUpFrame(0,true);
                    }else{
                        mChosenImages.set(1, "0");
                        mCurrentID=findViewById(R.id.letter1).getTag().toString();
                        setUpFrame(0,false);
                    }
                }
            }
        });

        findViewById(R.id.image2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mBeingValidated) {
                    if (mLetterStatus.get(1).equals("0") && mChosenLetterCount < 2) {
                        mBeingValidated = true;
                        mChosenLetterCount++;
                        mCurrentAudio = mRoundLetters.get(1).get("audio_url");
                        if (!mCurrentID.equals("")) {
                            mChosenImages.set(0, "1");
                            mCorrect = findViewById(R.id.letter2).getTag().equals(mCurrentID);
                            setUpFrame(1, true);
                        } else {
                            mChosenImages.set(1, "1");
                            mCurrentID = findViewById(R.id.letter2).getTag().toString();
                            setUpFrame(1, false);
                        }
                    }
                }
            }
        });

        findViewById(R.id.image3).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mBeingValidated) {
                    if (mLetterStatus.get(2).equals("0") && mChosenLetterCount < 2) {
                        mBeingValidated = true;
                        mChosenLetterCount++;
                        mCurrentAudio = mRoundLetters.get(2).get("audio_url");
                        if (!mCurrentID.equals("")) {
                            mChosenImages.set(0, "2");
                            mCorrect = findViewById(R.id.letter3).getTag().equals(mCurrentID);
                            setUpFrame(2, true);
                        } else {
                            mChosenImages.set(1, "2");
                            mCurrentID = findViewById(R.id.letter3).getTag().toString();
                            setUpFrame(2, false);
                        }
                    }
                }
            }
        });

        findViewById(R.id.image4).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mBeingValidated){
                    if(mLetterStatus.get(3).equals("0") && mChosenLetterCount<2) {
                        mBeingValidated = true;
                        mChosenLetterCount++;
                        mCurrentAudio = mRoundLetters.get(3).get("audio_url");
                        if (!mCurrentID.equals("")) {
                            mChosenImages.set(0, "3");
                            mCorrect = findViewById(R.id.letter4).getTag().equals(mCurrentID);
                            setUpFrame(3, true);
                        } else {
                            mChosenImages.set(1, "3");
                            mCurrentID = findViewById(R.id.letter4).getTag().toString();
                            setUpFrame(3, false);
                        }
                    }
                }
            }
        });

        findViewById(R.id.image5).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mBeingValidated) {
                    if (mLetterStatus.get(4).equals("0") && mChosenLetterCount < 2) {
                        mBeingValidated = true;
                        mChosenLetterCount++;
                        mCurrentAudio = mRoundLetters.get(4).get("audio_url");
                        if (!mCurrentID.equals("")) {
                            mChosenImages.set(0, "4");
                            mCorrect = findViewById(R.id.letter5).getTag().equals(mCurrentID);
                            setUpFrame(4, true);
                        } else {
                            mChosenImages.set(1, "4");
                            mCurrentID = findViewById(R.id.letter5).getTag().toString();
                            setUpFrame(4, false);
                        }
                    }
                }
            }
        });

        findViewById(R.id.image6).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mBeingValidated){
                    if(mLetterStatus.get(5).equals("0")  && mChosenLetterCount<2){
                        mBeingValidated=true;
                        mChosenLetterCount++;
                        mCurrentAudio=mRoundLetters.get(5).get("audio_url");
                        if(!mCurrentID.equals("")){
                            mChosenImages.set(0, "5");
                            mCorrect =findViewById(R.id.letter6).getTag().equals(mCurrentID);
                            setUpFrame(5,true);
                        }else{
                            mChosenImages.set(1, "5");
                            mCurrentID=findViewById(R.id.letter6).getTag().toString();
                            setUpFrame(5,false);
                        }
                    }
                }
            }
        });


    }//protected void setupFrameListens(){

    //////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber, boolean secondGuess){

        if(secondGuess){
            mValidateAvailable=true;
            long mAudioDuration=playGeneralAudio(mCurrentAudio);
            validateHandler.postDelayed(processValidate, mAudioDuration+10);

        }else{
            playGeneralAudio(mCurrentAudio);
            mBeingValidated=false;

        }
        switch(frameNumber){
            default:
            case 0:{
                findViewById(R.id.image1).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.letter1).setVisibility(TextView.VISIBLE);
                findViewById(R.id.letterBox1).setVisibility(TextView.VISIBLE);
                break;
            }
            case 1:{
                findViewById(R.id.image2).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.letter2).setVisibility(TextView.VISIBLE);
                findViewById(R.id.letterBox2).setVisibility(TextView.VISIBLE);
                break;
            }
            case 2:{
                findViewById(R.id.image3).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.letter3).setVisibility(TextView.VISIBLE);
                findViewById(R.id.letterBox3).setVisibility(TextView.VISIBLE);
                break;
            }
            case 3:{
                findViewById(R.id.image4).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.letter4).setVisibility(TextView.VISIBLE);
                findViewById(R.id.letterBox4).setVisibility(TextView.VISIBLE);
                break;
            }
            case 4:{
                findViewById(R.id.image5).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.letter5).setVisibility(TextView.VISIBLE);
                findViewById(R.id.letterBox5).setVisibility(TextView.VISIBLE);
                break;
            }
            case 5:{
                findViewById(R.id.image6).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.letter6).setVisibility(TextView.VISIBLE);
                findViewById(R.id.letterBox6).setVisibility(TextView.VISIBLE);
                break;
            }
        }//end switch(frameNumber){
    }//end private void setUpFrame(int frameNumber){


    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable processValidate = new Runnable(){
        @Override
        public void run(){
            if(mValidateAvailable) {
                validate();
            }
        }

    };

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, mAudioDuration);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

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
                        mBeingValidated=false;
                        mValidateAvailable=false;
                        mAudioDuration=playGeneralAudio("sfx_wrong");
                    }
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+5);
                    break;
                }
                case 1:{
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+5);
                    break;
                }
                case 2:{
                    mChosenLetterCount=0;
                    mCurrentID="";
                    mChosenImages.set(0, "");
                    mChosenImages.set(1, "");
                    if(mCorrectLetterCount==3){
                        mRoundNumber++;
                        if(mRoundNumber<(mCurrentLetters.size())){
                            clearText();
                            inBetweenRounds(0);
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 10);
                        }else{
                            mLastActivityData=0;
                            addPointToAllAvailable();
                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        clearInCorrects();
                        mBeingValidated=false;
                        mValidateAvailable=false;

                    }
                    break;
                }
                case 3:{
                    inBetweenRounds(1);
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
                case 4:{

                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };//end private Runnable processGuess = new Runnable(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearInCorrects(){
        if(mLetterStatus.get(0).equals("0") || mLetterStatus.get(0).equals("1")){
            mLetterStatus.set(0, "0");
            ((TextView) findViewById(R.id.letter1)).setTextColor(
                    ContextCompat.getColor(this, R.color.normalBlack));
            findViewById(R.id.letter1).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.letterBox1).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image1).setVisibility(ImageButton.VISIBLE);
        }
        if(mLetterStatus.get(1).equals("0") || mLetterStatus.get(1).equals("1")){
            mLetterStatus.set(1, "0");
            ((TextView) findViewById(R.id.letter2)).setTextColor(
                    ContextCompat.getColor(this, R.color.normalBlack));
            findViewById(R.id.letter2).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.letterBox2).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image2).setVisibility(ImageButton.VISIBLE);
        }
        if(mLetterStatus.get(2).equals("0") || mLetterStatus.get(2).equals("1")){
            mLetterStatus.set(2, "0");
            ((TextView) findViewById(R.id.letter3)).setTextColor(
                    ContextCompat.getColor(this, R.color.normalBlack));
            findViewById(R.id.letter3).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.letterBox3).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image3).setVisibility(ImageButton.VISIBLE);
        }
        if(mLetterStatus.get(3).equals("0") || mLetterStatus.get(3).equals("1")){
            mLetterStatus.set(3, "0");
            ((TextView) findViewById(R.id.letter4)).setTextColor(
                    ContextCompat.getColor(this, R.color.normalBlack));
            findViewById(R.id.letter4).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.letterBox4).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image4).setVisibility(ImageButton.VISIBLE);
        }
        if(mLetterStatus.get(4).equals("0") || mLetterStatus.get(4).equals("1")){
            mLetterStatus.set(4, "0");
            ((TextView) findViewById(R.id.letter5)).setTextColor(
                    ContextCompat.getColor(this, R.color.normalBlack));
            findViewById(R.id.letter5).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.letterBox5).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image5).setVisibility(ImageButton.VISIBLE);
        }
        if(mLetterStatus.get(5).equals("0") || mLetterStatus.get(5).equals("1")){
            mLetterStatus.set(5, "0");
            ((TextView) findViewById(R.id.letter6)).setTextColor(
                    ContextCompat.getColor(this, R.color.normalBlack));
            findViewById(R.id.letter6).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.letterBox6).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image6).setVisibility(ImageButton.VISIBLE);
        }

    }//end private void clearInCorrects(){

    ////////////////////////////////////////////////////////////////////////////////////////////


    private void inBetweenRounds(int level){
        if(level==0){
            clearText();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearText(){
        findViewById(R.id.letter1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letter2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letter3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letter4).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letter5).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letter6).setVisibility(TextView.INVISIBLE);

        findViewById(R.id.letterBox1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letterBox2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letterBox3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letterBox4).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letterBox5).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.letterBox6).setVisibility(TextView.INVISIBLE);


        //clear image
        ((TextView)findViewById(R.id.letter1)).setText("");
        ((TextView)findViewById(R.id.letter2)).setText("");
        ((TextView)findViewById(R.id.letter3)).setText("");
        ((TextView)findViewById(R.id.letter4)).setText("");
        ((TextView)findViewById(R.id.letter5)).setText("");
        ((TextView)findViewById(R.id.letter6)).setText("");

        ((TextView)findViewById(R.id.letter1)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.letter2)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.letter3)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.letter4)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.letter5)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.letter6)).setTypeface(appData.getCurrentFontType());

        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        /*
        if(mCurrentGSP.get("group_id").equals("2") && mCurrentGSP.get("phase_id").equals("2")){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(params.leftMargin,
                    getResources().getDimensionPixelSize(R.dimen.lt03_text_ucase_top_margin),
                    params.rightMargin, 0);
            ((TextView) findViewById(R.id.letter1)).setGravity(Gravity.CENTER_HORIZONTAL);
            findViewById(R.id.letter1).setLayoutParams(params);
            findViewById(R.id.letter2).setLayoutParams(params);
            findViewById(R.id.letter3).setLayoutParams(params);
            findViewById(R.id.letter4).setLayoutParams(params);
            findViewById(R.id.letter5).setLayoutParams(params);
            findViewById(R.id.letter6).setLayoutParams(params);
            //((TextView)findViewById(R.id.letter1)).se
        }
*/
        ((TextView) findViewById(R.id.letter1)).setTextColor(
                ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter2)).setTextColor(
                ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter3)).setTextColor(
                ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter4)).setTextColor(
                ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter5)).setTextColor(
                ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter6)).setTextColor(
                ContextCompat.getColor(this, R.color.normalBlack));



    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){
        loadImagesAndHideText();
        clearText();

    }//end private void clearText(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.ACTIVITY_CURRENT_LETTERS:{

                mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
                if(mCurrentGSP.get("phase_id").equals("3")){
                    mCurrentGSP.put("phase_id","2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        mCurrentGSP.get("current_level")};

                String selection="variable_phase_levels.group_id="
                            +mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id="
                            +mCurrentGSP.get("phase_id")+
                        " AND ((variable_phase_levels.level_number="
                            +mCurrentGSP.get("current_level")+"-1 "+
                        " OR variable_phase_levels.level_number="
                            +mCurrentGSP.get("current_level")+"-2 "+
                        " OR variable_phase_levels.level_number="
                            +mCurrentGSP.get("current_level")+"-3"+
                        " OR variable_phase_levels.level_number="
                            +mCurrentGSP.get("current_level")+" ) "+
                        " OR variable_phase_levels.level_number<="
                            +mCurrentGSP.get("current_level")+" -3)";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ACTIVITY_CURRENT_LETTERS_WRW,
                        projection, selection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
         switch(loader.getId()) {
         default:
             break;
         case Constants.ACTIVITY_CURRENT_LETTERS:{
             processData(data);
             break;
         }
     }
    }
/////////////
    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }



    ///////////////////////////////////////////////////////////////////////////////////////////
}

