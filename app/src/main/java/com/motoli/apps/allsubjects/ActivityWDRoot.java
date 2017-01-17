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
 * on 5/13/2016.
 */
public class ActivityWDRoot  extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {

    protected int mCorrectWordCount;



    protected ArrayList<HashMap<String,String>> mCurrentWords;
    protected ArrayList<HashMap<String,String>> mRoundWords;
    protected ArrayList<HashMap<String,String>> mAllWords;

    protected boolean mPlayWordAudio;

    @Override
    protected void beginRound() {
        super.beginRound();
        mRoundWords = new ArrayList<>();
        mRoundWords.add(new HashMap<>(mCurrentWords.get(mRoundNumber)));

        mCorrectID="0";
        if (mRoundWords.get(0).get("is_correct").equals("1")){
            mCorrectID = mRoundWords.get(0).get("word_id");
            mCorrectAudio=mRoundWords.get(0).get("audio_url");

        }

        int mCount = mRoundWords.size();
        Collections.shuffle(mAllWords);

        if(mActivityNumber==1){
            do {
                if (!mAllWords.get(mCount).get("word_id").equals(mCorrectID)) {
                    mRoundWords.add(new HashMap<>(mAllWords.get(mCount)));
                }
                mCount++;
            } while (mRoundWords.size() < 4 && mCount <= mAllWords.size());
        }
        Collections.shuffle(mRoundWords);
        displayScreen();
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processData(Cursor mCursor) {
        super.processData(mCursor);
        mCurrentWords=new ArrayList<HashMap<String, String>>();
        int mCount=0;
        if(mCursor.moveToFirst()) {
            do{
                mCurrentWords.add(new HashMap<String, String>());
                mCurrentWords.get(mCount).put("word_id",
                        mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
                mCurrentWords.get(mCount).put("word_text",
                        mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
                mCurrentWords.get(mCount).put("audio_url",
                        mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
                mCurrentWords.get(mCount).put("image_url",
                        mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
                mCurrentWords.get(mCount).put("level_number",
                        mCursor.getString(mCursor.getColumnIndex("level_number"))); //7
                mCurrentWords.get(mCount).put("is_correct","1");
                mCurrentWords.get(mCount).put("guessed_yet","0");
                mCount++;
                if (mCount >= Constants.NUMBER_VARIABLES ||
                        mCursor.isLast()){
                    break;
                }else if(mCursor.isLast()){
                    mCursor.moveToFirst();
                }
            }while(mCursor.moveToNext());
        }

        Collections.shuffle(mCurrentWords);

        if(mCursor.moveToFirst()) {
            mAllWords = new ArrayList<>();
            mCount = 0;
            do {
                mAllWords.add(new HashMap<String, String>());
                mAllWords.get(mCount).put("word_id",
                        mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
                mAllWords.get(mCount).put("word_text",
                        mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
                mAllWords.get(mCount).put("audio_url",
                        mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
                mAllWords.get(mCount).put("image_url",
                        mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
                mAllWords.get(mCount).put("level_number",
                        mCursor.getString(mCursor.getColumnIndex("level_number"))); //7
                mAllWords.get(mCount).put("is_correct","0");
                mAllWords.get(mCount).put("guessed_yet","0");
                mCount++;

            } while (mCursor.moveToNext());
            Collections.shuffle(mAllWords);
        }
    }


    @Override
    protected void processTheGuess(long mAudioDuration) {
        super.processTheGuess(mAudioDuration);
    }

    @Override
    protected void processValidationDisplay() {
        super.processValidationDisplay();
        switch(mActivityNumber) {
            default:
            case 1: {
                if (mCorrect) {
                    switch (mCurrentLocation) {
                        default:
                        case 0:
                            mRoundWords.get(0).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.word1)).setTextColor(
                                    getResources().getColor(R.color.correct_green));
                            break;
                        case 1:
                            mRoundWords.get(1).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.word2)).setTextColor(
                                    getResources().getColor(R.color.correct_green));
                            break;
                        case 2:
                            mRoundWords.get(2).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.word3)).setTextColor(
                                    getResources().getColor(R.color.correct_green));
                            break;
                        case 3:
                            mRoundWords.get(3).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.word4)).setTextColor(
                                    getResources().getColor(R.color.correct_green));
                            break;
                    }

                } else {
                    mIncorrectInRound++;

                    if (mIncorrectInRound >= 2) {
                        switch (mCorrectLocation) {
                            default:
                            case 0:
                                mRoundWords.get(1).put("guessed_yet", "1");
                                mRoundWords.get(2).put("guessed_yet", "1");
                                mRoundWords.get(3).put("guessed_yet", "1");
                                break;
                            case 1:
                                mRoundWords.get(0).put("guessed_yet", "1");
                                mRoundWords.get(2).put("guessed_yet", "1");
                                mRoundWords.get(3).put("guessed_yet", "1");
                                break;
                            case 2:
                                mRoundWords.get(1).put("guessed_yet", "1");
                                mRoundWords.get(0).put("guessed_yet", "1");
                                mRoundWords.get(3).put("guessed_yet", "1");
                                break;
                            case 3:
                                mRoundWords.get(1).put("guessed_yet", "1");
                                mRoundWords.get(2).put("guessed_yet", "1");
                                mRoundWords.get(0).put("guessed_yet", "1");
                                break;
                        }
                    }
                    switch (mCurrentLocation) {
                        default:
                        case 0:
                            mRoundWords.get(0).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.word1)).setTextColor(
                                    getResources().getColor(R.color.incorrect_red));
                            break;
                        case 1:
                            mRoundWords.get(1).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.word2)).setTextColor(
                                    getResources().getColor(R.color.incorrect_red));
                            break;
                        case 2:
                            mRoundWords.get(2).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.word3)).setTextColor(
                                    getResources().getColor(R.color.incorrect_red));
                            break;
                        case 3:
                            mRoundWords.get(3).put("guessed_yet", "1");
                            ((TextView) findViewById(R.id.word4)).setTextColor(
                                    getResources().getColor(R.color.incorrect_red));
                            break;
                    }


                }
                break;
            }
            case 2: {
                break;
            }
        }
    }

    @Override
    protected void setUpListeners() {
        super.setUpListeners();
        /*
        findViewById(R.id.btnInfo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mBeingValidated) {
                    playClickSound();
                    long mAudioDuration = playInstructionAudio();
                    mAudioHandler.postDelayed(playWordAudio, mAudioDuration + 20);
                }
            }
        });
                  */


        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mValidateAvailable && !mBeingValidated){
                    playClickSound();
                    validate();
                }
            }
        });
    }

    protected long playInstructionAudio(){
        long mAudioDuration = super.playInstructionAudio();
        mAudioHandler.postDelayed(playWordAudio, mAudioDuration + 20);
        return mAudioDuration;
    }

    @Override
    protected void validate() {
        super.validate();
        mBeingValidated=true;
        if(mCorrect) {
            mCorrectWordCount++;
            if(findViewById(R.id.btnValidate)!=null) {
                if(mActivityNumber==1) {
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_ok_mic);
                }else {
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_ok);
                }
            }
        }else if(findViewById(R.id.btnValidate)!=null) {
            if(mActivityNumber==1) {
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
            // addActivityPointsByLevel();
            addActivityPoints();
            placeVariableAttempts();
        }

        mProcessGuessPosition=0;
        processTheGuess(10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected Runnable playWordAudio = new Runnable() {

        @Override
        public void run() {
            mAudioHandler.removeCallbacks(playWordAudio);
            mBeingValidated = false;
            if (!mCorrectAudio.equals("") && mPlayWordAudio) {
                playGeneralAudio(mCorrectAudio);
            }
        }
    };

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }
}
