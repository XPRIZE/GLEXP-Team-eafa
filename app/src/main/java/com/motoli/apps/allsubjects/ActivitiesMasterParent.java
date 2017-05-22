package com.motoli.apps.allsubjects;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.os.Process.getElapsedCpuTime;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
public abstract class ActivitiesMasterParent extends Master_Parent {

    protected String mCurrentVariable;
    protected String mCorrectAudio;
    protected String mCurrentAudio;
    protected String mCurrentID;
    protected String mCorrectID;
    protected String currentLtrWrdAudio="";
    protected String matchingLtrWrdAudio="";
    protected String mInstructionAudio;
    protected String[] mInfo;
    
    protected boolean mBeginRound;
    protected boolean mCorrect=false;
    protected boolean mBeingValidated=false;
    protected boolean mValidateAvailable=false;

    protected int mCorrectLocation=0;
    protected int mActivityNumber;
    protected int mCurrentLocation;
    protected int roundNumber=0;
    protected int mRoundNumber=0;
    protected int mIncorrectInRound=0;
    protected int mProcessGuessPosition=0;
    protected int mLastActivityData=0;
    private int endAudioLocation;

    protected Handler validateHandler = new Handler();
    protected Handler guessHandler = new Handler();
    protected Handler lastActivityDataHandler = new Handler();
    protected Handler startActivityHandler = new Handler();

    protected ArrayList<ArrayList<String>> mCurrentWords;
    protected ArrayList<ArrayList<ArrayList<String>>> allActivityText;
    
    protected HashMap<String,String> mCurrentGSP;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        runs garbage collection before the start of every new activity. This is for any lose ties
        will use to help find garbage leaks as well
         */
        freeMemory();

