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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/31/2015.
 *
 * This activity test if the word as 1 2 3 or 4 syllables. The user click either 1 - 4 fingers hand
 * to respond. After click number of syllables user clicks validate button to test if correct of not
 */
public class ActivityCS02 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private int mIncorrectInRound=0;
    private int mCorrectSyllables;
    private int mNumberOfSyllablesSelected;


    private ArrayList<ArrayList<String>> mSyllableWords;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_cs02);
        appData.addToClassOrder(13);
        appData.setActivityType(3);
        fadeInOrOutScreenInActivity(true);
        mInstructionAudio="info_cs02";

        mBeingValidated=true;
        setUpListeners();
        clearFrames();
        allHandsVisible();
        setupFrameListens();

        ((ImageView) findViewById(R.id.syllableImage)).setImageResource(R.drawable.blank_image);

        startActivityHandler.postDelayed(startActivity, 500);
    }//end public void onCreate(Bundle savedInstanceState) {

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);
    }

    /**
     * Process the Data from the database into a ArrayList<> for needed variables of information. 
     * It also shuffles the data to have more random words for  8 words
     * It then calls beginRound
     * @param mCursor data coming from the db
     */
    protected void processData(Cursor mCursor){
        mSyllableWords=new ArrayList<>();
        int mRoundNumber=0;
        if(mCursor.moveToFirst()){
            do{
                mSyllableWords.add(new ArrayList<String>());
                mSyllableWords.get(mRoundNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
                mSyllableWords.get(mRoundNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
                mSyllableWords.get(mRoundNumber)
                .add(mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
                mSyllableWords.get(mRoundNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mSyllableWords.get(mRoundNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mSyllableWords.get(mRoundNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
                mSyllableWords.get(mRoundNumber)
                        .add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
                if (mCursor.getString(mCursor.getColumnIndex("number_of_syllables")).equals("0")) {
                    mSyllableWords.get(mRoundNumber).add("1"); //7

                } else {
                    mSyllableWords.get(mRoundNumber).add(
                            mCursor.getString(mCursor.getColumnIndex("number_of_syllables"))); //7
                }
                mSyllableWords.get(mRoundNumber).add("0"); //8-1
                mSyllableWords.get(mRoundNumber).add("0"); //9-2
                mSyllableWords.get(mRoundNumber).add("0"); //10-3
                mSyllableWords.get(mRoundNumber).add("0"); //11-4


                mRoundNumber++;
                if (mRoundNumber >= Constants.NUMBER_VARIABLES || mCursor.isLast()) {
                    break;
                } else {
                    mCursor.moveToNext();
                }
            }while (!mCursor.isAfterLast());
        }

        Collections.shuffle(mSyllableWords);

        if(mSyllableWords.size()<Constants.NUMBER_VARIABLES ) {
            for (int i = 0; i < mSyllableWords.size(); i++) {
                if (!mSyllableWords.get(mSyllableWords.size() - 1).get(0)
                        .equals(mSyllableWords.get(i).get(0))) {
                    mSyllableWords.add(new ArrayList<>(mSyllableWords.get(i)));
                    if (mSyllableWords.size() >= Constants.NUMBER_VARIABLES) {
                        break;
                    }
                }
            }
        }

        beginRound();
    }

    /**
     * beginRound()
     * Begins each new round for the 8 different words. Sets audio for current word and id for
     * processing storage of results. Places of image of word to count syllable of.
     * If first round will play instruction audio and then audio of word is played after.
     */
    protected void beginRound(){

        mIncorrectInRound=0;
        mBeingValidated=false;
        mCorrectSyllables=Integer.parseInt(mSyllableWords.get(roundNumber).get(7));
        mCurrentAudio=mSyllableWords.get(roundNumber).get(2);
        mCurrentID=mSyllableWords.get(roundNumber).get(0);
        mCorrectID=mSyllableWords.get(roundNumber).get(0);

        try{
            String imageName=mSyllableWords.get(roundNumber)
                    .get(5).replace(".jpg", "").replace(".png", "").trim();
            int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
            ((ImageView) findViewById(R.id.syllableImage)).setImageResource(resID);
        }catch (Exception e) {
            ((ImageView) findViewById(R.id.syllableImage)).setImageResource(R.drawable.blank_image);
            Log.e("MyTag", "Failure to get drawable id.", e);
        }

        long  mAudioDuration=(roundNumber==0) ? playInstructionAudio() :0 ;

        mAudioHandler.postDelayed(playWordAudioTimed, mAudioDuration+20);
    }

    /**
     * validate()
     * Confirms if user chose correct number of syllables for given word. Then marks hand picked
     * or all others except for correct is second time user is wrong
     * Also calls addActivityPoints() in ActivitiesMasterParent to store process. Before going to
     * leveled processGuess
     */
    protected void validate(){
        mBeingValidated=true;
        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
        }else{
            mIncorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            if (mIncorrectInRound >= 2) {
                switch (mCorrectSyllables) {
                    default:
                    case 1:
                        mSyllableWords.get(roundNumber).set(9, "1");
                        mSyllableWords.get(roundNumber).set(10, "1");
                        mSyllableWords.get(roundNumber).set(11, "1");
                        break;
                    case 2:
                        mSyllableWords.get(roundNumber).set(8, "1");
                        mSyllableWords.get(roundNumber).set(10, "1");
                        mSyllableWords.get(roundNumber).set(11, "1");
                        break;
                    case 3:
                        mSyllableWords.get(roundNumber).set(9, "1");
                        mSyllableWords.get(roundNumber).set(8, "1");
                        mSyllableWords.get(roundNumber).set(11, "1");
                        break;
                    case 4:
                        mSyllableWords.get(roundNumber).set(9, "1");
                        mSyllableWords.get(roundNumber).set(10, "1");
                        mSyllableWords.get(roundNumber).set(8, "1");
                        findViewById(R.id.syllableHand4)
                                .setBackgroundResource(R.drawable.rounded_border_incorrect);
                        break;
                }
            }
            switch (mNumberOfSyllablesSelected) {
                default:
                case 1:
                    mSyllableWords.get(roundNumber).set(8, "1");
                    break;
                case 2:
                    mSyllableWords.get(roundNumber).set(9, "1");
                    break;
                case 3:
                    mSyllableWords.get(roundNumber).set(10, "1");
                    break;
                case 4:
                    mSyllableWords.get(roundNumber).set(11, "1");
                    break;
            }
        }

        addActivityPoints();
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }


    /**
     * processHandVisibility()
     * Removes all hands except correct one if correct one chosen
     * OR Removes all hands except correct one if incorrect one chosen twice
     * OR Removes incorrect hand chosen
     */
    private void processHandVisibility(){
        if(mCorrect){
            switch(mCorrectSyllables) {
                default:
                case 1:
                    findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                    break;
                case 2:
                    findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                    break;
                case 3:
                    findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                    break;
                case 4:
                    findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                    break;
            }
        }else {
            if (mIncorrectInRound >= 2) {
                switch (mCorrectSyllables) {
                    default:
                    case 1:
                        findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 2:
                        findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 3:
                        findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 4:
                        findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                        findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                        break;
                }
            } else {
                switch (mNumberOfSyllablesSelected) {
                    default:
                    case 1:
                        findViewById(R.id.syllableHand1).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 2:
                        findViewById(R.id.syllableHand2).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 3:
                        findViewById(R.id.syllableHand3).setVisibility(ImageView.INVISIBLE);
                        break;
                    case 4:
                        findViewById(R.id.syllableHand4).setVisibility(ImageView.INVISIBLE);
                        break;
                }
            }
        }
    }

    /**
     * setupFrameListens()
     * Called from onCreate. Changes mChosenSyllables related value to true if click and other
     * previous values have been clicked. Then calls setUpFrame for selected box to properly color.
     * If correct hand makes mCorrect = true otherwise it is false for use in validation
     */
    protected void setupFrameListens() {
        findViewById(R.id.syllableHand1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mSyllableWords.get(roundNumber).get(8).equals("1") && !mBeingValidated) {
                    mNumberOfSyllablesSelected = 1;
                    mCorrect = (mSyllableWords.get(roundNumber).get(7).equals("1"));
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.syllableHand2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mSyllableWords.get(roundNumber).get(9).equals("1") && !mBeingValidated) {
                    mNumberOfSyllablesSelected = 2;
                    mCorrect = (mSyllableWords.get(roundNumber).get(7).equals("2"));
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.syllableHand3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mSyllableWords.get(roundNumber).get(10).equals("1") && !mBeingValidated) {
                    mNumberOfSyllablesSelected = 3;
                    mCorrect = (mSyllableWords.get(roundNumber).get(7).equals("3"));
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.syllableHand4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mSyllableWords.get(roundNumber).get(11).equals("1") && !mBeingValidated) {
                    mNumberOfSyllablesSelected = 4;
                    mCorrect = (mSyllableWords.get(roundNumber).get(7).equals("4"));
                    setUpFrame();
                }
            }
        });
    }

    
    /**
     * clearFrames() to default of unchosen
     */
    private void clearFrames(){
        ((ImageView) findViewById(R.id.syllableFrame1)).setImageResource(R.drawable.frm_cs02_off);
        ((ImageView) findViewById(R.id.syllableFrame2)).setImageResource(R.drawable.frm_cs02_off);
        ((ImageView) findViewById(R.id.syllableFrame3)).setImageResource(R.drawable.frm_cs02_off);
        ((ImageView) findViewById(R.id.syllableFrame4)).setImageResource(R.drawable.frm_cs02_off);
    }

    /**
     * allHandsVisible()
     */
    private void allHandsVisible(){
        findViewById(R.id.syllableHand1).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.syllableHand2).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.syllableHand3).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.syllableHand4).setVisibility(ImageView.VISIBLE);
    }

    /**
     * setUpFrame()
     * sets validate available & frame on based upon hand representing number of syllables selected
     */
    private void setUpFrame() {
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        switch (mNumberOfSyllablesSelected){
            default:
            case 1:
                ((ImageView) findViewById(R.id.syllableFrame1))
                        .setImageResource(R.drawable.frm_cs02_on);
                break;
            case 2:
                ((ImageView) findViewById(R.id.syllableFrame2))
                        .setImageResource(R.drawable.frm_cs02_on);
                break;
            case 3:
                ((ImageView) findViewById(R.id.syllableFrame3))
                        .setImageResource(R.drawable.frm_cs02_on);
                break;
            case 4:
                ((ImageView) findViewById(R.id.syllableFrame4))
                        .setImageResource(R.drawable.frm_cs02_on);
                break;
        }
        mValidateAvailable=true;
    }

    /**
     * setUpListners()
     * called in onCreate. If btnMicDude clicked plays current word. It btnValidate clicked and
     * validate is available will process validation of user choice.
     */
    protected void setUpListeners(){
        findViewById(R.id.syllableImage).setOnClickListener(new View.OnClickListener() {
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
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, playCorrectOrNot(mCorrect)+10);
                    break;
                case 1:
                    processHandVisibility();
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
                                .setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                case 3:
                    allHandsVisible();
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
        CursorLoader cursorLoader;

        ArrayList<HashMap<String,String>> mAllGPL
                =new ArrayList<>(appData.getAppUserCurrentGPL());

        String[] projection = new String[]{
                appData.getCurrentActivity().get(0),
                appData.getCurrentUserID(),
                mAllGPL.get(3).get("app_user_id"),
                mAllGPL.get(3).get("group_id"),
                "8",
                mAllGPL.get(3).get("app_user_current_level")};

        String selection=" words.number_of_syllables>=1 " +
                "AND words.number_of_syllables<=4 " +
                "AND variable_phase_levels.group_id="+mAllGPL.get(3).get("group_id")+" " +
                "AND variable_phase_levels.phase_id="+mAllGPL.get(3).get("phase_id")+" ";

        cursorLoader = new CursorLoader(this,
                AppProvider.CONTENT_URI_CURRENT_WORD_SYLLABLE_VALUES, projection, selection,
                null, null);

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
            case Constants.ACTIVITY_CURRENT_WRDS_LTRS:{
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
}
