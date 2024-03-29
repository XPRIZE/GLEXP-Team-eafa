package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;


/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 *
 * 3/5/2016
 * This class was vastly redone along with Motoli_Application on 3/5/2016
 * due to various calls to AppProvider
 * that were not need in anyway. This could have been causing some delay in the app
 * and was loading useless global variables wasting memory. ADMB
 */

public class  Motoli_Front extends Master_Parent
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motoli_front);
        appData.addToClassOrder(0);
        appData.setCurrentSection(2);
        Log.d(Constants.LOGCAT, "Motoli_Front onCreate");
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
          return new CursorLoader(this,
                AppProvider.CONTENT_URI_ALL_USERS, null,null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        appData.setCurrentUserID("0");
        appData.addToClassOrder(1);
        Intent main = new Intent(this,Launch_Platform.class);
        startActivity(main);
        finish();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}
