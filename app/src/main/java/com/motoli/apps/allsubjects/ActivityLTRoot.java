package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
 * on 4/30/2016.
 *
 * ActivityLTRoot
 * This CANNOT run by itself. It must be the parent for only the activities ending with LT
 * It is basically a class used to streamline similar function found in the LT activities
 *
 */
public class ActivityLTRoot extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {

    protected ArrayList<HashMap<String,String>> mCurrentLetters;
    protected ArrayList<HashMap<String,String>> mRoundLetters;
    protected ArrayList<HashMap<String,String>> mExtraLetters;


    protected String mCorrectLetterText;
    protected String mCurrentLetterAudio;

    /*******************************
     * mLetterStatus and mChosenImages  used in LT03 and LT04
     */
    protected ArrayList<String> mLetterStatus;
    protected ArrayList<String> mChosenImages;

    protected boolean mValidateButton;
    protected boolean mPlayLetterAudio;


    protected int mCorrectLetterCount=0;

    /**
     *
     * @param mCursor Data brought from database
     */
    @Override
    protected void processData(Cursor mCursor){
        mCurrentLetters= new ArrayList<>();

        mCurrentWords=new ArrayList<>();
        int mCount=0;
        if(mCursor.moveToFirst()){
            do{
                int mAllowed=0;
                if(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))!=null) {
                    mAllowed = Integer.parseInt(
                            mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row")));
                }
                do {
                    mCurrentLetters.add(new HashMap<String, String>());
                    mCurrentLetters.get(mCount).put("letter_id",
                            mCursor.getString(mCursor.getColumnIndex("letter_id")));
                    mCurrentLetters.get(mCount).put("letter_text",
                            mCursor.getString(mCursor.getColumnIndex("letter_text")));
                    mCurrentLetters.get(mCount).put("audio_url",
                            mCursor.getString(mCursor.getColumnIndex("audio_url")));
                    mCurrentLetters.get(mCount).put("image_url",
                            mCursor.getString(mCursor.getColumnIndex("image_url")));
                    mAllowed++;
                    mCount++;
                }while(mAllowed<Constants.INA_ROW_CORRECT );
                if (mCount >= Constants.NUMBER_VARIABLES) {
                    break;
                }else if(mCursor.isLast()){
                    mCursor.moveToFirst();
                }
            }while(mCursor.moveToNext());
        }

        Collections.shuffle(mCurrentLetters);

        ArrayList<HashMap<String, String>> mTempLetters = new ArrayList<>();
        String mPreviousID = "0";

