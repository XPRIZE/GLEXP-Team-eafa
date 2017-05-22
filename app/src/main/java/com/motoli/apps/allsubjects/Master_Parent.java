package com.motoli.apps.allsubjects;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
public class Master_Parent extends Activity {
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

    protected long playCorrectOrNot(boolean mCorrect){
        return  audioFunctions.playCorrectOrNot(mCorrect);
    }
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


    /**
     * Will exit application completely
     * @param view exitApp Image
     */
    public void exitApplication(View view){
        finishAffinity();
    }

    /**
     * Leave app open and returns screen to normalcy
     * @param view stayInApp Image
     */
    public void stayInApplication(View view){
        appData.addToClassOrder(2);
        if(findViewById(R.id.exitOrStayIcons) != null) {
            findViewById(R.id.launchGeneral).setAlpha(1.0f);
            findViewById(R.id.exitOrStayIcons).setVisibility(View.INVISIBLE);
        }
    }

    /**
     *
     */
    protected void moveBackwords(){
        int mActivityType=appData.getActivityType();
        Intent main;
        audioFunctions.stopAudio();

        
        switch(mActivityType){
            default:
            case 0://FRONT PAGE
            case 1:

                //  New style for eixit application if icons are available show them
                //  otherwise run former dialog box asking if use really wants to leave
                if(findViewById(R.id.exitOrStayIcons) != null){
                    findViewById(R.id.launchGeneral).setAlpha(0.2f);
                    findViewById(R.id.exitOrStayIcons).setVisibility(View.VISIBLE);

                }else {
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
                }
                break;

            case 2:
            case 4:
                /*
                If mActivityType stored is 2 or 4 then return app back to Launch_Platform page
                and close current activity
                 */
                appData.setCurrentActivityName("Launch_Platform");

                main = new Intent(getApplicationContext(),Launch_Platform.class);
                startActivity(main);
                finish();
                break;
            case 3:
                /*
                This is called from actual activity of learning, cause app to move back to
                Activities_Platoform where another "game" can be chosen
                 */
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

        }

    }
}
