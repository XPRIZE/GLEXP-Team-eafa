package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityTRGridArea extends BaseAdapter {
        private Context context;
    //	private final String[] mobileValues;
        private ArrayList<HashMap<String,String>> mTracingData;

        static class ViewHolder {
             TextView text;
             ImageView icon;
            }




        public ActivityTRGridArea(Context context, ArrayList<HashMap<String, String>> mTracingData) {
            this.context = context;
            //this.mobileValues = mobileValues;
            this.mTracingData=mTracingData;

        }

        public View getView(int mPosition, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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


