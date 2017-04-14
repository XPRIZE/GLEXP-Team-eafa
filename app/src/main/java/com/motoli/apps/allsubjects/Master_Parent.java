package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;


public class Master_Parent extends AppCompatActivity {
    protected  Motoli_Application appData;

    protected boolean allowSound=true;
    protected int maxVolume=100;
    protected  boolean mFinishRound=false;

    protected Handler mAudioHandler = new Handler();

    protected Audio_Functions audioFunctions;
    
    
    public Master_Parent() {
        // TODO Auto-generated constructor stub
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appData=((Motoli_Application) getApplicationContext());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    public void onStart(){
        super.onStart();
        audioFunctions =new Audio_Functions(this, allowSound,maxVolume);
        audioFunctions.stopAudio();
    }

    public void onDestroy(){
        super.onDestroy();
        //audioFunctions.stopAudio();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    protected long getAudioDuration(String mAudioUrl){
        return audioFunctions.getAudioDuration(mAudioUrl);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    protected long playGeneralAudio(String mAudioUrl){
        return audioFunctions.playGeneralAudio(mAudioUrl);
        
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected long playFeedBackAudio(String mAudioUrl){
        return audioFunctions.playFeedBackAudio(mAudioUrl);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    protected long playSplitAudio(String mFirstAudio, String mSecondSudio){
        return audioFunctions.playSplitAudio(mFirstAudio, mSecondSudio);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    protected void playClickSound(){
        audioFunctions.playGeneralAudio("sfx_select");
    }
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result =true;
        if ((keyCode == KeyEvent.KEYCODE_BACK && !mFinishRound)) {
            moveBackwords();
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

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected void exitApplication(){
        new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.strLeaveApp))
                .setMessage(getResources().getString(R.string.strWouldYouLieToLeave))
                .setPositiveButton(getResources().getString(R.string.strYes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }
                )
                .setNegativeButton(getResources().getString(R.string.strNo),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                appData.addToClassOrder(2);
                            }
                        }
                )
                .show();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    protected void moveBackwords(){
        int mActivityType=appData.getActivityType();
        Intent main;
        audioFunctions.stopAudio();

        
        switch(mActivityType){
            default:
            case 0://FRONT PAGE
            case 1:
                new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.strLeaveApp))
                .setMessage(getResources().getString(R.string.strWouldYouLieToLeave))
                .setPositiveButton(getResources().getString(R.string.strYes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finishAffinity();
                    }
                 })
                .setNegativeButton(getResources().getString(R.string.strNo), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        appData.addToClassOrder(2);
                    }
                 })
                 .show();
                break;

            case 2://Goto LAUNCH PLATFORM
            case 4:
                appData.setCurrentActivityName("Launch_Platform");

                main = new Intent(getApplicationContext(),Launch_Platform.class);
                startActivity(main);
                finish();
                break;
            case 3://Goto ACTIVITIES PLATFORM
                appData.setCurrentActivityName("Activities_Platform");

                main = new Intent(getApplicationContext(),Activities_Platform.class);
                startActivity(main);
                finish();
                break;
            case 5://Goto ACTIVITY BK02 BOOK LIST
                appData.setCurrentActivityName("ActivityBK02BookList");

                main = new Intent(getApplicationContext(),ActivityBK02BookList.class);
                startActivity(main);
                finish();
                break;

            case 6://ACTIVITY LT03
                appData.setCurrentActivityName("ActivityTR02");
                main = new Intent(getApplicationContext(),ActivityTR02.class);
                startActivity(main);
                finish();	
                break;
        /*    case 7://ACTIVITY SD01
                appData.setCurrentActivityName("ActivitySD01");
                main = new Intent(getApplicationContext(),ActivitySD01.class);
                startActivity(main);
                finish();
                break;
            case 8://ACTIVITY SD02
                appData.setCurrentActivityName("ActivitySD02");
                main = new Intent(getApplicationContext(),ActivitySD02.class);
                startActivity(main);
                finish();
                break;
            case 9://ACTIVITY SD03
                appData.setCurrentActivityName("ActivitySD03");
                main = new Intent(getApplicationContext(),ActivitySD03.class);
                startActivity(main);
                finish();
                break;
            case 10://ACTIVITY SD06
                appData.setCurrentActivityName("ActivitySD06");
                main = new Intent(getApplicationContext(),ActivitySD06.class);
                startActivity(main);
                finish();
                break;
            case 11://ACTIVITY SD04
                appData.setCurrentActivityName("ActivitySD04");
                main = new Intent(getApplicationContext(),ActivitySD04.class);
                startActivity(main);
                finish();
                break;


            case 13://ACTIVITY CS01
                appData.setCurrentActivityName("ActivityCS01");
                main = new Intent(getApplicationContext(),ActivityCS01.class);
                startActivity(main);
                finish();
                break;
            case 14://ACTIVITY CS02
                appData.setCurrentActivityName("ActivityCS02");
                main = new Intent(getApplicationContext(),ActivityCS02.class);
                startActivity(main);
                finish();
                break;


            case 16://ACTIVITY RS01
                appData.setCurrentActivityName("ActivityRS01");
                main = new Intent(getApplicationContext(),ActivityRS01.class);
                startActivity(main);
                finish();
                break;
            case 17://ACTIVITY RS02
                appData.setCurrentActivityName("ActivityRS02");
                main = new Intent(getApplicationContext(),ActivityRS02.class);
                startActivity(main);
                finish();
                break;
            case 18://ACTIVITY RS03
                appData.setCurrentActivityName("ActivityRS03");
                main = new Intent(getApplicationContext(),ActivityRS03.class);
                startActivity(main);
                finish();
                break;
            case 19://ACTIVITY BK02
                appData.setCurrentActivityName("ActivityBK02BookList");
                main = new Intent(getApplicationContext(),ActivityBK02BookList.class);
                startActivity(main);
                finish();
                break;
            case 20://ACTIVITY BK02
                appData.setCurrentActivityName("ActivityBK02Book");
                main = new Intent(getApplicationContext(),ActivityBK02Book.class);
                startActivity(main);
                finish();
                break;
           */
        }

    }
}
