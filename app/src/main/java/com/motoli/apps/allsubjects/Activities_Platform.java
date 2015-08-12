package com.motoli.apps.allsubjects;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.motoli.apps.allsubjects.R.drawable;



import android.app.LoaderManager;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;



public class Activities_Platform extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor> {  

	
	
	ArrayList<ArrayList<String>> AllGroups;
	ArrayList<ArrayList<String>> AllSections;
	ArrayList<ArrayList<String>> AllGroupsPhaseSectionActivities;
	ArrayList<String> CurrentGroup_Section_Phase;
	ArrayList<ArrayList<String>> currentActivities;
	
		static final String[] MOBILE_OS = new String[] { 
			"Android", "iOS","Windows", "Blackberry" };
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
		setContentView(R.layout.activities_platform);
		appData = ((Motoli_Application) getApplicationContext());
		appData.addToClassOrder(3);
		AllGroups = new ArrayList<ArrayList<String>>(appData.getAllGroups());
		AllSections = new ArrayList<ArrayList<String>>(appData.getAllSections());
		AllGroupsPhaseSectionActivities = new ArrayList<ArrayList<String>>(appData.getGroupsPhaseSectionsActivities());

		CurrentGroup_Section_Phase = new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
		
		getLoaderManager().initLoader(MotoliConstants.UPDATE_LEVELS, null, this);

	    Toast.makeText(getApplicationContext(), "first", Toast.LENGTH_LONG).show();

	    ((ImageView) findViewById(R.id.Activity_1)).setVisibility(ImageView.INVISIBLE);
	    ((ImageView) findViewById(R.id.Activity_2)).setVisibility(ImageView.INVISIBLE);
	    ((ImageView) findViewById(R.id.Activity_3)).setVisibility(ImageView.INVISIBLE);
	    ((ImageView) findViewById(R.id.Activity_4)).setVisibility(ImageView.INVISIBLE);
	    ((ImageView) findViewById(R.id.Activity_5)).setVisibility(ImageView.INVISIBLE);
	    ((ImageView) findViewById(R.id.Activity_6)).setVisibility(ImageView.INVISIBLE);
		 
			findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					playClickSound();
					moveBackwords();		
				}
			});

	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_LONG).show();
	    
	
	}
	public void onPause(){
		System.gc();
		super.onPause();
		
	}
	 /////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection =null;
		CursorLoader cursorLoader=null;
		switch(id){
			default:
			case MotoliConstants.ACTIVITIES_LOADER:{
				projection = new String[] {
						S4K_Database.Groups_Phase_Sections_Activities.GPSA_ID,
						S4K_Database.Groups_Phase_Sections_Activities.GROUP_ID,
						S4K_Database.Groups_Phase_Sections_Activities.PHASE_ID,
						S4K_Database.Groups_Phase_Sections_Activities.SECTION_ID,
						S4K_Database.Groups_Phase_Sections_Activities.ACTIVITY_ID,
						S4K_Database.Activities.ACTIVITY_NAME,
						S4K_Database.Activities.ACTIVITY_IMG};
				  String gps_tblname=S4K_Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME;
				  
				String selection=gps_tblname+"."+S4K_Database.Groups_Phase_Sections_Activities.GROUP_ID+"="+CurrentGroup_Section_Phase.get(0)
						+" AND "+gps_tblname+"."+S4K_Database.Groups_Phase_Sections_Activities.PHASE_ID+"="+CurrentGroup_Section_Phase.get(1)
						+" AND "+gps_tblname+"."+S4K_Database.Groups_Phase_Sections_Activities.SECTION_ID+"="+CurrentGroup_Section_Phase.get(2);
				String order=S4K_Database.Activities.ACTIVITIES_TABLE_NAME+"."+S4K_Database.Activities.ACTIVITY_ORDER+" ASC";
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_WRD_LTR_ACTIVITIES, projection, selection, null, order);
				break;
			}
			case MotoliConstants.UPDATE_LEVELS:{
				projection = new String[] {
						CurrentGroup_Section_Phase.get(0),
						CurrentGroup_Section_Phase.get(1),
						CurrentGroup_Section_Phase.get(2),
						CurrentGroup_Section_Phase.get(3)};
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_GROUPS_PHASE_LEVELS_UPDATE, projection, appData.getCurrentUserID(), null, null);
				break;
			}			
			case MotoliConstants.APP_USER_CURRENT_GPL_LOADER:{
				projection = new String[] {
						S4K_Database.App_Users_Current_GPL.APP_USER_CURRENT_GPL_ID,
						S4K_Database.App_Users_Current_GPL.APP_USER_ID,
						S4K_Database.App_Users_Current_GPL.GROUP_ID,
						S4K_Database.App_Users_Current_GPL.PHASE_ID,
						S4K_Database.App_Users_Current_GPL.APP_USER_CURRENT_LEVEL
						};
				String gpl_tblname=S4K_Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME;
				  
				String selection=gpl_tblname+"."+S4K_Database.App_Users_Current_GPL.GROUP_ID+"="+args.getInt("group_id")
						+" AND "+gpl_tblname+"."+S4K_Database.App_Users_Current_GPL.PHASE_ID+"="+args.getInt("phase_id")
						+" AND "+gpl_tblname+"."+S4K_Database.App_Users_Current_GPL.APP_USER_ID+"="+args.getInt("section_id");
				
				String order=S4K_Database.Activities.ACTIVITIES_TABLE_NAME+"."+S4K_Database.Activities.ACTIVITY_ORDER+" ASC";
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_WRD_LTR_ACTIVITIES, projection, selection, null, order);
				break;
			}
		} 
		
		return cursorLoader;
		
	}

	
	@Override
	 public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		 switch(loader.getId()) {
			 default:
			 case MotoliConstants.ACTIVITIES_LOADER:{
				currentActivities=new ArrayList<ArrayList<String>>();
				int activityCount=0;
				while(data.moveToNext()){
					currentActivities.add(new ArrayList<String>());
					currentActivities.get(activityCount).add(data.getString(data.getColumnIndex(S4K_Database.Activities.ACTIVITY_ID)));
					currentActivities.get(activityCount).add(data.getString(data.getColumnIndex(S4K_Database.Activities.ACTIVITY_NAME)));
					currentActivities.get(activityCount).add(data.getString(data.getColumnIndex(S4K_Database.Activities.ACTIVITY_IMG)));
					activityCount++;
		
				}
				displayScreen();
				break;
			}
			case MotoliConstants.UPDATE_LEVELS:{
				
				data.moveToFirst();
				if(data.getString(data.getColumnIndex("current_phase")).equals("1")){
					CurrentGroup_Section_Phase.set(1, String.valueOf(Integer.parseInt(CurrentGroup_Section_Phase.get(1))+1));
					CurrentGroup_Section_Phase.set(3, "1");
				}else if(data.getString(data.getColumnIndex("current_phase")).equals("3")){
					CurrentGroup_Section_Phase.set(3,String.valueOf(Integer.parseInt(CurrentGroup_Section_Phase.get(3))+1));
				}
				/*
				CurrentGroup_Section_Phase.get(0),
				CurrentGroup_Section_Phase.get(1),
				CurrentGroup_Section_Phase.get(2),
				CurrentGroup_Section_Phase.get(3)
				*/
				
				appData.setCurrentGroup_Section_Phase(CurrentGroup_Section_Phase.get(0), CurrentGroup_Section_Phase.get(1),
							CurrentGroup_Section_Phase.get(2), CurrentGroup_Section_Phase.get(3));

				 appData.setGroupsPhaseLevels(data);
				 CurrentGroup_Section_Phase = new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
				 getLoaderManager().restartLoader(MotoliConstants.ACTIVITIES_LOADER, null, this);

				break;
			}
		 }
		
	 }
	 
	 @Override
	 public void onLoaderReset(Loader<Cursor> loader) {


	  
	 }
	 
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void displayScreen(){
		
		for(int i=0; i<currentActivities.size(); i++){
			String imageName=currentActivities.get(i).get(2).replace(".jpg", "").replace(".png", "");
			
			Class<drawable> res = R.drawable.class;
			int drawableId = 0;
			Field field;
			

			switch(i){
				default:
				case 0:{
					 ((ImageView) findViewById(R.id.Activity_1)).setVisibility(ImageView.VISIBLE);
					((ImageView) findViewById(R.id.Activity_1)).setTag(currentActivities.get(i).get(0));
					try{
						field = res.getField(imageName);
						drawableId = field.getInt(null);
						((ImageView) findViewById(R.id.Activity_1)).setImageResource(drawableId);
						
					}catch (Exception e) {
						Log.e("MyTag", "Failure to get drawable id.", e);
					}
					break;
				}
				case 1:{
					 ((ImageView) findViewById(R.id.Activity_2)).setVisibility(ImageView.VISIBLE);
					 ((ImageView) findViewById(R.id.Activity_2)).setTag(currentActivities.get(i).get(0));
					try{
						field = res.getField(imageName);
						drawableId = field.getInt(null);
						((ImageView) findViewById(R.id.Activity_2)).setImageResource(drawableId);
						
					}catch (Exception e) {
						Log.e("MyTag", "Failure to get drawable id.", e);
					}
					break;
				}
				case 2:{
					 ((ImageView) findViewById(R.id.Activity_3)).setVisibility(ImageView.VISIBLE);
					 ((ImageView) findViewById(R.id.Activity_3)).setTag(currentActivities.get(i).get(0));
					try{
						field = res.getField(imageName);
						drawableId = field.getInt(null);
						((ImageView) findViewById(R.id.Activity_3)).setImageResource(drawableId);
						
					}catch (Exception e) {
						Log.e("MyTag", "Failure to get drawable id.", e);
					}
					break;
				}
				case 3:{
					 ((ImageView) findViewById(R.id.Activity_4)).setVisibility(ImageView.VISIBLE);
					 ((ImageView) findViewById(R.id.Activity_4)).setTag(currentActivities.get(i).get(0));
					try{
						field = res.getField(imageName);
						drawableId = field.getInt(null);
						((ImageView) findViewById(R.id.Activity_4)).setImageResource(drawableId);
						
					}catch (Exception e) {
						Log.e("MyTag", "Failure to get drawable id.", e);
					}
					break;
				}
				case 4:{
					 ((ImageView) findViewById(R.id.Activity_5)).setVisibility(ImageView.VISIBLE);
					 ((ImageView) findViewById(R.id.Activity_5)).setTag(currentActivities.get(i).get(0));
					try{
						field = res.getField(imageName);
						drawableId = field.getInt(null);
						((ImageView) findViewById(R.id.Activity_5)).setImageResource(drawableId);
						
					}catch (Exception e) {
						Log.e("MyTag", "Failure to get drawable id.", e);
					}
					break; 
				}
				case 5:{
					 ((ImageView) findViewById(R.id.Activity_6)).setVisibility(ImageView.VISIBLE);
					 ((ImageView) findViewById(R.id.Activity_6)).setTag(currentActivities.get(i).get(0));
					try{
						field = res.getField(imageName);
						drawableId = field.getInt(null);
						((ImageView) findViewById(R.id.Activity_6)).setImageResource(drawableId);
						
					}catch (Exception e) {
						Log.e("MyTag", "Failure to get drawable id.", e);
					}
					break;
				}
				/*
				case 6:{
					((ImageView) findViewById(R.id.Activity_7)).setTag(currentActivities.get(i).get(0));
					try{
						field = res.getField(imageName);
						drawableId = field.getInt(null);
						((ImageView) findViewById(R.id.Activity_7)).setImageResource(drawableId);
						
					}catch (Exception e) {
						Log.e("MyTag", "Failure to get drawable id.", e);
					}
					break;
				}	
				case 7:{
					((ImageView) findViewById(R.id.Activity_8)).setTag(currentActivities.get(i).get(0));
					try{
						field = res.getField(imageName);
						drawableId = field.getInt(null);
						((ImageView) findViewById(R.id.Activity_8)).setImageResource(drawableId);
						
					}catch (Exception e) {
						Log.e("MyTag", "Failure to get drawable id.", e);
					}
					break;
				}
				*/					
			}//end switch(i){
		}//end for(int i=0; i<currentActivities.size(); i++){
		
		activityButtonListener();
		
	}//end public void displayScreen(){
	

	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void activityButtonListener(){
		
		findViewById(R.id.Activity_1).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				processActivityButton(findViewById(R.id.Activity_1).getTag());
			}
		});
		
		findViewById(R.id.Activity_2).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				processActivityButton(findViewById(R.id.Activity_2).getTag());
				
			}
		});
		
		findViewById(R.id.Activity_3).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				processActivityButton(findViewById(R.id.Activity_3).getTag());
			
				
			}
		});
		
		findViewById(R.id.Activity_4).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				processActivityButton(findViewById(R.id.Activity_4).getTag());
		
				
			}
		});
		
		findViewById(R.id.Activity_5).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				processActivityButton(findViewById(R.id.Activity_5).getTag());
		
				
			}
		});
		
		findViewById(R.id.Activity_6).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				processActivityButton(findViewById(R.id.Activity_6).getTag());
		
				
			}
		});
		/*
		findViewById(R.id.Activity_7).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				processActivityButton(findViewById(R.id.Activity_7).getTag());
			
				
			}
		});
		
		findViewById(R.id.Activity_8).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				processActivityButton(findViewById(R.id.Activity_8).getTag());
				
				
			}
		});
		*/

		
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void processActivityButton(Object imageTag){
		int clickedActivityID= Integer.parseInt(imageTag.toString());
				
		ArrayList<String> currentActivity=new ArrayList<String>();
		
		currentActivity.add(String.valueOf(clickedActivityID));
		
		appData.setCurrentActivity(currentActivity);
		
		switch(clickedActivityID){
			default:{
				break;
			}
			case 6:{
				Intent main = new Intent(this,Activity_WD07.class);
		  		startActivity(main);
		  		finish();
				break;
			}
			case 20:{
		  		Intent main = new Intent(this,Activity_LE02.class);
		  		startActivity(main);
		  		finish();
				break;
			}//end case 20 LE02
			case 22:{
		  		Intent main = new Intent(this,Activity_TL01.class);
		  		startActivity(main);
		  		finish();
				break;
			}//end case 22 TL01
			case 2:{
		  		Intent main = new Intent(this,Activity_WD01.class);
		  		startActivity(main);
		  		finish();
				break;
			}//end case 2 WD01	
			case 4:{
		  		Intent main = new Intent(this,Activity_WD04.class);
		  		startActivity(main);
		  		finish();
				break;
			}//end case 2 WD01	
			case 8:{
		  		Intent main = new Intent(this,Activity_WD08.class);
		  		startActivity(main);
		  		finish();
				break;
			}//end case 8 WD08	
		}//end switch(clickedActivityID){
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

		
}
