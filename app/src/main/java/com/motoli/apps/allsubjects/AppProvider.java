package com.motoli.apps.allsubjects;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
public class AppProvider extends ContentProvider {


    private Context mContext;
    private boolean mSetAll;



    private AppProviderProcesses mAppProcesses;


    private SQLiteDatabase mDatabase;


    private static final int ALL_USERS = 1; //0
    private static final int INDIVIDUAL_USER = 2; //0
    private static final int INDIVIDUAL_GROUP_PHASE_SECTIONS = 16;//7
    private static final int SELECTED_ACTIVITIES = 23;
    private static final int ACTIVITY_USER_RW_UPDATE=27;
    private static final int GROUPS_PHASE_LEVELS_UPDATE=28;
    private static final int CURRENT_PHONIC_LETTERS=29;
    private static final int MATCHING_PHONIC_LETTERS_WORDS=30;
    private static final int CURRENT_SYLLABLE_WORDS=33;
    private static final int CURRENT_SYLLABLES=34;
    private static final int CURRENT_WORD_SYLLABLE_VALUES=35;
    private static final int BOOKS_READ_ALOUD=36;
    private static final int BOOK_PAGES_READ_ALOUD=37;
    private static final int ACTIVITY_CURRENT_LETTERS_WRW=38;
    private static final int ACTIVITY_CURRENT_LETTERS=39;
    private static final int ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE=40;
    private static final int CURRENT_WORDS_BY_LEVEL=43;
    private static final int GUESS_WORDS=44;
    private static final int ALL_LOWERCASE_LETTERS=45;
    private static final int ACTIVITY_USER_RW_ALL_UPDATE=46;
    private static final int CURRENT_WORDS_BY_LEVEL_WITH_PHONICS=47;
    private static final int ORDERED_PHONICS=48;
    private static final int CURRENT_ALL_SYLLABLES=49;
    private static final int BOOKS_DECODABLE=50;
    private static final int CURRENT_PHONICS_FOR_VIDEOS=52;
    private static final int CURRENT_SYLLABLES_FOR_VIDEOS=53;
    private static final int BOOK_VIDEOS=55;
    private static final int UPDATE_BOOK_LEVEL=56;
    private static final int UPDATE_BOOK_READ=57;
    private static final int CURRENT_PHONIC_LETTERS_WORDS_FIRST_LETTER=58;
    private static final int CURRENT_PHONIC_LETTERS_FIRST=59;
    private static final int CURRENT_PHONIC_LETTERS_WORDS_LAST_LETTER=60;
    private static final int CURRENT_PHONIC_LETTERS_LAST=61;
    private static final int CURRENT_WORDS_FOR_VIDEOS=62;
    private static final int SECTION_AWARDS=63;
    private static final int MATH_SHAPES=64;
    private static final int ACTIVITY_USER_RW_UPDATE_MS=65;
    private static final int MATH_NUMBERS=66;
    private static final int MATH_OPERATIONS=67;
    private static final int ACTIVITY_USER_ATTEMPTS=68;
    private static final int CHECK_WRONGS=69;
    private static final int DEFAULT=70;
    private static final int ACTIVITY_USER_RW_ALL_UPDATE_NO_INCORRECT=71;
    private static final int TRACING = 72;
    private static final int CURRENT_SHAPES_FOR_VIDEOS =73;
    private static final int CURRENT_NUMBERS_FOR_VIDEOS = 74;
    private static final int CURRENT_OPERATIONS_FOR_VIDEOS = 75;
    private static final int UPDATE_DIFFERENT_SECTIONS = 76;
    private static final int ACTIVITY_USER_RW_UPDATE_VV = 77;
    private static final int ACTIVITY_USER_RW_UPDATE_EVV = 78;
    private static final int ALL_MAX_LEVELS = 79;
    private static final int GET_MAX_LEVEL = 80;
    private static final int RESET_LEVELS = 81;


    private static final String AUTHORITY = "com.motoli.apps.allsubjects.AppProvider";
    public static final Uri CONTENT_URI_ALL_USERS
            = Uri.parse("content://" + AUTHORITY +"/all_users");
    public static final Uri CONTENT_URI_GROUPS_PHASE_LEVELS_UPDATE
            = Uri.parse("content://" + AUTHORITY +"/groups_phase_levels/update");
    public static final Uri CONTENT_URI_TRACING
            = Uri.parse("content://" + AUTHORITY + "/tracing");
    public static final Uri CONTENT_URI_INDIVIDUAL_GROUP_PHASE_SECTIONS
            = Uri.parse("content://" + AUTHORITY +"/groups_phase_sections/launch");
    public static final Uri CONTENT_URI_SELECTED_ACTIVITIES
            = Uri.parse("content://" + AUTHORITY +"/activities/wrd_ltr");
    public static final Uri CONTENT_URI_ACTIVITY_CURRENT_LETTERS
            = Uri.parse("content://" + AUTHORITY +"/letters/current");
    public static final Uri CONTENT_URI_ACTIVITY_CURRENT_LETTERS_WRW
            = Uri.parse("content://" + AUTHORITY +"/letters/current_wrw");
    public static final Uri CONTENT_URI_ACTIVITY_USER_RW_UPDATE
            = Uri.parse("content://" + AUTHORITY +"/app_users_activity_variable_values/update");
    public static final Uri CONTENT_URI_ACTIVITY_USER_RW_UPDATE_MS
            = Uri.parse("content://" + AUTHORITY +"/app_users_activity_variable_values/update_ms");
    public static final Uri CONTENT_URI_CURRENT_PHONIC_LETTERS
            = Uri.parse("content://" + AUTHORITY +"/phonics");
    public static final Uri CONTENT_URI_MATCHING_PHONIC_LETTERS_WORDS
            = Uri.parse("content://" + AUTHORITY +"/phonic_words/matching");
    public static final Uri CONTENT_URI_CURRENT_SYLLABLE_WORDS
            =Uri.parse("content://"+AUTHORITY+"/syllable_words");
    public static final Uri CONTENT_URI_CURRENT_SYLLABLES
            =Uri.parse("content://"+AUTHORITY+"/syllables");
    public static final Uri CONTENT_URI_CURRENT_WORD_SYLLABLE_VALUES
            =Uri.parse("content://"+AUTHORITY+"/words/syllable");
    public static final Uri CONTENT_URI_BOOKS_READ_ALOUD
            =Uri.parse("content://"+AUTHORITY+"/books/read_aloud");
    public static final Uri CONTENT_URI_BOOKS_DECODABLE
            =Uri.parse("content://"+AUTHORITY+"/books/decodable");
    public static final Uri CONTENT_URI_BOOK_PAGES_READ_ALOUD
            =Uri.parse("content://"+AUTHORITY+"/books/pages_read_aloud");
    public static final Uri CONTENT_URI_ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE
            =Uri.parse("content://"+AUTHORITY+"/activities/complete");
    public static final Uri CONTENT_ALL_MAX_LEVELS
            =Uri.parse("content://"+AUTHORITY+"/levels/max");
    public static final Uri CONTENT_GET_MAX_LEVEL
            = Uri.parse("content://" + AUTHORITY + "/levels/get_max");
    public static final Uri CONTENT_URI_CURRENT_WORDS_BY_LEVEL=Uri.parse("content://"+AUTHORITY+"/words/level");
    public static final Uri CONTENT_URI_CURRENT_WORDS_BY_LEVEL_WITH_PHONICS=Uri.parse("content://"+AUTHORITY+"/words/phonics");
    public static final Uri CONTENT_URI_GUESS_WORDS=Uri.parse("content://"+AUTHORITY+"/words/guess");
    public static final Uri CONTENT_URI_ALL_LOWERCASE_LETTERS=Uri.parse("content://"+AUTHORITY+"/letters/all_lower");
    public static final Uri CONTENT_URI_ACTIVITY_USER_RW_ALL_UPDATE = Uri.parse("content://" + AUTHORITY +"/app_users_activity_variable_values/update_all");
    public static final Uri CONTENT_URI_ORDERED_PHONICS= Uri.parse("content://" + AUTHORITY +"/phonics/ordered");
    public static final Uri CONTENT_URI_CURRENT_ALL_SYLLABLES= Uri.parse("content://" + AUTHORITY +"/syllables/all");
    public static final Uri CONTENT_URI_CURRENT_PHONICS_FOR_VIDEOS=Uri.parse("content://"+AUTHORITY+"/phonics/videos");
    public static final Uri CONTENT_URI_CURRENT_SYLLABLES_FOR_VIDEOS=Uri.parse("content://"+AUTHORITY+"/syllables/videos");
    public static final Uri CONTENT_URI_BOOK_VIDEOS=Uri.parse("content://"+AUTHORITY+"/books/videos");
    public static final Uri CONTENT_URI_UPDATE_BOOK_LEVEL=Uri.parse("content://"+AUTHORITY+"/books/update_level");
    public static final Uri CONTENT_URI_UPDATE_BOOK_READ=Uri.parse("content://"+AUTHORITY+"/books/update_book_read");
    public static final Uri CONTENT_URI_CURRENT_PHONIC_LETTERS_WORDS_FIRST_LETTER=Uri.parse("content://"+AUTHORITY+"/phonic_words/first_letter");
    public static final Uri CONTENT_URI_CURRENT_PHONIC_LETTERS_FIRST=Uri.parse("content://"+AUTHORITY+"/phonics/first");
    public static final Uri CONTENT_URI_CURRENT_PHONIC_LETTERS_WORDS_LAST_LETTER=Uri.parse("content://"+AUTHORITY+"/phonic_words/last_letter");
    public static final Uri CONTENT_URI_CURRENT_PHONIC_LETTERS_LAST=Uri.parse("content://"+AUTHORITY+"/phonics/last");
    public static final Uri CONTENT_URI_CURRENT_WORDS_FOR_VIDEOS=Uri.parse("content://"+AUTHORITY+"/words/videos");
    public static final Uri CONTENT_URI_SECTION_AWARDS=Uri.parse("content://"+AUTHORITY+"/sections/awards");
    public static final Uri CONTENT_URI_MATH_SHAPES=Uri.parse("content://"+AUTHORITY+"/math_shapes");
    public static final Uri CONTENT_URI_MATH_NUMBERS=Uri.parse("content://"+AUTHORITY+"/math_numbers");
    public static final Uri CONTENT_URI_MATH_OPERATIONS=Uri.parse("content://"+AUTHORITY+"/math_operations");
    public static final Uri CONTENT_URI_ACTIVITY_USER_ATTEMPTS=Uri.parse("content://"+AUTHORITY+"/activity_user_attempts");
    public static final Uri CONTENT_URI_CHECK_WRONGS=Uri.parse("content://"+AUTHORITY+"/check_wrongs");
    public static final Uri CONTENT_URI_CURRENT_SHAPES_FOR_VIDEOS = Uri.parse("content://"
            + AUTHORITY + "/math_shapes/videos");
    public static final Uri CONTENT_URI_CURRENT_NUMBERS_FOR_VIDEOS = Uri.parse("content://"
            + AUTHORITY + "/math_numbers/videos");
    public static final Uri CONTENT_URI_CURRENT_OPERATIONS_FOR_VIDEOS = Uri.parse("content://"
            + AUTHORITY + "/math_operations/videos");
    public static final Uri CONTENT_URI_UPDATE_DIFFERENT_SECTIONS = Uri.parse("content://" +
            AUTHORITY + "/update/sections");

