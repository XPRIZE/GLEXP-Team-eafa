package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/13/2015.
 */
public class ActivityBK02Book extends Master_Parent implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<ArrayList<String>> mBookPages;
    private HashMap<String,String> mBookInfo;

    private int mCurrentPage;
    private int mHighlightCount;
    private int mAudioPlayPosition;

    private long mAudioDuration;

    private boolean mAllowNextPage;

    private TextView mBookPageText;

    private Handler mAudioHandler = new Handler();
    private Handler mWordHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_bk02_book);
        appData.addToClassOrder(20);

        mBookInfo=new HashMap<>(appData.getCurrentBook());


        mBookPageText=((TextView) findViewById(R.id.bookPageText));
        mBookPageText.setTypeface(appData.getCurrentFontType());
        appData.setActivityType(5);
       // mBookPageText.setText("");
        mBookPageTextSizeAndType();
        setUpTopButtons();

        getLoaderManager().initLoader(Constants.BOOK_PAGES_READ_ALOUD, null, this);




    }


    public void backButtonClicked(View view){
        playClickSound();
        moveBackwords();
    }


    ////////////////////////////////////////////////////////////////////////////////////////

    private void setUpTopButtons(){
        findViewById(R.id.movePageLeft).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                left2right();
            }
        });

        findViewById(R.id.movePageRight).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                right2left();
            }
        });

        findViewById(R.id.btnRepeat).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                hideMoveButtons();
                placePageWordsAndImage();
                readPage();
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void hideMoveButtons(){
        findViewById(R.id.btnRepeat).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.movePageLeft).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.movePageRight).setVisibility(ImageView.INVISIBLE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void setMoveButtons(){

        if(mBookPages.get(mCurrentPage).get(4).equals("")) {
            findViewById(R.id.btnRepeat).setVisibility(ImageView.INVISIBLE);
        }else{
            findViewById(R.id.btnRepeat).setVisibility(ImageView.VISIBLE);
        }

        if(mCurrentPage == (mBookPages.size()-1) && mCurrentPage!=0){
            findViewById(R.id.movePageRight).setVisibility(ImageView.INVISIBLE);
            findViewById(R.id.movePageLeft).setVisibility(ImageView.VISIBLE);

            updateBookRead();

        }else if(mCurrentPage==0 && mBookPages.size()>1){
            findViewById(R.id.movePageLeft).setVisibility(ImageView.INVISIBLE);
            findViewById(R.id.movePageRight).setVisibility(ImageView.VISIBLE);
        }else if(mCurrentPage==0){
            findViewById(R.id.movePageLeft).setVisibility(ImageView.INVISIBLE);
            findViewById(R.id.movePageRight).setVisibility(ImageView.INVISIBLE);
        }else{
            findViewById(R.id.movePageLeft).setVisibility(ImageView.VISIBLE);
            findViewById(R.id.movePageRight).setVisibility(ImageView.VISIBLE);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    private void updateBookRead(){

        String mWhere="book_id="+mBookInfo.get("book_id");

        getContentResolver().update(
                AppProvider.CONTENT_URI_UPDATE_BOOK_READ, null, mWhere, null);

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    protected void processData(Cursor mCursor){
        mBookPages=new ArrayList<>();
        int mPageNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mBookPages.add(new ArrayList<String>());
            mBookPages.get(mPageNumber).add(mCursor.getString
                    (mCursor.getColumnIndex("book_pages_id")));    //0
            mBookPages.get(mPageNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("book_id")));          //1
            mBookPages.get(mPageNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("book_page_order")));  //2
            mBookPages.get(mPageNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("book_page_image")));  //3
            mBookPages.get(mPageNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("book_page_audio")));  //4
            mBookPages.get(mPageNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("book_page_text")));   //5
            mBookPages.get(mPageNumber).add(mCursor.getString(
                    mCursor.getColumnIndex("book_page_time")));   //6
            mPageNumber++;
            mCursor.moveToNext();
        }

        startBook();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void startBook(){
        setMoveButtons();
        placePageWordsAndImage();
        readPage();
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void readPage(){
        mAllowNextPage=false;
        mHighlightCount = 0;
        mAudioPlayPosition=0;
        hideMoveButtons();
        placePageWordsAndImage();
        mAudioDuration = getAudioDuration(mBookPages.get(mCurrentPage).get(4).replace(".mp3",""));
        mAudioHandler.postDelayed(audioRunnable, (long) 20);
        mWordHandler.postDelayed(wordHighlight, (long) 500);

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void placePageWordsAndImage(){
        String strCurrentText = mBookPages.get(mCurrentPage).get(5);

        strCurrentText = strCurrentText.replace("&br", "<br/>").replace("&bt", "<br/>")
                .replace("&dq:", "&quot;").replace("&sq:", "&rsquo;").replace("\t", "")
                .replace("\r", "").replace("\n", "").replace(";", " ");

        mBookPageText.setText(Html.fromHtml(strCurrentText));

        if(!mBookPages.get(mCurrentPage).get(3).equals("prev")) {
            try {
                String imageName = mBookPages.get(mCurrentPage).get(3)
                        .replace(".jpg", "").replace(".png", "").trim();
                int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
                ((ImageView) findViewById(R.id.bookPageImage)).setImageResource(resID);
            } catch (Exception e) {
                ((ImageView) findViewById(R.id.bookPageImage))
                        .setImageResource(R.drawable.blank_image);
                Log.e("MyTag", "Failure to get drawable id.", e);
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////

    private Runnable wordHighlight = new Runnable() {
        @Override
        public void run() {
            mBookPageText.setText("");

            String strCurrentText = "";
            String allWords = mBookPages.get(mCurrentPage).get(5);
            String mHighlightColor="#cc3333";

            ArrayList<String> wordArray = new ArrayList<>(Arrays.asList(allWords.split(";")));
            for(int i=0;i<wordArray.size(); i++) {
                wordArray.set(i, wordArray.get(i).replace("&br", "<br/>").replace("&bt", "<br/>")
                        .replace("&dq:", "&quot;").replace("&sq:", "&rsquo;").replace("\t", "")
                        .replace("\r", "").replace("\n", ""));
            }
            int mWordCount=wordArray.size();
            double mWordHighlightTime=(double)(mAudioDuration/mWordCount)/1000;
            ArrayList<String> mHighlightArray = new ArrayList<>();

            mHighlightArray.add("0");
            for(int i=1;i<mWordCount;i++){
                double mHighlight=Double.valueOf(mHighlightArray.get(i-1));
                mHighlight+=mWordHighlightTime;
                mHighlightArray.add(String.valueOf(mHighlight));
            }


            if (wordArray.size() > mHighlightArray.size()) {
                int hlCount = wordArray.size() - mHighlightArray.size();
                int amountNeeded = mHighlightArray.size() + hlCount;
                for (int i = mHighlightArray.size(); i < amountNeeded; i++) {
                    Double newHighlight = Double.valueOf(mHighlightArray.get(i - 1)) + 0.5;
                    mHighlightArray.add(String.valueOf(newHighlight));
                }
            }//end if (wordArray.size() > mHighlightArray.size()) {

            for (int i = 0; i < wordArray.size(); i++) {
                if (mHighlightCount == mHighlightArray.size()) {
                    strCurrentText += wordArray.get(i) + " ";
                } else {
                    if (i == mHighlightCount)
                        strCurrentText += "<font color='"+mHighlightColor+"'>"+
                                wordArray.get(i) + "</font> ";
                    else
                        strCurrentText += wordArray.get(i) + " ";

                }
            }//end for (int i = 0; i < wordArray.size(); i++) {

            mBookPageText.setText(Html.fromHtml(strCurrentText));
            mBookPageText.setTextColor(Color.BLACK);

            mHighlightCount++;

            if (mHighlightCount < mHighlightArray.size()) {
                Double waitTimeDbl = (Double.valueOf(mHighlightArray.get(mHighlightCount)) -
                        Double.valueOf(mHighlightArray.get(mHighlightCount - 1))) * 1000;

                mWordHandler.postDelayed(wordHighlight, waitTimeDbl.longValue());
            } else if (mHighlightCount == mHighlightArray.size()) {
                mWordHandler.postDelayed(wordHighlight, 200);
            } else {
                mWordHandler.removeCallbacks(wordHighlight);
            }
        }
    };//end private Runnable wordHighlight = new Runnable() 

    ////////////////////////////////////////////////////////////////////////////////////////
    
    private void right2left() {
        mCurrentPage++;
        if (mCurrentPage <= (mBookPages.size()-1) && mAllowNextPage) {
            mBookPageText.setText("");
            hideMoveButtons();
            setMoveButtons();

            placePageWordsAndImage();
            readPage();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    private void left2right() {
        mCurrentPage--;
        if (mCurrentPage  >= 0  && mAllowNextPage) {
            mBookPageText.setText("");
            hideMoveButtons();
            setMoveButtons();

            placePageWordsAndImage();
            readPage();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private Runnable audioRunnable = new Runnable() {
        @Override
        public void run() {

            long mAudioDurationSub;
            switch (mAudioPlayPosition) {
                case 0:
                default: {
                    mAllowNextPage = false;
                    mAudioDurationSub = playGeneralAudio(
                            mBookPages.get(mCurrentPage).get(4).replace(".mp3",""));
                    mAudioPlayPosition++;
                    mAudioHandler.postDelayed(audioRunnable, mAudioDurationSub + 10);

                    break;
                }
                case 1: {
                    mAllowNextPage = true;
                    setMoveButtons();

                    mAudioHandler.removeCallbacks(audioRunnable);
                    mAudioPlayPosition = 0;
                    break;
                }
            }
        }
    };
    
    
    ////////////////////////////////////////////////////////////////////////////////////////

    private void mBookPageTextSizeAndType(){
        float mTextNormal= getResources().getDimension(R.dimen.bk02_font_normal);



        switch(Integer.parseInt(mBookInfo.get("book_font_size"))){
            case 0:/*{
                mBookPageText.setTextSize(mTextNormal * (float) 0.2);

                break;
            }*/
            case 1:{
                mBookPageText.setTextSize(mTextNormal*(float)0.5);
                Log.d(Constants.LOGCAT,String.valueOf(mTextNormal*(float)0.7));
                break;
            }
            case 2:{
                mBookPageText.setTextSize(mTextNormal*(float)0.6);
                Log.d(Constants.LOGCAT,String.valueOf(mTextNormal*(float)0.8));
                break;
            }
            case 3:{
                mBookPageText.setTextSize(mTextNormal*(float)0.8);
                Log.d(Constants.LOGCAT,String.valueOf(mTextNormal*(float)0.9));
                break;
            }
            default:
            case 4:{
                mBookPageText.setTextSize(mTextNormal*(float)1.0);
                Log.d(Constants.LOGCAT,String.valueOf(mTextNormal*(float)1.0));
                break;
            }
            case 5:{
                mBookPageText.setTextSize(mTextNormal*(float)1.2);
                Log.d(Constants.LOGCAT,String.valueOf(mTextNormal*(float)1.2));
                break;
            }
        }//end switch(readingFontSize){

    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        HashMap<String,String> mCurrentBook=new HashMap<>(appData.getCurrentBook());
        switch(id){
            default:
            case Constants.BOOK_PAGES_READ_ALOUD: {
                String mWhere="book_id="+mCurrentBook.get("book_id");
                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_BOOK_PAGES_READ_ALOUD, null, mWhere, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        appData = ((Motoli_Application) getApplicationContext());

        switch(loader.getId()) {
            default:
            case Constants.BOOK_PAGES_READ_ALOUD:{
                processData(data);
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

}
