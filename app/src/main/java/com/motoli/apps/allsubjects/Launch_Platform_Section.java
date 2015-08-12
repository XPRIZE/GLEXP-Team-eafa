package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.motoli.apps.allsubjects.R.color;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Launch_Platform_Section extends BaseAdapter {
		private Context context;
	//	private final String[] mobileValues;
		private ArrayList<ArrayList<String>> sectionData;
		
		static class ViewHolder {
			 TextView text;
			 ImageView icon;
			}
		
		public Launch_Platform_Section(Context context, ArrayList<ArrayList<String>> sectionData) {
			this.context = context;
			//this.mobileValues = mobileValues;
			this.sectionData=sectionData;

		}
	 
		public View getView(int position, View convertView, ViewGroup parent) {
	 		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	
			ViewHolder holder;
			 
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.launch_platform_section,parent, false);
				holder = new ViewHolder();
				//holder.text = (TextView) convertView.findViewById(R.id.text);
				holder.icon = (ImageView) convertView.findViewById(R.id.grid_item_image);
			 
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			if(sectionData.get(position).get(8).equals("0")){
				holder.icon.setAlpha(0.2f);
			}else{
				holder.icon.setAlpha(1.0f);
			}
			
			holder.icon.setImageResource(context.getResources().getIdentifier(sectionData.get(position).get(3).replace(".png","") , "drawable", context.getPackageName()));
			holder.icon.setTag(sectionData.get(position));
			
			holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
			holder.icon.setPadding(8, 8, 8, 8);
            
			holder.icon.destroyDrawingCache();
			 
			 



			return convertView;
		}
	 
		
		
		private Bitmap decodeFile(File f) {
		    try {
		        // Decode image size
		        BitmapFactory.Options o = new BitmapFactory.Options();
		        o.inJustDecodeBounds = true;
		        BitmapFactory.decodeStream(new FileInputStream(f), null, o);

		        // The new size we want to scale to
		        final int REQUIRED_SIZE=70;

		        // Find the correct scale value. It should be the power of 2.
		        int scale = 1;
		        while(o.outWidth / scale / 2 >= REQUIRED_SIZE && 
		              o.outHeight / scale / 2 >= REQUIRED_SIZE) {
		            scale *= 2;
		        }

		        // Decode with inSampleSize
		        BitmapFactory.Options o2 = new BitmapFactory.Options();
		        o2.inSampleSize = scale;
		        return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		    } catch (FileNotFoundException e) {}
		    return null;
		}
		
		
		
		@Override
		public int getCount() {
			return sectionData.size();
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


