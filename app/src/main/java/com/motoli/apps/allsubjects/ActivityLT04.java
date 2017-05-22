package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
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
 *
 * There are 6 different letter each round. They are randomly placed in the 6 spots. There is a
 * separate random order of called letter. The audio of the current letter is played and replayed
 * from the orange mic man. The user selected the correct or incorrect letter and the presses
 * the validate icon. will not move forward until all six are acuretly chosen. tracks correct and
 * incorrect answer and time.
 */
public class ActivityLT04 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private int mCurrentTextLocation=0;
    private int correctWordCount=0;
    private int mRoundNumber=0;

    private boolean mCorrect=false;

    protected ArrayList<ArrayList<String>> mCurrentText;
    private ArrayList<String> wordStatus;

    private ArrayList<ArrayList<String>> mCurrentWords;

    private ArrayList<ArrayList<ArrayList<String>>> mAllActivityText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_lt04);
        appData.addToClassOrder(8);
        mAllActivityText = new ArrayList<>();


        mBeingValidated=true;
        mInstructionAudio="info_lt04";
        clearActivity();


        findViewById(R.id.frame0).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.box1).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box2).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box3).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box4).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box5).setVisibility(RelativeLayout.INVISIBLE);
        findViewById(R.id.box6).setVisibility(RelativeLayout.INVISIBLE);

        ((TextView) findViewById(R.id.letter1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter4)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter5)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter6)).setTypeface(appData.getCurrentFontType());

        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        if(mCurrentGSP.get("group_id").equals("2") && mCurrentGSP.get("phase_id").equals("2")) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(params.leftMargin,
                    getResources().getDimensionPixelSize(R.dimen.wd03_text_ucase_top_margin),
                    params.rightMargin, 0);
            findViewById(R.id.letter1).setLayoutParams(params);
            findViewById(R.id.letter2).setLayoutParams(params);
            findViewById(R.id.letter3).setLayoutParams(params);
            findViewById(R.id.letter4).setLayoutParams(params);
            findViewById(R.id.letter5).setLayoutParams(params);
            findViewById(R.id.letter6).setLayoutParams(params);

        }
        mRoundNumber=0;


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
        getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_LETTERS, null, this);
    }

    /**
     * displayScreen()
     * displays the 6 letters from a shuffle and tags them for choice. plays instruction audio if
     * in first round and first letter otherwise plays letter audio
     */
    protected void displayScreen(){
        findViewById(R.id.frame0).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.box1).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box2).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box3).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box4).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box5).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.box6).setVisibility(RelativeLayout.VISIBLE);


        wordStatus=new ArrayList<>();
        for(int i=0; i<6;i++){
            wordStatus.add("0");
        }
        correctWordCount=0;
        mIncorrectInRound=0;
        mCurrentText = new ArrayList<>(mAllActivityText.get(mRoundNumber));
        Collections.shuffle(mCurrentText);

        findViewById(R.id.micDude).setVisibility(ImageView.VISIBLE);

        findViewById(R.id.letter1).setTag(mCurrentText.get(0));
        ((TextView) findViewById(R.id.letter1)).setText(mCurrentText.get(0).get(1));

        findViewById(R.id.letter2).setTag(mCurrentText.get(1));
        ((TextView) findViewById(R.id.letter2)).setText(mCurrentText.get(1).get(1));

        findViewById(R.id.letter3).setTag(mCurrentText.get(2));
        ((TextView) findViewById(R.id.letter3)).setText(mCurrentText.get(2).get(1));

        findViewById(R.id.letter4).setTag(mCurrentText.get(3));
        ((TextView) findViewById(R.id.letter4)).setText(mCurrentText.get(3).get(1));

        findViewById(R.id.letter5).setTag(mCurrentText.get(4));
        ((TextView) findViewById(R.id.letter5)).setText(mCurrentText.get(4).get(1));

        findViewById(R.id.letter6).setTag(mCurrentText.get(5));
        ((TextView) findViewById(R.id.letter6)).setText(mCurrentText.get(5).get(1));

        Collections.shuffle(mCurrentText);

        setAudio();
        long mAudioDuration=(correctWordCount==0 && mRoundNumber<1)? playInstructionAudio():0;

        mAudioHandler.postDelayed(playWordAudioTimed, mAudioDuration+10);

        mValidateAvailable=false;
        mBeingValidated=false;
        setupFrameListens();
        setUpListeners();
    }

    /**
     * clearActivity()
     * clears all letters and makes the text all black for beginning of each round
     */
    private void clearActivity(){
        ((TextView) findViewById(R.id.letter1)).setText("");
        ((TextView) findViewById(R.id.letter2)).setText("");
        ((TextView) findViewById(R.id.letter3)).setText("");
        ((TextView) findViewById(R.id.letter4)).setText("");
        ((TextView) findViewById(R.id.letter5)).setText("");
        ((TextView) findViewById(R.id.letter6)).setText("");

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off_mic);

        ((TextView) findViewById(R.id.letter1))
                .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.letter2))
                .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.letter3))
                .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.letter4))
                .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.letter5))
                .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.letter6))
                .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));

        ( (ImageView) findViewById(R.id.letterBox1))
                .setImageResource(R.drawable.rounded_text_unselected);
        ((ImageView) findViewById(R.id.letterBox2))
                .setImageResource(R.drawable.rounded_text_unselected);
        ((ImageView) findViewById(R.id.letterBox3))
                .setImageResource(R.drawable.rounded_text_unselected);
        ((ImageView) findViewById(R.id.letterBox4))
                .setImageResource(R.drawable.rounded_text_unselected);
        ((ImageView) findViewById(R.id.letterBox5))
                .setImageResource(R.drawable.rounded_text_unselected);
        ((ImageView) findViewById(R.id.letterBox6))
                .setImageResource(R.drawable.rounded_text_unselected);

        ((TextView) findViewById(R.id.letter1)).setPaintFlags(((TextView) findViewById(R.id.letter1))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.letter2)).setPaintFlags(((TextView) findViewById(R.id.letter2))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.letter3)).setPaintFlags(((TextView) findViewById(R.id.letter3))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.letter4)).setPaintFlags(((TextView) findViewById(R.id.letter4))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.letter5)).setPaintFlags(((TextView) findViewById(R.id.letter5))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ((TextView) findViewById(R.id.letter6)).setPaintFlags(((TextView) findViewById(R.id.letter6))
                .getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));

    }//end private void clearActivity(){

    /**
     * setAudio()
     * sets the current letter Audio
     */
    private void setAudio(){
        mCurrentAudio=mCurrentText.get(correctWordCount).get(2);
    }

    /**
     * setupFrameListens()
     * Called from onCreate. Sets response of activity when letter is chosen. If correct letter
     * mCorrect = true else false. Get audio of current letter and ID of letter chosen
     */
    protected void setupFrameListens(){
        findViewById(R.id.letter1).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                if(wordStatus.get(0).equals("0") && !mBeingValidated){
                    ArrayList<String> mLetter= (ArrayList<String>) findViewById(R.id.letter1).getTag();
                    mCurrentID=mLetter.get(0);
                    matchingLtrWrdAudio=mLetter.get(2);
                    mCurrentTextLocation=0;
                    mCorrect =mLetter.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.letter2).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> mLetter=(ArrayList<String>) findViewById(R.id.letter2).getTag();
                mCurrentID=mLetter.get(0);
                matchingLtrWrdAudio=mLetter.get(2);
                if(wordStatus.get(1).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=1;
                    mCorrect =mLetter.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.letter3).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> mLetter=(ArrayList<String>) findViewById(R.id.letter3).getTag();
                mCurrentID=mLetter.get(0);
                matchingLtrWrdAudio=mLetter.get(2);
                if(wordStatus.get(2).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=2;
                    mCorrect =mLetter.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.letter4).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> mLetter=(ArrayList<String>) findViewById(R.id.letter4).getTag();
                mCurrentID=mLetter.get(0);
                matchingLtrWrdAudio=mLetter.get(2);
                if(wordStatus.get(3).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=3;
                    mCorrect =mLetter.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(3);
                }
            }
        });

        findViewById(R.id.letter5).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> mLetter=(ArrayList<String>) findViewById(R.id.letter5).getTag();
                mCurrentID=mLetter.get(0);
                matchingLtrWrdAudio=mLetter.get(2);
                if(wordStatus.get(4).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=4;
                    mCorrect =mLetter.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(4);
                }
            }
        });

        findViewById(R.id.letter6).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                ArrayList<String> mLetter=(ArrayList<String>) findViewById(R.id.letter6).getTag();
                mCurrentID=mLetter.get(0);
                matchingLtrWrdAudio=mLetter.get(2);
                if(wordStatus.get(5).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=5;
                    mCorrect =mLetter.get(0).equals(mCurrentText.get(correctWordCount).get(0));
                    setUpFrame(5);
                }
            }
        });

    }//protected void setupFrameListens(){

    /**
     * setUpFrame(int mFrameNumber)
     * Makes frame selected when letter is chosen and allows validate icon to bne clicked
     * @param mFrameNumber represents the frame chosen to change
     */
    private void setUpFrame(int mFrameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_on_mic);
        mValidateAvailable=true;

        switch(mFrameNumber){
            default:
            case 0:
                ((ImageView) findViewById(R.id.letterBox1))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            case 1:
                ((ImageView) findViewById(R.id.letterBox1))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            case 2:
                ((ImageView) findViewById(R.id.letterBox1))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            case 3:
                ((ImageView) findViewById(R.id.letterBox1))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            case 4:
                ((ImageView) findViewById(R.id.letterBox1))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
            case 5:
                ((ImageView) findViewById(R.id.letterBox1))
                        .setImageResource(R.drawable.rounded_text_selected);
                break;
        }//end switch(frameNumber){
    }//end private void setUpFrame(int frameNumber){

    /**
     * clearFrames() to be all unselected so long as they have not already been chosen/processed
     */
    private void clearFrames(){
        if(wordStatus.get(0).equals("0")) {
            ((ImageView) findViewById(R.id.letterBox1))
                    .setImageResource(R.drawable.rounded_text_unselected);
        }
        if(wordStatus.get(1).equals("0")) {
            ((ImageView) findViewById(R.id.letterBox1))
                    .setImageResource(R.drawable.rounded_text_unselected);
        }
        if(wordStatus.get(2).equals("0")) {
            ((ImageView) findViewById(R.id.letterBox1))
                    .setImageResource(R.drawable.rounded_text_unselected);
        }
        if(wordStatus.get(3).equals("0")) {
            ((ImageView) findViewById(R.id.letterBox1))
                    .setImageResource(R.drawable.rounded_text_unselected);
        }
        if(wordStatus.get(4).equals("0")) {
            ((ImageView) findViewById(R.id.letterBox1))
                    .setImageResource(R.drawable.rounded_text_unselected);
        }
        if(wordStatus.get(5).equals("0")) {
            ((ImageView) findViewById(R.id.letterBox1))
                    .setImageResource(R.drawable.rounded_text_unselected);
        }
    }

    /**
     * setUpListners() called in onCreate.
     * If btnValidate clicked run validation. If micDude clicked play current audio
     */
    protected void setUpListeners(){
        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mValidateAvailable && !mBeingValidated){
                    playClickSound();
                    validate();
                }
            }
        });
        findViewById(R.id.micDude).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentAudio.equals(""))
                    playGeneralAudio(mCurrentAudio);
            }
        });
    }//end protected void setUpListeners(){


    /**
     * validate()
     * adjusts validate icon accordingly to correct or not. Do not allow validate to be clicked
     * again. Add points for right or wrong process. Then call to recolor screen
     */
    protected void validate(){
        mBeingValidated=true;
        if(mCorrect){
            correctWordCount++;
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok_mic);
        }else{
            mIncorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok_mic);
        }
        addActivityPoints();
        processLayoutAfterGuess();
        placeVariableAttempts();

        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }//end protected void validate(){

    /**
     * processLayoutAfterGuess()
     * redraws screen is correct or incorrect choice. if Correct block use of letter box again
     */
    private void processLayoutAfterGuess(){

        switch(mCurrentTextLocation){
            default:
            case 0:{
                if(mCorrect){
                    wordStatus.set(0, "2");
                    ((TextView) findViewById(R.id.letter1)).
                            setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.letterBox1))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(0, "1");
                    ((TextView) findViewById(R.id.letter1))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.letter1))
                            .setPaintFlags(((TextView) findViewById(R.id.letter1)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.letterBox1))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
            case 1:{
                if(mCorrect){
                    wordStatus.set(1, "2");
                    ((TextView) findViewById(R.id.letter2))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.letterBox2))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(1, "1");
                    ((TextView) findViewById(R.id.letter2))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.letter2))
                            .setPaintFlags(((TextView) findViewById(R.id.letter2)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.letterBox2))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
            case 2:{
                if(mCorrect){
                    wordStatus.set(2, "2");
                    ((TextView) findViewById(R.id.letter3)).setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.letterBox3)).setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(2, "1");
                    ((TextView) findViewById(R.id.letter3))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.letter3))
                            .setPaintFlags(((TextView) findViewById(R.id.letter3)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.letterBox3))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
            case 3:{
                if(mCorrect){
                    wordStatus.set(3, "2");
                    ((TextView) findViewById(R.id.letter4))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.letterBox4))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(3, "1");
                    ((TextView) findViewById(R.id.letter4))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.letter4))
                            .setPaintFlags(((TextView) findViewById(R.id.letter4)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.letterBox4))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
            case 4:{
                if(mCorrect){
                    wordStatus.set(4, "2");
                    ((TextView) findViewById(R.id.letter5))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.letterBox5))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(4, "1");
                    ((TextView) findViewById(R.id.letter5))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.letter5))
                            .setPaintFlags(((TextView) findViewById(R.id.letter5)).getPaintFlags()
                                    | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.letterBox5))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
            case 5:{
                if(mCorrect){
                    wordStatus.set(5, "2");
                    ((TextView) findViewById(R.id.letter6))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    ((ImageView) findViewById(R.id.letterBox6))
                            .setImageResource(R.drawable.rounded_text_correct);
                }else{
                    wordStatus.set(5, "1");
                    ((TextView) findViewById(R.id.letter6))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    ((TextView) findViewById(R.id.letter6))
                            .setPaintFlags(((TextView) findViewById(R.id.letter6)).getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((ImageView) findViewById(R.id.letterBox6))
                            .setImageResource(R.drawable.rounded_text_incorrect);
                }
                break;
            }
        }//end switch
    }//end private void processLayoutAfterGuess(boolean correctWord){

    /**
     * Play right or wrong sound then in time will play word if
     * incorrect number of syllables chosen.  In time then clears all current round data and
     * decides is activity completed of move to next round. It will then return to
     * activities_platform or beginRound depending on conclusion.
     */
    private Runnable processGuess = new Runnable(){
        @Override
        public void run(){
            long mAudioDuration;

            switch(mProcessGuessPosition){
                case 0:
                default:
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, playCorrectOrNot(mCorrect)+10);
                    break;
                case 1:
                    if(mCorrect){
                        mAudioDuration=playGeneralAudio(mCurrentAudio);
                    }else{
                        mAudioDuration=playGeneralAudio(matchingLtrWrdAudio);
                    }
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                case 2:

                    guessHandler.removeCallbacks(processGuess);
                    if(correctWordCount==6){
                        mRoundNumber++;
                        if(mRoundNumber!=(mAllActivityText.size())){
                            clearActivity();
                            inBetweenRounds(0);
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;
                            fadeInOrOutScreenInActivity(false);
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,2000);
                        }
                    }else{
                        if(mCorrect){
                            clearInCorrects();
                            setAudio();
                            mAudioDuration=(correctWordCount==0)? playInstructionAudio():0;
                            mAudioHandler.postDelayed(playWordAudioTimed, mAudioDuration+10);
                        }
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off_mic);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }
                    break;
                case 3:
                    inBetweenRounds(1);
                    displayScreen();
                    guessHandler.removeCallbacks(processGuess);
                    break;
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };//end private Runnable processGuess = new Runnable(){

    /**
     * clearInCorrects() clears only letter if they have not already chosen correct or they were
     * just chosen incorrect
     */
    private void clearInCorrects(){
        if(wordStatus.get(0).equals("0") || wordStatus.get(0).equals("1")){
            wordStatus.set(0, "0");
            ((ImageView) findViewById(R.id.letterBox1))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter1))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.letter1))
                    .setPaintFlags(((TextView) findViewById(R.id.letter1)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(1).equals("0") || wordStatus.get(1).equals("1")){
            wordStatus.set(1, "0");
            ((ImageView) findViewById(R.id.letterBox2))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter2))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.letter2))
                    .setPaintFlags(((TextView) findViewById(R.id.letter2)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(2).equals("0") || wordStatus.get(2).equals("1")){
            wordStatus.set(2, "0");
            ((ImageView) findViewById(R.id.letterBox3))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter3))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.letter3))
                    .setPaintFlags(((TextView) findViewById(R.id.letter3)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(3).equals("0") || wordStatus.get(3).equals("1")){
            wordStatus.set(3, "0");
            ((ImageView) findViewById(R.id.letterBox4))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter4))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.letter4))
                    .setPaintFlags(((TextView) findViewById(R.id.letter4)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(4).equals("0") || wordStatus.get(4).equals("1")){
            wordStatus.set(4, "0");
            ((ImageView) findViewById(R.id.letterBox5))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter5))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.letter5))
                    .setPaintFlags(((TextView) findViewById(R.id.letter5)).getPaintFlags()
                            & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(wordStatus.get(5).equals("0") || wordStatus.get(5).equals("1")){
            wordStatus.set(5, "0");
            ((ImageView) findViewById(R.id.letterBox6))
                    .setImageResource(R.drawable.rounded_text_unselected);
            ((TextView) findViewById(R.id.letter6))
                    .setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
            ((TextView) findViewById(R.id.letter6))
                    .setPaintFlags(((TextView) findViewById(R.id.letter6)).getPaintFlags()
                    & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

    }//end private void clearInCorrects(){

    /**
     * hides orange mic man and validate
     * @param mPart represents part of round
     */
    private void inBetweenRounds(int mPart){
        if(mPart==0){
            findViewById(R.id.micDude).setVisibility(ImageView.INVISIBLE);
            findViewById(R.id.btnValidate).setVisibility(ImageView.INVISIBLE);
        }else{
            findViewById(R.id.micDude).setVisibility(ImageView.VISIBLE);
            findViewById(R.id.btnValidate).setVisibility(ImageView.VISIBLE);

            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_off_mic);

        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    private void shuffleGameData(){
        mAllActivityText.clear();

        int nCurrentCount=0;
        int nNumberRounds=Math.ceil((double)mCurrentWords.size()/6)>=2 ? 2 :1;
        //2;//Constants.INA_ROW_CORRECT;
        int nPosition=0;

        for(int nRoundCount=0; nRoundCount<nNumberRounds; nRoundCount++){
            mAllActivityText.add(new ArrayList<ArrayList<String>>());
            int mWordCount=0;
            do{
                if(nCurrentCount==mCurrentWords.size()){
                    nCurrentCount=1;
                }

                boolean mNoMatching=true;
                for(ArrayList mTemp : mAllActivityText.get(nRoundCount) ) {
                    if(mTemp.get(0).equals(mCurrentWords.get(nPosition).get(0))){
                        mNoMatching=false;
                        break;
                    }
                }

                if(mNoMatching) {
                    mAllActivityText.get(nRoundCount).add(new ArrayList<String>());
                    mAllActivityText.get(nRoundCount).get(mWordCount)
                            .add(mCurrentWords.get(nPosition).get(0)); //word_id
                    mAllActivityText.get(nRoundCount).get(mWordCount)
                            .add(mCurrentWords.get(nPosition).get(1)); //word_text
                    mAllActivityText.get(nRoundCount).get(mWordCount)
                            .add(mCurrentWords.get(nPosition).get(2)); //word_audio
                    mAllActivityText.get(nRoundCount).get(mWordCount)
                            .add(mCurrentWords.get(nPosition).get(5));    //word_image
                    mWordCount++;
                }
                nPosition++;
                if(nPosition==mCurrentWords.size()){
                    nPosition=0;
                }

                nCurrentCount=(mWordCount==0 && nRoundCount>0 ) ?nCurrentCount : (nCurrentCount+1) ;
            }while(mWordCount<6);
        }//end for(int roundCount=0; roundCount<numberOfRoundsAvail; roundCount++){
        for(int i=0;i<nNumberRounds; i++){
            Collections.shuffle(mAllActivityText.get(i));
        }
    }//end private void shuffleGameData(){

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.ACTIVITY_CURRENT_LETTERS:{

                mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
                String level_number=(Integer.parseInt(mCurrentGSP.get("current_level"))<=2) ? "3"
                        : mCurrentGSP.get("current_level");
                if(mCurrentGSP.get("phase_id").equals("3")){
                    mCurrentGSP.put("phase_id","2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        mCurrentGSP.get("current_level")};
                String secondSelection="variable_phase_levels.group_id="
                        +mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id="+
                        mCurrentGSP.get("phase_id")+
                        " AND ((variable_phase_levels.level_number="
                        +mCurrentGSP.get("current_level")+"-1 "+
                        " OR variable_phase_levels.level_number="
                        +mCurrentGSP.get("current_level")+"-2 "+
                        " OR variable_phase_levels.level_number="
                        +mCurrentGSP.get("current_level")+"-3"+
                        " OR variable_phase_levels.level_number="
                        +mCurrentGSP.get("current_level")+" ) "+
                        " OR variable_phase_levels.level_number<="
                        +mCurrentGSP.get("current_level")+"-3)";

                String selection="variable_phase_levels.group_id="+mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id="+mCurrentGSP.get("phase_id")+
                        " AND ((variable_phase_levels.level_number="+level_number+"-1 "+
                        " OR variable_phase_levels.level_number="+level_number+"-2 "+
                        " OR variable_phase_levels.level_number="+level_number+"-3"+
                        " OR variable_phase_levels.level_number="+level_number+" ) "+
                        " OR variable_phase_levels.level_number<="+level_number+"-3)";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ACTIVITY_CURRENT_LETTERS_WRW,
                        projection, selection, null, secondSelection);
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
            case Constants.ACTIVITY_CURRENT_LETTERS:{
                processData(data);
                break;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mCurrentWords=new ArrayList<>();
        int currentWordNumber=0;

        if(mCursor.moveToFirst()) {
            do{


                mCurrentWords.add(new ArrayList<String>());
                mCurrentWords.get(currentWordNumber).add(
                        mCursor.getString(mCursor.getColumnIndex("letter_id")));
                mCurrentWords.get(currentWordNumber).add(
                        mCursor.getString(mCursor.getColumnIndex("letter_text")));
                mCurrentWords.get(currentWordNumber).add(
                        mCursor.getString(mCursor.getColumnIndex("audio_url")));
                mCurrentWords.get(currentWordNumber).add("0");
                mCurrentWords.get(currentWordNumber).add("0");
                mCurrentWords.get(currentWordNumber).add(
                        mCursor.getString(mCursor.getColumnIndex("image_url")));
                mCurrentWords.get(currentWordNumber).add("0"); //6
                currentWordNumber++;
            }while(mCursor.moveToNext());
        }

        shuffleGameData();
        displayScreen();

    }
}
