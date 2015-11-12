package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
import android.os.Bundle;
import android.os.Handler;

//import com.motoli.apps.allsubjects.GenActivityFunctions;

import java.util.ArrayList;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class UserSelect extends Master_Parent {




    boolean actInstructionPlaying=false;

    //DB variables
    Cursor mpCursor = null;
     SQLiteDatabase sqlDB;

    private String currentUserId="new";

    private ArrayList<String> userTags;
    public static final String TABLE_NAME = "units";
//	private final String COLUMN_NAME = "unit_name";

    /**/

    private Handler pageHandler = new Handler();
    private ListView ListActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);

        setContentView(R.layout.user_select);
        appData.addToClassOrder(1);
        playInstructions();

        findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
            }
        });



        ImageButton speakerButton=(ImageButton) findViewById(R.id.btnInfo);
        speakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actAudio.playSFXAudio("sfx_select");
                //playInstructions();

            }
        });

        // classLevel=1;
        settingsButton();




        //appData.setAllUnits();

        userTags=new ArrayList<String>();
        ArrayList<String> userName = new ArrayList<String>();
        ArrayList<String> userAvatar = new ArrayList<String>();

        //sqlDB=mpDB.getReadableDatabase();

        //mpDB.createGuestUser(getResources().getString(R.string.strGuestUser));

        ArrayList<ArrayList<String>> allUsers=appData.getAllUsers();

        //databaseOpenCloseFunctions(3);

        if(allUsers.size()<=1){
            userTags.add("");
            userName.add("");
            userAvatar.add("0");
        }

        userTags.add("new");
        userName.add(getResources().getString(R.string.strNewUser));
        userAvatar.add("7");


        for (int i = 0; i < allUsers.size(); i++) {
            userTags.add(allUsers.get(i).get(0));
            userName.add(allUsers.get(i).get(1));
            userAvatar.add(allUsers.get(i).get(2));
        }

        ListActivity = (ListView) findViewById(R.id.Select_List);

        if(allUsers.size()>1){
            View listHeader = View.inflate(this, R.layout.user_select_footer_header, null);
            ListActivity.addHeaderView(listHeader);
        }

         View listFooter = View.inflate(this, R.layout.user_select_footer_header, null);
         ListActivity.addFooterView(listFooter);


        ListActivity.setDivider(null);
        // Getting adapter by passing xml data ArrayList
        UserSelectRow adapter = new UserSelectRow(this, userTags, userName,  userAvatar, this);
        adapter.setTypeface(appData.getCurrentFontType());

        ListActivity.setAdapter(adapter);

        /*
        ListActivity.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {

            //	actAudio.stopAudio();
            //	actAudio.playSFXAudio("sfx_select");

                if(position!=0 && position<(userTags.size()+1) && !view.findViewById(R.id.UserSelectIcon).getTag().toString().equals("")){

                    for(int i=0;i<ListActivity.getChildCount();i++){
                        ((ImageView) parent.getChildAt(i).findViewById(R.id.UserSelectArrow)).setVisibility(ImageView.INVISIBLE);
                    }

                    //ListActivity


                    String userTag=view.findViewById(R.id.UserSelectIcon).getTag().toString();
                    appData.setCurrentUserID(userTag);
                    appData.createCurrentDefaultLevels();
                    currentUserId=userTag;

                    ImageView currentArrow = (ImageView) view.findViewById(R.id.UserSelectArrow);
                    currentArrow.setVisibility(ImageView.VISIBLE);
                   TextView txt = (TextView) view.findViewById(R.id.UserSelectText);
                   Log.d(MotoliConstants.LOGCAT, "user name: "+txt.getText()+"current user tag: "+userTag);


                    validate();
                }
            }
        });
        */

        appData.setCurrentUserID("0");
        appData.createCurrentDefaultLevels();
        currentUserId="0";
        validate();

        //if (intNumberAppUsers >= 1) {


    }






    // ///////////////////////////////////////////////////////////////////////////////////////////

    protected void gotoAppPage(String strUserID) {


        Intent main = new Intent(this,Launch_Platform.class);
        startActivity(main);
        finish();
//  		startActivityForResult(main, MotoliConstants.ACTIVITY_USERSELECT);


        /*
        Intent intentSettings = new Intent(getApplicationContext(),Launch_Platform.class);
        startActivity(intentSettings);
        finish();
        */

    }


    // ///////////////////////////////////////////////////////////////////////////////////////////


    // ///////////////////////////////////////////////////////////////////////////////////////////
/*
    private void newUserClick(){
        validateAvailable=true;
        currentUserId="new";
        ((ImageButton) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
    //	findViewById(R.id.UserNewArrow).setVisibility(ImageView.VISIBLE);
        Toast.makeText(UserSelect.this, "User New  Button", Toast.LENGTH_LONG).show();
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////

    private void guestUserClick(){
        validateAvailable=true;
        currentUserId="0";
        ((ImageButton) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_on);
    //	findViewById(R.id.UserGuestArrow).setVisibility(ImageView.VISIBLE);
        Toast.makeText(UserSelect.this, "User Guest  Button", Toast.LENGTH_LONG).show();
    }
    */
    // ///////////////////////////////////////////////////////////////////////////////////////////

    private void validate(){


        /**
         * Below not added as there is currently only one user this could change in future
         */
        /*
        if(currentUserId.equals("new")){


        }else
        if(currentUserId.equals("0")){
*/
            gotoAppPage("0");
        /*
        }else{
            gotoAppPage(currentUserId);
        }
        */
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////



    /////////////////////////////////////////////////////////////////////////////////////////

    protected void playInstructions(){
        //playGeneralAudio("app_sc01_b");
    }




    /////////////////////////////////////////////////////////////////////////////////////////


    protected void settingsButton(){


        findViewById(R.id.btnSettings).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                            ////  STOP AUDIO

                        /*
                            actAudio.playSFXAudio("sfx_select");
                            Intent main = new Intent(UserSelect.this,Settings_App.class);
                            startActivityForResult(main, MotoliConstants.ACTIVITY_SETTINGS);


                            */
                            //Intent intentSettings = new Intent(getApplicationContext(), Settings_App.class);
                            //startActivity(intentSettings);

                    }
                });
    }


}
