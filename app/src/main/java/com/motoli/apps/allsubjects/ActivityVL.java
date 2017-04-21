
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
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 5/14/2016.
 *
 * Plays all of the different videos based upon what section user is coming from and returns
 * to the same section if desired
 * @author Aaron Borsay
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
    private ImageView mVideoBird;
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
        mVideoBird = (ImageView) findViewById(R.id.videoBird);
        mVideoBird.setVisibility(ImageView.INVISIBLE);
        FrameLayout mFrame =((FrameLayout) findViewById(R.id.videoFrame));
        switch(appData.getCurrentVideoID()){
           default:
           case 2:{
               mFrame.setForeground(ContextCompat.getDrawable(this,R.drawable.a_rounded_boxa));
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
               mFrame.setForeground(ContextCompat.getDrawable(this,R.drawable.a_rounded_boxa));
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

               mFrame.setForeground(ContextCompat.getDrawable(this,R.drawable.a_rounded_boxa));
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
               mFrame.setForeground(ContextCompat.getDrawable(this,R.drawable.a_rounded_box_words));
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
                mFrame.setForeground(ContextCompat.getDrawable(this,R.drawable.a_rounded_boxa));
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
                mFrame.setForeground(ContextCompat.getDrawable(this,R.drawable.a_rounded_boxa));
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
                mFrame.setForeground(ContextCompat.getDrawable(this,R.drawable.a_rounded_boxa));
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
                           mVideoBird.setVisibility(ImageView.INVISIBLE);

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
                        playSyllableCountVideo();
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

    public void syllabelVideoClicked(View view) {
        playSyllableCountVideo();
    }
    public void playSyllableCountVideo(){
        if (!mPlayingVideo) {

            mActivityIcon.setVisibility(ImageView.INVISIBLE);
            mVideoBird.setVisibility(ImageView.INVISIBLE);


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

        if(appData.getCurrentVideoID()==4){
            if(mCurrentVariables.size()<=0){
                playSyllableCountVideo();
            }
        }

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
        mVideoBird.setVisibility(ImageView.INVISIBLE);

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
                    mVideoView.setBackgroundColor(ContextCompat.getColor(
                            ActivityVL.this,R.color.transparent));
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
                mVideoView.setBackground(ContextCompat.getDrawable(
                        ActivityVL.this,R.drawable.bg_learn_letters));
            }
            if (mCurrentGSP.get("group_id").equals("2")) {
                if (mCurrentGSP.get("phase_id").equals("1")
                        || mCurrentGSP.get("phase_id").equals("2")) {
                    mActivityIcon.setImageDrawable(
                            ContextCompat.getDrawable(
                                    ActivityVL.this,R.drawable.icon_letters_names_act));
                } else if (mCurrentGSP.get("phase_id").equals("3")) {
                    mActivityIcon.setImageDrawable(
                            ContextCompat.getDrawable(
                                    ActivityVL.this,R.drawable.icon_letters_phonic_act));
                }
            } else if (mCurrentGSP.get("group_id").equals("3")) {
                mActivityIcon.setImageDrawable(ContextCompat.getDrawable(
                        ActivityVL.this,R.drawable.icon_syllab_practice));
            } else if (mCurrentGSP.get("group_id").equals("4")) {
                mActivityIcon.setImageDrawable(ContextCompat.getDrawable(
                        ActivityVL.this,R.drawable.icon_words_act));
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

                mActivityIcon.setImageDrawable(ContextCompat.getDrawable(
                        ActivityVL.this,R.drawable.icon_letters_names_act));
            } else if (mCurrentGSP.get("group_id").equals("6")) {
                mActivityIcon.setImageDrawable(ContextCompat.getDrawable(
                        ActivityVL.this,R.drawable.icon_math_shape_act));
            } else if (mCurrentGSP.get("group_id").equals("7")) {
                mActivityIcon.setImageDrawable(ContextCompat.getDrawable(
                        ActivityVL.this,R.drawable.icon_number_act));
            } else if (mCurrentGSP.get("group_id").equals("8")) {
                mActivityIcon.setImageDrawable(ContextCompat.getDrawable(
                        ActivityVL.this,R.drawable.icon_math_operations_act));
            }
            if(appData.getCurrentVideoID()!=6) {
                mActivityIcon.setVisibility(ImageView.VISIBLE);
                mVideoBird.setVisibility(ImageView.VISIBLE);
            }

        }
    };

    ///////////////////////////////////////////////////////////////////////////////////////////

    private Runnable runAfterVideoLetter = new Runnable(){
        @Override
        public void run(){

            mPlayingVideo = false;
            mVideoView.setBackground(ContextCompat.getDrawable(
                    ActivityVL.this,R.drawable.bg_learn_letters));

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

    /**
     * Part of Project Motoli All Subjects
     * for Education Technology For Development
     * created by Aaron D Michaelis Borsay
     * on 12/11/2015.
     */
    private static class ActivityVLIcon extends BaseAdapter {
            private Context mContext;
            private ArrayList<HashMap<String,String>> mVideoData;

            static class ViewHolder {
                 TextView text;
                 ImageView icon;
            }

            private ViewHolder holder;

            private ActivityVLIcon(Context mContext, ArrayList<HashMap<String,String>> mVideoData) {
                this.mContext = mContext;
                this.mVideoData=mVideoData;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater mInflater = (LayoutInflater) mContext
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                final Motoli_Application appData
                        = ((Motoli_Application) mContext.getApplicationContext());

                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.activity_vl_icon,parent, false);
                    holder = new ViewHolder();
                    holder.icon = (ImageView) convertView.findViewById(R.id.tracingImage);
                    holder.text =( TextView) convertView.findViewById(R.id.grid_item_text);

                    holder.text.setTypeface(appData.getCurrentFontType());
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                if(Integer.parseInt(mVideoData.get(position).get("level_number"))
                        <=Integer.parseInt(mVideoData.get(position).get("current_level"))) {
                    holder.icon.setAlpha(1.0f);
                    if(mVideoData.get(position).get("variable_text").matches("-?\\d+(\\.\\d+)?")){
                        holder.text.setTypeface(appData.getNumberFontTypeface());
                    }else{
                        holder.text.setTypeface(appData.getCurrentFontType());
                    }
                    holder.text.setText(mVideoData.get(position).get("variable_text"));
                }else {
                    holder.icon.setAlpha(0.1f);
                    holder.text.setText("");
                }
                holder.text.setTag(mVideoData.get(position));

                holder.icon.setImageResource(mContext.getResources()
                        .getIdentifier("round_square_"+mVideoData.get(position).get("level_color"),
                                "drawable",
                                mContext.getPackageName()));
                holder.icon.setTag(mVideoData.get(position));
                holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                holder.icon.setPadding(8, 8, 8, 8);
                holder.icon.destroyDrawingCache();
                return convertView;
            }

            @Override
            public int getCount() {
                return mVideoData.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

        }
}
