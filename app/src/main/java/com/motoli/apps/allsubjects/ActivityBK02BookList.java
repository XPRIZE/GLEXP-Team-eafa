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
public class ActivityBK02BookList extends Activity_General_Parent implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<ArrayList<String>> mAllBooks;
    private GridView mBooksGrid;
    private ArrayList list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_bk02_book_list);
        appData.addToClassOrder(19);

        getLoaderManager().initLoader(MotoliConstants.BOOKS_READ_ALOUD, null, this);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playClickSound();
                moveBackwords();
            }
        });

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayScreen(Cursor cursor){
        mAllBooks=new ArrayList<ArrayList<String>>();


        int currentGroup=0;
        int groupCount=0;

        if (cursor.moveToFirst()) {
            int mStoryOrder=0;
            for (int j = 0; j < cursor.getCount(); j++) {

//				if(groupNumber%3)
                mAllBooks.add(new ArrayList<String>());

                mAllBooks.get(mStoryOrder).add(cursor.getString(cursor.getColumnIndex("book_id")));                 //0
                mAllBooks.get(mStoryOrder).add(cursor.getString(cursor.getColumnIndex("book_order")));			    //1
                mAllBooks.get(mStoryOrder).add(cursor.getString(cursor.getColumnIndex("book_title")));			    //2
                mAllBooks.get(mStoryOrder).add(cursor.getString(cursor.getColumnIndex("book_page_image")));         //3
                mAllBooks.get(mStoryOrder).add(cursor.getString(cursor.getColumnIndex("book_type")));	            //4
                mAllBooks.get(mStoryOrder).add(cursor.getString(cursor.getColumnIndex("book_number_of_pages")));    //5
                mAllBooks.get(mStoryOrder).add(cursor.getString(cursor.getColumnIndex("book_font_size")));		    //6
                mAllBooks.get(mStoryOrder).add("1");                                                                //7

                mStoryOrder++;
                cursor.moveToNext();
            }
        }

        //AllGroups = new ArrayList<ArrayList<String>>(appData.getAllGroups());
        //AllSections = new ArrayList<ArrayList<String>>(appData.getAllSections());



        mBooksGrid = (GridView) findViewById(R.id.booksGrid);


        mBooksGrid.setAdapter(new ActivityBK02BookCover(this, mAllBooks));

        mBooksGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                list = (ArrayList) v.findViewById(R.id.grid_item_image).getTag();

                //String mBookID = (String) list.get(0);
                //String mCurrentUserLevel = (String) list.get(8);


                openBook();

            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private void openBook(){
        appData.setCurrentBook((ArrayList<String>) list);
        appData.setmCurrentActivityName("ActivityBK02Book");
        Intent main = new Intent(this,ActivityBK02Book.class);
        startActivity(main);
        finish();

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id){
            default:

            case MotoliConstants.BOOKS_READ_ALOUD:{
                /*
                projection = new String[] {
                        Database.Groups_Phase_Sections_Activities.GPSA_ID,
                        Database.Groups_Phase_Sections_Activities.GROUP_ID,
                        Database.Groups_Phase_Sections_Activities.PHASE_ID,
                        Database.Groups_Phase_Sections_Activities.SECTION_ID,
                        Database.Groups_Phase_Sections_Activities.ACTIVITY_ID,
                        Database.Activities.ACTIVITY_NAME,
                        Database.Activities.ACTIVITY_IMG};
                String gps_tblname=Database.Groups_Phase_Sections_Activities.GROUPS_PHASE_SECTIONS_ACTIVITIES_TABLE_NAME;

                String selection=gps_tblname+"."+Database.Groups_Phase_Sections_Activities.GROUP_ID+"="+args.getInt("group_id")
                        +" AND "+gps_tblname+"."+Database.Groups_Phase_Sections_Activities.PHASE_ID+"="+args.getInt("phase_id")
                        +" AND "+gps_tblname+"."+Database.Groups_Phase_Sections_Activities.SECTION_ID+"="+args.getInt("section_id");
                String order=Database.Activities.ACTIVITIES_TABLE_NAME+"."+Database.Activities.ACTIVITY_ORDER+" ASC";

                */
                cursorLoader = new CursorLoader(this,
                        MotoliContentProvider.CONTENT_URI_BOOKS_READ_ALOUD, null, null, null, null);
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

            case MotoliConstants.BOOKS_READ_ALOUD:{
                displayScreen(data);

                break;
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }
}
