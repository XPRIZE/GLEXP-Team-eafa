package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
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

public class ActivityLT05 extends ActivitiesMasterParent  implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private long mAudioDuration=0;

    private boolean mRunningGather=true;
    private int mLowerCase=1;
    private ActivityLT05GridArea mAdapter;
    private GridView mLetterGrid;
    private HashMap<String,String> mLetterInfo;
    private ArrayList<HashMap<String,String>> mCurrentLetters;
    private boolean mPlayingAudio=false;

    @Override
    public void onResume() {
        super.onResume();
        if(mLowerCase==2) {
            findViewById(R.id.btnChangeCase).setVisibility(ImageView.VISIBLE);
        }else{
            findViewById(R.id.btnChangeCase).setVisibility(ImageView.INVISIBLE);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_lt05);
        appData.addToClassOrder(4);
        appData.setActivityType(3);
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());


        final ArrayList<HashMap<String,String>> mAllUserGPL
                = new ArrayList<>(appData.getAppUserCurrentGPL());

        mLowerCase=(mCurrentGSP.get("phase_id").equals("3")
                || mCurrentGSP.get("phase_id").equals("2") ? 2 : 1) ;


        findViewById(R.id.btnChangeCase).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mRunningGather) {
                    if (mLowerCase == 2) {
                        mCurrentGSP.put("phase_id","1");
                        mCurrentGSP.put("current_level",
                                mAllUserGPL.get(0).get("app_user_current_level"));
                        mLowerCase = 1;
                    } else {
                        mCurrentGSP.put("phase_id","2");
                        mCurrentGSP.put("current_level",
                                mAllUserGPL.get(1).get("app_user_current_level"));
                        mLowerCase = 2;
                    }
                    runDataGather();
                }
            }
        });

        mInstructionAudio="info_lt05";
        getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void runDataGather(){
        getLoaderManager().restartLoader(Constants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

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

        mAdapter= new  ActivityLT05GridArea(this, mCurrentLetters);

        mLetterGrid = (GridView) findViewById(R.id.lettersGrid);

        mLetterGrid.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mPlayingAudio=false;
        mLetterGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(!mPlayingAudio) {
                    mLetterInfo =new HashMap<>
                            ((HashMap) v.findViewById(R.id.grid_item_text).getTag());

                    String mLevelNumber = mLetterInfo.get("level_number");
                    String mCurrentLevelNumber = mLetterInfo.get("level_number");
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

        for(int i=0; i<mCurrentLetters.size();i++){
            if(mCurrentLetters.get(i).get("letter_id").equals(mLetterInfo.get("letter_id"))){
                mCurrentLetters.get(i).put("level_color",
                        mCurrentLetters.get(i).get("level_color").replace("a",""));
                mCurrentLetters.get(i).put("level_color",
                        mCurrentLetters.get(i).get("level_color")+"a");
            }else{
                mCurrentLetters.get(i).put("level_color",
                        mCurrentLetters.get(i).get("level_color").replace("a",""));
            }
        }
        playAudio(mLetterInfo.get("audio_url"));
        mAdapter.notifyDataSetChanged();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void playAudio(String mAudio ){
        mAudioDuration=playGeneralAudio(mAudio);
        mAudioHandler.postDelayed(runRestFontTypeFace, mAudioDuration+500);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable runRestFontTypeFace = new Runnable(){
        @Override
        public void run(){
            for(int i=0; i<mCurrentLetters.size();i++){
                mCurrentLetters.get(i).put("level_color",
                        mCurrentLetters.get(i).get("level_color").replace("a",""));
            }
            mAdapter.notifyDataSetChanged();
            mPlayingAudio=false;
        }
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.ACTIVITY_CURRENT_WRDS_LTRS:{
                mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

                mCurrentGSP.put("phase_id",String.valueOf(mLowerCase));

                String[] projection;
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        "999",
                        mCurrentGSP.get("current_level")
                };

                String selection="variable_phase_levels.group_id="+mCurrentGSP.get("group_id")
                        +" AND variable_phase_levels.phase_id="+mCurrentGSP.get("phase_id")
                        +" AND variable_phase_levels.level_number" +
                        "<="+mCurrentGSP.get("current_level");

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ACTIVITY_CURRENT_LETTERS,
                        projection, selection, null, null);

                break;
            }
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
            case Constants.ACTIVITY_CURRENT_WRDS_LTRS:{
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