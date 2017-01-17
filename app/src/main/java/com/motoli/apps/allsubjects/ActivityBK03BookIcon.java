package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityBK03BookIcon extends BaseAdapter{

    private Activity activity;
    ///private ArrayList<HashMap<String, String>> data;
    private ArrayList<ArrayList<String>> mBooksData;

    private static LayoutInflater inflater = null;
    private Typeface currentFontType=null;
//	final ListAdapter ListUnit = getListAdapter();
    // public ImageLoader imageLoader;

    private Context mContext;

    static class ViewHolder {
        TextView text;
        ImageView icon;
    }

    private  ViewHolder holder;


    public ActivityBK03BookIcon(Context context, ArrayList<ArrayList<String>> books ) {
        this.mBooksData=books;
        this.mContext=context;

        // imageLoader=new ImageLoader(activity.getApplicationContext());
    }
     /**/
    public void setTypeface(Typeface cF){
        currentFontType = cF;
    }

    public int getCount() {
        return mBooksData.size();
    }

    public void clearData() {
        // clear the data
        mBooksData.clear();
    }
    public void resetData(ArrayList<ArrayList<String>> books){
        this.mBooksData=books;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Motoli_Application appData = ((Motoli_Application) mContext.getApplicationContext());
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        String currentUserTag=appData.getCurrentUserID();

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

        holder.icon.setImageResource(mContext.getResources()
                .getIdentifier("round_square_"+mBooksData.get(position).get(2)
                        , "drawable", mContext.getPackageName()));

        holder.icon.setTag(mBooksData.get(position));

        holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.icon.setPadding(8, 8, 8, 8);

        holder.icon.destroyDrawingCache();


        holder.text.setText(mBooksData.get(position).get(4));

        return convertView;


    }
}

