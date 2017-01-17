package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/28/2015.
 */
public class ActivityQN01 extends ActivitiesMasterParent
        implements   LoaderManager.LoaderCallbacks<Cursor> {

    private long mAudioDuration = 0;

    private ActivityQN01GridArea mAdapter;
    private GridView mVariableGrid;
    private Bundle mClickedNumber;
    private ArrayList<ArrayList<String>> mAllPhonics;
    private boolean mPlayingAudio = false;

    private ArrayList<Bundle> mRoundsNumberSet;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_qn01);

        mCurrentGSP=new HashMap<String,String>(appData.getCurrentGroup_Section_Phase());

        appData.addToClassOrder(4);

        findViewById(R.id.btnChangeCase).setVisibility(ImageView.INVISIBLE);

        mInstructionAudio="info_qn01";


        getLoaderManager().initLoader(Constants.MATH_NUMBERS_SET, null, this);



    }


    ///////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor mCursor) {
        mRoundsNumberSet = new ArrayList<Bundle>();
        int mNumber = 0;
        if (mCursor.moveToFirst()) {
            do {
                mRoundsNumberSet.add(new Bundle());
                mRoundsNumberSet.get(mNumber).putString("math_numbers_id",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_id")));
                mRoundsNumberSet.get(mNumber).putString("math_number",
                        mCursor.getString(mCursor.getColumnIndex("math_number")));
                mRoundsNumberSet.get(mNumber).putString("math_number_style",
                        mCursor.getString(mCursor.getColumnIndex("math_number_style")));
                mRoundsNumberSet.get(mNumber).putString("math_numbers_audio",
                        mCursor.getString(mCursor.getColumnIndex("math_numbers_audio")));

                mRoundsNumberSet.get(mNumber).putString("level_number",
                        mCursor.getString(mCursor.getColumnIndex("level_number")));
                mRoundsNumberSet.get(mNumber).putString("level_color",
                        mCursor.getString(mCursor.getColumnIndex("level_color")));
                mRoundsNumberSet.get(mNumber).putString("current_level",mCurrentGSP.get("current_level"));
                mNumber++;
            } while (mCursor.moveToNext());
        }

        mAdapter = new ActivityQN01GridArea(this, mRoundsNumberSet);

        mVariableGrid = (GridView) findViewById(R.id.variablesGrid);


        mVariableGrid.setAdapter(mAdapter);
        mPlayingAudio = false;
        mVariableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (!mPlayingAudio) {
                    mClickedNumber = new Bundle
                            ((Bundle) v.findViewById(R.id.number0).getTag());


                    String mLevelNumber = mClickedNumber.getString("level_number");
                    String mCurrentLevelNumber = mClickedNumber.getString("current_level");
                    if (Integer.parseInt(mLevelNumber)
                            <= Integer.parseInt(mCurrentLevelNumber)) {
                        mPlayingAudio = true;


                        playPhonic();

                    }
                }

            }
        });


    }


    private void playPhonic() {

        for (int i = 0; i < mRoundsNumberSet.size(); i++) {
            if (mRoundsNumberSet.get(i).getString("math_numbers_id")
                    .equals(mClickedNumber.getString("math_numbers_id"))) {
                mRoundsNumberSet.get(i).putString("level_color", 
                        mRoundsNumberSet.get(i).getString("level_color").replace("a", ""));
                mRoundsNumberSet.get(i).putString("level_color",
                        mRoundsNumberSet.get(i).getString("level_color") + "a");
            } else {
                mRoundsNumberSet.get(i).putString("level_color",
                        mRoundsNumberSet.get(i).getString("level_color").replace("a", ""));
            }
        }
        playAudio(mClickedNumber.getString("math_numbers_audio"));
        mAdapter.notifyDataSetChanged();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void playAudio(String mAudio) {
        mAudioDuration = playGeneralAudio(mAudio);
        mAudioHandler.postDelayed(runRestFontTypeFace, mAudioDuration + 100);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private Runnable runRestFontTypeFace = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < mRoundsNumberSet.size(); i++) {
                mRoundsNumberSet.get(i).putString("level_color",
                        mRoundsNumberSet.get(i).getString("level_color").replace("a", ""));
            }
            mAdapter.notifyDataSetChanged();
            mPlayingAudio = false;
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
            case Constants.MATH_NUMBERS_SET: {

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
            case Constants.MATH_NUMBERS_SET: {
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