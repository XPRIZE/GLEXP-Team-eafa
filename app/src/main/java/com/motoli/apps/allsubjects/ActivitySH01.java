package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/28/2015.
 */
public class ActivitySH01 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private boolean mPlayingAudio=false;
    private boolean mShapeChosen=false;
    private boolean mValidateAvailable=false;

    private String mShapeAudio="";
    private String mShapeAudioSmall="";
    private ArrayList<String> mTypeAudio;
    private int mMathShapeColorID;
    private int mMathShapeTypeId;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sh01);
        findViewById(R.id.currentShape).setVisibility(CanvasView.INVISIBLE);
        findViewById(R.id.currentShapeSmall).setVisibility(CanvasView.INVISIBLE);

        appData.addToClassOrder(5);

        mCurrentGSP = new HashMap<>(appData.getCurrentGroup_Section_Phase());

        mInstructionAudio="info_sh01";
        getLoaderManager().initLoader(Constants.CURRENT_MATH_SHAPES, null, this);

        hideChosenShape();

        findViewById(R.id.circle).setVisibility(CanvasView.GONE);
        findViewById(R.id.square).setVisibility(CanvasView.GONE);
        findViewById(R.id.triangle).setVisibility(CanvasView.GONE);
        findViewById(R.id.line).setVisibility(CanvasView.GONE);
        findViewById(R.id.star).setVisibility(CanvasView.GONE);
        findViewById(R.id.rectangle).setVisibility(CanvasView.GONE);

        fadeColors();

    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void callShapeAudio(){
        getLoaderManager().restartLoader(Constants.EXTRA_MATH_SHAPES, null, this);
    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void fadeNotChosenColor(){
        unFadeColors();
        float mAlpha=0.65f;
        for(int i=1;i<=6;i++){
            if(i!=mMathShapeColorID) {
                switch (i) {
                    default:
                    case 1:
                        findViewById(R.id.greenColor).setAlpha(mAlpha);
                        break;
                    case 2:
                        findViewById(R.id.blueColor).setAlpha(mAlpha);
                        break;
                    case 3:
                        findViewById(R.id.redColor).setAlpha(mAlpha);
                        break;
                    case 4:
                        findViewById(R.id.yellowColor).setAlpha(mAlpha);
                        break;
                    case 5:
                        findViewById(R.id.pinkColor).setAlpha(mAlpha);
                        break;
                    case 6:
                        findViewById(R.id.orangeColor).setAlpha(mAlpha);
                        break;
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void fadeNotChosenShape(){
        unFadeShapes();
        float mAlpha=0.65f;
        for(int i=1;i<=6;i++){
            if(i!=mMathShapeTypeId) {
                switch (i) {
                    default:
                    case 1:
                        findViewById(R.id.circle).setAlpha(mAlpha);
                        break;
                    case 2:
                        findViewById(R.id.square).setAlpha(mAlpha);
                        break;
                    case 3:
                        findViewById(R.id.triangle).setAlpha(mAlpha);
                        break;
                    case 4:
                        findViewById(R.id.line).setAlpha(mAlpha);
                        break;
                    case 5:
                        findViewById(R.id.star).setAlpha(mAlpha);
                        break;
                    case 6:
                        findViewById(R.id.rectangle).setAlpha(mAlpha);
                        break;
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void unFadeColors(){
        findViewById(R.id.greenColor).setAlpha(1.0f);
        findViewById(R.id.blueColor).setAlpha(1.0f);
        findViewById(R.id.redColor).setAlpha(1.0f);
        findViewById(R.id.yellowColor).setAlpha(1.0f);
        findViewById(R.id.pinkColor).setAlpha(1.0f);
        findViewById(R.id.orangeColor).setAlpha(1.0f);
    }
    //////////////////////////////////////////////////////////////////////////////////////

    private void fadeColors(){
        float mAlpha=0.65f;
        findViewById(R.id.greenColor).setAlpha(mAlpha);
        findViewById(R.id.blueColor).setAlpha(mAlpha);
        findViewById(R.id.redColor).setAlpha(mAlpha);
        findViewById(R.id.yellowColor).setAlpha(mAlpha);
        findViewById(R.id.pinkColor).setAlpha(mAlpha);
        findViewById(R.id.orangeColor).setAlpha(mAlpha);
    }
    //////////////////////////////////////////////////////////////////////////////////////

    private void fadeShapes(){
        float mAlpha=0.65f;
        findViewById(R.id.circle).setAlpha(mAlpha);
        findViewById(R.id.square).setAlpha(mAlpha);
        findViewById(R.id.triangle).setAlpha(mAlpha);
        findViewById(R.id.star).setAlpha(mAlpha);
        findViewById(R.id.line).setAlpha(mAlpha);
        findViewById(R.id.rectangle).setAlpha(mAlpha);
    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void unFadeShapes(){
        findViewById(R.id.circle).setAlpha(1.0f);
        findViewById(R.id.square).setAlpha(1.0f);
        findViewById(R.id.triangle).setAlpha(1.0f);
        findViewById(R.id.star).setAlpha(1.0f);
        findViewById(R.id.line).setAlpha(1.0f);
        findViewById(R.id.rectangle).setAlpha(1.0f);
    }

    //////////////////////////////////////////////////////////////////////////////////////

    private void setShapeColor(){
        mValidateAvailable=false;
        fadeNotChosenColor();
        for(int i=0;i<6;i++){
            CanvasView customCanvas;
            switch (i){
                default:
                case 0:{
                    customCanvas  = (CanvasView) findViewById(R.id.circle);
                    break;
                }
                case 1:{
                    customCanvas  = (CanvasView) findViewById(R.id.square);
                    break;
                }
                case 2:{
                    customCanvas  = (CanvasView) findViewById(R.id.triangle);
                    break;
                }
                case 3:{
                    customCanvas  = (CanvasView) findViewById(R.id.line);
                    break;
                }
                case 4:{
                    customCanvas  = (CanvasView) findViewById(R.id.star);
                    break;
                }
                case 5:{
                    customCanvas  = (CanvasView) findViewById(R.id.rectangle);
                    break;
                }
            }
           // customCanvas.setColor(mMathShapeColorID);

           // customCanvas.invalidate();
        }
        //unFadeShapes();
    }
    ////////////////////////////////////////////////////////////////////////////////////

    private void showChosenShape(){
        findViewById(R.id.currentShape).setVisibility(CanvasView.VISIBLE);
        CanvasView customCanvas=(CanvasView) findViewById(R.id.currentShape);
        customCanvas.setType(mMathShapeTypeId);
        customCanvas.setColor(0);

        customCanvas.invalidate();


        findViewById(R.id.currentShapeSmall).setVisibility(CanvasView.VISIBLE);
        CanvasView customCanvasSmall=(CanvasView) findViewById(R.id.currentShapeSmall);
        customCanvasSmall.setType(mMathShapeTypeId);
        customCanvasSmall.setColor(0);

        customCanvasSmall.invalidate();


    }

    ////////////////////////////////////////////////////////////////////////////////////

    private void showChosenShapeAndColor(){
        CanvasView customCanvas=(CanvasView) findViewById(R.id.currentShape);
        customCanvas.clearCanvas();
        customCanvas.setType(mMathShapeTypeId);
        customCanvas.setColor(mMathShapeColorID);

        customCanvas.invalidate();

        CanvasView customCanvasSmall=(CanvasView) findViewById(R.id.currentShapeSmall);
        customCanvasSmall.clearCanvas();
        customCanvasSmall.setType(mMathShapeTypeId);
        customCanvasSmall.setColor(mMathShapeColorID);

        customCanvasSmall.invalidate();
    }


    ////////////////////////////////////////////////////////////////////////////////////

    private void hideChosenShape(){
        findViewById(R.id.currentShape).setVisibility(CanvasView.INVISIBLE);
        findViewById(R.id.currentShapeSmall).setVisibility(CanvasView.INVISIBLE);
    }

    private void allowValidate(){
        showChosenShapeAndColor();
        mValidateAvailable=true;
    }
    ////////////////////////////////////////////////////////////////////////////////////


    protected void setUpListeners(){

        findViewById(R.id.greenColor).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio && mShapeChosen) {
                    mMathShapeColorID=1;
                    setShapeColor();
                    allowValidate();
                    callShapeAudio();

                }
            }
        });

        findViewById(R.id.blueColor).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio && mShapeChosen) {
                    mMathShapeColorID=2;
                    setShapeColor();
                    allowValidate();
                    callShapeAudio();
                }
            }
        });

        findViewById(R.id.redColor).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio && mShapeChosen) {
                    mMathShapeColorID=3;
                    setShapeColor();
                    allowValidate();
                    callShapeAudio();
                }
            }
        });

        findViewById(R.id.yellowColor).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio && mShapeChosen) {
                    mMathShapeColorID=4;
                    setShapeColor();
                    allowValidate();
                    callShapeAudio();
                }
            }
        });

        findViewById(R.id.pinkColor).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio && mShapeChosen) {
                    mMathShapeColorID=5;
                    setShapeColor();
                    allowValidate();
                    callShapeAudio();
                }
            }
        });

        findViewById(R.id.orangeColor).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio && mShapeChosen) {
                    mMathShapeColorID=6;
                    setShapeColor();
                    allowValidate();
                    callShapeAudio();
                }
            }
        });

        findViewById(R.id.circle).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio) {
                    mShapeChosen = true;
                    mMathShapeTypeId=1;
                    fadeNotChosenShape();
                    showChosenShape();
                    playGeneralAudio(mTypeAudio.get(0));
                }
            }
        });

        findViewById(R.id.square).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio ) {
                    mShapeChosen = true;
                    mMathShapeTypeId=4;
                    fadeNotChosenShape();
                    showChosenShape();
                    playGeneralAudio(mTypeAudio.get(3));
                }
            }
        });

        findViewById(R.id.triangle).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio ) {
                    mShapeChosen = true;
                    mMathShapeTypeId=5;
                    fadeNotChosenShape();
                    showChosenShape();
                    playGeneralAudio(mTypeAudio.get(4));


                }
            }
        });

        findViewById(R.id.line).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio ) {
                    mShapeChosen = true;
                    mMathShapeTypeId=2;
                    fadeNotChosenShape();
                    showChosenShape();
                    playGeneralAudio(mTypeAudio.get(1));
                }
            }
        });

        findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio ) {
                    mShapeChosen = true;
                    mMathShapeTypeId=6;
                    fadeNotChosenShape();
                    showChosenShape();
                    playGeneralAudio(mTypeAudio.get(5));
                }
            }
        });

        findViewById(R.id.rectangle).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingAudio ) {
                    mShapeChosen = true;
                    mMathShapeTypeId=3;
                    fadeNotChosenShape();
                    showChosenShape();
                    playGeneralAudio(mTypeAudio.get(2));
                }
            }
        });

        findViewById(R.id.currentShape).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mValidateAvailable){
                    playShapeAudio(0);
                }
            }
        });

        findViewById(R.id.currentShapeSmall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mValidateAvailable){
                    playShapeAudio(1);
                }
            }
        });



    }


    ///////////////////////////////////////////////////////////////////////////////////////

    private void playShapeAudio(int mType){
        if(!mShapeAudioSmall.equals("") && mType==1) {
            playGeneralAudio(mShapeAudioSmall);
        }else if(!mShapeAudio.equals("") && mType==0) {
            playGeneralAudio(mShapeAudio);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable returnToNoValidate = new Runnable(){

        @Override
        public void run(){
            mAudioHandler.removeCallbacks(returnToNoValidate);
            mValidateAvailable=false;
            fadeShapes();
            unFadeColors();
            hideChosenShape();
        }
    };
    //////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mTypeAudio=new ArrayList<>();
        for(int i=0;i<6;i++){
            mTypeAudio.add("");
        }


        if(mCursor.moveToFirst()){
            do{

                int mShape=mCursor.getInt(mCursor.getColumnIndex("math_shape_type_id"));
                mTypeAudio.set(mShape-1,mCursor.getString(mCursor.getColumnIndex("math_shape_type_audio")));

                switch(mShape){
                    default:
                    case 1:
                        findViewById(R.id.circle).setVisibility(CanvasView.VISIBLE);
                        break;
                    case 2:
                        findViewById(R.id.line).setVisibility(CanvasView.VISIBLE);
                        break;
                    case 3:
                        findViewById(R.id.rectangle).setVisibility(CanvasView.VISIBLE);
                        break;
                    case 4:
                        findViewById(R.id.square).setVisibility(CanvasView.VISIBLE);
                        break;
                    case 5:
                        findViewById(R.id.triangle).setVisibility(CanvasView.VISIBLE);
                        break;
                    case 6:
                        findViewById(R.id.star).setVisibility(CanvasView.VISIBLE);
                        break;
                }
            }while(mCursor.moveToNext());
        }

        fadeShapes();
        fadeColors();
        setUpListeners();
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] mData;
        String mWhere="";
        String mOrderBy="";
        CursorLoader cursorLoader;
        switch(id) {
            default:
            case Constants.CURRENT_MATH_SHAPES: {


                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "2"}; //6
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
                        "3"}; //6
                mWhere="math_shape_type_id=" +mMathShapeTypeId+" "+
                        "AND math_shape_color_id="+mMathShapeColorID;
                mOrderBy="math_shape_size ASC ";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_SHAPES, mData, mWhere, null, mOrderBy);
                break;
            }
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
            case Constants.CURRENT_MATH_SHAPES:{
                processData(data);
                break;
            }
            case Constants.EXTRA_MATH_SHAPES:{
                mShapeAudio="";
                mShapeAudioSmall="";
                int mSize=0;
                if(data.moveToFirst()){
                    do {
                        if(mSize==0) {
                            mShapeAudioSmall = data.getString(data.getColumnIndex("math_shape_audio"));
                            playGeneralAudio(data.getString(data.getColumnIndex("math_shape_color_audio")));
                        }else{
                            mShapeAudio = data.getString(data.getColumnIndex("math_shape_audio"));
                        }
                        mSize++;
                    }while(data.moveToNext());
                }
                allowValidate();
                break;
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
