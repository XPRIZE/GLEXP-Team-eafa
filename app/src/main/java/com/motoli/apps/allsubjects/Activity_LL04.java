package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.VideoView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Activity_LL04 extends Activity_General_Parent {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_ll04);
        appData.addToClassOrder(28);
        allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();


        // VideoView refference see main.xml

        String mFileName="syllable_clip.mp4";
        Class<R.raw> res = R.raw.class;
        int mFileCode;
        VideoView mVideoView = (VideoView)findViewById(R.id.videoActivity);


        mVideoView.setMediaController(new MediaController(this));
        mFileName=mFileName.replace(".mp4","");
        try {

            mFileCode = res.getField(mFileName.replace(".mp4","")).getInt(null);
            Uri video = Uri.parse("android.resource://" + getPackageName() + "/"+mFileCode);



            mVideoView.setVideoURI(video);
            mVideoView.requestFocus();
            mVideoView.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
        //getResources().getIdentifier(mFileName.replace(".mp3",""), "raw", getPackageName());
       // String mFileName=getFileStreamPath("syllable_clip.mp4");

        
        findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
            }
        });


    }

}
