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
	  getLoaderManager().restartLoader(MotoliConstants.GROUPS_PHASE_SECTIONS_ACTIVITIES_LOADER, null, this);
	 }
	 
	 /////////////////////////////////////////////////////////////////////////////////////
	 
	 private void displayListView() {
		 
		 
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
		  getLoaderManager().initLoader(MotoliConstants.GROUPS_PHASE_SECTIONS_ACTIVITIES_LOADER, null, this);
		  
	 }
	 
	 /////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection =null;
		CursorLoader cursorLoader=null;
		switch(id){
			default:
			case MotoliConstants.APP_USER_LOADER:{
				projection = new String[] {
						S4K_Database.App_Users._ID,
						S4K_Database.App_Users.APP_USER_ID,
						S4K_Database.App_Users.APP_USER_NAME,
						S4K_Database.App_Users.APP_USER_AVATAR};
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_APP_USERS, projection, null, null, S4K_Database.App_Users.APP_USER_NAME+" ASC");
				break;
			}
			case MotoliConstants.GROUPS_LOADER:{
				projection = new String[]{
						S4K_Database.Groups._ID,
						S4K_Database.Groups.GROUP_ID,
						S4K_Database.Groups.GROUP_NAME,
						S4K_Database.Groups.GROUP_CODE};
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_GROUPS, projection, null, null, S4K_Database.Groups.GROUP_ID+" ASC");
				break;
			}
			case MotoliConstants.SECTIONS_LOADER:{
				projection = new String[]{
						S4K_Database.Sections._ID,	
						S4K_Database.Sections.SECTION_ID,	
						S4K_Database.Sections.GROUP_ID,	
						S4K_Database.Sections.SECTION_NAME,	
						S4K_Database.Sections.SECTION_IMAGE,	
						S4K_Database.Sections.SECTION_ALL_INCLUSIVE,	
						S4K_Database.Sections.SECTION_ORDER};	
				String sortOder=S4K_Database.Sections.GROUP_ID+" ASC, "
						+S4K_Database.Sections.SECTION_ORDER+" ASC";
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_SECTIONS, projection, null, null, sortOder);
				break;
			}
			 case MotoliConstants.LEVEL_SECTION_ACTIVITY_RELATION_LOADER:{
				projection = new String[]{
						S4K_Database.Level_Section_Activity_Relation._ID,	
						S4K_Database.Level_Section_Activity_Relation.LEVEL_SECTION_ACTIVITY_ID,	
						S4K_Database.Level_Section_Activity_Relation.LEVEL_NUMBER,	
						S4K_Database.Level_Section_Activity_Relation.SECTION_ID,	
						S4K_Database.Level_Section_Activity_Relation.ACTIVITY_ID};	
				String sortOder=S4K_Database.Level_Section_Activity_Relation.LEVEL_NUMBER+" ASC, "
						+S4K_Database.Level_Section_Activity_Relation.SECTION_ID+" ASC, "
						+S4K_Database.Level_Section_Activity_Relation.ACTIVITY_ID+" ASC";
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_LSARS, projection, null, null, sortOder);
			 	break;
			 }
			 case MotoliConstants.ACTIVITIES_LOADER:{
					projection = new String[]{
							S4K_Database.Activities._ID,	
							S4K_Database.Activities.ACTIVITY_ID,	
							S4K_Database.Activities.ACTIVITY_NUMBER,	
							S4K_Database.Activities.ACTIVITY_IMG,	
							S4K_Database.Activities.ACTIVITY_CODE,	
							S4K_Database.Activities.ACTIVITY_TYPE,
							S4K_Database.Activities.ACTIVITY_NAME,
							S4K_Database.Activities.ACTIVITY_ORDER};	
					String sortOder=S4K_Database.Activities.ACTIVITY_NUMBER+" ASC";
					cursorLoader = new CursorLoader(this,
						    Motoli_ContentProvidor.CONTENT_URI_ACTIVITIES, projection, null, null, sortOder);				
				 break;
			 }	
			 case MotoliConstants.GROUPS_PHASE_LOADER:{
					projection = new String[]{
							S4K_Database.Groups_Phase._ID,	
							S4K_Database.Groups_Phase.GROUPS_PHASE_ID,	
							S4K_Database.Groups_Phase.GROUP_ID,	
							S4K_Database.Groups_Phase.PHASE_ID,	
							S4K_Database.Groups_Phase.GROUP_PHASE_NAME};	
					String sortOder=S4K_Database.Groups_Phase.GROUP_ID+" ASC";
					cursorLoader = new CursorLoader(this,
						    Motoli_ContentProvidor.CONTENT_URI_GROUPS_PHASE, projection, null, null, sortOder);				
				 break;
			 }
			 case MotoliConstants.GROUPS_PHASE_LEVELS_LOADER:{
					projection = new String[]{
							S4K_Database.Groups_Phase_Levels._ID,	
							S4K_Database.Groups_Phase_Levels.GPL_ID,	
							S4K_Database.Groups_Phase_Levels.GROUP_ID,	
							S4K_Database.Groups_Phase_Levels.PHASE_ID,	
							S4K_Database.Groups_Phase_Levels.LEVEL_NUMBER,
							S4K_Database.Groups_Phase_Levels.GROUP_PHASE_LEVEL_NAME,
							S4K_Database.Groups_Phase_Levels.GROUP_PHASE_LEVEL_DESCRIPTION,
							S4K_Database.Groups_Phase_Levels.GROUP_PHASE_LEVEL_APP_TITLE};	
					String sortOder=S4K_Database.Groups_Phase_Levels.GPL_ID+" ASC";
					cursorLoader = new CursorLoader(this,
						    Motoli_ContentProvidor.CONTENT_URI_GROUPS_PHASE_LEVELS, projection, null, null, sortOder);				
				 break;
			 }
			 case MotoliConstants.GROUPS_PHASE_LEVELS_SECTIONS_LOADER:{
					projection = new String[]{
							S4K_Database.Groups_Phase_Levels_Sections._ID,	
							S4K_Database.Groups_Phase_Levels_Sections.GLS_ID,	
							S4K_Database.Groups_Phase_Levels_Sections.GROUP_ID,	
							S4K_Database.Groups_Phase_Levels_Sections.PHASE_ID,	
							S4K_Database.Groups_Phase_Levels_Sections.LEVEL_NUMBER,
							S4K_Database.Groups_Phase_Levels_Sections.SECTION_ID};	
					String sortOder=S4K_Database.Groups_Phase_Levels_Sections.GLS_ID+" ASC";
					cursorLoader = new CursorLoader(this,
						    Motoli_ContentProvidor.CONTENT_URI_GROUP_PHASE_LEVELS_SECTIONS, projection, null, null, sortOder);				
				 break;
			 }
			 case MotoliConstants.WORDS_LOADER:{
					projection = new String[]{
							S4K_Database.Words._ID,	
							S4K_Database.Words.WORD_ID,	
							S4K_Database.Words.HELPER_TF,	
							S4K_Database.Words.WORD_SEPERATION,	
							S4K_Database.Words.WORD_DISTRACTOR_FIRST_LETTERS,	
							S4K_Database.Words.WORD_DISTRACTOR_LAST_LETTERS,	
							S4K_Database.Words.AUDIO_URL,	
							S4K_Database.Words.IMAGE_URL,	
							S4K_Database.Words.PHONIC_SCHEME,	
							S4K_Database.Words.ACTUAL_PHONIC_SCHEME,	
							S4K_Database.Words.PHONIC_AUDIO_URL,	
							S4K_Database.Words.PHONIC_MISCA_SCHEME,	
							S4K_Database.Words.ACTUAL_MISCA_SCHEME,	
							S4K_Database.Words.PHONIC_MISCA_AUDIO_URL,	
							S4K_Database.Words.PHONIC_MISCB_SCHEME,	
							S4K_Database.Words.ACTUAL_MISCB_SCHEME,	
							S4K_Database.Words.PHONIC_MISCB_AUDIO_URL,	
							S4K_Database.Words.PHONIC_MISCC_SCHEME,	
							S4K_Database.Words.ACTUAL_MISCC_SCHEME,	
							S4K_Database.Words.PHONIC_MISCC_AUDIO_URL,								
							S4K_Database.Words.RHYMING_SCHEME,	
							S4K_Database.Words.ACTUAL_RHYMING_SCHEME,	
							S4K_Database.Words.RHYMING_AUDIO_URL,	
							S4K_Database.Words.DISTRACTOR_PHONIC_SCHEME,	
							S4K_Database.Words.DISTRACTOR_ACTUAL_PHONIC_SCHEME,	
							S4K_Database.Words.DISTRACTOR_PHONIC_AUDIO_URL,	
							S4K_Database.Words.RHYMING_ID,	
							S4K_Database.Words.PHONIC_ID,	
							S4K_Database.Words.FIRST_LETTERS,	
							S4K_Database.Words.LAST_LETTERS,
							S4K_Database.Words.DISTRACTOR_PHONIC_ID};	
					String sortOder=S4K_Database.Words.WORD_ID+" ASC";
					cursorLoader = new CursorLoader(this,
						    Motoli_ContentProvidor.CONTENT_URI_WORDS, projection, null, null, sortOder);				
				 break;
			 }
			 case MotoliConstants.WORDS_PHASE_LEVELS_LOADER:{
					projection = new String[]{
							S4K_Database.Words_Phase_Levels._ID,	
							S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_ID,	
							S4K_Database.Words_Phase_Levels.WORD_ID,	
							S4K_Database.Words_Phase_Levels.GROUP_ID,	
							S4K_Database.Words_Phase_Levels.PHASE_ID,
							S4K_Database.Words_Phase_Levels.LEVEL_NUMBER};	
					String sortOder=S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_ID+" ASC";
					cursorLoader = new CursorLoader(this,
						    Motoli_ContentProvidor.CONTENT_URI_WORDS_PHASE_LEVELS, projection, null, null, sortOder);				
				 break;
			 }			 
			 case MotoliConstants.GROUPS_PHASE_SECTIONS_ACTIVITIES_LOADER:{
					projection = new String[]{
							S4K_Database.Groups_Phase_Sections_Activities._ID,	
							S4K_Database.Groups_Phase_Sections_Activities.GPSA_ID,
							S4K_Database.Groups_Phase_Sections_Activities.GROUP_ID,
							S4K_Database.Groups_Phase_Sections_Activities.PHASE_ID,
							S4K_Database.Groups_Phase_Sections_Activities.SECTION_ID,
							S4K_Database.Groups_Phase_Sections_Activities.ACTIVITY_ID};	
					
					String sortOder=S4K_Database.Groups_Phase_Sections_Activities.GROUP_ID+" ASC, "
							+S4K_Database.Groups_Phase_Sections_Activities.PHASE_ID+" ASC, "
							+S4K_Database.Groups_Phase_Sections_Activities.SECTION_ID+" ASC, "
							+S4K_Database.Groups_Phase_Sections_Activities.ACTIVITY_ID+" ASC";
					cursorLoader = new CursorLoader(this,
						    Motoli_ContentProvidor.CONTENT_URI_GROUPS_PHASE_SECTIONS_ACTIVITIES, projection, null, null, sortOder);				
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
			 case MotoliConstants.GROUPS_PHASE_LEVELS_SECTIONS_LOADER:{
				 appData.setGroupsPhaseLevelsSections(data);
				 getLoaderManager().restartLoader(loader.getId()-1, null, this);
				 break;
			 }
			 case MotoliConstants.WORDS_LOADER:{
				// appData.setGroupsPhaseLevelsSections(data);
				 getLoaderManager().restartLoader(loader.getId()-1, null, this);
				 break;
			 }
			 case MotoliConstants.WORDS_PHASE_LEVELS_LOADER:{
				 appData.setWordsPhaseLevels(data);
				 getLoaderManager().restartLoader(loader.getId()-1, null, this);
				 break;
			 }
			 case MotoliConstants.GROUPS_PHASE_SECTIONS_ACTIVITIES_LOADER:{
				 appData.setGroupsPhaseSectionsActivities(data);
				 getLoaderManager().restartLoader(loader.getId()-1, null, this);
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
