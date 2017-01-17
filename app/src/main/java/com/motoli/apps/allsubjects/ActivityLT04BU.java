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
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ActivityLT04BU extends ActivityLTRoot
        implements LoaderManager.LoaderCallbacks<Cursor>{



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_lt04);
        appData.addToClassOrder(8);
        allActivityText = new ArrayList<>();
         mValidateButton=(findViewById(R.id.btnValidate)!=null);
        mActivityNumber=4;

        mBeingValidated=true;
        mInstructionAudio="info_lt04";
        mPlayLetterAudio=true;

        clearActivity();

        findViewById(R.id.lettersBox).setVisibility(TableLayout.VISIBLE);


        ((TextView) findViewById(R.id.letter1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter4)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter5)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter6)).setTypeface(appData.getCurrentFontType());

        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        if(mCurrentGSP.get("group_id").equals("2") && mCurrentGSP.get("phase_id").equals("2")) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(params.leftMargin,
                    getResources().getDimensionPixelSize(R.dimen.lt04_text_ucase_top_margin),
                    params.rightMargin, 0);
            findViewById(R.id.letter1).setLayoutParams(params);
            findViewById(R.id.letter2).setLayoutParams(params);
            findViewById(R.id.letter3).setLayoutParams(params);
            findViewById(R.id.letter4).setLayoutParams(params);
            findViewById(R.id.letter5).setLayoutParams(params);
            findViewById(R.id.letter6).setLayoutParams(params);

        }
        mRoundNumber=0;
        mIncorrectInRound=0;

        startActivityHandler.postDelayed(startActivity, 200);

