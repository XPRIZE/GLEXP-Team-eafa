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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 9/28/2015.
 */
public class ActivityRS01 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private int mIncorrectInRound=0;
    private int mCurrentSyllableLocation=0;

    private ArrayList<ArrayList<String>> mCurrentSyllables;
    private ArrayList<ArrayList<String>> mAllSyllables;

    private ArrayList<ArrayList<ArrayList<String>>> mAllActivityText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_rs01);
        appData.addToClassOrder(16);

        mAllActivityText = new ArrayList<>();
        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        mInstructionAudio="info_rs01";
        mBeingValidated=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();
        
        startActivityHandler.postDelayed(startActivity, 500);
    }//end public void onCreate(Bundle savedInstanceState) {

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.CURRENT_SYLLABLE, null, this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){

        mIncorrectInRound=0;
        
        mCurrentSyllables = new ArrayList<>(mAllActivityText.get(roundNumber));
        Collections.shuffle(mCurrentSyllables);

        ((TextView) findViewById(R.id.syllableText1)).setText(mCurrentSyllables.get(0).get(2));

        if(mCurrentSyllables.get(0).get(0).equals("1")){
            mCorrectLocation=0;
            matchingLtrWrdAudio=mCurrentSyllables.get(0).get(3);
        }


        ((TextView) findViewById(R.id.syllableText2)).setText(mCurrentSyllables.get(1).get(2));

        if(mCurrentSyllables.get(1).get(0).equals("1")){
            mCorrectLocation=1;
            matchingLtrWrdAudio=mCurrentSyllables.get(1).get(3);
        }

        ((TextView) findViewById(R.id.syllableText3)).setText(mCurrentSyllables.get(2).get(2));

        if(mCurrentSyllables.get(2).get(0).equals("1")){
            mCorrectLocation=2;
            matchingLtrWrdAudio=mCurrentSyllables.get(2).get(3);
        }

        ((TextView) findViewById(R.id.syllableText4)).setText(mCurrentSyllables.get(3).get(2));

        if(mCurrentSyllables.get(3).get(0).equals("1")){
            mCorrectLocation=3;
            matchingLtrWrdAudio=mCurrentSyllables.get(3).get(3);
        }
        long mAudioDuration=0;
        if(roundNumber==0)
            mAudioDuration=playInstructionAudio();

        mAudioHandler.postDelayed(playLtrAudio, mAudioDuration+20);

    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    private void clearActivity(){





        ((TextView) findViewById(R.id.syllableText1)).setText("");
        ((TextView) findViewById(R.id.syllableText2)).setText("");
        ((TextView) findViewById(R.id.syllableText3)).setText("");
        ((TextView) findViewById(R.id.syllableText4)).setText("");

        ((TextView) findViewById(R.id.syllableText1)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText2)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText3)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText4)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));


        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off_mic);

        clearFrames();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok_mic);
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok_mic);
        }
        processFontColorAndPoints();
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    private void processFontColorAndPoints(){
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCurrentID
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        if(mCorrect){
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok_mic);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentID,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
            switch(mCurrentSyllableLocation){
                default:
                case 0:
                    mCurrentSyllables.get(0).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    break;
                case 1:
                    mCurrentSyllables.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText2)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    break;
                case 2:
                    mCurrentSyllables.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText3)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    break;
                case 3:
                    mCurrentSyllables.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText4)).setTextColor(
                            ContextCompat.getColor(this,R.color.correct_green));
                    break;
            }


        }else{
            mIncorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok_mic);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCurrentID,
                    String.valueOf(mIncorrectInRound)};

            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        mCurrentSyllables.get(1).add(6, "1");
                        mCurrentSyllables.get(2).add(6, "1");
                        mCurrentSyllables.get(3).add(6, "1");
                        break;
                    case 1:
                        mCurrentSyllables.get(0).add(6, "1");
                        mCurrentSyllables.get(2).add(6, "1");
                        mCurrentSyllables.get(3).add(6, "1");
                        break;
                    case 2:
                        mCurrentSyllables.get(1).add(6, "1");
                        mCurrentSyllables.get(0).add(6, "1");
                        mCurrentSyllables.get(3).add(6, "1");
                        break;
                    case 3:
                        mCurrentSyllables.get(1).add(6, "1");
                        mCurrentSyllables.get(2).add(6, "1");
                        mCurrentSyllables.get(0).add(6, "1");
                        break;
                }
            }
            switch(mCurrentSyllableLocation){
                default:
                case 0:
                    mCurrentSyllables.get(0).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
                case 1:
                    mCurrentSyllables.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText2)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
                case 2:
                    mCurrentSyllables.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText3)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
                case 3:
                    mCurrentSyllables.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText4)).setTextColor(
                            ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
            }


        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    private void processChoiceView(){
        if(mCorrect){
            switch(mCurrentSyllableLocation){
                default:
                case 0:
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 1:
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 2:
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 3:
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    break;
            }
        }else{
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 2:
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        break;
                }
            }else{
                switch(mCurrentSyllableLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        break;
                    case 2:
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable processGuess = new Runnable(){

        //mBeingValidated=false;


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
                    mAudioDuration=playGeneralAudio(currentLtrWrdAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    processChoiceView();
                    guessHandler.removeCallbacks(processGuess);
                    clearFrames();
                    if(mCorrect){
                        roundNumber++;
                        if(roundNumber!=(mAllActivityText.size())){
                            //mBeingValidated=false;
                            mValidateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;

                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));

                        }
                    }else{
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off_mic);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                }
                case 3:{
                    ((ImageView) findViewById(R.id.btnValidate))
                             .setImageResource(R.drawable.btn_validate_off_mic);
                    displayScreen();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };

    
    /////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_rs01_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_rs01_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_rs01_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_rs01_off);


    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    protected void setupFrameListens(){
        findViewById(R.id.syllableText1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentSyllables.get(0).get(6).equals("0") && !mBeingValidated){
                    mCurrentSyllableLocation=0;
                    currentLtrWrdAudio=mCurrentSyllables.get(0).get(3);
                    mCurrentID=mCurrentSyllables.get(0).get(1);
                    mCorrect=(mCurrentSyllables.get(0).get(0).equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.syllableText2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentSyllables.get(1).get(6).equals("0") && !mBeingValidated) {
                    mCurrentSyllableLocation = 1;
                    currentLtrWrdAudio = mCurrentSyllables.get(1).get(3);
                    mCurrentID = mCurrentSyllables.get(1).get(1);
                    mCorrect = (mCurrentSyllables.get(1).get(0).equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.syllableText3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentSyllables.get(2).get(6).equals("0") && !mBeingValidated){
                    mCurrentSyllableLocation=2;
                    currentLtrWrdAudio=mCurrentSyllables.get(2).get(3);
                    mCurrentID=mCurrentSyllables.get(2).get(1);
                    mCorrect=(mCurrentSyllables.get(2).get(0).equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.syllableText4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentSyllables.get(3).get(6).equals("0") && !mBeingValidated) {
                    mCurrentSyllableLocation = 3;
                    currentLtrWrdAudio = mCurrentSyllables.get(3).get(3);
                    mCurrentID = mCurrentSyllables.get(3).get(1);
                    mCorrect = (mCurrentSyllables.get(3).get(0).equals("1"));
                    setUpFrame(3);
                }
            }
        });


    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_on_mic);
        mValidateAvailable=true;
        switch(frameNumber){
            default:
            case 0:{
                ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_rs01_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_rs01_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_rs01_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_rs01_on);
                break;
            }
        }
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////
    
    protected long playInstructionAudio(){
        long mAudioDuration=super.playInstructionAudio();
        mAudioHandler.postDelayed(playLtrAudio, mAudioDuration+20);
        return mAudioDuration;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playLtrAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playLtrAudio);
            mBeingValidated=false;
            if(!matchingLtrWrdAudio.equals(""))
                playGeneralAudio(matchingLtrWrdAudio);
        }
    };
    
    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_SYLLABLE_WRDS:{
                String mSyllableWordSelection = "variable_phase_levels.group_id = 3 " +
                        "AND variable_phase_levels.phase_id = 1 " +
                        "AND variable_phase_levels.level_number = 1 "+
                        "AND variable_type_relations.variable_type_id=4";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_SYLLABLE_WORDS,
                        null,null,null,mSyllableWordSelection);
                break;
            }
            case Constants.CURRENT_SYLLABLE:{

                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID()};
                String mSelection="";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_SYLLABLES, projection, mSelection,
                        null, null);
                break;
            }
        }

        return cursorLoader;
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<ArrayList<String>>  mAllSyllableWords;
        switch(loader.getId()) {
            default:
                break;
            case Constants.CURRENT_SYLLABLE_WRDS:{
                mAllSyllableWords=new ArrayList<>();
                int currentPhonicWordNumber=0;

                for (data.moveToFirst(); !data.isAfterLast();data.moveToNext()){
                    mAllSyllableWords.add(new ArrayList<String>());
                    mAllSyllableWords.get(currentPhonicWordNumber).add(
                            data.getString(data.getColumnIndex("syllabe_word_id")));
                    mAllSyllableWords.get(currentPhonicWordNumber).add(
                            data.getString(data.getColumnIndex("syllabe_word_text")));
                    mAllSyllableWords.get(currentPhonicWordNumber).add(
                            data.getString(data.getColumnIndex("syllabe_word_audio")));
                    mAllSyllableWords.get(currentPhonicWordNumber).add(
                            data.getString(data.getColumnIndex("syllabe_word_image")));
                    mAllSyllableWords.get(currentPhonicWordNumber).add(
                            data.getString(data.getColumnIndex("syllabe_word_first_id")));
                    mAllSyllableWords.get(currentPhonicWordNumber).add(
                            data.getString(data.getColumnIndex("syllabe_word_last_id")));
                    currentPhonicWordNumber++;
                }
                getLoaderManager().restartLoader(Constants.CURRENT_SYLLABLE, null, this);
                break;
            }
            case Constants.CURRENT_SYLLABLE:{

                processData(data);
                break;
            }
        }//end switch
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        int mNumberOfPhonicVariables=(mCursor.getCount()*2>Constants.NUMBER_PHONIC_VARIABLES)
                ? Constants.NUMBER_PHONIC_VARIABLES : mCursor.getCount()*2;

        mCurrentSyllables=new ArrayList<>();
        int mSyllableNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentGSP.put("current_level",
                    mCursor.getString(mCursor.getColumnIndex("app_user_current_level")));
            mCurrentSyllables.add(new ArrayList<String>());
            mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("syllables_id"))); //0
            mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("syllables_text"))); //1
            mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("syllables_audio"))); //2
            mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("number_correct"))); //3
            mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("number_incorrect"))); //4
            mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("level_number"))); //6
            mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("syllable_sister_id"))); //7

            mSyllableNumber++;
            if (mSyllableNumber >=mNumberOfPhonicVariables) {
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else{
                mCursor.moveToNext();
            }
        }


        Collections.shuffle(mCurrentSyllables);

        ArrayList<ArrayList<String>> mTempCurrentWords=new ArrayList<>();

        String mPreviousWordId="0";
        for(int i=0; i<mNumberOfPhonicVariables; i++){
            if(!mCurrentSyllables.get(i).get(0).equals(mPreviousWordId)){
                mTempCurrentWords.add(new ArrayList<>(mCurrentSyllables.get(i)));
            }else{
                if((i+1)<mNumberOfPhonicVariables){
                    mTempCurrentWords.add(new ArrayList<>(mCurrentSyllables.get(i+1)));
                    mTempCurrentWords.add(new ArrayList<>(mCurrentSyllables.get(i)));
                    i++;
                }else{
                    mTempCurrentWords.add(new ArrayList<>(mTempCurrentWords.get(0)));
                    mTempCurrentWords.set(0,mCurrentSyllables.get(i));
                }
            }
            mPreviousWordId=mCurrentSyllables.get(i).get(0);
        }

        mCurrentSyllables=new ArrayList<>(mTempCurrentWords);

        mAllSyllables=new ArrayList<>();
        mSyllableNumber=0;
        int mNumberCorrectInARow=Constants.INA_ROW_CORRECT;
        if(mCursor.moveToFirst()){
            do {
                mAllSyllables.add(new ArrayList<String>());
                mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("syllables_id"))); //0
                mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("syllables_text"))); //1
                mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("syllables_audio"))); //2
                mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("number_correct"))); //3
                mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("number_incorrect"))); //4
                mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("number_correct_in_a_row"))); //5
                mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("level_number"))); //6
                mAllSyllables.get(mSyllableNumber).add(mCursor.getString(
                        mCursor.getColumnIndex("syllable_sister_id"))); //7

                mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(
                        mAllSyllables.get(mSyllableNumber).get(5)))
                        ? Integer.parseInt(mAllSyllables.get(mSyllableNumber).get(5))
                        :mNumberCorrectInARow;
                mSyllableNumber++;
            }while(mCursor.moveToNext());
        }


        shuffleGameData();
        displayScreen();

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        findViewById(R.id.btnMicDude).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!matchingLtrWrdAudio.equals(""))
                    playGeneralAudio(matchingLtrWrdAudio);
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

    ////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void shuffleGameData(){
        mAllActivityText.clear();
        ArrayList<ArrayList<String>> mTempSyllables=new ArrayList<>(mCurrentSyllables);

        for(int mCount=0; mCount<mCurrentSyllables.size();mCount++){
            mAllActivityText.add(new ArrayList<ArrayList<String>>());
            mAllActivityText.get(mCount).add(new ArrayList<String>());
            mAllActivityText.get(mCount).get(0).add("1"); //0


            mAllActivityText.get(mCount).get(0).add(mCurrentSyllables.get(mCount).get(0)); //syllables_id 1
            mAllActivityText.get(mCount).get(0).add(mCurrentSyllables.get(mCount).get(1)); //syllables_text 2
            mAllActivityText.get(mCount).get(0).add(mCurrentSyllables.get(mCount).get(2)); //syllables_audio 3
            mAllActivityText.get(mCount).get(0).add(mCurrentSyllables.get(mCount).get(3)); //number correct 4
            mAllActivityText.get(mCount).get(0).add(mCurrentSyllables.get(mCount).get(4)); //number incorrect 5
            mAllActivityText.get(mCount).get(0).add("0"); //guess yet 6
            mAllActivityText.get(mCount).get(0).add(mCurrentSyllables.get(mCount).get(7)); //syllables_sister_id 7

            Collections.shuffle(mTempSyllables);
            int mSubCount=1;
            for(int i=0;i<mTempSyllables.size();i++){
                if(!mTempSyllables.get(i).get(0).equals(mCurrentSyllables.get(mCount).get(0)) &&
                        mTempSyllables.get(i).get(6).equals(mCurrentSyllables.get(mCount).get(6))){
                    mAllActivityText.get(mCount).add(new ArrayList<String>());
                    mAllActivityText.get(mCount).get(mSubCount).add("0");

                    mAllActivityText.get(mCount).get(mSubCount).add(mTempSyllables.get(i).get(0)); //phonic_id
                    mAllActivityText.get(mCount).get(mSubCount).add(mTempSyllables.get(i).get(1)); //phonic_text
                    mAllActivityText.get(mCount).get(mSubCount).add(mTempSyllables.get(i).get(2)); //phonic_audio
                    mAllActivityText.get(mCount).get(mSubCount).add(mTempSyllables.get(i).get(3)); //number correct
                    mAllActivityText.get(mCount).get(mSubCount).add(mTempSyllables.get(i).get(4)); //number incorrect
                    mAllActivityText.get(mCount).get(mSubCount).add("0"); //guessed yet
                    mAllActivityText.get(mCount).get(mSubCount).add(mTempSyllables.get(i).get(7)); //syllables_sister_id

                    mSubCount++;
                    if((mSubCount==3 && mCurrentSyllables.size()>=12) ||(mSubCount==2 && mCurrentSyllables.size()<12)) {
                        break;
                    }
                }
            }

            Collections.shuffle(mAllSyllables);

            for(int mDistCount=0;mDistCount<mAllSyllables.size(); mDistCount++){
                if(!mAllSyllables.get(mDistCount).get(0)
                        .equals(mCurrentSyllables.get(mCount).get(0)) ){

                    boolean mPhonicOkay=true;
                    for(ArrayList mTemp : mAllActivityText.get(mCount) ) {
                        if (mAllSyllables.get(mDistCount).get(0).equals(mTemp.get(1)) ||
                                mAllSyllables.get(mDistCount).get(0).equals(mTemp.get(7)) ) {
                            Log.d(Constants.LOGCAT,"FALSE");
                            mPhonicOkay=false;

                        }
                    }
                    if(mPhonicOkay) {
                        mAllActivityText.get(mCount).add(new ArrayList<String>());
                        mAllActivityText.get(mCount).get(mSubCount).add("0");

                        mAllActivityText.get(mCount).get(mSubCount).add(
                                mAllSyllables.get(mDistCount).get(0)); //syllables_id
                        mAllActivityText.get(mCount).get(mSubCount).add(
                                mAllSyllables.get(mDistCount).get(1)); //syllables_text
                        mAllActivityText.get(mCount).get(mSubCount).add(
                                mAllSyllables.get(mDistCount).get(2)); //syllables_audio
                        mAllActivityText.get(mCount).get(mSubCount).add(
                                mAllSyllables.get(mDistCount).get(3)); //number correct
                        mAllActivityText.get(mCount).get(mSubCount).add(
                                mAllSyllables.get(mDistCount).get(4)); //number incorrect
                        mAllActivityText.get(mCount).get(mSubCount).add("0"); //guessed yet
                        mAllActivityText.get(mCount).get(mSubCount).add(
                                mAllSyllables.get(mDistCount).get(7)); //syllables_sister_id

                        mSubCount++;
                    }
                }
                if(mSubCount>=4){
                    break;
                }



            }



        }//end for(int mCount=0; mCount<mCurrentSyllables.size();mCount++){


        for(int i=0; i<mAllActivityText.size(); i++){
            Collections.shuffle(mAllActivityText.get(i));
        }
        //Collections.shuffle(mAllActivityText);

    }//end private void shuffleGameData(){
}