        switch(mActivityNumber) {
            default:
            case 1:
            case 2:
            case 6:{
                if(mCurrentLetters.size()>=8) {
                    for (int i = 0; i < Constants.NUMBER_VARIABLES; i++) {
                        if (!mCurrentLetters.get(i).get("letter_id").equals(mPreviousID)) {
                            mTempLetters.add(new HashMap<>(mCurrentLetters.get(i)));
                        } else {
                            if ((i + 1) < Constants.NUMBER_VARIABLES) {
                                mTempLetters.add(
                                        new HashMap<>(mCurrentLetters.get(i + 1)));
                                mTempLetters.add(
                                        new HashMap<>(mCurrentLetters.get(i)));
                                i++;
                            } else {
                                mTempLetters.add(new HashMap<>(mTempLetters.get(0)));
                                mTempLetters.set(0, mCurrentLetters.get(i));
                            }
                        }
                        mPreviousID = mCurrentLetters.get(i).get("letter_id");
                    }

                    mCurrentLetters = new ArrayList<>(mTempLetters);
                }
                mExtraLetters = new ArrayList<>();
                mCount = 0;
                if (mCursor.moveToFirst()) {
                    do {
                        mExtraLetters.add(new HashMap<String, String>());
                        mExtraLetters.get(mCount).put("letter_id",
                                mCursor.getString(mCursor.getColumnIndex("letter_id")));
                        mExtraLetters.get(mCount).put("letter_text",
                                mCursor.getString(mCursor.getColumnIndex("letter_text")));
                        mExtraLetters.get(mCount).put("audio_url",
                                mCursor.getString(mCursor.getColumnIndex("audio_url")));
                        mExtraLetters.get(mCount).put("image_url",
                                mCursor.getString(mCursor.getColumnIndex("image_url")));
                        mCount++;
                    } while (mCursor.moveToNext());
                }

                Collections.shuffle(mExtraLetters);
                break;
            }
            case 4:{
                ArrayList<ArrayList<HashMap<String,String>>> mDividedLetters
                        =new ArrayList<>();
                mDividedLetters.add(new ArrayList<HashMap<String,String>>());
                mDividedLetters.add(new ArrayList<HashMap<String,String>>());

                boolean mFirstRound = true;

                do{
                    for(HashMap<String,String> mIndividualLetters : mCurrentLetters) {
                        boolean mNoMatch=true;

                        if(mFirstRound) {
                            if (mDividedLetters.get(0).size() == 0) {
                                mDividedLetters.get(0).add(new HashMap<>(mIndividualLetters));
                            } else {

                                if (mDividedLetters.get(0).size() < 6) {
                                    for (HashMap<String, String> mTemp : mDividedLetters.get(0)) {
                                        if (mTemp.get("letter_id")
                                                .equals(mIndividualLetters.get("letter_id"))) {
                                            mNoMatch = false;
                                            break;
                                        }
                                    }
                                    if (mNoMatch) {
                                        mDividedLetters.get(0).add(
                                                new HashMap<>(mIndividualLetters));
                                        if (mDividedLetters.get(0).size() == 6) {
                                            mFirstRound = false;
                                        }
                                    } else {
                                        mDividedLetters.get(1).add(
                                                new HashMap<>(mIndividualLetters));
                                        if (mDividedLetters.get(1).size() == 6) {
                                            break;
                                        }
                                    }
                                } else {
                                    for (HashMap<String, String> mTemp : mDividedLetters.get(1)) {
                                        if (mTemp.get("letter_id")
                                                .equals(mIndividualLetters.get("letter_id"))) {
                                            mNoMatch = false;
                                            break;
                                        }
                                    }
                                    if (mNoMatch) {
                                        mDividedLetters.get(1).add(
                                                new HashMap<>(mIndividualLetters));
                                        if (mDividedLetters.get(1).size() == 6) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }else{
                            if (mDividedLetters.get(1).size() == 0) {
                                mDividedLetters.get(1).add(new HashMap<>(mIndividualLetters));
                            }else{
                                for (HashMap<String, String> mTemp : mDividedLetters.get(1)) {
                                    if (mTemp.get("letter_id")
                                            .equals(mIndividualLetters.get("letter_id"))) {
                                        mNoMatch = false;
                                        break;
                                    }
                                }
                                if (mNoMatch) {
                                    mDividedLetters.get(1).add(
                                            new HashMap<>(mIndividualLetters));
                                    if (mDividedLetters.get(1).size() == 6) {
                                        break;
                                    }
                                }
                            }


                        }
                    }

                }while(mDividedLetters.get(0).size()<6 || mDividedLetters.get(1).size()<6);

                mCurrentLetters = new ArrayList<>(mDividedLetters.get(0));
                mCurrentLetters.addAll(mDividedLetters.get(1));

                break;
            }
            case 3: {
                ArrayList<ArrayList<HashMap<String,String>>> mDividedLetters
                        =new ArrayList<>();
                mDividedLetters.add(new ArrayList<HashMap<String,String>>());
                mDividedLetters.add(new ArrayList<HashMap<String,String>>());

                int mLetterCount=0;
                do{
                    for(HashMap<String,String> mIndividualLetters : mCurrentLetters) {


                        if (mDividedLetters.get(0).size() == 0) {
                            mDividedLetters.get(0).add(new HashMap<>(mIndividualLetters));
                        } else {
                            boolean mFirstRound = true;
                            if(mDividedLetters.get(0).size()<3) {
                                for (HashMap<String, String> mTemp : mDividedLetters.get(0)) {
                                    if (mTemp.get("letter_id")
                                            .equals(mIndividualLetters.get("letter_id"))) {
                                        mFirstRound = false;
                                        break;
                                    }
                                }
                            }else{
                                mFirstRound=false;
                            }
                            if (mFirstRound) {
                                mDividedLetters.get(0).add(new HashMap<>(mIndividualLetters));
                            } else {
                                boolean mSecondRound = true;
                                if(mDividedLetters.get(1).size()<3) {
                                    for (HashMap<String, String> mTemp : mDividedLetters.get(1)) {
                                        if (mTemp.get("letter_id")
                                                .equals(mIndividualLetters.get("letter_id"))) {
                                            mSecondRound = false;
                                            break;
                                        }
                                    }

                                    if (mSecondRound) {
                                        mDividedLetters.get(1).add(new HashMap<>(mIndividualLetters));
                                    }
                                }
                            }
                        }
                    }

                }while(mDividedLetters.get(0).size()<3 || mDividedLetters.get(1).size()<3);

                mCurrentLetters = new ArrayList<>(mDividedLetters.get(0));
                mCurrentLetters.addAll(mDividedLetters.get(1));
                
                break;
            }
        }//end switch(mActivityNumber)
        beginRound();
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////

    protected Runnable playLetterAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playLetterAudio);
            mBeingValidated=false;
            if(mCorrectAudio!=null && !mCorrectAudio.equals("") && mPlayLetterAudio)
                playGeneralAudio(mCorrectAudio);
        }
    };

