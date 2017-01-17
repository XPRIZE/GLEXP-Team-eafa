package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Motoli_Application_copy extends Application {


    protected static  Context sContext;

    private  Typeface currentFontType;
    private  Typeface currentFontTypeBold;
    private  Typeface currentFontTypeItalic;
    private  Typeface currentFontTypeItalicAndBold;

    private String currentUserID;
    private int currentLevel=1;
    private int mCurrentSection=0;

    private String mCurrentActivityName;

    private ArrayList<String>  currentActivity;

    private ArrayList<ArrayList<String>> allUsers;
    private ArrayList<ArrayList<String>> allGroupsPhaseLevelsSections;
    private ArrayList<ArrayList<String>> allGroupsPhaseLevels;
    private ArrayList<ArrayList<String>> mMaxLevelsGroupsPhase;
    private ArrayList<ArrayList<String>> allGroupsPhase;
    private ArrayList<ArrayList<String>> allGroupsPhaseSectionsActivities;
    private ArrayList<ArrayList<String>> mCurrentUserGPL;
    private ArrayList<ArrayList<String>> mAppUserCurrentGPL;
    private ArrayList<String> mCurrentBook;
    private ArrayList<String> allGPSsWA;

    private ArrayList<Integer> classOrder;
    private Map<String, Integer> classMap;


    private int mCurrentVideoID=0;

    private ArrayList<String> CurrentGroup_Section_Phase;

    private ArrayList<ArrayList<String>> allVariablesPhaseLevels;

    private ArrayList<ArrayList<ArrayList<ArrayList<String>>>> all_LSARs;

    private ArrayList<ArrayList<String>> allSecitons;
    private ArrayList<String> allLevels;

    public Motoli_Application_copy() {
        // TODO Auto-generated constructor stu
        Log.d("here", "here again");

    }


    @Override
    public void onCreate() {
        super.onCreate();
        allLevels=new ArrayList<String>();
        classMap = new HashMap<String, Integer>();
        classOrder=new ArrayList<Integer>();
        mCurrentActivityName="SplashPage";
        currentFontType=Typeface.createFromAsset(getAssets(), "fonts/AndikaNewBasic-R.ttf");
        currentFontTypeBold=Typeface.createFromAsset(getAssets(), "fonts/AndikaNewBasic-B.ttf");
        currentFontTypeItalic=Typeface.createFromAsset(getAssets(), "fonts/AndikaNewBasic-I.ttf");
        currentFontTypeItalicAndBold=Typeface.createFromAsset(getAssets(), "fonts/AndikaNewBasic-BI.ttf");
        mCurrentSection=0;
        Log.d("here", "here");
    }




    ////////////////////////////////////////////////////////////////////////////////////////

    public void setCurrentSection(int mSection){
        mCurrentSection=mSection;
    }

    public int getCurrentSection(){
        return mCurrentSection;
    }
    public String getActivityName(){
        return mCurrentActivityName;
    }

    public int getCurrentVideoID(){return mCurrentVideoID; }

    public void setCurrentVideoID(int mCurrentVideoID){ this.mCurrentVideoID=mCurrentVideoID; }
    ////////////////////////////////////////////////////////////////////////////////////////

    public void setCurrentActivityName(String mActivityName){
        mCurrentActivityName=mActivityName;
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

    public void removeLastClassOrder(){
        classOrder.remove(classOrder.size() - 1);
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    public void setClassesForLaunch(){
        classOrder.clear();
        classOrder=new ArrayList<Integer>();
        classOrder.add(0);
        classOrder.add(1);
        classOrder.add(2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    public void setClassesForActivities(){

        classOrder.clear();
        classOrder=new ArrayList<Integer>();
        classOrder.add(0);
        classOrder.add(1);
        classOrder.add(2);
        classOrder.add(3);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public int getPreviousClass(){
        int mClassOrderSize=classOrder.size();
        int mPreviousClassNumber=0;
        if(mClassOrderSize-2<0){
            classOrder=new ArrayList<Integer>();
            classOrder.add(0);
            classOrder.add(1);
            classOrder.add(2);
            mPreviousClassNumber = classOrder.get(0);
        }else {
            mPreviousClassNumber = classOrder.get(mClassOrderSize - 2);
            classOrder.remove(mClassOrderSize-1);
        }

        mClassOrderSize=classOrder.size();
        classOrder.remove(mClassOrderSize - 1);
        return mPreviousClassNumber;
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

    public ArrayList<String> getCurrentActivity(){
        return currentActivity;
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    public void setCurrentActivity(ArrayList<String> currentActivity){
        this.currentActivity=currentActivity;
    }

    public ArrayList<String> getCurrentBook(){
        return mCurrentBook;
    }

    public void setCurrentBook(ArrayList<String> mCurrentBook){
        this.mCurrentBook=new ArrayList<String>(mCurrentBook);
    }



    ////////////////////////////////////////////////////////////////////////////////////////







    ////////////////////////////////////////////////////////////////////////////////////////

    public void setGroupsPhaseLevelsSections(Cursor mCursor){
        this.allGroupsPhaseLevelsSections=new ArrayList<ArrayList<String>>();
        int currentIndex=0;
        while(mCursor.moveToNext()){
            allGroupsPhaseLevelsSections.add(new ArrayList<String>());
            allGroupsPhaseLevelsSections.get(currentIndex).add(mCursor.getString(mCursor.getColumnIndex("gls_id")));
            allGroupsPhaseLevelsSections.get(currentIndex).add(mCursor.getString(mCursor.getColumnIndex("group_id")));
            allGroupsPhaseLevelsSections.get(currentIndex).add(mCursor.getString(mCursor.getColumnIndex("phase_id")));
           // allGroupsPhaseLevelsSections.get(currentIndex).add(allGroupsPhaseLevelsSectionsCursor.getString(allGroupsPhaseLevelsSectionsCursor.getColumnIndex("level_number")));
            allGroupsPhaseLevelsSections.get(currentIndex).add(mCursor.getString(mCursor.getColumnIndex("section_id")));
            currentIndex++;
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    /*
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
\*/


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

    //////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<ArrayList<String>> getMaxLevelsGroupsPhase(){
        return mMaxLevelsGroupsPhase;
    }
    //////////////////////////////////////////////////////////////////////////////////////////

    public void setMaxLevelsGroupsPhase(Cursor cursor){
        mMaxLevelsGroupsPhase=new ArrayList<ArrayList<String>>();
        if(cursor.moveToFirst()){
            do{
                mMaxLevelsGroupsPhase.add(new ArrayList<String>());
                mMaxLevelsGroupsPhase.get(mMaxLevelsGroupsPhase.size()-1).add(cursor.getString(cursor.getColumnIndex("group_id")));
                mMaxLevelsGroupsPhase.get(mMaxLevelsGroupsPhase.size()-1).add(cursor.getString(cursor.getColumnIndex("phase_id")));
                mMaxLevelsGroupsPhase.get(mMaxLevelsGroupsPhase.size()-1).add(cursor.getString(cursor.getColumnIndex("max_level_number")));
            }while(cursor.moveToNext());
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

    public void setVariablesPhaseLevels(Cursor allVariablesPhaseLevelsCursor){
        this.allVariablesPhaseLevels=new ArrayList<ArrayList<String>>();
        int currentIndex=0;
        while(allVariablesPhaseLevelsCursor.moveToNext()){
            allVariablesPhaseLevels.add(new ArrayList<String>());
            allVariablesPhaseLevels.get(currentIndex).add(allVariablesPhaseLevelsCursor.getString(allVariablesPhaseLevelsCursor.getColumnIndex("variable_phase_levels_id")));
            allVariablesPhaseLevels.get(currentIndex).add(allVariablesPhaseLevelsCursor.getString(allVariablesPhaseLevelsCursor.getColumnIndex("variable_id")));
            allVariablesPhaseLevels.get(currentIndex).add(allVariablesPhaseLevelsCursor.getString(allVariablesPhaseLevelsCursor.getColumnIndex("group_id")));
            allVariablesPhaseLevels.get(currentIndex).add(allVariablesPhaseLevelsCursor.getString(allVariablesPhaseLevelsCursor.getColumnIndex("phase_id")));
            allVariablesPhaseLevels.get(currentIndex).add(allVariablesPhaseLevelsCursor.getString(allVariablesPhaseLevelsCursor.getColumnIndex("level_number")));
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


            all_LSARCursor.moveToFirst();
            do{

                if(!currentLevel.equals(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("level_number")))){
                    currentLevel=all_LSARCursor.getString(all_LSARCursor.getColumnIndex("level_number"));
                }

                if(!String.valueOf(currentSection).equals(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("section_id")))){
                    currentSection=Integer.valueOf(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("section_id")));
                }

            //	all_LSARs.add(new ArrayList<String>());
                currentIndex=all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).size();
                all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).add(new ArrayList<String>());
                all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).get(currentIndex).add(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("level_section_activity_id")));
                all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).get(currentIndex).add(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("level_number")));
                all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).get(currentIndex).add(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("section_id")));
                all_LSARs.get(Integer.parseInt(currentLevel)).get(currentSection).get(currentIndex).add(all_LSARCursor.getString(all_LSARCursor.getColumnIndex("activity_id")));
            }while(all_LSARCursor.moveToNext());
            Log.d(Constants.LOGCAT, "look");
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<ArrayList<ArrayList<ArrayList<String>>>> getAll_LSARs(){
        return all_LSARs;
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

    public void setCurrentGPL(ArrayList<ArrayList<String>> currentGPL){
        mCurrentUserGPL=new ArrayList<ArrayList<String>>(currentGPL);
    }


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

    public void setAppUserCurrentGPL(ArrayList<ArrayList<String>> appUserCurrentGPL){
        mAppUserCurrentGPL=new ArrayList<ArrayList<String>>(appUserCurrentGPL);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<ArrayList<String>> getAppUserCurrentGPL(){
        return  mAppUserCurrentGPL;
    }



    ////////////////////////////////////////////////////////////////////////////////////////

}
