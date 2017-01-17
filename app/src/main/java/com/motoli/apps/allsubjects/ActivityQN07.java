package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 5/18/2016.
 */
public class ActivityQN07 extends ActivityQNRoot
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private  HashMap<Integer,Integer>  mGuessedOrderedNumbers;
    

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_qn07);

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mActivityNumber=7;
        appData.addToClassOrder(6);
        mBeingValidated=true;
        mInstructionAudio="info_qn07";
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off);
       
        setUpListeners();
        setupFrameListens();

        clearShapeBoxes();
        clearNumbers();
        blackText();
        mRoundNumber=0;

        ((TextView)findViewById(R.id.orderedNumber1)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.orderedNumber2)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.orderedNumber3)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.orderedNumber4)).setTypeface(appData.getNumberFontTypeface());

        ((TextView)findViewById(R.id.number1)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number2)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number3)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number4)).setTypeface(appData.getNumberFontTypeface());


        mValidateAvailable=false;
        mBeingValidated=false;

        startActivityHandler.postDelayed(startActivity, 700);
    }//end public void onCreate(Bundle savedInstanceState) {

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.MATH_NUMBERS_SET, null, this);
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    private void clearNumbers(){
        ((TextView) findViewById(R.id.orderedNumber1)).setText("");
        ((TextView) findViewById(R.id.orderedNumber2)).setText("");
        ((TextView) findViewById(R.id.orderedNumber3)).setText("");
        ((TextView) findViewById(R.id.orderedNumber4)).setText("");

        ((TextView) findViewById(R.id.number1)).setText("");
        ((TextView) findViewById(R.id.number2)).setText("");
        ((TextView) findViewById(R.id.number3)).setText("");
        ((TextView) findViewById(R.id.number4)).setText("");

        findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
        findViewById(R.id.number2).setVisibility(TextView.VISIBLE);
        findViewById(R.id.number3).setVisibility(TextView.VISIBLE);
        findViewById(R.id.number4).setVisibility(TextView.VISIBLE);

    }

    private void blackText(){
        ((TextView) findViewById(R.id.orderedNumber1)).setTextColor(getResources()
                .getColor(R.color.regularBlack));
        ((TextView) findViewById(R.id.orderedNumber2)).setTextColor(getResources()
                .getColor(R.color.regularBlack));
        ((TextView) findViewById(R.id.orderedNumber3)).setTextColor(getResources()
                .getColor(R.color.regularBlack));
        ((TextView) findViewById(R.id.orderedNumber4)).setTextColor(getResources()
                .getColor(R.color.regularBlack));
    }
    //////////////////////////////////////////////////////////////////////////////////////////

    private void clearShapeBoxes(){
        ((ImageView) findViewById(R.id.frame1)).setImageResource(R.drawable.frm_qn06_off);
        ((ImageView) findViewById(R.id.frame2)).setImageResource(R.drawable.frm_qn06_off);
        ((ImageView) findViewById(R.id.frame3)).setImageResource(R.drawable.frm_qn06_off);
        ((ImageView) findViewById(R.id.frame4)).setImageResource(R.drawable.frm_qn06_off);
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){

        mGuessedOrderedNumbers=new  HashMap<>();
        mGuessedOrderedNumbers.put(0,-1);
        mGuessedOrderedNumbers.put(1,-1);
        mGuessedOrderedNumbers.put(2,-1);
        mGuessedOrderedNumbers.put(3,-1);

        Collections.shuffle(mRoundNumberSet);

        Log.d(Constants.LOGCAT,"mRoundNumberSet.get(0).get('math_number')"
                +mRoundNumberSet.get(0).get("math_number"));
        Log.d(Constants.LOGCAT,"mRoundNumberSet.get(1).get('math_number')"
                +mRoundNumberSet.get(1).get("math_number"));
        Log.d(Constants.LOGCAT,"mRoundNumberSet.get(2).get('math_number')"
                +mRoundNumberSet.get(2).get("math_number"));
        Log.d(Constants.LOGCAT,"mRoundNumberSet.get(3).get('math_number')"
                +mRoundNumberSet.get(3).get("math_number"));

        ((TextView) findViewById(R.id.number1)).setText(mRoundNumberSet.get(0).get("math_number"));
        ((TextView) findViewById(R.id.number2)).setText(mRoundNumberSet.get(1).get("math_number"));
        ((TextView) findViewById(R.id.number3)).setText(mRoundNumberSet.get(2).get("math_number"));
        ((TextView) findViewById(R.id.number4)).setText(mRoundNumberSet.get(3).get("math_number"));

        findViewById(R.id.number1).setTag(mRoundNumberSet.get(0));
        findViewById(R.id.number2).setTag(mRoundNumberSet.get(1));
        findViewById(R.id.number3).setTag(mRoundNumberSet.get(2));
        findViewById(R.id.number4).setTag(mRoundNumberSet.get(3));

        mBeingValidated=false;
        if(mRoundNumber==0) {
            playInstructionAudio();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private void colorLetters(){
        if(mCorrect){
            ((TextView) findViewById(R.id.orderedNumber1)).setTextColor(getResources()
                    .getColor(R.color.correct_green));
            ((TextView) findViewById(R.id.orderedNumber2)).setTextColor(getResources()
                    .getColor(R.color.correct_green));
            ((TextView) findViewById(R.id.orderedNumber3)).setTextColor(getResources()
                    .getColor(R.color.correct_green));
            ((TextView) findViewById(R.id.orderedNumber4)).setTextColor(getResources()
                    .getColor(R.color.correct_green));

        }else {
            ((TextView) findViewById(R.id.orderedNumber1)).setTextColor(getResources()
                    .getColor(R.color.incorrect_red));
            ((TextView) findViewById(R.id.orderedNumber2)).setTextColor(getResources()
                    .getColor(R.color.incorrect_red));
            ((TextView) findViewById(R.id.orderedNumber3)).setTextColor(getResources()
                    .getColor(R.color.incorrect_red));
            ((TextView) findViewById(R.id.orderedNumber4)).setTextColor(getResources()
                    .getColor(R.color.incorrect_red));
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void validate() {
        mCorrect=false;
        for(int i=0; i<4;i++) {
            if(mCurrentNumbers.get(i).equals(mGuessedOrderedNumbers.get(i))){
                mCorrect=true;
            }else{
                mNumberTotalIncorrect++;
                mCorrect=false;
                break;
            }
        }

        colorLetters();



        super.validate();

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void unPlaceInOrderedNumber(boolean mEntireSet){
        for(int i=3;i>=0;i--){
            if(mGuessedOrderedNumbers.get(i)!=-1) {
                mNumberGuessed--;
                mGuessedOrderedNumbers.put(i,-1);
                mRoundNumberSet.get(mCurrentLocation).put("guessed_yet","0");

                switch(i){
                    default:
                    case 0:{
                        ((TextView) findViewById(R.id.orderedNumber1)).setText("");
                        break;
                    }
                    case 1:{
                        ((TextView) findViewById(R.id.orderedNumber2)).setText("");
                        break;
                    }
                    case 2:{
                        ((TextView) findViewById(R.id.orderedNumber3)).setText("");
                        break;
                    }
                    case 3:{
                        ((TextView) findViewById(R.id.orderedNumber4)).setText("");
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        mValidateAvailable = false;
                        break;
                    }
                }
                if(!mEntireSet) {
                    break;
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void placeInOrderedNumbers(int mLocation){
        
        for(int i=0;i<=3;i++){
            if(mGuessedOrderedNumbers.get(i)==-1) {
                mNumberGuessed++;
                mGuessedOrderedNumbers.put(i,
                        Integer.parseInt(mRoundNumberSet.get(mLocation).get("math_number")));
                mRoundNumberSet.get(mLocation).put("guessed_yet","1");
                switch(i){
                    default:
                    case 0:{
                        findViewById(R.id.orderedNumber1).setTag(mLocation);
                        ((TextView) findViewById(R.id.orderedNumber1))
                                .setText(mRoundNumberSet.get(mLocation).get("math_number"));
                        break;
                    }
                    case 1:{
                        findViewById(R.id.orderedNumber2).setTag(mLocation);
                        ((TextView) findViewById(R.id.orderedNumber2))
                                .setText(mRoundNumberSet.get(mLocation).get("math_number"));
                        break;
                    }
                    case 2:{
                        findViewById(R.id.orderedNumber3).setTag(mLocation);
                        ((TextView) findViewById(R.id.orderedNumber3))
                                .setText(mRoundNumberSet.get(mLocation).get("math_number"));
                        break;
                    }
                    case 3:{
                        findViewById(R.id.orderedNumber4).setTag(mLocation);
                        ((TextView) findViewById(R.id.orderedNumber4))
                                .setText(mRoundNumberSet.get(mLocation).get("math_number"));
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_on);
                        mValidateAvailable = true;
                        break;
                    }
                }
                break;
            }
        }

    }


    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){
        guessHandler.postDelayed(processGuess, mAudioDuration);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens() {

        final int mClearTime=500;

        findViewById(R.id.orderedNumber1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!((TextView) findViewById(R.id.orderedNumber1)).getText().equals("")
                        &&!mBeingValidated && mNumberGuessed==1) {
                    mBeingValidated=true;
                    mCurrentLocation = Integer.parseInt(
                            findViewById(R.id.orderedNumber1).getTag().toString());
                    clearShapeBoxes();
                    unPlaceInOrderedNumber(false);
                    validateHandler.postDelayed(clearUnclearFramesTimer, mClearTime);

                }
            }
        });


        findViewById(R.id.orderedNumber2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!((TextView) findViewById(R.id.orderedNumber2)).getText().equals("")
                        &&!mBeingValidated  && mNumberGuessed==2) {
                    mCurrentLocation = Integer.parseInt(
                            findViewById(R.id.orderedNumber2).getTag().toString());
                    clearShapeBoxes();
                    unPlaceInOrderedNumber(false);
                    validateHandler.postDelayed(clearUnclearFramesTimer, mClearTime);

                }
            }
        });

        findViewById(R.id.orderedNumber3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!((TextView) findViewById(R.id.orderedNumber3)).getText().equals("")
                        &&!mBeingValidated  && mNumberGuessed==3) {
                    mBeingValidated=true;
                    mCurrentLocation = Integer.parseInt(
                            findViewById(R.id.orderedNumber3).getTag().toString());
                    clearShapeBoxes();
                    unPlaceInOrderedNumber(false);
                    validateHandler.postDelayed(clearUnclearFramesTimer, mClearTime);

                }
            }
        });

        findViewById(R.id.orderedNumber4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!((TextView) findViewById(R.id.orderedNumber4)).getText().equals("")
                        &&!mBeingValidated  && mNumberGuessed==4) {
                    mBeingValidated=true;
                    mCurrentLocation = Integer.parseInt(
                            findViewById(R.id.orderedNumber4).getTag().toString());
                    clearShapeBoxes();
                    unPlaceInOrderedNumber(false);
                    validateHandler.postDelayed(clearUnclearFramesTimer, mClearTime);

                }
            }
        });

        findViewById(R.id.frame1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRoundNumberSet.get(0).get("guessed_yet").equals("0")
                        &&!mBeingValidated) {
                    mBeingValidated=true;
                    mCurrentLocation = 0;
                    playGeneralAudio(mRoundNumberSet.get(0).get("math_numbers_audio"));;
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.frame1))
                            .setImageResource(R.drawable.frm_qn06_on);
                    placeInOrderedNumbers(0);
                    validateHandler.postDelayed(clearUnclearFramesTimer, mClearTime);

                }
            }
        });

        findViewById(R.id.frame2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRoundNumberSet.get(1).get("guessed_yet").equals("0")
                        &&!mBeingValidated) {
                    mBeingValidated=true;
                    mCurrentLocation = 1;
                    playGeneralAudio(mRoundNumberSet.get(1).get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.frame2))
                            .setImageResource(R.drawable.frm_qn06_on);
                    placeInOrderedNumbers(1);
                    validateHandler.postDelayed(clearUnclearFramesTimer, mClearTime);
                }
            }
        });

        findViewById(R.id.frame3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRoundNumberSet.get(2).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mBeingValidated=true;
                    mCurrentLocation =2;
                    playGeneralAudio(mRoundNumberSet.get(2).get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.frame3))
                            .setImageResource(R.drawable.frm_qn06_on);
                    placeInOrderedNumbers(2);
                    validateHandler.postDelayed(clearUnclearFramesTimer, mClearTime);
                }
            }
        });

        findViewById(R.id.frame4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRoundNumberSet.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mBeingValidated=true;
                    mCurrentLocation =3;
                    playGeneralAudio(mRoundNumberSet.get(3).get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.frame4))
                            .setImageResource(R.drawable.frm_qn06_on);
                    placeInOrderedNumbers(3);
                    validateHandler.postDelayed(clearUnclearFramesTimer, mClearTime);
                }
            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void unHideNumbers(){
        for(int i=0; i<=3; i++) {
            mRoundNumberSet.get(i).put("guessed_yet", "0");
        }
        findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
        findViewById(R.id.number2).setVisibility(TextView.VISIBLE);
        findViewById(R.id.number3).setVisibility(TextView.VISIBLE);
        findViewById(R.id.number4).setVisibility(TextView.VISIBLE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void clearUnclearFrames(){
        switch(mCurrentLocation){
            default:
            case 0:{
                ((ImageView) findViewById(R.id.frame1))
                        .setImageResource(R.drawable.frm_qn06_off);
                if(findViewById(R.id.number1).getVisibility() == TextView.VISIBLE) {
                    findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
                }else{
                    findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
                }
                break;
            }
            case 1:{
                ((ImageView) findViewById(R.id.frame2))
                        .setImageResource(R.drawable.frm_qn06_off);
                if(findViewById(R.id.number2).getVisibility() == TextView.VISIBLE) {
                    findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
                }else{
                    findViewById(R.id.number2).setVisibility(TextView.VISIBLE);
                }
                break;
            }
            case 2:{
                ((ImageView) findViewById(R.id.frame3))
                        .setImageResource(R.drawable.frm_qn06_off);
                if(findViewById(R.id.number3).getVisibility() == TextView.VISIBLE) {
                    findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
                }else{
                    findViewById(R.id.number3).setVisibility(TextView.VISIBLE);
                }
                break;
            }
            case 3:{
                ((ImageView) findViewById(R.id.frame4))
                        .setImageResource(R.drawable.frm_qn06_off);
                if(findViewById(R.id.number4).getVisibility() == TextView.VISIBLE) {
                    findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
                }else{
                    findViewById(R.id.number4).setVisibility(TextView.VISIBLE);
                }
                break;
            }
        }
        mBeingValidated=false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private Runnable clearUnclearFramesTimer = new Runnable() {
        @Override
        public void run() {
            clearUnclearFrames();
        }
    };

    /////////////////////////////////////////////////////////////////////////////////////////

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
                    mAudioDuration=0;
                    //playGeneralAudio(mCurrentNumberAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    mBeingValidated=false;
                    mValidateAvailable=false;
                    guessHandler.removeCallbacks(processGuess);
                    clearShapeBoxes();
                    blackText();

                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off);
                    if(mCorrect){
                        mRoundNumber++;
                        clearNumbers();
                        if(mRoundNumber!=(mCurrentNumberSet.size())){

                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 10);
                        }else{
                            if(mNumberTotalIncorrect==0){
                                addPointToAllAvailableIfNoIncorrect();
                            }
                            mLastActivityData=0;
                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    } else {
                        unHideNumbers();
                        unPlaceInOrderedNumber(true);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                }
                case 3: {
                    clearNumbers();
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };

    /////////////////////////////////////////////////////////////////////////////////////////

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
        String mWhere = " ";
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
                mData[6]="9";
                mOrderBy="";
                mWhere+=args.getString("where")+" ";
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