        mFinishRound=false;
        /*
        sets mCurrentGSP for current GSP or set to defauly group 2 is date isfound
         */
        if(appData.getCurrentGroup_Section_Phase()!=null) {
            mCurrentGSP = new HashMap<>(appData.getCurrentGroup_Section_Phase());
        }else{
            mCurrentGSP = new HashMap<>();
            mCurrentGSP.put("group_id","2");
            mCurrentGSP.put("phase_id","1");
            mCurrentGSP.put("section_id","5");
            mCurrentGSP.put("current_level","1");
        }
        if(!appData.getActivityName().equals("ActivityVL")) {
            appData.setActivityType(3);
        }
        getElapsedCpuTime();

    }

    /**
     * This function is called to just cause either a fade in or out of the activity page
     * @param mFadeIn true it will fade in, otherwise false is fade out
     */
    protected void fadeInOrOutScreenInActivity(boolean mFadeIn){
        if(findViewById(R.id.activityMainPart)!=null) {
            if (mFadeIn) {
                findViewById(R.id.activityMainPart)
                        .setVisibility(LinearLayout.VISIBLE);
                findViewById(R.id.activityMainPart)
                        .setAnimation(AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.fade_in));
            } else {
                findViewById(R.id.activityMainPart)
                        .setVisibility(LinearLayout.INVISIBLE);
                findViewById(R.id.activityMainPart)
                        .setAnimation(AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.fade_out));
            }
        }
    }

    /**
     * onResume() called naturally to hide the change case and video icons. If able it will show
     * the activityName (only used for debugging purposes. This will also begin the process of
     * tracking the number of times a user uses certain app for certain level
     */
    @Override
    public void onResume(){
        super.onResume();

        if(findViewById(R.id.btnChangeCase)!=null){
            findViewById(R.id.btnChangeCase).setVisibility(ImageView.INVISIBLE);
        }
        if(findViewById(R.id.btnVideo)!=null) {
            findViewById(R.id.btnVideo).setVisibility(ImageView.INVISIBLE);
        }

        if(findViewById(R.id.activityName)!=null) {
           // findViewById(R.id.activityName).setVisibility(TextView.INVISIBLE);
            if (appData.getAllowActivityText()) {
                ((TextView) findViewById(R.id.activityName))
                        .setText(appData.getActivityName() + " ("
                        + mCurrentGSP.get("group_id") + "|"
                        + mCurrentGSP.get("phase_id") + "|"
                        + mCurrentGSP.get("section_id") + "|"
                        + mCurrentGSP.get("current_level") + ")");
            } else {
                ((TextView) findViewById(R.id.activityName)).setText("");
            }
        }

        if(appData.getCurrentActivity()!=null) {
            mInfo = new String[]{
                    mCurrentGSP.get("group_id"),
                    mCurrentGSP.get("phase_id"),
                    appData.getCurrentActivity().get(0),
                    mCurrentGSP.get("current_level"),
                    appData.getCurrentUserID(),
                    "0"};
           getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_ATTEMPTS,
                    null, "", mInfo);
        }
    }

    /**
     * addActivityPointsByLevel
     * Is called only by SD (Phonic) activities called in ActivitySDRoot.
     * Add point for right or wrong answer to database
     */
    protected  void addActivityPointsByLevel(){
        String where = " WHERE app_user_id=" + appData.getCurrentUserID()+ " " +
                "AND variable_id=" + mCurrentGSP.get("current_level") + " " +
                "AND activity_id=" + appData.getCurrentActivity().get(0);
        String[] selectionArgs;

        if (mCorrect) {
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentGSP.get("current_level"),
                    String.valueOf(mIncorrectInRound),
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};

            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
        }else{
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCurrentGSP.get("current_level"),
                    String.valueOf(mIncorrectInRound),
                    appData.getCurrentActivity().get(0),
                    appData.getCurrentUserID()};


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE,
                    null, where, selectionArgs);
        }
    }

    /**
     * addActivityPointsToVariablesValues
     * Is called only by SD (Phonic) activities called in ActivitySDRoot. called  immediately after
     * addActivityPointsByLevel
     * Add point for right or wrong answer to database
     * fairlyadb boot sure this was done due to original multiple tables to add points to
     *Will possibly remove later
     */
    protected  void addActivityPointsToVariablesValues(){
        String mWhere=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCorrectID
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] mInfo;
        if(mCorrect) {
            if(mIncorrectInRound>=1) {
                mInfo = new String[]{
                        "0",
                        "0",
                        mCurrentGSP.get("current_level"),
                        mCorrectID,
                        String.valueOf(mIncorrectInRound)};
            }else{
                mInfo = new String[]{
                        "1",
                        "0",
                        mCurrentGSP.get("current_level"),
                        mCorrectID,
                        String.valueOf(mIncorrectInRound)};
            }

            getContentResolver().update(
                    AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE_VV, null, mWhere, mInfo);
        }else{
            mInfo = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCorrectID,
                    String.valueOf(mIncorrectInRound)};

            getContentResolver().update(
                    AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE_VV, null, mWhere, mInfo);
        }
    }

    /**
     * used to free memory by running a garbage collection used ast beginning of each activity
     */
    public void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }


    /**
     * This is used only in letters and words and eventually syllables
     */
    protected void placeVariableAttempts(){
        String mType="1";

        switch (Integer.parseInt(mCurrentGSP.get("group_id"))){
            case 2:
                switch (Integer.parseInt(mCurrentGSP.get("phase_id"))){
                    default:
                    case 1:
                        mType="1";
                        break;
                    case 2:
                        mType="2";
                        break;
                    case 3:
                        mType="3";
                        break;
                }
                break;
            case 3:
                switch (Integer.parseInt(mCurrentGSP.get("phase_id"))){
                    default:
                    case 1:
                        mType="4";
                        break;
                    case 2:
                        mType="5";
                        break;
                }
                break;
            case 4:
                mType="6";
                break;
            case 5:
                mType="7";
                break;
            case 6:
                mType="9";
                break;
            case 7:
                mType="10";
                break;
            case 8:
                mType="11";
                break;
        }
        String mCorrectOrNot="0";
        if(mCorrect){
            mCorrectOrNot="1";
        }
        String[] mInfo = new String[]{
                mCorrectID,
                mType,
                mCurrentGSP.get("group_id"),
                mCurrentGSP.get("phase_id"),
                appData.getCurrentActivity().get(0),
                appData.getCurrentUserID(),
                mCurrentGSP.get("current_level"),
                mCorrectOrNot,
                String.valueOf(mIncorrectInRound)
        };
        getContentResolver().update(
                AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE_EVV, null, null, mInfo);
    }

    /**
     * addActivityPoints()
     * sets data to be called in AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE in order to
     * either add points to relevant correct answer or set to zero if needed. Also to track
     * general right or wrong.
     */
    protected  void addActivityPoints(){
        String mWhere=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCorrectID
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] mInfo;
        if(mCorrect) {
            if(mIncorrectInRound>=1) {
                /*
                 * if last answer was incorrect and second
                 */
                mInfo = new String[]{
                        "0",
                        "0",
                        mCurrentGSP.get("current_level"),
                        mCorrectID,
                        String.valueOf(mIncorrectInRound),
                        appData.getCurrentActivity().get(0)};
            }else{
                mInfo = new String[]{
                        "1",
                        "0",
                        mCurrentGSP.get("current_level"),
                        mCorrectID,
                        String.valueOf(mIncorrectInRound),
                        appData.getCurrentActivity().get(0)};
            }
            
            getContentResolver().update(
                    AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, mWhere, mInfo);
        }else{
            mInfo = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mCorrectID,
                    String.valueOf(mIncorrectInRound),
                    appData.getCurrentActivity().get(0)};

            getContentResolver().update(
                    AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, mWhere, mInfo);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void addPointToAllAvailable(){
        String mWhere=" activity_id="+appData.getCurrentActivity().get(0)+" " +
                "AND app_user_id="+appData.getCurrentUserID();

        getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_ALL_UPDATE,
                null, mWhere, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void addPointToAllAvailableIfNoIncorrect(){
        String mWhere=" activity_id="+appData.getCurrentActivity().get(0)+" " +
                "AND app_user_id="+appData.getCurrentUserID();
        getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_ALL_UPDATE,
                null, mWhere, null);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    protected Runnable playExtraEndAudio = new Runnable(){
        @Override
        public void run() {
            switch(endAudioLocation){
                default:
                case 0:
                    Log.d(Constants.LOGCAT,"app_audio_finished_level");
                    long mAudioDuration=playFeedBackAudio("app_audio_finished_level");
                    endAudioLocation++;
                    lastActivityDataHandler.postDelayed(playExtraEndAudio, mAudioDuration+40);

                    break;
                case 1:
                    Log.d(Constants.LOGCAT,"app_audio_next_level");
                    playFeedBackAudio("app_audio_next_level");
                    lastActivityDataHandler.removeCallbacks(playExtraEndAudio);
                    break;
            }
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////


    protected void beginRound(){  }
    protected void displayScreen(){  }
    protected long playInstructionAudio(){
        return playGeneralAudio(mInstructionAudio);
    }

    /**
     * Plays audio of current word so long as audio exists
     */
    protected Runnable playWordAudioTimed = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playWordAudioTimed);
            if(!mCurrentAudio.equals(""))
                playGeneralAudio(mCurrentAudio);
        }
    };
    protected void processData(Cursor mCursor){    }
    protected void processTheGuess(long mAudioDuration){ }
    protected void processValidationDisplay(){ }
    protected void setUpListeners() { }
    protected void validate(){ }

    public void backButtonClicked(View view){
        playClickSound();
        moveBackwords();
    }

    public void infoButtonClicked(View view){
        if(!mBeingValidated) {
            playClickSound();
            playInstructionAudio();
        }
    }

    public void tvButtonClicked(View view){
        playClickSound();
        moveToVideo();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void moveToVideo(){
        ArrayList<String> currentActivity = new ArrayList<>();
        currentActivity.add(String.valueOf(appData.getCurrentVideoID()));
        appData.setCurrentActivity(currentActivity);
        appData.setCurrentActivityName("ActivityVL");
        Intent main = new Intent(this,ActivityVL.class);
        startActivity(main);
        finish();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    protected Runnable returnToActivities_Platorm = new Runnable(){
        @Override
        public void run(){
            mFinishRound=true;
            mCurrentGSP = new HashMap<>(appData.getCurrentGroup_Section_Phase());
            long mAudioDuration=0;
            String[] mInfo = new String[]{
                    mCurrentGSP.get("group_id"),
                    mCurrentGSP.get("phase_id"),
                    appData.getCurrentActivity().get(0),
                    mCurrentGSP.get("current_level"),
                    appData.getCurrentUserID(),
                    "1"};

            switch(Integer.parseInt(mCurrentGSP.get("group_id"))){
                default:
                case 2:
                    switch(Integer.parseInt(mCurrentGSP.get("phase_id"))) {
                        default:
                        case 1:
                            mInfo[5] = "1";
                            break;
                        case 2:
                            mInfo[5] = "2";
                            break;
                        case 3:
                            mInfo[5] = "3";
                            break;
                    }
                    break;
                case 3:
                    if(mCurrentGSP.get("phase_id").equals("1")){
                        mInfo[5] =  "4";
                    }else{
                        mInfo[5] =  "5";
                    }
                    break;
                case 4:
                    mInfo[5] =  "6";
                    break;
                case 5:
                    if(mCurrentGSP.get("phase_id").equals("1")){
                        mInfo[5] =  "7";
                    }else{
                        mInfo[5] =  "8";
                    }
                    break;
                case 6:
                    mInfo[5] = "9";
                    break;
                case 7:
                    mInfo[5] = "10";
                    break;
                case 8:
                    mInfo[5] = "11";
                    break;
            }
            switch(mLastActivityData){
                default:
                case 0:
                    mLastActivityData++;

                    Log.d(Constants.LOGCAT,"before check wrongs");
                    Cursor mCursor =getContentResolver().query(
                            AppProvider.CONTENT_GET_MAX_LEVEL,null,null,mInfo,null);
                    int mMaxLevel=1;
                    if(mCursor != null && mCursor.moveToFirst()){
                        mMaxLevel=mCursor.getInt(mCursor.getColumnIndex("max_level_number"));
                    }

                    if(Integer.parseInt(mCurrentGSP.get("current_level"))<mMaxLevel
                            && mMaxLevel!=1 ) {
                        mCursor = getContentResolver().query(
                                AppProvider.CONTENT_URI_CHECK_WRONGS, null, null, mInfo, null);
                        Log.d(Constants.LOGCAT, "after check wrongs");

                        int mInARowCorrect = Constants.INA_ROW_CORRECT;

                        if (mCurrentGSP.get("group_id").equals("6")
                                && Integer.parseInt(mCurrentGSP.get("current_level")) > 6) {
                            mInARowCorrect = Constants.INA_ROW_CORRECT_HIGHER;
                        } else if (mCurrentGSP.get("group_id").equals("6")) {
                            mInARowCorrect = Constants.INA_ROW_CORRECT_WORDS;
                        } else if (mCurrentGSP.get("group_id").equals("4")) {
                            mInARowCorrect = Constants.INA_ROW_CORRECT_WORDS;
                        } else if (mCurrentGSP.get("group_id").equals("3")) {
                            mInARowCorrect = Constants.INA_ROW_CORRECT_WORDS;
                        } else if (mCurrentGSP.get("group_id").equals("8")) {
                            mInARowCorrect = Constants.MATH_OPERATIONS_INA_ROW_CORRECT;
                        }

                        if (mCursor != null && mCursor.moveToFirst()) {
                            int mVariableCount = 0;
                            String mCorrectInLevel = mCursor.getString(
                                    mCursor.getColumnIndex("number_correct_in_level"));
                            String mGroupID = mCurrentGSP.get("group_id");
                            int mCurrentLevel = Integer.parseInt(mCurrentGSP.get("current_level"));
                            if (mCursor.getString(
                            mCursor.getColumnIndex("variable_count")) != null) {
                                mVariableCount = mCursor.getInt(
                                        mCursor.getColumnIndex("variable_count"));
                            }

                            int mNumberCorrectInARow = 0;
                            if (mCursor.getString(
                                    mCursor.getColumnIndex("number_correct_in_a_row")) != null) {
                                mNumberCorrectInARow = Integer.parseInt(mCursor.getString(
                                        mCursor.getColumnIndex("number_correct_in_a_row")));
                            }

                            int mAttempts = 0;
                            if (mCursor.getString(
                                    mCursor.getColumnIndex("attempts")) != null) {
                                mAttempts = Integer.parseInt(mCursor.getString(
                                        mCursor.getColumnIndex("attempts")));
                            }

                            if (mAttempts >= Math.ceil(
                                    ((double) (mVariableCount * mInARowCorrect + 2)) / 8)
                                    && mNumberCorrectInARow < mInARowCorrect) {
                                Log.d(Constants.LOGCAT, "app_audio_many_mistakes");
                                mAudioDuration = playFeedBackAudio("app_audio_many_mistakes");
                            } else if (mCursor.getString(
                                    mCursor.getColumnIndex("number_correct_in_a_row")) != null
                                    && Integer.parseInt(mCursor.getString(
                                    mCursor.getColumnIndex("number_correct_in_a_row")))
                                    < mInARowCorrect) {
                                Log.d(Constants.LOGCAT, "app_audio_almost_there");
                                mAudioDuration = playFeedBackAudio("app_audio_almost_there");
                            } else {
                                if(mCorrectInLevel!=null) {
                                    Log.d(Constants.LOGCAT, "app_audio_get_medal");
                                    mAudioDuration = getAudioDuration("app_audio_get_medal");
                                    if (Integer.parseInt(mCorrectInLevel)
                                            >= Constants.MATH_OPERATIONS_INA_ROW_CORRECT
                                            || mCorrectInLevel.equals("2")
                                            || (mCorrectInLevel.equals("1")
                                            && (mGroupID.equals("4") || mGroupID.equals("3")))
                                            || (mCorrectInLevel.equals("1")
                                            && mGroupID.equals("6") && mCurrentLevel > 6)) {
                                        endAudioLocation = 0;
                                        lastActivityDataHandler.postDelayed(playExtraEndAudio,
                                                mAudioDuration + 20);
                                        mAudioDuration +=
                                                getAudioDuration("app_audio_finished_level") + 20;
                                        mAudioDuration +=
                                                getAudioDuration("app_audio_next_level") + 20;
                                    }
                                    playFeedBackAudio("app_audio_get_medal");
                                }
                            }
                        } else {
                            Log.d(Constants.LOGCAT, "app_audio_almost_there");
                            mAudioDuration = playFeedBackAudio("app_audio_almost_there");
                        }
                    }else{
                        mAudioDuration=playFeedBackAudio("app_audio_get_medal");
                    }
                    lastActivityDataHandler.postDelayed(returnToActivities_Platorm, mAudioDuration);
                    if(mCursor != null ) {
                        mCursor.close();
                    }
                    break;
                case 1:
                    Log.d(Constants.LOGCAT,"before ");
                    moveBackwords();
                    break;
            }//end switch(lastPagePosition){
        }
    };//end private Runnable returnToActivitiesPage = new Runnable(){

}