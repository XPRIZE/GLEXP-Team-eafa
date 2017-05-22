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
 *
 * User database to gather books user is currently allow to read. Displays 8 books at a time. When
 * book is clicked application goes to ActivityBKBook to display and play audio of related book.
 */
public class ActivityBK02BookList extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{


    /**
     * mAllowMove lets or does not let application respond to a user clicking book or arrow
     */
    private boolean mAllowMove;

    /**
     * mSection is the current section of 8 books user is on. It starts at 0.
     */
    private int mSection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_bk02_book_list);
        mSection=0;
        mAllowMove = false;
        appData.addToClassOrder(19);
        appData.setActivityType(2);
        findViewById(R.id.btnBackward).setVisibility(ImageView.INVISIBLE);
        findViewById(R.id.btnForward).setVisibility(ImageView.INVISIBLE);
        getLoaderManager().initLoader(Constants.BOOKS_READ_ALOUD, null, this);
    }

    /**
     * displayScreen display up to 8 possible book images for that section of books the
     * user is current on. It sets the book info in a tag for use when image is click in
     * openBook(View).
     * @param mCursor this cursor is from onLoadFinished()
     */
    private void displayScreen(Cursor mCursor){
        ArrayList<HashMap<String,String>> mAllBooks = new ArrayList<>();

        int mBookCount=0;
        if (mCursor.moveToFirst()) {
            int mStoryOrder=0;
            do{
                mAllBooks.add(new HashMap<String, String>());

                mAllBooks.get(mStoryOrder).put("book_id",
                        mCursor.getString(mCursor.getColumnIndex("book_id")));
                mAllBooks.get(mStoryOrder).put("book_order",
                        mCursor.getString(mCursor.getColumnIndex("book_order")));
                mAllBooks.get(mStoryOrder).put("book_title",
                        mCursor.getString(mCursor.getColumnIndex("book_title")));
                mAllBooks.get(mStoryOrder).put("book_page_image",
                        mCursor.getString(mCursor.getColumnIndex("book_page_image")));
                mAllBooks.get(mStoryOrder).put("book_type",
                        mCursor.getString(mCursor.getColumnIndex("book_type")));
                mAllBooks.get(mStoryOrder).put("book_number_of_pages",
                        mCursor.getString(mCursor.getColumnIndex("book_number_of_pages")));
                mAllBooks.get(mStoryOrder).put("book_font_size",
                        mCursor.getString(mCursor.getColumnIndex("book_font_size")));
                mAllBooks.get(mStoryOrder).put("visible","1");
                mAllBooks.get(mStoryOrder).put("book_read",
                        mCursor.getString(mCursor.getColumnIndex("book_read")));
                mAllBooks.get(mStoryOrder).put("group_id",
                        mCursor.getString(mCursor.getColumnIndex("group_id")));
                mBookCount=mCursor.getInt(mCursor.getColumnIndex("book_count"));
                mStoryOrder++;
            }while(mCursor.moveToNext());
        }

        mCursor.close();

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
        /*
        loops through all images allowed to place book cover and set tag to use with OpenBook
         */
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

            mBookImage.setImageDrawable(null);
            mBookImage.setImageResource(0);
            mBookImage.setImageResource(android.R.color.transparent);



            if (mBookSize >= mPosition) {
                String mText=mAllBooks.get(mPosition-1).get("book_title")
                        .replace("&sq:", "&rsquo;");
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

        mAllowMove = true;
    }

    /**
     * Moves back one page of the books if available
     * @param view is reference to btnBackword image
     */
    public void moveBackward(View view){
        if(mAllowMove) {
            mAllowMove=false;
            mSection--;
            for (int mPosition = 1; mPosition <= 8; mPosition++) {
                ImageView mBookImage;
                TextView mBookText;
                ImageView mBookCup;

                if (mPosition == 1) {
                    mBookText = ((TextView) findViewById(R.id.text1));
                    mBookCup = ((ImageView) findViewById(R.id.cup1));
                    mBookImage = ((ImageView) findViewById(R.id.image1));
                } else if (mPosition == 2) {
                    mBookText = ((TextView) findViewById(R.id.text2));
                    mBookCup = ((ImageView) findViewById(R.id.cup2));
                    mBookImage = ((ImageView) findViewById(R.id.image2));
                } else if (mPosition == 3) {
                    mBookText = ((TextView) findViewById(R.id.text3));
                    mBookCup = ((ImageView) findViewById(R.id.cup3));
                    mBookImage = ((ImageView) findViewById(R.id.image3));
                } else if (mPosition == 4) {
                    mBookText = ((TextView) findViewById(R.id.text4));
                    mBookCup = ((ImageView) findViewById(R.id.cup4));
                    mBookImage = ((ImageView) findViewById(R.id.image4));
                } else if (mPosition == 5) {
                    mBookText = ((TextView) findViewById(R.id.text5));
                    mBookCup = ((ImageView) findViewById(R.id.cup5));
                    mBookImage = ((ImageView) findViewById(R.id.image5));
                } else if (mPosition == 6) {
                    mBookText = ((TextView) findViewById(R.id.text6));
                    mBookCup = ((ImageView) findViewById(R.id.cup6));
                    mBookImage = ((ImageView) findViewById(R.id.image6));
                } else if (mPosition == 7) {
                    mBookText = ((TextView) findViewById(R.id.text7));
                    mBookCup = ((ImageView) findViewById(R.id.cup7));
                    mBookImage = ((ImageView) findViewById(R.id.image7));
                } else {
                    mBookText = ((TextView) findViewById(R.id.text8));
                    mBookCup = ((ImageView) findViewById(R.id.cup8));
                    mBookImage = ((ImageView) findViewById(R.id.image8));
                }

                mBookCup.setAlpha(0.0f);
                mBookText.setText("");
                mBookImage.setAlpha(0.0f);
            }

            getLoaderManager().restartLoader(Constants.BOOKS_READ_ALOUD, null, this);
        }
    }

    /**
     * Moves forward one page of the books if available
     * @param view is reference to btnForward image
     */
    public void moveForward(View view){
        if(mAllowMove) {
            mAllowMove=false;
            mSection++;
            for (int mPosition = 1; mPosition <= 8; mPosition++) {
                ImageView mBookImage;
                TextView mBookText;
                ImageView mBookCup;

                if (mPosition == 1) {
                    mBookText = ((TextView) findViewById(R.id.text1));
                    mBookCup = ((ImageView) findViewById(R.id.cup1));
                    mBookImage = ((ImageView) findViewById(R.id.image1));
                } else if (mPosition == 2) {
                    mBookText = ((TextView) findViewById(R.id.text2));
                    mBookCup = ((ImageView) findViewById(R.id.cup2));
                    mBookImage = ((ImageView) findViewById(R.id.image2));
                } else if (mPosition == 3) {
                    mBookText = ((TextView) findViewById(R.id.text3));
                    mBookCup = ((ImageView) findViewById(R.id.cup3));
                    mBookImage = ((ImageView) findViewById(R.id.image3));
                } else if (mPosition == 4) {
                    mBookText = ((TextView) findViewById(R.id.text4));
                    mBookCup = ((ImageView) findViewById(R.id.cup4));
                    mBookImage = ((ImageView) findViewById(R.id.image4));
                } else if (mPosition == 5) {
                    mBookText = ((TextView) findViewById(R.id.text5));
                    mBookCup = ((ImageView) findViewById(R.id.cup5));
                    mBookImage = ((ImageView) findViewById(R.id.image5));
                } else if (mPosition == 6) {
                    mBookText = ((TextView) findViewById(R.id.text6));
                    mBookCup = ((ImageView) findViewById(R.id.cup6));
                    mBookImage = ((ImageView) findViewById(R.id.image6));
                } else if (mPosition == 7) {
                    mBookText = ((TextView) findViewById(R.id.text7));
                    mBookCup = ((ImageView) findViewById(R.id.cup7));
                    mBookImage = ((ImageView) findViewById(R.id.image7));
                } else {
                    mBookText = ((TextView) findViewById(R.id.text8));
                    mBookCup = ((ImageView) findViewById(R.id.cup8));
                    mBookImage = ((ImageView) findViewById(R.id.image8));
                }

                mBookCup.setAlpha(0.0f);
                mBookText.setText("");
                mBookImage.setAlpha(0.0f);
            }

            getLoaderManager().restartLoader(Constants.BOOKS_READ_ALOUD, null, this);
        }
    }

    /**
     * Based on tag set in displayScreen() the tag is a HashMap of book in order to start
     * the correct the activity displaying the current book chosen. Current book is set in to
     * Motoli_Application class for storage
     * @param view is called image1-8
     */
    public void openBook(View view){

        if(mAllowMove) {
            mAllowMove=false;
            /*
            uncehcked tag. Need to find wat to solve this.
             */
            HashMap<String, String> mList = (HashMap) view.getTag();

            appData.setCurrentBook(mList);
            appData.setCurrentActivityName("ActivityBK02Book");
            Intent main = new Intent(this, ActivityBK02Book.class);
            startActivity(main);
            finish();
        }
    }

    /**
     * Gathers booksin current section to display from AppProvider (database)
     * @param id this is just a defauly id
     * @param args is null as none are needed
     * @return cursorLoader to cause onLoadFinished to be called with associated Cursro
     */
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
