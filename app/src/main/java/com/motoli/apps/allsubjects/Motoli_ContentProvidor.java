package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class Motoli_ContentProvidor extends ContentProvider {

	
	private S4K_DBHelper dbHelper;
	
	private static final int ALL_USERS = 1; //0
	private static final int INDIVIDUAL_USER = 2; //0
	private static final int ALL_GROUPS = 3; //1
	private static final int INDIVIDUAL_GROUP = 4;//1
	private static final int ALL_SECTIONS = 5; //2
	private static final int INDIVIDUAL_SECTION = 6;//2
	private static final int ALL_LSARS = 7;//3
	private static final int INDIVIDUAL_LSARS = 8;//3
	private static final int ALL_ACTIVITIES = 9;//4
	private static final int INDIVIDUAL_ACTIVITIES = 10;//4
	private static final int ALL_GROUPS_PHASE = 11;//5
	private static final int INDIVIDUAL_GROUP_PHASE = 12;//5	
	private static final int ALL_GROUPS_PHASE_LEVELS = 13;//6
	private static final int INDIVIDUAL_GROUP_PHASE_LEVELS = 14;//6		
	private static final int ALL_GROUPS_PHASE_LEVELS_SECTIONS = 15;//7
	private static final int INDIVIDUAL_GROUP_PHASE_LEVELS_SECTIONS = 16;//7		
	private static final int ALL_WORDS = 17;//8
	private static final int INDIVIDUAL_WORDS = 18;//8		
	private static final int ALL_WORDS_PHASE_LEVELS = 19;//9
	private static final int INDIVIDUAL_WORDS_PHASE_LEVELS = 20;//9		
	private static final int ALL_GROUPS_PHASE_SECTIONS_ACTIVITIES = 21;
	private static final int INDIVIDUAL_GROUPS_PHASE_SECTIONS_ACTIVITIES = 22;
	private static final int WRD_LTR_ACTIVITIES = 23;
	private static final int APP_USERS_CURRENT_GPL_INDIVIDUAL = 24;
	private static final int ACTIVITY_CURRENT_WRDS_LTRS = 25;
	private static final int ACTIVITY_CURRENT_WRDS_LTRS_WRW =26;
	private static final int ACTIVITY_USER_RW_UPDATE=27;
	private static final int GROUPS_PHASE_LEVELS_UPDATE=28;
	
	private static final String AUTHORITY = "com.motoli.apps.allsubjects.Motoli_ContentProvidor";
	
	public static final Uri CONTENT_URI_APP_USERS = Uri.parse("content://" + AUTHORITY +"/app_users");
	public static final Uri CONTENT_URI_GROUPS = Uri.parse("content://" + AUTHORITY +"/groups");
	public static final Uri CONTENT_URI_SECTIONS = Uri.parse("content://" + AUTHORITY +"/sections");
	public static final Uri CONTENT_URI_LSARS = Uri.parse("content://" + AUTHORITY +"/level_section_activity_relation");
	public static final Uri CONTENT_URI_ACTIVITIES = Uri.parse("content://" + AUTHORITY +"/activities");
	public static final Uri CONTENT_URI_GROUPS_PHASE = Uri.parse("content://" + AUTHORITY +"/groups_phase");
	public static final Uri CONTENT_URI_GROUPS_PHASE_LEVELS = Uri.parse("content://" + AUTHORITY +"/groups_phase_levels");
	public static final Uri CONTENT_URI_GROUPS_PHASE_LEVELS_UPDATE = Uri.parse("content://" + AUTHORITY +"/groups_phase_levels/update");

	public static final Uri CONTENT_URI_GROUP_PHASE_LEVELS_SECTIONS = Uri.parse("content://" + AUTHORITY +"/groups_phase_levels_sections");

	
	public static final Uri CONTENT_URI_GROUP_PHASE_LEVELS_SECTIONS_INDIVIDUAL = Uri.parse("content://" + AUTHORITY +"/groups_phase_levels_sections/launch");
	public static final Uri CONTENT_URI_WRD_LTR_ACTIVITIES = Uri.parse("content://" + AUTHORITY +"/activities/wrd_ltr");

	public static final Uri CONTENT_URI_WORDS = Uri.parse("content://" + AUTHORITY +"/words");
	public static final Uri CONTENT_URI_WORDS_PHASE_LEVELS = Uri.parse("content://" + AUTHORITY +"/words_phase_levels");
	public static final Uri CONTENT_URI_GROUPS_PHASE_SECTIONS_ACTIVITIES = Uri.parse("content://" + AUTHORITY +"/groups_phase_sections_activities");
	public static final Uri CONTENT_URI_APP_USERS_CURRENT_GPL_INDIVIDUAL = Uri.parse("content://" + AUTHORITY +"/app_users_current_gpl/current_user");
	public static final Uri CONTENT_URI_ACTIVITY_CURRENT_WRDS_LTRS = Uri.parse("content://" + AUTHORITY +"/words/current");
	public static final Uri CONTENT_URI_ACTIVITY_CURRENT_WRDS_LTRS_WRW = Uri.parse("content://" + AUTHORITY +"/words/current_wrw");
	public static final Uri CONTENT_URI_ACTIVITY_USER_RW_UPDATE = Uri.parse("content://" + AUTHORITY +"/app_users_words_activities_right_wrong/update");

	
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "app_users", ALL_USERS);
		uriMatcher.addURI(AUTHORITY, "app_users/#", INDIVIDUAL_USER);
		uriMatcher.addURI(AUTHORITY, "groups", ALL_GROUPS);
		uriMatcher.addURI(AUTHORITY, "groups/#", INDIVIDUAL_GROUP);
		uriMatcher.addURI(AUTHORITY, "sections", ALL_SECTIONS);
		uriMatcher.addURI(AUTHORITY, "sections/#", INDIVIDUAL_SECTION);
		uriMatcher.addURI(AUTHORITY, "level_section_activity_relation", ALL_LSARS);
		uriMatcher.addURI(AUTHORITY, "level_section_activity_relation/#", INDIVIDUAL_LSARS);
		uriMatcher.addURI(AUTHORITY, "activities", ALL_ACTIVITIES);
		uriMatcher.addURI(AUTHORITY, "activities/#", INDIVIDUAL_ACTIVITIES);
		uriMatcher.addURI(AUTHORITY, "groups_phase", ALL_GROUPS_PHASE);
		uriMatcher.addURI(AUTHORITY, "groups_phase/#", INDIVIDUAL_GROUP_PHASE);
		uriMatcher.addURI(AUTHORITY, "groups_phase_levels", ALL_GROUPS_PHASE_LEVELS);
		uriMatcher.addURI(AUTHORITY, "groups_phase_levels/update", GROUPS_PHASE_LEVELS_UPDATE);
		uriMatcher.addURI(AUTHORITY, "groups_phase_levels/#", INDIVIDUAL_GROUP_PHASE_LEVELS);	
		uriMatcher.addURI(AUTHORITY, "groups_phase_levels_sections", ALL_GROUPS_PHASE_LEVELS_SECTIONS);
		uriMatcher.addURI(AUTHORITY, "groups_phase_levels_sections/*", INDIVIDUAL_GROUP_PHASE_LEVELS_SECTIONS);		
		uriMatcher.addURI(AUTHORITY, "words", ALL_WORDS);
		uriMatcher.addURI(AUTHORITY, "words/#", INDIVIDUAL_WORDS);	
		uriMatcher.addURI(AUTHORITY, "words_phase_levels", ALL_WORDS_PHASE_LEVELS);
		uriMatcher.addURI(AUTHORITY, "words_phase_levels/#", INDIVIDUAL_WORDS_PHASE_LEVELS);	
		uriMatcher.addURI(AUTHORITY, "groups_phase_sections_activities", ALL_GROUPS_PHASE_SECTIONS_ACTIVITIES);
		uriMatcher.addURI(AUTHORITY, "groups_phase_sections_activities/#", INDIVIDUAL_GROUPS_PHASE_SECTIONS_ACTIVITIES);	
		uriMatcher.addURI(AUTHORITY, "activities/wrd_ltr", WRD_LTR_ACTIVITIES);	
		uriMatcher.addURI(AUTHORITY, "app_users_current_gpl/current_user", APP_USERS_CURRENT_GPL_INDIVIDUAL);	
		uriMatcher.addURI(AUTHORITY, "words/current", ACTIVITY_CURRENT_WRDS_LTRS);	
		uriMatcher.addURI(AUTHORITY, "words/current_wrw", ACTIVITY_CURRENT_WRDS_LTRS_WRW);	
		uriMatcher.addURI(AUTHORITY, "/app_users_words_activities_right_wrong/update", ACTIVITY_USER_RW_UPDATE);

	}
	
	public Motoli_ContentProvidor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCreate() {
		dbHelper = new S4K_DBHelper(getContext());
		
		return false;
	}

	 @Override
	 public Cursor query(Uri uri, String[] projection, String selection,
	   String[] selectionArgs, String sortOrder) {
	 
	  SQLiteDatabase db = dbHelper.getWritableDatabase();
	  SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	  
	  String rawSql="";
	  Cursor cursor =null;
	 
	  
	  String words_tblname=S4K_Database.Words.WORDS_TABLE_NAME;
	  String words_phase_levels_tblname=S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME;
	  
	  
	  switch (uriMatcher.match(uri)) {
		  case ALL_USERS:
			  queryBuilder.setTables(S4K_Database.App_Users.APP_USERS_TABLE_NAME);	  
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case INDIVIDUAL_USER:
			  queryBuilder.setTables(S4K_Database.App_Users.APP_USERS_TABLE_NAME);
			   String id = uri.getPathSegments().get(1);
			   queryBuilder.appendWhere(S4K_Database.App_Users._ID + "=" + id);
			   cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			 break;
		  case ALL_GROUPS:
			  queryBuilder.setTables(S4K_Database.Groups.GROUPS_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case INDIVIDUAL_GROUP:
			  queryBuilder.setTables(S4K_Database.Groups.GROUPS_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case ALL_SECTIONS:
			  queryBuilder.setTables(S4K_Database.Sections.SECTIONS_TABLE_NAME);
			  cursor=S4K_Database.Sections.getAllSections(db, sortOrder);
			  //cursor = queryBuilder.query(db, projection, selection,
			//		    selectionArgs, null, null, sortOrder);
			  break;
		  case INDIVIDUAL_SECTION:
			  queryBuilder.setTables(S4K_Database.Sections.SECTIONS_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case ALL_LSARS:
			  queryBuilder.setTables(S4K_Database.Level_Section_Activity_Relation.LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case INDIVIDUAL_LSARS:
			  queryBuilder.setTables(S4K_Database.Level_Section_Activity_Relation.LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;	
		  case ALL_ACTIVITIES:
			  queryBuilder.setTables(S4K_Database.Activities.ACTIVITIES_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case INDIVIDUAL_ACTIVITIES:
			  queryBuilder.setTables(S4K_Database.Activities.ACTIVITIES_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;				  
		  case ALL_GROUPS_PHASE:
			  queryBuilder.setTables(S4K_Database.Groups_Phase.GROUPS_PHASE_TABLE_NAME);
			  cursor=queryBuilder.query(db, projection, selection,
							    selectionArgs, null, null, sortOrder);
					  //S4K_Database.Sections.getAllSections(db, sortOrder);
			  //cursor = queryBuilder.query(db, projection, selection,
			//		    selectionArgs, null, null, sortOrder);
			  break;
		  case INDIVIDUAL_GROUP_PHASE:
			  queryBuilder.setTables(S4K_Database.Groups_Phase.GROUPS_PHASE_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case ALL_GROUPS_PHASE_LEVELS:
			  queryBuilder.setTables(S4K_Database.Groups_Phase_Levels.GROUPS_PHASE_LEVELS_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case GROUPS_PHASE_LEVELS_UPDATE:
			  rawSql="SELECT * FROM "+S4K_Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME;
			  
			  
			  
			  rawSql="SELECT COUNT(gpl_id) AS countLevels FROM groups_phase_levels  "
			  	+" WHERE groups_phase_levels.group_id="+projection[0]+" AND groups_phase_levels.phase_id="+projection[1];
			  cursor = db.rawQuery(rawSql, null);
			  cursor.moveToFirst();
			  String highestLevel=cursor.getString(cursor.getColumnIndex("countLevels"));
			  
			  rawSql="SELECT MIN(app_users_words_activities_right_wrong.number_correct_in_a_row) AS minNumberCorrect, words_phase_levels.* "
				+" FROM app_users_words_activities_right_wrong "
				+" LEFT JOIN words_phase_levels "
				+" ON words_phase_levels.word_id = app_users_words_activities_right_wrong.word_id "
				+" WHERE words_phase_levels.group_id="+projection[0]+" AND words_phase_levels.phase_id="+projection[1]
				+" AND words_phase_levels.level_number="+projection[3];
			  cursor = db.rawQuery(rawSql, null);
			  cursor.moveToFirst();
			  int minNumberCorrect =cursor.getInt(cursor.getColumnIndex("minNumberCorrect"));
			  MatrixCursor extras = new MatrixCursor(new String[] { "current_phase" });
			  
			  if(minNumberCorrect==3){
				  if(projection[0].equals("2") && projection[1].equals("1") && projection[3].equals(highestLevel)){
					  rawSql="UPDATE app_users_current_gpl SET app_user_current_level=app_user_current_level+1 "
							  +" WHERE group_id="+projection[0]+" AND phase_id=2 AND app_user_id="+selection;
					  db.execSQL(rawSql);
					  String[] newProjection=new String[]{"0",selection,projection[0],"2",projection[2],projection[3]};
					  String newSelection=S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.GROUP_ID+"="+projection[0]
								+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.PHASE_ID+"=2"
								+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.LEVEL_NUMBER+"<=1";
							  
					  updateWordsCorrectIncorrect(newProjection, newSelection);

					  	
					  extras.addRow(new String[] { "1"});
				  }else  if(projection[0].equals("2") && projection[1].equals("2") && projection[3].equals(highestLevel)){
					  rawSql="UPDATE app_users_current_gpl SET app_user_current_level=app_user_current_level+1 "
							  +" WHERE group_id="+projection[0]+" AND phase_id=3 AND app_user_id="+selection;
					  db.execSQL(rawSql);
					  
					  String[] newProjection=new String[]{"0",selection,projection[0],"3",projection[2],projection[3]};
					  String newSelection=S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.GROUP_ID+"="+projection[0]
								+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.PHASE_ID+"=3"
								+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.LEVEL_NUMBER+"<=1";
							  
					  updateWordsCorrectIncorrect(newProjection, newSelection);
					  
					  extras.addRow(new String[] { "2"});
				  }else if(!projection[3].equals(highestLevel)){
					  rawSql="UPDATE app_users_current_gpl SET app_user_current_level=app_user_current_level+1 "
							  +" WHERE group_id="+projection[0]+" AND phase_id="+projection[1]+" AND app_user_id="+selection;
					  db.execSQL(rawSql);
					  String[] newProjection=new String[]{"0",selection,projection[0],projection[1],projection[2],projection[3]};
					  String newSelection=S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.GROUP_ID+"="+projection[0]
								+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.PHASE_ID+"="+projection[1]
								+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.LEVEL_NUMBER+"<="+projection[3];
							  
					  updateWordsCorrectIncorrect(newProjection, newSelection);
					  extras.addRow(new String[] { "3"});
				  }
			  }else{
				  String[] newProjection=new String[]{"0",selection,projection[0],projection[1],projection[2],projection[3]};
				  String newSelection=S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.GROUP_ID+"="+projection[0]
							+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.PHASE_ID+"="+projection[1]
							+" AND "+S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME+"."+S4K_Database.Words_Phase_Levels.LEVEL_NUMBER+"<="+projection[3];
						  
				  updateWordsCorrectIncorrect(newProjection, newSelection);
				  extras.addRow(new String[] { "0"});
			  }
			  String[] newProjection = new String[]{
						S4K_Database.Groups_Phase_Levels._ID,	
						S4K_Database.Groups_Phase_Levels.GPL_ID,	
						S4K_Database.Groups_Phase_Levels.GROUP_ID,	
						S4K_Database.Groups_Phase_Levels.PHASE_ID,	
						S4K_Database.Groups_Phase_Levels.LEVEL_NUMBER,
						S4K_Database.Groups_Phase_Levels.GROUP_PHASE_LEVEL_NAME,
						S4K_Database.Groups_Phase_Levels.GROUP_PHASE_LEVEL_DESCRIPTION,
						S4K_Database.Groups_Phase_Levels.GROUP_PHASE_LEVEL_APP_TITLE};	
				String newSortOder=S4K_Database.Groups_Phase_Levels.GPL_ID+" ASC";
				//cursorLoader = new CursorLoader(this,
				//	    Motoli_ContentProvidor.CONTENT_URI_GROUPS_PHASE_LEVELS, projection, null, null, sortOder);
			  
				//  queryBuilder.setTables(S4K_Database.Groups_Phase_Levels.GROUPS_PHASE_LEVELS_TABLE_NAME);
				//  cursor = queryBuilder.query(db, projection, selection,
				//		    selectionArgs, null, null, sortOrder);
				  
			  queryBuilder.setTables(S4K_Database.Groups_Phase_Levels.GROUPS_PHASE_LEVELS_TABLE_NAME);
			  Cursor cursorGPL = queryBuilder.query(db, newProjection, null,
					    null, null, null, newSortOder);
			  
			
			  Cursor[] cursors = { extras, cursorGPL };
			  cursor = new MergeCursor(cursors);
			  break;			  
		  case INDIVIDUAL_GROUP_PHASE_LEVELS:
			  queryBuilder.setTables(S4K_Database.Groups_Phase_Levels.GROUPS_PHASE_LEVELS_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;	
		  case ALL_GROUPS_PHASE_LEVELS_SECTIONS:
			  queryBuilder.setTables(S4K_Database.Groups_Phase_Levels_Sections.GROUPS_PHASE_LEVELS_SECTIONS_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case INDIVIDUAL_GROUP_PHASE_LEVELS_SECTIONS:			  
			 // queryBuilder.setTables(S4K_Database.Groups_Phase_Levels_Sections.GROUPS_PHASE_LEVELS_SECTIONS_TABLE_NAME);
			  if(selection==null)
				  selection="0";
			  
			  rawSql="SELECT  groups_phase_levels_sections.group_id, MIN(groups_phase_levels_sections.phase_id) AS lw_phase_id, "
					  +" groups_phase_levels_sections.level_number, groups_phase_levels_sections.section_id,  sections.section_name, "
					  +" sections.section_image, sections.section_all_inclusive, sections.section_order,"
					  +" app_users_current_gpl.app_user_current_level,  COUNT(groups_phase_sections_activities.activity_id) AS activity_count  "
					  +" FROM groups_phase_levels_sections  "
					  +" INNER JOIN sections  ON groups_phase_levels_sections.section_id=sections.section_id "
					  +" INNER JOIN app_users_current_gpl ON app_users_current_gpl.group_id=groups_phase_levels_sections.group_id "
					  +" AND app_users_current_gpl.phase_id=groups_phase_levels_sections.phase_id "
					  +" LEFT JOIN groups_phase_sections_activities ON groups_phase_sections_activities.group_id=groups_phase_levels_sections.group_id "
					  +" AND groups_phase_sections_activities.phase_id=groups_phase_levels_sections.phase_id" 
					  +" AND groups_phase_sections_activities.section_id=groups_phase_levels_sections.section_id"
					  +" WHERE groups_phase_levels_sections.level_number=1 "
					  +" AND app_users_current_gpl.app_user_id="+selection
					  +" GROUP BY groups_phase_levels_sections.section_id "
					  +" ORDER BY groups_phase_levels_sections.section_id ASC, groups_phase_levels_sections.group_id ASC, "
					  +" groups_phase_levels_sections.phase_id ASC";
			  cursor = db.rawQuery(rawSql, null);
			  

			  break;
		  case ALL_WORDS:
			  queryBuilder.setTables(S4K_Database.Words.WORDS_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case INDIVIDUAL_WORDS:
			  queryBuilder.setTables(S4K_Database.Words.WORDS_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;	
		  case ALL_WORDS_PHASE_LEVELS:
			  queryBuilder.setTables(S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case INDIVIDUAL_WORDS_PHASE_LEVELS:
			  queryBuilder.setTables(S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case ALL_GROUPS_PHASE_SECTIONS_ACTIVITIES:
			  queryBuilder.setTables(S4K_Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case INDIVIDUAL_GROUPS_PHASE_SECTIONS_ACTIVITIES:
			  queryBuilder.setTables(S4K_Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME);
			  cursor = queryBuilder.query(db, projection, selection,
					    selectionArgs, null, null, sortOrder);
			  break;
		  case WRD_LTR_ACTIVITIES:
			//  queryBuilder.setTables(S4K_Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME);
			//  cursor = queryBuilder.query(db, projection, selection,
			//		    selectionArgs, null, null, sortOrder);
			  
			  
			  
			  
			  String gps_tblname=S4K_Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME;
			  String act_tblname=S4K_Database.Activities.ACTIVITIES_TABLE_NAME;

			  rawSql="SELECT * FROM "+gps_tblname
					  +" INNER JOIN "+S4K_Database.Activities.ACTIVITIES_TABLE_NAME
					  +" ON "+act_tblname+"."+S4K_Database.Activities.ACTIVITY_ID
					  +"="+gps_tblname+"."+S4K_Database.Groups_Phase_Sections_Activities.ACTIVITY_ID +" "
					  +"WHERE "+selection+" ORDER BY "+sortOrder;
			  			
			  cursor = db.rawQuery(rawSql, null);

			  break;	
		  case APP_USERS_CURRENT_GPL_INDIVIDUAL:
			  queryBuilder.setTables(S4K_Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME);
			  rawSql="SELECT * FROM "+S4K_Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME
					  +" WHERE "+selection+" ORDER BY "+sortOrder;
			  cursor = db.rawQuery(rawSql, null);	  
			  //cursor = queryBuilder.query(db, projection, selection,
					  //selectionArgs, null, null, sortOrder);
			  break;	  
		  case ACTIVITY_CURRENT_WRDS_LTRS:

			  
			  rawSql="SELECT "+words_tblname+".* FROM "+words_tblname
					  +" INNER JOIN "+words_phase_levels_tblname
					  +" ON "+words_phase_levels_tblname+"."+S4K_Database.Words_Phase_Levels.WORD_ID
					  +"="+words_tblname+"."+S4K_Database.Words.WORD_ID+" "
					  +" WHERE "+selection+" ORDER BY "+words_tblname+"."+S4K_Database.Words.WORD_TEXT+" ASC";
			  cursor = db.rawQuery(rawSql, null);

			  break;
		  case ACTIVITY_CURRENT_WRDS_LTRS_WRW:
			  String auwarw_tblname=S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_TABLE_NAME;
			  
			  /*
			  rawSql="SELECT * FROM "+S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_TABLE_NAME
			  		+" WHERE ";
			  
			  rawSql="SELECT "+auwarw_tblname+"._id AS auwarw_id_correct, "+auwarw_tblname+".*, "+words_tblname+".* FROM "+words_tblname
					  +" INNER JOIN "+words_phase_levels_tblname
					  +" ON "+words_phase_levels_tblname+"."+S4K_Database.Words_Phase_Levels.WORD_ID
					  +"="+words_tblname+"."+S4K_Database.Words.WORD_ID+" "
					  +" LEFT JOIN "+S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_TABLE_NAME
					  +" ON "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.WORD_ID+"="+words_tblname+"."+S4K_Database.Words.WORD_ID 
					  +" AND "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.ACTIVITY_ID+"="+projection[0]
					  +" AND "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.APP_USER_ID+"="+projection[1]
					  +" WHERE "+selection+"";
			  cursor = db.rawQuery(rawSql, null);
			  int blankCount=0;
			  
			  while(cursor.moveToNext()){
				  String auwarw_id=cursor.getString(cursor.getColumnIndex("auwarw_id_correct"));
				  if(auwarw_id == null){

					  blankCount++;
					  ContentValues values = new ContentValues();
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.WORD_ID, cursor.getString(cursor.getColumnIndex(S4K_Database.Words.WORD_ID)));
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.ACTIVITY_ID, projection[0]);
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.APP_USER_ID, projection[1]);
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT_IN_ROW, "0");
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT, "0");
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_INCORRECT, "0");

					
					  long current_id =db.insert(auwarw_tblname, null, values);
					  
					  rawSql="UPDATE "+auwarw_tblname+" SET "+S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_ID
						+"="+String.valueOf(current_id)
						+" WHERE "+S4K_Database.App_Users_Words_Activities_Right_Wrong._ID
						+"="+String.valueOf(current_id);
					
					  db.execSQL(rawSql);
						
					  String hello="eeee";
					  Log.d(MotoliConstants.LOGCAT,	hello);
					  
					 // db.execSQL(rawSql);
					  //db.in
					  
				  }else if(cursor.getInt(cursor.getColumnIndex(S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT))>=MotoliConstants.NUMBER_CORRECT){
					  blankCount++;
				  }//end if(auwarw_id.equals("")){
				  
			  }//end  while(cursor.moveToNext()){
			  
			  String[] returnString= 
			  */
			  
			  if(sortOrder!=null)
				  updateWordsCorrectIncorrect(projection, sortOrder);
			  else
				  updateWordsCorrectIncorrect(projection, selection);
			  
			  
			  rawSql="SELECT "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_ID+","
					  +auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT+","
					  +auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT_IN_ROW+","
					  +auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_INCORRECT+","
					  +words_tblname+".* FROM "+words_tblname
					  +" INNER JOIN "+words_phase_levels_tblname
					  +" ON "+words_phase_levels_tblname+"."+S4K_Database.Words_Phase_Levels.WORD_ID
					  +"="+words_tblname+"."+S4K_Database.Words.WORD_ID+" "
					  +" LEFT JOIN "+S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_TABLE_NAME
					  +" ON "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.WORD_ID+"="+words_tblname+"."+S4K_Database.Words.WORD_ID 
					  +" AND "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.ACTIVITY_ID+"="+projection[0]
					  +" AND "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.APP_USER_ID+"="+projection[1]
					  +" WHERE "+selection+" ";
			  
			//  if(blankCount==cursor.getCount()){
			//	  rawSql+=" ORDER BY RANDOM() ";
			//  }else{
				  rawSql+="  ORDER BY "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_INCORRECT+" DESC,  RANDOM()"; 
			//  }
			  
			  
			  cursor = db.rawQuery(rawSql, null);
			  	
				ArrayList<ArrayList<String>> currentWords=new ArrayList<ArrayList<String>>();
				int currentWordNumber=0;
				while(cursor.moveToNext()){
					currentWords.add(new ArrayList<String>());
					currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("word_id")));
					currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("word_text")));
					currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("audio_url")));
					currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("number_correct")));
					currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("number_incorrect")));
					currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("image_url")));

					
					currentWordNumber++;
				}
				currentWordNumber++;
			  break;			  
		default:
		   throw new IllegalArgumentException("Unsupported URI: " + uri);
	  }
	 

	  return cursor;
	 
	 }
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	 private String[] updateWordsCorrectIncorrect(String[] projection, String whereSection) {
		  SQLiteDatabase db = dbHelper.getWritableDatabase();
		  
		  String rawSql="";
		  Cursor cursor =null;
		  Cursor cursorSecond =null;
		 
		  
		  String words_tblname=S4K_Database.Words.WORDS_TABLE_NAME;
		  String words_phase_levels_tblname=S4K_Database.Words_Phase_Levels.WORDS_PHASE_LEVELS_TABLE_NAME;
		  int blankCount=0;

		  String auwarw_tblname=S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_TABLE_NAME;
		  
		  rawSql="SELECT * FROM "+S4K_Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME+" "
				  +" WHERE "+S4K_Database.Groups_Phase_Sections_Activities.GROUP_ID+"="+projection[2]+" "
				  +" AND "+S4K_Database.Groups_Phase_Sections_Activities.PHASE_ID+"="+projection[3]+" "
				  +" AND "+S4K_Database.Groups_Phase_Sections_Activities.SECTION_ID+"="+projection[4];
		  
		  cursor = db.rawQuery(rawSql, null);
		  while(cursor.moveToNext()){
			  String activity_id=cursor.getString(cursor.getColumnIndex(S4K_Database.App_Users_Words_Activities_Right_Wrong.ACTIVITY_ID));
		

			  rawSql="SELECT "+auwarw_tblname+"._id AS auwarw_id_correct, "+auwarw_tblname+".*, "+words_tblname+".* FROM "+words_tblname
					  +" INNER JOIN "+words_phase_levels_tblname
					  +" ON "+words_phase_levels_tblname+"."+S4K_Database.Words_Phase_Levels.WORD_ID
					  +"="+words_tblname+"."+S4K_Database.Words.WORD_ID+" "
					  +" LEFT JOIN "+S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_TABLE_NAME
					  +" ON "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.WORD_ID+"="+words_tblname+"."+S4K_Database.Words.WORD_ID 
					  +" AND "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.ACTIVITY_ID+"="+activity_id
					  +" AND "+auwarw_tblname+"."+S4K_Database.App_Users_Words_Activities_Right_Wrong.APP_USER_ID+"="+projection[1]
					  +" WHERE "+whereSection+"";
			  cursorSecond = db.rawQuery(rawSql, null);
			  
			  while(cursorSecond.moveToNext()){
				  String auwarw_id=cursorSecond.getString(cursorSecond.getColumnIndex("auwarw_id_correct"));
				  if(auwarw_id == null){
	
					  blankCount++;
					  ContentValues values = new ContentValues();
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.WORD_ID, cursorSecond.getString(cursorSecond.getColumnIndex(S4K_Database.Words.WORD_ID)));
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.ACTIVITY_ID, activity_id);
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.APP_USER_ID, projection[1]);
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT_IN_ROW, "0");
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT, "0");
					  values.put(S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_INCORRECT, "0");
	
					
					  long current_id =db.insert(auwarw_tblname, null, values);
					  
					  rawSql="UPDATE "+auwarw_tblname+" SET "+S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_ID
						+"="+String.valueOf(current_id)
						+" WHERE "+S4K_Database.App_Users_Words_Activities_Right_Wrong._ID
						+"="+String.valueOf(current_id);
					
					  db.execSQL(rawSql);
		
				  }else if(cursorSecond.getInt(cursorSecond.getColumnIndex(S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT))>=MotoliConstants.NUMBER_CORRECT){
					  blankCount++;
				  }//end if(auwarw_id.equals("")){
				  
			  }//end  while(cursor.moveToNext()){
		  }
		  int curCount=cursor.getCount();
		  
		  String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
		  cursor.close();
		  return returnString;
		  
	 }
	 
	 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 
	@Override
	public String getType(Uri uri) {

		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		  SQLiteDatabase db = dbHelper.getWritableDatabase();
		  SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		  
		  String rawSql="";
		  Cursor cursor =null;		  
		  
		  switch (uriMatcher.match(uri)) {
				default:
					break;
				case ACTIVITY_USER_RW_UPDATE:{
					int numberCorrectInARow=0;
					rawSql="SELECT "+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT_IN_ROW
								+" FROM "+S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_TABLE_NAME+" "+selection;
					cursor = db.rawQuery(rawSql, null);
					
					cursor.moveToFirst();
					numberCorrectInARow=cursor.getInt(cursor.getColumnIndex(S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT_IN_ROW));
					
					//this is hard coded will need to fix
					if(!(selectionArgs[3].equals("1") && (selectionArgs[4].equals("11572") || selectionArgs[4].equals("11573"))) 
							&& !(selectionArgs[3].equals("2") && selectionArgs[4].equals("11573"))){
						
					
						rawSql="UPDATE "+S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_TABLE_NAME+" ";
						rawSql+=" SET "+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT+"="
									+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT+"+"+selectionArgs[0]+", "
									+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_INCORRECT+"="
									+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_INCORRECT+"+"+selectionArgs[1];
						if(numberCorrectInARow<MotoliConstants.NUMBER_INA_ROW_CORRECT){
							if(Integer.parseInt(selectionArgs[0])==1){
								rawSql+=", "+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT_IN_ROW+"="
										+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT_IN_ROW+"+1 ";
							}else{
								rawSql+=", "+S4K_Database.App_Users_Words_Activities_Right_Wrong.NUMBER_CORRECT_IN_ROW+"=0 ";
							}
						}
						rawSql+=selection;
						db.execSQL(rawSql);
					}
					//	db.update(S4K_Database.App_Users_Words_Activities_Right_Wrong.AUWARW_TABLE_NAME, values, selection, null);
					break;
				}
		  }
	
		  return 0;
	}

}
