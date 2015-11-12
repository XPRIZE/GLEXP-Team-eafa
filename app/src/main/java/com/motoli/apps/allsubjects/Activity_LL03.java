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

import java.util.ArrayList;

public class Activity_LL03 extends Activity_General_Parent {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
		setContentView(R.layout.activity_ll03);
		appData.addToClassOrder(28);
		allActivityText = new ArrayList<ArrayList<ArrayList<String>>>();
		
		
        // VideoView refference see main.xml
        VideoView mVideoView = (VideoView)findViewById(R.id.videoActivity);
        
        String uriPath = "android.resource://com.motoli.apps.allsubjects.raw/"+R.raw.letter_a_sound;
         
        Uri uri = Uri.parse(uriPath); //Declare your url here.
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" 
        	      + R.raw.letter_a_sound);
        
        mVideoView.setMediaController(new MediaController(this));       
        mVideoView.setVideoURI(video);
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
