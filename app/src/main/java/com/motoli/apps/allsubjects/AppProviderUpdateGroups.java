package com.motoli.apps.allsubjects;

import android.content.ContentValues;
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
 * on 2/6/2017.
 */
public class AppProviderUpdateGroups {

    private  ArrayList<HashMap<String,String>> mUserGPL;
    private SQLiteDatabase mDatabase;

    public AppProviderUpdateGroups(Context mContext,
                                ArrayList<HashMap<String,String>> mUserGPL,
                                SQLiteDatabase mDatabase){
        this.mUserGPL = mUserGPL;
        this.mDatabase = mDatabase;
    }

    //////////////////////////////////////////////////////////////////////////////////////////



    protected String[] updateMathNumbersProcess(String[] mInfo, String whereSection) {
        Log.d(Constants.LOGCAT, "updateMathNumbersProcess");
        String mRawSQL;

        int blankCount=0;

        mRawSQL="SELECT * " +
                "FROM groups_phase_sections_activities " +
                "WHERE group_id="+mInfo[2]+" " +
                "AND phase_id="+mInfo[3]+" " +
                "AND section_id="+mInfo[4];

        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);
        while(mCursor.moveToNext()){
            String mActivityID=mCursor.getString(mCursor.getColumnIndex("activity_id"));

            mRawSQL="SELECT variable_type_relations.variable_type_id, " +
                    "app_users_activity_variable_values._id AS auavv_id_correct, " +
                    "app_users_activity_variable_values.*, "+
                    "math_numbers.* " +
                    "FROM math_numbers "+
                    "LEFT JOIN variable_type_relations " +
                    "ON variable_type_relations.variable_id" +
                    "=math_numbers.math_numbers_id "+
                    "INNER JOIN variable_phase_levels "+
                    "ON variable_phase_levels.variable_id" +
                    "=math_numbers.math_numbers_id "+
                    "LEFT JOIN app_users_activity_variable_values "+
                    "ON app_users_activity_variable_values.variable_id" +
                    "=math_numbers.math_numbers_id "+
                    "AND app_users_activity_variable_values.activity_id="+mActivityID+" "+
                    "AND app_users_activity_variable_values.app_user_id="+mInfo[1]+" "+
                    "WHERE "+whereSection;
            Cursor mCursor2 = mDatabase.rawQuery(mRawSQL, null);


            while(mCursor2.moveToNext()){
                String auavv_id=mCursor2.getString(mCursor2.getColumnIndex("auavv_id_correct"));
                if(auavv_id == null){

                    blankCount++;
                    ContentValues values = new ContentValues();
                    values.put("variable_id",
                            mCursor2.getString(mCursor2.getColumnIndex("math_numbers_id")));
                    values.put("type_id","10");
                    values.put("group_id", mInfo[2]);
                    values.put("phase_id", mInfo[3]);
                    values.put("activity_id", mActivityID);
                    values.put("app_user_id", mInfo[1]);

                    values.put("number_incorrect", "0");
                    values.put("level_number", mInfo[5]);

                    if(mActivityID.equals("48")){
                        values.put("number_correct", "2");
                        values.put("number_correct_in_a_row", "2");
                    }else {
                        values.put("number_correct", "0");
                        values.put("number_correct_in_a_row", "0");
                    }

                    long current_id =mDatabase.insert("app_users_activity_variable_values", null, values);

                    mRawSQL="UPDATE app_users_activity_variable_values " +
                            "SET auavv_id="+String.valueOf(current_id)+" " +
                            "WHERE _id="+String.valueOf(current_id);
                    mDatabase.execSQL(mRawSQL);

                }else if(mCursor2.getInt(mCursor2.getColumnIndex("number_correct"))
                        >=Constants.NUMBER_CORRECT){
                    blankCount++;
                }//end if(auavv_id.equals("")){

            }//end  while(mCursor.moveToNext()){
            if(mInfo[5].equals("3")){
                mRawSQL="UPDATE app_users_activity_variable_values " +
                        "SET number_correct_in_a_row=0, " +
                        "number_correct=0 " +
                        "WHERE variable_id =( " +
                        "SELECT app_users_activity_variable_values.variable_id " +
                        "FROM app_users_activity_variable_values AS av " +
                        "LEFT JOIN math_numbers  " +
                        "ON math_numbers.math_number=0 " +
                        "WHERE app_users_activity_variable_values.variable_id" +
                        "=math_numbers.math_numbers_id " +
                        "AND app_users_activity_variable_values.group_id=7 ) " +
                        "AND group_id=7 " +
                        "AND activity_id!=49 " +
                        "AND activity_id!=50 " +
                        "AND activity_id!=51 " +
                        "AND activity_id!=52 " +
                        "AND activity_id!=53";
                mDatabase.execSQL(mRawSQL);
            }
            mCursor2.close();
        }
        int curCount=mCursor.getCount();

        String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
        mCursor.close();

