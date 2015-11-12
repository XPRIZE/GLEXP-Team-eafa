package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/26/2015.
 */
import java.util.ArrayList;
import java.util.Collections;








import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;

import org.w3c.dom.Text;


public class ActivityRS02 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{


    private String mCurrentSyllableAudio;
    private String mCorrectSyllableAudio;
    private String mCurrentSyllableWordAudio;

    private String mCorrectSyllableId;

    private ArrayList<ArrayList<String>> currentWords;

    private ArrayList<ArrayList<ArrayList<String>>> mAllActivityText;
    private ArrayList<ArrayList<String>> mCurrentSyllableWords;

    private int mAllSyllableWordsLocation=0;
    private int mIncorrectInRound=0;


    private ArrayList<ArrayList<String>> mTheCurrentSyllable;

    private ArrayList<String> mMatchingSyllableWord;
    private ArrayList<ArrayList<String>> mAllSyllables;
    private ArrayList<ArrayList<String>> mCurrentSyllables;
    private ArrayList<ArrayList<String>> mCurrentRoundVariables;

    private ArrayList<Bundle> mAllSyllableWordsBundle;

    private static final String mSyllableWordId="syllable_word_id";
    private static final String mSyllableWordText="syllable_word_text";
    private static final String mSyllableWordAudio="syllable_word_audio";
    private static final String mSyllableWordImage="syllable_word_image";
    private static final String mSyllableWordFirst="syllable_word_first_id";
    private static final String mSyllableWordLast="syllable_word_last_id";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_rs02);
        appData.addToClassOrder(17);
        mAllActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

        beingValidated=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();

        ((TextView) findViewById(R.id.syllableText1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.syllableText2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.syllableText3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.syllableText4)).setTypeface(appData.getCurrentFontType());

        getLoaderManager().initLoader(MotoliConstants.CURRENT_SYLLABLE_WRDS, null, this);
        ///Uri todoUri = MotoliContentProvider.CONTENT_URI_WRD_LTR_ACTIVITIES;

    }//end public void onCreate(Bundle savedInstanceState) {


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private long playInstructionAudio(){
        return playGeneralAudio("info_sd05");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////



    private void clearActivity(){
        findViewById(R.id.progressCircle1).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.progressCircle2).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.progressCircle3).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.progressCircle4).setVisibility(ProgressBar.VISIBLE);

        ((TextView) findViewById(R.id.syllableText1)).setText("");
        ((TextView) findViewById(R.id.syllableText2)).setText("");
        ((TextView) findViewById(R.id.syllableText3)).setText("");
        ((TextView) findViewById(R.id.syllableText4)).setText("");

        ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText2)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText3)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText4)).setTextColor(getResources().getColor(R.color.normalBlack));


        ((ImageView) findViewById(R.id.syllableImage0)).setImageResource(R.drawable.blank_image);

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);

        clearFrames();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void setTextColor(){
        ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText2)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText3)).setTextColor(getResources().getColor(R.color.normalBlack));
        ((TextView) findViewById(R.id.syllableText4)).setTextColor(getResources().getColor(R.color.normalBlack));
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){
        mIncorrectInRound=0;

        mCurrentRoundVariables = new ArrayList<ArrayList<String>>(mAllActivityText.get(roundNumber));
        Collections.shuffle(mCurrentRoundVariables);
        
        for (int i = 0; i < 4; i++) {
            if (mCurrentRoundVariables.get(i).get(0).equals("1")) {
                mCorrectLocation = i;
                mCorrectSyllableId=mCurrentRoundVariables.get(i).get(1);
                mCorrectSyllableAudio=mCurrentRoundVariables.get(i).get(3);
            }

            switch(i){
                default:
                case 0:{
                    ((TextView) findViewById(R.id.syllableText1)).setText(mCurrentRoundVariables.get(i).get(2));
                    findViewById(R.id.progressCircle1).setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.syllableText1).setTag(mCurrentRoundVariables.get(i).get(6));
                    break;
                }
                case 1:{
                    ((TextView) findViewById(R.id.syllableText2)).setText(mCurrentRoundVariables.get(i).get(2));
                    findViewById(R.id.progressCircle2).setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.syllableText2).setTag(mCurrentRoundVariables.get(i).get(6));
                    break;
                }
                case 2:{
                    ((TextView) findViewById(R.id.syllableText3)).setText(mCurrentRoundVariables.get(i).get(2));
                    findViewById(R.id.progressCircle3).setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.syllableText3).setTag(mCurrentRoundVariables.get(i).get(6));
                    break;
                }
                case 3:{
                    ((TextView) findViewById(R.id.syllableText4)).setText(mCurrentRoundVariables.get(i).get(2));
                    findViewById(R.id.progressCircle4).setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.syllableText4).setTag(mCurrentRoundVariables.get(i).get(6));
                    break;
                }
            }
        }

        Collections.shuffle(mAllSyllableWordsBundle);
        for(int i=0;i<mAllSyllableWordsBundle.size();i++){
            if(mAllSyllableWordsBundle.get(i).getString(mSyllableWordFirst).equals(mCorrectSyllableId)){
                mCurrentSyllableWordAudio=mAllSyllableWordsBundle.get(i).getString(mSyllableWordAudio);
                try{
                    String imageName=mAllSyllableWordsBundle.get(i).getString(mSyllableWordImage)
                            .replace(".jpg", "").replace(".png", "").trim();
                    int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
                    ((ImageView) findViewById(R.id.syllableImage0)).setImageResource(resID);
                }catch (Exception e) {
                    ((ImageView) findViewById(R.id.syllableImage0)).setImageResource(R.drawable.blank_image);
                    Log.e("MyTag", "Failure to get drawable id.", e);
                }
                break;
            }
        }


        long audioDuration=0;
        if(roundNumber==0)
            audioDuration=playInstructionAudio();

        audioHandler.postDelayed(playSyllableWordAudio, audioDuration+20);
    }//end private void displayScreen(Cursor mCurrentSyllablesCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playSyllableWordAudio = new Runnable(){

        @Override
        public void run(){
            audioHandler.removeCallbacks(playSyllableWordAudio);
            beingValidated=false;
            if(!mCurrentSyllableWordAudio.equals(""))
                playGeneralAudio(mCurrentSyllableWordAudio);
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //end private void displayScreen(Cursor currentWordsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens(){
        findViewById(R.id.syllableText1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentRoundVariables.get(0).get(6).equals("0") && !beingValidated){
                    mAllSyllableWordsLocation=0;
                    mCurrentSyllableAudio=mCurrentRoundVariables.get(0).get(3);
                    //playGeneralAudio(mCurrentSyllableAudio);
                    correctChoice=(mCurrentRoundVariables.get(0).get(0).equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.syllableText2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentRoundVariables.get(1).get(6).equals("0") && !beingValidated){
                    mAllSyllableWordsLocation=1;
                    mCurrentSyllableAudio=mCurrentRoundVariables.get(1).get(3);
                    //playGeneralAudio(mCurrentSyllableAudio);
                    correctChoice=(mCurrentRoundVariables.get(1).get(0).equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.syllableText3).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentRoundVariables.get(2).get(6).equals("0") && !beingValidated){
                    mAllSyllableWordsLocation=2;
                    mCurrentSyllableAudio=mCurrentRoundVariables.get(2).get(3);
                    //playGeneralAudio(mCurrentSyllableAudio);
                    correctChoice=(mCurrentRoundVariables.get(2).get(0).equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.syllableText4).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentRoundVariables.get(3).get(6).equals("0") && !beingValidated){
                    mAllSyllableWordsLocation=3;
                    mCurrentSyllableAudio=mCurrentRoundVariables.get(3).get(3);
                    //playGeneralAudio(mCurrentSyllableAudio);
                    correctChoice=(mCurrentRoundVariables.get(3).get(0).equals("1"));
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
                ((ImageView)findViewById(R.id.syllableTextFrame1)).setImageResource(R.drawable.frm_rs02_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.syllableTextFrame2)).setImageResource(R.drawable.frm_rs02_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.syllableTextFrame3)).setImageResource(R.drawable.frm_rs02_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.syllableTextFrame4)).setImageResource(R.drawable.frm_rs02_on);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.syllableTextFrame1)).setImageResource(R.drawable.frm_rs02_off);
        ((ImageView)findViewById(R.id.syllableTextFrame2)).setImageResource(R.drawable.frm_rs02_off);
        ((ImageView)findViewById(R.id.syllableTextFrame3)).setImageResource(R.drawable.frm_rs02_off);
        ((ImageView)findViewById(R.id.syllableTextFrame4)).setImageResource(R.drawable.frm_rs02_off);


    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void validate(){
        beingValidated=true;
        if(correctChoice) {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
        }else{
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
        }

        ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.normalBlack));

        if(correctChoice) {
            switch (mAllSyllableWordsLocation) {
                default:
                case 0:
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
                case 1:
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
                case 2:
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
                case 3:
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.correct_green));
                    break;
            }
        }else{
            switch (mAllSyllableWordsLocation) {
                default:
                case 0:
                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
                case 1:
                    ((TextView) findViewById(R.id.syllableText2)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
                case 2:
                    ((TextView) findViewById(R.id.syllableText3)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
                case 3:
                    ((TextView) findViewById(R.id.syllableText4)).setTextColor(getResources().getColor(R.color.incorrect_red));
                    break;
            }
        }

        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);




    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processPointsAndView(){
        String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+mCorrectSyllableId
                +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

        if(correctChoice){
            //processLayoutAfterGuess(true);
            //correctWordCount++;


            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    currentGSP.get(3),
                    mCorrectSyllableId,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            switch(mAllSyllableWordsLocation){
                default:
                case 0:
                    mCurrentRoundVariables.get(0).add(6, "1");

                    ((TextView) findViewById(R.id.syllableText1)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 1:
                    mCurrentRoundVariables.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText2)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 2:
                    mCurrentRoundVariables.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText3)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    ((TextView) findViewById(R.id.syllableText4)).setText("");
                    break;
                case 3:
                    mCurrentRoundVariables.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.syllableText4)).setTextColor(getResources().getColor(R.color.correct_green));
                    ((TextView) findViewById(R.id.syllableText2)).setText("");
                    ((TextView) findViewById(R.id.syllableText3)).setText("");
                    ((TextView) findViewById(R.id.syllableText1)).setText("");
                    break;
            }


        }else{
            mIncorrectInRound++;

            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    currentGSP.get(3),
                    mCorrectSyllableId,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(MotoliContentProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        mCurrentRoundVariables.get(1).add(6, "1");
                        mCurrentRoundVariables.get(2).add(6, "1");
                        mCurrentRoundVariables.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 1:
                        mCurrentRoundVariables.get(0).add(6, "1");
                        mCurrentRoundVariables.get(2).add(6, "1");
                        mCurrentRoundVariables.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 2:
                        mCurrentRoundVariables.get(1).add(6, "1");
                        mCurrentRoundVariables.get(0).add(6, "1");
                        mCurrentRoundVariables.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                    case 3:
                        mCurrentRoundVariables.get(1).add(6, "1");
                        mCurrentRoundVariables.get(2).add(6, "1");
                        mCurrentRoundVariables.get(0).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        break;
                }
            }else{
                switch(mAllSyllableWordsLocation){
                    default:
                    case 0:
                        mCurrentRoundVariables.get(0).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText1)).setText("");
                        break;
                    case 1:
                        mCurrentRoundVariables.get(1).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText2)).setText("");
                        break;
                    case 2:
                        mCurrentRoundVariables.get(2).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText3)).setText("");
                        break;
                    case 3:
                        mCurrentRoundVariables.get(3).add(6, "1");
                        ((TextView) findViewById(R.id.syllableText4)).setText("");
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////

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
                    processPointsAndView();
                    audioDuration=playGeneralAudio(mCurrentSyllableAudio);//playGeneralAudio(currentLtrWrdAudio);
                    mProcessGuessPosition++;
                    audioHandler.postDelayed(processGuess, audioDuration+10);
                    break;
                }
                case 2:{
                    guessHandler.removeCallbacks(processGuess);
                    clearFrames();
                    if(correctChoice){
                        roundNumber++;
                        if(roundNumber!=(mAllActivityText.size())){
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
            }//switch(mProcessGuessPosition){
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
                        Database.Syllable_Words.SYLLABLE_WORD_FIRST_ID+"!=0";

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_SYLLABLE_WORDS,null,selection,null,null);
                break;
            }
            case MotoliConstants.CURRENT_SYLLABLE:{
                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID()};

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_SYLLABLES, projection,
                        null, null, null);
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
            case MotoliConstants.CURRENT_SYLLABLE_WRDS:{
                mAllSyllableWordsBundle=new ArrayList<Bundle>();
                int currentPhonicWordNumber=0;

                for (data.moveToFirst(); !data.isAfterLast();data.moveToNext()){
                    mAllSyllableWordsBundle.add(new Bundle());

                    mAllSyllableWordsBundle.get(currentPhonicWordNumber).putString(mSyllableWordId,
                            data.getString(data.getColumnIndex(mSyllableWordId)));
                    mAllSyllableWordsBundle.get(currentPhonicWordNumber).putString(mSyllableWordText,
                            data.getString(data.getColumnIndex(mSyllableWordText)));
                    mAllSyllableWordsBundle.get(currentPhonicWordNumber).putString(mSyllableWordAudio,
                            data.getString(data.getColumnIndex(mSyllableWordAudio)));
                    mAllSyllableWordsBundle.get(currentPhonicWordNumber).putString(mSyllableWordImage,
                            data.getString(data.getColumnIndex(mSyllableWordImage)));
                    mAllSyllableWordsBundle.get(currentPhonicWordNumber).putString(mSyllableWordFirst,
                            data.getString(data.getColumnIndex(mSyllableWordFirst)));
                    mAllSyllableWordsBundle.get(currentPhonicWordNumber).putString(mSyllableWordLast,
                            data.getString(data.getColumnIndex(mSyllableWordLast)));


                    currentPhonicWordNumber++;
                }
                getLoaderManager().restartLoader(MotoliConstants.CURRENT_SYLLABLE, null, this);
                break;
            }
            case MotoliConstants.CURRENT_SYLLABLE:{

                processData(data);
                break;
            }
        }// end switch
        data.close();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void processData(Cursor mCursor){
        int mCursorCount=0;
        int mNumberCorrectInARow=MotoliConstants.INA_ROW_CORRECT;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            if(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))!=null) {
                mCursorCount++;
            }
            mCursor.moveToNext();
        }
        int mNumberOfSyllableVariables=(mCursorCount*2>MotoliConstants.NUMBER_SYLLABLE_VARIABLES) ?
                MotoliConstants.NUMBER_SYLLABLE_VARIABLES : mCursorCount*2;

        mCurrentSyllables=new ArrayList<ArrayList<String>>();

        int mSyllableNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            if(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))!=null) {
                mCurrentSyllables.add(new ArrayList<String>());
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_id"))); //0
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_text"))); //1
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_audio"))); //2
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
                mCurrentSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5

                if(!mCurrentSyllables.get(mSyllableNumber).get(5).isEmpty()) {
                    mNumberCorrectInARow = (mNumberCorrectInARow > Integer.parseInt(mCurrentSyllables.get(mSyllableNumber).get(5)))
                            ? Integer.parseInt(mCurrentSyllables.get(mSyllableNumber).get(5)) : mNumberCorrectInARow;
                }



                mSyllableNumber++;
            }

            if (mSyllableNumber >= mNumberOfSyllableVariables) {
                break;
            } else if (mCursor.isLast()) {
                mCursor.moveToFirst();
            } else {
                mCursor.moveToNext();
            }

        }

        Collections.shuffle(mCurrentSyllables);

        ArrayList<ArrayList<String>> mTempCurrentSyllables=new ArrayList<ArrayList<String>>();

        String mPreviousWordId="0";
        for(int i=0; i<mNumberOfSyllableVariables; i++){
            if(!mCurrentSyllables.get(i).get(0).equals(mPreviousWordId)){
                mTempCurrentSyllables.add(new ArrayList<String>(mCurrentSyllables.get(i)));
            }else{
                if((i+1)<mNumberOfSyllableVariables){
                    mTempCurrentSyllables.add(new ArrayList<String>(mCurrentSyllables.get(i+1)));
                    mTempCurrentSyllables.add(new ArrayList<String>(mCurrentSyllables.get(i)));
                    i++;
                }else{
                    mTempCurrentSyllables.add(new ArrayList<String>(mTempCurrentSyllables.get(0)));
                    mTempCurrentSyllables.set(0,mCurrentSyllables.get(i));
                }
            }
            mPreviousWordId=mCurrentSyllables.get(i).get(0);
        }

        mCurrentSyllables=new ArrayList<ArrayList<String>>(mTempCurrentSyllables);

        mAllSyllables=new ArrayList<ArrayList<String>>();
        mSyllableNumber=0;
        mCursor.moveToFirst();

        while (!mCursor.isAfterLast()) {
            mAllSyllables.add(new ArrayList<String>());
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_id"))); //0
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_text"))); //1
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("syllables_audio"))); //2
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mAllSyllables.get(mSyllableNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //5

            mSyllableNumber++;
            mCursor.moveToNext();
        }
        if((mNumberCorrectInARow==MotoliConstants.INA_ROW_CORRECT)){
           showCompleteLevelStar(true);
        }else{
            showCompleteLevelStar(false);
        }
        
        shuffleGameData();
        displayScreen();


    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////
    private void shuffleGameData(){
        String mSyllableId;
        mAllActivityText.clear();

        for(int currentCount=0; currentCount<mCurrentSyllables.size();currentCount++){
            mAllActivityText.add(new ArrayList<ArrayList<String>>());
            mAllActivityText.get(currentCount).add(new ArrayList<String>());
            mAllActivityText.get(currentCount).get(0).add("1"); //0

            mSyllableId=mCurrentSyllables.get(currentCount).get(0);

            mAllActivityText.get(currentCount).get(0).add(mCurrentSyllables.get(currentCount).get(0)); //phonic_id 1
            mAllActivityText.get(currentCount).get(0).add(mCurrentSyllables.get(currentCount).get(1)); //phonic_text 2
            mAllActivityText.get(currentCount).get(0).add(mCurrentSyllables.get(currentCount).get(2)); //phonic_audio 3
            mAllActivityText.get(currentCount).get(0).add(mCurrentSyllables.get(currentCount).get(3)); //number correct 4
            mAllActivityText.get(currentCount).get(0).add(mCurrentSyllables.get(currentCount).get(4)); //number incorrect 5
            mAllActivityText.get(currentCount).get(0).add("0"); //guess yet 6

            Collections.shuffle(mAllSyllables);

            int distCurrentCount=1;
            for(int distCount=0;distCount<mAllSyllables.size(); distCount++){

                if(!mAllSyllables.get(distCount).get(0).equals(mSyllableId)){
                    for(ArrayList mTemp : mAllActivityText.get(currentCount) ){
                        if(!mAllSyllables.get(distCount).get(0).equals(mTemp.get(1))){
                            mAllActivityText.get(currentCount).add(new ArrayList<String>());
                            mAllActivityText.get(currentCount).get(distCurrentCount).add("0");

                            mAllActivityText.get(currentCount).get(distCurrentCount).add(mAllSyllables.get(distCount).get(0)); //phonic_id
                            mAllActivityText.get(currentCount).get(distCurrentCount).add(mAllSyllables.get(distCount).get(1)); //phonic_text
                            mAllActivityText.get(currentCount).get(distCurrentCount).add(mAllSyllables.get(distCount).get(2)); //phonic_audio
                            mAllActivityText.get(currentCount).get(distCurrentCount).add(mAllSyllables.get(distCount).get(3)); //number correct
                            mAllActivityText.get(currentCount).get(distCurrentCount).add(mAllSyllables.get(distCount).get(4)); //number incorrect
                            mAllActivityText.get(currentCount).get(distCurrentCount).add("0"); //guessed yet

                            distCurrentCount++;
                            break;
                        }//  end if(!sortedWords.get(distCount).get(0).equals(mTemp.get(1))){
                    }//end for(ArrayList mTemp : mAllActivityText.get(currentCount) ){
                    if(distCurrentCount>=4){
                        break;
                    }
                }//end if(!mSortedPhonics.get(distCount).get(0).equals(mSyllableId)){
            }//end for(int distCount=0;distCount<mSortedPhonics.size(); distCount++){


        }//end for(int currentCount=0; currentCount<mCurrentSyllables.size();currentCount++){


        for(int i=0; i<mAllActivityText.size(); i++){
            Collections.shuffle(mAllActivityText.get(i));
        }
    }//end private void shuffleGameData(){
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////




    private void setUpListeners(){
        findViewById(R.id.syllableImage0).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!beingValidated) {
                    audioHandler.postDelayed(playSyllableWordAudio, 20);
                }
            }
        });


        findViewById(R.id.btnInfo).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!beingValidated) {
                    playClickSound();
                    long audioDuration=playInstructionAudio();
                    audioHandler.postDelayed(playSyllableWordAudio, audioDuration+20);
                }
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
            }
        });


        findViewById(R.id.btnValidate).setOnClickListener(new OnClickListener() {
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
