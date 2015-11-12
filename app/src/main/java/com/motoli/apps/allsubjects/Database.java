package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.provider.BaseColumns;

public final class Database {



    private Database() {
        //  Auto-generated constructor stub
        // ((Motoli_Application) getApplicationContext());


    }


    ////////////////////////////////////////////////////////////////////////

    public static void onCreate(SQLiteDatabase db){
        App_Users.onCreate(db);

        Groups.onCreate(db);
        Groups_Phase.onCreate(db);
        Groups_Phase_Levels.onCreate(db);
        Groups_Phase_Sections.onCreate(db);
        Groups_Phase_Sections_Activities.onCreate(db);

        Sections.onCreate(db);

        Phonics.onCreate(db);
        Phonic_Words.onCreate(db);

        Syllables.onCreate(db);
        Syllable_Words.onCreate(db);

        Books.onCreate(db);
        Book_Pages.onCreate(db);

        Levels.onCreate(db);
        Letters.onCreate(db);
        Level_Section_Activity_Relation.onCreate(db);

        Activities.onCreate(db);



        Words.onCreate(db);
        Variable_Phase_Levels.onCreate(db);

        App_Users_Current_GPL.onCreate(db);
        App_Users_Activity_Variable_Values.onCreate(db);
        Users_Words_Activities_RW_Order.onCreate(db);

        VariableTypeRelations.onCreate(db);
        /*
        Activities.upDate(db, myDataBase);
*/
    }

