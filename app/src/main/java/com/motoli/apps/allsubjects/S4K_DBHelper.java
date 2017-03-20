package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class S4K_DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION=11;
    private static final String DB_NAME="db_allsubjects.sqlite";
    private static final String DB_NAME_OLD="db_allsubjects.old.sqlite";
    private static	String DB_PATH = null;

    private static final String DB_NAME_TEMP="db_allaubjects.new.sqlite";
    private static	String DB_PATH_TEMP = null;
    private int mDatabaseVersion=0;


    private final Context myContext;


    public S4K_DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Database.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(Constants.LOGCAT, "upgrade database please");
    }




    ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * */
    public void createDataBase() throws IOException {
    }


    public int getDatabaseVersion(){
        return mDatabaseVersion;
    }

    public void setDatabaseVersion(int mDatabaseVersion){
        this.mDatabaseVersion = mDatabaseVersion;
    }

    @Override
    public SQLiteDatabase getWritableDatabase(){
        File dbFile = myContext.getDatabasePath(DB_NAME);
        SQLiteDatabase mDB;

        if (!dbFile.exists()) {
            try {
                SQLiteDatabase checkDB = myContext.openOrCreateDatabase(DB_NAME,
                        myContext.MODE_PRIVATE, null);
                if(checkDB != null){
                    checkDB.close();
                }

                copyDataBase(dbFile);

                mDB= SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                        SQLiteDatabase.OPEN_READWRITE);
                moveDatabaseFile(mDB);

                mDB.setVersion(1);
                setDatabaseVersion(1);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        else{
            if(getDatabaseVersion() < DB_VERSION){
                mDB = SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                        SQLiteDatabase.OPEN_READWRITE);

                Log.d(Constants.LOGCAT, "mDB.getVersion(): "+mDB.getVersion());
                Log.d(Constants.LOGCAT, "DB_VERSION: "+DB_VERSION);
                setDatabaseVersion(mDB.getVersion());
                mDB.close();
                if(getDatabaseVersion() < DB_VERSION) {
                    Log.d(Constants.LOGCAT, "needs to change");
                    DB_PATH = "/data/data/" + myContext.getPackageName() + "/" + "databases/";
                    File to = new File(DB_PATH, DB_NAME_OLD);

                    dbFile.renameTo(to);
                    dbFile = myContext.getDatabasePath(DB_NAME);
                    if (!dbFile.exists()) {
                        try {
                            SQLiteDatabase checkDB = myContext.openOrCreateDatabase(DB_NAME,
                                    myContext.MODE_PRIVATE, null);
                            if (checkDB != null) {
                                checkDB.close();
                            }
                            copyDataBase(dbFile);

                            mDB = SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                                    SQLiteDatabase.OPEN_READWRITE);
                            //  moveDatabaseFile(mDB);
                            placeOldDatabaseFiles(mDB);
                            mDB.setVersion(DB_VERSION);
                            mDB.close();



                        } catch (IOException e) {
                            throw new RuntimeException("Error creating source database", e);
                        }
                    }

                    //to.delete();
                }

            }

        }
       /* */

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private void placeOldDatabaseFiles(SQLiteDatabase mDatabase) {
        Database.updateDB(mDatabase, myContext, DB_NAME_OLD);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void moveDatabaseFile(SQLiteDatabase db){
        Database.addNew(db);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void copyDataBase(File dbFile) throws IOException {
        InputStream is = myContext.getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }

}
