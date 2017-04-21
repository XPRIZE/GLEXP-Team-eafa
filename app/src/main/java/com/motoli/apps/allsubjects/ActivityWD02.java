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
 * on 10/31/2015.
 */
public class ActivityWD02 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{



    private int mCurrentTextLocation=0;
    private int mIncorrectInRound=0;

    private String mCorrectID;

    private String mCurrentCorrectAudio="";
    private String mClickedAudio="";

    private ArrayList<ArrayList<String>> mCurrentText;

    private ArrayList<ArrayList<String>> mAllText;


    private ArrayList<ArrayList<ArrayList<String>>> mAllActivityText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_wd02);
        appData.addToClassOrder(5);
        mAllActivityText = new ArrayList<>();
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mBeingValidated=true;
        mInstructionAudio="info_wd02";
        clearActivity();
        setUpListeners();
        setupFrameListens();

        ((TextView) findViewById(R.id.word1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word4)).setTypeface(appData.getCurrentFontType());

        getLoaderManager().initLoader(Constants.GUESS_WORDS, null, this);
        startActivityHandler.postDelayed(startActivity, 200);
    }//end public void onCreate(Bundle savedInstanceState) {

    //////////////////////////////////////////////////////////////////////////////////////////

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////

    private void start(){
        getLoaderManager().initLoader(Constants.GUESS_WORDS, null, this);
    }

    public void onResume(){
        super.onResume();
        mBeingValidated=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();

        getLoaderManager().restartLoader(Constants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration = super.playInstructionAudio();
        mAudioHandler.postDelayed(playLtrAudio, mAudioDuration+20);
        return mAudioDuration;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////



    private void clearActivity(){
        /*




*/

        ((TextView) findViewById(R.id.word1)).setText("");
        ((TextView) findViewById(R.id.word2)).setText("");
        ((TextView) findViewById(R.id.word3)).setText("");
        ((TextView) findViewById(R.id.word4)).setText("");

        ((TextView) findViewById(R.id.word1)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word2)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word3)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word4)).setTextColor(
                ContextCompat.getColor(this,R.color.normalBlack));


        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);

        clearFrames();

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        String mCurrentImage="";
        mIncorrectInRound=0;
        mCurrentText = new ArrayList<>(mAllActivityText.get(roundNumber));
        Collections.shuffle(mCurrentText);

        int mFontSize=0;
        for(int i=0;i<mCurrentText.size();i++){
            mFontSize=(mCurrentText.get(i).get(2).length()>mFontSize) ?
                    mCurrentText.get(i).get(2).length() : mFontSize;
        }

        if(mFontSize>=8) {
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdFifthFontSize));
        }else if(mFontSize>=7){
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdForthFontSize));
        }else if(mFontSize>=6){
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdThirdFontSize));
        }else if(mFontSize>=5){
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdSecondFontSize));
        }else{
            ((TextView) findViewById(R.id.word1))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word2))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word3))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
            ((TextView) findViewById(R.id.word4))
                    .setTextSize(getResources().getDimension(R.dimen.wdFirstFontSize));
        }



        ((TextView) findViewById(R.id.word1)).setText(mCurrentText.get(0).get(2));
        //
        
        if(mCurrentText.get(0).get(0).equals("1")){
            mCorrectLocation=0;
            mCorrectID=mCurrentText.get(0).get(1);
            mCurrentImage=mCurrentText.get(0).get(7);
            mCurrentCorrectAudio=mCurrentText.get(0).get(3);
        }


        ((TextView) findViewById(R.id.word2)).setText(mCurrentText.get(1).get(2));
        //
        if(mCurrentText.get(1).get(0).equals("1")){
            mCorrectLocation=1;
            mCorrectID=mCurrentText.get(1).get(1);
            mCurrentImage=mCurrentText.get(1).get(7);
            mCurrentCorrectAudio=mCurrentText.get(1).get(3);
        }

        ((TextView) findViewById(R.id.word3)).setText(mCurrentText.get(2).get(2));
        //
        if(mCurrentText.get(2).get(0).equals("1")){
            mCorrectLocation=2;
            mCorrectID=mCurrentText.get(2).get(1);
            mCurrentImage=mCurrentText.get(2).get(7);
            mCurrentCorrectAudio=mCurrentText.get(2).get(3);
        }

        ((TextView) findViewById(R.id.word4)).setText(mCurrentText.get(3).get(2));
        //
        if(mCurrentText.get(3).get(0).equals("1")){
            mCorrectLocation=3;
            mCorrectID=mCurrentText.get(3).get(1);
            mCurrentImage=mCurrentText.get(3).get(7);
            mCurrentCorrectAudio=mCurrentText.get(3).get(3);
        }

        long mAudioDuration=0;
        if(roundNumber==0)
            mAudioDuration=playInstructionAudio();

        if(!mCurrentImage.equals("")){
            try{
                String imageName=mCurrentImage.replace(".jpg", "").replace(".png", "").trim();
                int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
                ((ImageView) findViewById(R.id.image0)).setImageResource(resID);
            }catch (Exception e) {
                ((ImageView) findViewById(R.id.image0)).setImageResource(R.drawable.blank_image);
                Log.e("MyTag", "Failure to get drawable id.", e);
            }

            mAudioHandler.postDelayed(playLtrAudio, mAudioDuration+20);
        }else{
            mCorrect=true;
            mProcessGuessPosition=2;
            guessHandler.postDelayed(processGuess, mAudioDuration );
        }
    }//end private void displayScreen(Cursor currentWordsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playLtrAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playLtrAudio);
            mBeingValidated=false;
            if(!mCurrentCorrectAudio.equals(""))
                playGeneralAudio(mCurrentCorrectAudio);
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////
    
    protected void setupFrameListens(){
        findViewById(R.id.word1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(0).get(6).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=0;
                    mClickedAudio=mCurrentText.get(0).get(3);
                    mCurrentID=mCurrentText.get(0).get(1);
                    mCorrect=(mCurrentText.get(0).get(0).equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.word2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(1).get(6).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=1;
                    mClickedAudio=mCurrentText.get(1).get(3);
                    mCurrentID=mCurrentText.get(1).get(1);
                    mCorrect=(mCurrentText.get(1).get(0).equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.word3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(2).get(6).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=2;
                    mClickedAudio=mCurrentText.get(2).get(3);
                    mCurrentID=mCurrentText.get(2).get(1);
                    mCorrect=(mCurrentText.get(2).get(0).equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.word4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(3).get(6).equals("0") && !mBeingValidated){
                    mCurrentTextLocation=3;
                    mClickedAudio=mCurrentText.get(3).get(3);
                    mCurrentID=mCurrentText.get(3).get(1);
                    mCorrect=(mCurrentText.get(3).get(0).equals("1"));
                    setUpFrame(3);
                }
            }
        });


    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        mValidateAvailable=true;
        switch(frameNumber){
            default:
            case 0:{
                ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_wd02_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_wd02_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_wd02_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_wd02_on);
                break;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    
    private void clearFrames(){
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_wd02_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_wd02_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_wd02_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_wd02_off);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
        }else{
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
        }
        processFontColorAndPoints();
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processFontColorAndPoints(){
        String[] selectionArgs;

        if(mCorrect){
            String where=" WHERE app_user_id="+appData.getCurrentUserID()
                    +" AND variable_id="+mCurrentID
                    +" AND activity_id="+appData.getCurrentActivity().get(0);
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentID,
                    String.valueOf(mIncorrectInRound)};


            getContentResolver().update(
                    AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            switch(mCurrentTextLocation){
                default:
                case 0:
                    mCurrentText.get(0).add(6, "1");
                    ((TextView) findViewById(R.id.word1))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    break;
                case 1:
                    mCurrentText.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.word2))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    break;
                case 2:
                    mCurrentText.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.word3))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    break;
                case 3:
                    mCurrentText.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.word4))
                            .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
                    break;
            }


        }else{
            String where=" WHERE app_user_id="+appData.getCurrentUserID()
                    +" AND variable_id="+mCorrectID
                    +" AND activity_id="+appData.getCurrentActivity().get(0);
            mIncorrectInRound++;
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCorrectID,
                    String.valueOf(mIncorrectInRound)};

            getContentResolver().update(
                    AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
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
                    ((TextView) findViewById(R.id.word1))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
                case 1:
                    mCurrentText.get(1).add(6, "1");
                    ((TextView) findViewById(R.id.word2))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
                case 2:
                    mCurrentText.get(2).add(6, "1");
                    ((TextView) findViewById(R.id.word3))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
                case 3:
                    mCurrentText.get(3).add(6, "1");
                    ((TextView) findViewById(R.id.word4))
                            .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
                    break;
            }


        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processChoiceView(){


        if(mCorrect){

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
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
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
                    processChoiceView();
                    mAudioDuration=playGeneralAudio(mClickedAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
                    guessHandler.removeCallbacks(processGuess);
                    clearFrames();
                    if(mCorrect){
                        roundNumber++;
                        if(roundNumber!=(mAllActivityText.size())){
                            //mBeingValidated=false;
                            mValidateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 50);
                        }else{
                            mLastActivityData=0;
                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.INVISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));
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
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off);
                    displayScreen();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };



    /////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.GUESS_WORDS:{
                if(mCurrentGSP.get("phase_id").equals("3")){
                    mCurrentGSP.put("phase_id","2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),"4", "1", "11",
                        mCurrentGSP.get("current_level"),"1"};

                String selection="variable_phase_levels.group_id" +
                        "="+mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id" +
                        "="+mCurrentGSP.get("phase_id")+
                        " AND ((variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-1 "+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-2 "+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-3"+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+" ) "+
                        " OR variable_phase_levels.level_number" +
                        "<="+mCurrentGSP.get("current_level")+" -3)"+
                        " AND variable_phase_levels.level_number>0";


                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_GUESS_WORDS, projection, selection, null, null);
                break;
            }
            case Constants.CURRENT_WORDS:{

                if(mCurrentGSP.get("phase_id").equals("3")){
                    mCurrentGSP.put("phase_id","2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),"4", "1", "11",
                        mCurrentGSP.get("current_level"),"0"};

                String selection="variable_phase_levels.group_id" +
                        "="+mCurrentGSP.get("group_id")+
                        " AND variable_phase_levels.phase_id" +
                        "="+mCurrentGSP.get("phase_id")+
                        " AND ((variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-1 "+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-2 "+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+"-3"+
                        " OR variable_phase_levels.level_number" +
                        "="+mCurrentGSP.get("current_level")+" ) "+
                        " OR variable_phase_levels.level_number" +
                        "<="+mCurrentGSP.get("current_level")+" -3)"+
                        " AND variable_phase_levels.level_number>0 " +
                        "AND words.image_url!=''";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_WORDS_BY_LEVEL, 
                        projection, selection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor mCursor) {

        switch(loader.getId()) {
            default:
                break;
            case Constants.GUESS_WORDS:{
                mAllText=new ArrayList<>();
                int mGuessWordNumber=0;
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    mAllText.add(new ArrayList<String>());
                    mAllText.get(mGuessWordNumber).add(
                            mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
                    mAllText.get(mGuessWordNumber).add(
                            mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
                    mAllText.get(mGuessWordNumber).add(
                            mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
                    mAllText.get(mGuessWordNumber).add("0"); //3
                    mAllText.get(mGuessWordNumber).add("0"); //4
                    mAllText.get(mGuessWordNumber).add(
                            mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
                    mAllText.get(mGuessWordNumber).add("0"); //6
                    mAllText.get(mGuessWordNumber).add(
                            mCursor.getString(mCursor.getColumnIndex("level_number"))); //7

                    mGuessWordNumber++;
                    mCursor.moveToNext();
                }
                Collections.shuffle(mAllText);
                getLoaderManager().restartLoader(Constants.CURRENT_WORDS, null, this);
                break;
            }
            case Constants.CURRENT_WORDS:{
                processData(mCursor);
                break;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mCurrentWords=new ArrayList<>();
        int currentWordNumber=0;
        if(mCursor.moveToFirst()) {
            do{

                mCurrentWords.add(new ArrayList<String>());
                mCurrentWords.get(currentWordNumber).add
                        (mCursor.getString(mCursor.getColumnIndex("word_id"))); //0
                mCurrentWords.get(currentWordNumber).add(
                        mCursor.getString(mCursor.getColumnIndex("word_text"))); //1
                mCurrentWords.get(currentWordNumber).add(
                        mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
                mCurrentWords.get(currentWordNumber).add("0"); //3
                mCurrentWords.get(currentWordNumber).add("0"); //4
                mCurrentWords.get(currentWordNumber).add(
                        mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
                mCurrentWords.get(currentWordNumber).add("0"); //6
                mCurrentWords.get(currentWordNumber).add(
                        mCursor.getString(mCursor.getColumnIndex("level_number"))); //7

                currentWordNumber++;
                if (currentWordNumber >= Constants.NUMBER_VARIABLES ||
                        mCursor.isLast()) {
                    break;
                }
            }while(mCursor.moveToNext());
        }

        Collections.shuffle(mCurrentWords);

        if(mCurrentWords.size()<Constants.NUMBER_VARIABLES ) {
            for (int i = 0; i < mCurrentWords.size(); i++) {
                if (!mCurrentWords.get(mCurrentWords.size() - 1).get(0)
                        .equals(mCurrentWords.get(i).get(0))) {
                    mCurrentWords.add(new ArrayList<>(mCurrentWords.get(i)));
                    if (mCurrentWords.size() >= Constants.NUMBER_VARIABLES) {
                        break;
                    }
                }
            }
        }

        shuffleGameData();
        displayScreen();

    }
    ////////////////////////////////////////////////////////////////////////////////////////////


    private void shuffleGameData(){

        ArrayList<ArrayList<String>> mTempWords=new ArrayList<>(mCurrentWords);

        ArrayList<ArrayList<String>> mSortedWords=new ArrayList<>(mAllText);
        mAllActivityText.clear();

        for(int mCount=0; mCount<mCurrentWords.size();mCount++){
            mAllActivityText.add(new ArrayList<ArrayList<String>>());
            mAllActivityText.get(mCount).add(new ArrayList<String>());
            mAllActivityText.get(mCount).get(0).add("1"); //0

            mAllActivityText.get(mCount).get(0)
                    .add(mCurrentWords.get(mCount).get(0)); //word_id 1
            mAllActivityText.get(mCount).get(0)
                    .add(mCurrentWords.get(mCount).get(1)); //word_text 2
            mAllActivityText.get(mCount).get(0)
                    .add(mCurrentWords.get(mCount).get(2)); //audio_url 3
            mAllActivityText.get(mCount).get(0)
                    .add(mCurrentWords.get(mCount).get(3)); //number correct 4
            mAllActivityText.get(mCount).get(0)
                    .add(mCurrentWords.get(mCount).get(4)); //number incorrect 5
            mAllActivityText.get(mCount).get(0)
                    .add("0"); //guess yet 6
            mAllActivityText.get(mCount).get(0)
                    .add(mCurrentWords.get(mCount).get(5)); //image_url 7

            Collections.shuffle(mTempWords);
            int mSubCount=1;
            for(int i=0;i<mTempWords.size();i++){
                boolean mNoMatching=true;
                for(ArrayList mTemp : mAllActivityText.get(mCount) ) {
                    if(mTemp.get(1).equals(mTempWords.get(i).get(0))){
                        mNoMatching=false;
                        break;
                    }
                }
                if(mNoMatching) {
                    mAllActivityText.get(mCount).add(new ArrayList<String>());
                    mAllActivityText.get(mCount).get(mSubCount).add("0"); //0


                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempWords.get(i).get(0)); //word_id 1
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempWords.get(i).get(1)); //word_text 2
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempWords.get(i).get(2)); //audio_url 3
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempWords.get(i).get(3)); //number correct 4
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempWords.get(i).get(4)); //number incorrect 5
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add("0"); //guess yet 6
                    mAllActivityText.get(mCount).get(mSubCount)
                            .add(mTempWords.get(i).get(5)); //image_url 7
                    mSubCount++;
                    if((mSubCount==3 && mCurrentWords.size()>=12)
                            ||(mSubCount==2 && mCurrentWords.size()<12)) {
                        break;
                    }
                }
            }


            Collections.shuffle(mSortedWords);

            for(int mDistCount=0;mDistCount<mSortedWords.size(); mDistCount++){
                if(!mSortedWords.get(mDistCount).get(0)
                        .equals(mCurrentWords.get(mCount).get(0)) ){

                    boolean mPhonicOkay=true;
                    for(ArrayList mTemp : mAllActivityText.get(mCount) ) {
                        if (mSortedWords.get(mDistCount).get(0).equals(mTemp.get(1)) ) {
                            Log.d(Constants.LOGCAT, "FALSE");
                            mPhonicOkay=false;

                        }
                    }
                    if(mPhonicOkay) {
                        mAllActivityText.get(mCount).add(new ArrayList<String>());
                        mAllActivityText.get(mCount).get(mSubCount).add("0");

                        mAllActivityText.get(mCount).get(mSubCount).add(mSortedWords.get(mDistCount).get(0)); //word_id
                        mAllActivityText.get(mCount).get(mSubCount).add(mSortedWords.get(mDistCount).get(1)); //word_text
                        mAllActivityText.get(mCount).get(mSubCount).add(mSortedWords.get(mDistCount).get(2)); //audio_url
                        mAllActivityText.get(mCount).get(mSubCount).add(mSortedWords.get(mDistCount).get(3)); //number correct
                        mAllActivityText.get(mCount).get(mSubCount).add(mSortedWords.get(mDistCount).get(4)); //number incorrect
                        mAllActivityText.get(mCount).get(mSubCount).add("0"); //guessed yet
                        mAllActivityText.get(mCount).get(mSubCount).add(mSortedWords.get(mDistCount).get(5)); //image_url 7
                        mSubCount++;
                    }
                }
                if(mSubCount>=4) {
                    break;
                }
            

            }//end for(int distCount=0;distCount<mSortedWords.size(); distCount++){


        }//end for(int mCount=0; mCount<mCurrentWords.size();mCount++){

        for(int i=0; i<mAllActivityText.size(); i++){
            Collections.shuffle(mAllActivityText.get(i));
        }
    }//end private void shuffleGameData(){

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){
        findViewById(R.id.image0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mCurrentCorrectAudio.equals(""))
                    playGeneralAudio(mCurrentCorrectAudio);
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

}
