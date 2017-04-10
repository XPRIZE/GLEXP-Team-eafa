package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/28/2015.
 */
public class ActivitySH06 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private int mCurrentLocation=0;
    private int mIncorrectInRound=0;
    private int mIncorrectInActivity=0;

    private int mRoundNumber=0;

    private boolean mCorrect=false;

    private String mCurrentMathShapeId;
    private String mLevelMathShapeId;

    private String mCurrentShapeAudio;
    private String mCorrectShapeAudio;


    private boolean mThreeDigitSet;

    private String mCurrentCorrectId;
    private String mCurrentCorrectTypeId;

    private ArrayList<HashMap<String,String>> mCurrentShapeSet;
    private ArrayList<HashMap<String,String>> mCurrentShapeRound;
    private ArrayList<HashMap<String,String>> mShapeSet;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sh06);

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        mInstructionAudio="info_sh06";
        appData.addToClassOrder(5);

        mCurrentGSP=new HashMap<String, String>(appData.getCurrentGroup_Section_Phase());

        mBeingValidated=true;
        mIncorrectInActivity=0;
        clearActivity();
        hideShapes();
        setUpListeners();
        setupFrameListens();

        getLoaderManager().initLoader(Constants.CURRENT_MATH_SHAPES, null, this);

    }//end public void onCreate(Bundle savedInstanceState) {


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected long playInstructionAudio(){
        long mAudioDuration = super.playInstructionAudio();
       // mAudioHandler.postDelayed(playShapeAudio, mAudioDuration+20);
        return mAudioDuration;
    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void clearActivity(){





        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);

        clearFrames();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected void beginRound(){
        findViewById(R.id.shape0).setVisibility(CanvasView.INVISIBLE);

        mIncorrectInRound=0;

        //mCurrentShapeRound.clear();
        mCurrentShapeRound=new ArrayList<HashMap<String, String>>();
        mCurrentShapeRound.add(mCurrentShapeSet.get(mRoundNumber));
        mCurrentCorrectId=mCurrentShapeRound.get(0).get("math_shape_id");
        mCurrentCorrectTypeId=mCurrentShapeRound.get(0).get("math_shape_type_id");
        mCorrectShapeAudio=mCurrentShapeRound.get(0).get("math_shape_audio");

        mShapeSet=new ArrayList<HashMap<String, String>>();

        final Random rand = new Random();
        int mRandomNumber=2;
        if(Integer.parseInt(mCurrentGSP.get("current_level"))>7) {
            mRandomNumber = rand.nextInt(10);
        }

        int mMathShapeSize=4;
        int mCountInSet=0;

        mShapeSet.add(new HashMap<String, String>());

        mShapeSet.get(mCountInSet).put("math_shape_type_id",
                mCurrentShapeRound.get(0).get("math_shape_type_id"));
        mShapeSet.get(mCountInSet).put("math_shape_color_id",
                mCurrentShapeRound.get(0).get("math_shape_color_id"));
        mShapeSet.get(mCountInSet).put("math_shape_size",
                mCurrentShapeRound.get(0).get("math_shape_size"));



        mCountInSet++;

        if(mCurrentGSP.get("current_level").equals("8")) {
            if(rand.nextInt(2)==1) {
                mShapeSet.add(new HashMap<String, String>());
                mShapeSet.get(mCountInSet).put("math_shape_type_id",
                        mCurrentShapeRound.get(0).get("math_shape_type_id"));
                mShapeSet.get(mCountInSet).put("math_shape_color_id",
                        mCurrentShapeRound.get(0).get("math_shape_color_id"));
                mShapeSet.get(mCountInSet).put("math_shape_size",
                        mCurrentShapeRound.get(0).get("math_shape_size"));
                mCountInSet++;

                mShapeSet.add(new HashMap<String, String>());
                mShapeSet.get(mCountInSet).put("math_shape_type_id",
                        String.valueOf(rand.nextInt(6) + 1));
                mShapeSet.get(mCountInSet).put("math_shape_color_id",
                        String.valueOf(rand.nextInt(6) + 1));

                if (rand.nextInt(2) == 0) {
                    mMathShapeSize = 0;
                }
                if (mShapeSet.get(mCountInSet).get("math_shape_type_id")
                        .equals(mCurrentShapeRound.get(0).get("math_shape_type_id")) &&
                        mShapeSet.get(mCountInSet).get("math_shape_color_id")
                                .equals(mCurrentShapeRound.get(0).get("math_shape_color_id")) &&
                        String.valueOf(mMathShapeSize)
                                .equals(mCurrentShapeRound.get(0).get("math_shape_size"))) {
                    if (mMathShapeSize == 0) {
                        mMathShapeSize = 4;
                    } else {
                        mMathShapeSize = 0;
                    }
                }
                mShapeSet.get(mCountInSet).put("math_shape_size", String.valueOf(mMathShapeSize));
            }else{
                mShapeSet.add(new HashMap<String, String>());
                mShapeSet.get(mCountInSet).put("math_shape_type_id", String.valueOf(
                        rand.nextInt(6) + 1));
                mShapeSet.get(mCountInSet).put("math_shape_color_id", String.valueOf(
                        rand.nextInt(6) + 1));

                if(rand.nextInt(2)==0){
                    mMathShapeSize=0;
                }
                if(mShapeSet.get(mCountInSet).get("math_shape_type_id")
                        .equals(mCurrentShapeRound.get(0).get("math_shape_type_id")) &&
                        mShapeSet.get(mCountInSet).get("math_shape_color_id")
                                .equals(mCurrentShapeRound.get(0).get("math_shape_color_id")) &&
                        String.valueOf(mMathShapeSize)
                                .equals(mCurrentShapeRound.get(0).get("math_shape_size"))) {
                    if(mMathShapeSize==0){
                        mMathShapeSize=4;
                    }else{
                        mMathShapeSize=0;
                    }
                }
                mShapeSet.get(mCountInSet).put("math_shape_size", String.valueOf(mMathShapeSize));
                mCountInSet++;
                mShapeSet.add(new HashMap<String, String>());
                mShapeSet.get(mCountInSet).put("math_shape_type_id",
                        mShapeSet.get(mCountInSet-1).get("math_shape_type_id"));
                mShapeSet.get(mCountInSet).put("math_shape_color_id",
                        mShapeSet.get(mCountInSet-1).get("math_shape_color_id"));
                mShapeSet.get(mCountInSet).put("math_shape_size",
                        mShapeSet.get(mCountInSet-1).get("math_shape_size"));
            }
        }else if(mRandomNumber>2){
            mShapeSet.add(new HashMap<String, String>());
            mShapeSet.get(mCountInSet).put("math_shape_type_id", String.valueOf(
                    rand.nextInt(6) + 1));
            mShapeSet.get(mCountInSet).put("math_shape_color_id", String.valueOf(
                    rand.nextInt(6) + 1));

            if(rand.nextInt(2)==0){
                mMathShapeSize=0;
            }
            if(mShapeSet.get(mCountInSet).get("math_shape_type_id")
                    .equals(mCurrentShapeRound.get(0).get("math_shape_type_id")) &&
                    mShapeSet.get(mCountInSet).get("math_shape_color_id")
                            .equals(mCurrentShapeRound.get(0).get("math_shape_color_id")) &&
                    String.valueOf(mMathShapeSize)
                            .equals(mCurrentShapeRound.get(0).get("math_shape_size"))) {
                if(mMathShapeSize==0){
                    mMathShapeSize=4;
                }else{
                    mMathShapeSize=0;
                }
            }
            mShapeSet.get(mCountInSet).put("math_shape_size", String.valueOf(mMathShapeSize));
            mCountInSet++;
            mShapeSet.add(new HashMap<String, String>());
            mShapeSet.get(mCountInSet).put("math_shape_type_id",
                    String.valueOf(rand.nextInt(6) + 1));
            mShapeSet.get(mCountInSet).put("math_shape_color_id",
                    String.valueOf(rand.nextInt(6) + 1));

            if (rand.nextInt(2) == 0) {
                mMathShapeSize = 0;
            }
            if (mShapeSet.get(mCountInSet).get("math_shape_type_id")
                    .equals(mCurrentShapeRound.get(0).get("math_shape_type_id")) &&
                    mShapeSet.get(mCountInSet).get("math_shape_color_id")
                            .equals(mCurrentShapeRound.get(0).get("math_shape_color_id")) &&
                    String.valueOf(mMathShapeSize)
                            .equals(mCurrentShapeRound.get(0).get("math_shape_size"))) {
                if (mMathShapeSize == 0) {
                    mMathShapeSize = 4;
                } else {
                    mMathShapeSize = 0;
                }
            }
            mShapeSet.get(mCountInSet).put("math_shape_size", String.valueOf(mMathShapeSize));
        }else {

            mShapeSet.add(new HashMap<String, String>());
            mShapeSet.get(mCountInSet).put("math_shape_type_id",
                    String.valueOf(rand.nextInt(6) + 1));
            mShapeSet.get(mCountInSet).put("math_shape_color_id",
                    String.valueOf(rand.nextInt(6) + 1));

            if (rand.nextInt(2) == 0) {
                mMathShapeSize = 0;
            }
            if (mShapeSet.get(mCountInSet).get("math_shape_type_id")
                    .equals(mCurrentShapeRound.get(0).get("math_shape_type_id")) &&
                    mShapeSet.get(mCountInSet).get("math_shape_color_id")
                            .equals(mCurrentShapeRound.get(0).get("math_shape_color_id")) &&
                    String.valueOf(mMathShapeSize)
                            .equals(mCurrentShapeRound.get(0).get("math_shape_size"))) {
                if (mMathShapeSize == 0) {
                    mMathShapeSize = 4;
                } else {
                    mMathShapeSize = 0;
                }
            }
            mShapeSet.get(mCountInSet).put("math_shape_size", String.valueOf(mMathShapeSize));
        }

        if(mCurrentGSP.get("current_level").equals("8")) {
            mShapeSet.add(new HashMap<>(mShapeSet.get(0)));
            mShapeSet.add(new HashMap<>(mShapeSet.get(1)));
            mShapeSet.add(new HashMap<>(mShapeSet.get(2)));
            mShapeSet.add(new HashMap<>(mShapeSet.get(0)));
        }else if(mRandomNumber>2){

            mShapeSet.add(new HashMap<>(mShapeSet.get(0)));
            mShapeSet.add(new HashMap<>(mShapeSet.get(1)));
            mShapeSet.add(new HashMap<>(mShapeSet.get(2)));
            mShapeSet.add(new HashMap<>(mShapeSet.get(0)));
        }else{
            mShapeSet.add(new HashMap<>(mShapeSet.get(0)));
            mShapeSet.add(new HashMap<>(mShapeSet.get(1)));
            mShapeSet.add(new HashMap<>(mShapeSet.get(0)));
            mShapeSet.add(new HashMap<>(mShapeSet.get(1)));
            mShapeSet.add(new HashMap<>(mShapeSet.get(0)));

        }

        displayTopScreen();
        getLoaderManager().restartLoader(Constants.MATH_SHAPES_SET, null, this);

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void finishBeginRound(Cursor mCursor){
        int mExtraCount=mCurrentShapeRound.size()-1;
        if(mCursor.moveToFirst()){
            do{
                mExtraCount++;
                mCurrentShapeRound.add(new HashMap<String, String>());
                mCurrentShapeRound.get(mExtraCount).put("correct_guess","0");
                mCurrentShapeRound.get(mExtraCount).put("guessed_yet","0");
                mCurrentShapeRound.get(mExtraCount).put("math_shape_id",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_id"))); //0
                mCurrentShapeRound.get(mExtraCount).put("math_shape_code",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_code"))); //1
                mCurrentShapeRound.get(mExtraCount).put("math_shape_type_id",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_type_id"))); //2
                mCurrentShapeRound.get(mExtraCount).put("math_shape_color_id",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_color_id"))); //3
                mCurrentShapeRound.get(mExtraCount).put("math_shape_size",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_size"))); //4
                mCurrentShapeRound.get(mExtraCount).put("math_shape_kind",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_kind"))); //5
                mCurrentShapeRound.get(mExtraCount).put("math_shape_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_audio"))); //6
                mCurrentShapeRound.get(mExtraCount).put("math_shape_image",
                        mCursor.getString(mCursor.getColumnIndex("math_shape_image"))); //7

                if(mExtraCount>=3){
                    break;
                }
            }while(mCursor.moveToNext());
        }

        if(mExtraCount<3){
            //issue not enough
        }
        displayScreen();

    }
    /////////////////////////////////////////////////////////////////////////////////////////

    private void displayTopScreen(){
        CanvasView mCustomCanvas;
        for(int mLocation=0;mLocation<7;mLocation++){
            switch(mLocation){
                default:
                case 0:{
                    findViewById(R.id.shapeA).setVisibility(CanvasView.VISIBLE);
                    mCustomCanvas  = (CanvasView) findViewById(R.id.shapeA);
                    break;
                }
                case 1:{
                    findViewById(R.id.shapeB).setVisibility(CanvasView.VISIBLE);
                    mCustomCanvas  = (CanvasView) findViewById(R.id.shapeB);
                    break;
                }
                case 2:{
                    findViewById(R.id.shapeC).setVisibility(CanvasView.VISIBLE);
                    mCustomCanvas  = (CanvasView) findViewById(R.id.shapeC);
                    break;
                }
                case 3:{
                    findViewById(R.id.shapeD).setVisibility(CanvasView.VISIBLE);
                    mCustomCanvas  = (CanvasView) findViewById(R.id.shapeD);
                    break;
                }
                case 4:{
                    findViewById(R.id.shapeE).setVisibility(CanvasView.VISIBLE);
                    mCustomCanvas  = (CanvasView) findViewById(R.id.shapeE);
                    break;
                }
                case 5:{
                    findViewById(R.id.shapeF).setVisibility(CanvasView.VISIBLE);
                    mCustomCanvas  = (CanvasView) findViewById(R.id.shapeF);
                    break;
                }
                case 6:{
                    findViewById(R.id.shapeG).setVisibility(CanvasView.INVISIBLE);
                    mCustomCanvas  = (CanvasView) findViewById(R.id.shapeG);
                    break;
                }
            }


            mCustomCanvas.clearCanvas();

            RelativeLayout.LayoutParams mParams
                    =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            int mShapeMargin=getResources().getDimensionPixelSize(R.dimen.sh06_top_tops_margins);
            int mShapeSidesMargin=getResources().getDimensionPixelSize(R.dimen.sh06_top_side_margins);
            if(mShapeSet.get(mLocation).get("math_shape_size").equals("4")){
                mShapeMargin=getResources()
                        .getDimensionPixelSize(R.dimen.sh06_top_tops_large_margins);
                mShapeSidesMargin=getResources()
                        .getDimensionPixelSize(R.dimen.sh06_top_side_large_margins);
            }

            mParams.setMargins(mShapeSidesMargin, mShapeMargin,mShapeSidesMargin,mShapeMargin);

            mCustomCanvas.setLayoutParams(mParams);

            mCustomCanvas.setType(Integer.parseInt(mShapeSet.get(mLocation)
                    .get("math_shape_type_id")));
            mCustomCanvas.setColor(Integer.parseInt(mShapeSet.get(mLocation)
                    .get("math_shape_color_id")));

            mCustomCanvas.invalidate();


        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){
        Collections.shuffle(mCurrentShapeRound);

        for (int i = 0; i < 4; i++) {
            if (mCurrentShapeRound.get(i).get("correct_guess").equals("1")) {
                mCorrectLocation = i;
            }
            placingCurrentShapes(i);
        }

        if(mRoundNumber==0){
            playInstructionAudio();
        }

        mBeingValidated=false;

    }//end private void displayScreen(Cursor currentWordsCursor){

    //////////////////////////////////////////////////////////////////////////////////////////////

    private void placingGuessShape(){
        CanvasView mCustomCanvas;

        findViewById(R.id.shape0).setVisibility(CanvasView.VISIBLE);
        mCustomCanvas  = (CanvasView) findViewById(R.id.shape0);

        mCustomCanvas.clearCanvas();
        /**/
        RelativeLayout.LayoutParams mParams
                =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        int mShapeMargin=getResources().getDimensionPixelSize(R.dimen.sh06_top_tops_margins);
        int mShapeSidesMargin=getResources().getDimensionPixelSize(R.dimen.sh06_top_side_margins);
        if(mCurrentShapeRound.get(mCurrentLocation).get("math_shape_size").equals("4")){
            mShapeMargin=getResources()
                    .getDimensionPixelSize(R.dimen.sh06_top_tops_large_margins);
            mShapeSidesMargin=getResources()
                    .getDimensionPixelSize(R.dimen.sh06_top_side_large_margins);
        }


        mParams.setMargins(mShapeSidesMargin, mShapeMargin,mShapeSidesMargin,mShapeMargin);


        mCustomCanvas.setLayoutParams(mParams);

        mCustomCanvas.setType(Integer.parseInt(mCurrentShapeRound.get(mCurrentLocation)
                .get("math_shape_type_id")));
        mCustomCanvas.setColor(Integer.parseInt(mCurrentShapeRound.get(mCurrentLocation)
                .get("math_shape_color_id")));

        mCustomCanvas.invalidate();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

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
        }

        mCustomCanvas.clearCanvas();

        /**/
        RelativeLayout.LayoutParams mParams
                =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        int mShapeMargin=getResources()
                .getDimensionPixelSize(R.dimen.sh06_bottom_tops_margins);
        int mShapeMarginSides=getResources()
                .getDimensionPixelSize(R.dimen.sh06_bottom_side_margins);
        if(mCurrentShapeRound.get(shapeID).get("math_shape_size").equals("4")){
            mShapeMargin=getResources()
                    .getDimensionPixelSize(R.dimen.sh06_bottom_tops_large_margins);
            mShapeMarginSides=getResources()
                    .getDimensionPixelSize(R.dimen.sh06_bottom_side_large_margins);
        }
        mParams.setMargins(mShapeMarginSides, mShapeMargin,mShapeMarginSides,mShapeMargin);

        mCustomCanvas.setLayoutParams(mParams);

        mCustomCanvas.setType(Integer.parseInt(mCurrentShapeRound.get(shapeID)
                .get("math_shape_type_id")));
        mCustomCanvas.setColor(Integer.parseInt(mCurrentShapeRound.get(shapeID)
                .get("math_shape_color_id")));

        mCustomCanvas.invalidate();


    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable playShapeAudio = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(playShapeAudio);
            mBeingValidated=false;
            if(!mCorrectShapeAudio.equals(""))
                playGeneralAudio(mCorrectShapeAudio);
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //end private void displayScreen(Cursor currentWordsCursor){


    ////////////////////////////////////////////////////////////////////////////////////////////




    protected void setupFrameListens(){

        findViewById(R.id.frame1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentShapeRound.get(0).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=0;
                    mCurrentShapeAudio=mCurrentShapeRound.get(0).get("math_shape_audio");
                    mCurrentMathShapeId=mCurrentShapeRound.get(0).get("math_shape_id");
                    mCorrect=(mCurrentShapeRound.get(0).get("correct_guess").equals("1"));
                    playGeneralAudio(mCurrentShapeAudio);
                    setUpFrame(0);
                    placingGuessShape();

                }
            }
        });

        findViewById(R.id.frame2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentShapeRound.get(1).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 1;
                    mCurrentShapeAudio = mCurrentShapeRound.get(1).get("math_shape_audio");
                    mCurrentMathShapeId = mCurrentShapeRound.get(1).get("math_shape_id");
                    mCorrect = (mCurrentShapeRound.get(1).get("correct_guess").equals("1"));
                    playGeneralAudio(mCurrentShapeAudio);
                    setUpFrame(1);
                    placingGuessShape();

                }
            }
        });

        findViewById(R.id.frame3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentShapeRound.get(2).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=2;
                    mCurrentShapeAudio=mCurrentShapeRound.get(2).get("math_shape_audio");
                    mCurrentMathShapeId=mCurrentShapeRound.get(2).get("math_shape_id");
                    mCorrect=(mCurrentShapeRound.get(2).get("correct_guess").equals("1"));
                    playGeneralAudio(mCurrentShapeAudio);
                    setUpFrame(2);
                    placingGuessShape();

                }
            }
        });

        findViewById(R.id.frame4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentShapeRound.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 3;
                    mCurrentShapeAudio = mCurrentShapeRound.get(3).get("math_shape_audio");
                    mCurrentMathShapeId = mCurrentShapeRound.get(3).get("math_shape_id");
                    mCorrect = (mCurrentShapeRound.get(3).get("correct_guess").equals("1"));
                    playGeneralAudio(mCurrentShapeAudio);
                    setUpFrame(3);
                    placingGuessShape();

                }
            }
        });

        findViewById(R.id.shapeSpace1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentShapeRound.get(0).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=0;
                    mCurrentShapeAudio=mCurrentShapeRound.get(0).get("math_shape_audio");
                    mCurrentMathShapeId=mCurrentShapeRound.get(0).get("math_shape_id");
                    mCorrect=(mCurrentShapeRound.get(0).get("correct_guess").equals("1"));
                    playGeneralAudio(mCurrentShapeAudio);
                    setUpFrame(0);
                    placingGuessShape();

                }
            }
        });

        findViewById(R.id.shapeSpace2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentShapeRound.get(1).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 1;
                    mCurrentShapeAudio = mCurrentShapeRound.get(1).get("math_shape_audio");
                    mCurrentMathShapeId = mCurrentShapeRound.get(1).get("math_shape_id");
                    mCorrect = (mCurrentShapeRound.get(1).get("correct_guess").equals("1"));
                    setUpFrame(1);
                    placingGuessShape();

                }
            }
        });

        findViewById(R.id.shapeSpace3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrentShapeRound.get(2).get("guessed_yet").equals("0")
                        && !mBeingValidated){
                    mCurrentLocation=2;
                    mCurrentShapeAudio=mCurrentShapeRound.get(2).get("math_shape_audio");
                    mCurrentMathShapeId=mCurrentShapeRound.get(2).get("math_shape_id");
                    mCorrect=(mCurrentShapeRound.get(2).get("correct_guess").equals("1"));
                    setUpFrame(2);
                    placingGuessShape();

                }
            }
        });

        findViewById(R.id.shapeSpace4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrentShapeRound.get(3).get("guessed_yet").equals("0")
                        && !mBeingValidated) {
                    mCurrentLocation = 3;
                    mCurrentShapeAudio = mCurrentShapeRound.get(3).get("math_shape_audio");
                    mCurrentMathShapeId = mCurrentShapeRound.get(3).get("math_shape_id");
                    mCorrect = (mCurrentShapeRound.get(3).get("correct_guess").equals("1"));
                    setUpFrame(3);
                    placingGuessShape();

                }
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpFrame(int frameNumber){
        clearFrames();
        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
        mValidateAvailable=true;

        switch(frameNumber){
            default:
            case 0:{
                ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_sh06_on);
                break;
            }
            case 1:{
                ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_sh06_on);
                break;
            }
            case 2:{
                ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_sh06_on);
                break;
            }
            case 3:{
                ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_sh06_on);
                break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void hideShapes(){
        findViewById(R.id.shape1).setVisibility(CanvasView.INVISIBLE);
        findViewById(R.id.shape2).setVisibility(CanvasView.INVISIBLE);
        findViewById(R.id.shape3).setVisibility(CanvasView.INVISIBLE);
        findViewById(R.id.shape4).setVisibility(CanvasView.INVISIBLE);
    }
    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_sh06_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_sh06_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_sh06_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_sh06_off);


    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void validate(){
        mBeingValidated=true;
        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_ok);
        }else{
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_no_ok);
        }
        placingGuessShape();
        processFontColorAndPoints();
        processChoiceView();
        mProcessGuessPosition=0;
        guessHandler.postDelayed(processGuess, 10);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    private void processFontColorAndPoints(){
        String where=" WHERE app_user_id="+appData.getCurrentUserID()
                +" AND variable_id="+mLevelMathShapeId
                +" AND activity_id="+appData.getCurrentActivity().get(0);
        String[] selectionArgs;
        ContentValues mValues = new ContentValues();
        mValues.put("level_number",mLevelMathShapeId);
        mValues.put("activity_id",appData.getCurrentActivity().get(0));
        if(mCorrect) {
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_ok);
            selectionArgs = new String[]{
                    "1",
                    "0",
                    mCurrentGSP.get("current_level"),
                    mLevelMathShapeId,
                    String.valueOf(mIncorrectInRound)};


            if (!mCurrentGSP.get("current_level").equals("9")){
                getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, mValues, where, selectionArgs);
            }
            switch(mCurrentLocation){
                default:
                case 0:
                    mCurrentShapeRound.get(0).put("guessed_yet", "1");
                    break;
                case 1:
                    mCurrentShapeRound.get(1).put("guessed_yet", "1");
                    break;
                case 2:
                    mCurrentShapeRound.get(2).put("guessed_yet", "1");
                    break;
                case 3:
                    mCurrentShapeRound.get(3).put("guessed_yet", "1");
                    break;
            }


        }else{
            mIncorrectInRound++;
            mIncorrectInActivity++;
            ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_no_ok);
            selectionArgs = new String[]{
                    "0",
                    "1",
                    mCurrentGSP.get("current_level"),
                    mLevelMathShapeId,
                    String.valueOf(mIncorrectInRound)};

            getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_UPDATE, null, where, selectionArgs);
            if(mIncorrectInRound>=2){
                switch(mCorrectLocation){
                    default:
                    case 0:
                        mCurrentShapeRound.get(1).put("guessed_yet", "1");
                        mCurrentShapeRound.get(2).put("guessed_yet", "1");
                        mCurrentShapeRound.get(3).put("guessed_yet", "1");
                        break;
                    case 1:
                        mCurrentShapeRound.get(0).put("guessed_yet", "1");
                        mCurrentShapeRound.get(2).put("guessed_yet", "1");
                        mCurrentShapeRound.get(3).put("guessed_yet", "1");
                        break;
                    case 2:
                        mCurrentShapeRound.get(1).put("guessed_yet", "1");
                        mCurrentShapeRound.get(0).put("guessed_yet", "1");
                        mCurrentShapeRound.get(3).put("guessed_yet", "1");
                        break;
                    case 3:
                        mCurrentShapeRound.get(1).put("guessed_yet", "1");
                        mCurrentShapeRound.get(2).put("guessed_yet", "1");
                        mCurrentShapeRound.get(0).put("guessed_yet", "1");
                        break;
                }
            }
            switch(mCurrentLocation){
                default:
                case 0:
                    mCurrentShapeRound.get(0).put("guessed_yet", "1");
                    break;
                case 1:
                    mCurrentShapeRound.get(1).put("guessed_yet", "1");
                    break;
                case 2:
                    mCurrentShapeRound.get(2).put("guessed_yet", "1");
                    break;
                case 3:
                    mCurrentShapeRound.get(3).put("guessed_yet", "1");
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

    //////////////////////////////////////////////////////////////////////////////////////


    protected void addPointToAllAvailable(){
        String where=" WHERE app_user_id="+appData.getCurrentUserID() +" " +
                "AND level_number="+mCurrentGSP.get("current_level") +" " +
                "AND activity_id="+appData.getCurrentActivity().get(0);

       // getContentResolver().update(AppProvider.CONTENT_URI_ACTIVITY_USER_RW_ALL_UPDATE,
        //        null, where, null);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////


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
                    mAudioDuration=playGeneralAudio(mCurrentShapeAudio);
                    mProcessGuessPosition++;
                    mAudioHandler.postDelayed(processGuess, mAudioDuration+10);
                    break;
                }
                case 2:{
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
                            //if(mIncorrectInActivity==0){
                            //    addPointToAllAvailable();
                           // }
                            mLastActivityData=0;

                            findViewById(R.id.activityMainPart)
                                    .setVisibility(LinearLayout.VISIBLE);
                            findViewById(R.id.activityMainPart)
                                    .setAnimation(AnimationUtils.loadAnimation(
                                            getApplicationContext(), R.anim.fade_out));


                            lastActivityDataHandler.postDelayed(returnToActivities_Platorm,10);
                        }
                    }else{
                        findViewById(R.id.shape0).setVisibility(CanvasView.INVISIBLE);
                        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
                        mBeingValidated=false;
                        mValidateAvailable=false;
                    }

                    break;
                }
                case 3:{
                    ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);
                    beginRound();
                    guessHandler.removeCallbacks(processGuess);
                    break;
                }
            }//switch(mProcessGuessPosition){
        }//public void run(){
    };



    ///////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] mData;
        String mWhere="";
        String mOrderBy="";
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
                mOrderBy=" ";
                mWhere="AND math_shapes.math_shape_type_id<="+mCurrentGSP.get("current_level")+" ";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_SHAPES, mData, mWhere, null, mOrderBy);
                break;
            }
            case Constants.MATH_SHAPE_LEVELS:{
                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "4"}; //6

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_SHAPES, mData, mWhere, null, mOrderBy);
                break;
            }
            case Constants.MATH_SHAPES_SET:{

                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "5"}; //6

                String[] mWhereSets= new String[]{"","",""};

                mWhereSets[0]="math_shapes.math_shape_type_id="+
                        mShapeSet.get(1).get("math_shape_type_id")+" " +
                        "AND math_shapes.math_shape_color_id=" +
                        mShapeSet.get(1).get("math_shape_color_id") + " " +
                        "AND math_shapes.math_shape_size=" +
                        mShapeSet.get(1).get("math_shape_size");

                if(!mShapeSet.get(1).equals(mShapeSet.get(2))) {
                    mWhereSets[1]="( math_shapes.math_shape_type_id="+
                            mShapeSet.get(2).get("math_shape_type_id")+" " +
                            "AND math_shapes.math_shape_color_id=" +
                            mShapeSet.get(2).get("math_shape_color_id") + " " +
                            "AND math_shapes.math_shape_size=" +
                            mShapeSet.get(2).get("math_shape_size") + " )" +
                            "AND NOT ( math_shapes.math_shape_type_id!="+
                            mShapeSet.get(1).get("math_shape_type_id")+" " +
                            "AND math_shapes.math_shape_color_id!=" +
                            mShapeSet.get(1).get("math_shape_color_id") + " " +
                            "AND math_shapes.math_shape_size!=" +
                            mShapeSet.get(1).get("math_shape_size") + ") ";
                }else{
                    mWhereSets[1] = " math_shapes.math_shape_color_id=" +
                            mShapeSet.get(1).get("math_shape_color_id") + " ";
                }

                mWhereSets[2] = " math_shapes.math_shape_color_id=" +
                        mShapeSet.get(0).get("math_shape_color_id") + " " +
                        "AND NOT ( math_shapes.math_shape_type_id!=" +
                        mShapeSet.get(2).get("math_shape_type_id") + " " +
                        "AND math_shapes.math_shape_color_id!=" +
                        mShapeSet.get(2).get("math_shape_color_id") + " " +
                        "AND math_shapes.math_shape_size!=" +
                        mShapeSet.get(2).get("math_shape_size") + ") " +
                        "AND NOT ( math_shapes.math_shape_type_id!=" +
                        mShapeSet.get(1).get("math_shape_type_id") + " " +
                        "AND math_shapes.math_shape_color_id!=" +
                        mShapeSet.get(1).get("math_shape_color_id") + " " +
                        "AND math_shapes.math_shape_size!=" +
                        mShapeSet.get(1).get("math_shape_size") + ") ";


                mWhere+="AND   math_shapes.math_shape_kind==0 " +
                        "AND math_shapes.math_shape_id!="+mCurrentCorrectId+" ";


                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_SHAPES, mData, mWhere, mWhereSets, null);
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
                mWhere="AND math_shapes.math_shape_type_id<=6 ";
                for(int i=0; i<mCurrentShapeRound.size(); i++){
                    mWhere+= "AND math_shapes.math_shape_id!="+
                            mCurrentShapeRound.get(i).get("math_shape_id")+" ";
                }


                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_SHAPES, mData, mWhere, null, mOrderBy);
                break;
            }

        }

        return cursorLoader;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor mCursor) {

        switch(loader.getId()) {
            default:
            case Constants.CURRENT_MATH_SHAPES:{
                processData(mCursor);
                break;
            }
            case Constants.MATH_SHAPE_LEVELS:{
                mLevelMathShapeId="313";
                if(mCursor.moveToFirst()){
                    mLevelMathShapeId=mCursor.getString(mCursor.getColumnIndex("variable_id"));
                }
                beginRound();
                break;
            }
            case Constants.MATH_SHAPES_SET: {
                finishBeginRound(mCursor);
                break;
            }
            case Constants.EXTRA_MATH_SHAPES:{
                //finishBeginRound(mCursor);
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

    protected void processData(Cursor mCursor){
        mCurrentShapeSet=new ArrayList<>();
        int mNumber=0;
        int mNumberCorrectInARow=Constants.INA_ROW_CORRECT;
        if(mCursor.moveToFirst()) {
            do{
                mCurrentShapeSet.add(new HashMap<String, String>());
                mCurrentShapeSet.get(mNumber).put("correct_guess","1");
                mCurrentShapeSet.get(mNumber).put("guessed_yet","0");
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
                    mCurrentShapeSet.add(mCurrentShapeSet.get(i));
                    if (mCurrentShapeSet.size() >= Constants.NUMBER_VARIABLES) {
                        break;
                    }
                }
            }
        }

        getLoaderManager().restartLoader(Constants.MATH_SHAPE_LEVELS, null, this);



    }
    ////////////////////////////////////////////////////////////////////////////////////////////



    protected void setUpListeners(){




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
