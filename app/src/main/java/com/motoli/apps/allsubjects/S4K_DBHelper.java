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
import java.util.ArrayList;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class S4K_DBHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION=2;
	private static final String DB_NAME="db_allsubjects.sqlite";
	private static	String DB_PATH = null;

	private static final String DB_NAME_TEMP="db_allaubjects.new.sqlite";
	private static	String DB_PATH_TEMP = null;
	
	
	private final Context myContext;

	
	public S4K_DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.myContext=context;
		
		
		//DB_NAME=context.getResources().getString(R.string.database);
		DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
		 try {
			createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	public S4K_DBHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		this.myContext=context;

		
	//	DB_NAME=context.getResources().getString(R.string.database);
		DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";

		try {
			createDataBase() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		S4K_Database.onCreate(db);

		

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(MotoliConstants.LOGCAT, "upgrade database please");
		//S4K_Database.App_Users.onUpgrade(db, oldVersion, newVersion);
	}

	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			SQLiteDatabase db=this.getReadableDatabase();

			try {

				copyDataBase();
				S4K_Database.updataDB(db, this.myContext, DB_NAME_TEMP);
			} catch (SQLiteException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////


	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME_TEMP;

		
		 File targetFile = new File(outFileName);
		OutputStream myOutput = new FileOutputStream(targetFile);
		    
		    
		    
		// Open the empty db as the output stream
		//OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();


	}
/*
	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}
	*/
	
}
