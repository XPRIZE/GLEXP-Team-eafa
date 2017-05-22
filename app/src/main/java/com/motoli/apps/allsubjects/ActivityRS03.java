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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 9/24/2015.
 */
public class ActivityRS03 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private int mCurrentSyllablesWordsLocation=0;
    private int mIncorrectInRound=0;

    private String mCurrentSyllableAudio;
    private String mCurrentWordAudio;

    private ArrayList<ArrayList<String>> mCurrentSyllableWords;
    private  ArrayList<String> mCurrentSyllable;
    private ArrayList<ArrayList<String>> mAllSyllables;

    private ArrayList<HashMap<String,String>> mAllSyllableWordsHashMap;

    private static final String mSyllableWordId="syllable_word_id";
    private static final String mSyllableWordText="syllable_word_text";
    private static final String mSyllableWordAudio="syllable_word_audio";
    private static final String mSyllableWordImage="syllable_word_image";
    private static final String mSyllableWordFirst="syllable_word_first_id";
    private static final String mSyllableWordLast="syllable_word_last_id";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_rs03);
        appData.addToClassOrder(8);
        mRoundNumber = 0;
        mBeingValidated=true;
        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));


        mInstructionAudio="info_rs03";
        setUpListeners();
        visibleProgress();
        clearActivity();
        setupFrameListens();
        ((TextView) findViewById(R.id.btnTextRS03)).setTypeface(appData.getCurrentFontType());

        startActivityHandler.postDelayed(startActivity, 500);
    }//end public void onCreate(Bundle savedInstanceState) {

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.CURRENT_SYLLABLE_WRDS, null, this);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration= super.playInstructionAudio();
        mAudioHandler.postDelayed(playWordAudio, mAudioDuration + 20);
        return mAudioDuration;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////

    private void visibleProgress(){



    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){
        ((ImageView) findViewById(R.id.btnImageRS03)).setImageResource(R.drawable.blank_image);
        ((TextView) findViewById(R.id.btnTextRS03)).setText("");

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        mIncorrectInRound=0;

        mCurrentSyllable = new ArrayList<>(mAllSyllables.get(mRoundNumber));


        mCurrentSyllableWords=new ArrayList<>();
        mCurrentSyllableWords.clear();

        Collections.shuffle(mAllSyllableWordsHashMap);

        ((ImageView) findViewById(R.id.btnCircleRS03)).setImageResource(R.drawable.btn_circle);
        ((ImageView) findViewById(R.id.btnStarRS03)).setImageResource(R.drawable.btn_star);

        boolean foundMatch=false;
        for(int i=0;i<mAllSyllableWordsHashMap.size(); i++){
            if (mAllSyllableWordsHashMap.get(i).get(mSyllableWordLast)
                    .equals(mCurrentSyllable.get(0))
                    || mAllSyllableWordsHashMap.get(i).get(mSyllableWordFirst)
                    .equals(mCurrentSyllable.get(0))) {
                mCurrentSyllableWords.add(new ArrayList<String>());
                mCurrentSyllableWords.get(0).add(
                        mAllSyllableWordsHashMap.get(i).get(mSyllableWordId)); //0
                mCurrentSyllableWords.get(0).add
                        (mAllSyllableWordsHashMap.get(i).get(mSyllableWordText)); //1
                mCurrentSyllableWords.get(0).add(
                        mAllSyllableWordsHashMap.get(i).get(mSyllableWordAudio)); //2
                mCurrentSyllableWords.get(0).add
                        (mAllSyllableWordsHashMap.get(i).get(mSyllableWordImage)); //3
                mCurrentSyllableWords.get(0).add(
                        mAllSyllableWordsHashMap.get(i).get(mSyllableWordFirst)); //4
                mCurrentSyllableWords.get(0).add(
                        mAllSyllableWordsHashMap.get(i).get(mSyllableWordLast)); //5
                if (mAllSyllableWordsHashMap.get(i).get(mSyllableWordFirst)
                        .equals(mCurrentSyllable.get(0))) {
                    mCurrentSyllableWords.get(0).add("0"); //6
                    mCorrectLocation=0;
                }else{
                    mCurrentSyllableWords.get(0).add("1"); //6
                    mCorrectLocation=1;
                }
                mCurrentSyllableWords.get(0).add("0"); //7
                mCurrentSyllableWords.get(0).add("0"); //8

                foundMatch=true;
                break;
            }
        }

        long mAudioDuration = 0;
        if (mRoundNumber == 0)
            mAudioDuration = playInstructionAudio();

        if(foundMatch) {

            mCurrentSyllableAudio = mCurrentSyllable.get(2);
            mCurrentWordAudio=mCurrentSyllableWords.get(0).get(2);





            ((TextView) findViewById(R.id.btnTextRS03)).setText(mCurrentSyllable.get(1));


            try{
                String imageName=mCurrentSyllableWords.get(0).get(3)
                        .replace(".jpg", "").replace(".png", "").trim();
                //field = res.getField(imageName);
                //drawableId = field.getInt(null);

                int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());

                ((ImageView) findViewById(R.id.btnImageRS03)).setImageResource(resID);


            }catch (Exception e) {
                ((ImageView) findViewById(R.id.btnImageRS03))
                        .setImageResource(R.drawable.blank_image);
                Log.e("MyTag", "Failure to get drawable id.", e);
            }


            mAudioHandler.postDelayed(playWordAudio, mAudioDuration + 20);
        }else{
            mCurrentSyllableAudio="";
            mCurrentWordAudio="";
            mCorrect=true;
            mProcessGuessPosition=2;
            guessHandler.postDelayed(processGuess, 10);
        }

    }//end private void displayScreen(Cursor currentWordsCursor){

    ///////////////////////////////////////////////////////////////////////////////////////////

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
            if(!mCurrentSyllableAudio.equals(""))
                playGeneralAudio(mCurrentSyllableAudio);
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setupFrameListens(){
        findViewById(R.id.btnCircleRS03).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (/*!mCurrentSyllableWords.get(0).get(7).equals("2") && */!mBeingValidated) {
                    mCurrentSyllablesWordsLocation = 0;
                    mCurrentSyllableWords.get(0).set(7,"1");
                    ((ImageView) findViewById(R.id.btnCircleRS03))
                            .setImageResource(R.drawable.btn_circle_select);
                    if (!mCurrentSyllableWords.get(0).get(8).equals("2")) {
                        ((ImageView) findViewById(R.id.btnStarRS03))
                                .setImageResource(R.drawable.btn_star);
                    }
                    mCorrect = mCurrentSyllableWords.get(0).get(6).equals("0");
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.btnStarRS03).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (/*!mCurrentSyllableWords.get(0).get(8).equals("2") && */ !mBeingValidated) {
                    mCurrentSyllableWords.get(0).set(8,"1");
                    mCurrentSyllablesWordsLocation = 1;
                    if (!mCurrentSyllableWords.get(0).get(7).equals("2")) {
                        ((ImageView) findViewById(R.id.btnCircleRS03))
                                .setImageResource(R.drawable.btn_circle);
                    }
                    ((ImageView) findViewById(R.id.btnStarRS03))
                            .setImageResource(R.drawable.btn_star_select);

                    mCorrect = mCurrentSyllableWords.get(0).get(6).equals("1");
                    setUpFrame();
                }
            }
        });




    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(){
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        mValidateAvailable=true;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCurrentSyllable.get(0)
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        if(mCorrect){
            //processLayoutAfterGuess(true);
            //correctWordCount++;


            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            if(mIncorrectInRound>=1){
                selectionArgs = new String[]{
                        "0",
                        "0",
                        mCurrentGSP.get("current_level"),
                        mCurrentSyllable.get(0),
                        String.valueOf(mIncorrectInRound)};
                mAllSyllables.add(mCurrentSyllable);
            }else {
                selectionArgs = new String[]{
                        "1",
                        "0",
                        mCurrentGSP.get("current_level"),
                        mCurrentSyllable.get(0),
                        String.valueOf(mIncorrectInRound)};
            }

            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
            switch(mCurrentSyllablesWordsLocation){
                default:
                case 0:
                    ((ImageView) findViewById(R.id.btnCircleRS03))
                            .setImageResource(R.drawable.btn_rs03_circle_right);
                    break;
                case 1:
                    ((ImageView) findViewById(R.id.btnStarRS03))
                            .setImageResource(R.drawable.btn_rs03_star_right);
                    break;
            }


        }else{
            mIncorrectInRound++;

            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCurrentSyllable.get(0),
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        mCurrentSyllableWords.get(0).set(8,"2");
                        ((ImageView) findViewById(R.id.btnCircleRS03))
                                .setImageResource(R.drawable.btn_circle_select);
                        ((ImageView) findViewById(R.id.btnStarRS03))
                                .setImageResource(R.drawable.btn_rs03_star_wrong);

                        break;
                    case 1:
                        mCurrentSyllableWords.get(0).set(7,"2");
                        ((ImageView) findViewById(R.id.btnStarRS03))
                                .setImageResource(R.drawable.btn_star_select);
                        ((ImageView) findViewById(R.id.btnCircleRS03))
                                .setImageResource(R.drawable.btn_rs03_circle_wrong);
                        break;
                }
            }else{
                switch(mCurrentSyllablesWordsLocation){
                    default:
                    case 0:
                        mCurrentSyllableWords.get(0).set(7,"2");
                        ((ImageView) findViewById(R.id.btnCircleRS03))
                                .setImageResource(R.drawable.btn_rs03_circle_wrong);
                        break;
                    case 1:
                        mCurrentSyllableWords.get(0).set(8,"2");
                        ((ImageView) findViewById(R.id.btnStarRS03))
                                .setImageResource(R.drawable.btn_rs03_star_wrong);
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }

        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

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
                    mAudioDuration=playGeneralAudio(currentLtrWrdAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                case 2:
                    guessHandler.removeCallbacks(processGuess);
                    if(mCorrect){
                        mRoundNumber++;
                        if(mRoundNumber!=(mAllSyllables.size())){
                            //mBeingValidated=false;
                            mValidateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;

                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                            fadeInOrOutScreenInActivity(false);

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

                    guessHandler.removeCallbacks(processGuess);
                    displayScreen();
                    break;
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_SYLLABLE_WRDS:{
                String selection="syllable_words.syllable_word_text!=''";


                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_SYLLABLE_WORDS,null,selection,null,null);
                break;
            }
            case Constants.CURRENT_SYLLABLE:{

                mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        mCurrentGSP.get("current_level")};
                String selection="";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_SYLLABLES,
                        projection, selection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch(loader.getId()) {
            default:
                break;
            case Constants.CURRENT_SYLLABLE_WRDS:{
                mAllSyllableWordsHashMap=new ArrayList<>();
                int currentSyllableWordNumber=0;

                for (data.moveToFirst(); !data.isAfterLast();data.moveToNext()){
                    mAllSyllableWordsHashMap.add(new HashMap<String,String>());

                    mAllSyllableWordsHashMap.get(currentSyllableWordNumber).put(mSyllableWordId,
                            data.getString(data.getColumnIndex(mSyllableWordId)));
                    mAllSyllableWordsHashMap.get(currentSyllableWordNumber).put(mSyllableWordText,
                            data.getString(data.getColumnIndex(mSyllableWordText)));
                    mAllSyllableWordsHashMap.get(currentSyllableWordNumber).put(mSyllableWordAudio,
                            data.getString(data.getColumnIndex(mSyllableWordAudio)));
                    mAllSyllableWordsHashMap.get(currentSyllableWordNumber).put(mSyllableWordImage,
                            data.getString(data.getColumnIndex(mSyllableWordImage)));
                    mAllSyllableWordsHashMap.get(currentSyllableWordNumber).put(mSyllableWordFirst,
                            data.getString(data.getColumnIndex(mSyllableWordFirst)));
                    mAllSyllableWordsHashMap.get(currentSyllableWordNumber).put(mSyllableWordLast,
                            data.getString(data.getColumnIndex(mSyllableWordLast)));


                    currentSyllableWordNumber++;
                }
                getLoaderManager().restartLoader(Constants.CURRENT_SYLLABLE, null, this);
                break;
            }
            case Constants.CURRENT_SYLLABLE:{
        
                processData(data);

                break;
            }
        }// end switch
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        int mNumberOfSyllableVariables=(mCursor.getCount()*2>Constants.NUMBER_SYLLABLE_VARIABLES) ?
                Constants.NUMBER_SYLLABLE_VARIABLES : mCursor.getCount()*2;
        int mNumberCorrectInARow=Constants.INA_ROW_CORRECT;
        mAllSyllables=new ArrayList<>();
        int mSyllableNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentGSP.put("current_level",
                    mCursor.getString(mCursor.getColumnIndex("app_user_current_level")));
            mAllSyllables.add(new ArrayList<String>());
            mAllSyllables.get(mSyllableNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("syllables_id"))); //0
            mAllSyllables.get(mSyllableNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("syllables_text"))); //1
            mAllSyllables.get(mSyllableNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("syllables_audio"))); //2
            mAllSyllables.get(mSyllableNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mAllSyllables.get(mSyllableNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mAllSyllables.get(mSyllableNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            mAllSyllables.get(mSyllableNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("syllable_sister_id"))); //6

            mNumberCorrectInARow=(mNumberCorrectInARow
                    >Integer.parseInt(mAllSyllables.get(mSyllableNumber).get(5)))
                    ? Integer.parseInt(mAllSyllables.get(mSyllableNumber).get(5))
                    :mNumberCorrectInARow;


            mSyllableNumber++;
            if (mSyllableNumber >=mNumberOfSyllableVariables) {
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else{
                mCursor.moveToNext();
            }
        }


        Collections.shuffle(mAllSyllables);

        ArrayList<ArrayList<String>> mTempCurrentWords=new ArrayList<>();

        String mPreviousWordId="0";
        for(int i=0; i<mNumberOfSyllableVariables; i++){
            if(!mAllSyllables.get(i).get(0).equals(mPreviousWordId)){
                mTempCurrentWords.add(new ArrayList<>(mAllSyllables.get(i)));
            }else{
                if((i+1)<mNumberOfSyllableVariables){
                    mTempCurrentWords.add(new ArrayList<>(mAllSyllables.get(i+1)));
                    mTempCurrentWords.add(new ArrayList<>(mAllSyllables.get(i)));
                    i++;
                }else{
                    mTempCurrentWords.add(new ArrayList<>(mTempCurrentWords.get(0)));
                    mTempCurrentWords.set(0,mAllSyllables.get(i));
                }
            }
            mPreviousWordId=mAllSyllables.get(i).get(0);
        }

        mAllSyllables=new ArrayList<>(mTempCurrentWords);



        displayScreen();

    }
    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){

        findViewById(R.id.btnTextRS03).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentSyllableAudio.equals("") && !mBeingValidated){
                    playClickSound();
                    playGeneralAudio(mCurrentSyllableAudio);
                }

            }
        });

        findViewById(R.id.btnImageRS03).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentWordAudio.equals("") && !mBeingValidated){
                    playClickSound();
                    playGeneralAudio(mCurrentWordAudio);
                }

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

}
