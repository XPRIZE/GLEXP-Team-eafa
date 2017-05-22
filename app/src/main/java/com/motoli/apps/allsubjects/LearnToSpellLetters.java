package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 5/5/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class LearnToSpellLetters extends BaseAdapter {
        private Context mContext;
        private ArrayList<HashMap<String,String>> mLetterData;

        static class ViewHolder {
             TextView text;
             ImageView icon;
            }

        public LearnToSpellLetters(Context mContext, ArrayList<HashMap<String,String>> mLetterData) {
            this.mContext = mContext;
            this.mLetterData=mLetterData;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater mInflater
                    = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Motoli_Application appData
                   = ((Motoli_Application) mContext.getApplicationContext());
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_lt05_grid_area,parent, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.tracingImage);
                holder.text =( TextView) convertView.findViewById(R.id.grid_item_text);

                holder.text.setTypeface(appData.getCurrentFontType());
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(Integer.parseInt(mLetterData.get(position).get("level_number"))
                    <=Integer.parseInt(mLetterData.get(position).get("level_number"))) {
                holder.icon.setAlpha(1.0f);
                holder.text.setText(mLetterData.get(position).get("letter_text"));
            }else {
                holder.icon.setAlpha(0.1f);
                holder.text.setText("");

            }
            holder.text.setTag(mLetterData.get(position));

            holder.icon.setImageResource(mContext.getResources()
                    .getIdentifier("round_square_"+mLetterData.get(position).get("level_color")
                            , "drawable", mContext.getPackageName()));

            holder.icon.setTag(mLetterData.get(position));

            holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.icon.setPadding(4, 4, 4, 4);
            
            holder.icon.destroyDrawingCache();

            return convertView;
        }






        @Override
        public int getCount() {
            return mLetterData.size();
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


