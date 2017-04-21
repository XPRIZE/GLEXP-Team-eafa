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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.ImageView;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/26/2015.
 */
public class ActivityRS02 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{


    private String mCurrentSyllableAudio;
    private String mCurrentSyllableWordAudio;

    private String mCorrectSyllableId;


    private ArrayList<ArrayList<ArrayList<String>>> mAllActivityText;

    private int mAllSyllableWordsLocation=0;
    private int mIncorrectInRound=0;

    private ArrayList<ArrayList<String>> mAllSyllables;
    private ArrayList<ArrayList<String>> mCurrentSyllables;
    private ArrayList<ArrayList<String>> mCurrentRoundVariables;

    private ArrayList<HashMap<String,String>> mAllSyllableWords;

    private static final String mSyllableWordId="syllable_word_id";
    private static final String mSyllableWordText="syllable_word_text";
    private static final String mSyllableWordAudio="syllable_word_audio";
    private static final String mSyllableWordImage="syllable_word_image";
    private static final String mSyllableWordFirst="syllable_word_first_id";
    private static final String mSyllableWordLast="syllable_word_last_id";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_rs02);
        appData.addToClassOrder(17);
        mAllActivityText = new ArrayList<>();

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mInstructionAudio="info_rs02";
        mBeingValidated=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();

        ((TextView) findViewById(R.id.syllableText1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.syllableText2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.syllableText3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.syllableText4)).setTypeface(appData.getCurrentFontType());

        startActivityHandler.postDelayed(startActivity, 500);
    }//end public void onCreate(Bundle savedInstanceState) {

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.CURRENT_SYLLABLE_WRDS, null, this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration=super.playInstructionAudio();
        mAudioHandler.postDelayed(playSyllableWordAudio, mAudioDuration+20);
        return mAudioDuration;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){
        ((TextView) findViewById(R.id.syllableText1)).setText("");
        ((TextView) findViewById(R.id.syllableText2)).setText("");
        ((TextView) findViewById(R.id.syllableText3)).setText("");
        ((TextView) findViewById(R.id.syllableText4)).setText("");

        ((TextView) findViewById(R.id.syllableText1)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText2)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText3)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText4)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));


        ((ImageView) findViewById(R.id.syllableImage0)).setImageResource(R.drawable.blank_image);

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);

        clearFrames();

    }

 
    /////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mIncorrectInRound=0;
                
        mCurrentRoundVariables = new ArrayList<>(mAllActivityText.get(roundNumber));
        Collections.shuffle(mCurrentRoundVariables);
        
        for (int i = 0; i < 4; i++) {
            if (mCurrentRoundVariables.get(i).get(0).equals("1")) {
                mCorrectLocation = i;
                mCorrectSyllableId=mCurrentRoundVariables.get(i).get(1);
            }

            switch(i){
                default:
                case 0:{
                    ((TextView) findViewById(R.id.syllableText1)).setText(
                            mCurrentRoundVariables.get(i).get(2));

                    findViewById(R.id.syllableText1).setTag(mCurrentRoundVariables.get(i).get(6));
                    break;
                }
                case 1:{
                    ((TextView) findViewById(R.id.syllableText2)).setText(
                            mCurrentRoundVariables.get(i).get(2));

                    findViewById(R.id.syllableText2).setTag(mCurrentRoundVariables.get(i).get(6));
                    break;
                }
                case 2:{
                    ((TextView) findViewById(R.id.syllableText3)).setText(
                            mCurrentRoundVariables.get(i).get(2));

                    findViewById(R.id.syllableText3).setTag(mCurrentRoundVariables.get(i).get(6));
                    break;
                }
                case 3:{
                    ((TextView) findViewById(R.id.syllableText4)).setText(
                            mCurrentRoundVariables.get(i).get(2));

                    findViewById(R.id.syllableText4).setTag(mCurrentRoundVariables.get(i).get(6));
                    break;
                }
            }
        }

        Collections.shuffle(mAllSyllableWords);
        for(int i=0;i<mAllSyllableWords.size();i++){
            if(mAllSyllableWords.get(i).get(mSyllableWordFirst)
                    .equals(mCorrectSyllableId)){
                mCurrentSyllableWordAudio=mAllSyllableWords.get(i).get(mSyllableWordAudio);
                try{
                    String imageName=mAllSyllableWords.get(i).get(mSyllableWordImage)
                            .replace(".jpg", "").replace(".png", "").trim();
                    int resID = getResources().getIdentifier(imageName ,
                            "drawable", getPackageName());
                    ((ImageView) findViewById(R.id.syllableImage0)).setImageResource(resID);
                }catch (Exception e) {
                    ((ImageView) findViewById(R.id.syllableImage0))
                            .setImageResource(R.drawable.blank_image);
                    Log.e("MyTag", "Failure to get drawable id.", e);
                }
                break;
            }
        }


        long mAudioDuration=0;
        if(roundNumber==0)
            mAudioDuration=playInstructionAudio();

        mAudioHandler.postDelayed(playSyllableWordAudio, mAudioDuration+20);
    }//end private void displayScreen(Cursor mCurrentSyllablesCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playSyllableWordAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playSyllableWordAudio);
            mBeingValidated=false;
            if(!mCurrentSyllableWordAudio.equals(""))
                playGeneralAudio(mCurrentSyllableWordAudio);
        }
    };


    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens(){
        findViewById(R.id.syllableText1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentRoundVariables.get(0).get(6).equals("0") && !mBeingValidated){
                    mAllSyllableWordsLocation=0;
                    mCurrentSyllableAudio=mCurrentRoundVariables.get(0).get(3);
                    playGeneralAudio(mCurrentSyllableAudio);
                    mCorrect=(mCurrentRoundVariables.get(0).get(0).equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.syllableText2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentRoundVariables.get(1).get(6).equals("0") && !mBeingValidated){
                    mAllSyllableWordsLocation=1;
                    mCurrentSyllableAudio=mCurrentRoundVariables.get(1).get(3);
                    playGeneralAudio(mCurrentSyllableAudio);
                    mCorrect=(mCurrentRoundVariables.get(1).get(0).equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.syllableText3).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentRoundVariables.get(2).get(6).equals("0") && !mBeingValidated){
                    mAllSyllableWordsLocation=2;
                    mCurrentSyllableAudio=mCurrentRoundVariables.get(2).get(3);
                    playGeneralAudio(mCurrentSyllableAudio);
                    mCorrect=(mCurrentRoundVariables.get(2).get(0).equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.syllableText4).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentRoundVariables.get(3).get(6).equals("0") && !mBeingValidated){
                    mAllSyllableWordsLocation=3;
                    mCurrentSyllableAudio=mCurrentRoundVariables.get(3).get(3);
                    playGeneralAudio(mCurrentSyllableAudio);
                    mCorrect=(mCurrentRoundVariables.get(3).get(0).equals("1"));
                    setUpFrame(3);
                }
            }
        });


    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        mValidateAvailable=true;
        switch(frameNumber){
            default:
            case 0:{
                ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_rs02_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_rs02_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_rs02_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_rs02_on);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_rs02_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_rs02_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_rs02_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_rs02_off);


    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
        }

        ((TextView) findViewById(R.id.syllableText1))
                .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));

        if(mCorrect) {
            switch (mAllSyllableWordsLocation) {
                default:
                case 0:
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    break;
                case 1:
                    ((TextView) findViewById(R.id.syllableText2)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    break;
                case 2:
                    ((TextView) findViewById(R.id.syllableText3)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    break;
                case 3:
                    ((TextView) findViewById(R.id.syllableText4)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    break;
            }
        }else{
            switch (mAllSyllableWordsLocation) {
                default:
                case 0:
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
                case 1:
                    ((TextView) findViewById(R.id.syllableText2)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
                case 2:
                    ((TextView) findViewById(R.id.syllableText3)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
                case 3:
                    ((TextView) findViewById(R.id.syllableText4)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
            }
        }

        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processPointsAndView(){
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCorrectSyllableId
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        if(mCorrect){
            //processLayoutAfterGuess(true);
            //correctWordCount++;


            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCorrectSyllableId,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
            switch(mAllSyllableWordsLocation){
                default:
                case 0:
                    mCurrentRoundVariables.get(0).add(6, "1");

                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 1:
                    mCurrentRoundVariables.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText2)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 2:
                    mCurrentRoundVariables.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText3)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 3:
                    mCurrentRoundVariables.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText4)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
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
                    mCorrectSyllableId,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        mCurrentRoundVariables.get(1).add(6, "1");
                        mCurrentRoundVariables.get(2).add(6, "1");
                        mCurrentRoundVariables.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 1:
                        mCurrentRoundVariables.get(0).add(6, "1");
                        mCurrentRoundVariables.get(2).add(6, "1");
                        mCurrentRoundVariables.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 2:
                        mCurrentRoundVariables.get(1).add(6, "1");
                        mCurrentRoundVariables.get(0).add(6, "1");
                        mCurrentRoundVariables.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 3:
                        mCurrentRoundVariables.get(1).add(6, "1");
                        mCurrentRoundVariables.get(2).add(6, "1");
                        mCurrentRoundVariables.get(0).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        break;
                }
            }else{
                switch(mAllSyllableWordsLocation){
                    default:
                    case 0:
                        mCurrentRoundVariables.get(0).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        break;
                    case 1:
                        mCurrentRoundVariables.get(1).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        break;
                    case 2:
                        mCurrentRoundVariables.get(2).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        break;
                    case 3:
                        mCurrentRoundVariables.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
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
                    clearFrames();
                    if(mCorrect){
                        roundNumber++;
                        if(roundNumber!=(mAllActivityText.size())){
                            //mBeingValidated=false;
                            mValidateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;

                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
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
                    displayScreen();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };

    //////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_SYLLABLE_WRDS:{
                String selection="syllable_words.syllable_word_first_id!=0";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_SYLLABLE_WORDS,null,selection,null,null);
                break;
            }
            case Constants.CURRENT_SYLLABLE:{
                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID()};

                String mSelection="AND (SELECT COUNT(syllable_word_first_id) " +
                        "FROM syllable_words " +
                        "WHERE syllable_word_first_id=syllables.syllables_id)>0";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_SYLLABLES, projection,
                        mSelection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
                break;
            case Constants.CURRENT_SYLLABLE_WRDS:{
                mAllSyllableWords=new ArrayList<>();
                int currentPhonicWordNumber=0;

                for (data.moveToFirst(); !data.isAfterLast();data.moveToNext()){
                    mAllSyllableWords.add(new HashMap<String, String>());

                    mAllSyllableWords.get(currentPhonicWordNumber).put(mSyllableWordId,
                            data.getString(data.getColumnIndex(mSyllableWordId)));
                    mAllSyllableWords.get(currentPhonicWordNumber).put(mSyllableWordText,
                            data.getString(data.getColumnIndex(mSyllableWordText)));
                    mAllSyllableWords.get(currentPhonicWordNumber).put(mSyllableWordAudio,
                            data.getString(data.getColumnIndex(mSyllableWordAudio)));
                    mAllSyllableWords.get(currentPhonicWordNumber).put(mSyllableWordImage,
                            data.getString(data.getColumnIndex(mSyllableWordImage)));
                    mAllSyllableWords.get(currentPhonicWordNumber).put(mSyllableWordFirst,
                            data.getString(data.getColumnIndex(mSyllableWordFirst)));
                    mAllSyllableWords.get(currentPhonicWordNumber).put(mSyllableWordLast,
                            data.getString(data.getColumnIndex(mSyllableWordLast)));


                    currentPhonicWordNumber++;
                }
                getLoaderManager().restartLoader(Constants.CURRENT_SYLLABLE, null, this);
                break;
            }
            case Constants.CURRENT_SYLLABLE:{

                processData(data);
                break;
            }
        }// end switch
        data.close();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        int mCursorCount=0;
        int mNumberCorrectInARow=Constants.INA_ROW_CORRECT;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {

            if(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))!=null) {
                mCursorCount++;
            }
            mCursor.moveToNext();
        }


        int mNumberOfSyllableVariables=(mCursorCount*2>Constants.NUMBER_SYLLABLE_VARIABLES) ?
                Constants.NUMBER_SYLLABLE_VARIABLES : mCursorCount*2;

        mCurrentSyllables=new ArrayList<>();

        int mSyllableNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentGSP.put("current_level",
                    mCursor.getString(mCursor.getColumnIndex("app_user_current_level")));
            if(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))!=null) {

                mCurrentSyllables.add(new ArrayList<String>());
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("syllables_id"))); //0
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("syllables_text"))); //1
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("syllables_audio"))); //2
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("number_correct"))); //3
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("number_incorrect"))); //4
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("number_correct_in_a_row"))); //5
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("level_number"))); //6
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("syllable_sister_id"))); //7


                if(!mCurrentSyllables.get(mSyllableNumber).get(5).isEmpty()) {
                    mNumberCorrectInARow = (mNumberCorrectInARow > Integer.parseInt(
                            mCurrentSyllables.get(mSyllableNumber).get(5)))
                            ? Integer.parseInt(mCurrentSyllables.get(mSyllableNumber).get(5))
                            : mNumberCorrectInARow;
                }



                mSyllableNumber++;
            }

            if (mSyllableNumber >= mNumberOfSyllableVariables) {
                break;
            } else if (mCursor.isLast()) {
                mCursor.moveToFirst();
            } else {
                mCursor.moveToNext();
            }

        }

        Collections.shuffle(mCurrentSyllables);

        ArrayList<ArrayList<String>> mTempCurrentSyllables=new ArrayList<>();

        String mPreviousWordId="0";
        for(int i=0; i<mNumberOfSyllableVariables; i++){
            if(!mCurrentSyllables.get(i).get(0).equals(mPreviousWordId)){
                mTempCurrentSyllables.add(new ArrayList<>(mCurrentSyllables.get(i)));
            }else{
                if((i+1)<mNumberOfSyllableVariables){
                    mTempCurrentSyllables.add(new ArrayList<>(mCurrentSyllables.get(i+1)));
                    mTempCurrentSyllables.add(new ArrayList<>(mCurrentSyllables.get(i)));
                    i++;
                }else{
                    mTempCurrentSyllables.add(new ArrayList<>(mTempCurrentSyllables.get(0)));
                    mTempCurrentSyllables.set(0,mCurrentSyllables.get(i));
                }
            }
            mPreviousWordId=mCurrentSyllables.get(i).get(0);
        }

        mCurrentSyllables=new ArrayList<>(mTempCurrentSyllables);

        mAllSyllables=new ArrayList<>();
        mSyllableNumber=0;
        mCursor.moveToFirst();

        while (!mCursor.isAfterLast()) {
            mAllSyllables.add(new ArrayList<String>());
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("syllables_id"))); //0
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("syllables_text"))); //1
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("syllables_audio"))); //2
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("number_correct"))); //3
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("number_incorrect"))); //4
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("level_number"))); //6
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("syllable_sister_id"))); //7

            mSyllableNumber++;
            mCursor.moveToNext();
        }
        
        shuffleGameData();
        displayScreen();


    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    private void shuffleGameData(){
        mAllActivityText.clear();
        ArrayList<ArrayList<String>> mTempSyllables=new ArrayList<>(mCurrentSyllables);

        for(int mCount=0; mCount<mCurrentSyllables.size();mCount++){
            mAllActivityText.add(new ArrayList<ArrayList<String>>());
            mAllActivityText.get(mCount).add(new ArrayList<String>());
            mAllActivityText.get(mCount).get(0).add("1"); //0


            mAllActivityText.get(mCount).get(0).add(
                    mCurrentSyllables.get(mCount).get(0)); //syllables_id 1
            mAllActivityText.get(mCount).get(0).add(
                    mCurrentSyllables.get(mCount).get(1)); //syllables_text 2
            mAllActivityText.get(mCount).get(0).add(
                    mCurrentSyllables.get(mCount).get(2)); //syllables_audio 3
            mAllActivityText.get(mCount).get(0).add(
                    mCurrentSyllables.get(mCount).get(3)); //number correct 4
            mAllActivityText.get(mCount).get(0).add(
                    mCurrentSyllables.get(mCount).get(4)); //number incorrect 5
            mAllActivityText.get(mCount).get(0).add("0"); //guess yet 6
            mAllActivityText.get(mCount).get(0).add(
                    mCurrentSyllables.get(mCount).get(7)); //syllable_sister_id 7

            Collections.shuffle(mTempSyllables);
            int mSubCount=1;
            for(int i=0;i<mTempSyllables.size();i++){
                if(!mTempSyllables.get(i).get(0).equals(mCurrentSyllables.get(mCount).get(0)) &&
                        mTempSyllables.get(i).get(6).equals(mCurrentSyllables.get(mCount).get(6))){
                    mAllActivityText.get(mCount).add(new ArrayList<String>());
                    mAllActivityText.get(mCount).get(mSubCount).add("0");

                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempSyllables.get(i).get(0)); //phonic_id
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempSyllables.get(i).get(1)); //phonic_text
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempSyllables.get(i).get(2)); //phonic_audio
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempSyllables.get(i).get(3)); //number correct
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempSyllables.get(i).get(4)); //number incorrect
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add("0"); //guessed yet
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mCurrentSyllables.get(mCount).get(7)); //syllable_sister_id 7

                    mSubCount++;
                    if((mSubCount==3 && mCurrentSyllables.size()>=12)
                            ||(mSubCount==2
                            && mCurrentSyllables.size()<12)) {
                        break;
                    }
                }
            }

            Collections.shuffle(mAllSyllables);

            for(int mDistCount=0;mDistCount<mAllSyllables.size(); mDistCount++){
                if(!mAllSyllables.get(mDistCount).get(0)
                        .equals(mCurrentSyllables.get(mCount).get(0)) ){

                    boolean mPhonicOkay=true;
                    for(ArrayList mTemp : mAllActivityText.get(mCount) ) {
                        if (mAllSyllables.get(mDistCount).get(0).equals(mTemp.get(1)) ||
                                mAllSyllables.get(mDistCount).get(0).equals(mTemp.get(7)) ) {
                            Log.d(Constants.LOGCAT,"FALSE");
                            mPhonicOkay=false;

                        }
                    }
                    if(mPhonicOkay) {
                        mAllActivityText.get(mCount).add(new ArrayList<String>());
                        mAllActivityText.get(mCount).get(mSubCount).add("0");

                        mAllActivityText.get(mCount).get(mSubCount).add(mAllSyllables.get(mDistCount).get(0)); //syllables_id
                        mAllActivityText.get(mCount).get(mSubCount).add(mAllSyllables.get(mDistCount).get(1)); //syllables_text
                        mAllActivityText.get(mCount).get(mSubCount).add(mAllSyllables.get(mDistCount).get(2)); //syllables_audio
                        mAllActivityText.get(mCount).get(mSubCount).add(mAllSyllables.get(mDistCount).get(3)); //number correct
                        mAllActivityText.get(mCount).get(mSubCount).add(mAllSyllables.get(mDistCount).get(4)); //number incorrect
                        mAllActivityText.get(mCount).get(mSubCount).add("0"); //guessed yet
                        mAllActivityText.get(mCount).get(mSubCount).add(mCurrentSyllables.get(mCount).get(7)); //syllable_sister_id 7

                        mSubCount++;
                    }
                }
                if(mSubCount>=4){
                    break;
                }
            }
        }//end for(int mCount=0; mCount<mCurrentSyllables.size();mCount++){

        for(int i=0; i<mAllActivityText.size(); i++){
            Collections.shuffle(mAllActivityText.get(i));
        }
    }//end private void shuffleGameData(){
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////




    protected void setUpListeners(){
        findViewById(R.id.syllableImage0).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mBeingValidated) {
                    mAudioHandler.postDelayed(playSyllableWordAudio, 20);
                }
            }
        });





        findViewById(R.id.btnValidate).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mValidateAvailable && !mBeingValidated){
                    playClickSound();
                    validate();
                }
            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

}