        return returnString;

    }

    ////////////////////////////////////////////////////////////////////////////////////

    protected String[] updateLetterProcess(String[] projection,
                                           String whereSection) {
        Log.d(Constants.LOGCAT, "updateLetterProcess");

        String mRawSQL;
        Cursor mCursor2;


        mRawSQL="SELECT app_user_current_level " +
                "FROM app_users_current_gpl " +
                "WHERE app_user_id="+projection[1]+" "+
                "AND group_id="+projection[2]+" AND phase_id="+projection[3];
        Cursor mUserCursor = mDatabase.rawQuery(mRawSQL, null);
        String mAppUserCurrentLevel="0";
        if(mUserCursor.moveToFirst()){
            mAppUserCurrentLevel=mUserCursor.getString(
                    mUserCursor.getColumnIndex("app_user_current_level"));
        }
        mUserCursor.close();

        int blankCount=0;

        String mAUAVVTable="app_users_activity_variable_values";

        mRawSQL="SELECT * " +
                "FROM groups_phase_sections_activities "
                +" WHERE group_id="+projection[2]+" "
                +" AND phase_id="+projection[3]+" "
                +" AND section_id="+projection[4];

        if(projection[2].equals("3") && projection[3].equals("1")){
            mRawSQL+=" AND activity_id>=29 AND activity_id<=31";
        }

        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);

        mRawSQL="SELECT COUNT(variable_id) AS number_variables " +
                "FROM variable_phase_levels " +
                "WHERE group_id="+projection[2]+" "+
                "AND phase_id="+projection[3]+" " +
                "AND level_number<="+mAppUserCurrentLevel;
        Cursor mVariablesCursor = mDatabase.rawQuery(mRawSQL, null);
        int mNumberOfVariables=0;
        if(mVariablesCursor.moveToFirst()){
            mNumberOfVariables=mVariablesCursor.getInt(
                    mVariablesCursor.getColumnIndex("number_variables"));
        }
        mVariablesCursor.close();

        while(mCursor.moveToNext()){
            String mActivityID=mCursor.getString(mCursor.getColumnIndex(
                    "activity_id"));


            mRawSQL="SELECT variable_type_relations.variable_type_id, " +
                    "app_users_activity_variable_values._id AS auavv_id_correct, " +
                    "app_users_activity_variable_values.*, letters.* " +
                    "FROM letters LEFT JOIN variable_type_relations " +
                    "ON variable_type_relations.variable_id=letters.letter_id  " +
                    "INNER JOIN variable_phase_levels " +
                    "ON variable_phase_levels.variable_id=letters.letter_id  " +
                    "LEFT JOIN app_users_activity_variable_values " +
                    "ON app_users_activity_variable_values.variable_id=letters.letter_id " +
                    "AND app_users_activity_variable_values.activity_id="+mActivityID+" "+
                    "AND app_users_activity_variable_values.app_user_id="+projection[1]+" "+
                    "WHERE "+whereSection;

            mCursor2 = mDatabase.rawQuery(mRawSQL, null);

            while(mCursor2.moveToNext()){
                String auavv_id=mCursor2.getString(mCursor2.getColumnIndex("auavv_id_correct"));
                if(auavv_id == null){

                    blankCount++;
                    ContentValues values = new ContentValues();
                    values.put("variable_id",
                            mCursor2.getString(mCursor2.getColumnIndex("letter_id")));
                    values.put("type_id",
                            (projection[3].equals("2")) ? "2" :"1");
                    values.put("activity_id", mActivityID);
                    values.put("group_id", projection[2]);
                    values.put("phase_id", projection[3]);
                    values.put("app_user_id", projection[1]);
                    values.put("level_number", projection[5]);

                    if(mActivityID.equals("20")) {
                        values.put("number_correct_in_a_row",
                                String.valueOf(Constants.INA_ROW_CORRECT));
                        values.put("number_correct",
                                String.valueOf(Constants.INA_ROW_CORRECT));
                    }else if(mActivityID.equals("8") && mNumberOfVariables<6){
                        values.put("number_correct_in_a_row",
                                String.valueOf(Constants.INA_ROW_CORRECT));
                        values.put("number_correct",
                                String.valueOf(Constants.INA_ROW_CORRECT));

                    }else  if((Integer.parseInt(mAppUserCurrentLevel)<27
                            && projection[3].equals("1"))

                            || (Integer.parseInt(mAppUserCurrentLevel)<15
                            && projection[3].equals("2"))
                            ){
                        values.put("number_correct_in_a_row", "0");
                        values.put("number_correct", "0");

                    }else{

                        if(projection[2].equals("2") && projection[3].equals("1")) {
                            values.put("number_correct_in_a_row", "0");
                            values.put("number_correct", "0");
                        }else{

                            values.put("number_correct_in_a_row", "0");
                            values.put("number_correct", "0");
                        }
                    }

                    values.put("number_incorrect", "0");


                    long current_id =mDatabase.insert(mAUAVVTable, null, values);

                    if(mActivityID.equals("8") && mAppUserCurrentLevel.equals("3")){
                        mRawSQL="SELECT MAX(number_correct) AS max_correct " +
                                "FROM app_users_activity_variable_values " +
                                "WHERE activity_id=8 " +
                                "AND app_user_id="+projection[1];
                        Cursor cursorThird=mDatabase.rawQuery(mRawSQL, null);
                        int mMaxCorrect=0;
                        if(cursorThird.moveToFirst()){
                            mMaxCorrect=cursorThird.getInt(cursorThird.getColumnIndex("max_correct"));
                        }
                        cursorThird.close();


                        /*
                         * THIS NEEDS TO BE CHANGED BACK
                         */
                        if(mMaxCorrect==7){
                            mRawSQL="UPDATE app_users_activity_variable_values " +
                                    "SET number_correct_in_a_row=2," +
                                    "number_correct=2 " +
                                    "WHERE activity_id=8 " +
                                    "AND app_user_id="+projection[1];
                            mDatabase.execSQL(mRawSQL);
                        }
                    }

                    mRawSQL="UPDATE "+mAUAVVTable+" " +
                            "SET auavv_id="+String.valueOf(current_id)
                            +" WHERE _id="+String.valueOf(current_id);

                    mDatabase.execSQL(mRawSQL);

                }else if(mCursor2.getInt(mCursor2.getColumnIndex("number_correct"))>=Constants.NUMBER_CORRECT){
                    blankCount++;
                }//end if(auavv_id.equals("")){

            }//end  while(mCursor.moveToNext()){
            mCursor2.close();
        }
        int curCount=mCursor.getCount();

        String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
        mCursor.close();

        return returnString;

    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    protected String[] updateMathShapesProcess(String[] mInfo, String whereSection) {
        Log.d(Constants.LOGCAT, "updateMathShapesProcess");
        String mRawSQL;
        Cursor mCursor2;


        int blankCount=0;

        mRawSQL="SELECT * " +
                "FROM groups_phase_sections_activities " +
                "WHERE group_id="+mInfo[2]+" " +
                "AND phase_id="+mInfo[3]+" " +
                "AND section_id="+mInfo[4];

        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);
        while(mCursor.moveToNext()){
            String mActivityID=mCursor.getString(mCursor.getColumnIndex("activity_id"));

            mRawSQL="SELECT variable_type_relations.variable_type_id, " +
                    "app_users_activity_variable_values._id AS auavv_id_correct, " +
                    "app_users_activity_variable_values.*, "+
                    "math_shapes.* " +
                    "FROM math_shapes "+
                    "LEFT JOIN variable_type_relations " +
                    "ON variable_type_relations.variable_id" +
                    "=math_shapes.math_shape_id "+
                    "INNER JOIN variable_phase_levels "+
                    "ON variable_phase_levels.variable_id" +
                    "=math_shapes.math_shape_id "+
                    "LEFT JOIN app_users_activity_variable_values "+
                    "ON app_users_activity_variable_values.variable_id" +
                    "=math_shapes.math_shape_id "+
                    "AND app_users_activity_variable_values.activity_id="+mActivityID+" "+
                    "AND app_users_activity_variable_values.app_user_id="+mInfo[1]+" "+
                    "WHERE "+whereSection + " ";
            if(Integer.parseInt(mInfo[5])>6) {
                mRawSQL+="AND math_shapes.math_shape_kind==2 " +
                        "AND variable_phase_levels.level_number==" + mInfo[5]+" ";
            }else{
                mRawSQL+="AND math_shapes.math_shape_kind==0 " +
                        "AND variable_phase_levels.level_number<="+mInfo[5]+" ";
            }
            mCursor2 = mDatabase.rawQuery(mRawSQL, null);


            while(mCursor2.moveToNext()){
                String auavv_id=mCursor2.getString(mCursor2.getColumnIndex("auavv_id_correct"));
                if(auavv_id == null
                        && ((!mActivityID.equals("59") && Integer.parseInt(mInfo[5])<7))
                        || (mActivityID.equals("59") && Integer.parseInt(mInfo[5])>6)){

                    blankCount++;
                    ContentValues values = new ContentValues();
                    values.put("variable_id",
                            mCursor2.getString(mCursor2.getColumnIndex("math_shape_id")));
                    values.put("type_id","9");
                    values.put("group_id", mInfo[2]);
                    values.put("phase_id", mInfo[3]);
                    values.put("activity_id", mActivityID);
                    values.put("app_user_id", mInfo[1]);
                    values.put("level_number", mInfo[5]);
                    values.put("number_incorrect", "0");

                    if ((mActivityID.equals("56") || mActivityID.equals("57")
                            || mActivityID.equals("58") ) &&
                            Integer.parseInt(mInfo[5])<=1) {
                        /*
                         * activity_sh03 and activity_sh05 cannot be used
                         * until at the very least level 2
                         */

                        values.put("number_correct", "2");
                        values.put("number_correct_in_a_row", "2");

                    }else if(mActivityID.equals("54")) {
                        /*
                         * activity_sh01 will not require anything to move to next level
                         * as it is the piano activity
                         */
                        values.put("number_correct", "2");
                        values.put("number_correct_in_a_row", "2");
                    }else {
                        values.put("number_correct", "0");
                        values.put("number_correct_in_a_row", "0");
                    }

                    long current_id =mDatabase.insert("app_users_activity_variable_values",
                            null, values);

                    mRawSQL="UPDATE app_users_activity_variable_values " +
                            "SET auavv_id="+String.valueOf(current_id)+" " +
                            "WHERE _id="+String.valueOf(current_id);
                    mDatabase.execSQL(mRawSQL);

                }else if(mCursor2.getInt(mCursor2.getColumnIndex("number_correct"))
                        >=Constants.NUMBER_CORRECT){
                    blankCount++;
                }//end if(auavv_id.equals("")){

            }//end  while(mCursor.moveToNext()){
            mCursor2.close();
        }
        int curCount=mCursor.getCount();

        String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
        mCursor.close();

        return returnString;

    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    protected String[] updateMathOperationsProcess(String[] mInfo) {
        Log.d(Constants.LOGCAT,"updateMathOperationsProcess BEGIN");
        int blankCount=0;



        String mRawSQL="SELECT * " +
                "FROM groups_phase_sections_activities " +
                "WHERE group_id=8 " +
                "AND phase_id=1 " +
                "AND section_id="+mInfo[4];

        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);
        while(mCursor.moveToNext()) {
            String mActivityID=mCursor.getString(mCursor.getColumnIndex("activity_id"));

            mRawSQL="SELECT app_users_activity_variable_values.auavv_id, " +
                    "math_operations_levels.* " +
                    "FROM math_operations_levels " +
                    "LEFT JOIN app_users_activity_variable_values " +
                    "ON app_users_activity_variable_values.level_number " +
                    "= math_operations_levels.math_equation_levels " +
                    "AND app_users_activity_variable_values.activity_id=" + mActivityID + " " +
                    "WHERE  math_operations_levels.math_equation_levels= " + mInfo[5];

            Cursor mCursor2 = mDatabase.rawQuery(mRawSQL, null);
            if(mCursor2.moveToFirst()) {
                String auavv_id = mCursor2.getString(mCursor2.getColumnIndex("auavv_id"));
                if (auavv_id == null
                        && (mActivityID.equals("61")
                        || ((mActivityID.equals("60") || mActivityID.equals("62"))
                        && mCursor2.getInt(mCursor2.getColumnIndex("max_addition_number_one"))>0)
                        || ((mActivityID.equals("63") || mActivityID.equals("64"))
                        &&  mCursor2.getInt(
                        mCursor2.getColumnIndex("max_substitution_number_one"))>0)
                        || (mActivityID.equals("65") &&  mCursor2.getInt(
                        mCursor2.getColumnIndex("max_multiplication_number_one"))>0))){
                    ContentValues values = new ContentValues();

                    values.put("variable_id", mInfo[5]);
                    values.put("type_id", "11");
                    values.put("group_id", "8");
                    values.put("phase_id", "1");
                    values.put("activity_id", mActivityID);
                    values.put("app_user_id", mInfo[1]);

                    values.put("number_incorrect", "0");
                    values.put("level_number", mInfo[5]);
                    if(mInfo[5].equals("11") && ( mActivityID.equals("60")
                            || mActivityID.equals("62") || mActivityID.equals("63")
                            || mActivityID.equals("64"))){
                        values.put("number_correct", "20");
                        values.put("number_correct_in_a_row", "20");
                    }else if (mActivityID.equals("61") ) {
                        values.put("number_correct", "20");
                        values.put("number_correct_in_a_row", "20");
                    } else {
                        values.put("number_correct", "0");
                        values.put("number_correct_in_a_row", "0");
                    }
                    long current_id = mDatabase.insert("app_users_activity_variable_values",
                            null, values);
                    mRawSQL = "UPDATE app_users_activity_variable_values " +
                            "SET auavv_id=" + String.valueOf(current_id) + " " +
                            "WHERE _id=" + String.valueOf(current_id);
                    mDatabase.execSQL(mRawSQL);
                }
            }
            mCursor2.close();
        }

        String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(2) };
        mCursor.close();
        Log.d(Constants.LOGCAT,"updateMathOperationsProcess END");
        return returnString;

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    protected void updateWordsProcessNEW(String[] projection) {
        Log.d(Constants.LOGCAT, "updateWordsProcess");
        String mRawSQL;
        String mAppUserCurrentLevel="0";
        String mGroupID=projection[2];
        String mPhaseID=projection[3];
        String mUserID=projection[1];
        Cursor mCursor;


        /////////////////////////////////////////////////////////////////////

        mRawSQL="SELECT app_user_current_level " +
                "FROM app_users_current_gpl " +
                "WHERE app_user_id="+mUserID+" "+
                "AND group_id="+mGroupID+" " +
                "AND phase_id="+mPhaseID;
        Cursor mUserCursor = mDatabase.rawQuery(mRawSQL, null);
        if(mUserCursor.moveToFirst()){
            mAppUserCurrentLevel=mUserCursor.getString(
                    mUserCursor.getColumnIndex("app_user_current_level"));
        }
        mUserCursor.close();

        ///////////////////////////////////////////////////////////////////

        mRawSQL="SELECT app_users_variables_values.auvv_id, " +
                "activities.*, " +
                "groups_phase_levels.* " +
                "FROM groups_phase_levels " +
                "LEFT JOIN activities " +
                "ON activities.activity_type='WD' ";
        mRawSQL+="LEFT JOIN app_users_variables_values " +
                "ON app_users_variables_values.level_number" +
                "=groups_phase_levels.level_number " +
                "AND app_users_variables_values.activity_id" +
                "=activities.activity_id " +
                "WHERE groups_phase_levels.level_number=" + mAppUserCurrentLevel + " " +
                "AND groups_phase_levels.group_id=" + mGroupID + " " +
                "AND groups_phase_levels.phase_id=" + mPhaseID + " ";
        mRawSQL+="GROUP BY activities.activity_id " +
                "ORDER BY activities.activity_code ASC";
        mCursor = mDatabase.rawQuery(mRawSQL, null);
        if(mCursor.moveToFirst()) {
            do {
                String mID=mCursor.getString(mCursor.getColumnIndex("auvv_id"));
                String mActivityID=mCursor.getString(mCursor.getColumnIndex("activity_id"));
                if (mID == null) {
                    ContentValues values = new ContentValues();
                    values.put("variable_id",mAppUserCurrentLevel);
                    values.put("type_id","6");
                    values.put("group_id", mGroupID);
                    values.put("phase_id", mPhaseID);
                    values.put("activity_id", mActivityID);
                    values.put("app_user_id", mUserID);
                    values.put("level_number", mAppUserCurrentLevel);
                    values.put("number_correct_in_a_row", "0");
                    values.put("number_correct", "0");


                    values.put("number_incorrect", "0");

                    long current_id =mDatabase.insert("app_users_variables_values", null, values);

                    mRawSQL="UPDATE app_users_variables_values " +
                            "SET auvv_id="+String.valueOf(current_id) +" " +
                            "WHERE _id="+String.valueOf(current_id);
                    mDatabase.execSQL(mRawSQL);
                }//end if(auavv_id.equals("")){

            }while(mCursor.moveToNext());
        }
        mCursor.close();
    }