    public static final Uri CONTENT_URI_ACTIVITY_USER_RW_UPDATE_VV = Uri.parse("content://"
            + AUTHORITY + "/app_users_activity_variable_values/update_vv");
    public static final Uri CONTENT_URI_ACTIVITY_USER_RW_UPDATE_EVV = Uri.parse("content://" +
            AUTHORITY + "/app_users_activity_variable_values/update_evv");
    public static final Uri CONTENT_URI_RESET_LEVELS = Uri.parse("content://" +
            AUTHORITY + "/levels/reset");
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "all_users", ALL_USERS);
        uriMatcher.addURI(AUTHORITY, "app_users/#", INDIVIDUAL_USER);
        uriMatcher.addURI(AUTHORITY, "groups_phase_levels/update", GROUPS_PHASE_LEVELS_UPDATE);
        uriMatcher.addURI(AUTHORITY, "groups_phase_sections/launch", INDIVIDUAL_GROUP_PHASE_SECTIONS);
        uriMatcher.addURI(AUTHORITY, "activities/wrd_ltr", SELECTED_ACTIVITIES);
        uriMatcher.addURI(AUTHORITY, "letters/current", ACTIVITY_CURRENT_LETTERS);
        uriMatcher.addURI(AUTHORITY, "letters/current_wrw",ACTIVITY_CURRENT_LETTERS_WRW);
        uriMatcher.addURI(AUTHORITY, "app_users_activity_variable_values/update", ACTIVITY_USER_RW_UPDATE);
        uriMatcher.addURI(AUTHORITY, "app_users_activity_variable_values/update_ms", ACTIVITY_USER_RW_UPDATE_MS);
        uriMatcher.addURI(AUTHORITY, "app_users_activity_variable_values/update_vv", ACTIVITY_USER_RW_UPDATE_VV);
        uriMatcher.addURI(AUTHORITY, "levels/max", ALL_MAX_LEVELS);
        uriMatcher.addURI(AUTHORITY, "levels/get_max", GET_MAX_LEVEL);
        uriMatcher.addURI(AUTHORITY, "app_users_activity_variable_values/update_all", ACTIVITY_USER_RW_ALL_UPDATE);
        uriMatcher.addURI(AUTHORITY, "app_users_activity_variable_values/update_all_no_incorrect", ACTIVITY_USER_RW_ALL_UPDATE_NO_INCORRECT);
        uriMatcher.addURI(AUTHORITY, "phonics", CURRENT_PHONIC_LETTERS);
        uriMatcher.addURI(AUTHORITY, "phonics/ordered", ORDERED_PHONICS);
        uriMatcher.addURI(AUTHORITY, "phonic_words/matching",MATCHING_PHONIC_LETTERS_WORDS);
        uriMatcher.addURI(AUTHORITY, "syllable_words",CURRENT_SYLLABLE_WORDS);
        uriMatcher.addURI(AUTHORITY, "syllables",CURRENT_SYLLABLES);
        uriMatcher.addURI(AUTHORITY, "syllables/all",CURRENT_ALL_SYLLABLES);
        uriMatcher.addURI(AUTHORITY, "words/syllable",CURRENT_WORD_SYLLABLE_VALUES);
        uriMatcher.addURI(AUTHORITY, "books/decodable",BOOKS_DECODABLE);
        uriMatcher.addURI(AUTHORITY, "levels/reset", RESET_LEVELS);
        uriMatcher.addURI(AUTHORITY, "books/read_aloud",BOOKS_READ_ALOUD);
        uriMatcher.addURI(AUTHORITY, "books/pages_read_aloud",BOOK_PAGES_READ_ALOUD);
        uriMatcher.addURI(AUTHORITY, "activities/complete",ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE);
        uriMatcher.addURI(AUTHORITY, "words/level",CURRENT_WORDS_BY_LEVEL);
        uriMatcher.addURI(AUTHORITY, "words/guess",GUESS_WORDS);
        uriMatcher.addURI(AUTHORITY, "words/phonics",CURRENT_WORDS_BY_LEVEL_WITH_PHONICS);
        uriMatcher.addURI(AUTHORITY, "tracing", TRACING);

        uriMatcher.addURI(AUTHORITY, "letters/all_lower", ALL_LOWERCASE_LETTERS);
        uriMatcher.addURI(AUTHORITY, "phonics/videos", CURRENT_PHONICS_FOR_VIDEOS);
        uriMatcher.addURI(AUTHORITY, "syllables/videos", CURRENT_SYLLABLES_FOR_VIDEOS);
        uriMatcher.addURI(AUTHORITY, "books/videos",BOOK_VIDEOS);
        uriMatcher.addURI(AUTHORITY, "books/update_level",UPDATE_BOOK_LEVEL);
        uriMatcher.addURI(AUTHORITY, "books/update_book_read", UPDATE_BOOK_READ);
        uriMatcher.addURI(AUTHORITY, "phonic_words/first_letter",CURRENT_PHONIC_LETTERS_WORDS_FIRST_LETTER);
        uriMatcher.addURI(AUTHORITY, "phonics/first",CURRENT_PHONIC_LETTERS_FIRST);
        uriMatcher.addURI(AUTHORITY, "phonic_words/last_letter",CURRENT_PHONIC_LETTERS_WORDS_LAST_LETTER);
        uriMatcher.addURI(AUTHORITY, "phonics/last",CURRENT_PHONIC_LETTERS_LAST);
        uriMatcher.addURI(AUTHORITY, "words/videos",CURRENT_WORDS_FOR_VIDEOS);
        uriMatcher.addURI(AUTHORITY, "sections/awards",SECTION_AWARDS);
        uriMatcher.addURI(AUTHORITY, "math_shapes",MATH_SHAPES);
        uriMatcher.addURI(AUTHORITY, "math_numbers",MATH_NUMBERS);
        uriMatcher.addURI(AUTHORITY, "math_operations",MATH_OPERATIONS);
        uriMatcher.addURI(AUTHORITY, "activity_user_attempts", ACTIVITY_USER_ATTEMPTS);
        uriMatcher.addURI(AUTHORITY, "check_wrongs", CHECK_WRONGS);
        uriMatcher.addURI(AUTHORITY, "default", DEFAULT);
        uriMatcher.addURI(AUTHORITY, "math_shapes/videos", CURRENT_SHAPES_FOR_VIDEOS);
        uriMatcher.addURI(AUTHORITY, "math_numbers/videos", CURRENT_NUMBERS_FOR_VIDEOS);
        uriMatcher.addURI(AUTHORITY, "math_operations/videos", CURRENT_OPERATIONS_FOR_VIDEOS);
        uriMatcher.addURI(AUTHORITY, "update/sections", UPDATE_DIFFERENT_SECTIONS);
        uriMatcher.addURI(AUTHORITY, "app_users_activity_variable_values/update_evv",
                ACTIVITY_USER_RW_UPDATE_EVV);

    }

    public AppProvider() {
        // TODO Auto-generated constructor stub
        mSetAll=false;
    }

    @Override
    public boolean onCreate() {

        mSetAll=false;
        mContext = getContext();

        S4K_DBHelper dbHelper = new S4K_DBHelper(getContext());
        mDatabase = dbHelper.getWritableDatabase();
       // mAppProcesses = new AppProviderProcesses();
        return false;
    }

    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    public SQLiteDatabase getDatabase(){
        return mDatabase;
    }

    @Override
    public Uri insert(@NonNull  Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull  Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Cursor query(@NonNull Uri uri,  String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        /*
        if (uriMatcher.match(uri) == ALL_USERS) {
            dbHelper = new S4K_DBHelper(getContext());
        }
        */

        Log.d(Constants.LOGCAT, " public Cursor query");
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String mRawSQL;
        Cursor mCursor;
        Motoli_Application mAppData =
                ((Motoli_Application) mContext.getApplicationContext());
        ArrayList<HashMap<String,String>> mUserGPL;
        switch (uriMatcher.match(uri)) {
            case DEFAULT:{
                mCursor=null;
                break;
            }
            case ALL_USERS: {
                /*
                 * ALL_USERS
                 * is called as a default in case more users are added
                 * at a later date
                 */
                queryBuilder.setTables("app_users");
                mCursor = queryBuilder.query(mDatabase, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case GET_MAX_LEVEL:
                mCursor = getMaxLevel(selectionArgs);
                break;
            case UPDATE_DIFFERENT_SECTIONS:
                mUserGPL = new ArrayList<>(getUserGPL(projection[0], "0"));
                mAppProcesses = new AppProviderProcesses(mContext,
                        mUserGPL,
                        mDatabase,
                        mAppData);
                mCursor =mAppProcesses.processesIndividualSections(projection);
                break;
            case RESET_LEVELS:
                mRawSQL="DELETE FROM app_users_activity_use_info " +
                        "WHERE app_user_id=" + selection + " ";
                mDatabase.execSQL(mRawSQL);
                mRawSQL="DELETE FROM app_users_variables_values " +
                        "WHERE app_user_id=" + selection + " ";
                mDatabase.execSQL(mRawSQL);
                mRawSQL="DELETE FROM app_users_activity_variable_values " +
                        "WHERE app_user_id=" + selection + " ";
                mDatabase.execSQL(mRawSQL);

                mRawSQL="DELETE FROM app_users_current_gpl " +
                        "WHERE app_user_id=" + selection + " ";
                mDatabase.execSQL(mRawSQL);

                mRawSQL="SELECT * FROM groups_phase";
                mCursor=mDatabase.rawQuery(mRawSQL, null);

                while(mCursor.moveToNext()){
                    int phase_id=mCursor.getInt(mCursor.getColumnIndex("phase_id"));
                    int group_id=mCursor.getInt(mCursor.getColumnIndex("group_id"));
                    int current_level=(phase_id==1) ? 0 :0;
                    if (group_id==2 && phase_id==1){
                        current_level=0;
                    }

                    if(group_id==2 && phase_id==3){
                        current_level=0;
                    }

                    if(group_id==3 && phase_id==1 ){
                        current_level=2;
                    }
                    if(group_id==3 && phase_id==2 ){
                        current_level=1;
                    }

                    if(group_id==4){
                        current_level=1;
                    }

                    if(group_id==5){
                        current_level=1;
                    }

                    if(group_id==7) {
                        current_level=0;
                    }
                    if(group_id==8){
                        current_level=0;
                    }
                    if( group_id==6 ){
                        current_level=0;
                    }
                    if(group_id==9){
                        current_level=1;
                    }
                    if(group_id!=1) {
                        String insertQuery = "INSERT INTO app_users_current_gpl (" +
                                "app_user_id, group_id, phase_id, app_user_current_level ) " +
                                "VALUES(" + selection + "," + group_id + "," +
                                phase_id + "," + current_level + ")";
                        mDatabase.execSQL(insertQuery);
                    }
                }

                mRawSQL = "SELECT * FROM activities LIMIT 1";
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            case ALL_MAX_LEVELS:
                mRawSQL="SELECT MAX(groups_phase_levels.level_number) AS max_level_number, " +
                        "groups_phase_levels.group_id, " +
                        "groups_phase_levels.phase_id, " +
                        "app_users_current_gpl.app_user_current_level " +
                        "FROM groups_phase_levels " +
                        "INNER JOIN app_users_current_gpl " +
                        "ON app_users_current_gpl.group_id=groups_phase_levels.group_id " +
                        "AND  app_users_current_gpl.phase_id=groups_phase_levels.phase_id " +
                        "AND app_users_current_gpl.app_user_id=" + selection + " " +
                        "GROUP BY groups_phase_levels.group_id , " +
                        "groups_phase_levels.phase_id " +
                        "ORDER BY groups_phase_levels.group_id ASC," +
                        "groups_phase_levels.phase_id ASC";
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            case INDIVIDUAL_USER:{
                /*
                 * INDIVIDUAL_USER
                 * currently only calls the one default user, again users are enabled
                 * in case the desire to add more then one user is brought up
                 */
                queryBuilder.setTables("app_users");
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere("_id=" + id);
                mCursor = queryBuilder.query(mDatabase, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case BOOKS_DECODABLE: {
                /*
                 * BOOKS_DECODABLE
                 * to gather all books decodable within availability defined
                 * in db thus no needed variables
                 */
                mCursor = booksDecodable();
                break;
            }
            case BOOKS_READ_ALOUD: {
                /*
                 * BOOKS_READ_ALOUD
                 * to gather all books read aloud within availability defined
                 * in db thus no needed variables
                 */
                mCursor = booksReadAloud(sortOrder);
                break;
            }
            case BOOK_PAGES_READ_ALOUD: {
                /*
                 * BOOK_PAGES_READ_ALOUD
                 * to gather all books read aloud pages within availability
                 * defined by variable selection coming from associated activity.
                 */
                mCursor = bookPagesReadAloud(selection);
                break;
            }
            case GROUPS_PHASE_LEVELS_UPDATE: {
                /*
                 * GROUPS_PHASE_LEVELS_UPDATE
                 * This is used to update appData.setAppUserCurrentGPL not the actual DB
                 * the data is pulled to update the global variable in order to make sure
                 * application is working correctly.
                 *
                 * Option 1 is to do an update for all group phase levels
                 * Option 2 is only for certain groups.
                 * This is done to streamline the timing it takes to go through this
                 * on Launch_Platform and Activities_Platform. It was causing
                 * an unneeded delay
                 */

                mRawSQL="SELECT * FROM settings WHERE settings_id=2";
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                if (mCursor.moveToFirst()) {
                    if(mCursor.getString(mCursor.getColumnIndex("setting_data")).equals("true")){
                        mSetAll=false;
                    }else{
                        mSetAll=false;
                    }
                }
                mUserGPL = new ArrayList<>(getUserGPL("0","0"));

                switch(sortOrder){
                    case "0":

                        mAppProcesses = new AppProviderProcesses(mContext,
                                mUserGPL,
                                mDatabase,
                                mAppData);
                        if(mSetAll) {

                            mCursor = mAppProcesses.groupPhaseLevelsUpdateAll(selection);
                        }else{
                            mCursor = mAppProcesses.groupPhaseLevelsUpdateAll(selection);
                        }
                        break;
                    case "1":
                        mUserGPL = new ArrayList<>(getUserGPL("0", "0"));
                        mAppProcesses = new AppProviderProcesses(mContext,
                                mUserGPL,
                                mDatabase,
                                mAppData);
                        mCursor = mAppProcesses.groupPhaseLevelsUpdate( selection);
                        break;
                    case "2":
                        mCursor = mAppProcesses.groupPhaseLevels(selection);
                        break;
                    default:
                        mCursor = mAppProcesses.groupPhaseLevels(selection);
                        break;
                }
                break;
            }
            case INDIVIDUAL_GROUP_PHASE_SECTIONS: {
                /*
                 * INDIVIDUAL_GROUP_PHASE_SECTIONS
                 * This is used in Launch_Platform in order to pull and create
                 * the correct section to place on the page
                 */


                Log.d(Constants.LOGCAT, "start INDIVIDUAL_GROUP_PHASE_SECTIONS");
                if (selection == null) {
                    selection = "0";
                }
                String mLetterPhaseID = "1";
                int mMaxLevel = 0;
                int mCurrentLevel = 0;
                mRawSQL = "SELECT MAX(level_number) AS max_level " +
                        "FROM variable_phase_levels " +
                        "WHERE  group_id=2 AND phase_id=1 " +
                        "LIMIT 1";
                mCursor = mDatabase.rawQuery(mRawSQL, null);

                if (mCursor.moveToFirst()) {
                    do {
                        mMaxLevel = mCursor.getInt(mCursor.getColumnIndex("max_level"));
                    } while (mCursor.moveToNext());
                }

                mRawSQL = "SELECT app_user_current_level " +
                        "FROM app_users_current_gpl " +
                        "WHERE group_id=2 AND phase_id=1 AND app_user_id=" + selection + " " +
                        "LIMIT 1";
                mCursor = mDatabase.rawQuery(mRawSQL, null);

                if (mCursor.moveToFirst()) {
                    do {
                        mCurrentLevel = mCursor.getInt(mCursor.getColumnIndex("app_user_current_level"));
                    } while (mCursor.moveToNext());
                }

                if (mCurrentLevel > mMaxLevel) {
                    mLetterPhaseID = "2";
                }


                mRawSQL = "SELECT  groups_phase_sections.group_id, " +
                        //"MAX(variable_phase_levels.level_number) as max_level, "+
                        "MIN(groups_phase_sections.phase_id) AS lowest_phase_id, " +
                        "groups_phase_sections.section_id,  sections.section_name, " +
                        "sections.section_image, " +
                        "sections.section_order, app_users_current_gpl.app_user_current_level, " +
                        "COUNT(DISTINCT groups_phase_sections_activities.activity_id) " +
                        "AS activity_count, " +
                        "COUNT(DISTINCT  app_users_activity_variable_values.variable_id) " +
                        "AS variable_count," +
                        "CASE " +

                        "WHEN groups_phase_sections.group_id=5 " +
                        "AND groups_phase_sections.section_id=14 " +
                        "THEN  ( SELECT COUNT(*) " +
                        "FROM groups_phase_levels " +
                        "INNER JOIN app_users_current_gpl " +
                        "ON app_users_current_gpl.group_id = groups_phase_levels.group_id " +
                        "WHERE ( groups_phase_levels.group_id = 2 " +
                        "AND  groups_phase_levels.phase_id = 1 " +
                        "AND  app_users_current_gpl.app_user_current_level  > 4 " +
                        "AND groups_phase_levels.level_number " +
                        "< app_users_current_gpl.app_user_current_level) " +
                        "OR ((groups_phase_levels.group_id != 2 " +
                        "AND  groups_phase_levels.phase_id != 1) " +
                        "AND groups_phase_levels.group_id = app_users_current_gpl.group_id " +
                        "AND groups_phase_levels.phase_id = app_users_current_gpl.phase_id " +
                        "AND groups_phase_levels.level_number " +
                        "< app_users_current_gpl.app_user_current_level) )" +
                        /* +
                        "SELECT COUNT(books.book_id)  " +
                        "FROM books  " +
                        "INNER JOIN groups_phase_levels " +
                        "ON groups_phase_levels.gpl_id=books.associated_levels " +
                        "INNER JOIN app_users_current_gpl " +
                        "ON app_users_current_gpl.group_id=groups_phase_levels.group_id " +
                        "AND app_users_current_gpl.phase_id=groups_phase_levels.phase_id   " +
                        "WHERE books.book_type=0 " +
                        "AND groups_phase_levels.level_number" +
                        "<=app_users_current_gpl.app_user_current_level " +
                        "ORDER BY groups_phase_levels.group_id ASC, " +
                        "groups_phase_levels.phase_id ASC, " +
                        "books.book_title ASC   )   " + */

                        "WHEN groups_phase_sections.group_id=5 " +
                        "AND groups_phase_sections.section_id=13 " +
                        "THEN  (SELECT COUNT(books.book_id) " +
                        "FROM books  " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.group_id=2  " +
                        "AND variable_phase_levels.phase_id=3  " +
                        "AND variable_phase_levels.level_number=books.decodable_levels " +
                        "INNER JOIN app_users_current_gpl " +
                        "ON app_users_current_gpl.group_id=2 " +
                        "AND app_users_current_gpl.phase_id=3 " +
                        "WHERE books.book_type=1  " +
                        "AND variable_phase_levels.level_number" +
                        "<=app_users_current_gpl.app_user_current_level   " +
                        "ORDER BY variable_phase_levels.level_number ASC, " +
                        "books.book_title ASC )   " +
                        "ELSE -1 " +
                        "END " +
                        "AS usable_section " +
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
                        "WHERE  app_users_current_gpl.app_user_id=" + selection + " " +
                        "AND ((groups_phase_sections.group_id=2 " +
                        "AND groups_phase_sections.phase_id=" + mLetterPhaseID + ") " +
                        "OR (groups_phase_sections.group_id!=2) OR (groups_phase_sections.group_id=2 " +
                        "AND groups_phase_sections.phase_id=3)) " +
                        "GROUP BY groups_phase_sections.section_id " +
                        "ORDER BY  groups_phase_sections.group_id ASC, " +
                        "sections.section_order ASC, groups_phase_sections.phase_id ASC";


                mCursor = mDatabase.rawQuery(mRawSQL, null);
                Log.d(Constants.LOGCAT, "end INDIVIDUAL_GROUP_PHASE_SECTIONS");

                break;
            }
            case ALL_LOWERCASE_LETTERS: {
                /*
                 * ALL_LOWERCASE_LETTERS
                 * This is used in all the two different WD activities (WD05, WD06)
                 * This is used because I need all of the possible letters for
                 * use to find first letter of word WD05
                 * or cut word up into individual letters WD06
                 */
                mRawSQL = "SELECT letters.* " +
                        "FROM letters " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=letters.letter_id " +
                        "WHERE variable_phase_levels.group_id=2 " +
                        "AND variable_phase_levels.phase_id=1";
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case SELECTED_ACTIVITIES: {
                /*
                 * SELECTED_ACTIVITIES
                 * used in Activities_Platform and Launch_Platform
                 * It is used to get correct activities for each section
                 * however in Launch_Platform it is used to just get on activity_id
                 * for a section that is only one activity
                 */



                mRawSQL = "SELECT MIN(groups_phase_sections_activities.phase_id) " +
                        "AS minphase_id, ";

                if(projection[1].equals("8")){
                    //mRawSQL+="COUNT(math_operations.math_operations_id) AS mo_count,";
                    mRawSQL+="math_operations_levels.max_addition_number_one " +
                            "AS allow_addition, " +
                            "math_operations_levels.max_substitution_number_one " +
                            "AS allow_substitution, " +
                            "math_operations_levels.max_multiplication_number_one " +
                            "AS allow_multiplication, ";
                }
              /*   */
                mRawSQL+="activities.*," +
                        "groups_phase_sections_activities.* " +
                        "FROM groups_phase_sections_activities " +
                        "INNER JOIN activities " +
                        "ON activities.activity_id=groups_phase_sections_activities.activity_id ";
                if(projection[1].equals("8")){
                    mRawSQL+="INNER JOIN math_operations_levels " +
                            "ON math_operations_levels.math_equation_levels="+projection[0]+" ";


                    /*
                    mRawSQL+="LEFT JOIN math_operations " +
                            "ON math_operations.math_equation_levels<=1 " +
                            "AND  " +
                            "CASE WHEN activities.activity_id=65 " +
                            "THEN  math_operations.operand=3 " +
                            "ELSE math_operations.operand=1 END ";
                            */
                }

                mRawSQL+="WHERE " + selection + " " +
                        "GROUP BY activities.activity_id " +
                        "ORDER BY " + sortOrder;

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE: {
                /*
                 * ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE
                 * This is used in Activities_Platform
                 * It is used to get list of completion of levels for all groups and sections
                 */

                mRawSQL = "SELECT activities.activity_id, " +
                        "MIN(app_users_activity_variable_values.number_correct_in_a_row) AS " +
                        "number_correct_in_a_row " +
                        "FROM activities " +
                        "INNER JOIN groups_phase_sections_activities " +
                        "ON groups_phase_sections_activities.activity_id" +
                        "=activities.activity_id  " +
                        "JOIN app_users_activity_variable_values " +
                        "ON app_users_activity_variable_values.activity_id" +
                        "=activities.activity_id " +
                        " WHERE app_users_activity_variable_values.level_number " +
                        "= (SELECT  MAX(auavv.level_number) " +
                        "FROM app_users_activity_variable_values auavv " +
                        "WHERE auavv.activity_id=activities.activity_id )" +
                        "GROUP BY app_users_activity_variable_values.activity_id " +
                        "ORDER BY app_users_activity_variable_values.group_id ASC, " +
                        "app_users_activity_variable_values.phase_id ASC, " +
                        "activities.activity_order ASC ";
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case GUESS_WORDS:
            case CURRENT_WORDS_BY_LEVEL: {
                /*
                 * CURRENT_WORDS_BY_LEVEL
                 * This is used in all of the WD activities in order to get the
                 * words for that activity based on current level in words
                 */

                mCursor = activityCurrentWordWRW(projection, selection);
                break;
            }
            case ACTIVITY_CURRENT_LETTERS: {
                /*
                 * ACTIVITY_CURRENT_LETTERS
                 * This is used in LT05 and VL02
                 * It is used for videos in VL02
                 * It is used for the piano in LT05
                 * All are based upon the current level for letters
                 */

                if (projection != null) {
                    mRawSQL = "SELECT app_user_current_level " +
                            "FROM app_users_current_gpl " +
                            "WHERE group_id=" + projection[2] + " " +
                            "AND phase_id=" + projection[3] + " " +
                            "AND app_user_id=" + projection[1];
                    mCursor = mDatabase.rawQuery(mRawSQL, null);
                    int mCurrentLevel = 0;
                    if (mCursor.moveToFirst()) {
                        mCurrentLevel = mCursor.getInt(mCursor.getColumnIndex("app_user_current_level"));
                    }

                    selection = "variable_phase_levels.group_id=" + projection[2]
                            + " AND variable_phase_levels.phase_id=" + projection[3]
                            + " AND variable_phase_levels.level_number<=" + String.valueOf(mCurrentLevel);
                }

                mRawSQL = "SELECT letters.*, " +
                        "gpl.* " +
                        "FROM letters " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=letters.letter_id  " +
                        "INNER JOIN groups_phase_levels AS gpl " +
                        "ON variable_phase_levels.group_id=gpl.group_id " +
                        "AND variable_phase_levels.phase_id=gpl.phase_id " +
                        "AND variable_phase_levels.level_number =gpl.level_number " +
                        "WHERE " + selection + " " +
                        "ORDER BY letters.letter_text ASC";

                mCursor = mDatabase.rawQuery(mRawSQL, null);

                break;
            }
            case CURRENT_SYLLABLES_FOR_VIDEOS: {
                /*
                 * CURRENT_SYLLABLES_FOR_VIDEOS
                 * Used is VL04 for organizing videos for syllables based on current level
                 */

                mRawSQL = "SELECT app_users_current_gpl.app_user_current_level, " +
                        "gpl.* " +
                        "FROM syllables " +
                        "INNER JOIN app_users_current_gpl " +
                        "ON app_users_current_gpl.group_id=gpl.group_id  " +
                        "AND app_users_current_gpl.phase_id=gpl.phase_id " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=syllables.syllables_id  " +
                        "INNER JOIN groups_phase_levels AS gpl " +
                        "ON variable_phase_levels.group_id=gpl.group_id " +
                        "AND variable_phase_levels.phase_id=gpl.phase_id " +
                        "AND variable_phase_levels.level_number =gpl.level_number " +
                        "WHERE " + selection + " " +
                        "GROUP BY variable_phase_levels.level_number " +
                        "ORDER BY gpl.level_number ASC, " +
                        "gpl.group_phase_level_name ASC, " +
                        "syllables.syllables_text ASC";

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case CURRENT_PHONICS_FOR_VIDEOS: {
                /*
                 * CURRENT_PHONICS_FOR_VIDEOS
                 * Used is VL03 for organizing videos for phonics based on current level
                 */
                mRawSQL = "SELECT phonics.*, " +
                        "gpl.* " +
                        "FROM phonics " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=phonics.phonic_id  " +
                        "INNER JOIN groups_phase_levels AS gpl " +
                        "ON variable_phase_levels.group_id=gpl.group_id " +
                        "AND variable_phase_levels.phase_id=gpl.phase_id " +
                        "AND variable_phase_levels.level_number =gpl.level_number " +
                        "WHERE " + selection + " " +
                        "ORDER BY variable_phase_levels.level_number ASC, " +
                        "phonics.phonic_text ASC";

                mCursor = mDatabase.rawQuery(mRawSQL, null);

                break;
            }
            case CURRENT_WORDS_FOR_VIDEOS: {
                /*
                 * CURRENT_WORDS_FOR_VIDEOS
                 * Used is VL05 for organizing videos for words based on current level
                 */
                mRawSQL = "SELECT phonics.phonic_id AS variable_id, " +
                        "phonics.phonic_text AS variable_text, " +
                        "gpl.level_color, " +
                        "gpl.level_video, " +
                        "gpl.level_number " +
                        "FROM phonics " +
                        "INNER JOIN variable_phase_levels AS phonic_levels " +
                        "ON phonic_levels.group_id=2 " +
                        "AND phonic_levels.phase_id=3 " +
                        "AND phonic_levels.variable_id=phonics.phonic_id  " +
                        "INNER JOIN groups_phase_levels AS gpl " +
                        "ON gpl.group_id=4 AND gpl.phase_id=1 " +
                        "AND gpl.level_number=phonic_levels.level_number " +
                        "WHERE " + selection + " " +
                        "GROUP BY phonics.phonic_id " +
                        "ORDER BY gpl.level_number";

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case CURRENT_PHONIC_LETTERS: {
                /*
                 * CURRENT_PHONIC_LETTERS
                 * Is used in SD01,SD04, SD05,SD06,SD07
                 *
                 */

                if (!projection[5].equals("999")) {
                    mUserGPL = getUserGPL("0","0");
                    AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                            mUserGPL,
                            mDatabase);
                    mUpdateGroups.updatePhonicsProcess(projection);

                }
                mRawSQL = "SELECT variable_phase_levels.level_number," +
                        "app_users_variables_values.variable_id, " +
                        "app_users_variables_values.number_correct, " +
                        "app_users_variables_values.number_correct_in_a_row, " +
                        "app_users_variables_values.number_incorrect, " +
                        "phonics.* " +
                        "FROM phonics " +
                        "INNER JOIN variable_type_relations " +
                        "ON variable_type_relations.variable_id=phonics.phonic_id " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=phonics.phonic_id   " +
                        "LEFT JOIN app_users_variables_values " +
                        "ON app_users_variables_values.variable_id=phonics.phonic_id " +
                        "AND app_users_variables_values.activity_id=" + projection[0] + " " +
                        "AND app_users_variables_values.app_user_id=" + projection[1] + " " +
                        "WHERE " + selection + " ";


                if (sortOrder != null && !sortOrder.equals("")) {
                    mRawSQL += "ORDER BY " + sortOrder + " ";
                } else {
                    mRawSQL += "ORDER BY app_users_variables_values.number_correct_in_a_row ASC, " +
                            "app_users_variables_values.number_incorrect DESC, " +
                            "RANDOM() ";
                }

                if (!projection[0].equals("35")) {
                    mRawSQL += "LIMIT 20";
                }

                mCursor = mDatabase.rawQuery(mRawSQL, null);

                break;
            }
            case CURRENT_PHONIC_LETTERS_FIRST: {
                /*
                 * CURRENT_PHONIC_LETTERS_FIRST
                 * This is used in SD02 to gather phonics and phonic words that have related
                 * first letters in words
                 */
                if (!projection[5].equals("999")) {
                    mUserGPL = getUserGPL("0","0");
                    AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                            mUserGPL,
                            mDatabase);
                    mUpdateGroups.updatePhonicsProcess(projection);
                    mUpdateGroups.updatePhonicsProcessOLD(projection,selection);
                }
                mRawSQL = "SELECT  " +
                        "(SELECT phonic_word_id " +
                        "FROM phonic_words " +
                        "WHERE phonic_words.phonic_word_first_id=phonics.phonic_id " +
                        "ORDER BY RANDOM()) " +
                        "AS phonic_word_id, " +
                        "variable_phase_levels.level_number," +
                        "app_users_variables_values.variable_id, " +
                        "app_users_variables_values.number_correct, " +
                        "app_users_variables_values.number_correct_in_a_row, " +
                        "app_users_variables_values.number_incorrect, " +
                        "phonics.* " +
                        "FROM phonics " +
                        "INNER JOIN variable_type_relations " +
                        "ON variable_type_relations.variable_id=phonics.phonic_id " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=phonics.phonic_id   " +
                        "LEFT JOIN app_users_variables_values " +
                        "ON app_users_variables_values.variable_id=phonics.phonic_id " +
                        "AND app_users_variables_values.activity_id=" + projection[0] + " " +
                        "AND app_users_variables_values.app_user_id=" + projection[1] + " " +
                        "WHERE " + selection + " " +
                        "AND phonic_word_id IS NOT NULL ";


                if (sortOrder != null && !sortOrder.equals("")) {
                    mRawSQL += "ORDER BY " + sortOrder + " ";
                } else {
                    mRawSQL += "ORDER BY app_users_variables_values.number_correct_in_a_row ASC, " +
                            "app_users_variables_values.number_incorrect DESC, " +
                            "RANDOM() ";
                }

                mRawSQL += "LIMIT 20";

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case CURRENT_PHONIC_LETTERS_LAST: {
                /*
                 * CURRENT_PHONIC_LETTERS_LAST
                 * This is used in SD03 to gather phonics and phonic words that have related
                 * last letters in words
                 */
                if (!projection[5].equals("999")) {
                    mUserGPL = getUserGPL("0","0");
                    AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                            mUserGPL,
                            mDatabase);
                    mUpdateGroups.updatePhonicsProcess(projection);
                    mUpdateGroups.updatePhonicsProcessOLD(projection,selection);

                }
                mRawSQL = "SELECT  " +
                        "(SELECT phonic_word_id " +
                        "FROM phonic_words " +
                        "WHERE phonic_words.phonic_word_last_id=phonics.phonic_id " +
                        "ORDER BY RANDOM()) " +
                        "AS phonic_word_id, " +
                        "variable_phase_levels.level_number," +
                        "app_users_variables_values.variable_id, " +
                        "app_users_variables_values.number_correct, " +
                        "app_users_variables_values.number_correct_in_a_row, " +
                        "app_users_variables_values.number_incorrect, " +
                        "phonics.* " +
                        "FROM phonics " +
                        "INNER JOIN variable_type_relations " +
                        "ON variable_type_relations.variable_id=phonics.phonic_id " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=phonics.phonic_id   " +
                        "LEFT JOIN app_users_variables_values " +
                        "ON app_users_variables_values.variable_id=phonics.phonic_id " +
                        "AND app_users_variables_values.activity_id=" + projection[0] + " " +
                        "AND app_users_variables_values.app_user_id=" + projection[1] + " " +
                        "WHERE " + selection + " " +
                        "AND phonic_word_id IS NOT NULL ";


                if (sortOrder != null && !sortOrder.equals("")) {
                    mRawSQL += "ORDER BY " + sortOrder + " ";
                } else {
                    mRawSQL += "ORDER BY app_users_variables_values.number_correct_in_a_row ASC, " +
                            "app_users_variables_values.number_incorrect DESC, " +
                            "RANDOM() ";
                }

                mRawSQL += "LIMIT 20";

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case ORDERED_PHONICS: {
                /*
                 * ORDERED_PHONICS
                 * This is used in SD01 and WD05
                 * It is used to match phonic with word in WD
                 * Is used to match phonic with random phonics in SD
                 */
                mRawSQL = "SELECT phonics.*, " +
                        "variable_phase_levels.* " +
                        "FROM phonics " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=phonics.phonic_id " +
                        "AND variable_phase_levels.group_id=2 " +
                        "AND variable_phase_levels.phase_id=3 " +
                        "WHERE " + selection + " " +
                        "GROUP BY phonics.phonic_text " +
                        "ORDER BY " + sortOrder + ", " +
                        "LENGTH(phonics.phonic_text) DESC, " +
                        "RANDOM() ";
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case SECTION_AWARDS: {
                /*
                 * SECTION_AWARDS
                 * This is used in Launch_Platform
                 * to help set up the awards section
                 * THIS WILL MOVE TO A DIFFERENT FRAGMENT
                 */
                mRawSQL = "SELECT " +
                        "(SELECT MAX(app_users_current_gpl.app_user_current_level) " +
                        "FROM app_users_current_gpl " +
                        "WHERE app_users_current_gpl.group_id=groups_phase.group_id " +
                        "AND app_users_current_gpl.phase_id=groups_phase.phase_id " +
                        "AND app_users_current_gpl.app_user_id = " + selection +") " +
                        "AS current_level_number," +
                        "(SELECT MAX(variable_phase_levels.level_number) " +
                        "FROM variable_phase_levels " +
                        "WHERE variable_phase_levels.group_id=groups_phase.group_id " +
                        "AND variable_phase_levels.phase_id=groups_phase.phase_id ) " +
                        "AS max_level_number, " +
                        "(SELECT " +
                        "SUM(app_users_activity_variable_values.number_correct_in_a_row)  " +
                        "FROM app_users_activity_variable_values  " +
                        "WHERE  app_users_activity_variable_values.group_id" +
                        "=groups_phase.group_id " +
                        "AND app_users_activity_variable_values.phase_id" +
                        "=groups_phase.phase_id " +
                        "AND app_users_activity_variable_values.app_user_id=" + selection +") " +
                        "AS correct_count,   " +
                        "(SELECT MIN(app_users_activity_variable_values.number_correct_in_a_row)  " +
                        "FROM app_users_activity_variable_values  " +
                        "WHERE  app_users_activity_variable_values.group_id" +
                        "=groups_phase.group_id " +
                        "AND app_users_activity_variable_values.phase_id" +
                        "=groups_phase.phase_id " +
                        "AND app_users_activity_variable_values.app_user_id=0) " +
                        "AS max_correct_count, " +
                        "(SELECT COUNT(variable_phase_levels.variable_phase_levels_id)" +
                        " FROM variable_phase_levels  " +
                        "WHERE variable_phase_levels.group_id=groups_phase.group_id " +
                        "AND variable_phase_levels.phase_id=groups_phase.phase_id " +
                        "AND variable_phase_levels.level_number!=-2 ) " +
                        "AS variable_number, " +
                        "groups_phase.*, " +
                        "groups_phase_sections.section_id " +
                        "FROM  groups_phase   " +
                        "INNER JOIN groups_phase_sections " +
                        "ON  groups_phase_sections.group_id=groups_phase.group_id " +
                        "AND groups_phase_sections.phase_id=groups_phase.phase_id " +
                        "WHERE groups_phase.group_id!=5 AND groups_phase.group_id!=9 " +
                        "AND groups_phase.group_id!=1 " +
                        "GROUP BY groups_phase.groups_phase_id";

                /*
                mRawSQL = "SELECT " +
                        "(SELECT MAX(app_users_current_gpl.app_user_current_level) " +
                        "FROM app_users_current_gpl " +
                        "WHERE app_users_current_gpl.group_id=groups_phase.group_id " +
                        "AND app_users_current_gpl.phase_id=groups_phase.phase_id " +
                        "AND app_users_current_gpl.app_user_id = " + selection +") " +
                        "AS current_level_number," +
                        "(SELECT MAX(variable_phase_levels.level_number) " +
                        "FROM variable_phase_levels " +
                        "WHERE variable_phase_levels.group_id=groups_phase.group_id " +
                        "AND variable_phase_levels.phase_id=groups_phase.phase_id ) " +
                        "AS max_level_number, " +
                        "(SELECT " +
                        "SUM(app_users_activity_variable_values.number_correct_in_a_row)  " +
                        "FROM app_users_activity_variable_values  " +
                        "WHERE  app_users_activity_variable_values.type_id" +
                        "=groups_phase_type_relations.type_id  " +
                        "AND app_users_activity_variable_values.app_user_id=" + selection +") " +
                        "AS correct_count,   " +
                        "(SELECT MIN(app_users_activity_variable_values.number_correct_in_a_row)  " +
                        "FROM app_users_activity_variable_values  " +
                        "WHERE  app_users_activity_variable_values.type_id" +
                        "=groups_phase_type_relations.type_id " +
                        "AND app_users_activity_variable_values.app_user_id=0) " +
                        "AS max_correct_count, " +
                        "(SELECT COUNT(variable_phase_levels.variable_phase_levels_id)" +
                        " FROM variable_phase_levels  " +
                        "WHERE variable_phase_levels.group_id=groups_phase.group_id " +
                        "AND variable_phase_levels.phase_id=groups_phase.phase_id " +
                        "AND variable_phase_levels.level_number!=-2 ) " +
                        "AS variable_number, " +
                        "groups_phase.*, " +
                        "groups_phase_type_relations.type_id, " +
                        "groups_phase_sections.section_id " +
                        "FROM  groups_phase   " +
                        "INNER JOIN groups_phase_type_relations " +
                        "ON groups_phase_type_relations.group_id=groups_phase.group_id " +
                        "AND groups_phase_type_relations.phase_id=groups_phase.phase_id    " +
                        "INNER JOIN groups_phase_sections " +
                        "ON  groups_phase_sections.group_id=groups_phase.group_id " +
                        "AND groups_phase_sections.phase_id=groups_phase.phase_id " +
                        "WHERE groups_phase.group_id!=5 AND groups_phase.group_id!=9 " +
                        "AND groups_phase.group_id!=1 " +
                        "GROUP BY groups_phase.groups_phase_id";
*/
                mCursor = mDatabase.rawQuery(mRawSQL, null);

                break;
            }
            case MATCHING_PHONIC_LETTERS_WORDS: {
                /*
                 * MATCHING_PHONIC_LETTERS_WORDS
                 * This is used in SD06
                 * Used to find associated phonic words
                 * with either matching first or last phonic
                 * Random sort to not have redundancy.
                 */
                mRawSQL = "SELECT * " +
                        "FROM phonic_words " +
                        "WHERE phonic_words.phonic_word_first_id=" +projection[0]+" "+
                        "OR phonic_words.phonic_word_last_id=" +projection[0]+" "+
                        "ORDER BY RANDOM()";
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case CURRENT_PHONIC_LETTERS_WORDS_FIRST_LETTER: {
                /*
                 * CURRENT_PHONIC_LETTERS_WORDS_FIRST_LETTER
                 * Used in SD02
                 * Used to get phonic_word first phonic in first part of word
                 * And get other phonic_words that are not the same as single used for
                 * random words
                 */
                mRawSQL = "SELECT phonics.*,  " +
                        "phonic_words.* " +
                        "FROM phonic_words  " +
                        "LEFT JOIN phonics  " +
                        "WHERE  " +
                        " " + selection + " " +
                        "ORDER BY RANDOM() " +
                        "LIMIT " + projection[0];
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case CURRENT_PHONIC_LETTERS_WORDS_LAST_LETTER: {
                /*
                 * CURRENT_PHONIC_LETTERS_WORDS_LAST_LETTER
                 * Used in SD03
                 * Used to get phonic_word last phonic in first part of word
                 * And get other phonic_words that are not the same as single used for
                 * random words
                 */
                mRawSQL = "SELECT phonics.*,  " +
                        "phonic_words.* " +
                        "FROM phonic_words  " +
                        "LEFT JOIN phonics  " +
                        "WHERE  " + selection + " " +
                        "ORDER BY RANDOM() " +
                        "LIMIT " + projection[0];
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case CURRENT_SYLLABLE_WORDS: {
                /*
                 * CURRENT_SYLLABLE_WORDS
                 * This is used in all of the RS activities to get
                 * the current syllable_words based upon their level
                 */
                mRawSQL = "SELECT syllable_words.* FROM syllable_words WHERE " + selection;

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case CURRENT_ALL_SYLLABLES: {
                /*
                 * CURRENT_ALL_SYLLABLES
                 * This is used in RS04 to get current syllables associated
                 * the piano uses this based upon current level
                 */

                mRawSQL = "SELECT app_users_current_gpl.app_user_current_level, " +
                        "variable_phase_levels.level_number," +
                        "app_users_activity_variable_values.variable_id, " +
                        "app_users_activity_variable_values.number_correct, " +
                        "app_users_activity_variable_values.number_correct_in_a_row, " +
                        "app_users_activity_variable_values.number_incorrect," +
                        "syllables.* " +
                        "FROM syllables " +
                        " LEFT JOIN app_users_current_gpl " +
                        "ON app_users_current_gpl.group_id=3 " +
                        "AND app_users_current_gpl.phase_id=2 " +
                        "INNER JOIN variable_type_relations " +
                        "ON variable_type_relations.variable_id=syllables.syllables_id " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=syllables.syllables_id   " +
                        "LEFT JOIN app_users_activity_variable_values " +
                        "ON app_users_activity_variable_values.variable_id=syllables.syllables_id " +
                        "AND app_users_activity_variable_values.app_user_id=" + projection[1] + " " +
                        "WHERE " + selection + " " +
                        " GROUP BY syllables.syllables_id ";


                if (sortOrder != null && !sortOrder.equals("")) {
                    mRawSQL += "ORDER BY " + sortOrder;
                } else {
                    mRawSQL += "ORDER BY app_users_activity_variable_values.number_correct_in_a_row ASC, " +
                            "app_users_activity_variable_values.number_incorrect DESC, " +
                            "RANDOM()";
                }

                mCursor = mDatabase.rawQuery(mRawSQL, null);


                break;
            }
            case CURRENT_SYLLABLES: {
                /*
                 * CURRENT_SYLLABLES
                 * This is used in RS01, RS02, RS03
                 * It is used to gather current syllables based upon current level for
                 * use in activity.
                 */

                mRawSQL = "SELECT variable_phase_levels.level_number, " +
                        "COUNT(sw1.syllable_word_first_id) AS first_count, " +
                        "COUNT(sw2.syllable_word_last_id) AS last_count, " +
                        "app_users_activity_variable_values.auavv_id, " +
                        "app_users_activity_variable_values.activity_id, " +
                        "app_users_activity_variable_values.number_correct, " +
                        "app_users_activity_variable_values.number_correct_in_a_row, " +
                        "app_users_activity_variable_values.number_incorrect, " +
                        "syllables.*, " +
                        "(SELECT app_users_current_gpl.app_user_current_level "+
                        "FROM app_users_current_gpl " +
                        "WHERE app_users_current_gpl.group_id=3 " +
                        "AND app_users_current_gpl.phase_id=2) AS app_user_current_level " +
                        "FROM syllables " +
                        "INNER JOIN variable_phase_levels  " +
                        "ON variable_phase_levels.variable_id=syllables.syllables_id " +
                        "AND variable_phase_levels.group_id=3 " +
                        "AND  variable_phase_levels.phase_id=2 " +
                        "LEFT JOIN app_users_activity_variable_values " +
                        "ON app_users_activity_variable_values.variable_id=syllables.syllables_id  " +
                        "AND app_users_activity_variable_values.activity_id=" + projection[0] + " " +
                        "AND app_users_activity_variable_values.app_user_id=" + projection[1] + " " +
                        "LEFT JOIN syllable_words AS sw1 " +
                        "ON sw1.syllable_word_first_id=syllables.syllables_id  " +
                        "LEFT JOIN syllable_words AS sw2 " +
                        "ON sw2.syllable_word_last_id=syllables.syllables_id " +
                        "WHERE  variable_phase_levels.level_number<=" +
                        "(SELECT app_users_current_gpl.app_user_current_level " +
                        "FROM app_users_current_gpl " +
                        "WHERE app_users_current_gpl.group_id=3 " +
                        "AND app_users_current_gpl.phase_id=2)  " +
                        selection + " " +
                        "GROUP BY syllables.syllables_id " +
                        "ORDER BY app_users_activity_variable_values.number_correct_in_a_row ASC, " +
                        "RANDOM(),variable_phase_levels.level_number DESC,  " +
                        "syllables.syllables_text ASC";

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case ACTIVITY_CURRENT_LETTERS_WRW: {
                /*
                 * ACTIVITY_CURRENT_LETTERS_WRW
                 * Is used in LT01, LT02, LT03, LT04, LT06
                 * This gathers the current letters used for rounds
                 * based upon current level
                 */
                mCursor = activityCurrentLettersWRW(projection, selection);

                break;
            }
            case CURRENT_WORD_SYLLABLE_VALUES: {
                /*
                 * CURRENT_WORD_SYLLABLE_VALUES
                 * Used in CS01, CS02, and CS03
                 * This gathers the current words with syllable values
                 * Based upon the current_level in syllables
                 */

                mCursor = currentWordSyllableValues(projection, selection, sortOrder);

                break;
            }
            case CURRENT_WORDS_BY_LEVEL_WITH_PHONICS: {
                /*
                 * CURRENT_WORDS_BY_LEVEL_WITH_PHONICS
                 * Is used in WD05
                 * Used to get words based uponAppProvider
                 */
                mCursor = activityCurrentWordLevelPhonics(projection, selection);

                break;
            }
            case BOOK_VIDEOS:
                mCursor = bookVideos();
                break;
            case MATH_SHAPES:
                if ((projection[0].equals("55") || projection[0].equals("56") ||
                        projection[0].equals("57") || projection[0].equals("58")) &&
                        projection[6].equals("0")) {
                    mCursor = currentMathShapesFourIn(projection, selection);
                }else if (projection[6].equals("11")){
                    mCursor = currentMathShapesSH02Extra(projection,selection);
                } else if (projection[6].equals("2")) {
                    mCursor = getAllowedShapes();
                } else if (projection[6].equals("3")) {
                    mCursor = getOnePianoShape(selection);
                }  else if (projection[6].equals("4")) {
                    mCursor = currentMathShapeUppers(projection);
                }  else if (projection[6].equals("5")) {
                    mCursor = currentMathShapesSH06(projection, selection, selectionArgs);
                }else {
                    mCursor = currentMathShapes(projection, selection, sortOrder);
                }
                break;
            case MATH_NUMBERS:
                switch(projection[6]){
                    case "1":
                        mCursor = getAllMathNumbers(projection, selection, sortOrder);
                        break;
                    case "3":
                        mCursor = getAllMathNumbersForPiano();
                        break;
                    case "9":
                        mCursor = getExtraThreeMathNumbers(projection, selection, sortOrder);
                        break;
                    default:
                        mCursor = getMathNumbers(projection, selection);
                        break;
                }
                break;
            case MATH_OPERATIONS:{
                mCursor = getMathOperations(projection,selection);
                break;
            }
            case CHECK_WRONGS:{
                mCursor = checkWrongs(selectionArgs);
                break;
            }
            case TRACING:{
                mCursor = getTracingList(projection,selection,sortOrder);
                break;
            }
            case CURRENT_SHAPES_FOR_VIDEOS:{
                /*
                 * CURRENT_SHAPES_FOR_VIDEOS
                 * Used is VL07 for organizing videos for shapes based on current level
                 */
                mRawSQL = "SELECT * " +
                        "FROM groups_phase_levels " +
                        "WHERE group_id=6 " +
                        "AND phase_id=1 " +
                        "AND level_number<="+selection + " " +
                        "ORDER BY level_number ASC";

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case CURRENT_NUMBERS_FOR_VIDEOS:{
                /*
                 * CURRENT_NUMBERS_FOR_VIDEOS
                 * Used is VL08 for organizing videos for numbers based on current level
                 */
                mRawSQL = "SELECT * " +
                        "FROM groups_phase_levels " +
                        "WHERE group_id=7 " +
                        "AND phase_id=1 " +
                        "AND level_number<="+selection + " " +
                        "ORDER BY level_number ASC";

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case CURRENT_OPERATIONS_FOR_VIDEOS:{
                /*
                 * CURRENT_OPERATIONS_FOR_VIDEOS
                 * Used is VL09 for organizing videos for math operations based on current level
                 */
                mRawSQL = "SELECT * " +
                        "FROM groups_phase_levels " +
                        "WHERE group_id=8 " +
                        "AND phase_id=1 " +
                        "AND level_number<="+selection + " " +
                        "ORDER BY level_number ASC";

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }


        Log.d(Constants.LOGCAT, "END of AppProvider");
//        mDatabase.close();
        return mCursor;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private Cursor getTracingList(String[] mData, String mWhere, String mOrderBy){
        Log.d(Constants.LOGCAT, "getTracingList");
        Cursor mCursor;

        if(mData[6].equals("1")){
            String mRawSQL = "SELECT tracing.* " +
                    "FROM tracing " +
                    "ORDER BY " + mOrderBy;
            mCursor= mDatabase.rawQuery(mRawSQL, null);
        }else {
            String mRawSQL = "SELECT tracing.* " +
                    "FROM tracing " +
                    "WHERE " + mWhere + " " +
                    "ORDER BY " + mOrderBy;
            mCursor= mDatabase.rawQuery(mRawSQL, null);
        }

        return mCursor;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private Cursor getMaxLevel( String[] mInfo){

        Log.d(Constants.LOGCAT, "atLastLevel");
        Cursor mCursor;
        String mRawSQL;
        mRawSQL = "SELECT MAX(level_number) AS max_level_number " +
                "FROM groups_phase_levels " +
                "WHERE group_id=" + mInfo[0] + " " +
                "AND phase_id=" + mInfo[1] + " ";
        mCursor= mDatabase.rawQuery(mRawSQL, null);

        return mCursor;
    }


    private Cursor checkWrongs(String[] mInfo){
        Log.d(Constants.LOGCAT, "checkWrongs");
        Cursor mCursor;
        String mRawSQL;
        String mLevelNumber="0";

        if(!mInfo[0].equals("9")) {
            mRawSQL = "SELECT MAX(level_number) AS max_level_number " +
                    "FROM groups_phase_levels " +
                    "WHERE group_id=" + mInfo[0] + " " +
                    "AND phase_id=" + mInfo[1] + " ";
            mCursor = mDatabase.rawQuery(mRawSQL, null);
            if (mCursor.moveToFirst()) {
                mLevelNumber = mCursor.getString(mCursor.getColumnIndex("max_level_number"));
            }
            mRawSQL = "SELECT app_user_current_level " +
                    "FROM app_users_current_gpl " +
                    "WHERE group_id=" + mInfo[0] + " " +
                    "AND phase_id=" + mInfo[1] + " "+
                    "AND app_user_id=" + mInfo[4] + " ";
            mCursor = mDatabase.rawQuery(mRawSQL, null);
            if (mCursor.moveToFirst()) {
                if(Integer.parseInt(mLevelNumber)
                        >= mCursor.getInt(mCursor.getColumnIndex("app_user_current_level"))){
                    mLevelNumber
                            =mCursor.getString(mCursor.getColumnIndex("app_user_current_level"));
                }
            }
        }



        mRawSQL="SELECT use_info.attempts, " +
                "COUNT(variable_values.auavv_id) AS variable_count, " +
                "MIN(variable_values.number_correct_in_a_row) AS number_correct_in_a_row, " +
                "MIN(variable_values_second.number_correct_in_a_row) AS number_correct_in_level " +
                "FROM app_users_activity_use_info use_info " +
                "INNER JOIN app_users_activity_variable_values variable_values " +
                "ON variable_values.group_id= use_info.group_id " +
                "AND variable_values.phase_id= use_info.phase_id " +
                "AND variable_values.activity_id=use_info.activity_id ";
        if(!mLevelNumber.equals("0")) {
            mRawSQL+="AND variable_values.level_number=" + mLevelNumber + " ";
        }else{
            mRawSQL+="AND variable_values.level_number=use_info.level_number ";
        }
        mRawSQL+="AND variable_values.app_user_id=use_info.app_user_id " +
                "INNER JOIN app_users_activity_variable_values variable_values_second " +
                "ON variable_values_second.group_id= use_info.group_id " +
                "AND variable_values_second.phase_id= use_info.phase_id ";
        if(!mLevelNumber.equals("0")) {
            mRawSQL+="AND variable_values_second.level_number=" + mLevelNumber + " ";
        }else{
            mRawSQL+="AND variable_values_second.level_number=use_info.level_number ";
        }
        mRawSQL+="AND variable_values_second.app_user_id=use_info.app_user_id " +
                "WHERE use_info.group_id=" +  mInfo[0] + " " +
                "AND use_info.phase_id=" +  mInfo[1] + " " +
                "AND use_info.activity_id=" + mInfo[2] + " " +
                "AND use_info.app_user_id=" + mInfo[4] + " ";
        mCursor= mDatabase.rawQuery(mRawSQL, null);
        return mCursor;
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private Cursor getMathOperations(String[] mData, String mWhere){

        Log.d(Constants.LOGCAT, "getMathOperations");
        String mRawSQL;
        Cursor mCursor;

        String[] menuCols = new String[] { "number_one", "number_two", "max_addition_result" };

        MatrixCursor mOutPutCursor  = new MatrixCursor(menuCols);

        ArrayList<ArrayList<Integer>> mNumberOneSet = new ArrayList<>();
        int mMaxAdditionOne, mMaxAdditionTwo, mMaxAdditionResult, mAllowZero;
        int mMaxSubstitutionOne, mMaxSubstitutionTwo, mMaxSubstitutionResult;
        int mMaxMultiplicationOne, mMaxMultiplicationTwo, mMaxMultiplicationResult;
        int mPreviousAdditionOne, mPreviousAdditionTwo;
        int mPreviousSubstitutionOne, mPreviousSubstitutionTwo;
        int mPreviousMultiplicationOne, mPreviousMultiplicationTwo;

        int mCurrentLevel = Integer.parseInt(mData[5]);
        int mPreviousCurrentLevel = mCurrentLevel-1;


        mMaxAdditionOne=mMaxAdditionTwo=mMaxAdditionResult=0;
        mMaxSubstitutionOne=mMaxSubstitutionTwo=mMaxSubstitutionResult=0;
        mMaxMultiplicationOne=mMaxMultiplicationTwo=mMaxMultiplicationResult=mAllowZero=0;

        mPreviousAdditionOne=mPreviousAdditionTwo=0;
        mPreviousSubstitutionOne=mPreviousSubstitutionTwo=0;
        mPreviousMultiplicationOne=mPreviousMultiplicationTwo=0;

        mRawSQL="SELECT max_addition_number_one, " +
                "max_addition_number_two, " +
                "max_addition_result," +
                "allow_addition_zero, " +
                "max_substitution_number_one, " +
                "max_substitution_number_two, " +
                "max_substitution_result, " +
                "max_multiplication_number_one, " +
                "max_multiplication_number_two, " +
                "max_multiplication_result " +
                "FROM math_operations_levels " +
                "WHERE math_equation_levels = " + mCurrentLevel + " " +
                "OR math_equation_levels = " + mPreviousCurrentLevel + " " +
                "ORDER BY math_equation_levels DESC";
        mCursor = mDatabase.rawQuery(mRawSQL, null);
        if(mCursor.moveToFirst()){
            mMaxAdditionOne = mCursor.getInt(
                    mCursor.getColumnIndex("max_addition_number_one"));
            mMaxAdditionTwo = mCursor.getInt(
                    mCursor.getColumnIndex("max_addition_number_two"));
            mMaxAdditionResult = mCursor.getInt(
                    mCursor.getColumnIndex("max_addition_result"));
            mMaxSubstitutionOne = mCursor.getInt(
                    mCursor.getColumnIndex("max_substitution_number_one"));
            mMaxSubstitutionTwo = mCursor.getInt(
                    mCursor.getColumnIndex("max_substitution_number_two"));
            mMaxSubstitutionResult = mCursor.getInt(
                    mCursor.getColumnIndex("max_substitution_result"));
            mMaxMultiplicationOne = mCursor.getInt(
                    mCursor.getColumnIndex("max_multiplication_number_one"));
            mMaxMultiplicationTwo = mCursor.getInt(
                    mCursor.getColumnIndex("max_multiplication_number_two"));
            mMaxMultiplicationResult = mCursor.getInt(
                    mCursor.getColumnIndex("max_multiplication_result"));
            mAllowZero = mCursor.getInt(
                    mCursor.getColumnIndex("allow_addition_zero"));
            if(mCursor.moveToNext()){
                mPreviousAdditionOne = mCursor.getInt(
                        mCursor.getColumnIndex("max_addition_number_one"));
                mPreviousAdditionTwo = mCursor.getInt(
                        mCursor.getColumnIndex("max_addition_number_two"));
            }
        }
        mCursor.close();

        //mDatabase.close();

        int mCurrentOne, mCurrentTwo, mPreviousTwo, mPreviousOne, mMaxResult;

        int mNumberForLoop=10;

        switch (Integer.parseInt(mWhere)){
            default:
            case 1:{

                mCurrentOne = mMaxAdditionOne;
                mCurrentTwo = mMaxAdditionTwo;
                mPreviousTwo = mPreviousAdditionTwo;
                mPreviousOne = mPreviousAdditionOne;
                mMaxResult = mMaxAdditionResult;


                if(mCurrentOne==mPreviousOne){
                    mPreviousOne=0;
                }
                if(mCurrentTwo==mPreviousTwo){
                    mPreviousTwo=0;
                }

                if(mPreviousOne==0){
                    int mEquationCount=0;
                    do{
                        int mBaseOne=( mAllowZero==0) ? 1 : mPreviousOne;
                        int mBaseTwo=(mPreviousTwo==0  && mAllowZero==0) ? 1 : mPreviousTwo;

                        Random mRandom = new Random();

                        int mRandomNumberOne = mRandom.nextInt((mCurrentOne - mPreviousOne))
                                + mBaseOne;
                        int mRandomNumberTwo = mRandom.nextInt((mCurrentTwo - mPreviousTwo))
                                + mBaseTwo;
                        ArrayList<Integer> mTempRandom = new ArrayList<>();
                        mTempRandom.add(mRandomNumberOne);
                        mTempRandom.add(mRandomNumberTwo);
                        mTempRandom.add(mMaxResult);
                        boolean mPass=true;
                        int mLoop=0;
                        while(mNumberOneSet.size()!=0 && mNumberOneSet.contains(mTempRandom)
                                && (mRandomNumberOne + mRandomNumberTwo)<=mMaxResult){
                            if(mLoop>=mNumberForLoop){
                                mPass=false;
                                break;
                            }
                            mRandomNumberOne = mRandom.nextInt((mCurrentOne - mPreviousOne))
                                    + mBaseOne;
                            mRandomNumberTwo = mRandom.nextInt((mCurrentTwo - mPreviousTwo))
                                    + mBaseTwo;
                            mTempRandom = new ArrayList<>();
                            mTempRandom.add(mRandomNumberOne);
                            mTempRandom.add(mRandomNumberTwo);
                            mLoop++;
                        }

                        if(mPass) {

                            mNumberOneSet.add(new ArrayList<Integer>());
                            mNumberOneSet.get(mEquationCount).add(mRandomNumberOne);
                            mNumberOneSet.get(mEquationCount).add(mRandomNumberTwo);
                            mNumberOneSet.get(mEquationCount).add(mMaxResult);
                            mOutPutCursor.addRow(new Object[]{String.valueOf(mRandomNumberOne),
                                    String.valueOf(mRandomNumberTwo), String.valueOf(mMaxResult)});
                            mEquationCount++;
                        }

                    }while(mEquationCount<=Constants.MATH_OPERATIONS_VARIABLES
                            && mEquationCount<=(mMaxResult-1));
                    Log.d(Constants.LOGCAT, "leaving operations do while");
                }else{
                    int mEquationCount=0;
                    /*
                     * This section is for the current variable choices only
                     */
                    do{
                        mPreviousTwo=0;
                        int mBaseOne=mPreviousOne + ((mAllowZero==0) ? 1 : 0);
                        int mBaseTwo=mPreviousTwo + ((mAllowZero==0) ? 1 : 0);

                        Random mRandom = new Random();
                        int mRandomNumberOne = mRandom.nextInt((mCurrentOne - mPreviousOne))
                                + mBaseOne;
                        int mRandomNumberTwo = mRandom.nextInt((mCurrentTwo - mPreviousTwo))
                                + mBaseTwo;
                        ArrayList<Integer> mTempRandom = new ArrayList<>();
                        mTempRandom.add(mRandomNumberOne);
                        mTempRandom.add(mRandomNumberTwo);
                        mTempRandom.add(mMaxResult);
                        boolean mPass=true;
                        int mLoop=0;
                        while(mNumberOneSet.size()!=0 && mNumberOneSet.contains(mTempRandom)
                                && (mRandomNumberOne + mRandomNumberTwo)<=mMaxResult){
                            if(mLoop>=mNumberForLoop){
                                mPass=false;
                                break;
                            }
                            mRandomNumberOne = mRandom.nextInt((mCurrentOne - mPreviousOne))
                                    + mBaseOne;
                            mRandomNumberTwo = mRandom.nextInt((mCurrentTwo - mPreviousTwo))
                                    + mBaseTwo;
                            mTempRandom = new ArrayList<>();
                            mTempRandom.add(mRandomNumberOne);
                            mTempRandom.add(mRandomNumberTwo);
                            mTempRandom.add(mMaxResult);
                            mLoop++;
                        }

                        if(mPass) {

                            mNumberOneSet.add(new ArrayList<Integer>());
                            mNumberOneSet.get(mEquationCount).add(mRandomNumberOne);
                            mNumberOneSet.get(mEquationCount).add(mRandomNumberTwo);
                            mNumberOneSet.get(mEquationCount).add(mMaxResult);
                            mOutPutCursor.addRow(new Object[]{String.valueOf(mRandomNumberOne),
                                    String.valueOf(mRandomNumberTwo), String.valueOf(mMaxResult)});
                            mEquationCount++;
                        }

                    }while(mEquationCount<=Constants.MATH_OPERATIONS_UPPER_VARIABLES);

                    /*
                     * Below is only for the lesser amount of variable bellow the current round
                     */


                    mCurrentOne = mPreviousAdditionOne;
                    mCurrentTwo = mPreviousAdditionTwo;
                    mPreviousTwo = 0;
                    mPreviousOne = 0;
                    int mEquationCountSecondary=0;
                    do{
                        int mBaseOne=( mAllowZero==0) ? 1 : mPreviousOne;
                        int mBaseTwo=( mAllowZero==0) ? 1 : mPreviousTwo;

                        Random mRandom = new Random();
                        int mRandomNumberOne = mRandom.nextInt((mCurrentOne - mPreviousOne))
                                + mBaseOne;
                        int mRandomNumberTwo = mRandom.nextInt((mCurrentTwo - mPreviousTwo))
                                + mBaseTwo;
                        ArrayList<Integer> mTempRandom = new ArrayList<>();
                        mTempRandom.add(mRandomNumberOne);
                        mTempRandom.add(mRandomNumberTwo);
                        mTempRandom.add(mMaxResult);
                        boolean mPass=true;
                        int mLoop=0;
                        while(mNumberOneSet.size()!=0 && mNumberOneSet.contains(mTempRandom)
                                && (mRandomNumberOne + mRandomNumberTwo)<=mMaxResult){
                            if(mLoop>=mNumberForLoop){
                                mPass=false;
                                break;
                            }
                            mRandomNumberOne = mRandom.nextInt((mCurrentOne - mPreviousOne))
                                    + mBaseOne;
                            mRandomNumberTwo = mRandom.nextInt((mCurrentTwo - mPreviousTwo))
                                    + mBaseTwo;
                            mTempRandom = new ArrayList<>();
                            mTempRandom.add(mRandomNumberOne);
                            mTempRandom.add(mRandomNumberTwo);
                            mTempRandom.add(mMaxResult);
                            mLoop++;
                        }

                        if(mPass) {

                            mNumberOneSet.add(new ArrayList<Integer>());
                            mNumberOneSet.get(mEquationCount).add(mRandomNumberOne);
                            mNumberOneSet.get(mEquationCount).add(mRandomNumberTwo);
                            mNumberOneSet.get(mEquationCount).add(mMaxResult);
                            mOutPutCursor.addRow(new Object[]{String.valueOf(mRandomNumberOne),
                                    String.valueOf(mRandomNumberTwo), String.valueOf(mMaxResult)});
                            mEquationCount++;
                            mEquationCountSecondary++;
                        }


                    }while(mEquationCountSecondary<=
                            (Constants.MATH_OPERATIONS_VARIABLES
                                    -Constants.MATH_OPERATIONS_UPPER_VARIABLES)
                            && mEquationCount<12);
                    Log.d(Constants.LOGCAT, "leaving operations do while");
                }

                break;
            }
            case 2:{

                mCurrentOne = mMaxSubstitutionOne;
                mCurrentTwo = mMaxSubstitutionTwo;
                mPreviousTwo = mPreviousSubstitutionTwo;
                mPreviousOne = mPreviousSubstitutionOne;
                mMaxResult = mMaxSubstitutionResult;


                if(mCurrentOne==mPreviousOne){
                    mPreviousOne=0;
                }
                if(mCurrentTwo==mPreviousTwo){
                    mPreviousTwo=0;
                }



                int mActualTwo;
                int mEquationCount=0;
                int mAttemptCount=0;
                do{
                    int mBaseOne=(mAllowZero==0) ? 1 : mPreviousOne;
                    int mBaseTwo=(mAllowZero==0) ? 1 : mPreviousTwo;


                    Random mRandom = new Random();
                    int mRandomNumberOne = mRandom.nextInt((mCurrentOne - mPreviousOne))
                            + mBaseOne;
                    mActualTwo = (mRandomNumberOne>mCurrentTwo)
                            ? mCurrentTwo : mRandomNumberOne;
                    int mRandomNumberTwo=0;
                    if(mActualTwo-mPreviousTwo>0) {
                        mRandomNumberTwo = mRandom.nextInt((mActualTwo - mPreviousTwo))
                                + mBaseTwo;
                    }
                    ArrayList<Integer> mTempRandom = new ArrayList<>();
                    mTempRandom.add(mRandomNumberOne);
                    mTempRandom.add(mRandomNumberTwo);
                    mTempRandom.add(mMaxResult);
                    boolean mPass=true;
                    int mLoop=0;
                    while(mNumberOneSet.size()!=0 && mNumberOneSet.contains(mTempRandom)
                            && (mRandomNumberOne + mRandomNumberTwo)>=0){

                        if(mLoop>=mNumberForLoop){
                            mPass=false;
                            break;
                        }
                        mRandomNumberOne = mRandom.nextInt((mCurrentOne - mPreviousOne))
                                + mBaseOne;
                        mActualTwo = (mRandomNumberOne>mCurrentTwo)
                                ? mCurrentTwo : mRandomNumberOne;
                        if(mActualTwo-mPreviousTwo==0){
                            mRandomNumberTwo=0;
                        }else {
                            mRandomNumberTwo = mRandom.nextInt((mActualTwo - mPreviousTwo))
                                    + mBaseTwo;
                        }
                        mTempRandom = new ArrayList<>();
                        mTempRandom.add(mRandomNumberOne);
                        mTempRandom.add(mRandomNumberTwo);
                        mTempRandom.add(mMaxResult);
                        mLoop++;
                    }

                    if(mPass) {
                        mNumberOneSet.add(new ArrayList<Integer>());
                        mNumberOneSet.get(mEquationCount).add(mRandomNumberOne);
                        mNumberOneSet.get(mEquationCount).add(mRandomNumberTwo);
                        mNumberOneSet.get(mEquationCount).add(mMaxResult);
                        mOutPutCursor.addRow(new Object[]{String.valueOf(mRandomNumberOne),
                                String.valueOf(mRandomNumberTwo), String.valueOf(mMaxResult)});
                        mEquationCount++;
                        mAttemptCount=0;
                    }
                    mAttemptCount++;
                }while(mEquationCount<=Constants.MATH_OPERATIONS_VARIABLES
                        && mAttemptCount<=Constants.MATH_OPERATIONS_VARIABLES);
                Log.d(Constants.LOGCAT, "leaving operations do while");


                break;
            }
            case 3:{
                mCurrentOne = mMaxMultiplicationOne;
                mCurrentTwo = mMaxMultiplicationTwo;
                mPreviousTwo = mPreviousMultiplicationTwo;
                mPreviousOne = mPreviousMultiplicationOne;
                mMaxResult = mMaxMultiplicationResult;


                if(mCurrentOne==mPreviousOne){
                    mPreviousOne=0;
                }
                if(mCurrentTwo==mPreviousTwo){
                    mPreviousTwo=0;
                }

                int mEquationCount=0;
                do{

                    Random mRandom = new Random();
                    int mRandomNumberOne = mRandom.nextInt((mCurrentOne - mPreviousOne)) + 1;
                    int mRandomNumberTwo = mRandom.nextInt((mCurrentTwo - mPreviousTwo)) + 1;
                    ArrayList<Integer> mTempRandom = new ArrayList<>();
                    mTempRandom.add(mRandomNumberOne);
                    mTempRandom.add(mRandomNumberTwo);
                    mTempRandom.add(mMaxResult);
                    boolean mPass=true;
                    int mLoop=0;
                    while(mNumberOneSet.size()!=0 && mNumberOneSet.contains(mTempRandom)
                            && (mRandomNumberOne * mRandomNumberTwo)<=mMaxResult){
                        if(mLoop>=mNumberForLoop){
                            mPass=false;
                            break;
                        }
                        mRandomNumberOne = mRandom.nextInt((mCurrentOne - mPreviousOne)) + 1;
                        mRandomNumberTwo = mRandom.nextInt((mCurrentTwo - mPreviousTwo)) + 1;
                        mTempRandom = new ArrayList<>();
                        mTempRandom.add(mRandomNumberOne);
                        mTempRandom.add(mRandomNumberTwo);
                        mLoop++;
                    }

                    if(mPass) {

                        mNumberOneSet.add(new ArrayList<Integer>());
                        mNumberOneSet.get(mEquationCount).add(mRandomNumberOne);
                        mNumberOneSet.get(mEquationCount).add(mRandomNumberTwo);
                        mNumberOneSet.get(mEquationCount).add(mMaxResult);
                        mOutPutCursor.addRow(new Object[]{String.valueOf(mRandomNumberOne),
                                String.valueOf(mRandomNumberTwo), String.valueOf(mMaxResult)});
                        mEquationCount++;
                    }

                }while(mEquationCount<=Constants.MATH_OPERATIONS_VARIABLES
                        && mEquationCount<=(mMaxResult-1));
                Log.d(Constants.LOGCAT, "leaving operations do while");



              //  mOutPutCursor=
                break;
            }
        }

       //
        Log.d(Constants.LOGCAT,"hrrtr");


      /*
*/
        MergeCursor mMergeCursor;
        if(mOutPutCursor.getCount()>=0){
            mMergeCursor = new MergeCursor(new Cursor[] { mOutPutCursor });
        }else{
            mMergeCursor = new MergeCursor(new Cursor[] { mCursor });
        }

        Log.d(Constants.LOGCAT, "Get Math Operation Cursor count: " + mMergeCursor.getCount());
        return mMergeCursor;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private Cursor currentMathShapeUppers(String[] mData) {
        Log.d(Constants.LOGCAT, "currentMathShapeUppers");

        String mRawSQL="SELECT *" +
                "FROM app_users_activity_variable_values " +
                "WHERE group_id=6 " +
                "AND level_number=" + mData[5]+" ";
        return mDatabase.rawQuery(mRawSQL, null);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private Cursor getAllMathNumbersForPiano(){
        Log.d(Constants.LOGCAT, "getAllMathNumbersForPiano");

        String mRawSQL="SELECT math_numbers.math_numbers_id, " +
                "math_numbers.math_number, " +
                "math_numbers.math_number_style, " +
                "math_numbers.math_numbers_audio, " +
                "variable_phase_levels.level_number, " +
                "groups_phase_levels.level_color  " +
                "FROM math_numbers  " +
                "LEFT JOIN variable_phase_levels   " +
                "ON variable_phase_levels.variable_id=math_numbers.math_numbers_id " +
                "AND variable_phase_levels.group_id=7 " +
                "LEFT JOIN groups_phase_levels " +
                "ON groups_phase_levels.group_id=7 " +
                "AND groups_phase_levels.level_number=variable_phase_levels.level_number  " +
                "ORDER BY  variable_phase_levels.level_number ASC,  " +
                "math_numbers.math_number ASC ";
        return mDatabase.rawQuery(mRawSQL, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private Cursor getAllMathNumbers( String[] mData, String mWhere, String mOrderBy){
        Log.d(Constants.LOGCAT, "getAllMathNumbers");

        String mRawSQL="SELECT math_numbers.* ," +
                "app_users_activity_variable_values.* " +
                "FROM app_users_activity_variable_values " +
                "LEFT JOIN math_numbers " +
                "ON math_numbers.math_numbers_id=app_users_activity_variable_values.variable_id " +
                "WHERE app_users_activity_variable_values.activity_id=" +mData[0]+" "+
                "AND app_users_activity_variable_values.app_user_id=" +mData[1]+" "+
                "AND app_users_activity_variable_values.group_id=7 " + mWhere+ " "+
                "ORDER BY "+mOrderBy+" RANDOM() ";

        return mDatabase.rawQuery(mRawSQL, null);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    protected Cursor currentMathShapesSH02Extra(String[] mData, String mWhere) {
        Log.d(Constants.LOGCAT, "currentMathShapesSH02Extra");

        String mRawSQL = "SELECT math_shapes.* , " +
                "app_users_activity_variable_values.* " +
                "FROM math_shapes " +
                "LEFT JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=math_shapes.math_shape_id " +
                "AND app_users_activity_variable_values.activity_id=" + mData[0] + " " +
                "AND app_users_activity_variable_values.app_user_id=" + mData[1] + " " +
                "AND app_users_activity_variable_values.group_id=6 " +
                "WHERE " + mWhere + " " +
                "ORDER BY RANDOM() " +
                "LIMIT 10 ";
        return mDatabase.rawQuery(mRawSQL, null);

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    protected Cursor currentMathShapesFourIn(String[] mData, String mWhere) {
        Log.d(Constants.LOGCAT, "currentMathShapesFourIn");

        String mRawSQL = "SELECT  * " +
                "FROM ( " +
                "SELECT math_shapes.* , " +
                "app_users_activity_variable_values.* " +
                "FROM math_shapes " +
                "LEFT JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=math_shapes.math_shape_id " +
                "AND app_users_activity_variable_values.activity_id=" + mData[0] + " " +
                "AND app_users_activity_variable_values.app_user_id=" + mData[1] + " " +
                "AND app_users_activity_variable_values.group_id=6 " +
                "WHERE math_shapes.math_shape_kind==" + mData[6] + " " + mWhere + " " +
                "AND app_users_activity_variable_values.number_correct_in_a_row==0 " +
                "GROUP BY math_shapes.math_shape_color_id " +
                "ORDER BY RANDOM() " +
                "LIMIT 4 ) " +
                "UNION ALL " +
                "SELECT  * " +
                "FROM ( " +
                "SELECT math_shapes.*, " +
                "app_users_activity_variable_values.* " +
                "FROM math_shapes " +
                "LEFT JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=math_shapes.math_shape_id " +
                "AND app_users_activity_variable_values.activity_id=" + mData[0] + " " +
                "AND app_users_activity_variable_values.app_user_id=" + mData[1] + " " +
                "AND app_users_activity_variable_values.group_id=6 " +
                "WHERE math_shapes.math_shape_kind==" + mData[6] + " " + mWhere + " " +
                "AND app_users_activity_variable_values.number_correct_in_a_row>0  " +
                "ORDER BY RANDOM() " +
                "LIMIT  8 )";
        return mDatabase.rawQuery(mRawSQL, null);

    }

    //////////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////////

    protected Cursor currentMathShapesSH06( String[] mData, String mWhere, String[] mWhereSets) {
        Log.d(Constants.LOGCAT, "currentMathShapesSH06");

        String mRawSQL="SELECT  * " +
                "FROM ( " +
                "SELECT math_shapes.* , " +
                "app_users_activity_variable_values.* " +
                "FROM math_shapes " +
                "LEFT JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=math_shapes.math_shape_id " +
                "AND app_users_activity_variable_values.activity_id=" +mData[0]+" "+
                "AND app_users_activity_variable_values.app_user_id=" +mData[1]+" "+
                "AND app_users_activity_variable_values.group_id=6 " +
                "WHERE  " + mWhereSets[0] + " " +  mWhere + " " +
                "ORDER BY RANDOM() " +
                "LIMIT 1 ) " +
                "UNION ALL " +
                "SELECT  * " +
                "FROM ( " +
                "SELECT math_shapes.* , " +
                "app_users_activity_variable_values.* " +
                "FROM math_shapes " +
                "LEFT JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=math_shapes.math_shape_id " +
                "AND app_users_activity_variable_values.activity_id=" +mData[0]+" "+
                "AND app_users_activity_variable_values.app_user_id=" +mData[1]+" "+
                "AND app_users_activity_variable_values.group_id=6 " +
                "WHERE  " + mWhereSets[1] + " " +  mWhere + " " +
                "ORDER BY RANDOM() " +
                "LIMIT 1 ) " +
                "UNION ALL " +
                "SELECT  * " +
                "FROM ( " +
                "SELECT math_shapes.* , " +
                "app_users_activity_variable_values.* " +
                "FROM math_shapes " +
                "LEFT JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=math_shapes.math_shape_id " +
                "AND app_users_activity_variable_values.activity_id=" +mData[0]+" "+
                "AND app_users_activity_variable_values.app_user_id=" +mData[1]+" "+
                "AND app_users_activity_variable_values.group_id=6 " +
                "WHERE  " + mWhereSets[2] + " " +  mWhere + " " +
                "ORDER BY RANDOM() " +
                "LIMIT 4 )";
        return mDatabase.rawQuery(mRawSQL, null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected Cursor currentMathShapes( String[] mData, String mWhere, String mOrderBy){
        Log.d(Constants.LOGCAT, "currentMathShapes");

        String mRawSQL="SELECT math_shapes.* , " +
                "app_users_activity_variable_values.* " +
                "FROM math_shapes " +
                "LEFT JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=math_shapes.math_shape_id " +
                "AND app_users_activity_variable_values.activity_id=" +mData[0]+" "+
                "AND app_users_activity_variable_values.app_user_id=" +mData[1]+" "+
                "AND app_users_activity_variable_values.group_id=6 " +
                "WHERE math_shapes.math_shape_kind=="+mData[6]+" " + mWhere + " ";
        if(!mOrderBy.trim().equals("")){
            if(mOrderBy.equals("rand")){
                mRawSQL += "ORDER BY  RANDOM() " +
                        "LIMIT 8";
            }else {
                mRawSQL += "ORDER BY " + mOrderBy + " RANDOM() " +
                        "LIMIT 8";
            }
        }
        return mDatabase.rawQuery(mRawSQL, null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////


    protected Cursor currentWordSyllableValues(String[] projection, String selection,String sortOrder){
        Log.d(Constants.LOGCAT, "currentWordSyllableValues");

        ArrayList<HashMap<String,String>> mUserGPL = new ArrayList<>(getUserGPL("0","3"));
        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        mUpdateGroups.updateSyllableWordsProcess(projection, sortOrder);



        String mRawSQL="SELECT MAX(level_number) as max_level_number " +
                "FROM variable_phase_levels " +
                "WHERE group_id=3 " +
                "AND phase_id=1 " +
                "LIMIT 1";
        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);
        int mMaxLevelNumber=0;
        if(mCursor.moveToFirst()){
            mMaxLevelNumber=mCursor.getInt(mCursor.getColumnIndex("max_level_number"));
        }
        mCursor.close();

        mRawSQL="SELECT app_users_activity_variable_values.variable_id, " +
                "app_users_activity_variable_values.number_correct, " +
                "app_users_activity_variable_values.number_correct_in_a_row, " +
                "app_users_activity_variable_values.number_incorrect, " +
                "words.*  " +
                "FROM words " +
                "INNER JOIN variable_phase_levels " +
                "ON variable_phase_levels.variable_id=words.word_id  " +
                "LEFT JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=words.word_id " +
                "AND app_users_activity_variable_values.activity_id=" +projection[0]+" "+
                "AND app_users_activity_variable_values.app_user_id=" +projection[1]+" "+
                "AND app_users_activity_variable_values.group_id=3 " +
                "AND app_users_activity_variable_values.phase_id=1 " +
                "WHERE "+selection+" ";
        if(Integer.parseInt(projection[5])<=mMaxLevelNumber){
            mRawSQL+="AND variable_phase_levels.level_number=="+projection[5]+" " +
                    "ORDER BY  app_users_activity_variable_values.number_correct_in_a_row ASC, " +
                    "app_users_activity_variable_values.number_incorrect DESC, " +
                    "RANDOM() ";
        }else{
            mRawSQL+="ORDER BY RANDOM() ";
        }
        mRawSQL+="LIMIT 8";

        return mDatabase.rawQuery(mRawSQL, null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected Cursor activityCurrentWordLevelPhonics(String[] projection, String selection) {
        Log.d(Constants.LOGCAT, "activityCurrentWordLevelPhonics");

        String mRawSQL="SELECT phonics.*, "+
                "variable_phase_levels.level_number," +
                "app_users_activity_variable_values.variable_id," +
                "app_users_activity_variable_values.number_correct, " +
                "app_users_activity_variable_values.number_correct_in_a_row, " +
                "app_users_activity_variable_values.number_incorrect, " +
                "words.*  " +
                "FROM words " +
                "LEFT JOIN phonics " +
                "ON phonics.phonic_id=words.word_first_letter "+
                "INNER JOIN variable_phase_levels " +
                "ON variable_phase_levels.variable_id=words.word_id  " +
                "INNER JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=words.word_id " +
                "AND app_users_activity_variable_values.activity_id=" +projection[0]+" "+
                "AND app_users_activity_variable_values.app_user_id=" +projection[1]+" "+
                "WHERE " +selection+" "+
                "ORDER BY  app_users_activity_variable_values.number_correct_in_a_row ASC, " +
                "app_users_activity_variable_values.number_incorrect DESC, " +
                "app_users_activity_variable_values.number_correct ASC, " +
                "RANDOM() " +
                "LIMIT 12";
        return mDatabase.rawQuery(mRawSQL, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    protected Cursor activityCurrentWordWRW(String[] projection, String selection){
        Log.d(Constants.LOGCAT,"activityCurrentWordWRW");

/*
        String mRawSQL="SELECT * " +
                "FROM (" +
                "SELECT words.*, " +
                "variable_phase_levels.level_number " +
                "FROM words " +
                "INNER JOIN variable_phase_levels " +
                "ON variable_phase_levels.variable_id=words.word_id  " +
                "WHERE variable_phase_levels.group_id=" + projection[2] + "  " +
                "AND variable_phase_levels.phase_id=" + projection[3] + "  " +
                "AND variable_phase_levels.level_number=" + projection[5] + " ";
        if(projection[0].equals("46")){
            mRawSQL+="AND words.word_separation != 0 AND words.word_separation != '' ";
        }



        if(projection[6].equals(1)){
            mRawSQL+="LIMIT 4 ";
        }
        mRawSQL+= ")  " +
                "UNION ALL " +
                "SELECT * " +
                "FROM ( " +
                "SELECT words.*, " +
                "variable_phase_levels.level_number " +
                "FROM words  " +
                "INNER JOIN variable_phase_levels " +
                "ON variable_phase_levels.variable_id=words.word_id  " +
                "WHERE "+selection+" "+
                "ORDER BY RANDOM()) ";
        if(projection[6].equals(1)){
            mRawSQL += "LIMIT 12 ";
        }else {
            mRawSQL += "LIMIT 20 ";
        }
        */
        String mRawSQL="SELECT variable_phase_levels.level_number," +
                "app_users_activity_variable_values.variable_id," +
                "app_users_activity_variable_values.number_correct, " +
                "app_users_activity_variable_values.number_correct_in_a_row, " +
                "app_users_activity_variable_values.number_incorrect, " +
                "words.*  " +
                "FROM words " +
                "INNER JOIN variable_phase_levels " +
                "ON variable_phase_levels.variable_id=words.word_id  " +
                "INNER JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.variable_id=words.word_id " +
                "AND app_users_activity_variable_values.activity_id=" +projection[0]+" "+
                "AND app_users_activity_variable_values.app_user_id=" +projection[1]+" "+
                "WHERE " +selection+" "+
                "ORDER BY  app_users_activity_variable_values.number_correct_in_a_row ASC, " +
                "app_users_activity_variable_values.number_incorrect DESC, " +
                "app_users_activity_variable_values.number_correct ASC, " +
                "RANDOM() " +
                "LIMIT 36";

        return mDatabase.rawQuery(mRawSQL, null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected Cursor activityCurrentLettersWRW(String[] projection, String selection){
        Log.d(Constants.LOGCAT,"activityCurrentLettersWRW");

        String mRawSQL="SELECT app_users_activity_variable_values.variable_id," +
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


        return mDatabase.rawQuery(mRawSQL, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////

    private Cursor getExtraThreeMathNumbers( String[] mData, String mWhere, String mOrderBy){
        Log.d(Constants.LOGCAT,"getExtraThreeMathNumbers");

        String mRawSQL="SELECT math_numbers.* ," +
                "app_users_activity_variable_values.* " +
                "FROM app_users_activity_variable_values " +
                "LEFT JOIN math_numbers " +
                "ON math_numbers.math_numbers_id=app_users_activity_variable_values.variable_id " +
                "WHERE app_users_activity_variable_values.activity_id=" +mData[0]+" "+
                "AND app_users_activity_variable_values.app_user_id=" +mData[1]+" "+
                "AND app_users_activity_variable_values.group_id=7 " + mWhere+ " "+
                "ORDER BY "+mOrderBy+" RANDOM() " +
                "LIMIT 3";

        return mDatabase.rawQuery(mRawSQL, null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private Cursor getMathNumbers( String[] mData, String mWhere){
        Log.d(Constants.LOGCAT, "getMathNumbers");
        String mRawSQL="SELECT " +
                "MIN(mn2.math_number) AS min_number, " +
                "MAX(mn2.math_number) AS max_number," +
                "math_numbers.*," +
                "app_users_activity_variable_values.* " +
                "FROM app_users_activity_variable_values " +
                "JOIN   ( " +
                "SELECT * " +
                "FROM math_numbers " +
                "LEFT JOIN app_users_activity_variable_values AS au1 " +
                "WHERE  au1.activity_id=" +mData[0]+" "+
                "AND au1.app_user_id=" +mData[1]+" "+
                "AND au1.group_id=7  " +
                "AND math_numbers.math_numbers_id=au1.variable_id   " +
                ")  mn2  " +
                "LEFT JOIN math_numbers " +
                "ON math_numbers.math_numbers_id=app_users_activity_variable_values.variable_id  " +
                "WHERE app_users_activity_variable_values.activity_id=" +mData[0]+" "+
                "AND app_users_activity_variable_values.app_user_id=" +mData[1]+" "+
                "AND app_users_activity_variable_values.group_id=7 " + mWhere+ " "+
                "GROUP BY math_numbers.math_number  " +
                "ORDER BY app_users_activity_variable_values.number_correct_in_a_row ASC,  " +
                "RANDOM() " +
                "LIMIT 8 ";

        return mDatabase.rawQuery(mRawSQL, null);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    private Cursor getOnePianoShape(String mWhere){
        Log.d(Constants.LOGCAT, "getOnePianoShape");
        String mRawSQL="SELECT * " +
                "FROM math_shapes " +
                "WHERE "+mWhere+" "+
                "AND math_shape_kind=0 " +
                "ORDER BY math_shape_size ASC, RANDOM() " +
                "LIMIT 8";
        return mDatabase.rawQuery(mRawSQL, null);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private Cursor getAllowedShapes(){
        Log.d(Constants.LOGCAT,"getAllowedShapes");

        String mRawSQL="SELECT math_shapes.* " +
                "FROM math_shapes " +
                "INNER JOIN app_users_current_gpl " +
                "ON app_users_current_gpl.group_id=6 " +
                "INNER JOIN variable_phase_levels " +
                "ON variable_phase_levels.group_id=6  " +
                "AND variable_phase_levels.variable_id=math_shapes.math_shape_id " +
                "WHERE variable_phase_levels.level_number>0 " +
                "AND variable_phase_levels.level_number" +
                "<=app_users_current_gpl.app_user_current_level " +
                "AND math_shapes.math_shape_type_id<=6 " +
                "GROUP BY math_shapes.math_shape_type_id " +
                "ORDER BY math_shapes.math_shape_type_id ASC";
        return mDatabase.rawQuery(mRawSQL, null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private Cursor bookVideos() {
        Log.d(Constants.LOGCAT,"bookVideos");

        String mRawSQL="SELECT * FROM groups_phase_levels " +
                "INNER JOIN app_users_current_gpl " +
                "ON app_users_current_gpl.phase_id=groups_phase_levels.phase_id " +
                "AND app_users_current_gpl.group_id=groups_phase_levels.group_id " +
                "WHERE groups_phase_levels.group_id=5 " +
                "AND groups_phase_levels.phase_id=1 ";
        return mDatabase.rawQuery(mRawSQL, null);
    }









    /////////////////////////////////////////////////////////////////////////////////////////////

    private Cursor bookPagesReadAloud(String selection) {
        Log.d(Constants.LOGCAT, "bookPagesReadAloud");
        String mRawSQL="SELECT * " +
                "FROM book_pages " +
                "WHERE  " +selection+" "+
                "ORDER BY book_page_order ASC";
        return mDatabase.rawQuery(mRawSQL, null);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    private Cursor booksDecodable() {
        Log.d(Constants.LOGCAT, "booksDecodable");
        String mRawSQL="SELECT  phonics.*,books.*  " +
                "FROM  phonics " +
                "INNER JOIN  books " +
                "ON books.decodable_levels = variable_phase_levels.level_number " +
                "INNER JOIN variable_phase_levels " +
                "ON variable_phase_levels.group_id=2 " +
                "AND variable_phase_levels.phase_id=3 " +
                "AND variable_phase_levels.variable_id=phonics.phonic_id " +
                "INNER JOIN app_users_current_gpl " +
                "ON app_users_current_gpl.group_id=2  " +
                "AND app_users_current_gpl.phase_id=3 " +
                "WHERE books.book_type=1 " +
                "AND books.decodable_levels<app_users_current_gpl.app_user_current_level  " +
                "ORDER BY books.decodable_levels ASC";
        return mDatabase.rawQuery(mRawSQL, null);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private Cursor booksReadAloud(String mSort) {
        Log.d(Constants.LOGCAT, "booksReadAloud");
        String mRawSQL="SELECT books.*," +
                "book_pages.book_page_image, " +
                "groups_phase_levels.gpl_id, " +
                "groups_phase_levels.level_number, " +
                "app_users_current_gpl.app_user_current_level, " +
                "app_users_current_gpl.group_id, " +
                "app_users_current_gpl.phase_id  " +
                "FROM groups_phase_levels " +
                "INNER JOIN app_users_current_gpl " +
                "INNER JOIN books " +
                "ON books.associated_levels = groups_phase_levels.gpl_id  " +
                "INNER JOIN book_pages ON book_pages.book_id=books.book_id  " +
                "WHERE ( groups_phase_levels.group_id = 2 " +
                "AND  groups_phase_levels.phase_id = 1 " +
                "AND  app_users_current_gpl.app_user_current_level  > 4 " +
                "AND groups_phase_levels.level_number " +
                "< app_users_current_gpl.app_user_current_level) " +
                "OR ((groups_phase_levels.group_id != 2 " +
                "AND  groups_phase_levels.phase_id != 1) " +
                "AND groups_phase_levels.group_id = app_users_current_gpl.group_id " +
                "AND groups_phase_levels.phase_id = app_users_current_gpl.phase_id " +
                "AND groups_phase_levels.level_number " +
                "< app_users_current_gpl.app_user_current_level)" +
                "GROUP BY books.book_id " +
                "ORDER BY books.difficulty_level ASC, " +
                "books.book_title ASC ";
        /*
            OLDER VERSION

        String mRawSQL="SELECT " +
                "(SELECT COUNT(bk2.book_id)  " +
                "FROM groups_phase_levels AS gpl " +
                "INNER JOIN books AS bk2 " +
                "ON bk2.associated_levels=gpl.gpl_id " +
                "INNER JOIN book_pages ON book_pages.book_id" +
                "=bk2.book_id " +
                "INNER JOIN app_users_current_gpl AS aucgpl " +
                "ON aucgpl.group_id=gpl.group_id " +
                "AND aucgpl.phase_id=gpl.phase_id " +
                "WHERE gpl.level_number<(aucgpl.app_user_current_level-1) " +
                "AND bk2.book_type=0  " +
                "AND book_pages.book_page_order=0 ) as book_count," +
                "gpl.group_id, " +
                "books.*, " +
                "book_pages.book_page_image " +
                "FROM groups_phase_levels AS gpl " +
                "INNER JOIN books " +
                "ON books.associated_levels=gpl.gpl_id " +
                "INNER JOIN book_pages " +
                "ON book_pages.book_id=books.book_id " +
                "INNER JOIN app_users_current_gpl AS aucgpl " +
                "ON aucgpl.group_id=gpl.group_id " +
                "AND aucgpl.phase_id=gpl.phase_id " +
                "WHERE gpl.level_number<(aucgpl.app_user_current_level-1)  " +
                "AND books.book_type=0 " +
                "AND book_pages.book_page_order=0" +
                " ORDER BY books.difficulty_level ASC, " +
                "books.book_title ASC ";
        */
        if(mSort!=null){
            mRawSQL+=mSort;
        }
        return mDatabase.rawQuery(mRawSQL, null);
    }



    /////////////////////////////////////////////////////////////////////////////////////////////
/*
    private Cursor groupPhaseLevels(String mAppUserId){
        SQLiteDatabase mDatabase = dbHelper.getWritableDatabase();

        String mRawSQL="SELECT * FROM app_users_current_gpl  " +
                "WHERE app_users_current_gpl.app_user_id="+mAppUserId+" "+
                "ORDER BY app_users_current_gpl.group_id ASC, "+
                "app_users_current_gpl.phase_id ASC";
        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);

        Log.d(Constants.LOGCAT, "end groupPhaseLevelsUpdate");

        return mCursor;
    }
*/



    /////////////////////////////////////////////////////////////////////////////////////////////

    protected ArrayList<HashMap<String, String>> getUserGPL( String mAppUserId,
                                                             String mGroupID){

        ArrayList<HashMap<String,String>> mUserGPL=new ArrayList<>();

        String mRawSQL="SELECT * " +
                "FROM app_users_current_gpl  " +
                "WHERE app_users_current_gpl.app_user_id="+mAppUserId+" ";
        if(!mGroupID.equals("0")) {
            mRawSQL+="AND app_users_current_gpl.group_id=" + mGroupID + " ";

        }
        mRawSQL+="ORDER BY app_users_current_gpl.group_id ASC, "+
                "app_users_current_gpl.phase_id ASC";
        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);

        if(mCursor.moveToFirst()){
            do{
                mUserGPL.add(new HashMap<String,String>());
                mUserGPL.get(mUserGPL.size()-1).put("_id", mCursor.getString(
                        mCursor.getColumnIndex("_id"))); //0
                mUserGPL.get(mUserGPL.size()-1).put("app_user_id",mCursor.getString(
                        mCursor.getColumnIndex("app_user_id"))); //1
                mUserGPL.get(mUserGPL.size()-1).put("group_id",mCursor.getString(
                        mCursor.getColumnIndex("group_id"))); //2
                mUserGPL.get(mUserGPL.size()-1).put("phase_id",mCursor.getString(
                        mCursor.getColumnIndex("phase_id"))); //3
                mUserGPL.get(mUserGPL.size()-1).put("app_user_current_level",mCursor.getString(
                        mCursor.getColumnIndex("app_user_current_level"))); //4
                mUserGPL.get(mUserGPL.size()-1).put("type_id","0"); //5

                /*
                 * This below is getting the type_id for the group_id/phase_id relation
                 * I might be able to change this later to have a separate related table with type_id
                 * as primary and group_id and phase_id as relations
                 */
                switch(Integer.parseInt(mUserGPL.get(mUserGPL.size()-1).get("group_id"))){
                    default:
                    case 2:
                        switch(Integer.parseInt(mUserGPL.get(mUserGPL.size()-1).get("phase_id"))){
                            default:
                            case 1:
                                mUserGPL.get(mUserGPL.size()-1).put("type_id", "1");
                                break;
                            case 2:
                                mUserGPL.get(mUserGPL.size()-1).put("type_id", "2");
                                break;
                            case 3:
                                mUserGPL.get(mUserGPL.size()-1).put("type_id", "3");
                                break;
                        }
                        break;
                    case 3:
                        if(mUserGPL.get(mUserGPL.size()-1).get("phase_id").equals("1")){
                            mUserGPL.get(mUserGPL.size()-1).put("type_id", "4");
                        }else{
                            mUserGPL.get(mUserGPL.size()-1).put("type_id", "5");
                        }
                        break;
                    case 4:
                        mUserGPL.get(mUserGPL.size()-1).put("type_id", "6");
                        break;
                    case 5:
                        if(mUserGPL.get(mUserGPL.size()-1).get("phase_id").equals("1")){
                            mUserGPL.get(mUserGPL.size()-1).put("type_id", "7");
                        }else{
                            mUserGPL.get(mUserGPL.size()-1).put("type_id", "8");
                        }
                        break;
                    case 6:
                        mUserGPL.get(mUserGPL.size()-1).put("type_id","9");
                        break;
                    case 7:
                        mUserGPL.get(mUserGPL.size()-1).put("type_id","10");
                        break;
                    case 8:
                        mUserGPL.get(mUserGPL.size()-1).put("type_id","11");
                        break;
                }
            }while(mCursor.moveToNext());
        }
        mCursor.close();
        return mUserGPL;
    }



    //////////////////////////////////////////////////////////////////////////////////////

    @Override
    public int update(@NonNull Uri uri, ContentValues mValues, String mWhere,
                      String[] mInfo) {
        Log.d(Constants.LOGCAT, "public int update");

        String mRawSQL;
        Cursor mCursor;




        switch (uriMatcher.match(uri)) {


            case ACTIVITY_USER_RW_ALL_UPDATE:{
                String mPerfectScore=String.valueOf(
                        Constants.NUMBER_VARIABLES+(Constants.NUMBER_VARIABLES/2));
                mRawSQL="UPDATE app_users_activity_variable_values " +
                        "SET number_correct=" + mPerfectScore +" , " +
                        "number_correct_in_a_row=" + mPerfectScore+ " " +
                        "WHERE "+mWhere;
                mDatabase.execSQL(mRawSQL);
                break;
            }
            case ACTIVITY_USER_RW_ALL_UPDATE_NO_INCORRECT:{
                mRawSQL="UPDATE app_users_activity_variable_values " +
                        "SET number_correct=number_correct, " +
                        "number_correct_in_a_row=number_correct_in_a_row+1 " +
                        "WHERE "+mWhere;
                mDatabase.execSQL(mRawSQL);
                break;
            }
            case UPDATE_BOOK_READ:{
                mRawSQL="UPDATE books " +
                        "SET book_read=1 " +
                        "WHERE "+mWhere;
                mDatabase.execSQL(mRawSQL);
                break;
            }
            case UPDATE_BOOK_LEVEL:{
                mRawSQL="SELECT app_user_current_level " +
                        "FROM app_users_current_gpl " +
                        "WHERE app_users_current_gpl.group_id=5 " +
                        "AND app_users_current_gpl.phase_id=1  ";
                mCursor = mDatabase.rawQuery(mRawSQL,null);
                int mCurrentLevel=1;
                if(mCursor.moveToFirst()){
                    mCurrentLevel=mCursor.getInt(mCursor.getColumnIndex("app_user_current_level"));
                }
                if(mCurrentLevel<8) {
                    mRawSQL = "UPDATE app_users_current_gpl " +
                            "SET app_user_current_level=app_user_current_level+1 " +
                            "WHERE group_id=5 " +
                            "AND phase_id=1  ";
                    mDatabase.execSQL(mRawSQL);
                }
                break;
            }
            case ACTIVITY_USER_ATTEMPTS:{
                mRawSQL="SELECT MAX(app_user_current_level) as current_level " +
                        "FROM app_users_current_gpl " +
                        "WHERE group_id=" + mInfo[0] + " " +
                        "AND phase_id=" + mInfo[1];
                mCursor = mDatabase.rawQuery(mRawSQL, null);

                if(mCursor.moveToFirst()){
                    String mLevel=mCursor.getString(mCursor.getColumnIndex("current_level"));
                    if(mLevel!=null){
                        mInfo[3]=mLevel;
                    }
                }


                mRawSQL="SELECT COUNT(_id) AS number_of " +
                        "FROM app_users_activity_use_info " +
                        "WHERE  group_id=" + mInfo[0] + " " +
                        "AND phase_id=" + mInfo[1] + " " +
                        "AND activity_id=" + mInfo[2] + " " +
                        "AND level_number= " + mInfo[3] + " " +
                        "AND app_user_id=" + mInfo[4] + " ";
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                mCursor.moveToFirst();
                if(Integer.parseInt(mCursor.getString(mCursor.getColumnIndex("number_of")))>0 &&
                        mInfo[5].equals("1")){
                    mRawSQL="UPDATE app_users_activity_use_info " +
                            "SET attempts=attempts+1 " +
                            "WHERE group_id=" + mInfo[0] + " " +
                            "AND phase_id=" + mInfo[1] + " " +
                            "AND activity_id=" + mInfo[2] + " " +
                            "AND level_number= " + mInfo[3] + " " +
                            "AND app_user_id=" + mInfo[4] + " ";
                    mDatabase.execSQL(mRawSQL);
                }else if(mInfo[5].equals("0") &&
                        mCursor.getString(mCursor.getColumnIndex("number_of")).equals("0")){


                    mRawSQL="INSERT INTO app_users_activity_use_info " +
                            "(group_id, phase_id, activity_id, level_number," +
                            "app_user_id,attempts)" +
                            "VALUES (" +
                            mInfo[0] + "," +
                            mInfo[1] + "," +
                            mInfo[2] + "," +
                            mInfo[3] + "," +
                            mInfo[4] + "," +
                            "1"+
                            ")";
                    mDatabase.execSQL(mRawSQL);
                }
                break;
            }
            case ACTIVITY_USER_RW_UPDATE_EVV:
                mRawSQL="SELECT * " +
                        "FROM app_users_variables_values " +
                        "WHERE variable_id=" + mInfo[0] + " " +
                        "AND type_id=" + mInfo[1] + " " +
                        "AND group_id=" + mInfo[2] + " " +
                        "AND phase_id=" + mInfo[3] + " " +
                        "AND activity_id=" + mInfo[4] + " " +
                        "AND app_user_id=" + mInfo[5] + " " +
                        "LIMIT 1";
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                if(mCursor.moveToFirst()){
                    mRawSQL="UPDATE app_users_variables_values " +
                            "SET ";
                    if(mInfo[7].equals("1")){
                        mRawSQL+="number_correct=number_correct+1 ";
                        if(Integer.parseInt(mInfo[8])>0){
                            mRawSQL+=",number_correct_in_a_row=number_correct_in_a_row+1 ";
                        }else{
                            mRawSQL+=",number_incorrect=number_incorrect+1 ";
                        }
                    }else{
                        mRawSQL+="number_incorrect=number_incorrect+1 ";
                    }
                    mRawSQL+="WHERE variable_id=" + mInfo[0] + " " +
                            "AND group_id=" + mInfo[2] + " " +
                            "AND phase_id=" + mInfo[3] + " " +
                            "AND activity_id=" + mInfo[4] + " " +
                            "AND app_user_id=" + mInfo[5] + " ";
                    mDatabase.execSQL(mRawSQL);
                }else{
                    ContentValues values = new ContentValues();
                    values.put("variable_id", mInfo[0]);
                    values.put("group_id",mInfo[1]);
                    values.put("group_id",mInfo[2]);
                    values.put("phase_id",mInfo[3]);
                    values.put("activity_id",mInfo[4]);
                    values.put("app_user_id",mInfo[5]);
                    values.put("level_number",mInfo[6]);
                    if(mInfo[7].equals("1")){
                        values.put("number_correct","1");
                        if(Integer.parseInt(mInfo[8])>0){
                            values.put("number_incorrect","0");
                            values.put("number_correct_in_a_row","1");
                        }else{
                            values.put("number_correct_in_a_row","0");
                            values.put("number_incorrect","0");
                        }
                    }else{
                        values.put("number_correct","0");
                        values.put("number_correct_in_a_row","0");
                        values.put("number_incorrect","1");
                    }
                    long current_id =mDatabase.insert(
                            "app_users_variables_values", null, values);
                    mRawSQL="UPDATE app_users_variables_values " +
                            "SET auvv_id="+String.valueOf(current_id)
                            +" WHERE _id="+String.valueOf(current_id);
                    mDatabase.execSQL(mRawSQL);
                }
                break;
            case ACTIVITY_USER_RW_UPDATE_VV:{
                int mCorrectInARow;
                mRawSQL="SELECT number_correct_in_a_row " +
                        "FROM app_users_variables_values " +
                        mWhere;
                mCursor = mDatabase.rawQuery(mRawSQL, null);

                if(mCursor.moveToFirst()){
                    if(mCursor.getString(
                            mCursor.getColumnIndex("number_correct_in_a_row"))!=null) {
                        mCorrectInARow = mCursor.getInt(
                                mCursor.getColumnIndex("number_correct_in_a_row"));

                        mRawSQL = "UPDATE app_users_variables_values " +
                                "SET number_correct=number_correct+" + mInfo[0] + ", " +
                                "number_incorrect=number_incorrect+" + mInfo[1];

                        if(mCorrectInARow<=Constants.MAX_CORRECT_INA_ROW){
                            if (mInfo[0].equals("1") && mInfo[4].equals("0")) {
                                mRawSQL += ", number_correct_in_a_row=number_correct_in_a_row+1 ";
                            } else if(mCorrectInARow>0 && Integer.parseInt(mInfo[4])<2) {
                                mRawSQL += ", number_correct_in_a_row=number_correct_in_a_row-1";
                            }else if(Integer.parseInt(mInfo[4])<2) {
                                mRawSQL += ", number_correct_in_a_row=0 ";
                            }
                        }
                        mRawSQL += mWhere;
                        mDatabase.execSQL(mRawSQL);

                        mRawSQL = "SELECT number_correct, " +
                                "number_correct_in_a_row, " +
                                "number_incorrect " +
                                "FROM app_users_variables_values " +
                                mWhere;
                        mCursor = mDatabase.rawQuery(mRawSQL, null);
                        mCursor.moveToFirst();
                        int mNumberCorrect
                                = mCursor.getInt(mCursor.getColumnIndex("number_correct"));
                        int mNumberCorrectInARow
                                = mCursor.getInt(mCursor.getColumnIndex("number_correct_in_a_row"));
                        int mNumberInCorrect
                                = mCursor.getInt(mCursor.getColumnIndex("number_incorrect"));

                        int mInCorrectReduce = (mNumberCorrectInARow == 3 && mNumberCorrect
                                > 5 && mNumberInCorrect > 0) ? 1 : 0;

                        mRawSQL = "UPDATE app_users_variables_values " +
                                "SET number_incorrect=number_incorrect-" + mInCorrectReduce + " " +
                                mWhere;
                        mDatabase.execSQL(mRawSQL);
                    }
                }
                mCursor.close();
                break;
            }
            case ACTIVITY_USER_RW_UPDATE:{
                int mCorrectInARow;
                mRawSQL="SELECT number_correct_in_a_row " +
                        "FROM app_users_activity_variable_values " +
                        mWhere;
                mCursor = mDatabase.rawQuery(mRawSQL, null);

                if(mCursor.moveToFirst()){
                    if(mCursor.getString(
                            mCursor.getColumnIndex("number_correct_in_a_row"))!=null) {
                        mCorrectInARow = mCursor.getInt(
                                mCursor.getColumnIndex("number_correct_in_a_row"));

                        mRawSQL = "UPDATE app_users_activity_variable_values " +
                                "SET number_correct=number_correct+" + mInfo[0] + ", " +
                                "number_incorrect=number_incorrect+" + mInfo[1];

                        if(mCorrectInARow<=Constants.MAX_CORRECT_INA_ROW){
                            if (mInfo[0].equals("1") && mInfo[4].equals("0")) {
                                mRawSQL += ", number_correct_in_a_row=number_correct_in_a_row+1 ";
                            } else if(mCorrectInARow>0 && Integer.parseInt(mInfo[4])<2) {
                                mRawSQL += ", number_correct_in_a_row=number_correct_in_a_row-1";
                            }else if(Integer.parseInt(mInfo[4])<2) {
                                mRawSQL += ", number_correct_in_a_row=0 ";
                            }
                        }
                        /*
                        if(mValues!=null && mValues.containsKey("activity_id")
                                && mValues.get("activity_id").equals("59")
                                && mCorrectInARow < Constants.INA_ROW_CORRECT_HIGHER){
                            if (mInfo[0].equals("1") && mInfo[4].equals("0")) {
                                mRawSQL += ", number_correct_in_a_row=number_correct_in_a_row+1 ";
                            } else if(mCorrectInARow>0) {
                                mRawSQL += ", number_correct_in_a_row=number_correct_in_a_row-1";
                            }else {
                                mRawSQL += ", number_correct_in_a_row=0 ";
                            }
                        }else if (mCorrectInARow <= Constants.INA_ROW_CORRECT) {
                            if (mInfo[0].equals("1") && mInfo[4].equals("0")) {
                                mRawSQL += ", number_correct_in_a_row=number_correct_in_a_row+1 ";
                            } else {
                                mRawSQL += ", number_correct_in_a_row=0 ";
                            }
                        }
                        */
                        mRawSQL += mWhere;
                        mDatabase.execSQL(mRawSQL);

                        mRawSQL = "SELECT number_correct, " +
                                "number_correct_in_a_row, " +
                                "number_incorrect " +
                                "FROM app_users_activity_variable_values " +
                                mWhere;
                        mCursor = mDatabase.rawQuery(mRawSQL, null);
                        mCursor.moveToFirst();
                        int mNumberCorrect
                                = mCursor.getInt(mCursor.getColumnIndex("number_correct"));
                        int mNumberCorrectInARow
                                = mCursor.getInt(mCursor.getColumnIndex("number_correct_in_a_row"));
                        int mNumberInCorrect
                                = mCursor.getInt(mCursor.getColumnIndex("number_incorrect"));

                        int mInCorrectReduce = (mNumberCorrectInARow == 3 && mNumberCorrect
                                > 5 && mNumberInCorrect > 0) ? 1 : 0;

                        mRawSQL = "UPDATE app_users_activity_variable_values " +
                                "SET number_incorrect=number_incorrect-" + mInCorrectReduce + " " +
                                mWhere;
                        mDatabase.execSQL(mRawSQL);
                    }
                }
                mCursor.close();
                //	mDatabase.update("app_users_activity_variable_values", mValues, mWhere, null);
                break;
            }
            case ACTIVITY_USER_RW_UPDATE_MS:{
                int mCorrectInARow;
                int mCorrectCorrect;
                int mFirstCount=0;
                int mSecondCount=0;


                mRawSQL="SELECT number_correct_in_a_row "
                        +" FROM app_users_activity_variable_values "+mWhere;
                mCursor = mDatabase.rawQuery(mRawSQL, null);

                if(mCursor.moveToFirst()){
                    if(mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row"))!=null) {
                        mCorrectInARow = mCursor.getInt(mCursor.getColumnIndex("number_correct_in_a_row"));

                        if ( mInfo[0].equals("1")) {
                            mInfo[0] = "2";
                        }

                        mRawSQL = "UPDATE app_users_activity_variable_values " +
                                "SET number_correct=number_correct+" + mInfo[0] + ", " +
                                "number_incorrect=number_incorrect+" + mInfo[1];

                        if (mCorrectInARow < Constants.INA_ROW_CORRECT
                                || mInfo[0].equals("2")) {
                            if (mInfo[0].equals("1") && mInfo[4].equals("0")) {
                                mRawSQL += ", number_correct_in_a_row=number_in_a_correct+1 ";
                            } else if (mInfo[0].equals("2")) {
                                mRawSQL += ", number_correct_in_a_row=2";
                            } else {
                                mRawSQL += ", number_correct_in_a_row=0 ";
                            }
                        }
                        mRawSQL += mWhere;
                        mDatabase.execSQL(mRawSQL);

                        mRawSQL = "SELECT number_correct, " +
                                "number_correct_in_a_row, " +
                                "number_incorrect " +
                                "FROM app_users_activity_variable_values " +
                                mWhere;
                        mCursor = mDatabase.rawQuery(mRawSQL, null);
                        mCursor.moveToFirst();
                        int mNumberCorrect = mCursor.getInt(mCursor.getColumnIndex("number_correct"));
                        int mNumberCorrectInARow = mCursor.getInt(mCursor.getColumnIndex("number_correct_in_a_row"));
                        int mNumberInCorrect = mCursor.getInt(mCursor.getColumnIndex("number_incorrect"));

                        int mInCorrectReduce = (mNumberCorrectInARow == 3 && mNumberCorrect > 5 && mNumberInCorrect > 0) ? 1 : 0;

                        mRawSQL = "UPDATE app_users_activity_variable_values " +
                                "SET number_incorrect=number_incorrect-" + mInCorrectReduce + " " +
                                mWhere;
                        mDatabase.execSQL(mRawSQL);
                    }
                }

                mRawSQL="SELECT COUNT(av2.number_correct_in_a_row) AS correct " +
                        "FROM app_users_activity_variable_values AS av2 " +
                        "WHERE av2.app_user_id="+mInfo[6]+" " +
                        "AND av2.activity_id="+mInfo[5]+" " +
                        "AND av2.number_correct_in_a_row>=1 " +
                        "UNION ALL " +
                        "SELECT COUNT(av1.auavv_id) AS correct " +
                        "FROM app_users_activity_variable_values AS av1 " +
                        "WHERE av1.app_user_id="+mInfo[6]+" " +
                        "AND av1.activity_id="+mInfo[5]+" ";


                mCursor=mDatabase.rawQuery(mRawSQL,null);
                if(mCursor.moveToFirst()){
                    do{
                        mFirstCount=mCursor.getInt(mCursor.getColumnIndex("correct"));
                    }while(mCursor.moveToNext());
                }

                mCorrectCorrect=mSecondCount-mFirstCount;
                if(mCorrectCorrect<=4){
                    mRawSQL="UPDATE app_users_activity_variable_values " +
                            "SET number_correct_in_a_row=1," +
                            "number_correct=number_correct+1 " +
                            "WHERE app_user_id="+mInfo[6]+" " +
                            "AND activity_id="+mInfo[5]+" " +
                            "AND number_correct_in_a_row==0";
                    mDatabase.execSQL(mRawSQL);
                }
                mCursor.close();
                //	mDatabase.update("app_users_activity_variable_values", mValues, mWhere, null);
                break;
            }
        }

        return 0;
    }

}
