package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
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
 */
public class ActivityQN02 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private int mCurrentLocation=5;
    private int mIncorrectInRound=0;
    private int mRound=0;

    
    private boolean mShowDots=false;

    private String mCurrentMathNumberId;

    private String mCurrentNumberAudio;
    private String mCorrectNumberAudio;

    protected int mNumberCorrectInRow=0;


    private String mCurrentCorrectId;


    private ArrayList<HashMap<String,String>> mCurrentNumberSet;
    private ArrayList<HashMap<String,String>> mCurrentNumberRound;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_qn02);
        appData.addToClassOrder(5);

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
        mInstructionAudio="info_qn02";
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
        mBeingValidated=true;

        clearActivity();
        hideNumbersAndShapes();
        setUpListeners();
        setupFrameListens();

        ((TextView) findViewById(R.id.number1)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number2)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number3)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number4)).setTypeface(appData.getNumberFontTypeface());


        getLoaderManager().initLoader(Constants.CURRENT_MATH_SHAPES, null, this);

    }//end public void onCreate(HashMap<String,String> savedInstanceState) {


    //////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration = super.playInstructionAudio();
        mAudioHandler.postDelayed(playNumberAudio, mAudioDuration+20);
        return mAudioDuration;
    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){


        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off_mic);

        clearFrames();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected void beginRound(){
        mCurrentLocation=5;
        mIncorrectInRound=0;
        mCurrentNumberRound=new ArrayList<>();
        mCurrentNumberRound.add(mCurrentNumberSet.get(mRound));
        mCurrentCorrectId=mCurrentNumberRound.get(0).get("math_numbers_id");
        mCorrectNumberAudio=mCurrentNumberRound.get(0).get("math_numbers_audio");
        getLoaderManager().restartLoader(Constants.EXTRA_MATH_NUMBERS, null, this);

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void finishBeginRound(Cursor mCursor){
        int mExtraCount=0;
        if(mCursor.moveToFirst()){
            do{
                mExtraCount++;
                mCurrentNumberRound.add(new HashMap<String,String>());
                mCurrentNumberRound.get(mExtraCount).put("correct_guess", "0");
                mCurrentNumberRound.get(mExtraCount).put("guessed_yet", "0");
                mCurrentNumberRound.get(mExtraCount).put("math_numbers_id",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_id"))); //0
                mCurrentNumberRound.get(mExtraCount).put("math_number",
                        mCursor.getString(mCursor.getColumnIndex("math_number"))); //1
                mCurrentNumberRound.get(mExtraCount).put("math_number_style",
                        mCursor.getString(mCursor.getColumnIndex("math_number_style"))); //2
                mCurrentNumberRound.get(mExtraCount).put("math_numbers_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_audio"))); //3

                mCurrentNumberRound.get(mExtraCount).put("number_correct",
                        mCursor.getString(mCursor.getColumnIndex("number_correct"))); //5
                mCurrentNumberRound.get(mExtraCount).put("number_correct_in_a_row",
                        mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
                mCurrentNumberRound.get(mExtraCount).put("number_incorrect",
                        mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //7
                if(mExtraCount>=3){
                    break;
                }
            }while(mCursor.moveToNext());
        }


        displayScreen();

    }
    /////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        Collections.shuffle(mCurrentNumberRound);

        hideNumbersAndShapes();


        final Random mRandom = new Random();
        int mRandomNumber=mRandom.nextInt(2);
        for (int i = 0; i < 4; i++) {
            if (mCurrentNumberRound.get(i).get("correct_guess").equals("1")) {
                mCorrectLocation = i;
            }
            if((Integer.parseInt(mCurrentGSP.get("current_level"))<=3 && mRound%2==0)
                    || mRandomNumber==0) {
                mShowDots=true;
                placingCurrentNumbersAsDots(i);
                placingCurrentNumbers(i);
                findViewById(R.id.shape1).setVisibility(MathOperationDots.VISIBLE);
                findViewById(R.id.shape2).setVisibility(MathOperationDots.VISIBLE);
                findViewById(R.id.shape3).setVisibility(MathOperationDots.VISIBLE);
                findViewById(R.id.shape4).setVisibility(MathOperationDots.VISIBLE);
            }else{
                mShowDots=false;
                placingCurrentNumbersAsDots(i);
                placingCurrentNumbers(i);
                findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
                findViewById(R.id.number2).setVisibility(TextView.VISIBLE);
                findViewById(R.id.number3).setVisibility(TextView.VISIBLE);
                findViewById(R.id.number4).setVisibility(TextView.VISIBLE);
            }
            //placingCurrentNumbers(i, true);
        }


        long mAudioDuration=0;
        if(mRound==0)
            mAudioDuration=playInstructionAudio();

        mAudioHandler.postDelayed(playNumberAudio, mAudioDuration + 20);

    }//end private void displayScreen(Cursor currentWordsCursor){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void placingCurrentNumbers(int shapeID) {
        switch (shapeID) {
            default:
            case 0: {
                ((TextView) findViewById(R.id.number1))
                        .setText(mCurrentNumberRound.get(0).get("math_number"));

                break;
            }
            case 1: {
                ((TextView) findViewById(R.id.number2))
                        .setText(mCurrentNumberRound.get(1).get("math_number"));
                break;
            }
            case 2: {
                ((TextView) findViewById(R.id.number3))
                        .setText(mCurrentNumberRound.get(2).get("math_number"));
                break;
            }
            case 3: {
                ((TextView) findViewById(R.id.number4))
                        .setText(mCurrentNumberRound.get(3).get("math_number"));
                break;
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    private void placingCurrentNumbersAsDots(int mShapeID){
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

        /**/
        customCanvasDots.clearCanvas();
        if(Integer.parseInt(mCurrentNumberRound.get(mShapeID).get("math_number"))>=4){
            if (mShapeID==mCurrentLocation) {
                customCanvasDots.setAllowTextNumbers(true);
            } else {
                customCanvasDots.setAllowTextNumbers(false);
            }
           // customCanvasDots.setAllowTens(true);
        }else{
            customCanvasDots.setAllowTextNumbers(false);
            //customCanvasDots.setAllowTens(false);
        }




        customCanvasDots.setColor(0);
        customCanvasDots.setNumberOfDots(
                Integer.parseInt(mCurrentNumberRound.get(mShapeID).get("math_number")));
   

        customCanvasDots.invalidate();


    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playNumberAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playNumberAudio);
            mBeingValidated=false;
            if(!mCorrectNumberAudio.equals(""))
                playGeneralAudio(mCorrectNumberAudio);
        }
    };



    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens(){

        findViewById(R.id.frame1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentNumberRound.get(0).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=0;
                    mCurrentNumberAudio=mCurrentNumberRound.get(0).get("math_numbers_audio");
                    mCurrentMathNumberId=mCurrentNumberRound.get(0).get("math_numbers_id");
                    mCorrect=(mCurrentNumberRound.get(0).get("correct_guess").equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.frame2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentNumberRound.get(1).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 1;
                    mCurrentNumberAudio = mCurrentNumberRound.get(1).get("math_numbers_audio");
                    mCurrentMathNumberId = mCurrentNumberRound.get(1).get("math_numbers_id");
                    mCorrect = (mCurrentNumberRound.get(1).get("correct_guess").equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.frame3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentNumberRound.get(2).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=2;
                    mCurrentNumberAudio=mCurrentNumberRound.get(2).get("math_numbers_audio");
                    mCurrentMathNumberId=mCurrentNumberRound.get(2).get("math_numbers_id");
                    mCorrect=(mCurrentNumberRound.get(2).get("correct_guess").equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.frame4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentNumberRound.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 3;
                    mCurrentNumberAudio = mCurrentNumberRound.get(3).get("math_numbers_audio");
                    mCurrentMathNumberId = mCurrentNumberRound.get(3).get("math_numbers_id");
                    mCorrect = (mCurrentNumberRound.get(3).get("correct_guess").equals("1"));
                    setUpFrame(3);
                }
            }
        });

        findViewById(R.id.shapeSpace1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentNumberRound.get(0).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=0;
                    mCurrentNumberAudio=mCurrentNumberRound.get(0).get("math_numbers_audio");
                    mCurrentMathNumberId=mCurrentNumberRound.get(0).get("math_numbers_id");
                    mCorrect=(mCurrentNumberRound.get(0).get("correct_guess").equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.shapeSpace2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentNumberRound.get(1).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 1;
                    mCurrentNumberAudio = mCurrentNumberRound.get(1).get("math_numbers_audio");
                    mCurrentMathNumberId = mCurrentNumberRound.get(1).get("math_numbers_id");
                    mCorrect = (mCurrentNumberRound.get(1).get("correct_guess").equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.shapeSpace3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentNumberRound.get(2).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=2;
                    mCurrentNumberAudio=mCurrentNumberRound.get(2).get("math_numbers_audio");
                    mCurrentMathNumberId=mCurrentNumberRound.get(2).get("math_numbers_id");
                    mCorrect=(mCurrentNumberRound.get(2).get("correct_guess").equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.shapeSpace4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentNumberRound.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 3;
                    mCurrentNumberAudio = mCurrentNumberRound.get(3).get("math_numbers_audio");
                    mCurrentMathNumberId = mCurrentNumberRound.get(3).get("math_numbers_id");
                    mCorrect = (mCurrentNumberRound.get(3).get("correct_guess").equals("1"));
                    setUpFrame(3);
                }
            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){

        if(mShowDots) {
            for (int i = 0; i < 4; i++) {
                placingCurrentNumbersAsDots(i);
            }
        }


        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_on_mic);
        mValidateAvailable=true;

        switch(frameNumber){
            default:
            case 0:{
                ((ImageView)findViewById(R.id.frame1))
                        .setImageResource(R.drawable.frm_qn02_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.frame2))
                        .setImageResource(R.drawable.frm_qn02_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.frame3))
                        .setImageResource(R.drawable.frm_qn02_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.frame4))
                        .setImageResource(R.drawable.frm_qn02_on);
                break;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void hideNumbersAndShapes(){
        findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
        findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
        findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
        findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);

        findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);

        ((TextView) findViewById(R.id.number1)).setTextColor(ContextCompat.getColor(this,
                R.color.regularBlack));
        ((TextView) findViewById(R.id.number2)).setTextColor(ContextCompat.getColor(this,
                R.color.regularBlack));
        ((TextView) findViewById(R.id.number3)).setTextColor(ContextCompat.getColor(this,
                R.color.regularBlack));
        ((TextView) findViewById(R.id.number4)).setTextColor(ContextCompat.getColor(this,
                R.color.regularBlack));

    }
    private void clearFrames(){
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_qn02_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_qn02_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_qn02_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_qn02_off);


    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        if(mCorrect) {
            mNumberCorrectInRow++;
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok_mic);
        }else{
            mNumberCorrectInRow=0;
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok_mic);
        }

        if(mNumberCorrectInRow>=mCurrentNumberSet.size()){
            addPointToAllAvailableIfNoIncorrect();
        }

        processFontColorAndPoints();
        processChoiceView();
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processFontColorAndPoints(){
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCurrentMathNumberId
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        if(mCorrect){
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok_mic);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentMathNumberId,
                    String.valueOf(mIncorrectInRound),
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
            switch(mCurrentLocation){
                default:
                case 0:
                    mCurrentNumberRound.get(0).put("guessed_yet", "1");
                    break;
                case 1:
                    mCurrentNumberRound.get(1).put("guessed_yet", "1");
                    break;
                case 2:
                    mCurrentNumberRound.get(2).put("guessed_yet", "1");
                    break;
                case 3:
                    mCurrentNumberRound.get(3).put("guessed_yet", "1");
                    break;
            }


        }else{
            mIncorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok_mic);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCurrentMathNumberId,
                    String.valueOf(mIncorrectInRound),
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        mCurrentNumberRound.get(1).put("guessed_yet", "1");
                        mCurrentNumberRound.get(2).put("guessed_yet", "1");
                        mCurrentNumberRound.get(3).put("guessed_yet", "1");
                        break;
                    case 1:
                        mCurrentNumberRound.get(0).put("guessed_yet", "1");
                        mCurrentNumberRound.get(2).put("guessed_yet", "1");
                        mCurrentNumberRound.get(3).put("guessed_yet", "1");
                        break;
                    case 2:
                        mCurrentNumberRound.get(1).put("guessed_yet", "1");
                        mCurrentNumberRound.get(0).put("guessed_yet", "1");
                        mCurrentNumberRound.get(3).put("guessed_yet", "1");
                        break;
                    case 3:
                        mCurrentNumberRound.get(1).put("guessed_yet", "1");
                        mCurrentNumberRound.get(2).put("guessed_yet", "1");
                        mCurrentNumberRound.get(0).put("guessed_yet", "1");
                        break;
                }
            }
            switch(mCurrentLocation){
                default:
                case 0:
                    mCurrentNumberRound.get(0).put("guessed_yet", "1");
                    break;
                case 1:
                    mCurrentNumberRound.get(1).put("guessed_yet", "1");
                    break;
                case 2:
                    mCurrentNumberRound.get(2).put("guessed_yet", "1");
                    break;
                case 3:
                    mCurrentNumberRound.get(3).put("guessed_yet", "1");
                    break;
            }


        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    private void processChoiceView(){
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
        }else{
            if(mIncorrectInRound>=2){
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


        }

        switch(mCurrentLocation){
            default:
            case 0:

                findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
                if(mCorrect){
                    findViewById(R.id.shape1).setVisibility(MathOperationDots.VISIBLE);
                    ((TextView) findViewById(R.id.number1)).setTextColor(
                            ContextCompat.getColor(this, R.color.correct_green));
                }else {
                    ((TextView) findViewById(R.id.number1)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                }
                break;
            case 1:

                findViewById(R.id.number2).setVisibility(TextView.VISIBLE);
                if(mCorrect){
                    findViewById(R.id.shape2).setVisibility(MathOperationDots.VISIBLE);
                    ((TextView) findViewById(R.id.number2)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                }else{
                    ((TextView) findViewById(R.id.number2)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));

                }
                break;
            case 2:

                findViewById(R.id.number3).setVisibility(TextView.VISIBLE);
                if(mCorrect){
                    findViewById(R.id.shape3).setVisibility(MathOperationDots.VISIBLE);
                    ((TextView) findViewById(R.id.number3)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                }else{

                    ((TextView) findViewById(R.id.number3)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));

                }
                break;
            case 3:

                findViewById(R.id.number4).setVisibility(TextView.VISIBLE);
                if(mCorrect){
                    findViewById(R.id.shape4).setVisibility(MathOperationDots.VISIBLE);
                    ((TextView) findViewById(R.id.number4)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                }else{
                    ((TextView) findViewById(R.id.number4)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));

                }
                break;
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private void clearWrong(){
        switch(mCurrentLocation){
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

    //////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable processGuess = new Runnable(){

        //mBeingValidated=false;


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
                    mAudioDuration=playGeneralAudio(mCurrentNumberAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    clearWrong();
                    guessHandler.removeCallbacks(processGuess);

                    clearFrames();
                    if(mCorrect){
                        mRound++;
                        hideNumbersAndShapes();

                        if(mRound!=(mCurrentNumberSet.size())){
                            //mBeingValidated=false;
                            mValidateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;

                            findViewById(R.id.activityMainPartSecond)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPartSecond)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));

                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off_mic);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                }
                case 3:{
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off_mic);
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };



    ////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] mData;
        String mWhere;
        String mOrderBy="";
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
                mWhere="AND math_numbers.math_number!=0 ";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_NUMBERS, mData, mWhere, null, mOrderBy);
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
                        "0"}; //6
                mWhere= "AND math_numbers.math_numbers_id!="+mCurrentCorrectId+" "+
                        "AND math_numbers.math_number!=0 ";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_NUMBERS, mData, mWhere, null, mOrderBy);
                break;
            }
        }

        return cursorLoader;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
            case Constants.MATH_NUMBERS_SET:{
                processData(data);
                break;
            }
            case Constants.EXTRA_MATH_NUMBERS:{
                finishBeginRound(data);
                break;
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mCurrentNumberSet=new ArrayList<>();
        int mNumber=0;
        int mNumberCorrectInARow=Constants.INA_ROW_CORRECT;
        if(mCursor.moveToFirst()) {
            do{
                mCurrentNumberSet.add(new HashMap<String,String>());
                mCurrentNumberSet.get(mNumber).put("correct_guess","1");
                mCurrentNumberSet.get(mNumber).put("guessed_yet","0");
                mCurrentNumberSet.get(mNumber).put("math_numbers_id",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_id"))); //0
                mCurrentNumberSet.get(mNumber).put("math_number",
                        mCursor.getString(mCursor.getColumnIndex("math_number"))); //1
                mCurrentNumberSet.get(mNumber).put("math_number_style",
                        mCursor.getString(mCursor.getColumnIndex("math_number_style"))); //2
                mCurrentNumberSet.get(mNumber).put("math_numbers_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_audio"))); //3
          
                mCurrentNumberSet.get(mNumber).put("number_correct",
                        mCursor.getString(mCursor.getColumnIndex("number_correct"))); //5
                mCurrentNumberSet.get(mNumber).put("number_correct_in_a_row",
                        mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
                mCurrentNumberSet.get(mNumber).put("number_incorrect",
                        mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //7

                mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(
                        mCurrentNumberSet.get(mNumber).get("number_correct_in_a_row")))
                        ? Integer.parseInt(
                        mCurrentNumberSet.get(mNumber).get("number_correct_in_a_row"))
                        :mNumberCorrectInARow;


                mNumber++;
                if (mNumber >= Constants.NUMBER_QN_VARIABLES ||
                        mCursor.isLast()){
                    break;
                }
            }while(mCursor.moveToNext());
        }



        Collections.shuffle(mCurrentNumberSet);

        if(mCurrentNumberSet.size()<Constants.NUMBER_QN_VARIABLES ) {
            for (int i = 0; i < mCurrentNumberSet.size(); i++) {
                if (!mCurrentNumberSet.get(mCurrentNumberSet.size() - 1).get("math_numbers_id")
                        .equals(mCurrentNumberSet.get(i).get("math_numbers_id"))) {
                    mCurrentNumberSet.add(new HashMap<>(mCurrentNumberSet.get(i)));
                    if (mCurrentNumberSet.size() >= Constants.NUMBER_QN_VARIABLES) {
                        break;
                    }
                    if(mCurrentNumberSet.size()<Constants.NUMBER_QN_VARIABLES &&
                            i==mCurrentNumberSet.size()-1){
                        i=-1;
                    }
                }
            }
        }


        beginRound();

    }
    ////////////////////////////////////////////////////////////////////////////////////////////



    protected void setUpListeners(){
        findViewById(R.id.btnMicDude).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCorrectNumberAudio.equals(""))
                    playGeneralAudio(mCorrectNumberAudio);
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

    //////////////////////////////////////////////////////////////////////////////////////////

}
