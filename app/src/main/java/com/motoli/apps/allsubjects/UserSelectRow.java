package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import java.util.ArrayList;



import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class UserSelectRow extends BaseAdapter{

	private Activity activity;
	///private ArrayList<HashMap<String, String>> data;
	private ArrayList<String> userCodes;
	private ArrayList<String> userNames;
	private ArrayList<String> userAvatars;

	private static LayoutInflater inflater = null;
	private Typeface currentFontType=null;
//	final ListAdapter ListUnit = getListAdapter();
	// public ImageLoader imageLoader;

	private Context context;

    
   
	public UserSelectRow(Activity a,ArrayList<String> userCode, ArrayList<String> userName,ArrayList<String> userAvatar,Context context) {
		activity = a;
		//data = d;
		userCodes = userCode;
		userNames=userName;
		userAvatars=userAvatar;
	    this.context=context;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		// imageLoader=new ImageLoader(activity.getApplicationContext());
	}
	 /**/
	public void setTypeface(Typeface cF){
		currentFontType = cF;
	}

	public int getCount() {
		return userCodes.size();
	}
	
	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Motoli_Application appState = ((Motoli_Application) context.getApplicationContext());
		
		String currentUserTag=appState.getCurrentUserID();
		

		
		View vi = convertView;
		if (convertView == null)
		vi = inflater.inflate(R.layout.user_select_row, null);
		TextView title = (TextView) vi.findViewById(R.id.UserSelectText); // unit name
		
		ImageView currentArrow = (ImageView) vi.findViewById(R.id.UserSelectArrow);
		vi.findViewById(R.id.UserSelectIcon).setTag(userCodes.get(position));

		Log.d("My Logs", "position: "+position);
		

			currentArrow.setVisibility(ImageView.INVISIBLE);

		
		
		if(currentFontType!=null){
			title.setTypeface(currentFontType);
		}
		
		
	//	HashMap<String, String> units = new HashMap<String, String>();

		
		
		// Setting all values in listview
		title.setText(userNames.get(position));
		
		//String actionTag=vi.findViewById(R.id.UserSelectIcon).getTag().toString();

		switch(Integer.valueOf(userAvatars.get(position))){
			default:
			case 0:{
				((ImageView) vi.findViewById(R.id.UserSelectIcon)).setImageResource(R.drawable.bnt_avatar_guest);
				break;
			}
			case 1:{
				((ImageView) vi.findViewById(R.id.UserSelectIcon)).setImageResource(R.drawable.bnt_avatar_1);
				break;
			}
			case 2:{
				((ImageView) vi.findViewById(R.id.UserSelectIcon)).setImageResource(R.drawable.bnt_avatar_2);
				break;
			}
			case 3:{
				((ImageView) vi.findViewById(R.id.UserSelectIcon)).setImageResource(R.drawable.bnt_avatar_3);
				break;
			}
			case 4:{
				((ImageView) vi.findViewById(R.id.UserSelectIcon)).setImageResource(R.drawable.bnt_avatar_4);
				break;
			}
			case 5:{
				((ImageView) vi.findViewById(R.id.UserSelectIcon)).setImageResource(R.drawable.bnt_avatar_5);
				break;
			}
			case 6:{
				((ImageView) vi.findViewById(R.id.UserSelectIcon)).setImageResource(R.drawable.bnt_avatar_6);
				break;
			}
			case 7:{
				((ImageView) vi.findViewById(R.id.UserSelectIcon)).setImageResource(R.drawable.bnt_avatar_new);
				break;
			}
		}

	//	((ImageView) vi.findViewById(R.id.UserSelectIcon)).setImageResource(resId);
		
		if(title.getText().equals("")){
			 vi.findViewById(R.id.UserSelectIcon).setVisibility(ImageView.INVISIBLE);
		}
		if(userCodes.get(position).equals("")){
			vi.findViewById(R.id.UserSelectIcon).setVisibility(ImageView.INVISIBLE);
			title.setVisibility(TextView.INVISIBLE);
		}
		//((TextView) vi.findViewById(R.id.unitNumber)).setText(unitNumbers.get(position));
	
		return vi;
	}
}

