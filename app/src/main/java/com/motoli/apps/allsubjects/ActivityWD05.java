package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ActivityWD05 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{


    private ArrayList<ArrayList<String>> mCurrentPhonics;


    private int mIncorrectInRound=0;

    private int mChosenLetterLocation=0;

    private ArrayList<ArrayList<String>> mCurrentLetters;
    private ArrayList<ArrayList<String>> mCurrentWords;
    private ArrayList<String> mCurrentWord;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_wd05);

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        mInstructionAudio="info_wd05";
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        mBeingValidated=true;

        clearFrames();
        clearActivity();
        setUpListeners();
        setupFrameListens();

        ((TextView) findViewById(R.id.word0)).setTypeface(appData.getCurrentFontType());

        ((TextView) findViewById(R.id.letter1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter4)).setTypeface(appData.getCurrentFontType());


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
        getLoaderManager().initLoader(Constants.ALL_LOWERCASE_LETTERS, null, this);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration=super.playInstructionAudio();
        mAudioHandler.postDelayed(playCurrentWordAudio, mAudioDuration+20);
        return mAudioDuration;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////



    private void clearActivity(){

        ((TextView) findViewById(R.id.word0)).setText("");


        ((TextView) findViewById(R.id.letter1)).setText("");
        ((TextView) findViewById(R.id.letter2)).setText("");
        ((TextView) findViewById(R.id.letter3)).setText("");
        ((TextView) findViewById(R.id.letter4)).setText("");

        ((TextView) findViewById(R.id.letter1))
                .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter2))
                .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter3))
                .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter4))
                .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));


        ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.blank_image);

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);

        clearFrames();

    }



    /////////////////////////////////////////////////////////////////////////////////////////

    protected void beginRound(){
        mCurrentWord = new ArrayList<>(mCurrentWords.get(roundNumber));

        Bundle mWordData=new Bundle();

        mWordData.putString("first_letter_length",
                String.valueOf(mCurrentWord.get(9).trim().length()));
        mWordData.putString("phonic_text", mCurrentWord.get(9));
        mWordData.putString("phonic_grammar", mCurrentWord.get(10));
        mWordData.putString("phonic_is_vowel", mCurrentWord.get(11));
        mWordData.putString("phonic_id", mCurrentWord.get(13));


        getLoaderManager().restartLoader(Constants.CURRENT_PHONIC_LETTERS, mWordData, this);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void finishRound(){
        mIncorrectInRound=0;
        String mPhonicIsVowel=mCurrentWord.get(11);
        int mTypeInRow=1;


        if(!mCurrentWord.get(5).equals("")) {

            try {
                String imageName = mCurrentWord.get(5).replace(".jpg", "")
                        .replace(".png", "").trim();
                int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
                ((ImageView) findViewById(R.id.image0)).setImageResource(resID);
            } catch (Exception e) {
                ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.blank_image);
                Log.e("MyTag", "Failure to get drawable id.", e);
            }
        }else{
            ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.bird_no_image);

        }

        int mFontSize=0;

        mFontSize=(mCurrentWord.get(1).length()>mFontSize)
                ?  mCurrentWord.get(1).length() : mFontSize;



        if(mFontSize>=8) {
            ((TextView) findViewById(R.id.word0))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSizeWD05));
        }else if(mFontSize>=7){
            ((TextView) findViewById(R.id.word0))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSizeWD05));
        }else if(mFontSize>=6){
            ((TextView) findViewById(R.id.word0))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSizeWD05));
        }else if(mFontSize>=5){
            ((TextView) findViewById(R.id.word0))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSizeWD05));
        }else{
            ((TextView) findViewById(R.id.word0))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSizeWD05));
        }

        ((TextView) findViewById(R.id.word0))
                .setText(Html.fromHtml(mCurrentWord.get(1).replaceFirst(mCurrentWord.get(9),
                        "<u>&nbsp;&nbsp;&nbsp;</u>")));


        mCurrentLetters = new ArrayList<>();


        mCurrentLetters.add(new ArrayList<String>());
        mCurrentLetters.get(0).add(mCurrentWord.get(9));
        mCurrentLetters.get(0).add(mCurrentWord.get(12));
        mCurrentLetters.get(0).add(mCurrentWord.get(13));
        mCurrentLetters.get(0).add("1");    //3
        mCurrentLetters.get(0).add("0");    //4

        int mCount=1;
        for(int i=0;i<mCurrentPhonics.size();i++){
            if(mTypeInRow<=2 && mCurrentPhonics.get(i).get(3).equals(mPhonicIsVowel)){
                mCurrentLetters.add(new ArrayList<String>());
                mCurrentLetters.get(mCount).add(mCurrentPhonics.get(i).get(1));
                mCurrentLetters.get(mCount).add(mCurrentPhonics.get(i).get(4));
                mCurrentLetters.get(mCount).add(mCurrentPhonics.get(i).get(0));
                mCurrentLetters.get(mCount).add("0");    //3
                mCurrentLetters.get(mCount).add("0");    //4
                mCount++;
                mTypeInRow++;
            }else if(!mCurrentPhonics.get(i).get(3).equals(mPhonicIsVowel)){
                mCurrentLetters.add(new ArrayList<String>());
                mCurrentLetters.get(mCount).add(mCurrentPhonics.get(i).get(1));
                mCurrentLetters.get(mCount).add(mCurrentPhonics.get(i).get(4));
                mCurrentLetters.get(mCount).add(mCurrentPhonics.get(i).get(0));
                mCurrentLetters.get(mCount).add("0");    //3
                mCurrentLetters.get(mCount).add("0");    //4
                mCount++;
            }
            if(mCount>=4){
                break;
            }

        }
        Collections.shuffle(mCurrentLetters);
        displayScreen();
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){

        ((TextView) findViewById(R.id.letter1)).setText(mCurrentLetters.get(0).get(0));
        findViewById(R.id.letter1).setTag(mCurrentLetters.get(0));
        if(mCurrentLetters.get(0).get(3).equals("1")) {
            mCorrectLocation = 0;
        }

        ((TextView) findViewById(R.id.letter2)).setText(mCurrentLetters.get(1).get(0));
        findViewById(R.id.letter2).setTag(mCurrentLetters.get(1));
        if(mCurrentLetters.get(1).get(3).equals("1")) {
            mCorrectLocation = 1;
        }

        ((TextView) findViewById(R.id.letter3)).setText(mCurrentLetters.get(2).get(0));
        findViewById(R.id.letter3).setTag(mCurrentLetters.get(2));
        if(mCurrentLetters.get(2).get(3).equals("1")) {
            mCorrectLocation = 2;
        }

        ((TextView) findViewById(R.id.letter4)).setText(mCurrentLetters.get(3).get(0));
        findViewById(R.id.letter4).setTag(mCurrentLetters.get(3));
        if(mCurrentLetters.get(3).get(3).equals("1")) {
            mCorrectLocation = 3;
        }

        setupFrameListens();
        setUpListeners();

        long mAudioDuration=0;
        if(roundNumber==0)
            mAudioDuration=playInstructionAudio();

        mAudioHandler.postDelayed(playCurrentWordAudio, mAudioDuration+20);
    }//end private void displayScreen(Cursor mCurrentWordsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playCurrentWordAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playCurrentWordAudio);
            mBeingValidated=false;
            if(!mCurrentWord.get(2).equals(""))
                playGeneralAudio(mCurrentWord.get(2));
        }
    };

    //end private void displayScreen(Cursor currentWordsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens(){
        findViewById(R.id.letter1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentLetters.get(0).get(4).equals("0") && !mBeingValidated){
                    ((TextView) findViewById(R.id.word0))
                            .setText(mCurrentWord.get(1).replaceFirst(mCurrentWord.get(9),
                                    mCurrentLetters.get(0).get(0)));
                    mChosenLetterLocation=0;
                    mCorrect=(mCurrentLetters.get(0).get(3).equals("1"));
                    ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_wd05_on);
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.letter2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentLetters.get(1).get(4).equals("0") && !mBeingValidated){
                    ((TextView) findViewById(R.id.word0))
                            .setText(mCurrentWord.get(1).replaceFirst(mCurrentWord.get(9),
                                    mCurrentLetters.get(1).get(0)));

                    mChosenLetterLocation=1;
                    mCorrect=(mCurrentLetters.get(1).get(3).equals("1"));
                    ((ImageView)findViewById(R.id.frame2))
                            .setImageResource(R.drawable.frm_wd05_on);
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.letter3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentLetters.get(2).get(4).equals("0") && !mBeingValidated){
                    ((TextView) findViewById(R.id.word0))
                            .setText(mCurrentWord.get(1).replaceFirst(mCurrentWord.get(9),
                                    mCurrentLetters.get(2).get(0)));

                    mChosenLetterLocation=2;
                    mCorrect=(mCurrentLetters.get(2).get(3).equals("1"));
                    ((ImageView)findViewById(R.id.frame3))
                            .setImageResource(R.drawable.frm_wd05_on);
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.letter4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentLetters.get(3).get(4).equals("0") && !mBeingValidated){
                    ((TextView) findViewById(R.id.word0))
                            .setText(mCurrentWord.get(1).replaceFirst(mCurrentWord.get(9),
                                    mCurrentLetters.get(3).get(0)));

                    mChosenLetterLocation=3;
                    mCorrect=(mCurrentLetters.get(3).get(3).equals("1"));
                    ((ImageView)findViewById(R.id.frame4))
                            .setImageResource(R.drawable.frm_wd05_on);
                    setUpFrame(3);
                }
            }
        });


    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        mValidateAvailable=true;

        switch(frameNumber) {
            default:
            case 0: {
                ((ImageView) findViewById(R.id.frame1))
                        .setImageResource(R.drawable.frm_wd05_on);
                break;
            }
            case 1: {
                ((ImageView) findViewById(R.id.frame2))
                        .setImageResource(R.drawable.frm_wd05_on);
                break;
            }
            case 2: {
                ((ImageView) findViewById(R.id.frame3))
                        .setImageResource(R.drawable.frm_wd05_on);
                break;
            }
            case 3: {
                ((ImageView) findViewById(R.id.frame4))
                        .setImageResource(R.drawable.frm_wd05_on);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        ((ImageView) findViewById(R.id.frame1)).setImageResource(R.drawable.frm_wd05_off);
        ((ImageView) findViewById(R.id.frame2)).setImageResource(R.drawable.frm_wd05_off);
        ((ImageView) findViewById(R.id.frame3)).setImageResource(R.drawable.frm_wd05_off);
        ((ImageView) findViewById(R.id.frame4)).setImageResource(R.drawable.frm_wd05_off);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
        }

        ((TextView) findViewById(R.id.letter1))
                .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter2))
                .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter3))
                .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter4))
                .setTextColor(ContextCompat.getColor(this, R.color.normalBlack));

        if(mCorrect) {
            switch (mChosenLetterLocation) {
                default:
                case 0:
                    ((TextView) findViewById(R.id.letter1))
                            .setTextColor(ContextCompat.getColor(this, R.color.correct_green));
                    break;
                case 1:
                    ((TextView) findViewById(R.id.letter2))
                            .setTextColor(ContextCompat.getColor(this, R.color.correct_green));
                    break;
                case 2:
                    ((TextView) findViewById(R.id.letter3))
                            .setTextColor(ContextCompat.getColor(this, R.color.correct_green));
                    break;
                case 3:
                    ((TextView) findViewById(R.id.letter4))
                            .setTextColor(ContextCompat.getColor(this, R.color.correct_green));
                    break;
            }
        }else{
            switch (mChosenLetterLocation) {
                default:
                case 0:
                    ((TextView) findViewById(R.id.letter1))
                            .setTextColor(ContextCompat.getColor(this, R.color.incorrect_red));
                    break;
                case 1:
                    ((TextView) findViewById(R.id.letter2))
                            .setTextColor(ContextCompat.getColor(this, R.color.incorrect_red));
                    break;
                case 2:
                    ((TextView) findViewById(R.id.letter3))
                            .setTextColor(ContextCompat.getColor(this, R.color.incorrect_red));
                    break;
                case 3:
                    ((TextView) findViewById(R.id.letter4))
                            .setTextColor(ContextCompat.getColor(this, R.color.incorrect_red));
                    break;
            }
        }
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processPointsAndView(){
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCurrentWord.get(0)
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;

        if(mCorrect){
            //processLayoutAfterGuess(true);
            //correctWordCount++;


            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentWord.get(0),
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(
                    AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            switch(mChosenLetterLocation){
                default:
                case 0:
                    // mCurrentWord.get(0).add(6, "1");

                    ((TextView) findViewById(R.id.letter1))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    ((TextView) findViewById(R.id.letter2)).setText("");
                    ((TextView) findViewById(R.id.letter3)).setText("");
                    ((TextView) findViewById(R.id.letter4)).setText("");
                    break;
                case 1:
                    //mCurrentWord.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.letter2))
                            .setTextColor(ContextCompat.getColor(this, R.color.correct_green));
                    ((TextView) findViewById(R.id.letter1)).setText("");
                    ((TextView) findViewById(R.id.letter3)).setText("");
                    ((TextView) findViewById(R.id.letter4)).setText("");
                    break;
                case 2:
                    //mCurrentWord.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.letter3))
                            .setTextColor(ContextCompat.getColor(this, R.color.correct_green));
                    ((TextView) findViewById(R.id.letter2)).setText("");
                    ((TextView) findViewById(R.id.letter1)).setText("");
                    ((TextView) findViewById(R.id.letter4)).setText("");
                    break;
                case 3:
                    //mCurrentWord.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.letter4))
                            .setTextColor(ContextCompat.getColor(this, R.color.correct_green));
                    ((TextView) findViewById(R.id.letter2)).setText("");
                    ((TextView) findViewById(R.id.letter3)).setText("");
                    ((TextView) findViewById(R.id.letter1)).setText("");
                    break;
            }


        }else{
            mIncorrectInRound++;

            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCurrentWord.get(0),
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(
                    AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        //  mCurrentWord.get(1).add(6, "1");
                        // mCurrentWord.get(2).add(6, "1");
                        //mCurrentWord.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.letter2)).setText("");
                        ((TextView) findViewById(R.id.letter3)).setText("");
                        ((TextView) findViewById(R.id.letter4)).setText("");
                        break;
                    case 1:
                        //mCurrentWord.get(0).add(6, "1");
                        //mCurrentWord.get(2).add(6, "1");
                        //mCurrentWord.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.letter1)).setText("");
                        ((TextView) findViewById(R.id.letter3)).setText("");
                        ((TextView) findViewById(R.id.letter4)).setText("");
                        break;
                    case 2:
                        //mCurrentWord.get(1).add(6, "1");
                        //mCurrentWord.get(0).add(6, "1");
                        //mCurrentWord.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.letter1)).setText("");
                        ((TextView) findViewById(R.id.letter2)).setText("");
                        ((TextView) findViewById(R.id.letter4)).setText("");
                        break;
                    case 3:
                        //mCurrentWord.get(1).add(6, "1");
                        //mCurrentWord.get(2).add(6, "1");
                        //mCurrentWord.get(0).add(6, "1");
                        ((TextView) findViewById(R.id.letter1)).setText("");
                        ((TextView) findViewById(R.id.letter2)).setText("");
                        ((TextView) findViewById(R.id.letter3)).setText("");
                        break;
                }
            }else{
                switch(mChosenLetterLocation){
                    default:
                    case 0:
                        //mCurrentWord.get(0).add(6, "1");
                        ((TextView) findViewById(R.id.letter1)).setText("");
                        break;
                    case 1:
                        //mCurrentWord.get(1).add(6, "1");
                        ((TextView) findViewById(R.id.letter2)).setText("");
                        break;
                    case 2:
                        //mCurrentWord.get(2).add(6, "1");
                        ((TextView) findViewById(R.id.letter3)).setText("");
                        break;
                    case 3:
                        //mCurrentWord.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.letter4)).setText("");
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private Runnable processGuess = new Runnable(){
        @Override
        public void run(){
            long mAudioDuration;

            switch(mProcessGuessPosition){
                case 0:
                default:
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, playCorrectOrNot(mCorrect)+10);
                    break;
                case 1:
                    processPointsAndView();
                    mAudioDuration=0;
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                case 2:
                    guessHandler.removeCallbacks(processGuess);
                    clearFrames();
                    if(mCorrect){
                        roundNumber++;
                        if(roundNumber!=(mCurrentWords.size())){
                            mValidateAvailable=false;

                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;
                            fadeInOrOutScreenInActivity(false);

                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        ((TextView) findViewById(R.id.word0))
                                .setText(Html.fromHtml(mCurrentWord.get(1).replaceFirst(
                                        mCurrentWord.get(9), "<u>&nbsp;&nbsp;&nbsp;</u>")));
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                case 3:
                    clearFrames();
                    clearActivity();
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off);
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };



    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.ALL_LOWERCASE_LETTERS:{


                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ALL_LOWERCASE_LETTERS, null, null, null, null);
                break;
            }
            case Constants.CURRENT_PHONIC_LETTERS:{
                String mSelection="LENGTH(phonics.phonic_text)<="+
                        args.getString("first_letter_length")+" " +
                        "AND phonics.phonic_text!=\""+args.getString("phonic_text")+"\" " +
                        "AND phonics.phonic_id!="+args.getString("phonic_id")+" " +
                        "AND variable_phase_levels.level_number" +
                        "<=" +mCurrentGSP.get("current_level");

                if(Integer.parseInt(mCurrentGSP.get("current_level"))<=10){
                    mSelection+="+(10-"+mCurrentGSP.get("current_level")+") ";
                }

                        //"AND phonic_grammar!="+args.getString("phonic_grammar");

                String mOrder;

                if("1".equals(args.getString("phonic_is_vowel"))){
                    mOrder="phonics.phonic_is_vowel DESC";
                }else{
                    mOrder="phonics.phonic_is_vowel ASC";
                }
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ORDERED_PHONICS,
                        null, mSelection, null, mOrder);
                break;
            }
            case Constants.CURRENT_WORDS:{

                if(mCurrentGSP.get("phase_id").equals("3")){
                    mCurrentGSP.put("phase_id","2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),"4", "1", "11",
                        mCurrentGSP.get("current_level"),"0"};

                String selection="variable_phase_levels.group_id="+mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id="+mCurrentGSP.get("phase_id")+
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
                        " AND variable_phase_levels.level_number>0 " +
                        "AND words.word_first_letter!=0 ";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_WORDS_BY_LEVEL_WITH_PHONICS,
                        projection, selection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<ArrayList<String>> mAllLowerCaseLetters;

        switch(loader.getId()) {
            
            default:
            case Constants.ALL_LOWERCASE_LETTERS:{
                mAllLowerCaseLetters=new ArrayList<>();
                int mCount=0;
                if(data.moveToFirst()){
                    do{
                        mAllLowerCaseLetters.add(new ArrayList<String>());
                        mAllLowerCaseLetters.get(mCount).add(
                                data.getString(data.getColumnIndex("letter_text")));
                        mAllLowerCaseLetters.get(mCount).add(
                                data.getString(data.getColumnIndex("audio_url")));
                        mAllLowerCaseLetters.get(mCount).add(
                                data.getString(data.getColumnIndex("image_url")));
                        mCount++;
                    }while(data.moveToNext());
                }
                Collections.shuffle(mAllLowerCaseLetters);
                getLoaderManager().restartLoader(Constants.CURRENT_WORDS, null, this);
                break;
            }
            case Constants.CURRENT_PHONIC_LETTERS:{
                mCurrentPhonics=new ArrayList<>();
                int mCount=0;
                if(data.moveToFirst()) {
                    do {
                        mCurrentPhonics.add(new ArrayList<String>());
                        mCurrentPhonics.get(mCount).add(
                                data.getString(data.getColumnIndex("phonic_id")));      //0
                        mCurrentPhonics.get(mCount).add(
                                data.getString(data.getColumnIndex("phonic_text")));    //1
                        mCurrentPhonics.get(mCount).add(
                                data.getString(data.getColumnIndex("phonic_grammar"))); //2
                        mCurrentPhonics.get(mCount).add(
                                data.getString(data.getColumnIndex("phonic_is_vowel")));//3
                        mCurrentPhonics.get(mCount).add(
                                data.getString(data.getColumnIndex("phonic_audio")));   //4
                        mCount++;
                    }while(data.moveToNext());
                }
                finishRound();
                break;
            }
            case Constants.CURRENT_WORDS:{
                processData(data);
                break;
            }
        }
        data.close();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mCurrentWords=new ArrayList<>();
        int currentWordNumber=0;
        int mNumberCorrectInARow=Constants.INA_ROW_CORRECT;
        if(mCursor.moveToFirst()){
            do {
                mCurrentWords.add(new ArrayList<String>());
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("word_id")));                 //0
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("word_text")));               //1
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("audio_url")));               //2
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("number_correct")));          //3
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("number_incorrect")));        //4
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("image_url")));               //5
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
                if(mCursor.getString(mCursor.getColumnIndex("word_first_letter")).equals("")) {
                    mCurrentWords.get(currentWordNumber).add(
                            mCursor.getString(mCursor.getColumnIndex("word_text")).substring(0,1));       //7
                }else{
                    mCurrentWords.get(currentWordNumber)
                            .add(mCursor.getString(mCursor.getColumnIndex("word_first_letter")));       //7

                }
                mCurrentWords.get(currentWordNumber).add("");                                                                   //8

                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("phonic_text")));             //9
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("phonic_grammar")));          //10
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("phonic_is_vowel")));         //11
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("phonic_audio")));         //12
                mCurrentWords.get(currentWordNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("phonic_id")));             //13


                mNumberCorrectInARow = (mNumberCorrectInARow
                        > Integer.parseInt(mCurrentWords.get(currentWordNumber).get(6)))
                        ? Integer.parseInt(mCurrentWords.get(currentWordNumber).get(6))
                        : mNumberCorrectInARow;

                currentWordNumber++;
                if (currentWordNumber >= Constants.NUMBER_VARIABLES ||
                        mCursor.isLast()) {
                    break;
                } 
            }while(mCursor.moveToNext());
        }else{
            moveBackwords();
        }



        Collections.shuffle(mCurrentWords);

        if(mCurrentWords.size()<Constants.NUMBER_VARIABLES ) {
            for (int i = 0; i < mCurrentWords.size(); i++) {
                if (!mCurrentWords.get(mCurrentWords.size() - 1)
                        .get(0).equals(mCurrentWords.get(i).get(0))) {
                    mCurrentWords.add(new ArrayList<>(mCurrentWords.get(i)));
                    if (mCurrentWords.size() >= Constants.NUMBER_VARIABLES) {
                        break;
                    }
                }
            }
        }

        



        beginRound();




        //displayScreen();

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }


    ////////////////////////////////////////////////////////////////////////////////////////////





    protected void setUpListeners(){


        findViewById(R.id.image0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mBeingValidated) {
                    mAudioHandler.postDelayed(playCurrentWordAudio, 20);
                }
            }
        });
        findViewById(R.id.word0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    mAudioHandler.postDelayed(playCurrentWordAudio, 20);
                }
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



}
