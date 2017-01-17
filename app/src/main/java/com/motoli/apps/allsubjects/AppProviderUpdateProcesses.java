package com.motoli.apps.allsubjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 9/21/2016.
 */
public class AppProviderUpdateProcesses {
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public AppProviderUpdateProcesses(SQLiteDatabase mDatabase, Context mContext){
        this.mDatabase=mDatabase;
        this.mContext = mContext;
    }


}