    /////////////////////////////////////////////////////////////////////////////////////

    /***********************************************************************************
     * processValidationDisplay()
     * This is what displays the response on the screen. It is is that same for
     * a few different LT activities, but LT03 and LT04 are different but  to each other
     * due to the matching concept. This code in a switch defines the different styles.
     *
     * THIS CODE MAYBE USED EVEN AT AN EARLIER PARENT
     */
    @Override
    protected void processValidationDisplay(){

        switch(mActivityNumber) {
            default:
            case 1:
            case 2:
            case 6:{
                if (mCorrect) {
                    switch (mCurrentLocation) {
                        default:
                        case 0:
                            mRoundLetters.get(0).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.letter1))
                                    .setTextColor(ContextCompat.getColor(this,
                                            R.color.correct_green));
                            break;
                        case 1:
                            mRoundLetters.get(1).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.letter2))
                                    .setTextColor(ContextCompat.getColor(this,
                                            R.color.correct_green));
                            break;
                        case 2:
                            mRoundLetters.get(2).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.letter3))
                                    .setTextColor(ContextCompat.getColor(this,
                                            R.color.correct_green));
                            break;
                        case 3:
                            mRoundLetters.get(3).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.letter4))
                                    .setTextColor(ContextCompat.getColor(this,
                                            R.color.correct_green));
                            break;
                    }

                } else {
                    mIncorrectInRound++;
                    if (mIncorrectInRound >= 2) {
                        switch (mCorrectLocation) {
                            default:
                            case 0:
                                mRoundLetters.get(1).put("guessed_yet", "1");
                                mRoundLetters.get(2).put("guessed_yet", "1");
                                mRoundLetters.get(3).put("guessed_yet", "1");
                                break;
                            case 1:
                                mRoundLetters.get(0).put("guessed_yet", "1");
                                mRoundLetters.get(2).put("guessed_yet", "1");
                                mRoundLetters.get(3).put("guessed_yet", "1");
                                break;
                            case 2:
                                mRoundLetters.get(1).put("guessed_yet", "1");
                                mRoundLetters.get(0).put("guessed_yet", "1");
                                mRoundLetters.get(3).put("guessed_yet", "1");
                                break;
                            case 3:
                                mRoundLetters.get(1).put("guessed_yet", "1");
                                mRoundLetters.get(2).put("guessed_yet", "1");
                                mRoundLetters.get(0).put("guessed_yet", "1");
                                break;
                        }
                    }
                    switch (mCurrentLocation) {
                        default:
                        case 0:
                            mRoundLetters.get(0).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.letter1))
                                    .setTextColor(ContextCompat.getColor(this,
                                            R.color.incorrect_red));
                            break;
                        case 1:
                            mRoundLetters.get(1).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.letter2))
                                    .setTextColor(ContextCompat.getColor(this,
                                            R.color.incorrect_red));
                            break;
                        case 2:
                            mRoundLetters.get(2).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.letter3))
                                    .setTextColor(ContextCompat.getColor(this,
                                            R.color.incorrect_red));
                            break;
                        case 3:
                            mRoundLetters.get(3).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.letter4))
                                    .setTextColor(ContextCompat.getColor(this,
                                            R.color.incorrect_red));
                            break;
                    }
                }
                break;
            }
            case 3:
            case 4:{
                int mEnd=2;
                if(mActivityNumber==3) {
                    mEnd=2;
                }
                for(int i=0;i<mEnd;i++){
                    if(mActivityNumber==3) {
                        mCurrentLocation = Integer.valueOf(mChosenImages.get(i));
                    }
                    switch(mCurrentLocation){
                        default:
                        case 0:{
                            if(mCorrect){
                                mLetterStatus.set(0, "2");
                                ((TextView) findViewById(R.id.letter1)).setTextColor(
                                        ContextCompat.getColor(this, R.color.correct_green));
                                findViewById(R.id.letterBox1).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_correct));
                            }else{
                                mLetterStatus.set(0, "1");
                                ((TextView) findViewById(R.id.letter1)).setTextColor(
                                        ContextCompat.getColor(this, R.color.incorrect_red));

                                ( findViewById(R.id.letterBox1)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_incorrect));
                            }
                            break;
                        }
                        case 1:{
                            if(mCorrect){
                                mLetterStatus.set(1, "2");
                                ((TextView) findViewById(R.id.letter2)).setTextColor(
                                        ContextCompat.getColor(this, R.color.correct_green));
                                ( findViewById(R.id.letterBox2)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_correct));
                            }else{
                                mLetterStatus.set(1, "1");
                                ((TextView) findViewById(R.id.letter2)).setTextColor(
                                        ContextCompat.getColor(this, R.color.incorrect_red));

                                ( findViewById(R.id.letterBox2)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_incorrect));
                            }
                            break;
                        }
                        case 2:{
                            if(mCorrect){
                                mLetterStatus.set(2, "2");
                                ((TextView) findViewById(R.id.letter3)).setTextColor(
                                        ContextCompat.getColor(this, R.color.correct_green));
                                ( findViewById(R.id.letterBox3)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_correct));
                            }else{
                                mLetterStatus.set(2, "1");
                                ((TextView) findViewById(R.id.letter3)).setTextColor(
                                        ContextCompat.getColor(this, R.color.incorrect_red));

                                ( findViewById(R.id.letterBox3)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_incorrect));
                            }
                            break;
                        }
                        case 3:{
                            if(mCorrect){
                                mLetterStatus.set(3, "2");
                                ((TextView) findViewById(R.id.letter4)).setTextColor(
                                        ContextCompat.getColor(this, R.color.correct_green));
                                ( findViewById(R.id.letterBox4)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_correct));
                            }else{
                                mLetterStatus.set(3, "1");
                                ((TextView) findViewById(R.id.letter4)).setTextColor(
                                        ContextCompat.getColor(this, R.color.incorrect_red));

                                ( findViewById(R.id.letterBox4)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_incorrect));
                            }
                            break;
                        }
                        case 4:{
                            if(mCorrect){
                                mLetterStatus.set(4, "2");
                                ((TextView) findViewById(R.id.letter5)).setTextColor(
                                        ContextCompat.getColor(this, R.color.correct_green));
                                ( findViewById(R.id.letterBox5)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_correct));
                            }else{
                                mLetterStatus.set(4, "1");
                                ((TextView) findViewById(R.id.letter5)).setTextColor(
                                        ContextCompat.getColor(this, R.color.incorrect_red));

                                ( findViewById(R.id.letterBox5)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_incorrect));
                            }
                            break;
                        }
                        case 5:{
                            if(mCorrect){
                                mLetterStatus.set(5, "2");
                                ((TextView) findViewById(R.id.letter6)).setTextColor(
                                        ContextCompat.getColor(this, R.color.correct_green));
                                ( findViewById(R.id.letterBox6)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_correct));
                            }else{
                                mLetterStatus.set(5, "1");
                                ((TextView) findViewById(R.id.letter6)).setTextColor(
                                        ContextCompat.getColor(this, R.color.incorrect_red));

                                (findViewById(R.id.letterBox6)).setBackground(
                                        ContextCompat.getDrawable(this,
                                                R.drawable.rounded_text_incorrect));
                            }
                            break;
                        }
                    }//end switch
                }
                break; //end of case 3:
            }
            /*
            case 6:{
                if(mCorrect){

                    if(mActivityNumber==4){
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_no_ok_bottom_space);
                    }else {
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_ok);
                    }
                    switch(mCurrentLocation){
                        default:
                        case 0:
                            mRoundLetters.get(0).put("guessed_yet", "1");
                            findViewById(R.id.image1)
                                    .setBackgroundResource(R.drawable.rounded_border_correct);
                            findViewById(R.id.image4)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            findViewById(R.id.image2)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            findViewById(R.id.image3)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            ((ImageView) findViewById(R.id.image4))
                                    .setImageResource(R.drawable.blank_image);

                            ((ImageView) findViewById(R.id.image2))
                                    .setImageResource(R.drawable.blank_image);
                            ((ImageView) findViewById(R.id.image3))
                                    .setImageResource(R.drawable.blank_image);
                            break;
                        case 1:
                            mRoundLetters.get(1).put("guessed_yet", "1");
                            findViewById(R.id.image2)
                                    .setBackgroundResource(R.drawable.rounded_border_correct);
                            findViewById(R.id.image1)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            findViewById(R.id.image4)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            findViewById(R.id.image3)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            ((ImageView) findViewById(R.id.image1))
                                    .setImageResource(R.drawable.blank_image);
                            ((ImageView) findViewById(R.id.image4))
                                    .setImageResource(R.drawable.blank_image);
                            ((ImageView) findViewById(R.id.image3))
                                    .setImageResource(R.drawable.blank_image);
                            break;
                        case 2:
                            mRoundLetters.get(2).put("guessed_yet", "1");
                            findViewById(R.id.image3)
                                    .setBackgroundResource(R.drawable.rounded_border_correct);
                            findViewById(R.id.image1)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            findViewById(R.id.image2)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            findViewById(R.id.image4)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            ((ImageView) findViewById(R.id.image1))
                                    .setImageResource(R.drawable.blank_image);
                            ((ImageView) findViewById(R.id.image2))
                                    .setImageResource(R.drawable.blank_image);
                            ((ImageView) findViewById(R.id.image4))
                                    .setImageResource(R.drawable.blank_image);
                            break;
                        case 3:
                            mRoundLetters.get(3).put("guessed_yet", "1");
                            findViewById(R.id.image4)
                                    .setBackgroundResource(R.drawable.rounded_border_correct);
                            findViewById(R.id.image1)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            findViewById(R.id.image2)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            findViewById(R.id.image3)
                                    .setBackgroundResource(R.drawable.rounded_border_unchosen);
                            ((ImageView) findViewById(R.id.image1))
                                    .setImageResource(R.drawable.blank_image);
                            ((ImageView) findViewById(R.id.image2))
                                    .setImageResource(R.drawable.blank_image);
                            ((ImageView)findViewById(R.id.image3))
                                    .setImageResource(R.drawable.blank_image);
                            break;
                    }


                }else{
                    mIncorrectInRound++;

                    if(mIncorrectInRound>=2){
                        switch(mCorrectLocation){
                            default:
                            case 0:
                                mRoundLetters.get(1).put("guessed_yet", "1");
                                mRoundLetters.get(2).put("guessed_yet", "1");
                                mRoundLetters.get(3).put("guessed_yet", "1");
                                ((ImageView) findViewById(R.id.image4))
                                        .setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image2))
                                        .setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image3))
                                        .setImageResource(R.drawable.blank_image);
                                break;
                            case 1:
                                mRoundLetters.get(0).put("guessed_yet", "1");
                                mRoundLetters.get(2).put("guessed_yet", "1");
                                mRoundLetters.get(3).put("guessed_yet", "1");
                                ((ImageView) findViewById(R.id.image1))
                                        .setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image4))
                                        .setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image3))
                                        .setImageResource(R.drawable.blank_image);
                                break;
                            case 2:
                                mRoundLetters.get(1).put("guessed_yet", "1");
                                mRoundLetters.get(0).put("guessed_yet", "1");
                                mRoundLetters.get(3).put("guessed_yet", "1");
                                ((ImageView) findViewById(R.id.image1))
                                        .setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image2))
                                        .setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image4))
                                        .setImageResource(R.drawable.blank_image);
                                break;
                            case 3:
                                mRoundLetters.get(1).put("guessed_yet", "1");
                                mRoundLetters.get(2).put("guessed_yet", "1");
                                mRoundLetters.get(0).put("guessed_yet", "1");
                                ((ImageView) findViewById(R.id.image1))
                                        .setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image2))
                                        .setImageResource(R.drawable.blank_image);
                                ((ImageView) findViewById(R.id.image3))
                                        .setImageResource(R.drawable.blank_image);
                                break;
                        }
                    }else{
                        switch(mCurrentLocation){
                            default:
                            case 0:
                                mRoundLetters.get(0).put("guessed_yet", "1");
                                ((ImageView) findViewById(R.id.image1))
                                        .setImageResource(R.drawable.blank_image);
                                break;
                            case 1:
                                mRoundLetters.get(1).put("guessed_yet", "1");
                                ((ImageView) findViewById(R.id.image2))
                                        .setImageResource(R.drawable.blank_image);
                                break;
                            case 2:
                                mRoundLetters.get(2).put("guessed_yet", "1");
                                ((ImageView) findViewById(R.id.image3))
                                        .setImageResource(R.drawable.blank_image);
                                break;
                            case 3:
                                mRoundLetters.get(3).put("guessed_yet", "1");
                                ((ImageView) findViewById(R.id.image4))
                                        .setImageResource(R.drawable.blank_image);
                                break;
                        }
                    }

                }

            }*/
        }
    }


    protected long playInstructionAudio(){
        long mAudioDuration= super.playInstructionAudio();
        mAudioHandler.postDelayed(playLetterAudio, mAudioDuration+10);
        return mAudioDuration;
    }


    //////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void setUpListeners() {

        if(mActivityNumber==1 || mActivityNumber==2 || mActivityNumber==4 || mActivityNumber==6) {
            findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mValidateAvailable && !mBeingValidated) {
                        playClickSound();
                        validate();
                    }
                }
            });
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void validate(){
        super.validate();
        mBeingValidated=true;
        if(mCorrect) {
            mCorrectLetterCount++;
            if(mValidateButton) {
                if(mActivityNumber==4 || mActivityNumber==1) {
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_ok_mic);
                }else{
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_ok);
                }
            }
        }else if(mValidateButton) {
            if(mActivityNumber==4 || mActivityNumber==1) {
                ((ImageView) findViewById(R.id.btnValidate))
                        .setImageResource(R.drawable.btn_validate_no_ok_mic);
            }else{
                ((ImageView) findViewById(R.id.btnValidate))
                        .setImageResource(R.drawable.btn_validate_no_ok);
            }
        }
        mProcessGuessPosition=0;
        processTheGuess(5);
        processValidationDisplay();
        if(mActivityNumber==1 || mActivityNumber==2 || mActivityNumber==4 || mActivityNumber==6) {
            addActivityPoints();
            //addActivityPointsByLevel();
            placeVariableAttempts();
        }


    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){ }

    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void beginRound(){
        if(mActivityNumber==1 || mActivityNumber==2 || mActivityNumber==6) {

            mRoundLetters = new ArrayList<>();
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundLetters.get(mRoundLetters.size() - 1).put("guessed_yet", "0");
            mRoundLetters.get(mRoundLetters.size() - 1).put("is_correct", "1");
            mCurrentLetterAudio=mCurrentLetters.get(mRoundNumber).get("audio_url");

            mCorrectID = mCurrentLetters.get(mRoundNumber).get("letter_id");
            int mCount = 0;

            Log.d(Constants.LOGCAT, "mExtraLetters size"+String.valueOf(mExtraLetters.size()));
            Collections.shuffle(mExtraLetters);

            do {
                if (!mExtraLetters.get(mCount).get("letter_id").equals(mCorrectID)) {
                    mRoundLetters.add(new HashMap<>(mExtraLetters.get(mCount)));
                    mRoundLetters.get(mRoundLetters.size() - 1).put("guessed_yet", "0");
                    mRoundLetters.get(mRoundLetters.size() - 1).put("is_correct", "0");
                }
                mCount++;
            } while (mRoundLetters.size() < 4 && mCount <= mExtraLetters.size());
        }else if(mActivityNumber==4) {
            mRoundLetters = new ArrayList<>();
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundLetters.get(mRoundLetters.size() - 1).put("guessed_yet", "0");
            mRoundLetters.get(mRoundLetters.size() - 1).put("is_correct", "1");
            mRoundNumber++;
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundLetters.get(mRoundLetters.size() - 1).put("guessed_yet", "0");
            mRoundLetters.get(mRoundLetters.size() - 1).put("is_correct", "1");
            mRoundNumber++;
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundLetters.get(mRoundLetters.size() - 1).put("guessed_yet", "0");
            mRoundLetters.get(mRoundLetters.size() - 1).put("is_correct", "1");
            mRoundNumber++;
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundLetters.get(mRoundLetters.size() - 1).put("guessed_yet", "0");
            mRoundLetters.get(mRoundLetters.size() - 1).put("is_correct", "1");
            mRoundNumber++;
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundLetters.get(mRoundLetters.size() - 1).put("guessed_yet", "0");
            mRoundLetters.get(mRoundLetters.size() - 1).put("is_correct", "1");
            mRoundNumber++;
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundLetters.get(mRoundLetters.size() - 1).put("guessed_yet", "0");
            mRoundLetters.get(mRoundLetters.size() - 1).put("is_correct", "1");
        }else{
            mRoundLetters = new ArrayList<>();
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundNumber++;
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundNumber++;
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));
            mRoundLetters.add(new HashMap<>(mCurrentLetters.get(mRoundNumber)));

        }
        Collections.shuffle(mRoundLetters);
        displayScreen();
    }


    ///////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appData.setActivityType(3);

        mValidateButton=(findViewById(R.id.btnValidate)!=null);
        mRoundNumber=0;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
