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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/13/2015.
 */
public class ActivityBK03Books extends ActivitiesMasterParent  implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private ArrayList<String> mBookInfo;
    private int mCurrentPage;
    private TextView mBookPageText;
    private boolean mAllowNextPage;

    private int mHighlightCount;

    private int mAudioPlayPosition;

    private String mBookId;
    private ArrayList<ArrayList<String>> mCurrentBooks;
    private ArrayList<ArrayList<String>> mBookPages;

    ListView mBookList;
    ActivityBK03BookIcon mAdapter;

    private Handler mAudioHandler = new Handler();
    private Handler mWordHandler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_bk03_books);
        appData.addToClassOrder(4);
        appData.setActivityType(2);

        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        findViewById(R.id.movePageLeft).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.movePageRight).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.btnRepeat).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.bookPageView).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.bookPageText).setVisibility(ImageView.INVISIBLE);

        mBookList = (ListView) findViewById(R.id.bookList);

        mBookPageText=((TextView) findViewById(R.id.bookPageText));
        mBookPageText.setTypeface(appData.getCurrentFontType());

        setUpTopButtons();

        getLoaderManager().initLoader(Constants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void createBookList(Cursor mCursor){
        mCurrentBooks=new ArrayList<>();
        int mBookCount=0;
        if (mCursor.moveToFirst()) {
            do {
                mCurrentBooks.add(new ArrayList<String>());
                mCurrentBooks.get(mBookCount).add(
                        mCursor.getString(mCursor.getColumnIndex("book_id")));       //0
                mCurrentBooks.get(mBookCount).add(
                        mCursor.getString(mCursor.getColumnIndex("book_title")));     //1
                mCurrentBooks.get(mBookCount).add(
                        mCursor.getString(mCursor .getColumnIndex("phonic_color")));    //2
                mCurrentBooks.get(mBookCount).add(
                        mCursor.getString(mCursor.getColumnIndex("book_font_size")));    //3
                mCurrentBooks.get(mBookCount).add(
                        mCursor.getString(mCursor.getColumnIndex("phonic_text")));    //4
                mCurrentBooks.get(mBookCount).add(
                        mCursor.getString(mCursor.getColumnIndex("decodable_levels")));    //5

                mBookCount++;
            } while (mCursor.moveToNext());
        }

        mAdapter= new  ActivityBK03BookIcon(this, mCurrentBooks);



        mBookList.setAdapter(mAdapter);

        mBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                mBookInfo = new ArrayList<>
                        ((ArrayList) v.findViewById(R.id.tracingImage).getTag());

                String mBookTitle = (String) mBookInfo.get(1);
                mBookId = (String) mBookInfo.get(0);

                beginBook(v);
            }
        });


    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private void beginBook(View v){


        for(int i=0; i<mCurrentBooks.size();i++){
            if(mCurrentBooks.get(i).get(5).equals(mBookInfo.get(5))){
                mCurrentBooks.get(i).set(2,mCurrentBooks.get(i).get(2).replace("a",""));
                mCurrentBooks.get(i).set(2,mCurrentBooks.get(i).get(2)+"a");
            }else{
                mCurrentBooks.get(i).set(2,mCurrentBooks.get(i).get(2).replace("a",""));
            }
        }
        mAdapter.notifyDataSetChanged();

        getLoaderManager().restartLoader(Constants.BOOK_PAGES_READ_ALOUD, null, this);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    private void processBook(Cursor mCursor){
        mBookPages=new ArrayList<ArrayList<String>>();
        int mPageNumber=0;
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            mBookPages.add(new ArrayList<String>());
            mBookPages.get(mPageNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("book_pages_id")));    //0
            mBookPages.get(mPageNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("book_id")));          //1
            mBookPages.get(mPageNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("book_page_order")));  //2
            mBookPages.get(mPageNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("book_page_image")));  //3
            mBookPages.get(mPageNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("book_page_audio")));  //4
            mBookPages.get(mPageNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("book_page_text")));   //5
            mBookPages.get(mPageNumber).add(
                    mCursor.getString(mCursor.getColumnIndex("book_page_time")));   //6
            mPageNumber++;
            mCursor.moveToNext();
        }
        mCurrentPage=0;
        setBookPageTextSizeAndType();
        startBook();

    }
    ////////////////////////////////////////////////////////////////////////////////////////

    private void hideMoveButtons(){
        findViewById(R.id.btnRepeat).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.movePageLeft).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.movePageRight).setVisibility(ImageView.INVISIBLE);
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
                placePageWords();
                readPage();
            }
        });

    }
    ////////////////////////////////////////////////////////////////////////////////////////

    private void readPage(){
        mAllowNextPage=false;
        mHighlightCount = 0;
        mAudioPlayPosition=0;
        hideMoveButtons();
        placePageWords();
        mAudioHandler.postDelayed(audioRunnable, (long) 20);
        mWordHandler.postDelayed(wordHighlight, (long) 500);

    }
    //////////////////////////////////////////////////////////////////////////////////////

    private void right2left() {
        mCurrentPage++;
        if (mCurrentPage <= (mBookPages.size()-1) && mAllowNextPage) {
            mBookPageText.setText("");
            hideMoveButtons();
            setMoveButtons();

            placePageWords();
        }else{
            mCurrentPage--;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void left2right() {
        mCurrentPage--;
        if (mCurrentPage  >= 0  && mAllowNextPage) {
            mBookPageText.setText("");
            hideMoveButtons();
            setMoveButtons();

            placePageWords();
        }else {
            mCurrentPage++;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////

    private void setBookPageTextSizeAndType(){
        float mTextNormal= getResources().getDimension(R.dimen.bk03_font_normal);

        switch(Integer.parseInt(mBookInfo.get(3))){
            case 0:
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

    ////////////////////////////////////////////////////////////////////////////////////////

    private void startBook(){

        findViewById(R.id.btnRepeat).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.bookPageView).setVisibility(ImageView.VISIBLE);
        findViewById(R.id.bookPageText).setVisibility(ImageView.VISIBLE);
        setMoveButtons();
        mAllowNextPage=true;
        placePageWords();
       // readPage();
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private void placePageWords(){
        String strCurrentText = mBookPages.get(mCurrentPage).get(5);

        strCurrentText = strCurrentText.replace(";", " ")
                .replace("&br", "<br/>").replace("\t","");
        strCurrentText = strCurrentText.replace("\r", "")
                .replace("&bt", "<br/>").replace("\n", "").trim();
        strCurrentText="<font color='#000000'>"+strCurrentText+"</font> ";
        mBookPageText.setText(Html.fromHtml(strCurrentText));


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
    ////////////////////////////////////////////////////////////////////////////////////////

    private Runnable audioRunnable = new Runnable() {
        @Override
        public void run() {

            long mAudioDuration;
            switch (mAudioPlayPosition) {
                case 0:
                default: {
                    mAllowNextPage = false;
                    mAudioDuration = playGeneralAudio(mBookPages.get(mCurrentPage)
                            .get(4).replace(".mp3",""));
                    mAudioPlayPosition++;
                    mAudioHandler.postDelayed(audioRunnable, mAudioDuration + 10);

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

    private Runnable wordHighlight = new Runnable() {
        @Override
        public void run() {
            mBookPageText.setText("");

            String strCurrentText = "";
            String allWords = mBookPages.get(mCurrentPage).get(5);
            String allHiglightTimes = mBookPages.get(mCurrentPage).get(6);
            String mHighlightColor="#cc3333";

            ArrayList<String> wordArray = new ArrayList<String>(Arrays.asList(allWords.split(";")));
            for(int i=0;i<wordArray.size();i++){
                wordArray.set(i, wordArray.get(i).replace("&br", "<br/>").replace("\t","")
                        .replace("\r", "").replace("\n", "").replace(" ",""));
            }
            ArrayList<String> highlightArray = new ArrayList<String>(
                    Arrays.asList(allHiglightTimes.split(";")));





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
                        strCurrentText += "<font color='"+mHighlightColor+"'>"
                                + wordArray.get(i).trim() + "</font> ";
                    else
                        strCurrentText += wordArray.get(i).trim() + " ";

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


    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        switch(id){
            default:

            case Constants.BOOKS_READ_ALOUD:{

                cursorLoader = new CursorLoader(this,
                        AppProvider.CONTENT_URI_BOOKS_DECODABLE, null, null, null, null);
                break;
           }
            case Constants.BOOK_PAGES_READ_ALOUD: {

                String mWhere="book_id="+mBookId;
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

            case Constants.BOOKS_READ_ALOUD:{
                createBookList(data);

                break;
            }
            case Constants.BOOK_PAGES_READ_ALOUD:{
                processBook(data);
                break;
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    /////////////////////////////////////////////////////////////////////////////////////////////


}