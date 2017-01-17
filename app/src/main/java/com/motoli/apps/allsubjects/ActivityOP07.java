package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/28/2015.
 */
public class ActivityOP07 extends ActivitiesMasterParent
        implements   LoaderManager.LoaderCallbacks<Cursor> {

    private long mAudioDuration = 0;

    private ActivityOP07GridArea mAdapter;
    private GridView mVariableGrid;

    private ArrayList<HashMap<String,String>> mRoundsNumberSet;

    private int mType=0;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_op07);

        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        setBackground();

        appData.addToClassOrder(4);
        mInstructionAudio="info_qn01";

        ((TextView) findViewById(R.id.number0)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number1)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number2)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number3)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number4)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number5)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number6)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number7)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number8)).setTypeface(appData.getNumberFontTypeface());
        ((TextView) findViewById(R.id.number9)).setTypeface(appData.getNumberFontTypeface());


        findViewById(R.id.btnAddition).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mType=0;
                setBackground();
            }
        });


        findViewById(R.id.btnSubtraction).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mType=1;
                setBackground();
            }
        });


        findViewById(R.id.btnMultiplication).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mType=2;
                setBackground();
            }
        });



        getLoaderManager().initLoader(Constants.MATH_OPERATIONS, null, this);



    }


    private void setBackground(){
        int mNumber = 0;
        ArrayList<HashMap<String,String>> mNumberSet = new ArrayList<>();
        int mRow=1;
        int mColumn=0;
        boolean mFinished=false;
        String mSymbol="+";
       // findViewById(R.id.btnAddition).setAlpha(0.5f);
        findViewById(R.id.btnSubtraction).setAlpha(0.5f);
        findViewById(R.id.btnMultiplication).setAlpha(0.5f);
        if(mType==0){
          //  findViewById(R.id.btnAddition).setAlpha(1.0f);

            do {
                if (mColumn == 0) {
                    mNumberSet.add(new HashMap<String, String>());
                    mNumberSet.get(mNumber).put("type", "0");
                    mNumberSet.get(mNumber).put("equation", String.valueOf(mRow));
                    mNumberSet.get(mNumber).put("answer", String.valueOf(mRow));
                } else {
                    mNumberSet.add(new HashMap<String, String>());
                    mNumberSet.get(mNumber).put("type", "1");
                    mNumberSet.get(mNumber).put("equation", String.valueOf(mRow) + " "
                            + mSymbol + " " + String.valueOf(mColumn - 1)+"");
                    mNumberSet.get(mNumber).put("answer",  String.valueOf(mRow + (mColumn - 1)));
                }
                mColumn++;
                mNumber++;
                if (mColumn >= 11) {
                    mRow++;
                    mColumn=0;
                }
                if(mRow>=10){
                    mFinished=true;
                }
            }while(!mFinished);
        }else if(mType==1){
            findViewById(R.id.btnSubtraction).setAlpha(1.0f);
            mSymbol="-";
            do {
                if (mColumn == 0) {
                    mNumberSet.add(new HashMap<String, String>());
                    mNumberSet.get(mNumber).put("type", "0");
                    mNumberSet.get(mNumber).put("equation", String.valueOf(mRow));
                    mNumberSet.get(mNumber).put("answer", String.valueOf(mRow));
                } else {
                    if(mRow - (mColumn - 1)>=0) {
                        mNumberSet.add(new HashMap<String, String>());
                        mNumberSet.get(mNumber).put("type", "2");
                        mNumberSet.get(mNumber).put("equation", String.valueOf(mRow) + " "
                                + mSymbol + " " + String.valueOf(mColumn - 1) + "");
                        mNumberSet.get(mNumber).put("answer", String.valueOf(mRow - (mColumn - 1)));
                    }else{
                        mNumberSet.add(new HashMap<String, String>());
                        mNumberSet.get(mNumber).put("type", "2");
                        mNumberSet.get(mNumber).put("equation", "");
                        mNumberSet.get(mNumber).put("answer", "0");
                    }
                }
                mColumn++;
                mNumber++;
                if (mColumn >= 11) {
                    mRow++;
                    mColumn=0;
                }
                if(mRow>=10){
                    mFinished=true;
                }
            }while(!mFinished);
        }else if(mType==2) {
            findViewById(R.id.btnMultiplication).setAlpha(1.0f);
            mSymbol = "*";
            do {
                if (mColumn == 0) {
                    mNumberSet.add(new HashMap<String, String>());
                    mNumberSet.get(mNumber).put("type", "0");
                    mNumberSet.get(mNumber).put("equation", String.valueOf(mRow));
                    mNumberSet.get(mNumber).put("answer", String.valueOf(mRow));
                } else {
                    mNumberSet.add(new HashMap<String, String>());
                    mNumberSet.get(mNumber).put("type", "3");
                    mNumberSet.get(mNumber).put("equation", String.valueOf(mRow) + " "
                            + mSymbol + " " + String.valueOf(mColumn - 1) + "");
                    mNumberSet.get(mNumber).put("answer", String.valueOf(mRow * (mColumn - 1)));

                }
                mColumn++;
                mNumber++;
                if (mColumn >= 11) {
                    mRow++;
                    mColumn = 0;
                }
                if (mRow >= 10) {
                    mFinished = true;
                }
            } while (!mFinished);
        }

        mAdapter = new ActivityOP07GridArea(this, mNumberSet);

        mVariableGrid = (GridView) findViewById(R.id.variablesGrid);

        mVariableGrid.setAdapter(mAdapter);
        mVariableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                /*if (!mPlayingAudio) {
                    mClickedNumber = new Bundle
                            ((Bundle) v.findViewById(R.id.answer).getTag());
                }*/

            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor mCursor) {
        mRoundsNumberSet = new ArrayList<>();
        int mNumber = 0;


        if (mCursor.moveToFirst()) {
            do {
                mRoundsNumberSet.add(new HashMap<String,String>());
                mRoundsNumberSet.get(mNumber).put("math_numbers_id",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_id")));
                mRoundsNumberSet.get(mNumber).put("math_number",
                        mCursor.getString(mCursor.getColumnIndex("math_number")));
                mRoundsNumberSet.get(mNumber).put("math_number_style",
                        mCursor.getString(mCursor.getColumnIndex("math_number_style")));
                mRoundsNumberSet.get(mNumber).put("math_numbers_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_audio")));

                mRoundsNumberSet.get(mNumber).put("level_number",
                        mCursor.getString(mCursor.getColumnIndex("level_number")));
                mRoundsNumberSet.get(mNumber).put("level_color",
                        mCursor.getString(mCursor.getColumnIndex("level_color")));
                mRoundsNumberSet.get(mNumber).put("current_level",
                        mCurrentGSP.get("current_level"));
                mNumber++;
            } while (mCursor.moveToNext());
        }
        mType=0;
        setBackground();

    }


    /////////////////////////////////////////////////////////////////////////////////////////////

    private void playAudio(String mAudio) {
        mAudioDuration = playGeneralAudio(mAudio);
        mAudioHandler.postDelayed(runRestFontTypeFace, mAudioDuration + 100);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable runRestFontTypeFace = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < mRoundsNumberSet.size(); i++) {
                mRoundsNumberSet.get(i).put("level_color",
                        mRoundsNumberSet.get(i).get("level_color").replace("a", ""));
            }
            mAdapter.notifyDataSetChanged();
            //mCurrentPhonicText.setTypeface(appData.getCurrentFontType());
        }
    };


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] mData;
        String mWhere="";
        String mOrderBy="";
        CursorLoader cursorLoader;
        switch(id) {
            default:
            case Constants.MATH_OPERATIONS: {

                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "3"}; //6

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_MATH_NUMBERS,
                        mData, mWhere, null, mOrderBy);
                break;
            }
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            default:
            case Constants.MATH_OPERATIONS: {
                displayScreen(data);
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

///////////////////////////////////////////////////////////////////////////////////////////////////


}