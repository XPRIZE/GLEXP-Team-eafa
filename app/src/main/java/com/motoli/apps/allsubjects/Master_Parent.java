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
import android.view.KeyEvent;


public class Master_Parent extends Activity {
    protected  Motoli_Application appData;

    protected boolean allowSound=true;
    protected int maxVolume=100;
    
    protected Handler audioHandler = new Handler();

    protected Audio_Functions audioFunctions;
    
    
    public Master_Parent() {
        // TODO Auto-generated constructor stub
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        audioFunctions =new Audio_Functions(this, allowSound,maxVolume);
        audioFunctions.stopAudio();
        appData=((Motoli_Application) getApplicationContext());

    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected long playGeneralAudio(String audio_url){
        return audioFunctions.playGeneralAudio(audio_url);
        
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    protected void playClickSound(){
        audioFunctions.playGeneralAudio("sfx_select");
        
    }
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result =true;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
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
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    protected void moveBackwords(){
        int class_number=appData.getPreviousClass();
        Intent main;
        audioFunctions.stopAudio();
        
        switch(class_number){
            default:
            case 0://FRONT PAGE
            case 1:
                new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.strLeaveApp))
                .setMessage(getResources().getString(R.string.strWouldYouLieToLeave))
                .setPositiveButton(getResources().getString(R.string.strYes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                        
                        finish();
                    }
                 })
                .setNegativeButton(getResources().getString(R.string.strNo), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                    }
                 })
                /* .setNeutralButton(getResources().getString(R.string.strCancel), new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }
                })*/
                 .show();
                //finish();
                break;
            /*
            case 1://USER SELECT
                appData.setmCurrentActivityName("UserSelect");
                main = new Intent(getApplicationContext(),UserSelect.class);

                startActivity(main);
                finish();break;
                */
            case 2://LAUNCH PLATFORM
                appData.setmCurrentActivityName("Launch_Platform");

                main = new Intent(getApplicationContext(),Launch_Platform.class);
                startActivity(main);
                finish();
                break;
            case 3://ACTIVITIES PLATFORM
                appData.setmCurrentActivityName("Activities_Platform");

                main = new Intent(getApplicationContext(),Activities_Platform.class);
                startActivity(main);
                finish();
                break;
            case 4://ACTIVITY LT05
                appData.setmCurrentActivityName("Activity_LT05");

                main = new Intent(getApplicationContext(),Activity_LT05.class);
                startActivity(main);
                finish();
                break;
            case 5://ACTIVITY LT01
                appData.setmCurrentActivityName("Activity_LT01");

                main = new Intent(getApplicationContext(),Activity_LT01.class);
                startActivity(main);
                finish();
                break;

            case 6://ACTIVITY LT03
                appData.setmCurrentActivityName("Activity_LT03");
                main = new Intent(getApplicationContext(),Activity_LT03.class);
                startActivity(main);
                finish();	
                break;
            case 7://ACTIVITY SD01
                appData.setmCurrentActivityName("ActivitySD01");
                main = new Intent(getApplicationContext(),ActivitySD01.class);
                startActivity(main);
                finish();
                break;
            case 8://ACTIVITY SD02
                appData.setmCurrentActivityName("ActivitySD02");
                main = new Intent(getApplicationContext(),ActivitySD02.class);
                startActivity(main);
                finish();
                break;
            case 9://ACTIVITY SD03
                appData.setmCurrentActivityName("ActivitySD03");
                main = new Intent(getApplicationContext(),ActivitySD03.class);
                startActivity(main);
                finish();
                break;
            case 10://ACTIVITY SD06
                appData.setmCurrentActivityName("ActivitySD06");
                main = new Intent(getApplicationContext(),ActivitySD06.class);
                startActivity(main);
                finish();
                break;
            case 11://ACTIVITY SD04
                appData.setmCurrentActivityName("ActivitySD04");
                main = new Intent(getApplicationContext(),ActivitySD04.class);
                startActivity(main);
                finish();
                break;
            case 12://ACTIVITY SD05
                appData.setmCurrentActivityName("ActivitySD05");
                main = new Intent(getApplicationContext(),ActivitySD05.class);
                startActivity(main);
                finish();
                break;

            case 13://ACTIVITY CS01
                appData.setmCurrentActivityName("ActivityCS01");
                main = new Intent(getApplicationContext(),ActivityCS01.class);
                startActivity(main);
                finish();
                break;
            case 14://ACTIVITY CS02
                appData.setmCurrentActivityName("ActivityCS02");
                main = new Intent(getApplicationContext(),ActivityCS02.class);
                startActivity(main);
                finish();
                break;
            case 15://ACTIVITY CS03
                appData.setmCurrentActivityName("ActivityCS03");
                main = new Intent(getApplicationContext(),ActivityCS03.class);
                startActivity(main);
                finish();
                break;

            case 16://ACTIVITY RS01
                appData.setmCurrentActivityName("ActivityRS01");
                main = new Intent(getApplicationContext(),ActivityRS01.class);
                startActivity(main);
                finish();
                break;
            case 17://ACTIVITY RS02
                appData.setmCurrentActivityName("ActivityRS02");
                main = new Intent(getApplicationContext(),ActivityRS02.class);
                startActivity(main);
                finish();
                break;
            case 18://ACTIVITY RS03
                appData.setmCurrentActivityName("ActivityRS03");
                main = new Intent(getApplicationContext(),ActivityRS03.class);
                startActivity(main);
                finish();
                break;
            case 19://ACTIVITY BK02
                appData.setmCurrentActivityName("ActivityBK02BookList");
                main = new Intent(getApplicationContext(),ActivityBK02BookList.class);
                startActivity(main);
                finish();
                break;
            case 20://ACTIVITY BK02
                appData.setmCurrentActivityName("ActivityBK02Book");
                main = new Intent(getApplicationContext(),ActivityBK02Book.class);
                startActivity(main);
                finish();
                break;
        }

    }
}
