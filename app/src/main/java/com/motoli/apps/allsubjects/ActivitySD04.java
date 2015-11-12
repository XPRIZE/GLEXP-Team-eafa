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


public class ActivitySD04 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{

    private int mCurrentPhonicsWordsLocation=0;
    private int mIncorrectInRound=0;

    private String mCurrentWordAudio;
    private String mLeftPhonicAudio;
    private String mRightPhonicAudio;


    private ArrayList<ArrayList<String>> mCurrentPhonicWords;
    //private  ArrayList<String> mAllPhonics.get(roundNumber);
    private ArrayList<ArrayList<String>> mAllPhonics;
    private ArrayList<ArrayList<String>> mOppositePhonics;

    private ArrayList<Bundle> mAllPhonicWordsBundle;
    private ArrayList<ArrayList<ArrayList<String>>> mAllActivityText;

    private static final String mPhncWrdId="phonic_word_id";
    private static final String mPhncWrdText="phonic_word_text";
    private static final String mPhncWrdAudio="phonic_word_audio";
    private static final String mPhncWrdImage="phonic_word_image";
    private static final String mPhncWrdFirst="phonic_word_first_id";
    private static final String mPhncWrdLast="phonic_word_last_id";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sd04);
        appData.addToClassOrder(8);
        mAllActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

        beingValidated=true;

        setUpListeners();
        visibleProgress();
        clearActivity();
        setupFrameListens();
        ((TextView) findViewById(R.id.phonicText0)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.phonicText1)).setTypeface(appData.getCurrentFontType());

        getLoaderManager().initLoader(MotoliConstants.CURRENT_PHNC_LTRS_WRDS, null, this);

    }//end public void onCreate(Bundle savedInstanceState) {


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private long playInstructionAudio(){
        return playGeneralAudio("info_sd04");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////

    private void visibleProgress(){
        findViewById(R.id.loadingCircle0).setVisibility(ProgressBar.VISIBLE);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){
        ((ImageView) findViewById(R.id.phonicImage0)).setImageResource(R.drawable.blank_image);

        ((TextView) findViewById(R.id.phonicText0)).setText("");
        ((TextView) findViewById(R.id.phonicText1)).setText("");
        ((TextView) findViewById(R.id.phonicText0)).setTextColor(getResources().getColor(R.color.veryDarkGray));
        ((TextView) findViewById(R.id.phonicText1)).setTextColor(getResources().getColor(R.color.veryDarkGray));


        ((ImageView) findViewById(R.id.btnMicFace0)).setImageResource(R.drawable.btn_dude_sd04_left_off);
        ((ImageView) findViewById(R.id.btnMicFace1)).setImageResource(R.drawable.btn_dude_sd04_right_off);


        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){
        mIncorrectInRound=0;

        //mAllPhonics.get(roundNumber) = new ArrayList<String>(mAllPhonics.get(roundNumber));


        mCurrentPhonicWords=new ArrayList<ArrayList<String>>();
        mCurrentPhonicWords.clear();

        Collections.shuffle(mAllPhonicWordsBundle);



        boolean foundMatch=false;
        for(int i=0;i<mAllPhonicWordsBundle.size(); i++){
            if ( mAllPhonicWordsBundle.get(i).getString(mPhncWrdFirst).equals(mAllPhonics.get(roundNumber).get(0))) {
                mCurrentPhonicWords.add(new ArrayList<String>());
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdId)); //0
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdText)); //1
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdAudio)); //2
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdImage)); //3
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdFirst)); //4
                mCurrentPhonicWords.get(0).add(mAllPhonicWordsBundle.get(i).getString(mPhncWrdLast)); //5
                mCurrentPhonicWords.get(0).add("0"); //6
                mCorrectLocation=0;
                mCurrentWordAudio=mCurrentPhonicWords.get(0).get(2);
                mCurrentPhonicWords.get(0).add("0"); //7
                foundMatch=true;
                break;
            }
        }



            long audioDuration = 0;
        if (roundNumber == 0)
            audioDuration = playInstructionAudio();

        if(foundMatch) {

            Collections.shuffle(mOppositePhonics);
            for(int i=0;i<mOppositePhonics.size(); i++) {
                if(!mOppositePhonics.get(i).get(0).equals(mAllPhonics.get(roundNumber).get(0))){
                    boolean random= Math.random() < 0.5;

                    if(random){
                        ((TextView) findViewById(R.id.phonicText0)).setText(mAllPhonics.get(roundNumber).get(1));

                        mCorrectLocation=0;
                        ((TextView) findViewById(R.id.phonicText1)).setText(mOppositePhonics.get(i).get(1));
                        mLeftPhonicAudio=mAllPhonics.get(roundNumber).get(2);
                        mRightPhonicAudio=mOppositePhonics.get(i).get(2);

                    }else{
                        ((TextView) findViewById(R.id.phonicText0)).setText(mOppositePhonics.get(i).get(1));
                        mCorrectLocation=1;
                        ((TextView) findViewById(R.id.phonicText1)).setText(mAllPhonics.get(roundNumber).get(1));

                        mLeftPhonicAudio=mOppositePhonics.get(i).get(2);
                        mRightPhonicAudio=mAllPhonics.get(roundNumber).get(2);
                    }

                    break;
                }
            }

         //   findViewById(R.id.loadingCircle2).setVisibility(ProgressBar.INVISIBLE);
            findViewById(R.id.loadingCircle0).setVisibility(ProgressBar.INVISIBLE);



            try{
                String imageName=mCurrentPhonicWords.get(0).get(3).replace(".jpg", "").replace(".png", "").trim();
                //field = res.getField(imageName);
                //drawableId = field.getInt(null);

                int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());

                ((ImageView) findViewById(R.id.phonicImage0)).setImageResource(resID);


            }catch (Exception e) {
                ((ImageView) findViewById(R.id.phonicImage0)).setImageResource(R.drawable.blank_image);
                Log.e("MyTag", "Failure to get drawable id.", e);
            }


            audioHandler.postDelayed(playLtrAudio, audioDuration + 20);
        }else{
            mLeftPhonicAudio="";
            mRightPhonicAudio="";
            correctChoice=true;
            mProcessGuessPosition=2;
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
         //   if(!mCurrentPhonicAudio.equals(""))
           //     playGeneralAudio(mCurrentPhonicAudio);
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //end private void displayScreen(Cursor mAllPhonicWordsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens(){
        findViewById(R.id.btnMicFace0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (/*!mCurrentPhonicWords.get(0).get(7).equals("1") || !mCurrentPhonicWords.get(0).get(7).equals("3")
                        && */!beingValidated) {
                    mCurrentPhonicsWordsLocation = 0;
                    if (mCurrentPhonicWords.get(0).get(7).equals("2")){
                        mCurrentPhonicWords.get(0).set(7, "3");
                    }else{
                        mCurrentPhonicWords.get(0).set(7, "1");
                    }
                    ((ImageView) findViewById(R.id.btnMicFace0)).setImageResource(R.drawable.btn_dude_sd04_left_on);
                    ((ImageView) findViewById(R.id.btnMicFace1)).setImageResource(R.drawable.btn_dude_sd04_right_off);

                    ((TextView) findViewById(R.id.phonicText0)).setTextColor(getResources().getColor(R.color.normalBlack));
                    ((TextView) findViewById(R.id.phonicText1)).setTextColor(getResources().getColor(R.color.veryDarkGray));
                    playGeneralAudio(mLeftPhonicAudio);
                    correctChoice = mCorrectLocation==0;
                    //mCurrentPhonicWords.get(0).get(6).equals("0");
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.btnMicFace1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (/*!mCurrentPhonicWords.get(0).get(7).equals("2") || !mCurrentPhonicWords.get(0).get(7).equals("3")
                        &&*/ !beingValidated) {
                    if (mCurrentPhonicWords.get(0).get(7).equals("1")){
                        mCurrentPhonicWords.get(0).set(7, "3");
                    }else{
                        mCurrentPhonicWords.get(0).set(7, "2");
                    }
                    mCurrentPhonicsWordsLocation = 1;
                    ((ImageView) findViewById(R.id.btnMicFace0)).setImageResource(R.drawable.btn_dude_sd04_left_off);
                    ((ImageView) findViewById(R.id.btnMicFace1)).setImageResource(R.drawable.btn_dude_sd04_right_on);
                    ((TextView) findViewById(R.id.phonicText0)).setTextColor(getResources().getColor(R.color.veryDarkGray));
                    ((TextView) findViewById(R.id.phonicText1)).setTextColor(getResources().getColor(R.color.normalBlack));
                    playGeneralAudio(mRightPhonicAudio);

                    correctChoice = mCorrectLocation==1;
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.phonicText0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (/*!mCurrentPhonicWords.get(0).get(7).equals("1") && !mCurrentPhonicWords.get(0).get(7).equals("3")
                        && */!beingValidated) {
                    mCurrentPhonicsWordsLocation = 0;
                    if (mCurrentPhonicWords.get(0).get(7).equals("2")){
                        mCurrentPhonicWords.get(0).set(7, "3");
                    }else{
                        mCurrentPhonicWords.get(0).set(7, "1");
                    }
                    ((ImageView) findViewById(R.id.btnMicFace0)).setImageResource(R.drawable.btn_dude_sd04_left_on);
                    ((ImageView) findViewById(R.id.btnMicFace1)).setImageResource(R.drawable.btn_dude_sd04_right_off);
                    ((TextView) findViewById(R.id.phonicText0)).setTextColor(getResources().getColor(R.color.veryDarkGray));
                    ((TextView) findViewById(R.id.phonicText1)).setTextColor(getResources().getColor(R.color.normalBlack));
                    playGeneralAudio(mLeftPhonicAudio);

                    correctChoice = mCorrectLocation==0;
                    setUpFrame();
                }
            }
        });

        findViewById(R.id.phonicText1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (/*!mCurrentPhonicWords.get(0).get(7).equals("2") && !mCurrentPhonicWords.get(0).get(7).equals("3")
                        && */!beingValidated) {
                    if (mCurrentPhonicWords.get(0).get(7).equals("1")){
                        mCurrentPhonicWords.get(0).set(7, "3");
                    }else{
                        mCurrentPhonicWords.get(0).set(7, "2");
                    }
                    mCurrentPhonicsWordsLocation = 1;
                    ((ImageView) findViewById(R.id.btnMicFace0)).setImageResource(R.drawable.btn_dude_sd04_left_off);
                    ((ImageView) findViewById(R.id.btnMicFace1)).setImageResource(R.drawable.btn_dude_sd04_right_on);
                    ((TextView) findViewById(R.id.phonicText0)).setTextColor(getResources().getColor(R.color.veryDarkGray));
                    ((TextView) findViewById(R.id.phonicText1)).setTextColor(getResources().getColor(R.color.normalBlack));
                    playGeneralAudio(mRightPhonicAudio);

                    correctChoice = mCorrectLocation==1;
                    setUpFrame();
                }
            }
        });




    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(){
        if(mCurrentPhonicsWordsLocation==0) {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on);
        }else{
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_on_right);

        }
        validateAvailable=true;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void validate(){
        beingValidated=true;
        String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+mAllPhonics.get(roundNumber).get(0)
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
                        mAllPhonics.get(roundNumber).get(0),
                        String.valueOf(mIncorrectInRound)};
                mAllPhonics.add(mAllPhonics.get(roundNumber));
            }else {
                selectionArgs = new String[]{
                        "1",
                        "0",
                        currentGSP.get(3),
                        mAllPhonics.get(roundNumber).get(0),
                        String.valueOf(mIncorrectInRound)};
            }

            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            switch(mCurrentPhonicsWordsLocation){
                default:
                case 0:
                    ((TextView) findViewById(R.id.phonicText0)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
                case 1:
                    ((TextView) findViewById(R.id.phonicText1)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
            }


        }else{
            mIncorrectInRound++;

            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    currentGSP.get(3),
                    mAllPhonics.get(roundNumber).get(0),
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.phonicText0)).setTextColor(getResources().getColor(R.color.correct_green));
                        ((TextView) findViewById(R.id.phonicText1)).setTextColor(getResources().getColor(R.color.incorrect_red));

                        break;
                    case 1:
                        ((TextView) findViewById(R.id.phonicText0)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        ((TextView) findViewById(R.id.phonicText1)).setTextColor(getResources().getColor(R.color.correct_green));
                        break;
                }
            }else{
                switch(mCurrentPhonicsWordsLocation){
                    default:
                    case 0:
                        ((TextView) findViewById(R.id.phonicText0)).setTextColor(getResources().getColor(R.color.incorrect_red));
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.phonicText1)).setTextColor(getResources().getColor(R.color.incorrect_red));
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

        //beingValidated=false;


        @Override
        public void run(){
            long audioDuration;

            switch(mProcessGuessPosition){
                case 0:
                default:{
                    if(correctChoice){
                        audioDuration=playGeneralAudio("sfx_right");
                    }else{
                        audioDuration=playGeneralAudio("sfx_wrong");
                    }
                    mProcessGuessPosition++;
                    audioHandler.postDelayed(processGuess, audioDuration+10);
                    break;
                }
                case 1:{
                    audioDuration=playGeneralAudio(currentLtrWrdAudio);
                    mProcessGuessPosition++;
                    audioHandler.postDelayed(processGuess, audioDuration+10);
                    break;
                }
                case 2:{
                    guessHandler.removeCallbacks(processGuess);
                    if(correctChoice){
                        roundNumber++;
                        if(roundNumber!=(mAllPhonics.size())){
                            //beingValidated=false;
                            validateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            lastActivityData=0;
                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        ((ImageView) findViewById(R.id.btnMicFace0)).setImageResource(R.drawable.btn_dude_sd04_left_off);
                        ((ImageView) findViewById(R.id.btnMicFace1)).setImageResource(R.drawable.btn_dude_sd04_right_off);
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
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };



    ///////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.CURRENT_PHNC_LTRS_WRDS:{
                String selection=Database.Phonic_Words.PHONIC_WORDS_TABLE_NAME+"."+Database.Phonic_Words.PHONIC_WORD_FIRST_ID+"!=0";

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_PHNC_LTRS_WRDS,null,selection,null,null);
                break;
            }
            case MotoliConstants.CURRENT_PHNC_LTRS:{

                ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        currentGSP.get(0),
                        currentGSP.get(1),
                        currentGSP.get(2),
                        currentGSP.get(3)};
                String selection=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+Database.Variable_Phase_Levels.GROUP_ID+"="+currentGSP.get(0)
                        +" AND "+Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+Database.Variable_Phase_Levels.PHASE_ID+"="+currentGSP.get(1)
                        +" AND "+Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+
                        Database.Variable_Phase_Levels.LEVEL_NUMBER+"<="+currentGSP.get(3)
                        +" AND variable_type_relations.variable_type_id=3";

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_PHNC_LTRS, projection, selection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<ArrayList<String>> mAllPhonicWords;

        switch(loader.getId()) {
            default:
                break;
            case MotoliConstants.CURRENT_PHNC_LTRS_WRDS:{
                mAllPhonicWordsBundle=new ArrayList<Bundle>();
                mAllPhonicWords=new ArrayList<ArrayList<String>>();
                int currentPhonicWordNumber=0;

                for (data.moveToFirst(); !data.isAfterLast();data.moveToNext()){
                    mAllPhonicWords.add(new ArrayList<String>());
                    mAllPhonicWordsBundle.add(new Bundle());

                    mAllPhonicWordsBundle.get(currentPhonicWordNumber).putString(mPhncWrdId, data.getString(data.getColumnIndex(mPhncWrdId)));
                    mAllPhonicWordsBundle.get(currentPhonicWordNumber).putString(mPhncWrdText,data.getString(data.getColumnIndex(mPhncWrdText)));
                    mAllPhonicWordsBundle.get(currentPhonicWordNumber).putString(mPhncWrdAudio,data.getString(data.getColumnIndex(mPhncWrdAudio)));
                    mAllPhonicWordsBundle.get(currentPhonicWordNumber).putString(mPhncWrdImage,data.getString(data.getColumnIndex(mPhncWrdImage)));
                    mAllPhonicWordsBundle.get(currentPhonicWordNumber).putString(mPhncWrdFirst,data.getString(data.getColumnIndex(mPhncWrdFirst)));
                    mAllPhonicWordsBundle.get(currentPhonicWordNumber).putString(mPhncWrdLast,data.getString(data.getColumnIndex(mPhncWrdLast)));




                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_id")));
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_text")));
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_audio")));
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_image")));
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_first_id")));
                    mAllPhonicWords.get(currentPhonicWordNumber).add(data.getString(data.getColumnIndex("phonic_word_last_id")));
                    currentPhonicWordNumber++;
                }
                getLoaderManager().restartLoader(MotoliConstants.CURRENT_PHNC_LTRS, null, this);
                break;
            }
            case MotoliConstants.CURRENT_PHNC_LTRS:{
                /*
                mAllPhonics=new ArrayList<ArrayList<String>>();
                int currentPhonicNumber=0;
                int mNumberCorrectInARow=MotoliConstants.INA_ROW_CORRECT;

                for(data.moveToFirst(); !data.isAfterLast(); data.moveToNext()){
                    mAllPhonics.add(new ArrayList<String>());
                    mAllPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("phonic_id"))); //0
                    mAllPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("phonic_text"))); //1
                    mAllPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("phonic_audio"))); //2
                    mAllPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("number_correct"))); //3
                    mAllPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("number_incorrect"))); //4
                    mAllPhonics.get(currentPhonicNumber).add(data.getString(data.getColumnIndex("number_correct_in_a_row"))); //5

                    mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(mAllPhonics.get(currentPhonicNumber).get(5)))
                            ? Integer.parseInt(mAllPhonics.get(currentPhonicNumber).get(5)):mNumberCorrectInARow;
                    currentPhonicNumber++;
                }
                if((mNumberCorrectInARow==MotoliConstants.INA_ROW_CORRECT)){
                   showCompleteLevelStar(true);
                }else{
                    showCompleteLevelStar(false);
                }


                mOppositePhonics=new ArrayList<ArrayList<String>>(mAllPhonics);
                Collections.shuffle(mOppositePhonics);
                Collections.shuffle(mAllPhonics);
                displayScreen();
                */
                processData(data);
                break;
            }
        }// end switch
        data.close();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void processData(Cursor mCursor){
        int mNumberOfPhonicVariables=(mCursor.getCount()*2>MotoliConstants.NUMBER_PHONIC_VARIABLES) ?
                MotoliConstants.NUMBER_PHONIC_VARIABLES : mCursor.getCount()*2;
        int mNumberCorrectInARow=MotoliConstants.INA_ROW_CORRECT;
        mAllPhonics=new ArrayList<ArrayList<String>>();
        int mWordNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mAllPhonics.add(new ArrayList<String>());
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_id"))); //0
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_text"))); //1
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_audio"))); //2
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5

            mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(mAllPhonics.get(mWordNumber).get(5)))
                    ? Integer.parseInt(mAllPhonics.get(mWordNumber).get(5)):mNumberCorrectInARow;

            if(mAllPhonics.get(mWordNumber).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT-3))){
                /* This is because is first attempt the letter should appear 3 times to move to next round faster */
                mWordNumber++;
                mAllPhonics.add(new ArrayList<String>());
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_id"))); //0
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_text"))); //1
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_audio"))); //2
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5


                mWordNumber++;
                mAllPhonics.add(new ArrayList<String>());
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_id"))); //0
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_text"))); //1
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_audio"))); //2
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            }else if(mAllPhonics.get(mWordNumber).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT-2))){
                mWordNumber++;
                mAllPhonics.add(new ArrayList<String>());
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_id"))); //0
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_text"))); //1
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("phonic_audio"))); //2
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mAllPhonics.get(mWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5
            }
            mWordNumber++;
            if (mWordNumber >=mNumberOfPhonicVariables) {
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else{
                mCursor.moveToNext();
            }
        }


        Collections.shuffle(mAllPhonics);

        ArrayList<ArrayList<String>> mTempCurrentWords=new ArrayList<ArrayList<String>>();

        String mPreviousWordId="0";
        for(int i=0; i<mNumberOfPhonicVariables; i++){
            if(!mAllPhonics.get(i).get(0).equals(mPreviousWordId)){
                mTempCurrentWords.add(new ArrayList<String>(mAllPhonics.get(i)));
            }else{
                if((i+1)<mNumberOfPhonicVariables){
                    mTempCurrentWords.add(new ArrayList<String>(mAllPhonics.get(i+1)));
                    mTempCurrentWords.add(new ArrayList<String>(mAllPhonics.get(i)));
                    i++;
                }else{
                    mTempCurrentWords.add(new ArrayList<String>(mTempCurrentWords.get(0)));
                    mTempCurrentWords.set(0,mAllPhonics.get(i));
                }
            }
            mPreviousWordId=mAllPhonics.get(i).get(0);
        }

        mAllPhonics=new ArrayList<ArrayList<String>>(mTempCurrentWords);
        mOppositePhonics=new ArrayList<ArrayList<String>>(mAllPhonics);

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



        findViewById(R.id.phonicImage0).setOnClickListener(new View.OnClickListener() {
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
