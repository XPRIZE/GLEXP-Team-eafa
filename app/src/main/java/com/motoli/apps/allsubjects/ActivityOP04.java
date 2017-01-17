package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
 */
public class ActivityOP04 extends ActivityOPRoot
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private int mNumberOne=0;
    private int mNumberTwo=0;
    private String mCurrentMathOperationsId;

    private int mAnswer=0;



    private TextView mFinalNumber;
    private TextView mEquationNumber1, mEquationNumber2;
    private MathOperationDots mEquationDots1, mEquationDots2, mFinalDots;

    private ArrayList<HashMap<String,String>> mMathOperations;

    private int mCurrentLevel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_op04);
        mOperand=2;
        appData.addToClassOrder(5);
        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        mCurrentGSP = new HashMap<>(appData.getCurrentGroup_Section_Phase());

        mCurrentLevel=Integer.parseInt(mCurrentGSP.get("current_level"));
        mInstructionAudio="info_op04";

        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off);
        findViewById(R.id.answerDots).setAlpha(0f);
        findViewById(R.id.numberPad).setAlpha(1.0f);


        mFinalNumber = ((TextView) findViewById(R.id.equationResult));
        mFinalNumber.setText("");
        mEquationNumber1 = ((TextView) findViewById(R.id.equationNumber1));
        mEquationNumber2 = ((TextView) findViewById(R.id.equationNumber2));
        mEquationDots1 = ((MathOperationDots) findViewById(R.id.equationDots1));
        mEquationDots2 = ((MathOperationDots) findViewById(R.id.equationDots2));
        mFinalDots = ((MathOperationDots) findViewById(R.id.finalDots));

        mEquationNumber1.setText("");
        mEquationNumber2.setText("");

        mEquationDots1.setNumberOfDots(0);
        mEquationDots2.setNumberOfDots(0);
        mFinalDots.setNumberOfDots(0);

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
        ((TextView) findViewById(R.id.equationSymbol)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.equalSymbol)).setTypeface(appData.getNumberFontTypeface());

        ((TextView) findViewById(R.id.equationSymbol)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.equalSymbol)).setTypeface(appData.getNumberFontTypeface());

        mEquationNumber1.setTypeface(appData.getNumberFontTypeface());
        mEquationNumber2.setTypeface(appData.getNumberFontTypeface());
        mFinalNumber.setTypeface(appData.getNumberFontTypeface());

        resetNumberBoxes();
        setUpListeners();
        setUpNumberPad();

        getLoaderManager().initLoader(Constants.MATH_OPERATIONS, null, this);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    protected void beginRound(){
        mAllowNumberPad=true;

        mIncorrectInRound=0;
        mValidateAvailable=false;
        mBeingValidated=false;
        mFinalNumber.setTextColor(getResources().getColor(R.color.normalBlack));
        mFinalNumber.setPaintFlags(mFinalNumber.getPaintFlags()
                & (~ Paint.STRIKE_THRU_TEXT_FLAG));

        displayScreen();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mCurrentMathOperationsId=mMathOperations.get(mRoundNumber).get("math_operations_id");
        mNumberOne=Integer.parseInt(mMathOperations.get(mRoundNumber).get("number_one"));
        mNumberTwo=Integer.parseInt(mMathOperations.get(mRoundNumber).get("number_two"));
        mAnswer=mNumberOne-mNumberTwo;

        ((TextView) findViewById(R.id.equationNumber1)).setText(String.valueOf(mNumberOne));
        ((TextView) findViewById(R.id.equationNumber2)).setText(String.valueOf(mNumberTwo));

        placingCurrentNumbersAsImages(0);
        placingCurrentNumbersAsImages(1);
        placingCurrentNumbersAsImages(2);

        if(mRoundNumber==0)
            playInstructionAudio();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void placingCurrentNumbersAsImages(int shapeID){
        MathOperationDots mCustomMathOperationDots;
        int mNumber=0;
        if(shapeID==0){
            mCustomMathOperationDots  = mEquationDots1;
            mNumber=mNumberOne;
        }else if(shapeID==1){
            mCustomMathOperationDots  = mEquationDots2;
            mNumber=mNumberTwo;
        }else{
            mCustomMathOperationDots  = mFinalDots;
            mNumber=mAnswer;
        }
        if(mNumberTwo>mNumberOne){
            mCustomMathOperationDots.setMaxDots(mNumberTwo);
        }else{
            mCustomMathOperationDots.setMaxDots(mNumberOne);
        }

        mCustomMathOperationDots.setVisibility(MathOperationDots.VISIBLE);
        mCustomMathOperationDots.clearCanvas();

        mCustomMathOperationDots.setNumberOfDots(mNumber);

        mCustomMathOperationDots.invalidate();
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

    protected void processData(Cursor mCursor){
        mMathOperations=new ArrayList<>();
        int mNumber=0;
        boolean mFirstOperation=true;
        if(mCursor.moveToFirst()) {
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
                mMathOperations.get(mNumber).put("operand","2"); //2
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

        /*
        if(mMathOperations.size()<Constants.MATH_OPERATIONS_VARIABLES ) {
            for (int i = 0; i < mMathOperations.size(); i++) {
                if (!mMathOperations.get(mMathOperations.size() - 1).get("math_operations_id")
                        .equals(mMathOperations.get(i).get("math_operations_id"))) {
                    mMathOperations.add(new HashMap<String,String>(mMathOperations.get(i)));
                    if (mMathOperations.size() >= Constants.NUMBER_VARIABLES) {
                        break;
                    }
                    if(mMathOperations.size()<Constants.NUMBER_VARIABLES &&
                            i==mMathOperations.size()-1){
                        i=-1;
                    }
                }
            }

        }
        */

        beginRound();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////



    public void setValidate(){
        mFinalNumber.setText(mUsersAnswer);
        // mFinalNumber.setSelection(mFinalNumber.getText().length());

        if(mFinalNumber.length()>=1){
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_on);
            mValidateAvailable=true;
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_off);
            mValidateAvailable=false;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        mProcessGuessPosition=0;
        if(Integer.parseInt(mFinalNumber.getText().toString())==mAnswer){
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            mCorrect=true;
            mCorrectInARow++;
            mFinalNumber.setTextColor(getResources().getColor(R.color.correct_green));
            findViewById(R.id.answerDots).setAlpha(1.0f);
            findViewById(R.id.numberPad).setAlpha(0f);
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            mCorrect=false;
            mCorrectInARow=0;
            mFinalNumber.setTextColor(getResources().getColor(R.color.incorrect_red));

        }


        guessHandler.postDelayed(processGuess, 20);

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private Runnable processGuess = new Runnable(){
        @Override
        public void run(){
            long mAudioDuration=0;

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
                    /*
                    if(mCorrect){
                        mAudioDuration=playGeneralAudio(currentLtrWrdAudio);
                    }
                    */
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{

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

                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                    getApplicationContext(), R.anim.fade_out));
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        mUsersAnswer="";
                        mFinalNumber.setText("");
                        mFinalNumber.setTextColor(getResources().getColor(R.color.normalBlack));
                        mFinalNumber.setPaintFlags(mFinalNumber.getPaintFlags()
                                & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                        if(mIncorrectInRound>=3){
                            mAllowNumberPad=false;
                            findViewById(R.id.numberPad).setAlpha(0.2f);
                            mProcessGuessPosition=4;
                            mUsersAnswer=String.valueOf(mAnswer);
                            mFinalNumber.setText(mUsersAnswer);
                            mAudioHandler.postDelayed(processGuess, 100);
                        }
                    }
                    break;
                }
                case 3:{
                    inBetweenRounds(false);
                    //inBetweenRounds(1);
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
                case 4:{
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mValidateAvailable=true;
                    mUsersAnswer = String.valueOf(mAnswer);
                    mFinalNumber.setText(String.valueOf(mAnswer));
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };//end private Runnable processGuess = new Runnable(){

    /////////////////////////////////////////////////////////////////////////////////////////

    private void inBetweenRounds(boolean mClear){
        if(mClear){
            mUsersAnswer="";
            mFinalNumber.setText("");
            findViewById(R.id.answerDots).setAlpha(0f);
            findViewById(R.id.numberPad).setAlpha(1.0f);
            mFinalNumber.setTextColor(getResources().getColor(R.color.normalBlack));
            mFinalNumber.setPaintFlags(mFinalNumber.getPaintFlags()
                    & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_off);
            mEquationDots1.setNumberOfDots(0);
            mEquationDots1.invalidate();
            mEquationDots2.setNumberOfDots(0);
            mEquationDots2.invalidate();
            ((TextView) findViewById(R.id.equationNumber1)).setText("");
            ((TextView) findViewById(R.id.equationNumber2)).setText("");

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

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] mData;
        String mWhere="";
        String mOrderBy="";
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
                mWhere="2";
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
            case Constants.MATH_NUMBERS_SET: {
                processData(data);
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
