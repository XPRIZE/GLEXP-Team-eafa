package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 5/14/2016.
 */
public class ActivityVL extends ActivitiesMasterParent implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private long mAudioDuration=0;

    private ActivityVLIcon mAdapter;
    private HashMap<String,String> mVideoInfo;
    private ArrayList<HashMap<String,String>> mCurrentVariables;

    private boolean mPlayingVideo=false;
    private boolean mProceedPast=false;

    private VideoView mVideoView;
    private ImageView mActivityIcon;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_vl);
        appData.addToClassOrder(28);
        if(appData.getActivityType()==1) {
            appData.setActivityType(2);
        }else{
            appData.setActivityType(3);
        }

        allActivityText = new ArrayList<>();
        mVideoView = (VideoView)findViewById(R.id.videoView);

        mActivityIcon = (ImageView) findViewById(R.id.activityIcon);
        mActivityIcon.setVisibility(ImageView.INVISIBLE);


        switch(appData.getCurrentVideoID()){
           default:
           case 2:{
               ((FrameLayout) findViewById(R.id.videoFrame)).setForeground(
                       getResources().getDrawable(R.drawable.a_rounded_boxa));
               findViewById(R.id.videoList)
                       .setBackgroundResource(R.color.video_page_letters_side);
               findViewById(R.id.videoListBackground)
                       .setBackgroundResource(R.color.video_page_letters_side);

               findViewById(R.id.activityMainPart)
                       .setBackgroundResource(R.color.video_page_letters_side);
               findViewById(R.id.topIcons)
                       .setBackgroundResource(R.color.video_page_letters_main);
               findViewById(R.id.iconSpace)
                       .setBackgroundResource(R.color.video_page_letters_main);
               getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_LETTERS, null, this);
               break;
           }
           case 3:{
               ((FrameLayout) findViewById(R.id.videoFrame)).setForeground(
                       getResources().getDrawable(R.drawable.a_rounded_boxa));
               findViewById(R.id.topIcons)
                       .setBackgroundResource(R.color.video_page_letters_main);
               findViewById(R.id.iconSpace)
                       .setBackgroundResource(R.color.video_page_letters_main);
               findViewById(R.id.activityMainPart)
                       .setBackgroundResource(R.color.video_page_letters_side);
               findViewById(R.id.videoList)
                       .setBackgroundResource(R.color.video_page_letters_side);
               findViewById(R.id.videoListBackground)
                       .setBackgroundResource(R.color.video_page_letters_side);
               getLoaderManager().initLoader(Constants.CURRENT_PHONIC_LETTERS, null, this);
               break;
           }
           case 4:{
               ((FrameLayout) findViewById(R.id.videoFrame)).setForeground(
                       getResources().getDrawable(R.drawable.a_rounded_boxa));
               findViewById(R.id.activityMainPart)
                       .setBackgroundResource(R.color.video_page_letters_side);
               findViewById(R.id.videoList)
                       .setBackgroundResource(R.color.video_page_letters_side);
               findViewById(R.id.videoListBackground)
                       .setBackgroundResource(R.color.video_page_letters_side);
               findViewById(R.id.topIcons)
                       .setBackgroundResource(R.color.video_page_letters_main);
               findViewById(R.id.iconSpace)
                       .setBackgroundResource(R.color.video_page_letters_main);
               getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_SYLLABLES, null, this);
               break;
           }
           case 5:{
               ((FrameLayout) findViewById(R.id.videoFrame)).setForeground(
                       getResources().getDrawable(R.drawable.a_rounded_box_words));
               findViewById(R.id.topIcons)
                       .setBackgroundResource(R.color.application_words_video);
               findViewById(R.id.iconSpace)
                       .setBackgroundResource(R.color.application_words_video);
               findViewById(R.id.videoList)
                       .setBackgroundResource(R.color.application_words_video_side);
               findViewById(R.id.videoListBackground)
                       .setBackgroundResource(R.color.application_words_video_side);
               findViewById(R.id.activityMainPart)
                       .setBackgroundResource(R.color.application_words_video_side);
               getLoaderManager().initLoader(Constants.CURRENT_WORDS, null, this);
               break;
           }
           case 6:{
               getLoaderManager().initLoader(Constants.BOOKS_READ_ALOUD, null, this);
               break;
           }
            case 7:{
                ((FrameLayout) findViewById(R.id.videoFrame)).setForeground(
                        getResources().getDrawable(R.drawable.a_rounded_boxa));
                findViewById(R.id.activityMainPart)
                        .setBackgroundResource(R.color.video_page_letters_side);
                findViewById(R.id.videoList)
                        .setBackgroundResource(R.color.video_page_letters_side);
                findViewById(R.id.videoListBackground)
                        .setBackgroundResource(R.color.video_page_letters_side);
                findViewById(R.id.topIcons)
                        .setBackgroundResource(R.color.video_page_letters_main);
                findViewById(R.id.iconSpace)
                        .setBackgroundResource(R.color.video_page_letters_main);
                getLoaderManager().initLoader(Constants.MATH_SHAPES_SET, null, this);
                break;
            }
            case 8:{
                ((FrameLayout) findViewById(R.id.videoFrame)).setForeground(
                        getResources().getDrawable(R.drawable.a_rounded_boxa));
                findViewById(R.id.activityMainPart)
                        .setBackgroundResource(R.color.video_page_letters_side);
                findViewById(R.id.videoList)
                        .setBackgroundResource(R.color.video_page_letters_side);
                findViewById(R.id.videoListBackground)
                        .setBackgroundResource(R.color.video_page_letters_side);
                findViewById(R.id.topIcons)
                        .setBackgroundResource(R.color.video_page_letters_main);
                findViewById(R.id.iconSpace)
                        .setBackgroundResource(R.color.video_page_letters_main);
                getLoaderManager().initLoader(Constants.MATH_NUMBERS_SET, null, this);
                break;
            }
            case 9:{
                ((FrameLayout) findViewById(R.id.videoFrame)).setForeground(
                        getResources().getDrawable(R.drawable.a_rounded_boxa));
                findViewById(R.id.activityMainPart)
                        .setBackgroundResource(R.color.video_page_letters_side);
                findViewById(R.id.videoList)
                        .setBackgroundResource(R.color.video_page_letters_side);
                findViewById(R.id.videoListBackground)
                        .setBackgroundResource(R.color.video_page_letters_side);
                findViewById(R.id.topIcons)
                        .setBackgroundResource(R.color.video_page_letters_main);
                findViewById(R.id.iconSpace)
                        .setBackgroundResource(R.color.video_page_letters_main);
                getLoaderManager().initLoader(Constants.MATH_OPERATIONS, null, this);
                break;
            }
        }


        mProceedPast=false;
        mPlayingVideo=false;
        switch(appData.getCurrentVideoID()) {
            default:{
                findViewById(R.id.noVideoSpace).setVisibility(Space.VISIBLE);
                findViewById(R.id.letterVideo).setVisibility(ImageView.GONE);
                findViewById(R.id.syllablesVideo).setVisibility(ImageView.GONE);
                break;
            }
            case 2: {
                findViewById(R.id.noVideoSpace).setVisibility(Space.GONE);
                findViewById(R.id.letterVideo).setVisibility(ImageView.VISIBLE);
                findViewById(R.id.syllablesVideo).setVisibility(ImageView.GONE);

                findViewById(R.id.letterVideo).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                       if(!mPlayingVideo) {
                            mActivityIcon.setVisibility(ImageView.INVISIBLE);

                            Uri mVideoUri= Uri.parse("android.resource://" + getPackageName() + "/"
                                    + R.raw.group_video_letters);

                            mVideoView.setMediaController(null);
                            mVideoView.setVideoURI(mVideoUri);
                            mVideoView.requestFocus();
                            mVideoView.setZOrderOnTop(true);
                            mVideoView.seekTo(0);
                            mVideoView.start();
                            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                                @Override
                                public void onPrepared(MediaPlayer mp) {

                                    mVideoView.setZOrderOnTop(false);
                                    mVideoView.setBackgroundColor(Color.TRANSPARENT);
                                    mAudioDuration = mp.getDuration();
                                    mAudioHandler.postDelayed(runAfterVideoLetter,
                                            mAudioDuration + 500);
                                }
                            });
                          /*   */
                        }
                    }
                });
                break;
            }
            case 4: {
                findViewById(R.id.noVideoSpace).setVisibility(Space.GONE);
                findViewById(R.id.letterVideo).setVisibility(ImageView.GONE);
                findViewById(R.id.syllablesVideo).setVisibility(ImageView.VISIBLE);
                findViewById(R.id.syllablesVideo).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                       if (!mPlayingVideo) {

                            mActivityIcon.setVisibility(ImageView.INVISIBLE);


                            Uri mVideoUri = Uri.parse("android.resource://" + getPackageName() + "/"
                                    + R.raw.group_video_syllables);

                            mVideoView.setMediaController(null);
                            mVideoView.setVideoURI(mVideoUri);
                            mVideoView.requestFocus();
                            mVideoView.setZOrderOnTop(true);
                            mVideoView.seekTo(10);
                            mVideoView.start();
                            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mVideoView.setZOrderOnTop(false);
                                    mVideoView.setBackgroundColor(Color.TRANSPARENT);
                                    mAudioDuration = mp.getDuration();
                                    mAudioHandler.postDelayed(runAfterVideoLetter, mAudioDuration + 500);
                                }
                            });
                       /*       */
                        }
                    }
                });

                break;
            }

        }

        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mProceedPast) {
                    findViewById(R.id.videoFrame).setVisibility(View.INVISIBLE);
                    moveToActivities();
                    return true;
                }
                return false;
            }
        });


    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private void moveToActivities(){
       if(mCurrentGSP.get("group_id").equals("2")){
           if(mCurrentGSP.get("phase_id").equals("1")
                   || mCurrentGSP.get("phase_id").equals("2")){
               mCurrentGSP.put("section_id","3");
           }else{
               mCurrentGSP.put("section_id","5");
           }
        }else if(mCurrentGSP.get("group_id").equals("3") ){
           mCurrentGSP.put("section_id","8");
       }else if(mCurrentGSP.get("group_id").equals("4") ) {
           mCurrentGSP.put("section_id", "11");
       }else if(mCurrentGSP.get("group_id").equals("6")) {
           mCurrentGSP.put("section_id", "17");
       }else if(mCurrentGSP.get("group_id").equals("7")) {
           mCurrentGSP.put("section_id", "20");
       }else if(mCurrentGSP.get("group_id").equals("8")) {
           mCurrentGSP.put("section_id", "23");
       }

        appData.setCurrentGroup_Section_Phase(mCurrentGSP.get("group_id"),
                mCurrentGSP.get("phase_id"),
                mCurrentGSP.get("section_id"),
                mCurrentGSP.get("current_level"));
        appData.setCurrentActivityName("Activities_Platform");
        Intent main = new Intent(this,Activities_Platform.class);
        startActivity(main);
        finish();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor mCursor){
        mCurrentVariables=new ArrayList<>();
        ListView mVideoList;
        int mCount=0;
        if (mCursor.moveToFirst()) {
            do {

                switch(appData.getCurrentVideoID()){
                    default:
                    case 2:{
                        mCurrentVariables.add(new HashMap<String, String>());
                        mCurrentVariables.get(mCount).put("variable_id",
                                mCursor.getString(mCursor.getColumnIndex("letter_id")));
                        mCurrentVariables.get(mCount).put("variable_text",
                                mCursor.getString(mCursor.getColumnIndex("letter_text")));

                        mCurrentVariables.get(mCount).put("level_color",
                                mCursor.getString(mCursor.getColumnIndex("level_color")));
                        mCurrentVariables.get(mCount).put("level_video",
                                mCursor.getString(mCursor.getColumnIndex("level_video")));
                        mCurrentVariables.get(mCount).put("level_number",
                                mCursor.getString(mCursor.getColumnIndex("level_number")));
                        mCurrentVariables.get(mCount).put("current_level",
                                mCurrentGSP.get("current_level"));

                        break;
                    }
                    case 3:{
                        mCurrentVariables.add(new HashMap<String, String>());
                        mCurrentVariables.get(mCount).put("variable_id",
                                mCursor.getString(mCursor.getColumnIndex("phonic_id")));
                        mCurrentVariables.get(mCount).put("variable_text",
                                mCursor.getString(mCursor.getColumnIndex("phonic_text")));
                        mCurrentVariables.get(mCount).put("level_color",
                                mCursor.getString(mCursor.getColumnIndex("level_color")));
                        mCurrentVariables.get(mCount).put("level_video",
                                mCursor.getString(mCursor.getColumnIndex("level_video")));
                        mCurrentVariables.get(mCount).put("level_number",
                                mCursor.getString(mCursor.getColumnIndex("level_number")));
                        mCurrentVariables.get(mCount).put("current_level",
                                mCurrentGSP.get("current_level"));

                        break;
                    }
                    case 4:{
                        mCurrentVariables.add(new HashMap<String, String>());
                        mCurrentVariables.get(mCount).put("variable_id",
                                mCursor.getString(mCursor.getColumnIndex("level_number")));
                        mCurrentVariables.get(mCount).put("variable_text",
                                mCursor.getString(mCursor.getColumnIndex("group_phase_level_name")));
                        mCurrentVariables.get(mCount).put("level_color",
                                mCursor.getString(mCursor.getColumnIndex("level_color")));
                        mCurrentVariables.get(mCount).put("level_video",
                                mCursor.getString(mCursor.getColumnIndex("level_video")));
                        mCurrentVariables.get(mCount).put("level_number",
                                mCursor.getString(mCursor.getColumnIndex("level_number")));
                        mCurrentVariables.get(mCount).put("current_level",
                                mCursor.getString(mCursor.getColumnIndex("app_user_current_level")));

                        break;
                    }
                    case 5:{
                        mCurrentVariables.add(new HashMap<String, String>());
                        mCurrentVariables.get(mCount).put("variable_id",
                                mCursor.getString(mCursor.getColumnIndex("variable_id")));
                        mCurrentVariables.get(mCount).put("variable_text",
                                mCursor.getString(mCursor.getColumnIndex("variable_text")));

                        mCurrentVariables.get(mCount).put("level_color",
                                mCursor.getString(mCursor.getColumnIndex("level_color")));
                        mCurrentVariables.get(mCount).put("level_video",
                                mCursor.getString(mCursor.getColumnIndex("level_video")));
                        mCurrentVariables.get(mCount).put("level_number",
                                mCursor.getString(mCursor.getColumnIndex("level_number")));
                        mCurrentVariables.get(mCount).put("current_level",
                                mCurrentGSP.get("current_level"));

                        break;
                    }
                    case 6:
                    case 7:
                    case 8:
                    case 9:{
                       /// getLoaderManager().initLoader(Constants.BOOKS_READ_ALOUD, null, this);
                        mCurrentVariables.add(new HashMap<String, String>());
                        mCurrentVariables.get(mCount).put("variable_id",
                                mCursor.getString(mCursor.getColumnIndex("gpl_id")));
                        mCurrentVariables.get(mCount).put("variable_text",
                                mCursor.getString(mCursor.getColumnIndex("level_number")));

                        mCurrentVariables.get(mCount).put("level_color",
                                mCursor.getString(mCursor.getColumnIndex("level_color")));
                        mCurrentVariables.get(mCount).put("level_video",
                                mCursor.getString(mCursor.getColumnIndex("level_video")));
                        mCurrentVariables.get(mCount).put("level_number",
                                mCursor.getString(mCursor.getColumnIndex("level_number")));
                        mCurrentVariables.get(mCount).put("current_level",
                                mCurrentGSP.get("current_level"));

                        break;
                    }
                }
                mCount++;
            } while (mCursor.moveToNext());
        }

        mAdapter= new ActivityVLIcon(this, mCurrentVariables);

        mVideoList = (ListView) findViewById(R.id.videoList);


        mVideoList.setAdapter(mAdapter);
        mPlayingVideo=false;
        mVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(!mPlayingVideo) {
                    mVideoInfo =new HashMap<>
                            ((HashMap) v.findViewById(R.id.grid_item_text).getTag());


                    String mLevelNumber = mVideoInfo.get("level_number");
                    String mCurrentLevelNumber = mVideoInfo.get("current_level");
                    if (Integer.parseInt(mLevelNumber)
                            <= Integer.parseInt(mCurrentLevelNumber)) {
                        mPlayingVideo = true;
                        mProceedPast=false;

                        playVideo();

                    }
                }

            }
        });


    }


    private void playVideo(){

        for(int i=0; i<mCurrentVariables.size();i++){
            if(mCurrentVariables.get(i).get("variable_id").equals(mVideoInfo.get("variable_id"))){
                mCurrentVariables.get(i).put("level_color",
                        mCurrentVariables.get(i).get("level_color").replace("a",""));
                mCurrentVariables.get(i).put("level_color",
                        mCurrentVariables.get(i).get("level_color")+"a");
            }else{
                mCurrentVariables.get(i).put("level_color",
                        mCurrentVariables.get(i).get("level_color").replace("a",""));
            }
        }
        playLetterVideo(mVideoInfo.get("level_video"));
        mAdapter.notifyDataSetChanged();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void playLetterVideo(String mVideo ){

        mActivityIcon.setVisibility(ImageView.INVISIBLE);
        mVideo = mVideo.replace(".mp4", "");
        int mCheckExistence = getResources().getIdentifier(mVideo, "raw", getPackageName());
        if(mCheckExistence!=0) {
            Uri mVideoUri = Uri.parse("android.resource://" + getPackageName() + "/"
                    + mCheckExistence);
            mVideoView.setMediaController(null);
            mVideoView.setVideoURI(mVideoUri);
            mVideoView.requestFocus();
            mVideoView.setZOrderOnTop(true);
            mVideoView.seekTo(10);
            mVideoView.start();

            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    mVideoView.setZOrderOnTop(false);
                    mVideoView.setBackgroundColor(Color.TRANSPARENT);
                    mVideoView.setBackgroundColor(getResources().getColor(R.color.transparent));
                    mAudioDuration=mp.getDuration();
                    mAudioHandler.postDelayed(runAfterVideo, mAudioDuration+2000);
                }

            });
        }else{
            mAudioHandler.postDelayed(runAfterVideo, 100);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable runAfterVideo = new Runnable(){
        @Override
        public void run(){
            for(int i=0; i<mCurrentVariables.size();i++){
                mCurrentVariables.get(i).put("level_color",
                        mCurrentVariables.get(i).get("level_color").replace("a",""));
            }
            mAdapter.notifyDataSetChanged();
            mPlayingVideo = false;

            if (!mCurrentGSP.get("group_id").equals("5")) {
                playGeneralAudio("let_us_practice");
                mProceedPast = true;
                mVideoView.setBackground(getResources().getDrawable(R.drawable.bg_learn_letters));
            }
            if (mCurrentGSP.get("group_id").equals("2")) {
                if (mCurrentGSP.get("phase_id").equals("1")
                        || mCurrentGSP.get("phase_id").equals("2")) {
                    mActivityIcon.setImageDrawable(
                            getResources().getDrawable(R.drawable.icon_letters_names_act));
                } else if (mCurrentGSP.get("phase_id").equals("3")) {
                    mActivityIcon.setImageDrawable(
                            getResources().getDrawable(R.drawable.icon_letters_phonic_act));
                }
            } else if (mCurrentGSP.get("group_id").equals("3")) {
                mActivityIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.icon_syllab_practice));
            } else if (mCurrentGSP.get("group_id").equals("4")) {
                mActivityIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.icon_words_act));
            } else if (mCurrentGSP.get("group_id").equals("5")) {

                for (int i = 0; i < mCurrentVariables.size(); i++) {
                    mCurrentVariables.get(i).put("level_color",
                            mCurrentVariables.get(i).get("level_color").replace("a", ""));
                    mCurrentVariables.get(i).put("current_level",
                            String.valueOf(Integer.parseInt(
                                    mCurrentVariables.get(i).get("current_level")) + 1));
                }
                mAdapter.notifyDataSetChanged();
                getContentResolver()
                        .update(AppProvider.CONTENT_URI_UPDATE_BOOK_LEVEL, null, null, null);
                mPlayingVideo = false;

                mActivityIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.icon_letters_names_act));
            } else if (mCurrentGSP.get("group_id").equals("6")) {
                mActivityIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.icon_math_shape_act));
            } else if (mCurrentGSP.get("group_id").equals("7")) {
                mActivityIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.icon_number_act));
            } else if (mCurrentGSP.get("group_id").equals("8")) {
                mActivityIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.icon_math_operations_act));
            }
            if(appData.getCurrentVideoID()!=6) {
                mActivityIcon.setVisibility(ImageView.VISIBLE);
            }

        }
    };

    ///////////////////////////////////////////////////////////////////////////////////////////

    private Runnable runAfterVideoLetter = new Runnable(){
        @Override
        public void run(){

            mPlayingVideo = false;
            mVideoView.setBackground(getResources().getDrawable(R.drawable.bg_learn_letters));

        }
    };


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case Constants.ACTIVITY_CURRENT_LETTERS:{

                String selection="variable_phase_levels.group_id=2 " +
                        "AND variable_phase_levels.phase_id=" +mCurrentGSP.get("phase_id")+ " "+
                        "AND variable_phase_levels.level_number<="+mCurrentGSP.get("current_level");

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_ACTIVITY_CURRENT_LETTERS,
                        null, selection, null, null);
                break;
            }
            case Constants.BOOKS_READ_ALOUD:{

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_BOOK_VIDEOS, null,
                        null, null, null);
                break;
            }
            case Constants.CURRENT_WORDS:{
                String selection= "phonic_levels.level_number" +
                        "<="+mCurrentGSP.get("current_level")+" " +
                        "AND gpl.level_video!=\"\"";
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_WORDS_FOR_VIDEOS, null,
                        selection, null, null);
                break;
            }
            case Constants.ACTIVITY_CURRENT_SYLLABLES:{

                String selection="variable_phase_levels.group_id=3 " +
                        "AND variable_phase_levels.phase_id=2 "+
                        "AND variable_phase_levels.level_number<=" +
                        "(SELECT app_users_current_gpl.app_user_current_level " +
                        "FROM app_users_current_gpl " +
                        "WHERE app_users_current_gpl.group_id=3 " +
                        "AND app_users_current_gpl.phase_id=2 " +
                        "AND app_users_current_gpl.app_user_id="+appData.getCurrentUserID()+") ";
                //+mCurrentGSP.get("current_level");

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_SYLLABLES_FOR_VIDEOS,
                        null, selection, null, null);
                break;
            }
            case Constants.CURRENT_PHONIC_LETTERS:{


                String selection="variable_phase_levels.group_id=2 " +
                        "AND variable_phase_levels.phase_id=3 "+
                        "AND variable_phase_levels.level_number<="+mCurrentGSP.get("current_level");
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_PHONICS_FOR_VIDEOS,
                        null, selection, null, null);
                break;
            }
            case Constants.MATH_SHAPES_SET:{
                String selection=mCurrentGSP.get("current_level");
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_SHAPES_FOR_VIDEOS,
                        null, selection, null, null);
                break;
            }
            case Constants.MATH_NUMBERS_SET:{
                String selection=mCurrentGSP.get("current_level");
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_NUMBERS_FOR_VIDEOS,
                        null, selection, null, null);
                break;
            }
            case Constants.MATH_OPERATIONS:{
                String selection=mCurrentGSP.get("current_level");
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_CURRENT_OPERATIONS_FOR_VIDEOS,
                        null, selection, null, null);
                break;
            }

        }
        return cursorLoader;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        displayScreen(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

}
