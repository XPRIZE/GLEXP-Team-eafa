package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 5/18/2016.
 */
public class ActivityQNRoot extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {

    protected ArrayList<HashMap<String,String>> mRoundNumberSet;
    protected ArrayList<HashMap<String,String>> mCurrentNumberSet;
    protected HashMap<String,String> mCorrectNumberSet;

    protected SparseIntArray  mCurrentNumbers;
    protected int mNumberGuessed=0;
    protected int mNumberTotalIncorrect=0;
    protected int mNumberCorrectInRow=0;
    protected boolean mNumberMain;
    protected boolean mShowDots;
    protected int mColor=0;

    @Override
    protected void beginRound() {
        super.beginRound();
        mNumberGuessed=0;
        Bundle mCountData;

        mIncorrectInRound=0;
        mRoundNumberSet = new ArrayList<>();
        mRoundNumberSet.add(mCurrentNumberSet.get(mRoundNumber));
        mCurrentVariable=mRoundNumberSet.get(0).get("math_number");
        mCorrectNumberSet=mRoundNumberSet.get(0);
        mCorrectID="0";
         if (mRoundNumberSet.get(0).get("is_correct").equals("1")){
            mCorrectID = mRoundNumberSet.get(0).get("math_numbers_id");
        }
        switch (mActivityNumber){
            default:
            case 1:{
                break;
            }
            case 7:
                mCurrentNumbers=new SparseIntArray();
                final Random mRandom = new Random();
                int mRandomNumber;

                boolean mLastPart;

                if(mCurrentVariable.equals("0")) {
                    mRandomNumber = 0;
                    mLastPart = false;
                }else if(mCurrentVariable.equals(mCorrectNumberSet.get("max_number"))){
                    mRandomNumber = 0;
                    mLastPart = true;
                }else if((Integer.parseInt(mCorrectNumberSet.get("max_number"))
                        - Integer.parseInt(mCurrentVariable))
                        <= (Integer.parseInt(mCurrentVariable)
                        -Integer.parseInt(mCorrectNumberSet.get("min_number")))){
                    mLastPart=true;
                    if(Integer.parseInt(mCurrentVariable)
                            ==Integer.parseInt(mCorrectNumberSet.get("max_number"))){
                        mRandomNumber=0;
                    }else if(Integer.parseInt(mCurrentVariable)
                            ==Integer.parseInt(mCorrectNumberSet.get("max_number"))-1){
                        mRandomNumber=mRandom.nextInt(1);
                    }else if(Integer.parseInt(mCurrentVariable)
                            ==Integer.parseInt(mCorrectNumberSet.get("max_number"))-2){
                        if(mCurrentVariable.equals("2")){
                         mRandomNumber = mRandom.nextInt(1)+1;
                        }else {
                            mRandomNumber = mRandom.nextInt(2);
                        }
                    }else{
                        mRandomNumber=mRandom.nextInt(3);
                    }
                }else{
                    mLastPart=false;
                    if(Integer.parseInt(mCurrentVariable)
                            ==Integer.parseInt(mCorrectNumberSet.get("min_number"))){
                        mRandomNumber=0;
                    }else if(Integer.parseInt(mCurrentVariable)
                            ==Integer.parseInt(mCorrectNumberSet.get("min_number"))+1){
                        mRandomNumber=mRandom.nextInt(1);
                    }else if(Integer.parseInt(mCurrentVariable)
                            ==Integer.parseInt(mCorrectNumberSet.get("min_number"))+2){
                        mRandomNumber=mRandom.nextInt(2);
                    }else{
                        mRandomNumber=mRandom.nextInt(3);
                    }
                }



                if(Integer.parseInt(mCorrectNumberSet.get("max_number"))>19){
                    int mFirstNumber;
                    int mSecondNumber;
                    int mThirdNumber;
                    int mFourthNumber;

                    switch(mRandomNumber){
                        default:
                        case 0:{
                            Log.d(Constants.LOGCAT,"case 0 | mLastPart="+mLastPart);
                            if(mLastPart){
                                mFirstNumber=mRandom.nextInt(Integer.parseInt(mCurrentVariable)-3);
                                mSecondNumber=mFirstNumber + mRandom.nextInt(
                                        Integer.parseInt(mCurrentVariable)-2 - mFirstNumber) + 1;
                                mThirdNumber=mSecondNumber + mRandom.nextInt(
                                        Integer.parseInt(mCurrentVariable)-1 - mSecondNumber) + 1;
                                mFourthNumber=Integer.parseInt(mCurrentVariable);
                            }else{
                                mFourthNumber=Integer.parseInt(mCurrentVariable)
                                        +mRandom.nextInt(Integer.parseInt(
                                                mCorrectNumberSet.get("max_number"))
                                        -Integer.parseInt(mCurrentVariable)-4)+3;
                                mThirdNumber=Integer.parseInt(mCurrentVariable)
                                        + mRandom.nextInt(mFourthNumber
                                        - Integer.parseInt(mCurrentVariable) -3) + 2;
                                mSecondNumber=Integer.parseInt(mCurrentVariable)
                                        + mRandom.nextInt(mThirdNumber
                                        - Integer.parseInt(mCurrentVariable)- 2) + 1;
                                mFirstNumber=Integer.parseInt(mCurrentVariable);

                            }
                            break;
                        }
                        case 1:{
                            Log.d(Constants.LOGCAT,"case 1 | mLastPart="+mLastPart);
                            if(mLastPart){
                                mFirstNumber=mRandom.nextInt(Integer.parseInt(mCurrentVariable)-2);
                                mSecondNumber=mFirstNumber + mRandom.nextInt(Integer.parseInt(
                                        mCurrentVariable)-2 - mFirstNumber) + 1;
                                mThirdNumber=Integer.parseInt(mCurrentVariable);
                                mFourthNumber= Integer.parseInt(mCurrentVariable)
                                        + mRandom.nextInt(Integer.parseInt(
                                        mCorrectNumberSet.get("max_number"))
                                        - Integer.parseInt(mCurrentVariable)-1) + 1;

                            }else{
                                mFirstNumber=mRandom.nextInt(Integer.parseInt(mCurrentVariable)-1);
                                mSecondNumber=Integer.parseInt(mCurrentVariable);
                                if((Integer.parseInt(mCorrectNumberSet.get("max_number"))
                                        -Integer.parseInt(mCurrentVariable)-3)<=0){
                                    mFourthNumber = Integer.parseInt(mCurrentVariable)  +2;
                                }else{
                                    mFourthNumber = Integer.parseInt(mCurrentVariable)
                                            + mRandom.nextInt(Integer.parseInt(
                                            mCorrectNumberSet.get("max_number"))
                                            - Integer.parseInt(mCurrentVariable) - 3) + 2;
                                }
                                if((mFourthNumber
                                        - Integer.parseInt(mCurrentVariable) -2)<=0){
                                    mThirdNumber=mFourthNumber;
                                    mFourthNumber=mThirdNumber+1;
                                }else {
                                    mThirdNumber = Integer.parseInt(mCurrentVariable)
                                            + mRandom.nextInt(mFourthNumber
                                            - Integer.parseInt(mCurrentVariable) - 2) + 1;
                                }
                            }
                            break;
                        }
                        case 2:{
                            mFourthNumber =4;
                            Log.d(Constants.LOGCAT,"case 2 | mLastPart="+mLastPart);
                            if(mLastPart){
                                mFirstNumber=mRandom.nextInt(Integer.parseInt(mCurrentVariable)-1);
                                mSecondNumber=Integer.parseInt(mCurrentVariable);
                                if((Integer.parseInt(mCorrectNumberSet.get("max_number"))
                                        -Integer.parseInt(mCurrentVariable)-3)<=0){
                                    mFourthNumber = Integer.parseInt(mCurrentVariable) +2;
                                }else{
                                    mFourthNumber = Integer.parseInt(mCurrentVariable)
                                            + mRandom.nextInt(Integer.parseInt(
                                            mCorrectNumberSet.get("max_number"))
                                            - Integer.parseInt(mCurrentVariable) - 3) + 2;
                                }
                                if((mFourthNumber
                                        - Integer.parseInt(mCurrentVariable) -2)<=0){
                                    mThirdNumber=mFourthNumber;
                                    mFourthNumber=mThirdNumber+1;
                                }else {
                                    mThirdNumber = Integer.parseInt(mCurrentVariable)
                                            + mRandom.nextInt(mFourthNumber
                                            - Integer.parseInt(mCurrentVariable) - 2) + 2;
                                }
                            }else{
                                mFirstNumber=mRandom.nextInt(Integer.parseInt(mCurrentVariable)-2);
                                mSecondNumber=mFirstNumber + mRandom.nextInt(
                                        Integer.parseInt(mCurrentVariable)-2 - mFirstNumber + 1);
                                mThirdNumber=Integer.parseInt(mCurrentVariable);
                                if((mFourthNumber
                                        - Integer.parseInt(mCurrentVariable) -1)<=0){
                                    mFourthNumber=mThirdNumber+1;
                                }else {
                                    mFourthNumber = Integer.parseInt(mCurrentVariable)
                                            + mRandom.nextInt(Integer.parseInt(
                                            mCorrectNumberSet.get("max_number"))
                                            - Integer.parseInt(mCurrentVariable) - 1) + 1;
                                }
                            }
                            break;
                        }
                        case 3:{
                            Log.d(Constants.LOGCAT,"case 3 | mLastPart="+mLastPart);
                            if(mLastPart){
                                mFourthNumber=Integer.parseInt(mCurrentVariable)
                                        +mRandom.nextInt(Integer.parseInt(
                                                mCorrectNumberSet.get("max_number"))
                                        -Integer.parseInt(mCurrentVariable)-4)+3;
                                mThirdNumber=Integer.parseInt(mCurrentVariable)
                                        + mRandom.nextInt(mFourthNumber
                                        - Integer.parseInt(mCurrentVariable) -3) + 2;
                                mSecondNumber=Integer.parseInt(mCurrentVariable)
                                        + mRandom.nextInt(mThirdNumber
                                        - Integer.parseInt(mCurrentVariable)- 2) + 1;
                                mFirstNumber=Integer.parseInt(mCurrentVariable);
                            }else{
                                mFirstNumber=mRandom.nextInt(Integer.parseInt(mCurrentVariable)-3);
                                mSecondNumber=mFirstNumber + mRandom.nextInt(
                                        Integer.parseInt(mCurrentVariable)-2 - mFirstNumber) + 1;
                                mThirdNumber=mSecondNumber + mRandom.nextInt(
                                        Integer.parseInt(mCurrentVariable)-1 - mSecondNumber) + 1;
                                mFourthNumber=Integer.parseInt(mCurrentVariable);
                            }

                            break;
                        }
                    }//end switch

                    mCurrentNumbers.put(0,mFirstNumber);
                    mCurrentNumbers.put(1,mSecondNumber);
                    mCurrentNumbers.put(2,mThirdNumber);
                    mCurrentNumbers.put(3,mFourthNumber);

                }else{
                    switch(mRandomNumber){
                        default:
                        case 0:{
                            if(mLastPart){
                                mCurrentNumbers.put(0,Integer.parseInt(mCurrentVariable)-3);
                                mCurrentNumbers.put(1,Integer.parseInt(mCurrentVariable)-2);
                                mCurrentNumbers.put(2,Integer.parseInt(mCurrentVariable)-1);
                                mCurrentNumbers.put(3,Integer.parseInt(mCurrentVariable));

                            }else{
                                mCurrentNumbers.put(0,Integer.parseInt(mCurrentVariable));
                                mCurrentNumbers.put(1,Integer.parseInt(mCurrentVariable)+1);
                                mCurrentNumbers.put(2,Integer.parseInt(mCurrentVariable)+2);
                                mCurrentNumbers.put(3,Integer.parseInt(mCurrentVariable)+3);
                            }
                            break;
                        }
                        case 1:{
                            if(mLastPart){
                                mCurrentNumbers.put(0,Integer.parseInt(mCurrentVariable)-2);
                                mCurrentNumbers.put(1,Integer.parseInt(mCurrentVariable)-1);
                                mCurrentNumbers.put(2,Integer.parseInt(mCurrentVariable));
                                mCurrentNumbers.put(3,Integer.parseInt(mCurrentVariable)+1);

                            }else{
                                mCurrentNumbers.put(0,Integer.parseInt(mCurrentVariable)-1);
                                mCurrentNumbers.put(1,Integer.parseInt(mCurrentVariable));
                                mCurrentNumbers.put(2,Integer.parseInt(mCurrentVariable)+1);
                                mCurrentNumbers.put(3,Integer.parseInt(mCurrentVariable)+2);
                            }
                            break;
                        }
                        case 2:{
                            if(mLastPart){
                                mCurrentNumbers.put(0,Integer.parseInt(mCurrentVariable)-1);
                                mCurrentNumbers.put(1,Integer.parseInt(mCurrentVariable));
                                mCurrentNumbers.put(2,Integer.parseInt(mCurrentVariable)+1);
                                mCurrentNumbers.put(3,Integer.parseInt(mCurrentVariable)+2);

                            }else{
                                mCurrentNumbers.put(0,Integer.parseInt(mCurrentVariable)-2);
                                mCurrentNumbers.put(1,Integer.parseInt(mCurrentVariable)-1);
                                mCurrentNumbers.put(2,Integer.parseInt(mCurrentVariable));
                                mCurrentNumbers.put(3,Integer.parseInt(mCurrentVariable)+1);
                            }
                            break;
                        }
                        case 3:{
                            if(mLastPart){
                                mCurrentNumbers.put(0,Integer.parseInt(mCurrentVariable));
                                mCurrentNumbers.put(1,Integer.parseInt(mCurrentVariable)+1);
                                mCurrentNumbers.put(2,Integer.parseInt(mCurrentVariable)+2);
                                mCurrentNumbers.put(3,Integer.parseInt(mCurrentVariable)+3);

                            }else{
                                mCurrentNumbers.put(0,Integer.parseInt(mCurrentVariable)-3);
                                mCurrentNumbers.put(1,Integer.parseInt(mCurrentVariable)-2);
                                mCurrentNumbers.put(2,Integer.parseInt(mCurrentVariable)-1);
                                mCurrentNumbers.put(3,Integer.parseInt(mCurrentVariable));
                                mCurrentNumbers.put(4,Integer.parseInt(mCurrentVariable));
                            }
                            break;
                        }
                    }//end switch


                }
                mCountData = new Bundle();
                Log.d(Constants.LOGCAT,"mCurrentNumbers.get(0)="+mCurrentNumbers.get(0));
                Log.d(Constants.LOGCAT,"mCurrentNumbers.get(1)="+mCurrentNumbers.get(1));
                Log.d(Constants.LOGCAT,"mCurrentNumbers.get(2)="+mCurrentNumbers.get(2));
                Log.d(Constants.LOGCAT,"mCurrentNumbers.get(3)="+mCurrentNumbers.get(3));
                String mWhere="AND (";
                for(int i=0; i<=3; i++){
                    Log.d(Constants.LOGCAT,"mCurrentNumbers.get("+i+")="+mCurrentNumbers.get(i));
                    Log.d(Constants.LOGCAT,"mCurrentVariable="+mCurrentVariable);

                    if(mCurrentNumbers.get(i)!=Integer.parseInt(mCurrentVariable)){
                        mWhere+="math_numbers.math_number" +
                                "=="+String.valueOf(mCurrentNumbers.get(i))+" OR ";

                    }
                }

                mWhere=mWhere.substring(0,mWhere.lastIndexOf("OR "));
                mWhere+=")";

                mCountData.putString("1",String.valueOf(mCurrentNumbers.get(0)));
                mCountData.putString("2",String.valueOf(mCurrentNumbers.get(1)));
                mCountData.putString("3",String.valueOf(mCurrentNumbers.get(2)));
                mCountData.putString("4",String.valueOf(mCurrentNumbers.get(3)));
                mCountData.putString("5",String.valueOf(mCurrentNumbers.get(4)));
                mCountData.putString("where",mWhere);

                getLoaderManager().restartLoader(Constants.EXTRA_MATH_NUMBERS,
                        mCountData, this);

                break;
            case 5:{

                if(mRoundNumber<=mCurrentNumberSet.size()) {

                    mCountData = new Bundle();

                    mCountData.putString("math_number",
                            mRoundNumberSet.get(0).get("math_number"));
                    mCountData.putString("math_numbers_id",
                            mRoundNumberSet.get(0).get("math_numbers_id"));


                    getLoaderManager().restartLoader(Constants.EXTRA_MATH_NUMBERS,
                            mCountData, this);
                }else{
                    lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                }
                break;
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void processData(Cursor mCursor) {
        mCurrentNumberSet=new ArrayList<>();
        int mCount=0;
        int mNumberVariables=Constants.NUMBER_QN_VARIABLES;
        if(mActivityNumber==7
                && Integer.parseInt(mCurrentGSP.get("current_level"))<=2){
            mNumberVariables=mNumberVariables/2;
        }
        if(mCursor.moveToFirst()) {
            do  {
                mCurrentNumberSet.add(new HashMap<String,String>());
                mCurrentNumberSet.get(mCount).put("is_correct", "1");
                mCurrentNumberSet.get(mCount).put("guessed_yet", "0");
                mCurrentNumberSet.get(mCount).put("math_numbers_id",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_id"))); //0
                mCurrentNumberSet.get(mCount).put("math_number",
                        mCursor.getString(mCursor.getColumnIndex("math_number"))); //1
                mCurrentNumberSet.get(mCount).put("math_number_style",
                        mCursor.getString(mCursor.getColumnIndex("math_number_style"))); //2
                mCurrentNumberSet.get(mCount).put("math_numbers_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_audio"))); //3
                mCurrentNumberSet.get(mCount).put("min_number",
                        mCursor.getString(mCursor.getColumnIndex("min_number"))); //3
                mCurrentNumberSet.get(mCount).put("max_number",
                        mCursor.getString(mCursor.getColumnIndex("max_number"))); //3
                mCount++;
                if (mCount >= mNumberVariables ||
                        mCursor.isLast() ) {
                    break;
                }
            }while(mCursor.moveToNext());
        }else{
            moveBackwords();
        }

        mCount=0;

        while( mCurrentNumberSet.size()<mNumberVariables){
            mCurrentNumberSet.add(new HashMap<>(mCurrentNumberSet.get(mCount)));
            mCount++;
        }

        Collections.shuffle(mCurrentNumberSet);

        beginRound();
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    
    protected void processExtraData(Cursor mCursor){
        int mCount=mRoundNumberSet.size();
        if(mCursor.moveToFirst()) {
            do  {
                mRoundNumberSet.add(new HashMap<String,String>());
                mRoundNumberSet.get(mCount).put("is_correct", "0");
                mRoundNumberSet.get(mCount).put("guessed_yet", "0");
                mRoundNumberSet.get(mCount).put("math_numbers_id",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_id"))); //0
                mRoundNumberSet.get(mCount).put("math_number",
                        mCursor.getString(mCursor.getColumnIndex("math_number"))); //1
                mRoundNumberSet.get(mCount).put("math_number_style",
                        mCursor.getString(mCursor.getColumnIndex("math_number_style"))); //2
                mRoundNumberSet.get(mCount).put("math_numbers_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_audio"))); //3
                mRoundNumberSet.get(mCount).put("min_number",
                        mCorrectNumberSet.get("min_number"));
                mRoundNumberSet.get(mCount).put("max_number",
                        mCorrectNumberSet.get("max  _number"));
                mCount++;
                if (mCount >= Constants.NUMBER_QN_VARIABLES ||
                        mCursor.isLast()) {
                    break;
                }
            }while(mCursor.moveToNext() && mCount < 4);

            displayScreen();

        }else{
            moveBackwords();
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void validate() {
        super.validate();
        mBeingValidated=true;
        if(mCorrect) {
            mNumberCorrectInRow++;

            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
        }else{
            mNumberCorrectInRow=0;
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
        }
        addActivityPoints();
        if(mCurrentNumberSet.size()<8){
            addActivityPoints();
        }
        if(mNumberCorrectInRow>=mCurrentNumberSet.size()){
            addPointToAllAvailableIfNoIncorrect();
        }
        processValidationDisplay();

        if(mActivityNumber==5) {
            processChoiceView();
        }

        if(mCorrect && mActivityNumber==5){
            clearWrong();
        }

        mProcessGuessPosition=0;
        processTheGuess(5);
    }


    //////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){ }

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected void placeNumbers(int mShapeID){
        switch(mShapeID){
            default:
            case 0:{
                ((TextView) findViewById(R.id.number1))
                        .setText(mRoundNumberSet.get(mShapeID).get("math_number"));
                findViewById(R.id.number1).setTag(mRoundNumberSet.get(mShapeID));

                break;
            }
            case 1:{
                ((TextView) findViewById(R.id.number2))
                        .setText(mRoundNumberSet.get(mShapeID).get("math_number"));
                findViewById(R.id.number2).setTag(mRoundNumberSet.get(mShapeID));
                break;
            }
            case 2:{
                ((TextView) findViewById(R.id.number3))
                        .setText(mRoundNumberSet.get(mShapeID).get("math_number"));
                findViewById(R.id.number3).setTag(mRoundNumberSet.get(mShapeID));
                break;
            }
            case 3:{
                ((TextView) findViewById(R.id.number4))
                        .setText(mRoundNumberSet.get(mShapeID).get("math_number"));
                findViewById(R.id.number4).setTag(mRoundNumberSet.get(mShapeID));
                break;
            }

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    protected void placeNumberDots(int mShapeID){
        MathOperationDots customCanvasDots;
        switch(mShapeID){
            default:
            case 0:{
                customCanvasDots  = (MathOperationDots) findViewById(R.id.shape1);
                break;
            }
            case 1:{
                customCanvasDots  = (MathOperationDots) findViewById(R.id.shape2);
                break;
            }
            case 2:{
                customCanvasDots  = (MathOperationDots) findViewById(R.id.shape3);
                break;
            }
            case 3:{
                customCanvasDots  = (MathOperationDots) findViewById(R.id.shape4);
                break;
            }

        }

        customCanvasDots.clearCanvas();

        customCanvasDots.setNumberOfDots(
                Integer.parseInt(mRoundNumberSet.get(mShapeID).get("math_number")));
        customCanvasDots.setColor(mColor);
        customCanvasDots.invalidate();

        customCanvasDots.setTag(mRoundNumberSet.get(mShapeID));

    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processValidationDisplay(){
        switch(mActivityNumber) {
            case 7:{
                break;
            }
            default:
            case 1:
            case 5: {
                if (mCorrect) {
                    switch (mCurrentLocation) {
                        default:
                        case 0:
                            mRoundNumberSet.get(0).put("guessed_yet", "1");
                            break;
                        case 1:
                            mRoundNumberSet.get(1).put("guessed_yet", "1");
                            break;
                        case 2:
                            mRoundNumberSet.get(2).put("guessed_yet", "1");
                            break;
                        case 3:
                            mRoundNumberSet.get(3).put("guessed_yet", "1");
                            break;
                    }
                } else {
                    mIncorrectInRound++;
                    if (mIncorrectInRound >= 2) {
                        switch (mCorrectLocation) {
                            default:
                            case 0:
                                mRoundNumberSet.get(1).put("guessed_yet", "1");
                                mRoundNumberSet.get(2).put("guessed_yet", "1");
                                mRoundNumberSet.get(3).put("guessed_yet", "1");
                                break;
                            case 1:
                                mRoundNumberSet.get(0).put("guessed_yet", "1");
                                mRoundNumberSet.get(2).put("guessed_yet", "1");
                                mRoundNumberSet.get(3).put("guessed_yet", "1");
                                break;
                            case 2:
                                mRoundNumberSet.get(1).put("guessed_yet", "1");
                                mRoundNumberSet.get(0).put("guessed_yet", "1");
                                mRoundNumberSet.get(3).put("guessed_yet", "1");
                                break;
                            case 3:
                                mRoundNumberSet.get(1).put("guessed_yet", "1");
                                mRoundNumberSet.get(2).put("guessed_yet", "1");
                                mRoundNumberSet.get(0).put("guessed_yet", "1");
                                break;
                        }
                    }
                    switch (mCurrentLocation) {
                        default:
                        case 0:
                            mRoundNumberSet.get(0).put("guessed_yet", "1");
                            break;
                        case 1:
                            mRoundNumberSet.get(1).put("guessed_yet", "1");
                            break;
                        case 2:
                            mRoundNumberSet.get(2).put("guessed_yet", "1");
                            break;
                        case 3:
                            mRoundNumberSet.get(3).put("guessed_yet", "1");
                            break;
                    }
                }
                break;
            }
        }//end switch
    }


    protected void processChoiceView(){
        if(mCorrect || mIncorrectInRound>=2) {
            switch (mCorrectLocation) {
                default:
                case 0:
                    findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
                    break;
                case 1:
                    findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
                    break;
                case 2:
                    findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
                    break;
                case 3:
                    findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
                    break;
            }
        }


        switch(mCurrentLocation){
            default:
            case 0:
                if(Integer.parseInt(mCurrentVariable) > 9 || mNumberMain ||mShowDots) {
                    findViewById(R.id.shape1).setVisibility(MathOperationDots.VISIBLE);
                }
                findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
                if(mCorrect){
                    ((TextView) findViewById(R.id.number1)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                }else{
                    ((TextView) findViewById(R.id.number1)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));

                }
                break;
            case 1:
                if(Integer.parseInt(mCurrentVariable) > 9 || mNumberMain ||mShowDots) {
                    findViewById(R.id.shape2).setVisibility(MathOperationDots.VISIBLE);
                }
                findViewById(R.id.number2).setVisibility(TextView.VISIBLE);
                if(mCorrect){
                    ((TextView) findViewById(R.id.number2)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                }else{
                    ((TextView) findViewById(R.id.number2)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));

                }
                break;
            case 2:
                if(Integer.parseInt(mCurrentVariable) > 9 || mNumberMain ||mShowDots) {
                    findViewById(R.id.shape3).setVisibility(MathOperationDots.VISIBLE);
                }
                findViewById(R.id.number3).setVisibility(TextView.VISIBLE);
                if(mCorrect){
                    ((TextView) findViewById(R.id.number3)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                }else{
                    ((TextView) findViewById(R.id.number3)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));

                }
                break;
            case 3:
                if(Integer.parseInt(mCurrentVariable) > 9 || mNumberMain ||mShowDots) {
                    findViewById(R.id.shape4).setVisibility(MathOperationDots.VISIBLE);
                }
                findViewById(R.id.number4).setVisibility(TextView.VISIBLE);
                if(mCorrect){
                    ((TextView) findViewById(R.id.number4)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                }else{
                    ((TextView) findViewById(R.id.number4)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));

                }
                break;
        }

    }



    //////////////////////////////////////////////////////////////////////////////////////////

    protected void clearWrong(){
        if(mCorrect){
            switch(mCorrectLocation){
                default:
                case 0:
                    findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
                    break;
                case 1:
                    findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
                    break;
                case 2:
                    findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);

                    findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);

                    break;
                case 3:
                    findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.shape3).setVisibility(TextView.INVISIBLE);
                    findViewById(R.id.shape1).setVisibility(TextView.INVISIBLE);

                    break;
            }
        }else {
            switch (mCurrentLocation) {
                default:
                case 0:
                    findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
                    break;
                case 1:
                    findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);

                    break;
                case 2:
                    findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
                    break;
                case 3:
                    findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);
                    findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
                    break;
            }
        }
    }

    protected void setUpListeners() {


        if(mActivityNumber==5 || mActivityNumber==7) {
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
