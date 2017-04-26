package com.motoli.apps.allsubjects;

import java.util.Collections;


import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import static com.motoli.apps.allsubjects.R.*;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 *
 * Audio plays find the letter *. There are four letters displayed one of which is correct.
 * User then choose correct or incorrect letter and activity responds likewise. Answer and
 * time is tracked. Data is gathered here as well.
 */
public class ActivityLT01 extends ActivityLTRoot {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(anim.activity_fade_in, anim.activity_fade_out);
        setContentView(layout.activity_lt01);
        appData.addToClassOrder(5);
        mValidateButton=(findViewById(R.id.btnValidate)!=null);
        mActivityNumber=1;

        mBeingValidated=true;
        mInstructionAudio="info_lt01";
        mPlayLetterAudio=true;


        fadeInOrOutScreenInActivity(true);
        clearActivity();
        setUpListeners();
        setupFrameListens();


        ((TextView) findViewById(id.letter1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(id.letter2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(id.letter3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(id.letter4)).setTypeface(appData.getCurrentFontType());

        startActivityHandler.postDelayed(startActivity, 500);
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
        ((TextView) findViewById(id.letter1)).setText("");
        ((TextView) findViewById(id.letter2)).setText("");
        ((TextView) findViewById(id.letter3)).setText("");
        ((TextView) findViewById(id.letter4)).setText("");

        ((TextView) findViewById(id.letter1)).setTextColor(ContextCompat
                .getColor(this, color.normalBlack));
        ((TextView) findViewById(id.letter2)).setTextColor(ContextCompat
                .getColor(this, color.normalBlack));
        ((TextView) findViewById(id.letter3)).setTextColor(ContextCompat
                .getColor(this, color.normalBlack));
        ((TextView) findViewById(id.letter4)).setTextColor(ContextCompat
                .getColor(this, color.normalBlack));

        ((ImageView) findViewById(id.btnValidate)).setImageResource(drawable.btn_validate_off_mic);

        clearFrames();
    }

    /**
     * displayScreen()
     * PLaces shuffled letters in the four different locations. For correct letter ID and Audio
     * is globally stored. Plays instruction audio if first round and no matter plays current
     * letter
     */
    @Override
    protected void displayScreen(){
        mIncorrectInRound=0;
        Collections.shuffle(mRoundLetters);

        for(int i=0;i<4;i++){
            if(mRoundLetters.get(i).get("is_correct").equals("1")){
                mCorrectLocation=i;
                mCorrectID=mRoundLetters.get(i).get("letter_id");
                mCorrectAudio=mRoundLetters.get(i).get("audio_url");
                break;
            }
        }

        ((TextView) findViewById(id.letter1))
                .setText(mRoundLetters.get(0).get("letter_text"));
        ((TextView) findViewById(id.letter2))
                .setText(mRoundLetters.get(1).get("letter_text"));
        ((TextView) findViewById(id.letter3))
                .setText(mRoundLetters.get(2).get("letter_text"));
        ((TextView) findViewById(id.letter4))
                .setText(mRoundLetters.get(3).get("letter_text"));

        mValidateAvailable=false;
        mBeingValidated=false;

        if(mRoundNumber==0 && !mCorrectAudio.equals("")) {
            startActivityHandler.postDelayed(playInstructions, 900);
        }else{
            playGeneralAudio(mCorrectAudio);
        }

    }//end private void displayScreen(Cursor currentWordsCursor){

    private Runnable playInstructions = new Runnable() {
        @Override
        public void run() {
            playInstructionAudio();
        }
    };

    /**
     * setupFrameListens()
     * Called from onCreate. Sets response of activity when letter is chosen. If correct letter
     * mCorrect = true else false. Sets variables of current letter chosen for validation and
     * tracking purposes
     */
    protected void setupFrameListens(){
        findViewById(id.frame1).setOnClickListener(new OnClickListener() {
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

        findViewById(id.frame2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (mRoundLetters.get(1).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 1;
                    mCurrentAudio = mRoundLetters.get(1).get("audio_url");
                    mCurrentID = mRoundLetters.get(1).get("letter_id");
                    mCorrect = (mRoundLetters.get(1).get("is_correct").equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(id.frame3).setOnClickListener(new OnClickListener() {
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

        findViewById(id.frame4).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (mRoundLetters.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 3;
                    mCurrentAudio = mRoundLetters.get(3).get("audio_url");
                    mCurrentID = mRoundLetters.get(3).get("letter_id");
                    mCorrect = (mRoundLetters.get(3).get("is_correct").equals("1"));
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
        ((ImageView) findViewById(id.btnValidate)).setImageResource(drawable.btn_validate_on_mic);
        mValidateAvailable=true;
        switch(mFrameNumber){
            default:
            case 0:
                ((ImageView)findViewById(id.frame1))
                        .setImageResource(drawable.frm_lt01_on);
                break;
            case 1:
                ((ImageView)findViewById(id.frame2))
                        .setImageResource(drawable.frm_lt01_on);
                break;
            case 2:
                ((ImageView)findViewById(id.frame3))
                        .setImageResource(drawable.frm_lt01_on);
                break;
            case 3:
                ((ImageView)findViewById(id.frame4))
                        .setImageResource(drawable.frm_lt01_on);
                break;
        }
    }

    /**
     * clearFrames all to not chosen
     */
    private void clearFrames(){
        ((ImageView)findViewById(id.frame1)).setImageResource(drawable.frm_lt01_off);
        ((ImageView)findViewById(id.frame2)).setImageResource(drawable.frm_lt01_off);
        ((ImageView)findViewById(id.frame3)).setImageResource(drawable.frm_lt01_off);
        ((ImageView)findViewById(id.frame4)).setImageResource(drawable.frm_lt01_off);
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
    protected Runnable processGuess = new Runnable(){
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
                mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
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
                        guessHandler.postDelayed(processGuess, 10);
                    }else{
                        mLastActivityData=0;
                        fadeInOrOutScreenInActivity(false);
                        lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                    }
                }else{
                    ((ImageView) findViewById(id.btnValidate))
                            .setImageResource(drawable.btn_validate_off_mic);
                    mBeingValidated=false;
                    mValidateAvailable=false;
                }
                break;
            case 3:
                ((ImageView) findViewById(id.btnValidate))
                        .setImageResource(drawable.btn_validate_off_mic);
                beginRound();
                guessHandler.removeCallbacks(processGuess);
                break;
        }//switch(ProcessGuessPosition){
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

        String selection="variable_phase_levels.group_id="+
                    mCurrentGSP.get("group_id")+
                " AND variable_phase_levels.phase_id="
                    +mCurrentGSP.get("phase_id")+
                " AND ((variable_phase_levels.level_number="
                    +mCurrentGSP.get("current_level")+"-1 "+
                " OR variable_phase_levels.level_number="
                    +mCurrentGSP.get("current_level")+"-2 "+
                " OR variable_phase_levels.level_number="
                    +mCurrentGSP.get("current_level")+"-3"+/**/
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
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    /**
     * setUpListners()
     * called in onCreate. If btnMicDude clicked plays current word.
     */
    protected void setUpListeners(){
        super.setUpListeners();
        findViewById(id.btnMicDude).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!mCorrectAudio.equals(""))
                    playGeneralAudio(mCorrectAudio);
            }
        });

    }

}
