package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/12/2015.
 */


import java.util.ArrayList;



import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySD07 extends Activity_General_Parent  implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private long audioDuration=0;
    private boolean mLowerCase;
    private ArrayList<ArrayList<String>> currentWords;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_sd07);
        appData.addToClassOrder(4);
        ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
        mLowerCase = (currentGSP.get(1).equals("3") || currentGSP.get(1).equals("2"));

        findViewById(R.id.btnChangeCase).setVisibility(ImageView.INVISIBLE);

        findViewById(R.id.btnChangeCase).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mLowerCase = (!mLowerCase);
                setTextCase();
            }
        });

        resetFontsTypeFace();

        ((TextView)findViewById(R.id.phonic_1)).setText("");
        ((TextView)findViewById(R.id.phonic_2)).setText("");
        ((TextView)findViewById(R.id.phonic_3)).setText("");
        ((TextView)findViewById(R.id.phonic_4)).setText("");
        ((TextView)findViewById(R.id.phonic_5)).setText("");
        ((TextView)findViewById(R.id.phonic_6)).setText("");
        ((TextView)findViewById(R.id.phonic_7)).setText("");
        ((TextView)findViewById(R.id.phonic_8)).setText("");
        ((TextView)findViewById(R.id.phonic_9)).setText("");
        ((TextView)findViewById(R.id.phonic_10)).setText("");
        ((TextView)findViewById(R.id.phonic_11)).setText("");
        ((TextView)findViewById(R.id.phonic_12)).setText("");
        ((TextView)findViewById(R.id.phonic_13)).setText("");
        ((TextView)findViewById(R.id.phonic_14)).setText("");
        ((TextView)findViewById(R.id.phonic_15)).setText("");
        ((TextView)findViewById(R.id.phonic_16)).setText("");
        ((TextView)findViewById(R.id.phonic_17)).setText("");
        ((TextView)findViewById(R.id.phonic_18)).setText("");
        ((TextView)findViewById(R.id.phonic_19)).setText("");
        ((TextView)findViewById(R.id.phonic_20)).setText("");
        ((TextView)findViewById(R.id.phonic_21)).setText("");
        ((TextView)findViewById(R.id.phonic_22)).setText("");
        ((TextView)findViewById(R.id.phonic_23)).setText("");
        ((TextView)findViewById(R.id.phonic_24)).setText("");
        ((TextView)findViewById(R.id.phonic_25)).setText("");
        ((TextView)findViewById(R.id.phonic_26)).setText("");
        ((TextView)findViewById(R.id.phonic_27)).setText("");
        ((TextView)findViewById(R.id.phonic_28)).setText("");
        ((TextView)findViewById(R.id.phonic_29)).setText("");
        ((TextView)findViewById(R.id.phonic_30)).setText("");
        ((TextView)findViewById(R.id.phonic_31)).setText("");
        ((TextView)findViewById(R.id.phonic_32)).setText("");
        ((TextView)findViewById(R.id.phonic_33)).setText("");
        ((TextView)findViewById(R.id.phonic_34)).setText("");
        ((TextView)findViewById(R.id.phonic_35)).setText("");
        ((TextView)findViewById(R.id.phonic_36)).setText("");
        ((TextView)findViewById(R.id.phonic_37)).setText("");
        ((TextView)findViewById(R.id.phonic_38)).setText("");
        ((TextView)findViewById(R.id.phonic_39)).setText("");
        ((TextView)findViewById(R.id.phonic_40)).setText("");
        ((TextView)findViewById(R.id.phonic_41)).setText("");
        ((TextView)findViewById(R.id.phonic_42)).setText("");
        ((TextView)findViewById(R.id.phonic_43)).setText("");
        ((TextView)findViewById(R.id.phonic_44)).setText("");
        ((TextView)findViewById(R.id.phonic_45)).setText("");
        ((TextView)findViewById(R.id.phonic_46)).setText("");
        ((TextView)findViewById(R.id.phonic_47)).setText("");
        ((TextView)findViewById(R.id.phonic_48)).setText("");
        ((TextView)findViewById(R.id.phonic_49)).setText("");
        ((TextView)findViewById(R.id.phonic_50)).setText("");

        findViewById(R.id.phonic_layout_1).setTag("");
        findViewById(R.id.phonic_layout_2).setTag("");
        findViewById(R.id.phonic_layout_3).setTag("");
        findViewById(R.id.phonic_layout_4).setTag("");
        findViewById(R.id.phonic_layout_5).setTag("");
        findViewById(R.id.phonic_layout_6).setTag("");
        findViewById(R.id.phonic_layout_7).setTag("");
        findViewById(R.id.phonic_layout_8).setTag("");
        findViewById(R.id.phonic_layout_9).setTag("");
        findViewById(R.id.phonic_layout_10).setTag("");
        findViewById(R.id.phonic_layout_11).setTag("");
        findViewById(R.id.phonic_layout_12).setTag("");
        findViewById(R.id.phonic_layout_13).setTag("");
        findViewById(R.id.phonic_layout_14).setTag("");
        findViewById(R.id.phonic_layout_15).setTag("");
        findViewById(R.id.phonic_layout_16).setTag("");
        findViewById(R.id.phonic_layout_17).setTag("");
        findViewById(R.id.phonic_layout_18).setTag("");
        findViewById(R.id.phonic_layout_19).setTag("");
        findViewById(R.id.phonic_layout_20).setTag("");
        findViewById(R.id.phonic_layout_21).setTag("");
        findViewById(R.id.phonic_layout_22).setTag("");
        findViewById(R.id.phonic_layout_23).setTag("");
        findViewById(R.id.phonic_layout_24).setTag("");
        findViewById(R.id.phonic_layout_25).setTag("");
        findViewById(R.id.phonic_layout_27).setTag("");
        findViewById(R.id.phonic_layout_28).setTag("");
        findViewById(R.id.phonic_layout_29).setTag("");
        findViewById(R.id.phonic_layout_30).setTag("");
        findViewById(R.id.phonic_layout_31).setTag("");
        findViewById(R.id.phonic_layout_32).setTag("");
        findViewById(R.id.phonic_layout_33).setTag("");
        findViewById(R.id.phonic_layout_34).setTag("");
        findViewById(R.id.phonic_layout_35).setTag("");
        findViewById(R.id.phonic_layout_36).setTag("");
        findViewById(R.id.phonic_layout_37).setTag("");
        findViewById(R.id.phonic_layout_38).setTag("");
        findViewById(R.id.phonic_layout_39).setTag("");
        findViewById(R.id.phonic_layout_40).setTag("");
        findViewById(R.id.phonic_layout_41).setTag("");
        findViewById(R.id.phonic_layout_42).setTag("");
        findViewById(R.id.phonic_layout_43).setTag("");
        findViewById(R.id.phonic_layout_44).setTag("");
        findViewById(R.id.phonic_layout_45).setTag("");
        findViewById(R.id.phonic_layout_46).setTag("");
        findViewById(R.id.phonic_layout_47).setTag("");
        findViewById(R.id.phonic_layout_48).setTag("");
        findViewById(R.id.phonic_layout_49).setTag("");
        findViewById(R.id.phonic_layout_50).setTag("");

        getLoaderManager().initLoader(MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void resetFontsTypeFace(){
        ((TextView)findViewById(R.id.phonic_1)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_2)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_3)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_4)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_5)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_6)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_7)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_8)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_9)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_10)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_11)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_12)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_13)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_14)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_15)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_16)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_17)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_18)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_19)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_20)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_21)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_22)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_23)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_24)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_25)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_26)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_27)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_28)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_29)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_30)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_31)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_32)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_33)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_34)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_35)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_36)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_37)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_38)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_39)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_40)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_41)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_42)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_43)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_44)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_45)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_46)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_47)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_48)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_49)).setTypeface(appData.getCurrentFontType());
        ((TextView)findViewById(R.id.phonic_50)).setTypeface(appData.getCurrentFontType());


    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable runRestFontTypeFace = new Runnable(){
        @Override
        public void run(){
            resetFontsTypeFace();
        }
    };



    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor currentWordsCursor){

        currentWords=new ArrayList<ArrayList<String>>();
        int currentWordNumber=0;
        while(currentWordsCursor.moveToNext()){
            currentWords.add(new ArrayList<String>());
            currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex(Database.Phonics.PHONIC_ID)));
            currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex(Database.Phonics.PHONIC_TEXT)));
            currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex(Database.Phonics.PHONIC_AUDIO)));
            currentWordNumber++;
        }

        // THIS IS SET FOR ENGLISH LANGUAGE (26 letters) ONLY RIGHT NOW MORE CODING NEEDED TO ACCOMIDATE OTHER LETTERS


        for(int phonic_number=1; phonic_number<=50; phonic_number++){
            switch(phonic_number){
                default:
                case 0:{
                    break;
                }
                case 1:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_1).setTag(currentWords.get(phonic_number-1).get(0));

                        ((TextView)findViewById(R.id.phonic_1)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_1).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_1).setAlpha(0.08f);
                    }
                    break;
                }
                case 2:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_2).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_2)).setText(currentWords.get(phonic_number-1).get(1));

                        findViewById(R.id.phonic_layout_2).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_2).setAlpha(0.08f);
                    }
                    break;
                }
                case 3:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_3).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_3)).setText(currentWords.get(phonic_number-1).get(1));

                        findViewById(R.id.phonic_layout_3).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_3).setAlpha(0.08f);
                    }
                    break;
                }
                case 4:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_4).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_4)).setText(currentWords.get(phonic_number-1).get(1));

                        findViewById(R.id.phonic_layout_4).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_4).setAlpha(0.08f);
                    }
                    break;
                }
                case 5:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_5).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_5)).setText(currentWords.get(phonic_number-1).get(1));

                        findViewById(R.id.phonic_layout_5).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_5).setAlpha(0.08f);
                    }
                    break;
                }
                case 6:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_6).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_6)).setText(currentWords.get(phonic_number-1).get(1));

                        findViewById(R.id.phonic_layout_6).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_6).setAlpha(0.08f);
                    }
                    break;
                }
                case 7:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_7).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_7)).setText(currentWords.get(phonic_number-1).get(1));

                        findViewById(R.id.phonic_layout_7).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_7).setAlpha(0.08f);
                    }
                    break;
                }
                case 8:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_8).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_8)).setText(currentWords.get(phonic_number-1).get(1));

                        findViewById(R.id.phonic_layout_8).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_8).setAlpha(0.08f);
                    }
                    break;
                }
                case 9:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_9).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_9)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_9).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_9).setAlpha(0.08f);
                    }
                    break;
                }
                case 10:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_10).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_10)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_10).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_10).setAlpha(0.08f);
                    }
                    break;
                }
                case 11:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_11).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_11)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_11).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_11).setAlpha(0.08f);
                    }
                    break;
                }
                case 12:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_12).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_12)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_12).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_12).setAlpha(0.08f);
                    }
                    break;
                }
                case 13:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_13).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_13)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_13).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_13).setAlpha(0.08f);
                    }
                    break;
                }
                case 14:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_14).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_14)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_14).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_14).setAlpha(0.08f);
                    }
                    break;
                }
                case 15:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_15).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_15)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_15).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_15).setAlpha(0.08f);
                    }
                    break;
                }
                case 16:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_16).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_16)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_16).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_16).setAlpha(0.08f);
                    }
                    break;
                }
                case 17:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_17).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_17)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_17).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_17).setAlpha(0.08f);
                    }
                    break;
                }
                case 18:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_18).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_18)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_18).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_18).setAlpha(0.08f);
                    }
                    break;
                }
                case 19:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_19).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_19)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_19).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_19).setAlpha(0.08f);
                    }
                    break;
                }
                case 20:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_20).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_20)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_20).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_20).setAlpha(0.08f);
                    }
                    break;
                }
                case 21:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_21).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_21)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_21).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_21).setAlpha(0.08f);
                    }
                    break;
                }
                case 22:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_22).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_22)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_22).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_22).setAlpha(0.08f);
                    }
                    break;
                }
                case 23:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_23).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_23)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_23).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_23).setAlpha(0.08f);
                    }
                    break;
                }
                case 24:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_24).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_24)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_24).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_24).setAlpha(0.08f);
                    }
                    break;
                }
                case 25:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_25).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_25)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_25).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_25).setAlpha(0.08f);
                    }
                    break;
                }
                case 26:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_26).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_26)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_26).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_26).setAlpha(0.08f);
                    }
                    break;
                }
                case 27:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_27).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_27)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_27).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_27).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 28:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_28).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_28)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_28).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_28).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 29:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_29).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_29)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_29).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_29).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 30:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_30).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_30)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_30).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_30).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 31:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_31).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_31)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_31).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_31).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 32:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_32).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_32)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_32).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_32).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 33:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_33).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_33)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_33).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_33).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 34:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_34).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_34)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_34).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_34).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 35:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_35).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_35)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_35).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_35).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 36:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_36).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_36)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_36).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_36).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 37:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_37).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_37)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_37).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_37).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 38:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_38).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_38)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_38).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_38).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 39:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_39).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_39)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_39).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_39).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 40:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_40).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_40)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_40).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_40).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 41:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_41).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_41)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_41).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_41).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 42:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_42).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_42)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_42).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_42).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 43:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_43).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_43)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_43).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_43).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 44:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_44).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_44)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_44).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_44).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 45:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_45).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_45)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_45).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_45).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 46:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_46).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_46)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_46).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_46).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 47:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_47).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_47)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_47).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_47).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 48:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_48).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_48)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_48).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_48).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 49:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_49).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_49)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_49).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_49).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

                case 50:{
                    if(currentWords.size()>=phonic_number){
                        findViewById(R.id.phonic_layout_50).setTag(currentWords.get(phonic_number-1).get(0));
                        ((TextView)findViewById(R.id.phonic_50)).setText(currentWords.get(phonic_number-1).get(1));
                        findViewById(R.id.phonic_layout_50).setAlpha(1.0f);
                    }else{
                        findViewById(R.id.phonic_layout_50).setAlpha(0.08f);
                    }
                    break;
                }

