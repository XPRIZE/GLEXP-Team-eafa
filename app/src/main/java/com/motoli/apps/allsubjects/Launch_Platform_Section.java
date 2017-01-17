package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Launch_Platform_Section extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String,String>> mSectionData;

        static class ViewHolder {
             TextView text;
             ImageView icon;
            }

        public Launch_Platform_Section(Context context,
                                       ArrayList<HashMap<String,String>> mSectionData) {
            this.context = context;
            this.mSectionData=mSectionData;

        }

        public View getView(int mPosition, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = (LayoutInflater) 
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.launch_platform_section,parent, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.tracingImage);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Log.d(Constants.LOGCAT, mSectionData.get(mPosition).get("section_id"));

            if(mSectionData.get(mPosition).get("group_id").equals("2")
                    && Integer.parseInt(mSectionData.get(mPosition).get("variable_count"))>0){
                holder.icon.setAlpha(1.0f);
            }else if(mSectionData.get(mPosition).get("activity_count").equals("0") ||
                   mSectionData.get(mPosition).get("app_user_current_level").equals("0")){
                holder.icon.setAlpha(0.5f);
            }else if(mSectionData.get(mPosition).get("section_id").equals("8")){
                holder.icon.setAlpha(1.0f);
            }else if (mSectionData.get(mPosition).get("usable_section").equals("0")) {
                holder.icon.setAlpha(0.5f);

            }else if(mSectionData.get(mPosition).get("activity_count").equals("1") &&
                    mSectionData.get(mPosition).get("variable_count").equals("0")) {
                holder.icon.setAlpha(1.0f);

            //number and math section
            }else if(mSectionData.get(mPosition).get("section_id").equals("17") ||
                    mSectionData.get(mPosition).get("section_id").equals("20") ||
                    mSectionData.get(mPosition).get("section_id").equals("23")){
                holder.icon.setAlpha(1.0f);
            }else if(Integer.parseInt(mSectionData.get(mPosition).get("activity_count"))>1 &&
                    Integer.parseInt(mSectionData.get(mPosition).get("variable_count"))>=4){
                holder.icon.setAlpha(1.0f);
            }else{
                holder.icon.setAlpha(0.5f);
            }


            holder.icon.setImageResource(context.getResources()
                    .getIdentifier(mSectionData.get(mPosition).get("section_image")
                            .replace(".png","") , "drawable", context.getPackageName()));
            holder.icon.setTag(mSectionData.get(mPosition));

            holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.icon.setPadding(8, 8, 8, 8);
            
            holder.icon.destroyDrawingCache();





            return convertView;
        }






        @Override
        public int getCount() {
            return mSectionData.size();
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


