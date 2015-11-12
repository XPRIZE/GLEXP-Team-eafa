package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */







import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public abstract class Activity_General_Parent extends Master_Parent {


    protected String currentLtrWrdAudio="";
    protected String matchingLtrWrdAudio="";

    protected String currentWordID="";
    protected String mCurrentPhonicId="";

    protected boolean correctChoice=false;

    protected boolean beingValidated=false;

    protected boolean validateAvailable=false;

    protected int correctLocation=0;
    protected int mCorrectLocation=0;

    protected int roundNumber=0;
    protected int validateFlashPosition=0;
    protected int processGuessPosition=0;
    protected int mProcessGuessPosition=0;

    protected int lastActivityData=0;

    protected Handler validateHandler = new Handler();
    protected Handler guessHandler = new Handler();
    protected Handler lastActivityDataHandler = new Handler();


    protected ArrayList<ArrayList<String>> currentText;
    protected ArrayList<ArrayList<String>> currentWords;
    protected ArrayList<ArrayList<String>> mCurrentWords;



    protected ArrayList<ArrayList<ArrayList<String>>> allActivityText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    ////////////////////////////////////////////////////////////////////////////////////////////

    public void onResume(){
        super.onResume();
        ((TextView) findViewById(R.id.activityName)).setText(appData.getActivityName()+"|"
                +appData.getCurrentGroup_Section_Phase().get(3));

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////

    protected Runnable returnToActivities_Platorm = new Runnable(){
        @Override
        public void run(){
            long audioDuration=0;
            switch(lastActivityData){
                default:
                case 0:{
                    //if(allowBravo){
                    //	audioDuration=playPageAudio(R.raw.good_job);
                    //}
                    lastActivityData++;
                    lastActivityDataHandler.postDelayed(returnToActivities_Platorm, audioDuration+500);
                    break;
                }
                case 1:{
                    moveBackwords();
                }
            }//end switch(lastPagePosition){
        }
    };//end private Runnable returnToActivitiesPage = new Runnable(){


    protected void showCompleteLevelStar(boolean mShowStar){
        if(mShowStar){
            findViewById(R.id.finishedRoundStar).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.finishedRoundStar).setVisibility(View.INVISIBLE);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

}