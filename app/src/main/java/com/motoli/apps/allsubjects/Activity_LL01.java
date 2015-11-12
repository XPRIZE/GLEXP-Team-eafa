package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import java.util.ArrayList;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.VideoView;

public class Activity_LL01 extends Activity_General_Parent {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
		setContentView(R.layout.activity_ll01);
		appData.addToClassOrder(27);
		allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();
		
		
        // VideoView refference see main.xml
        VideoView mVideoView = (VideoView)findViewById(R.id.videoActivity);
        
        String uriPath = "android.resource://com.motoli.apps.allsubjects.raw/"+R.raw.alphabet_en;

        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getPackageName())
                .path(Integer.toString(R.raw.alphabet_en))
                .build();

        mVideoView.setMediaController(new MediaController(this));       
        mVideoView.setVideoURI(uri);

        mVideoView.requestFocus();
        mVideoView.start();
        
        
		findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				playClickSound();
				moveBackwords();		
			}
		});

	}

}
