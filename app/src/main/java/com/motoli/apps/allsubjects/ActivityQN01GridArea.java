package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 3/18/2015.
 */

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityQN01GridArea extends BaseAdapter {
        private Context mContext;
    //	private final String[] mobileValues;
        private ArrayList<Bundle> mNumberData;

        static class ViewHolder {
             TextView text;
             ImageView icon;
            MathOperationDots shapes;
            }

        private  ViewHolder holder;


        public ActivityQN01GridArea(Context mContext, ArrayList<Bundle> mNumberData) {
            this.mContext = mContext;
            //this.mobileValues = mobileValues;
            this.mNumberData=mNumberData;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           final Motoli_Application appData = ((Motoli_Application) mContext.getApplicationContext());



            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_qn01_grid_area,parent, false);
                holder = new ViewHolder();
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


            if(Integer.parseInt(mNumberData.get(position).getString("level_number"))
                    <=Integer.parseInt(mNumberData.get(position).getString("current_level"))) {
/*
                if(Integer.parseInt(mNumberData.get(position).getString("current_level"))>2  ||
                        !mNumberData.get(position).getString("math_number").equals("0")) {

                    */
                    holder.icon.setAlpha(1.0f);
                    holder.text.setText(mNumberData.get(position).getString("math_number"));
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

            if(Integer.parseInt(mNumberData.get(position).getString("math_number"))>=10){
                holder.shapes.setAllowTens(true);
            }else{
                holder.shapes.setAllowTens(false);
            }

            if(Integer.parseInt(mNumberData.get(position).getString("level_number"))
                    <=Integer.parseInt(mNumberData.get(position).getString("current_level")) &&
                    Integer.parseInt(mNumberData.get(position).getString("math_number"))<=160 &&
                    !mNumberData.get(position).getString("math_number").equals("0")){
                holder.shapes.setColor(7);
                holder.shapes.setUpDots(
                        Integer.parseInt(mNumberData.get(position).getString("math_number")));
                holder.shapes.invalidate();
                holder.shapes.setVisibility(MathOperationDots.VISIBLE);

                //holder.shapes.setNumberOfDots(
                //        Integer.parseInt(mNumberData.get(position).getString("math_number")));

            }else{
                holder.shapes.setVisibility(MathOperationDots.INVISIBLE);

            }


            holder.icon.setImageResource(mContext.getResources()
                    .getIdentifier("round_square_"
                            + mNumberData.get(position).getString("level_color")
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


