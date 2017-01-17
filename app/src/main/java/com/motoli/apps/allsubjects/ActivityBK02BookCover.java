package com.motoli.apps.allsubjects;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/13/2015.
 */
public class ActivityBK02BookCover  extends BaseAdapter {
    private Context context;
    private ArrayList<ArrayList<String>> mBooksData;

    static class ViewHolder {
        TextView text;
        ImageView icon;
        ImageView cup;
    }

    public ActivityBK02BookCover(Context context, ArrayList<ArrayList<String>> mBooksData) {
        this.context = context;
        this.mBooksData=mBooksData;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_bk02_book_cover,parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.tracingImage);
            holder.cup=(ImageView) convertView.findViewById(R.id.readBook);
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

        if(mBooksData.get(position).get(8).equals("0")){
            holder.cup.setVisibility(ImageView.VISIBLE);
            holder.cup.setImageDrawable(context.getResources()
                    .getDrawable(R.drawable.btn_award_red));
        }else{
            holder.cup.setVisibility(ImageView.INVISIBLE);
        }
        holder.text.setText(Html.fromHtml(mBooksData.get(position).get(2)
                .replace("&sq:", "&rsquo;")));

        String mBookCoverPage=mBooksData.get(position).get(3)
                    .replace(".png", "").replace(".jpg", "");


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