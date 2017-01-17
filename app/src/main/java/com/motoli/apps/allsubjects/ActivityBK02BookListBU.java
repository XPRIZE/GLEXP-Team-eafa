package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/13/2015.
 */
public class ActivityBK02BookListBU extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<ArrayList<String>> mAllBooks;

    private GridView mBooksGrid;
    private ArrayList list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_bk02_book_list_old);
        appData.addToClassOrder(19);
        appData.setActivityType(2);
        getLoaderManager().initLoader(Constants.BOOKS_READ_ALOUD, null, this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor cursor){
        mAllBooks=new ArrayList<>();

        if (cursor.moveToFirst()) {
            int mStoryOrder=0;
            for (int j = 0; j < cursor.getCount(); j++) {
                mAllBooks.add(new ArrayList<String>());

                mAllBooks.get(mStoryOrder).add(cursor.getString(
                        cursor.getColumnIndex("book_id")));                 //0
                mAllBooks.get(mStoryOrder).add(cursor.getString(
                        cursor.getColumnIndex("book_order")));			    //1
                mAllBooks.get(mStoryOrder).add(cursor.getString(
                        cursor.getColumnIndex("book_title")));			    //2
                mAllBooks.get(mStoryOrder).add(cursor.getString(
                        cursor.getColumnIndex("book_page_image")));         //3
                mAllBooks.get(mStoryOrder).add(cursor.getString(
                        cursor.getColumnIndex("book_type")));	            //4
                mAllBooks.get(mStoryOrder).add(cursor.getString(
                        cursor.getColumnIndex("book_number_of_pages")));    //5
                mAllBooks.get(mStoryOrder).add(cursor.getString(
                        cursor.getColumnIndex("book_font_size")));		    //6
                mAllBooks.get(mStoryOrder).add("1");                                                                //7
                mAllBooks.get(mStoryOrder).add(cursor.getString(
                        cursor.getColumnIndex("book_read")));		        //8
                mAllBooks.get(mStoryOrder).add(cursor.getString(
                        cursor.getColumnIndex("group_id")));		        //9
                mStoryOrder++;
                cursor.moveToNext();
            }
        }



        mBooksGrid = (GridView) findViewById(R.id.booksGrid);


        mBooksGrid.setAdapter(new ActivityBK02BookCover(this, mAllBooks));

        mBooksGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                list = (ArrayList) v.findViewById(R.id.tracingImage).getTag();

                openBook();
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void openBook(){
       // appData.setCurrentBook((ArrayList<String>) list);
        appData.setCurrentActivityName("ActivityBK02Book");
        Intent main = new Intent(this,ActivityBK02Book.class);
        startActivity(main);
        finish();
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;
        cursorLoader = new CursorLoader(this,
                AppProvider.CONTENT_URI_BOOKS_READ_ALOUD, null, null, null, null);
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
