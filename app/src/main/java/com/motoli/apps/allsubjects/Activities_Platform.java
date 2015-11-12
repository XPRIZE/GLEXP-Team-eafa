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
import android.widget.LinearLayout;
import android.widget.Toast;



public class Activities_Platform extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor> {  



    ArrayList<ArrayList<String>> AllGroups;
    ArrayList<ArrayList<String>> AllSections;
    ArrayList<ArrayList<String>> AllGroupsPhaseSectionActivities;
    ArrayList<String> CurrentGroup_Section_Phase;
    ArrayList<ArrayList<String>> mCurrentActivities;
    private ArrayList<ArrayList<String>> mAppUserCurrentGPL;
    ArrayList<ArrayList<String>> mAllPhaseInfo;
    ArrayList<ArrayList<String>> mActivityComplete;



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

        if(CurrentGroup_Section_Phase.get(0).equals("2")){
            findViewById(R.id.ActivitiesSubFrame).setBackgroundResource(R.color.application_letters);
        }else if(CurrentGroup_Section_Phase.get(0).equals("3")){
            findViewById(R.id.ActivitiesSubFrame).setBackgroundResource(R.color.application_syllables);
           // ((ImageView) findViewById(R.id.btnBack)).setImageResource(drawable.btn_move_bluelight);
        }else if(CurrentGroup_Section_Phase.get(0).equals("4")){
            findViewById(R.id.ActivitiesSubFrame).setBackgroundResource(R.color.application_words);
        }else if(CurrentGroup_Section_Phase.get(0).equals("5")){
            findViewById(R.id.ActivitiesSubFrame).setBackgroundResource(R.color.application_books);
        }else if(CurrentGroup_Section_Phase.get(0).equals("6")){
            findViewById(R.id.ActivitiesSubFrame).setBackgroundResource(R.color.application_number);
        }else if(CurrentGroup_Section_Phase.get(0).equals("7")){
            findViewById(R.id.ActivitiesSubFrame).setBackgroundResource(R.color.application_math);
        }else if(CurrentGroup_Section_Phase.get(0).equals("8")){
            findViewById(R.id.ActivitiesSubFrame).setBackgroundResource(R.color.application_write);
        }


        if(CurrentGroup_Section_Phase.get(0).equals("2") &&
                (CurrentGroup_Section_Phase.get(1).equals("1") ||CurrentGroup_Section_Phase.get(1).equals("2"))) {
            findViewById(R.id.btnChangeCase).setVisibility(ImageView.INVISIBLE);
        }else{
            findViewById(R.id.btnChangeCase).setVisibility(ImageView.INVISIBLE);
        }

        getLoaderManager().initLoader(MotoliConstants.UPDATE_LEVELS, null, this);


