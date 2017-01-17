package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 5/9/2016.
 *
 * ActivitySDRoot
 * This CANNOT run by itself. It must be the parent for only the activities ending with LT
 * It is basically a class used to streamline similar function found in the LT activities
 *
 */
public class ActivitySDRoot extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {


    protected ArrayList<HashMap<String,String>> mCurrentPhonics;
    protected ArrayList<HashMap<String,String>> mRoundPhonics;
    protected ArrayList<HashMap<String,String>> mRoundPhonicWords;
    protected ArrayList<HashMap<String,String>> mCurrentPhonicWords;
    protected HashMap<String,String> mCorrectPhonicWord;

    protected ArrayList<HashMap<String,String>> mExtraPhonics;

    protected String mFirstPhonicIsVowel;
    
    protected String mCurrentAudio;
    
    protected int mCorrectPhonicCount;
    protected boolean mPlayPhonicAudio;

    protected  Bundle mPhonicData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void beginRound() {
        super.beginRound();
        mRoundPhonics = new ArrayList<HashMap<String, String>>();
        mRoundPhonics.add(mCurrentPhonics.get(mRoundNumber));

        mCorrectID="0";
        if (mRoundPhonics.get(0).get("is_correct").equals("1")){
            mCorrectID = mRoundPhonics.get(0).get("phonic_id");
        }
        switch (mActivityNumber){
                        default:
            case 1:
            case 2:
            case 3:
            case 4:{

                if(mRoundNumber<=mCurrentPhonics.size()) {

                    mPhonicData = new Bundle();

                    mPhonicData.putString("first_letter_length",
                            String.valueOf(mCurrentPhonics.get(0).get("phonic_text").length()));
                    mPhonicData.putString("phonic_text", mRoundPhonics.get(0).get("phonic_text"));
                    mPhonicData.putString("phonic_grammar",
                            mRoundPhonics.get(0).get("phonic_grammar"));
                    mPhonicData.putString("phonic_grammar_last",
                            mRoundPhonics.get(0).get("phonic_grammar_last"));


                    mPhonicData.putString("phonic_is_vowel",
                            mRoundPhonics.get(0).get("phonic_is_vowel"));
                    mPhonicData.putString("phonic_id",
                            mRoundPhonics.get(0).get("phonic_id"));

                    mFirstPhonicIsVowel=mPhonicData.getString("phonic_is_vowel");

                    getLoaderManager().restartLoader(Constants.CURRENT_EXTRA_PHONICS,
                            mPhonicData, this);
                }else{
                    lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                }
                break;
            }
        }

    }
    
    //////////////////////////////////////////////////////////////////////////////////////////

