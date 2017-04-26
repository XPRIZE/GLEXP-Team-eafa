package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects for Education Technology For Development
 * author Aaron D Michaelis Borsay
 * on 8/31/2015.
 *
 * This activity test if the word as 1 2 or 3 syllables. The user bust click therefore 1 -3 boxes
 * to respond. After click number of boxes user click validate button to test if correct of not
 */
public class ActivityCS01 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{




    private ArrayList<String> mChosenSyllables;

    private ArrayList<HashMap<String,String>> mSyllableWords;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_cs01);
        appData.addToClassOrder(13);
        appData.setActivityType(3);

        mInstructionAudio="info_cs01";
        fadeInOrOutScreenInActivity(true);

        mBeingValidated=true;
        setUpListeners();
        clearFrames();
        setupFrameListens();

        mChosenSyllables=new ArrayList<>();
        mChosenSyllables.add("0");
        mChosenSyllables.add("0");
        mChosenSyllables.add("0");


        startActivityHandler.postDelayed(startActivity, 500);
    }//end public void onCreate(Bundle savedInstanceState) {

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.SINGLE_DEFAULT, null, this);
    }

    /**
     * Process the Data from the database into a ArrayList<HashMap<>> for needed
     * variables of information. It also shuffles the data to have more random words for  8 words
     * It then calls beginRound
     * @param mCursor data coming from the db
     */
    protected void processData(Cursor mCursor){
        mSyllableWords=new ArrayList<>();
        int currentWordNumber=0;

        if(mCursor.moveToFirst()) {
            do{
                mSyllableWords.add(new HashMap<String, String>());
                mSyllableWords.get(currentWordNumber).put("word_id",
                        mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
                mSyllableWords.get(currentWordNumber).put("audio_url",
                        mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
                if (mCursor.getString(mCursor.getColumnIndex("number_of_syllables")).equals("0")) {
                    mSyllableWords.get(currentWordNumber).put("number_of_syllables", "1"); //7

                } else {
                    mSyllableWords.get(currentWordNumber).put("number_of_syllables",
                            mCursor.getString(mCursor.getColumnIndex("number_of_syllables"))); //7
                }

                currentWordNumber++;

                if (currentWordNumber >= Constants.NUMBER_VARIABLES || mCursor.isLast()) {
                    break;
                } else {
                    mCursor.moveToNext();
                }
            }while (!mCursor.isAfterLast());
        }

                Collections.shuffle(mSyllableWords);

        if(mSyllableWords.size()<Constants.NUMBER_VARIABLES ) {
            for (int i = 0; i < mSyllableWords.size(); i++) {
                if (!mSyllableWords.get(mSyllableWords.size() - 1)
                        .get("word_id").equals(mSyllableWords.get(i).get("word_id"))) {
                    mSyllableWords.add(new HashMap<>(mSyllableWords.get(i)));
                    if (mSyllableWords.size() >= Constants.NUMBER_VARIABLES) {
                        break;
                    }
                }
            }
        }

        beginRound();
    }


    /**
     * Begins each new round for the 8 different words. Sets audio for current word and id for
     * processing storage of results. If first round will play instruction audio and then audio
     * of word is played after.
     */
    protected void beginRound(){
        mIncorrectInRound=0;
        mBeingValidated=false;
        for(int i=0; i<mChosenSyllables.size(); i++){
            mChosenSyllables.set(i,"0");
        }
        mCurrentAudio=mSyllableWords.get(roundNumber).get("audio_url");
        mCurrentID=mSyllableWords.get(roundNumber).get("word_id");
        mCorrectID=mSyllableWords.get(roundNumber).get("word_id");
        long mAudioDuration=0;
        if(roundNumber==0) {
            mAudioDuration = playInstructionAudio();
        }

        mAudioHandler.postDelayed(playWordAudioTimed, mAudioDuration+20);
    }


    /**
     * validate()
     * Confirms if user chose correct number of syllables for given word. Then changes boxes chosen
     * to green is correct, red if incorrect.
     * Also calls addActivityPoints() in ActivitiesMasterParent to store process. Before going to
     * leveled processGuess
     */
    protected void validate(){
        mBeingValidated=true;
        int mNumberOfSyllables=0;
        for(int i=0;i<mChosenSyllables.size(); i++){
            if(mChosenSyllables.get(i).equals("1")){
                mNumberOfSyllables++;
            }else{
                break;
            }
        }
        mCorrect = (mSyllableWords.get(roundNumber)
                .get("number_of_syllables").equals(String.valueOf(mNumberOfSyllables)));

        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok_mic);
            for(int i=0;i<mChosenSyllables.size(); i++) {
                switch (i) {
                    default:
                    case 0:
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables1))
                                    .setImageResource(R.drawable.rounded_cs01_correct);
                        }
                        break;
                    case 1:
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables2))
                                    .setImageResource(R.drawable.rounded_cs01_correct);
                        }
                        break;
                    case 2:
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables3))
                                    .setImageResource(R.drawable.rounded_cs01_correct);
                        }
                        break;
                }//end   switch (i) {
            }//end for(int i=0;i<mChosenSyllables.size(); i++) {
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok_mic);
            for(int i=0;i<mChosenSyllables.size(); i++) {
                switch (i) {
                    default:
                    case 0:
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables1))
                                    .setImageResource(R.drawable.rounded_cs01_wrong);
                        }
                        break;
                    case 1:
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables2))
                                    .setImageResource(R.drawable.rounded_cs01_wrong);
                        }
                        break;
                    case 2:
                        if(mChosenSyllables.get(i).equals("1")) {
                            ((ImageView) findViewById(R.id.syllables3))
                                    .setImageResource(R.drawable.rounded_cs01_wrong);
                        }
                        break;
                }//end   switch (i) {
            }//end for(int i=0;i<mChosenSyllables.size(); i++) {
        }

        addActivityPoints();
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    /**
     * setupFrameListens()
     * Called from onCreate. Changes mChosenSyllables related value to true if click and other
     * previous values have been clicked. Then calls setUpFrame for selected box to properly color
     */
    protected void setupFrameListens() {
        findViewById(R.id.syllables1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    if(mChosenSyllables.get(0).equals("0")) {
                        mChosenSyllables.set(0, "1");
                    }else if(mChosenSyllables.get(1).equals("0")){
                        mChosenSyllables.set(0, "0");
                    }
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.syllables2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    if(mChosenSyllables.get(1).equals("0")
                            && mChosenSyllables.get(0).equals("1")) {
                        mChosenSyllables.set(1, "1");
                    }else if(mChosenSyllables.get(2).equals("0")){
                        mChosenSyllables.set(1, "0");
                    }
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.syllables3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    if(mChosenSyllables.get(2).equals("0")
                            && mChosenSyllables.get(1).equals("1")) {
                        mChosenSyllables.set(2, "1");
                    }else{
                        mChosenSyllables.set(2, "0");
                    }
                    setUpFrame(2);
                }
            }
        });
    }

    /**
     * clearFrames()
     * Sets all syllable count boxes to default pink
     */
    private void clearFrames(){
        ((ImageView)findViewById(R.id.syllables1))
                .setImageResource(R.drawable.rounded_cs01_unchosen);
        ((ImageView)findViewById(R.id.syllables2))
                .setImageResource(R.drawable.rounded_cs01_unchosen);
        ((ImageView)findViewById(R.id.syllables3))
                .setImageResource(R.drawable.rounded_cs01_unchosen);

    }


    /**
     * setUpFrame(int mFrameNumber)
     * Colors selected box to yellow and changes btnValidate icon to on and allow user to now
     * validate chose of syllables
     * @param mFrameNumber represents the frame chosen to change
     */
    private void setUpFrame(int mFrameNumber) {
        //clearFrames();
        if(mChosenSyllables.get(0).equals("1") || mChosenSyllables.get(1).equals("1")
                || mChosenSyllables.get(2).equals("1")) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_on_mic);
            mValidateAvailable = true;
            switch (mFrameNumber) {
                default:
                case 0: {
                    if (mChosenSyllables.get(0).equals("1")) {
                        ((ImageView) findViewById(R.id.syllables1))
                                .setImageResource(R.drawable.rounded_cs01_chosen);
                    } else {
                        ((ImageView) findViewById(R.id.syllables1))
                                .setImageResource(R.drawable.rounded_cs01_unchosen);
                    }
                    break;
                }
                case 1: {
                    if (mChosenSyllables.get(1).equals("1")) {
                        ((ImageView) findViewById(R.id.syllables2))
                                .setImageResource(R.drawable.rounded_cs01_chosen);
                    } else {
                        ((ImageView) findViewById(R.id.syllables2))
                                .setImageResource(R.drawable.rounded_cs01_unchosen);
                    }
                    break;
                }
                case 2: {
                    if (mChosenSyllables.get(2).equals("1")) {
                        ((ImageView) findViewById(R.id.syllables3))
                                .setImageResource(R.drawable.rounded_cs01_chosen);
                    } else {
                        ((ImageView) findViewById(R.id.syllables3))
                                .setImageResource(R.drawable.rounded_cs01_unchosen);
                    }
                    break;
                }
            }
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_off_mic);
            mValidateAvailable = false;
        }
    }

    /**
     * setUpListners()
     * called in onCreate. If btnMicDude clicked plays current word. It btnValidate clicked and
     * validate is available will process validation of user choice.
     */
    protected void setUpListeners(){
        findViewById(R.id.btnMicDude).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentAudio.equals(""))
                    playGeneralAudio(mCurrentAudio);
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

    /**
     * processGuess called by validate(). Play right or wrong sound then in time will play word if
     * incorrect number of syllables chosen.  In time then clears all current round data and
     * decides is activity completed of move to next round. It will then return to
     * activities_platform or beginRound depending on conclusion.
     */
    private Runnable processGuess = new Runnable(){

         @Override
         public void run(){
             long mAudioDuration=0;

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
                     if(!mCorrect) {
                         mAudioDuration = playGeneralAudio(mCurrentAudio);
                     }
                     mProcessGuessPosition++;
                     mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                     break;
                 case 2:
                     guessHandler.removeCallbacks(processGuess);
                     clearFrames();
                     if(mCorrect){
                         roundNumber++;
                         if(roundNumber!=(mSyllableWords.size())){
                             mValidateAvailable=false;
                             mProcessGuessPosition++;
                             guessHandler.postDelayed(processGuess, 500);
                         }else{
                             mLastActivityData=0;
                             fadeInOrOutScreenInActivity(false);
                             lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                         }
                     }else{
                         ((ImageView) findViewById(R.id.btnValidate))
                                 .setImageResource(R.drawable.btn_validate_off_mic);
                         mBeingValidated=false;
                         mValidateAvailable=false;
                     }
                     for(int i=0; i<mChosenSyllables.size(); i++){
                         mChosenSyllables.set(i,"0");
                     }
                     break;
                 case 3:
                     ((ImageView) findViewById(R.id.btnValidate))
                             .setImageResource(R.drawable.btn_validate_off_mic);
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

        ArrayList<HashMap<String,String>> mAllGPL
                =new ArrayList<>(appData.getAppUserCurrentGPL());

        projection = new String[]{
                appData.getCurrentActivity().get(0),
                appData.getCurrentUserID(),
                mAllGPL.get(3).get("app_user_id"),
                mAllGPL.get(3).get("group_id"),
                "8",
                mAllGPL.get(3).get("app_user_current_level")};

        String selection=" words.number_of_syllables>=1 " +
                "AND words.number_of_syllables<=3 " +
                "AND variable_phase_levels.group_id="+mAllGPL.get(3).get("group_id")+" " +
                "AND variable_phase_levels.phase_id="+mAllGPL.get(3).get("phase_id")+" ";

        cursorLoader = new CursorLoader(this,
                AppProvider.CONTENT_URI_CURRENT_WORD_SYLLABLE_VALUES,
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
        processData(data);
    }


    /**
     * Not used
     * @param loader  current loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {    }
}
