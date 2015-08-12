package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;

public class Motoli_Application extends Application {

	
	protected static  Context sContext;

	private  Typeface currentFontType;
	private  Typeface currentFontTypeBold;
	private  Typeface currentFontTypeItalic;
	private  Typeface currentFontTypeItalicAndBold;

	private String currentUserID;
	private int currentLevel=1;
	
	private ArrayList<String>  currentActivity;
	
	private ArrayList<ArrayList<String>> allUsers;
	private ArrayList<ArrayList<String>> allActivities;
	private ArrayList<ArrayList<String>> allGroupsPhaseLevelsSections;
	private ArrayList<ArrayList<String>> allGroupsPhaseLevels;
	private ArrayList<ArrayList<String>> allGroupsPhase;
	private ArrayList<ArrayList<String>> allGroupsPhaseSectionsActivities;
	private ArrayList<String> allGPSsWA;
	
	private ArrayList<Integer> classOrder;
	private Map<String, Integer> classMap;
	
	
	private ArrayList<String> CurrentGroup_Section_Phase;

	private ArrayList<ArrayList<String>> allWordsPhaseLevels;
	
	private ArrayList<ArrayList<ArrayList<ArrayList<String>>>> all_LSARs;

	private ArrayList<ArrayList<String>> allGroups;
	private ArrayList<ArrayList<String>> allSecitons;
	private ArrayList<String> allLevels;
	
	public Motoli_Application() {
		// TODO Auto-generated constructor stu
		Log.d("here", "here again");
	
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		Motoli_Application.sContext = getApplicationContext();
		allLevels=new ArrayList<String>();
		classMap = new HashMap<String, Integer>();
		classOrder=new ArrayList<Integer>();
		
		currentFontType=Typeface.createFromAsset(getAssets(), "fonts/AndikaNewBasic-R.ttf");
		currentFontTypeBold=Typeface.createFromAsset(getAssets(), "fonts/AndikaNewBasic-B.ttf");
		currentFontTypeItalic=Typeface.createFromAsset(getAssets(), "fonts/AndikaNewBasic-I.ttf");
		currentFontTypeItalicAndBold=Typeface.createFromAsset(getAssets(), "fonts/AndikaNewBasic-BI.ttf");

		Log.d("here", "here");
	}
	

 public static Context getAppContext() {
     return Motoli_Application.sContext;
 }
 
	////////////////////////////////////////////////////////////////////////////////////////

