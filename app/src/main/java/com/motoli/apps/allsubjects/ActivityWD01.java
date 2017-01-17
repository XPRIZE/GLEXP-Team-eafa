package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/31/2015.
 */
public class ActivityWD01 extends ActivityWDRoot
        implements LoaderManager.LoaderCallbacks<Cursor>{
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_wd01);
        appData.addToClassOrder(5);

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        findViewById(R.id.activityMainPartSecond)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPartSecond)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        mBeingValidated=true;
        mActivityNumber=1;
        mInstructionAudio="info_wd01";
        mPlayWordAudio=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();


        ((TextView) findViewById(R.id.word1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word4)).setTypeface(appData.getCurrentFontType());


        startActivityHandler.postDelayed(startActivity, 200);

    }//end public void onCreate(Bundle savedInstanceState) {

    //////////////////////////////////////////////////////////////////////////////////////////

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////

    private void start(){
        getLoaderManager().initLoader(Constants.CURRENT_WORDS, null, this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
 
    private void clearActivity(){
        /*




        */

        ((TextView) findViewById(R.id.word1)).setText("");
        ((TextView) findViewById(R.id.word2)).setText("");
        ((TextView) findViewById(R.id.word3)).setText("");
        ((TextView) findViewById(R.id.word4)).setText("");

        ((TextView) findViewById(R.id.word1)).setTextColor(
                ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.word2)).setTextColor(
                ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.word3)).setTextColor(
                ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.word4)).setTextColor(
                ContextCompat.getColor(this, R.color.normalBlack));


        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off_mic);

        clearFrames();

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){
        guessHandler.postDelayed(processGuess, mAudioDuration);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mIncorrectInRound=0;
        Collections.shuffle(mRoundWords);

        int mFontSize=0;
        for(int i=0;i<mRoundWords.size();i++){
            mFontSize=(mRoundWords.get(i).get("word_text").length()>mFontSize) ?
                    mRoundWords.get(i).get("word_text").length() : mFontSize;
        }

        float mFontSizeDimension=getResources().getDimension(R.dimen.wdFirstFontSize);
        if(mFontSize>=8) {
            mFontSizeDimension=getResources().getDimension(R.dimen.wdFifthFontSize);
        }else if(mFontSize>=7){
            mFontSizeDimension=getResources().getDimension(R.dimen.wdForthFontSize);
        }else if(mFontSize>=6){
            mFontSizeDimension=getResources().getDimension(R.dimen.wdThirdFontSize);
        }else if(mFontSize>=5) {
            mFontSizeDimension=getResources().getDimension(R.dimen.wdSecondFontSize);
        }
        ((TextView) findViewById(R.id.word1)).setTextSize(mFontSizeDimension);
        ((TextView) findViewById(R.id.word2)).setTextSize(mFontSizeDimension);
        ((TextView) findViewById(R.id.word3)).setTextSize(mFontSizeDimension);
        ((TextView) findViewById(R.id.word4)).setTextSize(mFontSizeDimension);
        
        for(int i=0;i<mRoundWords.size();i++) {
            if (mRoundWords.get(i).get("is_correct").equals("1")) {
                mCorrectLocation = i;
                break;
            }
        }

        ((TextView) findViewById(R.id.word1)).setText(mRoundWords.get(0).get("word_text"));
        ((TextView) findViewById(R.id.word2)).setText(mRoundWords.get(1).get("word_text"));
        ((TextView) findViewById(R.id.word3)).setText(mRoundWords.get(2).get("word_text"));
        ((TextView) findViewById(R.id.word4)).setText(mRoundWords.get(3).get("word_text"));

        /*




        */
        long mAudioDuration=0;
        if(mRoundNumber==0) {
            mAudioDuration = playInstructionAudio();
        }
        
        mAudioHandler.postDelayed(playWordAudio, mAudioDuration+20);

    }//end private void displayScreen(Cursor currentWordsCursor){
    
    ///////////////////////////////////////////////////////////////////////////////////////
    
    protected void setupFrameListens(){
        findViewById(R.id.frame1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mRoundWords.get(0).get("guessed_yet").equals("0") && !mBeingValidated){
                    mCurrentLocation=0;
                    mCurrentAudio=mRoundWords.get(0).get("audio_url");
                    mCorrect=(mRoundWords.get(0).get("is_correct").equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.frame2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRoundWords.get(1).get("guessed_yet").equals("0") && !mBeingValidated) {
                    mCurrentLocation = 1;
                    mCurrentAudio = mRoundWords.get(1).get("audio_url");
                    mCorrect = (mRoundWords.get(1).get("is_correct").equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.frame3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mRoundWords.get(2).get("guessed_yet").equals("0") && !mBeingValidated){
                    mCurrentLocation=2;
                    mCurrentAudio=mRoundWords.get(2).get("audio_url");
                    mCorrect=(mRoundWords.get(2).get("is_correct").equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.frame4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRoundWords.get(3).get("guessed_yet").equals("0") && !mBeingValidated) {
                    mCurrentLocation = 3;
                    mCurrentAudio = mRoundWords.get(3).get("audio_url");
                    mCorrect = (mRoundWords.get(3).get("is_correct").equals("1"));
                    setUpFrame(3);
                }
            }
        });


    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on_mic);
        mValidateAvailable=true;
        switch(frameNumber){
            default:
            case 0:{
                ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_wd01_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_wd01_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_wd01_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_wd01_on);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_wd01_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_wd01_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_wd01_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_wd01_off);


    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    
    private void processChoiceView(){
        if(mCorrect){
            switch(mCurrentLocation){
                default:
                case 0:
                    ((TextView) findViewById(R.id.word2)).setText("");
                    ((TextView) findViewById(R.id.word3)).setText("");
                    ((TextView) findViewById(R.id.word4)).setText("");
                    break;
                case 1:
                    ((TextView) findViewById(R.id.word1)).setText("");
                    ((TextView) findViewById(R.id.word3)).setText("");
                    ((TextView) findViewById(R.id.word4)).setText("");
                    break;
                case 2:
                    ((TextView) findViewById(R.id.word2)).setText("");
                    ((TextView) findViewById(R.id.word1)).setText("");
                    ((TextView) findViewById(R.id.word4)).setText("");
                    break;
                case 3:
                    ((TextView) findViewById(R.id.word2)).setText("");
                    ((TextView) findViewById(R.id.word3)).setText("");
                    ((TextView) findViewById(R.id.word1)).setText("");
                    break;
            }
        }else{
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.word2)).setText("");
                        ((TextView) findViewById(R.id.word3)).setText("");
                        ((TextView) findViewById(R.id.word4)).setText("");
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.word1)).setText("");
                        ((TextView) findViewById(R.id.word3)).setText("");
                        ((TextView) findViewById(R.id.word4)).setText("");
                        break;
                    case 2:
                        ((TextView) findViewById(R.id.word1)).setText("");
                        ((TextView) findViewById(R.id.word2)).setText("");
                        ((TextView) findViewById(R.id.word4)).setText("");
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.word1)).setText("");
                        ((TextView) findViewById(R.id.word2)).setText("");
                        ((TextView) findViewById(R.id.word3)).setText("");
                        break;
                }
            }else{
                switch(mCurrentLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.word1)).setText("");
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.word2)).setText("");
                        break;
                    case 2:
                        ((TextView) findViewById(R.id.word3)).setText("");
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.word4)).setText("");
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }
    }


    private Runnable processGuess = new Runnable(){
        @Override
        public void run(){
            long mAudioDuration;

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
                    mAudioDuration=playGeneralAudio(mCurrentAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    processChoiceView();
                    guessHandler.removeCallbacks(processGuess);
                    clearFrames();
                    if(mCorrect){
                        mRoundNumber++;
                        if(mRoundNumber!=(mCurrentWords.size())){
                            mValidateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 50);
                        }else{
                            mLastActivityData=0;
                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
                            findViewById(R.id.activityMainPartSecond)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPartSecond)
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

                    break;
                }
                case 3:{
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off_mic);
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] mInfo;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_WORDS:{

                mInfo = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),"4", "1", "11",
                        mCurrentGSP.get("current_level"),"0"};

                String mWhere="variable_phase_levels.group_id=4 " +
                        " AND variable_phase_levels.phase_id=1 " +
                        " AND ((variable_phase_levels.level_number" +
                            "="+mCurrentGSP.get("current_level")+"-1 "+
                        " OR variable_phase_levels.level_number" +
                            "="+mCurrentGSP.get("current_level")+"-2 "+
                        " OR variable_phase_levels.level_number" +
                            "="+mCurrentGSP.get("current_level")+"-3"+
                        " OR variable_phase_levels.level_number" +
                            "="+mCurrentGSP.get("current_level")+" ) "+
                        " OR variable_phase_levels.level_number" +
                            "<="+mCurrentGSP.get("current_level")+" -3)"+
                        " AND variable_phase_levels.level_number>0";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_WORDS_BY_LEVEL,
                        mInfo, mWhere, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
            case Constants.CURRENT_WORDS:{
                processData(data);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        super.processData(mCursor);
        beginRound();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        super.setUpListeners();
        findViewById(R.id.btnMicDude).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCorrectAudio.equals("") && !mBeingValidated) {
                    playGeneralAudio(mCorrectAudio);
                }
            }
        });

        

    }

    
}
