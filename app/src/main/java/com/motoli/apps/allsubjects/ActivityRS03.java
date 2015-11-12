package com.motoli.apps.allsubjects;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 9/24/2015.
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


public class ActivityRS03 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{

    private int mCurrentSyllablesWordsLocation=0;
    private int mIncorrectInRound=0;

    private String mCurrentSyllableAudio;
    private String mCurrentWordAudio;

    private ArrayList<ArrayList<String>> mCurrentSyllableWords;
    private  ArrayList<String> mCurrentSyllable;
    private ArrayList<ArrayList<String>> mAllSyllables;

    private ArrayList<Bundle> mAllSyllableWordsBundle;
    private ArrayList<ArrayList<ArrayList<String>>> mAllActivityText;

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
        mAllActivityText = new ArrayList<ArrayList<ArrayList<String>>>();
        roundNumber = 0;
        beingValidated=true;

        setUpListeners();
        visibleProgress();
        clearActivity();
        setupFrameListens();
        ((TextView) findViewById(R.id.btnTextRS03)).setTypeface(appData.getCurrentFontType());

        getLoaderManager().initLoader(MotoliConstants.CURRENT_SYLLABLE_WRDS, null, this);

    }//end public void onCreate(Bundle savedInstanceState) {


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private long playInstructionAudio(){
        return playGeneralAudio("info_rs03");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////

    private void visibleProgress(){
        findViewById(R.id.loadingCircle1).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.loadingCircle2).setVisibility(ProgressBar.VISIBLE);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){
        ((ImageView) findViewById(R.id.btnImageRS03)).setImageResource(R.drawable.blank_image);
        ((TextView) findViewById(R.id.btnTextRS03)).setText("");

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){
        mIncorrectInRound=0;

        mCurrentSyllable = new ArrayList<String>(mAllSyllables.get(roundNumber));


        mCurrentSyllableWords=new ArrayList<ArrayList<String>>();
        mCurrentSyllableWords.clear();

        Collections.shuffle(mAllSyllableWordsBundle);

        ((ImageView) findViewById(R.id.btnCircleRS03)).setImageResource(R.drawable.btn_rs03_circle);
        ((ImageView) findViewById(R.id.btnStarRS03)).setImageResource(R.drawable.btn_rs03_star);

        boolean foundMatch=false;
        for(int i=0;i<mAllSyllableWordsBundle.size(); i++){
            if (mAllSyllableWordsBundle.get(i).getString(mSyllableWordLast).equals(mCurrentSyllable.get(0))
                    || mAllSyllableWordsBundle.get(i).getString(mSyllableWordFirst).equals(mCurrentSyllable.get(0))) {
                mCurrentSyllableWords.add(new ArrayList<String>());
                mCurrentSyllableWords.get(0).add(mAllSyllableWordsBundle.get(i).getString(mSyllableWordId)); //0
                mCurrentSyllableWords.get(0).add(mAllSyllableWordsBundle.get(i).getString(mSyllableWordText)); //1
                mCurrentSyllableWords.get(0).add(mAllSyllableWordsBundle.get(i).getString(mSyllableWordAudio)); //2
                mCurrentSyllableWords.get(0).add(mAllSyllableWordsBundle.get(i).getString(mSyllableWordImage)); //3
                mCurrentSyllableWords.get(0).add(mAllSyllableWordsBundle.get(i).getString(mSyllableWordFirst)); //4
                mCurrentSyllableWords.get(0).add(mAllSyllableWordsBundle.get(i).getString(mSyllableWordLast)); //5
                if (mAllSyllableWordsBundle.get(i).getString(mSyllableWordFirst).equals(mCurrentSyllable.get(0))) {
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

        long audioDuration = 0;
        if (roundNumber == 0)
            audioDuration = playInstructionAudio();

        if(foundMatch) {

            mCurrentSyllableAudio = mCurrentSyllable.get(2);
            mCurrentWordAudio=mCurrentSyllableWords.get(0).get(2);


            findViewById(R.id.loadingCircle2).setVisibility(ProgressBar.INVISIBLE);
            findViewById(R.id.loadingCircle1).setVisibility(ProgressBar.INVISIBLE);

            ((TextView) findViewById(R.id.btnTextRS03)).setText(mCurrentSyllable.get(1));


            try{
                String imageName=mCurrentSyllableWords.get(0).get(3).replace(".jpg", "").replace(".png", "").trim();
                //field = res.getField(imageName);
                //drawableId = field.getInt(null);

                int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());

                ((ImageView) findViewById(R.id.btnImageRS03)).setImageResource(resID);


            }catch (Exception e) {
                ((ImageView) findViewById(R.id.btnImageRS03)).setImageResource(R.drawable.blank_image);
                Log.e("MyTag", "Failure to get drawable id.", e);
            }


            audioHandler.postDelayed(playLtrAudio, audioDuration + 20);
        }else{
            mCurrentSyllableAudio="";
            mCurrentWordAudio="";
            correctChoice=true;
            processGuessPosition=2;
            guessHandler.postDelayed(processGuess, 10);
        }

    }//end private void displayScreen(Cursor currentWordsCursor){

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playLtrAudio = new Runnable(){

        @Override
        public void run(){
            audioHandler.removeCallbacks(playLtrAudio);
            beingValidated=false;
            if(!mCurrentSyllableAudio.equals(""))
                playGeneralAudio(mCurrentSyllableAudio);
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //end private void displayScreen(Cursor mAllSyllableWordsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens(){
        findViewById(R.id.btnCircleRS03).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (/*!mCurrentSyllableWords.get(0).get(7).equals("2") && */!beingValidated) {
                    mCurrentSyllablesWordsLocation = 0;
                    mCurrentSyllableWords.get(0).set(7,"1");
                    ((ImageView) findViewById(R.id.btnCircleRS03)).setImageResource(R.drawable.btn_rs03_circle_select);
                    if (!mCurrentSyllableWords.get(0).get(8).equals("2")) {
                        ((ImageView) findViewById(R.id.btnStarRS03)).setImageResource(R.drawable.btn_rs03_star);
                    }
                    correctChoice = mCurrentSyllableWords.get(0).get(6).equals("0");
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.btnStarRS03).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (/*!mCurrentSyllableWords.get(0).get(8).equals("2") && */ !beingValidated) {
                    mCurrentSyllableWords.get(0).set(8,"1");
                    mCurrentSyllablesWordsLocation = 1;
                    if (!mCurrentSyllableWords.get(0).get(7).equals("2")) {
                        ((ImageView) findViewById(R.id.btnCircleRS03)).setImageResource(R.drawable.btn_rs03_circle);
                    }
                    ((ImageView) findViewById(R.id.btnStarRS03)).setImageResource(R.drawable.btn_rs03_star_select);

                    correctChoice = mCurrentSyllableWords.get(0).get(6).equals("1");
                    setUpFrame();
                }
            }
        });




    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(){
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on);
        validateAvailable=true;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void validate(){
        beingValidated=true;
        String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+mCurrentSyllable.get(0)
                +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

        if(correctChoice){
            //processLayoutAfterGuess(true);
            //correctWordCount++;


            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
            if(mIncorrectInRound>=1){
                selectionArgs = new String[]{
                        "0",
                        "0",
                        currentGSP.get(3),
                        mCurrentSyllable.get(0),
                        String.valueOf(mIncorrectInRound)};
                mAllSyllables.add(mCurrentSyllable);
            }else {
                selectionArgs = new String[]{
                        "1",
                        "0",
                        currentGSP.get(3),
                        mCurrentSyllable.get(0),
                        String.valueOf(mIncorrectInRound)};
            }

            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            switch(mCurrentSyllablesWordsLocation){
                default:
                case 0:
                    ((ImageView) findViewById(R.id.btnCircleRS03)).setImageResource(R.drawable.btn_rs03_circle_right);
                    break;
                case 1:
                    ((ImageView) findViewById(R.id.btnStarRS03)).setImageResource(R.drawable.btn_rs03_star_right);
                    break;
            }


        }else{
            mIncorrectInRound++;

            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    currentGSP.get(3),
                    mCurrentSyllable.get(0),
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        mCurrentSyllableWords.get(0).set(8,"2");
                        ((ImageView) findViewById(R.id.btnCircleRS03)).setImageResource(R.drawable.btn_rs03_circle_select);
                        ((ImageView) findViewById(R.id.btnStarRS03)).setImageResource(R.drawable.btn_rs03_star_wrong);

                        break;
                    case 1:
                        mCurrentSyllableWords.get(0).set(7,"2");
                        ((ImageView) findViewById(R.id.btnStarRS03)).setImageResource(R.drawable.btn_rs03_star_select);
                        ((ImageView) findViewById(R.id.btnCircleRS03)).setImageResource(R.drawable.btn_rs03_circle_wrong);
                        break;
                }
            }else{
                switch(mCurrentSyllablesWordsLocation){
                    default:
                    case 0:
                        mCurrentSyllableWords.get(0).set(7,"2");
                        ((ImageView) findViewById(R.id.btnCircleRS03)).setImageResource(R.drawable.btn_rs03_circle_wrong);
                        break;
                    case 1:
                        mCurrentSyllableWords.get(0).set(8,"2");
                        ((ImageView) findViewById(R.id.btnStarRS03)).setImageResource(R.drawable.btn_rs03_star_wrong);
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }

        //processPoints();


        //processGuessPosition=0;

        //guessHandler.postDelayed(processGuess, 10);

        processGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);




    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable processGuess = new Runnable(){

        //beingValidated=false;


        @Override
        public void run(){
            long audioDuration;

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
                    audioDuration=playGeneralAudio(currentLtrWrdAudio);
                    processGuessPosition++;
                    audioHandler.postDelayed(processGuess, audioDuration+10);
                    break;
                }
                case 2:{
                    guessHandler.removeCallbacks(processGuess);
                    if(correctChoice){
                        roundNumber++;
                        if(roundNumber!=(mAllSyllables.size())){
                            //beingValidated=false;
                            validateAvailable=false;
                            clearActivity();
                            processGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            lastActivityData=0;
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);
                        beingValidated=false;
                        validateAvailable=false;
                    }

                    break;
                }
                case 3:{
                    ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);

                    guessHandler.removeCallbacks(processGuess);
                    displayScreen();
                    break;
                }
            }//switch(processGuessPosition){
        }//public void run(){
    };



    ///////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.CURRENT_SYLLABLE_WRDS:{
                String selection=Database.Syllable_Words.SYLLABLE_WORDS_TABLE+"."+
                        Database.Syllable_Words.SYLLABLE_WORD_TEXT+"!=''";

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_SYLLABLE_WORDS,null,selection,null,null);
                break;
            }
            case MotoliConstants.CURRENT_SYLLABLE:{

                ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        currentGSP.get(0),
                        currentGSP.get(1),
                        currentGSP.get(2),
                        currentGSP.get(3)};
                String selection=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+
                        Database.Variable_Phase_Levels.GROUP_ID+"="+currentGSP.get(0)
                        +" AND "+Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+
                        Database.Variable_Phase_Levels.PHASE_ID+"="+currentGSP.get(1)
                        +" AND "+Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+
                        Database.Variable_Phase_Levels.LEVEL_NUMBER+"<="+currentGSP.get(3);

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_SYLLABLES, projection, selection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<ArrayList<String>> mAllSyllableWords;

        switch(loader.getId()) {
            default:
                break;
            case MotoliConstants.CURRENT_SYLLABLE_WRDS:{
                mAllSyllableWordsBundle=new ArrayList<Bundle>();
                mAllSyllableWords=new ArrayList<ArrayList<String>>();
                int currentSyllableWordNumber=0;

                for (data.moveToFirst(); !data.isAfterLast();data.moveToNext()){
                    mAllSyllableWords.add(new ArrayList<String>());
                    mAllSyllableWordsBundle.add(new Bundle());

                    mAllSyllableWordsBundle.get(currentSyllableWordNumber).putString(mSyllableWordId, 
                            data.getString(data.getColumnIndex(mSyllableWordId)));
                    mAllSyllableWordsBundle.get(currentSyllableWordNumber).putString(mSyllableWordText,
                            data.getString(data.getColumnIndex(mSyllableWordText)));
                    mAllSyllableWordsBundle.get(currentSyllableWordNumber).putString(mSyllableWordAudio,
                            data.getString(data.getColumnIndex(mSyllableWordAudio)));
                    mAllSyllableWordsBundle.get(currentSyllableWordNumber).putString(mSyllableWordImage,
                            data.getString(data.getColumnIndex(mSyllableWordImage)));
                    mAllSyllableWordsBundle.get(currentSyllableWordNumber).putString(mSyllableWordFirst,
                            data.getString(data.getColumnIndex(mSyllableWordFirst)));
                    mAllSyllableWordsBundle.get(currentSyllableWordNumber).putString(mSyllableWordLast,
                            data.getString(data.getColumnIndex(mSyllableWordLast)));




                    mAllSyllableWords.get(currentSyllableWordNumber).add(data.getString(data.getColumnIndex("syllable_word_id")));
                    mAllSyllableWords.get(currentSyllableWordNumber).add(data.getString(data.getColumnIndex("syllable_word_text")));
                    mAllSyllableWords.get(currentSyllableWordNumber).add(data.getString(data.getColumnIndex("syllable_word_audio")));
                    mAllSyllableWords.get(currentSyllableWordNumber).add(data.getString(data.getColumnIndex("syllable_word_image")));
                    mAllSyllableWords.get(currentSyllableWordNumber).add(data.getString(data.getColumnIndex("syllable_word_first_id")));
                    mAllSyllableWords.get(currentSyllableWordNumber).add(data.getString(data.getColumnIndex("syllable_word_last_id")));
                    currentSyllableWordNumber++;
                }
                getLoaderManager().restartLoader(MotoliConstants.CURRENT_SYLLABLE, null, this);
                break;
            }
            case MotoliConstants.CURRENT_SYLLABLE:{
        
                processData(data);

                break;
            }
        }// end switch
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void processData(Cursor mCursor){
        int mNumberOfSyllableVariables=(mCursor.getCount()*2>MotoliConstants.NUMBER_SYLLABLE_VARIABLES) ?
                MotoliConstants.NUMBER_SYLLABLE_VARIABLES : mCursor.getCount()*2;
        int mNumberCorrectInARow=MotoliConstants.INA_ROW_CORRECT;
        mAllSyllables=new ArrayList<ArrayList<String>>();
        int mSyllableNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mAllSyllables.add(new ArrayList<String>());
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_id"))); //0
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_text"))); //1
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_audio"))); //2
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5

            mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(mAllSyllables.get(mSyllableNumber).get(5)))
                    ? Integer.parseInt(mAllSyllables.get(mSyllableNumber).get(5)):mNumberCorrectInARow;


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

        ArrayList<ArrayList<String>> mTempCurrentWords=new ArrayList<ArrayList<String>>();

        String mPreviousWordId="0";
        for(int i=0; i<mNumberOfSyllableVariables; i++){
            if(!mAllSyllables.get(i).get(0).equals(mPreviousWordId)){
                mTempCurrentWords.add(new ArrayList<String>(mAllSyllables.get(i)));
            }else{
                if((i+1)<mNumberOfSyllableVariables){
                    mTempCurrentWords.add(new ArrayList<String>(mAllSyllables.get(i+1)));
                    mTempCurrentWords.add(new ArrayList<String>(mAllSyllables.get(i)));
                    i++;
                }else{
                    mTempCurrentWords.add(new ArrayList<String>(mTempCurrentWords.get(0)));
                    mTempCurrentWords.set(0,mAllSyllables.get(i));
                }
            }
            mPreviousWordId=mAllSyllables.get(i).get(0);
        }

        mAllSyllables=new ArrayList<ArrayList<String>>(mTempCurrentWords);


        if((mNumberCorrectInARow==MotoliConstants.INA_ROW_CORRECT)){
           showCompleteLevelStar(true);
        }else{
            showCompleteLevelStar(false);
        }

        displayScreen();

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////



    private void setUpListeners(){

        findViewById(R.id.btnTextRS03).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentSyllableAudio.equals("") && !beingValidated){
                    playClickSound();
                    playGeneralAudio(mCurrentSyllableAudio);
                }

            }
        });

        findViewById(R.id.btnImageRS03).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentWordAudio.equals("") && !beingValidated){
                    playClickSound();
                    playGeneralAudio(mCurrentWordAudio);
                }

            }
        });


        findViewById(R.id.btnInfo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                long audioDuration=playInstructionAudio();
                audioHandler.postDelayed(playLtrAudio, audioDuration + 20);
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
            }
        });


        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(validateAvailable && !beingValidated){
                    playClickSound();
                    validate();
                }
            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

}
