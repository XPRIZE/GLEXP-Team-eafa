package com.motoli.apps.allsubjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ImageView;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 *
 * Audio plays match the letter*. There are four letters displayed one of which is correct and 
 * matches with displayed correct letter.
 * User then choose correct or incorrect letter and activity responds likewise. Answer and
 * time is tracked. Data is gathered here as well. 
 */
public class ActivityLT02 extends ActivityLTRoot
        implements LoaderManager.LoaderCallbacks<Cursor>{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_lt02);
        appData.addToClassOrder(5);
        allActivityText = new ArrayList<>();
        mValidateButton=(findViewById(R.id.btnValidate)!=null);
        mActivityNumber=2;

        mBeingValidated=true;
        mInstructionAudio="info_lt02";
        mPlayLetterAudio=false;

        fadeInOrOutScreenInActivity(true);
        clearActivity();
        setUpListeners();
        setupFrameListens();

        ((TextView) findViewById(R.id.letter0)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter4)).setTypeface(appData.getCurrentFontType());

        startActivityHandler.postDelayed(startActivity, 780);

    }//end public void onCreate(Bundle savedInstanceState) {

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_LETTERS, null, this);
    }

    /**
     * clearActivity()
     * clears all letters and changed their color to black. Also turns off validation icon.
     */
    private void clearActivity(){
        ((TextView) findViewById(R.id.letter0)).setText("");
        ((TextView) findViewById(R.id.letter1)).setText("");
        ((TextView) findViewById(R.id.letter2)).setText("");
        ((TextView) findViewById(R.id.letter3)).setText("");
        ((TextView) findViewById(R.id.letter4)).setText("");

        ((TextView) findViewById(R.id.letter1)).setTextColor(ContextCompat
                .getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter2)).setTextColor(ContextCompat
                .getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter3)).setTextColor(ContextCompat
                .getColor(this, R.color.normalBlack));
        ((TextView) findViewById(R.id.letter4)).setTextColor(ContextCompat
                .getColor(this, R.color.normalBlack));

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);

        clearFrames();
    }

    /**
     * displayScreen()
     * PLaces shuffled letters in the four different locations. For correct letter ID and Audio
     * is globally stored. Plays instruction audio if first round and no matter plays current
     * letter
     */
    protected void displayScreen(){
        mIncorrectInRound=0;
        Collections.shuffle(mRoundLetters);
        for(int i=0;i<4;i++){
            if(mRoundLetters.get(i).get("is_correct").equals("1")){
                mCorrectLocation=i;
                mCorrectID=mRoundLetters.get(i).get("letter_id");
                mCorrectLetterText=mRoundLetters.get(i).get("letter_text");
                mCorrectAudio=mRoundLetters.get(i).get("audio_url");
                break;
            }
        }

        ((TextView) findViewById(R.id.letter1)).setText(mRoundLetters.get(0).get("letter_text"));
        ((TextView) findViewById(R.id.letter2)).setText(mRoundLetters.get(1).get("letter_text"));
        ((TextView) findViewById(R.id.letter3)).setText(mRoundLetters.get(2).get("letter_text"));
        ((TextView) findViewById(R.id.letter4)).setText(mRoundLetters.get(3).get("letter_text"));
        ((TextView) findViewById(R.id.letter0)).setText(mCorrectLetterText);

        long mAudioDuration=0;
        if(mRoundNumber==0) {
            mAudioDuration = playInstructionAudio();
        }
        mAudioHandler.postDelayed(playLetterAudio, mAudioDuration+20);

    }//end private void displayScreen(Cursor currentWordsCursor){

    /**
     * setupFrameListens()
     * Called from onCreate. Sets response of activity when letter is chosen. If correct letter
     * mCorrect = true else false. Sets variables of current letter chosen for validation and
     * tracking purposes
     */
    protected void setupFrameListens(){
        findViewById(R.id.frame1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mRoundLetters.get(0).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=0;
                    mCurrentAudio=mRoundLetters.get(0).get("audio_url");
                    mCurrentID=mRoundLetters.get(0).get("letter_id");
                    mCorrect=(mRoundLetters.get(0).get("is_correct").equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.frame2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mRoundLetters.get(1).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=1;
                    mCurrentAudio=mRoundLetters.get(1).get("audio_url");
                    mCurrentID=mRoundLetters.get(1).get("letter_id");
                    mCorrect=(mRoundLetters.get(1).get("is_correct").equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.frame3).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mRoundLetters.get(2).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=2;
                    mCurrentAudio=mRoundLetters.get(2).get("audio_url");
                    mCurrentID=mRoundLetters.get(2).get("letter_id");
                    mCorrect=(mRoundLetters.get(2).get("is_correct").equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.frame4).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mRoundLetters.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=3;
                    mCurrentAudio=mRoundLetters.get(3).get("audio_url");
                    mCurrentID=mRoundLetters.get(3).get("letter_id");
                    mCorrect=(mRoundLetters.get(3).get("is_correct").equals("1"));
                    setUpFrame(3);
                }
            }
        });


    }

    /**
     * setUpFrame(int mFrameNumber)
     * Set frame indicator to chosen and changes btnValidate icon to on and allow user to now
     * validate chose of syllables
     * @param mFrameNumber represents the frame chosen to change
     */
    private void setUpFrame(int mFrameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        mValidateAvailable=true;
        switch(mFrameNumber){
            default:
            case 0:
                ((ImageView)findViewById(R.id.frame1))
                        .setImageResource(R.drawable.frm_sd01_on);
                break;
            case 1:
                ((ImageView)findViewById(R.id.frame2))
                        .setImageResource(R.drawable.frm_sd01_on);
                break;
            case 2:
                ((ImageView)findViewById(R.id.frame3))
                        .setImageResource(R.drawable.frm_sd01_on);
                break;
            case 3:
                ((ImageView)findViewById(R.id.frame4))
                        .setImageResource(R.drawable.frm_sd01_on);
                break;
        }
    }

    /**
     * clearFrames all to not chosen
     */
    private void clearFrames(){
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_sd01_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_sd01_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_sd01_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_sd01_off);

    }

    /**
     * called from ActivityLTRoot to run processGuess after set time
     * @param mAudioDuration time till call processGuess
     */
    @Override
    protected void processTheGuess(long mAudioDuration){
        guessHandler.postDelayed(processGuess, mAudioDuration);
    }


    /**
     * processGuess called by validate()->processTheGuess from ActivityLTRoot.
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
                    if(mCorrect){
                        mAudioDuration=playGeneralAudio("sfx_right");
                    }else{
                        mAudioDuration=playGeneralAudio("sfx_wrong");
                    }
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                case 1:

                    mAudioDuration=playGeneralAudio(mCurrentAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+500);
                    break;
                case 2:
                    adjustLettersToChoice();
                    guessHandler.removeCallbacks(processGuess);
                    clearFrames();
                    if(mCorrect){
                        mRoundNumber++;
                        if(mRoundNumber!=(mCurrentLetters.size())){
                            mValidateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;

                            fadeInOrOutScreenInActivity(false);
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                case 3:
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off);
                    beginRound();

                    guessHandler.removeCallbacks(processGuess);
                    break;
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };

    /**
     * onCreateLoader
     * sends request to  AppProvider for needed word list to use in activity based up current
     * group_id, phase_id, and users current level in related group and phase
     * @param id which data is it processing
     * @param args extra data to help process database if needed
     * @return Loader<Cursor> data grabed from AppProvider for use in activity
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;

        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
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

        String selection="variable_phase_levels.group_id="
                    +mCurrentGSP.get("group_id")+
                " AND variable_phase_levels.phase_id="
                    +mCurrentGSP.get("phase_id")+
                " AND ((variable_phase_levels.level_number="
                    +mCurrentGSP.get("current_level")+"-1 "+
                " OR variable_phase_levels.level_number="
                    +mCurrentGSP.get("current_level")+"-2 "+
                " OR variable_phase_levels.level_number="
                    +mCurrentGSP.get("current_level")+"-3"+
                " OR variable_phase_levels.level_number="
                    +mCurrentGSP.get("current_level")+" ) "+
                " OR variable_phase_levels.level_number<="
                    +mCurrentGSP.get("current_level")+" -3)";

        cursorLoader = new CursorLoader(this,
                AppProvider.CONTENT_URI_ACTIVITY_CURRENT_LETTERS_WRW,
                projection, selection, null, null);


        return cursorLoader;
    }

    /**
     * Takes data from last loader and then send it to processData to organize
     * @param loader current loader
     * @param data data from AppProvider to be processed
     */
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

    /**
     * Not used
     * @param loader  current loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {    }


    /**
     * setUpListners()
     * called in onCreate. If letter0 clicked plays current word.
     */
    protected void setUpListeners(){
        super.setUpListeners();
        findViewById(R.id.letter0).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentLetterAudio.equals(""))
                    playGeneralAudio(mCurrentLetterAudio);
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////

}
