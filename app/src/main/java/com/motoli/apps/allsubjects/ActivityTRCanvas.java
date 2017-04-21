package com.motoli.apps.allsubjects;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

/**
* Part of Project Motoli All Subjects
* for Education Technology For Development
* created by Aaron D Michaelis Borsay
* on 11/13/2016.
*/
public class ActivityTRCanvas extends ActivitiesMasterParent {

    private ImageView mBaseImage;
    private ImageView mDesignImage;


    private ActivityTRLettersCanvas mCustomCanvas;



    private HashMap<String,String> mCanvasList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);

        setContentView(R.layout.activity_tr_canvas);

        mBaseImage = ((ImageView) findViewById(R.id.baseImage));
        mDesignImage = ((ImageView) findViewById(R.id.designImage));

        mCustomCanvas = (ActivityTRLettersCanvas) findViewById(R.id.canvasArea);
        mCustomCanvas.clearCanvas();

        appData.addToClassOrder(28);
        appData.setActivityType(6);
        mCurrentGSP = new HashMap<>(appData.getCurrentGroup_Section_Phase());
        mCanvasList = appData.getCanvasList();

        setUpListeners();

        displayScreen();
}


    protected void displayScreen(){

        try{
            String imageName=mCanvasList.get("design_image")
                    .replace(".jpg", "").replace(".png", "").trim();
            int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
            mDesignImage.setImageResource(resID);
        }catch (Exception e) {
            mDesignImage.setImageResource(R.drawable.blank_image);
            Log.e("MyTag", "Failure to get drawable design image.", e);
        }


        try{
            String imageName=mCanvasList.get("base_image")
                    .replace(".jpg", "").replace(".png", "").trim();
            int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
            mBaseImage.setImageResource(resID);
        }catch (Exception e) {
            mBaseImage.setImageResource(R.drawable.blank_image);
            Log.e("MyTag", "Failure to get drawable base image.", e);
        }
   /**/
    }



    protected void setUpListeners(){

        findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
             //   mCustomCanvas.drawColor(Color.TRANSPARENT, Mode.MULTIPLY);
                mCustomCanvas.clearCanvas();
            }
        });
    }
}


