package com.motoli.apps.allsubjects;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/24/2015.
 */
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class ActivitySD06 extends ActivitySDRoot
        implements LoaderManager.LoaderCallbacks<Cursor>{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sd06);
        appData.addToClassOrder(8);
        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));


        mBeingValidated=true;
        mActivityNumber=6;
        mInstructionAudio="info_sd06";
        mPlayPhonicAudio=true;

        setUpListeners();
        visibleProgress();
        clearActivity();
        setupFrameListens();
        ((TextView) findViewById(R.id.phonic0)).setTypeface(appData.getCurrentFontType());


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
        getLoaderManager().initLoader(Constants.CURRENT_PHONIC_LETTERS, null, this);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private void visibleProgress(){


    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){
        ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.blank_image);
        ((TextView) findViewById(R.id.phonic0)).setText("");

        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void afterEndOfActivity(){
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_on);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mIncorrectInRound=0;
        
        long mAudioDuration = 0;
        if (mRoundNumber == 0) {
            mAudioDuration = playInstructionAudio();
        }

        ((ImageView) findViewById(R.id.beginningPhonicCircle))
                .setImageResource(R.drawable.btn_circle);
        ((ImageView) findViewById(R.id.endingPhonicStar))
                .setImageResource(R.drawable.btn_star);

        if(mCorrectPhonicWord.get("phonic_word_first_id")
                .equals(mRoundPhonics.get(0).get("phonic_id"))){
            mCorrectLocation=0;
        }else{
            mCorrectLocation=1;
        }
         
        mCorrectAudio = mRoundPhonics.get(0).get("phonic_audio");
        mCurrentAudio=mCorrectPhonicWord.get("phonic_word_audio");




        ((TextView) findViewById(R.id.phonic0)).setText(mRoundPhonics.get(0).get("phonic_text").replace("&quot;","'"));
        
        try{
            String imageName=mCorrectPhonicWord.get("phonic_word_image").replace(".jpg", "")
                    .replace(".png", "").trim();
            int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
            ((ImageView) findViewById(R.id.image0)).setImageResource(resID);
        }catch (Exception e) {
            ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.blank_image);
            Log.e("MyTag", "Failure to get drawable id.", e);
        }

        mAudioHandler.postDelayed(playPhonicAudio, mAudioDuration + 20);

    }//end private void displayScreen(Cursor currentWordsCursor){
    
    ///////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens(){
        findViewById(R.id.beginningPhonicCircle).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    mCurrentLocation = 0;
                    mCorrectPhonicWord.put("guessed_yet","1");
                    ((ImageView) findViewById(R.id.beginningPhonicCircle))
                            .setImageResource(R.drawable.btn_circle_select);
                    if (!mCorrectPhonicWord.get("guessed_yet_other").equals("2")) {
                        ((ImageView) findViewById(R.id.endingPhonicStar))
                                .setImageResource(R.drawable.btn_star);
                    }
                    mCorrect = (mCorrectLocation==mCurrentLocation);
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.endingPhonicStar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    mCorrectPhonicWord.put("guessed_yet_other","1");
                    mCurrentLocation = 1;
                    if (!mCorrectPhonicWord.get("guessed_yet").equals("2")) {
                        ((ImageView) findViewById(R.id.beginningPhonicCircle))
                                .setImageResource(R.drawable.btn_circle);
                    }
                    ((ImageView) findViewById(R.id.endingPhonicStar))
                            .setImageResource(R.drawable.btn_star_select);

                    mCorrect = (mCorrectLocation==mCurrentLocation);
                    setUpFrame();
                }
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){
        guessHandler.postDelayed(processGuess, mAudioDuration);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(){
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        mValidateAvailable=true;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

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
                    mAudioDuration=playGeneralAudio(currentLtrWrdAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    guessHandler.removeCallbacks(processGuess);
                    if(mCorrect){
                        mRoundNumber++;
                        if(mRoundNumber!=(mCurrentPhonics.size())){
                            mValidateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;
                            afterEndOfActivity();
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
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off);

                    guessHandler.removeCallbacks(processGuess);
                    beginRound();
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };



    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        super.setUpListeners();
        findViewById(R.id.phonic0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCorrectAudio.equals("") && !mBeingValidated){
                    playClickSound();
                    playGeneralAudio(mCorrectAudio);
                }

            }
        });

        findViewById(R.id.image0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentAudio.equals("") && !mBeingValidated){
                    playClickSound();
                    playGeneralAudio(mCurrentAudio);
                }

            }
        });



    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader mCursorLoader;
        switch(id){
            case Constants.MATCHING_PHONIC_LETTERS_WORDS:{
                String[] mInfo = new String[]{mCorrectID};
                mCursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATCHING_PHONIC_LETTERS_WORDS,
                        mInfo,null,null,null);
                break;
            }
            default:

            case Constants.CURRENT_PHONIC_LETTERS:{

                String[] mInfo = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        mCurrentGSP.get("current_level")};
                String mWhere="variable_phase_levels.group_id="+mCurrentGSP.get("group_id")+ " " +
                        "AND variable_phase_levels.phase_id="+mCurrentGSP.get("phase_id") +" " +
                        "AND variable_phase_levels.level_number" +
                        "<="+mCurrentGSP.get("current_level") +" " +
                        "AND variable_type_relations.variable_type_id=3 ";

                mCursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS, mInfo, mWhere, null, null);
                break;
            }
        }

        return mCursorLoader;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch(loader.getId()) {
            default:
                break;
            case Constants.MATCHING_PHONIC_LETTERS_WORDS:{
                processSinglePhonicWord(data);
                displayScreen();
                break;
            }
            case Constants.CURRENT_PHONIC_LETTERS:{
                processData(data);
                break;
            }
        }// end switch
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void beginRound(){
        super.beginRound();
        mCorrectAudio=mRoundPhonics.get(0).get("phonic_audio");
        
        getLoaderManager().restartLoader(Constants.MATCHING_PHONIC_LETTERS_WORDS,
                null, this);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }


}
