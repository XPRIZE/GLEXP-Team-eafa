package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

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
    ArrayList<ArrayList<String>> allSecitons;
    ArrayList<ArrayList<String>> allUserGroupPhaseInfo;
    private ArrayList list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.launch_platform);
        appData.addToClassOrder(2);






        getLoaderManager().initLoader(MotoliConstants.UPDATE_LEVELS, null, this);





            findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    playClickSound();
                    moveBackwords();
                }
            });
        findViewById(R.id.btnSettings).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                goToSettings();
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
                            allSecitons.get(userCount).add("");
                            allSecitons.get(userCount).add("0");
                            allSecitons.get(userCount).add("0");
                            allSecitons.get(userCount).add("0");
                            allSecitons.get(userCount).add("0");

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
                allSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("lw_phase_id")));			//6
                
                allSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("app_user_current_level")));	//7
                allSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("activity_count")));			//8
                allSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("variable_count")));			//9
                allSecitons.get(userCount).add(allSectionsCursor.getString(allSectionsCursor.getColumnIndex("max_level")));			        //10

                /*
                for(int i=0; i<allUserGroupPhaseInfo.size(); i++){

                    if(allSecitons.get(userCount).get(1).equals(allUserGroupPhaseInfo.get(i).get(1)) &&
                            allSecitons.get(userCount).get(6).equals(allUserGroupPhaseInfo.get(i).get(2)) &&
                            allUserGroupPhaseInfo.get(i).get(3).equals("0") ){
                        allSecitons.get(userCount).set(7, allUserGroupPhaseInfo.get(2).get(3));
                        allSecitons.get(userCount).set(8, allUserGroupPhaseInfo.get(2).get(3));
                    }
                }
                */

                userCount++;
                allSectionsCursor.moveToNext();
            }
        }

        //AllGroups = new ArrayList<ArrayList<String>>(appData.getAllGroups());
        AllSections = new ArrayList<ArrayList<String>>(appData.getAllSections());


 
        sectionGrid = (GridView) findViewById(R.id.SectionGrid);


        sectionGrid.setAdapter(new Launch_Platform_Section(this, allSecitons));

        sectionGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                list = (ArrayList) v.findViewById(R.id.grid_item_image).getTag();

                String mActivityCount = (String) list.get(8);
                String mCurrentUserLevel = (String) list.get(7);
                String mCurrentVariableCount = (String) list.get(9);
                if (mActivityCount.equals("1") ||
                        ((!mActivityCount.equals("0") || !mActivityCount.equals("1")) &&
                                Integer.parseInt(mCurrentVariableCount) >= 4) ||
                        (list.get(7).equals("1") &&  Integer.parseInt(mCurrentVariableCount) >= 4) ||
                        list.get(0).equals("14") || list.get(0).equals("15")) {
                    if (!mCurrentUserLevel.equals("0") &&
                            (!mActivityCount.equals("0") && !mActivityCount.equals("1"))) {

                        move_to_activities((String) list.get(1), (String) list.get(6),
                                (String) list.get(0), mCurrentUserLevel);
                    } else if (mActivityCount.equals("1") && !mCurrentUserLevel.equals("0")) {
                        process_single_activity_section();
                    }

                }
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void process_single_activity_section(){
        int currentGroup=Integer.parseInt((String)list.get(1));
        int currentPhaase=Integer.parseInt((String)list.get(6));
        int currentSection=Integer.parseInt((String)list.get(0));

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

        appData.setCurrentGroup_Section_Phase((String) list.get(1), (String) list.get(6),
                (String) list.get(0), (String) list.get(7));

        switch(clickedActivityID){
            default:
                break;
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
            }//end case 22 LT01
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
            case 27:{
                appData.setmCurrentActivityName("Activity_LL01");
                Intent main = new Intent(this,Activity_LL01.class);
                startActivity(main);
                finish();
                break;
            }
            case 28:{
                appData.setmCurrentActivityName("Activity_LL02");

                Intent main = new Intent(this,Activity_LL02.class);
                startActivity(main);
                finish();
                break;
            }
            case 37:
            {
                appData.setmCurrentActivityName("ActivityBK02");

                Intent main = new Intent(this,ActivityBK02BookList.class);
                startActivity(main);
                finish();
                break;
            }
            case 39:{
                appData.setmCurrentActivityName("Activity_LL04");

                Intent main = new Intent(this,Activity_LL04.class);
                startActivity(main);
                finish();
                break;
            }
            case 21:{
                appData.setmCurrentActivityName("Activity_LL03");

                Intent main = new Intent(this,Activity_LL03.class);
                startActivity(main);
                finish();
                break;
            }
            case 36:{
                appData.setmCurrentActivityName("ActivityBK03");

                Intent main = new Intent(this,ActivityBK03BookList.class);
                startActivity(main);
                finish();

                break;
            }
            case 40:{
                appData.setmCurrentActivityName("ProgressLetter");

                Intent main = new Intent(this,ProgressLetters.class);
                startActivity(main);
                finish();
                break;
            }
            case 38:{
                break;
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void goToSettings(){
        appData.setCurrentGroup_Section_Phase("0","0","0","0");
        appData.setmCurrentActivityName("Settings");
        Intent main = new Intent(this,Settings.class);
        startActivity(main);
        finish();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void move_to_activities(String group_id, String phase_id, String section_id, String current_level){
        appData.setCurrentGroup_Section_Phase(group_id, phase_id, section_id, current_level);
        appData.setmCurrentActivityName("Activities_Platform");
        Intent main = new Intent(this,Activities_Platform.class);
        startActivity(main);
        finish();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.SECTIONS_LOADER:{
                projection = new String[] {
                        Database.Groups_Phase_Sections.GROUP_ID,
                        Database.Groups_Phase_Sections.PHASE_ID,
                        Database.Groups_Phase_Sections.SECTION_ID,
                        Database.Sections.SECTION_IMAGE,
                        Database.Sections.SECTION_NAME};
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_GROUP_PHASE_SECTIONS_INDIVIDUAL, projection, appData.getCurrentUserID(), null, null);
                break;
            }
            case MotoliConstants.ACTIVITIES_LOADER:{
                projection = new String[] {
                        Database.Groups_Phase_Sections_Activities.GPSA_ID,
                        Database.Groups_Phase_Sections_Activities.GROUP_ID,
                        Database.Groups_Phase_Sections_Activities.PHASE_ID,
                        Database.Groups_Phase_Sections_Activities.SECTION_ID,
                        Database.Groups_Phase_Sections_Activities.ACTIVITY_ID,
                        Database.Activities.ACTIVITY_NAME,
                        Database.Activities.ACTIVITY_IMG};
                  String gps_tblname=Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME;

                String selection=gps_tblname+"."+Database.Groups_Phase_Sections_Activities.GROUP_ID+"="+args.getInt("group_id")
                        +" AND "+gps_tblname+"."+Database.Groups_Phase_Sections_Activities.PHASE_ID+"="+args.getInt("phase_id")
                        +" AND "+gps_tblname+"."+Database.Groups_Phase_Sections_Activities.SECTION_ID+"="+args.getInt("section_id");
                String order=Database.Activities.ACTIVITIES_TABLE_NAME+"."+Database.Activities.ACTIVITY_ORDER+" ASC";
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_WRD_LTR_ACTIVITIES, projection, selection, null, order);
                break;
            }
            case MotoliConstants.APP_USER_CURRENT_GPL_LOADER:{
                projection = new String[]{
                        Database.App_Users_Current_GPL.APP_USER_CURRENT_GPL_ID,
                        Database.App_Users_Current_GPL.APP_USER_ID,
                        Database.App_Users_Current_GPL.GROUP_ID,
                        Database.App_Users_Current_GPL.PHASE_ID,
                        Database.App_Users_Current_GPL.APP_USER_CURRENT_LEVEL};
                String selection=Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME+"."+Database.App_Users_Current_GPL.APP_USER_ID
                        +"="+appData.getCurrentUserID();
                String order = Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME+"."+Database.App_Users_Current_GPL.GROUP_ID+" ASC, "
                        +Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME+"."+Database.App_Users_Current_GPL.PHASE_ID+" ASC";
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_APP_USERS_CURRENT_GPL_INDIVIDUAL, projection, selection, null, order);

                break;
            }
            case MotoliConstants.UPDATE_LEVELS:{
                projection = new String[] {
                        "",
                        "",
                        "",
                        ""};
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_GROUPS_PHASE_LEVELS_UPDATE, projection, appData.getCurrentUserID(), null, null);
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
                     goto_activity(data.getString(data.getColumnIndex(Database.Activities.ACTIVITY_ID)));
                 }
                 break;
             }
             case MotoliConstants.APP_USER_CURRENT_GPL_LOADER:{
                 allUserGroupPhaseInfo=new ArrayList<ArrayList<String>>();
                 int activityCount=0;
                 while(data.moveToNext()){
                     allUserGroupPhaseInfo.add(new ArrayList<String>());
                     allUserGroupPhaseInfo.get(activityCount).add(data.getString(data.getColumnIndex(Database.App_Users_Current_GPL.APP_USER_ID)));
                     allUserGroupPhaseInfo.get(activityCount).add(data.getString(data.getColumnIndex(Database.App_Users_Current_GPL.GROUP_ID)));
                     allUserGroupPhaseInfo.get(activityCount).add(data.getString(data.getColumnIndex(Database.App_Users_Current_GPL.PHASE_ID)));
                     allUserGroupPhaseInfo.get(activityCount).add(data.getString(data.getColumnIndex(Database.App_Users_Current_GPL.APP_USER_CURRENT_LEVEL)));
                     activityCount++;
                 }
                 getLoaderManager().restartLoader(MotoliConstants.SECTIONS_LOADER, null, this);
                 break;
             } case MotoliConstants.UPDATE_LEVELS:{
                 getLoaderManager().restartLoader(MotoliConstants.APP_USER_CURRENT_GPL_LOADER, null, this);
                 break;

             }
         }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }




}
