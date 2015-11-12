package com.motoli.apps.allsubjects;

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

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/31/2015.
 */
public class ActivityWD02 extends Activity_General_Parent 
        implements LoaderManager.LoaderCallbacks<Cursor>{



    private int mCurrentTextLocation=0;
    private int incorrectInRound=0;

    private String mCorrectId;

    private String mCurrentImage="";
    private String mCurrentCorrectAudio="";
    private String mClickedAudio="";

    private ArrayList<ArrayList<String>> mCurrentText;

    private ArrayList<ArrayList<String>> mAllText;

    private ArrayList<ArrayList<ArrayList<String>>> allActivityText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_wd02);
        appData.addToClassOrder(5);
        allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

        beingValidated=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();

        ((TextView) findViewById(R.id.word1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word4)).setTypeface(appData.getCurrentFontType());

        getLoaderManager().initLoader(MotoliConstants.GUESS_WORDS, null, this);
        ///Uri todoUri = MotoliContentProvider.CONTENT_URI_WRD_LTR_ACTIVITIES;

    }//end public void onCreate(Bundle savedInstanceState) {

    public void onResume(){
        super.onResume();
        beingValidated=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();

        getLoaderManager().restartLoader(MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private long playInstructionAudio(){
        return playGeneralAudio("info_wd02.mp3");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////



    private void clearActivity(){
        findViewById(R.id.loadingCircle1).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.loadingCircle2).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.loadingCircle3).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.loadingCircle4).setVisibility(ProgressBar.VISIBLE);


        ((TextView) findViewById(R.id.word1)).setText("");
        ((TextView) findViewById(R.id.word2)).setText("");
        ((TextView) findViewById(R.id.word3)).setText("");
        ((TextView) findViewById(R.id.word4)).setText("");

        ((TextView) findViewById(R.id.word1)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.word2)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.word3)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.word4)).setTextColor(getResources().getColor(R.color.normalBlack));


        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);

        clearFrames();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){
        incorrectInRound=0;
        mCurrentImage="";
        mCurrentText = new ArrayList<ArrayList<String>>(allActivityText.get(roundNumber));
        Collections.shuffle(mCurrentText);

        ((TextView) findViewById(R.id.word1)).setText(mCurrentText.get(0).get(2));
        findViewById(R.id.loadingCircle1).setVisibility(ProgressBar.INVISIBLE);
        
        if(mCurrentText.get(0).get(0).equals("1")){
            correctLocation=0;
            mCorrectId=mCurrentText.get(0).get(1);
            mCurrentImage=mCurrentText.get(0).get(7);
            mCurrentCorrectAudio=mCurrentText.get(0).get(3);
        }


        ((TextView) findViewById(R.id.word2)).setText(mCurrentText.get(1).get(2));
        findViewById(R.id.loadingCircle2).setVisibility(ProgressBar.INVISIBLE);
        if(mCurrentText.get(1).get(0).equals("1")){
            correctLocation=1;
            mCorrectId=mCurrentText.get(1).get(1);
            mCurrentImage=mCurrentText.get(1).get(5);
            mCurrentCorrectAudio=mCurrentText.get(1).get(3);
        }

        ((TextView) findViewById(R.id.word3)).setText(mCurrentText.get(2).get(2));
        findViewById(R.id.loadingCircle3).setVisibility(ProgressBar.INVISIBLE);
        if(mCurrentText.get(2).get(0).equals("1")){
            correctLocation=2;
            mCorrectId=mCurrentText.get(2).get(1);
            mCurrentImage=mCurrentText.get(2).get(5);
            mCurrentCorrectAudio=mCurrentText.get(2).get(3);
        }

        ((TextView) findViewById(R.id.word4)).setText(mCurrentText.get(3).get(2));
        findViewById(R.id.loadingCircle4).setVisibility(ProgressBar.INVISIBLE);
        if(mCurrentText.get(3).get(0).equals("1")){
            correctLocation=3;
            mCorrectId=mCurrentText.get(3).get(1);
            mCurrentImage=mCurrentText.get(3).get(5);
            mCurrentCorrectAudio=mCurrentText.get(3).get(3);
        }

        //This is Image
//        ((TextView) findViewById(R.id.word0)).setText(correctText);
        //findViewById(R.id.loadingCircle0).setVisibility(ProgressBar.INVISIBLE);


        long audioDuration=0;
        if(roundNumber==0)
            audioDuration=playInstructionAudio();

        if(!mCurrentImage.equals("")){
            try{
                String imageName=mCurrentImage.replace(".jpg", "").replace(".png", "").trim();
                int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
                ((ImageView) findViewById(R.id.wordImage0)).setImageResource(resID);
            }catch (Exception e) {
                ((ImageView) findViewById(R.id.wordImage0)).setImageResource(R.drawable.blank_image);
                Log.e("MyTag", "Failure to get drawable id.", e);
            }

            audioHandler.postDelayed(playLtrAudio, audioDuration+20);
        }else{
            correctChoice=true;
            mProcessGuessPosition=2;
            guessHandler.postDelayed(processGuess, audioDuration + 20);
        }
    }//end private void displayScreen(Cursor currentWordsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playLtrAudio = new Runnable(){

        @Override
        public void run(){
            audioHandler.removeCallbacks(playLtrAudio);
            beingValidated=false;
            if(!mCurrentCorrectAudio.equals(""))
                playGeneralAudio(mCurrentCorrectAudio);
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //end private void displayScreen(Cursor currentWordsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens(){
        findViewById(R.id.wordFrame1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(0).get(6).equals("0") && !beingValidated){
                    mCurrentTextLocation=0;
                    mClickedAudio=mCurrentText.get(0).get(3);
                    currentWordID=mCurrentText.get(0).get(1);
                    correctChoice=(mCurrentText.get(0).get(0).equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.wordFrame2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(1).get(6).equals("0") && !beingValidated){
                    mCurrentTextLocation=1;
                    mClickedAudio=mCurrentText.get(1).get(3);
                    currentWordID=mCurrentText.get(1).get(1);
                    correctChoice=(mCurrentText.get(1).get(0).equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.wordFrame3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(2).get(6).equals("0") && !beingValidated){
                    mCurrentTextLocation=2;
                    mClickedAudio=mCurrentText.get(2).get(3);
                    currentWordID=mCurrentText.get(2).get(1);
                    correctChoice=(mCurrentText.get(2).get(0).equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.wordFrame4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(3).get(6).equals("0") && !beingValidated){
                    mCurrentTextLocation=3;
                    mClickedAudio=mCurrentText.get(3).get(3);
                    currentWordID=mCurrentText.get(3).get(1);
                    correctChoice=(mCurrentText.get(3).get(0).equals("1"));
                    setUpFrame(3);
                }
            }
        });


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on);
        validateAvailable=true;
        //	validateFlashPosition=0;
        //	validateFlash.removeCallbacks(processValidateFlash);
        //	validateFlash.postDelayed(processValidateFlash, (long)50);
        switch(frameNumber){
            default:
            case 0:{
                ((ImageView)findViewById(R.id.wordFrame1)).setImageResource(R.drawable.frm_wd02_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.wordFrame2)).setImageResource(R.drawable.frm_wd02_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.wordFrame3)).setImageResource(R.drawable.frm_wd02_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.wordFrame4)).setImageResource(R.drawable.frm_wd02_on);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.wordFrame1)).setImageResource(R.drawable.frm_wd02_off);
        ((ImageView)findViewById(R.id.wordFrame2)).setImageResource(R.drawable.frm_wd02_off);
        ((ImageView)findViewById(R.id.wordFrame3)).setImageResource(R.drawable.frm_wd02_off);
        ((ImageView)findViewById(R.id.wordFrame4)).setImageResource(R.drawable.frm_wd02_off);


    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void validate(){
        beingValidated=true;
        if(correctChoice) {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
        }else{
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
        }
        processFontColorAndPoints();
        processGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processFontColorAndPoints(){
        String[] selectionArgs;
        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

        if(correctChoice){
            String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                    +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+currentWordID
                    +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    currentGSP.get(3),
                    currentWordID,
                    String.valueOf(incorrectInRound)};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            switch(mCurrentTextLocation){
                default:
                case 0:
                    mCurrentText.get(0).add(6, "1");
                    ((TextView) findViewById(R.id.word1)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
                case 1:
                    mCurrentText.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.word2)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
                case 2:
                    mCurrentText.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.word3)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
                case 3:
                    mCurrentText.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.word4)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
            }


        }else{
            String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                    +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+mCorrectId
                    +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
            incorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    currentGSP.get(3),
                    mCorrectId,
                    String.valueOf(incorrectInRound)};

            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(incorrectInRound>=2){
                switch(correctLocation){
                    default:
                    case 0:
                        mCurrentText.get(1).add(6, "1");
                        mCurrentText.get(2).add(6, "1");
                        mCurrentText.get(3).add(6, "1");
                        break;
                    case 1:
                        mCurrentText.get(0).add(6, "1");
                        mCurrentText.get(2).add(6, "1");
                        mCurrentText.get(3).add(6, "1");
                        break;
                    case 2:
                        mCurrentText.get(1).add(6, "1");
                        mCurrentText.get(0).add(6, "1");
                        mCurrentText.get(3).add(6, "1");
                        break;
                    case 3:
                        mCurrentText.get(1).add(6, "1");
                        mCurrentText.get(2).add(6, "1");
                        mCurrentText.get(0).add(6, "1");
                        break;
                }
            }
            switch(mCurrentTextLocation){
                default:
                case 0:
                    mCurrentText.get(0).add(6, "1");
                    ((TextView) findViewById(R.id.word1)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
                case 1:
                    mCurrentText.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.word2)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
                case 2:
                    mCurrentText.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.word3)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
                case 3:
                    mCurrentText.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.word4)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
            }


        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processChoiceView(){


        if(correctChoice){

            switch(mCurrentTextLocation){
                default:
                case 0:
                    ((TextView) findViewById(R.id.word2)).setText("");
                    ((TextView) findViewById(R.id.word3)).setText("");
                    ((TextView) findViewById(R.id.word4)).setText("");
                    break;
                case 1:
                    ((TextView) findViewById(R.id.word1)).setText("");
                    ((TextView) findViewById(R.id.word3)).setText("");
                    ((TextView) findViewById(R.id.word4)).setText("");
                    break;
                case 2:
                    ((TextView) findViewById(R.id.word2)).setText("");
                    ((TextView) findViewById(R.id.word1)).setText("");
                    ((TextView) findViewById(R.id.word4)).setText("");
                    break;
                case 3:
                    ((TextView) findViewById(R.id.word2)).setText("");
                    ((TextView) findViewById(R.id.word3)).setText("");
                    ((TextView) findViewById(R.id.word1)).setText("");
                    break;
            }


        }else{
            if(incorrectInRound>=2){
                switch(correctLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.word2)).setText("");
                        ((TextView) findViewById(R.id.word3)).setText("");
                        ((TextView) findViewById(R.id.word4)).setText("");
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.word1)).setText("");
                        ((TextView) findViewById(R.id.word3)).setText("");
                        ((TextView) findViewById(R.id.word4)).setText("");
                        break;
                    case 2:
                        ((TextView) findViewById(R.id.word1)).setText("");
                        ((TextView) findViewById(R.id.word2)).setText("");
                        ((TextView) findViewById(R.id.word4)).setText("");
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.word1)).setText("");
                        ((TextView) findViewById(R.id.word2)).setText("");
                        ((TextView) findViewById(R.id.word3)).setText("");
                        break;
                }
            }else{
                switch(mCurrentTextLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.word1)).setText("");
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.word2)).setText("");
                        break;
                    case 2:
                        ((TextView) findViewById(R.id.word3)).setText("");
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.word4)).setText("");
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////

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
                    processChoiceView();
                    audioDuration=playGeneralAudio(mClickedAudio);
                    processGuessPosition++;
                    audioHandler.postDelayed(processGuess, audioDuration+10);
                    break;
                }
                case 2:{
                    guessHandler.removeCallbacks(processGuess);
                    clearFrames();
                    if(correctChoice){
                        roundNumber++;
                        if(roundNumber!=(allActivityText.size())){
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
                    displayScreen();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(processGuessPosition){
        }//public void run(){
    };



    ///////////////////////////////////////////////////////////////////////////////////////////////////



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
                        " AND variable_phase_levels.level_number!=0 " +
                        "AND words.image_url!=''";

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
                data.moveToFirst();
                while (!data.isAfterLast()) {
                    mAllText.add(new ArrayList<String>());
                    mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("word_id"))); //0
                    mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("word_text"))); //1
                    mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("audio_url"))); //2
                    mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("number_correct"))); //3
                    mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("number_incorrect"))); //4
                    mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("image_url"))); //5
                    mAllText.get(mGuessWordNumber).add(data.getString(data.getColumnIndex("number_correct_in_a_row"))); //6
                    mGuessWordNumber++;
                    data.moveToNext();
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void processData(Cursor mCursor){
        mCurrentWords=new ArrayList<ArrayList<String>>();
        int currentWordNumber=0;
        int mNumberCorrectInARow=MotoliConstants.INA_ROW_CORRECT;
        int mNumberOfWords=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentWords.add(new ArrayList<String>());
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6

            mNumberOfWords++;
            mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(mCurrentWords.get(currentWordNumber).get(6)))
                    ? Integer.parseInt(mCurrentWords.get(currentWordNumber).get(6)):mNumberCorrectInARow;

            if(mCurrentWords.get(currentWordNumber).get(6).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT-3))){
                /* This is because is first attempt the letter should appear 3 times to move to next round faster */
                currentWordNumber++;
                mCurrentWords.add(new ArrayList<String>());
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6

            }else if(mCurrentWords.get(currentWordNumber).get(6).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT-2))){
                currentWordNumber++;
                mCurrentWords.add(new ArrayList<String>());
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
                mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
            }
            currentWordNumber++;
            if (currentWordNumber >= MotoliConstants.NUMBER_VARIABLES ||
                    currentWordNumber>=mCursor.getCount()*2) {
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else{
                mCursor.moveToNext();
            }
        }

        if((mNumberCorrectInARow==MotoliConstants.INA_ROW_CORRECT)){
            showCompleteLevelStar(true);
        }else{
            showCompleteLevelStar(false);
        }

        Collections.shuffle(mCurrentWords);

        ArrayList<ArrayList<String>> mTempCurrentWords=new ArrayList<ArrayList<String>>();

        String mPreviousWordId="0";
        for(int i=0; i<MotoliConstants.NUMBER_VARIABLES; i++){
            if(!mCurrentWords.get(i).get(0).equals(mPreviousWordId)){
                mTempCurrentWords.add(new ArrayList<String>(mCurrentWords.get(i)));
            }else{
                if((i+1)<MotoliConstants.NUMBER_VARIABLES){
                    mTempCurrentWords.add(new ArrayList<String>(mCurrentWords.get(i+1)));
                    mTempCurrentWords.add(new ArrayList<String>(mCurrentWords.get(i)));
                    i++;
                }else{
                    mTempCurrentWords.add(new ArrayList<String>(mTempCurrentWords.get(0)));
                    mTempCurrentWords.set(0,mCurrentWords.get(i));
                }
            }
            mPreviousWordId=mCurrentWords.get(i).get(0);
        }

        mCurrentWords=new ArrayList<ArrayList<String>>(mTempCurrentWords);


        shuffleGameData();
        displayScreen();

    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void shuffleGameData(){

        ArrayList<ArrayList<String>> sortedWords=new ArrayList<ArrayList<String>>(mAllText);
        String word_id;
        allActivityText.clear();

        for(int currentCount=0; currentCount<mCurrentWords.size();currentCount++){
            allActivityText.add(new ArrayList<ArrayList<String>>());
            allActivityText.get(currentCount).add(new ArrayList<String>());
            allActivityText.get(currentCount).get(0).add("1"); //0

            word_id=mCurrentWords.get(currentCount).get(0);

            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(0)); //word_id 1
            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(1)); //word_text 2
            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(2)); //audio_url 3
            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(3)); //number correct 4
            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(4)); //number incorrect 5
            allActivityText.get(currentCount).get(0).add("0"); //guess yet 6
            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(5)); //image_url 7

            Collections.shuffle(sortedWords);

            int distCurrentCount=1;
            for(int distCount=0;distCount<4; distCount++){

                if(!sortedWords.get(distCount).get(0).equals(word_id) ){

                    for(ArrayList mTemp : allActivityText.get(currentCount) ){
                        if(!sortedWords.get(distCount).get(0).equals(mTemp.get(1))){
                            allActivityText.get(currentCount).add(new ArrayList<String>());
                            allActivityText.get(currentCount).get(distCurrentCount).add("0");

                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(0)); //word_id
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(1)); //word_text
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(2)); //audio_url
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(3)); //number correct
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(4)); //number incorrect
                            allActivityText.get(currentCount).get(distCurrentCount).add("0"); //guessed yet
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(5));
                            distCurrentCount++;
                            break;
                        }//  end if(!sortedWords.get(distCount).get(0).equals(mTemp.get(1))){
                    }//end for(ArrayList mTemp : allActivityText.get(currentCount) ){
                    if(distCurrentCount>=4){
                        break;
                    }
                }//end if(!sortedWords.get(distCount).get(0).equals(word_id)){
            }//end for(int distCount=0;distCount<sortedWords.size(); distCount++){


        }//end for(int currentCount=0; currentCount<mCurrentWords.size();currentCount++){

        for(int i=0; i<allActivityText.size(); i++){
            Collections.shuffle(allActivityText.get(i));
        }
    }//end private void shuffleGameData(){

    //////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpListeners(){
        findViewById(R.id.wordImage0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentCorrectAudio.equals(""))
                    playGeneralAudio(mCurrentCorrectAudio);
            }
        });


        findViewById(R.id.btnInfo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                long audioDuration=playInstructionAudio();
                audioHandler.postDelayed(playLtrAudio, audioDuration+20);
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