/**/
      //  getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_LETTERS, null, this);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mLetterStatus=new ArrayList<>();
        for(int i=0; i<6;i++){
            mLetterStatus.add("0");
        }

        mCorrectLetterCount=0;
        
        Collections.shuffle(mRoundLetters);
        
        findViewById(R.id.letter1).setTag(mRoundLetters.get(0));
        ((TextView)findViewById(R.id.letter1)).setText(mRoundLetters.get(0).get("letter_text"));

        findViewById(R.id.letter2).setTag(mRoundLetters.get(1));
        ((TextView)findViewById(R.id.letter2)).setText(mRoundLetters.get(1).get("letter_text"));

        findViewById(R.id.letter3).setTag(mRoundLetters.get(2));
        ((TextView)findViewById(R.id.letter3)).setText(mRoundLetters.get(2).get("letter_text"));

        findViewById(R.id.letter4).setTag(mRoundLetters.get(3));
        ((TextView)findViewById(R.id.letter4)).setText(mRoundLetters.get(3).get("letter_text"));

        findViewById(R.id.letter5).setTag(mRoundLetters.get(4));
        ((TextView)findViewById(R.id.letter5)).setText(mRoundLetters.get(4).get("letter_text"));

        findViewById(R.id.letter6).setTag(mRoundLetters.get(5));
        ((TextView)findViewById(R.id.letter6)).setText(mRoundLetters.get(5).get("letter_text"));

        //There are two shuffles like this to make the calling of the letters not the order
        //that the letters are placed in the box
        Collections.shuffle(mRoundLetters);

        setAudio();

        mCorrectID=mRoundLetters.get(mCorrectLetterCount).get("letter_id");

        if(mCorrectLetterCount==0 && mRoundNumber<6){
            playInstructionAudio();
        }

        mValidateAvailable=false;
        mBeingValidated=false;
        setupFrameListens();
        setUpListeners();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){

        ((TextView)findViewById(R.id.letter1)).setText("");
        ((TextView)findViewById(R.id.letter2)).setText("");
        ((TextView)findViewById(R.id.letter3)).setText("");
        ((TextView)findViewById(R.id.letter4)).setText("");
        ((TextView)findViewById(R.id.letter5)).setText("");
        ((TextView)findViewById(R.id.letter6)).setText("");



        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off_mic);
        

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

        ((ImageView) findViewById(R.id.letterBox1))
                .setImageResource(R.drawable.rounded_image);
        ((ImageView) findViewById(R.id.letterBox2))
                .setImageResource(R.drawable.rounded_image);
        ((ImageView) findViewById(R.id.letterBox3))
                .setImageResource(R.drawable.rounded_image);
        ((ImageView) findViewById(R.id.letterBox4))
                .setImageResource(R.drawable.rounded_image);
        ((ImageView) findViewById(R.id.letterBox5))
                .setImageResource(R.drawable.rounded_image);
        ((ImageView) findViewById(R.id.letterBox6))
                .setImageResource(R.drawable.rounded_image);

        //remove stricktrough
        ((TextView) findViewById(R.id.letter1)).setPaintFlags(
                ((TextView) findViewById(R.id.letter1)).getPaintFlags()
                        & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.letter2)).setPaintFlags(
                ((TextView) findViewById(R.id.letter2)).getPaintFlags()
                        & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.letter3)).setPaintFlags(
                ((TextView) findViewById(R.id.letter3)).getPaintFlags()
                        & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.letter4)).setPaintFlags(
                ((TextView) findViewById(R.id.letter4)).getPaintFlags()
                        & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.letter5)).setPaintFlags(
                ((TextView) findViewById(R.id.letter5)).getPaintFlags()
                        & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.letter6)).setPaintFlags(
                ((TextView) findViewById(R.id.letter6)).getPaintFlags()
                        & (~ Paint.STRIKE_THRU_TEXT_FLAG));


    }//end private void clearActivity(){


    ////////////////////////////////////////////////////////////////////////////////////////////

    private void setAudio(){
        mCorrectAudio=mRoundLetters.get(mCorrectLetterCount).get("audio_url");
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){
        guessHandler.postDelayed(processGuess, mAudioDuration);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens(){
        findViewById(R.id.letter1).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                HashMap<String,String> mClickedLetter =
                        (HashMap<String,String>) findViewById(R.id.letter1).getTag();
                mCurrentAudio=mClickedLetter.get("audio_url");
                mCurrentLocation=0;
                if(mLetterStatus.get(0).equals("0") && !mBeingValidated){
                    mCorrect =mClickedLetter.get("letter_id").equals(
                            mRoundLetters.get(mCorrectLetterCount).get("letter_id"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.letter2).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                HashMap<String,String> mClickedLetter =
                        (HashMap<String,String>) findViewById(R.id.letter2).getTag();
                mCurrentAudio=mClickedLetter.get("audio_url");
                mCurrentLocation=1;
                if(mLetterStatus.get(1).equals("0") && !mBeingValidated){
                    mCorrect =mClickedLetter.get("letter_id").equals(
                            mRoundLetters.get(mCorrectLetterCount).get("letter_id"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.letter3).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                HashMap<String,String> mClickedLetter =
                        (HashMap<String,String>) findViewById(R.id.letter3).getTag();
                mCurrentAudio=mClickedLetter.get("audio_url");
                mCurrentLocation=2;
                if(mLetterStatus.get(2).equals("0") && !mBeingValidated){
                    mCorrect =mClickedLetter.get("letter_id").equals(
                            mRoundLetters.get(mCorrectLetterCount).get("letter_id"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.letter4).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                HashMap<String,String> mClickedLetter =
                        (HashMap<String,String>) findViewById(R.id.letter4).getTag();
                mCurrentAudio=mClickedLetter.get("audio_url");
                mCurrentLocation=3;
                if(mLetterStatus.get(3).equals("0") && !mBeingValidated){
                    mCorrect =mClickedLetter.get("letter_id").equals(
                            mRoundLetters.get(mCorrectLetterCount).get("letter_id"));
                    setUpFrame(3);
                }
            }
        });

        findViewById(R.id.letter5).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                HashMap<String,String> mClickedLetter =
                        (HashMap<String,String>) findViewById(R.id.letter5).getTag();
                mCurrentAudio=mClickedLetter.get("audio_url");
                mCurrentLocation=4;
                //playCurrentLetterAudio();
                if(mLetterStatus.get(4).equals("0") && !mBeingValidated){
                    mCorrect =mClickedLetter.get("letter_id").equals(
                            mRoundLetters.get(mCorrectLetterCount).get("letter_id"));
                    setUpFrame(4);
                }
            }
        });

        findViewById(R.id.letter6).setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                HashMap<String,String> mClickedLetter =
                        (HashMap<String,String>) findViewById(R.id.letter6).getTag();
                mCurrentID=mClickedLetter.get("letter_id");
                mCurrentAudio=mClickedLetter.get("audio_url");
                mCurrentLocation=5;
                //playCurrentLetterAudio();
                if(mLetterStatus.get(5).equals("0") && !mBeingValidated){
                    mCorrect =mClickedLetter.get("letter_id").equals(
                            mRoundLetters.get(mCorrectLetterCount).get("letter_id"));
                    setUpFrame(5);
                }
            }
        });


    }//protected void setupFrameListens(){

    /////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_on_mic);
        mValidateAvailable=true;

        switch(frameNumber){
            default:
            case 0:{
                ((ImageView) findViewById(R.id.letterBox1))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
            case 1:{
                ((ImageView) findViewById(R.id.letterBox2))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
            case 2:{
                ((ImageView) findViewById(R.id.letterBox3))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
            case 3:{
                ((ImageView) findViewById(R.id.letterBox4))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
            case 4:{
                ((ImageView) findViewById(R.id.letterBox5))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
            case 5:{
                ((ImageView) findViewById(R.id.letterBox6))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
        }//end switch(frameNumber){
    }//end private void setUpFrame(int frameNumber){

    ///////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        if(mLetterStatus.get(0).equals("0"))
            ((ImageView) findViewById(R.id.letterBox1))
                    .setImageResource(R.drawable.rounded_image);

        if(mLetterStatus.get(1).equals("0"))
            ((ImageView) findViewById(R.id.letterBox2))
                    .setImageResource(R.drawable.rounded_image);

        if(mLetterStatus.get(2).equals("0"))
            ((ImageView) findViewById(R.id.letterBox3))
                    .setImageResource(R.drawable.rounded_image);

        if(mLetterStatus.get(3).equals("0"))
            ((ImageView) findViewById(R.id.letterBox4))
                    .setImageResource(R.drawable.rounded_image);

        if(mLetterStatus.get(4).equals("0"))
            ((ImageView) findViewById(R.id.letterBox5))
                    .setImageResource(R.drawable.rounded_image);

        if(mLetterStatus.get(5).equals("0"))
            ((ImageView) findViewById(R.id.letterBox6))
                    .setImageResource(R.drawable.rounded_image);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        super.setUpListeners();
         
        findViewById(R.id.micDude).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mCorrectAudio.equals(""))
                    playGeneralAudio(mCorrectAudio);
            }
        });


    }//end protected void setUpListeners(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playCorrectLetterAudio = new Runnable(){
        @Override
        public void run(){
            playGeneralAudio(mCorrectAudio);
            setCorrectID();
        }
    };


    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_LETTERS, null, this);
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
                    if(mCorrect){
                        mAudioDuration=playGeneralAudio(mCurrentAudio);
                    }else{
                        mAudioDuration=playGeneralAudio(mCurrentAudio);
                    }
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    guessHandler.removeCallbacks(processGuess);
                    if(mCorrectLetterCount==6){
                        mRoundNumber++;
                        mIncorrectInRound=0;
                        if(mRoundNumber<(mCurrentLetters.size())){
                            clearActivity();
                            inBetweenRounds(0);
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;
                            findViewById(R.id.lettersBox).setVisibility(TableLayout.INVISIBLE);
                            findViewById(R.id.lettersBox)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
                            findViewById(R.id.micDude).setVisibility(ImageView.INVISIBLE);
                            findViewById(R.id.micDude)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
                            findViewById(R.id.btnValidate).setVisibility(ImageView.INVISIBLE);
                            findViewById(R.id.btnValidate)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));

                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,2000);
                        }
                    }else{
                        if(mCorrect){
                            clearInCorrects();
                            setAudio();

                            mAudioDuration=(mCorrectLetterCount==0)? playInstructionAudio():0;
                            mIncorrectInRound=0;
                            mAudioHandler.postDelayed(playCorrectLetterAudio, mAudioDuration+10);

                        }
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off_mic);
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
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };//end private Runnable processGuess = new Runnable(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void setCorrectID(){
        mCorrectID=mRoundLetters.get(mCorrectLetterCount).get("letter_id");
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void clearInCorrects(){
        if(mLetterStatus.get(0).equals("0") || mLetterStatus.get(0).equals("1")){
            mLetterStatus.set(0, "0");
            ((ImageView) findViewById(R.id.letterBox1))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter1))
                    .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
            ((TextView) findViewById(R.id.letter1))
                    .setPaintFlags(((TextView) findViewById(R.id.letter1)).getPaintFlags() 
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(mLetterStatus.get(1).equals("0") || mLetterStatus.get(1).equals("1")){
            mLetterStatus.set(1, "0");
            ((ImageView) findViewById(R.id.letterBox2))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter2))
                    .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
            ((TextView) findViewById(R.id.letter2))
                    .setPaintFlags(((TextView) findViewById(R.id.letter2)).getPaintFlags() 
                    & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(mLetterStatus.get(2).equals("0") || mLetterStatus.get(2).equals("1")){
            mLetterStatus.set(2, "0");
            ((ImageView) findViewById(R.id.letterBox3))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter3))
                    .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
            ((TextView) findViewById(R.id.letter3))
                    .setPaintFlags(((TextView) findViewById(R.id.letter3)).getPaintFlags() 
                    & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(mLetterStatus.get(3).equals("0") || mLetterStatus.get(3).equals("1")){
            mLetterStatus.set(3, "0");
            ((ImageView) findViewById(R.id.letterBox4))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter4))
                    .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
            ((TextView) findViewById(R.id.letter4))
                    .setPaintFlags(((TextView) findViewById(R.id.letter4)).getPaintFlags() 
                    & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(mLetterStatus.get(4).equals("0") || mLetterStatus.get(4).equals("1")){
            mLetterStatus.set(4, "0");
            ((ImageView) findViewById(R.id.letterBox5))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter5))
                    .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
            ((TextView) findViewById(R.id.letter5))
                    .setPaintFlags(((TextView) findViewById(R.id.letter5)).getPaintFlags()
                    & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(mLetterStatus.get(5).equals("0") || mLetterStatus.get(5).equals("1")){
            mLetterStatus.set(5, "0");
            ((ImageView) findViewById(R.id.letterBox6))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter6))
                    .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
            ((TextView) findViewById(R.id.letter6))
                    .setPaintFlags(((TextView) findViewById(R.id.letter6)).getPaintFlags() 
                    & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

    }//end private void clearInCorrects(){

    ////////////////////////////////////////////////////////////////////////////////////////////
    
    private void inBetweenRounds(int level){
        if(level==0){
            findViewById(R.id.micDude).setVisibility(ImageView.INVISIBLE);


        }else{
            findViewById(R.id.micDude).setVisibility(ImageView.VISIBLE);

            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_off_mic);


        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.ACTIVITY_CURRENT_LETTERS:{
                mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
                String level_number=(Integer.parseInt(mCurrentGSP.get("current_level"))<=2) ? "3" 
                        : mCurrentGSP.get("current_level");
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

                String secondSelection="variable_phase_levels.group_id="
                            +mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id="+
                            mCurrentGSP.get("phase_id")+
                        " AND ((variable_phase_levels.level_number="
                            +mCurrentGSP.get("current_level")+"-1 "+
                        " OR variable_phase_levels.level_number="
                            +mCurrentGSP.get("current_level")+"-2 "+
                        " OR variable_phase_levels.level_number="
                            +mCurrentGSP.get("current_level")+"-3"+
                        " OR variable_phase_levels.level_number="
                            +mCurrentGSP.get("current_level")+" ) "+
                        " OR variable_phase_levels.level_number<="
                            +mCurrentGSP.get("current_level")+"-3)";

                String selection="variable_phase_levels.group_id="+mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id="+mCurrentGSP.get("phase_id")+
                        " AND ((variable_phase_levels.level_number="+level_number+"-1 "+
                        " OR variable_phase_levels.level_number="+level_number+"-2 "+
                        " OR variable_phase_levels.level_number="+level_number+"-3"+
                        " OR variable_phase_levels.level_number="+level_number+" ) "+
                        " OR variable_phase_levels.level_number<="+level_number+"-3)";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ACTIVITY_CURRENT_LETTERS_WRW,
                        projection, selection, null, secondSelection);
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

    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }



    
    ////////////////////////////////////////////////////////////////////////////////////////

}
