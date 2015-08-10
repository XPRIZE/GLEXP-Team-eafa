package com.motoli.apps.allsubjects;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;




import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.SQLException;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Launch_Platform extends Master_Parent implements
LoaderManager.LoaderCallbacks<Cursor>{
//	implements LoaderManager.LoaderCallbacks<Cursor> {  

	
	
	GridView sectionGrid;
	ArrayList<ArrayList<String>> AllGroups;
	ArrayList<ArrayList<String>> AllSections;
	ArrayList<String> allGPSsWA;
	ArrayList<ArrayList<String>> allCurrentSecitons;
	ArrayList<ArrayList<String>> allPhaseInfo;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
		setContentView(R.layout.launch_platform);
		appData.addToClassOrder(2);
		
		

		 
		 
		 
		 
		getLoaderManager().initLoader(MotoliConstants.APP_USER_CURRENT_GPL_LOADER, null, this);
		  
	
		 
			findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					playClickSound();
					moveBackwords();		
				}
			});

	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////

	
	public void onPause(){
		System.gc();
		super.onPause();
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void displayScreen(Cursor allSectionsCursor){
		allCurrentSecitons=new ArrayList<ArrayList<String>>();
		
		
		int currentGroup=0;
		int groupCount=0;
		
		if (allSectionsCursor.moveToFirst()) {
			int userCount=0;
			for (int j = 0; j < allSectionsCursor.getCount(); j++) {
				groupCount++;
				int presentGroup=allSectionsCursor.getInt(allSectionsCursor.getColumnIndex("group_id"));
				if(currentGroup==0)
					currentGroup=presentGroup;
				 
				Log.d(MotoliConstants.LOGCAT, String.valueOf(groupCount%3)+"...");

				if(currentGroup!=presentGroup){
					if((4-groupCount)!=0){
						for(int i=0; i<=(3-groupCount); i++){
							allCurrentSecitons.add(new ArrayList<String>());
							
							allCurrentSecitons.get(userCount).add("0");
							allCurrentSecitons.get(userCount).add(String.valueOf(currentGroup));
							allCurrentSecitons.get(userCount).add("");
							allCurrentSecitons.get(userCount).add("xpsec_blank.png");
							allCurrentSecitons.get(userCount).add("");
							allCurrentSecitons.get(userCount).add("");
							allCurrentSecitons.get(userCount).add("");
							allCurrentSecitons.get(userCount).add("");
							allCurrentSecitons.get(userCount).add("0");
							allCurrentSecitons.get(userCount).add("0");

							userCount++;
							//groupCount++;
						}
					}
				}
				if(groupCount>3 || currentGroup!=presentGroup ){
					groupCount=1;
				}		
//				if(groupNumber%3)
				currentGroup=allSectionsCursor.getInt(allSectionsCursor.getColumnIndex("group_id"));
				allCurrentSecitons.add(new ArrayList<String>());
				
				allCurrentSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("section_id"))); 			//0
				allCurrentSecitons.get(userCount).add(String.valueOf(currentGroup));															//1
				allCurrentSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("section_name")));			//2
				allCurrentSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("section_image")));			//3
				allCurrentSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("section_all_inclusive")));	//4
				allCurrentSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("section_order")));			//5
				allCurrentSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("lw_phase_id")));			//6
				allCurrentSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("level_number")));			//7
				allCurrentSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("app_user_current_level")));	//8
				allCurrentSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("activity_count")));			//9

				if(allCurrentSecitons.get(userCount).get(0).equals("3")){
				if(allCurrentSecitons.get(userCount).get(6).equals("1")){
					if(!allPhaseInfo.get(2).get(3).equals("0")){
						//allCurrentSecitons.get(userCount).set(7, allPhaseInfo.get(2).get(3));
						//allCurrentSecitons.get(userCount).set(8, allPhaseInfo.get(2).get(3));
						allCurrentSecitons.get(userCount).set(6, "2");
					}
				}
				}
				
				userCount++;
				allSectionsCursor.moveToNext();
			}
		}	
		
		AllGroups = new ArrayList<ArrayList<String>>(appData.getAllGroups());
		AllSections = new ArrayList<ArrayList<String>>(appData.getAllSections());


 
		sectionGrid = (GridView) findViewById(R.id.SectionGrid);

 
		sectionGrid.setAdapter(new Launch_Platform_Section(this, allCurrentSecitons));
 
		sectionGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				ArrayList list= (ArrayList) v.findViewById(R.id.grid_item_image).getTag();
				int currentGroup=Integer.parseInt((String) list.get(1));
				
				
				int currentLevel=Integer.parseInt(appData.getAllLevels().get(currentGroup));
				
				
				int currentSection=Integer.parseInt((String) list.get(0));
			
				
				int activitySize=appData.getAll_LSARs().get(currentLevel).get(currentSection).size();
				String activity_count=(String) list.get(9);
				String section_level=(String) list.get(8);
				
				if(!section_level.equals("0") && (!activity_count.equals("0") && !activity_count.equals("1"))){
					//Toast.makeText(getApplicationContext(),activity_count+" ACTIVITY COUNT", Toast.LENGTH_SHORT).show();
					//appData.setCurrentGroup_Section_Phase((String) list.get(1), (String) list.get(6), (String) list.get(0));
					move_to_activities((String) list.get(1), (String) list.get(6), (String) list.get(0), section_level);
				}else if(activity_count.equals("1")){
					process_single_activity_section(list);
				}
					
				
				/**/
			}
		});	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private void process_single_activity_section(ArrayList<String> sectionInfo){
		int currentGroup=Integer.parseInt((String) sectionInfo.get(1));
		int currentPhaase=Integer.parseInt((String) sectionInfo.get(6));
		int currentSection=Integer.parseInt((String) sectionInfo.get(0));
		
		Bundle bundle = new Bundle();
		 bundle.putInt("group_id", currentGroup);
		 bundle.putInt("phase_id", currentPhaase);
		 bundle.putInt("section_id", currentSection);
		 
		 
		 getLoaderManager().restartLoader(MotoliConstants.ACTIVITIES_LOADER, bundle, this);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private void goto_activity(String activity_id){
		
		int clickedActivityID= Integer.parseInt(activity_id);
		
		ArrayList<String> currentActivity=new ArrayList<String>();
		
		currentActivity.add(String.valueOf(clickedActivityID));
		
		appData.setCurrentActivity(currentActivity);
		
		
		switch(clickedActivityID){
			default:
				break;
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
			case 27:{
		  		Intent main = new Intent(this,Activity_LL01.class);
		  		startActivity(main);
		  		finish();
				break;
			}
			case 28:{
		  		Intent main = new Intent(this,Activity_LL02.class);
		  		startActivity(main);
		  		finish();
				break;
			}
		}
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private void move_to_activities(String group_id, String phase_id, String section_id, String current_level){
		appData.setCurrentGroup_Section_Phase(group_id, phase_id, section_id, current_level);
  		Intent main = new Intent(this,Activities_Platform.class);
  		startActivity(main);
  		finish();
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection =null;
		CursorLoader cursorLoader=null;
		switch(id){
			default:
			case MotoliConstants.SECTIONS_LOADER:{
				projection = new String[] {
						S4K_Database.Groups_Phase_Levels_Sections.GROUP_ID,
						S4K_Database.Groups_Phase_Levels_Sections.PHASE_ID,
						S4K_Database.Groups_Phase_Levels_Sections.LEVEL_NUMBER,
						S4K_Database.Groups_Phase_Levels_Sections.SECTION_ID,
						S4K_Database.Sections.SECTION_IMAGE,
						S4K_Database.Sections.SECTION_NAME};
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_GROUP_PHASE_LEVELS_SECTIONS_INDIVIDUAL, projection, appData.getCurrentUserID(), null, null);
				break;
			}
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
				  
				String selection=gps_tblname+"."+S4K_Database.Groups_Phase_Sections_Activities.GROUP_ID+"="+args.getInt("group_id")
						+" AND "+gps_tblname+"."+S4K_Database.Groups_Phase_Sections_Activities.PHASE_ID+"="+args.getInt("phase_id")
						+" AND "+gps_tblname+"."+S4K_Database.Groups_Phase_Sections_Activities.SECTION_ID+"="+args.getInt("section_id");
				String order=S4K_Database.Activities.ACTIVITIES_TABLE_NAME+"."+S4K_Database.Activities.ACTIVITY_ORDER+" ASC";
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_WRD_LTR_ACTIVITIES, projection, selection, null, order);
				break;
			}
			case MotoliConstants.APP_USER_CURRENT_GPL_LOADER:{
				projection = new String[]{
						S4K_Database.App_Users_Current_GPL.APP_USER_CURRENT_GPL_ID,
						S4K_Database.App_Users_Current_GPL.APP_USER_ID,
						S4K_Database.App_Users_Current_GPL.GROUP_ID,
						S4K_Database.App_Users_Current_GPL.PHASE_ID,
						S4K_Database.App_Users_Current_GPL.APP_USER_CURRENT_LEVEL};
				String selection=S4K_Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME+"."+S4K_Database.App_Users_Current_GPL.APP_USER_ID
						+"="+appData.getCurrentUserID();
				String order = S4K_Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME+"."+S4K_Database.App_Users_Current_GPL.GROUP_ID+" ASC, "
						+S4K_Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME+"."+S4K_Database.App_Users_Current_GPL.PHASE_ID+" ASC";
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_APP_USERS_CURRENT_GPL_INDIVIDUAL, projection, selection, null, order);		
				
				break;
			}

		}
		
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		appData = ((Motoli_Application) getApplicationContext());

		 switch(loader.getId()) {
			 default:
			 case MotoliConstants.SECTIONS_LOADER:{
				 displayScreen(data);
				 break;
			 }
			 case MotoliConstants.ACTIVITIES_LOADER:{
				 if(data.moveToFirst()){
					 goto_activity(data.getString(data.getColumnIndex(S4K_Database.Activities.ACTIVITY_ID)));
				 }
				 break;
			 }
			 case MotoliConstants.APP_USER_CURRENT_GPL_LOADER:{
				 allPhaseInfo=new ArrayList<ArrayList<String>>();
				 int activityCount=0;
				 while(data.moveToNext()){
					 allPhaseInfo.add(new ArrayList<String>());
					 allPhaseInfo.get(activityCount).add(data.getString(data.getColumnIndex(S4K_Database.App_Users_Current_GPL.APP_USER_ID)));
					 allPhaseInfo.get(activityCount).add(data.getString(data.getColumnIndex(S4K_Database.App_Users_Current_GPL.GROUP_ID)));
					 allPhaseInfo.get(activityCount).add(data.getString(data.getColumnIndex(S4K_Database.App_Users_Current_GPL.PHASE_ID)));
					 allPhaseInfo.get(activityCount).add(data.getString(data.getColumnIndex(S4K_Database.App_Users_Current_GPL.APP_USER_CURRENT_LEVEL)));
					 activityCount++;
				 }
				 getLoaderManager().restartLoader(MotoliConstants.SECTIONS_LOADER, null, this);
				 break;
			 }
		 }
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}
	
	 
		//launchSectionType=appState.get_LaunchSectionType();//needed to display screen correctly
		
		//classLevel=2;
		
		//setUserInfo();
		
		//setUpPage();
		//SelectListView= (ListView) findViewById(R.id.Select_List);
		
		
		/////////////////////////////////
		
		
		
		/*

	}
	
	*/

		
		
		/*
	
	 private void displayListView() {
		 
		 
	 }


	
	// ///////////////////////////////////////////////////////////////////////////////////////////

	// ///////////////////////////////////////////////////////////////////////////////////////////

	private void validate(){
	/*
		if(validateAvailable){
			((ImageButton) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_ok);
			appState.setAppSection(9);
			Intent intentSettings = new Intent(getApplicationContext(),
					Alpha_Learn_Activities.class);
			startActivity(intentSettings);
			finish();
		}
		
		/*
		if(currentUserId.equals("new")){
			
		}else if(currentUserId.equals("0")){
			gotoAppPage("0");
		}else{
			
		}
		*/
		
		/*
	}
	
	// ///////////////////////////////////////////////////////////////////////////////////////////


	
	///////////////////////////////////////////////////////////////////////////////////////
	//	pageSections 

	public void setUpPage(){

		
		
		  // The desired columns to be bound
		  String[] columns = new String[] {
		    S4K_Database.App_Users._ID,
		    S4K_Database.App_Users.APP_USER_ID,
		    S4K_Database.App_Users.APP_USER_NAME,
		    S4K_Database.App_Users.APP_USER_AVATAR
		  };
		 
		  // the XML defined views which the data will be bound to
		  int[] to = new int[] { 
		    R.id.id,
		    R.id.id,
		    R.id.name,
		    R.id.avatar,
		  };
		 
		  // create an adapter from the SimpleCursorAdapter
		  dataAdapter = new SimpleCursorAdapter(
		    this, 
		    R.layout.user_info, 
		    null, 
		    columns, 
		    to,
		    0);
		 
		  // get reference to the ListView
		  ListView listView = (ListView) findViewById(R.id.testlist);
		  // Assign adapter to ListView
		  listView.setAdapter(dataAdapter);
		  //Ensures a loader is initialized and active.
		  getLoaderManager().initLoader(0, null, this);
		  
		  
		  
				setUpLaunchGeneralSection();
				
	}//end public void setUpPage(){
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpUsersSection(){
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpGroupsSection(){
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpActivitiesSection(){
		
	}
	

	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void setUpLaunchGeneralSection(){
		ArrayList<ArrayList<String>> xp_sections = new ArrayList<ArrayList<String>>();
		int section_types=7;
		int sectionLocation=0;
		
		databaseOpenCloseFunctions(0);
		for(int i=0; i<=section_types; i++){
			mpCursor = mpDB.rawQuery("SELECT * FROM xp_sections WHERE xp_section_type="+i+" ORDER BY xp_section_id ASC",null);

			if(mpCursor.getCount()>0){
				mpCursor.moveToFirst();
				int sectionCount = mpCursor.getCount();
				
				
				for (int j = 0; j < sectionCount; j++) {
					xp_sections.add(new ArrayList<String>());
					xp_sections.get(sectionLocation).add(mpCursor.getString(mpCursor.getColumnIndex("xp_section_id")));
					xp_sections.get(sectionLocation).add(mpCursor.getString(mpCursor.getColumnIndex("xp_section_name")));
					xp_sections.get(sectionLocation).add(mpCursor.getString(mpCursor.getColumnIndex("xp_section_image")));
					sectionLocation++;
					mpCursor.moveToNext();
				}//end for (int i = 0; i < sectionCount; i++) {
				
				if(sectionCount%4!=0){
					for(int j=0;j<(4-(sectionCount%4));j++){
						xp_sections.add(new ArrayList<String>());
						xp_sections.get(sectionLocation).add("");
						xp_sections.get(sectionLocation).add("");
						xp_sections.get(sectionLocation).add("");
						sectionLocation++;
					}
				}//end if(sectionCount%4!=0){
			}//end if(mpCursor.getCount()>0){
	
		}//end for(int i=0; i<=section_types; i++){
		databaseOpenCloseFunctions(2);
	
		appState.set_sections_info(xp_sections);
		
		sectionGrid = (GridView) findViewById(R.id.SectionGrid);

		sectionGrid.setAdapter(new SectionGridAdapter(this,this, xp_sections));

		sectionGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(appState.sContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
				Select_Section(position);
			}
		});
		
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void Select_Section(int sectionChossen){
		switch(sectionChossen){
			default:
			case 0:{
				findViewById(R.id.Launch_Video).setVisibility(TableLayout.VISIBLE);
				VideoView vv = (VideoView)this.findViewById(R.id.soundVideo);
				String fileName = "android.resource://" + getPackageName() + "/" + R.raw.alphabet_en;
				vv.setVideoURI(Uri.parse(fileName));
				vv.start();
			

				break;
			}
			
				
		}
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	

	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////


	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	
	  public class SectionGridAdapter extends BaseAdapter {
			private Activity activity;

	    	private Context mContext;

			private  ArrayList<ArrayList<String>> sectionValues;

			public SectionGridAdapter(Activity a,Context context, ArrayList<ArrayList<String>> sectionValues) {
				this.mContext = context;
				this.sectionValues = sectionValues;
				activity = a;
				
			}

			@Override
			public int getCount() {
				return sectionValues.size();
			}

			public Object getItem(int position) {
				return position;
			}

			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) { 
				Motoli appState = ((Motoli) mContext.getApplicationContext());
				ArrayList<String> sectionInfo=appState.get_sections_info().get(position);
				
				View grid;
				String sectionName=sectionInfo.get(1);
				String imageName=sectionInfo.get(2).replace(".png", "").replace(".jpg", "");
				
				if(convertView==null){
					LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					grid = inflater.inflate(R.layout.launch_general_image, parent, false);
			        
				}else{
					grid = (View)convertView;
				}
				
				ImageView imageView = (ImageView)grid.findViewById(R.id.image);	
				if(imageName.equals("")){
					imageView.setImageResource(R.drawable.invisible_image);
				}else{
					Class<drawable> res = R.drawable.class;
					int drawableId = 0;
					Field field;
					
					try{
						//Toast.makeText(mContext, imageName, Toast.LENGTH_SHORT).show();
						field = res.getField(imageName);
						drawableId = field.getInt(null);
						imageView.setImageResource(drawableId);
		
					}catch (Exception e) {
						imageView.setImageResource(R.drawable.icon_read_act);
						Log.e("MyTag", "Failure to get drawable id.", e);
					}
				}
				return grid;
			}
			*/

		
}
