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

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/28/2015.
 */
public class ActivityQN04 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArrayList<HashMap<String,String>> mRoundsNumberSet;
    private ArrayList<HashMap<String,String>>  mExtraNumberSet;

    private ArrayList<Boolean> mLocationChosen;


    private int mRound=0;
    private int mColor=0;
    private int mCurrentLocation=2;
    private String mCorrectMathNumberId;

    private ArrayList<Boolean> mGreaterThen;
    protected int mNumberCorrectInRow=0;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_qn04);

        appData.addToClassOrder(6);
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        mGreaterThen=new ArrayList<>();
        mGreaterThen.add(false);
        mGreaterThen.add(false);

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        findViewById(R.id.activityMainPartSecond)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPartSecond)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mInstructionAudio="info_qn04";

        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off);

        ((TextView)findViewById(R.id.number1)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number2)).setTypeface(appData.getNumberFontTypeface());

        eraseNumbersAndShapes();

        viewProgressBars();

        setUpListeners();

        mRound=0;

        mValidateAvailable=false;
        mBeingValidated=false;

        getLoaderManager().initLoader(Constants.MATH_NUMBERS_SET, null, this);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    protected void beginRound(){
        hideStars();
        numbersOrDots();
        mColor=0;
        ((TextView) findViewById(R.id.number1)).setTextColor(
                ContextCompat.getColor(this,R.color.regularBlack));
        ((TextView) findViewById(R.id.number2)).setTextColor(
                ContextCompat.getColor(this,R.color.regularBlack));
        ((TextView) findViewById(R.id.number1)).setPaintFlags(
                ((TextView) findViewById(R.id.number1)).getPaintFlags()
                        &  (~Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.number2)).setPaintFlags(
                ((TextView) findViewById(R.id.number2)).getPaintFlags()
                        &  (~Paint.STRIKE_THRU_TEXT_FLAG));
        mLocationChosen=new ArrayList<>();
        mLocationChosen.add(false);
        mLocationChosen.add(false);

        boolean mRandom= Math.random() < 0.5;
        int mRandomSecond;

        do{
            mRandomSecond=(int) Math.ceil(Math.random() * (mExtraNumberSet.size()-1));
        }while(Integer.parseInt(mExtraNumberSet.get(mRandomSecond).get("math_numbers_id"))
                ==Integer.parseInt(mRoundsNumberSet.get(mRound).get("math_numbers_id")));

        mCorrectMathNumberId=mRoundsNumberSet.get(mRound).get("math_numbers_id");

        if(mRandom){

                ((TextView) findViewById(R.id.number1))
                        .setText(mRoundsNumberSet.get(mRound).get("math_number"));

                ((TextView) findViewById(R.id.number2))
                        .setText(mExtraNumberSet.get(mRandomSecond).get("math_number"));


            findViewById(R.id.shapeSpace1).setTag(mRoundsNumberSet.get(mRound));
            findViewById(R.id.shapeSpace2).setTag(mExtraNumberSet.get(mRandomSecond));
        }else{
                ((TextView) findViewById(R.id.number1))
                        .setText(mExtraNumberSet.get(mRandomSecond).get("math_number"));

                ((TextView) findViewById(R.id.number2))
                        .setText(mRoundsNumberSet.get(mRound).get("math_number"));


            findViewById(R.id.shapeSpace1).setTag(mExtraNumberSet.get(mRandomSecond));
            findViewById(R.id.shapeSpace2).setTag(mRoundsNumberSet.get(mRound));
        }



        if(Integer.parseInt(mRoundsNumberSet.get(mRound).get("math_number"))<=25
                && (mRoundsNumberSet.get(mRound).get("is_number").equals("0")
                        || mExtraNumberSet.get(mRandomSecond).get("is_number").equals("0"))) {
            for (int i = 0; i < 2; i++) {
                mColor=0;
                ((TextView) findViewById(R.id.number1)).setText("");
                ((TextView) findViewById(R.id.number2)).setText("");

                placingCurrentNumbersAsDots(i);
            }
        }else{
            for (int i = 0; i < 2; i++) {
                placingCurrentNumbers(i);
            }
        }

        if(Integer.parseInt((String)
                ((HashMap)findViewById(R.id.shapeSpace1).getTag()).get("math_number"))
                > Integer.parseInt((String)
                ((HashMap) findViewById(R.id.shapeSpace2).getTag()).get("math_number"))){
            mGreaterThen.set(0,true);
            mGreaterThen.set(1,false);
        }else{
            mGreaterThen.set(0,false);
            mGreaterThen.set(1,true);
        }

        if(mRound==0)
            playInstructionAudio();
        mCurrentLocation=2;
        setupFrameListens();
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens() {
        findViewById(R.id.shapeSpace1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated && !mLocationChosen.get(0)) {
                    mCurrentLocation = 0;
                    mLocationChosen.set(0,true);
                    mLocationChosen.set(1,false);

                    playGeneralAudio((String) ((HashMap) findViewById(R.id.shapeSpace1).getTag())
                            .get("math_numbers_audio"));
                    ((ImageView) findViewById(R.id.numberBox1))
                            .setImageResource(R.drawable.frm_qn04_on_left);
                    ((ImageView) findViewById(R.id.numberBox2))
                            .setImageResource(R.drawable.frm_qn04_off);

                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mCorrect = mGreaterThen.get(0);
                    mValidateAvailable = true;
                }
            }
        });

        findViewById(R.id.shapeSpace2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated && !mLocationChosen.get(1)) {
                    mCurrentLocation = 1;
                    mLocationChosen.set(0,false);
                    mLocationChosen.set(1,true);

                    playGeneralAudio((String) ((HashMap) findViewById(R.id.shapeSpace2).getTag())
                            .get("math_numbers_audio"));
                    ((ImageView) findViewById(R.id.numberBox1))
                            .setImageResource(R.drawable.frm_qn04_off);
                    ((ImageView) findViewById(R.id.numberBox2))
                            .setImageResource(R.drawable.frm_qn04_on_right);
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mCorrect = mGreaterThen.get(1);
                    mValidateAvailable = true;
                }
            }
        });

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
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
        processFontColorAndPoints();
        if(mNumberCorrectInRow>=mRoundsNumberSet.size()){
            addPointToAllAvailableIfNoIncorrect();
        }
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void eraseNumbersAndShapes(){
        ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();
        ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();

        ((TextView) findViewById(R.id.number1)).setText("");
        ((TextView) findViewById(R.id.number2)).setText("");

        ((ImageView)findViewById(R.id.numberBox1)).setImageResource(R.drawable.frm_qn04_off);
        ((ImageView)findViewById(R.id.numberBox2)).setImageResource(R.drawable.frm_qn04_off);

    }

    ////////////////////////////////////////////////////////////////////////////////////////

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
                    mAudioDuration=0;//playGeneralAudio(mCurrentNumberAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    guessHandler.removeCallbacks(processGuess);
                    if(mCorrect) {
                        eraseNumbersAndShapes();

                        mRound++;
                        if (mRound != (mRoundsNumberSet.size())) {
                            mBeingValidated = false;
                            mValidateAvailable = false;
                            viewProgressBars();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        } else {
                            mLastActivityData = 0;
                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
                            findViewById(R.id.activityMainPartSecond)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPartSecond)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));

                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm, 10);
                        }
                    }else{
                        mBeingValidated = false;
                        mValidateAvailable = false;
                        hideStars();
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                    }

                    break;
                }
                case 3:{
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off);
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };


    ////////////////////////////////////////////////////////////////////////////////////////////
    private void processFontColorAndPoints(){
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCorrectMathNumberId
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        if(mCorrect){
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCorrectMathNumberId,
                    "0",
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};

            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCorrectMathNumberId,
                    "0",
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};

            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);

        }

        boolean mShowDots=false;
        if(((HashMap) findViewById(R.id.shapeSpace1).getTag()).get("is_number").equals("0") ||
                ((HashMap) findViewById(R.id.shapeSpace2).getTag()).get("is_number").equals("0")){
            mShowDots=true;
        }
        if(mCurrentLocation==0 && mCorrect){
            if(!mShowDots){
                ((TextView) findViewById(R.id.number1)).setTextColor(
                        ContextCompat.getColor(this,R.color.correct_green));
            }else{
                mColor=1;
                placingCurrentNumbersAsDots(0);
            }
        }else if(mCurrentLocation==0 && !mCorrect){
            if(!mShowDots){
                ((TextView) findViewById(R.id.number1)).setTextColor(
                        ContextCompat.getColor(this,R.color.incorrect_red));

            }else{
                mColor=2;
                placingCurrentNumbersAsDots(0);
            }
        }else if(mCurrentLocation==1 && mCorrect){
            if(!mShowDots){
                ((TextView) findViewById(R.id.number2)).setTextColor(
                        ContextCompat.getColor(this,R.color.correct_green));
            }else{
                mColor=1;
                placingCurrentNumbersAsDots(1);
            }
        }else if(mCurrentLocation==1 && !mCorrect){
            if(!mShowDots){
                ((TextView) findViewById(R.id.number2)).setTextColor(
                        ContextCompat.getColor(this,R.color.incorrect_red));

            }else{
                mColor=2;
                placingCurrentNumbersAsDots(1);
            }
        }


    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void placingCurrentNumbers(int shapeID){
        switch(shapeID){
            default:
            case 0:{
                findViewById(R.id.number1).setVisibility(MathOperationDots.VISIBLE);
                ((TextView) findViewById(R.id.number1)).setTextColor(ContextCompat.getColor(this,R.color.regularBlack));
                ((TextView) findViewById(R.id.number1)).setPaintFlags(
                        ((TextView) findViewById(R.id.number1)).getPaintFlags()
                                &  (~Paint.STRIKE_THRU_TEXT_FLAG));
                ((TextView) findViewById(R.id.number1)).setText((String)
                        ((HashMap) findViewById(R.id.shapeSpace1).getTag()).get("math_number"));
                break;
            }
            case 1:{
                findViewById(R.id.number2).setVisibility(MathOperationDots.VISIBLE);
                ((TextView) findViewById(R.id.number2)).setTextColor(ContextCompat.getColor(this,R.color.regularBlack));
                ((TextView) findViewById(R.id.number2)).setPaintFlags(
                        ((TextView) findViewById(R.id.number2)).getPaintFlags()
                                &  (~Paint.STRIKE_THRU_TEXT_FLAG));
                ((TextView) findViewById(R.id.number2)).setText((String)
                        ((HashMap) findViewById(R.id.shapeSpace2).getTag()).get("math_number"));
                break;
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void placingCurrentNumbersAsDots(int shapeID){
        MathOperationDots customCanvasDots;
        switch(shapeID){
            default:
            case 0:{
                findViewById(R.id.shape1).setVisibility(MathOperationDots.VISIBLE);
                customCanvasDots  = (MathOperationDots) findViewById(R.id.shape1);
                customCanvasDots.setNumberOfDots(Integer.parseInt((String)
                        ((HashMap) findViewById(R.id.shapeSpace1).getTag()).get("math_number")));
                break;
            }
            case 1:{
                findViewById(R.id.shape2).setVisibility(MathOperationDots.VISIBLE);
                customCanvasDots  = (MathOperationDots) findViewById(R.id.shape2);
                customCanvasDots.setNumberOfDots(Integer.parseInt((String)
                        ((HashMap) findViewById(R.id.shapeSpace2).getTag()).get("math_number")));
                break;
            }
        }
        customCanvasDots.clearCanvas();
        customCanvasDots.setColor(mColor);
        customCanvasDots.invalidate();
    }


    /////////////////////////////////////////////////////////////////////////////////////////

    private void hideStars(){
        ((ImageView) findViewById(R.id.numberBox1)).setImageResource(R.drawable.frm_qn04_off);
        ((ImageView) findViewById(R.id.numberBox2)).setImageResource(R.drawable.frm_qn04_off);

    }
    /////////////////////////////////////////////////////////////////////////////////////////

    private void numbersOrDots(){
        if(Integer.parseInt(mCurrentGSP.get("current_level"))<=0) {
            findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);

            findViewById(R.id.shape1).setVisibility(MathOperationDots.VISIBLE);
            findViewById(R.id.shape2).setVisibility(MathOperationDots.VISIBLE);

            ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();
            ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();
        }else{
            findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
            findViewById(R.id.number2).setVisibility(TextView.VISIBLE);

            findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
            findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);

            ((TextView) findViewById(R.id.number1)).setText("");
            ((TextView) findViewById(R.id.number2)).setText("");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mRoundsNumberSet=new ArrayList<>();
        int mNumber=0;
        int mNumberCorrectInARow=Constants.INA_ROW_CORRECT;
        if(mCursor.moveToFirst()) {
            do  {
                mRoundsNumberSet.add(new HashMap<String,String>());
                mRoundsNumberSet.get(mNumber).put("correct_guess", "0");
                mRoundsNumberSet.get(mNumber).put("guessed_yet", "0");
                mRoundsNumberSet.get(mNumber).put("math_numbers_id",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_id"))); //0
                mRoundsNumberSet.get(mNumber).put("math_number",
                        mCursor.getString(mCursor.getColumnIndex("math_number"))); //1
                mRoundsNumberSet.get(mNumber).put("math_number_style",
                        mCursor.getString(mCursor.getColumnIndex("math_number_style"))); //2
                mRoundsNumberSet.get(mNumber).put("math_numbers_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_audio"))); //3

                mRoundsNumberSet.get(mNumber).put("number_correct",
                        mCursor.getString(mCursor.getColumnIndex("number_correct"))); //5
                mRoundsNumberSet.get(mNumber).put("number_correct_in_a_row",
                        mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
                mRoundsNumberSet.get(mNumber).put("number_incorrect",
                        mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //7

                mRoundsNumberSet.get(mNumber).put("is_number", "1");


                mNumberCorrectInARow = (mNumberCorrectInARow > Integer.parseInt(
                        mRoundsNumberSet.get(mNumber).get("number_correct_in_a_row")))
                        ? Integer.parseInt(
                        mRoundsNumberSet.get(mNumber).get("number_correct_in_a_row"))
                        : mNumberCorrectInARow;

                mNumber++;
                if (mNumber >= Constants.NUMBER_QN_VARIABLES ||
                        mCursor.isLast()) {
                    break;
                }
            }while(mCursor.moveToNext());
        }else{
            moveBackwords();
        }

        mNumber=0;
        while(mRoundsNumberSet.size()<8){
            mRoundsNumberSet.add(new HashMap<>(mRoundsNumberSet.get(mNumber)));
            mNumber++;
        }



        Collections.shuffle(mRoundsNumberSet);


        for(mNumber=0;mNumber<mRoundsNumberSet.size();mNumber++) {
            if (Integer.parseInt(mCurrentGSP.get("current_level")) <= 2) {
                if (mNumber % 2 == 0) {
                    mRoundsNumberSet.get(mNumber).put("is_number", "0");
                } else {
                    mRoundsNumberSet.get(mNumber).put("is_number", "1");
                }
            } else {
                mRoundsNumberSet.get(mNumber).put("is_number", "1");
            }
        }

        getLoaderManager().restartLoader(Constants.EXTRA_MATH_NUMBERS, null, this);


        //beginRound();

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void processExtraData(Cursor mCursor){
        mExtraNumberSet=new ArrayList<>();
        int mNumber=0;
        int mNumberCorrectInARow=Constants.INA_ROW_CORRECT;
        if(mCursor.moveToFirst()) {
            do  {
                mExtraNumberSet.add(new HashMap<String,String>());
                mExtraNumberSet.get(mNumber).put("correct_guess", "0");
                mExtraNumberSet.get(mNumber).put("guessed_yet", "0");
                mExtraNumberSet.get(mNumber).put("math_numbers_id",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_id"))); //0
                mExtraNumberSet.get(mNumber).put("math_number",
                        mCursor.getString(mCursor.getColumnIndex("math_number"))); //1
                mExtraNumberSet.get(mNumber).put("math_number_style",
                        mCursor.getString(mCursor.getColumnIndex("math_number_style"))); //2
                mExtraNumberSet.get(mNumber).put("math_numbers_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_audio"))); //3

                mExtraNumberSet.get(mNumber).put("number_correct",
                        mCursor.getString(mCursor.getColumnIndex("number_correct"))); //5
                mExtraNumberSet.get(mNumber).put("number_correct_in_a_row",
                        mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
                mExtraNumberSet.get(mNumber).put("number_incorrect",
                        mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //7
                mExtraNumberSet.get(mNumber).put("is_number", "1");
                mNumberCorrectInARow = (mNumberCorrectInARow > Integer.parseInt(
                        mExtraNumberSet.get(mNumber).get("number_correct_in_a_row")))
                        ? Integer.parseInt(
                        mExtraNumberSet.get(mNumber).get("number_correct_in_a_row"))
                        : mNumberCorrectInARow;

                mNumber++;

            }while(mCursor.moveToNext());
        }else{
            moveBackwords();
        }

        Collections.shuffle(mExtraNumberSet);

        
        beginRound();
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void viewProgressBars(){

        findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);

        findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
        findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);

    }



    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] mData;
        String mWhere="";
        String mOrderBy;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.MATH_NUMBERS_SET:{

                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "0"}; //6
                mOrderBy="app_users_activity_variable_values.number_correct_in_a_row ASC, ";
                if(Integer.parseInt(mCurrentGSP.get("current_level"))<=2) {
                    mWhere = "AND math_numbers.math_number!=0 ";
                }
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_NUMBERS,
                        mData, mWhere, null, mOrderBy);
                break;

            }
            case Constants.EXTRA_MATH_NUMBERS:{

                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "1"}; //6
                mOrderBy="app_users_activity_variable_values.number_correct_in_a_row ASC, ";
                if(Integer.parseInt(mCurrentGSP.get("current_level"))<=2) {
                    mWhere = "AND math_numbers.math_number!=0 ";
                }
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_NUMBERS,
                        mData, mWhere, null, mOrderBy);
                break;

            }
        }

        return cursorLoader;
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
    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
                break;
            case Constants.MATH_NUMBERS_SET:{
                processData(data);
                break;
            }
            case Constants.EXTRA_MATH_NUMBERS:{

                processExtraData(data);
                break;
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }
}
