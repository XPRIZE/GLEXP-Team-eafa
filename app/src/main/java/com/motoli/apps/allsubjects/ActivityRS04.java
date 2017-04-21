package com.motoli.apps.allsubjects;



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

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/11/2015.
 */
public class ActivityRS04 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<ArrayList<String>> mCurrentSyllables;

    private ArrayList<String> mSyllableInfo;

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

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor mCursor){
        mCurrentSyllables=new ArrayList<>();
        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
        int phonicCount=0;
        if (mCursor.moveToFirst()) {
            do {
                mCurrentGSP.put("current_level",
                        mCursor.getString(mCursor.getColumnIndex("app_user_current_level")));
                mCurrentSyllables.add(new ArrayList<String>());
                mCurrentSyllables.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("syllables_id")));       //0
                mCurrentSyllables.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("syllables_text")));     //1
                mCurrentSyllables.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("syllables_audio")));    //2
                mCurrentSyllables.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("syllable_color")));    //3
                mCurrentSyllables.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("level_number")));    //4
                mCurrentSyllables.get(phonicCount).add(
                        mCursor.getString(mCursor.getColumnIndex("app_user_current_level")));//5

                phonicCount++;
            } while (mCursor.moveToNext());
        }




        GridView mSyllableGrid = (GridView) findViewById(R.id.syllablesGrid);

        mAdapter=new ActivityRS04Icon(this, mCurrentSyllables);
        mSyllableGrid.setAdapter(mAdapter);
        mPlayingAudio=false;

        mSyllableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if(!mPlayingAudio) {
                    mSyllableInfo = new ArrayList<>((ArrayList)
                            v.findViewById(R.id.grid_item_text).getTag());

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
        mAudioHandler.postDelayed(runRestFontTypeFace, playGeneralAudio(mAudio)+500);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable runRestFontTypeFace = new Runnable(){
        @Override
        public void run(){
            for(int i=0; i<mCurrentSyllables.size();i++){
                mCurrentSyllables.get(i).set(3,mCurrentSyllables.get(i).get(3).replace("a",""));
            }
            mAdapter.notifyDataSetChanged();
            mPlayingAudio=false;
        }
    };

    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.CURRENT_SYLLABLE:{

                mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());
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


    /**
     * Part of Project Motoli All Subjects
     * for Education Technology For Development
     * created by Aaron D Michaelis Borsay
     * on 12/11/2015.
     */
    private static class ActivityRS04Icon extends BaseAdapter {
            private Context mContext;
            private ArrayList<ArrayList<String>> mSyllableData;

            static class ViewHolder {
                 TextView text;
                 ImageView icon;
                }

            private ViewHolder holder;


            private ActivityRS04Icon(Context mContext, ArrayList<ArrayList<String>> mSyllableData) {
                this.mContext = mContext;
                //this.mobileValues = mobileValues;
                this.mSyllableData=mSyllableData;

            }

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

               final Motoli_Application appData = ((Motoli_Application) mContext.getApplicationContext());



                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.activity_rs04_icon,parent, false);
                    holder = new ViewHolder();
                    holder.icon = (ImageView) convertView.findViewById(R.id.tracingImage);
                    holder.text =( TextView) convertView.findViewById(R.id.grid_item_text);

                    holder.text.setTypeface(appData.getCurrentFontType());
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }


                if(Integer.parseInt(mSyllableData.get(position).get(4))
                        <=Integer.parseInt(mSyllableData.get(position).get(5))) {
                    holder.icon.setAlpha(1.0f);
                    holder.text.setText(mSyllableData.get(position).get(1));

                }else {
                    holder.icon.setAlpha(0.1f);
                    holder.text.setText("");

                }
                holder.text.setTag(mSyllableData.get(position));

                holder.icon.setImageResource(mContext.getResources()
                        .getIdentifier("round_square_" + mSyllableData.get(position).get(3)
                                , "drawable", mContext.getPackageName()));

                holder.icon.setTag(mSyllableData.get(position));

                holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                holder.icon.setPadding(8, 8, 8, 8);

                holder.icon.destroyDrawingCache();


                return convertView;
            }

            @Override
            public int getCount() {
                return mSyllableData.size();
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