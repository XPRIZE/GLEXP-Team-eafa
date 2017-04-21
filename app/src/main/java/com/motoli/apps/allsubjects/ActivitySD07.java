package com.motoli.apps.allsubjects;



import java.util.ArrayList;
import java.util.HashMap;


import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/11/2015.
 */
public class ActivitySD07 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{


    private ActivitySD07GridArea mAdapter;
    private ArrayList<String> mPhonicInfo;
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
        mCurrentPhonics=new ArrayList<>();
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
        int phonicCount=0;
        if (mCursor.moveToFirst()) {
            do {
                mCurrentPhonics.add(new ArrayList<String>());
                mCurrentPhonics.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("phonic_id")));       //0
                mCurrentPhonics.get(phonicCount).add(
                mCursor.getString(mCursor.getColumnIndex("phonic_text")));     //1
                mCurrentPhonics.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("phonic_grammar")));  //2
                mCurrentPhonics.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("phonic_audio")));    //3
                mCurrentPhonics.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("phonic_color")));    //4
                mCurrentPhonics.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("level_number")));    //5
                mCurrentPhonics.get(phonicCount).add(
                        mCurrentGSP.get("current_level"));    //6

                phonicCount++;
            } while (mCursor.moveToNext());
        }

        mAdapter= new  ActivitySD07GridArea(this, mCurrentPhonics);

        GridView mPhonicGrid = (GridView) findViewById(R.id.phonicsGrid);


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
        mAudioHandler.postDelayed(runRestFontTypeFace, playGeneralAudio(mAudio)+500);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
    private Runnable runRestFontTypeFace = new Runnable(){
        @Override
        public void run(){
            for(int i=0; i<mCurrentPhonics.size();i++){
                mCurrentPhonics.get(i).set(4,mCurrentPhonics.get(i).get(4).replace("a",""));
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
            case Constants.CURRENT_PHONIC_LETTERS:{

                mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
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
                        AppProvider.CONTENT_URI_CURRENT_PHONIC_LETTERS,
                        projection, selection, null, sortOrder);
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


    private static class ActivitySD07GridArea extends BaseAdapter {
        private Context mContext;
        private ArrayList<ArrayList<String>> mPhonicData;
        private ViewHolder holder;
        static class ViewHolder {
             TextView text;
             ImageView icon;
        }



        private ActivitySD07GridArea(Context mContext, ArrayList<ArrayList<String>> mPhonicData) {
            this.mContext = mContext;
            this.mPhonicData=mPhonicData;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater
                    = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            final Motoli_Application appData
                   = ((Motoli_Application) mContext.getApplicationContext());
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_sd07_grid_area,parent, false);
                holder = new ViewHolder();
                holder.text =( TextView) convertView.findViewById(R.id.grid_item_text);
                holder.icon = (ImageView) convertView.findViewById(R.id.tracingImage);
                holder.text.setTypeface(appData.getCurrentFontType());
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(Integer.parseInt(mPhonicData.get(position).get(5))
                    <=Integer.parseInt(mPhonicData.get(position).get(6))) {
                holder.icon.setAlpha(1.0f);
                String mPhonicText=mPhonicData.get(position).get(1).replace("&quot;","'");
                holder.text.setText(mPhonicText);
            }else {
                holder.icon.setAlpha(0.1f);
                holder.text.setText("");
            }
            holder.text.setTag(mPhonicData.get(position));
            holder.icon.setImageResource(mContext.getResources()
                    .getIdentifier("round_square_"+mPhonicData.get(position).get(4)
                            , "drawable", mContext.getPackageName()));
            holder.icon.setTag(mPhonicData.get(position));
            holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.icon.setPadding(8, 8, 8, 8);
            holder.icon.destroyDrawingCache();
            return convertView;
        }

        @Override
        public int getCount() {
            return mPhonicData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
                return 0;
            }

        }
}