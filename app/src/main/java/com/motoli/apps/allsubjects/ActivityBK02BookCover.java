package com.motoli.apps.allsubjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/13/2015.
 */
public class ActivityBK02BookCover  extends BaseAdapter {
    private Context context;
    //	private final String[] mobileValues;
    private ArrayList<ArrayList<String>> mBooksData;

    static class ViewHolder {
        TextView text;
        ImageView icon;
    }




    public ActivityBK02BookCover(Context context, ArrayList<ArrayList<String>> mBooksData) {
        this.context = context;
        //this.mobileValues = mobileValues;
        this.mBooksData=mBooksData;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_bk02_book_cover,parent, false);
            holder = new ViewHolder();
            //holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.icon = (ImageView) convertView.findViewById(R.id.grid_item_image);

            holder.text=(TextView) convertView.findViewById(R.id.gridText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(mBooksData.get(position).get(7).equals("0")){
            holder.icon.setAlpha(0.1f);
        }else{
            holder.icon.setAlpha(1.0f);
        }
        holder.text.setText(mBooksData.get(position).get(2));


        String mBookCoverPage=MotoliConstants.RESOURCE_CODE+"_img_book_"+
                mBooksData.get(position).get(0)+"_cover";

        int checkExistence =context.getResources().getIdentifier(mBookCoverPage,
                "drawable", context.getPackageName());
        if(checkExistence==0){
            mBookCoverPage=mBooksData.get(position).get(3).replace(".png", "").replace(".jpg", "");
        }


        holder.icon.setImageResource(context.getResources().getIdentifier(mBookCoverPage,
                "drawable", context.getPackageName()));

        holder.icon.setTag(mBooksData.get(position));

        holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.icon.setPadding(8, 8, 8, 8);

        holder.icon.destroyDrawingCache();





        return convertView;
    }






    @Override
    public int getCount() {
        return mBooksData.size();
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