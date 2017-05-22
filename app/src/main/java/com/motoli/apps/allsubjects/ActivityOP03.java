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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/28/2015.
 *
 *
 * last update 4/13/2017
 *
 * Does simple Addition. It will have dots printed on screen representing the numbers for
 * subtraction and if the correct answer is given dots will appear where number pad is in order
 * to show the new dots value.
 * Data is pulled from ActivityProvider based upon current level of user.
 * Activity is  available with each new level
 *
 * This class is using similar function found in other ActivityOP**
 */
public class ActivityOP03 extends ActivityOPRoot
        implements LoaderManager.LoaderCallbacks<Cursor> {


    private String mCurrentMathOperationsId;

    private boolean mVerticalMath=false;

    private int mAnswer=0;



    private TextView mFinalNumber, mVerticalFinalNumber;

    private ArrayList<HashMap<String,String>> mMathOperations;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_op03);
        appData.addToClassOrder(5);


        mCurrentGSP = new HashMap<>(appData.getCurrentGroup_Section_Phase());
        mInstructionAudio="info_op03";

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
        ((ImageView) findViewById(R.id.btnVerticalValidate))
                .setImageResource(R.drawable.btn_validate_off);


        findViewById(R.id.activityMainVertical)
                .setVisibility(LinearLayout.INVISIBLE);
        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.INVISIBLE);

        mFinalNumber = ((TextView) findViewById(R.id.equationResult));
        mVerticalFinalNumber= ((TextView) findViewById(R.id.verticalResult));
        mFinalNumber.setText("");
        mVerticalFinalNumber.setText("");
        TextView mEquationNumber1 = ((TextView) findViewById(R.id.equationNumber1));
        TextView mEquationNumber2 = ((TextView) findViewById(R.id.equationNumber2));
        mEquationNumber1.setText("");
        mEquationNumber2.setText("");




        ((TextView) findViewById(R.id.guessNumber0)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.guessNumber1)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.guessNumber2)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.guessNumber3)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.guessNumber4)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.guessNumber5)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.guessNumber6)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.guessNumber7)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.guessNumber8)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.guessNumber9)).setTypeface(appData.getNumberFontTypeface());



        ((TextView) findViewById(R.id.verticalGuessNumber0))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalGuessNumber1))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalGuessNumber2))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalGuessNumber3))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalGuessNumber4))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalGuessNumber5))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalGuessNumber6))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalGuessNumber7))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalGuessNumber8))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalGuessNumber9))
                .setTypeface(appData.getNumberFontTypeface());
        
        
        ((TextView) findViewById(R.id.verticalNumber1))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalNumber2))
                .setTypeface(appData.getNumberFontTypeface());
        
        ((TextView) findViewById(R.id.verticalSymbol))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.verticalEqual))
                .setTypeface(appData.getNumberFontTypeface());
        
        ((TextView) findViewById(R.id.equationSymbol))
                .setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.equalSymbol))
                .setTypeface(appData.getNumberFontTypeface());

        mEquationNumber1.setTypeface(appData.getNumberFontTypeface());
        mEquationNumber2.setTypeface(appData.getNumberFontTypeface());
        mFinalNumber.setTypeface(appData.getNumberFontTypeface());
        mVerticalFinalNumber.setTypeface(appData.getNumberFontTypeface());

        resetNumberBoxes();
        setUpListeners();
        setUpNumberPad();

        getLoaderManager().initLoader(Constants.MATH_OPERATIONS, null, this);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////


    protected void beginRound(){
        mAllowNumberPad=true;
        mIncorrectInRound=0;
        mValidateAvailable=false;
        mBeingValidated=false;
        mFinalNumber.setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
        mVerticalFinalNumber.setTextColor(ContextCompat.getColor(this,R.color.normalBlack));

        displayScreen();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){


        mCurrentMathOperationsId=mMathOperations.get(mRoundNumber).get("math_operations_id");
        mAnswer=Integer.parseInt(mMathOperations.get(mRoundNumber).get("number_one"))
                + Integer.parseInt(mMathOperations.get(mRoundNumber).get("number_two"));
        int mNumberOne, mNumberTwo;

        final Random rand = new Random();
        if(rand.nextInt(2)==0){
            mNumberOne=Integer.parseInt(mMathOperations.get(mRoundNumber).get("number_one"));
            mNumberTwo=Integer.parseInt(mMathOperations.get(mRoundNumber).get("number_two"));
        }else{
            mNumberOne=Integer.parseInt(mMathOperations.get(mRoundNumber).get("number_two"));
            mNumberTwo=Integer.parseInt(mMathOperations.get(mRoundNumber).get("number_one"));
        }

        mAnswer=mNumberOne+mNumberTwo;

        ((TextView) findViewById(R.id.equationNumber1)).setText(String.valueOf(mNumberOne));
        ((TextView) findViewById(R.id.equationNumber2)).setText(String.valueOf(mNumberTwo));

        ((TextView) findViewById(R.id.verticalNumber1)).setText(String.valueOf(mNumberOne));
        ((TextView) findViewById(R.id.verticalNumber2)).setText(String.valueOf(mNumberTwo));

        if(mRoundNumber==0)
            playInstructionAudio();
    }


    //////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mMathOperations=new ArrayList<>();
        int mNumber=0;
        boolean mFirstOperation=true;
        mVerticalMath=false;
        if(mCursor.moveToFirst()) {

            if(mCursor.getInt(mCursor.getColumnIndex("max_addition_result"))>4){
                mVerticalMath=true;
            }
            do{
                if(mFirstOperation) {
                    mCursor.moveToFirst();
                }
                mFirstOperation=false;
                mMathOperations.add(new HashMap<String,String>());
                mMathOperations.get(mNumber).put("correct_guess","1");
                mMathOperations.get(mNumber).put("guessed_yet","0");
                mMathOperations.get(mNumber).put("math_operations_id","0"); //0
                mMathOperations.get(mNumber).put("number_one",
                        mCursor.getString(mCursor.getColumnIndex("number_one"))); //1
                mMathOperations.get(mNumber).put("operand","1"); //2
                mMathOperations.get(mNumber).put("number_two",
                        mCursor.getString(mCursor.getColumnIndex("number_two"))); //3

                mNumber++;
                if (mNumber >= Constants.MATH_OPERATIONS_VARIABLES
                        || mNumber >= (mCursor.getCount()*2.5)){
                    break;
                }else if(mNumber < Constants.MATH_OPERATIONS_VARIABLES  &&
                        mCursor.isLast()){
                    mCursor.moveToFirst();
                    mFirstOperation=true;
                }
            }while(mCursor.moveToNext());
        }

        Collections.shuffle(mMathOperations);

        if(mVerticalMath){
            findViewById(R.id.activityMainVertical)
                    .setVisibility(LinearLayout.VISIBLE);
            findViewById(R.id.activityMainVertical)
                    .setAnimation(AnimationUtils.loadAnimation(
                            getApplicationContext(), R.anim.fade_in));
            findViewById(R.id.activityMainPart)
                    .setVisibility(LinearLayout.INVISIBLE);

        }else {
            findViewById(R.id.activityMainPart)
                    .setVisibility(LinearLayout.VISIBLE);
            findViewById(R.id.activityMainPart)
                    .setAnimation(AnimationUtils.loadAnimation(
                            getApplicationContext(), R.anim.fade_in));
            findViewById(R.id.activityMainVertical)
                    .setVisibility(LinearLayout.INVISIBLE);

        }

        beginRound();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        mProcessGuessPosition=0;
        if(Integer.parseInt(mFinalNumber.getText().toString())==mAnswer){
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            ((ImageView) findViewById(R.id.btnVerticalValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            mCorrect=true;
            mCorrectInARow++;
            mFinalNumber.setTextColor(ContextCompat.getColor(this,R.color.correct_green));
            mVerticalFinalNumber.setTextColor(ContextCompat.getColor(this,R.color.correct_green));
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            ((ImageView) findViewById(R.id.btnVerticalValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            mCorrect=false;
            mCorrectInARow=0;
            mFinalNumber.setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
            mVerticalFinalNumber.setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));

        }
        guessHandler.postDelayed(processGuess, 20);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processPoints() {
        String where = " WHERE app_user_id=" + appData.getCurrentUserID()+ " " +
                "AND variable_id=" + mCurrentGSP.get("current_level") + " " +
                "AND activity_id=" + appData.getCurrentActivity().get(0);
        String[] selectionArgs;

        if (mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            ((ImageView) findViewById(R.id.btnVerticalValidate))
                    .setImageResource(R.drawable.btn_validate_ok);

            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentMathOperationsId,
                    String.valueOf(mIncorrectInRound),
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
        }else{
            mIncorrectInRound++;
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCurrentMathOperationsId,
                    String.valueOf(mIncorrectInRound),
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////

    private Runnable processGuess = new Runnable(){
        @Override
        public void run(){
            long mAudioDuration=0;

            switch(mProcessGuessPosition){
                case 0:
                default:
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, playCorrectOrNot(mCorrect)+10);
                    break;
                case 1:
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                case 2:
                    processPoints();
                    guessHandler.removeCallbacks(processGuess);

                    if(mCorrect){
                        mRoundNumber++;
                        inBetweenRounds(true);
                        if(mRoundNumber!=mMathOperations.size()){
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 100);
                        }else{
                            mLastActivityData=0;
                            if(mVerticalMath){
                                findViewById(R.id.activityMainVertical)
                                        .setVisibility(LinearLayout.INVISIBLE);
                                findViewById(R.id.activityMainVertical)
                                        .setAnimation(AnimationUtils.loadAnimation(
                                                getApplicationContext(), R.anim.fade_out));
                                findViewById(R.id.activityMainPart)
                                        .setVisibility(LinearLayout.INVISIBLE);

                            }else {
                                findViewById(R.id.activityMainPart)
                                        .setVisibility(LinearLayout.INVISIBLE);
                                findViewById(R.id.activityMainPart)
                                        .setAnimation(AnimationUtils.loadAnimation(
                                                getApplicationContext(), R.anim.fade_out));
                                findViewById(R.id.activityMainVertical)
                                        .setVisibility(LinearLayout.INVISIBLE);

                            }
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        mUsersAnswer="";
                        mFinalNumber.setText("");
                        mFinalNumber.setTextColor(ContextCompat.getColor(ActivityOP03.this,
                                R.color.normalBlack));

                        mVerticalFinalNumber.setText("");
                        mVerticalFinalNumber.setTextColor(ContextCompat.getColor(ActivityOP03.this,
                                R.color.normalBlack));

                        mFinalNumber.setPaintFlags(mFinalNumber.getPaintFlags()
                                & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        ((ImageView) findViewById(R.id.btnVerticalValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                        if(mIncorrectInRound>=3){
                            mAllowNumberPad=false;
                          //  findViewById(R.id.numberPad).setAlpha(0.2f);
                            mProcessGuessPosition=4;
                            mAudioHandler.postDelayed(processGuess, 100);
                        }
                    }
                    break;
                case 3:
                    inBetweenRounds(false);
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                case 4:
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    ((ImageView) findViewById(R.id.btnVerticalValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mValidateAvailable=true;
                    mUsersAnswer = String.valueOf(mAnswer);
                    mFinalNumber.setText(String.valueOf(mAnswer));
                    mVerticalFinalNumber.setText(String.valueOf(mAnswer));
                    break;
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };//end private Runnable processGuess = new Runnable(){

    /////////////////////////////////////////////////////////////////////////////////////////

    private void inBetweenRounds(boolean mClear){
        if(mClear){
            mUsersAnswer="";
            ((TextView) findViewById(R.id.verticalNumber1)).setText("");
            ((TextView) findViewById(R.id.verticalNumber2)).setText("");

            mFinalNumber.setText("");
            mFinalNumber.setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.verticalNumber1)).setText("");
            ((TextView) findViewById(R.id.verticalNumber2)).setText("");
            mVerticalFinalNumber.setText("");
            mVerticalFinalNumber.setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            mFinalNumber.setPaintFlags(mFinalNumber.getPaintFlags()
                    & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_off);
            ((ImageView) findViewById(R.id.btnVerticalValidate))
                    .setImageResource(R.drawable.btn_validate_off);
            ((TextView) findViewById(R.id.equationNumber1)).setText("");
            ((TextView) findViewById(R.id.equationNumber2)).setText("");
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////



        public void setValidate(){
            mFinalNumber.setText(mUsersAnswer);
            mVerticalFinalNumber.setText(mUsersAnswer);
            if(mFinalNumber.length()>=1){
                ((ImageView) findViewById(R.id.btnValidate))
                        .setImageResource(R.drawable.btn_validate_on);
                ((ImageView) findViewById(R.id.btnVerticalValidate))
                        .setImageResource(R.drawable.btn_validate_on);
                mValidateAvailable=true;
            }else{
                ((ImageView) findViewById(R.id.btnValidate))
                        .setImageResource(R.drawable.btn_validate_off);
                ((ImageView) findViewById(R.id.btnVerticalValidate))
                        .setImageResource(R.drawable.btn_validate_off);
                mValidateAvailable=false;
            }
        }

    //////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){

        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mValidateAvailable && !mBeingValidated){
                    playClickSound();
                    validate();
                }
            }
        });
        findViewById(R.id.btnVerticalValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mValidateAvailable && !mBeingValidated){
                    playClickSound();
                    validate();
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] mData;
        String mWhere;
        String mOrderBy;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.MATH_OPERATIONS:{



                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "0"}; //6
                mOrderBy="app_users_activity_variable_values.number_correct_in_a_row ASC, ";
                mWhere="1";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_OPERATIONS, mData, mWhere, null, mOrderBy);
                break;
            }
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch(loader.getId()) {
            default:
            case Constants.MATH_OPERATIONS: {
                processData(data);
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
