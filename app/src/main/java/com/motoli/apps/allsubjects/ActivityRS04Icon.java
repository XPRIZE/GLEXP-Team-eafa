package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 12/11/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityRS04Icon extends BaseAdapter {
        private Context mContext;
    //	private final String[] mobileValues;
        private ArrayList<ArrayList<String>> mSyllableData;

        static class ViewHolder {
             TextView text;
             ImageView icon;
            }

        private  ViewHolder holder;


        public ActivityRS04Icon(Context mContext, ArrayList<ArrayList<String>> mSyllableData) {
            this.mContext = mContext;
            //this.mobileValues = mobileValues;
            this.mSyllableData=mSyllableData;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           final Motoli_Application appData = ((Motoli_Application) mContext.getApplicationContext());



            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_rs04_icon,parent, false);
                holder = new ViewHolder();
                //holder.text = (TextView) convertView.findViewById(R.id.text);
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

            //}


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


