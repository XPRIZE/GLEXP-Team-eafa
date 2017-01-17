package com.motoli.apps.allsubjects;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/22/2015.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class ActivitySD03 extends ActivitySDRoot
        implements LoaderManager.LoaderCallbacks<Cursor>{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sd03);
        appData.addToClassOrder(8);

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        findViewById(R.id.activityMainPartSecond)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mBeingValidated=true;
        mActivityNumber=3;
        mInstructionAudio="info_sd03";
        mPlayPhonicAudio=true;

        setUpListeners();
        clearActivity();
        setupFrameListens();

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
    ////////////////////////////////////////////////////////////////////////////////////////////

    public void onDestroy() {
        super.onDestroy();
        guessHandler.removeCallbacks(processGuess);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){
        ((ImageView) findViewById(R.id.image1)).setImageResource(R.drawable.blank_image);
        ((ImageView) findViewById(R.id.image2)).setImageResource(R.drawable.blank_image);
        ((ImageView) findViewById(R.id.image3)).setImageResource(R.drawable.blank_image);
        ((ImageView) findViewById(R.id.image4)).setImageResource(R.drawable.blank_image);

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off_mic);

        clearFrames();
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void afterEndOfActivity(){
        clearActivity();
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_on_mic);

        //findViewById(R.id.frameGroup).setVisibility(LinearLayout.INVISIBLE);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mIncorrectInRound=0;
        long mAudioDuration = 0;
        if (mRoundNumber == 0) {
            mAudioDuration = playInstructionAudio();
        }

        if (mCorrectPhonicWord != null) {
            Collections.shuffle(mRoundPhonicWords);
            for (int i = 0; i < 4; i++) {
                if (mRoundPhonicWords.get(i).get("is_correct").equals("1")) {
                    mCorrectLocation = i;
                }
                processPlacingImage(i);
            }

            mAudioHandler.postDelayed(playPhonicAudio, mAudioDuration + 20);
        } else {
            mCorrect = true;
            mProcessGuessPosition = 2;
            guessHandler.postDelayed(processGuess, mAudioDuration + 20);

        }
    }//end private void displayScreen(Cursor currentWordsCursor){	

    /////////////////////////////////////////////////////////////////////////////////////////

    private void processPlacingImage(int wordNumber){
        try{
            String imageName=mRoundPhonicWords.get(wordNumber).get("phonic_word_image")
                    .replace(".jpg", "").replace(".png", "").trim();

            int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());

            switch(wordNumber){
                default:
                case 0:{

                    findViewById(R.id.image1).setTag(mRoundPhonicWords.get(wordNumber));
                    ((ImageView) findViewById(R.id.image1)).setImageResource(resID);
                    break;
                }
                case 1:{

                    findViewById(R.id.image2).setTag(mRoundPhonicWords.get(wordNumber));
                    ((ImageView) findViewById(R.id.image2)).setImageResource(resID);
                    break;
                }
                case 2:{

                    findViewById(R.id.image3).setTag(mRoundPhonicWords.get(wordNumber));
                    ((ImageView) findViewById(R.id.image3)).setImageResource(resID);
                    break;
                }
                case 3:{

                    findViewById(R.id.image4).setTag(mRoundPhonicWords.get(wordNumber));
                    ((ImageView) findViewById(R.id.image4)).setImageResource(resID);
                    break;
                }
            }

        }catch (Exception e) {
            Log.e("MyTag", "Failure to get drawable id.", e);
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens(){
        findViewById(R.id.frame1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mRoundPhonicWords.get(0).get("guessed_yet").equals("0") && !mBeingValidated){
                    mCurrentLocation=0;
                    mCurrentAudio=mRoundPhonicWords.get(0).get("phonic_word_audio");
                    playGeneralAudio(mCurrentAudio);
                    mCorrect=mRoundPhonicWords.get(0).get("is_correct").equals("1");
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.frame2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mRoundPhonicWords.get(1).get("guessed_yet").equals("0") && !mBeingValidated){
                    mCurrentLocation=1;
                    mCurrentAudio=mRoundPhonicWords.get(1).get("phonic_word_audio");
                    playGeneralAudio(mCurrentAudio);
                    mCorrect=mRoundPhonicWords.get(1).get("is_correct").equals("1");
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.frame3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mRoundPhonicWords.get(2).get("guessed_yet").equals("0") && !mBeingValidated){
                    mCurrentLocation=2;
                    mCurrentAudio=mRoundPhonicWords.get(2).get("phonic_word_audio");
                    playGeneralAudio(mCurrentAudio);
                    mCorrect=mRoundPhonicWords.get(2).get("is_correct").equals("1");
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.frame4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mRoundPhonicWords.get(3).get("guessed_yet").equals("0") && !mBeingValidated){
                    mCurrentLocation=3;
                    mCurrentAudio=mRoundPhonicWords.get(3).get("phonic_word_audio");
                    playGeneralAudio(mCurrentAudio);
                    mCorrect=mRoundPhonicWords.get(3).get("is_correct").equals("1");
                    setUpFrame(3);
                }
            }
        });


    }

    /////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on_mic);
        mValidateAvailable=true;
        switch(frameNumber){
            default:
            case 0:{
                ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_sd03_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_sd03_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_sd03_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_sd03_on);
                break;
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_sd03_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_sd03_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_sd03_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_sd03_off);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////



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
                    mAudioDuration=playGeneralAudio(mCurrentAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    processAfterValidationDisplay();
                    guessHandler.removeCallbacks(processGuess);
                    clearFrames();
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

                            findViewById(R.id.activityMainPartSecond)
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

                    break;
                }
                case 3:{
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off_mic);
                    guessHandler.removeCallbacks(processGuess);
                    beginRound();
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){
        guessHandler.postDelayed(processGuess, mAudioDuration);
    }



    
    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void beginRound(){
        super.beginRound();
        mCorrectAudio=mRoundPhonics.get(0).get("phonic_audio");
        getLoaderManager().restartLoader(Constants.CURRENT_PHONIC_LETTERS_WORDS_SINGLE,
                null, this);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        super.setUpListeners();

        findViewById(R.id.btnMicDude).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mRoundPhonics.get(0).get("phonic_audio").equals("") && !mBeingValidated){
                    playClickSound();
                    playGeneralAudio(mRoundPhonics.get(0).get("phonic_audio"));
                }

            }
        });



    }

    ////////////////////////////////////////////////////////////////////////////////////////////

     @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader mCursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_PHONIC_LETTERS_WORDS:{
                ArrayList<String> mGrammarArray = new ArrayList<String>(
                        Arrays.asList(mRoundPhonics.get(0).get("phonic_grammar").split(";")));
                String selection=" phonics.phonic_id=phonic_words.phonic_word_last_id  ";
                if(mGrammarArray.size()>0) {
                    selection += " AND ( phonic_words.phonic_word_text " +
                            "NOT LIKE \"%"+ mRoundPhonics.get(0).get("phonic_text") + "\" AND ";

                    for (int i = 0; i < mGrammarArray.size(); i++) {
                        if (i > 0) {
                            selection += " AND ";
                        }
                        selection += " phonic_words.phonic_word_text NOT LIKE " +
                                "\"%" + mGrammarArray.get(i) + "\" ";
                    }
                    selection+=" ) ";
                }

                String[] projection = new String[]{"16"};

                mCursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS_WORDS_LAST_LETTER
                        ,projection,selection,null,null);
                break;
            }
            case Constants.CURRENT_PHONIC_LETTERS_WORDS_SINGLE:{
                String mWhere=" phonic_words.phonic_word_last_id" +
                        "="+mRoundPhonics.get(0).get("phonic_id")+" " +
                        "AND phonics.phonic_id" +
                        "="+mRoundPhonics.get(0).get("phonic_id")+" ";
                String[] mInfo = new String[]{"1"};
                mCursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS_WORDS_LAST_LETTER,
                        mInfo,mWhere,null,null);
                break;
            }
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
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS_LAST,
                        mInfo, mWhere, null, null);
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
            case Constants.CURRENT_PHONIC_LETTERS_WORDS:{
                processExtraPhonicWords(data);
                processPhonicWords();
                break;
            }
            case Constants.CURRENT_PHONIC_LETTERS_WORDS_SINGLE:{
                processSinglePhonicWord(data);
                getLoaderManager().restartLoader(Constants.CURRENT_PHONIC_LETTERS_WORDS,
                        null, this);
                break;
            }
            case Constants.CURRENT_PHONIC_LETTERS:{
                processData(data);
                break;
            }
        }// end switch
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    ////////////////////////////////////////////////////////////////////////////////////////////


}
