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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class ActivitySD04 extends ActivitySDRoot 
        implements LoaderManager.LoaderCallbacks<Cursor>{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sd04);
        appData.addToClassOrder(8);
        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));



        mBeingValidated=true;
        mActivityNumber=4;
        mInstructionAudio="info_sd04";
        mPlayPhonicAudio=true;

        setUpListeners();
        visibleProgress();
        clearActivity();
        setupFrameListens();

        ((TextView) findViewById(R.id.phonic0)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.phonic1)).setTypeface(appData.getCurrentFontType());


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

    //////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){
        ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.blank_image);

        ((TextView) findViewById(R.id.phonic0)).setText("");
        ((TextView) findViewById(R.id.phonic1)).setText("");
        ((TextView) findViewById(R.id.phonic0)).setTextColor(
                getResources().getColor(R.color.darkGray));
        ((TextView) findViewById(R.id.phonic1)).setTextColor(
                getResources().getColor(R.color.darkGray));


        ((ImageView) findViewById(R.id.btnMicDude1))
                .setImageResource(R.drawable.btn_dude_left_off);
        ((ImageView) findViewById(R.id.btnMicDude2))
                .setImageResource(R.drawable.btn_dude_right_off);


        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void afterEndOfActivity(){
        clearActivity();
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_on);
        //findViewById(R.id.frameGroup).setVisibility(LinearLayout.INVISIBLE);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mIncorrectInRound=0;
        long mAudioDuration = 0;
        if (mRoundNumber == 0)
            mAudioDuration = playInstructionAudio();

        mCorrectLocation=0;
        if(mRoundPhonics.get(1).get("is_correct").equals("1")){
            mCorrectLocation=1;
        }
        ((TextView) findViewById(R.id.phonic0)).setText(mRoundPhonics.get(0).get("phonic_text").replace("&quot;","'"));
        ((TextView) findViewById(R.id.phonic1)).setText(mRoundPhonics.get(1).get("phonic_text").replace("&quot;","'"));


        try{
            String imageName=mCorrectPhonicWord.get("phonic_word_image")
                    .replace(".jpg", "").replace(".png", "").trim();
            int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());

            ((ImageView) findViewById(R.id.image0)).setImageResource(resID);


        }catch (Exception e) {
            ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.blank_image);
            Log.e("MyTag", "Failure to get drawable id.", e);
        }

        mAudioHandler.postDelayed(playPhonicAudio, mAudioDuration + 20);

    }//end private void displayScreen(Cursor currentWordsCursor){



    //////////////////////////////////////////////////////////////////////////////////////////////

    private void phonicButtons(){
        switch (mCurrentLocation){
            default:
            case 0:{
                if (!mBeingValidated) {
                    if (mRoundPhonics.get(0).get("guessed_yet").equals("2")){
                        mRoundPhonics.get(0).put("guessed_yet", "3");
                    }else{
                        mRoundPhonics.get(0).put("guessed_yet", "1");
                    }
                    ((ImageView) findViewById(R.id.btnMicDude1))
                            .setImageResource(R.drawable.btn_dude_left_on);
                    ((ImageView) findViewById(R.id.btnMicDude2))
                            .setImageResource(R.drawable.btn_dude_right_off);


                    ((TextView) findViewById(R.id.phonic0))
                            .setTextColor(getResources().getColor(R.color.normalBlack));
                    ((TextView) findViewById(R.id.phonic1))
                            .setTextColor(getResources().getColor(R.color.darkGray));


                    playGeneralAudio(mRoundPhonics.get(0).get("phonic_audio"));
                    mCorrect=mRoundPhonics.get(0).get("is_correct").equals("1");
                }
                break;
            }
            case 1:{
                if ( !mBeingValidated) {
                    if (mRoundPhonics.get(1).get("guessed_yet").equals("2")){
                        mRoundPhonics.get(1).put("guessed_yet", "3");
                    }else{
                        mRoundPhonics.get(1).put("guessed_yet", "1");
                    }
                    ((ImageView) findViewById(R.id.btnMicDude1))
                            .setImageResource(R.drawable.btn_dude_left_off);
                    ((ImageView) findViewById(R.id.btnMicDude2))
                            .setImageResource(R.drawable.btn_dude_right_on);
                    ((TextView) findViewById(R.id.phonic0))
                            .setTextColor(getResources().getColor(R.color.darkGray));
                    ((TextView) findViewById(R.id.phonic1))
                            .setTextColor(getResources().getColor(R.color.normalBlack));

                    playGeneralAudio(mRoundPhonics.get(1).get("phonic_audio"));
                    mCorrect=mRoundPhonics.get(1).get("is_correct").equals("1");
                }
                break;
            }
        }//end switch
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        mValidateAvailable=true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens(){
        findViewById(R.id.btnMicDude1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCurrentLocation=0;
                phonicButtons();
            }
        });

        findViewById(R.id.btnMicDude2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCurrentLocation=1;
                phonicButtons();
            }
        });

        findViewById(R.id.phonic0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCurrentLocation=0;
               phonicButtons();
            }
        });

        findViewById(R.id.phonic1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCurrentLocation=1;
                phonicButtons();
            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){
        guessHandler.postDelayed(processGuess, mAudioDuration);
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
                            guessHandler.postDelayed(processGuess, 10);
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
                        ((ImageView) findViewById(R.id.btnMicDude1))
                                .setImageResource(R.drawable.btn_dude_left_off);
                        ((ImageView) findViewById(R.id.btnMicDude2))
                                .setImageResource(R.drawable.btn_dude_right_off);
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

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_PHONIC_LETTERS_WORDS_SINGLE:{
                String mPhonicID;
                if(mRoundPhonics.get(0).get("is_correct").equals("1")){
                    mPhonicID=mRoundPhonics.get(0).get("phonic_id");
                }else{
                    mPhonicID=mRoundPhonics.get(1).get("phonic_id");
                }
                String mWhere=" phonic_words.phonic_word_first_id="+mPhonicID+" " +
                        "AND phonics.phonic_id="+mPhonicID+" ";
                String[] mInfo = new String[]{"1"};
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS_WORDS_FIRST_LETTER,
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
                        "AND variable_type_relations.variable_type_id=3";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS_FIRST,
                        mInfo, mWhere, null, null);
                break;
            }
            case Constants.CURRENT_EXTRA_PHONICS:{
                ArrayList<String> mGrammarArray = new ArrayList<String>(
                        Arrays.asList(args.getString("phonic_grammar").split(";")));
                String mWhere="variable_phase_levels.level_number" +
                        "<=" +mCurrentGSP.get("current_level")+ " " +
                        "AND phonics.phonic_id!="+args.getString("phonic_id")+" ";

                for(int i=0; i<mGrammarArray.size(); i++){
                    if(i==0){
                        mWhere+="AND ( phonics.phonic_text!=\""+mGrammarArray.get(i)+"\" ";
                    }else{
                        mWhere+="AND phonics.phonic_text!=\""+mGrammarArray.get(i)+"\" ";
                    }
                    if(i==mGrammarArray.size()-1){
                        mWhere+=") ";
                    }
                }
                switch(Integer.parseInt(args.getString("phonic_is_vowel"))){
                    default:
                    case 0:{
                        mWhere+="AND phonic_is_vowel <=1 ";
                        break;
                    }
                    case 2:{
                        mWhere+="AND (phonic_is_vowel <=0 OR phonic_is_vowel==2 " +
                                "OR phonic_is_vowel ==4) ";
                        break;
                    }
                    case 3:{
                        mWhere+="AND (phonic_is_vowel <=1 OR phonic_is_vowel==3 " +
                                "OR phonic_is_vowel ==5) ";
                        break;
                    }
                    case 4:{
                        mWhere+="AND (phonic_is_vowel <=0 OR phonic_is_vowel==2 " +
                                "OR phonic_is_vowel ==4) ";
                        break;
                    }
                    case 5:{
                        mWhere+="AND (phonic_is_vowel <=1 OR phonic_is_vowel==3 " +
                                "OR phonic_is_vowel ==5) ";
                        break;
                    }
                    case 6:{
                        mWhere+="AND (phonic_is_vowel <=0 OR phonic_is_vowel==2 " +
                                "OR phonic_is_vowel ==4 OR phonic_is_vowel ==6) ";
                        break;
                    }
                }//end switch


                String mOrder;
                if(args.getString("phonic_is_vowel").equals("0")){
                    mOrder="phonics.phonic_is_vowel ASC";
                }else{
                    mOrder="phonics.phonic_is_vowel DESC";
                }
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ORDERED_PHONICS, null, mWhere, null, mOrder);
                break;
            }
        }

        return cursorLoader;
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor mCursor) {

        switch(loader.getId()) {
            default:
                break;
            case Constants.CURRENT_PHONIC_LETTERS_WORDS_SINGLE:{
                processSinglePhonicWord(mCursor);
                displayScreen();
                break;
            }
            case Constants.CURRENT_PHONIC_LETTERS:{
                processData(mCursor);
                break;
            }
            case Constants.CURRENT_EXTRA_PHONICS:{
                processExtraPhonics(mCursor);
                getLoaderManager().restartLoader(
                        Constants.CURRENT_PHONIC_LETTERS_WORDS_SINGLE, null, this);

                break;
            }
        }// end switch
        mCursor.close();
    }



    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        super.setUpListeners();

        findViewById(R.id.image0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCorrectPhonicWord.get("phonic_word_audio").equals("") && !mBeingValidated){
                    playClickSound();
                    playGeneralAudio(mCorrectPhonicWord.get("phonic_word_audio"));
                }

            }
        });


    }


}
