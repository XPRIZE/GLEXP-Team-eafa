package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/11/2015.
 */


import java.util.ArrayList;
import java.util.HashMap;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class ActivitySD07 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private long mAudioDuration=0;

    private ActivitySD07GridArea mAdapter;
    private GridView mPhonicGrid;
    private ArrayList<String> mPhonicInfo;
    private ArrayList<ArrayList<String>> mAllPhonics;
    private ArrayList<ArrayList<String>> mCurrentPhonics;
    private boolean mPlayingAudio=false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sd07);
        appData.addToClassOrder(4);
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        mInstructionAudio="info_sd07";


        getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);


    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor mCursor){
        mCurrentPhonics=new ArrayList<ArrayList<String>>();
        mCurrentGSP=new HashMap<String,String>(appData.getCurrentGroup_Section_Phase());
        int phonicCount=0;
        if (mCursor.moveToFirst()) {
            do {
                mCurrentPhonics.add(new ArrayList<String>());
                mCurrentPhonics.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("phonic_id")));       //0
                mCurrentPhonics.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("phonic_text")));     //1
                mCurrentPhonics.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("phonic_grammar")));  //2
                mCurrentPhonics.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("phonic_audio")));    //3
                mCurrentPhonics.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("phonic_color")));    //4
                mCurrentPhonics.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("level_number")));    //5
                mCurrentPhonics.get(phonicCount).add(mCurrentGSP.get("current_level"));    //6

                phonicCount++;
            } while (mCursor.moveToNext());
        }

        mAdapter= new  ActivitySD07GridArea(this, mCurrentPhonics);

        mPhonicGrid = (GridView) findViewById(R.id.phonicsGrid);


        mPhonicGrid.setAdapter(mAdapter);
        mPlayingAudio=false;
        mPhonicGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(!mPlayingAudio) {
                    mPhonicInfo =new ArrayList<> ((ArrayList) v
                            .findViewById(R.id.grid_item_text).getTag());


                    String mLevelNumber = mPhonicInfo.get(5);
                    String mCurrentLevelNumber = mPhonicInfo.get(6);
                    if (Integer.parseInt(mLevelNumber)
                            <= Integer.parseInt(mCurrentLevelNumber)) {
                        mPlayingAudio = true;


                        playPhonic();

                    }
                }

            }
        });


    }


    private void playPhonic(){

        for(int i=0; i<mCurrentPhonics.size();i++){

            if(mCurrentPhonics.get(i).get(0).equals(mPhonicInfo.get(0))){
                mCurrentPhonics.get(i).set(4,mCurrentPhonics.get(i).get(4).replace("a",""));
                mCurrentPhonics.get(i).set(4,mCurrentPhonics.get(i).get(4)+"a");
            }else{
                mCurrentPhonics.get(i).set(4,mCurrentPhonics.get(i).get(4).replace("a",""));
            }
        }
        playAudio(mPhonicInfo.get(3));
        mAdapter.notifyDataSetChanged();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void playAudio(String mAudio ){
        mAudioDuration=playGeneralAudio(mAudio);
        mAudioHandler.postDelayed(runRestFontTypeFace, mAudioDuration+500);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private Runnable runRestFontTypeFace = new Runnable(){
        @Override
        public void run(){
            for(int i=0; i<mCurrentPhonics.size();i++){
                mCurrentPhonics.get(i).set(4,mCurrentPhonics.get(i).get(4).replace("a",""));
            }
            mAdapter.notifyDataSetChanged();
            mPlayingAudio=false;
            //mCurrentPhonicText.setTypeface(appData.getCurrentFontType());
        }
    };


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_PHONIC_LETTERS:{

                mCurrentGSP=new HashMap<String,String>(appData.getCurrentGroup_Section_Phase());
                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        "999",
                        };

                String selection="variable_phase_levels.group_id="+mCurrentGSP.get("group_id")
                        +" AND variable_phase_levels.phase_id="+mCurrentGSP.get("phase_id")
                        +" AND variable_type_relations.variable_type_id=3";

                String sortOrder="variable_phase_levels.level_number ASC, phonics.phonic_text ASC";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS, projection, selection, null, sortOrder);
                break;
            }
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
            case Constants.CURRENT_PHONIC_LETTERS:{
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