package com.motoli.apps.allsubjects;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 9/21/2016.
 */
public class AppProviderRetrieve {
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private AppProviderUpdateProcesses mAppProviderUpdateProcesses;


    public AppProviderRetrieve(SQLiteDatabase mDatabase, Context mContext) {
        this.mDatabase = mDatabase;
        this.mContext = mContext;
    }

    public void onCreate() {
        mAppProviderUpdateProcesses = new AppProviderUpdateProcesses(mDatabase, mContext);
    }


}
