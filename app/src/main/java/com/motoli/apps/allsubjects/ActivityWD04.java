package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/31/2015.
 */
public class ActivityWD04 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{


    private int mCorrectWordCount=0;
    private int mProcessGuessPosition=0;
    private int mNumberOfWordsChosen=0;

    private String mCurrentWordID="";


    private ArrayList<String> mWordStatus;
    private ArrayList<String> mChosenFrames;
    //Handlers
    private ArrayList<ArrayList<String>> mAllText;

private ArrayList<ArrayList<String>> mCurrentText;





    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_wd04);

        appData.addToClassOrder(6);
        allActivityText = new ArrayList<>();
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mBeingValidated=true;
        mInstructionAudio="info_wd04";
        clearActivity();
        viewProgressBars();

        setUpListeners();

        roundNumber=0;


        mCurrentText = new ArrayList<>();
        mChosenFrames=new ArrayList<>();
        mChosenFrames.add("");
        mChosenFrames.add("");

        mValidateAvailable=false;
        mBeingValidated=false;

        //ArrayList<String> currentUser=new ArrayList<String>();

        //database


        startActivityHandler.postDelayed(startActivity, 200);
    }//end public void onCreate(Bundle savedInstanceState) {

    //////////////////////////////////////////////////////////////////////////////////////////

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////

    private void start(){
        getLoaderManager().initLoader(Constants.CURRENT_WORDS, null, this);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        hideProgressBars();
        loadImagesAndHideText();
        mWordStatus=new ArrayList<String>();
        for(int i=0; i<6;i++){
            mWordStatus.add("0");
        }

        mNumberOfWordsChosen=0;
        mCorrectWordCount=0;

        if(roundNumber==0){
            playInstructionAudio();
        }

        clearText();
        mCurrentText.clear();
        mCurrentText = new ArrayList<ArrayList<String>>(allActivityText.get(roundNumber));
        Collections.shuffle(mCurrentText);


        int mFontSize=0;
        for(int i=0;i<mCurrentText.size();i++){
            mFontSize=(mCurrentText.get(i).get(1).length()>mFontSize) ?
                    mCurrentText.get(i).get(1).length() : mFontSize;
        }

        if(mFontSize>=8) {
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word5))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word6))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
        }else if(mFontSize>=7){
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word5))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word6))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
        }else if(mFontSize>=6){
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word5))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word6))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
        }else if(mFontSize>=5){
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word5))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word6))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
        }else{
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word5))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word6))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
        }

        findViewById(R.id.word1).setTag(mCurrentText.get(0).get(0));
        ((TextView)findViewById(R.id.word1)).setText(mCurrentText.get(0).get(1));

        findViewById(R.id.word2).setTag(mCurrentText.get(1).get(0));
        ((TextView)findViewById(R.id.word2)).setText(mCurrentText.get(1).get(1));

        findViewById(R.id.word3).setTag(mCurrentText.get(2).get(0));
        ((TextView)findViewById(R.id.word3)).setText(mCurrentText.get(2).get(1));

        findViewById(R.id.word4).setTag(mCurrentText.get(3).get(0));
        ((TextView)findViewById(R.id.word4)).setText(mCurrentText.get(3).get(1));

        findViewById(R.id.word5).setTag(mCurrentText.get(4).get(0));
        ((TextView)findViewById(R.id.word5)).setText(mCurrentText.get(4).get(1));

        findViewById(R.id.word6).setTag(mCurrentText.get(5).get(0));
        ((TextView)findViewById(R.id.word6)).setText(mCurrentText.get(5).get(1));

        mValidateAvailable=false;
        mBeingValidated=false;
        setupFrameListens();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    private void hideProgressBars(){






    }

    private void viewProgressBars(){






    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){


    }



    ////////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void loadImagesAndHideText(){
        findViewById(R.id.word1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.word2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.word3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.word4).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.word5).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.word6).setVisibility(TextView.INVISIBLE);

        findViewById(R.id.wordBox1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.wordBox2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.wordBox3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.wordBox4).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.wordBox5).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.wordBox6).setVisibility(TextView.INVISIBLE);


        findViewById(R.id.image1).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image2).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image3).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image4).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image5).setVisibility(ImageButton.VISIBLE);
        findViewById(R.id.image6).setVisibility(ImageButton.VISIBLE);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens(){
        findViewById(R.id.image1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mWordStatus.get(0).equals("0") && !mBeingValidated && mNumberOfWordsChosen<2){
                    mNumberOfWordsChosen++;
                    currentLtrWrdAudio=mCurrentText.get(0).get(2);
                    //mCurrentWordID=mCurrentText.get(0).get(0);
                    if(!mCurrentWordID.equals("")){
                        mChosenFrames.set(0, "0");
                        mCorrect =findViewById(R.id.word1).getTag().equals(mCurrentWordID);
                        setUpFrame(0,true);
                    }else{
                        mChosenFrames.set(1, "0");
                        mCurrentWordID=findViewById(R.id.word1).getTag().toString();
                        setUpFrame(0,false);
                    }
                }
            }
        });

        findViewById(R.id.image2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mWordStatus.get(1).equals("0") && !mBeingValidated && mNumberOfWordsChosen<2){
                    mNumberOfWordsChosen++;
                    currentLtrWrdAudio=mCurrentText.get(1).get(2);
                    //mCurrentWordID=mCurrentText.get(1).get(0);
                    if(!mCurrentWordID.equals("")){
                        mChosenFrames.set(0, "1");
                        mCorrect =findViewById(R.id.word2).getTag().equals(mCurrentWordID);
                        setUpFrame(1,true);
                    }else{
                        mChosenFrames.set(1, "1");
                        mCurrentWordID=findViewById(R.id.word2).getTag().toString();
                        setUpFrame(1,false);
                    }
                }
            }
        });

        findViewById(R.id.image3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mWordStatus.get(2).equals("0") && !mBeingValidated && mNumberOfWordsChosen<2){
                    mNumberOfWordsChosen++;
                    currentLtrWrdAudio=mCurrentText.get(2).get(2);
                    //mCurrentWordID=mCurrentText.get(2).get(0);
                    if(!mCurrentWordID.equals("")){
                        mChosenFrames.set(0, "2");
                        mCorrect =findViewById(R.id.word3).getTag().equals(mCurrentWordID);
                        setUpFrame(2,true);
                    }else{
                        mChosenFrames.set(1, "2");

                        mCurrentWordID=findViewById(R.id.word3).getTag().toString();
                        setUpFrame(2,false);
                    }
                }
            }
        });

        findViewById(R.id.image4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mWordStatus.get(3).equals("0") && !mBeingValidated && mNumberOfWordsChosen<2){
                    mNumberOfWordsChosen++;
                    currentLtrWrdAudio=mCurrentText.get(3).get(2);
                    //mCurrentWordID=mCurrentText.get(3).get(0);
                    if(!mCurrentWordID.equals("")){
                        mChosenFrames.set(0, "3");
                        mCorrect =findViewById(R.id.word4).getTag().equals(mCurrentWordID);
                        setUpFrame(3,true);
                    }else{
                        mChosenFrames.set(1, "3");

                        mCurrentWordID=findViewById(R.id.word4).getTag().toString();
                        setUpFrame(3,false);
                    }
                }
            }
        });

        findViewById(R.id.image5).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mWordStatus.get(4).equals("0") && !mBeingValidated && mNumberOfWordsChosen<2){
                    mNumberOfWordsChosen++;
                    currentLtrWrdAudio=mCurrentText.get(4).get(2);
                    //mCurrentWordID=mCurrentText.get(4).get(0);
                    if(!mCurrentWordID.equals("")){
                        mChosenFrames.set(0, "4");
                        mCorrect =findViewById(R.id.word5).getTag().equals(mCurrentWordID);
                        setUpFrame(4,true);
                    }else{
                        mChosenFrames.set(1, "4");

                        mCurrentWordID=findViewById(R.id.word5).getTag().toString();
                        setUpFrame(4,false);
                    }
                }
            }
        });

        findViewById(R.id.image6).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mWordStatus.get(5).equals("0") && !mBeingValidated && mNumberOfWordsChosen<2){
                    mNumberOfWordsChosen++;
                    currentLtrWrdAudio=mCurrentText.get(5).get(2);
                    //mCurrentWordID=mCurrentText.get(5).get(0);
                    if(!mCurrentWordID.equals("")){
                        mChosenFrames.set(0, "5");
                        mCorrect =findViewById(R.id.word6).getTag().equals(mCurrentWordID);
                        setUpFrame(5,true);
                    }else{
                        mChosenFrames.set(1, "5");
                        mCurrentWordID=findViewById(R.id.word6).getTag().toString();
                        setUpFrame(5,false);
                    }
                }
            }
        });


    }//protected void setupFrameListens(){

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber, boolean secondGuess){
        //clearFrames();
        //((ImageView) findViewById(R.id.btnAudio)).setImageResource(R.drawable.btn_audio_on);
        if(secondGuess){
            //((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
            mValidateAvailable=false;
            long mAudioDuration=playGeneralAudio(currentLtrWrdAudio);
            validateHandler.postDelayed(processValidate, mAudioDuration+100);

        }else{
            playGeneralAudio(currentLtrWrdAudio);
        }
        switch(frameNumber){
            default:
            case 0:{
                findViewById(R.id.image1).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.word1).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox1).setVisibility(TextView.VISIBLE);

                findViewById(R.id.wordBox1).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
                break;
            }
            case 1:{
                findViewById(R.id.image2).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.word2).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox2).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox2).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
                break;
            }
            case 2:{
                findViewById(R.id.image3).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.word3).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox3).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox3).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
                break;
            }
            case 3:{
                findViewById(R.id.image4).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.word4).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox4).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox4).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
                break;
            }
            case 4:{
                findViewById(R.id.image5).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.word5).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox5).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox5).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
                break;
            }
            case 5:{
                findViewById(R.id.image6).setVisibility(ImageButton.INVISIBLE);
                findViewById(R.id.word6).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox6).setVisibility(TextView.VISIBLE);
                findViewById(R.id.wordBox6).setBackground(getResources().getDrawable(R.drawable.rounded_text_selected));
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
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCurrentWordID
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;

        if(mCorrect){
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentWordID,
                    "0"};


            //getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            processLayoutAfterGuess();
            mCorrectWordCount++;
        }else{
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCurrentWordID,
                    "0"};


            //getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            processLayoutAfterGuess();
        }

        // DONE DIFFERENTLY NOW
        //processPoints();


        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void addPointToAllAvailable(){
            String where=" activity_id="+appData.getCurrentActivity().get(0)+" " +
                    "AND app_user_id="+appData.getCurrentUserID();

            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_ALL_UPDATE,
                    null, where, null);





    }
    private void processLayoutAfterGuess(){
        for(int i=0;i<2;i++){
            int currentWordLocation=Integer.valueOf(mChosenFrames.get(i));

            switch(currentWordLocation){
                default:
                case 0:{
                    if(mCorrect){
                        mWordStatus.set(0, "2");
                        ((TextView) findViewById(R.id.word1)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox1)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        mWordStatus.set(0, "1");
                        ((TextView) findViewById(R.id.word1)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word1)).setPaintFlags(((TextView) findViewById(R.id.word1)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ( findViewById(R.id.wordBox1)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
                    }
                    break;
                }
                case 1:{
                    if(mCorrect){
                        mWordStatus.set(1, "2");
                        ((TextView) findViewById(R.id.word2)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox2)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        mWordStatus.set(1, "1");
                        ((TextView) findViewById(R.id.word2)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word2)).setPaintFlags(((TextView) findViewById(R.id.word2)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ( findViewById(R.id.wordBox2)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
                    }
                    break;
                }
                case 2:{
                    if(mCorrect){
                        mWordStatus.set(2, "2");
                        ((TextView) findViewById(R.id.word3)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox3)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        mWordStatus.set(2, "1");
                        ((TextView) findViewById(R.id.word3)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word3)).setPaintFlags(((TextView) findViewById(R.id.word3)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ( findViewById(R.id.wordBox3)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
                    }
                    break;
                }
                case 3:{
                    if(mCorrect){
                        mWordStatus.set(3, "2");
                        ((TextView) findViewById(R.id.word4)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox4)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        mWordStatus.set(3, "1");
                        ((TextView) findViewById(R.id.word4)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word4)).setPaintFlags(((TextView) findViewById(R.id.word4)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ( findViewById(R.id.wordBox4)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
                    }
                    break;
                }
                case 4:{
                    if(mCorrect){
                        mWordStatus.set(4, "2");
                        ((TextView) findViewById(R.id.word5)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox5)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        mWordStatus.set(4, "1");
                        ((TextView) findViewById(R.id.word5)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word5)).setPaintFlags(((TextView) findViewById(R.id.word5)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ( findViewById(R.id.wordBox5)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
                    }
                    break;
                }
                case 5:{
                    if(mCorrect){
                        mWordStatus.set(5, "2");
                        ((TextView) findViewById(R.id.word6)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox6)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        mWordStatus.set(5, "1");
                        ((TextView) findViewById(R.id.word6)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word6)).setPaintFlags(
                                ((TextView) findViewById(R.id.word6)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        (findViewById(R.id.wordBox6)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
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
                    Log.d(Constants.LOGCAT,"processGuess case 0");
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
                    Log.d(Constants.LOGCAT,"processGuess case 1");

                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    mNumberOfWordsChosen=0;
                    mCurrentWordID="";
                    mChosenFrames.set(0, "");
                    mChosenFrames.set(1, "");
                    guessHandler.removeCallbacks(processGuess);
                    if(mCorrectWordCount==3){
                        roundNumber++;
                        if(roundNumber<(allActivityText.size())){
                            clearText();
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
                        //if(mCorrect){
                        clearInCorrects();
                        //}
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }
                    break;
                }
                case 3:{
                    inBetweenRounds(1);
                    displayScreen();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };//end private Runnable processGuess = new Runnable(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearInCorrects(){
        if(mWordStatus.get(0).equals("0") || mWordStatus.get(0).equals("1")){
            mWordStatus.set(0, "0");
            findViewById(R.id.wordBox1).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word1)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word1)).setPaintFlags(
                    ((TextView) findViewById(R.id.word1)).getPaintFlags()
                            & (~Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word1).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox1).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image1).setVisibility(ImageButton.VISIBLE);
        }
        if(mWordStatus.get(1).equals("0") || mWordStatus.get(1).equals("1")){
            mWordStatus.set(1, "0");
            findViewById(R.id.wordBox2).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word2)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word2)).setPaintFlags(((TextView) findViewById(R.id.word2)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word2).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox2).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image2).setVisibility(ImageButton.VISIBLE);
        }
        if(mWordStatus.get(2).equals("0") || mWordStatus.get(2).equals("1")){
            mWordStatus.set(2, "0");
            findViewById(R.id.wordBox3).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word3)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word3)).setPaintFlags(((TextView) findViewById(R.id.word3)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word3).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox3).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image3).setVisibility(ImageButton.VISIBLE);
        }
        if(mWordStatus.get(3).equals("0") || mWordStatus.get(3).equals("1")){
            mWordStatus.set(3, "0");
            findViewById(R.id.wordBox4).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word4)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word4)).setPaintFlags(((TextView) findViewById(R.id.word4)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word4).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox4).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image4).setVisibility(ImageButton.VISIBLE);
        }
        if(mWordStatus.get(4).equals("0") || mWordStatus.get(4).equals("1")){
            mWordStatus.set(4, "0");
            findViewById(R.id.wordBox5).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word5)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word5)).setPaintFlags(((TextView) findViewById(R.id.word5)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word5).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox5).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image5).setVisibility(ImageButton.VISIBLE);
        }
        if(mWordStatus.get(5).equals("0") || mWordStatus.get(5).equals("1")){
            mWordStatus.set(5, "0");
            findViewById(R.id.wordBox6).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word6)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word6)).setPaintFlags(((TextView) findViewById(R.id.word6)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word6).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox6).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image6).setVisibility(ImageButton.VISIBLE);
        }

    }//end private void clearInCorrects(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////


    private void inBetweenRounds(int level){
        if(level==0){
            clearText();
            viewProgressBars();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearText(){
        findViewById(R.id.word1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.word2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.word3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.word4).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.word5).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.word6).setVisibility(TextView.INVISIBLE);

        findViewById(R.id.wordBox1).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.wordBox2).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.wordBox3).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.wordBox4).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.wordBox5).setVisibility(TextView.INVISIBLE);
        findViewById(R.id.wordBox6).setVisibility(TextView.INVISIBLE);


        //clear image
        ((TextView)findViewById(R.id.word1)).setText("");
        ((TextView)findViewById(R.id.word2)).setText("");
        ((TextView)findViewById(R.id.word3)).setText("");
        ((TextView)findViewById(R.id.word4)).setText("");
        ((TextView)findViewById(R.id.word5)).setText("");
        ((TextView)findViewById(R.id.word6)).setText("");

        ((TextView)findViewById(R.id.word1)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.word2)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.word3)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.word4)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.word5)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.word6)).setTypeface(appData.getCurrentFontType());


        if(mCurrentGSP.get("group_id").equals("2") && mCurrentGSP.get("phase_id").equals("2")){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(params.leftMargin,
                    getResources().getDimensionPixelSize(R.dimen.wd04_text_ucase_top_margin),
                    params.rightMargin, 0);
            ((TextView) findViewById(R.id.word1)).setGravity(Gravity.CENTER_HORIZONTAL);
            findViewById(R.id.word1).setLayoutParams(params);
            findViewById(R.id.word2).setLayoutParams(params);
            findViewById(R.id.word3).setLayoutParams(params);
            findViewById(R.id.word4).setLayoutParams(params);
            findViewById(R.id.word5).setLayoutParams(params);
            findViewById(R.id.word6).setLayoutParams(params);
            //((TextView)findViewById(R.id.word1)).se
        }

        ((TextView) findViewById(R.id.word1)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.word2)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.word3)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.word4)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.word5)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.word6)).setTextColor(getResources().getColor(R.color.normalBlack));

        //remove stricktrough
        ((TextView) findViewById(R.id.word1)).setPaintFlags(((TextView) findViewById(R.id.word1)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.word2)).setPaintFlags(((TextView) findViewById(R.id.word2)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.word3)).setPaintFlags(((TextView) findViewById(R.id.word3)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.word4)).setPaintFlags(((TextView) findViewById(R.id.word4)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.word5)).setPaintFlags(((TextView) findViewById(R.id.word5)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.word6)).setPaintFlags(((TextView) findViewById(R.id.word6)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));


        findViewById(R.id.wordBox1).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
        findViewById(R.id.wordBox2).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
        findViewById(R.id.wordBox3).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
        findViewById(R.id.wordBox4).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
        findViewById(R.id.wordBox5).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
        findViewById(R.id.wordBox6).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));

    }

    ////////////////////////////////////////////////////////////////////////////////////////////


    private void clearActivity(){
        viewProgressBars();
        loadImagesAndHideText();
        clearText();

    }//end private void clearText(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void shuffleGameData(){

        //Collections.shuffle(mCurrentWords);
        allActivityText.clear();
        int currentCount=0;
        int numberOfRoundsAvail=(int)Math.ceil(((double)mCurrentWords.size())/3);

        for(int roundCount=0; roundCount<numberOfRoundsAvail;roundCount++){
            allActivityText.add(new ArrayList<ArrayList<String>>());
            for(int wordCount=0; wordCount<6;wordCount++){
                if(currentCount==mCurrentWords.size()){
                    currentCount=0;
                }
                allActivityText.get(roundCount).add(new ArrayList<String>());
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(0)); //word_id 1
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(1)); //word_text 2
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(2)); //audio_url 3
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(5));	//word_image

                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(3)); //number correct 4
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(4)); //number incorrect 5

                wordCount++;

                allActivityText.get(roundCount).add(new ArrayList<String>());
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(0)); //word_id 1
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(1)); //word_text 2
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(2)); //audio_url 3
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(5));	//word_image
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(3)); //number correct 4
                allActivityText.get(roundCount).get(wordCount).add(mCurrentWords.get(currentCount).get(4)); //number incorrect 5


                currentCount++;
            }//end for(int wordCount=0; wordCount<3;wordCount++){
        }//end for(int roundCount=0; roundCount<numberOfRoundsAvail; roundCount++){
        for(int i=0; i<allActivityText.size(); i++){
            Collections.shuffle(allActivityText.get(i));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.GUESS_WORDS:{
                if(mCurrentGSP.get("phase_id").equals("3")){
                    mCurrentGSP.put("phase_id","2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),"4", "1", "11",
                        mCurrentGSP.get("current_level"),"1"};

                String selection="variable_phase_levels.group_id" +
                        "="+mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id" +
                        "="+mCurrentGSP.get("phase_id")+
                        " AND ((variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-1 "+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-2 "+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-3"+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+" ) "+
                        " OR variable_phase_levels.level_number" +
                        "<="+mCurrentGSP.get("current_level")+" -3)"+
                        " AND variable_phase_levels.level_number>0";


                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_GUESS_WORDS, projection, selection, null, null);
                break;
            }
            case Constants.CURRENT_WORDS:{

                if(mCurrentGSP.get("phase_id").equals("3")){
                    mCurrentGSP.put("phase_id","2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),"4", "1", "11",
                        mCurrentGSP.get("current_level"),"0"};

                String selection="variable_phase_levels.group_id" +
                        "="+mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id" +
                        "="+mCurrentGSP.get("phase_id")+
                        " AND ((variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-1 "+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-2 "+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-3"+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+" ) "+
                        " OR variable_phase_levels.level_number" +
                        "<="+mCurrentGSP.get("current_level")+" -3)"+
                        " AND variable_phase_levels.level_number>0";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_WORDS_BY_LEVEL, projection, selection, null, null);
                break;
            }
        }
        return cursorLoader;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
                break;
            case Constants.GUESS_WORDS:{
                mAllText=new ArrayList<>();
                int mGuessWordNumber=0;
                if(data.moveToFirst()) {
                    do{
                        mAllText.add(new ArrayList<String>());
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("word_id"))); //0
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("word_text"))); //1
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("audio_url"))); //2
                        mAllText.get(mGuessWordNumber).add("0"); //3
                        mAllText.get(mGuessWordNumber).add("0"); //4
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("image_url"))); //5
                        mAllText.get(mGuessWordNumber).add("0"); //6
                        mGuessWordNumber++;
                    }while (data.moveToNext()) ;
                }
                Collections.shuffle(mAllText);
                getLoaderManager().restartLoader(Constants.CURRENT_WORDS, null, this);
                break;
            }
            case Constants.CURRENT_WORDS:{
                processData(data);
                break;
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mCurrentWords=new ArrayList<>();
        int currentWordNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentWords.add(new ArrayList<String>());
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_id")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_text")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("audio_url")));
            mCurrentWords.get(currentWordNumber).add("0");
            mCurrentWords.get(currentWordNumber).add("0");
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("image_url")));
            mCurrentWords.get(currentWordNumber).add("0"); //6

            currentWordNumber++;
            if (currentWordNumber == Constants.NUMBER_VARIABLES) {
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else if(mCursor.getCount()>=8 && currentWordNumber==9) {
                mCursor.moveToFirst();
            }else {
                mCursor.moveToNext();
            }
        }

        Collections.shuffle(mCurrentWords);

        ArrayList<ArrayList<String>> mTempCurrentWords=new ArrayList<ArrayList<String>>();

        String mPreviousWordId="0";
        String mPreviousPreviousWordID="0";
        boolean mCompleted=false;
        int mletterCount=0;
        int mletterListCount=0;
        do{
            if(mletterCount==0 || mletterCount==3){
                mTempCurrentWords.add(new ArrayList<>(mCurrentWords.get(mletterListCount)));
                mPreviousWordId=mCurrentWords.get(mletterListCount).get(0);
                mletterCount++;
                mletterListCount++;
            }else if(mletterCount==1 || mletterCount==4){
                if(mCurrentWords.get(mletterListCount).get(0).equals(mPreviousWordId)){
                    mletterListCount++;

                }else{
                    mTempCurrentWords.add(new ArrayList<String>(mCurrentWords.get(mletterListCount)));
                    mPreviousPreviousWordID=mCurrentWords.get(mletterListCount).get(0);
                    mletterCount++;
                    mletterListCount++;
                }

                if(mletterListCount>=mCurrentWords.size()){
                    mletterListCount=0;
                }
            }else if(mletterCount==2 || mletterCount==5){
                if(mCurrentWords.get(mletterListCount).get(0).equals(mPreviousWordId) ||
                        mCurrentWords.get(mletterListCount).get(0).equals(mPreviousPreviousWordID)){
                    mletterListCount++;

                }else{
                    mTempCurrentWords.add(new ArrayList<String>(mCurrentWords.get(mletterListCount)));
                    mPreviousPreviousWordID=mCurrentWords.get(mletterListCount).get(0);
                    mletterCount++;
                    mletterListCount++;
                    if(mletterCount>=6){
                        mCompleted=true;
                    }
                }

                if(mletterListCount>=mCurrentWords.size()){
                    mletterListCount=0;
                }
            }

        }while(!mCompleted);


        mCurrentWords=new ArrayList<>(mTempCurrentWords);


        shuffleGameData();
        displayScreen();

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

