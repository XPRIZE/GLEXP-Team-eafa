package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class ActivityQN03 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {


    private int mCorrectNumberCount=0;
    private int mProcessGuessPosition=0;
    private int mNumbersChosen=0;

    private String mCurrentNumberID="";

    private String mCurrentNumberAudio="";
    private int mColor=0;
    private int mRound;


    private ArrayList<String> mChosenFrames;

    private ArrayList<String> mNumberStatus;

    private ArrayList<HashMap<String,String>> mCurrentNumbers;
    
    private ArrayList<ArrayList<HashMap<String,String>>> mRoundsNumberSet;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_qn03);

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mInstructionAudio="info_qn03";
        appData.addToClassOrder(6);
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());


        ((TextView)findViewById(R.id.number1)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number2)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number3)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number4)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number5)).setTypeface(appData.getNumberFontTypeface());
        ((TextView)findViewById(R.id.number6)).setTypeface(appData.getNumberFontTypeface());
        viewProgressBars();

        setUpListeners();

        mRound=0;

        mChosenFrames=new ArrayList<>();
        mChosenFrames.add("");
        mChosenFrames.add("");

        mValidateAvailable=false;
        mBeingValidated=false;

        getLoaderManager().initLoader(Constants.MATH_NUMBERS_SET, null, this);

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mCurrentLocation=5;

        numbersOrDots();
        loadFrontImages();
        unSelectedNumberBoxes();

        mColor=0;

        mNumberStatus=new ArrayList<>();
        for(int i=0; i<6;i++){
            mNumberStatus.add("0");
        }

        mNumbersChosen=0;
        mCorrectNumberCount=0;

        
        mCurrentNumbers = new ArrayList<>(mRoundsNumberSet.get(mRound));
        //for(int j=0;j<2;j++) {
        Random mRandom=new Random();
            for (int i = 0; i < 3; i++) {
                HashMap<String,String> mTempData=
                        new HashMap<>(mCurrentNumbers.get(i));
                if(Integer.parseInt(mCurrentGSP.get("current_level"))<=2) {
                    mTempData.put("is_number", "0");
                }else{
                    mTempData.put("is_number", String.valueOf(mRandom.nextInt(1)));
                }
                mCurrentNumbers.add(mTempData);

            }
        //}
        Collections.shuffle(mCurrentNumbers);
        
        if(Integer.parseInt(mCurrentGSP.get("current_level"))>2){
            resetNumbers();
        }

        findViewById(R.id.image1).setTag(mCurrentNumbers.get(0));
        findViewById(R.id.image2).setTag(mCurrentNumbers.get(1));
        findViewById(R.id.image3).setTag(mCurrentNumbers.get(2));
        findViewById(R.id.image4).setTag(mCurrentNumbers.get(3));
        findViewById(R.id.image5).setTag(mCurrentNumbers.get(4));
        findViewById(R.id.image6).setTag(mCurrentNumbers.get(5));

        for (int i = 0; i < 6; i++) {
            if(Integer.parseInt(mCurrentNumbers.get(i).get("math_number"))<=25
                    && mCurrentNumbers.get(i).get("is_number").equals("0")) {
                placingCurrentNumbersAsDots(i);
            }else{
                placingCurrentNumbers(i);
            }
        }

        findViewById(R.id.frame0).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.box1).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box2).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box3).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box4).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box5).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box6).setVisibility(RelativeLayout.VISIBLE);


        hideProgressBars();

        mValidateAvailable=false;
        mBeingValidated=false;
        setupFrameListens();


        if(mRound==0){
            playInstructionAudio();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void placingCurrentNumbers(int shapeID) {
        switch (shapeID) {
            default:
            case 0: {
                findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
                findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
                ((TextView) findViewById(R.id.number1))
                        .setText(mCurrentNumbers.get(0).get("math_number"));

                break;
            }
            case 1: {
                findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
                findViewById(R.id.number2).setVisibility(TextView.VISIBLE);
                ((TextView) findViewById(R.id.number2))
                        .setText(mCurrentNumbers.get(1).get("math_number"));
                break;
            }
            case 2: {
                findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
                findViewById(R.id.number3).setVisibility(TextView.VISIBLE);
                ((TextView) findViewById(R.id.number3))
                        .setText(mCurrentNumbers.get(2).get("math_number"));
                break;
            }
            case 3: {
                findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);
                findViewById(R.id.number4).setVisibility(TextView.VISIBLE);
                ((TextView) findViewById(R.id.number4))
                        .setText(mCurrentNumbers.get(3).get("math_number"));
                break;
            }
            case 4: {
                findViewById(R.id.shape5).setVisibility(MathOperationDots.INVISIBLE);
                findViewById(R.id.number5).setVisibility(TextView.VISIBLE);
                ((TextView) findViewById(R.id.number5))
                        .setText(mCurrentNumbers.get(4).get("math_number"));
                break;
            }
            case 5: {
                findViewById(R.id.shape6).setVisibility(MathOperationDots.INVISIBLE);
                findViewById(R.id.number6).setVisibility(TextView.VISIBLE);
                ((TextView) findViewById(R.id.number6))
                        .setText(mCurrentNumbers.get(5).get("math_number"));
                break;
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private void hideProgressBars(){
        findViewById(R.id.frame0).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.box1).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box2).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box3).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box4).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box5).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box6).setVisibility(RelativeLayout.VISIBLE);

    }

    private void viewProgressBars(){
        findViewById(R.id.frame0).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.box1).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box2).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box3).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box4).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box5).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box6).setVisibility(RelativeLayout.INVISIBLE);

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void numbersOrDots(){
        if(Integer.parseInt(mCurrentGSP.get("current_level"))<=2) {
            findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.number5).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.number6).setVisibility(TextView.INVISIBLE);

            findViewById(R.id.shape1).setVisibility(MathOperationDots.VISIBLE);
            findViewById(R.id.shape2).setVisibility(MathOperationDots.VISIBLE);
            findViewById(R.id.shape3).setVisibility(MathOperationDots.VISIBLE);
            findViewById(R.id.shape4).setVisibility(MathOperationDots.VISIBLE);
            findViewById(R.id.shape5).setVisibility(MathOperationDots.VISIBLE);
            findViewById(R.id.shape6).setVisibility(MathOperationDots.VISIBLE);

            ((MathOperationDots) findViewById(R.id.shape1)).clearCanvas();
            ((MathOperationDots) findViewById(R.id.shape2)).clearCanvas();
            ((MathOperationDots) findViewById(R.id.shape3)).clearCanvas();
            ((MathOperationDots) findViewById(R.id.shape4)).clearCanvas();
            ((MathOperationDots) findViewById(R.id.shape5)).clearCanvas();
            ((MathOperationDots) findViewById(R.id.shape6)).clearCanvas();

        }else{
            findViewById(R.id.number1).setVisibility(TextView.VISIBLE);
            findViewById(R.id.number2).setVisibility(TextView.VISIBLE);
            findViewById(R.id.number3).setVisibility(TextView.VISIBLE);
            findViewById(R.id.number4).setVisibility(TextView.VISIBLE);
            findViewById(R.id.number5).setVisibility(TextView.VISIBLE);
            findViewById(R.id.number6).setVisibility(TextView.VISIBLE);

            findViewById(R.id.shape1).setVisibility(MathOperationDots.INVISIBLE);
            findViewById(R.id.shape2).setVisibility(MathOperationDots.INVISIBLE);
            findViewById(R.id.shape3).setVisibility(MathOperationDots.INVISIBLE);
            findViewById(R.id.shape4).setVisibility(MathOperationDots.INVISIBLE);
            findViewById(R.id.shape5).setVisibility(MathOperationDots.INVISIBLE);
            findViewById(R.id.shape6).setVisibility(MathOperationDots.INVISIBLE);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////

    private void placingCurrentNumbersAsDots(int shapeID) {
        MathOperationDots customCanvasDots;
        switch (shapeID) {
            default:
            case 0: {
                findViewById(R.id.number1).setVisibility(TextView.INVISIBLE);
                findViewById(R.id.shape1).setVisibility(MathOperationDots.VISIBLE);
                customCanvasDots = (MathOperationDots) findViewById(R.id.shape1);

                break;
            }
            case 1: {
                findViewById(R.id.number2).setVisibility(TextView.INVISIBLE);
                findViewById(R.id.shape2).setVisibility(MathOperationDots.VISIBLE);
                customCanvasDots = (MathOperationDots) findViewById(R.id.shape2);
                break;
            }
            case 2: {
                findViewById(R.id.number3).setVisibility(TextView.INVISIBLE);
                findViewById(R.id.shape3).setVisibility(MathOperationDots.VISIBLE);
                customCanvasDots = (MathOperationDots) findViewById(R.id.shape3);
                break;
            }
            case 3: {
                findViewById(R.id.number4).setVisibility(TextView.INVISIBLE);
                findViewById(R.id.shape4).setVisibility(MathOperationDots.VISIBLE);
                customCanvasDots = (MathOperationDots) findViewById(R.id.shape4);
                break;
            }
            case 4: {
                findViewById(R.id.number5).setVisibility(TextView.INVISIBLE);
                findViewById(R.id.shape5).setVisibility(MathOperationDots.VISIBLE);
                customCanvasDots = (MathOperationDots) findViewById(R.id.shape5);
                break;
            }
            case 5: {
                findViewById(R.id.number6).setVisibility(TextView.INVISIBLE);
                findViewById(R.id.shape6).setVisibility(MathOperationDots.VISIBLE);
                customCanvasDots = (MathOperationDots) findViewById(R.id.shape6);
                break;
            }
        }

        customCanvasDots.clearCanvas();
        if (Integer.parseInt(mCurrentNumbers.get(shapeID).get("math_number")) >= 10) {
            customCanvasDots.setAllowTextNumbers(true);
            //customCanvasDots.setAllowTens(true);
        } /*else {
           // customCanvasDots.setAllowTens(false);
        }*/

     
        customCanvasDots.setNumberOfDots(
                Integer.parseInt(mCurrentNumbers.get(shapeID).get("math_number")));
        mColor = 0;
        customCanvasDots.setColor(mColor);
        customCanvasDots.invalidate();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void loadFrontImages(){




        findViewById(R.id.image1).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image2).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image3).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image4).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image5).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image6).setVisibility(ImageButton.VISIBLE);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////



    protected void setupFrameListens(){
        findViewById(R.id.image1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mNumberStatus.get(0).equals("0") && !mBeingValidated && mNumbersChosen<2){
                    mNumbersChosen++;
                    mCurrentNumberAudio=mCurrentNumbers.get(0).get("math_numbers_audio");
                    if(!mCurrentNumberID.equals("")){
                        mChosenFrames.set(0, "0");
                        mCorrect =((HashMap) findViewById(R.id.image1).getTag())
                                .get("math_numbers_id").equals(mCurrentNumberID);
                        setUpFrame(0,true);
                    }else{
                        mChosenFrames.set(1, "0");
                        mCurrentNumberID=(String)((HashMap) findViewById(R.id.image1)
                                .getTag()).get("math_numbers_id");
                        setUpFrame(0,false);
                    }
                }
            }
        });

        findViewById(R.id.image2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mNumberStatus.get(1).equals("0") && !mBeingValidated && mNumbersChosen<2){
                    mNumbersChosen++;
                    mCurrentNumberAudio=mCurrentNumbers.get(1).get("math_numbers_audio");
                    if(!mCurrentNumberID.equals("")){
                        mChosenFrames.set(0, "1");
                        mCorrect =((HashMap) findViewById(R.id.image2).getTag())
                                .get("math_numbers_id").equals(mCurrentNumberID);
                        setUpFrame(1,true);
                    }else{
                        mChosenFrames.set(1, "1");
                        mCurrentNumberID=(String)((HashMap) findViewById(R.id.image2)
                                .getTag()).get("math_numbers_id");
                        setUpFrame(1,false);
                    }
                }
            }
        });

        findViewById(R.id.image3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mNumberStatus.get(2).equals("0") && !mBeingValidated && mNumbersChosen<2){
                    mNumbersChosen++;
                    mCurrentNumberAudio=mCurrentNumbers.get(2).get("math_numbers_audio");
                    if(!mCurrentNumberID.equals("")){
                        mChosenFrames.set(0, "2");
                        mCorrect =((HashMap) findViewById(R.id.image3)
                                .getTag()).get("math_numbers_id").equals(mCurrentNumberID);
                        setUpFrame(2,true);
                    }else{
                        mChosenFrames.set(1, "2");

                        mCurrentNumberID=(String)((HashMap) findViewById(R.id.image3).getTag())
                                .get("math_numbers_id");
                        setUpFrame(2,false);
                    }
                }
            }
        });

        findViewById(R.id.image4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mNumberStatus.get(3).equals("0") && !mBeingValidated && mNumbersChosen<2){
                    mNumbersChosen++;
                    mCurrentNumberAudio=mCurrentNumbers.get(3).get("math_numbers_audio");
                    if(!mCurrentNumberID.equals("")){
                        mChosenFrames.set(0, "3");
                        mCorrect =((HashMap) findViewById(R.id.image4).getTag())
                                .get("math_numbers_id").equals(mCurrentNumberID);
                        setUpFrame(3,true);
                    }else{
                        mChosenFrames.set(1, "3");

                        mCurrentNumberID=(String)((HashMap) findViewById(R.id.image4)
                                .getTag()).get("math_numbers_id");
                        setUpFrame(3,false);
                    }
                }
            }
        });

        findViewById(R.id.image5).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mNumberStatus.get(4).equals("0") && !mBeingValidated && mNumbersChosen<2){
                    mNumbersChosen++;
                    mCurrentNumberAudio=mCurrentNumbers.get(4).get("math_numbers_audio");
                    if(!mCurrentNumberID.equals("")){
                        mChosenFrames.set(0, "4");
                        mCorrect =((HashMap) findViewById(R.id.image5).getTag())
                                .get("math_numbers_id").equals(mCurrentNumberID);
                        setUpFrame(4,true);
                    }else{
                        mChosenFrames.set(1, "4");

                        mCurrentNumberID=(String)((HashMap) findViewById(R.id.image5)
                                .getTag()).get("math_numbers_id");
                        setUpFrame(4,false);
                    }
                }
            }
        });

        findViewById(R.id.image6).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mNumberStatus.get(5).equals("0") && !mBeingValidated && mNumbersChosen<2){
                    mNumbersChosen++;
                    mCurrentNumberAudio=mCurrentNumbers.get(5).get("math_numbers_audio");
                    //mCurrentNumberID=mCurrentNumbers.get(5).get(0);
                    if(!mCurrentNumberID.equals("")){
                        mChosenFrames.set(0, "5");
                        mCorrect =((HashMap) findViewById(R.id.image6).getTag())
                                .get("math_numbers_id").equals(mCurrentNumberID);
                        setUpFrame(5,true);
                    }else{
                        mChosenFrames.set(1, "5");
                        mCurrentNumberID=(String)((HashMap) findViewById(R.id.image6)
                                .getTag()).get("math_numbers_id");
                        setUpFrame(5,false);
                    }
                }
            }
        });


    }//protected void setupFrameListens(){

    ////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber, boolean secondGuess){
        if(secondGuess){
            mValidateAvailable=false;
            long mAudioDuration=playGeneralAudio(mCurrentNumberAudio);
            validateHandler.postDelayed(processValidate, mAudioDuration+100);

        }else{
            playGeneralAudio(mCurrentNumberAudio);
        }
        switch(frameNumber){
            default:
            case 0:{
                findViewById(R.id.image1).setVisibility(ImageButton.INVISIBLE);
                

                findViewById(R.id.numberBox1).setBackground(
                        ContextCompat.getDrawable(this,R.drawable.rounded_text_selected));
                break;
            }
            case 1:{
                findViewById(R.id.image2).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.numberBox2).setBackground(
                        ContextCompat.getDrawable(this,R.drawable.rounded_text_selected));
                break;
            }
            case 2:{
                findViewById(R.id.image3).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.numberBox3).setBackground(
                        ContextCompat.getDrawable(this,R.drawable.rounded_text_selected));
                break;
            }
            case 3:{
                findViewById(R.id.image4).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.numberBox4).setBackground(
                        ContextCompat.getDrawable(this,R.drawable.rounded_text_selected));
                break;
            }
            case 4:{
                findViewById(R.id.image5).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.numberBox5).setBackground(
                        ContextCompat.getDrawable(this,R.drawable.rounded_text_selected));
                break;
            }
            case 5:{
                findViewById(R.id.image6).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.numberBox6).setBackground(
                        ContextCompat.getDrawable(this,R.drawable.rounded_text_selected));
                break;
            }
        }//end switch(frameNumber){
    }//end private void setUpFrame(int frameNumber){


    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable processValidate = new Runnable(){
        @Override
        public void run(){
            validate();
        }

    };
    
    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
      
        if(mCorrect){
            processLayoutAfterGuess();
            mCorrectNumberCount++;
        }else{
            processLayoutAfterGuess();
        }

        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 300);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void addPointToAllAvailable(){
        String where=" activity_id="+appData.getCurrentActivity().get(0)+" " +
                "AND app_user_id="+appData.getCurrentUserID();

        getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_ALL_UPDATE,
                null, where, null);

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void processLayoutAfterGuess(){
        for(int i=0;i<2;i++){
            int currentWordLocation=Integer.valueOf(mChosenFrames.get(i));

            switch(currentWordLocation){
                default:
                case 0:{
                    if(mCorrect){
                        mNumberStatus.set(0, "2");
                        if(((HashMap) findViewById(R.id.image1).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number1)).setTextColor(
                                    ContextCompat.getColor(this,R.color.correct_green));
                            findViewById(R.id.numberBox1).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_correct));
                        }else{
                            mColor=1;
                            placingCurrentNumbersAsDots(0);
                        }
                    }else{
                        mNumberStatus.set(0, "1");
                        if(((HashMap) findViewById(R.id.image1).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number1)).setTextColor(
                                    ContextCompat.getColor(this,R.color.incorrect_red));

                            (findViewById(R.id.numberBox1)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_incorrect));
                        }else{
                            mColor=2;
                            placingCurrentNumbersAsDots(0);
                        }
                    }
                    break;
                }
                case 1:{
                    if(mCorrect){
                        mNumberStatus.set(1, "2");
                        if(((HashMap) findViewById(R.id.image2).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number2)).setTextColor(
                                    ContextCompat.getColor(this,R.color.correct_green));
                            (findViewById(R.id.numberBox2)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_correct));
                        }else{
                            mColor=1;
                            placingCurrentNumbersAsDots(1);
                        }
                    }else{
                        mNumberStatus.set(1, "1");
                        if(((HashMap) findViewById(R.id.image2).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number2)).setTextColor(
                                    ContextCompat.getColor(this,R.color.incorrect_red));

                            (findViewById(R.id.numberBox2)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_incorrect));
                        }else{
                            mColor=2;
                            placingCurrentNumbersAsDots(1);
                        }
                    }
                    break;
                }
                case 2:{
                    if(mCorrect){
                        mNumberStatus.set(2, "2");
                        if(((HashMap) findViewById(R.id.image3).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number3)).setTextColor(
                                    ContextCompat.getColor(this,R.color.correct_green));
                            (findViewById(R.id.numberBox3)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_correct));
                        }else{
                            mColor=1;
                            placingCurrentNumbersAsDots(2);
                        }
                    }else{
                        mNumberStatus.set(2, "1");
                        if(((HashMap) findViewById(R.id.image3).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number3)).setTextColor(
                                    ContextCompat.getColor(this,R.color.incorrect_red));

                            (findViewById(R.id.numberBox3)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_incorrect));
                        }else{
                            mColor=2;
                            placingCurrentNumbersAsDots(2);
                        }
                    }
                    break;
                }
                case 3:{
                    if(mCorrect){
                        mNumberStatus.set(3, "2");
                        if(((HashMap) findViewById(R.id.image4).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number4)).setTextColor(
                                    ContextCompat.getColor(this,R.color.correct_green));
                            (findViewById(R.id.numberBox4)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_correct));
                        }else{
                            mColor=1;
                            placingCurrentNumbersAsDots(3);
                        }
                    }else{
                        mNumberStatus.set(3, "1");
                        if(((HashMap) findViewById(R.id.image4).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number4)).setTextColor(
                                    ContextCompat.getColor(this,R.color.incorrect_red));

                            (findViewById(R.id.numberBox4)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_incorrect));
                        }else{
                            mColor=2;
                            placingCurrentNumbersAsDots(3);
                        }
                    }
                    break;
                }
                case 4:{
                    if(mCorrect){
                        mNumberStatus.set(4, "2");
                        if(((HashMap) findViewById(R.id.image5).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number5)).setTextColor(
                                    ContextCompat.getColor(this,R.color.correct_green));
                            (findViewById(R.id.numberBox5)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_correct));
                        }else{
                            mColor=1;
                            placingCurrentNumbersAsDots(4);
                        }
                    }else{
                        mNumberStatus.set(4, "1");
                        if(((HashMap) findViewById(R.id.image5).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number5)).setTextColor(
                                    ContextCompat.getColor(this,R.color.incorrect_red));

                            (findViewById(R.id.numberBox5)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_incorrect));
                        }else{
                            mColor=2;
                            placingCurrentNumbersAsDots(4);
                        }
                    }
                    break;
                }
                case 5:{
                    if(mCorrect){
                        mNumberStatus.set(5, "2");
                        if(((HashMap) findViewById(R.id.image6).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number6)).setTextColor(
                                    ContextCompat.getColor(this,R.color.correct_green));
                            (findViewById(R.id.numberBox6)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_correct));
                        }else{
                            mColor=1;
                            placingCurrentNumbersAsDots(5);
                        }
                    }else{
                        mNumberStatus.set(5, "1");
                        if(((HashMap) findViewById(R.id.image6).getTag())
                                .get("is_number").equals("1")) {
                            ((TextView) findViewById(R.id.number6)).setTextColor(
                                    ContextCompat.getColor(this,R.color.incorrect_red));

                            (findViewById(R.id.numberBox6)).setBackground(ContextCompat
                                    .getDrawable(this,R.drawable.rounded_text_incorrect));
                        }else{
                            mColor=2;
                            placingCurrentNumbersAsDots(5);
                        }
                    }
                    break;
                }
            }//end switch
        }
    }//end private void processLayoutAfterGuess(boolean correctWord){

    ////////////////////////////////////////////////////////////////////////////////////////////

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
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    mNumbersChosen=0;
                    mCurrentNumberID="";
                    mChosenFrames.set(0, "");
                    mChosenFrames.set(1, "");
                    guessHandler.removeCallbacks(processGuess);
                    if(mCorrectNumberCount==3){
                        mRound++;
                        if(mRound!=2){
                            inBetweenRounds(0);
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;
                            addPointToAllAvailable();


                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        mProcessGuessPosition++;
                        Log.d(Constants.LOGCAT, "before clearInCorrects case 2");
                        guessHandler.postDelayed(processGuess, 200);


                    }
                    break;
                }
                case 3:{
                    if(mCorrectNumberCount==3) {
                        inBetweenRounds(1);
                        displayScreen();
                        guessHandler.removeCallbacks(processGuess);
                    }else {
                        clearWrongMatches();
                        mBeingValidated = false;
                        mValidateAvailable = false;
                        guessHandler.removeCallbacks(processGuess);
                    }
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };//end private Runnable processGuess = new Runnable(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearWrongMatches(){
        if(mNumberStatus.get(0).equals("0") || mNumberStatus.get(0).equals("1")){
            mNumberStatus.set(0, "0");
            findViewById(R.id.numberBox1).setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
            if(((HashMap) findViewById(R.id.image1).getTag())
                    .get("is_number").equals("1")) {
                ((TextView) findViewById(R.id.number1))
                        .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
                ((TextView) findViewById(R.id.number1))
                        .setPaintFlags(((TextView) findViewById(R.id.number1))
                                .getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }else {
                mColor = 5;
                placingCurrentNumbersAsDots(0);
            }
            findViewById(R.id.image1).setVisibility(ImageButton.VISIBLE);
            
        }
        if(mNumberStatus.get(1).equals("0") || mNumberStatus.get(1).equals("1")){
            mNumberStatus.set(1, "0");
            findViewById(R.id.numberBox2).setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
            if(((HashMap) findViewById(R.id.image2).getTag())
                    .get("is_number").equals("1")) {
                ((TextView) findViewById(R.id.number2))
                        .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
                ((TextView) findViewById(R.id.number2))
                        .setPaintFlags(((TextView) findViewById(R.id.number2))
                                .getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }else {
                mColor = 5;
                placingCurrentNumbersAsDots(1);
            }
            findViewById(R.id.image2).setVisibility(ImageButton.VISIBLE);
        }
        if(mNumberStatus.get(2).equals("0") || mNumberStatus.get(2).equals("1")){
            mNumberStatus.set(2, "0");
            findViewById(R.id.numberBox3).setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
            if(((HashMap) findViewById(R.id.image3).getTag())
                    .get("is_number").equals("1")) {
                ((TextView) findViewById(R.id.number3))
                        .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
                ((TextView) findViewById(R.id.number3))
                        .setPaintFlags(((TextView) findViewById(R.id.number3))
                                .getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }else {
                mColor = 5;
                placingCurrentNumbersAsDots(2);
            }

            findViewById(R.id.image3).setVisibility(ImageButton.VISIBLE);
        }
        if(mNumberStatus.get(3).equals("0") || mNumberStatus.get(3).equals("1")){
            mNumberStatus.set(3, "0");
            findViewById(R.id.numberBox4).setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
            if(((HashMap) findViewById(R.id.image4).getTag())
                    .get("is_number").equals("1")) {
                ((TextView) findViewById(R.id.number4))
                        .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
                ((TextView) findViewById(R.id.number4))
                        .setPaintFlags(((TextView) findViewById(R.id.number4))
                                .getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }else {
                mColor = 5;
                placingCurrentNumbersAsDots(3);
            }

            findViewById(R.id.image4).setVisibility(ImageButton.VISIBLE);
        }
        if(mNumberStatus.get(4).equals("0") || mNumberStatus.get(4).equals("1")){
            mNumberStatus.set(4, "0");
            findViewById(R.id.numberBox5).setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
            if(((HashMap) findViewById(R.id.image5).getTag())
                    .get("is_number").equals("1")) {
                ((TextView) findViewById(R.id.number5))
                        .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
                ((TextView) findViewById(R.id.number5))
                        .setPaintFlags(((TextView) findViewById(R.id.number5))
                                .getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }else {
                mColor = 5;
                placingCurrentNumbersAsDots(4);
            }

            findViewById(R.id.image5).setVisibility(ImageButton.VISIBLE);
        }
        if(mNumberStatus.get(5).equals("0") || mNumberStatus.get(5).equals("1")){
            mNumberStatus.set(5, "0");
            findViewById(R.id.numberBox6).setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
            if(((HashMap) findViewById(R.id.image6).getTag())
                    .get("is_number").equals("1")) {
                ((TextView) findViewById(R.id.number6))
                        .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
                ((TextView) findViewById(R.id.number6))
                        .setPaintFlags(((TextView) findViewById(R.id.number6))
                                .getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }else {
                mColor = 5;
                placingCurrentNumbersAsDots(5);
            }
            findViewById(R.id.image6).setVisibility(ImageButton.VISIBLE);
        }

    }//end private void clearWrongMatches(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////


    private void inBetweenRounds(int level){
        if(level==0){
            viewProgressBars();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    private void resetNumbers(){

        ((TextView) findViewById(R.id.number1)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.number2)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.number3)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.number4)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.number5)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.number6)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));


    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////

    private void unSelectedNumberBoxes(){


        findViewById(R.id.numberBox1).setBackground(
                ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
        findViewById(R.id.numberBox2).setBackground(
                ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
        findViewById(R.id.numberBox3).setBackground(
                ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
        findViewById(R.id.numberBox4).setBackground(
                ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
        findViewById(R.id.numberBox5).setBackground(
                ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
        findViewById(R.id.numberBox6).setBackground(
                ContextCompat.getDrawable(this,R.drawable.rounded_text_unselected));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

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
                        AppProvider.CONTENT_URI_MATH_NUMBERS, mData, mWhere, null, mOrderBy);
                break;

            }
        }

        return cursorLoader;
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
                break;
            case Constants.MATH_NUMBERS_SET:{

                processData(data);
                break;
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    ///////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        ArrayList<HashMap<String,String>> mCurrentNumberSet=new ArrayList<>();
        int mNumber=0;
        int mNumberCorrectInARow=Constants.INA_ROW_CORRECT;
        if(mCursor.moveToFirst()) {
            do  {
                mCurrentNumberSet.add(new HashMap<String, String>());
                mCurrentNumberSet.get(mNumber).put("correct_guess", "1");
                mCurrentNumberSet.get(mNumber).put("guessed_yet", "0");
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
                mCurrentNumberSet.get(mNumber).put("is_number", "1");

                mNumberCorrectInARow = (mNumberCorrectInARow > Integer.parseInt(
                        mCurrentNumberSet.get(mNumber).get("number_correct_in_a_row")))
                        ? Integer.parseInt(
                        mCurrentNumberSet.get(mNumber).get("number_correct_in_a_row"))
                        : mNumberCorrectInARow;

                mNumber++;
                if (mNumber >= Constants.NUMBER_VARIABLES_SIX ||
                        mCursor.isLast()) {
                    break;
                }
            }while(mCursor.moveToNext());
        }else{
            moveBackwords();
        }


        Collections.shuffle(mCurrentNumberSet);


        mNumber=0;
        while(mCurrentNumberSet.size()<6){
            mCurrentNumberSet.add(mCurrentNumberSet.get(mNumber));

            mNumber++;
        }


        mNumber=0;
        mRoundsNumberSet= new ArrayList<>();
        for(int i=0; i<2; i++){
            mRoundsNumberSet.add(new ArrayList<HashMap<String, String>>());
            for(int j=0;j<3;j++){
                mRoundsNumberSet.get(i).add(new HashMap<String, String>());
                mRoundsNumberSet.get(i).get(j).put("correct_guess", "0");
                mRoundsNumberSet.get(i).get(j).put("guessed_yet", "0");
                mRoundsNumberSet.get(i).get(j).put("math_numbers_id",
                        mCurrentNumberSet.get(mNumber).get("math_numbers_id"));
                mRoundsNumberSet.get(i).get(j).put("math_number",
                        mCurrentNumberSet.get(mNumber).get("math_number"));
                mRoundsNumberSet.get(i).get(j).put("math_number_style",
                        mCurrentNumberSet.get(mNumber).get("math_number_style"));
                mRoundsNumberSet.get(i).get(j).put("math_numbers_audio",
                        mCurrentNumberSet.get(mNumber).get("math_numbers_audio"));
                mRoundsNumberSet.get(i).get(j).put("number_correct",
                        mCurrentNumberSet.get(mNumber).get("number_correct"));
                mRoundsNumberSet.get(i).get(j).put("number_correct_in_a_row",
                        mCurrentNumberSet.get(mNumber).get("number_correct_in_a_row"));
                mRoundsNumberSet.get(i).get(j).put("number_incorrect",
                        mCurrentNumberSet.get(mNumber).get("number_incorrect"));
                mRoundsNumberSet.get(i).get(j).put("is_number",
                        mCurrentNumberSet.get(mNumber).get("is_number"));
                mNumber++;
            }
            
        }

        Collections.shuffle(mRoundsNumberSet.get(0));
        Collections.shuffle(mRoundsNumberSet.get(1));

        displayScreen();

    }

}

