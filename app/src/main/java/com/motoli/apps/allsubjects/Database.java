package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 *
 * This class handles the creation of the database tables that are different for each device
 * and that are used throughout the program.
 * It pulls at first from a sqlite database in the asset folder.
 * It is able to update the database when the the version number in the sqlite file is higher
 * also in S$K_DBHelper the verion number is set higher then what is currently in the
 * Application it will call the update functions.
 * It only holds on to the user data so the user does not have to begin again.
 */



import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.provider.BaseColumns;
import android.util.Log;

public final class Database {



    public Database() {    }


    ////////////////////////////////////////////////////////////////////////

    public static void onCreate(SQLiteDatabase db){
        App_Users.onCreate(db);
        App_Users.createGuestUser(db);

        Log.d(Constants.LOGCAT, "App_Users.onCreate(db)");

        Settings.onCreate(db);
        Log.d(Constants.LOGCAT, "Settings.onCreate(db)");

        App_Users_Current_GPL.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users_Current_GPL.onCreate(db)");

        App_Users_Activity_Variable_Values.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users_Activity_Variable_Values.onCreate(db)");

        App_Users_Activity_Use_Info.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users_Activity_Use_Info.onCreate(db)");

        App_Users_Variables_Values.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users_Variables_Values.onCreate(db)");

    }


    public static void addNew(SQLiteDatabase db){
        App_Users.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users.onCreate(db)");

        App_Users_Current_GPL.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users_Current_GPL.onCreate(db)");

        App_Users_Activity_Variable_Values.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users_Activity_Variable_Values.onCreate(db)");

        App_Users_Activity_Use_Info.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users_Activity_Use_Info.onCreate(db)");

        App_Users_Variables_Values.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users_Variables_Values.onCreate(db)");

        Settings.onCreate(db);
        Settings.createSettings(db);
        Log.d(Constants.LOGCAT, "Settings.onCreate(db)");

        App_Users_Current_GPL.setNewUserCurrentGPL(db, 0);
        Log.d(Constants.LOGCAT, "App_Users_Current_GPL.doUpDate(db, mMyDataBase);");


    }



