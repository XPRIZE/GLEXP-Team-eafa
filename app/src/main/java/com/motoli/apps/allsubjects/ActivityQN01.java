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
 * on 12/28/2015.
 */
public class ActivityQN01 extends ActivitiesMasterParent
        implements   LoaderManager.LoaderCallbacks<Cursor> {


    private ActivityQN01GridArea mAdapter;
    private Bundle mClickedNumber;
    private boolean mPlayingAudio = false;

    private ArrayList<HashMap<String,String>> mRoundsNumberSet;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_qn01);

        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        appData.addToClassOrder(4);

        findViewById(R.id.btnChangeCase).setVisibility(ImageView.INVISIBLE);

        mInstructionAudio="info_qn01";


        getLoaderManager().initLoader(Constants.MATH_NUMBERS_SET, null, this);



    }


    ///////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor mCursor) {
        mRoundsNumberSet = new ArrayList<>();
        int mNumber = 0;
        if (mCursor.moveToFirst()) {
            do {
                mRoundsNumberSet.add(new HashMap<String, String>());
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
                mRoundsNumberSet.get(mNumber).put("current_level",mCurrentGSP.get("current_level"));
                mNumber++;
            } while (mCursor.moveToNext());
        }

        mAdapter = new ActivityQN01GridArea(this, mRoundsNumberSet);

        GridView mVariableGrid = (GridView) findViewById(R.id.variablesGrid);


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
            if (mRoundsNumberSet.get(i).get("math_numbers_id")
                    .equals(mClickedNumber.getString("math_numbers_id"))) {
                mRoundsNumberSet.get(i).put("level_color",
                        mRoundsNumberSet.get(i).get("level_color").replace("a", ""));
                mRoundsNumberSet.get(i).put("level_color",
                        mRoundsNumberSet.get(i).get("level_color") + "a");
            } else {
                mRoundsNumberSet.get(i).put("level_color",
                        mRoundsNumberSet.get(i).get("level_color").replace("a", ""));
            }
        }
        playAudio(mClickedNumber.getString("math_numbers_audio"));
        mAdapter.notifyDataSetChanged();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void playAudio(String mAudio) {
        long mAudioDuration = playGeneralAudio(mAudio);
        mAudioHandler.postDelayed(runRestFontTypeFace, mAudioDuration + 100);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private Runnable runRestFontTypeFace = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < mRoundsNumberSet.size(); i++) {
                mRoundsNumberSet.get(i).put("level_color",
                        mRoundsNumberSet.get(i).get("level_color").replace("a", ""));
            }
            mAdapter.notifyDataSetChanged();
            mPlayingAudio = false;
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


    /**
     * Part of Project Motoli All Subjects
     * for Education Technology For Development
     * created by Aaron D Michaelis Borsay
     * on 3/18/2015.
     */

    private static class ActivityQN01GridArea extends BaseAdapter {
            private Context mContext;
            private ArrayList<HashMap<String,String>> mNumberData;

            private static class ViewHolder {
                TextView text;
                ImageView icon;
                MathOperationDots shapes;
            }



            private ActivityQN01GridArea(Context mContext, ArrayList<HashMap<String,String>> mNumberData) {
                this.mContext = mContext;
                this.mNumberData=mNumberData;

            }

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

               final Motoli_Application appData = ((Motoli_Application) mContext.getApplicationContext());



                ViewHolder holder = new ViewHolder();
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.activity_qn01_grid_area,parent, false);
                    //holder.text = (TextView) convertView.findViewById(R.id.text);
                    holder.icon = (ImageView) convertView.findViewById(R.id.numberBox);
                    holder.text =(TextView) convertView.findViewById(R.id.number0);
                    holder.shapes=(MathOperationDots) convertView.findViewById(R.id.shape0);
                    holder.shapes.setVisibility(MathOperationDots.INVISIBLE);
                    holder.text.setTypeface(appData.getNumberFontTypeface());
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }


                if(Integer.parseInt(mNumberData.get(position).get("level_number"))
                        <=Integer.parseInt(mNumberData.get(position).get("current_level"))) {
    /*
                    if(Integer.parseInt(mNumberData.get(position).getString("current_level"))>2  ||
                            !mNumberData.get(position).getString("math_number").equals("0")) {

                        */
                        holder.icon.setAlpha(1.0f);
                        holder.text.setText(mNumberData.get(position).get("math_number"));
                    /*
                    }else {

                        holder.icon.setalpha(0.1f);

                        holder.text.settext("");
                    }
                    */
                }else {
                    holder.icon.setAlpha(0.1f);
                    holder.text.setText("");

                }
                holder.text.setTag(mNumberData.get(position));



                if(Integer.parseInt(mNumberData.get(position).get("level_number"))
                        <=Integer.parseInt(mNumberData.get(position).get("current_level")) &&
                        Integer.parseInt(mNumberData.get(position).get("math_number"))<=160 &&
                        !mNumberData.get(position).get("math_number").equals("0")){
                    holder.shapes.setColor(7);
                    holder.shapes.setUpDots(
                            Integer.parseInt(mNumberData.get(position).get("math_number")));
                    holder.shapes.invalidate();
                    holder.shapes.setVisibility(MathOperationDots.VISIBLE);

                    //holder.shapes.setNumberOfDots(
                    //        Integer.parseInt(mNumberData.get(position).getString("math_number")));

                }else{
                    holder.shapes.setVisibility(MathOperationDots.INVISIBLE);

                }


                holder.icon.setImageResource(mContext.getResources()
                        .getIdentifier("round_square_"
                                + mNumberData.get(position).get("level_color")
                                , "drawable", mContext.getPackageName()));

                holder.icon.setTag(mNumberData.get(position));

                holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    holder.icon.setPadding(8, 8, 8, 8);

                    holder.icon.destroyDrawingCache();



                return convertView;
            }






            @Override
            public int getCount() {
                return mNumberData.size();
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