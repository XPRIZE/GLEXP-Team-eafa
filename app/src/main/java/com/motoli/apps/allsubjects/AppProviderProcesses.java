package com.motoli.apps.allsubjects;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 9/19/2016.
 */
public class AppProviderProcesses {


    private SQLiteDatabase mDatabase;
    private ArrayList<HashMap<String,String>> mUserGPL;
    private Context mContext;
    private Motoli_Application mAppData;

    public AppProviderProcesses(Context mContext,
                                ArrayList<HashMap<String,String>> mUserGPL,
                                SQLiteDatabase mDatabase,
                                Motoli_Application mAppData){
        this.mContext = mContext;
        this.mUserGPL = mUserGPL;
        this.mDatabase = mDatabase;
        this.mAppData = mAppData;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////

    protected Cursor groupPhaseLevels(String mAppUserId){
        Log.d(Constants.LOGCAT, "groupPhaseLevels");

        String mRawSQL="SELECT * FROM app_users_current_gpl  " +
                "WHERE app_users_current_gpl.app_user_id="+mAppUserId+" "+
                "ORDER BY app_users_current_gpl.group_id ASC, "+
                "app_users_current_gpl.phase_id ASC";
        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);

        Log.d(Constants.LOGCAT, "end groupPhaseLevelsUpdate");

        return mCursor;
    }


    /////////////////////////////////////////////////////////////////////////////////////////

    protected Cursor groupPhaseLevelsUpdate( String mAppUserId){

        String mRawSQL;
        Cursor mCursor;

        for(int i=0;i<mUserGPL.size();i++){
            mRawSQL="SELECT MIN(auavv.number_correct_in_a_row) AS minNumberCorrect " +
                    "FROM app_users_activity_variable_values auavv "+
                    "WHERE auavv.level_number="+
                    mUserGPL.get(i).get("app_user_current_level")+ " " +
                    "AND auavv.group_id="+mUserGPL.get(i).get("group_id")+" " +
                    "AND auavv.phase_id="+mUserGPL.get(i).get("phase_id");

            mCursor = mDatabase.rawQuery(mRawSQL, null);
            mCursor.moveToFirst();
            int mMinNumberCorrect;
            if(mCursor.isNull(mCursor.getColumnIndex("minNumberCorrect"))){
                /*
                 * puts variables in if nothing is created yet
                 */
                switch(Integer.parseInt(mUserGPL.get(i).get("group_id"))){
                    default:
                        break;
                    case 2:    //group for letters/phonics
                        switch(Integer.parseInt(mUserGPL.get(i).get("phase_id"))){
                            default:
                                break;
                            case 1:
                                processGroup2Phase1NULL(mAppUserId, mUserGPL.get(i));
                                break;
                            case 2:
                                processGroup2Phase2NULL( mAppUserId, mUserGPL.get(i));
                                break;
                            case 3:
                                processGroup2Phase3NULL(mAppUserId, mUserGPL.get(i));
                                break;
                        }//end switch(Integer.parseInt(mUserGPL.get(i).get(3))){
                        break;
                    case 3:  //group for syllables
                        if(mUserGPL.get(i).get("phase_id").equals("1")){
                            processGroup3Phase1NULL( mAppUserId, mUserGPL.get(i));
                        }else{
                            processGroup3Phase2NULL( mAppUserId, mUserGPL.get(i));
                        }
                        break;
                    case 4:
                        processGroup4NULL(mAppUserId, mUserGPL.get(i));
                        break;
                    case 5:
                        processGroup5NULL();
                        break;
                    case 6:
                        processGroup6NULL(mAppUserId, mUserGPL.get(i));
                        break;
                    case 7:
                        processGroup7NULL(mAppUserId, mUserGPL.get(i));
                        break;
                    case 8:
                        processGroup8NULL(mAppUserId, mUserGPL.get(i));
                        break;
                }//end switch(Integer.parseInt(mUserGPL.get(i).get(2))){
            }else{
                /**
                 * Take care of updating variables is needed
                 */
                int mInARowCorrect=Constants.INA_ROW_CORRECT;
                if(mUserGPL.get(i).get("group_id").equals("6")
                        && Integer.parseInt(mUserGPL.get(i).get("app_user_current_level"))>6){
                    mInARowCorrect=Constants.INA_ROW_CORRECT_HIGHER;
                }else if(mUserGPL.get(i).get("group_id").equals("6")){
                    mInARowCorrect=Constants.INA_ROW_CORRECT_WORDS;
                }else  if(mUserGPL.get(i).get("group_id").equals("4")) {
                    mInARowCorrect = Constants.INA_ROW_CORRECT_WORDS;
                }else if(mUserGPL.get(i).get("group_id").equals("3")){
                    mInARowCorrect = Constants.INA_ROW_CORRECT_WORDS;
                }else if(mUserGPL.get(i).get("group_id").equals("8")){
                    mInARowCorrect = Constants.MATH_OPERATIONS_INA_ROW_CORRECT;
                }
                //  || mCurrentActivities.get(i).get(4).equals("CS")
                mMinNumberCorrect = mCursor.getInt(mCursor.getColumnIndex("minNumberCorrect"));
                if (mMinNumberCorrect >= mInARowCorrect){
                        /*
                        || (mMinNumberCorrect>=Constants.INA_ROW_CORRECT_WORDS
                            && (mUserGPL.get(i).get(2).equals("4")
                            || mUserGPL.get(i).get(2).equals("6")))
                            */
                    switch (Integer.parseInt(mUserGPL.get(i).get("group_id"))) {
                        default:
                            break;
                        case 2:    //group for letters/phonics
                            switch (Integer.parseInt(mUserGPL.get(i).get("phase_id"))) {
                                default:
                                    break;
                                case 1:
                                    processGroup2Phase1(mAppUserId, mUserGPL.get(i));
                                    break;
                                case 2:
                                    processGroup2Phase2(mAppUserId, mUserGPL.get(i));
                                    break;
                                case 3:
                                    processGroup2Phase3(mAppUserId, mUserGPL.get(i));
                                    break;
                            }//end switch(Integer.parseInt(mUserGPL.get(i).get(3))){

                            break;
                        case 3:    //group for syllables
                            if(mUserGPL.get(i).get("phase_id").equals("1")){
                                processGroup3Phase1(mAppUserId, mUserGPL.get(i));
                            }else {
                                processGroup3Phase2(mAppUserId, mUserGPL.get(i));
                            }
                            break;
                        case 4:   //group for words
                            processGroup4(mAppUserId, mUserGPL.get(i));
                            break;
                        case 5:
                            processGroup5();
                            break;
                        case 6:
                            processGroup6(mAppUserId, mUserGPL.get(i));
                            break;
                        case 7:
                            processGroup7( mAppUserId, mUserGPL.get(i));
                            break;
                        case 8:
                            processGroup8( mAppUserId, mUserGPL.get(i));
                            break;
                    }//end switch(Integer.parseInt(mUserGPL.get(i).get(2))){
                }//end  if ((mMinNumberCorrect >= Constants.INA_ROW_CORRECT)
            }

        }//end for(int i=0;i<mRegularGPL.size();i++){

        mRawSQL="SELECT * FROM app_users_current_gpl  " +
                "WHERE app_users_current_gpl.app_user_id="+mAppUserId+" "+
                "ORDER BY app_users_current_gpl.group_id ASC, "+
                "app_users_current_gpl.phase_id ASC";
        mCursor = mDatabase.rawQuery(mRawSQL, null);

        Log.d(Constants.LOGCAT, "end groupPhaseLevelsUpdateAll");

        return mCursor;


    }