    public static void updateDB(SQLiteDatabase mDatabase, Context mContext, String mDBOldName){

        SQLiteDatabase mDatabaseOld;


        File dbFile = mContext.getDatabasePath(mDBOldName);

        mDatabaseOld = SQLiteDatabase.openOrCreateDatabase(dbFile,null);

        App_Users.doUpDate(mDatabase, mDatabaseOld);
        Log.d(Constants.LOGCAT, "App_Users.doUpDate(mDatabase, mMyDataBase);");

        App_Users_Activity_Use_Info.doUpDate(mDatabase, mDatabaseOld);
        Log.d(Constants.LOGCAT, "App_Users_Activity_Use_Info.doUpDate(mDatabase, mDatabaseOld);");

        App_Users_Activity_Variable_Values.doUpDate(mDatabase, mDatabaseOld);
        Log.d(Constants.LOGCAT, "App_Users_Activity_Variable_Values.doUpDate(mDatabase, mDatabaseOld);");

        App_Users_Current_GPL.doUpDate(mDatabase, mDatabaseOld);
        Log.d(Constants.LOGCAT, "App_Users_Current_GPL.doUpDate(mDatabase, mDatabaseOld);");

        Settings.doUpDate(mDatabase, mDatabaseOld);
        Log.d(Constants.LOGCAT, "Settings.doUpDate(mDatabase, mDatabaseOld);");

        App_Users_Variables_Values.doUpDate(mDatabase, mDatabaseOld);
        Log.d(Constants.LOGCAT, "App_Users_Variables_Values.doUpDate(mDatabase, mDatabaseOld)");

    }




    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   settings TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Settings implements BaseColumns{
        public Settings(){}

        public static final String SETTINGS_CREATE="CREATE TABLE IF NOT EXISTS settings (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "settings_id INTEGER, " +
                "setting TEXT, " +
                "setting_data TEXT );";

        public static void onCreate(SQLiteDatabase db) {
            db.execSQL(SETTINGS_CREATE);

        }

        public static void createSettings(SQLiteDatabase db){
            ContentValues initialValues = new ContentValues();

            initialValues.put("_id",1);
            initialValues.put("settings_id", 1);
            initialValues.put("setting", "sound_effects");
            initialValues.put("setting_data", "true");
            db.insert("settings", null, initialValues);

            initialValues.put("_id",2);
            initialValues.put("settings_id", 2);
            initialValues.put("setting", "create all");
            initialValues.put("setting_data", "true");

            db.insert("settings", null, initialValues);
        }


        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS settings");
            onCreate(mDatabase);

            Cursor mCursor=mDatabaseOld.rawQuery("SELECT * FROM settings", null);
            if(mCursor.moveToFirst()) {
                do {
                    String updateSQL = "INSERT INTO settings " +
                            "(_id, settings_id, setting, setting_data ) " +
                            "VALUES (";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("settings_id")) + ",";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("setting")) + "',";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("setting_data")) + "')";
                    mDatabase.execSQL(updateSQL);

                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
    }



    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   APP USERS TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class App_Users implements BaseColumns{

        public App_Users(){ }



        public static final String APP_USERS_CREATE="CREATE TABLE  IF NOT EXISTS app_users ( " +
                "_id INTEGER PRIMARY KEY, " +
                "app_user_id INTEGER, " +
                "app_user_name TEXT, " +
                "app_user_avatar TEXT, " +
                "app_user_fn TEXT, " +
                "app_user_ln TEXT, " +
                "app_user_teacher TEXT, " +
                "app_user_school TEXT, " +
                "app_user_gender INTEGER, " +
                "app_user_long TEXT, " +
                "app_user_lat TEXT, " +
                "app_user_device TEXT, " +
                "app_user_age INTEGER, " +
                "app_user_grade TEXT )";


        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(APP_USERS_CREATE);
            createGuestUser(db);
        }

        //////////////////////////////////////////////////////////////////////


        public static void createGuestUser(SQLiteDatabase db){
            Cursor c=db.rawQuery("SELECT * FROM app_users WHERE app_user_id=0",null);
            if(!c.moveToFirst()){
                String mSQL="INSERT INTO app_users  (_id, app_user_id, app_user_name, " +
                        "app_user_avatar, app_user_fn, app_user_ln, app_user_teacher, " +
                        " app_user_school, app_user_gender, app_user_long, app_user_lat, " +
                        " app_user_device, app_user_age, app_user_grade) " +
                        "VALUES (0,0, '"+Constants.GUEST_NAME+"', '0','','',''," +
                        "'',0,'',''," +
                        "'',0,'')";
                db.execSQL(mSQL);

            }
            c.close();
        }


        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS app_users");
            onCreate(mDatabase);
            


            Cursor mCursor=mDatabaseOld.rawQuery("SELECT * FROM app_users", null);
            if(mCursor.moveToFirst()) {
                do{
                    String mSQL="SELECT COUNT(_id) AS count_id " +
                            "FROM  app_users " +
                            "WHERE app_user_id=" + mCursor.getString(mCursor.getColumnIndex("_id"));
                    Cursor mCursorDuplicate = mDatabase.rawQuery(mSQL,null);
                    if(mCursorDuplicate.moveToFirst()){
                        if(mCursorDuplicate.getInt(mCursorDuplicate.getColumnIndex("count_id"))==0){
                            String updateSQL = "INSERT INTO app_users (_id, app_user_id, " +
                                    "app_user_name, app_user_avatar, app_user_fn, app_user_ln, " +
                                    "app_user_teacher, app_user_school, app_user_gender, " +
                                    "app_user_long, app_user_lat, app_user_device, app_user_age, " +
                                    "app_user_grade )VALUES (";
                            updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_id")) + ",";
                            updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_id")) + ",";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_name")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_avatar")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_fn")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_ln")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_teacher")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_school")) + "',";
                            updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_gender")) + ",";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_long")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_lat")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_device")) + "',";
                            updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_age")) + ",";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_grade")) + "')";
                            mDatabase.execSQL(updateSQL);
                        }
                    }
                }while(mCursor.moveToNext());
            }
            mCursor.close();
        }


    }


    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   app_users_activity_variables_values TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////


    public static final class App_Users_Activity_Variable_Values implements BaseColumns{

        public App_Users_Activity_Variable_Values(){}



        public static final String AUWARW_CREATE="CREATE TABLE app_users_activity_variable_values ("+
                "_id INTEGER PRIMARY KEY, " +
                "auavv_id INTEGER, " +
                "variable_id INTEGER, " +
                "type_id INTEGER, " +
                "group_id INTEGER, " +
                "phase_id INTEGER, " +
                "activity_id INTEGER, " +
                "app_user_id INTEGER, " +
                "level_number INTEGER, " +
                "number_correct INTEGER, " +
                "number_correct_in_a_row INTEGER, "+
                "number_incorrect INTEGER,"+
                "UNIQUE (variable_id,type_id,activity_id,level_number,app_user_id) )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(AUWARW_CREATE);
        }


        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS app_users_activity_variable_values");
            onCreate(mDatabase);

            Cursor mCursor=mDatabaseOld.rawQuery("SELECT * FROM app_users_activity_variable_values", null);
            if(mCursor.moveToFirst()){
                do {
                    String updateSQL = "INSERT INTO app_users_activity_variable_values (_id, auavv_id," +
                            " variable_id, type_id, group_id, phase_id, activity_id, " +
                            "level_number, app_user_id, number_correct, " +
                            "number_correct_in_a_row, number_incorrect )" +
                            "VALUES (";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("auavv_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("auavv_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("variable_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("type_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("group_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("phase_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("activity_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("level_number")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("number_correct")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("number_incorrect")) + ")";
                    mDatabase.execSQL(updateSQL);

                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
    }


    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   app_users_variables_values TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////


    public static final class App_Users_Variables_Values implements BaseColumns{

        public App_Users_Variables_Values(){}



        public static final String AUWARW_CREATE="CREATE TABLE app_users_variables_values ("+
                "_id INTEGER PRIMARY KEY, " +
                "auvv_id INTEGER, " +
                "variable_id INTEGER, " +
                "type_id INTEGER, " +
                "group_id INTEGER, " +
                "phase_id INTEGER, " +
                "activity_id INTEGER, " +
                "app_user_id INTEGER, " +
                "level_number INTEGER, " +
                "number_correct INTEGER, " +
                "number_correct_in_a_row INTEGER, "+
                "number_incorrect INTEGER,"+
                "UNIQUE (variable_id,type_id,activity_id,level_number,app_user_id) )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(AUWARW_CREATE);
        }


        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS app_users_variables_values");
            onCreate(mDatabase);

            Cursor mCursor=mDatabaseOld.rawQuery("SELECT * FROM app_users_variables_values", null);
            if(mCursor.moveToFirst()){
                do {
                    String updateSQL = "INSERT INTO app_users_variables_values (_id, auvv_id," +
                            " variable_id, type_id, group_id, phase_id, activity_id, " +
                            "level_number, app_user_id, number_correct, " +
                            "number_correct_in_a_row, number_incorrect )" +
                            "VALUES (";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("auvv_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("auvv_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("variable_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("type_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("group_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("phase_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("activity_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("level_number")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("number_correct")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("number_correct_in_a_row")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("number_incorrect")) + ")";
                    mDatabase.execSQL(updateSQL);

                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
    }



    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   app_users_activity_use_info TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class App_Users_Activity_Use_Info implements BaseColumns {

        public App_Users_Activity_Use_Info() {}

        public static final String AUAUI_CREATE = "CREATE TABLE app_users_activity_use_info (" +
                "_id INTEGER PRIMARY KEY, " +
                "type_id INTEGER, " +
                "group_id INTEGER, " +
                "phase_id INTEGER, " +
                "activity_id INTEGER, " +
                "level_number INTEGER, " +
                "app_user_id INTEGER, " +
                "attempts INTEGER, " +
                "UNIQUE (level_number,type_id,activity_id,app_user_id) )";

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db) {
            db.execSQL(AUAUI_CREATE);
        }


        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS app_users_activity_use_info");
            onCreate(mDatabase);

            Cursor mCursor=mDatabaseOld.rawQuery("SELECT * FROM app_users_activity_use_info", null);
            if(mCursor.moveToFirst()) {
                do {
                    String updateSQL = "INSERT INTO app_users_activity_use_info (_id, " +
                            "type_id, activity_id, level_number, app_user_id, attempts )" +
                            "VALUES (";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("type_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("group_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("phase_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("activity_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("level_number")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("attempts")) + ")";
                    mDatabase.execSQL(updateSQL);

                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }

    }


    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   USERS CURRENT GROUP PHASE LEVEL TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class App_Users_Current_GPL implements BaseColumns{

        public App_Users_Current_GPL(){ }


        public static final String APP_USERS_CURRENT_GPL_CREATE="CREATE TABLE " +
                "IF NOT EXISTS app_users_current_gpl ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "app_user_id INTEGER, " +
                "group_id INTEGER, " +
                "phase_id INTEGER, " +
                "app_user_current_level  INTEGER )";


        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(APP_USERS_CURRENT_GPL_CREATE);
        }

        //////////////////////////////////////////////////////////////////////


        public static void setNewUserCurrentGPL(SQLiteDatabase db, int app_user_id){
            String query="SELECT * FROM groups_phase";
            Cursor mCursor=db.rawQuery(query, null);

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
                            "VALUES(" + app_user_id + "," + group_id + "," +
                            phase_id + "," + current_level + ")";
                    db.execSQL(insertQuery);
                }
            }
            mCursor.close();
        }


        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS app_users_current_gpl");
            onCreate(mDatabase);

            Cursor cursor=mDatabaseOld.rawQuery("SELECT * FROM app_users_current_gpl", null);
            if (cursor.moveToFirst()) {
                do {
                    String updateSQL = "INSERT INTO app_users_current_gpl (_id, app_user_id, " +
                            "group_id, phase_id, app_user_current_level ) VALUES (";
                    updateSQL += cursor.getString(cursor.getColumnIndex("_id")) + ",";
                    updateSQL += cursor.getString(cursor.getColumnIndex("app_user_id")) + ",";
                    updateSQL += cursor.getString(cursor.getColumnIndex("group_id")) + ",";
                    updateSQL += cursor.getString(cursor.getColumnIndex("phase_id")) + ",";
                    updateSQL += cursor.getString(cursor.getColumnIndex("app_user_current_level")) + ")";
                    mDatabase.execSQL(updateSQL);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }


    }

}
