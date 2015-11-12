package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/31/2015.
 */
public class ActivityWD06 extends Activity_General_Parent implements LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<ArrayList<ArrayList<String>>> mAllActivityText;
    private ArrayList<ArrayList<String>> mAllLowerCaseLetters;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_wd06);
        appData.addToClassOrder(17);
        mAllActivityText = new ArrayList<ArrayList<ArrayList<String>>>();

        beingValidated=true;

        //clearActivity();
       // setUpListeners();
       // setupFrameListens();

        ((TextView) findViewById(R.id.letter1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter4)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter5)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.letter6)).setTypeface(appData.getCurrentFontType());

        setLetterVisiblities();

        findViewById(R.id.leftLetter5Space).setVisibility(View.VISIBLE);
        findViewById(R.id.rightLetter5Space).setVisibility(View.VISIBLE);
        findViewById(R.id.leftLetter6Space).setVisibility(View.VISIBLE);
        findViewById(R.id.rightLetter6Space).setVisibility(View.VISIBLE);

        findViewById(R.id.letter5Frame).setVisibility(View.GONE);
        findViewById(R.id.letter6Frame).setVisibility(View.GONE);

        getLoaderManager().initLoader(MotoliConstants.ALL_LOWERCASE_LETTERS, null, this);
        ///Uri todoUri = MotoliContentProvider.CONTENT_URI_WRD_LTR_ACTIVITIES;

    }//end public void onCreate(Bundle savedInstanceState) {

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void setLetterVisiblities(){
        findViewById(R.id.leftLetter3Space).setVisibility(View.GONE);
        findViewById(R.id.rightLetter3Space).setVisibility(View.GONE);
        findViewById(R.id.leftLetter4Space).setVisibility(View.GONE);
        findViewById(R.id.rightLetter4Space).setVisibility(View.GONE);
        findViewById(R.id.leftLetter5Space).setVisibility(View.GONE);
        findViewById(R.id.rightLetter5Space).setVisibility(View.GONE);
        findViewById(R.id.leftLetter6Space).setVisibility(View.GONE);
        findViewById(R.id.rightLetter6Space).setVisibility(View.GONE);

        findViewById(R.id.letter1Frame).setVisibility(View.VISIBLE);
        findViewById(R.id.letter2Frame).setVisibility(View.VISIBLE);
        findViewById(R.id.letter3Frame).setVisibility(View.VISIBLE);
        findViewById(R.id.letter4Frame).setVisibility(View.VISIBLE);
        findViewById(R.id.letter5Frame).setVisibility(View.VISIBLE);
        findViewById(R.id.letter6Frame).setVisibility(View.VISIBLE);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.ALL_LOWERCASE_LETTERS:{


                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_ALL_LOWERCASE_LETTERS, null, null, null, null);
                break;
            }
            case MotoliConstants.CURRENT_WORDS:{

                ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
                if(currentGSP.get(1).equals("3")){
                    currentGSP.set(1,"2");
                }
                projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        currentGSP.get(0),
                        currentGSP.get(1),
                        currentGSP.get(2),
                        currentGSP.get(3)};
                //NOTICE THIS IS ALWAYS DEFAULTING TO PHASE 1

                String selection="variable_phase_levels.group_id="+currentGSP.get(0)+
                        " AND variable_phase_levels.phase_id="+currentGSP.get(1)+
                        " AND ((variable_phase_levels.level_number="+currentGSP.get(3)+"-1 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-2 "+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+"-3"+
                        " OR variable_phase_levels.level_number="+currentGSP.get(3)+" ) "+
                        " OR variable_phase_levels.level_number<="+currentGSP.get(3)+" -3)"+
                        " AND variable_phase_levels.level_number!=0";

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_WORDS_BY_LEVEL, projection, selection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {

            default:
            case MotoliConstants.ALL_LOWERCASE_LETTERS:{
                mAllLowerCaseLetters=new ArrayList<ArrayList<String>>();
                int mCount=0;
                if(data.moveToFirst()){
                    do{
                        mAllLowerCaseLetters.add(new ArrayList<String>());
                        mAllLowerCaseLetters.get(mCount).add(data.getString(data.getColumnIndex("letter_text")));
                        mAllLowerCaseLetters.get(mCount).add(data.getString(data.getColumnIndex("audio_url")));
                        mAllLowerCaseLetters.get(mCount).add(data.getString(data.getColumnIndex("image_url")));
                        mCount++;
                    }while(data.moveToNext());
                }
                Collections.shuffle(mAllLowerCaseLetters);
                getLoaderManager().restartLoader(MotoliConstants.CURRENT_WORDS, null, this);
                break;
            }
            case MotoliConstants.CURRENT_WORDS:{
                //processData(data);
                break;
            }
        }
        data.close();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

}