    //////////////////////////////////////////////////////////////////////////////////////////////


    protected Cursor groupPhaseLevelsUpdateAll(String mAppUserId){


        String mRawSQL;
        Cursor mCursor;
        int mGroupPhaseCount=0;

        for(int i=0;i<mUserGPL.size();i++){
            /*
            mRawSQL="SELECT MIN(auavv.number_correct_in_a_row) AS minNumberCorrect, "+
                    " variable_phase_levels.* "+
                    " FROM app_users_activity_variable_values auavv"+
                    " LEFT JOIN variable_phase_levels "+
                    " ON variable_phase_levels.variable_id = auavv.variable_id "+
                    " WHERE variable_phase_levels.group_id="+mUserGPL.get(i).get("group_id")+
                    " AND variable_phase_levels.phase_id="+mUserGPL.get(i).get("phase_id")+
                    " AND variable_phase_levels.level_number="+
                    mUserGPL.get(i).get("app_user_current_level")+
                    " AND auavv.type_id="+mUserGPL.get(i).get("type_id");
            */

            mRawSQL="SELECT MIN(app_users_activity_variable_values.number_correct_in_a_row) " +
                    "AS minNumberCorrect " +
                    "FROM app_users_activity_variable_values " +
                    "WHERE app_users_activity_variable_values.level_number" +
                    "=" + mUserGPL.get(i).get("app_user_current_level") + " " +
                    "AND app_users_activity_variable_values.group_id" +
                    "=" + mUserGPL.get(i).get("group_id") + " " +
                    "AND app_users_activity_variable_values.phase_id" +
                    "=" + mUserGPL.get(i).get("phase_id") + " ";
            mCursor = mDatabase.rawQuery(mRawSQL, null);
            mCursor.moveToFirst();
            int mMinNumberCorrect;
            if(mCursor.isNull(mCursor.getColumnIndex("minNumberCorrect"))){
                /*
                 * puts variables in if nothing is created yet
                 */
                switch(Integer.parseInt(mUserGPL.get(i).get("group_id"))){
                    default:
                        break;
                    case 2:    //group for letters/phonics
                        switch(Integer.parseInt(mUserGPL.get(i).get("phase_id"))){
                            default:
                                break;
                            case 1:
                                processGroup2Phase1NULL( mAppUserId, mUserGPL.get(i));
                                break;
                            case 2:
                                processGroup2Phase2NULL( mAppUserId, mUserGPL.get(i));
                                break;
                            case 3:
                                processGroup2Phase3NULL(mAppUserId, mUserGPL.get(i));
                                break;
                        }//end switch(Integer.parseInt(mUserGPL.get(i).get(3))){

                        break;
                    case 3:    //group for syllables
                        if(mUserGPL.get(i).get("phase_id").equals("1")){
                            processGroup3Phase1NULL(mAppUserId, mUserGPL.get(i));
                        }else{
                            processGroup3Phase2NULL(mAppUserId, mUserGPL.get(i));
                        }
                        break;
                    case 4:
                        processGroup4NULL(mAppUserId, mUserGPL.get(i));
                        break;
                    case 5:
                        processGroup5NULL();
                        break;
                    case 6:
                        processGroup6NULL( mAppUserId, mUserGPL.get(i));
                        break;
                    case 7:
                        processGroup7NULL(mAppUserId, mUserGPL.get(i));
                        break;
                    case 8:
                        processGroup8NULL(mAppUserId, mUserGPL.get(i));
                        break;
                }//end switch(Integer.parseInt(mUserGPL.get(i).get(2))){
            }else{
                /*
                 * Take care of updating variables is needed
                 */
                int mInARowCorrect=Constants.INA_ROW_CORRECT;
                if(mUserGPL.get(i).get("group_id").equals("6")
                        && Integer.parseInt(mUserGPL.get(i).get("app_user_current_level"))>6){
                    mInARowCorrect=Constants.INA_ROW_CORRECT_HIGHER;
                }else if(mUserGPL.get(i).get("group_id").equals("6")){
                    mInARowCorrect=Constants.INA_ROW_CORRECT_WORDS;
                }else  if(mUserGPL.get(i).get("group_id").equals("4")) {
                    mInARowCorrect = Constants.INA_ROW_CORRECT_WORDS;
                }else if(mUserGPL.get(i).get("group_id").equals("3")){
                    mInARowCorrect = Constants.INA_ROW_CORRECT_WORDS;
                }else if(mUserGPL.get(i).get("group_id").equals("8")){
                    mInARowCorrect = Constants.MATH_OPERATIONS_INA_ROW_CORRECT;
                }
                mMinNumberCorrect = mCursor.getInt(mCursor.getColumnIndex("minNumberCorrect"));
                if (mMinNumberCorrect >= mInARowCorrect){

                    mGroupPhaseCount = (mGroupPhaseCount>0) ? mGroupPhaseCount-- : 0 ;
                    switch (Integer.parseInt(mUserGPL.get(i).get("group_id"))) {
                        default:
                            mGroupPhaseCount++;
                            break;
                        case 2:   //group for letters/phonics
                            switch (Integer.parseInt(mUserGPL.get(i).get("phase_id"))) {
                                default:
                                    break;
                                case 1:
                                    processGroup2Phase1(mAppUserId, mUserGPL.get(i));
                                    break;
                                case 2:
                                    processGroup2Phase1(mAppUserId, mUserGPL.get(i));
                                    break;
                                case 3:
                                    processGroup2Phase3(mAppUserId, mUserGPL.get(i));
                                    break;
                            }//end switch(Integer.parseInt(mUserGPL.get(i).get(3))){
                            break;
                        case 3:   //group for syllables
                            if(mUserGPL.get(i).get("phase_id").equals("1")){
                                processGroup3Phase1(mAppUserId, mUserGPL.get(i));
                            }else {
                                processGroup3Phase2(mAppUserId, mUserGPL.get(i));
                            }
                            break;
                        case 4:   //group for words
                            processGroup4(mAppUserId, mUserGPL.get(i));
                            break;
                        case 5:
                            processGroup5();
                            break;
                        case 6:
                            processGroup6(mAppUserId, mUserGPL.get(i));
                            break;
                        case 7:
                            processGroup7(mAppUserId, mUserGPL.get(i));
                            break;
                        case 8:
                            processGroup8(mAppUserId, mUserGPL.get(i));
                            break;
                    }//end switch(Integer.parseInt(mUserGPL.get(i).get(2))){
                }else{
                    mGroupPhaseCount++;
                    Log.d(Constants.LOGCAT, "done with adding all variables");
                }
            }

        }//end for(int i=0;i<mRegularGPL.size();i++){

        mRawSQL="SELECT * FROM app_users_current_gpl  " +
                "WHERE app_users_current_gpl.app_user_id="+mAppUserId+" "+
                "ORDER BY app_users_current_gpl.group_id ASC, "+
                "app_users_current_gpl.phase_id ASC";
        mCursor = mDatabase.rawQuery(mRawSQL, null);

        Log.d(Constants.LOGCAT, "end groupPhaseLevelsUpdateAll");

        return mCursor;
    }



