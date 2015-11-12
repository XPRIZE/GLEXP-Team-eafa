package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
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


public class Activity_LT06 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{



    private int currentTextLocation=0;
    private int incorrectInRound=0;

    private String correctText="";
    private String mCorrectId;

    private ArrayList<ArrayList<String>> mCurrentText;
    private ArrayList<ArrayList<String>> mAllText;
    
    private ArrayList<ArrayList<String>> mCurrentWords;

    private ArrayList<ArrayList<ArrayList<String>>> allActivityText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_lt06);
        appData.addToClassOrder(5);
        allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

        beingValidated=true;

        clearActivity();
        setUpListeners();
        setupFrameListens();

        ((TextView) findViewById(R.id.Activity_Text0)).setTypeface(appData.getCurrentFontType());


        getLoaderManager().initLoader(MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);
        ///Uri todoUri = MotoliContentProvider.CONTENT_URI_WRD_LTR_ACTIVITIES;

    }//end public void onCreate(Bundle savedInstanceState) {


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private long playInstructionAudio(){
        return playGeneralAudio("info_lt06.mp3");
    }
    private void clearActivity(){
        findViewById(R.id.Activity_LoadingCircle1).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.Activity_LoadingCircle2).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.Activity_LoadingCircle3).setVisibility(ProgressBar.VISIBLE);
        findViewById(R.id.Activity_LoadingCircle4).setVisibility(ProgressBar.VISIBLE);



        ((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank_image);
        ((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank_image);
        ((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank_image);
        ((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank_image);

        findViewById(R.id.Activity_Image1).setBackgroundResource(R.drawable.rounded_border_unchosen);
        findViewById(R.id.Activity_Image2).setBackgroundResource(R.drawable.rounded_border_unchosen);
        findViewById(R.id.Activity_Image3).setBackgroundResource(R.drawable.rounded_border_unchosen);
        findViewById(R.id.Activity_Image4).setBackgroundResource(R.drawable.rounded_border_unchosen);

        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_off);

        clearFrames();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){
        incorrectInRound=0;

        mCurrentText = new ArrayList<ArrayList<String>>(allActivityText.get(roundNumber));
        Collections.shuffle(mCurrentText);

        for(int i=0;i<4;i++){

            if(mCurrentText.get(i).get(0).equals("1")){
                correctLocation=i;
                correctText=mCurrentText.get(i).get(2);
                mCorrectId=mCurrentText.get(i).get(1);
                matchingLtrWrdAudio=mCurrentText.get(i).get(3);
            }

            processPlacingImage(i);
        }

        ((TextView) findViewById(R.id.Activity_Text0)).setText(correctText);
        findViewById(R.id.Activity_LoadingCircle0).setVisibility(ProgressBar.INVISIBLE);


        long audioDuration=0;
        if(roundNumber==0)
            audioDuration=playInstructionAudio();

        audioHandler.postDelayed(playLtrAudio, audioDuration+20);

    }//end private void displayScreen(Cursor mCursor){

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void processPlacingImage(int wordNumber){
        //Class<drawable> res = R.drawable.class;
        //int drawableId = 0;
        //Field field;

        try{
            String imageName=mCurrentText.get(wordNumber).get(4).replace(".jpg", "").replace(".png", "").trim();
            //field = res.getField(imageName);
            //drawableId = field.getInt(null);

            int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());

            switch(wordNumber){
                default:
                case 0:{
                    findViewById(R.id.Activity_LoadingCircle1).setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.Activity_Image1).setTag(mCurrentText.get(wordNumber).get(0));
                    ((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(resID);
                    break;
                }
                case 1:{
                    findViewById(R.id.Activity_LoadingCircle2).setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.Activity_Image2).setTag(mCurrentText.get(wordNumber).get(0));
                    ((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(resID);
                    break;
                }
                case 2:{
                    findViewById(R.id.Activity_LoadingCircle3).setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.Activity_Image3).setTag(mCurrentText.get(wordNumber).get(0));
                    ((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(resID);
                    break;
                }
                case 3:{
                    findViewById(R.id.Activity_LoadingCircle4).setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.Activity_Image4).setTag(mCurrentText.get(wordNumber).get(0));
                    ((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(resID);
                    break;
                }
            }

        }catch (Exception e) {
            Log.e("MyTag", "Failure to get drawable id.", e);
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playLtrAudio = new Runnable(){

        @Override
        public void run(){
            audioHandler.removeCallbacks(playLtrAudio);
            beingValidated=false;

        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //end private void displayScreen(Cursor mCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens(){
        findViewById(R.id.Activity_Image1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(0).get(7).equals("0") && !beingValidated){
                    currentTextLocation=0;
                    currentLtrWrdAudio=mCurrentText.get(0).get(3);
                    currentWordID=mCurrentText.get(0).get(1);
                    //playGeneralAudio(currentLtrWrdAudio);
                    correctChoice=mCurrentText.get(0).get(0).equals("1");
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.Activity_Image2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(1).get(7).equals("0") && !beingValidated){
                    currentTextLocation=1;
                    currentLtrWrdAudio=mCurrentText.get(1).get(3);
                    currentWordID=mCurrentText.get(1).get(1);
                    //playGeneralAudio(currentLtrWrdAudio);
                    correctChoice=mCurrentText.get(1).get(0).equals("1");
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.Activity_Image3).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(2).get(7).equals("0") && !beingValidated){
                    currentTextLocation=2;
                    currentLtrWrdAudio=mCurrentText.get(2).get(3);
                    currentWordID=mCurrentText.get(2).get(1);
                    //playGeneralAudio(currentLtrWrdAudio);
                    correctChoice=mCurrentText.get(2).get(0).equals("1");
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.Activity_Image4).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(mCurrentText.get(3).get(7).equals("0") && !beingValidated){
                    currentTextLocation=3;
                    currentLtrWrdAudio=mCurrentText.get(3).get(3);
                    currentWordID=mCurrentText.get(3).get(1);
                    //playGeneralAudio(currentLtrWrdAudio);
                    correctChoice=mCurrentText.get(3).get(0).equals("1");
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
                ((ImageView)findViewById(R.id.Activity_ImageFrame1)).setImageResource(R.drawable.frm_lt06_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.Activity_ImageFrame2)).setImageResource(R.drawable.frm_lt06_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.Activity_ImageFrame3)).setImageResource(R.drawable.frm_lt06_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.Activity_ImageFrame4)).setImageResource(R.drawable.frm_lt06_on);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.Activity_ImageFrame1)).setImageResource(R.drawable.frm_lt06_off);
        ((ImageView)findViewById(R.id.Activity_ImageFrame2)).setImageResource(R.drawable.frm_lt06_off);
        ((ImageView)findViewById(R.id.Activity_ImageFrame3)).setImageResource(R.drawable.frm_lt06_off);
        ((ImageView)findViewById(R.id.Activity_ImageFrame4)).setImageResource(R.drawable.frm_lt06_off);


    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void validate(){
        beingValidated=true;
        if(correctChoice) {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_ok);
        }else{
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
        }
        processGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processPointsAndView(){
        String[] selectionArgs;
        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

        if(correctChoice){
            //processLayoutAfterGuess(true);
            //correctWordCount++;
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
            switch(currentTextLocation){
                default:
                case 0:
                    mCurrentText.get(0).add(7, "1");
                    findViewById(R.id.Activity_Image1).setBackgroundResource(R.drawable.rounded_border_correct);
                    findViewById(R.id.Activity_Image4).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    findViewById(R.id.Activity_Image2).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    findViewById(R.id.Activity_Image3).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    ((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank_image);

                    ((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank_image);
                    ((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank_image);
                    break;
                case 1:
                    mCurrentText.get(1).add(7, "1");
                    findViewById(R.id.Activity_Image2).setBackgroundResource(R.drawable.rounded_border_correct);
                    findViewById(R.id.Activity_Image1).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    findViewById(R.id.Activity_Image4).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    findViewById(R.id.Activity_Image3).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    ((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank_image);
                    ((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank_image);
                    ((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank_image);
                    break;
                case 2:
                    mCurrentText.get(2).add(7, "1");
                    findViewById(R.id.Activity_Image3).setBackgroundResource(R.drawable.rounded_border_correct);
                    findViewById(R.id.Activity_Image1).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    findViewById(R.id.Activity_Image2).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    findViewById(R.id.Activity_Image4).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    ((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank_image);
                    ((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank_image);
                    ((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank_image);
                    break;
                case 3:
                    mCurrentText.get(3).add(7, "1");
                    findViewById(R.id.Activity_Image4).setBackgroundResource(R.drawable.rounded_border_correct);
                    findViewById(R.id.Activity_Image1).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    findViewById(R.id.Activity_Image2).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    findViewById(R.id.Activity_Image3).setBackgroundResource(R.drawable.rounded_border_unchosen);
                    ((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank_image);
                    ((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank_image);
                    ((ImageView)findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank_image);
                    break;
            }


        }else{
            incorrectInRound++;
            String where=" WHERE "+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+appData.getCurrentUserID()
                    +" AND "+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="+mCorrectId
                    +" AND "+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+appData.getCurrentActivity().get(0);
            ((ImageView)findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_assess_no_ok);
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
                        mCurrentText.get(1).add(7, "1");
                        mCurrentText.get(2).add(7, "1");
                        mCurrentText.get(3).add(7, "1");
                        ((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank_image);
                        ((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank_image);
                        ((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank_image);
                        break;
                    case 1:
                        mCurrentText.get(0).add(7, "1");
                        mCurrentText.get(2).add(7, "1");
                        mCurrentText.get(3).add(7, "1");
                        ((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank_image);
                        ((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank_image);
                        ((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank_image);
                        break;
                    case 2:
                        mCurrentText.get(1).add(7, "1");
                        mCurrentText.get(0).add(7, "1");
                        mCurrentText.get(3).add(7, "1");
                        ((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank_image);
                        ((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank_image);
                        ((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank_image);
                        break;
                    case 3:
                        mCurrentText.get(1).add(7, "1");
                        mCurrentText.get(2).add(7, "1");
                        mCurrentText.get(0).add(7, "1");
                        ((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank_image);
                        ((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank_image);
                        ((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank_image);
                        break;
                }
            }else{
                switch(currentTextLocation){
                    default:
                    case 0:
                        mCurrentText.get(0).add(7, "1");
                        ((ImageView) findViewById(R.id.Activity_Image1)).setImageResource(R.drawable.blank_image);
                        break;
                    case 1:
                        mCurrentText.get(1).add(7, "1");
                        ((ImageView) findViewById(R.id.Activity_Image2)).setImageResource(R.drawable.blank_image);
                        break;
                    case 2:
                        mCurrentText.get(2).add(7, "1");
                        ((ImageView) findViewById(R.id.Activity_Image3)).setImageResource(R.drawable.blank_image);
                        break;
                    case 3:
                        mCurrentText.get(3).add(7, "1");
                        ((ImageView) findViewById(R.id.Activity_Image4)).setImageResource(R.drawable.blank_image);
                        break;
                }
            }
            //processLayoutAfterGuess(false);

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
                    processPointsAndView();
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
            case MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS:{

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
                        " OR variable_phase_levels.level_number<="+currentGSP.get(3)+"-3)";

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_ACTIVITY_CURRENT_LETTERS_WRW, projection, selection, null, null);
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
             case MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS:{
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
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mCurrentWords.add(new ArrayList<String>());
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("letter_id")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("letter_text")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("audio_url")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("image_url")));
            mCurrentWords.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6

            mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(mCurrentWords.get(currentWordNumber).get(6)))
                    ? Integer.parseInt(mCurrentWords.get(currentWordNumber).get(6)):mNumberCorrectInARow;

            currentWordNumber++;
            if (currentWordNumber == MotoliConstants.NUMBER_VARIABLES) {
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else if(mCursor.getCount()>=8 && currentWordNumber==9) {
                mCursor.moveToFirst();
            }else {
                mCursor.moveToNext();
            }
        }

        if((mNumberCorrectInARow == MotoliConstants.INA_ROW_CORRECT)){

           showCompleteLevelStar(true);
        }else{
            showCompleteLevelStar(false);
        }



        Collections.shuffle(mCurrentWords);

        ArrayList<ArrayList<String>> mTempCurrentWords=new ArrayList<ArrayList<String>>();
        int mTempCurrentWordsPosition=0;

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

        mAllText=new ArrayList<ArrayList<String>>();
        currentWordNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mAllText.add(new ArrayList<String>());
            mAllText.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("letter_id"))); //0
            mAllText.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("letter_text"))); //1
            mAllText.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("audio_url"))); //2
            mAllText.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct"))); //3
            mAllText.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //4
            mAllText.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("image_url"))); //5
            mAllText.get(currentWordNumber).add(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //6
            currentWordNumber++;
            mCursor.moveToNext();
        }
        
        shuffleGameData();
        displayScreen();

    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void shuffleGameData(){

        //Collections.shuffle(mCurrentWords);
        ArrayList<ArrayList<String>> sortedWords=new ArrayList<ArrayList<String>>(mAllText);
        String mLetterID;
        allActivityText.clear();

        for(int currentCount=0; currentCount<mCurrentWords.size();currentCount++){
            allActivityText.add(new ArrayList<ArrayList<String>>());
            allActivityText.get(currentCount).add(new ArrayList<String>());
            allActivityText.get(currentCount).get(0).add("1"); //0

            mLetterID=mCurrentWords.get(currentCount).get(0);

            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(0)); //letter_id 1
            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(1)); //word_text 2
            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(2)); //audio_url 3
            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(5));	//word_image 4
            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(3)); //number correct 5
            allActivityText.get(currentCount).get(0).add(mCurrentWords.get(currentCount).get(4)); //number incorrect 6
            allActivityText.get(currentCount).get(0).add("0"); //guess yet 6


            Collections.shuffle(sortedWords);

            int distCurrentCount=1;
            for(int distCount=0;distCount<4; distCount++){

                if(!sortedWords.get(distCount).get(0).equals(mLetterID) ){

                    for(ArrayList mTemp : allActivityText.get(currentCount) ){
                        if(!sortedWords.get(distCount).get(0).equals(mTemp.get(1))){
                            allActivityText.get(currentCount).add(new ArrayList<String>());
                            allActivityText.get(currentCount).get(distCurrentCount).add("0");
            
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(0)); //mLetterID
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(1)); //word_text
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(2)); //audio_url
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(5));	//word_image
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(3)); //number correct
                            allActivityText.get(currentCount).get(distCurrentCount).add(sortedWords.get(distCount).get(4)); //number incorrect
                            allActivityText.get(currentCount).get(distCurrentCount).add("0"); //guessed yet
                            
                            distCurrentCount++;
                            break;
                        }//  end if(!sortedWords.get(distCount).get(0).equals(mTemp.get(1))){
                    }//end for(ArrayList mTemp : allActivityText.get(currentCount) ){
                if(distCurrentCount>=4){
                    break;
                }
            }//end if(!sortedWords.get(distCount).get(0).equals(mLetterID)){
        }//end for(int distCount=0;distCount<sortedWords.size(); distCount++){


        }//end for(int currentCount=0; currentCount<mCurrentWords.size();currentCount++){


        for(int i=0; i<allActivityText.size(); i++){
            Collections.shuffle(allActivityText.get(i));
        }

    }//end private void shuffleGameData(){


    private void setUpListeners(){
        findViewById(R.id.Activity_CorrectText).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!matchingLtrWrdAudio.equals(""))
                    playGeneralAudio(matchingLtrWrdAudio);
            }
        });


        findViewById(R.id.btnInfo).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                long audioDuration=playInstructionAudio();
                audioHandler.postDelayed(playLtrAudio, audioDuration+20);
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
