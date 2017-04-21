package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
public class ActivityWD06 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<HashMap<String,String>> mAllWords;
    private ArrayList<HashMap<String,String>> mSeparateLetters;
    private HashMap<String, String> mCurrentWord;
    private String mGuessWord;
    private String[] mLocationOfChar;
    private ArrayList<Integer> mLocationOfCharacter;

    private final static int VALIDATE_TIME_SMALLER=400;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_wd06);
        appData.addToClassOrder(17);
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        mLocationOfCharacter= new ArrayList<>();
        mLocationOfCharacter.add(0);
        mLocationOfCharacter.add(0);
        mLocationOfCharacter.add(0);
        mLocationOfCharacter.add(0);
        mLocationOfCharacter.add(0);
        mLocationOfCharacter.add(0);

        roundNumber=0;

        mLocationOfChar = new String[6];

        for(int i=0; i<=5; i++){
            mLocationOfChar[i]="";
        }
        mBeingValidated=true;
        mValidateAvailable=false;

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mInstructionAudio="info_wd06";

        ((TextView) findViewById(R.id.letter1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter4)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter5)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter6)).setTypeface(appData.getCurrentFontType());

        hideLetters();
        setUpListeners();
        setupFrameListens();


        startActivityHandler.postDelayed(startActivity, 500);
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

    private void hideLetters(){
        ((TextView) findViewById(R.id.word0)).setText("");

        ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.blank_image);

        unHideLetterText();

        ((TextView) findViewById(R.id.word0))
                .setTextColor(ContextCompat.getColor(this,R.color.regularBlack));

        findViewById(R.id.frame1).setVisibility(View.INVISIBLE);
        findViewById(R.id.frame2).setVisibility(View.INVISIBLE);
        findViewById(R.id.frame3).setVisibility(View.GONE);
        findViewById(R.id.frame4).setVisibility(View.GONE);
        findViewById(R.id.frame5).setVisibility(View.GONE);
        findViewById(R.id.frame6).setVisibility(View.GONE);
}

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void unHideLetterText(){
        findViewById(R.id.letter1).setVisibility(TextView.VISIBLE);
        findViewById(R.id.letter2).setVisibility(TextView.VISIBLE);
        findViewById(R.id.letter3).setVisibility(TextView.VISIBLE);
        findViewById(R.id.letter4).setVisibility(TextView.VISIBLE);
        findViewById(R.id.letter5).setVisibility(TextView.VISIBLE);
        findViewById(R.id.letter6).setVisibility(TextView.VISIBLE);

    }


    /////////////////////////////////////////////////////////////////////////////////////////

    private void checkIfCorrect(){
        mGuessWord=((TextView) findViewById(R.id.word0)).getText().toString();

        if(mGuessWord.length()==mCurrentWord.get("word_text").length()){
            mCorrect = mGuessWord.equals(mCurrentWord.get("word_text"));
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_on);
            mValidateAvailable=true;
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_off);
            mValidateAvailable=false;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected void validate() {
        mBeingValidated = true;
        if (mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            ((TextView) findViewById(R.id.word0))
                    .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
        } else {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            ((TextView) findViewById(R.id.word0))
                    .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
        }
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);

    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private void processPointsAndView() {
        String mWhere = " WHERE app_user_id=" + appData.getCurrentUserID()
                + " AND variable_id=" + mCurrentWord.get("word_id")
                + " AND activity_id=" + appData.getCurrentActivity().get(0);
        String[] mSelectionArgs = new String[]{
                "1",
                "0",
                mCurrentGSP.get("current_level"),
                mCurrentWord.get("word_id"),
                String.valueOf(mIncorrectInRound)};

        if (mCorrect) {
            //processLayoutAfterGuess(true);
            //correctWordCount++;

            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);

            getContentResolver().update(
                    AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, mWhere, mSelectionArgs);
        }else {
            mIncorrectInRound++;

            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            mSelectionArgs[0]="0";
            mSelectionArgs[1]="1";

            getContentResolver().update(
                    AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, mWhere, mSelectionArgs);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////

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
                    processPointsAndView();
                    mAudioDuration=0;
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    guessHandler.removeCallbacks(processGuess);
                    if(mCorrect){
                        hideLetters();
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        roundNumber++;
                        if(roundNumber<(mAllWords.size())){
                            mValidateAvailable=false;
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
                        unHideLetterText();

                        for(int i=0; i<mSeparateLetters.size(); i++) {
                            mSeparateLetters.get(i).put("guessed", "0");
                        }
                        mGuessWord="";
                        ((TextView) findViewById(R.id.word0))
                                .setTextColor(ContextCompat.getColor(ActivityWD06.this,R.color.regularBlack));
                        ((TextView) findViewById(R.id.word0)).setText("");
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                }
                case 3:{
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };

    //////////////////////////////////////////////////////////////////////////////////////////

    private Runnable allowValidate = new Runnable() {
        @Override
        public void run() {
            mBeingValidated=false;
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////


    protected void setupFrameListens() {


        findViewById(R.id.letter1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mSeparateLetters.get(0).get("guessed").equals("0") && !mBeingValidated) {
                    mBeingValidated=true;
                    mLocationOfCharacter.set(returnFirstZero(), 1);

                    mGuessWord+=mSeparateLetters.get(0).get("letter_text");
                    mLocationOfChar[mGuessWord.length()-1]="1";
                    ((TextView) findViewById(R.id.word0)).setText(mGuessWord);
                    checkIfCorrect();
                    mSeparateLetters.get(0).put("guessed","1");
                    findViewById(R.id.letter1).setVisibility(TextView.INVISIBLE);
                    validateHandler.postDelayed(allowValidate, VALIDATE_TIME_SMALLER);
                }
            }
        });

        findViewById(R.id.letter2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mSeparateLetters.get(1).get("guessed").equals("0") && !mBeingValidated) {
                    mBeingValidated=true;
                    mLocationOfCharacter.set(returnFirstZero(), 2);
                    mGuessWord+=mSeparateLetters.get(1).get("letter_text");
                    mLocationOfChar[mGuessWord.length()-1]="2";
                    ((TextView) findViewById(R.id.word0)).setText(mGuessWord);
                    checkIfCorrect();
                    mSeparateLetters.get(1).put("guessed","1");
                    findViewById(R.id.letter2).setVisibility(TextView.INVISIBLE);
                    validateHandler.postDelayed(allowValidate, VALIDATE_TIME_SMALLER);
                }
            }
        });

        findViewById(R.id.letter3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mSeparateLetters.get(2).get("guessed").equals("0") && !mBeingValidated) {
                    mBeingValidated=true;
                    mLocationOfCharacter.set(returnFirstZero(), 3);
                    mGuessWord+=mSeparateLetters.get(2).get("letter_text");
                    mLocationOfChar[mGuessWord.length()-1]="3";
                    ((TextView) findViewById(R.id.word0)).setText(mGuessWord);
                    checkIfCorrect();
                    mSeparateLetters.get(2).put("guessed","1");
                    findViewById(R.id.letter3).setVisibility(TextView.INVISIBLE);
                    validateHandler.postDelayed(allowValidate, VALIDATE_TIME_SMALLER);
                }
            }
        });

        findViewById(R.id.letter4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mSeparateLetters.get(3).get("guessed").equals("0") && !mBeingValidated) {
                    mBeingValidated=true;
                    mLocationOfCharacter.set(returnFirstZero(), 4);
                    mGuessWord+=mSeparateLetters.get(3).get("letter_text");
                    mLocationOfChar[mGuessWord.length()-1]="4";
                    ((TextView) findViewById(R.id.word0)).setText(mGuessWord);
                    checkIfCorrect();
                    mSeparateLetters.get(3).put("guessed","1");
                    findViewById(R.id.letter4).setVisibility(TextView.INVISIBLE);
                    validateHandler.postDelayed(allowValidate, VALIDATE_TIME_SMALLER);
                }
            }
        });

        findViewById(R.id.letter5).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mSeparateLetters.get(4).get("guessed").equals("0") && !mBeingValidated) {
                    mBeingValidated=true;
                    mLocationOfCharacter.set(returnFirstZero(), 5);
                    mGuessWord+=mSeparateLetters.get(4).get("letter_text");
                    mLocationOfChar[mGuessWord.length()-1]="5";
                    ((TextView) findViewById(R.id.word0)).setText(mGuessWord);
                    checkIfCorrect();
                    mSeparateLetters.get(4).put("guessed","1");
                    findViewById(R.id.letter5).setVisibility(TextView.INVISIBLE);
                    validateHandler.postDelayed(allowValidate, VALIDATE_TIME_SMALLER);
                }
            }
        });

        findViewById(R.id.letter6).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mSeparateLetters.get(5).get("guessed").equals("0") && !mBeingValidated) {
                    mBeingValidated=true;
                    mLocationOfCharacter.set(returnFirstZero(), 6);
                    mGuessWord+=mSeparateLetters.get(5).get("letter_text");
                    mLocationOfChar[mGuessWord.length()-1]="6";
                    ((TextView) findViewById(R.id.word0)).setText(mGuessWord);
                    checkIfCorrect();
                    mSeparateLetters.get(5).put("guessed","1");
                    findViewById(R.id.letter6).setVisibility(TextView.INVISIBLE);
                    validateHandler.postDelayed(allowValidate, VALIDATE_TIME_SMALLER);
                }
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private int returnFirstZero(){
        int mPosition=7;
        for(int i=0; i<6;i++){
            if(mLocationOfCharacter.get(i)==0){
                mPosition=i;
                break;
            }
        }
        return mPosition;
    }
    @Override
    protected void beginRound() {
        super.beginRound();


        for(int i=0; i<=5; i++){
            mLocationOfChar[i]="";
            mLocationOfCharacter.set(i,0);
        }

        hideLetters();
        mValidateAvailable=false;
        mCurrentWord = new HashMap<>(mAllWords.get(roundNumber));
        String[] mSeparations;
        if(mCurrentWord.get("word_separation").equals("0")
                || mCurrentWord.get("word_separation").equals("")){
            mSeparations = mCurrentWord.get("word_text").split("");

        }else {
            mSeparations = mCurrentWord.get("word_separation").split(",");
        }
        mSeparateLetters = new ArrayList<>();
        int mLetterNumber=0;
        for (String mLetter : mSeparations) {
            mSeparateLetters.add(new HashMap<String, String>());
            mSeparateLetters.get(mLetterNumber).put("letter_text", mLetter);
            mSeparateLetters.get(mLetterNumber).put("guessed","0");
            mLetterNumber++;
        }
        Collections.shuffle(mSeparateLetters);
        mGuessWord="";

        displayImage();
        displayScreen();
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void displayImage(){
        if(!mCurrentWord.get("image_url").equals("")) {

            try {
                String imageName = mCurrentWord.get("image_url").replace(".jpg", "")
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
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void displayScreen() {
        super.displayScreen();

        int mLetterNumber=1;
        for (HashMap<String,String> mLetter : mSeparateLetters){
            switch(mLetterNumber){
                default:
                case 1:{
                    ((TextView) findViewById(R.id.letter1)).setText(mLetter.get("letter_text"));
                    findViewById(R.id.frame1).setVisibility(View.VISIBLE);
                    break;
                }
                case 2:{
                    ((TextView) findViewById(R.id.letter2)).setText(mLetter.get("letter_text"));
                    findViewById(R.id.frame2).setVisibility(View.VISIBLE);
                    break;
                }
                case 3:{
                    ((TextView) findViewById(R.id.letter3)).setText(mLetter.get("letter_text"));
                    findViewById(R.id.frame3).setVisibility(View.VISIBLE);
                    break;
                }
                case 4:{
                    ((TextView) findViewById(R.id.letter4)).setText(mLetter.get("letter_text"));
                    findViewById(R.id.frame4).setVisibility(View.VISIBLE);
                    break;
                }
                case 5:{
                    ((TextView) findViewById(R.id.letter5)).setText(mLetter.get("letter_text"));
                    findViewById(R.id.frame5).setVisibility(View.VISIBLE);
                    break;
                }
                case 6:{
                    ((TextView) findViewById(R.id.letter6)).setText(mLetter.get("letter_text"));
                    findViewById(R.id.frame6).setVisibility(View.VISIBLE);
                    break;
                }
            }
            mLetterNumber++;
        }
        long mAudioDuration=0;

        if(roundNumber==0)
            mAudioDuration=playInstructionAudio();

        mAudioHandler.postDelayed(playCurrentWordAudio, mAudioDuration+20);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration = super.playInstructionAudio();
        mAudioHandler.postDelayed(playCurrentWordAudio, mAudioDuration + 20);
        return mAudioDuration;
    }


    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processData(Cursor mCursor) {
        super.processData(mCursor);
        mAllWords=new ArrayList<>();
        int mCurrentWordNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mAllWords.add(new HashMap<String, String>());
            mAllWords.get(mCurrentWordNumber).put("word_id",
                    mCursor.getString(mCursor.getColumnIndex("word_id")));
            mAllWords.get(mCurrentWordNumber).put("word_text",
                    mCursor.getString(mCursor.getColumnIndex("word_text")));
            mAllWords.get(mCurrentWordNumber).put("audio_url",
                    mCursor.getString(mCursor.getColumnIndex("audio_url")));
            mAllWords.get(mCurrentWordNumber).put("word_separation",
                    mCursor.getString(mCursor.getColumnIndex("word_separation")));
            mAllWords.get(mCurrentWordNumber).put("image_url",
                    mCursor.getString(mCursor.getColumnIndex("image_url")));

            mCurrentWordNumber++;
            if (mCurrentWordNumber == Constants.NUMBER_VARIABLES) {
                break;
            }else if(mCursor.getCount()<8 && mCurrentWordNumber>=mCursor.getCount()){
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else if(mCursor.getCount()>=8 && mCurrentWordNumber==9) {
                mCursor.moveToFirst();

            }else {
                mCursor.moveToNext();
            }
        }

        Collections.shuffle(mAllWords);
        beginRound();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playCurrentWordAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playCurrentWordAudio);
            mBeingValidated=false;
            if(!mCurrentWord.get("audio_url").equals(""))
                playGeneralAudio(mCurrentWord.get("audio_url"));
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners() {

        findViewById(R.id.image0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    mAudioHandler.postDelayed(playCurrentWordAudio, 20);
                }
            }
        });


        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mValidateAvailable && !mBeingValidated) {
                    playClickSound();
                    validate();
                }
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_WORDS:{


                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(), "4", "1", "11",
                        mCurrentGSP.get("current_level"),"0"};

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
                        " AND variable_phase_levels.level_number>0 " +
                        "AND words.word_separation != 0 " +
                        "AND words.word_separation != '' ";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_WORDS_BY_LEVEL, projection, selection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {

            default:
            case Constants.CURRENT_WORDS:{
                processData(data);
                break;
            }
        }
        data.close();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

}
