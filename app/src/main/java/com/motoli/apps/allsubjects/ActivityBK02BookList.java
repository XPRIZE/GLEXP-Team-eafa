package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/13/2015.
 */
public class ActivityBK02BookList extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{



    private int mSection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_bk02_book_list);
        mSection=0;
        appData.addToClassOrder(19);
        appData.setActivityType(2);
        findViewById(R.id.btnBackward).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.btnForward).setVisibility(ImageView.INVISIBLE);
        getLoaderManager().initLoader(Constants.BOOKS_READ_ALOUD, null, this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor cursor){
        ArrayList<HashMap<String,String>> mAllBooks = new ArrayList<>();

        int mBookCount=0;
        if (cursor.moveToFirst()) {
            int mStoryOrder=0;
            for (int j = 0; j < cursor.getCount(); j++) {
                mAllBooks.add(new HashMap<String, String>());

                mAllBooks.get(mStoryOrder).put("book_id",
                        cursor.getString(cursor.getColumnIndex("book_id")));                 //0
                mAllBooks.get(mStoryOrder).put("book_order",
                        cursor.getString(cursor.getColumnIndex("book_order")));			    //1
                mAllBooks.get(mStoryOrder).put("book_title",
                        cursor.getString(cursor.getColumnIndex("book_title")));			    //2
                mAllBooks.get(mStoryOrder).put("book_page_image",
                        cursor.getString(cursor.getColumnIndex("book_page_image")));         //3
                mAllBooks.get(mStoryOrder).put("book_type",
                        cursor.getString(cursor.getColumnIndex("book_type")));	            //4
                mAllBooks.get(mStoryOrder).put("book_number_of_pages",
                        cursor.getString(cursor.getColumnIndex("book_number_of_pages")));    //5
                mAllBooks.get(mStoryOrder).put("book_font_size",
                        cursor.getString(cursor.getColumnIndex("book_font_size")));		    //6
                mAllBooks.get(mStoryOrder).put("visible","1");                                                                //7
                mAllBooks.get(mStoryOrder).put("book_read",
                        cursor.getString(cursor.getColumnIndex("book_read")));		        //8
                mAllBooks.get(mStoryOrder).put("group_id",
                        cursor.getString(cursor.getColumnIndex("group_id")));		        //9
                mBookCount=cursor.getCount();//.getInt(cursor.getColumnIndex("book_count"));
                mStoryOrder++;
                cursor.moveToNext();
            }
        }

        if(mSection>0) {
            findViewById(R.id.btnBackward).setVisibility(ImageView.VISIBLE);
        }else {
            findViewById(R.id.btnBackward).setVisibility(ImageView.INVISIBLE);
        }

        findViewById(R.id.btnForward).setVisibility(ImageView.INVISIBLE);
        if(mBookCount>8){
            if((mSection*8)<=(mBookCount-8)){
                findViewById(R.id.btnForward).setVisibility(ImageView.VISIBLE);
            }
        }

        Log.d(Constants.LOGCAT, "book list");

        int mBookSize = mAllBooks.size();
        for(int mPosition=1; mPosition<=8; mPosition++) {
            ImageView mBookImage;
            TextView mBookText;
            ImageView mBookCup;

            if(mPosition==1) {
                mBookText = ((TextView) findViewById(R.id.text1));
                mBookCup = ((ImageView) findViewById(R.id.cup1));
                mBookImage = ((ImageView) findViewById(R.id.image1));
            }else if(mPosition==2){
                mBookText = ((TextView) findViewById(R.id.text2));
                mBookCup = ((ImageView) findViewById(R.id.cup2));
                mBookImage = ((ImageView) findViewById(R.id.image2));
            }else if(mPosition==3){
                mBookText = ((TextView) findViewById(R.id.text3));
                mBookCup = ((ImageView) findViewById(R.id.cup3));
                mBookImage = ((ImageView) findViewById(R.id.image3));
            }else if(mPosition==4){
                mBookText = ((TextView) findViewById(R.id.text4));
                mBookCup = ((ImageView) findViewById(R.id.cup4));
                mBookImage = ((ImageView) findViewById(R.id.image4));
            }else if(mPosition==5){
                mBookText = ((TextView) findViewById(R.id.text5));
                mBookCup = ((ImageView) findViewById(R.id.cup5));
                mBookImage = ((ImageView) findViewById(R.id.image5));
            }else if(mPosition==6){
                mBookText = ((TextView) findViewById(R.id.text6));
                mBookCup = ((ImageView) findViewById(R.id.cup6));
                mBookImage = ((ImageView) findViewById(R.id.image6));
            }else if(mPosition==7){
                mBookText = ((TextView) findViewById(R.id.text7));
                mBookCup = ((ImageView) findViewById(R.id.cup7));
                mBookImage = ((ImageView) findViewById(R.id.image7));
            }else{
                mBookText = ((TextView) findViewById(R.id.text8));
                mBookCup = ((ImageView) findViewById(R.id.cup8));
                mBookImage = ((ImageView) findViewById(R.id.image8));
            }


            if (mBookSize >= mPosition) {
                String mText=mAllBooks.get(mPosition-1).get("book_title").replace("&sq:", "&rsquo;");
                String mImage = mAllBooks.get(mPosition-1).get("book_page_image")
                        .replace(".png", "").replace(".jpg", "");
                mBookCup.setAlpha(0.0f);
                if(mAllBooks.get(mPosition-1).get("book_read").equals("0")){
                    mBookCup.setAlpha(1.0f);
                }
                mBookText.setText(mText);
                mBookImage.setTag(mAllBooks.get(mPosition-1));
                mBookImage.setAlpha(1.0f);
                mBookImage.setImageResource(getResources().getIdentifier(mImage,
                        "drawable", getPackageName()));
            }else{
                mBookCup.setAlpha(0.0f);
                mBookText.setText("");
                mBookImage.setAlpha(0.0f);
            }
        }
            
/*
        mBooksGrid = (GridView) findViewById(R.id.booksGrid);


        mBooksGrid.setAdapter(new ActivityBK02BookCover(this, mAllBooks));

        mBooksGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                list = (ArrayList) v.findViewById(R.id.tracingImage).getTag();

                openBook();
            }
        });
        */
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    public void moveBackward(View view){
        mSection--;
        getLoaderManager().restartLoader(Constants.BOOKS_READ_ALOUD, null, this);
    }

    public void moveForward(View view){
        mSection++;
        getLoaderManager().restartLoader(Constants.BOOKS_READ_ALOUD, null, this);
    }
    public void openBook(View view){

        HashMap<String,String> mList = (HashMap) view.getTag();

        appData.setCurrentBook(mList);
        appData.setCurrentActivityName("ActivityBK02Book");
        Intent main = new Intent(this,ActivityBK02Book.class);
        startActivity(main);
        finish();
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;

        String mSortOrder = "LIMIT " + String.valueOf(mSection*8)+", 8";

        cursorLoader = new CursorLoader(this,
                AppProvider.CONTENT_URI_BOOKS_READ_ALOUD, null, null, null, mSortOrder);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        appData = ((Motoli_Application) getApplicationContext());
        displayScreen(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }
}
