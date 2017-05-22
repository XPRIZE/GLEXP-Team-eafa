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
import android.widget.AdapterView;
import android.widget.GridView;
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
 * Created by aborsay on 5/5/2017.
 */
public class LearnToSpell extends ActivitiesMasterParent  implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private ArrayList<HashMap<String,String>> mAllWords;

    private boolean mAllowLetter;
    private String mGuessWord;
    private ArrayList<HashMap<String,String>> mSeparateLetters;
    private String[] mLocationOfChar;
    private HashMap<String, String> mCurrentWord;
    private int mProcessWordPosition;

    private boolean mRunningGather=true;
    private boolean mIsImage;
    private int mLowerCase=1;
    private LearnToSpellLetters mAdapter;
    private HashMap<String,String> mLetterInfo;
    private ArrayList<HashMap<String,String>> mCurrentLetters;
    private boolean mPlayingAudio=false;
    private final static int VALIDATE_TIME=1200;
    private ArrayList<Integer> mLocationOfCharacter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.learn_to_spell);
        appData.addToClassOrder(4);
        appData.setActivityType(2
        );
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
        ((ImageView) findViewById(R.id.btnValidate))
                .setImageResource(R.drawable.btn_validate_off);
        mValidateAvailable = false;
        mGuessWord="";
        mAllowLetter = false;
        findViewById(R.id.lettersGrid).setAlpha(0.3f);
        findViewById(R.id.backspace).setAlpha(0.3f);
        roundNumber=0;
        mSeparateLetters = new ArrayList<>();
        ((TextView) findViewById(R.id.wordText)).setText("");
        ((TextView) findViewById(R.id.wordText)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.wordActual)).setText("");
        mInstructionAudio="info_lt05";
        setUpListeners();
        getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_LETTERS, null, this);
    }





    private void displayScreen(Cursor mCursor){
        mRunningGather=false;

        mCurrentLetters=new ArrayList<>();
        int mNumber=0;
        if (mCursor.moveToFirst()) {
            do {
                mCurrentLetters.add(new HashMap<String, String>());
                mCurrentLetters.get(mNumber).put("letter_id",
                        mCursor.getString(mCursor.getColumnIndex("letter_id")));       //0
                mCurrentLetters.get(mNumber).put("letter_text",
                        mCursor.getString(mCursor.getColumnIndex("letter_text")));     //1
                mCurrentLetters.get(mNumber).put("letter_is_vowel",
                        mCursor.getString(mCursor.getColumnIndex("letter_is_vowel")));  //2
                mCurrentLetters.get(mNumber).put("audio_url",
                        mCursor.getString(mCursor.getColumnIndex("audio_url")));    //3
                mCurrentLetters.get(mNumber).put("level_color",
                        mCursor.getString(mCursor.getColumnIndex("level_color")));    //4
                mCurrentLetters.get(mNumber).put("level_number",
                        mCursor.getString(mCursor.getColumnIndex("level_number")));    //5
                mCurrentLetters.get(mNumber).put("current_level",
                        mCurrentGSP.get("current_level"));    //6

                mNumber++;
            } while (mCursor.moveToNext());
        }


        mAdapter= new LearnToSpellLetters(this, mCurrentLetters);

        GridView mLetterGrid = (GridView) findViewById(R.id.lettersGrid);
        if(mCurrentLetters.size()>24){
            mLetterGrid.setNumColumns(9);
        }else{
            mLetterGrid.setNumColumns(8);
        }
        mLetterGrid.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mPlayingAudio=false;
        mLetterGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                mLetterInfo = new HashMap<>
                        ((HashMap) v.findViewById(R.id.grid_item_text).getTag());
                if (mGuessWord.length() <= 9 && mAllowLetter) {
                    mSeparateLetters.add(mLetterInfo);
                    mGuessWord += mLetterInfo.get("letter_text");

                    findViewById(R.id.backspace).setAlpha(1.0f);
                    if(mGuessWord.length()==9){
                        findViewById(R.id.lettersGrid).setAlpha(0.3f);
                    }
                    ((TextView) findViewById(R.id.wordText)).setText(mGuessWord);
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_on);
                    mValidateAvailable = true;
                }

            }
        });


        getLoaderManager().restartLoader(Constants.CURRENT_WORDS, null, this);
    }


    private Runnable allowValidate = new Runnable() {
        @Override
        public void run() {
            mBeingValidated=false;
        }
    };

    protected void validate() {
        mBeingValidated = true;

        mCorrect = (mGuessWord.trim().equals(mCurrentWord.get("word_text").trim())) ? true : false;

        if (mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            ((TextView) findViewById(R.id.wordText))
                    .setTextColor(ContextCompat.getColor(this,R.color.correct_green));
        } else {
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_no_ok);
            ((TextView) findViewById(R.id.wordText))
                    .setTextColor(ContextCompat.getColor(this,R.color.incorrect_red));
        }
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);

    }
    protected void setUpListeners() {

        findViewById(R.id.wordImage).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mBeingValidated) {
                    mProcessGuessPosition=0;
                    if(!mIsImage){
                        ((ImageView) findViewById(R.id.wordImage))
                                .setImageResource(R.drawable.bird_no_image_left_open);
                    }
                    mProcessWordPosition=0;
                    mAudioHandler.postDelayed(playCurrentWordAudio, 20);
                }
            }
        });


        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mValidateAvailable && !mBeingValidated) {
                    playClickSound();
                    validate();
                }
            }
        });
    }

    /**
     * plays current audio for word to spell. It has two cycles in order to show a bird opening and
     * closing to speak if no image for word and to set variable to allow user to validate.
     */
    private Runnable playCurrentWordAudio = new Runnable(){

        @Override
        public void run(){
            switch(mProcessWordPosition){
                default:
                case 0:
                    mAudioHandler.removeCallbacks(playCurrentWordAudio);
                    long mAudioDuration = 0;
                    if(!mCurrentWord.get("audio_url").equals("")) {
                        mAudioDuration= playGeneralAudio(mCurrentWord.get("audio_url"));
                    }
                    mProcessWordPosition++;
                    mAudioHandler.postDelayed(playCurrentWordAudio, mAudioDuration+10);
                    break;
                case 1:
                    mAudioHandler.removeCallbacks(playCurrentWordAudio);
                    mBeingValidated=false;
                    mProcessWordPosition=0;
                    if(!mIsImage){
                        ((ImageView) findViewById(R.id.wordImage))
                                .setImageResource(R.drawable.bird_no_image_left_close);
                    }
                    break;
            }

        }
    };


    /**
     * clears the currently typed word, if user wants to retype word. This is called from
     * restart image in layout.
     * @param view is restart image (btn_calc_refresh)
     */
    public void restartWord(View view){
        mSeparateLetters = new ArrayList<>();
        findViewById(R.id.lettersGrid).setAlpha(1.0f);
        mGuessWord ="";
        ((TextView) findViewById(R.id.wordText)).setText("");
    }


    /**
     * Deletes most recent letter in spelling section. If no letters
     * @param view is back arrow image (btn_calc_arrow)
     */
    public void deleteLetter(View view){
        //Makes sure mGuessWord has text
        if (mGuessWord!="" && !mBeingValidated && mGuessWord.length()>0) {
            mBeingValidated=true;
            int mLocation=mSeparateLetters.size()-1;


            int endIndex = mGuessWord.lastIndexOf(mSeparateLetters
                    .get(mLocation).get("letter_text"));
            if (endIndex != -1) {
                mGuessWord = mGuessWord.substring(0, endIndex);
            }
            ((TextView) findViewById(R.id.wordText)).setText(mGuessWord);
            if(mGuessWord.length()<=0) {

                findViewById(R.id.backspace).setAlpha(0.3f);
                ((ImageView) findViewById(R.id.btnValidate))
                        .setImageResource(R.drawable.btn_validate_off);
                mValidateAvailable = false;
            }
            if(mGuessWord.length()<10){
                findViewById(R.id.lettersGrid).setAlpha(1.0f);
            }

            mSeparateLetters.remove(mLocation);

        }else{
            Log.d(Constants.LOGCAT,"no more to delete");
        }

        validateHandler.postDelayed(allowValidate, VALIDATE_TIME);
    }



    @Override
    protected void processData(Cursor mCursor) {
        super.processData(mCursor);
        mAllWords=new ArrayList<>();
        int mCurrentWordNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mAllWords.add(new HashMap<String, String>());
            mAllWords.get(mCurrentWordNumber).put("word_id",
                    mCursor.getString(mCursor.getColumnIndex("word_id")));
            mAllWords.get(mCurrentWordNumber).put("word_text",
                    mCursor.getString(mCursor.getColumnIndex("word_text")));
            mAllWords.get(mCurrentWordNumber).put("audio_url",
                    mCursor.getString(mCursor.getColumnIndex("audio_url")));
            mAllWords.get(mCurrentWordNumber).put("word_separation",
                    mCursor.getString(mCursor.getColumnIndex("word_separation")));
            mAllWords.get(mCurrentWordNumber).put("image_url",
                    mCursor.getString(mCursor.getColumnIndex("image_url")));

            mCurrentWordNumber++;
            if (mCurrentWordNumber == Constants.NUMBER_VARIABLES) {
                break;
            }else if(mCursor.getCount()<8 && mCurrentWordNumber>=mCursor.getCount()){
                break;
            }else if(mCursor.isLast()){
                mCursor.moveToFirst();
            }else if(mCursor.getCount()>=8 && mCurrentWordNumber==9) {
                mCursor.moveToFirst();

            }else {
                mCursor.moveToNext();
            }
        }

        Collections.shuffle(mAllWords);
        beginRound();

    }


    @Override
    protected void beginRound() {
        super.beginRound();
        mGuessWord="";


        findViewById(R.id.lettersGrid).setAlpha(1.0f);
       mAllowLetter=true;


        ((TextView) findViewById(R.id.wordText)).setText("");
        mValidateAvailable=false;
        mCurrentWord = new HashMap<>(mAllWords.get(roundNumber));

        ((TextView) findViewById(R.id.wordActual)).setText(mCurrentWord.get("word_text"));
        mGuessWord="";

        mBeingValidated=false;
        displayImage();
        mProcessWordPosition=0;
        mAudioHandler.postDelayed(playCurrentWordAudio, 20);
    }


    private void displayImage(){
        mIsImage=false;
        if(!mCurrentWord.get("image_url").equals("")) {

            try {
                String imageName = mCurrentWord.get("image_url").replace(".jpg", "")
                        .replace(".png", "").trim();
                int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
                ((ImageView) findViewById(R.id.wordImage)).setImageResource(resID);
                mIsImage=true;
            } catch (Exception e) {
                ((ImageView) findViewById(R.id.wordImage)).setImageResource(R.drawable.blank_image);
                Log.e("MyTag", "Failure to get drawable id.", e);
            }
        }else{
            ((ImageView) findViewById(R.id.wordImage))
                    .setImageResource(R.drawable.bird_no_image_left_close);
            mIsImage=false;
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        String selection;
        String[] projection;
        switch(id){
            default:
            case Constants.ACTIVITY_CURRENT_LETTERS:
                mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());



                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        "2",
                        "1",
                        mCurrentGSP.get("section_id"),
                        "999",
                        mCurrentGSP.get("current_level")
                };

                selection="variable_phase_levels.group_id=2 " +
                        "AND variable_phase_levels.phase_id=1 " +
                        "AND variable_phase_levels.level_number <= 24";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ACTIVITY_CURRENT_LETTERS,
                        projection, selection, null, null);

                break;
            case  Constants.CURRENT_WORDS:


                ArrayList<HashMap<String,String>> mAllGPL = appData.getAppUserCurrentGPL();
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(), "5", "1", "11",
                        mCurrentGSP.get("current_level"),"0"};

                selection="variable_phase_levels.group_id=5"+
                        " AND variable_phase_levels.phase_id=1" +
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
                        " AND variable_phase_levels.level_number>0 ";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_WORDS_BY_LEVEL,
                        projection, selection, null, null);
                break;
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
            case Constants.ACTIVITY_CURRENT_WRDS_LTRS:
                displayScreen(data);
                break;
            case Constants.CURRENT_WORDS:
                processData(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }



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
                    //processPointsAndView();
                    mAudioDuration=0;
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                case 2:
                    guessHandler.removeCallbacks(processGuess);
                    if(mCorrect){
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        roundNumber++;
                        if(roundNumber<(mAllWords.size())){
                            mValidateAvailable=false;
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
                        }else{
                            mLastActivityData=0;
                            fadeInOrOutScreenInActivity(false);

                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{

                        for(int i=0; i<mSeparateLetters.size(); i++) {
                            mSeparateLetters.get(i).put("guessed", "0");
                        }
                        mGuessWord="";
                        ((TextView) findViewById(R.id.wordText))
                                .setTextColor(ContextCompat.getColor(
                                        LearnToSpell.this,R.color.regularBlack));
                        ((TextView) findViewById(R.id.wordText)).setText("");

                        findViewById(R.id.lettersGrid).setAlpha(1.0f);
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                        mProcessWordPosition=0;
                        mAudioHandler.postDelayed(playCurrentWordAudio, 20);
                    }

                    break;
                case 3:
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };
}
