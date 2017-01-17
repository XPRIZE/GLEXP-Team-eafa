package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import java.util.ArrayList;
import java.util.HashMap;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

public class Launch_Platform extends Master_Parent implements
LoaderManager.LoaderCallbacks<Cursor>{

    private boolean mPlayingVideo=false;
    private boolean mLoaded;

    private boolean mInSettings;

    private int mUpdatingLevel;

    private VideoView mVideoView;
    private ArrayList<HashMap<String,String>> mLevelList;

    GridView sectionGrid;
    GridView mAwardsGrid;

    private HashMap<String,String> mAllMaxLevels;
    private HashMap<String,String> mAllCurrentLevels;

    ArrayList<HashMap<String, String>> mAwards;
    ArrayList<HashMap<String,String>> allSections;
    ArrayList<ArrayList<String>> allUserGroupPhaseInfo;
    private Handler waitHandler = new Handler();
    private HashMap list;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.launch_platform);
        appData.setClassesForLaunch();

        appData.setActivityType(1);
        mLoaded=false;
        mInSettings=false;
        int mUpdateType=appData.getUpdateType();
        if(mUpdateType==0) {
            findViewById(R.id.progressFrame).setVisibility(View.VISIBLE);
            findViewById(R.id.sidePanelIcon).setVisibility(View.INVISIBLE);
            findViewById(R.id.sidePanelWaiting).setVisibility(View.VISIBLE);
        }
        findViewById(R.id.settingsPage).setVisibility(View.INVISIBLE);
        findViewById(R.id.videoPage).setVisibility(View.INVISIBLE);
        findViewById(R.id.awardsPage).setVisibility(View.INVISIBLE);
        findViewById(R.id.theVideo).setVisibility(RelativeLayout.INVISIBLE);

        mVideoView = (VideoView)findViewById(R.id.videoView);

        getLoaderManager().initLoader(Constants.UPDATE_LEVELS, null, this);


        findViewById(R.id.btnSections).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                findViewById(R.id.sectionsPage).setVisibility(View.VISIBLE);
                findViewById(R.id.settingsPage).setVisibility(View.INVISIBLE);
                findViewById(R.id.videoPage).setVisibility(View.INVISIBLE);
                findViewById(R.id.awardsPage).setVisibility(View.INVISIBLE);
                mInSettings=false;
            }
        });

        findViewById(R.id.btnVideos).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                findViewById(R.id.sectionsPage).setVisibility(View.INVISIBLE);
                findViewById(R.id.settingsPage).setVisibility(View.INVISIBLE);
                findViewById(R.id.videoPage).setVisibility(View.VISIBLE);
                findViewById(R.id.awardsPage).setVisibility(View.INVISIBLE);
                findViewById(R.id.theVideo).setVisibility(FrameLayout.INVISIBLE);
                mInSettings=false;

            }
        });

        findViewById(R.id.btnAwards).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                findViewById(R.id.sectionsPage).setVisibility(View.INVISIBLE);
                findViewById(R.id.settingsPage).setVisibility(View.INVISIBLE);
                findViewById(R.id.videoPage).setVisibility(View.INVISIBLE);
                findViewById(R.id.awardsPage).setVisibility(View.VISIBLE);
                mInSettings=false;
                startAwards();
            }
        });

        findViewById(R.id.btnSettings).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                findViewById(R.id.sectionsPage).setVisibility(View.INVISIBLE);
                findViewById(R.id.settingsPage).setVisibility(View.VISIBLE);
                findViewById(R.id.videoPage).setVisibility(View.INVISIBLE);
                findViewById(R.id.awardsPage).setVisibility(View.INVISIBLE);
                if(mLoaded) {
                    mInSettings=true;
                    startSettings();
                }
            }
        });

        groupVideos();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void groupVideos(){


        /**
         * videos for intro
         */


        findViewById(R.id.motoliVideo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingVideo) {
                    findViewById(R.id.theVideo).setVisibility(FrameLayout.VISIBLE);
                    findViewById(R.id.videoPage).setVisibility(LinearLayout.INVISIBLE);
                    Uri mVideoUri= Uri.parse("android.resource://" + getPackageName() + "/"
                            + R.raw.group_video_intro);
                    mPlayingVideo=true;
                    mVideoView.setVisibility(VideoView.VISIBLE);
                    mVideoView.setMediaController(null);
                    mVideoView.setVideoURI(mVideoUri);
                    mVideoView.requestFocus();
                    mVideoView.seekTo(10);
                    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            mVideoView.setZOrderOnTop(false);
                            mVideoView.setBackgroundColor(Color.TRANSPARENT);
                            long mAudioDuration = mp.getDuration();
                            mAudioHandler.postDelayed(runAfterVideo, mAudioDuration + 500);
                        }
                    });
                }
            }
        });


        findViewById(R.id.readVideo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingVideo) {

                    findViewById(R.id.theVideo).setVisibility(FrameLayout.VISIBLE);
                    findViewById(R.id.videoPage).setVisibility(LinearLayout.INVISIBLE);
                    Uri mVideoUri= Uri.parse("android.resource://" + getPackageName() + "/"
                            + R.raw.group_video_reading);
                    mPlayingVideo=true;
                    mVideoView.setVisibility(VideoView.VISIBLE);
                    mVideoView.setMediaController(null);
                    mVideoView.setVideoURI(mVideoUri);
                    mVideoView.requestFocus();
                    mVideoView.seekTo(10);
                    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            mVideoView.setZOrderOnTop(false);
                            mVideoView.setBackgroundColor(Color.TRANSPARENT);
                            long mAudioDuration = mp.getDuration();
                            mAudioHandler.postDelayed(runAfterVideo, mAudioDuration + 500);
                        }
                    });

                }
            }
        });


        findViewById(R.id.numbersVideo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingVideo) {

                    findViewById(R.id.theVideo).setVisibility(FrameLayout.VISIBLE);
                    findViewById(R.id.videoPage).setVisibility(LinearLayout.INVISIBLE);
                    Uri mVideoUri= Uri.parse("android.resource://" + getPackageName() + "/"
                            + R.raw.group_video_number);
                    mPlayingVideo=true;
                    mVideoView.setVisibility(VideoView.VISIBLE);
                    mVideoView.setMediaController(null);
                    mVideoView.setVideoURI(mVideoUri);
                    mVideoView.requestFocus();
                    mVideoView.seekTo(10);
                    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            mVideoView.setZOrderOnTop(false);
                            mVideoView.setBackgroundColor(Color.TRANSPARENT);
                            long mAudioDuration = mp.getDuration();
                            mAudioHandler.postDelayed(runAfterVideo, mAudioDuration + 500);
                        }
                    });

                }
            }
        });


        findViewById(R.id.tracingVideo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!mPlayingVideo) {

                    findViewById(R.id.theVideo).setVisibility(FrameLayout.VISIBLE);
                    findViewById(R.id.videoPage).setVisibility(LinearLayout.INVISIBLE);
                    Uri mVideoUri= Uri.parse("android.resource://" + getPackageName() + "/"
                            + R.raw.group_video_tracing);
                    mPlayingVideo=true;
                    mVideoView.setVisibility(VideoView.VISIBLE);
                    mVideoView.setMediaController(null);
                    mVideoView.setVideoURI(mVideoUri);
                    mVideoView.requestFocus();
                    mVideoView.seekTo(10);
                    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            mVideoView.setZOrderOnTop(false);
                            mVideoView.setBackgroundColor(Color.TRANSPARENT);
                            long mAudioDuration = mp.getDuration();
                            mAudioHandler.postDelayed(runAfterVideo, mAudioDuration + 500);
                        }
                    });

                }
            }
        });
     /*   */

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result =true;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(mPlayingVideo){
                mVideoView.stopPlayback();
                mAudioHandler.postDelayed(runAfterVideo, 10);

            }else {
                moveBackwords();
            }
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            result=false;
            // DebugLog.d(TAG, "volume up");
        }else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            result=false;
            // DebugLog.d(TAG, "volume down");
        }

        return result;
    }



    /////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable runAfterVideo = new Runnable(){
        @Override
        public void run(){

            findViewById(R.id.theVideo).setVisibility(FrameLayout.INVISIBLE);
            findViewById(R.id.videoPage).setVisibility(LinearLayout.VISIBLE);
            mPlayingVideo = false;

            /*  */
        }
    };
    /////////////////////////////////////////////////////////////////////////////////////////////

    private void startAwards(){
        getLoaderManager().restartLoader(Constants.SECTION_AWARDS_LOADER, null, this);

    }

    private void startSettings(){
        getLoaderManager().restartLoader(Constants.ALL_MAX_LEVELS, null, this);
    }

    public void resetLevels(View view){
        findViewById(R.id.allSpinners).setVisibility(LinearLayout.INVISIBLE);
        findViewById(R.id.btnVideos).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.btnSections).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.btnAwards).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.btnSettings).setVisibility(ImageView.INVISIBLE);

        findViewById(R.id.spinnerWaiting).setVisibility(RelativeLayout.VISIBLE);

        getLoaderManager().restartLoader(Constants.RESET_LEVELS, null, this);

    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    public void onPause(){
        System.gc();
        super.onPause();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////


    private void displayScreen(Cursor mCursor){
        if(mInSettings){
            findViewById(R.id.sectionsPage).setVisibility(View.INVISIBLE);
            findViewById(R.id.settingsPage).setVisibility(View.VISIBLE);
            findViewById(R.id.videoPage).setVisibility(View.INVISIBLE);
            findViewById(R.id.awardsPage).setVisibility(View.INVISIBLE);
            if(mLoaded) {
                mInSettings=true;
                startSettings();
            }
        }else {
            findViewById(R.id.videoPage).setVisibility(View.INVISIBLE);
            findViewById(R.id.awardsPage).setVisibility(View.INVISIBLE);
            findViewById(R.id.sectionsPage).setVisibility(View.VISIBLE);
        }
        Log.d(Constants.LOGCAT, "start displayScreen");

        allSections=new ArrayList<>();

        int currentGroup=0;
        int groupCount=0;

        if (mCursor.moveToFirst()) {
            int mNumber=0;
            for (int j = 0; j < mCursor.getCount(); j++) {
                groupCount++;
                int presentGroup=mCursor.getInt(mCursor.getColumnIndex("group_id"));
                if(currentGroup==0)
                    currentGroup=presentGroup;

                Log.d(Constants.LOGCAT, String.valueOf(groupCount%3)+"...");



                    if(mCursor.getString(mCursor.getColumnIndex("section_id")).equals("13")
                            /*
                            || mCursor.getString(mCursor.getColumnIndex("section_id")).equals("25")
                            || mCursor.getString(mCursor.getColumnIndex("section_id")).equals("26")*/){
                        allSections.add(new HashMap<String, String>());

                        allSections.get(mNumber).put("section_id","0");
                        allSections.get(mNumber).put("group_id",String.valueOf(currentGroup));
                        allSections.get(mNumber).put("section_name","");
                        allSections.get(mNumber).put("section_image","xpsec_blank.png");
                        allSections.get(mNumber).put("lowest_phase_id","");
                        allSections.get(mNumber).put("app_user_current_level","");
                        allSections.get(mNumber).put("activity_count","0");
                        allSections.get(mNumber).put("variable_count","0");
                        allSections.get(mNumber).put("usable_section,","0");

                        mNumber++;
                        //groupCount++;
                    }
                   //}
                //}
                if(groupCount>2 || currentGroup!=presentGroup ){
                    groupCount=1;
                }

                currentGroup=mCursor.getInt(mCursor.getColumnIndex("group_id"));
                allSections.add(new HashMap<String, String>());

                allSections.get(mNumber).put("section_id",
                        mCursor.getString(mCursor.getColumnIndex("section_id"))); 		        //0
                allSections.get(mNumber).put("group_id",
                        String.valueOf(currentGroup));		                                    //1
                allSections.get(mNumber).put("section_name",
                        mCursor.getString(mCursor.getColumnIndex("section_name")));		        //2
                allSections.get(mNumber).put("section_image",
                        mCursor.getString(mCursor.getColumnIndex("section_image")));	        //3
                allSections.get(mNumber).put("lowest_phase_id",
                        mCursor.getString(mCursor.getColumnIndex("lowest_phase_id")));		        //4
                allSections.get(mNumber).put("app_user_current_level",
                        mCursor.getString(mCursor.getColumnIndex("app_user_current_level")));   //5
                allSections.get(mNumber).put("activity_count",
                        mCursor.getString(mCursor.getColumnIndex("activity_count")));	        //6
                allSections.get(mNumber).put("variable_count",
                        mCursor.getString(mCursor.getColumnIndex("variable_count")));	        //7
                allSections.get(mNumber).put("usable_section",
                        mCursor.getString(mCursor.getColumnIndex("usable_section")));	        //8

                mNumber++;
                mCursor.moveToNext();
            }
        }

        if(Integer.parseInt(allSections.get(7).get("variable_count"))<4){
            allSections.get(6).put("usable_section","0");
        }

        if(Integer.parseInt(allSections.get(5).get("variable_count"))<=8){
            allSections.get(4).put("usable_section","0");
        }

        if(Integer.parseInt(allSections.get(9).get("usable_section"))>0){
            allSections.get(8).put("usable_section","1");
        }


        sectionGrid = (GridView) findViewById(R.id.SectionGrid);

        sectionGrid.setAdapter(new Launch_Platform_Section(this, allSections));
        int mRow;
        switch (appData.getCurrentSection()){
            default:
            case 0:
            case 1:
            case 2:
            case 3:{
                mRow=3;
                break;
            }
            case 4:
            case 5:
            case 6:{
                mRow=5;
                break;
            }
            case 7:
            case 8:
            case 9:{
                mRow=7;
                break;
            }
            case 10:
            case 11:
            case 12:{
                mRow=9;
                break;
            }
            case 13:
            case 14:{
                mRow=11;
                break;
            }
            case 15:{
                mRow=13;
                break;
            }
            case 16:
            case 17:
            case 18:{
                mRow=15;
                break;
            }
            case 19:
            case 20:
            case 21:{
                mRow=17;
                break;
            }
            case 22:
            case 23:
            case 24:{
                mRow=19;
                break;
            }
            case 25:
            case 26:
            case 27:{
                mRow=21;
                break;
            }
        }
        sectionGrid.setSmoothScrollbarEnabled(true);
        sectionGrid.smoothScrollToPosition(mRow);
        Log.d(Constants.LOGCAT, "end displayScreen Launch_Platform");
        mLoaded=true;
        sectionGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                list = (HashMap) v.findViewById(R.id.tracingImage).getTag();

                String mSectionID = (String) list.get("section_id");
                String mActivityCount = (String) list.get("activity_count");
                String mCurrentUserLevel = (String) list.get("app_user_current_level");
                String mCurrentVariableCount = (String) list.get("variable_count");

                appData.setCurrentSection(Integer.parseInt(mSectionID));

                if (Integer.parseInt((String) list.get("usable_section")) != 0) {
                    if (mSectionID.equals("17") ||
                            mSectionID.equals("20") ||
                            mSectionID.equals("23") ||
                            mSectionID.equals("8")) {
                        move_to_activities((String) list.get("group_id"),
                                (String) list.get("lowest_phase_id"),
                                (String) list.get("section_id"),
                                mCurrentUserLevel);

                    } else if (mActivityCount.equals("1") &&
                            mCurrentVariableCount.equals("0")) {
                        process_single_activity_section();

                    } else if (Integer.parseInt(mActivityCount) > 1 &&
                            Integer.parseInt(mCurrentVariableCount) >= 1) {
                        move_to_activities((String) list.get("group_id"),
                                (String) list.get("lowest_phase_id"),
                                (String) list.get("section_id"),
                                mCurrentUserLevel);
                    }
                }


            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////

   private void displayAwards(Cursor mCursor){
       findViewById(R.id.awardsPage).setVisibility(LinearLayout.VISIBLE);
       findViewById(R.id.sectionsPage).setVisibility(LinearLayout.INVISIBLE);

       Log.d(Constants.LOGCAT, "start displayAwards");
       mAwards=new ArrayList<>();
       if (mCursor.moveToFirst()) {
           int mNumber=0;
           do{
               mAwards.add(new HashMap<String, String>());
               mAwards.get(mNumber).put("group_id",
                       mCursor.getString(mCursor.getColumnIndex("group_id"))); //1
               mAwards.get(mNumber).put("phase_id",
                       mCursor.getString(mCursor.getColumnIndex("phase_id"))); //2
               if(mCursor.getString(mCursor.getColumnIndex("correct_count"))!=null) {
                   mAwards.get(mNumber).put("correct_count",
                           mCursor.getString(mCursor.getColumnIndex("correct_count")));
               }else{
                   mAwards.get(mNumber).put("correct_count","0");
               }
               if(mCursor.getString(mCursor.getColumnIndex("max_correct_count"))!=null) {
                   mAwards.get(mNumber).put("max_correct_count",
                           mCursor.getString(mCursor.getColumnIndex("max_correct_count")));
               }else{
                   mAwards.get(mNumber).put("max_correct_count","0");
               }
               mAwards.get(mNumber).put("variable_number",
                       mCursor.getString(mCursor.getColumnIndex("variable_number"))); //4
               mAwards.get(mNumber).put("groups_phase_awards_image",
                       mCursor.getString(mCursor.getColumnIndex("groups_phase_awards_image"))); //5
               mAwards.get(mNumber).put("section_id",
                       mCursor.getString(mCursor.getColumnIndex("section_id"))); //5
               mAwards.get(mNumber).put("current_level_number",
                       mCursor.getString(mCursor.getColumnIndex("current_level_number")));
               if(mCursor.getString(mCursor.getColumnIndex("max_level_number"))!=null) {
                   mAwards.get(mNumber).put("max_level_number",
                           mCursor.getString(mCursor.getColumnIndex("max_level_number")));
               }else{
                   mAwards.get(mNumber).put("max_level_number","15");
               }
               mNumber++;
           }while(mCursor.moveToNext());
       }


       mAwardsGrid = (GridView) findViewById(R.id.awardsGrid);


       mAwardsGrid.setAdapter(new LaunchPlatformAwards(this, mAwards));

       mAwardsGrid.setOnItemClickListener(new OnItemClickListener() {
           public void onItemClick(AdapterView<?> parent, View v,
                                   int position, long id) {
              /*
              mListBundle = (Bundle) v.findViewById(R.id.sectionImage).getTag();
               String mSectionID = (String) mListBundle.getString("section_id");
              // String mActivityCount = (String) mListBundle.getString(6);
               String mCurrentUserLevel = (String) mListBundle.getString(5);
               String mCurrentVariableCount = (String) mListBundle.getString(7);
               if (Integer.parseInt((String) list.get("usable_section")) != 0) {
                   if (mSectionID.equals("17") ||
                           mSectionID.equals("20") ||
                           mSectionID.equals("23")) {
                       move_to_activities((String) list.get("group_id"), (String) list.get("lowest_phase_id"),
                               (String) list.get("section_id"), mCurrentUserLevel);
                   } else if (Integer.parseInt(mActivityCount) > 1 &&
                           Integer.parseInt(mCurrentVariableCount) >= 4) {
                       move_to_activities((String) list.get("group_id"), (String) list.get("lowest_phase_id"),
                               (String) list.get("section_id"), mCurrentUserLevel);
                   }
               }

*/
//move_to_activities(String group_id, String phase_id, String section_id, String current_level)
           }
       });
   } 
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void process_single_activity_section(){
        int currentGroup=Integer.parseInt((String)list.get("group_id"));
        int currentPhase=Integer.parseInt((String) list.get("lowest_phase_id"));
        int currentSection = Integer.parseInt((String)list.get("section_id"));

        Bundle bundle = new Bundle();
        bundle.putInt("group_id", currentGroup);
         bundle.putInt("phase_id", currentPhase);
         bundle.putInt("section_id", currentSection);


         getLoaderManager().restartLoader(Constants.ACTIVITIES_LOADER, bundle, this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void goto_activity(String activity_id){

        int clickedActivityID = Integer.parseInt(activity_id);

        ArrayList<String> currentActivity = new ArrayList<String>();

        currentActivity.add(String.valueOf(clickedActivityID));

        appData.setCurrentActivity(currentActivity);


        appData.setCurrentGroup_Section_Phase((String) list.get("group_id"), (String) list.get("lowest_phase_id"),
                (String) list.get("section_id"), (String) list.get("app_user_current_level"));
        switch(clickedActivityID){
            default:
                break;
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
            }//end case 22 LT01
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

            case 28:{
                appData.setCurrentVideoID(2);
                appData.setCurrentActivityName("ActivityVL");
                //02
                Intent main = new Intent(this,ActivityVL.class);
                startActivity(main);
                finish();
                break;
            }
            case 37:
            {
                appData.setCurrentActivityName("ActivityBK02");

                Intent main = new Intent(this,ActivityBK02BookList.class);
                startActivity(main);
                finish();
                break;
            }
            case 39:{
                appData.setCurrentVideoID(4);
                appData.setCurrentActivityName("ActivityVL");
                //04
                Intent main = new Intent(this,ActivityVL.class);
                startActivity(main);
                finish();
                break;
            }
            case 21:{
                appData.setCurrentVideoID(3);
                appData.setCurrentActivityName("ActivityVL");
                //03
                Intent main = new Intent(this,ActivityVL.class);
                startActivity(main);
                finish();
                break;
            }
            case 36:{
                appData.setCurrentActivityName("ActivityBK03");

                Intent main = new Intent(this,ActivityBK03Books.class);
                startActivity(main);
                finish();

                break;
            }
            case 66:{
                appData.setCurrentVideoID(5);
                appData.setCurrentActivityName("ActivityVL");
                Intent main = new Intent(this,ActivityVL.class);
                startActivity(main);
                finish();
                break;
            }
            case 38:{
                appData.setCurrentVideoID(6);
                appData.setCurrentActivityName("ActivityVL");
                Intent main = new Intent(this,ActivityVL.class);
                startActivity(main);
                finish();
                break;
            }
            case 68:{
                appData.setCurrentActivityName("ActivityTR02");
                Intent main = new Intent(this,ActivityTR02.class);
                startActivity(main);
                finish();
                break;
            }//end case 22 LT01
            case 69:{
                appData.setCurrentActivityName("ActivityTR02");
                Intent main = new Intent(this,ActivityTR02.class);
                startActivity(main);
                finish();
                break;
            }//end case 22 LT01
            case 70:{
                appData.setCurrentVideoID(7);
                appData.setCurrentActivityName("ActivityVL");
                Intent main = new Intent(this,ActivityVL.class);
                startActivity(main);
                finish();
                break;
            }//end case 70 vl
            case 71:{
                appData.setCurrentVideoID(8);
                appData.setCurrentActivityName("ActivityVL");
                Intent main = new Intent(this,ActivityVL.class);
                startActivity(main);
                finish();
                break;
            }//end case 71 vl
            case 72:{
                appData.setCurrentActivityName("ActivityTR01");
                Intent main = new Intent(this,ActivityTR01.class);
                startActivity(main);
                finish();
                break;
            }//end case 72 vl
            case 73:{
                appData.setCurrentVideoID(9);
                appData.setCurrentActivityName("ActivityVL");
                Intent main = new Intent(this,ActivityVL.class);
                startActivity(main);
                finish();
                break;
            }//end case 72 vl
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////

    private void move_to_activities(String group_id, String phase_id,
                                    String section_id, String current_level){
        if(group_id.equals("2") && phase_id.equals("3")){
            appData.setCurrentVideoID(3);
        }else if(group_id.equals("2")){
            appData.setCurrentVideoID(2);
        }else if(group_id.equals("3")){
            appData.setCurrentVideoID(4);
        }else if(group_id.equals("4")){
            appData.setCurrentVideoID(5);
        }else if(group_id.equals("5")){
            appData.setCurrentVideoID(6);
        }else if(group_id.equals("6")){
            appData.setCurrentVideoID(7);
        }else if(group_id.equals("7")){
            appData.setCurrentVideoID(8);
        }else if(group_id.equals("8")){
            appData.setCurrentVideoID(9);
        }else if(group_id.equals("9")){
            appData.setCurrentVideoID(10);
        }else{
            appData.setCurrentVideoID(28);
        }
        appData.setCurrentGroup_Section_Phase(group_id, phase_id, section_id, current_level);
        appData.setCurrentActivityName("Activities_Platform");
        Intent main = new Intent(this,Activities_Platform.class);
        startActivity(main);
        finish();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.SECTIONS_LOADER:{
                projection = new String[] {
                        "group_id",
                        "phase_id",
                        "section_id",
                        "section_image",
                        "section_name"};
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_INDIVIDUAL_GROUP_PHASE_SECTIONS, projection,
                        appData.getCurrentUserID(), null, null);
                break;
            }

            case Constants.RESET_LEVELS:
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_RESET_LEVELS,
                        null, appData.getCurrentUserID(), null, null);
                break;
            case Constants.UPDATE_SEPARATE_LEVELS_FULL:


                projection = new String[]{
                        args.getString("0"),args.getString("1"),args.getString("2"),
                        args.getString("3"),args.getString("4")};

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_UPDATE_DIFFERENT_SECTIONS, projection,
                        null, null, null);
                break;
            case Constants.UPDATE_SEPARATE_LEVELS:


                projection = new String[]{
                        args.getString("0"),args.getString("1"),args.getString("2"),
                        args.getString("3"),args.getString("4")};

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_UPDATE_DIFFERENT_SECTIONS, projection,
                        null, null, null);
                break;
            case Constants.ALL_MAX_LEVELS:
                cursorLoader = new CursorLoader(this, AppProvider.CONTENT_ALL_MAX_LEVELS, null,
                        appData.getCurrentUserID(), null, null);
                break;
            case Constants.SECTION_AWARDS_LOADER:{
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_SECTION_AWARDS, null,
                        appData.getCurrentUserID(), null, null);
                break;
            }
            case Constants.ACTIVITIES_LOADER:{
                projection = new String[] {
                        "gpsa_id",
                        "group_id",
                        "phase_id",
                        "section_id",
                        "activity_id",
                        "activity_name",
                        "activity_img"};

                String selection="groups_phase_sections_activities.group_id="+args.getInt("group_id")
                        +" AND groups_phase_sections_activities.phase_id="+args.getInt("phase_id")
                        +" AND groups_phase_sections_activities.section_id="+args.getInt("section_id");
                String order="activities.activity_order ASC";
                cursorLoader = new CursorLoader(this, AppProvider.CONTENT_URI_SELECTED_ACTIVITIES,
                        projection, selection, null, order);
                break;
            }
            case Constants.UPDATE_LEVELS:{

                int mUpdateType=appData.getUpdateType();
                if(mUpdateType==1){
                    mUpdateType=2;
                }
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_GROUPS_PHASE_LEVELS_UPDATE, null,
                        appData.getCurrentUserID(), null, String.valueOf(mUpdateType));
                if(mUpdateType==0){
                    appData.setUpdateType(1);
                }
                break;
            }

        }

        return cursorLoader;
    }

    public void submitAdminPassword(View view){
        if(
        appData.inputAdminPassword(
                ((EditText)findViewById(R.id.adminPassword)).getText().toString())){
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            findViewById(R.id.spinnerPassword).setVisibility(LinearLayout.INVISIBLE);
            findViewById(R.id.allSpinners).setVisibility(LinearLayout.VISIBLE);
        }else{
            findViewById(R.id.spinnerPassword).setVisibility(LinearLayout.VISIBLE);
            findViewById(R.id.allSpinners).setVisibility(LinearLayout.INVISIBLE);
            findViewById(R.id.incorrectPassword).setAlpha(1.0f);
            ((EditText)findViewById(R.id.adminPassword)).setText("");
        }
    }

    private void processSettings(Cursor mCursor){
        if(appData.isAdminEnabled()){
            findViewById(R.id.spinnerPassword).setVisibility(LinearLayout.INVISIBLE);
            findViewById(R.id.allSpinners).setVisibility(LinearLayout.VISIBLE);
        }else{
            findViewById(R.id.spinnerPassword).setVisibility(LinearLayout.VISIBLE);
            findViewById(R.id.allSpinners).setVisibility(LinearLayout.INVISIBLE);
        }
        findViewById(R.id.spinnerWaiting).setVisibility(LinearLayout.INVISIBLE);
        mAllMaxLevels=new HashMap<>();
        mAllCurrentLevels=new HashMap<>();
        mInSettings=true;
        mLevelList = new ArrayList<>();
        if(mCursor.moveToFirst()){
            int mCount=0;
            do{
                mLevelList.add(new HashMap<String,String>());
                mLevelList.get(mCount).put("max_level_number",
                        mCursor.getString(mCursor.getColumnIndex("max_level_number")));
                mLevelList.get(mCount).put("group_id",
                        mCursor.getString(mCursor.getColumnIndex("group_id")));
                mLevelList.get(mCount).put("phase_id",
                        mCursor.getString(mCursor.getColumnIndex("phase_id")));
                mLevelList.get(mCount).put("current_level",
                        mCursor.getString(mCursor.getColumnIndex("app_user_current_level")));
                mCount++;
            }while(mCursor.moveToNext());
        }
        boolean mLowerCaseFinished=false;
        for (HashMap<String,String> mCurrentLevel :mLevelList ) {
            Spinner mSpinner;
            ArrayList<String> mLevelsArray;
            ArrayAdapter<String> mAdapter;
            switch (Integer.parseInt(mCurrentLevel.get("group_id"))){
                default:
                    break;
                case 2:
                    if(mCurrentLevel.get("phase_id").equals("1")) {
                        mAllMaxLevels.put("lower",mCurrentLevel.get("max_level_number"));
                        mAllCurrentLevels.put("lower",mCurrentLevel.get("current_level"));
                        mSpinner = (Spinner) findViewById(R.id.lowerCaseLevels);
                        mLevelsArray = new ArrayList<>();
                        for (int i = Integer.parseInt(mCurrentLevel.get("current_level"));
                             i <= Integer.parseInt(mCurrentLevel.get("max_level_number"));
                             i++) {
                            mLevelsArray.add(String.valueOf(i));
                        }
                        mSpinner.setAlpha(1.0f);
                        findViewById(R.id.lowerCaseSubmit).setVisibility(Button.VISIBLE);
                        if (Integer.parseInt(mCurrentLevel.get("current_level"))
                                >= Integer.parseInt(mCurrentLevel.get("max_level_number"))) {
                            mLowerCaseFinished = true;
                            mSpinner.setAlpha(0.5f);
                            mLevelsArray.add(mCurrentLevel.get("max_level_number"));
                            findViewById(R.id.lowerCaseSubmit).setVisibility(Button.INVISIBLE);
                        } else {
                            mSpinner.setAlpha(1.0f);
                            findViewById(R.id.lowerCaseSubmit).setVisibility(Button.VISIBLE);
                            mLowerCaseFinished = false;
                        }
                        mAdapter = new ArrayAdapter<>(this,
                                R.layout.spinner_text, mLevelsArray);
                        mAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
                        mSpinner.setAdapter(mAdapter);
                    }else if(mCurrentLevel.get("phase_id").equals("2")) {
                        mAllMaxLevels.put("upper",mCurrentLevel.get("max_level_number"));
                        mAllCurrentLevels.put("upper",mCurrentLevel.get("current_level"));
                        mSpinner = (Spinner) findViewById(R.id.upperCaseLevels);
                        mLevelsArray = new ArrayList<>();
                        if (mLowerCaseFinished
                                && Integer.parseInt(mCurrentLevel.get("current_level"))
                                < (Integer.parseInt(mCurrentLevel.get("max_level_number")))) {
                            findViewById(R.id.upperCaseSubmit).setVisibility(Button.VISIBLE);
                            mSpinner.setAlpha(1.0f);
                            for (int i = Integer.parseInt(mCurrentLevel.get("current_level"));
                                 i <= (Integer.parseInt(mCurrentLevel.get("max_level_number")));
                                 i++) {
                                mLevelsArray.add(String.valueOf(i));
                            }
                        }else{
                            findViewById(R.id.upperCaseSubmit).setVisibility(Button.INVISIBLE);
                            mSpinner.setAlpha(0.5f);
                            mLevelsArray.add(mCurrentLevel.get("current_level"));
                        }
                        mAdapter = new ArrayAdapter<>(this,
                                    R.layout.spinner_text, mLevelsArray);
                        mAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
                        mSpinner.setAdapter(mAdapter);
                    }else if(mCurrentLevel.get("phase_id").equals("3")) {
                        mAllMaxLevels.put("phonics",mCurrentLevel.get("max_level_number"));
                        mAllCurrentLevels.put("phonics",mCurrentLevel.get("current_level"));
                        mSpinner = (Spinner) findViewById(R.id.phonicLevels);
                        mLevelsArray = new ArrayList<>();
                        if (mLowerCaseFinished
                                && Integer.parseInt(mCurrentLevel.get("current_level"))
                                < (Integer.parseInt(mCurrentLevel.get("max_level_number"))-3)) {
                            mSpinner.setAlpha(1.0f);
                            findViewById(R.id.phonicsSubmit).setVisibility(Button.VISIBLE);
                            for (int i = Integer.parseInt(mCurrentLevel.get("current_level"));
                                 i < (Integer.parseInt(mCurrentLevel.get("max_level_number"))-3);
                                 i++) {
                                mLevelsArray.add(String.valueOf(i));
                            }
                        }else{
                                findViewById(R.id.phonicsSubmit).setVisibility(Button.INVISIBLE);
                                mSpinner.setAlpha(0.5f);
                                mLevelsArray.add(mCurrentLevel.get("current_level"));
                        }
                        mAdapter = new ArrayAdapter<>(this,
                                R.layout.spinner_text, mLevelsArray);
                        mAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
                        mSpinner.setAdapter(mAdapter);
                    }
                    break;
                case 3:
                    if(mCurrentLevel.get("phase_id").equals("2")){
                        mSpinner = (Spinner) findViewById(R.id.syllableCountLevels);
                        mSpinner.setAlpha(1.0f);
                        mLevelsArray = new ArrayList<>();
                        for (int i = Integer.parseInt(mCurrentLevel.get("current_level"));
                             i <= Integer.parseInt(mCurrentLevel.get("max_level_number"));
                             i++) {
                            mLevelsArray.add(String.valueOf(i));
                        }
                        mAdapter = new ArrayAdapter<>(this,
                                R.layout.spinner_text, mLevelsArray);
                        mAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
                        mSpinner.setAdapter(mAdapter);
                    }else{
                        String mMaxSyllableLevel
                                =String.valueOf(Integer.parseInt(mAllCurrentLevels.get("phonics"))-1);

                        mAllMaxLevels.put("syllables",mMaxSyllableLevel);
                        mAllCurrentLevels.put("syllables",mCurrentLevel.get("current_level"));

                        mSpinner = (Spinner) findViewById(R.id.syllableLevels);
                        mLevelsArray = new ArrayList<>();
                        findViewById(R.id.syllablesSubmit).setVisibility(Button.VISIBLE);
                        if (mLowerCaseFinished
                                && Integer.parseInt(mCurrentLevel.get("current_level"))
                                < Integer.parseInt(mMaxSyllableLevel)) {
                            mSpinner.setAlpha(1.0f);
                            for (int i = Integer.parseInt(mCurrentLevel.get("current_level"));
                                 i <= Integer.parseInt(mMaxSyllableLevel);
                                 i++) {
                                mLevelsArray.add(String.valueOf(i));
                            }
                        }else{
                            findViewById(R.id.syllablesSubmit).setVisibility(Button.INVISIBLE);
                            mSpinner.setAlpha(0.5f);
                            mLevelsArray.add("0");
                        }
                        mAdapter = new ArrayAdapter<>(this,
                                R.layout.spinner_text, mLevelsArray);
                        mAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
                        mSpinner.setAdapter(mAdapter);
                    }
                case 4:

                    String mMaxWordLevel
                            =String.valueOf(Integer.parseInt(mAllCurrentLevels.get("phonics"))-1);

                    mAllMaxLevels.put("words",mMaxWordLevel);
                    mAllCurrentLevels.put("words",mCurrentLevel.get("current_level"));

                    mSpinner = (Spinner) findViewById(R.id.wordLevels);
                    mLevelsArray = new ArrayList<>();
                    if (mLowerCaseFinished
                            && Integer.parseInt(mCurrentLevel.get("current_level"))
                            < Integer.parseInt(mMaxWordLevel)) {

                        findViewById(R.id.wordsSubmit).setVisibility(Button.VISIBLE);
                        mSpinner.setAlpha(1.0f);
                        for (int i = Integer.parseInt(mCurrentLevel.get("current_level"));
                             i <= Integer.parseInt(mMaxWordLevel);
                             i++) {
                            mLevelsArray.add(String.valueOf(i));
                        }
                    }else{
                        findViewById(R.id.wordsSubmit).setVisibility(Button.INVISIBLE);
                        mSpinner.setAlpha(0.5f);
                        mLevelsArray.add("0");
                    }
                    mAdapter = new ArrayAdapter<>(this,
                            R.layout.spinner_text, mLevelsArray);
                    mAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
                    mSpinner.setAdapter(mAdapter);
                    break;
                case 6:
                    mCurrentLevel.put("max_level_number","7");
                    mAllMaxLevels.put("shapes",mCurrentLevel.get("max_level_number"));
                    mAllCurrentLevels.put("shapes",mCurrentLevel.get("current_level"));

                    mSpinner = (Spinner) findViewById(R.id.shapeLevels);
                    mSpinner.setAlpha(1.0f);
                    mLevelsArray = new ArrayList<>();
                    if(!mCurrentLevel.get("current_level")
                            .equals(mCurrentLevel.get("max_level_number"))) {
                        mLevelsArray.add(mCurrentLevel.get("current_level"));
                    }else{
                        findViewById(R.id.shapesSubmit).setVisibility(Button.INVISIBLE);
                        mSpinner.setAlpha(0.5f);
                    }
                    mLevelsArray.add(mCurrentLevel.get("max_level_number"));

                    mAdapter = new ArrayAdapter<>(this,
                            R.layout.spinner_text, mLevelsArray);
                    mAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
                    mSpinner.setAdapter(mAdapter);
                    break;
                case 8:

                    mAllMaxLevels.put("math_operations",mCurrentLevel.get("max_level_number"));
                    mAllCurrentLevels.put("math_operations",mCurrentLevel.get("current_level"));


                    mSpinner = (Spinner) findViewById(R.id.mathOperationsLevels);
                    mSpinner.setAlpha(1.0f);
                    mLevelsArray = new ArrayList<>();
                    findViewById(R.id.mathOperationsSubmit).setVisibility(Button.VISIBLE);
                    if(Integer.parseInt(mCurrentLevel.get("current_level"))
                            < Integer.parseInt(mCurrentLevel.get("max_level_number"))) {
                        for (int i = Integer.parseInt(mCurrentLevel.get("current_level"));
                             i <= Integer.parseInt(mCurrentLevel.get("max_level_number"));
                             i++) {
                            mLevelsArray.add(String.valueOf(i));
                        }
                    }else{
                        findViewById(R.id.mathOperationsSubmit).setVisibility(Button.INVISIBLE);
                        mSpinner.setAlpha(0.5f);
                    }
                    mAdapter = new ArrayAdapter<>(this,
                            R.layout.spinner_text, mLevelsArray);
                    mAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
                    mSpinner.setAdapter(mAdapter);
                    break;
            }//end switch
        }//end for
    }

    //////////////////////////////////////////////////////////////////////////////////

    public void updateLevels(View view){
        if (view.getTag() instanceof String)
        {
            mUpdatingLevel=Integer.parseInt((String) view.getTag());
        } else if (view.getTag() instanceof Number)
        {
            mUpdatingLevel=((Number) view.getTag()).intValue();
        }else{
            mUpdatingLevel=0;
        }

        findViewById(R.id.allSpinners).setVisibility(LinearLayout.INVISIBLE);
        findViewById(R.id.btnVideos).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.btnSections).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.btnAwards).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.btnSettings).setVisibility(ImageView.INVISIBLE);

        findViewById(R.id.spinnerWaiting).setVisibility(RelativeLayout.VISIBLE);

        waitHandler.postDelayed(runLevelUpdates, 300);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable runLevelUpdates = new Runnable(){
        @Override
        public void run(){

            if(mUpdatingLevel==1){
                updateLowerCase();
            }else if(mUpdatingLevel==2){
                updateUpperCase();
            }else if(mUpdatingLevel==3){
                updatePhonics();
            }else if(mUpdatingLevel==4){
                updateSyllables();
            }else if(mUpdatingLevel==5){
                updateWords();
            }else if(mUpdatingLevel==6){
                updateShapes();
            }else if(mUpdatingLevel==7){
                updateMathOperations();
            }

        }
    };

    /////////////////////////////////////////////////////////////////////////////////

    private void updateUpperCase(){
        Spinner mSpinner = (Spinner) findViewById(R.id.upperCaseLevels);
        String mDesiredLevel=mSpinner.getSelectedItem().toString();

        Log.d(Constants.LOGCAT, "upper case level: " + mSpinner.getSelectedItem().toString());
        Bundle mInfo=new Bundle();

        mInfo.putString("0",appData.getCurrentUserID());
        mInfo.putString("1", "2");
        mInfo.putString("2", "2");
        mInfo.putString("3", mDesiredLevel);
        mInfo.putString("4", mAllCurrentLevels.get("upper"));


        getLoaderManager().restartLoader(Constants.UPDATE_SEPARATE_LEVELS, mInfo, this);

        /*
        String[] mSpinnerInfo = new String[]{
                appData.getCurrentUserID(),
                "2",
                "2",
                mDesiredLevel,
                mAllCurrentLevels.get("upper")
        };
        getContentResolver().update(
               AppProvider.CONTENT_URI_UPDATE_DIFFERENT_SECTIONS, null, null, mSpinnerInfo);
        */
    }

    private void updateWords(){
        Spinner mSpinner = (Spinner) findViewById(R.id.wordLevels);
        String mDesiredLevel=mSpinner.getSelectedItem().toString();

        Log.d(Constants.LOGCAT, "Word level: " + mSpinner.getSelectedItem().toString());
        Bundle mInfo=new Bundle();

        mInfo.putString("0",appData.getCurrentUserID());
        mInfo.putString("1", "4");
        mInfo.putString("2", "1");
        mInfo.putString("3", mDesiredLevel);
        mInfo.putString("4", mAllCurrentLevels.get("words"));


        getLoaderManager().restartLoader(Constants.UPDATE_SEPARATE_LEVELS, mInfo, this);
        /*
        String[] mSpinnerInfo = new String[]{
                appData.getCurrentUserID(),
                "4",
                "1",
                mDesiredLevel,
                mAllCurrentLevels.get("words")
        };
        getContentResolver().update(
                AppProvider.CONTENT_URI_UPDATE_DIFFERENT_SECTIONS, null, null, mSpinnerInfo);
          */
    }

    //////////////////////////////////////////////////////////////////////////////////

    private void updateShapes(){
        Spinner mSpinner = (Spinner) findViewById(R.id.shapeLevels);
        String mDesiredLevel=mSpinner.getSelectedItem().toString();

        Log.d(Constants.LOGCAT, "Shape level: " + mSpinner.getSelectedItem().toString());

        Bundle mInfo=new Bundle();

        mInfo.putString("0",appData.getCurrentUserID());
        mInfo.putString("1", "6");
        mInfo.putString("2", "1");
        mInfo.putString("3", mDesiredLevel);
        mInfo.putString("4", mAllCurrentLevels.get("shapes"));


        getLoaderManager().restartLoader(Constants.UPDATE_SEPARATE_LEVELS, mInfo, this);
        /*
        String[] mSpinnerInfo = new String[]{
                appData.getCurrentUserID(),
                "6",
                "1",
                mDesiredLevel,
                mAllCurrentLevels.get("shapes")
        };
        getContentResolver().update(
                AppProvider.CONTENT_URI_UPDATE_DIFFERENT_SECTIONS, null, null, mSpinnerInfo);
                */
    }

    ////////////////////////////////////////////////////////////////////////////////

    private void updateMathOperations(){
        String mDesiredLevel=((Spinner) findViewById(R.id.mathOperationsLevels))
                .getSelectedItem().toString();

        if(mDesiredLevel.equals("0")){
            startSettings();
        }else {
            Log.d(Constants.LOGCAT, "Math Operations level: " + mDesiredLevel);
            Bundle mInfo=new Bundle();

            mInfo.putString("0",appData.getCurrentUserID());
            mInfo.putString("1", "8");
            mInfo.putString("2", "1");
            mInfo.putString("3", mDesiredLevel);
            mInfo.putString("4", mAllCurrentLevels.get("math_operations"));


            getLoaderManager().restartLoader(Constants.UPDATE_SEPARATE_LEVELS, mInfo, this);

            /*
            String[] mSpinnerInfo = new String[]{
                    appData.getCurrentUserID(),
                    "8",
                    "1",
                    mDesiredLevel,
                    mAllCurrentLevels.get("math_operations")
            };
            getContentResolver().update(
                    AppProvider.CONTENT_URI_UPDATE_DIFFERENT_SECTIONS, null, null, mSpinnerInfo);
                    */
        }
    }

    //////////////////////////////////////////////////////////////////////////////

    private void updateSyllables(){
        Spinner mSpinner = (Spinner) findViewById(R.id.syllableLevels);
        String mDesiredLevel=mSpinner.getSelectedItem().toString();

        Log.d(Constants.LOGCAT, "syllable level: " + mSpinner.getSelectedItem().toString());
        Bundle mInfo=new Bundle();

        mInfo.putString("0",appData.getCurrentUserID());
        mInfo.putString("1", "3");
        mInfo.putString("2", "1");
        mInfo.putString("3", mDesiredLevel);
        mInfo.putString("4", mAllCurrentLevels.get("syllables"));


        getLoaderManager().restartLoader(Constants.UPDATE_SEPARATE_LEVELS, mInfo, this);
        /*
        String[] mSpinnerInfo = new String[]{
                appData.getCurrentUserID(),
                "3",
                "1",
                mDesiredLevel,
                mAllCurrentLevels.get("syllables")
        };
        getContentResolver().update(
                AppProvider.CONTENT_URI_UPDATE_DIFFERENT_SECTIONS, null, null, mSpinnerInfo);
        */
    }


    private void updatePhonics(){
        Spinner mSpinner = (Spinner) findViewById(R.id.phonicLevels);
        String mDesiredLevel=mSpinner.getSelectedItem().toString();

        Log.d(Constants.LOGCAT, "phonics level: " + mSpinner.getSelectedItem().toString());
        Bundle mInfo=new Bundle();

        mInfo.putString("0",appData.getCurrentUserID());
        mInfo.putString("1", "2");
        mInfo.putString("2", "3");
        mInfo.putString("3", mDesiredLevel);
        mInfo.putString("4", mAllCurrentLevels.get("phonics"));


        getLoaderManager().restartLoader(Constants.UPDATE_SEPARATE_LEVELS_FULL, mInfo, this);
        /*
        String[] mSpinnerInfo = new String[]{
                appData.getCurrentUserID(),
                "2",
                "3",
                mDesiredLevel,
                mAllCurrentLevels.get("phonics")
        };
        getContentResolver().update(
                AppProvider.CONTENT_URI_UPDATE_DIFFERENT_SECTIONS, null, null, mSpinnerInfo);
        */
    }

    public void updateSections(){
        findViewById(R.id.allSpinners).setVisibility(LinearLayout.INVISIBLE);
        findViewById(R.id.spinnerWaiting).setVisibility(RelativeLayout.VISIBLE);
    }
    private void updateLowerCase(){

        updateSections();

        Spinner mSpinner = (Spinner) findViewById(R.id.lowerCaseLevels);
        String mDesiredLevel=mSpinner.getSelectedItem().toString();
            if (mDesiredLevel.equals(mAllMaxLevels.get("lower"))) {
                mDesiredLevel = String.valueOf(Integer.parseInt(mDesiredLevel) + 1);
            }
            Log.d(Constants.LOGCAT, "lower case level: " + mSpinner.getSelectedItem().toString());
        Bundle mInfo=new Bundle();

        mInfo.putString("0",appData.getCurrentUserID());
        mInfo.putString("1", "2");
        mInfo.putString("2", "1");
        mInfo.putString("3", mDesiredLevel);
        mInfo.putString("4", mAllCurrentLevels.get("lower"));


        getLoaderManager().restartLoader(Constants.UPDATE_SEPARATE_LEVELS_FULL, mInfo, this);
        /*
        String[] mSpinnerInfo = new String[]{
                    appData.getCurrentUserID(),
                    "2",
                    "1",
                    mDesiredLevel,
                    mAllCurrentLevels.get("lower")
            };

            getLoaderManager().initLoader(Constants.UPDATE_LEVELS, null, this);
        */
    }


    public void completeReset(View view){

    }
    //////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        appData = ((Motoli_Application) getApplicationContext());

         switch(loader.getId()) {
             default:
             case Constants.SECTIONS_LOADER:{
                 findViewById(R.id.progressFrame).setVisibility(View.INVISIBLE);
                 findViewById(R.id.sidePanelIcon).setVisibility(View.VISIBLE);
                 findViewById(R.id.sidePanelWaiting).setVisibility(View.INVISIBLE);
                Log.d(Constants.LOGCAT,"before displayScreen");
                 displayScreen(data);
                 break;
             }
             case Constants.RESET_LEVELS:
                 appData.setUpdateType(0);
                 mInSettings=true;
                 getLoaderManager().restartLoader(Constants.UPDATE_LEVELS, null, this);
                 break;
             case Constants.UPDATE_SEPARATE_LEVELS_FULL:
                 mInSettings=true;
                 getLoaderManager().restartLoader(Constants.UPDATE_LEVELS, null, this);
                 break;
             case Constants.UPDATE_SEPARATE_LEVELS:
                 mInSettings=true;
                 startSettings();
                 break;
             case Constants.ALL_MAX_LEVELS:

                 findViewById(R.id.btnVideos).setVisibility(ImageView.VISIBLE);
                 findViewById(R.id.btnSections).setVisibility(ImageView.VISIBLE);
                 findViewById(R.id.btnAwards).setVisibility(ImageView.VISIBLE);
                 findViewById(R.id.btnSettings).setVisibility(ImageView.VISIBLE);
                 processSettings(data);
                 break;
             case Constants.SECTION_AWARDS_LOADER:{
                 displayAwards(data);
                 break;
             }
             
             case Constants.ACTIVITIES_LOADER:{
                 if(data.moveToFirst()){
                     goto_activity(data.getString(data.getColumnIndex("activity_id")));
                 }
                 break;
             }
             case Constants.UPDATE_LEVELS:{
                 allUserGroupPhaseInfo=new ArrayList<>();
                 if(data.moveToFirst()) {
                     do{
                         allUserGroupPhaseInfo.add(new ArrayList<String>());
                         allUserGroupPhaseInfo.get(allUserGroupPhaseInfo.size() - 1)
                                 .add(data.getString(data.getColumnIndex("app_user_id"))); //0
                         allUserGroupPhaseInfo.get(allUserGroupPhaseInfo.size() - 1)
                                 .add(data.getString(data.getColumnIndex("group_id"))); //1
                         allUserGroupPhaseInfo.get(allUserGroupPhaseInfo.size() - 1)
                                 .add(data.getString(data.getColumnIndex("phase_id"))); //2
                         allUserGroupPhaseInfo.get(allUserGroupPhaseInfo.size() - 1)
                                 .add(data.getString(
                                         data.getColumnIndex("app_user_current_level"))); //3
                     }while (data.moveToNext());
                 }
                 getLoaderManager().restartLoader(Constants.SECTIONS_LOADER, null, this);
                 break;
             }
         }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }




}