    public static void updataDB(SQLiteDatabase db, Context context, String DB_NAME_TEMP){
        // Motoli_Application appData;
        SQLiteDatabase myDataBase;
        //appData =(Motoli_Application) Motoli_Application.getAppContext();
        String DB_PATH = context.getFilesDir().getPath()+ context.getPackageName() + "/" + "databases/";
        String myPath = DB_PATH + DB_NAME_TEMP;

        File dbFile = context.getDatabasePath(DB_NAME_TEMP);
        //dbFile.exists();

        myDataBase = SQLiteDatabase.openOrCreateDatabase(dbFile,null);
        //.openDatabase(myPath, null,SQLiteDatabase.OPEN_READONLY);


        Activities.doUpDate(db, myDataBase);



        Groups_Phase.doUpDate(db, myDataBase);
        Groups_Phase_Levels.doUpDate(db, myDataBase);
        Groups_Phase_Sections.doUpDate(db, myDataBase);
        Groups_Phase_Sections_Activities.doUpDate(db, myDataBase);

        Sections.doUpDate(db, myDataBase);

        Phonics.doUpDate(db, myDataBase);
        Phonic_Words.doUpDate(db, myDataBase);

        Syllables.doUpDate(db, myDataBase);
        Syllable_Words.doUpDate(db, myDataBase);

        Books.doUpDate(db, myDataBase);
        Book_Pages.doUpDate(db,myDataBase);

        Letters.doUpDate(db,myDataBase);
        Level_Section_Activity_Relation.doUpDate(db, myDataBase);

        Words.doUpDate(db, myDataBase);
        Variable_Phase_Levels.doUpDate(db, myDataBase);
        VariableTypeRelations.doUpDate(db, myDataBase);

        //this is for guest user alone
       App_Users_Current_GPL.setNewUserCurrentGPL(db, 0);
        //App_Users_Current_GPL.doUpDate(db, myDataBase);
       // App_Users_Activity_Variable_Values.doUpDate(db, myDataBase);

    //	appData.setActivities(Activities.getAllActivities(db));
    //	appData.setLevelSectionActivitiesRelations(Level_Section_Activity_Relation.getAllLevelSectionActivityRelations(db));
        //appData.

        myDataBase.close();
    }


    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   activities TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Activities implements BaseColumns{

        private Activities(){}

        public static final String ACTIVITIES_TABLE_NAME="activities";

        public static final String _ID="_id";
        public static final String ACTIVITY_ID = "activity_id";
        public static final String ACTIVITY_NUMBER = "activity_number";
        public static final String ACTIVITY_CODE = "activity_code";
        public static final String ACTIVITY_TYPE = "activity_type";
        public static final String ACTIVITY_NAME = "activity_name";
        public static final String ACTIVITY_IMG = "activity_img";
        public static final String ACTIVITY_ORDER = "activity_order";


        public static final String ACTIVITIES_CREATE="CREATE TABLE "+ACTIVITIES_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                ACTIVITY_ID + " INTEGER, " +
                ACTIVITY_NUMBER  + " INTEGER, " +
                ACTIVITY_IMG  + " TEXT, " +
                ACTIVITY_CODE  + " TEXT, " +
                ACTIVITY_TYPE  + " TEXT, " +
                ACTIVITY_NAME  + " TEXT,"+
                ACTIVITY_ORDER + " INTEGER )";


        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(ACTIVITIES_CREATE);
        }

        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + ACTIVITIES_TABLE_NAME);
            onCreate(db);

            String query="SELECT * FROM "+ACTIVITIES_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+ACTIVITIES_TABLE_NAME+" ("+_ID+","+ACTIVITY_ID+","+
                        ACTIVITY_NUMBER+","+ACTIVITY_IMG+","+ACTIVITY_CODE+","+ACTIVITY_TYPE+","+ACTIVITY_NAME+","+ACTIVITY_ORDER+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(ACTIVITY_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(ACTIVITY_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(ACTIVITY_NUMBER))+",";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(ACTIVITY_IMG))+"',";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(ACTIVITY_CODE))+"',";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(ACTIVITY_TYPE))+"',";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(ACTIVITY_NAME))+"',";
                updateSQL+=cursor.getString(cursor.getColumnIndex(ACTIVITY_ORDER))+")";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static Cursor getAllActivities(SQLiteDatabase db){
            return db.rawQuery("SELECT * FROM "+ACTIVITIES_TABLE_NAME,null);
        }
        */
    }

    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   groups TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Groups implements BaseColumns{

        private Groups(){}

        public static final String GROUPS_TABLE_NAME="groups";

        public static final String _ID="_id";
        public static final String GROUP_ID= "group_id";
        public static final String GROUP_NAME= "group_name";
        public static final String GROUP_CODE= "group_code";


        public static final String GROUPS_CREATE="CREATE TABLE  IF NOT EXISTS "+GROUPS_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY , " +
                GROUP_ID + " INTEGER, " +
                GROUP_NAME  + " TEXT, " +
                GROUP_CODE  + " TEXT );";

        public static final ArrayList<String> DEFAULT_GROUP_IDS = new ArrayList<String>(Arrays.asList("1", "2", "3","4","5","6","7","8"));
        public static final ArrayList<String> DEFAULT_GROUP_NAMES = new ArrayList<String>(Arrays.asList("songs", "letters", "syllables","words","books","drawings","math","other"));
        public static final ArrayList<String> DEFAULT_GROUP_CODE = new ArrayList<String>(Arrays.asList("sg", "le", "sy","wd","bk","dr","mt","ot"));



        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(GROUPS_CREATE);

            for(int i=0; i<DEFAULT_GROUP_IDS.size();i++){
                db.execSQL("INSERT INTO "+GROUPS_TABLE_NAME+"  ("+_ID+","+GROUP_ID+","+
                        GROUP_NAME+" " +	", "+GROUP_CODE+" ) "+
                        "Values ("+DEFAULT_GROUP_IDS.get(i)+","+DEFAULT_GROUP_IDS.get(i)
                        +", '"+DEFAULT_GROUP_NAMES.get(i)+"', '"+DEFAULT_GROUP_CODE.get(i)+"')");

            }
            
        }

        ////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + GROUPS_TABLE_NAME);
            onCreate(db);
        }
        */
    }


    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   groups_phase TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Groups_Phase implements BaseColumns{

        private Groups_Phase(){}

        public static final String GROUPS_PHASE_TABLE_NAME="groups_phase";

        public static final String _ID="_id";
        public static final String GROUPS_PHASE_ID= "groups_phase_id";
        public static final String GROUP_ID= "group_id";
        public static final String PHASE_ID="phase_id";
        public static final String GROUP_PHASE_NAME= "groups_phase_name";


        public static final String GROUPS_PHASE_CREATE="CREATE TABLE "+GROUPS_PHASE_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                GROUPS_PHASE_ID + " INTEGER, " +
                GROUP_ID  + " INTEGER, " +
                PHASE_ID  + " INTEGER, " +
                GROUP_PHASE_NAME  + " TEXT )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(GROUPS_PHASE_CREATE);
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + GROUPS_PHASE_TABLE_NAME);
            onCreate(db);
        }
        */
        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + GROUPS_PHASE_TABLE_NAME);
            onCreate(db);

            String query="SELECT * FROM "+GROUPS_PHASE_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+GROUPS_PHASE_TABLE_NAME+" ("+_ID+","+GROUPS_PHASE_ID+","+
                        GROUP_ID+","+PHASE_ID+","+GROUP_PHASE_NAME+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(GROUPS_PHASE_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(GROUP_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(PHASE_ID))+",";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(GROUP_PHASE_NAME))+"')";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }


    }




    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   groups_levels TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Groups_Phase_Levels implements BaseColumns{

        private Groups_Phase_Levels(){}

        public static final String GROUPS_PHASE_LEVELS_TABLE_NAME="groups_phase_levels";

        public static final String _ID="_id";
        public static final String GPL_ID= "gpl_id";
        public static final String GROUP_ID= "group_id";
        public static final String PHASE_ID="phase_id";
        public static final String LEVEL_NUMBER= "level_number";
        public static final String GROUP_PHASE_LEVEL_NAME= "group_phase_level_name";
        public static final String GROUP_PHASE_LEVEL_DESCRIPTION= "group_phase_level_description";
        public static final String GROUP_PHASE_LEVEL_APP_TITLE= "group_phase_level_app_title";

        public static final String GROUPS_PHASE_LEVLES_CREATE="CREATE TABLE "+GROUPS_PHASE_LEVELS_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                GPL_ID + " INTEGER, " +
                GROUP_ID  + " INTEGER, " +
                PHASE_ID  + " INTEGER, " +
                LEVEL_NUMBER  + " INTEGER, " +
                GROUP_PHASE_LEVEL_NAME  + " TEXT, " +
                GROUP_PHASE_LEVEL_DESCRIPTION  + " TEXT, " +
                GROUP_PHASE_LEVEL_APP_TITLE  + " TEXT )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(GROUPS_PHASE_LEVLES_CREATE);
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + GROUPS_PHASE_LEVELS_TABLE_NAME);
            onCreate(db);
        }
        */
        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + GROUPS_PHASE_LEVELS_TABLE_NAME);
            onCreate(db);

            String query="SELECT * FROM "+GROUPS_PHASE_LEVELS_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+GROUPS_PHASE_LEVELS_TABLE_NAME+" ("+_ID+","+GPL_ID+","+
                        GROUP_ID+","+PHASE_ID+","+LEVEL_NUMBER+","+GROUP_PHASE_LEVEL_NAME+","
                        +GROUP_PHASE_LEVEL_DESCRIPTION+","+GROUP_PHASE_LEVEL_APP_TITLE+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(GPL_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(GROUP_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(PHASE_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(LEVEL_NUMBER))+",";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(GROUP_PHASE_LEVEL_NAME))+"',";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(GROUP_PHASE_LEVEL_DESCRIPTION))+"',";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(GROUP_PHASE_LEVEL_APP_TITLE))+"')";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }


    }






    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   groups_sections TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Groups_Phase_Sections implements BaseColumns{

        private Groups_Phase_Sections(){}

        public static final String GROUPS_PHASE_SECTIONS_TABLE_NAME="groups_phase_sections";

        public static final String _ID="_id";
        public static final String GLS_ID= "gls_id";
        public static final String GROUP_ID= "group_id";
        public static final String PHASE_ID="phase_id";
        public static final String SECTION_ID= "section_id";
        //public static final String ALLOWED_WHEN= "allowed_when";

        public static final String GROUPS_PHASE_SECTIONS_CREATE="CREATE TABLE "+GROUPS_PHASE_SECTIONS_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                GLS_ID + " INTEGER, " +
                GROUP_ID  + " INTEGER, " +
                PHASE_ID  + " INTEGER, " +
                SECTION_ID  + " INTEGER )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(GROUPS_PHASE_SECTIONS_CREATE);
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + GROUPS_PHASE_LEVELS_SECTIONS_TABLE_NAME);
            onCreate(db);
        }
        */
        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + GROUPS_PHASE_SECTIONS_TABLE_NAME);
            onCreate(db);
            String query="SELECT * FROM "+GROUPS_PHASE_SECTIONS_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+GROUPS_PHASE_SECTIONS_TABLE_NAME+" ("+_ID+","+GLS_ID+","+
                        GROUP_ID+","+PHASE_ID+","+SECTION_ID+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(GLS_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(GROUP_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(PHASE_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(SECTION_ID))+")";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }


    }




    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   groups_phase_sections_activities TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Groups_Phase_Sections_Activities implements BaseColumns{

        private Groups_Phase_Sections_Activities(){}

        public static final String GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME="groups_phase_sections_activities";

        public static final String _ID="_id";
        public static final String GPSA_ID= "gpsa_id";
        public static final String GROUP_ID= "group_id";
        public static final String PHASE_ID="phase_id";
        public static final String SECTION_ID= "section_id";
        public static final String ACTIVITY_ID= "activity_id";

        public static final String GROUPS_PHASE_LEVELS_SECTIONS_CREATE="CREATE TABLE "+GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                GPSA_ID + " INTEGER, " +
                GROUP_ID  + " INTEGER, " +
                PHASE_ID  + " INTEGER, " +
                SECTION_ID  + " INTEGER, " +
                ACTIVITY_ID  + " INTEGER )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(GROUPS_PHASE_LEVELS_SECTIONS_CREATE);
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME);
            onCreate(db);
        }
        */
        //////////////////////////////////////////////////////////////////////


        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME);
            onCreate(db);
            String query="SELECT * FROM "+GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME+" ("+_ID+","+GPSA_ID+","+
                        GROUP_ID+","+PHASE_ID+","+SECTION_ID+","+ACTIVITY_ID+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(GPSA_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(GROUP_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(PHASE_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(SECTION_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(ACTIVITY_ID))+")";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public ArrayList<ArrayList<String>> getGPSAForSection(SQLiteDatabase db,int group_id, int phase_id, int section_id){
            ArrayList<ArrayList<String>> GPSAs=new ArrayList<ArrayList<String>>();
            Cursor cursor=null;
            String query="SELECT * FROM "+GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME+
                    " WHERE "+GROUP_ID+"="+group_id+" AND "+PHASE_ID+"="+phase_id+" AND "+SECTION_ID+"="+section_id;
            cursor=db.rawQuery(query, null);
            while(cursor.moveToNext()){

            }
            return GPSAs;
        }
        */

    }

    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   LETTERS TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Letters implements BaseColumns{

        private Letters(){}


        public static final String LETTERS="letters";

        public static final String _ID="_id";
        public static final String LETTER_ID= "letter_id";
        public static final String LETTER_TEXT ="letter_text";
        public static final String AUDIO_URL= "audio_url";
        public static final String IMAGE_URL= "image_url";


        public static final String LETTERS_CREATE="CREATE TABLE  IF NOT EXISTS "+LETTERS+" ("+
                _ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LETTER_ID + " INTEGER, " +
                LETTER_TEXT  + " TEXT, " +
                AUDIO_URL  + " TEXT, " +
                IMAGE_URL  + " TEXT);" ;


        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(LETTERS_CREATE);

        }

        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + LETTERS);
            db.execSQL(LETTERS_CREATE);
            String query="SELECT * FROM "+LETTERS;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String insertSQL="INSERT INTO "+LETTERS+" ("+_ID+","+LETTER_ID+","+
                        LETTER_TEXT+","+AUDIO_URL+","+IMAGE_URL+")"+
                        "VALUES (";
                insertSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(LETTER_ID))+",";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(LETTER_TEXT))+"',";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(AUDIO_URL))+"',";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(IMAGE_URL))+"')";

                db.execSQL(insertSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }
    }


    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   LEVELS TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////


    public static final class Levels implements BaseColumns{

        private Levels(){}

        public static final String LEVELS_TABLE_NAME="levels";

        public static final String _ID="_id";
        public static final String LEVEL_ID="level_id";
        public static final String LEVEL_NUMBER="level_number";
        public static final String LEVEL_NATIVE_NUMBER="level_native_number";

        public static final String LEVELS_CREATE="CREATE TABLE "+LEVELS_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                LEVEL_ID + " INTEGER, " +
                LEVEL_NUMBER  + " INTEGER, " +
                LEVEL_NATIVE_NUMBER  + " INTEGER )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(LEVELS_CREATE);
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + LEVELS_TABLE_NAME);
            onCreate(db);
        }
        */
    }

    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   level_section_activity_relation TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Level_Section_Activity_Relation implements BaseColumns{

        private Level_Section_Activity_Relation(){}

        public static final String LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME="level_section_activity_relation";

        public static final String _ID="_id";
        public static final String LEVEL_SECTION_ACTIVITY_ID= "level_section_activity_id";
        public static final String LEVEL_NUMBER="level_number";
        public static final String SECTION_ID= "section_id";
        public static final String ACTIVITY_ID= "activity_id";


        public static final String LEVEL_SECTION_ACTIVITY_RELATION_CREATE="CREATE TABLE "+LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                LEVEL_SECTION_ACTIVITY_ID + " INTEGER, " +
                LEVEL_NUMBER  + " INTEGER, " +
                SECTION_ID  + " INTEGER, " +
                ACTIVITY_ID  + " INTEGER )";


        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(LEVEL_SECTION_ACTIVITY_RELATION_CREATE);
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME);
            onCreate(db);
        }
        */
        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME);
            onCreate(db);
            String query="SELECT * FROM "+LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME+" ("+_ID+","+LEVEL_SECTION_ACTIVITY_ID+","+
                        LEVEL_NUMBER+","+SECTION_ID+","+ACTIVITY_ID+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(LEVEL_SECTION_ACTIVITY_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(LEVEL_NUMBER))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(SECTION_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(ACTIVITY_ID))+")";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }


        //////////////////////////////////////////////////////////////////////
        /*
        public static Cursor getAllLevelSectionActivityRelations(SQLiteDatabase db){
            return db.rawQuery("SELECT * FROM "+LEVEL_SECTION_ACTIVITY_RELATION_TABLE_NAME,null);
        }
        */
    }




    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   sections TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Sections implements BaseColumns{

        private Sections(){}

        public static final String SECTIONS_TABLE_NAME="sections";

        public static final String _ID="_id";
        public static final String SECTION_ID= "section_id";
        public static final String GROUP_ID= "group_id";
        public static final String SECTION_NAME= "section_name";
        public static final String SECTION_IMAGE= "section_image";
        public static final String SECTION_ALL_INCLUSIVE= "section_all_inclusive";
        public static final String SECTION_ORDER= "section_order";

        public static final String SECTIONS_CREATE="CREATE TABLE  IF NOT EXISTS "+SECTIONS_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SECTION_ID + " INTEGER, " +
                GROUP_ID  + " INTEGER, " +
                SECTION_NAME  + " TEXT, " +
                SECTION_IMAGE  + " TEXT, " +
                SECTION_ALL_INCLUSIVE  + " INTEGER, "+
                SECTION_ORDER  + " INTEGER );" ;

        public static final ArrayList<String> DEFAULT_SECTION_IDS = new ArrayList<String>(Arrays.asList("1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","28"));
        public static final ArrayList<String> DEFAULT_GROUP_IDS = new ArrayList<String>(Arrays.asList("1", "2", "2","2","2","2","3","3","3","4","4","4","5","5","5","6","6","7","7","7","8"));
        public static final ArrayList<String> DEFAULT_SECTION_NAMES = new ArrayList<String>(Arrays.asList("Song Learn", "Upper LowerCase", "Letters Practice","Letters Learn Sound","Sound Practice"
                ,"Letter Test","Syllabe Learn","Syllable Pracice","Syllable Test","Words Learn","Words Practice","Words Test","Learn Books","Story Listen","Read Books","Drawing Shapes"
                ,"Writing Letters","Math Learn","Math Pracitce","Math Test","general_section"));
        public static final ArrayList<String> DEFAULT_SECTION_IMAGES = new ArrayList<String>(Arrays.asList("xpsec_song_learn.png", "xpsec_song_play.png", "xpsec_letter_learn.png",
                "xpsec_upper_lower_case.png","xpsec_letter_practice.png","xpsec_letter_test.png","xpsec_syllabe_learn.png","xpsec_syllable_pracice.png","xpsec_syllable_test.png"
                ,"xpsec_words_learn.png","xpsec_words_practice.png","xpsec_words_test.png","xpsec_learn_books.png","xpsec_story_listen.png","xpsec_read_books.png","xpsec_drawing_shapes.png"
                ,"xpsec_writing_letters.png","xpsec_math_learn.png","xpsec_math_pracitce.png","xpsec_math_test.png",""));
        public static final ArrayList<String> DEFAULT_SECTION_ALL_INCLUSIVES = new ArrayList<String>(Arrays.asList("0", "0", "0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"));
        public static final ArrayList<String> DEFAULT_SECTION_ORDERS = new ArrayList<String>(Arrays.asList("1", "1", "2","3","4","5","1","2","3","1","2","3","1","2","3","1","2","1","2","3","1"));



        ////////////////////////////////////////////////////////////////////////

        public static Cursor getAllSections(SQLiteDatabase db, String sortOrder){
            String query="SELECT * FROM "+SECTIONS_TABLE_NAME+" ORDER BY "+sortOrder;
            return db.rawQuery(query, null);
        }
        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(SECTIONS_CREATE);

            ContentValues initialValues = new ContentValues();
            for(int i=0; i<DEFAULT_SECTION_IDS.size();i++){
                initialValues.put(SECTION_ID, DEFAULT_SECTION_IDS.get(i));
                initialValues.put(GROUP_ID, DEFAULT_GROUP_IDS.get(i));
                initialValues.put(SECTION_NAME, DEFAULT_SECTION_NAMES.get(i));
                initialValues.put(SECTION_IMAGE, DEFAULT_SECTION_IMAGES.get(i));
                initialValues.put(SECTION_ALL_INCLUSIVE, DEFAULT_SECTION_ALL_INCLUSIVES.get(i));
                initialValues.put(SECTION_ORDER, DEFAULT_SECTION_ORDERS.get(i));

                db.insert(SECTIONS_TABLE_NAME, null, initialValues);
            }
        }





        //////////////////////////////////////////////////////////////////////
        /*
        public static void createStartData(SQLiteDatabase db){
            ContentValues initialValues = new ContentValues();
            for(int i=0; i<DEFAULT_GROUP_IDS.size();i++){
                initialValues.put(SECTION_ID, DEFAULT_SECTION_IDS.get(i));
                initialValues.put(GROUP_ID, DEFAULT_GROUP_IDS.get(i));
                initialValues.put(SECTION_NAME, DEFAULT_SECTION_NAMES.get(i));
                initialValues.put(SECTION_IMAGE, DEFAULT_SECTION_IMAGES.get(i));
                initialValues.put(SECTION_ALL_INCLUSIVE, DEFAULT_SECTION_ALL_INCLUSIVES.get(i));
                initialValues.put(SECTION_ORDER, DEFAULT_SECTION_ORDERS.get(i));

                db.insert(SECTIONS_TABLE_NAME, null, initialValues);
            }
        }
        */
        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + SECTIONS_TABLE_NAME);
            onCreate(db);
        }
        */
        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + SECTIONS_TABLE_NAME);
            db.execSQL(SECTIONS_CREATE);
            String query="SELECT * FROM "+SECTIONS_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+SECTIONS_TABLE_NAME+" ("+_ID+","+SECTION_ID+","+
                        GROUP_ID+","+SECTION_NAME+","+SECTION_IMAGE+","+
                        SECTION_ALL_INCLUSIVE+","+SECTION_ORDER+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(SECTION_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(GROUP_ID))+",";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(SECTION_NAME))+"',";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(SECTION_IMAGE))+"',";

                updateSQL+=cursor.getString(cursor.getColumnIndex(SECTION_ALL_INCLUSIVE))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(SECTION_ORDER))+")";

                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }
    }



    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   WORDS TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////


    public static final class Words implements BaseColumns{

        private Words(){}

        public static final String WORDS_TABLE_NAME="words";

        public static final String _ID = "_id";
        public static final String WORD_ID = "word_id";
        public static final String WORD_TEXT = "word_text";
        public static final String WORD_SEPARATION = "word_seperation";
        public static final String WORD_FIRST_LETTERS = "word_first_letter";
        public static final String WORD_LAST_LETTERS = "word_last_letter";
        public static final String NUMBER_SYLLABLES = "number_of_syllables";
        public static final String AUDIO_URL = "image_url";
        public static final String IMAGE_URL = "audio_url";





        public static final String WORDS_CREATE="CREATE TABLE "+WORDS_TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, " +
                WORD_ID + " INTEGER, " +
                WORD_TEXT + " TEXT, " +
                WORD_SEPARATION+ " TEXT, " +
                WORD_FIRST_LETTERS+ " TEXT, " +
                WORD_LAST_LETTERS+ " TEXT, " +
                NUMBER_SYLLABLES+" INTEGER, "+
                AUDIO_URL+" TEXT, "+
                IMAGE_URL+" TEXT )";

        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE_NAME);
            onCreate(db);
            String query="SELECT * FROM "+WORDS_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+WORDS_TABLE_NAME+" ("+_ID+","+WORD_ID+","+
                        WORD_TEXT+","+WORD_FIRST_LETTERS+","+WORD_LAST_LETTERS+","+NUMBER_SYLLABLES+","+
                        AUDIO_URL+","+IMAGE_URL+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(WORD_ID))+",";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(WORD_TEXT))+"',";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(WORD_FIRST_LETTERS))+"',";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(WORD_LAST_LETTERS))+"',";
                updateSQL+=""+cursor.getString(cursor.getColumnIndex(NUMBER_SYLLABLES))+",";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(AUDIO_URL))+"',";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(IMAGE_URL))+"' )";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }


        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(WORDS_CREATE);
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE_NAME);
            onCreate(db);
        }
        */

    }




    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   VARIABLE_PHASE_LEVELS_TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////


    public static final class Variable_Phase_Levels implements BaseColumns{

        private Variable_Phase_Levels(){}

        public static final String VARIABLE_PHASE_LEVELS_TABLE="variable_phase_levels";

        public static final String _ID="_id";
        public static final String VARIABLE_PHASE_LEVELS_ID="variable_phase_levels_id";
        public static final String VARIABLE_ID="variable_id";

        public static final String GROUP_ID= "group_id";
        public static final String PHASE_ID="phase_id";
        public static final String LEVEL_NUMBER= "level_number";


        public static final String VARIABLE_PHASE_LEVELS_CREATE="CREATE TABLE "+VARIABLE_PHASE_LEVELS_TABLE+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                VARIABLE_PHASE_LEVELS_ID + " INTEGER, " +
                VARIABLE_ID  + " INTEGER, " +
                GROUP_ID  + " INTEGER, " +
                PHASE_ID + " INTEGER, " +
                LEVEL_NUMBER  + " INTEGER  )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(VARIABLE_PHASE_LEVELS_CREATE);
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + WORDS_PHASE_LEVELS_TABLE_NAME);
            onCreate(db);
        }
        */
        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + VARIABLE_PHASE_LEVELS_TABLE);
            onCreate(db);
            String query="SELECT * FROM "+VARIABLE_PHASE_LEVELS_TABLE;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+VARIABLE_PHASE_LEVELS_TABLE+" ("+_ID+","+VARIABLE_PHASE_LEVELS_ID+","+
                        VARIABLE_ID+","+GROUP_ID+","+PHASE_ID+","+LEVEL_NUMBER+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(VARIABLE_PHASE_LEVELS_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(VARIABLE_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(GROUP_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(PHASE_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(LEVEL_NUMBER))+")";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }

    }

    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   VARIABLE_TYPE_RELATIONS
     *****************************************************/
    //////////////////////////////////////////////////////////////////////


    public static final class VariableTypeRelations implements BaseColumns{

        private VariableTypeRelations(){}

        public static final String VARIABLE_TYPE_RELATIONS="variable_type_relations";

        public static final String _ID="_id";
        public static final String VARIABLE_ID="variable_id";
        public static final String VARIABLE_TYPE_ID="variable_type_id";



        public static final String VARIABLE_TYPE_RELATIONS_CREATE="CREATE TABLE "+VARIABLE_TYPE_RELATIONS+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                VARIABLE_ID + " INTEGER, " +
                VARIABLE_TYPE_ID  + " INTEGER )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(VARIABLE_TYPE_RELATIONS_CREATE);
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + WORDS_PHASE_LEVELS_TABLE_NAME);
            onCreate(db);
        }
        */
        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + VARIABLE_TYPE_RELATIONS);
            onCreate(db);
            String query="SELECT * FROM "+VARIABLE_TYPE_RELATIONS;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+VARIABLE_TYPE_RELATIONS+" ("+_ID+","+VARIABLE_ID+","+
                        VARIABLE_TYPE_ID+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(VARIABLE_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(VARIABLE_TYPE_ID))+")";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }

    }

    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   app_user_level_activity_points TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    /*
    public static final class LAU_Points implements BaseColumns{

        private LAU_Points(){}

        public static final String LAU_POINTS_TABLE_NAME="lau_points";

        public static final String _ID="_id";
        public static final String LAU_POINTS_ID="lau_points_id";
        public static final String LEVEL_ID="level_id";
        public static final String ACTIVITY_ID="activity_id";
        public static final String APP_USER_ID="app_user_id";
        public static final String LAU_POINTS="lau_points";

        public static final String LAU_POINTS_CREATE="CREATE TABLE "+LAU_POINTS_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                LAU_POINTS_ID + " INTEGER, " +
                LEVEL_ID  + " INTEGER, " +
                ACTIVITY_ID  + " INTEGER, " +
                APP_USER_ID  + " INTEGER, " +
                LAU_POINTS  + " INTEGER )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(LAU_POINTS_CREATE);
        }

        //////////////////////////////////////////////////////////////////////

        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + LAU_POINTS_TABLE_NAME);
            onCreate(db);
        }

    }

    */


    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   USERS TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class App_Users implements BaseColumns{

        private App_Users(){ }

        public static final String APP_USERS_TABLE_NAME="app_users";

        public static final String _ID="_id";
        public static final String APP_USER_ID= "app_user_id";
        public static final String APP_USER_NAME= "app_user_name";
        public static final String APP_USER_AVATAR= "app_user_avatar";
        public static final String APP_USER_FN= "app_user_fn";
        public static final String APP_USER_LN= "app_user_ln";
        public static final String APP_USER_TEACHER= "app_user_teacher";
        public static final String APP_USER_SCHOOL= "app_user_school";
        public static final String APP_USER_GENDER= "app_user_gender";
        public static final String APP_USER_LONG= "app_user_long";
        public static final String APP_USER_LAT= "app_user_lat";
        public static final String APP_USER_DEVICE= "app_user_device";
        public static final String APP_USER_AGE= "app_user_age";
        public static final String APP_USER_GRADE= "app_user_grade";

        public static final String APP_USERS_CREATE="CREATE TABLE  IF NOT EXISTS "+APP_USERS_TABLE_NAME+" ( " +
                _ID   + " INTEGER PRIMARY KEY, " +
                APP_USER_ID + " INTEGER, " +
                APP_USER_NAME  + " TEXT, " +
                APP_USER_AVATAR  + " TEXT, " +
                APP_USER_FN  + " TEXT, " +
                APP_USER_LN  + " TEXT, " +
                APP_USER_TEACHER  + " TEXT, " +
                APP_USER_SCHOOL  + " TEXT, " +
                APP_USER_GENDER  + " INTEGER, " +
                APP_USER_LONG  + " TEXT, " +
                APP_USER_LAT  + " TEXT, " +
                APP_USER_DEVICE  + " TEXT, " +
                APP_USER_AGE  + " INTEGER, " +
                APP_USER_GRADE + " TEXT )";


        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(APP_USERS_CREATE);
            createGuestUser(db);
        }

        //////////////////////////////////////////////////////////////////////


        public static void createGuestUser(SQLiteDatabase db){
            Cursor c=db.rawQuery("SELECT * FROM "+APP_USERS_TABLE_NAME+"  WHERE "+APP_USER_ID+"=0",null);
            if(!c.moveToFirst()){

                db.execSQL("INSERT INTO "+APP_USERS_TABLE_NAME+"  ("+_ID+","+APP_USER_ID+","+
                        APP_USER_NAME+" " +	", "+APP_USER_AVATAR+" ) "+
                        "Values (0,0, '"+MotoliConstants.GUEST_NAME+"', '0')");

            }
            c.close();
        }


        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + APP_USERS_TABLE_NAME);
            onCreate(db);
        }
        */
    }


    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   app_users_activity_variables_values TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////


    public static final class App_Users_Activity_Variable_Values implements BaseColumns{

        private App_Users_Activity_Variable_Values(){}

        public static final String AUAVV_TABLE="app_users_activity_variable_values";

        public static final String _ID="_id";
        public static final String AUAVV_ID="auavv_id";
        public static final String VARIABLE_ID="variable_id";
        public static final String TYPE_ID="type_id";
        public static final String ACTIVITY_ID="activity_id";
        public static final String APP_USER_ID="app_user_id";
        public static final String NUMBER_CORRECT="number_correct";
        public static final String NUMBER_CORRECT_IN_ROW="number_correct_in_a_row";
        public static final String NUMBER_INCORRECT="number_incorrect";

        public static final String AUWARW_CREATE="CREATE TABLE "+AUAVV_TABLE+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                AUAVV_ID + " INTEGER, " +
                VARIABLE_ID  + " INTEGER, " +
                TYPE_ID  + " INTEGER, " +
                ACTIVITY_ID  + " INTEGER, " +
                APP_USER_ID  + " INTEGER, " +
                NUMBER_CORRECT  + " INTEGER, " +
                NUMBER_CORRECT_IN_ROW + " INTEGER, "+
                NUMBER_INCORRECT  + " INTEGER,"+
                "UNIQUE (VARIABLE_ID,TYPE_ID,ACTIVITY_ID,APP_USER_ID) )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(AUWARW_CREATE);
        }


        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + AUAVV_TABLE);
            db.execSQL(AUWARW_CREATE);
            String query="SELECT * FROM "+AUAVV_TABLE;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String insertSQL="INSERT INTO "+AUAVV_TABLE+" ("+_ID+","+
                        AUAVV_ID+","+VARIABLE_ID+","+TYPE_ID+","+APP_USER_ID+","+
                        NUMBER_CORRECT+","+NUMBER_CORRECT_IN_ROW+","+NUMBER_INCORRECT+")"+
                        "VALUES (";
                insertSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(AUAVV_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(VARIABLE_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(TYPE_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(APP_USER_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(NUMBER_CORRECT))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(NUMBER_CORRECT_IN_ROW))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(NUMBER_INCORRECT))+")";


                db.execSQL(insertSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }



        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + AUWARW_TABLE_NAME);
            onCreate(db);
        }
        */
        //////////////////////////////////////////////////////////////////////
        /*
        public static void adjust_correct_incorrect(SQLiteDatabase db,ArrayList<String> data){
            String query="INSERT INTO "+AUWARW_TABLE_NAME+" ("
                    +WORD_ID+","+ACTIVITY_ID+","+APP_USER_ID+","+NUMBER_CORRECT+","+NUMBER_INCORRECT+")"
                    +" VALUES("+data.get(0)+","+data.get(1)+","+data.get(2)+","+data.get(3)+","+data.get(4)+")"
                    +" ON DUPLICATE KEY UPDATE "+NUMBER_CORRECT+"="+NUMBER_CORRECT+"+1, "
                    +NUMBER_INCORRECT+"="+NUMBER_INCORRECT+"+1";
            db.execSQL(query);
        }
        */
    }


    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   app_user_level_activity_points TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////


    public static final class Users_Words_Activities_RW_Order implements BaseColumns{

        private Users_Words_Activities_RW_Order(){}

        public static final String UWARWO_TABLE_NAME="users_words_activities_rw_order";

        public static final String _ID="_id";
        public static final String UWARWO_ID="uwarwo_id";
        public static final String WORD_ID="word_id";
        public static final String ACTIVITY_ID="activity_id";
        public static final String APP_USER_ID="app_user_id";
        public static final String RIGHT_OR_WRONG="right_or_wrong";
        public static final String RW_ORDER="rw_order";

        public static final String UWARWO_CREATE="CREATE TABLE "+UWARWO_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                UWARWO_ID + " INTEGER, " +
                WORD_ID  + " INTEGER, " +
                ACTIVITY_ID  + " INTEGER, " +
                APP_USER_ID  + " INTEGER, " +
                RIGHT_OR_WRONG  + " INTEGER, " +
                RW_ORDER  + " INTEGER,"+
                "UNIQUE (WORD_ID,ACTIVITY_ID,APP_USER_ID,RW_ORDER) )" ;

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(UWARWO_CREATE);
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + UWARWO_TABLE_NAME);
            onCreate(db);
        }
        */
        //////////////////////////////////////////////////////////////////////
        /*
        public static void adjust_correct_incorrect(SQLiteDatabase db,ArrayList<String> data){
            String query="INSERT INTO "+AUWARW_TABLE_NAME+" ("
                    +WORD_ID+","+ACTIVITY_ID+","+APP_USER_ID+","+NUMBER_CORRECT+","+NUMBER_INCORRECT+")"
                    +" VALUES("+data.get(0)+","+data.get(1)+","+data.get(2)+","+data.get(3)+","+data.get(4)+")"
                    +" ON DUPLICATE KEY UPDATE "+NUMBER_CORRECT+"="+NUMBER_CORRECT+"+1, "
                    +NUMBER_INCORRECT+"="+NUMBER_INCORRECT+"+1";
            db.execSQL(query);
        }
        */
    }

    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   books TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Books implements BaseColumns{

        private Books(){}

        public static final String BOOKS_TABLE="books";

        public static final String _ID="_id";
        public static final String BOOK_ID = "book_id";
        public static final String BOOK_ORDER = "book_order";
        public static final String BOOK_TITLE = "book_title";
        public static final String BOOK_TYPE = "book_type";
        public static final String BOOK_NUMBER_OF_PAGES = "book_number_of_pages";
        public static final String BOOK_FONT_SIZE = "book_font_size";


        public static final String BOOKS_CREATE="CREATE TABLE "+BOOKS_TABLE+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                BOOK_ID + " INTEGER, " +
                BOOK_ORDER  + " INTEGER, " +
                BOOK_TITLE  + " TEXT, " +
                BOOK_TYPE  + " INTEGER, " +
                BOOK_NUMBER_OF_PAGES  + " INTEGER, " +
                BOOK_FONT_SIZE  + " INTEGER )";


        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(BOOKS_CREATE);
        }

        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + BOOKS_TABLE);
            onCreate(db);

            String query="SELECT * FROM "+BOOKS_TABLE;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+BOOKS_TABLE+" ("+_ID+","+BOOK_ID+","+
                        BOOK_ORDER+","+BOOK_TITLE+","+BOOK_TYPE+","+BOOK_NUMBER_OF_PAGES+","+BOOK_FONT_SIZE+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_ORDER))+",";
                updateSQL+="\""+cursor.getString(cursor.getColumnIndex(BOOK_TITLE))+"\",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_TYPE))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_NUMBER_OF_PAGES))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_FONT_SIZE))+")";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static Cursor getAllActivities(SQLiteDatabase db){
            return db.rawQuery("SELECT * FROM "+ACTIVITIES_TABLE_NAME,null);
        }
        */
    }

    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   book_pagess TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Book_Pages implements BaseColumns{

        private Book_Pages(){}

        public static final String BOOK_PAGES_TABLE="book_pages";

        public static final String _ID="_id";
        public static final String BOOK_PAGES_ID = "book_pages_id";
        public static final String BOOK_ID = "book_id";
        public static final String BOOK_PAGE_ORDER = "book_page_order";
        public static final String BOOK_PAGE_IMAGE = "book_page_image";
        public static final String BOOK_PAGE_AUDIO = "book_page_audio";
        public static final String BOOK_PAGE_FONT_SIZE = "book_page_font_size";
        public static final String BOOK_PAGE_TEXT = "book_page_text";
        public static final String BOOK_PAGE_TIME = "book_page_time";


        public static final String BOOK_PAGES_CREATE="CREATE TABLE "+BOOK_PAGES_TABLE+" ("+
                _ID   + " INTEGER PRIMARY KEY, " +
                BOOK_PAGES_ID + " INTEGER, " +
                BOOK_ID  + " INTEGER, " +
                BOOK_PAGE_ORDER  + " INTEGER, " +
                BOOK_PAGE_IMAGE  + " TEXT, " +
                BOOK_PAGE_AUDIO  + " TEXT, " +
               /* BOOK_PAGE_FONT_SIZE  + " INTEGER, "+*/
                BOOK_PAGE_TEXT + " TEXT, "+
                BOOK_PAGE_TIME + " TEXT)";

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(BOOK_PAGES_CREATE);
        }

        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + BOOK_PAGES_TABLE);
            onCreate(db);

            String query="SELECT * FROM "+BOOK_PAGES_TABLE;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String updateSQL="INSERT INTO "+BOOK_PAGES_TABLE+" ("+_ID+","+BOOK_PAGES_ID+","+
                        BOOK_ID+","+BOOK_PAGE_ORDER+","+BOOK_PAGE_IMAGE+","+BOOK_PAGE_AUDIO+/*","+
                        BOOK_PAGE_FONT_SIZE+*/","+BOOK_PAGE_TEXT+","+BOOK_PAGE_TIME+")"+
                        "VALUES (";
                updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_PAGES_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_PAGES_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_ID))+",";
                updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_PAGE_ORDER))+",";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(BOOK_PAGE_IMAGE))+"',";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(BOOK_PAGE_AUDIO))+"',";
                /*updateSQL+=cursor.getString(cursor.getColumnIndex(BOOK_PAGE_FONT_SIZE))+",";*/
                updateSQL+="\""+cursor.getString(cursor.getColumnIndex(BOOK_PAGE_TEXT))+"\",";
                updateSQL+="'"+cursor.getString(cursor.getColumnIndex(BOOK_PAGE_TIME))+"')";
                db.execSQL(updateSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static Cursor getAllActivities(SQLiteDatabase db){
            return db.rawQuery("SELECT * FROM "+ACTIVITIES_TABLE_NAME,null);
        }
        */
    }

    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   USERS CURRENT GROUP PHASE LEVEL TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class App_Users_Current_GPL implements BaseColumns{

        private App_Users_Current_GPL(){ }

        public static final String APP_USERS_CURRENT_GPL_TABLE_NAME="app_users_current_gpl";

        public static final String _ID="_id";
        public static final String APP_USER_CURRENT_GPL_ID= "app_user_current_gpl_id";
        public static final String APP_USER_ID= "app_user_id";
        public static final String GROUP_ID= "group_id";
        public static final String PHASE_ID= "phase_id";
        public static final String APP_USER_CURRENT_LEVEL= "app_user_current_level";


        public static final String APP_USERS_CURRENT_GPL_CREATE="CREATE TABLE  IF NOT EXISTS "+APP_USERS_CURRENT_GPL_TABLE_NAME+" ( " +
                _ID   + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                APP_USER_ID + " INTEGER, " +
                GROUP_ID  + " INTEGER, " +
                PHASE_ID  + " INTEGER, " +
                APP_USER_CURRENT_LEVEL  + " INTEGER )";


        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(APP_USERS_CURRENT_GPL_CREATE);
        }

        //////////////////////////////////////////////////////////////////////


        public static void setNewUserCurrentGPL(SQLiteDatabase db, int app_user_id){
            String query="SELECT * FROM "+Groups_Phase.GROUPS_PHASE_TABLE_NAME;
            Cursor cursor=db.rawQuery(query, null);
            while(cursor.moveToNext()){
                int phase_id=cursor.getInt(cursor.getColumnIndex(Groups_Phase.PHASE_ID));
                int group_id=cursor.getInt(cursor.getColumnIndex(Groups_Phase.GROUP_ID));
                int current_level=(phase_id==1) ? 0 :0;
                if(group_id==4){
                    current_level=0;
                }
                if(group_id==5) {
                    current_level = 1;
                }
                if(group_id!=1) {
                    String insertQuery = "INSERT INTO " + APP_USERS_CURRENT_GPL_TABLE_NAME + " (" +
                            APP_USER_ID + "," + GROUP_ID + "," + PHASE_ID + "," + APP_USER_CURRENT_LEVEL + ") " +
                            "Values (" + app_user_id + "," + group_id + "," + phase_id + "," + current_level + ")";
                    db.execSQL(insertQuery);
                }
            }
            cursor.close();
        }

        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + APP_USERS_CURRENT_GPL_TABLE_NAME);
            db.execSQL(APP_USERS_CURRENT_GPL_CREATE);
            String query="SELECT * FROM "+APP_USERS_CURRENT_GPL_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String insertSQL="INSERT INTO "+APP_USERS_CURRENT_GPL_TABLE_NAME+" ("+_ID+","+
                        APP_USER_ID+","+GROUP_ID+","+PHASE_ID+","+APP_USER_CURRENT_LEVEL+")"+
                        "VALUES (";
                insertSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(APP_USER_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(GROUP_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(PHASE_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(APP_USER_CURRENT_LEVEL))+")";


                db.execSQL(insertSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }

        //////////////////////////////////////////////////////////////////////
        /*
        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //	db.execSQL("DROP TABLE IF EXISTS " + APP_USERS_TABLE_NAME);
        //	onCreate(db);
        }
        */
    }
    /////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   phonics TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Phonics implements BaseColumns{

        private Phonics(){}

        public static final String PHONICS_TABLE_NAME="phonics";

        public static final String _ID="_id";
        public static final String PHONIC_ID= "phonic_id";
        public static final String PHONIC_TEXT= "phonic_text";
        public static final String PHONIC_GRAMMAR= "phonic_grammar";
        public static final String PHONIC_AUDIO= "phonic_audio";

        public static final String PHONIC_CREATE="CREATE TABLE  IF NOT EXISTS "+PHONICS_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PHONIC_ID + " INTEGER, " +
                PHONIC_TEXT  + " TEXT, " +
                PHONIC_GRAMMAR  + " TEXT, " +
                PHONIC_AUDIO  + " TEXT);" ;


        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(PHONIC_CREATE);

        }

        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + PHONICS_TABLE_NAME);
            db.execSQL(PHONIC_CREATE);
            String query="SELECT * FROM "+PHONICS_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String insertSQL="INSERT INTO "+PHONICS_TABLE_NAME+" ("+_ID+","+PHONIC_ID+","+
                        PHONIC_TEXT+","+PHONIC_GRAMMAR+","+PHONIC_AUDIO+")"+
                        "VALUES (";
                insertSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(PHONIC_ID))+",";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(PHONIC_TEXT))+"',";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(PHONIC_GRAMMAR))+"',";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(PHONIC_AUDIO))+"')";


                db.execSQL(insertSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }
    }


    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   phonic_words TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Phonic_Words implements BaseColumns{

        private Phonic_Words(){}


        public static final String PHONIC_WORDS_TABLE_NAME="phonic_words";

        public static final String _ID="_id";
        public static final String PHONIC_WORD_ID= "phonic_word_id";
        public static final String PHONIC_WORD_TEXT ="phonic_word_text";
        public static final String PHONIC_WORD_AUDIO= "phonic_word_audio";
        public static final String PHONIC_WORD_IMAGE= "phonic_word_image";
        public static final String PHONIC_WORD_FIRST_ID= "phonic_word_first_id";
        public static final String PHONIC_WORD_LAST_ID= "phonic_word_last_id";

        public static final String PHONIC_WORDS_CREATE="CREATE TABLE  IF NOT EXISTS "+PHONIC_WORDS_TABLE_NAME+" ("+
                _ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PHONIC_WORD_ID + " INTEGER, " +
                PHONIC_WORD_TEXT  + " TEXT, " +
                PHONIC_WORD_AUDIO  + " TEXT, " +
                PHONIC_WORD_IMAGE  + " TEXT, "+
                PHONIC_WORD_FIRST_ID + " INTEGER, "+
                PHONIC_WORD_LAST_ID + " INTEGER);" ;


        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(PHONIC_WORDS_CREATE);

        }

        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + PHONIC_WORDS_TABLE_NAME);
            db.execSQL(PHONIC_WORDS_CREATE);
            String query="SELECT * FROM "+PHONIC_WORDS_TABLE_NAME;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String insertSQL="INSERT INTO "+PHONIC_WORDS_TABLE_NAME+" ("+_ID+","+PHONIC_WORD_ID+","+
                        PHONIC_WORD_TEXT+","+PHONIC_WORD_AUDIO+","+PHONIC_WORD_IMAGE+","+
                        PHONIC_WORD_FIRST_ID+","+PHONIC_WORD_LAST_ID+")"+
                        "VALUES (";
                insertSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(PHONIC_WORD_ID))+",";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(PHONIC_WORD_TEXT))+"',";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(PHONIC_WORD_AUDIO))+"',";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(PHONIC_WORD_IMAGE))+"',";
                insertSQL+=cursor.getString(cursor.getColumnIndex(PHONIC_WORD_FIRST_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(PHONIC_WORD_LAST_ID))+")";

                db.execSQL(insertSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }
    }

    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   syllables TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Syllables implements BaseColumns{

        private Syllables(){}


        public static final String SYLLABLES_TABLE="syllables";

        public static final String _ID="_id";
        public static final String SYLLABLES_ID= "syllables_id";
        public static final String SYLLABLES_TEXT ="syllables_text";
        public static final String SYLLABLES_AUDIO= "syllables_audio";
        public static final String PHONIC_ID= "phonic_id";


        public static final String SYLLABLES_CREATE="CREATE TABLE  IF NOT EXISTS "+SYLLABLES_TABLE+" ("+
                _ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SYLLABLES_ID + " INTEGER, " +
                SYLLABLES_TEXT  + " TEXT, " +
                SYLLABLES_AUDIO  + " TEXT, " +
                PHONIC_ID  + " INTEGER);" ;


        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(SYLLABLES_CREATE);

        }

        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + SYLLABLES_TABLE);
            db.execSQL(SYLLABLES_CREATE);
            String query="SELECT * FROM "+SYLLABLES_TABLE;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String insertSQL="INSERT INTO "+SYLLABLES_TABLE+" ("+_ID+","+SYLLABLES_ID+","+
                        SYLLABLES_TEXT+","+SYLLABLES_AUDIO+","+PHONIC_ID+")"+
                        "VALUES (";
                insertSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(SYLLABLES_ID))+",";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(SYLLABLES_TEXT))+"',";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(SYLLABLES_AUDIO))+"',";
                insertSQL+=""+cursor.getString(cursor.getColumnIndex(PHONIC_ID))+")";

                db.execSQL(insertSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }
    }
    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   syllable_words TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Syllable_Words implements BaseColumns{

        private Syllable_Words(){}


        public static final String SYLLABLE_WORDS_TABLE="syllable_words";

        public static final String _ID="_id";
        public static final String SYLLABLE_WORD_ID= "syllable_word_id";
        public static final String SYLLABLE_WORD_TEXT ="syllable_word_text";
        public static final String SYLLABLE_WORD_AUDIO= "syllable_word_audio";
        public static final String SYLLABLE_WORD_IMAGE= "syllable_word_image";
        public static final String SYLLABLE_WORD_FIRST_ID= "syllable_word_first_id";
        public static final String SYLLABLE_WORD_LAST_ID= "syllable_word_last_id";

        public static final String SYLLABLE_WORDS_CREATE="CREATE TABLE  IF NOT EXISTS "+SYLLABLE_WORDS_TABLE+" ("+
                _ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SYLLABLE_WORD_ID + " INTEGER, " +
                SYLLABLE_WORD_TEXT  + " TEXT, " +
                SYLLABLE_WORD_AUDIO  + " TEXT, " +
                SYLLABLE_WORD_IMAGE  + " TEXT, "+
                SYLLABLE_WORD_FIRST_ID + " INTEGER, "+
                SYLLABLE_WORD_LAST_ID + " INTEGER);" ;


        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(SYLLABLE_WORDS_CREATE);

        }

        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase db, SQLiteDatabase db_temp){
            db.execSQL("DROP TABLE IF EXISTS " + SYLLABLE_WORDS_TABLE);
            db.execSQL(SYLLABLE_WORDS_CREATE);
            String query="SELECT * FROM "+SYLLABLE_WORDS_TABLE;
            Cursor cursor=db_temp.rawQuery(query, null);
            while(cursor.moveToNext()){
                String insertSQL="INSERT INTO "+SYLLABLE_WORDS_TABLE+" ("+_ID+","+SYLLABLE_WORD_ID+","+
                        SYLLABLE_WORD_TEXT+","+SYLLABLE_WORD_AUDIO+","+SYLLABLE_WORD_IMAGE+","+
                        SYLLABLE_WORD_FIRST_ID+","+SYLLABLE_WORD_LAST_ID+")"+
                        "VALUES (";
                insertSQL+=cursor.getString(cursor.getColumnIndex(_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(SYLLABLE_WORD_ID))+",";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(SYLLABLE_WORD_TEXT))+"',";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(SYLLABLE_WORD_AUDIO))+"',";
                insertSQL+="'"+cursor.getString(cursor.getColumnIndex(SYLLABLE_WORD_IMAGE))+"',";
                insertSQL+=cursor.getString(cursor.getColumnIndex(SYLLABLE_WORD_FIRST_ID))+",";
                insertSQL+=cursor.getString(cursor.getColumnIndex(SYLLABLE_WORD_LAST_ID))+")";

                db.execSQL(insertSQL);

            }//end while(cursor.moveToNext()){
            cursor.close();
        }
    }
}