	public void setCurrentFontType(Typeface setFontType){
		this.currentFontType=setFontType;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////

	public Typeface getCurrentFontType(){
		return this.currentFontType;
	}
	////////////////////////////////////////////////////////////////////////////////////////

	public Typeface getCurrentFontTypeBold(){
		return this.currentFontTypeBold;
	}
 ////////////////////////////////////////////////////////////////////////////////////////
  
	public void addToClassOrder(Integer classNumber){	 
		classOrder.add(classNumber);
	}

	////////////////////////////////////////////////////////////////////////////////////////

	public int getPreviousClass(){
		int coSize=classOrder.size();
		int previousClassNumber=classOrder.get(coSize-2);
		classOrder.remove(coSize-1);
		coSize=classOrder.size();
		classOrder.remove(coSize-1);
		return previousClassNumber;
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	public void setAllUsers(Cursor allUsersCursor){
		allUsers=new ArrayList<ArrayList<String>>();
		
		if (allUsersCursor.moveToFirst()) {
			for (int userCount = 0; userCount < allUsersCursor.getCount(); userCount++) {
				allUsers.add(new ArrayList<String>());
				
				allUsers.get(userCount).add(allUsersCursor.getString(allUsersCursor.getColumnIndex("app_user_id")));
				allUsers.get(userCount).add(allUsersCursor.getString(allUsersCursor.getColumnIndex("app_user_name")));
				allUsers.get(userCount).add(allUsersCursor.getString(allUsersCursor.getColumnIndex("app_user_avatar")));
				allUsersCursor.moveToNext();
			}
		}
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<ArrayList<String>> getAllUsers(){
		return this.allUsers;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public int getCurrentLevel(){
		return currentLevel;
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	public void setCurrentLevel(int currentLevel){
		this.currentLevel=currentLevel;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public ArrayList<String> getCurrentActivity(){
		return currentActivity;
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	public void setCurrentActivity(ArrayList<String> currentActivity){
		this.currentActivity=currentActivity;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////

	public void setAllGroups(Cursor allGroupsCursor){
		allGroups=new ArrayList<ArrayList<String>>();
		
		if (allGroupsCursor.moveToFirst()) {
			for (int userCount = 0; userCount < allGroupsCursor.getCount(); userCount++) {
				allGroups.add(new ArrayList<String>());
				
				allGroups.get(userCount).add(allGroupsCursor.getString(allGroupsCursor.getColumnIndex("group_id")));
				allGroups.get(userCount).add(allGroupsCursor.getString(allGroupsCursor.getColumnIndex("group_name")));
				allGroups.get(userCount).add(allGroupsCursor.getString(allGroupsCursor.getColumnIndex("group_code")));
				allGroupsCursor.moveToNext();
			}
		}	
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<ArrayList<String>> getAllGroups(){
		return this.allGroups;
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	public void setAllActivities(Cursor allActivitiesCursor){
		this.allActivities=new ArrayList<ArrayList<String>>();
		int currentIndex=0;
		while(allActivitiesCursor.moveToNext()){
			allActivities.add(new ArrayList<String>());
			allActivities.get(currentIndex).add(allActivitiesCursor.getString(allActivitiesCursor.getColumnIndex("activity_id")));
			allActivities.get(currentIndex).add(allActivitiesCursor.getString(allActivitiesCursor.getColumnIndex("activity_number")));
			allActivities.get(currentIndex).add(allActivitiesCursor.getString(allActivitiesCursor.getColumnIndex("activity_img")));
			allActivities.get(currentIndex).add(allActivitiesCursor.getString(allActivitiesCursor.getColumnIndex("activity_code")));
			allActivities.get(currentIndex).add(allActivitiesCursor.getString(allActivitiesCursor.getColumnIndex("activity_type")));
			allActivities.get(currentIndex).add(allActivitiesCursor.getString(allActivitiesCursor.getColumnIndex("activity_name")));
			allActivities.get(currentIndex).add(allActivitiesCursor.getString(allActivitiesCursor.getColumnIndex("activity_order")));
			currentIndex++;
		}

	}
	
	

	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public void setGroupsPhaseLevelsSections(Cursor allGroupsPhaseLevelsSectionsCursor){
		this.allGroupsPhaseLevelsSections=new ArrayList<ArrayList<String>>();
		int currentIndex=0;
		while(allGroupsPhaseLevelsSectionsCursor.moveToNext()){
			allGroupsPhaseLevelsSections.add(new ArrayList<String>());
			allGroupsPhaseLevelsSections.get(currentIndex).add(allGroupsPhaseLevelsSectionsCursor.getString(allGroupsPhaseLevelsSectionsCursor.getColumnIndex("gls_id")));
			allGroupsPhaseLevelsSections.get(currentIndex).add(allGroupsPhaseLevelsSectionsCursor.getString(allGroupsPhaseLevelsSectionsCursor.getColumnIndex("group_id")));
			allGroupsPhaseLevelsSections.get(currentIndex).add(allGroupsPhaseLevelsSectionsCursor.getString(allGroupsPhaseLevelsSectionsCursor.getColumnIndex("phase_id")));
			allGroupsPhaseLevelsSections.get(currentIndex).add(allGroupsPhaseLevelsSectionsCursor.getString(allGroupsPhaseLevelsSectionsCursor.getColumnIndex("level_number")));
			allGroupsPhaseLevelsSections.get(currentIndex).add(allGroupsPhaseLevelsSectionsCursor.getString(allGroupsPhaseLevelsSectionsCursor.getColumnIndex("section_id")));
			currentIndex++;
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////

	public void setGroupsPhaseLevelsUpdate(Cursor allGroupsPhaseLevelsCursor){
		this.allGroupsPhaseLevels=new ArrayList<ArrayList<String>>();
		int currentIndex=0;
		
		allGroupsPhaseLevelsCursor.moveToPosition(-1);
		
		while(allGroupsPhaseLevelsCursor.moveToNext()){
			if(allGroupsPhaseLevelsCursor.getColumnIndex("gpl_id")>=0){
				allGroupsPhaseLevels.add(new ArrayList<String>());
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("gpl_id")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("group_id")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("phase_id")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("level_number")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("group_phase_level_name")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("group_phase_level_description")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("group_phase_level_app_title")));
				currentIndex++;
			}
		}

	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public void setGroupsPhaseLevels(Cursor allGroupsPhaseLevelsCursor){
		this.allGroupsPhaseLevels=new ArrayList<ArrayList<String>>();
		int currentIndex=0;
		
		allGroupsPhaseLevelsCursor.moveToPosition(-1);
		
		while(allGroupsPhaseLevelsCursor.moveToNext()){
			if(allGroupsPhaseLevelsCursor.getColumnIndex("gpl_id")>=0){
				allGroupsPhaseLevels.add(new ArrayList<String>());
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("gpl_id")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("group_id")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("phase_id")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("level_number")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("group_phase_level_name")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("group_phase_level_description")));
				allGroupsPhaseLevels.get(currentIndex).add(allGroupsPhaseLevelsCursor.getString(allGroupsPhaseLevelsCursor.getColumnIndex("group_phase_level_app_title")));
				currentIndex++;
			}
		}

	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public void setGroupsPhaseSectionsActivities(Cursor allGroupsPhaseSectionsActivitiesCursor){
		this.allGroupsPhaseSectionsActivities=new ArrayList<ArrayList<String>>();
		this.allGPSsWA=new ArrayList<String>();
		
		int currentIndex=0;
		while(allGroupsPhaseSectionsActivitiesCursor.moveToNext()){
			allGroupsPhaseSectionsActivities.add(new ArrayList<String>());
			
			String group_id=allGroupsPhaseSectionsActivitiesCursor.getString(allGroupsPhaseSectionsActivitiesCursor.getColumnIndex("group_id"));
			String phase_id=allGroupsPhaseSectionsActivitiesCursor.getString(allGroupsPhaseSectionsActivitiesCursor.getColumnIndex("phase_id"));
			String section_id=allGroupsPhaseSectionsActivitiesCursor.getString(allGroupsPhaseSectionsActivitiesCursor.getColumnIndex("section_id"));
			String gps_id=group_id+phase_id+section_id;
			
			allGPSsWA.add(gps_id);
			
			allGroupsPhaseSectionsActivities.get(currentIndex).add(gps_id);
			allGroupsPhaseSectionsActivities.get(currentIndex).add(allGroupsPhaseSectionsActivitiesCursor.getString(allGroupsPhaseSectionsActivitiesCursor.getColumnIndex("gpsa_id")));
			allGroupsPhaseSectionsActivities.get(currentIndex).add(group_id);
			allGroupsPhaseSectionsActivities.get(currentIndex).add(phase_id);
			allGroupsPhaseSectionsActivities.get(currentIndex).add(section_id);
			allGroupsPhaseSectionsActivities.get(currentIndex).add(allGroupsPhaseSectionsActivitiesCursor.getString(allGroupsPhaseSectionsActivitiesCursor.getColumnIndex("activity_id")));
			currentIndex++;
		}

	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public ArrayList<ArrayList<String>> getGroupsPhaseSectionsActivities(){
		return allGroupsPhaseSectionsActivities;

	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public ArrayList<String> getGPSsWA(){
		return allGPSsWA;

	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public void setWordsPhaseLevels(Cursor allWordsPhaseLevelsCursor){
		this.allWordsPhaseLevels=new ArrayList<ArrayList<String>>();
		int currentIndex=0;
		while(allWordsPhaseLevelsCursor.moveToNext()){
			allWordsPhaseLevels.add(new ArrayList<String>());
			allWordsPhaseLevels.get(currentIndex).add(allWordsPhaseLevelsCursor.getString(allWordsPhaseLevelsCursor.getColumnIndex("words_phase_levels_id")));
			allWordsPhaseLevels.get(currentIndex).add(allWordsPhaseLevelsCursor.getString(allWordsPhaseLevelsCursor.getColumnIndex("word_id")));
			allWordsPhaseLevels.get(currentIndex).add(allWordsPhaseLevelsCursor.getString(allWordsPhaseLevelsCursor.getColumnIndex("group_id")));
			allWordsPhaseLevels.get(currentIndex).add(allWordsPhaseLevelsCursor.getString(allWordsPhaseLevelsCursor.getColumnIndex("phase_id")));
			allWordsPhaseLevels.get(currentIndex).add(allWordsPhaseLevelsCursor.getString(allWordsPhaseLevelsCursor.getColumnIndex("level_number")));
			currentIndex++;
		}

	}
	
	////////////////////////////////////////////////////////////////////////////////////////
		
	public void setGroupsPhase(Cursor allGroupsPhaseCursor){
		this.allGroupsPhase=new ArrayList<ArrayList<String>>();
		int currentIndex=0;
		while(allGroupsPhaseCursor.moveToNext()){
			allGroupsPhase.add(new ArrayList<String>());
			allGroupsPhase.get(currentIndex).add(allGroupsPhaseCursor.getString(allGroupsPhaseCursor.getColumnIndex("groups_phase_id")));
			allGroupsPhase.get(currentIndex).add(allGroupsPhaseCursor.getString(allGroupsPhaseCursor.getColumnIndex("group_id")));
			allGroupsPhase.get(currentIndex).add(allGroupsPhaseCursor.getString(allGroupsPhaseCursor.getColumnIndex("phase_id")));
			allGroupsPhase.get(currentIndex).add(allGroupsPhaseCursor.getString(allGroupsPhaseCursor.getColumnIndex("groups_phase_name")));
			currentIndex++;
		}

	}
	
		////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<ArrayList<String>> getAllActivities(){
		return allActivities;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public void setAll_LSARs(Cursor all_LSARCursor){
		this.all_LSARs=new ArrayList<ArrayList<ArrayList<ArrayList<String>>>>();
		
		int currentIndex=0;
		String currentLevel="0";
		int currentSection=0;
		String maxLevel="0";
		
		if(all_LSARCursor.getCount()!=0){
			all_LSARCursor.moveToFirst();
			do{
				if(!maxLevel.equals(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("level_number")))){
					maxLevel=all_LSARCursor.getString(all_LSARCursor.getColumnIndex("level_number"));
				}
			}while(all_LSARCursor.moveToNext());
			
			while(currentIndex<=Integer.parseInt(maxLevel)){
				all_LSARs.add(new ArrayList<ArrayList<ArrayList<String>>>());
				currentSection=0;
				while(currentSection<=21){
					all_LSARs.get(currentIndex).add(new ArrayList<ArrayList<String>>());
					currentSection++;
				}
				currentIndex++;
			}
			
			currentIndex=0;
			
			all_LSARCursor.moveToFirst();
			do{
			
				if(!currentLevel.equals(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("level_number")))){
					currentLevel=all_LSARCursor.getString(all_LSARCursor.getColumnIndex("level_number"));
				}
				
				if(!String.valueOf(currentSection).equals(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("section_id")))){
					currentSection=Integer.valueOf(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("section_id")));	
					currentIndex=0;
				}
				
			//	all_LSARs.add(new ArrayList<String>());
				currentIndex=all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).size();
				all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).add(new ArrayList<String>());
				all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).get(currentIndex).add(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("level_section_activity_id")));
				all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).get(currentIndex).add(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("level_number")));
				all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).get(currentIndex).add(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("section_id")));
				all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).get(currentIndex).add(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("activity_id")));
			}while(all_LSARCursor.moveToNext());
			Log.d(MotoliConstants.LOGCAT, "look");
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<ArrayList<ArrayList<ArrayList<String>>>> getAll_LSARs(){
		return all_LSARs;
	}
		

	
	////////////////////////////////////////////////////////////////////////////////////////

	public void setAllSections(Cursor allSectionsCursor){
		allSecitons=new ArrayList<ArrayList<String>>();
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
							allSecitons.add(new ArrayList<String>());
							
							allSecitons.get(userCount).add("0");
							allSecitons.get(userCount).add(String.valueOf(currentGroup));
							allSecitons.get(userCount).add("");
							allSecitons.get(userCount).add("xpsec_blank.png");
							allSecitons.get(userCount).add("");
							allSecitons.get(userCount).add("");
							allSecitons.get(userCount).add("");
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
				allSecitons.add(new ArrayList<String>());
				
				allSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("section_id"))); 			//0
				allSecitons.get(userCount).add(String.valueOf(currentGroup));															//1
				allSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("section_name")));			//2
				allSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("section_image")));			//3
				allSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("section_all_inclusive")));	//4
				allSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("section_order")));			//5
				allSecitons.get(userCount).add("");
				userCount++;
				allSectionsCursor.moveToNext();
			}
		}	
	}	

	
	////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<ArrayList<String>> getAllSections(){
		return this.allSecitons;
	}
	
	

	////////////////////////////////////////////////////////////////////////////////////////

	public void setCurrentUserID(String currentUserID){
		this.currentUserID=currentUserID;
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////

	public String getCurrentUserID(){
		return this.currentUserID;
	}

	
	////////////////////////////////////////////////////////////////////////////////////////

	public void setCurrentGroup_Section_Phase(String group_id, String phase_id, String section_id, String current_level){
		CurrentGroup_Section_Phase= new ArrayList<String>();
		CurrentGroup_Section_Phase.add(group_id);
		CurrentGroup_Section_Phase.add(phase_id);
		CurrentGroup_Section_Phase.add(section_id);
		CurrentGroup_Section_Phase.add(current_level);

		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<String> getCurrentGroup_Section_Phase(){
		return this.CurrentGroup_Section_Phase;
	}


	////////////////////////////////////////////////////////////////////////////////////////

	public void createCurrentDefaultLevels(){
		
		if(!allLevels.isEmpty())
			allLevels.clear();
		
	//	allLevels.
		for(int i=0; i<allGroups.size();i++){
			allLevels.add("1");			
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public ArrayList<String> getAllLevels(){
		return allLevels;
	}

	////////////////////////////////////////////////////////////////////////////////////////

}
