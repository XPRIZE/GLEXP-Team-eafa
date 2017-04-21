package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/31/2015.
 */
public class ActivityWD03 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private int mCurrentTextLocation=0;
    private int correctWordCount=0;
    private int mRoundNumber=0;

    private int mIncorrectInRound=0;
    private boolean mCorrect=false;

    protected ArrayList<ArrayList<String>> mCurrentText;
    private ArrayList<String> wordStatus;

    private ArrayList<ArrayList<String>> mCurrentWords;

    private ArrayList<ArrayList<ArrayList<String>>> mAllActivityText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_wd03);
        appData.addToClassOrder(8);
        mAllActivityText = new ArrayList<>();

        mBeingValidated=true;
        mInstructionAudio="info_wd03";
        clearActivity();


        findViewById(R.id.frame0).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.box1).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box2).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box3).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box4).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box5).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box6).setVisibility(RelativeLayout.INVISIBLE);

        ((TextView) findViewById(R.id.word1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word4)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word5)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word6)).setTypeface(appData.getCurrentFontType());

        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        if(mCurrentGSP.get("group_id").equals("2") && mCurrentGSP.get("phase_id").equals("2")) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(params.leftMargin,
                    getResources().getDimensionPixelSize(R.dimen.wd03_text_ucase_top_margin),
                    params.rightMargin, 0);
            findViewById(R.id.word1).setLayoutParams(params);
            findViewById(R.id.word2).setLayoutParams(params);
            findViewById(R.id.word3).setLayoutParams(params);
            findViewById(R.id.word4).setLayoutParams(params);
            findViewById(R.id.word5).setLayoutParams(params);
            findViewById(R.id.word6).setLayoutParams(params);

        }
        mRoundNumber=0;


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

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        findViewById(R.id.frame0).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.box1).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box2).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box3).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box4).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box5).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box6).setVisibility(RelativeLayout.VISIBLE);

        wordStatus=new ArrayList<>();
        for(int i=0; i<6;i++){
            wordStatus.add("0");
        }

        correctWordCount=0;
        mIncorrectInRound=0;

        mCurrentText = new ArrayList<>(mAllActivityText.get(mRoundNumber));
        Collections.shuffle(mCurrentText);


        int mFontSize=0;
        for(int i=0;i<mCurrentText.size();i++){
            mFontSize=(mCurrentText.get(i).get(1).length()>mFontSize) ?
                    mCurrentText.get(i).get(1).length() : mFontSize;
        }

        if(mFontSize>=8) {
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word5))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word6))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
        }else if(mFontSize>=7){
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word5))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word6))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
        }else if(mFontSize>=6){
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word5))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word6))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
        }else if(mFontSize>=5){
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word5))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word6))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
        }else{
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word5))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word6))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
        }


        findViewById(R.id.micDude).setVisibility(ImageView.VISIBLE);

        findViewById(R.id.word1).setTag(mCurrentText.get(0));
        ((TextView) findViewById(R.id.word1)).setText(mCurrentText.get(0).get(1));

        findViewById(R.id.word2).setTag(mCurrentText.get(1));
        ((TextView) findViewById(R.id.word2)).setText(mCurrentText.get(1).get(1));

        findViewById(R.id.word3).setTag(mCurrentText.get(2));
        ((TextView) findViewById(R.id.word3)).setText(mCurrentText.get(2).get(1));

        findViewById(R.id.word4).setTag(mCurrentText.get(3));
        ((TextView) findViewById(R.id.word4)).setText(mCurrentText.get(3).get(1));

        findViewById(R.id.word5).setTag(mCurrentText.get(4));
        ((TextView) findViewById(R.id.word5)).setText(mCurrentText.get(4).get(1));

        findViewById(R.id.word6).setTag(mCurrentText.get(5));
        ((TextView) findViewById(R.id.word6)).setText(mCurrentText.get(5).get(1));


        Collections.shuffle(mCurrentText);


        setAudio();

        long mAudioDuration=(correctWordCount==0 && mRoundNumber<1)? playInstructionAudio():0;

        mAudioHandler.postDelayed(playCurrentWord, mAudioDuration+10);

        mValidateAvailable=false;
        mBeingValidated=false;
        setupFrameListens();
        setUpListeners();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////



    private void clearActivity(){

        //clear image
        ((TextView) findViewById(R.id.word1)).setText("");
        ((TextView) findViewById(R.id.word2)).setText("");
        ((TextView) findViewById(R.id.word3)).setText("");
        ((TextView) findViewById(R.id.word4)).setText("");
        ((TextView) findViewById(R.id.word5)).setText("");
        ((TextView) findViewById(R.id.word6)).setText("");

        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off_bottom_space);




        ((TextView) findViewById(R.id.word1)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word2)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word3)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word4)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word5)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word6)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));

        ((ImageView) findViewById(R.id.frame1))
                .setImageResource(R.drawable.rounded_text_unselected);
        ((ImageView) findViewById(R.id.frame2))
                .setImageResource(R.drawable.rounded_text_unselected);
        ((ImageView) findViewById(R.id.frame3))
                .setImageResource(R.drawable.rounded_text_unselected);
        ((ImageView) findViewById(R.id.frame4))
                .setImageResource(R.drawable.rounded_text_unselected);
        ((ImageView) findViewById(R.id.frame5))
                .setImageResource(R.drawable.rounded_text_unselected);
        ((ImageView) findViewById(R.id.frame6))
                .setImageResource(R.drawable.rounded_text_unselected);

        //remove stricktrough
        ((TextView) findViewById(R.id.word1)).setPaintFlags(((TextView) findViewById(R.id.word1))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.word2)).setPaintFlags(((TextView) findViewById(R.id.word2))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.word3)).setPaintFlags(((TextView) findViewById(R.id.word3))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.word4)).setPaintFlags(((TextView) findViewById(R.id.word4))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.word5)).setPaintFlags(((TextView) findViewById(R.id.word5))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.word6)).setPaintFlags(((TextView) findViewById(R.id.word6))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));


    }//end private void clearActivity(){


    /////////////////////////////////////////////////////////////////////////////////////////////

    private void setAudio(){
        currentLtrWrdAudio=mCurrentText.get(correctWordCount).get(2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void playMatchingLtrWord(){
        //playGeneralAudio(matchingLtrWrdAudio);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens(){
        findViewById(R.id.word1).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText= (ArrayList<String>) findViewById(R.id.word1).getTag();
                mCurrentID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(0).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=0;
                    mCorrect =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.word2).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText=(ArrayList<String>) findViewById(R.id.word2).getTag();
                mCurrentID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(1).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=1;
                    mCorrect =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.word3).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText=(ArrayList<String>) findViewById(R.id.word3).getTag();
                mCurrentID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(2).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=2;
                    mCorrect =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.word4).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText=(ArrayList<String>) findViewById(R.id.word4).getTag();
                mCurrentID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(3).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=3;
                    mCorrect =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(3);
                }
            }
        });

        findViewById(R.id.word5).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText=(ArrayList<String>) findViewById(R.id.word5).getTag();
                mCurrentID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(4).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=4;
                    mCorrect =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(4);
                }
            }
        });

        findViewById(R.id.word6).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> thisText=(ArrayList<String>) findViewById(R.id.word6).getTag();
                mCurrentID=thisText.get(0);
                matchingLtrWrdAudio=thisText.get(2);
                playMatchingLtrWord();
                if(wordStatus.get(5).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=5;
                    mCorrect =thisText.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(5);
                }
            }
        });


    }//protected void setupFrameListens(){

    ////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_on_bottom_space);
        mValidateAvailable=true;

        switch(frameNumber){
            default:
            case 0:{
                ((ImageView) findViewById(R.id.frame1))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
            case 1:{
                ((ImageView) findViewById(R.id.frame2))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
            case 2:{
                ((ImageView) findViewById(R.id.frame3))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
            case 3:{
                ((ImageView) findViewById(R.id.frame4))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
            case 4:{
                ((ImageView) findViewById(R.id.frame5))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
            case 5:{
                ((ImageView) findViewById(R.id.frame6))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            }
        }//end switch(frameNumber){
    }//end private void setUpFrame(int frameNumber){

    ///////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        if(wordStatus.get(0).equals("0"))
            ((ImageView) findViewById(R.id.frame1))
                    .setImageResource(R.drawable.rounded_text_unselected);

        if(wordStatus.get(1).equals("0"))
            ((ImageView) findViewById(R.id.frame2))
                    .setImageResource(R.drawable.rounded_text_unselected);

        if(wordStatus.get(2).equals("0"))
            ((ImageView) findViewById(R.id.frame3))
                    .setImageResource(R.drawable.rounded_text_unselected);

        if(wordStatus.get(3).equals("0"))
            ((ImageView) findViewById(R.id.frame4))
                    .setImageResource(R.drawable.rounded_text_unselected);

        if(wordStatus.get(4).equals("0"))
            ((ImageView) findViewById(R.id.frame5))
                    .setImageResource(R.drawable.rounded_text_unselected);

        if(wordStatus.get(5).equals("0"))
            ((ImageView) findViewById(R.id.frame6))
                    .setImageResource(R.drawable.rounded_text_unselected);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){



        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mValidateAvailable && !mBeingValidated){
                    playClickSound();
                    validate();
                }
            }
        });

        findViewById(R.id.micDude).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!currentLtrWrdAudio.equals(""))
                    playGeneralAudio(currentLtrWrdAudio);
            }
        });


    }//end protected void setUpListeners(){

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration = super.playInstructionAudio();
        mAudioHandler.postDelayed(playCurrentWord, mAudioDuration+10);

        return mAudioDuration;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playCurrentWord = new Runnable(){
        @Override
        public void run(){
            playGeneralAudio(currentLtrWrdAudio);
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        String[] selectionArgs;

        if(mCorrect){
            String where=" WHERE app_user_id="+appData.getCurrentUserID()
                    +" AND variable_id="+mCurrentID
                    +" AND activity_id="+appData.getCurrentActivity().get(0);
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok_bottom_space);

            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentID,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);

            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok_bottom_space);
            correctWordCount++;
        }else{
            String where=" WHERE app_user_id="+appData.getCurrentUserID()
                    +" AND variable_id="+mCurrentID
                    +" AND activity_id="+appData.getCurrentActivity().get(0);
            mIncorrectInRound++;
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCorrectID,
                    "0",
                    String.valueOf(mIncorrectInRound)};
            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);

            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok_bottom_space);

        }

        processLayoutAfterGuess();

        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }//end protected void validate(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processLayoutAfterGuess(){

        switch(mCurrentTextLocation){
            default:
            case 0:{
                if(mCorrect){
                    wordStatus.set(0, "2");
                    ((TextView) findViewById(R.id.word1)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.frame1))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(0, "1");
                    ((TextView) findViewById(R.id.word1)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.word1)).setPaintFlags(
                            ((TextView) findViewById(R.id.word1)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.frame1))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
            case 1:{
                if(mCorrect){
                    wordStatus.set(1, "2");
                    ((TextView) findViewById(R.id.word2)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.frame2))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(1, "1");
                    ((TextView) findViewById(R.id.word2)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.word2)).setPaintFlags(
                            ((TextView) findViewById(R.id.word2)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.frame2))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
            case 2:{
                if(mCorrect){
                    wordStatus.set(2, "2");
                    ((TextView) findViewById(R.id.word3)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.frame3))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(2, "1");
                    ((TextView) findViewById(R.id.word3)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.word3)).setPaintFlags(
                            ((TextView) findViewById(R.id.word3)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.frame3))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
            case 3:{
                if(mCorrect){
                    wordStatus.set(3, "2");
                    ((TextView) findViewById(R.id.word4))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.frame4))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(3, "1");
                    ((TextView) findViewById(R.id.word4))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.word4))
                            .setPaintFlags(((TextView) findViewById(R.id.word4)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.frame4))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
            case 4:{
                if(mCorrect){
                    wordStatus.set(4, "2");
                    ((TextView) findViewById(R.id.word5)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.frame5))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(4, "1");
                    ((TextView) findViewById(R.id.word5))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.word5))
                            .setPaintFlags(((TextView) findViewById(R.id.word5)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.frame5))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
            case 5:{
                if(mCorrect){
                    wordStatus.set(5, "2");
                    ((TextView) findViewById(R.id.word6)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.frame6))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(5, "1");
                    ((TextView) findViewById(R.id.word6))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.word6))
                            .setPaintFlags(((TextView) findViewById(R.id.word6)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.frame6))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
        }//end switch

    }//end private void processLayoutAfterGuess(boolean correctWord){

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
                        mAudioDuration=playGeneralAudio(currentLtrWrdAudio);
                    }else{
                        mAudioDuration=playGeneralAudio(matchingLtrWrdAudio);
                    }
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{

                    guessHandler.removeCallbacks(processGuess);
                    if(correctWordCount==6){
                        mRoundNumber++;
                        if(mRoundNumber!=(mAllActivityText.size())){
                            clearActivity();
                            inBetweenRounds(0);
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;

                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,2000);
                        }
                    }else{
                        if(mCorrect){
                            clearInCorrects();
                            setAudio();

                            mAudioDuration=(correctWordCount==0)? playInstructionAudio():0;

                            mAudioHandler.postDelayed(playCurrentWord, mAudioDuration+10);

                        }
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off_bottom_space);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }
                    break;
                }
                case 3:{
                    inBetweenRounds(1);
                    displayScreen();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };//end private Runnable processGuess = new Runnable(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearInCorrects(){
        if(wordStatus.get(0).equals("0") || wordStatus.get(0).equals("1")){
            wordStatus.set(0, "0");
            ((ImageView) findViewById(R.id.frame1))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.word1))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.word1))
                    .setPaintFlags(((TextView) findViewById(R.id.word1)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(1).equals("0") || wordStatus.get(1).equals("1")){
            wordStatus.set(1, "0");
            ((ImageView) findViewById(R.id.frame2))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.word2))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.word2))
                    .setPaintFlags(((TextView) findViewById(R.id.word2)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(2).equals("0") || wordStatus.get(2).equals("1")){
            wordStatus.set(2, "0");
            ((ImageView) findViewById(R.id.frame3))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.word3))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.word3))
                    .setPaintFlags(((TextView) findViewById(R.id.word3)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(3).equals("0") || wordStatus.get(3).equals("1")){
            wordStatus.set(3, "0");
            ((ImageView) findViewById(R.id.frame4))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.word4))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.word4))
                    .setPaintFlags(((TextView) findViewById(R.id.word4)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(4).equals("0") || wordStatus.get(4).equals("1")){
            wordStatus.set(4, "0");
            ((ImageView) findViewById(R.id.frame5))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.word5))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.word5))
                    .setPaintFlags(((TextView) findViewById(R.id.word5)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(5).equals("0") || wordStatus.get(5).equals("1")){
            wordStatus.set(5, "0");
            ((ImageView) findViewById(R.id.frame6))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.word6))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.word6))
                    .setPaintFlags(((TextView) findViewById(R.id.word6)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

    }//end private void clearInCorrects(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void inBetweenRounds(int level){
        if(level==0){
            findViewById(R.id.micDude).setVisibility(ImageView.INVISIBLE);
            findViewById(R.id.btnValidate).setVisibility(ImageView.INVISIBLE);
        }else{
            findViewById(R.id.micDude).setVisibility(ImageView.VISIBLE);
            findViewById(R.id.btnValidate).setVisibility(ImageView.VISIBLE);

            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_off_bottom_space);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void shuffleGameData(){
        mAllActivityText.clear();

        int nCurrentCount=0;
        int nNumberRounds=Math.ceil((double)mCurrentWords.size()/6)>=2 ? 2 :1;
                //2;//Constants.INA_ROW_CORRECT;
        int nPosition=0;

        for(int nRoundCount=0; nRoundCount<nNumberRounds; nRoundCount++){
            mAllActivityText.add(new ArrayList<ArrayList<String>>());
            int mWordCount=0;
            do{
                if(nCurrentCount==mCurrentWords.size()){
                    nCurrentCount=1;
                }

                boolean mNoMatching=true;
                for(ArrayList mTemp : mAllActivityText.get(nRoundCount) ) {
                    if(mTemp.get(0).equals(mCurrentWords.get(nPosition).get(0))){
                        mNoMatching=false;
                        break;
                    }
                }

                if(mNoMatching) {
                    mAllActivityText.get(nRoundCount).add(new ArrayList<String>());
                    mAllActivityText.get(nRoundCount).get(mWordCount)
                            .add(mCurrentWords.get(nPosition).get(0)); //word_id
                    mAllActivityText.get(nRoundCount).get(mWordCount)
                            .add(mCurrentWords.get(nPosition).get(1)); //word_text
                    mAllActivityText.get(nRoundCount).get(mWordCount)
                            .add(mCurrentWords.get(nPosition).get(2)); //word_audio
                    mAllActivityText.get(nRoundCount).get(mWordCount)
                            .add(mCurrentWords.get(nPosition).get(5));    //word_image
                    mWordCount++;
                }
                nPosition++;
                if(nPosition==mCurrentWords.size()){
                    nPosition=0;
                }

                nCurrentCount=(mWordCount==0 && nRoundCount>0 )
                        ? nCurrentCount : (nCurrentCount+1) ;
            }while(mWordCount<6);
        }//end for(int roundCount=0; roundCount<numberOfRoundsAvail; roundCount++){
        for(int i=0;i<nNumberRounds; i++){
            Collections.shuffle(mAllActivityText.get(i));
        }
    }//end private void shuffleGameData(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_WORDS:{

                mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
                if(mCurrentGSP.get("phase_id").equals("3")){
                    mCurrentGSP.put("phase_id","2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),"4", "1", "11",
                        mCurrentGSP.get("current_level"),
                        "0"};

                String selection="variable_phase_levels.group_id" +
                        "="+mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id" +
                        "="+mCurrentGSP.get("phase_id")+
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
            case Constants.CURRENT_WORDS:{
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
    //////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mCurrentWords=new ArrayList<>();
        int currentWordNumber=0;

        if(mCursor.moveToFirst()) {
            do{
                mCurrentWords.add(new ArrayList<String>());
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("word_id")));
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("word_text")));
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("audio_url")));
                mCurrentWords.get(currentWordNumber).add("0");
                mCurrentWords.get(currentWordNumber).add("0");
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("image_url")));
                mCurrentWords.get(currentWordNumber).add("0"); //6
                currentWordNumber++;
            }while(mCursor.moveToNext());
        }

        shuffleGameData();
        displayScreen();

    }
}
