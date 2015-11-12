package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;


public class MotoliContentProvider extends ContentProvider {

	
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
	private static final int ALL_GROUPS_PHASE_SECTIONS = 15;//7
	private static final int INDIVIDUAL_GROUP_PHASE_SECTIONS = 16;//7
	private static final int ALL_WORDS = 17;//8
	private static final int INDIVIDUAL_WORDS = 18;//8		
	private static final int ALL_WORDS_PHASE_LEVELS = 19;//9
	private static final int INDIVIDUAL_WORDS_PHASE_LEVELS = 20;//9
	private static final int ALL_GROUPS_PHASE_SECTIONS_ACTIVITIES = 21;
	private static final int INDIVIDUAL_GROUPS_PHASE_SECTIONS_ACTIVITIES = 22;
	private static final int WRD_LTR_ACTIVITIES = 23;
	private static final int APP_USERS_CURRENT_GPL_INDIVIDUAL = 24;
	private static final int ACTIVITY_CURRENT_WRDS_LTRS = 25;
	private static final int ACTIVITY_CURRENT_WORDS_WRW =26;
	private static final int ACTIVITY_USER_RW_UPDATE=27;
	private static final int GROUPS_PHASE_LEVELS_UPDATE=28;
    private static final int CURRENT_PHNC_LTRS=29;
    private static final int CURRENT_PHNC_LTRS_WRDS=30;
    private static final int CURRENT_MAX_LEVEL_GROUP_PHASE=31;
    private static final int INDIVIDUAL_GPL=32;
    private static final int CURRENT_SYLLABLE_WORDS=33;
    private static final int CURRENT_SYLLABLES=34;
    private static final int CURRENT_WORD_SYLLABLE_VALUES=35;
    private static final int BOOKS_READ_ALOUD=36;
    private static final int BOOK_PAGES_READ_ALOUD=37;
    private static final int ACTIVITY_CURRENT_LETTERS_WRW=38;
    private static final int ACTIVITY_CURRENT_LETTERS=39;
    private static final int ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE=40;
    private static final int PROGRESS_GROUPS=41;
    private static final int PROGRESS_POINTS=42;
    private static final int CURRENT_WORDS_BY_LEVEL=43;
    private static final int GUESS_WORDS=44;
    private static final int ALL_LOWERCASE_LETTERS=45;



	private static final String AUTHORITY = "com.motoli.apps.allsubjects.Motoli_ContentProvidor";
	
	public static final Uri CONTENT_URI_APP_USERS = Uri.parse("content://" + AUTHORITY +"/app_users");
	public static final Uri CONTENT_URI_GROUPS = Uri.parse("content://" + AUTHORITY +"/groups");
	public static final Uri CONTENT_URI_SECTIONS = Uri.parse("content://" + AUTHORITY +"/sections");
	public static final Uri CONTENT_URI_LSARS = Uri.parse("content://" + AUTHORITY +"/level_section_activity_relation");
	public static final Uri CONTENT_URI_ACTIVITIES = Uri.parse("content://" + AUTHORITY +"/activities");
	public static final Uri CONTENT_URI_GROUPS_PHASE = Uri.parse("content://" + AUTHORITY +"/groups_phase");
	public static final Uri CONTENT_URI_GROUPS_PHASE_LEVELS = Uri.parse("content://" + AUTHORITY +"/groups_phase_levels");
	public static final Uri CONTENT_URI_GROUPS_PHASE_LEVELS_UPDATE = Uri.parse("content://" + AUTHORITY +"/groups_phase_levels/update");

	public static final Uri CONTENT_URI_GROUP_PHASE_SECTIONS = Uri.parse("content://" + AUTHORITY +"/groups_phase_sections");

	
	public static final Uri CONTENT_URI_GROUP_PHASE_SECTIONS_INDIVIDUAL = Uri.parse("content://" + AUTHORITY +"/groups_phase_sections/launch");
	public static final Uri CONTENT_URI_WRD_LTR_ACTIVITIES = Uri.parse("content://" + AUTHORITY +"/activities/wrd_ltr");

	public static final Uri CONTENT_URI_WORDS = Uri.parse("content://" + AUTHORITY +"/words");
	public static final Uri CONTENT_URI_WORDS_PHASE_LEVELS = Uri.parse("content://" + AUTHORITY +"/variable_phase_levels");
	public static final Uri CONTENT_URI_GROUPS_PHASE_SECTIONS_ACTIVITIES = Uri.parse("content://" + AUTHORITY +"/groups_phase_sections_activities");
	public static final Uri CONTENT_URI_APP_USERS_CURRENT_GPL_INDIVIDUAL = Uri.parse("content://" + AUTHORITY +"/app_users_current_gpl/current_user");
	//public static final Uri CONTEN_URI_INDIVIDUAL_GPL=Uri.parse("content://"+ AUTHORITY+"/app_users_current_gpl/individual_gpl");
    public static final Uri CONTENT_URI_ACTIVITY_CURRENT_LETTERS = Uri.parse("content://" + AUTHORITY +"/letters/current");

    public static final Uri CONTENT_URI_ACTIVITY_CURRENT_WORDS_WRW = Uri.parse("content://" + AUTHORITY +"/words/current_wrw");
    public static final Uri CONTENT_URI_ACTIVITY_CURRENT_LETTERS_WRW= Uri.parse("content://" + AUTHORITY +"/letters/current_wrw");
	public static final Uri CONTENT_URI_ACTIVITY_USER_RW_UPDATE = Uri.parse("content://" + AUTHORITY +"/app_users_activity_variable_values/update");
    public static final Uri CONTENT_URI_CURRENT_PHNC_LTRS = Uri.parse("content://" + AUTHORITY +"/phonics");
    public static final Uri CONTENT_URI_CURRENT_PHNC_LTRS_WRDS= Uri.parse("content://" + AUTHORITY +"/phonic_words");
	public static final Uri CONTENT_URI_MAX_LEVEL_GROUP_PHASE=Uri.parse("content://"+AUTHORITY+"/max_level");
    public static final Uri CONTENT_URI_CURRENT_SYLLABLE_WORDS=Uri.parse("content://"+AUTHORITY+"/syllable_words");
    public static final Uri CONTENT_URI_CURRENT_SYLLABLES=Uri.parse("content://"+AUTHORITY+"/syllables");
    public static final Uri CONTENT_URI_CURRENT_WORD_SYLLABLE_VALUES=Uri.parse("content://"+AUTHORITY+"/words/syllable");
    public static final Uri CONTENT_URI_BOOKS_READ_ALOUD=Uri.parse("content://"+AUTHORITY+"/books/read_aloud");
    public static final Uri CONTENT_URI_BOOK_PAGES_READ_ALOUD=Uri.parse("content://"+AUTHORITY+"/books/pages_read_aloud");
    public static final Uri CONTENT_URI_ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE=Uri.parse("content://"+AUTHORITY+"/activities/complete");
    public static final Uri CONTENT_URI_PROGRESS_GROUPS=Uri.parse("content://"+AUTHORITY+"/progress/groups");
    public static final Uri CONTENT_URI_PROGRESS_POINTS=Uri.parse("content://"+AUTHORITY+"/progress/points");

    public static final Uri CONTENT_URI_CURRENT_WORDS_BY_LEVEL=Uri.parse("content://"+AUTHORITY+"/words/level");

    public static final Uri CONTENT_URI_GUESS_WORDS=Uri.parse("content://"+AUTHORITY+"/words/guess");
    public static final Uri CONTENT_URI_ALL_LOWERCASE_LETTERS=Uri.parse("content://"+AUTHORITY+"/letters/all_lower");


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
		uriMatcher.addURI(AUTHORITY, "groups_phase_sections", ALL_GROUPS_PHASE_SECTIONS);
		uriMatcher.addURI(AUTHORITY, "groups_phase_sections/*", INDIVIDUAL_GROUP_PHASE_SECTIONS);
		uriMatcher.addURI(AUTHORITY, "words", ALL_WORDS);
		uriMatcher.addURI(AUTHORITY, "words/#", INDIVIDUAL_WORDS);
		uriMatcher.addURI(AUTHORITY, "variable_phase_levels", ALL_WORDS_PHASE_LEVELS);
		uriMatcher.addURI(AUTHORITY, "variable_phase_levels/#", INDIVIDUAL_WORDS_PHASE_LEVELS);
		uriMatcher.addURI(AUTHORITY, "groups_phase_sections_activities", ALL_GROUPS_PHASE_SECTIONS_ACTIVITIES);
		uriMatcher.addURI(AUTHORITY, "groups_phase_sections_activities/#", INDIVIDUAL_GROUPS_PHASE_SECTIONS_ACTIVITIES);	
		uriMatcher.addURI(AUTHORITY, "activities/wrd_ltr", WRD_LTR_ACTIVITIES);	
		uriMatcher.addURI(AUTHORITY, "app_users_current_gpl/current_user", APP_USERS_CURRENT_GPL_INDIVIDUAL);
        //uriMatcher.addURI(AUTHORITY, "app_users_current_gpl/individual_gpl", INDIVIDUAL_GPL);
        uriMatcher.addURI(AUTHORITY, "letters/current", ACTIVITY_CURRENT_LETTERS);

