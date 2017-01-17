package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/11/2015.
 */

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySD07GridArea extends BaseAdapter {
    private Context mContext;
    private ArrayList<ArrayList<String>> mPhonicData;
    private  ViewHolder holder;
    static class ViewHolder {
         TextView text;
         ImageView icon;
    }



    public ActivitySD07GridArea(Context mContext, ArrayList<ArrayList<String>> mPhonicData) {
        this.mContext = mContext;
        this.mPhonicData=mPhonicData;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater
                = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


