package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/28/2015.
 */
public class ActivitySH04 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private int mCurrentLocation=0;
    private int mIncorrectInRound=0;
    private int mRoundNumber=0;

    private boolean mCorrect=false;


    private String mCurrentMathShapeTypeId;
    private String mCurrentMathShapeSize;
    private String mCurrentMathShapeTypeAudio;
    private String mLanguage;

    private String mCurrentCorrectId;


    private ArrayList<HashMap<String,String>> mCurrentShapeSet;
    private ArrayList<HashMap<String,String>> mCurrentShapeRound;
    private ArrayList<HashMap<String,String>> mGuessingShapes;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sh04);
        appData.addToClassOrder(5);
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        mInstructionAudio="info_sh04";
        mBeingValidated=true;

        clearActivity();
        hideShapes();
        setUpListeners();
        setupFrameListens();

        getLoaderManager().initLoader(Constants.CURRENT_MATH_SHAPES, null, this);

    }//end public void onCreate(Bundle savedInstanceState) {


    ////////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration=super.playInstructionAudio();
        mAudioHandler.postDelayed(playShapeAudio, mAudioDuration+20);
        return mAudioDuration;
    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){





        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);

        clearFrames();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected void beginRound(){
        mIncorrectInRound=0;
        mGuessingShapes=new ArrayList<>();
        mCurrentShapeRound=new ArrayList<>();
        mCurrentShapeRound.add(mCurrentShapeSet.get(mRoundNumber));
        mCurrentMathShapeSize=mCurrentShapeRound.get(0).get("math_shape_size");
        mCurrentMathShapeTypeId=mCurrentShapeRound.get(0).get("math_shape_type_id");
        mCurrentMathShapeTypeAudio=mCurrentShapeRound.get(0).get("math_shape_type_audio");
        String mSubString[]=mCurrentShapeRound.get(0).get("math_shape_audio").split("_");

        mLanguage=mSubString[0];
        mCurrentCorrectId=mCurrentShapeRound.get(0).get("math_shape_id");
        placingCurrentShapes(4);
        getLoaderManager().restartLoader(Constants.MATHCING_MATH_SHAPE, null, this);

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void finishBeginRound(Cursor mCursor){
        int mExtraCount=0;
        if(mCursor.moveToFirst()){
            do{
                mExtraCount++;
                mGuessingShapes.add(new HashMap<String, String>());
                mGuessingShapes.get(mExtraCount).put("correct_guess","0");
                mGuessingShapes.get(mExtraCount).put("guessed_yet","0");
                mGuessingShapes.get(mExtraCount).put("math_shape_id",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_id"))); //0
                mGuessingShapes.get(mExtraCount).put("math_shape_code",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_code"))); //1
                mGuessingShapes.get(mExtraCount).put("math_shape_type_id",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_type_id"))); //2
                mGuessingShapes.get(mExtraCount).put("math_shape_color_id",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_color_id"))); //3
                mGuessingShapes.get(mExtraCount).put("math_shape_size",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_size"))); //4
                mGuessingShapes.get(mExtraCount).put("math_shape_kind",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_kind"))); //5
                mGuessingShapes.get(mExtraCount).put("math_shape_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_audio"))); //6
                mGuessingShapes.get(mExtraCount).put("math_shape_image",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_image"))); //7
                mGuessingShapes.get(mExtraCount).put("number_correct",
                        mCursor.getString(mCursor.getColumnIndex("number_correct"))); //8
                mGuessingShapes.get(mExtraCount).put("number_correct_in_a_row",
                        mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //9
                mGuessingShapes.get(mExtraCount).put("number_incorrect",
                        mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //10
                mGuessingShapes.get(mExtraCount).put("math_shape_type_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_type_audio")));
                if(mExtraCount>=3){
                    break;
                }
            }while(mCursor.moveToNext());
        }


        displayScreen();

    }
    /////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        Collections.shuffle(mGuessingShapes);

        for (int i = 0; i < 4; i++) {
            if (mGuessingShapes.get(i).get("correct_guess").equals("1")) {
                mCorrectLocation = i;
            }
            placingCurrentShapes(i);
        }







        long mAudioDuration=0;
        if(mRoundNumber==0)
            mAudioDuration=playInstructionAudio();

        mAudioHandler.postDelayed(playShapeAudio, mAudioDuration + 20);

    }//end private void displayScreen(Cursor currentWordsCursor){

    //////////////////////////////////////////////////////////////////////////////////////////

    private void placingCurrentShapes(int shapeID){
        CanvasView mCustomCanvas;
        switch(shapeID){
            default:
            case 0:{
                findViewById(R.id.shape1).setVisibility(CanvasView.VISIBLE);
                mCustomCanvas  = (CanvasView) findViewById(R.id.shape1);

                break;
            }
            case 1:{
                findViewById(R.id.shape2).setVisibility(CanvasView.VISIBLE);
                mCustomCanvas  = (CanvasView) findViewById(R.id.shape2);

                break;
            }
            case 2:{
                findViewById(R.id.shape3).setVisibility(CanvasView.VISIBLE);
                mCustomCanvas  = (CanvasView) findViewById(R.id.shape3);

                break;
            }
            case 3:{
                findViewById(R.id.shape4).setVisibility(CanvasView.VISIBLE);
                mCustomCanvas  = (CanvasView) findViewById(R.id.shape4);

                break;
            }
            case 4:{
                findViewById(R.id.shape0).setVisibility(CanvasView.VISIBLE);
                mCustomCanvas  = (CanvasView) findViewById(R.id.shape0);
                break;
            }
        }

        mCustomCanvas.clearCanvas();

        RelativeLayout.LayoutParams mParams
                =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

       // int mShapeMargin=getResources().getDimensionPixelSize(R.dimen.shape_margin_small);
        int mShapeMargin;

        if(shapeID==4){
            mShapeMargin=getResources().getDimensionPixelSize(R.dimen.top_shape_margin_small);
            if(mCurrentShapeRound.get(0).get("math_shape_size").equals("4")){
                mShapeMargin=getResources().getDimensionPixelSize(R.dimen.top_shape_margin_large);
            }
        }else{
            mShapeMargin=getResources().getDimensionPixelSize(R.dimen.shape_margin_small);
            if(mGuessingShapes.get(shapeID).get("math_shape_size").equals("4")){
                mShapeMargin=getResources().getDimensionPixelSize(R.dimen.shape_margin_large);
            }
        }
        mParams.setMargins(mShapeMargin, mShapeMargin,mShapeMargin,mShapeMargin);

        mCustomCanvas.setLayoutParams(mParams);

        if(shapeID==4) {
            mCustomCanvas.setType(Integer.parseInt(mCurrentShapeRound.get(0)
                    .get("math_shape_type_id")));
            mCustomCanvas.setColor(Integer.parseInt(mCurrentShapeRound.get(0)
                    .get("math_shape_color_id")));
        }else{
            mCustomCanvas.setType(Integer.parseInt(mGuessingShapes.get(shapeID)
                    .get("math_shape_type_id")));
            mCustomCanvas.setColor(Integer.parseInt(mGuessingShapes.get(shapeID)
                    .get("math_shape_color_id")));
        }
        mCustomCanvas.invalidate();


    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playShapeAudio = new Runnable(){

        @Override
        public void run() {
            mAudioHandler.removeCallbacks(playShapeAudio);
            mBeingValidated = false;
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////


    protected void setupFrameListens(){

        findViewById(R.id.frame1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mGuessingShapes.get(0).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=0;
                    mCorrect=(mGuessingShapes.get(0).get("correct_guess").equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.frame2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mGuessingShapes.get(1).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 1;
                    mCorrect = (mGuessingShapes.get(1).get("correct_guess").equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.frame3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mGuessingShapes.get(2).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=2;
                    mCorrect=(mGuessingShapes.get(2).get("correct_guess").equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.frame4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mGuessingShapes.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 3;
                    mCorrect = (mGuessingShapes.get(3).get("correct_guess").equals("1"));
                    setUpFrame(3);
                }
            }
        });

        findViewById(R.id.frame1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mGuessingShapes.get(0).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=0;
                    mCorrect=(mGuessingShapes.get(0).get("correct_guess").equals("1"));
                    setUpFrame(0);
                }
            }
        });

        findViewById(R.id.frame2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mGuessingShapes.get(1).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 1;
                    mCorrect = (mGuessingShapes.get(1).get("correct_guess").equals("1"));
                    setUpFrame(1);
                }
            }
        });

        findViewById(R.id.frame3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mGuessingShapes.get(2).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=2;
                    mCorrect=(mGuessingShapes.get(2).get("correct_guess").equals("1"));
                    setUpFrame(2);
                }
            }
        });

        findViewById(R.id.frame4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mGuessingShapes.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 3;
                    mCorrect = (mGuessingShapes.get(3).get("correct_guess").equals("1"));
                    setUpFrame(3);
                }
            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        mValidateAvailable=true;

        switch(frameNumber){
            default:
            case 0:{
                ((ImageView)findViewById(R.id.frame1))
                        .setImageResource(R.drawable.frm_sh04_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.frame2))
                        .setImageResource(R.drawable.frm_sh04_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.frame3))
                        .setImageResource(R.drawable.frm_sh04_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.frame4))
                        .setImageResource(R.drawable.frm_sh04_on);
                break;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private void hideShapes(){
        findViewById(R.id.shape1).setVisibility(CanvasView.INVISIBLE);
        findViewById(R.id.shape2).setVisibility(CanvasView.INVISIBLE);
        findViewById(R.id.shape3).setVisibility(CanvasView.INVISIBLE);
        findViewById(R.id.shape4).setVisibility(CanvasView.INVISIBLE);
    }
    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_sh04_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_sh04_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_sh04_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_sh04_off);


    }
    ////////////////////////////////////////////////////////////////////////////////////////////

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
        processChoiceView();
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void processFontColorAndPoints(){
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mCurrentCorrectId
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;

        if(mCorrect){
            ((ImageView) findViewById(R.id.btnValidate))
                    .setImageResource(R.drawable.btn_validate_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mCurrentCorrectId,
                    String.valueOf(mIncorrectInRound), //4
                    appData.getCurrentActivity().get(0), //5
                    appData.getCurrentUserID()}; //6


            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE_MS,
                    null, where, selectionArgs);
            switch(mCurrentLocation){
                default:
                case 0:
                    mGuessingShapes.get(0).put("guessed_yet", "1");
                    break;
                case 1:
                    mGuessingShapes.get(1).put("guessed_yet", "1");
                    break;
                case 2:
                    mGuessingShapes.get(2).put("guessed_yet", "1");
                    break;
                case 3:
                    mGuessingShapes.get(3).put("guessed_yet", "1");
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
                    mCurrentCorrectId,
                    String.valueOf(mIncorrectInRound), //4
                    appData.getCurrentActivity().get(0), //5
                    appData.getCurrentUserID()}; //6

            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE_MS,
                    null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        mGuessingShapes.get(1).put("guessed_yet", "1");
                        mGuessingShapes.get(2).put("guessed_yet", "1");
                        mGuessingShapes.get(3).put("guessed_yet", "1");
                        break;
                    case 1:
                        mGuessingShapes.get(0).put("guessed_yet", "1");
                        mGuessingShapes.get(2).put("guessed_yet", "1");
                        mGuessingShapes.get(3).put("guessed_yet", "1");
                        break;
                    case 2:
                        mGuessingShapes.get(1).put("guessed_yet", "1");
                        mGuessingShapes.get(0).put("guessed_yet", "1");
                        mGuessingShapes.get(3).put("guessed_yet", "1");
                        break;
                    case 3:
                        mGuessingShapes.get(1).put("guessed_yet", "1");
                        mGuessingShapes.get(2).put("guessed_yet", "1");
                        mGuessingShapes.get(0).put("guessed_yet", "1");
                        break;
                }
            }
            switch(mCurrentLocation){
                default:
                case 0:
                    mGuessingShapes.get(0).put("guessed_yet", "1");
                    break;
                case 1:
                    mGuessingShapes.get(1).put("guessed_yet", "1");
                    break;
                case 2:
                    mGuessingShapes.get(2).put("guessed_yet", "1");
                    break;
                case 3:
                    mGuessingShapes.get(3).put("guessed_yet", "1");
                    break;
            }


        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    private void processChoiceView(){
        if(mCorrect){
            switch(mCorrectLocation){
                default:
                case 0:
                    findViewById(R.id.shape2).setVisibility(CanvasView.INVISIBLE);
                    findViewById(R.id.shape3).setVisibility(CanvasView.INVISIBLE);
                    findViewById(R.id.shape4).setVisibility(CanvasView.INVISIBLE);

                    break;
                case 1:
                    findViewById(R.id.shape1).setVisibility(CanvasView.INVISIBLE);
                    findViewById(R.id.shape3).setVisibility(CanvasView.INVISIBLE);
                    findViewById(R.id.shape4).setVisibility(CanvasView.INVISIBLE);
                    break;
                case 2:
                    findViewById(R.id.shape2).setVisibility(CanvasView.INVISIBLE);
                    findViewById(R.id.shape1).setVisibility(CanvasView.INVISIBLE);
                    findViewById(R.id.shape4).setVisibility(CanvasView.INVISIBLE);
                    break;
                case 3:
                    findViewById(R.id.shape2).setVisibility(CanvasView.INVISIBLE);
                    findViewById(R.id.shape3).setVisibility(CanvasView.INVISIBLE);
                    findViewById(R.id.shape1).setVisibility(CanvasView.INVISIBLE);
                    break;
            }
        }else{
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        findViewById(R.id.shape2).setVisibility(CanvasView.INVISIBLE);
                        findViewById(R.id.shape3).setVisibility(CanvasView.INVISIBLE);
                        findViewById(R.id.shape4).setVisibility(CanvasView.INVISIBLE);
                        break;
                    case 1:
                        findViewById(R.id.shape1).setVisibility(CanvasView.INVISIBLE);
                        findViewById(R.id.shape3).setVisibility(CanvasView.INVISIBLE);
                        findViewById(R.id.shape4).setVisibility(CanvasView.INVISIBLE);
                        break;
                    case 2:
                        findViewById(R.id.shape2).setVisibility(CanvasView.INVISIBLE);
                        findViewById(R.id.shape1).setVisibility(CanvasView.INVISIBLE);
                        findViewById(R.id.shape4).setVisibility(CanvasView.INVISIBLE);
                        break;
                    case 3:
                        findViewById(R.id.shape2).setVisibility(CanvasView.INVISIBLE);
                        findViewById(R.id.shape3).setVisibility(CanvasView.INVISIBLE);
                        findViewById(R.id.shape1).setVisibility(CanvasView.INVISIBLE);
                        break;
                }
            }

            switch(mCurrentLocation){
                default:
                case 0:
                    findViewById(R.id.shape1).setVisibility(CanvasView.VISIBLE);
                    break;
                case 1:
                    findViewById(R.id.shape2).setVisibility(CanvasView.VISIBLE);
                    break;
                case 2:
                    findViewById(R.id.shape3).setVisibility(CanvasView.VISIBLE);
                    break;
                case 3:
                    findViewById(R.id.shape4).setVisibility(CanvasView.VISIBLE);
                    break;
            }
            //processLayoutAfterGuess(false);

        }
    }

    private void clearWrong(){
        switch(mCurrentLocation){
            default:
            case 0:
                findViewById(R.id.shape1).setVisibility(CanvasView.INVISIBLE);
                break;
            case 1:
                findViewById(R.id.shape2).setVisibility(CanvasView.INVISIBLE);
                break;
            case 2:
                findViewById(R.id.shape3).setVisibility(CanvasView.INVISIBLE);
                break;
            case 3:
                findViewById(R.id.shape4).setVisibility(CanvasView.INVISIBLE);
                break;
        }
    }

    private Runnable processGuess = new Runnable(){

        //mBeingValidated=false;


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
                    if(mCorrect){
                        mAudioDuration=playGeneralAudio(mLanguage + "_mathshape_typeid_a");
                    }else{
                        mAudioDuration=playGeneralAudio(mLanguage+"_mathshape_typeid_b");
                    }
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                case 2:
                    mAudioDuration=playGeneralAudio(mCurrentMathShapeTypeAudio);

                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                case 3:
                    clearWrong();
                    guessHandler.removeCallbacks(processGuess);

                    clearFrames();
                    if(mCorrect){
                        mRoundNumber++;
                        hideShapes();
                        if(mRoundNumber!=(mCurrentShapeSet.size())){
                            //mBeingValidated=false;
                            mValidateAvailable=false;
                            clearActivity();
                            mProcessGuessPosition++;
                            guessHandler.postDelayed(processGuess, 500);
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
                        ((ImageView) findViewById(R.id.btnValidate))
                                .setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                case 4:
                    ((ImageView) findViewById(R.id.btnValidate))
                            .setImageResource(R.drawable.btn_validate_off);
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };



    /////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] mData;
        String mWhere;
        String mOrderBy;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_MATH_SHAPES:{


                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "0"}; //6
                mOrderBy="app_users_activity_variable_values.number_correct_in_a_row ASC, ";
                mWhere="AND math_shapes.math_shape_type_id<="+mCurrentGSP.get("current_level")+" ";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_SHAPES, mData, mWhere, null, mOrderBy);
                break;
            }
            case Constants.MATHCING_MATH_SHAPE:{

                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "0"}; //6
                mWhere="AND math_shapes.math_shape_type_id=="+mCurrentMathShapeTypeId+" " +
                        "AND math_shapes.math_shape_size=="+mCurrentMathShapeSize+" " +
                        "AND math_shapes.math_shape_id!="+mCurrentCorrectId;
                mOrderBy="";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_SHAPES, mData, mWhere, null, mOrderBy);
                break;
            }
            case Constants.EXTRA_MATH_SHAPES:{

                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "0"}; //6
                mWhere="AND math_shapes.math_shape_type_id!="+mCurrentMathShapeTypeId+" "+
                        "AND math_shapes.math_shape_type_id<="+
                        mCurrentGSP.get("current_level")+" "+
                        "AND math_shapes.math_shape_id!="+mCurrentCorrectId;
                mOrderBy="";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_SHAPES, mData, mWhere, null, mOrderBy);
                break;
            }
        }

        return cursorLoader;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor mCursor) {

        switch(loader.getId()) {
            default:
            case Constants.CURRENT_MATH_SHAPES:{
                processData(mCursor);
                break;
            }
            case Constants.MATHCING_MATH_SHAPE:{
                int mExtraCount=0;
                if(mCursor.moveToFirst()) {
                    do{

                        mGuessingShapes.add(new HashMap<String, String>());
                        mGuessingShapes.get(mExtraCount).put("correct_guess", "1");
                        mGuessingShapes.get(mExtraCount).put("guessed_yet","0");
                        mGuessingShapes.get(mExtraCount).put("math_shape_id",
                                mCursor.getString(mCursor.getColumnIndex("math_shape_id"))); //0
                        mGuessingShapes.get(mExtraCount).put("math_shape_code",
                                mCursor.getString(mCursor.getColumnIndex("math_shape_code"))); //1
                        mGuessingShapes.get(mExtraCount).put("math_shape_type_id",
                                mCursor.getString(
                                        mCursor.getColumnIndex("math_shape_type_id"))); //2
                        mGuessingShapes.get(mExtraCount).put("math_shape_color_id",
                                mCursor.getString(
                                        mCursor.getColumnIndex("math_shape_color_id"))); //3
                        mGuessingShapes.get(mExtraCount).put("math_shape_size",
                                mCursor.getString(mCursor.getColumnIndex("math_shape_size"))); //4
                        mGuessingShapes.get(mExtraCount).put("math_shape_kind",
                                mCursor.getString(mCursor.getColumnIndex("math_shape_kind"))); //5
                        mGuessingShapes.get(mExtraCount).put("math_shape_audio",
                                mCursor.getString(mCursor.getColumnIndex("math_shape_audio"))); //6
                        mGuessingShapes.get(mExtraCount).put("math_shape_image",
                                mCursor.getString(mCursor.getColumnIndex("math_shape_image"))); //7
                        mGuessingShapes.get(mExtraCount).put("number_correct",
                                mCursor.getString(mCursor.getColumnIndex("number_correct"))); //8
                        mGuessingShapes.get(mExtraCount).put("number_correct_in_a_row",
                                mCursor.getString(
                                        mCursor.getColumnIndex("number_correct_in_a_row"))); //9
                        mGuessingShapes.get(mExtraCount).put("number_incorrect",
                                mCursor.getString(
                                        mCursor.getColumnIndex("number_incorrect"))); //10
                        mGuessingShapes.get(mExtraCount).put("math_shape_type_audio",
                                mCursor.getString(
                                        mCursor.getColumnIndex("math_shape_type_audio")));
                        mExtraCount++;
                        if(mExtraCount>=1){
                            break;
                        }
                    }while(mCursor.moveToNext());
                }
                getLoaderManager().restartLoader(Constants.EXTRA_MATH_SHAPES, null, this);
                break;
            }
            case Constants.EXTRA_MATH_SHAPES:{
                finishBeginRound(mCursor);
                break;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mCurrentShapeSet=new ArrayList<>();
        int mNumber=0;
        int mNumberCorrectInARow=Constants.INA_ROW_CORRECT;
        if (mCursor.moveToFirst()) {
            do{
                mCurrentShapeSet.add(new HashMap<String, String>());
                mCurrentShapeSet.get(mNumber).put("correct_guess","1");
                mCurrentShapeSet.get(mNumber).put("guessed_yet", "0");
                mCurrentShapeSet.get(mNumber).put("math_shape_id",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_id"))); //0
                mCurrentShapeSet.get(mNumber).put("math_shape_code",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_code"))); //1
                mCurrentShapeSet.get(mNumber).put("math_shape_type_id",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_type_id"))); //2
                mCurrentShapeSet.get(mNumber).put("math_shape_color_id",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_color_id"))); //3
                mCurrentShapeSet.get(mNumber).put("math_shape_size",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_size"))); //4
                mCurrentShapeSet.get(mNumber).put("math_shape_kind",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_kind"))); //5
                mCurrentShapeSet.get(mNumber).put("math_shape_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_audio"))); //6
                mCurrentShapeSet.get(mNumber).put("math_shape_image",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_image"))); //7
                mCurrentShapeSet.get(mNumber).put("number_correct",
                        mCursor.getString(mCursor.getColumnIndex("number_correct"))); //8
                mCurrentShapeSet.get(mNumber).put("number_correct_in_a_row",
                        mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))); //9
                mCurrentShapeSet.get(mNumber).put("number_incorrect",
                        mCursor.getString(mCursor.getColumnIndex("number_incorrect"))); //10

                mCurrentShapeSet.get(mNumber).put("math_shape_type_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_type_audio")));

                mNumberCorrectInARow=(mNumberCorrectInARow>Integer.parseInt(
                        mCurrentShapeSet.get(mNumber).get("number_correct_in_a_row")))
                        ? Integer.parseInt(
                        mCurrentShapeSet.get(mNumber).get("number_correct_in_a_row"))
                        :mNumberCorrectInARow;


                mNumber++;
                if (mNumber >= Constants.NUMBER_VARIABLES ||
                        mCursor.isLast()){
                    break;
                }
            }while(mCursor.moveToNext());
        }



        Collections.shuffle(mCurrentShapeSet);

        if(mCurrentShapeSet.size()<Constants.NUMBER_VARIABLES ) {
            for (int i = 0; i < mCurrentShapeSet.size(); i++) {
                if (!mCurrentShapeSet.get(mCurrentShapeSet.size() - 1).get("math_shape_id")
                        .equals(mCurrentShapeSet.get(i).get("math_shape_id"))) {
                    mCurrentShapeSet.add(new HashMap<>(mCurrentShapeSet.get(i)));
                    if (mCurrentShapeSet.size() >= Constants.NUMBER_VARIABLES) {
                        break;
                    }
                }
            }
        }


        beginRound();

    }
    ////////////////////////////////////////////////////////////////////////////////////////////



    protected void setUpListeners(){

        findViewById(R.id.shape0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                mAudioHandler.postDelayed(playShapeAudio, 20);
            }
        });



        findViewById(R.id.shape0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                playGeneralAudio(mCurrentMathShapeTypeAudio);
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
