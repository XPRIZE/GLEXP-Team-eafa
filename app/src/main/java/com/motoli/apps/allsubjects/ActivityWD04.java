package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/31/2015.
 */
public class ActivityWD04 extends Activity_General_Parent  implements LoaderManager.LoaderCallbacks<Cursor>{


    private int correctWordCount=0;
    private int processGuessPosition=0;
    private int numberOfWordsChosen=0;

    private String currentWordID="";


    private ArrayList<String> wordStatus;
    private ArrayList<String> chosenFrames;
    //Handlers
    private ArrayList<ArrayList<String>> mAllText;






    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_wd04);

        appData.addToClassOrder(6);
        allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

        beingValidated=true;

        clearActivity();
        viewProgressBars();

        setUpListeners();

        roundNumber=0;


        currentText = new ArrayList<ArrayList<String>>();
        chosenFrames=new ArrayList<String>();
        chosenFrames.add("");
        chosenFrames.add("");

        validateAvailable=false;
        beingValidated=false;

        //ArrayList<String> currentUser=new ArrayList<String>();

        //database

        getLoaderManager().initLoader(MotoliConstants.CURRENT_WORDS, null, this);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){
        hideProgressBars();
        loadImagesAndHideText();
        wordStatus=new ArrayList<String>();
        for(int i=0; i<6;i++){
            wordStatus.add("0");
        }

        numberOfWordsChosen=0;
        correctWordCount=0;

        if(roundNumber==0){
            playInstructionAudio();
        }

        clearText();
        currentText.clear();
        currentText = new ArrayList<ArrayList<String>>(allActivityText.get(roundNumber));
        Collections.shuffle(currentText);

        findViewById(R.id.word1).setTag(currentText.get(0).get(0));
        ((TextView)findViewById(R.id.word1)).setText(currentText.get(0).get(1));

        findViewById(R.id.word2).setTag(currentText.get(1).get(0));
        ((TextView)findViewById(R.id.word2)).setText(currentText.get(1).get(1));

        findViewById(R.id.word3).setTag(currentText.get(2).get(0));
        ((TextView)findViewById(R.id.word3)).setText(currentText.get(2).get(1));

        findViewById(R.id.word4).setTag(currentText.get(3).get(0));
        ((TextView)findViewById(R.id.word4)).setText(currentText.get(3).get(1));

        findViewById(R.id.word5).setTag(currentText.get(4).get(0));
        ((TextView)findViewById(R.id.word5)).setText(currentText.get(4).get(1));

        findViewById(R.id.word6).setTag(currentText.get(5).get(0));
        ((TextView)findViewById(R.id.word6)).setText(currentText.get(5).get(1));

        validateAvailable=false;
        beingValidated=false;
        setupFrameListens();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    private void hideProgressBars(){
        findViewById(R.id.loadingCircle1).setVisibility(ProgressBar.INVISIBLE);
        findViewById(R.id.loadingCircle2).setVisibility(ProgressBar.INVISIBLE);
        findViewById(R.id.loadingCircle3).setVisibility(ProgressBar.INVISIBLE);
        findViewById(R.id.loadingCircle4).setVisibility(ProgressBar.INVISIBLE);
        findViewById(R.id.loadingCircle5).setVisibility(ProgressBar.INVISIBLE);
        findViewById(R.id.loadingCircle6).setVisibility(ProgressBar.INVISIBLE);
    }

    private void viewProgressBars(){
        findViewById(R.id.loadingCircle1).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.loadingCircle2).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.loadingCircle3).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.loadingCircle4).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.loadingCircle5).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.loadingCircle6).setVisibility(ProgressBar.VISIBLE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpListeners(){

        findViewById(R.id.btnInfo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                playInstructionAudio();
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
            }
        });




    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    private int playInstructionAudio(){

        return (int) playGeneralAudio("info_wd04");
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
                if(wordStatus.get(0).equals("0") && !beingValidated && numberOfWordsChosen<2){
                    numberOfWordsChosen++;
                    currentLtrWrdAudio=currentText.get(0).get(2);
                    //currentWordID=currentText.get(0).get(0);
                    if(!currentWordID.equals("")){
                        chosenFrames.set(0, "0");
                        correctChoice =findViewById(R.id.word1).getTag().equals(currentWordID);
                        setUpFrame(0,true);
                    }else{
                        chosenFrames.set(1, "0");
                        currentWordID=findViewById(R.id.word1).getTag().toString();
                        setUpFrame(0,false);
                    }
                }
            }
        });

        findViewById(R.id.image2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(wordStatus.get(1).equals("0") && !beingValidated && numberOfWordsChosen<2){
                    numberOfWordsChosen++;
                    currentLtrWrdAudio=currentText.get(1).get(2);
                    //currentWordID=currentText.get(1).get(0);
                    if(!currentWordID.equals("")){
                        chosenFrames.set(0, "1");
                        correctChoice =findViewById(R.id.word2).getTag().equals(currentWordID);
                        setUpFrame(1,true);
                    }else{
                        chosenFrames.set(1, "1");
                        currentWordID=findViewById(R.id.word2).getTag().toString();
                        setUpFrame(1,false);
                    }
                }
            }
        });

        findViewById(R.id.image3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(wordStatus.get(2).equals("0") && !beingValidated && numberOfWordsChosen<2){
                    numberOfWordsChosen++;
                    currentLtrWrdAudio=currentText.get(2).get(2);
                    //currentWordID=currentText.get(2).get(0);
                    if(!currentWordID.equals("")){
                        chosenFrames.set(0, "2");
                        correctChoice =findViewById(R.id.word3).getTag().equals(currentWordID);
                        setUpFrame(2,true);
                    }else{
                        chosenFrames.set(1, "2");

                        currentWordID=findViewById(R.id.word3).getTag().toString();
                        setUpFrame(2,false);
                    }
                }
            }
        });

        findViewById(R.id.image4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(wordStatus.get(3).equals("0") && !beingValidated && numberOfWordsChosen<2){
                    numberOfWordsChosen++;
                    currentLtrWrdAudio=currentText.get(3).get(2);
                    //currentWordID=currentText.get(3).get(0);
                    if(!currentWordID.equals("")){
                        chosenFrames.set(0, "3");
                        correctChoice =findViewById(R.id.word4).getTag().equals(currentWordID);
                        setUpFrame(3,true);
                    }else{
                        chosenFrames.set(1, "3");

                        currentWordID=findViewById(R.id.word4).getTag().toString();
                        setUpFrame(3,false);
                    }
                }
            }
        });

        findViewById(R.id.image5).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(wordStatus.get(4).equals("0") && !beingValidated && numberOfWordsChosen<2){
                    numberOfWordsChosen++;
                    currentLtrWrdAudio=currentText.get(4).get(2);
                    //currentWordID=currentText.get(4).get(0);
                    if(!currentWordID.equals("")){
                        chosenFrames.set(0, "4");
                        correctChoice =findViewById(R.id.word5).getTag().equals(currentWordID);
                        setUpFrame(4,true);
                    }else{
                        chosenFrames.set(1, "4");

                        currentWordID=findViewById(R.id.word5).getTag().toString();
                        setUpFrame(4,false);
                    }
                }
            }
        });

        findViewById(R.id.image6).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(wordStatus.get(5).equals("0") && !beingValidated && numberOfWordsChosen<2){
                    numberOfWordsChosen++;
                    currentLtrWrdAudio=currentText.get(5).get(2);
                    //currentWordID=currentText.get(5).get(0);
                    if(!currentWordID.equals("")){
                        chosenFrames.set(0, "5");
                        correctChoice =findViewById(R.id.word6).getTag().equals(currentWordID);
                        setUpFrame(5,true);
                    }else{
                        chosenFrames.set(1, "5");
                        currentWordID=findViewById(R.id.word6).getTag().toString();
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
            validateAvailable=false;
            long audioDuration=playGeneralAudio(currentLtrWrdAudio);
            validateHandler.postDelayed(processValidate, audioDuration+100);

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

    private void validate(){
        beingValidated=true;
        String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+currentWordID
                +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

        if(correctChoice){
            selectionArgs = new String[]{
                    "1",
                    "0",
                    currentGSP.get(3),
                    currentWordID,
                    "0"};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            processLayoutAfterGuess();
            correctWordCount++;
        }else{
            selectionArgs = new String[]{
                    "0",
                    "1",
                    currentGSP.get(3),
                    currentWordID,
                    "0"};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            processLayoutAfterGuess();
        }

        // DONE DIFFERENTLY NOW
        //processPoints();


        processGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void addPointToAllAvailable(){
        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
        for(int i=0;i<mCurrentWords.size();i++){
            String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+
                    "="+appData.getCurrentUserID()
                    +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+
                    "="+mCurrentWords.get(i).get(0)
                    +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+
                    "="+appData.getCurrentActivity().get(0);

            String[] selectionArgs = new String[]{
                    "2",
                    "0",
                    currentGSP.get(3),
                    mCurrentWords.get(i).get(0),
                    "0"};

            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);


        }


    }
    private void processLayoutAfterGuess(){
        for(int i=0;i<2;i++){
            int currentWordLocation=Integer.valueOf(chosenFrames.get(i));

            switch(currentWordLocation){
                default:
                case 0:{
                    if(correctChoice){
                        wordStatus.set(0, "2");
                        ((TextView) findViewById(R.id.word1)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox1)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        wordStatus.set(0, "1");
                        ((TextView) findViewById(R.id.word1)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word1)).setPaintFlags(((TextView) findViewById(R.id.word1)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ( findViewById(R.id.wordBox1)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
                    }
                    break;
                }
                case 1:{
                    if(correctChoice){
                        wordStatus.set(1, "2");
                        ((TextView) findViewById(R.id.word2)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox2)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        wordStatus.set(1, "1");
                        ((TextView) findViewById(R.id.word2)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word2)).setPaintFlags(((TextView) findViewById(R.id.word2)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ( findViewById(R.id.wordBox2)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
                    }
                    break;
                }
                case 2:{
                    if(correctChoice){
                        wordStatus.set(2, "2");
                        ((TextView) findViewById(R.id.word3)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox3)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        wordStatus.set(2, "1");
                        ((TextView) findViewById(R.id.word3)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word3)).setPaintFlags(((TextView) findViewById(R.id.word3)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ( findViewById(R.id.wordBox3)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
                    }
                    break;
                }
                case 3:{
                    if(correctChoice){
                        wordStatus.set(3, "2");
                        ((TextView) findViewById(R.id.word4)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox4)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        wordStatus.set(3, "1");
                        ((TextView) findViewById(R.id.word4)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word4)).setPaintFlags(((TextView) findViewById(R.id.word4)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ( findViewById(R.id.wordBox4)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
                    }
                    break;
                }
                case 4:{
                    if(correctChoice){
                        wordStatus.set(4, "2");
                        ((TextView) findViewById(R.id.word5)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox5)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        wordStatus.set(4, "1");
                        ((TextView) findViewById(R.id.word5)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word5)).setPaintFlags(((TextView) findViewById(R.id.word5)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ( findViewById(R.id.wordBox5)).setBackground(getResources().getDrawable(R.drawable.rounded_text_incorrect));
                    }
                    break;
                }
                case 5:{
                    if(correctChoice){
                        wordStatus.set(5, "2");
                        ((TextView) findViewById(R.id.word6)).setTextColor(getResources().getColor(R.color.correct_green));
                        ( findViewById(R.id.wordBox6)).setBackground(getResources().getDrawable(R.drawable.rounded_text_correct));
                    }else{
                        wordStatus.set(5, "1");
                        ((TextView) findViewById(R.id.word6)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.word6)).setPaintFlags(((TextView) findViewById(R.id.word6)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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
            long audioDuration=0;

            switch(processGuessPosition){
                case 0:
                default:{
                    if(correctChoice){
                        audioDuration=playGeneralAudio("sfx_right");
                    }else{
                        audioDuration=playGeneralAudio("sfx_wrong");
                    }
                    processGuessPosition++;
                    audioHandler.postDelayed(processGuess, audioDuration+10);
                    break;
                }
                case 1:{
                    /*
                    if(correctChoice){
                        audioDuration=playGeneralAudio(currentLtrWrdAudio);
                    }
                    */
                    processGuessPosition++;
                    audioHandler.postDelayed(processGuess, audioDuration+10);
                    break;
                }
                case 2:{
                    numberOfWordsChosen=0;
                    currentWordID="";
                    chosenFrames.set(0, "");
                    chosenFrames.set(1, "");
                    guessHandler.removeCallbacks(processGuess);
                    if(correctWordCount==3){
                        roundNumber++;
                        if(roundNumber!=(allActivityText.size())){
                            clearText();
                            inBetweenRounds(0);
                            processGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            lastActivityData=0;
                            addPointToAllAvailable();

                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        //if(correctChoice){
                        clearInCorrects();
                        //}
                        beingValidated=false;
                        validateAvailable=false;
                    }
                    break;
                }
                case 3:{
                    inBetweenRounds(1);
                    displayScreen();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(processGuessPosition){
        }//public void run(){
    };//end private Runnable processGuess = new Runnable(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void clearInCorrects(){
        if(wordStatus.get(0).equals("0") || wordStatus.get(0).equals("1")){
            wordStatus.set(0, "0");
            findViewById(R.id.wordBox1).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word1)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word1)).setPaintFlags(((TextView) findViewById(R.id.word1)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word1).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox1).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image1).setVisibility(ImageButton.VISIBLE);
        }
        if(wordStatus.get(1).equals("0") || wordStatus.get(1).equals("1")){
            wordStatus.set(1, "0");
            findViewById(R.id.wordBox2).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word2)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word2)).setPaintFlags(((TextView) findViewById(R.id.word2)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word2).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox2).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image2).setVisibility(ImageButton.VISIBLE);
        }
        if(wordStatus.get(2).equals("0") || wordStatus.get(2).equals("1")){
            wordStatus.set(2, "0");
            findViewById(R.id.wordBox3).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word3)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word3)).setPaintFlags(((TextView) findViewById(R.id.word3)).getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word3).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox3).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image3).setVisibility(ImageButton.VISIBLE);
        }
        if(wordStatus.get(3).equals("0") || wordStatus.get(3).equals("1")){
            wordStatus.set(3, "0");
            findViewById(R.id.wordBox4).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word4)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word4)).setPaintFlags(((TextView) findViewById(R.id.word4)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word4).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox4).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image4).setVisibility(ImageButton.VISIBLE);
        }
        if(wordStatus.get(4).equals("0") || wordStatus.get(4).equals("1")){
            wordStatus.set(4, "0");
            findViewById(R.id.wordBox5).setBackground(getResources().getDrawable(R.drawable.rounded_text_unselected));
            ((TextView) findViewById(R.id.word5)).setTextColor(getResources().getColor(R.color.normalBlack));
            ((TextView) findViewById(R.id.word5)).setPaintFlags(((TextView) findViewById(R.id.word5)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            findViewById(R.id.word5).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.wordBox5).setVisibility(TextView.INVISIBLE);
            findViewById(R.id.image5).setVisibility(ImageButton.VISIBLE);
        }
        if(wordStatus.get(5).equals("0") || wordStatus.get(5).equals("1")){
            wordStatus.set(5, "0");
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

        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

        if(currentGSP.get(0).equals("2") && currentGSP.get(1).equals("2")){
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////

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
            case MotoliConstants.GUESS_WORDS:{
                ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
                if(currentGSP.get(1).equals("3")){
                    currentGSP.set(1,"2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        currentGSP.get(0),
                        currentGSP.get(1),
                        currentGSP.get(2),
                        currentGSP.get(3)};
                //NOTICE THIS IS ALWAYS DEFAULTING TO PHASE 1

                String selection="variable_phase_levels.group_id="+currentGSP.get(0)+
                        " AND variable_phase_levels.phase_id="+currentGSP.get(1)+
                        " AND ((variable_phase_levels.level_number="+currentGSP.get(3)+"-1 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-2 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-3"+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+" ) "+
                        " OR variable_phase_levels.level_number<="+currentGSP.get(3)+" -3)"+
                        " AND variable_phase_levels.level_number!=0";


                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_GUESS_WORDS, projection, selection, null, null);
                break;
            }
            case MotoliConstants.CURRENT_WORDS:{

                ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
                if(currentGSP.get(1).equals("3")){
                    currentGSP.set(1,"2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        currentGSP.get(0),
                        currentGSP.get(1),
                        currentGSP.get(2),
                        currentGSP.get(3)};
                //NOTICE THIS IS ALWAYS DEFAULTING TO PHASE 1

                String selection="variable_phase_levels.group_id="+currentGSP.get(0)+
                        " AND variable_phase_levels.phase_id="+currentGSP.get(1)+
                        " AND ((variable_phase_levels.level_number="+currentGSP.get(3)+"-1 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-2 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-3"+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+" ) "+
                        " OR variable_phase_levels.level_number<="+currentGSP.get(3)+" -3)"+
                        " AND variable_phase_levels.level_number!=0";

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_WORDS_BY_LEVEL, projection, selection, null, null);
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
            case MotoliConstants.GUESS_WORDS:{
                mAllText=new ArrayList<ArrayList<String>>();
                int mGuessWordNumber=0;
                if(data.moveToFirst()) {
                    do{
                        mAllText.add(new ArrayList<String>());
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("word_id"))); //0
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("word_text"))); //1
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("audio_url"))); //2
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("number_correct"))); //3
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("number_incorrect"))); //4
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("image_url"))); //5
                        mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("number_correct_in_a_row"))); //6
                        mGuessWordNumber++;
                    }while (data.moveToNext()) ;
                }
                Collections.shuffle(mAllText);
                getLoaderManager().restartLoader(MotoliConstants.CURRENT_WORDS, null, this);
                break;
            }
            case MotoliConstants.CURRENT_WORDS:{
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

    private void processData(Cursor mCursor){
        mCurrentWords=new ArrayList<ArrayList<String>>();
        int currentWordNumber=0;
        int mNumberCorrectInARow=MotoliConstants.INA_ROW_CORRECT;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentWords.add(new ArrayList<String>());
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_id")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_text")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("audio_url")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("image_url")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6

            mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(mCurrentWords.get(currentWordNumber).get(6)))
                    ? Integer.parseInt(mCurrentWords.get(currentWordNumber).get(6)):mNumberCorrectInARow;

            currentWordNumber++;
            if (currentWordNumber == MotoliConstants.NUMBER_VARIABLES) {
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else if(mCursor.getCount()>=8 && currentWordNumber==9) {
                mCursor.moveToFirst();
            }else {
                mCursor.moveToNext();
            }
        }

        if((mNumberCorrectInARow == MotoliConstants.INA_ROW_CORRECT)){
            showCompleteLevelStar(true);
        }else{
            showCompleteLevelStar(false);
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
                mTempCurrentWords.add(new ArrayList<String>(mCurrentWords.get(mletterListCount)));
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


        mCurrentWords=new ArrayList<ArrayList<String>>(mTempCurrentWords);


        shuffleGameData();
        displayScreen();

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