    protected void processExtraPhonicWords(Cursor mCursor){

        int mCount=0;
        mCurrentPhonicWords=new ArrayList<HashMap<String, String>>();

        for (mCursor.moveToFirst(); !mCursor.isAfterLast();mCursor.moveToNext()) {
            mCurrentPhonicWords.add(new HashMap<String, String>());

            mCurrentPhonicWords.get(mCount).put("phonic_word_id",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_id")));
            mCurrentPhonicWords.get(mCount).put("phonic_word_text",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_text")));
            mCurrentPhonicWords.get(mCount).put("phonic_word_audio",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_audio")));
            mCurrentPhonicWords.get(mCount).put("phonic_word_image",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_image")));
            mCurrentPhonicWords.get(mCount).put("phonic_word_first_id",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_first_id")));
            mCurrentPhonicWords.get(mCount).put("phonic_word_last_id",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_last_id")));
            mCurrentPhonicWords.get(mCount).put("phonic_text",
                    mCursor.getString(mCursor.getColumnIndex("phonic_text")));

            if(mCursor.getString(mCursor.getColumnIndex("phonic_grammar")).equals("")){
                mCurrentPhonicWords.get(mCount).put("phonic_grammar",
                        mCursor.getString(mCursor.getColumnIndex("phonic_text")));
            }else {
                mCurrentPhonicWords.get(mCount).put("phonic_grammar",
                        mCursor.getString(mCursor.getColumnIndex("phonic_grammar")));
            }

            mCurrentPhonicWords.get(mCount).put("is_correct","0");
            mCurrentPhonicWords.get(mCount).put("guessed_yet","0");

            mCount++;
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    
    protected void processSinglePhonicWord(Cursor mCursor){

        if (mCursor.moveToFirst()){
            mCorrectPhonicWord=new HashMap<String, String>();

            mCorrectPhonicWord.put("phonic_word_id",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_id")));
            mCorrectPhonicWord.put("phonic_word_text",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_text")));
            mCorrectPhonicWord.put("phonic_word_audio",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_audio")));
            mCorrectPhonicWord.put("phonic_word_image",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_image")));
            mCorrectPhonicWord.put("phonic_word_first_id",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_first_id")));
            mCorrectPhonicWord.put("phonic_word_last_id",
                    mCursor.getString(mCursor.getColumnIndex("phonic_word_last_id")));
            if(mActivityNumber!=6) {
                mCorrectPhonicWord.put("phonic_text",
                        mCursor.getString(mCursor.getColumnIndex("phonic_text")));

                if (mCursor.getString(mCursor.getColumnIndex("phonic_grammar")).equals("")) {
                    mCorrectPhonicWord.put("phonic_grammar",
                            mCursor.getString(mCursor.getColumnIndex("phonic_text")));
                } else {
                    mCorrectPhonicWord.put("phonic_grammar",
                            mCursor.getString(mCursor.getColumnIndex("phonic_grammar")));
                }
            }
            mCorrectPhonicWord.put("is_correct","1");
            mCorrectPhonicWord.put("guessed_yet","0");
            mCorrectPhonicWord.put("guessed_yet_other","0");

        }
        if(mActivityNumber==4){
            mCorrectAudio = mCorrectPhonicWord.get("phonic_word_audio");

        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    
    protected void processExtraPhonics(Cursor mCursor){
        int mCount=0;
        mExtraPhonics=new ArrayList<>();
        if(mCursor.moveToFirst()) {
            do {
                mExtraPhonics.add(new HashMap<String, String>());

                mExtraPhonics.get(mCount).put("phonic_id",
                        mCursor.getString(mCursor.getColumnIndex("phonic_id"))); 
                mExtraPhonics.get(mCount).put("phonic_text",
                        mCursor.getString(mCursor.getColumnIndex("phonic_text")));
                mExtraPhonics.get(mCount).put("phonic_audio",
                        mCursor.getString(mCursor.getColumnIndex("phonic_audio")));
                mExtraPhonics.get(mCount).put("phonic_grammar",
                        mCursor.getString(mCursor.getColumnIndex("phonic_grammar")));

                mExtraPhonics.get(mCount).put("phonic_grammar",
                        mCursor.getString(mCursor.getColumnIndex("phonic_grammar")));
                mExtraPhonics.get(mCount).put("phonic_is_vowel",
                        mCursor.getString(mCursor.getColumnIndex("phonic_is_vowel")));
                mExtraPhonics.get(mCount).put("is_correct","0");
                mExtraPhonics.get(mCount).put("guessed_yet","0");

                mCount++;
                if(mCount>7){
                    break;
                }
            }while(mCursor.moveToNext());
        }
        //Collections.shuffle(mExtraPhonics);

        mIncorrectInRound=0;
       

        int mSecondPhonicIsVowel;
        int mThirdPhonicIsVowel=0;

        mFirstPhonicIsVowel=mPhonicData.getString("phonic_is_vowel");


        switch (Integer.parseInt(mFirstPhonicIsVowel)){
            default:
            case 0:{
                mSecondPhonicIsVowel=1;
                break;
            }
            case 1:{
                mSecondPhonicIsVowel=0;
                break;
            }
            case 2:{
                mSecondPhonicIsVowel=0;
                break;
            }
            case 3:{
                mSecondPhonicIsVowel=1;
                break;
            }
            case 4:{
                mSecondPhonicIsVowel=2;
                mThirdPhonicIsVowel=0;
                break;
            }
            case 5:{
                mSecondPhonicIsVowel=3;
                mThirdPhonicIsVowel=1;
                break;
            }
            case 6:{
                mSecondPhonicIsVowel=4;
                mThirdPhonicIsVowel=2;
                break;
            }
        }
        int mTwoCount=0;
        int i=1;
        int mNumberOfPhonics;

        switch(mActivityNumber){
            default:{
                mNumberOfPhonics=4;
                break;
            }
            case 4:{
                mNumberOfPhonics=2;
                break;
            }
        }

        for(HashMap<String,String> mTempArray : mExtraPhonics){
            boolean mPlaceExtraPhonic=false;
            if(mTempArray.get("phonic_is_vowel").equals(mFirstPhonicIsVowel) && mTwoCount!=2 ){
                mPlaceExtraPhonic=true;
                mTwoCount++;
            }else if (mTempArray.get("phonic_is_vowel").equals(mSecondPhonicIsVowel) && i < 4) {
                mPlaceExtraPhonic=true;
            }else if (mTempArray.get("phonic_is_vowel").equals(mThirdPhonicIsVowel) && i < 4) {
                mPlaceExtraPhonic=true;
            }else if(i < 4){
                mPlaceExtraPhonic=true;
            }else{
                mPlaceExtraPhonic=false;
            }
            
            if(mPlaceExtraPhonic){
                mRoundPhonics.add(new HashMap<String, String>());

                mRoundPhonics.get(i).put("phonic_id",mTempArray.get("phonic_id"));
                mRoundPhonics.get(i).put("phonic_text",mTempArray.get("phonic_text"));
                mRoundPhonics.get(i).put("phonic_audio",mTempArray.get("phonic_audio"));
                mRoundPhonics.get(i).put("phonic_grammar",mTempArray.get("phonic_grammar"));
                mRoundPhonics.get(i).put("phonic_grammar_last",mTempArray.get("phonic_grammar_last"));

                mRoundPhonics.get(i).put("phonic_is_vowel",mTempArray.get("phonic_is_vowel"));
                mRoundPhonics.get(i).put("guessed_yet","0");
                mRoundPhonics.get(i).put("is_correct","0");
                i++;
            }
            if (i >= mNumberOfPhonics) {
                break;
            }
        }


        Collections.shuffle(mRoundPhonics);
        
        
        
        if(mActivityNumber!=4) {
            displayScreen();
        }
        
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    protected void processPhonicWords() {


        mRoundPhonicWords = new ArrayList<HashMap<String, String>>();

        mRoundPhonicWords.add(new HashMap<String, String>(mCorrectPhonicWord));


        Collections.shuffle(mRoundPhonicWords);

        int mCurrentCount = 1;

        for (int i = 0; i < mCurrentPhonicWords.size(); i++) {

            boolean mPhonicOkay = true;

            for (HashMap<String, String> mTemp : mRoundPhonicWords) {
                if ((mCurrentPhonicWords.get(i).get("phonic_word_id") != null &&
                        mCurrentPhonicWords.get(i).get("phonic_word_id")
                                .equals(mTemp.get("phonic_word_id"))) ||
                        (mCurrentPhonicWords.get(i).get("phonic_text") != null &&
                                mCurrentPhonicWords.get(i).get("phonic_text")
                                        .equals(mTemp.get("phonic_text"))) ||
                        (mCurrentPhonicWords.get(i).get("phonic_grammar") != null &&
                                mCurrentPhonicWords.get(i).get("phonic_grammar")
                                        .equals(mTemp.get("phonic_grammar")))) {
                    mPhonicOkay = false;
                    break;
                }
            }

            if (mPhonicOkay) {
                mRoundPhonicWords.add(new HashMap<String, String>());
                mRoundPhonicWords.get(mCurrentCount).put("phonic_word_id",
                        mCurrentPhonicWords.get(i).get("phonic_word_id")); //0
                mRoundPhonicWords.get(mCurrentCount).put("phonic_word_text",
                        mCurrentPhonicWords.get(i).get("phonic_word_text")); //1
                mRoundPhonicWords.get(mCurrentCount).put("phonic_word_audio",
                        mCurrentPhonicWords.get(i).get("phonic_word_audio")); //2
                mRoundPhonicWords.get(mCurrentCount).put("phonic_word_image",
                        mCurrentPhonicWords.get(i).get("phonic_word_image")); //3
                mRoundPhonicWords.get(mCurrentCount).put("phonic_word_first_id",
                        mCurrentPhonicWords.get(i).get("phonic_word_first_id")); //4
                mRoundPhonicWords.get(mCurrentCount).put("phonic_word_last_id",
                        mCurrentPhonicWords.get(i).get("phonic_word_last_id")); //5
                mRoundPhonicWords.get(mCurrentCount).put("is_correct", "0"); //6
                mRoundPhonicWords.get(mCurrentCount).put("guessed_yet", "0"); //7
                mRoundPhonicWords.get(mCurrentCount).put("phonic_text",
                        mCurrentPhonicWords.get(i).get("phonic_text")); //8
                mRoundPhonicWords.get(mCurrentCount).put("phonic_grammar",
                        mCurrentPhonicWords.get(i).get("phonic_grammar")); //19

                mCurrentCount++;
                if (mRoundPhonicWords.size() >= 4) {
                    break;
                }
            }
        }

        Collections.shuffle(mRoundPhonicWords);
        displayScreen();

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processData(Cursor mCursor) {
        super.processData(mCursor);
        ArrayList<HashMap<String, String>> mTempPhonics = new ArrayList<HashMap<String, String>>();


        mCurrentPhonics= new ArrayList<>();

        mCurrentWords=new ArrayList<>();
        int mCount=0;
        int mNumberOfNoCorrects=0;
        boolean mMoveToFirst=true;
        if(mCursor.moveToFirst()){
            do{
                if(Integer.parseInt(
                        mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row")))>=1) {
                    mNumberOfNoCorrects++;
                }
            }while(mCursor.moveToNext());

            mCursor.moveToFirst();
            do{
                if(mMoveToFirst){
                    mCursor.moveToFirst();
                    mMoveToFirst=false;
                }
                int mAllowed=Integer.parseInt(
                        mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row")));
                do {
                    mCurrentPhonics.add(new HashMap<String, String>());
                    mCurrentPhonics.get(mCount).put("phonic_id",
                            mCursor.getString(mCursor.getColumnIndex("phonic_id")));
                    mCurrentPhonics.get(mCount).put("phonic_text",
                            mCursor.getString(mCursor.getColumnIndex("phonic_text")));
                    mCurrentPhonics.get(mCount).put("phonic_audio",
                            mCursor.getString(mCursor.getColumnIndex("phonic_audio")));
                    mCurrentPhonics.get(mCount).put("phonic_grammar",
                            mCursor.getString(mCursor.getColumnIndex("phonic_grammar")));
                    mCurrentPhonics.get(mCount).put("phonic_grammar_last",
                            mCursor.getString(mCursor.getColumnIndex("phonic_grammar_last")));
                    mCurrentPhonics.get(mCount).put("phonic_is_vowel",
                            mCursor.getString(mCursor.getColumnIndex("phonic_is_vowel")));
                    mCurrentPhonics.get(mCount).put("is_correct","1");
                    mCurrentPhonics.get(mCount).put("guessed_yet","0");

                    mAllowed++;
                    mCount++;

                    if((mNumberOfNoCorrects<4 && mCount<Constants.NUMBER_VARIABLES)
                            || mCursor.getInt(mCursor.getColumnIndex("number_correct_in_a_row"))<2){
                        mCurrentPhonics.add(new HashMap<String, String>());
                        mCurrentPhonics.get(mCount).put("phonic_id",
                                mCursor.getString(mCursor.getColumnIndex("phonic_id")));
                        mCurrentPhonics.get(mCount).put("phonic_text",
                                mCursor.getString(mCursor.getColumnIndex("phonic_text")));
                        mCurrentPhonics.get(mCount).put("phonic_audio",
                                mCursor.getString(mCursor.getColumnIndex("phonic_audio")));
                        mCurrentPhonics.get(mCount).put("phonic_grammar",
                                mCursor.getString(mCursor.getColumnIndex("phonic_grammar")));
                        mCurrentPhonics.get(mCount).put("phonic_grammar_last",
                                mCursor.getString(mCursor.getColumnIndex("phonic_grammar_last")));
                        mCurrentPhonics.get(mCount).put("phonic_is_vowel",
                                mCursor.getString(mCursor.getColumnIndex("phonic_is_vowel")));
                        mCurrentPhonics.get(mCount).put("is_correct","1");
                        mCurrentPhonics.get(mCount).put("guessed_yet","0");

                        mAllowed++;
                        mCount++;
                    }
                }while(mAllowed<Constants.INA_ROW_CORRECT && mCount<Constants.NUMBER_VARIABLES
                        && mCursor.getCount()>Constants.NUMBER_VARIABLES);
                if (mCount >= Constants.NUMBER_VARIABLES) {
                    break;
                }else if(mCursor.isLast()){
                    mMoveToFirst=true;
                    mCursor.moveToFirst();
                }
            }while(mCursor.moveToNext());
        }
        int mNumberOfPhonicVariables=mCurrentPhonics.size();

        Collections.shuffle(mCurrentPhonics);
        if(mNumberOfNoCorrects>4) {
            String mPreviousWordId = "0";
            for (int i = 0; i < mNumberOfPhonicVariables; i++) {
                if (!mCurrentPhonics.get(i).get("phonic_id").equals(mPreviousWordId)) {
                    mTempPhonics.add(new HashMap<>(mCurrentPhonics.get(i)));
                } else {
                    if ((i + 1) < mNumberOfPhonicVariables) {
                        mTempPhonics.add(new HashMap<>(mCurrentPhonics.get(i + 1)));
                        mTempPhonics.add(new HashMap<>(mCurrentPhonics.get(i)));
                        i++;
                    } else {
                        mTempPhonics.add(new HashMap<>(mTempPhonics.get(0)));
                        mTempPhonics.set(0, mCurrentPhonics.get(i));
                    }
                }
                mPreviousWordId = mCurrentPhonics.get(i).get("phonic_id");
            }

            mCurrentPhonics = new ArrayList<>(mTempPhonics);
        }



        switch(mActivityNumber) {
            default:
                break;
            case 1:
            case 2:
            case 6:
                break;
            case 3:{
                break;
            }
        }
        beginRound();

            
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration) {
        super.processTheGuess(mAudioDuration);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////


    protected void processAfterValidationDisplay(){
        switch(mActivityNumber) {
            default:
            case 1: {
                if (!mCorrect) {
                    if (mIncorrectInRound >= 2) {
                        switch (mCorrectLocation) {
                            default:
                            case 0:
                                ((TextView) findViewById(R.id.phonic2)).setText("");
                                ((TextView) findViewById(R.id.phonic3)).setText("");
                                ((TextView) findViewById(R.id.phonic4)).setText("");
                                break;
                            case 1:
                                ((TextView) findViewById(R.id.phonic1)).setText("");
                                ((TextView) findViewById(R.id.phonic3)).setText("");
                                ((TextView) findViewById(R.id.phonic4)).setText("");
                                break;
                            case 2:
                                ((TextView) findViewById(R.id.phonic1)).setText("");
                                ((TextView) findViewById(R.id.phonic2)).setText("");
                                ((TextView) findViewById(R.id.phonic4)).setText("");
                                break;
                            case 3:
                                ((TextView) findViewById(R.id.phonic1)).setText("");
                                ((TextView) findViewById(R.id.phonic2)).setText("");
                                ((TextView) findViewById(R.id.phonic3)).setText("");
                                break;
                        }
                    } else {
                        switch (mCurrentLocation) {
                            default:
                            case 0:
                                ((TextView) findViewById(R.id.phonic1)).setText("");
                                break;
                            case 1:
                                ((TextView) findViewById(R.id.phonic2)).setText("");
                                break;
                            case 2:
                                ((TextView) findViewById(R.id.phonic3)).setText("");
                                break;
                            case 3:
                                ((TextView) findViewById(R.id.phonic4)).setText("");
                                break;
                        }
                    }

                }//end if (!mCorrect) {
                break;
            }//end case 1
            case 2:
            case 3:{
                if(!mCorrect){
                    if(mIncorrectInRound>=2){
                        switch(mCorrectLocation){
                            default:
                            case 0:
                                ((ImageView) findViewById(R.id.image2)).setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image3)).setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image4)).setImageResource(R.drawable.blank_image);
                                break;
                            case 1:
                                ((ImageView) findViewById(R.id.image1)).setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image3)).setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image4)).setImageResource(R.drawable.blank_image);
                                break;
                            case 2:
                                ((ImageView) findViewById(R.id.image1)).setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image2)).setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image4)).setImageResource(R.drawable.blank_image);
                                break;
                            case 3:
                                ((ImageView) findViewById(R.id.image1)).setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image2)).setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image3)).setImageResource(R.drawable.blank_image);
                                break;
                        }
                    }else{
                        switch(mCurrentLocation){
                            default:
                            case 0:
                                ((ImageView) findViewById(R.id.image1)).setImageResource(R.drawable.blank_image);
                                break;
                            case 1:
                                ((ImageView) findViewById(R.id.image2)).setImageResource(R.drawable.blank_image);
                                break;
                            case 2:
                                ((ImageView) findViewById(R.id.image3)).setImageResource(R.drawable.blank_image);
                                break;
                            case 3:
                                ((ImageView) findViewById(R.id.image4)).setImageResource(R.drawable.blank_image);
                                break;
                        }
                    }
                }
                break;
            }//end case 2
        }//end switch
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processValidationDisplay() {
        super.processValidationDisplay();
        switch(mActivityNumber) {
            default:
            case 1: {
                if(mCorrect){
                    if(mActivityNumber==1  || mActivityNumber==2 || mActivityNumber==3){
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_ok_mic);
                    }else {
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_ok);
                    }
                    switch(mCurrentLocation){
                        default:
                        case 0:
                            //mCurrentPhonics.get(0).put("guessed_yet", "1");

                            ((TextView) findViewById(R.id.phonic1))
                                    .setTextColor(getResources().getColor(R.color.correct_green));
                            ((TextView) findViewById(R.id.phonic2)).setText("");
                            ((TextView) findViewById(R.id.phonic3)).setText("");
                            ((TextView) findViewById(R.id.phonic4)).setText("");
                            break;
                        case 1:
                            //mCurrentPhonics.get(1).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.phonic2))
                                    .setTextColor(getResources().getColor(R.color.correct_green));
                            ((TextView) findViewById(R.id.phonic1)).setText("");
                            ((TextView) findViewById(R.id.phonic3)).setText("");
                            ((TextView) findViewById(R.id.phonic4)).setText("");
                            break;
                        case 2:
                           /// mCurrentPhonics.get(2).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.phonic3))
                                    .setTextColor(getResources().getColor(R.color.correct_green));
                            ((TextView) findViewById(R.id.phonic2)).setText("");
                            ((TextView) findViewById(R.id.phonic1)).setText("");
                            ((TextView) findViewById(R.id.phonic4)).setText("");
                            break;
                        case 3:
                            //mCurrentPhonics.get(3).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.phonic4))
                                    .setTextColor(getResources().getColor(R.color.correct_green));
                            ((TextView) findViewById(R.id.phonic2)).setText("");
                            ((TextView) findViewById(R.id.phonic3)).setText("");
                            ((TextView) findViewById(R.id.phonic1)).setText("");
                            break;
                    }


                }else {
                    mIncorrectInRound++;

                    if(mActivityNumber==1  || mActivityNumber==2 || mActivityNumber==3){
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_no_ok_mic);
                    }else {
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_no_ok);
                    }
                    if (mIncorrectInRound >= 2) {
                        switch (mCurrentLocation) {
                            default:
                            case 0:
                                mRoundPhonics.get(0).put("guessed_yet", "1");
                                ((TextView) findViewById(R.id.phonic1)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                break;
                            case 1:
                                mRoundPhonics.get(1).put("guessed_yet", "1");
                                ((TextView) findViewById(R.id.phonic2)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                break;
                            case 2:
                                mRoundPhonics.get(2).put("guessed_yet", "1");
                                ((TextView) findViewById(R.id.phonic3)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                break;
                            case 3:
                                mRoundPhonics.get(3).put("guessed_yet", "1");
                                ((TextView) findViewById(R.id.phonic4)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                break;
                        }
                        switch (mCorrectLocation) {
                            default:
                            case 0:
                                mRoundPhonics.get(1).put("guessed_yet", "1");
                                mRoundPhonics.get(2).put("guessed_yet", "1");
                                mRoundPhonics.get(3).put("guessed_yet", "1");
                                break;
                            case 1:
                                mRoundPhonics.get(0).put("guessed_yet", "1");
                                mRoundPhonics.get(2).put("guessed_yet", "1");
                                mRoundPhonics.get(3).put("guessed_yet", "1");
                                break;
                            case 2:
                                mRoundPhonics.get(0).put("guessed_yet", "1");
                                mRoundPhonics.get(1).put("guessed_yet", "1");
                                mRoundPhonics.get(3).put("guessed_yet", "1");
                                break;
                            case 3:
                                mRoundPhonics.get(0).put("guessed_yet", "1");
                                mRoundPhonics.get(1).put("guessed_yet", "1");
                                mRoundPhonics.get(2).put("guessed_yet", "1");
                                break;
                        }
                    } else {
                        switch (mCurrentLocation) {
                            default:
                            case 0:
                                mRoundPhonics.get(0).put("guessed_yet", "1");
                                ((TextView) findViewById(R.id.phonic1)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                break;
                            case 1:
                                mRoundPhonics.get(1).put("guessed_yet", "1");
                                ((TextView) findViewById(R.id.phonic2)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                break;
                            case 2:
                                mRoundPhonics.get(2).put("guessed_yet", "1");
                                ((TextView) findViewById(R.id.phonic3)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                break;
                            case 3:
                                mRoundPhonics.get(3).put("guessed_yet", "1");
                                ((TextView) findViewById(R.id.phonic4)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                break;
                        }
                    }
                }
                break;
            }
            case 2:
            case 3:{
                if(mCorrect){
                    mRoundPhonicWords.get(0).put("guessed_yet", "1");
                    mRoundPhonicWords.get(1).put("guessed_yet", "1");
                    mRoundPhonicWords.get(2).put("guessed_yet", "1");
                    mRoundPhonicWords.get(3).put("guessed_yet", "1");

                }else{
                    mIncorrectInRound++;

                    if(mIncorrectInRound>=2){
                        switch(mCorrectLocation){
                            default:
                            case 0:
                                mRoundPhonicWords.get(0).put("guessed_yet", "0");
                                mRoundPhonicWords.get(1).put("guessed_yet", "1");
                                mRoundPhonicWords.get(2).put("guessed_yet", "1");
                                mRoundPhonicWords.get(3).put("guessed_yet", "1");

                                break;
                            case 1:
                                mRoundPhonicWords.get(0).put("guessed_yet", "1");
                                mRoundPhonicWords.get(1).put("guessed_yet", "0");
                                mRoundPhonicWords.get(2).put("guessed_yet", "1");
                                mRoundPhonicWords.get(3).put("guessed_yet", "1");
                                break;
                            case 2:
                                mRoundPhonicWords.get(0).put("guessed_yet", "1");
                                mRoundPhonicWords.get(1).put("guessed_yet", "1");
                                mRoundPhonicWords.get(2).put("guessed_yet", "0");
                                mRoundPhonicWords.get(3).put("guessed_yet", "1");
                                break;
                            case 3:
                                mRoundPhonicWords.get(0).put("guessed_yet", "1");
                                mRoundPhonicWords.get(1).put("guessed_yet", "1");
                                mRoundPhonicWords.get(2).put("guessed_yet", "1");
                                mRoundPhonicWords.get(3).put("guessed_yet", "0");
                                break;
                        }

                    }else{
                        switch(mCurrentLocation){
                            default:
                            case 0:
                                mRoundPhonicWords.get(0).put("guessed_yet", "1");
                                break;
                            case 1:
                                mRoundPhonicWords.get(1).put("guessed_yet", "1");
                                break;
                            case 2:
                                mRoundPhonicWords.get(2).put("guessed_yet", "1");
                                break;
                            case 3:
                                mRoundPhonicWords.get(3).put("guessed_yet", "1");
                                break;
                        }
                    }
                }
                break;
            }
            case 4:{
                if(mCorrect){
                    switch(mCorrectLocation){
                        default:
                        case 0:
                            ((TextView) findViewById(R.id.phonic0))
                                    .setTextColor(getResources().getColor(R.color.correct_green));
                            break;
                        case 1:
                            ((TextView) findViewById(R.id.phonic1))
                                    .setTextColor(getResources().getColor(R.color.correct_green));
                            break;
                    }
                }else{
                    mIncorrectInRound++;
                    if(mIncorrectInRound>=2){
                        switch(mCorrectLocation){
                            default:
                            case 0:
                                ((TextView) findViewById(R.id.phonic0)).setTextColor(
                                        getResources().getColor(R.color.correct_green));
                                ((TextView) findViewById(R.id.phonic1)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));

                                break;
                            case 1:
                                ((TextView) findViewById(R.id.phonic0)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                ((TextView) findViewById(R.id.phonic1)).setTextColor(
                                        getResources().getColor(R.color.correct_green));
                                break;
                        }
                    }else{
                        switch(mCurrentLocation){
                            default:
                            case 0:
                                ((TextView) findViewById(R.id.phonic0)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                break;
                            case 1:
                                ((TextView) findViewById(R.id.phonic1)).setTextColor(
                                        getResources().getColor(R.color.incorrect_red));
                                break;
                        }
                    }
                }
                break;
            }//end case 4
            case 6:{
                if(mCorrect) {
                    switch (mCurrentLocation) {
                        default:
                        case 0:
                            ((ImageView) findViewById(R.id.beginningPhonicCircle))
                                    .setImageResource(R.drawable.btn_sd06_circle_right);
                            break;
                        case 1:
                            ((ImageView) findViewById(R.id.endingPhonicStar))
                                    .setImageResource(R.drawable.btn_sd06_star_right);
                            break;
                    }
                }else{
                    if(mIncorrectInRound>=2){
                        switch(mCorrectLocation){
                            default:
                            case 0:
                                mCorrectPhonicWord.put("guessed_yet_other","2");
                                ((ImageView) findViewById(R.id.beginningPhonicCircle))
                                        .setImageResource(R.drawable.btn_circle_select);
                                ((ImageView) findViewById(R.id.endingPhonicStar))
                                        .setImageResource(R.drawable.btn_sd06_star_wrong);

                                break;
                            case 1:
                                mCorrectPhonicWord.put("guessed_yet","2");
                                ((ImageView) findViewById(R.id.endingPhonicStar))
                                        .setImageResource(R.drawable.btn_star_select);
                                ((ImageView) findViewById(R.id.beginningPhonicCircle))
                                        .setImageResource(R.drawable.btn_sd06_circle_wrong);
                                break;
                        }
                    }else{
                        switch(mCurrentLocation){
                            default:
                            case 0:
                                mCorrectPhonicWord.put("guessed_yet","2");
                                ((ImageView) findViewById(R.id.beginningPhonicCircle))
                                        .setImageResource(R.drawable.btn_sd06_circle_wrong);
                                break;
                            case 1:
                                mCorrectPhonicWord.put("guessed_yet_other","2");
                                ((ImageView) findViewById(R.id.endingPhonicStar))
                                        .setImageResource(R.drawable.btn_sd06_star_wrong);
                                break;
                        }
                    }
                }
                break;
            }
        }//end switch
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void setUpListeners() {
        super.setUpListeners();


        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mValidateAvailable && !mBeingValidated){
                    playClickSound();
                    validate();
                }
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration=super.playInstructionAudio();
        mAudioHandler.postDelayed(playPhonicAudio, mAudioDuration+20);
        return mAudioDuration;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected Runnable playPhonicAudio = new Runnable() {

        @Override
        public void run() {
            mAudioHandler.removeCallbacks(playPhonicAudio);
            mBeingValidated = false;
            if (!mCorrectAudio.equals("") && mPlayPhonicAudio) {
                long mAudioDuration = playGeneralAudio(mCorrectAudio);
                if (mActivityNumber == 6) {
                    mAudioHandler.postDelayed(playPhonicWordAudio, mAudioDuration + 200);
                }
            }
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected Runnable playPhonicWordAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playPhonicAudio);
            mBeingValidated=false;
            if(!mCurrentAudio.equals("") && mPlayPhonicAudio) {
                playGeneralAudio(mCurrentAudio);
            }
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void validate() {
        super.validate();
        mBeingValidated=true;
        if(mCorrect) {
            mCorrectPhonicCount++;
            if(findViewById(R.id.btnValidate)!=null) {
                if(mActivityNumber==1 || mActivityNumber==2 || mActivityNumber==3){
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_ok_mic);
                }else {
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_ok);
                }
            }
        }else if(findViewById(R.id.btnValidate)!=null) {

            if(mActivityNumber==1 || mActivityNumber==2 || mActivityNumber==3){
                ((ImageView) findViewById(R.id.btnValidate))
                        .setImageResource(R.drawable.btn_validate_no_ok_mic);
            }else {
                ((ImageView) findViewById(R.id.btnValidate))
                        .setImageResource(R.drawable.btn_validate_no_ok);
            }
        }
        processValidationDisplay();
        if(mActivityNumber==1 || mActivityNumber==2  || mActivityNumber==3
                || mActivityNumber==4 || mActivityNumber==6) {
            addActivityPointsByLevel();
            addActivityPointsToVariablesValues();
        }

        mProcessGuessPosition=0;
        processTheGuess(10);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor mCursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
