package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/28/2015.
 */
public class ActivityQN05 extends ActivityQNRoot
        implements LoaderManager.LoaderCallbacks<Cursor> {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_qn05);


        mActivityNumber=5;
        appData.addToClassOrder(6);
        mBeingValidated=true;
        mInstructionAudio="info_qn05";


        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off);

        eraseNumbersAndShapes();

        viewProgressBars();

        setUpListeners();

        ((TextView)findViewById(R.id.number0)).setTypeface(appData.getNumberFontTypeface());

        ((TextView)findViewById(R.id.number1)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number2)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number3)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number4)).setTypeface(appData.getNumberFontTypeface());


        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mInstructionAudio="info_qn05";
        mRoundNumber=0;

        mValidateAvailable=false;
        mBeingValidated=false;

        getLoaderManager().initLoader(Constants.MATH_NUMBERS_SET, null, this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void cleanText(){
        ((TextView) findViewById(R.id.number1)).setTextColor(
                ContextCompat.getColor(this,R.color.regularBlack));
        ((TextView) findViewById(R.id.number2)).setTextColor(
                ContextCompat.getColor(this,R.color.regularBlack));
        ((TextView) findViewById(R.id.number3)).setTextColor(
                ContextCompat.getColor(this,R.color.regularBlack));
        ((TextView) findViewById(R.id.number4)).setTextColor(
                ContextCompat.getColor(this,R.color.regularBlack));


    }

    ////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){

        cleanText();
        hideProgressBars();


        MathOperationDots mMathOperationsDots  = (MathOperationDots) findViewById(R.id.shape0);

        final Random mRandom = new Random();
        int mRandomNumber=mRandom.nextInt(10)+1;


        if(mRandomNumber>5){
            mNumberMain=true;
            Log.d(Constants.LOGCAT,"ActivityQN05 mNumberMain=true");
        }else{
            mNumberMain=false;
            Log.d(Constants.LOGCAT,"ActivityQN05 mNumberMain=false");
        }

        if( mNumberMain){
            findViewById(R.id.number0).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.shape0).setVisibility(MathOperationDots.VISIBLE);

            findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
            findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
            findViewById(R.id.number2).setVisibility(TextView.VISIBLE);
            findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
            findViewById(R.id.number3).setVisibility(TextView.VISIBLE);
            findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
            findViewById(R.id.number4).setVisibility(TextView.VISIBLE);
            findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);

        }else{
            findViewById(R.id.number0).setVisibility(TextView.VISIBLE);
            findViewById(R.id.shape0).setVisibility(MathOperationDots.INVISIBLE);
            findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.shape1).setVisibility(MathOperationDots.VISIBLE);
            findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.shape2).setVisibility(MathOperationDots.VISIBLE);
            findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.shape3).setVisibility(MathOperationDots.VISIBLE);
            findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.shape4).setVisibility(MathOperationDots.VISIBLE);
        }

        ((TextView) findViewById(R.id.number0))
                .setText(mCorrectNumberSet.get("math_number"));
        findViewById(R.id.number0).setTag(mCorrectNumberSet);
        mCorrectID=mCorrectNumberSet.get("math_numbers_id");

        mMathOperationsDots.clearCanvas();

        if( mNumberMain) {
            mMathOperationsDots.setNumberOfDots(
                    Integer.parseInt(mCorrectNumberSet.get("math_number")));
            mMathOperationsDots.setTag(mCorrectNumberSet);
        }else{
            mMathOperationsDots.setNumberOfDots(0);
        }

        mMathOperationsDots.invalidate();
        
        Collections.shuffle(mRoundNumberSet);

        for(int i=0;i<4;i++){
            if (mRoundNumberSet.get(i).get("is_correct").equals("1")) {
                mCorrectLocation = i;
            }

            placeNumbers(i);
            placeNumberDots(i);

        }

        if(mRoundNumber==0)
            playInstructionAudio();

        setupFrameListens();

    }

    /////////////////////////////////////////////////////////////////////////////////////////


    private void eraseNumbersAndShapes(){
        ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();
        ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();
        ((MathOperationDots) findViewById(R.id.shape3)).clearCanvas();
        ((MathOperationDots) findViewById(R.id.shape4)).clearCanvas();

        ((TextView) findViewById(R.id.number0)).setText("");

        clearShapeBoxes();

    }

    //////////////////////////////////////////////////////////////////////////////////////////
    
    private void clearShapeBoxes(){
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_qn05_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_qn05_off);
        ((ImageView) findViewById(R.id.frame3)).setImageResource(R.drawable.frm_qn05_off);
        ((ImageView) findViewById(R.id.frame4)).setImageResource(R.drawable.frm_qn05_off);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void viewProgressBars(){
        findViewById(R.id.number0).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.shape0).setVisibility(MathOperationDots.INVISIBLE);

        findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
        findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
        findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
        findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);

        findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void hideProgressBars(){
        findViewById(R.id.number0).setVisibility(TextView.VISIBLE);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    /*
    private void processChoiceView(){
        if(mCorrect){
            switch(mCorrectLocation){
                default:
                case 0:
                    ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();
                    ((MathOperationDots) findViewById(R.id.shape3)).clearCanvas();
                    ((MathOperationDots) findViewById(R.id.shape4)).clearCanvas();
                    break;
                case 1:
                    ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();
                    ((MathOperationDots) findViewById(R.id.shape3)).clearCanvas();
                    ((MathOperationDots) findViewById(R.id.shape4)).clearCanvas();
                    break;
                case 2:
                    ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();
                    ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();
                    ((MathOperationDots) findViewById(R.id.shape4)).clearCanvas();
                    break;
                case 3:
                    ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();
                    ((MathOperationDots) findViewById(R.id.shape3)).clearCanvas();
                    ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();
                    break;
            }
        }else{
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();
                        ((MathOperationDots) findViewById(R.id.shape3)).clearCanvas();
                        ((MathOperationDots) findViewById(R.id.shape4)).clearCanvas();
                        break;
                    case 1:
                        ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();
                        ((MathOperationDots) findViewById(R.id.shape3)).clearCanvas();
                        ((MathOperationDots) findViewById(R.id.shape4)).clearCanvas();
                        break;
                    case 2:
                        ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();
                        ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();
                        ((MathOperationDots) findViewById(R.id.shape4)).clearCanvas();
                        break;
                    case 3:
                        ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();
                        ((MathOperationDots) findViewById(R.id.shape3)).clearCanvas();
                        ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();
                        break;
                }
            }

            switch(mCurrentLocation){
                default:
                case 0:
                    ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();

                    break;
                case 1:
                    ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();
                    break;
                case 2:
                    ((MathOperationDots) findViewById(R.id.shape3)).clearCanvas();
                    break;
                case 3:
                    ((MathOperationDots) findViewById(R.id.shape4)).clearCanvas();

                    break;
            }
            //processLayoutAfterGuess(false);

        }
    }


    */
    ///////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens() {

        findViewById(R.id.number0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playGeneralAudio((String)((HashMap) findViewById(R.id.number0).getTag())
                        .get("math_numbers_audio"));
            }
        });
        findViewById(R.id.frame1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRoundNumberSet.get(0).get("guessed_yet").equals("0")
                        &&!mBeingValidated) {
                    mCurrentLocation = 0;
                    playGeneralAudio(mRoundNumberSet.get(0).get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.frame1))
                            .setImageResource(R.drawable.frm_qn05_on);
                    

                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mCorrect = mRoundNumberSet.get(0).get("is_correct").equals("1");
                    mValidateAvailable = true;
                }
            }
        });

        findViewById(R.id.frame2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRoundNumberSet.get(1).get("guessed_yet").equals("0")
                        &&!mBeingValidated) {
                    mCurrentLocation = 1;
                    playGeneralAudio(mRoundNumberSet.get(1).get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.frame2))
                            .setImageResource(R.drawable.frm_qn05_on);
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mCorrect = mRoundNumberSet.get(1).get("is_correct").equals("1");
                    mValidateAvailable = true;
                }
            }
        });

        findViewById(R.id.frame3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRoundNumberSet.get(2).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation =2;
                    playGeneralAudio(mRoundNumberSet.get(2).get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.frame3))
                            .setImageResource(R.drawable.frm_qn05_on);
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mCorrect = mRoundNumberSet.get(2).get("is_correct").equals("1");
                    mValidateAvailable = true;
                }
            }
        });

        findViewById(R.id.frame4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mRoundNumberSet.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation =3;
                    playGeneralAudio(mRoundNumberSet.get(3).get("math_numbers_audio"));
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.frame4))
                            .setImageResource(R.drawable.frm_qn05_on);
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mCorrect = mRoundNumberSet.get(3).get("is_correct").equals("1");
                    mValidateAvailable = true;
                }
            }
        });

    }
    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processTheGuess(long mAudioDuration){
        guessHandler.postDelayed(processGuess, mAudioDuration);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

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
                    mColor=0;
                    clearWrong();
                    mBeingValidated=false;
                    mValidateAvailable=false;
                    guessHandler.removeCallbacks(processGuess);
                    //processChoiceView();
                    clearShapeBoxes();
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off);
                    if(mCorrect){
                        mRoundNumber++;
                        eraseNumbersAndShapes();
                        if(mRoundNumber!=(mCurrentNumberSet.size())){



                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 10);
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
        String mWhere = "AND math_numbers.math_number!=0 ";
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
                mData[6]="1";
                mOrderBy="";
                mWhere+="AND math_numbers.math_numbers_id!="+args.getString("math_numbers_id")+" ";
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