/////////////////////////////////////////////////////////////////////////////

            }//end switch(phonic_number)

        }//end for(int phonic_number=1; phonic_number<=26; phonic_number++){



        setTextCase();


        setUpButtonListeners();


    }

    private void setTextCase(){
       /* if(mLowerCase){
            ((TextView)findViewById(R.id.phonic_1)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_2)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_3)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_4)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_5)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_6)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_7)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_8)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_9)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_10)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_11)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_12)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_13)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_14)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_15)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_16)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_17)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_18)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_19)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_20)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_21)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_22)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_23)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_24)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_25)).setAllCaps(true);
            ((TextView)findViewById(R.id.phonic_26)).setAllCaps(true);
        }else{
        */
            ((TextView)findViewById(R.id.phonic_1)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_2)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_3)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_4)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_5)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_6)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_7)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_8)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_9)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_10)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_11)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_12)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_13)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_14)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_15)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_16)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_17)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_18)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_19)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_20)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_21)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_22)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_23)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_24)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_25)).setAllCaps(false);
            ((TextView)findViewById(R.id.phonic_26)).setAllCaps(false);

        //}
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:
            case MotoliConstants.CURRENT_PHNC_LTRS:{

                ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());
                String[] projection = new String[]{
                        appData.getCurrentActivity().get(0),
                        appData.getCurrentUserID(),
                        currentGSP.get(0),
                        currentGSP.get(1),
                        currentGSP.get(2),
                        currentGSP.get(3)};
                String selection=Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+Database.Variable_Phase_Levels.GROUP_ID+"="+currentGSP.get(0)
                        +" AND "+Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+Database.Variable_Phase_Levels.PHASE_ID+"="+currentGSP.get(1)
                        +" AND "+Database.Variable_Phase_Levels.VARIABLE_PHASE_LEVELS_TABLE+"."+
                        Database.Variable_Phase_Levels.LEVEL_NUMBER+"<="+currentGSP.get(3)
                        +" AND variable_type_relations.variable_type_id=3";

                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_CURRENT_PHNC_LTRS, projection, selection, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch(loader.getId()) {
            default:
            case MotoliConstants.CURRENT_PHNC_LTRS:{
                displayScreen(data);
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpButtonListeners(){
        findViewById(R.id.phonic_layout_1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_1).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(0).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_1)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_2).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(1).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_2)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_3).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(2).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_3)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_4).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(3).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_4)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_5).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_5).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(4).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_5)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_6).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_6).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(5).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_6)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_7).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_7).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(6).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_7)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_8).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_8).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(7).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_8)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_9).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_9).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(8).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_9)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_10).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_10).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(9).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_10)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_11).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_11).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(10).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_11)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_12).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_12).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(11).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_12)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_13).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_13).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(12).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_13)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_14).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_14).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(13).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_14)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_15).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_15).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(14).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_15)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_16).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_16).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(15).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_16)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_17).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_17).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(16).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_17)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_18).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_18).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(17).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_18)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_19).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_19).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(18).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_19)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_20).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_20).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(19).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_20)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_21).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_21).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(20).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_21)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_22).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_22).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(21).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_22)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_23).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_23).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(22).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_23)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_24).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_24).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(23).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_24)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_25).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_25).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(24).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_25)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_26).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_26).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(25).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_26)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_27).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_27).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(26).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_27)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_28).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_28).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(27).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_28)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_29).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_29).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(28).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_29)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_30).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_30).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(29).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_30)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_31).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_31).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(30).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_31)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_32).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_32).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(31).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_32)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_33).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_33).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(32).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_33)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_34).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_34).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(33).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_34)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_35).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_35).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(34).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_35)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_36).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_36).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(35).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_36)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_37).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_37).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(36).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_37)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_38).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_38).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(37).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_38)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_39).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_39).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(38).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_39)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_40).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_40).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(39).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_40)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_41).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_41).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(40).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_41)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_42).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_42).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(41).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_42)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_43).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_43).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(42).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_43)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_44).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_44).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(43).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_44)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_45).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_45).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(44).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_45)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_46).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_46).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(45).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_46)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_47).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_47).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(46).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_47)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_48).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_48).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(47).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_48)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_49).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_49).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(48).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_49)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        findViewById(R.id.phonic_layout_50).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!(findViewById(R.id.phonic_layout_50).getTag()).equals("")){
                    audioDuration=playGeneralAudio(currentWords.get(49).get(2));
                    resetFontsTypeFace();
                    audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
                    ((TextView)findViewById(R.id.phonic_50)).setTypeface(appData.getCurrentFontTypeBold());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////



    }


}