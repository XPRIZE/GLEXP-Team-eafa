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
import java.util.HashMap;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/28/2015.
 */
public class ActivityQN06 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private ArrayList<HashMap<String,String>> mExtraNumberSet;
    private ArrayList<HashMap<String,String>> mRoundsNumberSet;
    private ArrayList<HashMap<String,String>>  mCurrentNumberSet;



    private int mIncorrectInRound=0;
    private int mRound=0;
    private int mColor=0;
    private int mCurrentLocation=0;

    private String mCurrentMathNumberId="0";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_qn06);

        appData.addToClassOrder(6);
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());


        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off);
        mInstructionAudio="info_qn06";
        ((TextView)findViewById(R.id.number1)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.number2)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.number3)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.number4)).setTypeface(appData.getCurrentFontType());

        eraseNumbersAndShapes();

        viewProgressBars();

        setUpListeners();

        mRound=0;

        mValidateAvailable=false;
        mBeingValidated=false;

        getLoaderManager().initLoader(Constants.MATH_NUMBERS_SET, null, this);
    }


    ////////////////////////////////////////////////////////////////////////////////////////

    protected void beginRound(){

        hideProgressBars();

        Collections.shuffle(mExtraNumberSet);

        mCurrentMathNumberId=mRoundsNumberSet.get(mRound).get("math_numbers_id");

        int mSpot=0;
        mCurrentNumberSet = new ArrayList<>();

        do{
            if(!mExtraNumberSet.get(mSpot).get("math_numbers_id")
                    .equals(mRoundsNumberSet.get(mRound).get("math_numbers_id"))){
                mExtraNumberSet.get(mSpot).put("guessed_yet", "0");
                mCurrentNumberSet.add(mExtraNumberSet.get(mSpot));
            }

            if(mCurrentNumberSet.size()>=3){
                break;
            }
            mSpot++;
        }while(mSpot<mRoundsNumberSet.size());

        mRoundsNumberSet.get(mRound).put("correct_guess", "1");
        mRoundsNumberSet.get(mRound).put("guessed_yet", "0");
        mCurrentNumberSet.add(mRoundsNumberSet.get(mRound));

        Collections.shuffle(mCurrentNumberSet);

        for(int i=0;i<4;i++){
            if (mCurrentNumberSet.get(i).get("correct_guess").equals("1")) {
                mCorrectLocation = i;
                placeNumberDots(i);
            }

            switch(i){
                default:
                case 0:{
                    ((TextView) findViewById(R.id.number1))
                            .setText(mCurrentNumberSet.get(i).get("math_number"));
                    findViewById(R.id.number1).setTag(mCurrentNumberSet.get(i));
                    findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
                    ((TextView) findViewById(R.id.number1))
                            .setTextColor(ContextCompat.getColor(this,R.color.regularBlack));
                    break;
                }
                case 1:{
                    ((TextView) findViewById(R.id.number2))
                            .setText(mCurrentNumberSet.get(i).get("math_number"));
                    findViewById(R.id.number2).setTag(mCurrentNumberSet.get(i));
                    findViewById(R.id.number2).setVisibility(TextView.VISIBLE);
                    ((TextView) findViewById(R.id.number2))
                            .setTextColor(ContextCompat.getColor(this,R.color.regularBlack));
                    break;
                }
                case 2:{
                    ((TextView) findViewById(R.id.number3))
                            .setText(mCurrentNumberSet.get(i).get("math_number"));
                    findViewById(R.id.number3).setTag(mCurrentNumberSet.get(i));
                    findViewById(R.id.number3).setVisibility(TextView.VISIBLE);
                    ((TextView) findViewById(R.id.number3))
                            .setTextColor(ContextCompat.getColor(this,R.color.regularBlack));
                    break;
                }
                case 3:{
                    ((TextView) findViewById(R.id.number4))
                            .setText(mCurrentNumberSet.get(i).get("math_number"));
                    findViewById(R.id.number4).setTag(mCurrentNumberSet.get(i));
                    findViewById(R.id.number4).setVisibility(TextView.VISIBLE);
                    ((TextView) findViewById(R.id.number4))
                            .setTextColor(ContextCompat.getColor(this,R.color.regularBlack));
                    break;
                }
            }

        }


        if(mRound==0)
            playInstructionAudio();


        setupFrameListens();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void placeNumberDots(int shapeID){
        CanvasViewDots customCanvasDots;

        findViewById(R.id.shape0).setVisibility(CanvasViewDots.VISIBLE);
        customCanvasDots  = (CanvasViewDots) findViewById(R.id.shape0);

        customCanvasDots.clearCanvas();

        customCanvasDots.setNumberOfDots(
                Integer.parseInt(mRoundsNumberSet.get(mRound).get("math_number")));
        customCanvasDots.setColor(mColor);
        customCanvasDots.invalidate();

        customCanvasDots.setTag(mCurrentNumberSet.get(shapeID));

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private void eraseNumbersAndShapes(){
        ((TextView) findViewById(R.id.number1)).setText("");
        ((TextView) findViewById(R.id.number2)).setText("");
        ((TextView) findViewById(R.id.number3)).setText("");
        ((TextView) findViewById(R.id.number4)).setText("");

        ((CanvasViewDots) findViewById(R.id.shape0)).clearCanvas();

        clearShapeBoxes();

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void clearShapeBoxes(){
        ((ImageView)findViewById(R.id.numberBox1)).setImageResource(R.drawable.frm_qn06_off);
        ((ImageView)findViewById(R.id.numberBox2)).setImageResource(R.drawable.frm_qn06_off);
        ((ImageView) findViewById(R.id.numberBox3)).setImageResource(R.drawable.frm_qn06_off);
        ((ImageView) findViewById(R.id.numberBox4)).setImageResource(R.drawable.frm_qn06_off);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void viewProgressBars(){

        findViewById(R.id.shape0).setVisibility(CanvasViewDots.INVISIBLE);

        findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void hideProgressBars(){
        findViewById(R.id.shape0).setVisibility(CanvasViewDots.VISIBLE);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            clearWrong();
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
        }
        processFontColorAndPoints();
        colorDotsIfCorrect();
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }
////////////////////////////////////////////////////////////////////////////////////////////

    private void processFontColorAndPoints(){
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCurrentMathNumberId
                +" AND activityt_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        if(mCorrect){
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentMathNumberId,
                    String.valueOf(mIncorrectInRound),
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            switch(mCurrentLocation){
                default:
                case 0:
                    mCurrentNumberSet.get(0).put("guessed_yet", "1");
                    break;
                case 1:
                    mCurrentNumberSet.get(1).put("guessed_yet", "1");
                    break;
                case 2:
                    mCurrentNumberSet.get(2).put("guessed_yet", "1");
                    break;
                case 3:
                    mCurrentNumberSet.get(3).put("guessed_yet", "1");
                    break;
            }


        }else{
            mIncorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCurrentMathNumberId,
                    String.valueOf(mIncorrectInRound),
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        mCurrentNumberSet.get(1).put("guessed_yet", "1");
                        mCurrentNumberSet.get(2).put("guessed_yet", "1");
                        mCurrentNumberSet.get(3).put("guessed_yet", "1");
                        break;
                    case 1:
                        mCurrentNumberSet.get(0).put("guessed_yet", "1");
                        mCurrentNumberSet.get(2).put("guessed_yet", "1");
                        mCurrentNumberSet.get(3).put("guessed_yet", "1");
                        break;
                    case 2:
                        mCurrentNumberSet.get(1).put("guessed_yet", "1");
                        mCurrentNumberSet.get(0).put("guessed_yet", "1");
                        mCurrentNumberSet.get(3).put("guessed_yet", "1");
                        break;
                    case 3:
                        mCurrentNumberSet.get(1).put("guessed_yet", "1");
                        mCurrentNumberSet.get(2).put("guessed_yet", "1");
                        mCurrentNumberSet.get(0).put("guessed_yet", "1");
                        break;
                }
            }
            switch(mCurrentLocation){
                default:
                case 0:
                    mCurrentNumberSet.get(0).put("guessed_yet", "1");
                    break;
                case 1:
                    mCurrentNumberSet.get(1).put("guessed_yet", "1");
                    break;
                case 2:
                    mCurrentNumberSet.get(2).put("guessed_yet", "1");
                    break;
                case 3:
                    mCurrentNumberSet.get(3).put("guessed_yet", "1");
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
                    ((TextView) findViewById(R.id.number2)).setText("");
                    ((TextView) findViewById(R.id.number3)).setText("");
                    ((TextView) findViewById(R.id.number4)).setText("");
                    break;
                case 1:
                    ((TextView) findViewById(R.id.number1)).setText("");
                    ((TextView) findViewById(R.id.number3)).setText("");
                    ((TextView) findViewById(R.id.number4)).setText("");
                    break;
                case 2:
                    ((TextView) findViewById(R.id.number2)).setText("");
                    ((TextView) findViewById(R.id.number1)).setText("");
                    ((TextView) findViewById(R.id.number4)).setText("");
                    break;
                case 3:
                    ((TextView) findViewById(R.id.number2)).setText("");
                    ((TextView) findViewById(R.id.number3)).setText("");
                    ((TextView) findViewById(R.id.number1)).setText("");
                    break;
            }
        }else{
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.number2)).setText("");
                        ((TextView) findViewById(R.id.number3)).setText("");
                        ((TextView) findViewById(R.id.number4)).setText("");
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.number1)).setText("");
                        ((TextView) findViewById(R.id.number3)).setText("");
                        ((TextView) findViewById(R.id.number4)).setText("");
                        break;
                    case 2:
                        ((TextView) findViewById(R.id.number2)).setText("");
                        ((TextView) findViewById(R.id.number1)).setText("");
                        ((TextView) findViewById(R.id.number4)).setText("");
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.number2)).setText("");
                        ((TextView) findViewById(R.id.number3)).setText("");
                        ((TextView) findViewById(R.id.number1)).setText("");
                        break;
                }
            }

            switch(mCurrentLocation){
                default:
                case 0:
                    ((TextView) findViewById(R.id.number1)).setText("");

                    break;
                case 1:
                    ((TextView) findViewById(R.id.number2)).setText("");
                    break;
                case 2:
                    ((TextView) findViewById(R.id.number3)).setText("");
                    break;
                case 3:
                    ((TextView) findViewById(R.id.number4)).setText("");

                    break;
            }
            //processLayoutAfterGuess(false);

        }
    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void colorDotsIfCorrect(){
        switch(mCurrentLocation){
            default:
            case 0:
                if (mCorrect) {
                    ((TextView) findViewById(R.id.number1))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                }else{
                    ((TextView) findViewById(R.id.number1))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                }
                break;
            case 1:
                if (mCorrect) {
                    ((TextView) findViewById(R.id.number2))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                }else{
                    ((TextView) findViewById(R.id.number2))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                }
                break;
            case 2:
                if (mCorrect) {
                    ((TextView) findViewById(R.id.number3))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                }else{
                    ((TextView) findViewById(R.id.number3))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                }
                break;
            case 3:
                if (mCorrect) {
                    ((TextView) findViewById(R.id.number4))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                }else{
                    ((TextView) findViewById(R.id.number5))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                }
                break;
        }
        if(mCorrect){
            clearWrong();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////

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

                mNumberCorrectInARow = (mNumberCorrectInARow > Integer.parseInt(
                        mExtraNumberSet.get(mNumber).get("number_correct_in_a_row")))
                        ? Integer.parseInt(
                        mExtraNumberSet.get(mNumber).get("number_correct_in_a_row"))
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



        Collections.shuffle(mExtraNumberSet);

        beginRound();
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

        getLoaderManager().restartLoader(Constants.EXTRA_MATH_NUMBERS, null, this);
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



    ///////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens() {

        findViewById(R.id.shape0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playGeneralAudio((String)((HashMap) findViewById(R.id.shape0).getTag())
                        .get("math_numbers_audio"));
            }
        });
        findViewById(R.id.numberBox1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentNumberSet.get(0).get("guessed_yet").equals("0")
                        &&!mBeingValidated) {
                    mCurrentLocation = 0;
                    playGeneralAudio((String)((HashMap) findViewById(R.id.number1).getTag())
                            .get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.numberBox1))
                            .setImageResource(R.drawable.frm_qn06_on);


                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mCorrect = ((HashMap) findViewById(R.id.number1).getTag())
                            .get("math_numbers_id").equals(mRoundsNumberSet.get(mRound)
                                    .get("math_numbers_id"));
                    mValidateAvailable = true;
                }
            }
        });

        findViewById(R.id.numberBox2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentNumberSet.get(1).get("guessed_yet").equals("0")
                        &&!mBeingValidated) {
                    mCurrentLocation = 1;
                    playGeneralAudio((String) ((HashMap) findViewById(R.id.number2).getTag())
                            .get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.numberBox2))
                            .setImageResource(R.drawable.frm_qn06_on);
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mCorrect = ((HashMap) findViewById(R.id.number2).getTag())
                            .get("math_numbers_id").equals(mRoundsNumberSet.get(mRound)
                                    .get("math_numbers_id"));
                    mValidateAvailable = true;
                }
            }
        });

        findViewById(R.id.numberBox3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentNumberSet.get(2).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation =2;
                    playGeneralAudio((String)((HashMap) findViewById(R.id.number3).getTag())
                            .get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.numberBox3))
                            .setImageResource(R.drawable.frm_qn06_on);
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mCorrect = ((HashMap) findViewById(R.id.number3).getTag())
                            .get("math_numbers_id").equals(mRoundsNumberSet.get(mRound)
                                    .get("math_numbers_id"));
                    mValidateAvailable = true;
                }
            }
        });

        findViewById(R.id.numberBox4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentNumberSet.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation =3;
                    playGeneralAudio((String)((HashMap) findViewById(R.id.number4).getTag())
                            .get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.numberBox4))
                            .setImageResource(R.drawable.frm_qn06_on);
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mCorrect = ((HashMap) findViewById(R.id.number4).getTag())
                            .get("math_numbers_id").equals(mRoundsNumberSet.get(mRound)
                                    .get("math_numbers_id"));
                    mValidateAvailable = true;
                }
            }
        });

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
                    mAudioDuration=0;
                    //playGeneralAudio(mCurrentNumberAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+300);
                    break;
                }
                case 2:{
                    mColor=0;
                    clearWrong();
                    mBeingValidated=false;
                    mValidateAvailable=false;
                    guessHandler.removeCallbacks(processGuess);
                    processChoiceView();
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off);
                    if(mCorrect){
                        mRound++;
                        eraseNumbersAndShapes();
                        if(mRound!=(mRoundsNumberSet.size())){



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
                    } else {
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                }
                case 3: {
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };

    //////////////////////////////////////////////////////////////////////////////////////////

    private void clearWrong(){
        if(mCorrect){
            switch(mCorrectLocation){
                default:
                case 0:
                    ((TextView) findViewById(R.id.number2)).setText("");
                    ((TextView) findViewById(R.id.number3)).setText("");
                    ((TextView) findViewById(R.id.number4)).setText("");

                    break;
                case 1:
                    ((TextView) findViewById(R.id.number1)).setText("");
                    ((TextView) findViewById(R.id.number3)).setText("");
                    ((TextView) findViewById(R.id.number4)).setText("");
                    break;
                case 2:
                    ((TextView) findViewById(R.id.number2)).setText("");
                    ((TextView) findViewById(R.id.number1)).setText("");
                    ((TextView) findViewById(R.id.number4)).setText("");

                    break;
                case 3:
                    ((TextView) findViewById(R.id.number2)).setText("");
                    ((TextView) findViewById(R.id.number3)).setText("");
                    ((TextView) findViewById(R.id.number1)).setText("");

                    break;
            }
        }else {
            switch (mCurrentLocation) {
                default:
                case 0:
                    ((TextView) findViewById(R.id.number1)).setText("");
                    break;
                case 1:
                    ((TextView) findViewById(R.id.number2)).setText("");

                    break;
                case 2:
                    ((TextView) findViewById(R.id.number3)).setText("");
                    break;
                case 3:
                    ((TextView) findViewById(R.id.number4)).setText("");
                    break;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       
        String[] mData = new String[]{
                appData.getCurrentActivity().get(0), //0
                appData.getCurrentUserID(), //1
                mCurrentGSP.get("group_id"), //2
                mCurrentGSP.get("phase_id"), //3
                mCurrentGSP.get("section_id"), //4
                mCurrentGSP.get("current_level"),//5
                "0"}; //6
        String mOrderBy = "app_users_activity_variable_values.number_correct_in_a_row ASC, ";
        String mWhere = "AND math_numbers.math_number!=0 AND math_numbers.math_number<=9 ";
        
        CursorLoader cursorLoader;
        switch(id) {
            default:
            case Constants.MATH_NUMBERS_SET: {
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_NUMBERS,
                        mData, mWhere, null, mOrderBy);
                break;
            }

            case Constants.EXTRA_MATH_NUMBERS:{
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_NUMBERS,
                        mData, mWhere, null, mOrderBy);
                break;
            }
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch(loader.getId()) {
            default:
                break;
            case Constants.MATH_NUMBERS_SET: {
                processData(data);
                break;
            }
            case Constants.EXTRA_MATH_NUMBERS: {
                processExtraData(data);
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