    /////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////////


    protected Cursor processesIndividualSections(String[] mInfo){

        String mRawSQL;

        String mAppUserID=mInfo[0];
        String mGroupID=mInfo[1];
        String mPhaseID=mInfo[2];
        String mCurrentLevel=mInfo[4];
        String mDesiredLevel=mInfo[3];

        /*
         * Pattern for:
         * advanceGroupPhasesToNewLevel(mDatabase,group_id,phase_id,desired_level,app_user_id);
         *
         * Pattern for:
         * processGroup2Phase1(mDatabase, mAppUserId,   mUserGPLCurrent)
         *
         * Pattern for:
         * ArrayList<HashMap<String, String>> getUserGPL(mDatabase, mAppUserId, mGroupID){
         */


        switch(Integer.parseInt(mGroupID)){
            default:
                break;
            case 2:
                switch(Integer.parseInt(mInfo[2])){
                    default:
                    case 1:
                        mRawSQL="UPDATE app_users_activity_variable_values " +
                                "SET number_correct_in_a_row=12 " +
                                "WHERE group_id=" + mGroupID + " " +
                                "AND phase_id=" + mPhaseID + " " +
                                "AND level_number<=" + mCurrentLevel;
                        mDatabase.execSQL(mRawSQL);

                        for(int i=(Integer.parseInt(mCurrentLevel)+1);
                            i<=Integer.parseInt(mDesiredLevel);
                            i++){
                            Log.d(Constants.LOGCAT,"Lower moving to level: "+i);
                            processGroup2Phase1( mAppUserID,mUserGPL.get(0));
                        }
                        groupPhaseLevelsUpdateAll( mAppUserID);
                        break;
                    case 2:
                        for(int i=(Integer.parseInt(mCurrentLevel)+1);
                            i<=Integer.parseInt(mDesiredLevel);
                            i++){
                            Log.d(Constants.LOGCAT,"Upper moving to level: "+i);
                            processGroup2Phase2( mAppUserID,mUserGPL.get(1));
                        }
                        mRawSQL="UPDATE app_users_activity_variable_values " +
                                "SET number_correct_in_a_row=12 " +
                                "WHERE group_id=" + mGroupID + " " +
                                "AND phase_id=" + mPhaseID + " " +
                                "AND level_number<" + mDesiredLevel;
                        mDatabase.execSQL(mRawSQL);
                        break;
                    case 3:
                        for(int i=(Integer.parseInt(mCurrentLevel)+1);
                            i<=Integer.parseInt(mDesiredLevel);
                            i++){
                            Log.d(Constants.LOGCAT,"Phonics moving to level: "+i);
                            processGroup2Phase3( mAppUserID,mUserGPL.get(2));
                        }
                        mRawSQL="UPDATE app_users_activity_variable_values " +
                                "SET number_correct_in_a_row=12 " +
                                "WHERE group_id=" + mGroupID + " " +
                                "AND phase_id=" + mPhaseID + " " +
                                "AND level_number<" + mDesiredLevel;
                        mDatabase.execSQL(mRawSQL);

                        groupPhaseLevelsUpdateAll(mAppUserID);

                        break;
                }
                break;
            case 3:
                if(mInfo[2].equals("1")){
                    for(int i=(Integer.parseInt(mCurrentLevel)+1);
                        i<=Integer.parseInt(mDesiredLevel);
                        i++){
                        Log.d(Constants.LOGCAT,"Syllables moving to level: "+i);
                        processGroup3Phase1( mAppUserID,mUserGPL.get(3));
                    }
                    mRawSQL="UPDATE app_users_activity_variable_values " +
                            "SET number_correct_in_a_row=12 " +
                            "WHERE group_id=" + mGroupID + " " +
                            "AND phase_id=" + mPhaseID + " " +
                            "AND level_number<" + mDesiredLevel;
                    mDatabase.execSQL(mRawSQL);
                }
                break;
            case 4:
                for(int i=(Integer.parseInt(mCurrentLevel)+1);
                    i<=Integer.parseInt(mDesiredLevel);
                    i++){
                    Log.d(Constants.LOGCAT,"Words moving to level: "+i);
                    processGroup4(mAppUserID,mUserGPL.get(5));
                }

                mRawSQL="UPDATE app_users_activity_variable_values " +
                        "SET number_correct_in_a_row=12 " +
                        "WHERE group_id=" + mGroupID + " " +
                        "AND phase_id=" + mPhaseID + " " +
                        "AND level_number<" + mDesiredLevel;
                mDatabase.execSQL(mRawSQL);
                break;
            case 6:
                for(int i=(Integer.parseInt(mCurrentLevel)+1);
                    i<=Integer.parseInt(mDesiredLevel);
                    i++){
                    Log.d(Constants.LOGCAT,"Shapes moving to level: "+i);
                    processGroup6( mAppUserID,mUserGPL.get(6));
                }
                mRawSQL="UPDATE app_users_activity_variable_values " +
                        "SET number_correct_in_a_row=12 " +
                        "WHERE group_id=" + mGroupID + " " +
                        "AND phase_id=" + mPhaseID + " " +
                        "AND level_number<" + mDesiredLevel;
                mDatabase.execSQL(mRawSQL);
                break;
            case 8:
                for(int i=(Integer.parseInt(mCurrentLevel)+1);
                    i<=Integer.parseInt(mDesiredLevel);
                    i++){
                    Log.d(Constants.LOGCAT,"Math Operations moving to level: "+i);
                    processGroup8(mAppUserID,mUserGPL.get(8));
                }
                mRawSQL="UPDATE app_users_activity_variable_values " +
                        "SET number_correct_in_a_row=12 " +
                        "WHERE group_id=" + mGroupID + " " +
                        "AND phase_id=" + mPhaseID + " " +
                        "AND level_number<" + mDesiredLevel;
                mDatabase.execSQL(mRawSQL);
                break;
        }
        mRawSQL = "SELECT * FROM activities LIMIT 1";
        return mDatabase.rawQuery(mRawSQL, null);
    }
    private void processGroup2Phase1NULL( String mAppUserId,
                                          HashMap<String,String> mUserGPLCurrent){

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;

        if(Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))<=1) {

            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=4 " +
                    "WHERE group_id=2 " +
                    "AND phase_id=1 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level", "4");

            String mLetterSelection = "variable_phase_levels.group_id = " +
                    mUserGPLCurrent.get("group_id") + " " +
                    "AND variable_phase_levels.phase_id = " +
                    mUserGPLCurrent.get("phase_id") + " " +
                    "AND variable_phase_levels.level_number <= " +
                    mUserGPLCurrent.get("app_user_current_level")+" "+
                    "AND variable_type_relations.variable_type_id=1";

            String[] mLetterProjection = new String[]{
                    "0",
                    mAppUserId,
                    mUserGPLCurrent.get("group_id"),
                    mUserGPLCurrent.get("phase_id"),
                    "3",
                    "4"
            };


            mUpdateGroups.updateLetterProcess(mLetterProjection,
                    mLetterSelection);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup2Phase2NULL(String mAppUserId,
                                         HashMap<String,String> mUserGPLCurrent){

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;

        int lc_max_level_number=0;
        int lc_current_level=0;
        int uc_max_level_number=0;
        int uc_current_level=0;

        mRawSQL="SELECT MAX(vpl1.level_number) AS lc_max_level_number, " +
                "aucg1.app_user_current_level as lc_current_level, " +
                "MAX(vpl2.level_number) AS uc_max_level_number, " +
                "aucg2.app_user_current_level as uc_current_level " +
                "FROM variable_phase_levels AS vpl1 " +
                "INNER JOIN app_users_current_gpl AS aucg1 " +
                "ON aucg1.group_id=2  " +
                "AND aucg1.phase_id=1  " +
                "INNER JOIN variable_phase_levels AS vpl2 " +
                "ON vpl2.group_id=2 " +
                "AND vpl2.phase_id=2 " +
                "INNER JOIN app_users_current_gpl AS aucg2 " +
                "ON aucg2.group_id=2  " +
                "AND aucg2.phase_id=2 " +
                "WHERE vpl1.group_id=2   " +
                "AND vpl1.phase_id=1 " +
                "LIMIT 1";
        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        if(mLevelCursor.moveToFirst()){
            lc_max_level_number=mLevelCursor.getInt(mLevelCursor.getColumnIndex("lc_max_level_number"));
            lc_current_level=mLevelCursor.getInt(mLevelCursor.getColumnIndex("lc_current_level"));
            uc_max_level_number=mLevelCursor.getInt(mLevelCursor.getColumnIndex("uc_max_level_number"));
            uc_current_level=mLevelCursor.getInt(mLevelCursor.getColumnIndex("uc_current_level"));
        }
        mLevelCursor.close();

        if(lc_current_level>lc_max_level_number &&
                uc_current_level==0){
            mRawSQL="UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=4 "+
                    "WHERE group_id=2 " +
                    "AND phase_id=2 "+
                    "AND app_users_current_gpl.app_user_id="+mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level", "1");

            String mLetterSelection = "variable_phase_levels.group_id = 2 " +
                    "AND variable_phase_levels.phase_id = 2 " +
                    "AND variable_phase_levels.level_number <= 4 "+
                    "AND variable_type_relations.variable_type_id=2";

            String[] mLetterProjection = new String[]{
                    "0",
                    mAppUserId,
                    "2", //group_id
                    "2", //phase_id
                    "3", //section_id 4
                    "4" //level_number 5
            };
            mUpdateGroups.updateLetterProcess(mLetterProjection, mLetterSelection);

            mAppData.setCurrentGroup_Section_Phase("2","2","3","4");
        }else if(lc_current_level > lc_max_level_number &&
                uc_current_level <= uc_max_level_number) {
            mRawSQL="UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=app_user_current_level+1 "+
                    "WHERE group_id=2 " +
                    "AND phase_id=2 "+
                    "AND app_users_current_gpl.app_user_id="+mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level",
                    String.valueOf(Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))+1));

            String mLetterSelection = "variable_phase_levels.group_id = 2 " +
                    "AND variable_phase_levels.phase_id = 2 " +
                    "AND variable_phase_levels.level_number <= 1 "+
                    "AND variable_type_relations.variable_type_id=2";

            String[] mLetterProjection = new String[]{
                    "0",
                    mAppUserId,
                    "2", //group_id
                    "2", //phase_id
                    "3", //section_id 4
                    "4"//mUserGPLCurrent.get("app_user_current_level") //level_number 5
            };
            //updateLetterProcess(mLetterProjection, mLetterSelection);
            mUpdateGroups = new AppProviderUpdateGroups(mContext,
                    mUserGPL,
                    mDatabase);
            mUpdateGroups.updateLetterProcess(mLetterProjection, mLetterSelection);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup2Phase3NULL( String mAppUserId,
                                          HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        int lc_max_level_number=0;
        int lc_current_level=0;
        int phonic_max_level_number=0;
        int phonic_current_level=0;

        mRawSQL="SELECT MAX(vpl1.level_number) AS lc_max_level_number, " +
                "aucg1.app_user_current_level as lc_current_level, " +
                "MAX(vpl2.level_number) AS phonic_max_level_number, " +
                "aucg2.app_user_current_level as phonic_current_level " +
                "FROM variable_phase_levels AS vpl1 " +
                "INNER JOIN app_users_current_gpl AS aucg1 " +
                "ON aucg1.group_id=2  " +
                "AND aucg1.phase_id=1  " +
                "INNER JOIN variable_phase_levels AS vpl2 " +
                "ON vpl2.group_id=2 " +
                "AND vpl2.phase_id=3 " +
                "INNER JOIN app_users_current_gpl AS aucg2 " +
                "ON aucg2.group_id=2  " +
                "AND aucg2.phase_id=3 " +
                "WHERE vpl1.group_id=2   " +
                "AND vpl1.phase_id=1 " +
                "LIMIT 1";
        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        if(mLevelCursor.moveToFirst()){
            lc_max_level_number=mLevelCursor.getInt(mLevelCursor.getColumnIndex("lc_max_level_number"));
            lc_current_level=mLevelCursor.getInt(mLevelCursor.getColumnIndex("lc_current_level"));
            phonic_max_level_number=mLevelCursor.getInt(mLevelCursor.getColumnIndex("phonic_max_level_number"));
            phonic_current_level=mLevelCursor.getInt(mLevelCursor.getColumnIndex("phonic_current_level"));
        }
        mLevelCursor.close();


        if(lc_current_level>lc_max_level_number &&
                phonic_current_level==0){
            mRawSQL="UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=1 "+
                    "WHERE group_id=2 " +
                    "AND phase_id=3 "+
                    "AND app_user_id="+mAppUserId;
            mDatabase.execSQL(mRawSQL);
            mUserGPLCurrent.put("app_user_current_level","1");
            String mPhonicSelection = "variable_phase_levels.group_id = "+
                    mUserGPLCurrent.get("group_id")+" " +
                    "AND variable_phase_levels.phase_id = "+
                    mUserGPLCurrent.get("phase_id")+" " +
                    "AND variable_phase_levels.level_number <= "+
                    mUserGPLCurrent.get("app_user_current_level")+" "+
                    "AND variable_type_relations.variable_type_id=3";

            String[] mLetterProjection = new String[]{
                    "0",
                    mAppUserId,
                    mUserGPLCurrent.get("group_id"), //group_id
                    mUserGPLCurrent.get("phase_id"), //phase_id
                    "3", //section_id 4
                    mUserGPLCurrent.get("app_user_current_level") //level_number 5
            };
            mUpdateGroups.updatePhonicsProcess(mLetterProjection);
            mUpdateGroups.updatePhonicsProcessOLD(mLetterProjection,mPhonicSelection);


            int mSecondWordCount=0;

            mRawSQL= "SELECT COUNT(words._id) AS word_count " +
                    "FROM words " +
                    "INNER JOIN variable_phase_levels " +
                    "ON variable_phase_levels.variable_id=words.word_id " +
                    "WHERE  variable_phase_levels.group_id=4 " +
                    "AND variable_phase_levels.phase_id=1 " +
                    "AND variable_phase_levels.level_number="+
                    mUserGPLCurrent.get("app_user_current_level");
            Cursor mCountCursor = mDatabase.rawQuery(mRawSQL, null);
            if(mCountCursor.moveToFirst()){
                mSecondWordCount=mCountCursor.getInt(mCountCursor.getColumnIndex("word_count"));
            }
            mCountCursor.close();


            if( mSecondWordCount>=4){
                mRawSQL="UPDATE app_users_current_gpl " +
                        "SET app_user_current_level=1 " +
                        "WHERE group_id=4 " +
                        "AND phase_id=1 "+
                        "AND app_users_current_gpl.app_user_id="+mAppUserId;
                mDatabase.execSQL(mRawSQL);
                Log.d(Constants.LOGCAT, "line 1183: " + mRawSQL);




                String mWordSelection = "variable_phase_levels.group_id = 4 " +
                        "AND variable_phase_levels.phase_id = 1 " +
                        "AND variable_phase_levels.level_number <= "+
                        mUserGPLCurrent.get("app_user_current_level") + " " +
                        "AND variable_phase_levels.level_number>0 "+
                        "AND variable_type_relations.variable_type_id=5";

                String[] mWordProjection = new String[]{
                        "0", //0
                        mAppUserId, //1
                        "4", //group_id 2
                        "1", //phase_id 3
                        "11", //section_id 4
                        "1" //level_number 5
                };
                mUpdateGroups.updateWordsProcess(mWordProjection, mWordSelection);
                mUpdateGroups.updateWordsProcessNEW(mWordProjection);

            }else if(mSecondWordCount<4){
                mRawSQL="UPDATE app_users_current_gpl " +
                        "SET app_user_current_level=1 " +
                        "WHERE group_id=4 " +
                        "AND phase_id=1 "+
                        "AND app_users_current_gpl.app_user_id="+mAppUserId;
                mDatabase.execSQL(mRawSQL);
                Log.d(Constants.LOGCAT, "line 1215: " + mRawSQL);



                String mWordSelection = "variable_phase_levels.group_id = 4 " +
                        "AND variable_phase_levels.phase_id = 1 " +
                        "AND variable_phase_levels.level_number <= "+
                        mUserGPLCurrent.get("app_user_current_level") + " " +
                        "AND variable_phase_levels.level_number>0 "+
                        "AND variable_type_relations.variable_type_id=6";

                String[] mWordProjection = new String[]{
                        "1", //0
                        mAppUserId, //1
                        "4", //group_id 2
                        "1", //phase_id 3
                        "11", //section_id 4
                        "1" //level_number 5
                };
                mUpdateGroups.updateWordsProcess(mWordProjection, mWordSelection);
                mUpdateGroups.updateWordsProcessNEW(mWordProjection);

            }

        }else if(lc_current_level > lc_max_level_number &&
                phonic_current_level <= phonic_max_level_number){
            mRawSQL="UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=app_user_current_level+1 "+
                    "WHERE group_id=2 " +
                    "AND phase_id=3 "+
                    "AND app_users_current_gpl.app_user_id="+mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level",
                    String.valueOf(Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))+1));
            String mPhonicSelection = "variable_phase_levels.group_id = "+
                    mUserGPLCurrent.get("group_id")+" " +
                    "AND variable_phase_levels.phase_id = "+
                    mUserGPLCurrent.get("phase_id")+" " +
                    "AND variable_phase_levels.level_number <= "+
                    mUserGPLCurrent.get("app_user_current_level")+" "+
                    "AND variable_type_relations.variable_type_id=3 ";

            String[] mLetterProjection = new String[]{
                    "0",
                    mAppUserId,
                    mUserGPLCurrent.get("group_id"), //group_id
                    mUserGPLCurrent.get("phase_id"), //phase_id
                    "3", //section_id 4
                    mUserGPLCurrent.get("app_user_current_level") //level_number 5
            };
            mUpdateGroups.updatePhonicsProcess(mLetterProjection);
            mUpdateGroups.updatePhonicsProcessOLD(mLetterProjection,mPhonicSelection);


            /*
             * THE FOLLOWING MUST BE REMOVED LATER
             */
            mRawSQL="UPDATE app_users_activity_variable_values " +
                    "SET number_correct_in_a_row=2, " +
                    "number_correct=3 " +
                    "WHERE group_id=2 " +
                    "AND phase_id=3";
            mDatabase.execSQL(mRawSQL);
            /*
             * PART BEFORE MUST ALSO BE REMOVED
             */

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup3Phase1NULL(String mAppUserId,
                                         HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        mRawSQL="SELECT MAX(vpl.level_number) AS max_level_number, " +
                "app_users_current_gpl.app_user_current_level " +
                "FROM variable_phase_levels AS vpl " +
                "INNER JOIN app_users_current_gpl " +
                "ON app_users_current_gpl.group_id=3 " +
                "AND app_users_current_gpl.phase_id=1 " +
                "WHERE vpl.group_id=3 " +
                "AND vpl.phase_id=1 " +
                "LIMIT 1";
        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        int mMaxLevel=0;
        int mCurrentLevel=0;
        if(mLevelCursor.moveToFirst()){
            mMaxLevel=mLevelCursor.getInt(
                    mLevelCursor.getColumnIndex("max_level_number"));
            mCurrentLevel=mLevelCursor.getInt(
                    mLevelCursor.getColumnIndex("app_user_current_level"));
        }
        mLevelCursor.close();

        if(mCurrentLevel<=mMaxLevel) {


            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=1 " +
                    "WHERE group_id=3 " +
                    "AND phase_id=1 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level", "1");

            String mSelection = "variable_phase_levels.group_id = 3 " +
                    "AND variable_phase_levels.phase_id = 1 " +
                    "AND variable_phase_levels.level_number = 1 " +
                    "AND variable_phase_levels.level_number>0 " +
                    "AND variable_type_relations.variable_type_id=4";

            String[] mSyllableWordProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    mUserGPLCurrent.get("group_id"), //group_id 2
                    mUserGPLCurrent.get("phase_id"), //phase_id 3
                    "8", //section_id 4
                    "1" //level_number 5
            };
            mUpdateGroups.updateSyllableWordsProcess(mSyllableWordProjection, mSelection);
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup3Phase2NULL(String mAppUserId,
                                         HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        mRawSQL="SELECT * FROM app_users_current_gpl " +
                "WHERE group_id=2 " +
                "AND phase_id=3 "+
                "AND app_users_current_gpl.app_user_id="+mAppUserId+" " +
                "UNION " +
                "SELECT * FROM app_users_current_gpl " +
                "WHERE group_id=3 " +
                "AND phase_id=2 "+
                "AND app_users_current_gpl.app_user_id="+mAppUserId;

        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        int[] mLevels=new int[]{0,0};
        if(mLevelCursor.moveToFirst()){
            for(int j=0;j<2;j++){
                mLevels[j] = mLevelCursor.getInt(
                        mLevelCursor.getColumnIndex("app_user_current_level"));
                if(!mLevelCursor.moveToNext()){
                    break;
                }
            }
        }

        if((mLevels[0]-1)>mLevels[1]) {
            //  if(mUserGPL.get(2).get(4).equals("2")) {
            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=app_user_current_level+1 " +
                    "WHERE group_id=3 " +
                    "AND phase_id=2 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mRawSQL="SELECT * FROM app_users_current_gpl " +
                    "WHERE group_id=3 " +
                    "AND phase_id=2 "+
                    "AND app_users_current_gpl.app_user_id="+mAppUserId;

            mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
            if(mLevelCursor.moveToFirst()){

                mUserGPLCurrent.put("app_user_current_level", mLevelCursor.getString(
                        mLevelCursor.getColumnIndex("app_user_current_level")));
            }
            String mSyllableSelection = "variable_phase_levels.group_id = " +
                    mUserGPLCurrent.get("group_id") + " " +
                    "AND variable_phase_levels.phase_id = " +
                    mUserGPLCurrent.get("phase_id") + " " +
                    "AND variable_phase_levels.level_number <= " +
                    mUserGPLCurrent.get("app_user_current_level") + " " +
                    "AND variable_type_relations.variable_type_id=5";

            String[] mSyllableProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    mUserGPLCurrent.get("group_id"), //group_id 2
                    mUserGPLCurrent.get("phase_id"), //phase_id 3
                    "8", //section_id 4
                    mUserGPLCurrent.get("app_user_current_level") //level_number 5
            };
            mUpdateGroups.updateSyllableProcess(mSyllableProjection, mSyllableSelection);
        }
        mLevelCursor.close();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup4NULL(String mAppUserId,
                                   HashMap<String,String> mUserGPLCurrent) {
        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        /*
         * Group for Words based upon levels in phonics
         * Ignore until phonics level has 1 and at least 4 words are avaialble
         */
        mRawSQL="SELECT * FROM app_users_current_gpl " +
                "WHERE group_id=2 " +
                "AND phase_id=3 "+
                "AND app_users_current_gpl.app_user_id="+mAppUserId+" " +
                "UNION " +
                "SELECT * FROM app_users_current_gpl " +
                "WHERE group_id=4 " +
                "AND phase_id=1 "+
                "AND app_users_current_gpl.app_user_id="+mAppUserId;
        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        int[] mLevels=new int[]{0,0};
        if(mLevelCursor.moveToFirst()){
            for(int j=0;j<2;j++){
                mLevels[j] = mLevelCursor.getInt(
                        mLevelCursor.getColumnIndex("app_user_current_level"));
                if(!mLevelCursor.moveToNext()){
                    break;
                }
            }
        }
        mLevelCursor.close();
        if((mLevels[0]-1)>mLevels[1]) {
            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level="+mLevels[1]+"+1 "+ //app_user_current_level+1 " +
                    "WHERE group_id=4 " +
                    "AND phase_id=1 " +
                    "AND app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);
            mUserGPLCurrent.put("app_user_current_level", String.valueOf(mLevels[1]));
            String mWordSelection = "variable_phase_levels.group_id = " +
                    mUserGPLCurrent.get("group_id") + " " +
                    "AND variable_phase_levels.phase_id = " +
                    mUserGPLCurrent.get("phase_id") + " " +
                    "AND variable_phase_levels.level_number <= " +
                    mUserGPLCurrent.get("app_user_current_level") + " " +
                    "AND variable_phase_levels.level_number >0 " +
                    "AND variable_type_relations.variable_type_id=6";
            String[] mWordProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    mUserGPLCurrent.get("group_id"), //group_id 2
                    mUserGPLCurrent.get("phase_id"), //phase_id 3
                    "11", //section_id 4
                    "1" //level_number 5
            };
            mUpdateGroups.updateWordsProcess(mWordProjection, mWordSelection);
            mUpdateGroups.updateWordsProcessNEW(mWordProjection);


            /*
             * THE FOLLOWING MUST BE REMOVED LATER
             */
            mRawSQL="UPDATE app_users_activity_variable_values " +
                    "SET number_correct_in_a_row=2, " +
                    "number_correct=3 " +
                    "WHERE group_id=4";
            mDatabase.execSQL(mRawSQL);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup5NULL() {    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup6NULL(String mAppUserId,
                                   HashMap<String,String> mUserGPLCurrent) {
        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        if(Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))<=1) {
            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=1 " +
                    "WHERE group_id=6 " +
                    "AND phase_id=1 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);
            String mLetterSelection = "variable_phase_levels.group_id = 6 " +
                    "AND variable_phase_levels.phase_id = 1 " +
//                                    "AND variable_phase_levels.level_number <= 1 "+
                    "AND variable_type_relations.variable_type_id=9 ";
            String[] mLetterProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    "6", //group_id 2
                    "1", //phase_id 3
                    "17", //section_id 4
                    "1" //level_number 5
            };
            mUpdateGroups.updateMathShapesProcess(mLetterProjection, mLetterSelection);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup7NULL(String mAppUserId,
                                   HashMap<String,String> mUserGPLCurrent) {
        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        if(Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))<1) {

            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=1 " +
                    "WHERE group_id=7 " +
                    "AND phase_id=1 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);
            String mLetterSelection = "variable_phase_levels.group_id = 7 " +
                    "AND variable_phase_levels.phase_id = 1 " +
                    "AND variable_phase_levels.level_number <= 1 "+
                    "AND variable_type_relations.variable_type_id=10 ";
            String[] mLetterProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    "7", //group_id 2
                    "1", //phase_id 3
                    "20", //section_id 4
                    "1" //level_number 5
            };
            mUpdateGroups.updateMathNumbersProcess(mLetterProjection, mLetterSelection);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup8NULL(String mAppUserId,
                                   HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        String mFirstLevel="1";
        if(Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))<=1) {

            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level= " +mFirstLevel+" "+
                    "WHERE group_id=8 " +
                    "AND phase_id=1 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            String[] mProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    "8", //group_id 2
                    "1", //phase_id 3
                    "23", //section_id 4
                    mFirstLevel //level_number 5
            };

            mUpdateGroups.updateMathOperationsProcess(mProjection);
        }
    }


    private void processGroup2Phase2(String mAppUserId,
                                     HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        mRawSQL="UPDATE app_users_current_gpl " +
                "SET app_user_current_level=app_user_current_level+1 " +
                "WHERE group_id=2 " +
                "AND phase_id=2 "+
                "AND app_users_current_gpl.app_user_id="+mAppUserId;
        mDatabase.execSQL(mRawSQL);

        mUserGPLCurrent.put("app_user_current_level",
                String.valueOf(Integer.parseInt(
                        mUserGPLCurrent.get("app_user_current_level"))+1));

        String mLetterSelection = "variable_phase_levels.group_id = "+
                mUserGPLCurrent.get("group_id")+" " +
                "AND variable_phase_levels.phase_id = "+
                mUserGPLCurrent.get("phase_id")+" " +
                "AND variable_phase_levels.level_number <= "+
                mUserGPLCurrent.get("app_user_current_level")+" "+
                "AND variable_type_relations.variable_type_id=2";

        String[] mLetterProjection = new String[]{
                "0", //0
                mAppUserId, //1
                mUserGPLCurrent.get("group_id"), //group_id 2
                mUserGPLCurrent.get("phase_id"), //phase_id 3
                "3", //section_id 4
                mUserGPLCurrent.get("app_user_current_level") //level_number 5
        };

        mUpdateGroups.updateLetterProcess(mLetterProjection, mLetterSelection);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup2Phase3(String mAppUserId,
                                     HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        int mMaxLevel=0;
        mRawSQL="SELECT MAX(level_number) AS max_level_number " +
                "FROM variable_phase_levels " +
                "WHERE group_id=2 " +
                "AND phase_id=3";
        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        if(mLevelCursor.moveToFirst()){
            mMaxLevel=mLevelCursor.getInt(
                    mLevelCursor.getColumnIndex("max_level_number"));
        }
        mLevelCursor.close();

        if(mMaxLevel>Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))) {
            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=app_user_current_level+1 " +
                    "WHERE group_id=2 " +
                    "AND phase_id=3 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level",
                    String.valueOf(Integer.parseInt(mUserGPLCurrent.get("app_user_current_level")) + 1));

            String mLetterSelection = "variable_phase_levels.group_id = " +
                    mUserGPLCurrent.get("group_id") + " " +
                    "AND variable_phase_levels.phase_id = " +
                    mUserGPLCurrent.get("phase_id") + " " +
                    "AND variable_phase_levels.level_number <= " +
                    mUserGPLCurrent.get("app_user_current_level") + " " +
                    "AND variable_phase_levels.level_number!=0 " +
                    "AND variable_type_relations.variable_type_id=3";

            String[] mLetterProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    mUserGPLCurrent.get("group_id"), //group_id 2
                    mUserGPLCurrent.get("phase_id"), //phase_id 3
                    "5", //section_id 4
                    mUserGPLCurrent.get("app_user_current_level") //level_number 5
            };
            mUpdateGroups.updatePhonicsProcess(mLetterProjection);
            mUpdateGroups.updatePhonicsProcessOLD(mLetterProjection,mLetterSelection);

            mUpdateGroups.wordsPhaseLevelsUpdate(mAppUserId);
        }else if(mMaxLevel==Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))) {
            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=" +mMaxLevel+" "+
                    "WHERE group_id=2 " +
                    "AND phase_id=3 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup3Phase1( String mAppUserId,
                                      HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        mRawSQL="UPDATE app_users_current_gpl " +
                "SET app_user_current_level=app_user_current_level+1 " +
                "WHERE group_id=3 " +
                "AND phase_id=1 "+
                "AND app_users_current_gpl.app_user_id="+mAppUserId;
        mDatabase.execSQL(mRawSQL);

        mUserGPLCurrent.put("app_user_current_level",
                String.valueOf(Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))+1));

        String mSyllableWordSelection = "variable_phase_levels.group_id = 3 " +
                "AND variable_phase_levels.phase_id = 1 " +
                "AND variable_phase_levels.level_number <= "+
                mUserGPLCurrent.get("app_user_current_level")+" "+
                "AND variable_phase_levels.level_number>0 "+
                "AND variable_type_relations.variable_type_id=4";

        String[] mSyllableWordProjection = new String[]{
                "0", //0
                mAppUserId, //1
                mUserGPLCurrent.get("group_id"), //group_id 2
                mUserGPLCurrent.get("phase_id"), //phase_id 3
                "8", //section_id 4
                mUserGPLCurrent.get("app_user_current_level")//level_number 5
        };

        mUpdateGroups.updateSyllableWordsProcess(mSyllableWordProjection,
                mSyllableWordSelection);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    private void processGroup3Phase2( String mAppUserId,
                                      HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        mRawSQL="SELECT * FROM app_users_current_gpl " +
                "WHERE group_id=2 " +
                "AND phase_id=3 "+
                "AND app_users_current_gpl.app_user_id="+mAppUserId+" " +
                "UNION " +
                "SELECT * FROM app_users_current_gpl " +
                "WHERE group_id=3 " +
                "AND phase_id=2 "+
                "AND app_users_current_gpl.app_user_id="+mAppUserId;

        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        int[] mLevels=new int[]{0,0};
        if(mLevelCursor.moveToFirst()){
            for(int j=0;j<2;j++){
                mLevels[j] = mLevelCursor.getInt(
                        mLevelCursor.getColumnIndex("app_user_current_level"));
                if(!mLevelCursor.moveToNext()){
                    break;
                }
            }
        }
        mLevelCursor.close();

        if((mLevels[0]-1)>mLevels[1]) {
            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=app_user_current_level+1 " +
                    "WHERE group_id=3 " +
                    "AND phase_id=2 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level",
                    String.valueOf(Integer.parseInt(mUserGPLCurrent.get("app_user_current_level")) + 1));
            String[] mSyllableProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    mUserGPLCurrent.get("group_id"), //group_id 2
                    mUserGPLCurrent.get("phase_id"), //phase_id 3
                    "8", //section_id 4
                    mUserGPLCurrent.get("app_user_current_level")//level_number 5
            };

            String mSyllableSelection = "variable_phase_levels.group_id = " +
                    mUserGPLCurrent.get("group_id") + " " +
                    "AND variable_phase_levels.phase_id = " +
                    mUserGPLCurrent.get("phase_id") + " " +
                    "AND variable_phase_levels.level_number <= " +
                    mUserGPLCurrent.get("app_user_current_level") + " " +
                    "AND variable_type_relations.variable_type_id=5";
            mUpdateGroups.updateSyllableProcess(mSyllableProjection, mSyllableSelection);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup4( String mAppUserId,
                                HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        mRawSQL="SELECT MAX(ap3.app_user_current_level) AS phonic_max_level, " +
                "ap1.app_user_current_level as phonic_level, " +
                "ap2.app_user_current_level as word_level  " +
                "FROM app_users_current_gpl AS ap1  " +
                "INNER JOIN app_users_current_gpl AS ap2   " +
                "INNER JOIN app_users_current_gpl AS ap3 " +
                "WHERE  ap1.group_id=2 " +
                "AND ap1.phase_id=3 " +
                "AND ap2.group_id=4 " +
                "AND ap2.phase_id=1  " +
                "AND ap3.group_id=2 " +
                "AND ap3.phase_id=3 " +
                "LIMIT 1";
        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        int mPhonicLevel=0;
        int mWordLevel=0;
        int mMaxPhonicLevel=0;
        if(mLevelCursor.moveToFirst()){
            for(int j=0;j<2;j++){
                mPhonicLevel = mLevelCursor.getInt(
                        mLevelCursor.getColumnIndex("phonic_level"));
                mWordLevel= mLevelCursor.getInt(
                        mLevelCursor.getColumnIndex("word_level"));
                mMaxPhonicLevel= mLevelCursor.getInt(
                        mLevelCursor.getColumnIndex("phonic_max_level"));
                if(!mLevelCursor.moveToNext()){
                    break;
                }
            }
        }
        mLevelCursor.close();

        if(mPhonicLevel-2>=mWordLevel || mMaxPhonicLevel==mWordLevel+1) {

            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=app_user_current_level+1 " +
                    "WHERE group_id=4 " +
                    "AND phase_id=1 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level",
                    String.valueOf(Integer.parseInt(
                            mUserGPLCurrent.get("app_user_current_level")) + 1));

            String mWordSelection = "variable_phase_levels.group_id = " +
                    mUserGPLCurrent.get("group_id") + " " +
                    "AND variable_phase_levels.phase_id = " +
                    mUserGPLCurrent.get("phase_id") + " " +
                    "AND variable_phase_levels.level_number <= " +
                    mUserGPLCurrent.get("app_user_current_level") + " " +
                    "AND variable_phase_levels.level_number >0 " +
                    "AND variable_type_relations.variable_type_id=6";

            String[] mWordProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    mUserGPLCurrent.get("group_id"), //group_id 2
                    mUserGPLCurrent.get("phase_id"), //phase_id 3
                    "11", //section_id 4
                    mUserGPLCurrent.get("app_user_current_level")//level_number 5
            };

            mUpdateGroups.updateWordsProcess(mWordProjection, mWordSelection);
            mUpdateGroups.updateWordsProcessNEW(mWordProjection);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup5() {}

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup6( String mAppUserId,
                                HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        int mMaxLevel=0;
        mRawSQL="SELECT MAX(level_number) AS max_level_number " +
                "FROM variable_phase_levels " +
                "WHERE group_id=6 " +
                "AND phase_id=1";
        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        if(mLevelCursor.moveToFirst()){
            mMaxLevel=mLevelCursor.getInt(mLevelCursor.getColumnIndex("max_level_number"));
        }
        mLevelCursor.close();

        if(mMaxLevel>Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))) {


            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=app_user_current_level+1 " +
                    "WHERE group_id=6 " +
                    "AND phase_id=1 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level",
                    String.valueOf(Integer.parseInt(
                            mUserGPLCurrent.get("app_user_current_level")) + 1));

            String mLetterSelection = "variable_phase_levels.group_id = 6 " +
                    "AND variable_phase_levels.phase_id = 1 " +
                    // "AND variable_phase_levels.level_number " +
                    // "<= " + mUserGPLCurrent.get(4) + " " +
                    "AND variable_type_relations.variable_type_id=9 ";

            String[] mLetterProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    "6", //group_id 2
                    "1", //phase_id 3
                    "17", //section_id 4
                    mUserGPLCurrent.get("app_user_current_level")//level_number 5
            };

            mUpdateGroups.updateMathShapesProcess(mLetterProjection, mLetterSelection);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup7(String mAppUserId,
                               HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        int mMaxLevel=0;
        mRawSQL="SELECT MAX(level_number) AS max_level_number " +
                "FROM variable_phase_levels " +
                "WHERE group_id=7 " +
                "AND phase_id=1";
        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        if(mLevelCursor.moveToFirst()){
            mMaxLevel=mLevelCursor.getInt(mLevelCursor.getColumnIndex("max_level_number"));
        }
        mLevelCursor.close();

        if(mMaxLevel>Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))) {
            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=app_user_current_level+1 " +
                    "WHERE group_id=7 " +
                    "AND phase_id=1 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level",
                    String.valueOf(Integer.parseInt(
                            mUserGPLCurrent.get("app_user_current_level")) + 1));

            String mLetterSelection = "variable_phase_levels.group_id = 7 " +
                    "AND variable_phase_levels.phase_id = 1 " +
                    "AND variable_phase_levels.level_number " +
                    "<= " + mUserGPLCurrent.get("app_user_current_level") + " " +
                    "AND variable_type_relations.variable_type_id=10 ";

            String[] mLetterProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    "7", //group_id 2
                    "1", //phase_id 3
                    "20", //section_id 4
                    mUserGPLCurrent.get("app_user_current_level")//level_number 5
            };

            mUpdateGroups.updateMathNumbersProcess(mLetterProjection, mLetterSelection);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup8(String mAppUserId,
                               HashMap<String,String> mUserGPLCurrent) {

        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);
        String mRawSQL;
        int mMaxLevel=0;
        mRawSQL="SELECT MAX(math_equation_levels) AS max_level_number " +
                "FROM math_operations_levels ";
        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        if(mLevelCursor.moveToFirst()){
            mMaxLevel=mLevelCursor.getInt(mLevelCursor.getColumnIndex("max_level_number"));
        }
        mLevelCursor.close();

        if(mMaxLevel>Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))) {


            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=app_user_current_level+1 " +
                    "WHERE group_id=8 " +
                    "AND phase_id=1 " +
                    "AND app_users_current_gpl.app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPLCurrent.put("app_user_current_level",
                    String.valueOf(Integer.parseInt(
                            mUserGPLCurrent.get("app_user_current_level")) + 1));

            String[] mProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    "8", //group_id 2
                    "1", //phase_id 3
                    "23", //section_id 4
                    mUserGPLCurrent.get("app_user_current_level")//level_number 5
            };

            mUpdateGroups.updateMathOperationsProcess(mProjection);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processGroup2Phase1(String mAppUserId,
                                     HashMap<String,String> mUserGPLCurrent) {
        AppProviderUpdateGroups mUpdateGroups = new AppProviderUpdateGroups(mContext,
                mUserGPL,
                mDatabase);

        String mRawSQL;
        mRawSQL="UPDATE app_users_current_gpl " +
                "SET app_user_current_level=app_user_current_level+1 " +
                "WHERE group_id=2 " +
                "AND phase_id=1 "+
                "AND app_users_current_gpl.app_user_id="+mAppUserId;
        mDatabase.execSQL(mRawSQL);

        mUserGPLCurrent.put("app_user_current_level", String.valueOf(
                Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))+1));

        String mLetterSelection = "variable_phase_levels.group_id = "+
                mUserGPLCurrent.get("group_id")+" " +
                "AND variable_phase_levels.phase_id = "+
                mUserGPLCurrent.get("phase_id")+" " +
                "AND variable_phase_levels.level_number <= "+
                mUserGPLCurrent.get("app_user_current_level")+" "+
                "AND variable_type_relations.variable_type_id=1";

        String[] mLetterProjection = new String[]{
                "0", //0
                mAppUserId, //1
                mUserGPLCurrent.get("group_id"), //group_id 2
                mUserGPLCurrent.get("phase_id"), //phase_id 3
                "3", //section_id 4
                mUserGPLCurrent.get("app_user_current_level") //level_number 5
        };

        mUpdateGroups.updateLetterProcess(mLetterProjection, mLetterSelection);

        mRawSQL="SELECT MAX(level_number) AS max_level " +
                "FROM  variable_phase_levels " +
                "WHERE group_id=2 " +
                "AND phase_id=1 ";
        Cursor mLevelCursor = mDatabase.rawQuery(mRawSQL, null);
        int mMaxLevel=0;
        if(mLevelCursor.moveToFirst()){
            mMaxLevel=mLevelCursor.getInt(
                    mLevelCursor.getColumnIndex("max_level"));
        }
        mLevelCursor.close();

        if (Integer.parseInt(mUserGPLCurrent.get("app_user_current_level"))>mMaxLevel){
            mRawSQL="UPDATE app_users_current_gpl " +
                    "SET app_user_current_level=0 "+
                    "WHERE group_id=2 " +
                    "AND phase_id=2 "+
                    "AND app_users_current_gpl.app_user_id="+mAppUserId;
            mDatabase.execSQL(mRawSQL);


            mLetterSelection = "variable_phase_levels.group_id = 2 " +
                    "AND variable_phase_levels.phase_id = 2 " +
                    "AND variable_phase_levels.level_number <= 4 "+
                    "AND variable_type_relations.variable_type_id=2";

            mLetterProjection = new String[]{
                    "0",
                    mAppUserId,
                    "2", //group_id
                    "2", //phase_id
                    "3", //section_id 4
                    "4" //level_number 5
            };

            //updateLetterProcess(mLetterProjection, mLetterSelection);

            mUpdateGroups.updateLetterProcess(mLetterProjection, mLetterSelection);



            mAppData.setCurrentGroup_Section_Phase("2","2","3","0");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

}
