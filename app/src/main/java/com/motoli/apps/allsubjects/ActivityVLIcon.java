package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/11/2015.
 */

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityVLIcon extends BaseAdapter {
        private Context mContext;
        private ArrayList<HashMap<String,String>> mVideoData;

        static class ViewHolder {
             TextView text;
             ImageView icon;
        }

        private  ViewHolder holder;

        public ActivityVLIcon(Context mContext, ArrayList<HashMap<String,String>> mVideoData) {
            this.mContext = mContext;
            this.mVideoData=mVideoData;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final Motoli_Application appData
                    = ((Motoli_Application) mContext.getApplicationContext());

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_vl_icon,parent, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.tracingImage);
                holder.text =( TextView) convertView.findViewById(R.id.grid_item_text);

                holder.text.setTypeface(appData.getCurrentFontType());
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(Integer.parseInt(mVideoData.get(position).get("level_number"))
                    <=Integer.parseInt(mVideoData.get(position).get("current_level"))) {
                holder.icon.setAlpha(1.0f);
                if(mVideoData.get(position).get("variable_text").matches("-?\\d+(\\.\\d+)?")){
                    holder.text.setTypeface(appData.getNumberFontTypeface());
                }else{
                    holder.text.setTypeface(appData.getCurrentFontType());
                }
                holder.text.setText(mVideoData.get(position).get("variable_text"));
            }else {
                holder.icon.setAlpha(0.1f);
                holder.text.setText("");
            }
            holder.text.setTag(mVideoData.get(position));

            holder.icon.setImageResource(mContext.getResources()
                    .getIdentifier("round_square_"+mVideoData.get(position).get("level_color"),
                            "drawable",
                            mContext.getPackageName()));
            holder.icon.setTag(mVideoData.get(position));
            holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.icon.setPadding(8, 8, 8, 8);
            holder.icon.destroyDrawingCache();
            return convertView;
        }

        @Override
        public int getCount() {
            return mVideoData.size();
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