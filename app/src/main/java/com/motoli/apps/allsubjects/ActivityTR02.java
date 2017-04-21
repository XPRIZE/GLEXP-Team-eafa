package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
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
 * on 6/29/2016.
 */
public class ActivityTR02 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private GridView mIconGrid;



    private int mTracingType=0;

    private ArrayList<ArrayList<HashMap<String,String>>> mEntireTracingList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);

        setContentView(R.layout.activity_tr02);

        appData.addToClassOrder(28);
        appData.setActivityType(4);
        mCurrentGSP = new HashMap<>(appData.getCurrentGroup_Section_Phase());
        mIconGrid = (GridView) findViewById(R.id.iconGrid);
        mIconGrid.setVisibility(GridView.INVISIBLE);

        mTracingType=appData.getTracingType();
        getLoaderManager().initLoader(Constants.TRACING_LOADER, null, this);
        setUpListeners();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void displayScreen() {
        super.displayScreen();


        if( mEntireTracingList.size()  > mTracingType) {
            ActivityTRGridArea mAdapter = new ActivityTRGridArea(
                    this, mEntireTracingList.get(mTracingType));

            mIconGrid.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mIconGrid.setVisibility(GridView.VISIBLE);

            mIconGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    appData.placeCanvasList((HashMap<String, String>)
                            v.findViewById(R.id.tracingImage).getTag());


                    displayTracingScreen();

                }
            });
        }


    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void displayTracingScreen(){
        appData.setCurrentActivityName("ActivityTRCanvas");

        Intent main = new Intent(getBaseContext(),ActivityTRCanvas.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(main);
        finish();
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void processData(Cursor mCursor){
        mEntireTracingList=new ArrayList<>();
        int mNumber=0;
        int mProcessTracingType=-1;
        if(mCursor.moveToFirst()) {
            do {

                if((mProcessTracingType+1)
                        != mCursor.getInt(mCursor.getColumnIndex("tracing_type_id"))){
                    int mPastType=mProcessTracingType;
                    mProcessTracingType
                            = (mCursor.getInt(mCursor.getColumnIndex("tracing_type_id")) - 1);
                    mNumber=0;
                    mEntireTracingList.add(new ArrayList<HashMap<String, String>>());
                    if(mProcessTracingType>(mPastType+1)){
                        mEntireTracingList.add(new ArrayList<HashMap<String, String>>());
                    }
                }
                mEntireTracingList.get(mProcessTracingType).add(new HashMap<String, String>());
                mEntireTracingList.get(mProcessTracingType).get(mNumber).put("tracing_id",
                        mCursor.getString(mCursor.getColumnIndex("tracing_id")));

                mEntireTracingList.get(mProcessTracingType).get(mNumber).put("tracing_value",
                        mCursor.getString(mCursor.getColumnIndex("tracing_value")));

                mEntireTracingList.get(mProcessTracingType).get(mNumber).put("base_image",
                        mCursor.getString(mCursor.getColumnIndex("base_image")));
                mEntireTracingList.get(mProcessTracingType).get(mNumber).put("design_image",
                        mCursor.getString(mCursor.getColumnIndex("design_image")));
                mEntireTracingList.get(mProcessTracingType).get(mNumber).put("icon_image",
                        mCursor.getString(mCursor.getColumnIndex("icon_image")));
                mNumber++;
            } while (mCursor.moveToNext());
        }

        displayScreen();
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor>
    onCreateLoader(int id, Bundle bundle) {
        String[] mData;
        String mWhere="";
        String mOrderBy;
        CursorLoader cursorLoader;
        switch(id) {
            default:
            case Constants.TRACING_LOADER:{
                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "1"}; //6
                mOrderBy="tracing.tracing_type_id ASC, " +
                        "tracing.value_order ASC ";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_TRACING, mData, mWhere, null, mOrderBy);
                break;
            }
            case Constants.TRACING_SHAPES: {
                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "0"}; //6
                mWhere=" tracing.tracing_type_id==1 ";
                mOrderBy="tracing.value_order ASC ";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_TRACING, mData, mWhere, null, mOrderBy);
                break;
            }

            case Constants.TRACING_NUMBERS: {
                mData = new String[]{
                        appData.getCurrentActivity().get(0), //0
                        appData.getCurrentUserID(), //1
                        mCurrentGSP.get("group_id"), //2
                        mCurrentGSP.get("phase_id"), //3
                        mCurrentGSP.get("section_id"), //4
                        mCurrentGSP.get("current_level"),//5
                        "0"}; //6
                mWhere=" tracing.tracing_type_id==2 ";
                mOrderBy="tracing.value_order ASC ";

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_TRACING, mData, mWhere, null, mOrderBy);
                break;
            }
           /*  case Constants.TRACING_LOWER: {
                break;
            }
            case Constants.TRACING_UPPER: {
                break;
            }
            */
        }



        return cursorLoader;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor mCursor) {

        processData(mCursor);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners(){



        findViewById(R.id.shapesIcon).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                mTracingType=0;
                appData.setTracingType(mTracingType);
                mIconGrid.setVisibility(GridView.INVISIBLE);
                displayScreen();
                //chooseLoader();
            }
        });

        findViewById(R.id.numbersIcon).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                mTracingType=1;
                appData.setTracingType(mTracingType);
                mIconGrid.setVisibility(GridView.INVISIBLE);

                displayScreen();
            }
        });


        findViewById(R.id.lowerCaseAlphaIcon).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                mTracingType=2;
                appData.setTracingType(mTracingType);
                mIconGrid.setVisibility(GridView.INVISIBLE);
                displayScreen();
            }
        });

        findViewById(R.id.upperCaseAlphaIcon).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                mTracingType=3;
                appData.setTracingType(mTracingType);
                mIconGrid.setVisibility(GridView.INVISIBLE);
                displayScreen();
            }
        });

    }

    /**
     * Part of Project Motoli All Subjects
     * for Education Technology For Development
     * created by Aaron D Michaelis Borsay
     * on 8/12/2015.
     */
    private static class ActivityTRGridArea extends BaseAdapter {
            private Context context;
        //	private final String[] mobileValues;
            private ArrayList<HashMap<String,String>> mTracingData;

            static class ViewHolder {
                 TextView text;
                 ImageView icon;
                }




            private ActivityTRGridArea(Context context, ArrayList<HashMap<String, String>> mTracingData) {
                this.context = context;
                //this.mobileValues = mobileValues;
                this.mTracingData=mTracingData;

            }

            public View getView(int mPosition, View convertView, ViewGroup parent) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);


                ViewHolder holder;

                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.activity_tr_grid_area,null);
                    holder = new ViewHolder();
                    //holder.text = (TextView) convertView.findViewById(R.id.text);
                    holder.icon = (ImageView) convertView.findViewById(R.id.tracingImage);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                Log.d(Constants.LOGCAT, mTracingData.get(mPosition).get("tracing_value"));


                holder.icon.setImageResource(context.getResources()
                        .getIdentifier(mTracingData.get(mPosition).get("icon_image")
                                .replace(".png","").replace(".jpg","") ,
                                "drawable", context.getPackageName()));

                /*
                ArrayList mTempArray = new ArrayList();
                mTempArray.add(mTracingData.get(mPosition).get("tracing_id"));
                mTempArray.add(mTracingData.get(mPosition).get("tracing_value"));
                mTempArray.add(mTracingData.get(mPosition).get("base_image"));
                mTempArray.add(mTracingData.get(mPosition).get("design_image"));
                */
                holder.icon.setTag(mTracingData.get(mPosition));

                holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //holder.icon.setPadding(20, 20, 20, 20);

                holder.icon.destroyDrawingCache();

                return convertView;
            }






            @Override
            public int getCount() {
                return mTracingData.size();
            }

            @Override
            public Object getItem(int mPosition) {
                return null;
            }

            @Override
            public long getItemId(int mPosition) {
                return 0;
            }




        }
}