        findViewById(R.id.activityIconArea1).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.activityIconArea2).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.activityIconArea3).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.activityIconArea4).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.activityIconArea5).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.activityIconArea6).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.activityIconArea7).setVisibility(ImageView.GONE);
        findViewById(R.id.activityIconArea8).setVisibility(ImageView.GONE);

        findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
            }
        });
        findViewById(R.id.btnInfo).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                playInstructionAudio();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();


    }
    public void onPause(){
        System.gc();
        super.onPause();

    }
     /////////////////////////////////////////////////////////////////////////////////////

      private long playInstructionAudio(){
          long mAudioDuration=0;

          if(CurrentGroup_Section_Phase.get(0).equals("2")){
              if(CurrentGroup_Section_Phase.get(1).equals("1") || CurrentGroup_Section_Phase.get(1).equals("2")){
                  mAudioDuration = playGeneralAudio("info_letters.mp3");
              }else {
                  mAudioDuration = playGeneralAudio("info_phonics.mp3");
              }
          }else{
              Toast.makeText(getApplicationContext(),
                      "This needs to be adapted for other groups", Toast.LENGTH_LONG).show();
          }
        /*

                  info_words
          info_syllables
                  info_numbers
          info_count
                  */
          return mAudioDuration;
     }

    /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String gps_tblname=Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME;
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.ACTIVITIES_LOADER:{
                projection = new String[] {
                        Database.Groups_Phase_Sections_Activities.GPSA_ID,
                        Database.Groups_Phase_Sections_Activities.GROUP_ID,
                        Database.Groups_Phase_Sections_Activities.PHASE_ID,
                        Database.Groups_Phase_Sections_Activities.SECTION_ID,
                        Database.Groups_Phase_Sections_Activities.ACTIVITY_ID,
                        Database.Activities.ACTIVITY_NAME,
                        Database.Activities.ACTIVITY_IMG};



                String selection=gps_tblname+"."+Database.Groups_Phase_Sections_Activities.GROUP_ID
                        +"="+CurrentGroup_Section_Phase.get(0)+" AND "+gps_tblname+"."
                        +Database.Groups_Phase_Sections_Activities.SECTION_ID+"="
                        +CurrentGroup_Section_Phase.get(2);

                String order=Database.Activities.ACTIVITIES_TABLE_NAME+"."
                        +Database.Activities.ACTIVITY_ORDER+" ASC";
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_WRD_LTR_ACTIVITIES, projection, selection, null, order);
                break;
            }
            case MotoliConstants.ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE:{
                String mTypeID;
                if(CurrentGroup_Section_Phase.get(0).equals("2")) {
                    if (CurrentGroup_Section_Phase.get(1).equals("2")){
                        mTypeID="2";
                    }else if (CurrentGroup_Section_Phase.get(1).equals("3")) {
                        mTypeID="3";
                    }else{
                        mTypeID="1";
                    }
                }else if(CurrentGroup_Section_Phase.get(0).equals("3")) {
                    mTypeID="4";
                }else{
                    mTypeID="5";
                }
                String selection=gps_tblname+"."+Database.Groups_Phase_Sections_Activities.GROUP_ID
                        +"="+CurrentGroup_Section_Phase.get(0)+" AND "+gps_tblname+"."
                        +Database.Groups_Phase_Sections_Activities.SECTION_ID+"="
                        +CurrentGroup_Section_Phase.get(2)+ " AND "+gps_tblname+"."
                        +Database.Groups_Phase_Sections_Activities.PHASE_ID + "="
                        +CurrentGroup_Section_Phase.get(1)+" AND " +
                        "app_users_activity_variable_values.type_id="+mTypeID;
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE, null, selection, null, null);
                break;
            }
            case MotoliConstants.APP_USER_CURRENT_GPL_LOADER:{
                projection = new String[]{
                        Database.App_Users_Current_GPL.APP_USER_CURRENT_GPL_ID,
                        Database.App_Users_Current_GPL.APP_USER_ID,
                        Database.App_Users_Current_GPL.GROUP_ID,
                        Database.App_Users_Current_GPL.PHASE_ID,
                        Database.App_Users_Current_GPL.APP_USER_CURRENT_LEVEL};
                String selection=Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME+"."+
                        Database.App_Users_Current_GPL.APP_USER_ID+"="+appData.getCurrentUserID();
                String order = Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME+"."+
                        Database.App_Users_Current_GPL.GROUP_ID+" ASC, "+
                        Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME+"."+
                        Database.App_Users_Current_GPL.PHASE_ID+" ASC";
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_APP_USERS_CURRENT_GPL_INDIVIDUAL, projection, selection, null, order);
                break;
            }
            case MotoliConstants.UPDATE_LEVELS:{
                projection = new String[] {
                        CurrentGroup_Section_Phase.get(0),
                        CurrentGroup_Section_Phase.get(1),
                        CurrentGroup_Section_Phase.get(2),
                        CurrentGroup_Section_Phase.get(3)};
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_GROUPS_PHASE_LEVELS_UPDATE, projection, appData.getCurrentUserID(), null, null);
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

                mCurrentActivities=new ArrayList<ArrayList<String>>();
                int mActivityCount=0;
                while(data.moveToNext()){
                    mCurrentActivities.add(new ArrayList<String>());
                    mCurrentActivities.get(mActivityCount).add(data.getString(data.getColumnIndex(Database.Activities.ACTIVITY_ID))); //0
                    mCurrentActivities.get(mActivityCount).add(data.getString(data.getColumnIndex(Database.Activities.ACTIVITY_NAME))); //1
                    mCurrentActivities.get(mActivityCount).add(data.getString(data.getColumnIndex(Database.Activities.ACTIVITY_IMG))); //2

                    if(Integer.parseInt(CurrentGroup_Section_Phase.get(1))>=
                            Integer.parseInt(data.getString(data.getColumnIndex("minphase_id")))){
                        mCurrentActivities.get(mActivityCount).add("1"); //3
                    }else{
                        mCurrentActivities.get(mActivityCount).add("0"); //3
                    }
                    

                    mCurrentActivities.get(mActivityCount).add(data.getString(data.getColumnIndex("activity_type"))); //4

                    mCurrentActivities.get(mActivityCount).add("0"); //5

                    if(CurrentGroup_Section_Phase.get(0).equals("2") && mCurrentActivities.get(mActivityCount).get(0).equals("8") &&
                            Integer.parseInt(CurrentGroup_Section_Phase.get(3))<3 &&
                            (CurrentGroup_Section_Phase.get(1).equals("1") || CurrentGroup_Section_Phase.get(1).equals("2")) ) {
                        mCurrentActivities.get(mActivityCount).set(3, "0"); //3
                    }

                    if(CurrentGroup_Section_Phase.get(0).equals("2") &&
                            (mCurrentActivities.get(mActivityCount).get(0).equals("17") ||
                                    mCurrentActivities.get(mActivityCount).get(0).equals("24")) &&
                            Integer.parseInt(CurrentGroup_Section_Phase.get(3))<2 &&
                            (CurrentGroup_Section_Phase.get(1).equals("3")) ) {
                        mCurrentActivities.get(mActivityCount).set(3, "0"); //3
                    }

                    if(CurrentGroup_Section_Phase.get(0).equals("3") && CurrentGroup_Section_Phase.get(1).equals("1")){
                        for(int i=0; i<mAllPhaseInfo.size(); i++) {
                            if (mAllPhaseInfo.get(i).get(1).equals("2") &&
                                    mAllPhaseInfo.get(i).get(2).equals("3") &&
                                    Integer.parseInt(mAllPhaseInfo.get(i).get(3))<= 1 &&
                                    mCurrentActivities.get(mActivityCount).get(4).equals("RS")){
                                mCurrentActivities.get(mActivityCount).set(3, "0");
                                break;
                            }
                        }//end for
                    }//end if

                    for(int i=0;i<mActivityComplete.size();i++){
                        if(mActivityComplete.get(i).get(0).equals(mCurrentActivities.get(mActivityCount).get(0)) &&
                                mActivityComplete.get(i).get(1).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT)) /*&&
                                !mCurrentActivities.get(i).get(0).equals("6")*/){
                            mCurrentActivities.get(mActivityCount).set(5,String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                            break;
                        }
                    }


                    mActivityCount++;

                }
                displayScreen();
                break;
            }
             case MotoliConstants.ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE:{
                 mActivityComplete=new ArrayList<ArrayList<String>>();
                 int mActivity=0;
                 data.moveToFirst();
                 if(data.getCount()>0) {
                     do {
                         mActivityComplete.add(new ArrayList<String>());
                         mActivityComplete.get(mActivity).add(data.getString(data.getColumnIndex("activity_id")));
                         mActivityComplete.get(mActivity).add(data.getString(data.getColumnIndex("number_correct_in_a_row")));
                         mActivity++;
                     } while (data.moveToNext());
                 }
                 getLoaderManager().restartLoader(MotoliConstants.ACTIVITIES_LOADER, null, this);
                 break;
             }
             case MotoliConstants.APP_USER_CURRENT_GPL_LOADER:{
                 mAllPhaseInfo=new ArrayList<ArrayList<String>>();
                 int mActivityCount=0;
                 while(data.moveToNext()){
                     mAllPhaseInfo.add(new ArrayList<String>());
                     mAllPhaseInfo.get(mActivityCount).add(data.getString(data.getColumnIndex(Database.App_Users_Current_GPL.APP_USER_ID)));
                     mAllPhaseInfo.get(mActivityCount).add(data.getString(data.getColumnIndex(Database.App_Users_Current_GPL.GROUP_ID)));
                     mAllPhaseInfo.get(mActivityCount).add(data.getString(data.getColumnIndex(Database.App_Users_Current_GPL.PHASE_ID)));
                     mAllPhaseInfo.get(mActivityCount).add(data.getString(data.getColumnIndex(Database.App_Users_Current_GPL.APP_USER_CURRENT_LEVEL)));
                     mActivityCount++;
                 }
                 getLoaderManager().restartLoader(MotoliConstants.ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE, null, this);
                 break;
             }
            case MotoliConstants.UPDATE_LEVELS:{

                ArrayList<ArrayList<String>> mMaxLevelsGroupsPhase=new ArrayList<ArrayList<String>>(appData.getMaxLevelsGroupsPhase());

                mAppUserCurrentGPL=new ArrayList<ArrayList<String>>();
                if(data.moveToFirst()){
                    do{
                        mAppUserCurrentGPL.add(new ArrayList<String>());
                        mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).add(data.getString(data.getColumnIndex("_id"))); //0
                        mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).add(data.getString(data.getColumnIndex("app_user_id"))); //1
                        mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).add(data.getString(data.getColumnIndex("group_id"))); //2
                        mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).add(data.getString(data.getColumnIndex("phase_id"))); //3
                        mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).add(data.getString(data.getColumnIndex("app_user_current_level"))); //4
                        if(CurrentGroup_Section_Phase.get(0).equals(mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).get(2)) &&
                                CurrentGroup_Section_Phase.get(1).equals(mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).get(3)) &&
                                Integer.parseInt(CurrentGroup_Section_Phase.get(3))<Integer.parseInt(mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).get(4))){
                            CurrentGroup_Section_Phase.set(0,"2");
                            CurrentGroup_Section_Phase.set(3,mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).get(4));
                        }
                        String mCurrentMaxLevel="0";
                        for(int i=0;i<mMaxLevelsGroupsPhase.size(); i++){
                            if(mMaxLevelsGroupsPhase.get(i).get(0).equals(CurrentGroup_Section_Phase.get(0)) &&
                                    mMaxLevelsGroupsPhase.get(i).get(1).equals(CurrentGroup_Section_Phase.get(1))){
                                mCurrentMaxLevel=mMaxLevelsGroupsPhase.get(i).get(2);
                                break;
                            }
                        }
                        if(CurrentGroup_Section_Phase.get(0).equals(mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).get(2)) &&
                                CurrentGroup_Section_Phase.get(1).equals(mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).get(3)) &&
                                Integer.parseInt(mAppUserCurrentGPL.get(mAppUserCurrentGPL.size()-1).get(4)) >
                                        Integer.parseInt(mCurrentMaxLevel)){
                            if(CurrentGroup_Section_Phase.get(1).equals("1")){
                                CurrentGroup_Section_Phase.set(1, "2");
                                CurrentGroup_Section_Phase.set(3,"1");
                            }else {
                                CurrentGroup_Section_Phase.set(1, "3");
                                CurrentGroup_Section_Phase.set(3,"1");
                            }
                        }

                    }while(data.moveToNext());
                }
                appData.setAppUserCurrentGPL(mAppUserCurrentGPL);
                appData.setCurrentGroup_Section_Phase(CurrentGroup_Section_Phase.get(0), CurrentGroup_Section_Phase.get(1),
                        CurrentGroup_Section_Phase.get(2), CurrentGroup_Section_Phase.get(3));


                getLoaderManager().restartLoader(MotoliConstants.APP_USER_CURRENT_GPL_LOADER, null, this);

                break;
            }
         }

     }

     @Override
     public void onLoaderReset(Loader<Cursor> loader) {



     }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){

        if(CurrentGroup_Section_Phase.get(0).equals("3")){
            findViewById(R.id.ActivitiesSubFrame).setBackgroundResource(R.color.application_syllables);
        }else if(CurrentGroup_Section_Phase.get(0).equals("2")){
            findViewById(R.id.ActivitiesSubFrame).setBackgroundResource(R.color.application_letters);
        }

        for(int i=0; i<mCurrentActivities.size(); i++){
            String imageName=mCurrentActivities.get(i).get(2).replace(".jpg", "").replace(".png", "");

            Class<drawable> res = R.drawable.class;
            int drawableId;
            Field field;


            switch(i){
                default:
                case 0:{
                     findViewById(R.id.activityIconArea1).setVisibility(ImageView.VISIBLE);
                    findViewById(R.id.activityIcon1).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon1).setAlpha(0.1f);
                        findViewById(R.id.activityReward1).setVisibility(LinearLayout.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon1).setAlpha(1.0f);
                        if(mCurrentActivities.get(i).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT))){
                            if(CurrentGroup_Section_Phase.get(2).equals("11")){
                                findViewById(R.id.activityReward1).setVisibility(LinearLayout.VISIBLE);
                            }else {
                                findViewById(R.id.activityReward1).setVisibility(LinearLayout.INVISIBLE);
                            }
                        }else{
                            findViewById(R.id.activityReward1).setVisibility(LinearLayout.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon1)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                case 1:{
                     findViewById(R.id.activityIconArea2).setVisibility(ImageView.VISIBLE);
                     findViewById(R.id.activityIcon2).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon2).setAlpha(0.1f);
                        findViewById(R.id.activityReward2).setVisibility(LinearLayout.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon2).setAlpha(1.0f);
                        if(mCurrentActivities.get(i).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT))){
                            findViewById(R.id.activityReward2).setVisibility(LinearLayout.VISIBLE);
                        }else{
                            findViewById(R.id.activityReward2).setVisibility(LinearLayout.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon2)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                case 2:{
                     findViewById(R.id.activityIconArea3).setVisibility(ImageView.VISIBLE);
                     findViewById(R.id.activityIcon3).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon3).setAlpha(0.1f);
                        findViewById(R.id.activityReward3).setVisibility(LinearLayout.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon3).setAlpha(1.0f);
                        if(mCurrentActivities.get(i).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT))){
                            findViewById(R.id.activityReward3).setVisibility(LinearLayout.VISIBLE);
                        }else{
                            findViewById(R.id.activityReward3).setVisibility(LinearLayout.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon3)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                case 3:{
                     findViewById(R.id.activityIconArea4).setVisibility(ImageView.VISIBLE);
                     findViewById(R.id.activityIcon4).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon4).setAlpha(0.1f);
                        findViewById(R.id.activityReward4).setVisibility(LinearLayout.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon4).setAlpha(1.0f);
                        if(mCurrentActivities.get(i).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT))){
                            findViewById(R.id.activityReward4).setVisibility(LinearLayout.VISIBLE);
                        }else{
                            findViewById(R.id.activityReward4).setVisibility(LinearLayout.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon4)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                case 4:{
                     findViewById(R.id.activityIconArea5).setVisibility(ImageView.VISIBLE);
                     findViewById(R.id.activityIcon5).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon5).setAlpha(0.1f);
                        findViewById(R.id.activityReward5).setVisibility(LinearLayout.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon5).setAlpha(1.0f);
                        if(mCurrentActivities.get(i).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT))){
                            findViewById(R.id.activityReward5).setVisibility(LinearLayout.VISIBLE);
                        }else{
                            findViewById(R.id.activityReward5).setVisibility(LinearLayout.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon5)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                case 5:{
                     findViewById(R.id.activityIconArea6).setVisibility(ImageView.VISIBLE);
                     findViewById(R.id.activityIcon6).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon6).setAlpha(0.1f);
                        findViewById(R.id.activityReward6).setVisibility(LinearLayout.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon6).setAlpha(1.0f);
                        if(mCurrentActivities.get(i).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT))){
                            findViewById(R.id.activityReward6).setVisibility(LinearLayout.VISIBLE);
                        }else{
                            findViewById(R.id.activityReward6).setVisibility(LinearLayout.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon6)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }

                case 6:{
                    findViewById(R.id.activityIconArea7).setVisibility(ImageView.VISIBLE);
                    findViewById(R.id.activityIconArea8).setVisibility(ImageView.INVISIBLE);
                    findViewById(R.id.activityIcon8).setAlpha(0.0f);
                    findViewById(R.id.activityIcon7).setTag(mCurrentActivities.get(i));

                    findViewById(R.id.activityReward8).setVisibility(LinearLayout.INVISIBLE);

                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon7).setAlpha(0.1f);
                        findViewById(R.id.activityReward7).setVisibility(LinearLayout.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon7).setAlpha(1.0f);
                        if(mCurrentActivities.get(i).get(5).equals(String.valueOf(MotoliConstants.INA_ROW_CORRECT))){
                            findViewById(R.id.activityReward7).setVisibility(LinearLayout.VISIBLE);
                        }else{
                            findViewById(R.id.activityReward7).setVisibility(LinearLayout.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon7)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                 /*
                case 7:{
                    ((ImageView) findViewById(R.id.activityIcon8)).setTag(mCurrentActivities.get(i).get(0));
                    if(mActivityComplete.get(i).get(5).equals("1")){
                        findViewById(R.id.activityReward1).setVisibility(LinearLayout.VISIBLE);
                    }else{
                        findViewById(R.id.activityReward1).setVisibility(LinearLayout.INVISIBLE);
                    }
                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon8)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                */
            }//end switch(i){
        }//end for(int i=0; i<mCurrentActivities.size(); i++){

        activityButtonListener();

    }//end public void displayScreen(){


    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void activityButtonListener(){
        /**
         * There are warnings here with:
         * Warning:(*, *) Unchecked cast: 'java.lang.Object' to 'java.util.ArrayList<java.lang.String>'
         * I will look at this later as it is due to the Object casting to an ArrayList.
         * The object/tag was set with an ArrayList so I know it is correct, but still need to clean
         * warning
         */
        findViewById(R.id.activityIcon1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (findViewById(R.id.activityIcon1).getTag() instanceof ArrayList) {

                    ArrayList<String> mTempArray = new ArrayList<String>((ArrayList<String>) findViewById(R.id.activityIcon1).getTag());
                    if (mTempArray.get(3).equals("1")) {
                        processActivityButton(mTempArray.get(0));
                    }
                }
            }
        });

        findViewById(R.id.activityIcon2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> mTempArray=new ArrayList<String>((ArrayList<String>) findViewById(R.id.activityIcon2).getTag());
                if(mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
            }
        });

        findViewById(R.id.activityIcon3).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> mTempArray=new ArrayList<String>((ArrayList<String>) findViewById(R.id.activityIcon3).getTag());
                if(mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
            }
        });

        findViewById(R.id.activityIcon4).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> mTempArray=new ArrayList<String>((ArrayList<String>) findViewById(R.id.activityIcon4).getTag());
                if(mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
            }
        });

        findViewById(R.id.activityIcon5).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> mTempArray=new ArrayList<String>((ArrayList<String>) findViewById(R.id.activityIcon5).getTag());
                if(mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
            }
        });

        findViewById(R.id.activityIcon6).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> mTempArray = new ArrayList<String>((ArrayList<String>) findViewById(R.id.activityIcon6).getTag());
                if(mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
            }
        });

        findViewById(R.id.activityIcon7).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> mTempArray = new ArrayList<String>((ArrayList<String>) findViewById(R.id.activityIcon7).getTag());
                if (mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
                // processActivityButton(findViewById(R.id.activityIcon7).getTag());


            }
        });
