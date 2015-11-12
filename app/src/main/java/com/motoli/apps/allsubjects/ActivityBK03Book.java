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

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/13/2015.
 */
public class ActivityBK03Book extends Master_Parent implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<ArrayList<String>> mBookPages;
    private ArrayList<String> mBookInfo;

    private int mCurrentPage;
    private int mHighlightCount;
    private int mAudioPlayPosition;

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
        mBookInfo=new ArrayList<String>(appData.getCurrentBook());

        mBookPageText=((TextView) findViewById(R.id.bookPageText));
        mBookPageText.setTypeface(appData.getCurrentFontType());

       // mBookPageText.setText("");
        mBookPageTextSizeAndType();
        getLoaderManager().initLoader(MotoliConstants.BOOK_PAGES_READ_ALOUD, null, this);

        setUpTopButtons();


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

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
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

    private void processData(Cursor mCursor){
        mBookPages=new ArrayList<ArrayList<String>>();
        int mPageNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mBookPages.add(new ArrayList<String>());
            mBookPages.get(mPageNumber).add(mCursor.getString(mCursor.getColumnIndex("book_pages_id")));    //0
            mBookPages.get(mPageNumber).add(mCursor.getString(mCursor.getColumnIndex("book_id")));          //1
            mBookPages.get(mPageNumber).add(mCursor.getString(mCursor.getColumnIndex("book_page_order")));  //2
            mBookPages.get(mPageNumber).add(mCursor.getString(mCursor.getColumnIndex("book_page_image")));  //3
            mBookPages.get(mPageNumber).add(mCursor.getString(mCursor.getColumnIndex("book_page_audio")));  //4
            mBookPages.get(mPageNumber).add(mCursor.getString(mCursor.getColumnIndex("book_page_text")));   //5
            mBookPages.get(mPageNumber).add(mCursor.getString(mCursor.getColumnIndex("book_page_time")));   //6
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
        mAudioHandler.postDelayed(audioRunnable, (long) 20);
        mWordHandler.postDelayed(wordHighlight, (long) 500);

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void placePageWordsAndImage(){
        String strCurrentText = mBookPages.get(mCurrentPage).get(5);

        strCurrentText = strCurrentText.replace("&br;", "<br/>").replace("\t","").replace("\r", "");
        strCurrentText = strCurrentText.replace("\n", "").replace(";", " ");

        mBookPageText.setText(Html.fromHtml(strCurrentText));

        try{
            String imageName=mBookPages.get(mCurrentPage).get(3).replace(".jpg", "").replace(".png", "").trim();
            int resID = getResources().getIdentifier(imageName , "drawable", getPackageName());
            ((ImageView) findViewById(R.id.bookPageImage)).setImageResource(resID);
        }catch (Exception e) {
            ((ImageView) findViewById(R.id.bookPageImage)).setImageResource(R.drawable.blank_image);
            Log.e("MyTag", "Failure to get drawable id.", e);
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////

    private Runnable wordHighlight = new Runnable() {
        @Override
        public void run() {
            mBookPageText.setText("");

            String strCurrentText = "";
            String allWords = mBookPages.get(mCurrentPage).get(5);
            String allHiglightTimes = mBookPages.get(mCurrentPage).get(6);
            String mHighlightColor="#cc3333";//+ Integer.toHexString(getResources().getColor(R.color.bk02_highlight));
                    //getResources().getString(R.color.bk02_highlight);

            ArrayList<String> wordArray = new ArrayList<String>(Arrays.asList(allWords.split(";")));
            for(int i=0;i<wordArray.size();i++){
                wordArray.set(i, wordArray.get(i).replace("&br", "<br/>").replace("\t","").replace("\r", "").replace("\n", ""));
            }
            ArrayList<String> highlightArray = new ArrayList<String>(Arrays.asList(allHiglightTimes.split(";")));





            if (wordArray.size() > highlightArray.size()) {
                int hlCount = wordArray.size() - highlightArray.size();
                int amountNeeded = highlightArray.size() + hlCount;
                for (int i = highlightArray.size(); i < amountNeeded; i++) {
                    Double newHighlight = Double.valueOf(highlightArray.get(i - 1)) + 0.5;
                    highlightArray.add(String.valueOf(newHighlight));
                }
            }//end if (wordArray.size() > highlightArray.size()) {

            for (int i = 0; i < wordArray.size(); i++) {
                if (mHighlightCount == highlightArray.size()) {
                    strCurrentText += wordArray.get(i) + " ";
                } else {
                    if (i == mHighlightCount)
                        strCurrentText += "<font color='"+mHighlightColor+"'>"+ wordArray.get(i) + "</font> ";
                    else
                        strCurrentText += wordArray.get(i) + " ";

                }
            }//end for (int i = 0; i < wordArray.size(); i++) {

            mBookPageText.setText(Html.fromHtml(strCurrentText));
            mBookPageText.setTextColor(Color.BLACK);

            mHighlightCount++;

            if (mHighlightCount < highlightArray.size()) {
                Double waitTimeDbl = (Double.valueOf(highlightArray.get(mHighlightCount)) -
                        Double.valueOf(highlightArray.get(mHighlightCount - 1))) * 1000;

                mWordHandler.postDelayed(wordHighlight, waitTimeDbl.longValue());
            } else if (mHighlightCount == highlightArray.size()) {
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
    
    ////////////////////////////////////////////////////////////////////////////////////////

    private Runnable audioRunnable = new Runnable() {
        @Override
        public void run() {

            long audioDuration;
            switch (mAudioPlayPosition) {
                case 0:
                default: {
                    mAllowNextPage = false;
                    audioDuration = playGeneralAudio(mBookPages.get(mCurrentPage).get(4).replace(".mp3",""));
                    mAudioPlayPosition++;
                    audioHandler.postDelayed(audioRunnable, audioDuration + 10);

                    break;
                }
                case 1: {
                    mAllowNextPage = true;
                    setMoveButtons();

                    audioHandler.removeCallbacks(audioRunnable);
                    mAudioPlayPosition = 0;
                    break;
                }
            }
        }
    };
    
    
    ////////////////////////////////////////////////////////////////////////////////////////

    private void mBookPageTextSizeAndType(){
        float mTextNormal= getResources().getDimension(R.dimen.bk02_font_normal);



        switch(Integer.parseInt(mBookInfo.get(6))){
            case 0:{
                mBookPageText.setTextSize(mTextNormal*(float)0.2);
                break;
            }
            case 1:{
                mBookPageText.setTextSize(mTextNormal*(float)0.4);
                break;
            }
            case 2:{
                mBookPageText.setTextSize(mTextNormal*(float)0.6);
                break;
            }
            case 3:{
                mBookPageText.setTextSize(mTextNormal*(float)0.8);
                break;
            }
            default:
            case 4:{
                mBookPageText.setTextSize(mTextNormal*(float)1.0);
                break;
            }
            case 5:{
                mBookPageText.setTextSize(mTextNormal*(float)1.2);
                break;
            }
        }//end switch(readingFontSize){

    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        ArrayList<String> mCurrentBook=new ArrayList<String>(appData.getCurrentBook());
        switch(id){
            default:
            case MotoliConstants.BOOK_PAGES_READ_ALOUD: {
                String mWhere="book_id="+mCurrentBook.get(0);
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_BOOK_PAGES_READ_ALOUD, null, mWhere, null, null);
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
            case MotoliConstants.BOOK_PAGES_READ_ALOUD:{
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
