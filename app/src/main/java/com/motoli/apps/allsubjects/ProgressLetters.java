package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/23/2015.
 */
public class ProgressLetters  extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<ArrayList<String>> mProgressPoints;
    private ArrayList<ArrayList<String>> mProgressGroups;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.progress_letters);
        appData.addToClassOrder(5);
        allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

        beingValidated=true;

        setUpListeners();


        getLoaderManager().initLoader(MotoliConstants.PROGRESS_POINTS, null, this);
        ///Uri todoUri = MotoliContentProvider.CONTENT_URI_WRD_LTR_ACTIVITIES;

    }//end public void onCreate(Bundle savedInstanceState) {

    //////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(){

        Double mProgressLowerCase=Math.floor(
                (Float.valueOf(mProgressPoints.get(0).get(1))/
                        (Integer.parseInt(mProgressGroups.get(0).get(2))*10)*100));
        Double mProgressUpperCase=Math.floor(
                (Float.valueOf(mProgressPoints.get(1).get(1))/
                        (Integer.parseInt(mProgressGroups.get(1).get(2))*10)*100));
        Double mProgreePhonicsCase=Math.floor(
                (Float.valueOf(mProgressPoints.get(2).get(1))/
                        (Integer.parseInt(mProgressGroups.get(2).get(2))*10)*100));
        ((ProgressBar) findViewById(R.id.lowerCaseProgress)).setMax(100);
        ((ProgressBar) findViewById(R.id.lowerCaseProgress)).setProgress((int)Math.round(mProgressLowerCase));
        ((ProgressBar) findViewById(R.id.upperCaseProgress)).setMax(100);
        ((ProgressBar) findViewById(R.id.upperCaseProgress)).setProgress((int)Math.round(mProgressUpperCase));
        ((ProgressBar) findViewById(R.id.phonicsProgress)).setMax(100);
        ((ProgressBar) findViewById(R.id.phonicsProgress)).setProgress((int)Math.round(mProgreePhonicsCase));
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.PROGRESS_POINTS:{
                projection=new String[]{
                        appData.getCurrentUserID(),
                        "1",
                        "3"};
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_PROGRESS_POINTS, projection, null, null, null);
                break;
            }
            case MotoliConstants.PROGRESS_GROUPS:{

                projection = new String[]{"2"};

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_PROGRESS_GROUPS, projection, null, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch(loader.getId()) {
            default:
            case MotoliConstants.PROGRESS_POINTS: {
                mProgressPoints = new ArrayList<ArrayList<String>>();
                int mPointSpot = 0;
                if(data.getCount()==0){
                    for(int i=0;i<3;i++){
                        mProgressPoints.add(new ArrayList<String>());
                        mProgressPoints.get(mPointSpot).add(String.valueOf(i + 1));
                        mProgressPoints.get(mPointSpot).add("0");
                        mPointSpot++;
                    }
                }else {
                    data.moveToFirst();
                    do {
                        mProgressPoints.add(new ArrayList<String>());
                        mProgressPoints.get(mPointSpot).add(data.getString(data.getColumnIndex("type_id")));
                        mProgressPoints.get(mPointSpot).add(data.getString(data.getColumnIndex("user_total")));
                        mPointSpot++;
                    } while (data.moveToNext());
                }
                if(data.getCount()<3){
                    for(int i=data.getCount();i<3;i++){
                        mProgressPoints.add(new ArrayList<String>());
                        mProgressPoints.get(mPointSpot).add(String.valueOf(i + 1));
                        mProgressPoints.get(mPointSpot).add("0");
                        mPointSpot++;
                    }
                }
                getLoaderManager().restartLoader(MotoliConstants.PROGRESS_GROUPS, null, this);
                break;
            }
            case MotoliConstants.PROGRESS_GROUPS:{
                data.moveToFirst();
                mProgressGroups=new ArrayList<ArrayList<String>>();
                int mGroupSpot=0;
                do{
                    mProgressGroups.add(new ArrayList<String>());
                    mProgressGroups.get(mGroupSpot).add(data.getString(data.getColumnIndex("group_id")));
                    mProgressGroups.get(mGroupSpot).add(data.getString(data.getColumnIndex("phase_id")));
                    mProgressGroups.get(mGroupSpot).add(data.getString(data.getColumnIndex("number_of")));

                    mGroupSpot++;
                }while(data.moveToNext());
                displayScreen();
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setUpListeners(){
        findViewById(R.id.btnInfo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                long audioDuration=playInstructionAudio();
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
            }
        });

    }

    private long playInstructionAudio(){
        return playGeneralAudio("info_lt06.mp3");
    }


}