/*
        findViewById(R.id.activityIcon8).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                processActivityButton(findViewById(R.id.activityIcon8).getTag());


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
                appData.setmCurrentActivityName("Activity_LT03");
                Intent main = new Intent(this,Activity_LT03.class);
                startActivity(main);
                finish();
                break;
            }
            case 20:{
                appData.setmCurrentActivityName("Activity_LT05");
                Intent main = new Intent(this,Activity_LT05.class);
                startActivity(main);
                finish();
                break;
            }//end case 20 LT05
            case 22:{
                appData.setmCurrentActivityName("Activity_LT01");
                Intent main = new Intent(this,Activity_LT01.class);
                startActivity(main);
                finish();
                break;
            }//end case 22 TL01
            case 2:{
                appData.setmCurrentActivityName("Activity_LT02");
                Intent main = new Intent(this,Activity_LT02.class);
                startActivity(main);
                finish();
                break;
            }//end case 2 LT02
            case 4:{
                appData.setmCurrentActivityName("Activity_LT06");
                Intent main = new Intent(this,Activity_LT06.class);
                startActivity(main);
                finish();
                break;
            }//end case 2 LT02
            case 8:{
                appData.setmCurrentActivityName("Activity_LT04");
                Intent main = new Intent(this,Activity_LT04.class);
                startActivity(main);
                finish();
                break;
            }//end case 8 LT04
            case 23:{
                appData.setmCurrentActivityName("ActivitySD01");
                Intent main = new Intent(this,ActivitySD01.class);
                startActivity(main);
                finish();
                break;
            }
            case 15:{
                appData.setmCurrentActivityName("ActivitySD02");
                Intent main = new Intent(this,ActivitySD02.class);
                startActivity(main);
                finish();
                break;
            }//end case 15 ActivitySD02
            case 17:{
                appData.setmCurrentActivityName("ActivitySD03");
                Intent main = new Intent(this,ActivitySD03.class);
                startActivity(main);
                finish();
                break;
            }//end case 17 ActivitySD03
            case 24:{
                appData.setmCurrentActivityName("ActivitySD06");
                Intent main = new Intent(this,ActivitySD06.class);
                startActivity(main);
                finish();
                break;
            }//end case 17 ActivitySD03
            case 35:{
                appData.setmCurrentActivityName("ActivitySD07");
                Intent main = new Intent(this,ActivitySD07.class);
                startActivity(main);
                finish();
                break;
            }//end case 35 ActivitySD07
            case 25:{
                appData.setmCurrentActivityName("ActivitySD04");
                Intent main = new Intent(this,ActivitySD04.class);
                startActivity(main);
                finish();
                break;
            }//end case 17 ActivitySD03
            case 19:{
                appData.setmCurrentActivityName("ActivitySD05");
                Intent main = new Intent(this,ActivitySD05.class);
                startActivity(main);
                finish();
                break;
            }//end case 19 ActivitySD05
            case 29:{
                appData.setmCurrentActivityName("ActivityCS01");
                Intent main = new Intent(this,ActivityCS01.class);
                startActivity(main);
                finish();
                break;
            }//end case 29 ActivityCS01
            case 30:{
                appData.setmCurrentActivityName("ActivityCS02");
                Intent main = new Intent(this,ActivityCS02.class);
                startActivity(main);
                finish();
                break;
            }//end case 30 ActivityCS02
            case 31:{
                appData.setmCurrentActivityName("ActivityCS03");
                Intent main = new Intent(this,ActivityCS03.class);
                startActivity(main);
                finish();
                break;
            }//end case 31 ActivityCS03
            case 32:{
                appData.setmCurrentActivityName("ActivityRS01");
                Intent main = new Intent(this,ActivityRS01.class);
                startActivity(main);
                finish();
                break;
            }//end case 32 ActivityRS01
            case 33:{
                appData.setmCurrentActivityName("ActivityRS02");
                Intent main = new Intent(this,ActivityRS02.class);
                startActivity(main);
                finish();
                break;
            }//end case 33 ActivityRS02
            case 34:{
                appData.setmCurrentActivityName("ActivityRS03");
                Intent main = new Intent(this,ActivityRS03.class);
                startActivity(main);
                finish();
                break;
            }//end case 34 ActivityRS03
            case 41:{
                appData.setmCurrentActivityName("ActivityWD01");
                Intent main = new Intent(this,ActivityWD01.class);
                startActivity(main);
                finish();
                break;
            }//end case 41 ActivityWD01
            case 42:{
                appData.setmCurrentActivityName("ActivityWD02");
                Intent main = new Intent(this,ActivityWD02.class);
                startActivity(main);
                finish();
                break;
            }//end case 42 ActivityWD02
            case 43:{
                appData.setmCurrentActivityName("ActivityWD03");
                Intent main = new Intent(this,ActivityWD03.class);
                startActivity(main);
                finish();
                break;
            }//end case 43 ActivityWD03
            case 44:{
                appData.setmCurrentActivityName("ActivityWD04");
                Intent main = new Intent(this,ActivityWD04.class);
                startActivity(main);
                finish();
                break;
            }//end case 44 ActivityWD04
            case 45:{
                appData.setmCurrentActivityName("ActivityWD05");
                Intent main = new Intent(this,ActivityWD05.class);
                startActivity(main);
                finish();
                break;
            }//end case 45 ActivityWD05
            case 46:{
                appData.setmCurrentActivityName("ActivityWD06");
                Intent main = new Intent(this,ActivityWD06.class);
                startActivity(main);
                finish();
                break;
            }//end case 46 ActivityWD06
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
