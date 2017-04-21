package com.motoli.apps.allsubjects;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
public class LaunchPlatformAwards extends BaseAdapter {
        private Context mContext;
        private ArrayList<HashMap<String,String>> mSectionData;

        static class ViewHolder {
             TextView text;
             ImageView icon;
            ProgressBar progress;
            }

        public LaunchPlatformAwards(Context mContext, ArrayList<HashMap<String,String>> mSectionData) {
            this.mContext = mContext;
            this.mSectionData=mSectionData;
        }

        public View getView(int mPosition, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.launch_platform_awards,parent, false);
                holder = new ViewHolder();
                //holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.icon = (ImageView) convertView.findViewById(R.id.tracingImage);

                holder.progress=(ProgressBar)convertView.findViewById(R.id.sectionProgress);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            int mVariableNumber=Integer.parseInt(
                    mSectionData.get(mPosition).get("variable_number"));
            int mMaxLevel=Integer.parseInt(
                    mSectionData.get(mPosition).get("max_level_number"));
            int mCurrentLevel=Integer.parseInt(
                    mSectionData.get(mPosition).get("current_level_number"));
            int mMaxCorrectCount=Integer.parseInt(
                    mSectionData.get(mPosition).get("max_correct_count"));
            int mCurrentNumber=0;
            if(mSectionData.get(mPosition).get("correct_count")!=null) {
                mCurrentNumber=Integer.parseInt(
                        mSectionData.get(mPosition).get("correct_count"));
            }
            switch(Integer.parseInt(mSectionData.get(mPosition).get("group_id"))){
                default:
                case 2:{

                    mVariableNumber=mMaxLevel;
                    mCurrentNumber=mCurrentLevel;
                    holder.progress.setProgressDrawable(ContextCompat.getDrawable(mContext,R.drawable.progress_letter_phonics));

                    break;
                }
                case 3:{

                    mVariableNumber=mMaxLevel;
                    mCurrentNumber=mCurrentLevel;

                    holder.progress.setProgressDrawable(
                            ContextCompat.getDrawable(mContext,R.drawable.progress_syllables));
                    break;
                }
                case 4:{
                    mVariableNumber=mVariableNumber*5;
                    holder.progress.setProgressDrawable(
                            ContextCompat.getDrawable(mContext,R.drawable.progress_words));
                    break;
                }
                case 6:{
                    mVariableNumber=mMaxLevel;
                    mCurrentNumber=mCurrentLevel;
                    holder.progress.setProgressDrawable(
                            ContextCompat.getDrawable(mContext,R.drawable.progress_shapes));
                    break;
                }
                case 7:{
                    mVariableNumber=mMaxLevel;
                    mCurrentNumber=mCurrentLevel;
                    holder.progress.setProgressDrawable(
                            ContextCompat.getDrawable(mContext,R.drawable.progress_numbers));
                    break;
                }
                case 8:{
                    mVariableNumber=mMaxLevel;
                    mCurrentNumber=mCurrentLevel;

                    holder.progress.setProgressDrawable(
                            ContextCompat.getDrawable(mContext,R.drawable.progress_math));
                    break;
                }
            }


            if(mCurrentNumber>0 && mMaxCorrectCount<Constants.INA_ROW_CORRECT
                    && !mSectionData.get(mPosition).get("group_id").equals("4")){
                mCurrentNumber--;
            }else if(mCurrentNumber>0 && mMaxCorrectCount<Constants.INA_ROW_CORRECT_WORDS){
                mCurrentNumber--;
            }


            holder.progress.setMax(mVariableNumber);
            holder.progress.setProgress(mCurrentNumber);

            holder.icon.setAlpha(1.0f);

            holder.icon.setImageResource(mContext.getResources().getIdentifier(
                    mSectionData.get(mPosition).get("groups_phase_awards_image")
                            .replace(".png", ""), "drawable", mContext.getPackageName()));
            holder.icon.setTag(mSectionData.get(mPosition));

            holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);

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