        uriMatcher.addURI(AUTHORITY, "words/current_wrw", ACTIVITY_CURRENT_WORDS_WRW);
        uriMatcher.addURI(AUTHORITY, "letters/current_wrw",ACTIVITY_CURRENT_LETTERS_WRW);
		uriMatcher.addURI(AUTHORITY, "app_users_activity_variable_values/update", ACTIVITY_USER_RW_UPDATE);
        uriMatcher.addURI(AUTHORITY, "phonics", CURRENT_PHNC_LTRS);
        uriMatcher.addURI(AUTHORITY, "phonic_words",CURRENT_PHNC_LTRS_WRDS);
        uriMatcher.addURI(AUTHORITY, "max_level",CURRENT_MAX_LEVEL_GROUP_PHASE);
        uriMatcher.addURI(AUTHORITY, "syllable_words",CURRENT_SYLLABLE_WORDS);
        uriMatcher.addURI(AUTHORITY, "syllables",CURRENT_SYLLABLES);
        uriMatcher.addURI(AUTHORITY, "words/syllable",CURRENT_WORD_SYLLABLE_VALUES);
        uriMatcher.addURI(AUTHORITY, "books/read_aloud",BOOKS_READ_ALOUD);
        uriMatcher.addURI(AUTHORITY, "books/pages_read_aloud",BOOK_PAGES_READ_ALOUD);
        uriMatcher.addURI(AUTHORITY, "activities/complete",ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE);
        uriMatcher.addURI(AUTHORITY, "progress/groups", PROGRESS_GROUPS);
        uriMatcher.addURI(AUTHORITY, "progress/points", PROGRESS_POINTS);
        uriMatcher.addURI(AUTHORITY, "words/level",CURRENT_WORDS_BY_LEVEL);
        uriMatcher.addURI(AUTHORITY, "words/guess",GUESS_WORDS);
        uriMatcher.addURI(AUTHORITY, "letters/all_lower", ALL_LOWERCASE_LETTERS);

    }
	
	public MotoliContentProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCreate() {
		dbHelper = new S4K_DBHelper(getContext());
		
		return false;
	}

	 @Override
    public Cursor query(Uri uri,  String[] projection, String selection,
        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String rawSql;
        Cursor cursor;

         String mAUAVVTable=Database.App_Users_Activity_Variable_Values.AUAVV_TABLE;
         String m1LettersTable=Database.Letters.LETTERS;

        String mWordsTableName=Database.Words.WORDS_TABLE_NAME;
        String mVariablePhaseLevelsTableName=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE;
        String mPhonicsTableName=Database.Phonics.PHONICS_TABLE_NAME;
        String mPhonicWordsTableName=Database.Phonic_Words.PHONIC_WORDS_TABLE_NAME;
        switch (uriMatcher.match(uri)) {
            case ALL_USERS:
              queryBuilder.setTables(Database.App_Users.APP_USERS_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case INDIVIDUAL_USER:
              queryBuilder.setTables(Database.App_Users.APP_USERS_TABLE_NAME);
               String id = uri.getPathSegments().get(1);
               queryBuilder.appendWhere(Database.App_Users._ID + "=" + id);
               cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
             break;
            case ALL_GROUPS:
              queryBuilder.setTables(Database.Groups.GROUPS_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case INDIVIDUAL_GROUP:
              queryBuilder.setTables(Database.Groups.GROUPS_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case ALL_SECTIONS:
              queryBuilder.setTables(Database.Sections.SECTIONS_TABLE_NAME);
              cursor=Database.Sections.getAllSections(db, sortOrder);
              //cursor = queryBuilder.query(db, projection, selection,
            //		    selectionArgs, null, null, sortOrder);
              break;
            case INDIVIDUAL_SECTION:
              queryBuilder.setTables(Database.Sections.SECTIONS_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case ALL_LSARS:
              queryBuilder.setTables(Database.Level_Section_Activity_Relation.LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case INDIVIDUAL_LSARS:
              queryBuilder.setTables(Database.Level_Section_Activity_Relation.LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case ALL_ACTIVITIES:
              queryBuilder.setTables(Database.Activities.ACTIVITIES_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                      selectionArgs, null, null, sortOrder);
              break;
            case BOOKS_READ_ALOUD:
                cursor = booksReadAloud(uri, projection, selection,
                        selectionArgs, sortOrder);
                break;
            case BOOK_PAGES_READ_ALOUD:
                cursor=bookPagesReadAloud(uri, projection, selection,
                        selectionArgs, sortOrder);
                break;
            case INDIVIDUAL_ACTIVITIES:
              queryBuilder.setTables(Database.Activities.ACTIVITIES_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case ALL_GROUPS_PHASE:
              queryBuilder.setTables(Database.Groups_Phase.GROUPS_PHASE_TABLE_NAME);
              cursor=queryBuilder.query(db, projection, selection,
                                selectionArgs, null, null, sortOrder);
                      //Database.Sections.getAllSections(db, sortOrder);
              //cursor = queryBuilder.query(db, projection, selection,
            //		    selectionArgs, null, null, sortOrder);
              break;
            case INDIVIDUAL_GROUP_PHASE:
              queryBuilder.setTables(Database.Groups_Phase.GROUPS_PHASE_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case CURRENT_MAX_LEVEL_GROUP_PHASE:
                rawSql="SELECT  groups_phase_levels.group_id, groups_phase_levels.phase_id, " +
                        " MAX( groups_phase_levels.level_number) AS max_level_number" +
                        " FROM groups_phase_levels "+
                        "GROUP BY  groups_phase_levels.group_id, groups_phase_levels.phase_id "+
                        " ORDER BY  groups_phase_levels.group_id ASC, groups_phase_levels.phase_id ASC";
                cursor = db.rawQuery(rawSql, null);
                break;
            case ALL_GROUPS_PHASE_LEVELS:
              queryBuilder.setTables(Database.Groups_Phase_Levels.GROUPS_PHASE_LEVELS_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;

            case GROUPS_PHASE_LEVELS_UPDATE:
                cursor = groupPhaseLevelsUpdate(uri, projection, selection, selectionArgs, sortOrder);

              break;
            case INDIVIDUAL_GROUP_PHASE_LEVELS:
              queryBuilder.setTables(Database.Groups_Phase_Levels.GROUPS_PHASE_LEVELS_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case ALL_GROUPS_PHASE_SECTIONS:
              queryBuilder.setTables(Database.Groups_Phase_Sections.GROUPS_PHASE_SECTIONS_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case INDIVIDUAL_GROUP_PHASE_SECTIONS:
                if(selection==null) {
                    selection = "0";
                }
                String mLetterPhaseID="1";
                int mMaxLevel=0;
                int mCurrentLevel=0;
                rawSql="SELECT MAX(level_number) AS max_level " +
                        "FROM variable_phase_levels " +
                        "WHERE  group_id=2 AND phase_id=1 " +
                        "LIMIT 1";
                cursor = db.rawQuery(rawSql, null);

                if(cursor.moveToFirst()){
                    do{
                        mMaxLevel=cursor.getInt(cursor.getColumnIndex("max_level"));
                    }while(cursor.moveToNext());
                }

                rawSql="SELECT app_user_current_level " +
                        "FROM app_users_current_gpl " +
                        "WHERE group_id=2 AND phase_id=1 AND app_user_id="+selection+" " +
                        "LIMIT 1";
                cursor = db.rawQuery(rawSql, null);

                if(cursor.moveToFirst()){
                    do{
                        mCurrentLevel=cursor.getInt(cursor.getColumnIndex("app_user_current_level"));
                    }while(cursor.moveToNext());
                }

                if(mCurrentLevel>=mMaxLevel){
                    mLetterPhaseID="2";
                }



                rawSql="SELECT  groups_phase_sections.group_id, " +
                      "MAX(variable_phase_levels.level_number) as max_level, "+
                      "MIN(groups_phase_sections.phase_id) AS lw_phase_id, " +
                      "groups_phase_sections.section_id,  sections.section_name, " +
                      "sections.section_image,  sections.section_all_inclusive, " +
                      "sections.section_order, app_users_current_gpl.app_user_current_level, " +
                      "COUNT(DISTINCT groups_phase_sections_activities.activity_id) " +
                      "AS activity_count, " +
                      "COUNT(DISTINCT  app_users_activity_variable_values.variable_id) " +
                      "AS variable_count " +
                      "FROM groups_phase_sections " +
                      "INNER JOIN sections " +
                      "ON groups_phase_sections.section_id=sections.section_id " +
                      "INNER JOIN app_users_current_gpl " +
                      "ON app_users_current_gpl.group_id=groups_phase_sections.group_id " +
                      "AND app_users_current_gpl.phase_id=groups_phase_sections.phase_id " +
                      "LEFT JOIN groups_phase_sections_activities " +
                      "ON groups_phase_sections_activities.group_id" +
                      "=groups_phase_sections.group_id  " +
                      "AND groups_phase_sections_activities.phase_id" +
                      "=groups_phase_sections.phase_id " +
                      "AND groups_phase_sections_activities.section_id" +
                      "=groups_phase_sections.section_id  " +
                      "LEFT JOIN app_users_activity_variable_values " +
                      "ON app_users_activity_variable_values.activity_id" +
                      "=groups_phase_sections_activities.activity_id " +
                      "LEFT JOIN variable_phase_levels " +
                      "ON variable_phase_levels.group_id=groups_phase_sections.group_id  " +
                      "AND variable_phase_levels.phase_id=groups_phase_sections.phase_id "+
                      "WHERE  app_users_current_gpl.app_user_id="+selection +" " +
                      "AND ((groups_phase_sections.group_id=2 " +
                      "AND groups_phase_sections.phase_id="+mLetterPhaseID+") " +
                      "OR (groups_phase_sections.group_id!=2) OR (groups_phase_sections.group_id=2 " +
                      "AND groups_phase_sections.phase_id=3)) "+
                      "GROUP BY groups_phase_sections.section_id " +
                      "ORDER BY  groups_phase_sections.group_id ASC, " +
                      "sections.section_order ASC, groups_phase_sections.phase_id ASC";

                /*
                "SELECT  groups_phase_sections.group_id, MIN(groups_phase_sections.phase_id) AS lw_phase_id, "
                                +" groups_phase_sections.section_id,  sections.section_name, "
                      +" sections.section_image, sections.section_all_inclusive, sections.section_order,"
                      +" app_users_current_gpl.app_user_current_level,  " +
                      "COUNT(groups_phase_sections_activities.activity_id) AS activity_count  "
                      +" FROM groups_phase_sections  "
                      +" INNER JOIN sections  ON groups_phase_sections.section_id=sections.section_id "
                      +" INNER JOIN app_users_current_gpl " +
                      "ON app_users_current_gpl.group_id=groups_phase_sections.group_id "
                      +" AND app_users_current_gpl.phase_id=groups_phase_sections.phase_id "
                      +" LEFT JOIN groups_phase_sections_activities " +
                      "ON groups_phase_sections_activities.group_id=groups_phase_sections.group_id "
                      +" AND groups_phase_sections_activities.phase_id=groups_phase_sections.phase_id"
                      +" AND groups_phase_sections_activities.section_id=groups_phase_sections.section_id"
                      +" WHERE  app_users_current_gpl.app_user_id="+selection
                      +" GROUP BY groups_phase_sections.section_id "
                      +" ORDER BY  groups_phase_sections.group_id ASC,  " +
                      " sections.section_order ASC, groups_phase_sections.phase_id ASC";
            */
                        /*
                        groups_phase_sections.section_id ASC, groups_phase_sections.group_id ASC, "
                      +" groups_phase_sections.phase_id ASC";
                      */
              cursor = db.rawQuery(rawSql, null);


              break;
            case PROGRESS_GROUPS:
                rawSql="SELECT COUNT(*) as number_of, group_id, phase_id  " +
                        "FROM variable_phase_levels " +
                        "WHERE group_id="+projection[0]+" " +
                        "GROUP BY group_id, phase_id ORDER BY phase_id ASC";
                cursor = db.rawQuery(rawSql, null);
                break;
            case PROGRESS_POINTS:
                rawSql="SELECT sum(number_correct_in_a_row) AS user_total, type_id " +
                        "FROM app_users_activity_variable_values " +
                        "WHERE  app_user_id="+projection[0]+" AND " +
                        "type_id BETWEEN "+projection[1]+" AND "+projection[2]+" "+
                        "GROUP BY type_id ORDER BY type_id ASC";
                cursor = db.rawQuery(rawSql, null);
                break;
            case ALL_WORDS:
              queryBuilder.setTables(Database.Words.WORDS_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case INDIVIDUAL_WORDS:
              queryBuilder.setTables(Database.Words.WORDS_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case ALL_WORDS_PHASE_LEVELS:
              queryBuilder.setTables(Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case INDIVIDUAL_WORDS_PHASE_LEVELS:
              queryBuilder.setTables(Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case ALL_GROUPS_PHASE_SECTIONS_ACTIVITIES:
              queryBuilder.setTables(Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case INDIVIDUAL_GROUPS_PHASE_SECTIONS_ACTIVITIES:
              queryBuilder.setTables(Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME);
              cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
              break;
            case ALL_LOWERCASE_LETTERS:{
                rawSql="SELECT letters.* FROM letters " +
                        "INNER JOIN variable_type_relations " +
                        "ON variable_type_relations.variable_id=letters.letter_id " +
                        "WHERE variable_type_relations.variable_type_id=1";
                cursor = db.rawQuery(rawSql, null);
                break;
            }
            case WRD_LTR_ACTIVITIES:
            //  queryBuilder.setTables(Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME);
            //  cursor = queryBuilder.query(db, projection, selection,
            //		    selectionArgs, null, null, sortOrder);




              String gps_tblname=Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME;
              String act_tblname=Database.Activities.ACTIVITIES_TABLE_NAME;

              rawSql="SELECT MIN(groups_phase_sections_activities.phase_id) AS minphase_id,* "+
                      " FROM groups_phase_sections_activities"+
                      " INNER JOIN activities"+
                      " ON activities.activity_id=groups_phase_sections_activities.activity_id "+
                      " WHERE "+selection+" GROUP BY activities.activity_id ORDER BY "+sortOrder;

              cursor = db.rawQuery(rawSql, null);

              break;
            case ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE:
                rawSql="SELECT activities.activity_id,  " +
                        "MIN(app_users_activity_variable_values.number_correct_in_a_row) AS " +
                        "number_correct_in_a_row " +
                        "FROM activities " +
                        "INNER JOIN groups_phase_sections_activities " +
                        "ON groups_phase_sections_activities.activity_id=activities.activity_id  " +
                        "JOIN app_users_activity_variable_values " +
                        "ON app_users_activity_variable_values.activity_id=activities.activity_id " +
                        "WHERE  " +selection +" "+
                        "GROUP BY app_users_activity_variable_values.activity_id " +
                        "ORDER BY activities.activity_order ASC ";

                cursor = db.rawQuery(rawSql, null);
                break;
            case APP_USERS_CURRENT_GPL_INDIVIDUAL:
              queryBuilder.setTables(Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME);
              rawSql="SELECT * FROM "+Database.App_Users_Current_GPL.APP_USERS_CURRENT_GPL_TABLE_NAME
                      +" WHERE "+selection+" ORDER BY "+sortOrder;
              cursor = db.rawQuery(rawSql, null);
              //cursor = queryBuilder.query(db, projection, selection,
                      //selectionArgs, null, null, sortOrder);
              break;

            case GUESS_WORDS:
            case CURRENT_WORDS_BY_LEVEL:

              cursor =activityCurrentWordWRW(uri, projection, selection, selectionArgs, sortOrder);

                //db.rawQuery(rawSql, null);

              break;
            case ACTIVITY_CURRENT_LETTERS:


                rawSql="SELECT "+m1LettersTable+".* FROM "+m1LettersTable
                        +" INNER JOIN "+mVariablePhaseLevelsTableName
                        +" ON "+mVariablePhaseLevelsTableName+"."+Database.Variable_Phase_Levels.VARIABLE_ID
                        +"="+m1LettersTable+"."+Database.Letters.LETTER_ID+" "
                        +" WHERE "+selection+" ORDER BY "+m1LettersTable+"."+Database.Letters.LETTER_TEXT+" ASC";
                cursor = db.rawQuery(rawSql, null);

                break;
            case CURRENT_PHNC_LTRS:
                updatePhonicsProcess(projection, selection);

                rawSql="SELECT app_users_activity_variable_values.variable_id, " +
                        "app_users_activity_variable_values.number_correct, " +
                        "app_users_activity_variable_values.number_correct_in_a_row, " +
                        "app_users_activity_variable_values.number_incorrect, " +
                        "phonics.* " +
                        "FROM phonics " +
                        "INNER JOIN variable_type_relations " +
                        "ON variable_type_relations.variable_id=phonics.phonic_id " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=phonics.phonic_id   " +
                        "LEFT JOIN app_users_activity_variable_values " +
                        "ON app_users_activity_variable_values.variable_id=phonics.phonic_id " +
                        "AND app_users_activity_variable_values.activity_id="+projection[0]+" "+
                        "AND app_users_activity_variable_values.app_user_id="+projection[1]+" "+
                        "WHERE "+selection+" " +
                        "ORDER BY app_users_activity_variable_values.number_correct_in_a_row ASC, " +
                        "app_users_activity_variable_values.number_incorrect DESC, " +
                        "RANDOM()";

  /*
                        "SELECT "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+","
                        +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT+","
                        +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW+","
                        +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT+","
                        +mPhonicsTableName+".* FROM "+mPhonicsTableName
                        +" INNER JOIN variable_type_relations ON variable_type_relations.variable_id=phonics.phonic_id"
                        +" INNER JOIN "+mVariablePhaseLevelsTableName
                        +" ON "+mVariablePhaseLevelsTableName+"."+Database.Variable_Phase_Levels.VARIABLE_ID
                        +"="+mPhonicsTableName+"."+Database.Phonics.PHONIC_ID+" "
                        +" LEFT JOIN "+Database.App_Users_Activity_Variable_Values.AUAVV_TABLE
                        +" ON "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID
                        +"="+mPhonicsTableName+"."+Database.Phonics.PHONIC_ID
                        +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+projection[0]
                        +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+projection[1]
                        +" WHERE "+selection+" " +
                        " ORDER BY variable_phase_levels.level_number ASC, phonics.phonic_text ASC";
*/
                cursor = db.rawQuery(rawSql, null);

                break;
            case CURRENT_PHNC_LTRS_WRDS:


                rawSql="SELECT "+mPhonicWordsTableName+".* FROM "+mPhonicWordsTableName+" WHERE "+selection;
                        /*
                        +" INNER JOIN "+mVariablePhaseLevelsTableName
                        +" ON "+mVariablePhaseLevelsTableName+"."+Database.Variable_Phase_Levels.WORD_ID
                        +"="+mPhonicsTableName+"."+Database.Words.WORD_ID+" "
                        +" WHERE "+selection+" ORDER BY "+mPhonicWordsTableName+"."+mPhonicWordsTableName+" ASC";
                        */
                cursor = db.rawQuery(rawSql, null);

                break;

            case CURRENT_SYLLABLE_WORDS:
                rawSql="SELECT syllable_words.* FROM syllable_words WHERE "+selection;

                cursor = db.rawQuery(rawSql, null);
                break;
            case CURRENT_SYLLABLES:
                //updateSyllableProcess(projection, selection);


                rawSql="SELECT app_users_activity_variable_values.auavv_id, " +
                        "app_users_activity_variable_values.activity_id," +
                        "app_users_activity_variable_values.number_correct," +
                        "app_users_activity_variable_values.number_correct_in_a_row," +
                        "app_users_activity_variable_values.number_incorrect," +
                        "syllables.* " +
                        "FROM syllables " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=syllables.phonic_id  " +
                        "AND variable_phase_levels.group_id=2 " +
                        "AND  variable_phase_levels.phase_id=3 " +
                        "LEFT JOIN app_users_activity_variable_values " +
                        "ON app_users_activity_variable_values.variable_id=syllables.syllables_id " +
                        "AND app_users_activity_variable_values.activity_id="+projection[0]+" "+
                        "AND app_users_activity_variable_values.app_user_id="+projection[1]+" "+
                        "WHERE  variable_phase_levels.level_number<=" +
                        "(SELECT app_users_current_gpl.app_user_current_level " +
                        "FROM app_users_current_gpl " +
                        "WHERE app_users_current_gpl.group_id=2 " +
                        "AND app_users_current_gpl.phase_id=3) " +
                        "ORDER BY app_users_activity_variable_values.number_correct_in_a_row ASC, " +
                        "syllables.syllables_text ASC";

                cursor = db.rawQuery(rawSql, null);
                break;
            case ACTIVITY_CURRENT_WORDS_WRW:
                cursor = activityCurrentWordWRW(uri, projection, selection,
                        selectionArgs, sortOrder);


              break;
            case ACTIVITY_CURRENT_LETTERS_WRW:
                cursor = activityCurrentLettersWRW(uri, projection, selection,
                        selectionArgs, sortOrder);


                break;
            case CURRENT_WORD_SYLLABLE_VALUES:
                cursor=currentWordSyllableValues(uri, projection, selection,
                        selectionArgs, sortOrder);

                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }


        return cursor;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////



    private Cursor currentWordSyllableValues(Uri uri, String[] projection, String selection,
                                                 String[] selectionArgs, String sortOrder){
        String rawSql;
        Cursor cursor;
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        updateSyllableWordsProcess(projection, sortOrder);



        String mAUAVVTable=Database.App_Users_Activity_Variable_Values.AUAVV_TABLE;
        String mWordsTableName=Database.Words.WORDS_TABLE_NAME;
        String mVariablePhaseLevelsTableName=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE;


        rawSql="SELECT "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+","
                +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT+","
                +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW+","
                +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT+","
                +mWordsTableName+".* FROM "+mWordsTableName
                +" INNER JOIN "+mVariablePhaseLevelsTableName
                +" ON "+mVariablePhaseLevelsTableName+"."+Database.Variable_Phase_Levels.VARIABLE_ID
                +"="+mWordsTableName+"."+Database.Words.WORD_ID+" "
                +" LEFT JOIN "+Database.App_Users_Activity_Variable_Values.AUAVV_TABLE
                +" ON "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="
                +mWordsTableName+"."+Database.Words.WORD_ID
                +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+projection[0]
                +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+projection[1]
                +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.TYPE_ID+"="+MotoliConstants.TYPE_WORDS_FOR_SYLLALBES
                +" WHERE "+selection+" ";

        //  if(blankCount==cursor.getCount()){
        //	  rawSql+=" ORDER BY RANDOM() ";
        //  }else{
        //  rawSql+="  ORDER BY "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT+" DESC,  RANDOM()";
        //  }
        rawSql+=" ORDER BY  app_users_activity_variable_values.number_correct_in_a_row ASC,"+
                " app_users_activity_variable_values.number_incorrect DESC, "+
                " RANDOM() ";

        cursor = db.rawQuery(rawSql, null);

        ArrayList<ArrayList<String>> currentWords=new ArrayList<ArrayList<String>>();
        int currentWordNumber=0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            currentWords.add(new ArrayList<String>());
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("word_id")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("word_text")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("audio_url")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("number_correct")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("number_incorrect")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("image_url")));

            currentWordNumber++;
            if (currentWordNumber == 12) {
                break;
            }else if(cursor.isLast()){
                cursor.moveToFirst();
            }else if(cursor.getCount()>=8 && currentWordNumber==9) {
                cursor.moveToFirst();
            }else {
                cursor.moveToNext();
            }
        }
        Log.d(MotoliConstants.LOGCAT, String.valueOf(currentWords.size()));
        return cursor;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////


    private Cursor activityCurrentWordWRW(Uri uri, String[] projection, String selection,
                                                 String[] selectionArgs, String sortOrder){
        String rawSql;
        Cursor cursor;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if(sortOrder!=null)
            updateWordsProcess(projection, sortOrder);
        else
            updateWordsProcess(projection, selection);
        String mAUAVVTable=Database.App_Users_Activity_Variable_Values.AUAVV_TABLE;


        String mWordsTable=Database.Words.WORDS_TABLE_NAME;
        String mVariablePhaseLevelsTableName=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE;


        rawSql="SELECT "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+","
                +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT+","
                +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW+","
                +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT+","
                +mWordsTable+".* ";

        rawSql+=" FROM "+mWordsTable;

        rawSql+=" INNER JOIN "+mVariablePhaseLevelsTableName
                +" ON "+mVariablePhaseLevelsTableName+"."+Database.Variable_Phase_Levels.VARIABLE_ID
                +"="+mWordsTable+"."+Database.Words.WORD_ID+" ";

        rawSql+=" LEFT JOIN "+Database.App_Users_Activity_Variable_Values.AUAVV_TABLE
                +" ON "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+
                "="+mWordsTable+"."+Database.Words.WORD_ID
                +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+
                "="+projection[0]
                +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.APP_USER_ID+
                "="+projection[1];

        rawSql+=" WHERE "+selection+" ";

        rawSql+=" ORDER BY  app_users_activity_variable_values.number_correct_in_a_row ASC,"+
                " app_users_activity_variable_values.number_incorrect DESC, "+
                " RANDOM() ";

        cursor = db.rawQuery(rawSql, null);

        ArrayList<ArrayList<String>> currentWords=new ArrayList<ArrayList<String>>();
        int currentWordNumber=0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            currentWords.add(new ArrayList<String>());
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("word_id")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("word_text")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("audio_url")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("number_correct")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("number_incorrect")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("image_url")));

            currentWordNumber++;
            if (currentWordNumber == 12) {
                break;
            }else if(cursor.isLast()){
                cursor.moveToFirst();
            }else if(cursor.getCount()>=8 && currentWordNumber==9) {
                cursor.moveToFirst();
            }else {
                cursor.moveToNext();
            }
        }
        Log.d(MotoliConstants.LOGCAT, String.valueOf(currentWords.size()));
        return cursor;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////


    private Cursor activityCurrentLettersWRW(Uri uri, String[] projection, String selection,
                                                 String[] selectionArgs, String sortOrder){
        String rawSql;
        Cursor cursor;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if(sortOrder!=null) {
            updateWordsProcess(projection, sortOrder);
        }else {
            updateWordsProcess(projection, selection);
        }

        rawSql="SELECT app_users_activity_variable_values.variable_id," +
                "app_users_activity_variable_values.number_correct, " +
                "app_users_activity_variable_values.number_correct_in_a_row," +
                "app_users_activity_variable_values.number_incorrect," +
                "letters.* " +
                "FROM letters " +
                "INNER JOIN variable_phase_levels " +
                "ON variable_phase_levels.variable_id=letters.letter_id  " +
                "LEFT JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=letters.letter_id " +
                "AND app_users_activity_variable_values.activity_id="+projection[0]+" " +
                "AND app_users_activity_variable_values.app_user_id="+projection[1]+" "+
                "WHERE "+selection+" "+
                "ORDER BY  app_users_activity_variable_values.number_correct_in_a_row ASC, " +
                "app_users_activity_variable_values.number_incorrect DESC,  " +
                "RANDOM() ";


                /*
                "SELECT "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+","
                +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT+","
                +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW+","
                +mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT+","
                +mWordsTableName+".* FROM "+mWordsTableName
                +" INNER JOIN "+mVariablePhaseLevelsTableName
                +" ON "+mVariablePhaseLevelsTableName+"."+Database.Variable_Phase_Levels.VARIABLE_ID
                +"="+mWordsTableName+"."+Database.Words.WORD_ID+" "
                +" LEFT JOIN "+Database.App_Users_Activity_Variable_Values.AUAVV_TABLE
                +" ON "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="
                +mWordsTableName+"."+Database.Words.WORD_ID
                +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+projection[0]
                +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+projection[1]
                +" WHERE "+selection+" ";

        //  if(blankCount==cursor.getCount()){
        //	  rawSql+=" ORDER BY RANDOM() ";
        //  }else{
        //  rawSql+="  ORDER BY "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT+" DESC,  RANDOM()";
        //  }
        rawSql+=" ORDER BY  app_users_activity_variable_values.number_correct_in_a_row ASC,"+
                " app_users_activity_variable_values.number_incorrect DESC, "+
                " RANDOM() ";
        */
        cursor = db.rawQuery(rawSql, null);

        ArrayList<ArrayList<String>> currentWords=new ArrayList<ArrayList<String>>();
        int currentWordNumber=0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            currentWords.add(new ArrayList<String>());
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("letter_id")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("letter_text")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("audio_url")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("number_correct")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("number_incorrect")));
            currentWords.get(currentWordNumber).add(cursor.getString(cursor.getColumnIndex("image_url")));

            currentWordNumber++;
            if (currentWordNumber == 12) {
                break;
            }else if(cursor.isLast()){
                cursor.moveToFirst();
            }else if(cursor.getCount()>=8 && currentWordNumber==9) {
                cursor.moveToFirst();
            }else {
                cursor.moveToNext();
            }
        }
        Log.d(MotoliConstants.LOGCAT, String.valueOf(currentWords.size()));
        return cursor;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////

    private Cursor bookPagesReadAloud(Uri uri, String[] projection, String selection,
                                  String[] selectionArgs, String sortOrder) {
        String rawSql;
        Cursor cursor;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // String mAppUserId = selection;
        ArrayList<ArrayList<String>> mRegularGPL = new ArrayList<ArrayList<String>>();
        // Motoli_Application appData = ((Motoli_Application) getContext().getApplicationContext());

        rawSql="SELECT * " +
                "FROM book_pages " +
                "WHERE  " +selection+" "+
                "ORDER BY book_page_order ASC";
        cursor = db.rawQuery(rawSql, null);
        return cursor;

    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    private Cursor booksReadAloud(Uri uri, String[] projection, String selection,
                                          String[] selectionArgs, String sortOrder) {

        String rawSql;
        Cursor cursor;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
       // String mAppUserId = selection;
        ArrayList<ArrayList<String>> mRegularGPL = new ArrayList<ArrayList<String>>();
       // Motoli_Application appData = ((Motoli_Application) getContext().getApplicationContext());

        rawSql="SELECT books.*, book_pages.book_page_image " +
                "FROM books " +
                "INNER JOIN book_pages " +
                "ON book_pages.book_id=books.book_id " +
                "WHERE books.book_type=0 AND book_pages.book_page_order=0 " +
                "ORDER BY books.book_order ASC";
        cursor = db.rawQuery(rawSql, null);
        return cursor;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private Cursor groupPhaseLevelsUpdate(Uri uri, String[] projection, String mAppUserId,
                                          String[] selectionArgs, String sortOrder){

        String rawSql;
        Cursor cursor;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<ArrayList<String>> mRegularGPL=new ArrayList<ArrayList<String>>();


        rawSql="SELECT  groups_phase_levels.gpl_id, groups_phase_levels.group_id, groups_phase_levels.phase_id, " +
                " MAX( groups_phase_levels.level_number) AS maxLevel" +
                " FROM groups_phase_levels "+
                "GROUP BY  groups_phase_levels.group_id, groups_phase_levels.phase_id "+
                " ORDER BY  groups_phase_levels.group_id ASC, groups_phase_levels.phase_id ASC";
        cursor = db.rawQuery(rawSql, null);

        if(cursor.moveToFirst()){
            do{
                mRegularGPL.add(new ArrayList<String>());
                mRegularGPL.get(mRegularGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("gpl_id"))); //0
                mRegularGPL.get(mRegularGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("group_id"))); //1
                mRegularGPL.get(mRegularGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("phase_id"))); //2
                mRegularGPL.get(mRegularGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("maxLevel"))); //3
            }while(cursor.moveToNext());
        }

        ArrayList<ArrayList<String>> mUserGPL=new ArrayList<ArrayList<String>>();

        rawSql="SELECT * FROM app_users_current_gpl  " +
                "WHERE app_users_current_gpl.app_user_id="+mAppUserId+" "+
                "ORDER BY app_users_current_gpl.group_id ASC, "+
                "app_users_current_gpl.phase_id ASC";
        cursor = db.rawQuery(rawSql, null);

        if(cursor.moveToFirst()){
            do{
                mUserGPL.add(new ArrayList<String>());
                mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("_id"))); //0
                mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("app_user_id"))); //1
                mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("group_id"))); //2
                mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("phase_id"))); //3
                mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("app_user_current_level"))); //4
                mUserGPL.get(mUserGPL.size()-1).add("0"); //5

                switch(Integer.parseInt(mUserGPL.get(mUserGPL.size()-1).get(2))){
                    default:
                        mUserGPL.get(mUserGPL.size()-1).set(5, "0");
                        break;
                    case 2:{
                        switch(Integer.parseInt(mUserGPL.get(mUserGPL.size()-1).get(3))){
                            default:
                                mUserGPL.get(mUserGPL.size()-1).set(5, "0");
                                break;
                            case 1:{
                                mUserGPL.get(mUserGPL.size()-1).set(5, "1");
                                break;
                            }
                            case 2:{
                                mUserGPL.get(mUserGPL.size()-1).set(5, "2");
                                break;
                            }
                            case 3:{
                                mUserGPL.get(mUserGPL.size()-1).set(5, "3");
                                break;
                            }
                        }
                        break;
                    }
                    case 3:{
                        mUserGPL.get(mUserGPL.size()-1).set(5, "4");
                        mUserGPL.add(new ArrayList<String>());
                        mUserGPL.get(mUserGPL.size() - 1).add(cursor.getString(cursor.getColumnIndex("_id"))); //0
                        mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("app_user_id"))); //1
                        mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("group_id"))); //2
                        mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("phase_id"))); //3
                        mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("app_user_current_level"))); //4
                        mUserGPL.get(mUserGPL.size()-1).add("6");
                        break;
                    }
                    case 4:{
                        mUserGPL.get(mUserGPL.size()-1).set(5, "5");
                        break;
                    }
                    case 5:{
                        mUserGPL.get(mUserGPL.size()-1).set(5, "7");
                        break;
                    }
                }
            }while(cursor.moveToNext());
        }


       //` ArrayList<String> mCurrentGPL=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());


        for(int i=0;i<mUserGPL.size();i++){
            rawSql="SELECT MIN(auavv.number_correct_in_a_row) AS minNumberCorrect, "+
                    " variable_phase_levels.* "+
                    " FROM app_users_activity_variable_values auavv"+
                    " LEFT JOIN variable_phase_levels "+
                    " ON variable_phase_levels.variable_id = auavv.variable_id "+
                    " WHERE variable_phase_levels.group_id="+mUserGPL.get(i).get(2)+
                    " AND variable_phase_levels.phase_id="+mUserGPL.get(i).get(3)+
                    " AND variable_phase_levels.level_number="+mUserGPL.get(i).get(4)+
                    " AND auavv.type_id="+mUserGPL.get(i).get(5);
            cursor = db.rawQuery(rawSql, null);
            cursor.moveToFirst();
            int mMinNumberCorrect; //THINKING ABOUT THIS


            if(cursor.isNull(cursor.getColumnIndex("_id"))){
                /**
                 * puts variables in if nothing is created yet
                 */
                switch(Integer.parseInt(mUserGPL.get(i).get(2))){
                    default:
                        break;
                    case 2: {   //group for letters/phonics
                        switch(Integer.parseInt(mUserGPL.get(i).get(3))){
                            default:{
                                break;
                            }
                            case 1:{

                                if(Integer.parseInt(mUserGPL.get(0).get(4))<=1) {

                                    rawSql = "UPDATE app_users_current_gpl " +
                                            "SET app_user_current_level=1 " +
                                            "WHERE group_id=2 " +
                                            "AND phase_id=1 " +
                                            "AND app_users_current_gpl.app_user_id=" + mAppUserId;
                                    db.execSQL(rawSql);

                                    mUserGPL.get(i).set(4, "1");

                                    String mLetterSelection = "variable_phase_levels.group_id = " +
                                            mUserGPL.get(i).get(2) + " " +
                                            "AND variable_phase_levels.phase_id = " +
                                            mUserGPL.get(i).get(3) + " " +
                                            "AND variable_phase_levels.level_number <= " +
                                            mUserGPL.get(i).get(4)+" "+
                                            "AND variable_type_relations.variable_type_id=1";

                                    String[] mLetterProjection = new String[]{
                                            "0", //0
                                            mAppUserId, //1
                                            mUserGPL.get(i).get(2), //group_id 2
                                            mUserGPL.get(i).get(3), //phase_id 3
                                            "3", //section_id 4
                                            "1" //level_number 5
                                    };


                                    updateLetterProcess(mLetterProjection, mLetterSelection);
                                }
                                break;
                            }
                            case 2:
                            case 3:{
                                int mMaxLevel=0;
                                //This only allows the level to raised up when the phase 1 is finished
                                for (ArrayList<String> subGPL : mRegularGPL) {
                                    if(subGPL.get(1).equals("2") && subGPL.get(2).equals("1")){
                                        mMaxLevel=Integer.parseInt(subGPL.get(3));
                                        break;
                                    }
                                }
                                if(Integer.parseInt(mUserGPL.get(0).get(4))>=mMaxLevel &&
                                        Integer.parseInt(mUserGPL.get(i).get(4))<1){
                                    rawSql="UPDATE app_users_current_gpl " +
                                            "SET app_user_current_level=1 " +
                                            "WHERE group_id=2 " +
                                            "AND phase_id="+mUserGPL.get(i).get(3)+" "+
                                            "AND app_users_current_gpl.app_user_id="+mAppUserId;
                                    db.execSQL(rawSql);

                                    mUserGPL.get(i).set(4,"1");

                                    String mLetterSelection = "variable_phase_levels.group_id = "+
                                            mUserGPL.get(i).get(2)+" " +
                                            "AND variable_phase_levels.phase_id = "+
                                            mUserGPL.get(i).get(3)+" " +
                                            "AND variable_phase_levels.level_number <= "+
                                            mUserGPL.get(i).get(4)+" "+
                                            "AND variable_type_relations.variable_type_id=2";

                                    String mPhonicSelection = "variable_phase_levels.group_id = "+
                                            mUserGPL.get(i).get(2)+" " +
                                            "AND variable_phase_levels.phase_id = "+
                                            mUserGPL.get(i).get(3)+" " +
                                            "AND variable_phase_levels.level_number <= "+
                                            mUserGPL.get(i).get(4)+" "+
                                            "AND variable_type_relations.variable_type_id=3";

                                    String[] mLetterProjection = new String[]{
                                            "0",
                                            mAppUserId,
                                            mUserGPL.get(i).get(2), //group_id
                                            mUserGPL.get(i).get(3), //phase_id
                                            "3", //section_id 4
                                            "1" //level_number 5
                                    };



                                    if (mUserGPL.get(i).get(3).equals("2")) {
                                        updateLetterProcess(mLetterProjection, mLetterSelection);
                                    } else {
                                        mLetterProjection[4] = "5";
                                        updatePhonicsProcess(mLetterProjection, mPhonicSelection);
                                        int mFirstWordCount=0;
                                        int mSecondWordCount=0;

                                        rawSql= "SELECT COUNT(words._id) AS word_count " +
                                                "FROM words " +
                                                "INNER JOIN variable_phase_levels " +
                                                "ON variable_phase_levels.variable_id=words.word_id " +
                                                "WHERE  variable_phase_levels.group_id=4 " +
                                                "AND variable_phase_levels.phase_id=1 " +
                                                "AND variable_phase_levels.level_number="+
                                                mUserGPL.get(i).get(4);
                                        Cursor mCountCursor = db.rawQuery(rawSql, null);
                                        if(mCountCursor.moveToFirst()){
                                            mSecondWordCount=mCountCursor.getInt(mCountCursor.getColumnIndex("word_count"));
                                        }




                                        if( mSecondWordCount>=4){
                                            rawSql="UPDATE app_users_current_gpl " +
                                                    "SET app_user_current_level=1 " +
                                                    "WHERE group_id=4 " +
                                                    "AND phase_id=1 "+
                                                    "AND app_users_current_gpl.app_user_id="+mAppUserId;
                                            db.execSQL(rawSql);


                                            mUserGPL.get(4).set(4,
                                                    String.valueOf(Integer.parseInt(mUserGPL.get(4).get(4))+1));


                                            String mWordSelection = "variable_phase_levels.group_id = 4 " +
                                                    "AND variable_phase_levels.phase_id = 1 " +
                                                    "AND variable_phase_levels.level_number <= "+
                                                    mUserGPL.get(i).get(4) + " " +
                                                    "AND variable_phase_levels.level_number != 0 "+
                                                    "AND variable_type_relations.variable_type_id=5";

                                            String[] mWordProjection = new String[]{
                                                    "0", //0
                                                    mAppUserId, //1
                                                    "4", //group_id 2
                                                    "1", //phase_id 3
                                                    "11", //section_id 4
                                                    mUserGPL.get(i).get(4) //level_number 5
                                            };

                                            updateWordsProcess(mWordProjection, mWordSelection);
                                        }else if(mSecondWordCount<4){
                                            rawSql="UPDATE app_users_current_gpl " +
                                                    "SET app_user_current_level=1 " +
                                                    "WHERE group_id=4 " +
                                                    "AND phase_id=1 "+
                                                    "AND app_users_current_gpl.app_user_id="+mAppUserId;
                                            db.execSQL(rawSql);


                                            mUserGPL.get(4).set(4,
                                                    String.valueOf(Integer.parseInt(mUserGPL.get(4).get(4))+1));


                                            String mWordSelection = "variable_phase_levels.group_id = 4 " +
                                                    "AND variable_phase_levels.phase_id = 1 " +
                                                    "AND variable_phase_levels.level_number <= "+
                                                    mUserGPL.get(i).get(4) + " " +
                                                    "AND variable_phase_levels.level_number != 0 "+
                                                    "AND variable_type_relations.variable_type_id=5";

                                            String[] mWordProjection = new String[]{
                                                    "1", //0
                                                    mAppUserId, //1
                                                    "4", //group_id 2
                                                    "1", //phase_id 3
                                                    "11", //section_id 4
                                                    mUserGPL.get(i).get(4) //level_number 5
                                            };

                                            updateWordsProcess(mWordProjection, mWordSelection);
                                        }
                                    }

                                }
                                break;
                            }

                        }//end switch(Integer.parseInt(mUserGPL.get(i).get(3))){

                        break;
                    }
                    case 3:{    //group for syllables
                        rawSql="UPDATE app_users_current_gpl " +
                                "SET app_user_current_level=1 " +
                                "WHERE group_id=3 " +
                                "AND phase_id=1 "+
                                "AND app_users_current_gpl.app_user_id="+mAppUserId;
                        db.execSQL(rawSql);

                        mUserGPL.get(i).set(4,"1");

                        String mSyllableSelection = "variable_phase_levels.group_id = "+
                                mUserGPL.get(i).get(2)+" " +
                                "AND variable_phase_levels.phase_id = "+
                                mUserGPL.get(i).get(3)+" " +
                                "AND variable_phase_levels.level_number <= "+
                                mUserGPL.get(i).get(4)+" "+
                                "AND variable_type_relations.variable_type_id=4";

                        String mSyllableWordSelection = "variable_phase_levels.group_id = 3 " +
                                "AND variable_phase_levels.phase_id = 1 " +
                                "AND variable_phase_levels.level_number <= "+
                                mUserGPL.get(i).get(4)+" "+
                                "AND variable_type_relations.variable_type_id=6";

                        String[] mSyllableProjection = new String[]{
                                "0", //0
                                mAppUserId, //1
                                mUserGPL.get(i).get(2), //group_id 2
                                mUserGPL.get(i).get(3), //phase_id 3
                                "8", //section_id 4
                                "1" //level_number 5
                        };


                        updateSyllableProcess(mSyllableProjection, mSyllableSelection);
                        updateSyllableWordsProcess(mSyllableProjection, mSyllableWordSelection);

                        break;
                    }
                    case 4:{
                        /**
                         * Group for Words based upon levels in phonics
                         * Ignore until phonics level has 1 and at least 4 words are avaialble
                         */

                        rawSql="SELECT app_user_current_level " +
                                "FROM app_users_current_gpl " +
                                "WHERE group_id=4 " +
                                "AND phase_id=1 "+
                                "AND app_user_id="+mAppUserId;
                        Cursor wordCursor = db.rawQuery(rawSql, null);
                        if(wordCursor.moveToFirst() &&
                                wordCursor.getString(wordCursor.getColumnIndex("app_user_current_level")).equals("0")) {

                            rawSql = "UPDATE app_users_current_gpl " +
                                    "SET app_user_current_level=1 " +
                                    "WHERE group_id=4 " +
                                    "AND phase_id=1 " +
                                    "AND app_user_id=" + mAppUserId;
                            db.execSQL(rawSql);

                            mUserGPL.get(i).set(4, "1");

                            String mWordSelection = "variable_phase_levels.group_id = " +
                                    mUserGPL.get(i).get(2) + " " +
                                    "AND variable_phase_levels.phase_id = " +
                                    mUserGPL.get(i).get(3) + " " +
                                    "AND variable_phase_levels.level_number <= " +
                                    mUserGPL.get(i).get(4) + " " +
                                    "AND variable_phase_levels.level_number != 0 " +
                                    "AND variable_type_relations.variable_type_id=5";

                            String[] mWordProjection = new String[]{
                                    "0", //0
                                    mAppUserId, //1
                                    mUserGPL.get(i).get(2), //group_id 2
                                    mUserGPL.get(i).get(3), //phase_id 3
                                    "11", //section_id 4
                                    "1" //level_number 5
                            };

                            updateWordsProcess(mWordProjection, mWordSelection);
                        }
                        wordCursor.close();
                        break;
                    }
                    case 5:{
                        break;
                    }
                }//end switch(Integer.parseInt(mUserGPL.get(i).get(2))){
            }else{
                /**
                 * Take care of updating variables is needed
                 */

                mMinNumberCorrect = cursor.getInt(cursor.getColumnIndex("minNumberCorrect"));
                if (mMinNumberCorrect >= MotoliConstants.INA_ROW_CORRECT) {
                    switch (Integer.parseInt(mUserGPL.get(i).get(2))) {
                        default:
                            break;
                        case 2: {   //group for letters/phonics
                            switch (Integer.parseInt(mUserGPL.get(i).get(3))) {
                                default: {
                                    break;
                                }
                                case 1: {
                                    rawSql="UPDATE app_users_current_gpl " +
                                            "SET app_user_current_level=app_user_current_level+1 " +
                                            "WHERE group_id=2 " +
                                            "AND phase_id=1 "+
                                            "AND app_users_current_gpl.app_user_id="+mAppUserId;
                                    db.execSQL(rawSql);

                                    mUserGPL.get(i).set(4,
                                            String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4))+1));

                                    String mLetterSelection = "variable_phase_levels.group_id = "+
                                            mUserGPL.get(i).get(2)+" " +
                                            "AND variable_phase_levels.phase_id = "+
                                            mUserGPL.get(i).get(3)+" " +
                                            "AND variable_phase_levels.level_number <= "+
                                            mUserGPL.get(i).get(4)+" "+
                                            "AND variable_type_relations.variable_type_id=1";

                                    String[] mLetterProjection = new String[]{
                                            "0", //0
                                            mAppUserId, //1
                                            mUserGPL.get(i).get(2), //group_id 2
                                            mUserGPL.get(i).get(3), //phase_id 3
                                            "3", //section_id 4
                                            mUserGPL.get(i).get(4) //level_number 5
                                    };

                                    updateLetterProcess(mLetterProjection, mLetterSelection);

                                    break;
                                }
                                case 2: {
                                    rawSql="UPDATE app_users_current_gpl " +
                                            "SET app_user_current_level=app_user_current_level+1 " +
                                            "WHERE group_id=2 " +
                                            "AND phase_id=2 "+
                                            "AND app_users_current_gpl.app_user_id="+mAppUserId;
                                    db.execSQL(rawSql);

                                    mUserGPL.get(i).set(4,
                                            String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4))+1));

                                    String mLetterSelection = "variable_phase_levels.group_id = "+
                                            mUserGPL.get(i).get(2)+" " +
                                            "AND variable_phase_levels.phase_id = "+
                                            mUserGPL.get(i).get(3)+" " +
                                            "AND variable_phase_levels.level_number <= "+
                                            mUserGPL.get(i).get(4)+" "+
                                            "AND variable_type_relations.variable_type_id=2";

                                    String[] mLetterProjection = new String[]{
                                            "0", //0
                                            mAppUserId, //1
                                            mUserGPL.get(i).get(2), //group_id 2
                                            mUserGPL.get(i).get(3), //phase_id 3
                                            "3", //section_id 4
                                            mUserGPL.get(i).get(4) //level_number 5
                                    };

                                    updateLetterProcess(mLetterProjection, mLetterSelection);
                                    break;
                                }
                                case 3: {
                                    rawSql="UPDATE app_users_current_gpl " +
                                            "SET app_user_current_level=app_user_current_level+1 " +
                                            "WHERE group_id=2 " +
                                            "AND phase_id=3 "+
                                            "AND app_users_current_gpl.app_user_id="+mAppUserId;
                                    db.execSQL(rawSql);

                                    mUserGPL.get(i).set(4,
                                            String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)) + 1));

                                    String mLetterSelection = "variable_phase_levels.group_id = "+
                                            mUserGPL.get(i).get(2)+" " +
                                            "AND variable_phase_levels.phase_id = "+
                                            mUserGPL.get(i).get(3)+" " +
                                            "AND variable_phase_levels.level_number <= "+
                                            mUserGPL.get(i).get(4)+" "+
                                            "AND variable_type_relations.variable_type_id=3";

                                    String[] mLetterProjection = new String[]{
                                            "0", //0
                                            mAppUserId, //1
                                            mUserGPL.get(i).get(2), //group_id 2
                                            mUserGPL.get(i).get(3), //phase_id 3
                                            "5", //section_id 4
                                            mUserGPL.get(i).get(4) //level_number 5
                                    };

                                    updatePhonicsProcess(mLetterProjection, mLetterSelection);

                                    int mFirstWordCount=0;
                                    int mSecondWordCount=0;

                                    switch(Integer.parseInt(mUserGPL.get(i).get(4))){
                                        case 1:{
                                            rawSql= "SELECT COUNT(words._id) AS word_count " +
                                                    "FROM words " +
                                                    "INNER JOIN variable_phase_levels " +
                                                    "ON variable_phase_levels.variable_id=words.word_id " +
                                                    "WHERE  variable_phase_levels.group_id=4 " +
                                                    "AND variable_phase_levels.phase_id=1 " +
                                                    "AND variable_phase_levels.level_number="+
                                                    mUserGPL.get(i).get(4);
                                            Cursor mCountCursor = db.rawQuery(rawSql, null);
                                            if(mCountCursor.moveToFirst()){
                                                mSecondWordCount=mCountCursor.getInt(mCountCursor.getColumnIndex("word_count"));
                                            }

                                            break;
                                        }
                                        default:
                                        case 2:{
                                            rawSql="SELECT COUNT(words._id) AS word_count " +
                                                    "FROM words " +
                                                    "INNER JOIN variable_phase_levels " +
                                                    "ON variable_phase_levels.variable_id=words.word_id " +
                                                    "WHERE  variable_phase_levels.group_id=4 " +
                                                    "AND variable_phase_levels.phase_id=1 " +
                                                    "AND variable_phase_levels.level_number=" +
                                                    String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4))-1)+
                                                    " UNION " +
                                                    "SELECT COUNT(words._id) AS word_count " +
                                                    "FROM words " +
                                                    "INNER JOIN variable_phase_levels " +
                                                    "ON variable_phase_levels.variable_id=words.word_id " +
                                                    "WHERE  variable_phase_levels.group_id=4 " +
                                                    "AND variable_phase_levels.phase_id=1 " +
                                                    "AND variable_phase_levels.level_number="+
                                                    mUserGPL.get(i).get(4);
                                            Cursor mCountCursor = db.rawQuery(rawSql, null);
                                            if(mCountCursor.moveToFirst()){
                                                mFirstWordCount = mCountCursor.getInt(mCountCursor.getColumnIndex("word_count"));
                                            }
                                            break;
                                        }

                                    }

                                    rawSql="SELECT app_user_current_level " +
                                            "FROM app_users_current_gpl " +
                                            "WHERE group_id=2 " +
                                            "AND phase_id=3 " +
                                            "AND app_user_id="+mAppUserId;
                                    Cursor mLevelCursor = db.rawQuery(rawSql, null);
                                    String mOtherLevel="0";
                                    if(mLevelCursor.moveToFirst()){
                                        mOtherLevel=mLevelCursor.getString(mLevelCursor.getColumnIndex("app_user_current_level"));
                                    }

                                    if(mFirstWordCount<4 && mSecondWordCount>=4){
                                        rawSql="UPDATE app_users_current_gpl " +
                                                "SET app_user_current_level=" +mOtherLevel+ " "+
                                                "WHERE group_id=4 " +
                                                "AND phase_id=1 "+
                                                "AND app_users_current_gpl.app_user_id="+mAppUserId;
                                        db.execSQL(rawSql);


                                        mUserGPL.get(4).set(4,
                                                String.valueOf(Integer.parseInt(mUserGPL.get(4).get(4))+1));


                                        String mWordSelection = "variable_phase_levels.group_id = 4 " +
                                                "AND variable_phase_levels.phase_id = 1 " +
                                                "AND variable_phase_levels.level_number <= "+
                                                mOtherLevel+ " " +
                                                "AND variable_phase_levels.level_number != 0 "+
                                                "AND variable_type_relations.variable_type_id=5";

                                        String[] mWordProjection = new String[]{
                                                "0", //0
                                                mAppUserId, //1
                                                "4", //group_id 2
                                                "1", //phase_id 3
                                                "11", //section_id 4
                                                mOtherLevel//level_number 5
                                        };

                                        updateWordsProcess(mWordProjection, mWordSelection);
                                    }else if(mSecondWordCount<4 && mSecondWordCount!=mFirstWordCount){
                                        rawSql="UPDATE app_users_current_gpl " +
                                                "SET app_user_current_level="+mOtherLevel+ " "+
                                                "WHERE group_id=4 " +
                                                "AND phase_id=1 "+
                                                "AND app_users_current_gpl.app_user_id="+mAppUserId;
                                        db.execSQL(rawSql);


                                        mUserGPL.get(4).set(4,
                                                String.valueOf(Integer.parseInt(mUserGPL.get(4).get(4))+1));


                                        String mWordSelection = "variable_phase_levels.group_id = 4 " +
                                                "AND variable_phase_levels.phase_id = 1 " +
                                                "AND variable_phase_levels.level_number <= "+
                                                mOtherLevel+ " " +
                                                "AND variable_phase_levels.level_number != 0 "+
                                                "AND variable_type_relations.variable_type_id=5";

                                        String[] mWordProjection = new String[]{
                                                "1", //0
                                                mAppUserId, //1
                                                "4", //group_id 2
                                                "1", //phase_id 3
                                                "11", //section_id 4
                                                mOtherLevel//level_number 5
                                        };

                                        updateWordsProcess(mWordProjection, mWordSelection);
                                    }

                                    break;
                                }
                            }//end switch(Integer.parseInt(mUserGPL.get(i).get(3))){

                            break;
                        }
                        case 3: {    //group for syllables
                            rawSql="UPDATE app_users_current_gpl " +
                                    "SET app_user_current_level=app_user_current_level+1 " +
                                    "WHERE group_id=3 " +
                                    "AND phase_id=1 "+
                                    "AND app_users_current_gpl.app_user_id="+mAppUserId;
                            db.execSQL(rawSql);

                            mUserGPL.get(i).set(4,
                                    String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4))+1));

                            String mSyllableSelection = "variable_phase_levels.group_id = "+
                                    mUserGPL.get(i).get(2)+" " +
                                    "AND variable_phase_levels.phase_id = "+
                                    mUserGPL.get(i).get(3)+" " +
                                    "AND variable_phase_levels.level_number <= "+
                                    mUserGPL.get(i).get(4)+" "+
                                    "AND variable_type_relations.variable_type_id=4";

                            String mSyllableWordSelection = "variable_phase_levels.group_id = 3 " +
                                    "AND variable_phase_levels.phase_id = 1 " +
                                    "AND variable_phase_levels.level_number <= "+
                                    mUserGPL.get(i).get(4)+" "+
                                    "AND variable_type_relations.variable_type_id=6";

                            String[] mSyllableProjection = new String[]{
                                    "0", //0
                                    mAppUserId, //1
                                    mUserGPL.get(i).get(2), //group_id 2
                                    mUserGPL.get(i).get(3), //phase_id 3
                                    "8", //section_id 4
                                    mUserGPL.get(i).get(4)//level_number 5
                            };


                            updateSyllableProcess(mSyllableProjection, mSyllableSelection);
                            updateSyllableWordsProcess(mSyllableProjection, mSyllableWordSelection);
                            break;
                        }
                        case 4: {     //group for words
                            rawSql="UPDATE app_users_current_gpl " +
                                    "SET app_user_current_level=app_user_current_level+1 " +
                                    "WHERE group_id=4 " +
                                    "AND phase_id=1 "+
                                    "AND app_users_current_gpl.app_user_id="+mAppUserId;
                            db.execSQL(rawSql);

                            mUserGPL.get(i).set(4,
                                    String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4))+1));

                            String mWordSelection = "variable_phase_levels.group_id = "+
                                    mUserGPL.get(i).get(2)+" " +
                                    "AND variable_phase_levels.phase_id = "+
                                    mUserGPL.get(i).get(3)+" " +
                                    "AND variable_phase_levels.level_number <= "+
                                    mUserGPL.get(i).get(4) + " " +
                                    "AND variable_phase_levels.level_number != 0 "+
                                    "AND variable_type_relations.variable_type_id=5";

                            String[] mWordProjection = new String[]{
                                    "0", //0
                                    mAppUserId, //1
                                    mUserGPL.get(i).get(2), //group_id 2
                                    mUserGPL.get(i).get(3), //phase_id 3
                                    "11", //section_id 4
                                    mUserGPL.get(i).get(4) //level_number 5
                            };

                            updateWordsProcess(mWordProjection, mWordSelection);
                            break;
                        }
                        case 5: {
                            break;
                        }
                    }//end switch(Integer.parseInt(mUserGPL.get(i).get(2))){
                }//end  if ((mMinNumberCorrect >= MotoliConstants.INA_ROW_CORRECT)
            }




            /*
            if(!cursor.isNull(cursor.getColumnIndex("_id")) ||
                    (mUserGPL.get(i).get(2).equals("2") &&
                            mUserGPL.get(i).get(3).equals("1") &&
                            mUserGPL.get(i).get(4).equals("1") &&
                            cursor.isNull(cursor.getColumnIndex("_id"))) ||
                    (mUserGPL.get(i).get(2).equals("3") &&
                            mUserGPL.get(i).get(3).equals("1") &&
                            mUserGPL.get(i).get(4).equals("1") &&
                            cursor.isNull(cursor.getColumnIndex("_id"))) ||
                    (mUserGPL.get(i).get(2).equals("4") &&
                            mUserGPL.get(i).get(3).equals("1") &&
                            mUserGPL.get(i).get(4).equals("2") &&
                            cursor.isNull(cursor.getColumnIndex("_id")))){

                int mUserGPLevel = 0;
                for (int j = 0; j < mUserGPL.size(); j++) {
                    if (mUserGPL.get(j).get(2).equals(mRegularGPL.get(i).get(1))
                            && mUserGPL.get(j).get(3).equals(mRegularGPL.get(i).get(2))) {
                        mUserGPLevel = Integer.parseInt(mUserGPL.get(j).get(4));
                    }
                }

                if ((mMinNumberCorrect >= MotoliConstants.INA_ROW_CORRECT && Integer.parseInt(mRegularGPL.get(i).get(3))>=mUserGPLevel)
                        || (mUserGPLevel==1 && cursor.isNull(cursor.getColumnIndex("_id"))
                        && mUserGPL.get(i).get(2).equals(mCurrentGPL.get(0)))){

                    if (mMinNumberCorrect >= MotoliConstants.INA_ROW_CORRECT  &&
                            Integer.parseInt(mRegularGPL.get(i).get(3))>=mUserGPLevel) {
                        rawSql = "UPDATE app_users_current_gpl " +
                                " SET app_user_current_level=app_user_current_level+1 " +
                                " WHERE group_id=" + mUserGPL.get(i).get(2) +
                                " AND phase_id=" + mUserGPL.get(i).get(3) +
                                " AND app_user_id=" + mAppUserId;
                        db.execSQL(rawSql);

                        if(mUserGPL.get(i).get(2).equals("2") &&
                                mUserGPL.get(i).get(3).equals("3") &&
                                mRegularGPL.get(i).get(3).equals("3")) {
                            rawSql = "UPDATE app_users_current_gpl " +
                                    " SET app_user_current_level=3 " +
                                    " WHERE group_id=4" +
                                    " AND phase_id=1"+
                                    " AND app_user_id=" + mAppUserId;
                            db.execSQL(rawSql);
                        }

                        mUserGPL.get(i).set(4, String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)) + 1));
                    }
                    boolean mNextPhase=false;
                    String[] mNewProjections = new String[]{"0", mAppUserId, mUserGPL.get(i).get(2),
                            mUserGPL.get(i).get(3), "0", String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)) + 1)};
                    String mNewSelection = Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                            + Database.Variable_Phase_Levels.GROUP_ID + "=" + mUserGPL.get(i).get(2)
                            + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                            + Database.Variable_Phase_Levels.PHASE_ID + "="+ mUserGPL.get(i).get(3)
                            + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                            + Database.Variable_Phase_Levels.LEVEL_NUMBER + "<="
                            +String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)));

                    String mSyllableSelection = " variable_phase_levels.group_id=2 " +
                            "AND variable_phase_levels.phase_id=3 " +
                            "AND variable_phase_levels.level_number<=" +
                            "(SELECT app_users_current_gpl.app_user_current_level " +
                            "FROM app_users_current_gpl " +
                            "WHERE app_users_current_gpl.group_id=2 " +
                            "AND app_users_current_gpl.phase_id=3)";


                    String mNewSelectionSecond="";
                    String[] mNewProjectionsSecond = new String[]{"0", mAppUserId, mUserGPL.get(i).get(2),
                            mUserGPL.get(i).get(3), "0", String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)) + 1)};

                    if(mMinNumberCorrect==MotoliConstants.INA_ROW_CORRECT  &&
                            Integer.parseInt(mRegularGPL.get(i).get(3))==mUserGPLevel &&
                            mUserGPL.get(i).get(2).equals("2") &&
                            mUserGPL.get(i).get(3).equals("1") ){
                        mUserGPL.get(i).set(4,"1");
                        mNextPhase=true;

                        String mNewPhaseId=String.valueOf(Integer.parseInt(mUserGPL.get(i).get(3))+1);
                        mUserGPL.get(i).set(3,mNewPhaseId);
                        rawSql = "UPDATE app_users_current_gpl " +
                                " SET app_user_current_level=1 " +
                                " WHERE group_id=" + mUserGPL.get(i).get(2) +
                                " AND phase_id=" + mUserGPL.get(i).get(3) +
                                " AND app_user_id=" + mAppUserId;
                        db.execSQL(rawSql);


                        appData.setCurrentGroup_Section_Phase(mUserGPL.get(i).get(2), mUserGPL.get(i).get(3),
                                projection[2], "1");


                        mNewSelection = Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                + Database.Variable_Phase_Levels.GROUP_ID + "=" + mUserGPL.get(i).get(2)
                                + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                + Database.Variable_Phase_Levels.PHASE_ID + "="+ mUserGPL.get(i).get(3)
                                + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                + Database.Variable_Phase_Levels.LEVEL_NUMBER + "<="
                                +String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)));
                        mNewProjections = new String[]{"0", mAppUserId, mUserGPL.get(i).get(2),
                                mUserGPL.get(i).get(3), "0", String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)) + 1)};

                        mNewPhaseId=String.valueOf(Integer.parseInt(mUserGPL.get(i).get(3))+1);
                        mUserGPL.get(i).set(3,mNewPhaseId);
                        rawSql = "UPDATE app_users_current_gpl " +
                                " SET app_user_current_level=1 " +
                                " WHERE group_id=" + mUserGPL.get(i).get(2) +
                                " AND phase_id=" + mUserGPL.get(i).get(3) +
                                " AND app_user_id=" + mAppUserId;
                        db.execSQL(rawSql);

                        mNewSelectionSecond = Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                + Database.Variable_Phase_Levels.GROUP_ID + "=" + mUserGPL.get(i).get(2)
                                + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                + Database.Variable_Phase_Levels.PHASE_ID + "="+ mUserGPL.get(i).get(3)
                                + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                + Database.Variable_Phase_Levels.LEVEL_NUMBER + "<="
                                +String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)));
                        mNewProjectionsSecond = new String[]{"0", mAppUserId, mUserGPL.get(i).get(2),
                                mUserGPL.get(i).get(3), "5", String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)) + 1)};
                    }//end if(mMinNumberCorrect==MotoliConstants.INA_ROW_CORRECT  &&

                    rawSql="SELECT DISTINCT gpls.group_id, gpls.phase_id, gpls.section_id " +
                            "FROM groups_phase_sections gpls " +
                            "INNER JOIN groups_phase_sections_activities " +
                            "ON groups_phase_sections_activities.group_id = gpls.group_id " +
                            "AND groups_phase_sections_activities.phase_id = gpls.phase_id " +
                            "AND groups_phase_sections_activities.section_id = gpls.section_id " +
                            "WHERE (SELECT  COUNT(gpsa2.activity_id) "+
                            "FROM groups_phase_sections_activities gpsa2 "+
                            "WHERE gpsa2.group_id=gpls.group_id AND  gpsa2.phase_id=gpls.phase_id "+
                            "AND gpsa2.section_id=gpls.section_id) >1 "+
                            "ORDER BY  gpls.group_id ASC, gpls.phase_id ASC";
                    cursor = db.rawQuery(rawSql, null);

                    if (cursor.moveToFirst()) {
                        do {
                            mNewProjections[4] = cursor.getString(cursor.getColumnIndex("section_id"));
                            if(mUserGPL.get(i).get(2).equals("2")){
                                if(mUserGPL.get(i).get(3).equals("1") || mUserGPL.get(i).get(3).equals("2")){
                                    updateLetterProcess(mNewProjections, mNewSelection);

                                }else if(mNextPhase){
                                    updateLetterProcess(mNewProjections, mNewSelection);
                                    updatePhonicsProcess(mNewProjectionsSecond, mNewSelectionSecond);
                                    // updateWordsProcess(mNewProjectionsSecond, mNewSelectionSecond);
                                }else{
                                    updatePhonicsProcess(mNewProjections, mNewSelection);
                                }
                                       \
                            }else if(mUserGPL.get(i).get(2).equals("3")){

                                updateSyllableProcess(mNewProjections, mSyllableSelection);
                                // String[] mSyllableWordsProjections = new String[]{"0", mAppUserId, mUserGPL.get(i).get(2),
                                //        mUserGPL.get(i).get(3), "0", String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)) + 1)};
                                String mSyllableWordsSelection = Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                        + Database.Variable_Phase_Levels.GROUP_ID + "=3"
                                        + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                        + Database.Variable_Phase_Levels.PHASE_ID + "=1"
                                        + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                        + Database.Variable_Phase_Levels.LEVEL_NUMBER + "<="
                                        +String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)));

                                updateSyllableWordsProcess(mNewProjections, mSyllableWordsSelection);
                                //updateWordsProcess(mNewProjections, mNewSelection);
                            }else if(mUserGPL.get(i).get(2).equals("4")){
                                String mWordsSelection = Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                        + Database.Variable_Phase_Levels.GROUP_ID + "=4"
                                        + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                        + Database.Variable_Phase_Levels.PHASE_ID + "=1"
                                        + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                        + Database.Variable_Phase_Levels.LEVEL_NUMBER + "<="
                                        +String.valueOf(Integer.parseInt(mUserGPL.get(i).get(4)))
                                        + " AND " + Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE + "."
                                        + Database.Variable_Phase_Levels.LEVEL_NUMBER + "!=0";

                                updateWordsProcess(mNewProjections, mWordsSelection);
                            }


                        } while (cursor.moveToNext());
                    }

                }//end if ((mMinNumberCorrect == 3 && Integer.parseInt(mRegularGPL.get(i).get(3))<=mUserGPLevel)

            }//if(cursor.moveToFirst()) {
            */

        }//end for(int i=0;i<mRegularGPL.size();i++){




        mUserGPL=new ArrayList<ArrayList<String>>();

        rawSql="SELECT * FROM app_users_current_gpl  " +
                "WHERE app_users_current_gpl.app_user_id="+mAppUserId+" "+
                "ORDER BY app_users_current_gpl.group_id ASC, "+
                "app_users_current_gpl.phase_id ASC";
        cursor = db.rawQuery(rawSql, null);

        if(cursor.moveToFirst()){
            do{
                mUserGPL.add(new ArrayList<String>());
                mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("_id"))); //0
                mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("app_user_id"))); //1
                mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("group_id"))); //2
                mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("phase_id"))); //3
                mUserGPL.get(mUserGPL.size()-1).add(cursor.getString(cursor.getColumnIndex("app_user_current_level"))); //4
            }while(cursor.moveToNext());
        }
        return cursor;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private  String[] updateSyllableWordsProcess(String[] projection, String whereSection) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String rawSql;
        Cursor cursorSecond;



        String mWordsTableName=Database.Words.WORDS_TABLE_NAME;
        String mVariablePhaseLevelsTableName=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE;
        int blankCount=0;

        String mAUAVVTable=Database.App_Users_Activity_Variable_Values.AUAVV_TABLE;

        rawSql="SELECT * FROM "+Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME+" "
                +" WHERE "+Database.Groups_Phase_Sections_Activities.GROUP_ID+"="+projection[2]+" "
                +" AND "+Database.Groups_Phase_Sections_Activities.PHASE_ID+"="+projection[3]+" "
                +" AND "+Database.Groups_Phase_Sections_Activities.SECTION_ID+"="+projection[4];

        if(projection[2].equals("3") && projection[3].equals("1")){
            rawSql+=" AND activity_id>=29 AND activity_id<=31";
        }

        Cursor cursor = db.rawQuery(rawSql, null);
        while(cursor.moveToNext()){
            String activity_id=cursor.getString(cursor.getColumnIndex(Database.App_Users_Activity_Variable_Values.ACTIVITY_ID));


            rawSql="SELECT variable_type_relations.variable_type_id, "+mAUAVVTable+"._id AS auavv_id_correct, " +
                    mAUAVVTable+".*, "+mWordsTableName+".* FROM "+mWordsTableName+" "
                    +"LEFT JOIN variable_type_relations ON variable_type_relations.variable_id=words.word_id "
                    +" INNER JOIN "+mVariablePhaseLevelsTableName
                    +" ON "+mVariablePhaseLevelsTableName+"."+Database.Variable_Phase_Levels.VARIABLE_ID
                    +"="+mWordsTableName+"."+Database.Words.WORD_ID+" "
                    +" LEFT JOIN "+Database.App_Users_Activity_Variable_Values.AUAVV_TABLE
                    +" ON "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="
                    +mWordsTableName+"."+Database.Words.WORD_ID
                    +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+activity_id
                    +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+projection[1]
                    +" WHERE "+whereSection+"";
            cursorSecond = db.rawQuery(rawSql, null);

            while(cursorSecond.moveToNext()){
                String auavv_id=cursorSecond.getString(cursorSecond.getColumnIndex("auavv_id_correct"));
                if(auavv_id == null){

                    blankCount++;
                    ContentValues values = new ContentValues();
                    values.put(Database.App_Users_Activity_Variable_Values.VARIABLE_ID,
                            cursorSecond.getString(cursorSecond.getColumnIndex(Database.Words.WORD_ID)));
                    values.put(Database.App_Users_Activity_Variable_Values.TYPE_ID,
                            cursorSecond.getString(cursorSecond.getColumnIndex("variable_type_id")));
                    values.put(Database.App_Users_Activity_Variable_Values.ACTIVITY_ID, activity_id);
                    values.put(Database.App_Users_Activity_Variable_Values.APP_USER_ID, projection[1]);

                    if(activity_id.equals("6") || activity_id.equals("20")) {
                        values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW,
                                String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                        values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT,
                                String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                    }else{
                          /**/
                        if(projection[2].equals("2") && projection[3].equals("1")) {
                            //Toast.makeText(appData.getApplicationContext(), "remember to revert", Toast.LENGTH_LONG).show();
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW, "2");
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT, "2");
                        }else{

                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW, "0");
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT, "0");
                        }
                    }

                    values.put(Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT, "0");


                    long current_id =db.insert(mAUAVVTable, null, values);

                    rawSql="UPDATE "+mAUAVVTable+" SET "+Database.App_Users_Activity_Variable_Values.AUAVV_ID
                            +"="+String.valueOf(current_id)
                            +" WHERE "+Database.App_Users_Activity_Variable_Values._ID
                            +"="+String.valueOf(current_id);

                    db.execSQL(rawSql);

                }else if(cursorSecond.getInt(cursorSecond.getColumnIndex(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT))>=MotoliConstants.NUMBER_CORRECT){
                    blankCount++;
                }//end if(auavv_id.equals("")){

            }//end  while(cursor.moveToNext()){
            cursorSecond.close();
        }
        int curCount=cursor.getCount();

        String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
        cursor.close();

        return returnString;

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private String[] updateLetterProcess(String[] projection, String whereSection) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String rawSql;
        Cursor cursorSecond;


        rawSql="SELECT app_user_current_level FROM app_users_current_gpl " +
                "WHERE app_user_id="+projection[1]+" "+
                "AND group_id="+projection[2]+" AND phase_id="+projection[3];
        Cursor mUserCursor = db.rawQuery(rawSql, null);
        String mAppUserCurrentLevel="0";
        if(mUserCursor.moveToFirst()){
            mAppUserCurrentLevel=mUserCursor.getString(mUserCursor.getColumnIndex("app_user_current_level"));
        }


        String mLettersTable=Database.Letters.LETTERS;
        String mVariablePhaseLevelsTableName=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE;
        int blankCount=0;

        String mAUAVVTable=Database.App_Users_Activity_Variable_Values.AUAVV_TABLE;

        rawSql="SELECT * FROM "+Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME+" "
                +" WHERE "+Database.Groups_Phase_Sections_Activities.GROUP_ID+"="+projection[2]+" "
                +" AND "+Database.Groups_Phase_Sections_Activities.PHASE_ID+"="+projection[3]+" "
                +" AND "+Database.Groups_Phase_Sections_Activities.SECTION_ID+"="+projection[4];

        if(projection[2].equals("3") && projection[3].equals("1")){
            rawSql+=" AND activity_id>=29 AND activity_id<=31";
        }

        Cursor cursor = db.rawQuery(rawSql, null);
        while(cursor.moveToNext()){
            String activity_id=cursor.getString(cursor.getColumnIndex(Database.App_Users_Activity_Variable_Values.ACTIVITY_ID));


            rawSql="SELECT variable_type_relations.variable_type_id, " +
                    "app_users_activity_variable_values._id AS auavv_id_correct, " +
                    "app_users_activity_variable_values.*, letters.* " +
                    "FROM letters LEFT JOIN variable_type_relations " +
                    "ON variable_type_relations.variable_id=letters.letter_id  " +
                    "INNER JOIN variable_phase_levels " +
                    "ON variable_phase_levels.variable_id=letters.letter_id  " +
                    "LEFT JOIN app_users_activity_variable_values " +
                    "ON app_users_activity_variable_values.variable_id=letters.letter_id " +
                    "AND app_users_activity_variable_values.activity_id="+activity_id+" "+
                    "AND app_users_activity_variable_values.app_user_id="+projection[1]+" "+
                    "WHERE "+whereSection;
                    /*
                    "SELECT "+mAUAVVTable+"._id AS auavv_id_correct, "+mAUAVVTable+".*, "
                    +mLettersTable+".* FROM "+mLettersTable
                    +"LEFT JOIN variable_type_relations ON variable_type_relations.variable_id=letters.letter_id  "
                    +" INNER JOIN "+mVariablePhaseLevelsTableName
                    +" ON "+mVariablePhaseLevelsTableName+"."+Database.Variable_Phase_Levels.VARIABLE_ID
                    +"="+mLettersTable+"."+Database.Letters.LETTER_ID+" "
                    +" LEFT JOIN "+Database.App_Users_Activity_Variable_Values.AUAVV_TABLE
                    +" ON "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+"="
                    +mLettersTable+"."+Database.Letters.LETTER_ID
                    +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+activity_id
                    +" AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+projection[1]
                    +" WHERE "+whereSection+"";
                    */
            cursorSecond = db.rawQuery(rawSql, null);

            while(cursorSecond.moveToNext()){
                String auavv_id=cursorSecond.getString(cursorSecond.getColumnIndex("auavv_id_correct"));
                if(auavv_id == null){

                    blankCount++;
                    ContentValues values = new ContentValues();
                    values.put(Database.App_Users_Activity_Variable_Values.VARIABLE_ID,
                            cursorSecond.getString(cursorSecond.getColumnIndex(Database.Letters.LETTER_ID)));
                    values.put(Database.App_Users_Activity_Variable_Values.TYPE_ID,
                            cursorSecond.getString(cursorSecond.getColumnIndex("variable_type_id")));
                    values.put(Database.App_Users_Activity_Variable_Values.ACTIVITY_ID, activity_id);
                    values.put(Database.App_Users_Activity_Variable_Values.APP_USER_ID, projection[1]);

                    if(/*activity_id.equals("6") || */activity_id.equals("20")) {
                        values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW,
                                String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                        values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT,
                                String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                    }else if(activity_id.equals("8") &&
                            (mAppUserCurrentLevel.equals("1") || mAppUserCurrentLevel.equals("2"))){
                        values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW,
                                String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                        values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT,
                                String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                    }else{
                          /**/
                        if(projection[2].equals("2") && projection[3].equals("1")) {
                            //Toast.makeText(appData.getApplicationContext(), "remember to revert", Toast.LENGTH_LONG).show();
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW, "2");
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT, "2");
                        }else{

                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW, "0");
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT, "0");
                        }
                    }

                    values.put(Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT, "0");


                    long current_id =db.insert(mAUAVVTable, null, values);

                    rawSql="UPDATE "+mAUAVVTable+" SET "+Database.App_Users_Activity_Variable_Values.AUAVV_ID
                            +"="+String.valueOf(current_id)
                            +" WHERE "+Database.App_Users_Activity_Variable_Values._ID
                            +"="+String.valueOf(current_id);

                    db.execSQL(rawSql);

                }else if(cursorSecond.getInt(cursorSecond.getColumnIndex(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT))>=MotoliConstants.NUMBER_CORRECT){
                    blankCount++;
                }//end if(auavv_id.equals("")){

            }//end  while(cursor.moveToNext()){
            cursorSecond.close();
        }
        int curCount=cursor.getCount();

        String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
        cursor.close();

        return returnString;

    }
    //////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////

	 private String[] updateWordsProcess(String[] projection, String whereSection) {
		  SQLiteDatabase db = dbHelper.getWritableDatabase();

		  String rawSql;
		  Cursor cursorSecond;

		  int blankCount=0;

		  String mAUAVVTable="app_users_activity_variable_values";

		  rawSql="SELECT * FROM "+Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME+" "
				  +" WHERE "+Database.Groups_Phase_Sections_Activities.GROUP_ID+"="+projection[2]+" "
				  +" AND "+Database.Groups_Phase_Sections_Activities.PHASE_ID+"="+projection[3]+" "
				  +" AND "+Database.Groups_Phase_Sections_Activities.SECTION_ID+"="+projection[4];


		 Cursor cursor = db.rawQuery(rawSql, null);
		  while(cursor.moveToNext()){
			  String activity_id=cursor.getString(cursor.getColumnIndex(Database.App_Users_Activity_Variable_Values.ACTIVITY_ID));


			  rawSql="SELECT variable_type_relations.variable_type_id, " +
                      "app_users_activity_variable_values._id AS auavv_id_correct, " +
                      "app_users_activity_variable_values.*, "+
                      "words.* " +
                      "FROM words "+
                      "LEFT JOIN variable_type_relations " +
                      "ON variable_type_relations.variable_id=words.word_id "+
					  "INNER JOIN variable_phase_levels "+
					  "ON variable_phase_levels.variable_id=words.word_id "+
					  "LEFT JOIN app_users_activity_variable_values "+
					  "ON app_users_activity_variable_values.variable_id=words.word_id "+
					  "AND app_users_activity_variable_values.activity_id="+activity_id+" "+
					  "AND app_users_activity_variable_values.app_user_id="+projection[1]+" "+
					  "WHERE "+whereSection;
			  cursorSecond = db.rawQuery(rawSql, null);

              int countWords=cursorSecond.getCount();

			  while(cursorSecond.moveToNext()){
				  String auavv_id=cursorSecond.getString(cursorSecond.getColumnIndex("auavv_id_correct"));
				  if(auavv_id == null){

					  blankCount++;
					  ContentValues values = new ContentValues();
					  values.put(Database.App_Users_Activity_Variable_Values.VARIABLE_ID,
                              cursorSecond.getString(cursorSecond.getColumnIndex(Database.Words.WORD_ID)));
                      values.put(Database.App_Users_Activity_Variable_Values.TYPE_ID,
                              cursorSecond.getString(cursorSecond.getColumnIndex("variable_type_id")));
					  values.put(Database.App_Users_Activity_Variable_Values.ACTIVITY_ID, activity_id);
					  values.put(Database.App_Users_Activity_Variable_Values.APP_USER_ID, projection[1]);




                      if(projection[0].equals("1")) {
                          values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW,
                                  MotoliConstants.INA_ROW_CORRECT);
                          values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT,
                                  MotoliConstants.INA_ROW_CORRECT);
                      }else if(activity_id.equals("44")) {
                          values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW,
                                  MotoliConstants.INA_ROW_CORRECT);
                          values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT,
                                  MotoliConstants.INA_ROW_CORRECT);
                      }else if(activity_id.equals("42") && cursorSecond.getString(cursorSecond.getColumnIndex("image_url")).equals("")){
                          values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW,
                                  MotoliConstants.INA_ROW_CORRECT);
                          values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT,
                                  MotoliConstants.INA_ROW_CORRECT);
                      }else{

                          values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW, "0");
                          values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT, "0");
                      }


					  values.put(Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT, "0");


					  long current_id =db.insert(mAUAVVTable, null, values);

					  rawSql="UPDATE "+mAUAVVTable+" SET "+Database.App_Users_Activity_Variable_Values.AUAVV_ID
						+"="+String.valueOf(current_id)
						+" WHERE "+Database.App_Users_Activity_Variable_Values._ID
						+"="+String.valueOf(current_id);

					  db.execSQL(rawSql);

				  }else if(cursorSecond.getInt(cursorSecond.getColumnIndex(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT))>=MotoliConstants.NUMBER_CORRECT){
					  blankCount++;
				  }//end if(auavv_id.equals("")){

			  }//end  while(cursor.moveToNext()){
			  cursorSecond.close();
		  }
		  int curCount=cursor.getCount();

		  String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
		  cursor.close();

		  return returnString;

	 }
    //////////////////////////////////////////////////////////////////////////////////////////////


    private String[] updateSyllableProcess(String[] projection, String whereSection) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String rawSql;
        Cursor cursorSecond;

        projection[3]="1";
        projection[4]="8";
        String mSyllablesTableName=Database.Syllables.SYLLABLES_TABLE;
        String mVariablePhaseLevelsTableName=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE;

        int blankCount=0;

        String mAUAVVTable=Database.App_Users_Activity_Variable_Values.AUAVV_TABLE;

        rawSql="SELECT * FROM "+Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME+" "
                +" WHERE "+Database.Groups_Phase_Sections_Activities.GROUP_ID+"="+projection[2]
                +" AND "+Database.Groups_Phase_Sections_Activities.PHASE_ID+"="+projection[3]
                +" AND "+Database.Groups_Phase_Sections_Activities.SECTION_ID+"="+projection[4]
                +" AND activity_id>=32 AND activity_id<=34";

        Cursor cursor = db.rawQuery(rawSql, null);
        while(cursor.moveToNext()){
            String activity_id=cursor.getString(cursor.getColumnIndex(Database.App_Users_Activity_Variable_Values.ACTIVITY_ID));


            rawSql="SELECT ";
            switch(Integer.parseInt(activity_id)){
                default:
                case 24:
                case 23:
                case 32:
                case 34:{
                    rawSql+=" ";
                    break;
                }
                case 25:
                case 19:
                case 15:
                case 33:{
                    rawSql+=" DISTINCT syllable_words.syllable_word_first_id, ";
                    break;
                }
                case 17:{
                    rawSql+=" DISTINCT syllable_words.syllable_word_last_id, ";
                    break;
                }
            }//end switch(Integer.parseInt(activity_id)){
            rawSql+=mAUAVVTable+"._id AS auavv_id_correct, "+
                    mAUAVVTable+".*, variable_type_relations.variable_type_id, "+mSyllablesTableName+".* "+
                    "FROM "+mSyllablesTableName+" "+
                    " LEFT JOIN variable_type_relations ON variable_type_relations.variable_id=syllables.phonic_id "+
                    " INNER JOIN "+mVariablePhaseLevelsTableName+
                    " ON "+mVariablePhaseLevelsTableName+"."+Database.Variable_Phase_Levels.VARIABLE_ID+
                    "="+mSyllablesTableName+"."+Database.Syllables.PHONIC_ID+
                    " LEFT JOIN "+Database.App_Users_Activity_Variable_Values.AUAVV_TABLE+
                    " ON "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.VARIABLE_ID+
                    "="+mSyllablesTableName+"."+Database.Syllables.PHONIC_ID+
                    " AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.ACTIVITY_ID+"="+activity_id+
                    " AND "+mAUAVVTable+"."+Database.App_Users_Activity_Variable_Values.APP_USER_ID+"="+projection[1];

            switch(Integer.parseInt(activity_id)){
                default:
                case 24:
                case 23:
                case 32:
                case 34:{
                    rawSql+=" ";
                    break;
                }
                case 25:
                case 19:
                case 15:
                case 33:{
                    rawSql+=" JOIN syllable_words ON syllable_words.syllable_word_first_id=syllables.syllables_id";
                    break;
                }
                case 17:{
                    rawSql+=" JOIN syllable_words ON syllable_words.syllable_word_last_id=syllables.syllables_id";
                    break;
                }
            }//end switch(Integer.parseInt(activity_id)){

            rawSql+=" WHERE "+whereSection+" AND variable_type_relations.variable_type_id=4";

            cursorSecond = db.rawQuery(rawSql, null);

            for(cursorSecond.moveToFirst(); !cursorSecond.isAfterLast(); cursorSecond.moveToNext()){
                String auavv_id = cursorSecond.getString(cursorSecond.getColumnIndex("auavv_id_correct"));
                if (auavv_id == null) {

                    blankCount++;
                    ContentValues values = new ContentValues();
                    values.put(Database.App_Users_Activity_Variable_Values.VARIABLE_ID,
                            cursorSecond.getString(cursorSecond.getColumnIndex(Database.Syllables.SYLLABLES_ID)));
                    values.put(Database.App_Users_Activity_Variable_Values.TYPE_ID,
                            cursorSecond.getString(cursorSecond.getColumnIndex("variable_type_id")));
                    values.put(Database.App_Users_Activity_Variable_Values.ACTIVITY_ID, activity_id);
                    values.put(Database.App_Users_Activity_Variable_Values.APP_USER_ID, projection[1]);


                    values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW, "0");
                    values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT, "0");

                    values.put(Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT, "0");


                    long current_id = db.insert(mAUAVVTable, null, values);

                    rawSql = "UPDATE " + mAUAVVTable + " SET " + Database.App_Users_Activity_Variable_Values.AUAVV_ID
                            + "=" + String.valueOf(current_id)
                            + " WHERE " + Database.App_Users_Activity_Variable_Values._ID
                            + "=" + String.valueOf(current_id);

                    db.execSQL(rawSql);
                    /**/
                } else if (cursorSecond.getInt(cursorSecond.getColumnIndex(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT)) >= MotoliConstants.NUMBER_CORRECT) {
                    blankCount++;
                }//end if(auavv_id.equals("")){

            }//end for(cursorSecond.moveToFirst(); !cursorSecond.isAfterLast(); cursorSecond.moveToNext()){
            cursorSecond.close();
        }
        int curCount=cursor.getCount();

        String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
        cursor.close();

        return returnString;

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private String[] updatePhonicsProcess(String[] projection, String whereSection) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String rawSql;

        String mAppUserCurrentLevel="0";

        rawSql="SELECT * " +
                "FROM app_users_current_gpl " +
                "WHERE group_id=2 AND phase_id=3 " +
                "AND app_user_id="+projection[1]+" LIMIT 1";
        Cursor userCursor = db.rawQuery(rawSql, null);
        if(userCursor.moveToFirst()) {
            do {
                mAppUserCurrentLevel=userCursor.getString(userCursor.getColumnIndex("app_user_current_level"));
            } while (userCursor.moveToNext());
        }
        userCursor.close();

        Cursor cursorSecond;

        projection[3]="3";
        projection[4]="5";
        String mPhonicsTableName=Database.Phonics.PHONICS_TABLE_NAME;
        String mVariablePhaseLevelsTableName=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE;

        int blankCount=0;

        String mAUAVVTable=Database.App_Users_Activity_Variable_Values.AUAVV_TABLE;

        rawSql="SELECT * FROM "+Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME+" "
                +" WHERE "+Database.Groups_Phase_Sections_Activities.GROUP_ID+"="+projection[2]+" "
                +" AND "+Database.Groups_Phase_Sections_Activities.PHASE_ID+"="+projection[3]+" "
                +" AND "+Database.Groups_Phase_Sections_Activities.SECTION_ID+"="+projection[4];

        Cursor cursor = db.rawQuery(rawSql, null);
        if(cursor.moveToFirst()) {
            do {

                String activity_id = cursor.getString(cursor.getColumnIndex(Database.App_Users_Activity_Variable_Values.ACTIVITY_ID));


                rawSql = "SELECT ";
                switch (Integer.parseInt(activity_id)) {
                    default:
                    case 24:
                    case 23: {
                        rawSql += " ";
                        break;
                    }
                    case 25:
                    case 19:
                    case 15: {
                        rawSql += " DISTINCT phonic_words.phonic_word_first_id, ";
                        break;
                    }
                    case 17: {
                        rawSql += " DISTINCT phonic_words.phonic_word_last_id, ";
                        break;
                    }
                }//end switch(Integer.parseInt(activity_id)){
                rawSql += mAUAVVTable + "._id AS auavv_id_correct, " +
                        mAUAVVTable + ".*, variable_type_relations.variable_type_id, " + mPhonicsTableName + ".* " +
                        "FROM " + mPhonicsTableName +
                        " LEFT JOIN variable_type_relations ON variable_type_relations.variable_id=phonics.phonic_id " +
                        " INNER JOIN " + mVariablePhaseLevelsTableName +
                        " ON " + mVariablePhaseLevelsTableName + "." + Database.Variable_Phase_Levels.VARIABLE_ID +
                        "=" + mPhonicsTableName + "." + Database.Phonics.PHONIC_ID +
                        " LEFT JOIN " + Database.App_Users_Activity_Variable_Values.AUAVV_TABLE +
                        " ON " + mAUAVVTable + "." + Database.App_Users_Activity_Variable_Values.VARIABLE_ID +
                        "=" + mPhonicsTableName + "." + Database.Phonics.PHONIC_ID +
                        " AND " + mAUAVVTable + "." + Database.App_Users_Activity_Variable_Values.ACTIVITY_ID + "=" + activity_id +
                        " AND " + mAUAVVTable + "." + Database.App_Users_Activity_Variable_Values.APP_USER_ID + "=" + projection[1];

                switch (Integer.parseInt(activity_id)) {
                    default:
                    case 24:
                    case 23: {
                        rawSql += " ";
                        break;
                    }
                    case 25:
                    case 19:
                    case 15: {
                        rawSql += " JOIN phonic_words ON phonic_words.phonic_word_first_id=phonics.phonic_id";
                        break;
                    }
                    case 17: {
                        rawSql += " JOIN phonic_words ON phonic_words.phonic_word_last_id=phonics.phonic_id";
                        break;
                    }
                }//end switch(Integer.parseInt(activity_id)){

                rawSql += " WHERE " + whereSection + " AND variable_type_relations.variable_type_id=3";

                cursorSecond = db.rawQuery(rawSql, null);

                for (cursorSecond.moveToFirst(); !cursorSecond.isAfterLast(); cursorSecond.moveToNext()) {
                    String auavv_id = cursorSecond.getString(cursorSecond.getColumnIndex("auavv_id_correct"));
                    if (auavv_id == null) {

                        blankCount++;
                        ContentValues values = new ContentValues();
                        values.put(Database.App_Users_Activity_Variable_Values.VARIABLE_ID,
                                cursorSecond.getString(cursorSecond.getColumnIndex(Database.Phonics.PHONIC_ID)));
                        values.put(Database.App_Users_Activity_Variable_Values.TYPE_ID,
                                cursorSecond.getString(cursorSecond.getColumnIndex("variable_type_id")));
                        values.put(Database.App_Users_Activity_Variable_Values.ACTIVITY_ID, activity_id);
                        values.put(Database.App_Users_Activity_Variable_Values.APP_USER_ID, projection[1]);

                        if (activity_id.equals("35")) {
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW,
                                    String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT,
                                    String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                        } else if ((activity_id.equals("17") || activity_id.equals("24")) &&
                                (mAppUserCurrentLevel.equals("1"))) { //|| CurrentGroup_Section_Phase.get(3).equals("2"))) {
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW,
                                    String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT,
                                    String.valueOf(MotoliConstants.INA_ROW_CORRECT));
                        } else {
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW, "0");
                            values.put(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT, "0");
                        }


                        values.put(Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT, "0");


                        long current_id = db.insert(mAUAVVTable, null, values);

                        rawSql = "UPDATE " + mAUAVVTable + " SET " + Database.App_Users_Activity_Variable_Values.AUAVV_ID
                                + "=" + String.valueOf(current_id)
                                + " WHERE " + Database.App_Users_Activity_Variable_Values._ID
                                + "=" + String.valueOf(current_id);

                        db.execSQL(rawSql);
                    /**/
                    } else if (cursorSecond.getInt(cursorSecond.getColumnIndex(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT)) >= MotoliConstants.NUMBER_CORRECT) {
                        blankCount++;
                    }//end if(auavv_id.equals("")){

                }//end for(cursorSecond.moveToFirst(); !cursorSecond.isAfterLast(); cursorSecond.moveToNext()){
                cursorSecond.close();
            } while (cursor.moveToNext());
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
		 // SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		  
		  String rawSql;
		  Cursor cursor;
		  
        switch (uriMatcher.match(uri)) {
            default:
                break;
            case ACTIVITY_USER_RW_UPDATE:{
                int numberCorrectInARow;
                rawSql="SELECT "+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW
                            +" FROM "+Database.App_Users_Activity_Variable_Values.AUAVV_TABLE+" "+selection;
                cursor = db.rawQuery(rawSql, null);

                cursor.moveToFirst();
                numberCorrectInARow=cursor.getInt(cursor.getColumnIndex(Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW));

                //this is hard coded will need to fix
               // if(!(selectionArgs[3].equals("1") && (selectionArgs[4].equals("11572") || selectionArgs[4].equals("11573")))
                //        && !(selectionArgs[3].equals("2") && selectionArgs[4].equals("11573"))){


                    rawSql="UPDATE "+Database.App_Users_Activity_Variable_Values.AUAVV_TABLE+" ";
                    rawSql+=" SET "+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT+"="
                                +Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT+"+"+selectionArgs[0]+", "
                                +Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT+"="
                                +Database.App_Users_Activity_Variable_Values.NUMBER_INCORRECT+"+"+selectionArgs[1];
                    if(numberCorrectInARow<MotoliConstants.INA_ROW_CORRECT || selectionArgs[0].equals("2")){
                        if(selectionArgs[0].equals("1")&& selectionArgs[4].equals("0")){
                            rawSql+=", "+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW+"="
                                    +Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW+"+1 ";
                        }else if(selectionArgs[0].equals("2")) {
                            rawSql+=", "+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW+"=2";
                        }else{
                            rawSql+=", "+Database.App_Users_Activity_Variable_Values.NUMBER_CORRECT_IN_ROW+"=0 ";
                        }
                    }
                    rawSql+=selection;
                    db.execSQL(rawSql);
                rawSql="SELECT number_correct, number_correct_in_a_row, number_incorrect "+
                        "FROM app_users_activity_variable_values "+selection;
                cursor=db.rawQuery(rawSql,null);
                cursor.moveToFirst();
                int mNumberCorrect=cursor.getInt(cursor.getColumnIndex("number_correct"));
                int mNumberCorrectInARow=cursor.getInt(cursor.getColumnIndex("number_correct_in_a_row"));
                int mNumberInCorrect=cursor.getInt(cursor.getColumnIndex("number_incorrect"));

                int mInCorrectReduce=(mNumberCorrectInARow==3 && mNumberCorrect>5 && mNumberInCorrect>0) ? 1 :0;

                rawSql="UPDATE app_users_activity_variable_values "+
                        "SET number_incorrect=number_incorrect-"+mInCorrectReduce+" "+selection;
                db.execSQL(rawSql);

                //}
                cursor.close();
                //	db.update(Database.App_Users_Activity_Variable_Values.AUAVV_TABLE, values, selection, null);
                break;
            }
        }

        return 0;
	}

}
