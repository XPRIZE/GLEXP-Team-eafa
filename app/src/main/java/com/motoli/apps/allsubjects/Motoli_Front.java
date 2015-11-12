package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.app.LoaderManager;



public class Motoli_Front extends Master_Parent implements
LoaderManager.LoaderCallbacks<Cursor> {



    private SimpleCursorAdapter dataAdapter;

    protected Handler generalHandler = new Handler();





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.motoli_front);


        appData.addToClassOrder(0);

        Log.d("there", "there");
        displayListView();

    }

    //////////////////////////////////////////////////////////////////////////////////////

     @Override
     protected void onResume() {
      super.onResume();
      //Starts a new or restarts an existing Loader in this manager
      getLoaderManager().restartLoader(MotoliConstants.MAX_LEVEL_GROUP_PHASE, null, this);
     }

     /////////////////////////////////////////////////////////////////////////////////////

     private void displayListView() {


          // The desired columns to be bound
          String[] columns = new String[] {
            Database.App_Users._ID,
            Database.App_Users.APP_USER_ID,
            Database.App_Users.APP_USER_NAME,
            Database.App_Users.APP_USER_AVATAR
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
          getLoaderManager().initLoader(MotoliConstants.MAX_LEVEL_GROUP_PHASE, null, this);

     }

     /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.APP_USER_LOADER:{
                projection = new String[] {
                        Database.App_Users._ID,
                        Database.App_Users.APP_USER_ID,
                        Database.App_Users.APP_USER_NAME,
                        Database.App_Users.APP_USER_AVATAR};
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_APP_USERS, projection, null, null, Database.App_Users.APP_USER_NAME+" ASC");
                break;
            }
            case MotoliConstants.GROUPS_LOADER:{
                projection = new String[]{
                        Database.Groups._ID,
                        Database.Groups.GROUP_ID,
                        Database.Groups.GROUP_NAME,
                        Database.Groups.GROUP_CODE};
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_GROUPS, projection, null, null, Database.Groups.GROUP_ID+" ASC");
                break;
            }
            case MotoliConstants.SECTIONS_LOADER:{
                projection = new String[]{
                        Database.Sections._ID,
                        Database.Sections.SECTION_ID,
                        Database.Sections.GROUP_ID,
                        Database.Sections.SECTION_NAME,
                        Database.Sections.SECTION_IMAGE,
                        Database.Sections.SECTION_ALL_INCLUSIVE,
                        Database.Sections.SECTION_ORDER};
                String sortOder=Database.Sections.GROUP_ID+" ASC, "
                        +Database.Sections.SECTION_ORDER+" ASC";
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_SECTIONS, projection, null, null, sortOder);
                break;
            }
             case MotoliConstants.LEVEL_SECTION_ACTIVITY_RELATION_LOADER:{
                projection = new String[]{
                        Database.Level_Section_Activity_Relation._ID,
                        Database.Level_Section_Activity_Relation.LEVEL_SECTION_ACTIVITY_ID,
                        Database.Level_Section_Activity_Relation.LEVEL_NUMBER,
                        Database.Level_Section_Activity_Relation.SECTION_ID,
                        Database.Level_Section_Activity_Relation.ACTIVITY_ID};
                String sortOder=Database.Level_Section_Activity_Relation.LEVEL_NUMBER+" ASC, "
                        +Database.Level_Section_Activity_Relation.SECTION_ID+" ASC, "
                        +Database.Level_Section_Activity_Relation.ACTIVITY_ID+" ASC";
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_LSARS, projection, null, null, sortOder);
                break;
             }
             case MotoliConstants.ACTIVITIES_LOADER:{
                    projection = new String[]{
                            Database.Activities._ID,
                            Database.Activities.ACTIVITY_ID,
                            Database.Activities.ACTIVITY_NUMBER,
                            Database.Activities.ACTIVITY_IMG,
                            Database.Activities.ACTIVITY_CODE,
                            Database.Activities.ACTIVITY_TYPE,
                            Database.Activities.ACTIVITY_NAME,
                            Database.Activities.ACTIVITY_ORDER};
                    String sortOder=Database.Activities.ACTIVITY_NUMBER+" ASC";
                    cursorLoader = new CursorLoader(this,
                            MotoliContentProvider.CONTENT_URI_ACTIVITIES, projection, null, null, sortOder);
                 break;
             }
             case MotoliConstants.GROUPS_PHASE_LOADER:{
                    projection = new String[]{
                            Database.Groups_Phase._ID,
                            Database.Groups_Phase.GROUPS_PHASE_ID,
                            Database.Groups_Phase.GROUP_ID,
                            Database.Groups_Phase.PHASE_ID,
                            Database.Groups_Phase.GROUP_PHASE_NAME};
                    String sortOder=Database.Groups_Phase.GROUP_ID+" ASC";
                    cursorLoader = new CursorLoader(this,
                            MotoliContentProvider.CONTENT_URI_GROUPS_PHASE, projection, null, null, sortOder);
                 break;
             }
             case MotoliConstants.GROUPS_PHASE_LEVELS_LOADER:{
                    projection = new String[]{
                            Database.Groups_Phase_Levels._ID,
                            Database.Groups_Phase_Levels.GPL_ID,
                            Database.Groups_Phase_Levels.GROUP_ID,
                            Database.Groups_Phase_Levels.PHASE_ID,
                            Database.Groups_Phase_Levels.LEVEL_NUMBER,
                            Database.Groups_Phase_Levels.GROUP_PHASE_LEVEL_NAME,
                            Database.Groups_Phase_Levels.GROUP_PHASE_LEVEL_DESCRIPTION,
                            Database.Groups_Phase_Levels.GROUP_PHASE_LEVEL_APP_TITLE};
                    String sortOder=Database.Groups_Phase_Levels.GPL_ID+" ASC";
                    cursorLoader = new CursorLoader(this,
                            MotoliContentProvider.CONTENT_URI_GROUPS_PHASE_LEVELS, projection, null, null, sortOder);
                 break;
             }
             case MotoliConstants.GROUPS_PHASE_SECTIONS_LOADER:{
                    projection = new String[]{
                            Database.Groups_Phase_Sections._ID,
                            Database.Groups_Phase_Sections.GLS_ID,
                            Database.Groups_Phase_Sections.GROUP_ID,
                            Database.Groups_Phase_Sections.PHASE_ID,
                            Database.Groups_Phase_Sections.SECTION_ID};
                    String sortOder=Database.Groups_Phase_Sections.GLS_ID+" ASC";
                    cursorLoader = new CursorLoader(this,
                            MotoliContentProvider.CONTENT_URI_GROUP_PHASE_SECTIONS, projection, null, null, sortOder);
                 break;
             }
             case MotoliConstants.WORDS_LOADER:{
                    projection = new String[]{
                            Database.Words._ID,
                            Database.Words.WORD_ID,
                            Database.Words.WORD_TEXT,
                            Database.Words.WORD_FIRST_LETTERS,
                            Database.Words.WORD_LAST_LETTERS,
                            Database.Words.NUMBER_SYLLABLES,
                            Database.Words.AUDIO_URL,
                            Database.Words.IMAGE_URL};
                    String sortOder=Database.Words.WORD_ID+" ASC";
                    cursorLoader = new CursorLoader(this,
                            MotoliContentProvider.CONTENT_URI_WORDS, projection, null, null, sortOder);
                 break;
             }
             case MotoliConstants.VARIABLE_PHASE_LEVELS_LOADER:{
                    projection = new String[]{
                            Database.Variable_Phase_Levels._ID,
                            Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_ID,
                            Database.Variable_Phase_Levels.VARIABLE_ID,
                            Database.Variable_Phase_Levels.GROUP_ID,
                            Database.Variable_Phase_Levels.PHASE_ID,
                            Database.Variable_Phase_Levels.LEVEL_NUMBER};
                    String sortOder=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_ID+" ASC";
                    cursorLoader = new CursorLoader(this,
                            MotoliContentProvider.CONTENT_URI_WORDS_PHASE_LEVELS, projection, null, null, sortOder);
                 break;
             }
             case MotoliConstants.GROUPS_PHASE_SECTIONS_ACTIVITIES_LOADER:{
                    projection = new String[]{
                            Database.Groups_Phase_Sections_Activities._ID,
                            Database.Groups_Phase_Sections_Activities.GPSA_ID,
                            Database.Groups_Phase_Sections_Activities.GROUP_ID,
                            Database.Groups_Phase_Sections_Activities.PHASE_ID,
                            Database.Groups_Phase_Sections_Activities.SECTION_ID,
                            Database.Groups_Phase_Sections_Activities.ACTIVITY_ID};

                    String sortOder=Database.Groups_Phase_Sections_Activities.GROUP_ID+" ASC, "
                            +Database.Groups_Phase_Sections_Activities.PHASE_ID+" ASC, "
                            +Database.Groups_Phase_Sections_Activities.SECTION_ID+" ASC, "
                            +Database.Groups_Phase_Sections_Activities.ACTIVITY_ID+" ASC";
                    cursorLoader = new CursorLoader(this,
                            MotoliContentProvider.CONTENT_URI_GROUPS_PHASE_SECTIONS_ACTIVITIES, projection, null, null, sortOder);
                 break;
             }
            case MotoliConstants.MAX_LEVEL_GROUP_PHASE:{
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_MAX_LEVEL_GROUP_PHASE, null, null, null, null);
                break;
            }


         }
          return cursorLoader;
    }

     /////////////////////////////////////////////////////////////////////////////////////

    @Override
     public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


         switch(loader.getId()) {
             default:
             case MotoliConstants.APP_USER_LOADER:{
                appData.setAllUsers(data);
                dataAdapter.swapCursor(data);
                generalHandler.postDelayed(processUserSelect, (long)10);
                 break;
             }
             case MotoliConstants.GROUPS_LOADER:{
                 appData.setAllGroups(data);
                 getLoaderManager().restartLoader(loader.getId()-1, null, this);
                 break;
             }
             case MotoliConstants.SECTIONS_LOADER:{
                 appData.setAllSections(data);
                 getLoaderManager().restartLoader(loader.getId()-1, null, this);
                 break;
             }
             case MotoliConstants.LEVEL_SECTION_ACTIVITY_RELATION_LOADER:{
                 appData.setAll_LSARs(data);
                 getLoaderManager().restartLoader(loader.getId()-1, null, this);
                 break;
             }
             case MotoliConstants.ACTIVITIES_LOADER:{
                 appData.setAllActivities(data);
                 getLoaderManager().restartLoader(loader.getId()-1, null, this);
                 break;
             }
             case MotoliConstants.GROUPS_PHASE_LOADER:{
                 appData.setGroupsPhase(data);
                 getLoaderManager().restartLoader(loader.getId()-1, null, this);
                 break;
             }
             case MotoliConstants.GROUPS_PHASE_LEVELS_LOADER:{
                 appData.setGroupsPhaseLevels(data);
                 getLoaderManager().restartLoader(loader.getId()-1, null, this);
                 break;
             }
             case MotoliConstants.GROUPS_PHASE_SECTIONS_LOADER:{
                 appData.setGroupsPhaseLevelsSections(data);
                 getLoaderManager().restartLoader(loader.getId()-1, null, this);
                 break;
             }
             case MotoliConstants.WORDS_LOADER:{
                // appData.setGroupsPhaseLevelsSections(data);
                 getLoaderManager().restartLoader(loader.getId()-1, null, this);
                 break;
             }
             case MotoliConstants.VARIABLE_PHASE_LEVELS_LOADER:{
                 appData.setVariablesPhaseLevels(data);
                 getLoaderManager().restartLoader(loader.getId()-1, null, this);
                 break;
             }
             case MotoliConstants.GROUPS_PHASE_SECTIONS_ACTIVITIES_LOADER:{
                 appData.setGroupsPhaseSectionsActivities(data);
                 getLoaderManager().restartLoader(loader.getId()-1, null, this);
                 break;
             }
             case MotoliConstants.MAX_LEVEL_GROUP_PHASE:{
                 appData.setMaxLevelsGroupsPhase(data);
                 getLoaderManager().restartLoader(MotoliConstants.GROUPS_PHASE_SECTIONS_ACTIVITIES_LOADER, null, this);
                 break;
             }
         }

     }

     @Override
     public void onLoaderReset(Loader<Cursor> loader) {
      // This is called when the last Cursor provided to onLoadFinished()
      // above is about to be closed.  We need to make sure we are no
      // longer using it.
      dataAdapter.swapCursor(null);



     }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    protected Runnable processUserSelect = new Runnable(){
        @Override
        public void run(){
            goToUserSelect();
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void goToUserSelect(){
        Intent main = new Intent(this,UserSelect.class);
        startActivity(main);
        finish();
        //startActivityForResult(main, MotoliConstants.ACTIVITY_USERSELECT);
        //finish();
    }

}
