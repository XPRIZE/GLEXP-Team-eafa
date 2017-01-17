package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/11/2015.
 */


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

public class ActivityRS04 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private long mAudioDuration=0;


    private ArrayList<ArrayList<String>> mAllSyllables;
    private ArrayList<ArrayList<String>> mCurrentSyllables;

    private ArrayList<String> mSyllableInfo;

    private GridView mSyllableGrid;
    private ActivityRS04Icon mAdapter;
    private boolean mPlayingAudio=false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_rs04);
        appData.addToClassOrder(4);
        mInstructionAudio="info_rs04";
        findViewById(R.id.btnChangeCase).setVisibility(ImageView.INVISIBLE);


        startActivityHandler.postDelayed(startActivity, 500);
    }//end public void onCreate(Bundle savedInstanceState) {

    private Runnable startActivity = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void start(){
        getLoaderManager().initLoader(Constants.CURRENT_SYLLABLE, null, this);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor mCursor){
        mCurrentSyllables=new ArrayList<ArrayList<String>>();
        mCurrentGSP=new HashMap<String, String>(appData.getCurrentGroup_Section_Phase());
        int phonicCount=0;
        if (mCursor.moveToFirst()) {
            do {
                mCurrentGSP.put("current_level",
                        mCursor.getString(mCursor.getColumnIndex("app_user_current_level")));
                mCurrentSyllables.add(new ArrayList<String>());
                mCurrentSyllables.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("syllables_id")));       //0
                mCurrentSyllables.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("syllables_text")));     //1
                mCurrentSyllables.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("syllables_audio")));    //2
                mCurrentSyllables.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("syllable_color")));    //3
                mCurrentSyllables.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("level_number")));    //4
                mCurrentSyllables.get(phonicCount).add(mCursor.getString(mCursor.getColumnIndex("app_user_current_level")));    //5

                phonicCount++;
            } while (mCursor.moveToNext());
        }




        mSyllableGrid = (GridView) findViewById(R.id.syllablesGrid);

        mAdapter=new ActivityRS04Icon(this, mCurrentSyllables);
        mSyllableGrid.setAdapter(mAdapter);
        mPlayingAudio=false;

        mSyllableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if(!mPlayingAudio) {
                    mSyllableInfo = new ArrayList<String>((ArrayList) v.findViewById(R.id.grid_item_text).getTag());


                    String mLevelNumber = mSyllableInfo.get(4);
                    String mCurrentLevelNumber = mSyllableInfo.get(5);
                    if (Integer.parseInt(mLevelNumber)
                            <= Integer.parseInt(mCurrentLevelNumber)) {
                        mPlayingAudio = true;

                        playPhonic();
                    }
                }

            }
        });


    }


    /////////////////////////////////////////////////////////////////////////////////////////////
    private void playPhonic(){

        for(int i=0; i<mCurrentSyllables.size();i++){
            if(mCurrentSyllables.get(i).get(0).equals(mSyllableInfo.get(0))){
                mCurrentSyllables.get(i).set(3,mCurrentSyllables.get(i).get(3).replace("a",""));
                mCurrentSyllables.get(i).set(3,mCurrentSyllables.get(i).get(3)+"a");
            }else{
                mCurrentSyllables.get(i).set(3,mCurrentSyllables.get(i).get(3).replace("a",""));
            }
        }
        playAudio(mSyllableInfo.get(2));
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
            for(int i=0; i<mCurrentSyllables.size();i++){
                mCurrentSyllables.get(i).set(3,mCurrentSyllables.get(i).get(3).replace("a",""));
            }
            mAdapter.notifyDataSetChanged();
            mPlayingAudio=false;
            //mCurrentPhonicText.setTypeface(appData.getCurrentFontType());
        }
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_SYLLABLE:{

                mCurrentGSP=new HashMap<String,String>(appData.getCurrentGroup_Section_Phase());
                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        "999",
                        };

                String selection="variable_phase_levels.group_id=3"
                        +" AND variable_phase_levels.phase_id=2"
                        +" AND variable_type_relations.variable_type_id=5";


                String sortOrder="variable_phase_levels.level_number ASC, " +
                        "syllables.syllable_vowel ASC," +
                        "syllables.syllables_text ASC";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_ALL_SYLLABLES, projection, selection, null, sortOrder);
                break;
            }
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
            case Constants.CURRENT_SYLLABLE:{
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