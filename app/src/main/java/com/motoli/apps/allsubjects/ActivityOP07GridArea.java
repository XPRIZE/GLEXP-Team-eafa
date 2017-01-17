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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityOP07GridArea extends BaseAdapter {
        private Context mContext;
    //	private final String[] mobileValues;
        private ArrayList<HashMap<String,String>> mNumberData;

        static class ViewHolder {
             TextView mAnswer;
            TextView mEquation;
             ImageView icon;
            MathOperationDots shapes;
            TextView mEqual;
            }

        private  ViewHolder holder;


        public ActivityOP07GridArea(Context mContext, ArrayList<HashMap<String,String>> mNumberData) {
            this.mContext = mContext;
            //this.mobileValues = mobileValues;
            this.mNumberData=mNumberData;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           final Motoli_Application appData =
                   ((Motoli_Application) mContext.getApplicationContext());



            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_op07_grid_area,parent, false);
                holder = new ViewHolder();
                //holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.icon = (ImageView) convertView.findViewById(R.id.numberBox);
                holder.mAnswer =(TextView) convertView.findViewById(R.id.answer);
                holder.mEquation = (TextView) convertView.findViewById(R.id.equation);
                holder.shapes=(MathOperationDots) convertView.findViewById(R.id.shape0);
                holder.mEqual = (TextView) convertView.findViewById(R.id.equal);
                holder.shapes.setVisibility(MathOperationDots.INVISIBLE);
                holder.mAnswer.setTypeface(appData.getNumberFontTypeface());
                holder.mEquation.setTypeface(appData.getNumberFontTypeface());

                holder.mEqual.setTypeface(appData.getNumberFontTypeface());

                convertView.setTag(holder);

                /*
                if(Integer.parseInt(mNumberData.get(position).get("answer"))<0){
                    holder.icon.setVisibility(ImageView.INVISIBLE);
                    holder.mAnswer.setVisibility(ImageView.INVISIBLE);
                    holder.mEqual.setVisibility(TextView.INVISIBLE);
                    holder.mEquation.setVisibility(TextView.INVISIBLE);
                    holder.shapes.setVisibility(MathOperationDots.INVISIBLE);
                }
                */
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            if(mNumberData.get(position).get("equation").equals("")){
                holder.icon.setAlpha(0.0f);
            }else {
                holder.icon.setAlpha(1.0f);
            }
                holder.mAnswer.setText(mNumberData.get(position).get("answer"));
                holder.mEquation.setText(mNumberData.get(position).get("equation"));

                holder.mAnswer.setTag(mNumberData.get(position));

                holder.shapes.setAllowTens(true);


                if (mNumberData.get(position).get("answer") != null) {
                    holder.shapes.setColor(7);

                    holder.shapes.setUpDots(
                            Integer.parseInt(mNumberData.get(position).get("answer")));
                    if(mNumberData.get(position).get("equation").equals("")){
                        holder.mAnswer.setText("");
                    }
                    holder.shapes.invalidate();
                    holder.shapes.setVisibility(MathOperationDots.VISIBLE);


                } else {
                    holder.shapes.setVisibility(MathOperationDots.INVISIBLE);

                }
/**/

                if (mNumberData.get(position).get("type").equals("0")) {
                    holder.mEquation.setText("");
                    holder.mEqual.setText("");
                    holder.icon.setImageResource(mContext.getResources()
                            .getIdentifier("round_square_white"
                                    , "drawable", mContext.getPackageName()));
                } else if (mNumberData.get(position).get("type").equals("1")) {
                    holder.icon.setImageResource(mContext.getResources()
                            .getIdentifier("round_square_addition"
                                    , "drawable", mContext.getPackageName()));
                } else if (mNumberData.get(position).get("type").equals("2")) {
                    if(mNumberData.get(position).get("equation").equals("")){
                        holder.mEqual.setText("");
                    }
                    holder.icon.setImageResource(mContext.getResources()
                            .getIdentifier("round_square_substitution"
                                    , "drawable", mContext.getPackageName()));

                } else if (mNumberData.get(position).get("type").equals("3")) {
                    holder.icon.setImageResource(mContext.getResources()
                            .getIdentifier("round_square_multiplication"
                                    , "drawable", mContext.getPackageName()));
                }
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


