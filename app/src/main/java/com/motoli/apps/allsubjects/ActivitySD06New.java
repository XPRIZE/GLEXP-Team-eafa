package com.motoli.apps.allsubjects;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/24/2015.
 */
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class ActivitySD06New extends ActivitySDRoot
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private int mCurrentPhonicsWordsLocation=0;
    private int mIncorrectInRound=0;

    private String mCurrentAudio;
    private String mCurrentWordAudio;

    private ArrayList<ArrayList<String>> mCurrentPhonicWords;
    private  ArrayList<String> mCurrentPhonic;
    private ArrayList<ArrayList<String>> mAllPhonics;

    private ArrayList<Bundle> mAllPhonicWordsBundle;

    private static final String mPhncWrdId="phonic_word_id";
    private static final String mPhncWrdText="phonic_word_text";
    private static final String mPhncWrdAudio="phonic_word_audio";
    private static final String mPhncWrdImage="phonic_word_image";
    private static final String mPhncWrdFirst="phonic_word_first_id";
    private static final String mPhncWrdLast="phonic_word_last_id";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sd06);
        appData.addToClassOrder(8);


        mBeingValidated=true;
        mActivityNumber=6;
        mInstructionAudio="info_sd06";
        mPlayPhonicAudio=true;

        setUpListeners();
        visibleProgress();
        clearActivity();
        setupFrameListens();
        ((TextView) findViewById(R.id.phonic0)).setTypeface(appData.getCurrentFontType());

        getLoaderManager().initLoader(Constants.CURRENT_PHONIC_LETTERS, null, this);

    }//end public void onCreate(Bundle savedInstanceState) {


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////

    private void visibleProgress(){



    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){
        ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.blank_image);
        ((TextView) findViewById(R.id.phonic0)).setText("");

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mIncorrectInRound=0;

        mCurrentPhonic = new ArrayList<String>(mAllPhonics.get(roundNumber));


        mCurrentPhonicWords=new ArrayList<ArrayList<String>>();
        mCurrentPhonicWords.clear();

        Collections.shuffle(mAllPhonicWordsBundle);

        ((ImageView) findViewById(R.id.beginningPhonicCircle)).setImageResource(R.drawable.btn_circle);
        ((ImageView) findViewById(R.id.endingPhonicStar)).setImageResource(R.drawable.btn_star);

        boolean foundMatch=false;
        for(int i=0;i<mAllPhonicWordsBundle.size(); i++){
            if (mAllPhonicWordsBundle.get(i).getString(mPhncWrdLast).equals(mCurrentPhonic.get(0))
                    || mAllPhonicWordsBundle.get(i).getString(mPhncWrdFirst).equals(mCurrentPhonic.get(0))) {
                mCurrentPhonicWords.add(new ArrayList<String>());
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdId)); //0
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdText)); //1
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdAudio)); //2
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdImage)); //3
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdFirst)); //4
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdLast)); //5
                if (mAllPhonicWordsBundle.get(i).getString(mPhncWrdFirst).equals(mCurrentPhonic.get(0))) {
                    mCurrentPhonicWords.get(0).add("0"); //6
                    mCorrectLocation=0;
                }else{
                    mCurrentPhonicWords.get(0).add("1"); //6
                    mCorrectLocation=1;
                }
                mCurrentPhonicWords.get(0).add("0"); //7
                mCurrentPhonicWords.get(0).add("0"); //8

                foundMatch=true;
                break;
            }
        }

        long mAudioDuration = 0;
        if (roundNumber == 0)
            mAudioDuration = playInstructionAudio();

        if(foundMatch) {

            mCurrentAudio = mCurrentPhonic.get(2);
            mCurrentWordAudio=mCurrentPhonicWords.get(0).get(2);





            ((TextView) findViewById(R.id.phonic0)).setText(mCurrentPhonic.get(1));


            try{
                String imageName=mCurrentPhonicWords.get(0).get(3).replace(".jpg", "").replace(".png", "").trim();
                //field = res.getField(imageName);
                //drawableId = field.getInt(null);

                int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());

                ((ImageView) findViewById(R.id.image0)).setImageResource(resID);


            }catch (Exception e) {
                ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.blank_image);
                Log.e("MyTag", "Failure to get drawable id.", e);
            }


            mAudioHandler.postDelayed(playWordAudio, mAudioDuration + 20);
        }else{
            mCurrentAudio="";
            mCurrentWordAudio="";
            mCorrect=true;
            mProcessGuessPosition=2;
            guessHandler.postDelayed(processGuess, 10);
        }

    }//end private void displayScreen(Cursor currentWordsCursor){

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Runnable playWordAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playWordAudio);
            mBeingValidated=false;
            if(!mCurrentWordAudio.equals(""))
                playGeneralAudio(mCurrentWordAudio);
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playLtrAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playLtrAudio);
            mBeingValidated=false;
            if(!mCurrentAudio.equals(""))
                playGeneralAudio(mCurrentAudio);
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //end private void displayScreen(Cursor mAllPhonicWordsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens(){
        findViewById(R.id.beginningPhonicCircle).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (/*!mCurrentPhonicWords.get(0).get(7).equals("2") && */!mBeingValidated) {
                    mCurrentPhonicsWordsLocation = 0;
                    mCurrentPhonicWords.get(0).set(7,"1");
                    ((ImageView) findViewById(R.id.beginningPhonicCircle)).setImageResource(R.drawable.btn_circle_select);
                    if (!mCurrentPhonicWords.get(0).get(8).equals("2")) {
                        ((ImageView) findViewById(R.id.endingPhonicStar)).setImageResource(R.drawable.btn_star);
                    }
                    mCorrect = mCurrentPhonicWords.get(0).get(6).equals("0");
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.endingPhonicStar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (/*!mCurrentPhonicWords.get(0).get(8).equals("2") && */ !mBeingValidated) {
                    mCurrentPhonicWords.get(0).set(8,"1");
                    mCurrentPhonicsWordsLocation = 1;
                    if (!mCurrentPhonicWords.get(0).get(7).equals("2")) {
                        ((ImageView) findViewById(R.id.beginningPhonicCircle)).setImageResource(R.drawable.btn_circle);
                    }
                    ((ImageView) findViewById(R.id.endingPhonicStar)).setImageResource(R.drawable.btn_star_select);

                    mCorrect = mCurrentPhonicWords.get(0).get(6).equals("1");
                    setUpFrame();
                }
            }
        });




    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(){
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        mValidateAvailable=true;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCurrentPhonic.get(0)
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;

        if(mCorrect){
            //processLayoutAfterGuess(true);
            //correctWordCount++;


            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_ok);
            if(mIncorrectInRound>=1){
                selectionArgs = new String[]{
                        "0",
                        "0",
                        mCurrentGSP.get("current_level"),
                        mCurrentPhonic.get(0),
                        String.valueOf(mIncorrectInRound),
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID()};
                mAllPhonics.add(mCurrentPhonic);
            }else {
                selectionArgs = new String[]{
                        "1",
                        "0",
                        mCurrentGSP.get("current_level"),
                        mCurrentPhonic.get(0),
                        String.valueOf(mIncorrectInRound),
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID()};
            }

                getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE_MS, null, where, selectionArgs);
            switch(mCurrentPhonicsWordsLocation){
                default:
                case 0:
                    ((ImageView) findViewById(R.id.beginningPhonicCircle)).setImageResource(R.drawable.btn_sd06_circle_right);
                    break;
                case 1:
                    ((ImageView) findViewById(R.id.endingPhonicStar)).setImageResource(R.drawable.btn_sd06_star_right);
                    break;
            }


        }else{
            mIncorrectInRound++;

            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCurrentPhonic.get(0),
                    String.valueOf(mIncorrectInRound),
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE_MS, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        mCurrentPhonicWords.get(0).set(8,"2");
                        ((ImageView) findViewById(R.id.beginningPhonicCircle)).setImageResource(R.drawable.btn_circle_select);
                        ((ImageView) findViewById(R.id.endingPhonicStar)).setImageResource(R.drawable.btn_sd06_star_wrong);

                        break;
                    case 1:
                        mCurrentPhonicWords.get(0).set(7,"2");
                        ((ImageView) findViewById(R.id.endingPhonicStar)).setImageResource(R.drawable.btn_star_select);
                        ((ImageView) findViewById(R.id.beginningPhonicCircle)).setImageResource(R.drawable.btn_sd06_circle_wrong);
                        break;
                }
            }else{
                switch(mCurrentPhonicsWordsLocation){
                    default:
                    case 0:
                        mCurrentPhonicWords.get(0).set(7,"2");
                        ((ImageView) findViewById(R.id.beginningPhonicCircle)).setImageResource(R.drawable.btn_sd06_circle_wrong);
                        break;
                    case 1:
                        mCurrentPhonicWords.get(0).set(8,"2");
                        ((ImageView) findViewById(R.id.endingPhonicStar)).setImageResource(R.drawable.btn_sd06_star_wrong);
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }

        //processPoints();


        //mProcessGuessPosition=0;

        //guessHandler.postDelayed(processGuess, 10);

        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);




    }

    ////////////////////////////////////////////////////////////////////////////////////////////

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
                    guessHandler.removeCallbacks(processGuess);
                    if(mCorrect){
                        roundNumber++;
                        if(roundNumber!=(mAllPhonics.size())){
                            //mBeingValidated=false;
                            mValidateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                }
                case 3:{
                    ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);

                    guessHandler.removeCallbacks(processGuess);
                    displayScreen();
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };



    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        super.setUpListeners();
        findViewById(R.id.phonic0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentAudio.equals("") && !mBeingValidated){
                    playClickSound();
                    playGeneralAudio(mCurrentAudio);
                }

            }
        });

        findViewById(R.id.image0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentWordAudio.equals("") && !mBeingValidated){
                    playClickSound();
                    playGeneralAudio(mCurrentWordAudio);
                }

            }
        });



    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader mCursorLoader;
        switch(id){
            case Constants.CURRENT_PHONIC_LETTERS_WORDS_SINGLE:{
                String mWhere=" phonic_words.phonic_word_last_id" +
                        "="+mRoundPhonics.get(0).get("phonic_id")+" " +
                        "AND phonics.phonic_id" +
                        "="+mRoundPhonics.get(0).get("phonic_id")+" ";
                String[] mInfo = new String[]{"1"};
                mCursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS_WORDS_LAST_LETTER,
                        mInfo,mWhere,null,null);
                break;
            }
            default:

            case Constants.CURRENT_PHONIC_LETTERS:{

                String[] mInfo = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        mCurrentGSP.get("current_level")};
                String mWhere="variable_phase_levels.group_id="+mCurrentGSP.get("group_id")+ " " +
                        "AND variable_phase_levels.phase_id="+mCurrentGSP.get("phase_id") +" " +
                        "AND variable_phase_levels.level_number" +
                        "<="+mCurrentGSP.get("current_level") +" " +
                        "AND variable_type_relations.variable_type_id=3 ";

                mCursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS, mInfo, mWhere, null, null);
                break;
            }
        }

        return mCursorLoader;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<ArrayList<String>> mAllPhonicWords;

        switch(loader.getId()) {
            default:
                break;
            case Constants.CURRENT_PHONIC_LETTERS_WORDS_SINGLE:{
                processSinglePhonicWord(data);

                getLoaderManager().restartLoader(Constants.CURRENT_PHONIC_LETTERS, null, this);
                break;
            }
            case Constants.CURRENT_PHONIC_LETTERS:{
                processData(data);
                break;
            }
        }// end switch
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        super.processData(mCursor);
        beginRound();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void beginRound(){
        super.beginRound();
        mCorrectAudio=mRoundPhonics.get(0).get("phonic_audio");
        getLoaderManager().restartLoader(Constants.CURRENT_PHONIC_LETTERS_WORDS_SINGLE,
                null, this);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }



    ////////////////////////////////////////////////////////////////////////////////////////////







    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

}
