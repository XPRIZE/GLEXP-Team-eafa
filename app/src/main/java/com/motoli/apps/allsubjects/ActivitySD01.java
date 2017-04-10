package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class ActivitySD01 extends ActivitySDRoot 
        implements LoaderManager.LoaderCallbacks<Cursor>{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sd01);
        appData.addToClassOrder(5);
        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mActivityNumber=1;

        mBeingValidated=true;

        mInstructionAudio="info_sd01";
        mPlayPhonicAudio=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();


        ((TextView) findViewById(R.id.phonic1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.phonic2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.phonic3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.phonic4)).setTypeface(appData.getCurrentFontType());


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

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){





        ((TextView) findViewById(R.id.phonic1)).setText("");
        ((TextView) findViewById(R.id.phonic2)).setText("");
        ((TextView) findViewById(R.id.phonic3)).setText("");
        ((TextView) findViewById(R.id.phonic4)).setText("");

        ((TextView) findViewById(R.id.phonic1))
                .setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.phonic2))
                .setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.phonic3))
                .setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.phonic4))
                .setTextColor(getResources().getColor(R.color.normalBlack));


        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off_mic);

        clearFrames();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens(){
        findViewById(R.id.frame1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mRoundPhonics.get(0).get("guessed_yet").equals("0") && !mBeingValidated){
                    mCurrentLocation=0;
                    mCurrentAudio=mRoundPhonics.get(0).get("phonic_audio");
                    mCurrentID=mRoundPhonics.get(0).get("phonic_id");
                    mCorrect=(mRoundPhonics.get(0).get("is_correct").equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.frame2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mRoundPhonics.get(1).get("guessed_yet").equals("0") && !mBeingValidated){
                    mCurrentLocation=1;
                    mCurrentAudio=mRoundPhonics.get(1).get("phonic_audio");
                    mCurrentID=mRoundPhonics.get(1).get("phonic_id");
                    mCorrect=(mRoundPhonics.get(1).get("is_correct").equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.frame3).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mRoundPhonics.get(2).get("guessed_yet").equals("0") && !mBeingValidated){
                    mCurrentLocation=2;
                    mCurrentAudio=mRoundPhonics.get(2).get("phonic_audio");
                    mCurrentID=mRoundPhonics.get(2).get("phonic_id");
                    mCorrect=(mRoundPhonics.get(2).get("is_correct").equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.frame4).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mRoundPhonics.get(3).get("guessed_yet").equals("0") && !mBeingValidated){
                    mCurrentLocation=3;
                    mCurrentAudio=mRoundPhonics.get(3).get("phonic_audio");
                    mCurrentID=mRoundPhonics.get(3).get("phonic_id");
                    mCorrect=(mRoundPhonics.get(3).get("is_correct").equals("1"));
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
                ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_sd01_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_sd01_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_sd01_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_sd01_on);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_sd01_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_sd01_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_sd01_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_sd01_off);


    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void afterEndOfActivity(){
        clearActivity();
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_on_mic);
//        findViewById(R.id.frameGroup).setVisibility(LinearLayout.INVISIBLE);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){
        guessHandler.postDelayed(processGuess, mAudioDuration);
    }


    ////////////////////////////////////////////////////////////////////////////////////////

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
                        if(mRoundNumber<(mCurrentPhonics.size())){
                            //mBeingValidated=false;
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
                            findViewById(R.id.activityMainPartSecond)
                                    .setVisibility(LinearLayout.INVISIBLE);
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
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_PHONIC_LETTERS:{
                String[] mInfo = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        mCurrentGSP.get("current_level")};
                
                String mWhere="variable_phase_levels.group_id="+mCurrentGSP.get("group_id")+" "
                        +"AND variable_phase_levels.phase_id="+mCurrentGSP.get("phase_id")+" "
                        +"AND variable_phase_levels.level_number" +
                        "   <="+mCurrentGSP.get("current_level")+" "
                        +"AND variable_type_relations.variable_type_id=3";

                cursorLoader = new CursorLoader(this, 
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS,
                        mInfo, mWhere, null, null);
                break;
            }
            case Constants.CURRENT_EXTRA_PHONICS:{

                ArrayList<String> mGrammarArray = new ArrayList<String>(
                        Arrays.asList(args.getString("phonic_grammar").split(";")));
                ArrayList<String> mGrammarArrayTwo = new ArrayList<String>(
                        Arrays.asList(args.getString("phonic_grammar_last").split(";")));
                mGrammarArray.addAll(mGrammarArrayTwo);

                String mSelection="variable_phase_levels.level_number" +
                        "<=" +mCurrentGSP.get("current_level")+ " " +
                        "AND phonics.phonic_id!="+args.getString("phonic_id")+" ";


                for(int i=0; i<mGrammarArray.size(); i++){
                    if(i==0){
                        mSelection+="AND ( phonics.phonic_text!=\""+mGrammarArray.get(i)+"\" ";
                    }else{
                        mSelection+="AND phonics.phonic_text!=\""+mGrammarArray.get(i)+"\" ";
                    }
                    if(i==mGrammarArray.size()-1){
                        mSelection+=") ";
                    }

                }
                switch(Integer.parseInt(args.getString("phonic_is_vowel"))){
                    default:
                    case 0:{
                        mSelection+="AND phonic_is_vowel <=1 ";
                        break;
                    }
                    case 2:{
                        mSelection+="AND (phonic_is_vowel <=0 " +
                                "OR phonic_is_vowel==2 OR phonic_is_vowel ==4) ";
                        break;
                    }
                    case 3:{
                        mSelection+="AND (phonic_is_vowel <=1 " +
                                "OR phonic_is_vowel==3 OR phonic_is_vowel ==5) ";
                        break;
                    }
                    case 4:{
                        mSelection+="AND (phonic_is_vowel <=0 " +
                                "OR phonic_is_vowel==2 OR phonic_is_vowel ==4) ";
                        break;
                    }
                    case 5:{
                        mSelection+="AND (phonic_is_vowel <=1 " +
                                "OR phonic_is_vowel==3 OR phonic_is_vowel ==5) ";
                        break;
                    }
                    case 6:{
                        mSelection+="AND (phonic_is_vowel <=0 OR phonic_is_vowel==2 " +
                                "OR phonic_is_vowel ==4 OR phonic_is_vowel ==6) ";
                        break;
                    }
                }//end switch


                String mOrder="";
                if(args.getString("phonic_is_vowel").equals("0")){
                    mOrder="phonics.phonic_is_vowel ASC";
                }else{
                    mOrder="phonics.phonic_is_vowel DESC";
                }

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ORDERED_PHONICS,
                        null, mSelection, null, mOrder);

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
            case Constants.CURRENT_PHONIC_LETTERS:{
                processData(data);
                break;
            }
            case Constants.CURRENT_EXTRA_PHONICS:{
                processExtraPhonics(data);
                break;
            }
        }//end switch
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    

    protected void displayScreen(){

        for(int i=0; i<4; i++){
            mRoundPhonics.get(i).put("guessed_yet","0");
        }
        ((TextView) findViewById(R.id.phonic1)).setText(mRoundPhonics.get(0).get("phonic_text").replace("&quot;","'"));

        if(mRoundPhonics.get(0).get("is_correct").equals("1")){
            mCorrectLocation=0;
            mCorrectAudio=mRoundPhonics.get(0).get("phonic_audio");
        }


        ((TextView) findViewById(R.id.phonic2)).setText(mRoundPhonics.get(1).get("phonic_text").replace("&quot;","'"));

        if(mRoundPhonics.get(1).get("is_correct").equals("1")){
            mCorrectLocation=1;
            mCorrectAudio=mRoundPhonics.get(1).get("phonic_audio");
        }

        ((TextView) findViewById(R.id.phonic3)).setText(mRoundPhonics.get(2).get("phonic_text").replace("&quot;","'"));

        if(mRoundPhonics.get(2).get("is_correct").equals("1")){
            mCorrectLocation=2;
            mCorrectAudio=mRoundPhonics.get(2).get("phonic_audio");
        }

        ((TextView) findViewById(R.id.phonic4)).setText(mRoundPhonics.get(3).get("phonic_text").replace("&quot;","'"));

        if(mRoundPhonics.get(3).get("is_correct").equals("1")){
            mCorrectLocation=3;
            mCorrectAudio=mRoundPhonics.get(3).get("phonic_audio");
        }
        long mAudioDuration=0;
        if(mRoundNumber==0)
            mAudioDuration=playInstructionAudio();

        mAudioHandler.postDelayed(playPhonicAudio, mAudioDuration+20);

    }//end private void displayScreen(Cursor mPhonicsCursor){

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        super.setUpListeners();
        findViewById(R.id.btnMicDude).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mCorrectAudio.equals(""))
                    playGeneralAudio(mCorrectAudio);
            }
        });

    }


}