////////////////////////////////////////////////////////////////////////////////////////

    protected String[] updateWordsProcess(String[] projection, String whereSection) {
        Log.d(Constants.LOGCAT, "updateWordsProcess VV");
        String mRawSQL;

        int blankCount=0;

        String mAUAVVTable="app_users_activity_variable_values";

        mRawSQL="SELECT * FROM groups_phase_sections_activities "
                +" WHERE group_id="+projection[2]+" "
                +" AND phase_id="+projection[3]+" "
                +" AND section_id="+projection[4];


        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);
        while(mCursor.moveToNext()){
            String mActivityID=mCursor.getString(mCursor.getColumnIndex(
                    "activity_id"));


            mRawSQL="SELECT variable_type_relations.variable_type_id, " +
                    "app_users_activity_variable_values._id AS auavv_id_correct, " +
                    "app_users_activity_variable_values.*, "+
                    "words.* " +
                    "FROM words "+
                    "LEFT JOIN variable_type_relations " +
                    "ON variable_type_relations.variable_id=words.word_id "+
                    "INNER JOIN variable_phase_levels "+
                    "ON variable_phase_levels.variable_id=words.word_id "+
                    "LEFT JOIN app_users_activity_variable_values "+
                    "ON app_users_activity_variable_values.variable_id=words.word_id "+
                    "AND app_users_activity_variable_values.activity_id="+mActivityID+" "+
                    "AND app_users_activity_variable_values.app_user_id="+projection[1]+" "+
                    "WHERE "+whereSection;
            Cursor mCursor2 = mDatabase.rawQuery(mRawSQL, null);


            while(mCursor2.moveToNext()){
                String auavv_id=mCursor2.getString(mCursor2.getColumnIndex("auavv_id_correct"));
                if(auavv_id == null){

                    blankCount++;
                    ContentValues values = new ContentValues();
                    values.put("variable_id",
                            mCursor2.getString(mCursor2.getColumnIndex("word_id")));
                    values.put("type_id",
                            mCursor2.getString(mCursor2.getColumnIndex("variable_type_id")));
                    values.put("group_id", projection[2]);
                    values.put("phase_id", projection[3]);
                    values.put("activity_id", mActivityID);
                    values.put("app_user_id", projection[1]);
                    values.put("level_number", projection[5]);
                    if(mActivityID.equals("46") &&
                            mCursor2.getString(mCursor2.getColumnIndex("word_separation"))
                                    .equals("0")) {
                        values.put("number_correct_in_a_row",Constants.INA_ROW_CORRECT);
                        values.put("number_correct",Constants.INA_ROW_CORRECT);
                    }else if(mActivityID.equals("45")  &&
                            mCursor2.getString(mCursor2.getColumnIndex("word_first_letter"))
                                    .equals("0")){
                        values.put("number_correct_in_a_row", Constants.INA_ROW_CORRECT);
                        values.put("number_correct", Constants.INA_ROW_CORRECT);
                    }else if((mActivityID.equals("42") ) &&
                            mCursor2.getString(mCursor2.getColumnIndex("image_url")).equals("")){
                        values.put("number_correct_in_a_row", Constants.INA_ROW_CORRECT);
                        values.put("number_correct", Constants.INA_ROW_CORRECT);
                    }else{
                        values.put("number_correct_in_a_row", "0");
                        values.put("number_correct", "0");
                    }
                    values.put("number_incorrect", "0");
                    long current_id =mDatabase.insert(mAUAVVTable, null, values);

                    mRawSQL="UPDATE "+mAUAVVTable+" " +
                            "SET auavv_id="+String.valueOf(current_id)
                            +" WHERE _id="+String.valueOf(current_id);

                    mDatabase.execSQL(mRawSQL);

                }else if(mCursor2.getInt(mCursor2.getColumnIndex("number_correct"))>=Constants.NUMBER_CORRECT){
                    blankCount++;
                }//end if(auavv_id.equals("")){
            }//end  while(mCursor.moveToNext()){
            mCursor2.close();
        }
        int curCount=mCursor.getCount();
        String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
        mCursor.close();
        return returnString;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    protected String[] updateSyllableProcess(String[] projection, String whereSection) {
        Log.d(Constants.LOGCAT,"updateSyllableProcess");
        projection[3]="1";
        projection[4]="8";
        int blankCount=0;
        String mRawSQL="SELECT * FROM groups_phase_sections_activities " +
                "WHERE group_id=3 " +
                "AND phase_id= 2 " +
                "AND section_id=8 " +
                "AND activity_id>=32 AND activity_id<=34";

        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);
        while(mCursor.moveToNext()){
            String mActivityID=mCursor.getString(mCursor.getColumnIndex("activity_id"));

            mRawSQL="SELECT  COUNT(sw1.syllable_word_first_id) AS first_count, " +
                    "COUNT(sw2.syllable_word_last_id) AS last_count, "+
                    "app_users_activity_variable_values._id AS auavv_id_correct, " +
                    "app_users_activity_variable_values.*, " +
                    "variable_type_relations.variable_type_id, " +
                    "syllables.* " +
                    "FROM syllables LEFT JOIN variable_type_relations " +
                    "ON variable_type_relations.variable_id=syllables.syllables_id  " +
                    "INNER JOIN variable_phase_levels " +
                    "ON variable_phase_levels.variable_id=syllables.syllables_id " +
                    "LEFT JOIN app_users_activity_variable_values " +
                    "ON app_users_activity_variable_values.variable_id=syllables.syllables_id " +
                    "AND app_users_activity_variable_values.activity_id="+mActivityID+" "+
                    "AND app_users_activity_variable_values.app_user_id="+projection[1]+" "+
                    " LEFT JOIN syllable_words AS sw1 " +
                    "ON sw1.syllable_word_first_id=syllables.syllables_id   " +
                    "LEFT JOIN syllable_words AS sw2 " +
                    "ON   sw2.syllable_word_last_id=syllables.syllables_id " +
                    "WHERE "+whereSection + " " +
                    "GROUP BY syllables.syllables_id";
            Cursor mCursor2 = mDatabase.rawQuery(mRawSQL, null);

            for(mCursor2.moveToFirst(); !mCursor2.isAfterLast(); mCursor2.moveToNext()){
                String auavv_id = mCursor2.getString(mCursor2.getColumnIndex("auavv_id_correct"));
                if (auavv_id == null) {

                    blankCount++;
                    ContentValues values = new ContentValues();
                    values.put("variable_id",
                            mCursor2.getString(mCursor2.getColumnIndex("syllables_id")));
                    values.put("type_id",
                            mCursor2.getString(mCursor2.getColumnIndex("variable_type_id")));
                    values.put("group_id", "3");
                    values.put("phase_id", "2");
                    values.put("activity_id", mActivityID);
                    values.put("app_user_id", projection[1]);
                    values.put("level_number", projection[5]);
                    if((mActivityID.equals("33") &&
                            mCursor2.getString(mCursor2.getColumnIndex("first_count"))
                                    .equals("0")) || (mActivityID.equals("34") &&
                            (mCursor2.getString(mCursor2.getColumnIndex("last_count"))
                                    .equals("0") &&
                                    mCursor2.getString(mCursor2.getColumnIndex("last_count"))
                                            .equals("0")))){
                        values.put("number_correct_in_a_row",
                                String.valueOf(Constants.INA_ROW_CORRECT));
                        values.put("number_correct",
                                String.valueOf(Constants.INA_ROW_CORRECT));
                    }else{
                        values.put("number_correct_in_a_row", "0");
                        values.put("number_correct", "0");
                    }
                    values.put("number_incorrect", "0");

                    long current_id = mDatabase.insert("app_users_activity_variable_values",
                            null, values);

                    mRawSQL = "UPDATE app_users_activity_variable_values " +
                            "SET auavv_id=" + String.valueOf(current_id)+ " " +
                            "WHERE _id=" + String.valueOf(current_id);

                    mDatabase.execSQL(mRawSQL);
                } else if (mCursor2.getInt(mCursor2.getColumnIndex("number_correct")) >= Constants.NUMBER_CORRECT) {
                    blankCount++;
                }//end if(auavv_id.equals("")){

            }//end for(mCursor2.moveToFirst(); !mCursor2.isAfterLast(); mCursor2.moveToNext()){
            mCursor2.close();
        }
        int curCount=mCursor.getCount();

        String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
        mCursor.close();

        return returnString;

    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    protected void wordsPhaseLevelsUpdate(String mAppUserId){
        Log.d(Constants.LOGCAT,"wordsPhaseLevelsUpdate");
        String mRawSQL;
        Cursor mCursor;

        int mFirstWordCount=0;
        int mSecondWordCount=0;

        /*
         * if word count for level bellow current phonics level is 4
         * or higher and word count for second level bellow current
         * phonic level is not 4 or higher then
         */

        mRawSQL = "SELECT COUNT(words._id) AS word_count " +
                "FROM words " +
                "INNER JOIN variable_phase_levels " +
                "ON variable_phase_levels.variable_id=words.word_id " +
                "WHERE  variable_phase_levels.group_id=4 " +
                "AND variable_phase_levels.phase_id=1 " +
                "AND variable_phase_levels.level_number=" +
                String.valueOf(Integer.parseInt(mUserGPL.get(5).get("app_user_current_level")) + 1) +
                " UNION " +
                "SELECT COUNT(words._id) AS word_count " +
                "FROM words " +
                "INNER JOIN variable_phase_levels " +
                "ON variable_phase_levels.variable_id=words.word_id " +
                "WHERE  variable_phase_levels.group_id=4 " +
                "AND variable_phase_levels.phase_id=1 " +
                "AND variable_phase_levels.level_number=" +
                mUserGPL.get(5).get("app_user_current_level");
        mCursor = mDatabase.rawQuery(mRawSQL, null);
        if (mCursor.moveToFirst()) {
            mFirstWordCount = mCursor.getInt(mCursor.getColumnIndex("word_count"));
            if (mCursor.moveToNext()) {
                mSecondWordCount = mCursor.getInt(mCursor.getColumnIndex("word_count"));
            }
        }



        if(Integer.parseInt(mUserGPL.get(5).get("app_user_current_level"))+1
                <Integer.parseInt(mUserGPL.get(2).get("app_user_current_level")) &&
                mFirstWordCount<4 &&
                (mSecondWordCount>=4 || mSecondWordCount<4)) {
            mRawSQL = "UPDATE app_users_current_gpl " +
                    "SET app_user_current_level" +
                    "=app_user_current_level+1 " +
                    "WHERE group_id=4 " +
                    "AND phase_id=1 " +
                    "AND app_user_id=" + mAppUserId;
            mDatabase.execSQL(mRawSQL);

            mUserGPL.get(5).put("app_user_current_level",
                    String.valueOf(Integer.parseInt(mUserGPL.get(5).get("app_user_current_level")) + 1));

            String mWordSelection = "variable_phase_levels.group_id = 4 " +
                    "AND variable_phase_levels.phase_id = 1 " +
                    "AND variable_phase_levels.level_number <= " +
                    mUserGPL.get(5).get("app_user_current_level") + " " +
                    "AND variable_phase_levels.level_number > 0 " +
                    "AND variable_type_relations.variable_type_id=6";

            String[] mWordProjection = new String[]{
                    "0", //0
                    mAppUserId, //1
                    "4", //group_id 2
                    "1", //phase_id 3
                    "11", //section_id 4
                    mUserGPL.get(5).get("app_user_current_level")//level_number 5
            };

            updateWordsProcess(mWordProjection, mWordSelection);
            updateWordsProcessNEW(mWordProjection);

        }
        mCursor.close();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    protected   String[] updateSyllableWordsProcess(String[] projection, String whereSection) {
        Log.d(Constants.LOGCAT, "updateSyllableWordsProcess");

        String mRawSQL;

        int blankCount=0;

        String mAUAVVTable="app_users_activity_variable_values";

        mRawSQL="SELECT * FROM groups_phase_sections_activities " +
                "WHERE group_id=3 " +
                "AND phase_id=1 " +
                "AND section_id=8 " +
                "AND activity_id>=29 " +
                "AND activity_id<=31";//+projection[4];

        /*
        if(projection[2].equals("3") && projection[3].equals("1")){
            mRawSQL+=" AND activity_id>=29 AND activity_id<=31";
        }
        */
        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);
        while(mCursor.moveToNext()){
            String mActivityID=mCursor.getString(mCursor.getColumnIndex("activity_id"));

            mRawSQL="SELECT  variable_type_relations.variable_type_id, " +
                    "app_users_activity_variable_values._id AS auavv_id_correct, " +
                    "app_users_activity_variable_values.*, " +
                    "words.* " +
                    "FROM words " +
                    "LEFT JOIN variable_type_relations " +
                    "ON variable_type_relations.variable_id=words.word_id  " +
                    "INNER JOIN variable_phase_levels " +
                    "ON variable_phase_levels.variable_id=words.word_id  " +
                    "LEFT JOIN app_users_activity_variable_values " +
                    "ON app_users_activity_variable_values.variable_id=words.word_id " +
                    "AND app_users_activity_variable_values.activity_id=" +mActivityID+" "+
                    "AND app_users_activity_variable_values.app_user_id="+projection[1]+" "+
                    "WHERE "+whereSection+" ";
            Cursor mCursor2 = mDatabase.rawQuery(mRawSQL, null);

            if(mCursor2.moveToFirst()) {
                do {
                    String auavv_id = mCursor2.getString(mCursor2.getColumnIndex("auavv_id_correct"));
                    if (auavv_id == null) {

                        blankCount++;
                        ContentValues values = new ContentValues();
                        values.put("variable_id",
                                mCursor2.getString(mCursor2.getColumnIndex("word_id")));
                        values.put("type_id",
                                mCursor2.getString(mCursor2.getColumnIndex("variable_type_id")));
                        values.put("group_id", "3");
                        values.put("phase_id", "1");
                        values.put("activity_id", mActivityID);
                        values.put("app_user_id", projection[1]);
                        values.put("level_number", projection[5]);


                        if (mCursor2.getInt(
                                mCursor2.getColumnIndex("number_of_syllables")) <= 0 ||
                                mCursor2.getInt(
                                        mCursor2.getColumnIndex("number_of_syllables")) > 4) {
                            values.put("number_correct_in_a_row",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                            values.put("number_correct",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                        } else if (mActivityID.equals("29") &&
                                Integer.parseInt(mCursor2.getString(
                                        mCursor2.getColumnIndex("number_of_syllables"))) >= 4) {
                            values.put("number_correct_in_a_row",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                            values.put("number_correct",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                        } else if (mActivityID.equals("6") || mActivityID.equals("20")) {
                            values.put("number_correct_in_a_row",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                            values.put("number_correct",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                        } else {
                          /**/
                            if (projection[2].equals("2") && projection[3].equals("1")) {

                                values.put("number_correct_in_a_row", "2");
                                values.put("number_correct", "2");
                            } else {

                                values.put("number_correct_in_a_row", "0");
                                values.put("number_correct", "0");
                            }
                        }

                        values.put("number_incorrect", "0");

                        long current_id = mDatabase.insert(mAUAVVTable, null, values);

                        mRawSQL = "UPDATE " + mAUAVVTable + " " +
                                "SET auavv_id=" + String.valueOf(current_id)
                                + " WHERE _id=" + String.valueOf(current_id);

                        mDatabase.execSQL(mRawSQL);

                    } else if (mCursor2.getInt(mCursor2.getColumnIndex("number_correct"))
                            >= Constants.NUMBER_CORRECT) {
                        blankCount++;
                    }//end if(auavv_id.equals("")){

                } while (mCursor2.moveToNext());
            }
            mCursor2.close();
        }
        int curCount=mCursor.getCount();

        String[] returnString= new String[]{String.valueOf(blankCount), String.valueOf(curCount) };
        mCursor.close();

        return returnString;

    }
    protected void updatePhonicsProcess(String[] projection) {
        Log.d(Constants.LOGCAT, "updatePhonicsProcess");
        String mAppUserCurrentLevel="0";
        String mGroupID=projection[2];
        String mPhaseID=projection[3];
        String mUserID=projection[1];
        Cursor mCursor;
        String mPerfectValue=String.valueOf(
                Constants.NUMBER_VARIABLES+(Constants.NUMBER_VARIABLES/2));

        ///////////////////////////////////////////////////////////////////////////////

        String mRawSQL="SELECT * " +
                "FROM app_users_current_gpl " +
                "WHERE group_id=2 AND phase_id=3 " +
                "AND app_user_id="+mUserID+" LIMIT 1";
        Cursor userCursor = mDatabase.rawQuery(mRawSQL, null);
        if(userCursor.moveToFirst()) {
            do {
                mAppUserCurrentLevel=userCursor.getString(
                        userCursor.getColumnIndex("app_user_current_level"));
            } while (userCursor.moveToNext());
        }
        userCursor.close();

        ///////////////////////////////////////////////////////////////////////////////

        mRawSQL="SELECT app_users_activity_variable_values.auavv_id, " +
                "activities.*, " +
                "groups_phase_levels.* " +
                "FROM groups_phase_levels " +
                "LEFT JOIN activities " +
                "ON activities.activity_type='SD' ";
        mRawSQL+="LEFT JOIN app_users_activity_variable_values " +
                "ON app_users_activity_variable_values.level_number" +
                "=groups_phase_levels.level_number " +
                "AND app_users_activity_variable_values.activity_id" +
                "=activities.activity_id " +
                "WHERE groups_phase_levels.level_number=" + mAppUserCurrentLevel + " " +
                "AND groups_phase_levels.group_id=" + mGroupID + " " +
                "AND groups_phase_levels.phase_id=" + mPhaseID + " ";
        mRawSQL+="GROUP BY activities.activity_id " +
                "ORDER BY activities.activity_code ASC";
        mCursor = mDatabase.rawQuery(mRawSQL, null);

        if(mCursor.moveToFirst()) {
            do {
                String mID=mCursor.getString(mCursor.getColumnIndex("auavv_id"));
                String mActivityID=mCursor.getString(mCursor.getColumnIndex("activity_id"));
                if (mID == null) {
                    ContentValues values = new ContentValues();
                    values.put("variable_id",mAppUserCurrentLevel);
                    values.put("type_id","3");
                    values.put("group_id", mGroupID);
                    values.put("phase_id", mPhaseID);
                    values.put("activity_id", mActivityID);
                    values.put("app_user_id", mUserID);
                    values.put("level_number", mAppUserCurrentLevel);

                    if (mActivityID.equals("35")) {
                        values.put("number_correct_in_a_row",mPerfectValue);
                        values.put("number_correct",mPerfectValue);
                    } else if ((mActivityID.equals("17") || mActivityID.equals("24")) &&
                            (mAppUserCurrentLevel.equals("1"))) {
                        values.put("number_correct_in_a_row",mPerfectValue);
                        values.put("number_correct",mPerfectValue);
                    }else{
                        values.put("number_correct_in_a_row", "0");
                        values.put("number_correct", "0");
                    }
                    values.put("number_incorrect", "0");

                    long current_id = mDatabase.insert("app_users_activity_variable_values",
                            null, values);
                    mRawSQL = "UPDATE app_users_activity_variable_values " +
                            "SET auavv_id =" + String.valueOf(current_id) + " " +
                            "WHERE _id =" + String.valueOf(current_id);
                    mDatabase.execSQL(mRawSQL);
                }
            } while (mCursor.moveToNext());
        }
        mCursor.close();
    }

    //////////////////////////////////////////////////////////////////////////////////


    protected void updatePhonicsProcessOLD(String[] projection, String whereSection) {
        Log.d(Constants.LOGCAT, "updatePhonicsProcess");
        String mAppUserCurrentLevel="0";

        String mRawSQL="SELECT * " +
                "FROM app_users_current_gpl " +
                "WHERE group_id=2 AND phase_id=3 " +
                "AND app_user_id="+projection[1]+" LIMIT 1";
        Cursor userCursor = mDatabase.rawQuery(mRawSQL, null);
        if(userCursor.moveToFirst()) {
            do {
                mAppUserCurrentLevel=userCursor.getString(
                        userCursor.getColumnIndex("app_user_current_level"));
            } while (userCursor.moveToNext());
        }
        userCursor.close();

        projection[3]="3";
        projection[4]="5";

        //  String mAUAVVTable="app_users_variables_values";

        mRawSQL="SELECT * FROM groups_phase_sections_activities " +
                "WHERE group_id="+projection[2]+" "+
                "AND phase_id="+projection[3]+" "+
                "AND section_id="+projection[4];
        Cursor mCursor = mDatabase.rawQuery(mRawSQL, null);
        if(mCursor.moveToFirst()) {
            do {

                String mActivityID = mCursor.getString(mCursor.getColumnIndex(
                        "activity_id"));

                mRawSQL="SELECT  COUNT(pw1.phonic_word_first_id) AS first_count, " +
                        "COUNT(pw2.phonic_word_last_id) AS last_count, "+
                        "app_users_variables_values._id AS auavv_id_correct, " +
                        "app_users_variables_values.*, " +
                        "variable_type_relations.variable_type_id, " +
                        "phonics.* " +
                        "FROM phonics LEFT JOIN variable_type_relations " +
                        "ON variable_type_relations.variable_id=phonics.phonic_id  " +
                        "INNER JOIN variable_phase_levels " +
                        "ON variable_phase_levels.variable_id=phonics.phonic_id " +
                        "LEFT JOIN app_users_variables_values " +
                        "ON app_users_variables_values.variable_id=phonics.phonic_id " +
                        "AND app_users_variables_values.activity_id="+mActivityID+" "+
                        "AND app_users_variables_values.app_user_id="+projection[1]+" "+
                        " LEFT JOIN phonic_words AS pw1 " +
                        "ON pw1.phonic_word_first_id=phonics.phonic_id   " +
                        "LEFT JOIN phonic_words AS pw2 " +
                        "ON   pw2.phonic_word_last_id=phonics.phonic_id " +
                        "WHERE "+whereSection + " " +
                        "AND variable_type_relations.variable_type_id=3 " +
                        "GROUP BY phonics.phonic_id";
                Cursor mCursor2 = mDatabase.rawQuery(mRawSQL, null);

                for (mCursor2.moveToFirst(); !mCursor2.isAfterLast(); mCursor2.moveToNext()) {
                    String auavv_id = mCursor2.getString(
                            mCursor2.getColumnIndex("auavv_id_correct"));
                    if (auavv_id == null) {

                        ContentValues values = new ContentValues();
                        values.put("variable_id",
                                mCursor2.getString(mCursor2.getColumnIndex("phonic_id")));
                        values.put("type_id",
                                mCursor2.getString(mCursor2.getColumnIndex("variable_type_id")));

                        values.put("group_id", projection[2]);
                        values.put("phase_id", projection[3]);
                        values.put("activity_id",
                                mActivityID);
                        values.put("app_user_id",
                                projection[1]);
                        values.put("level_number", projection[5]);
                        /*
                        int mForward=1;
                        if(mForward==1){
                            values.put("number_correct_in_a_row", "2");
                            values.put("number_correct", "2");
                        }else */if(Integer.parseInt(mAppUserCurrentLevel)<6){
                            values.put("number_correct_in_a_row", "2");
                            values.put("number_correct", "2");
                        }else if (mActivityID.equals("35")) {

                            values.put("number_correct_in_a_row",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                            values.put("number_correct",
                                    String.valueOf(Constants.INA_ROW_CORRECT));

                        } else if ((mActivityID.equals("17") || mActivityID.equals("24")) &&
                                (mAppUserCurrentLevel.equals("1"))) {
                            //|| CurrentGroup_Section_Phase.get(3).equals("2"))) {

                            values.put("number_correct_in_a_row",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                            values.put("number_correct",
                                    String.valueOf(Constants.INA_ROW_CORRECT));

                        } else if ((mActivityID.equals("17") || mActivityID.equals("24")) &&
                                mCursor2.getString(mCursor2.getColumnIndex("first_count"))
                                        .equals("0") &&
                                mCursor2.getString(mCursor2.getColumnIndex("last_count"))
                                        .equals("0")){

                            values.put("number_correct_in_a_row",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                            values.put("number_correct",
                                    String.valueOf(Constants.INA_ROW_CORRECT));

                        }else if(mActivityID.equals("17") &&
                                mCursor2.getString(mCursor2.getColumnIndex("last_count"))
                                        .equals("0")){

                            values.put("number_correct_in_a_row",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                            values.put("number_correct",
                                    String.valueOf(Constants.INA_ROW_CORRECT));

                        }else if((mActivityID.equals("15") || mActivityID.equals("19") ||
                                mActivityID.equals("25") )&&
                                mCursor2.getString(mCursor2.getColumnIndex("first_count"))
                                        .equals("0")){

                            values.put("number_correct_in_a_row",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                            values.put("number_correct",
                                    String.valueOf(Constants.INA_ROW_CORRECT));
                        }else{
                            values.put("number_correct_in_a_row", "0");
                            values.put("number_correct", "0");
                        }


                        values.put("number_incorrect", "0");


                        long current_id = mDatabase.insert("app_users_variables_values",
                                null, values);

                        mRawSQL = "UPDATE app_users_variables_values " +
                                "SET  auvv_id =" + String.valueOf(current_id)
                                + " WHERE _id =" + String.valueOf(current_id);

                        mDatabase.execSQL(mRawSQL);

                    }
                    /*
                    else if (mCursor2.getInt(mCursor2.getColumnIndex("number_correct")) >= Constants.NUMBER_CORRECT) {
                    }//end if(auavv_id.equals("")){
                     */
                }//end for(mCursor2.moveToFirst(); !mCursor2.isAfterLast(); mCursor2.moveToNext()){
                mCursor2.close();
            } while (mCursor.moveToNext());
        }

        mCursor.close();


    }
    /////////////////////////////////////////////////////////////////////////////////////////////

}
