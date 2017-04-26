package com.motoli.apps.allsubjects;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import com.motoli.apps.allsubjects.R.drawable;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

public class Activities_Platform extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor> {

    ArrayList<ArrayList<String>> mCurrentActivities;
    ArrayList<HashMap<String,String>> mAllPhaseInfo;
    ArrayList<ArrayList<String>> mActivityComplete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activities_platform);
        fadeInOrOutScreenInActivity(true);
        appData = ((Motoli_Application) getApplicationContext());

        appData.setClassesForActivities();

        appData.setActivityType(2);
        mInstructionAudio = "choose_your_activity";
        findViewById(R.id.btnVideo).setVisibility(ImageView.VISIBLE);

        if(mCurrentGSP.get("group_id").equals("2")){
            findViewById(R.id.activityMainFrame)
                    .setBackgroundResource(R.color.application_letters);
        }else if(mCurrentGSP.get("group_id").equals("3")){
            findViewById(R.id.activityMainFrame)
                    .setBackgroundResource(R.color.application_syllables);
        }else if(mCurrentGSP.get("group_id").equals("4")){
            findViewById(R.id.activityMainFrame)
                    .setBackgroundResource(R.color.application_words);
        }else if(mCurrentGSP.get("group_id").equals("5")){
            findViewById(R.id.activityMainFrame)
                    .setBackgroundResource(R.color.application_books);
        }else if(mCurrentGSP.get("group_id").equals("6")){
            findViewById(R.id.activityMainFrame)
                    .setBackgroundResource(R.color.application_math_shape);
            findViewById(R.id.btnVideo).setAlpha(0.5f);
        }else if(mCurrentGSP.get("group_id").equals("7")){
            findViewById(R.id.activityMainFrame)
                    .setBackgroundResource(R.color.application_numbers);
           // findViewById(R.id.tablesButton).setVisibility(ImageView.VISIBLE);

        }else if(mCurrentGSP.get("group_id").equals("8")){
            findViewById(R.id.activityMainFrame)
                    .setBackgroundResource(R.color.application_math_operations);
            //findViewById(R.id.tablesButton).setVisibility(ImageView.VISIBLE);

        }else if(mCurrentGSP.get("group_id").equals("9")){
            findViewById(R.id.activityMainFrame)
                    .setBackgroundResource(R.color.application_write);
        }


        if(mCurrentGSP.get("group_id").equals("2") &&
                (mCurrentGSP.get("phase_id").equals("1")
                        || mCurrentGSP.get("phase_id").equals("2"))) {
            findViewById(R.id.btnChangeCase).setVisibility(ImageView.INVISIBLE);
        }else{
            findViewById(R.id.btnChangeCase).setVisibility(ImageView.INVISIBLE);
        }

       getLoaderManager().initLoader(Constants.UPDATE_LEVELS, null, this);




    }



    @Override
    public void onResume() {
        super.onResume();
        findViewById(R.id.btnVideo).setVisibility(ImageView.VISIBLE);
    }

    public void onPause(){
        System.gc();
        super.onPause();

    }


    /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.ACTIVITIES_LOADER:{
                projection = new String[] {
                        mCurrentGSP.get("current_level"),
                        mCurrentGSP.get("group_id"),
                        "phase_id",
                        "section_id",
                        "activity_id",
                        "activity_name",
                        "activity_img"};

                String selection="groups_phase_sections_activities.group_id="+
                        mCurrentGSP.get("group_id")+ " " +
                        "AND groups_phase_sections_activities.section_id=" +
                        mCurrentGSP.get("section_id");

                String order="activities.activity_order ASC";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_SELECTED_ACTIVITIES, projection, selection, null, order);
                break;
            }
            case Constants.ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE:{
                projection = new String[]{"0","0"};
                switch(Integer.parseInt(mCurrentGSP.get("group_id"))){
                    default:
                    case 2:{
                        if (mCurrentGSP.get("phase_id").equals("2")
                                || mCurrentGSP.get("phase_id").equals("1")){
                           projection[0]="1";
                            projection[1]="2";
                        }else if (mCurrentGSP.get("phase_id").equals("3")) {
                           projection[0]="3";
                        }
                        break;
                    }
                    case 3: {
                        projection[0] = "4";
                        projection[1] = "5";
                        break;
                    }
                    case 4: {
                        projection[0] = "6";
                        break;
                    }
                    case 5:{
                        projection[0]="7";
                        break;
                    }
                    case 6: {
                        projection[0] = "9";
                        break;
                    }
                    case 7: {
                        projection[0] = "10";
                        break;
                    }
                    case 8: {
                        projection[0] = "11";
                        break;
                    }
                    case 9: {
                        projection[0] = "8";
                        break;
                    }
                }//end switch

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE,
                        projection, null, null, null);
                break;
            }
            case Constants.UPDATE_LEVELS:{
                projection = new String[] {
                        mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        mCurrentGSP.get("current_level")};
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_GROUPS_PHASE_LEVELS_UPDATE, projection,
                        appData.getCurrentUserID(), null, "1");
                break;
            }

        }

        return cursorLoader;

    }


    @Override
     public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

         switch(loader.getId()) {
             default:
             case Constants.ACTIVITIES_LOADER:{

                mCurrentActivities=new ArrayList<>();
                int mActivityCount=0;
                 boolean allowSubstitution,allowMultiplication;
                 allowSubstitution=allowMultiplication=false;
                 if(data.moveToFirst()){

                     if(mCurrentGSP.get("group_id").equals("8")){

                         allowSubstitution
                                 = (data.getInt(data.getColumnIndex("allow_substitution"))>0);
                         allowMultiplication
                                 = (data.getInt(data.getColumnIndex("allow_multiplication"))>0);
                     }
                     do{
                        mCurrentActivities.add(new ArrayList<String>());
                        mCurrentActivities.get(mActivityCount).add(data.getString(
                                data.getColumnIndex("activity_id"))); //0
                        mCurrentActivities.get(mActivityCount).add(data.getString(
                                data.getColumnIndex("activity_name"))); //1
                        mCurrentActivities.get(mActivityCount).add(data.getString(
                                data.getColumnIndex("activity_img"))); //2

                        if(Integer.parseInt(mCurrentGSP.get("phase_id"))>= Integer.parseInt(
                                data.getString(data.getColumnIndex("minphase_id")))){
                            mCurrentActivities.get(mActivityCount).add("1"); //3
                        }else{
                            mCurrentActivities.get(mActivityCount).add("0"); //3
                        }


                        mCurrentActivities.get(mActivityCount).add(data.getString(
                                data.getColumnIndex("activity_type"))); //4

                        mCurrentActivities.get(mActivityCount).add("0"); //5
                        mCurrentActivities.get(mActivityCount).add("1"); //6


                        /*
                         * Will  not allow LT04|8 until at least 6 variables are available
                         */
                        if(mCurrentGSP.get("group_id").equals("2") &&
                                mCurrentActivities.get(mActivityCount).get(0).equals("8") &&
                                Integer.parseInt(mCurrentGSP.get("current_level"))<6 &&
                                (mCurrentGSP.get("phase_id").equals("1") ||
                                        mCurrentGSP.get("phase_id").equals("2")) ) {
                            mCurrentActivities.get(mActivityCount).set(3, "0"); //3
                        }


                        /*
                         * Will not allow SD03|17 or SD06|24 to be available until at least level 3
                         * of phonics is reached
                         */
                        if(mCurrentGSP.get("group_id").equals("2") &&
                                (mCurrentActivities.get(mActivityCount).get(0).equals("17")
                                        || mCurrentActivities.get(
                                        mActivityCount).get(0).equals("24"))
                                && Integer.parseInt(mCurrentGSP.get("current_level"))<2
                                && (mCurrentGSP.get("phase_id").equals("3")) ) {
                            mCurrentActivities.get(mActivityCount).set(3, "0"); //3
                        }

                        /*
                         * Will not allow SH03 and SH05 to appear until level 2
                         */
                        if(mCurrentGSP.get("group_id").equals("6") &&
                                (mCurrentActivities.get(mActivityCount).get(0).equals("58") ||
                                        mCurrentActivities.get(mActivityCount).get(0).equals("57") ||
                                        mCurrentActivities.get(mActivityCount).get(0).equals("56"))
                                && Integer.parseInt(mCurrentGSP.get("current_level"))<2){
                            mCurrentActivities.get(mActivityCount).set(3,"0"); //3
                        }

                        /*
                         * below takes care of the RS activities alone and hides them until after
                         * level 1 in phonics has been completed.
                         */
                        if(mCurrentGSP.get("group_id").equals("3") &&
                                mCurrentGSP.get("phase_id").equals("1")){
                            for(int i=0; i<mAllPhaseInfo.size(); i++) {
                                if (mAllPhaseInfo.get(i).get("group_id").equals("2") &&
                                        mAllPhaseInfo.get(i).get("phase_id").equals("3") &&
                                        Integer.parseInt(mAllPhaseInfo.get(i)
                                                .get("app_user_current_level"))<= 2 &&
                                        mCurrentActivities.get(mActivityCount).get(4).equals("RS")){
                                    mCurrentActivities.get(mActivityCount).set(3, "0");
                                    break;
                                }
                            }//end for
                        }//end if

                        for(int i=0;i<mActivityComplete.size();i++){
                            if(mActivityComplete.get(i).get(0)
                                    .equals(mCurrentActivities.get(mActivityCount).get(0)) ) {
                                mCurrentActivities.get(mActivityCount).set(5,
                                        mActivityComplete.get(i).get(1));
                                break;
                            }
                        }
                        mActivityCount++;
                     }while(data.moveToNext());
                 }

                 /*
                  * Takes into consideration that for each level user must finish in this
                  * order OP1, OP3, OP2
                  * And OP4, OP5
                  * OP6 is not available until OP1, OP3, and OP4 have been completed.
                  */
                 if(mCurrentGSP.get("group_id").equals("8")){
                     mCurrentActivities.get(1).set(3,"0");
                     mCurrentActivities.get(2).set(3,"0");
                     mCurrentActivities.get(3).set(3,"0");
                     mCurrentActivities.get(4).set(3,"0");
                     mCurrentActivities.get(5).set(3,"0");


                     mCurrentActivities.get(1).set(3, "1");
                     if (Integer.parseInt(mCurrentActivities.get(1).get(5)) >=
                             Constants.MATH_OPERATIONS_INA_ROW_CORRECT) {
                         mCurrentActivities.get(2).set(3, "1");

                     }

                     if (Integer.parseInt(mCurrentActivities.get(2).get(5)) >=
                             Constants.MATH_OPERATIONS_INA_ROW_CORRECT
                             && allowSubstitution) {
                         mCurrentActivities.get(3).set(3, "1");
                     }

                     if (Integer.parseInt(mCurrentActivities.get(3).get(5)) >=
                             Constants.MATH_OPERATIONS_INA_ROW_CORRECT
                             && allowSubstitution) {
                         mCurrentActivities.get(4).set(3, "1");
                     }
                     if (Integer.parseInt(mCurrentActivities.get(4).get(5)) >=
                             Constants.MATH_OPERATIONS_INA_ROW_CORRECT
                             && allowMultiplication){
                         mCurrentActivities.get(5).set(3, "1");
                     }
                 }


                 displayScreen();

                break;
            }
             case Constants.ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE:{
                 mActivityComplete=new ArrayList<>();
                 int mActivity=0;
                 data.moveToFirst();
                 if(data.getCount()>0) {
                     do {
                         mActivityComplete.add(new ArrayList<String>());
                         mActivityComplete.get(mActivity).add(
                                 data.getString(data.getColumnIndex("activity_id")));
                         mActivityComplete.get(mActivity).add(
                                 data.getString(data.getColumnIndex("number_correct_in_a_row")));
                         mActivity++;
                     } while (data.moveToNext());
                 }
                 getLoaderManager().restartLoader(Constants.ACTIVITIES_LOADER, null, this);
                 break;
             }
            case Constants.UPDATE_LEVELS:{

                mCurrentGSP=appData.getCurrentGroup_Section_Phase();

                //This is only used for LT05 and the RS activities
                mAllPhaseInfo=new ArrayList<>();
                if(data.moveToFirst()){
                    do {
                        mAllPhaseInfo.add(new HashMap<String, String>());
                        mAllPhaseInfo.get(mAllPhaseInfo.size() - 1).put("app_user_id",
                                data.getString(data.getColumnIndex("app_user_id"))); //0
                        mAllPhaseInfo.get(mAllPhaseInfo.size() - 1).put("group_id",
                                data.getString(data.getColumnIndex("group_id"))); //1
                        mAllPhaseInfo.get(mAllPhaseInfo.size() - 1).put("phase_id",
                                data.getString(data.getColumnIndex("phase_id"))); //2
                        mAllPhaseInfo.get(mAllPhaseInfo.size() - 1).put("app_user_current_level",
                                data.getString(data.getColumnIndex("app_user_current_level"))); //3

                        if (mCurrentGSP.get("group_id").equals(
                                mAllPhaseInfo.get(mAllPhaseInfo.size() - 1).get("group_id"))
                                && mCurrentGSP.get("phase_id").equals(mAllPhaseInfo
                                .get(mAllPhaseInfo.size() - 1).get("phase_id")) ) {
                            appData.setCurrentGroup_Section_Phase(
                                    mAllPhaseInfo.get(mAllPhaseInfo.size() - 1).get("group_id"),
                                    mAllPhaseInfo.get(mAllPhaseInfo.size() - 1).get("phase_id"),
                                    mCurrentGSP.get("section_id"),
                                    mAllPhaseInfo.get(mAllPhaseInfo.size() - 1)
                                            .get("app_user_current_level"));
                            mCurrentGSP=appData.getCurrentGroup_Section_Phase();
                        }
                    }while(data.moveToNext());
                }
                appData.setAppUserCurrentGPL(mAllPhaseInfo);

                getLoaderManager().restartLoader(
                        Constants.ACTIVITY_GROUP_SECTION_LEVEL_COMPLETE, null, this);

                break;
            }
         }

     }

     @Override
     public void onLoaderReset(Loader<Cursor> loader) { }

    private boolean visibleReward(int mPosition){
        if(((mCurrentGSP.get("group_id").equals("4") ||
                mCurrentGSP.get("group_id").equals("6")
                || mCurrentGSP.get("group_id").equals("3")) &&
                Integer.parseInt(mCurrentActivities.get(mPosition).get(5))
                        >=Constants.INA_ROW_CORRECT_WORDS)
                || (mCurrentGSP.get("group_id").equals("8")
                && Integer.parseInt(mCurrentActivities.get(mPosition).get(5))
                >=Constants.MATH_OPERATIONS_INA_ROW_CORRECT)
                || (Integer.parseInt(mCurrentActivities.get(mPosition).get(5))
                >=Constants.INA_ROW_CORRECT)
                && !mCurrentGSP.get("group_id").equals("8")){
            return (mPosition>0
                    || mCurrentGSP.get("section_id").equals("11")
                    || mCurrentGSP.get("section_id").equals("8")
                    || mCurrentGSP.get("section_id").equals("23"));
        }else{
            return false;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    protected void displayScreen(){


        fadeInOrOutScreenInActivity(true);
        if(mCurrentActivities.get(0).get(3).equals("0")
                && mCurrentGSP.get("group_id").equals("3")
                && !mCurrentActivities.get(0).get(0).equals("47")) {
            findViewById(R.id.btnVideo).setAlpha(0.5f);
        }else{
            findViewById(R.id.btnVideo).setAlpha(1.0f);
        }

        for(int i=0; i<mCurrentActivities.size(); i++){
            String imageName=mCurrentActivities.get(i).get(2)
                    .replace(".jpg", "").replace(".png", "");

            Class<drawable> res = R.drawable.class;
            int drawableId;
            Field field;
            if(mCurrentGSP.get("group_id").equals("2")){
                ((ImageView) findViewById(R.id.rewardCup1)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_red));
                ((ImageView) findViewById(R.id.rewardCup2)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_red));
                ((ImageView) findViewById(R.id.rewardCup3)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_red));
                ((ImageView) findViewById(R.id.rewardCup4)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_red));
                ((ImageView) findViewById(R.id.rewardCup5)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_red));
                ((ImageView) findViewById(R.id.rewardCup6)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_red));
            }else if(mCurrentGSP.get("group_id").equals("3")) {
                ((ImageView) findViewById(R.id.rewardCup1)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_blue));
                ((ImageView) findViewById(R.id.rewardCup2)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_blue));
                ((ImageView) findViewById(R.id.rewardCup3)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_blue));
                ((ImageView) findViewById(R.id.rewardCup4)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_blue));
                ((ImageView) findViewById(R.id.rewardCup5)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_blue));
                ((ImageView) findViewById(R.id.rewardCup6)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_blue));
            }else{
                ((ImageView) findViewById(R.id.rewardCup1)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_orange));
                ((ImageView) findViewById(R.id.rewardCup2)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_orange));
                ((ImageView) findViewById(R.id.rewardCup3)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_orange));
                ((ImageView) findViewById(R.id.rewardCup4)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_orange));
                ((ImageView) findViewById(R.id.rewardCup5)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_orange));
                ((ImageView) findViewById(R.id.rewardCup6)).setImageDrawable(
                        ContextCompat.getDrawable(this,drawable.btn_award_orange));
            }
            switch(i){
                default:
                case 0:{
                    findViewById(R.id.activityIcon1).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon1).setAlpha(0.5f);
                        findViewById(R.id.rewardCup1).setVisibility(ImageView.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon1).setAlpha(1.0f);
                        if(((mCurrentGSP.get("group_id").equals("4") ||
                                mCurrentGSP.get("group_id").equals("6")
                                || mCurrentGSP.get("group_id").equals("3")) &&
                                Integer.parseInt(mCurrentActivities.get(i).get(5))
                                        >=Constants.INA_ROW_CORRECT_WORDS)
                                || (mCurrentGSP.get("group_id").equals("8")
                                && Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.MATH_OPERATIONS_INA_ROW_CORRECT)
                                || (Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.INA_ROW_CORRECT)
                                && !mCurrentGSP.get("group_id").equals("8")){
                            if(mCurrentGSP.get("section_id").equals("11") ||
                                    mCurrentGSP.get("section_id").equals("8") /* ||
                                    mCurrentGSP.get("section_id").equals("23")*/){
                                findViewById(R.id.rewardCup1)
                                        .setVisibility(ImageView.VISIBLE);
                            }else {
                                findViewById(R.id.rewardCup1)
                                        .setVisibility(ImageView.INVISIBLE);
                            }
                        }else{
                            findViewById(R.id.rewardCup1)
                                    .setVisibility(ImageView.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon1)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                case 1:{
                     findViewById(R.id.activityIcon2).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon2).setAlpha(0.5f);
                        findViewById(R.id.rewardCup2).setVisibility(ImageView.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon2).setAlpha(1.0f);
                        if(((mCurrentGSP.get("group_id").equals("4")
                                || mCurrentGSP.get("group_id").equals("6")
                                || mCurrentGSP.get("group_id").equals("3"))
                                && Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.INA_ROW_CORRECT_WORDS)
                                || (mCurrentGSP.get("group_id").equals("8")
                                && Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.MATH_OPERATIONS_INA_ROW_CORRECT)
                                || (Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.INA_ROW_CORRECT)
                                && !mCurrentGSP.get("group_id").equals("8")){
                            findViewById(R.id.rewardCup2).setVisibility(ImageView.VISIBLE);
                        }else{
                            findViewById(R.id.rewardCup2).setVisibility(ImageView.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon2)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                case 2:{
                     findViewById(R.id.activityIcon3).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon3).setAlpha(0.5f);
                        findViewById(R.id.rewardCup3).setVisibility(ImageView.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon3).setAlpha(1.0f);
                        if(visibleReward(i)){
                            findViewById(R.id.rewardCup3)
                                    .setVisibility(ImageView.VISIBLE);
                        }else{
                            findViewById(R.id.rewardCup3)
                                    .setVisibility(ImageView.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon3)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                case 3:{

                     findViewById(R.id.activityIcon4).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon4).setAlpha(0.5f);
                        findViewById(R.id.rewardCup4).setVisibility(ImageView.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon4).setAlpha(1.0f);
                        if(((mCurrentGSP.get("group_id").equals("4") ||
                                mCurrentGSP.get("group_id").equals("6")
                                || mCurrentGSP.get("group_id").equals("3"))&&
                                Integer.parseInt(mCurrentActivities.get(i).get(5))
                                        >=Constants.INA_ROW_CORRECT_WORDS)
                                || (mCurrentGSP.get("group_id").equals("8")
                                && Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.MATH_OPERATIONS_INA_ROW_CORRECT)
                                || (Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.INA_ROW_CORRECT)
                                && !mCurrentGSP.get("group_id").equals("8")){
                            findViewById(R.id.rewardCup4)
                                    .setVisibility(ImageView.VISIBLE);
                        }else{
                            findViewById(R.id.rewardCup4)
                                    .setVisibility(ImageView.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon4))
                                .setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                case 4:{
                     findViewById(R.id.activityIcon5).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")){
                        findViewById(R.id.activityIcon5).setAlpha(0.5f);
                        findViewById(R.id.rewardCup5).setVisibility(ImageView.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon5).setAlpha(1.0f);
                        if(((mCurrentGSP.get("group_id").equals("4") ||
                                mCurrentGSP.get("group_id").equals("6")
                                || mCurrentGSP.get("group_id").equals("3") )&&
                                Integer.parseInt(mCurrentActivities.get(i).get(5))
                                        >=Constants.INA_ROW_CORRECT_WORDS)
                                || (mCurrentGSP.get("group_id").equals("8")
                                && Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.MATH_OPERATIONS_INA_ROW_CORRECT)
                                || (Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.INA_ROW_CORRECT)
                                && !mCurrentGSP.get("group_id").equals("8")){
                            findViewById(R.id.rewardCup5).setVisibility(ImageView.VISIBLE);
                        }else{
                            findViewById(R.id.rewardCup5)
                                    .setVisibility(ImageView.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon5)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }
                case 5:{
                    if(mCurrentActivities.get(5).get(0).equals("59")
                            && Integer.parseInt(mCurrentGSP.get("current_level"))<7){
                        mCurrentActivities.get(i).set(3,"0");
                    }
                     findViewById(R.id.activityIcon6).setTag(mCurrentActivities.get(i));
                    if(mCurrentActivities.get(i).get(3).equals("0")
                            || (mCurrentActivities.get(i).get(0).equals("59")
                            && Integer.parseInt(mCurrentGSP.get("current_level"))<7)){
                        findViewById(R.id.activityIcon6).setAlpha(0.5f);
                        findViewById(R.id.rewardCup6).setVisibility(ImageView.INVISIBLE);
                    }else{
                        findViewById(R.id.activityIcon6).setAlpha(1.0f);

                        if(((mCurrentGSP.get("group_id").equals("4") ||
                                (mCurrentGSP.get("group_id").equals("6")
                                        && Integer.parseInt(mCurrentGSP.get("current_level"))>6)
                                || mCurrentGSP.get("group_id").equals("3") )&&
                                Integer.parseInt(mCurrentActivities.get(i).get(5))
                                        >=Constants.INA_ROW_CORRECT_WORDS)
                                || (mCurrentGSP.get("group_id").equals("8")
                                && Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.MATH_OPERATIONS_INA_ROW_CORRECT)
                                || (Integer.parseInt(mCurrentActivities.get(i).get(5))
                                >=Constants.INA_ROW_CORRECT)
                                && !mCurrentGSP.get("group_id").equals("8")){

                            findViewById(R.id.rewardCup6).setVisibility(ImageView.VISIBLE);
                        }else{
                            findViewById(R.id.rewardCup6).setVisibility(ImageView.INVISIBLE);
                        }
                    }

                    try{
                        field = res.getField(imageName);
                        drawableId = field.getInt(null);
                        ((ImageView) findViewById(R.id.activityIcon6)).setImageResource(drawableId);

                    }catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    break;
                }



            }//end switch(i){
        }//end for(int i=0; i<mCurrentActivities.size(); i++){

        activityButtonListener();

    }//end public void displayScreen(){


    //////////////////////////////////////////////////////////////////////////////////////////

    private void activityButtonListener(){

        findViewById(R.id.btnVideo).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(!(mCurrentActivities.get(0).get(3).equals("0")
                        && mCurrentGSP.get("group_id").equals("3"))
                        || !mCurrentActivities.get(0).get(0).equals("47")) {
                    moveToVideo();
                }
            }
        });

        /*
            Originally was ArrayList<String> mTempArray = new ArrayList<>(
                            (ArrayList<String>) findViewById(R.id.activityIcon1).getTag());
         */
        findViewById(R.id.activityIcon1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (findViewById(R.id.activityIcon1).getTag() instanceof ArrayList) {
                    ArrayList<?> mTempArray = new ArrayList<>(
                            (ArrayList<?>) findViewById(R.id.activityIcon1).getTag());
                    if (mTempArray.get(3).equals("1")) {
                        processActivityButton(mTempArray.get(0));
                    }
                }
            }
        });

        findViewById(R.id.activityIcon2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                ArrayList<?> mTempArray=new ArrayList<>(
                        (ArrayList<?>) findViewById(R.id.activityIcon2).getTag());
                if(mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
            }
        });

        findViewById(R.id.activityIcon3).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<?> mTempArray=new ArrayList<>(
                        (ArrayList<?>) findViewById(R.id.activityIcon3).getTag());
                if(mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
            }
        });

        findViewById(R.id.activityIcon4).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<?> mTempArray=new ArrayList<>(
                        (ArrayList<?>) findViewById(R.id.activityIcon4).getTag());
                if(mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
            }
        });

        findViewById(R.id.activityIcon5).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<?> mTempArray=new ArrayList<>(
                        (ArrayList<?>) findViewById(R.id.activityIcon5).getTag());
                if(mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
            }
        });

        findViewById(R.id.activityIcon6).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<?> mTempArray = new ArrayList<>(
                        (ArrayList<?>) findViewById(R.id.activityIcon6).getTag());
                if(mTempArray.get(3).equals("1")) {
                    processActivityButton(mTempArray.get(0));
                }
            }
        });



    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void processActivityButton(Object imageTag){
        int clickedActivityID= Integer.parseInt(imageTag.toString());

        ArrayList<String> currentActivity=new ArrayList<>();

        currentActivity.add(String.valueOf(clickedActivityID));

        appData.setCurrentActivity(currentActivity);

        switch(clickedActivityID){
            default:{
                break;
            }
            case 6:{
                appData.setCurrentActivityName("ActivityLT03");
                Intent main = new Intent(this,ActivityLT03.class);
                startActivity(main);
                finish();
                break;
            }
            case 20:{
                appData.setCurrentActivityName("ActivityLT05");
                Intent main = new Intent(this,ActivityLT05.class);
                startActivity(main);
                finish();
                break;
            }//end case 20 LT05
            case 22:{
                appData.setCurrentActivityName("ActivityLT01");
                Intent main = new Intent(this,ActivityLT01.class);
                startActivity(main);
                finish();
                break;
            }//end case 22 TL01
            case 2:{
                appData.setCurrentActivityName("ActivityLT02");
                Intent main = new Intent(this,ActivityLT02.class);
                startActivity(main);
                finish();
                break;
            }//end case 2 LT02
            case 4:{
                appData.setCurrentActivityName("ActivityLT06");
                Intent main = new Intent(this,ActivityLT06.class);
                startActivity(main);
                finish();
                break;
            }//end case 2 LT02
            case 8:{
                appData.setCurrentActivityName("ActivityLT04");
                Intent main = new Intent(this,ActivityLT04.class);
                startActivity(main);
                finish();
                break;
            }//end case 8 LT04
            case 23:{
                appData.setCurrentActivityName("ActivitySD01");
                Intent main = new Intent(this,ActivitySD01.class);
                startActivity(main);
                finish();
                break;
            }
            case 15:{
                appData.setCurrentActivityName("ActivitySD02");
                Intent main = new Intent(this,ActivitySD02.class);
                startActivity(main);
                finish();
                break;
            }//end case 15 ActivitySD02
            case 17:{
                appData.setCurrentActivityName("ActivitySD03");
                Intent main = new Intent(this,ActivitySD03.class);
                startActivity(main);
                finish();
                break;
            }//end case 17 ActivitySD03
            case 24:{
                appData.setCurrentActivityName("ActivitySD06");
                Intent main = new Intent(this,ActivitySD06.class);
                startActivity(main);
                finish();
                break;
            }//end case 17 ActivitySD03
            case 35:{
                appData.setCurrentActivityName("ActivitySD07");
                Intent main = new Intent(this,ActivitySD07.class);
                startActivity(main);
                finish();
                break;
            }//end case 35 ActivitySD07
            case 25:{
                appData.setCurrentActivityName("ActivitySD04");
                Intent main = new Intent(this,ActivitySD04.class);
                startActivity(main);
                finish();
                break;
            }//end case 17 ActivitySD03

            case 29:{
                mCurrentGSP.put("phase_id","1");
                appData.setCurrentActivityName("ActivityCS01");
                Intent main = new Intent(this,ActivityCS01.class);
                startActivity(main);
                finish();
                break;
            }//end case 29 ActivityCS01
            case 30:{
                mCurrentGSP.put("phase_id","1");
                appData.setCurrentActivityName("ActivityCS02");
                Intent main = new Intent(this,ActivityCS02.class);
                startActivity(main);
                finish();
                break;
            }//end case 30 ActivityCS02

            case 32:{
                mCurrentGSP.put("phase_id","2");
                appData.setCurrentGroup_Section_Phase(mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        mCurrentGSP.get("current_level"));
                appData.setCurrentActivityName("ActivityRS01");
                Intent main = new Intent(this,ActivityRS01.class);
                startActivity(main);
                finish();
                break;
            }//end case 32 ActivityRS01
            case 33:{
                mCurrentGSP.put("phase_id","2");
                appData.setCurrentGroup_Section_Phase(mCurrentGSP.get("group_id"),
                        mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"),
                        mCurrentGSP.get("current_level"));
                appData.setCurrentActivityName("ActivityRS02");
                Intent main = new Intent(this,ActivityRS02.class);
                startActivity(main);
                finish();
                break;
            }//end case 33 ActivityRS02
            case 34:{
                mCurrentGSP.put("phase_id","2");
                appData.setCurrentGroup_Section_Phase(mCurrentGSP.get("group_id"), mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"), mCurrentGSP.get("current_level"));
                appData.setCurrentActivityName("ActivityRS03");
                Intent main = new Intent(this,ActivityRS03.class);
                startActivity(main);
                finish();
                break;
            }//end case 34 ActivityRS03
            case 41:{
                appData.setCurrentActivityName("ActivityWD01");
                Intent main = new Intent(this,ActivityWD01.class);
                startActivity(main);
                finish();
                break;
            }//end case 41 ActivityWD01
            case 42:{
                appData.setCurrentActivityName("ActivityWD02");
                Intent main = new Intent(this,ActivityWD02.class);
                startActivity(main);
                finish();
                break;
            }//end case 42 ActivityWD02
            case 43:{
                appData.setCurrentActivityName("ActivityWD03");
                Intent main = new Intent(this,ActivityWD03.class);
                startActivity(main);
                finish();
                break;
            }//end case 43 ActivityWD03
            case 44:{
                appData.setCurrentActivityName("ActivityWD04");
                Intent main = new Intent(this,ActivityWD04.class);
                startActivity(main);
                finish();
                break;
            }//end case 44 ActivityWD04
            case 45:{
                appData.setCurrentActivityName("ActivityWD05");
                Intent main = new Intent(this,ActivityWD05.class);
                startActivity(main);
                finish();
                break;
            }//end case 45 ActivityWD05
            case 46:{
                appData.setCurrentActivityName("ActivityWD06");
                Intent main = new Intent(this,ActivityWD06.class);
                startActivity(main);
                finish();
                break;
            }//end case 46 ActivityWD06
            case 47:{
                mCurrentGSP.put("phase_id","2");
                appData.setCurrentGroup_Section_Phase(mCurrentGSP.get("group_id"), mCurrentGSP.get("phase_id"),
                        mCurrentGSP.get("section_id"), mCurrentGSP.get("current_level"));
                appData.setCurrentActivityName("ActivityRS04");
                Intent main = new Intent(this,ActivityRS04.class);
                startActivity(main);
                finish();
                break;
            }//end case 46 ActivityWD06
            case 54:{
                appData.setCurrentActivityName("ActivitySH01");
                Intent main = new Intent(this,ActivitySH01.class);
                startActivity(main);
                finish();
                break;
            }
            case 55:{
                appData.setCurrentActivityName("ActivitySH02");
                Intent main = new Intent(this,ActivitySH02.class);
                startActivity(main);
                finish();
                break;
            }
            case 56:{
                appData.setCurrentActivityName("ActivitySH03");
                Intent main = new Intent(this,ActivitySH03.class);
                startActivity(main);
                finish();
                break;
            }
            case 57:{
                appData.setCurrentActivityName("ActivitySH04");
                Intent main = new Intent(this,ActivitySH04.class);
                startActivity(main);
                finish();
                break;
            }
            case 58:{
                appData.setCurrentActivityName("ActivitySH05");
                Intent main = new Intent(this,ActivitySH05.class);
                startActivity(main);
                finish();
                break;
            }
            case 59:{
                appData.setCurrentActivityName("ActivitySH06");
                Intent main = new Intent(this,ActivitySH06.class);
                startActivity(main);
                finish();
                break;
            }
            case 48:{
                appData.setCurrentActivityName("ActivityQN01");
                Intent main = new Intent(this,ActivityQN01.class);
                startActivity(main);
                finish();
                break;
            }
            case 49:{
                appData.setCurrentActivityName("ActivityQN02");
                Intent main = new Intent(this,ActivityQN02.class);
                startActivity(main);
                finish();
                break;
            }
            case 50:{
                appData.setCurrentActivityName("ActivityQN03");
                Intent main = new Intent(this,ActivityQN03.class);
                startActivity(main);
                finish();
                break;
            }
            case 51:{
                appData.setCurrentActivityName("ActivityQN04");
                Intent main = new Intent(this,ActivityQN04.class);
                startActivity(main);
                finish();
                break;
            }
            case 52:{
                appData.setCurrentActivityName("ActivityQN05");
                Intent main = new Intent(this,ActivityQN05.class);
                startActivity(main);
                finish();
                break;
            }
            case 53:{
                appData.setCurrentActivityName("ActivityQN06");
                Intent main = new Intent(this,ActivityQN06.class);
                startActivity(main);
                finish();
                break;
            }

            case 60:{
                appData.setCurrentActivityName("ActivityOP01");
                Intent main = new Intent(this,ActivityOP01.class);
                startActivity(main);
                finish();
                break;
            }
            case 61:{
                /*
                appData.setCurrentActivityName("ActivityOP02");
                Intent main = new Intent(this,ActivityOP02.class);
                 */
                appData.setCurrentActivityName("ActivityOP08");
                Intent main = new Intent(this,ActivityOP08.class);
                startActivity(main);
                finish();
                break;
            }
            case 62:{
                appData.setCurrentActivityName("ActivityOP03");
                Intent main = new Intent(this,ActivityOP03.class);
                startActivity(main);
                finish();
                break;
            }
            case 63:{
                appData.setCurrentActivityName("ActivityOP04");
                Intent main = new Intent(this,ActivityOP04.class);
                startActivity(main);
                finish();
                break;
            }
            case 64:{
                appData.setCurrentActivityName("ActivityOP05");
                Intent main = new Intent(this,ActivityOP05.class);
                startActivity(main);
                finish();
                break;
            }
            case 65:{
                appData.setCurrentActivityName("ActivityOP06");
                Intent main = new Intent(this,ActivityOP06.class);
                startActivity(main);
                finish();
                break;
            }
            case 67:{
                appData.setCurrentActivityName("ActivityQN07");
                Intent main = new Intent(this,ActivityQN07.class);
                startActivity(main);
                finish();
                break;
            }

        }//end switch(clickedActivityID){

    }



}
