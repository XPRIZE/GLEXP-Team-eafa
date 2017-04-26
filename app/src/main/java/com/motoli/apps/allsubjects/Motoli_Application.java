package com.motoli.apps.allsubjects;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 *
 * 3/5/2016
 * This class was vastly redone along with Motoli_Application on 3/5/2016
 * due to various calls to AppProvider
 * that were not need in anyway. This could have been causing some delay in the app
 * and was loading useless global variables wasting memory. ADMB
 *
 * @author Aaron Borsay
 */
public class Motoli_Application extends Application {

    private static final String ADMIN_PASSWORD="xprize";

    private  Typeface mNumberFontTypeface;

    private int mTracingType=0;

    private  Typeface mCurrentFontType;

    private boolean mIsAdminEnabled=false;
    private ArrayList<Integer> mClassOrder;
    private String mCurrentUserID;

    private String mCurrentActivityName;

    private int mUpdateType=0;

    private ArrayList<HashMap<String,String>> mAppUserCurrentGPL;
    
    private HashMap<String,String> mCurrentBook;
    private HashMap<String, String> mCurrentGroupSectionPhase;
    private ArrayList<String>  mCurrentActivity;


    private int mCurrentSection=0;

    private int mCurrentVideoID=0;


    private int mActivityType=0;

    private HashMap<String,String> mCanvasList;




    public Motoli_Application() {
        Log.d(Constants.LOGCAT, "Starting Motoli Application");

    }


    @Override
    public void onCreate() {
        super.onCreate();


        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);


        mClassOrder=new ArrayList<>();
        mCurrentActivityName="SplashPage";

        /*
         * The fonts are statically set here. Currently the Italic and the Italic Bold are 
         * set but never used in the app. Even despite that being unused data it 
         * must remain in case it is ever in use.
         */

        mCurrentFontType=Typeface.createFromAsset(getAssets(),
                "fonts/Convergence-Regular.ttf");
//               "fonts/Viga-Regular.ttf");/* "fonts/AndikaNewBasic.ttf");*/

        mNumberFontTypeface=Typeface.createFromAsset(getAssets(),
                "fonts/primer_print_bold.ttf"); //this is for the numbers


        mCurrentSection=0;
        Log.d(Constants.LOGCAT, "Creating Motoli Application");
    }


    /**
     * Tests is password inputted by user is valid for Admin abilities
     * @param mPassword password typed by use
     * @return boolean is password correct
     */
    public boolean inputAdminPassword(String mPassword){
        if(mPassword.toLowerCase().equals(ADMIN_PASSWORD)){
            mIsAdminEnabled=true;
            return true;
        }else{
            mIsAdminEnabled=false;
            return false;
        }
    }

    /**
     * Responds to Launch_Platform is Admin is available
     * @return true is admin is available
     */
    public boolean isAdminEnabled(){
        return mIsAdminEnabled;
    }

    /**
     * Returns type of tracing for use in ActivityTR02 to allow the if to return to that type
     * auto after tracing done
     * @return mTracingType int
     */
    public int getTracingType(){
        return mTracingType;
    }

    /**
     * Sets tracing type for use @see getTracingType()
     * @param mTracingType type set in ActivityTR02 when related icon is clicked
     */
    public void setTracingType(int mTracingType){
        this.mTracingType=mTracingType;
    }

    /**
     * Stores list data for simple return when tracing is done from ActivityTR02
     * @param mCanvasList Is set list based upon current mTracingType
     */
    public void placeCanvasList(HashMap<String,String> mCanvasList){
        this.mCanvasList=mCanvasList;
    }

    /**
     * Return stored list from ActivityTR
     * @return HashMap of current tracing list data
     */
    public HashMap<String,String> getCanvasList(){
        return mCanvasList;
    }

    /**
     * This is for debugging to allow activity text to appear in each activity for test purposes.
     * This is set statically by Motoli_Application
     * @return      True if allowed, otherwise false
     */
    public boolean getAllowActivityText(){
        /* boolean mAllowActivityText=true;*/
        return true;
    }

    /**
     * @see AppProvider Cursor query(@NonNull Uri uri,  String[] projection, String selection,
     *                     String[] selectionArgs, String sortOrder)
     *                     Case GROUPS_PHASE_LEVELS_UPDATE
     * Used to speed up Updating of data in DB
     * @param mUpdateType   1 is to do an update for all group phase levels
     *                      2 is only for certain groups.
     */
    public void setUpdateType(int mUpdateType){
        this.mUpdateType=mUpdateType;
    }

    /**
     * @link setUpdateType(int mUpdateType)
     * @return boolean  1 is to do an update for all group phase levels
     *                  2 is only for certain groups.
     */
    public int getUpdateType(){
        return this.mUpdateType;
    }

    /***************************************************************************
     * void setActivityType(int mActivityType)
     * @param mActivityType
     * 0 = User select
     * 1 = Launch Platform
     * 2 = Activities Platform
     * 3 = Activity BK, CS LT, OP, QN, RS, SD, SH, WD
     * 4 = Video Activity VL
     * 5 = Book Activity BK02
     */
    public  void setActivityType(int mActivityType){
        this.mActivityType=mActivityType;
    }

    /****************************************************************************
     * int getActivityType()
     * @return mActivityType
     */
    public int getActivityType(){
        return mActivityType;
    }


    /****************************************************************************
     * setCurrentSection(int mSection)
     * is used to help Activities_Platform set the section so the screen in Launch_Platform will
     * go back to previous location
     * @param   mSection    Current section app is in
     */
    public void setCurrentSection(int mSection){
        mCurrentSection=mSection;
    }

    /****************************************************************************
     * getCurrentSection()
     * helps to move Launch_Platform screen back to location where the person had last scrolled to
     * @return mCurrentSection
     */
    public int getCurrentSection(){
        return mCurrentSection;
    }

    /****************************************************************************
     * getActivityName()
     * This is used by Activity_General _Parent to print the activity name
     * This is for debug purposes alone. Must be not used in release app
     * @return mCurrentActivityName
     */
    public String getActivityName(){
        return mCurrentActivityName;
    }

    /****************************************************************************
     * setCurrentActivityName(String mActivityName)
     * This is used for debug person alone to set activity name to be used by a function
     * in ActivitiesMasterParent
     * @param   mActivityName   Sets the activity name used for debugging purposes
     */
    public void setCurrentActivityName(String mActivityName){
        mCurrentActivityName=mActivityName;
    }

    /****************************************************************************
     * getCurrentVideoID()
     * This is used in Activities_Platform in function
     * private void moveToVideo()
     * to move to video assocated with current group
     * @return mCurrentVideoID
     */
    public int getCurrentVideoID(){
        return mCurrentVideoID;
    }

    /****************************************************************************
     * setCurrentVideoID(int mCurrentVideoID)
     * Set video in Launch_Platform before user goes to Activities_Platform to allow user to go 
     * to correct video associated with current group.
     * @param mCurrentVideoID current ID video is one
     */
    public void setCurrentVideoID(int mCurrentVideoID){
        this.mCurrentVideoID=mCurrentVideoID;
    }

    /****************************************************************************
     * Typeface getCurrentFontType()
     * This is set in static but the font is needed throughout the app
     * @return mCurrentFontType
     */
     public Typeface getCurrentFontType(){
        return this.mCurrentFontType;
    }




    /****************************************************************************
     * Typeface getCurrentFontTypeQuivira()
     * This is set in static but the font is needed for UNICODE reasons
     * @return mNumberFontTypeface
     */
    public Typeface getNumberFontTypeface(){
        return this.mNumberFontTypeface;
    }



    /****************************************************************************
     * addToClassOrder(Integer classNumber)
     * This is used to help the app move to correct activities.
     * Each activity sets this to a stack list
     * @param classNumber Sets stack number. Usually is 5 for most general activities
     */
     public void addToClassOrder(Integer classNumber){
        mClassOrder.add(classNumber);
    }



    /****************************************************************************
     * void  setClassesForLaunch()
     * Used when the app starts in order to allow proper movement with back button
     * This moves past the stack for users currently and goes right to Launch_Platform order
     */
    public void setClassesForLaunch(){
        mClassOrder.clear();
        mClassOrder=new ArrayList<>();
        mClassOrder.add(0);
        mClassOrder.add(1);
        mClassOrder.add(2);
    }

    /****************************************************************************
     * void setClassesForActivities()
     * This is a reset of the stack order when the user has gone
     * to the Activities_Platform activity
     */
    public void setClassesForActivities(){

        mClassOrder.clear();
        mClassOrder=new ArrayList<>();
        mClassOrder.add(0);
        mClassOrder.add(1);
        mClassOrder.add(2);
        mClassOrder.add(3);
    }



    /****************************************************************************
     * ArrayList<String> getCurrentActivity()
     * Used in almost every game type activity to help with adding the points
     * to the database based upon the users guesses
     * @return  ArrayList of current activity data
     */
    public ArrayList<String> getCurrentActivity(){
        return mCurrentActivity;
    }

    /****************************************************************************
     * void setCurrentActivity(ArrayList<String> mCurrentActivity){
     * This is set in Launch_Platform in goto_activity(String activity_id).
     * This is used by the gaming activates to ad points based upon users guesses
     * @param mCurrentActivity  sets ArrayList of activity data for use in activity
     */
     public void setCurrentActivity(ArrayList<String> mCurrentActivity){
        this.mCurrentActivity=mCurrentActivity;
    }

    /****************************************************************************
     * ArrayList<String> getCurrentBook(){
     * Used in ActivityBK02Book to get which current book was chosen in
     * ActivityBK02BookList
     * @return  nCurrentBook HashMap of data of current book
     */
     public HashMap<String, String> getCurrentBook(){
        return mCurrentBook;
    }

    /****************************************************************************
     *  void setCurrentBook(ArrayList<String> mCurrentBook)
     *  This is used in ActivityBK02BookList to set the mCurrentBook
     *  to be used in ActivityBK02Book
     * @param mCurrentBook  Set data of current book
     */
     public void setCurrentBook(HashMap<String,String> mCurrentBook){
        this.mCurrentBook=new HashMap<>(mCurrentBook);
    }


    /****************************************************************************
     * setCurrentUserID(String mCurrentUserID)
     * This is to set the mCurrentUserID to be used throughout the app.
     * The default is current 0 but in case we ever decide to add the ability
     * to chose between different users this user id must remain
     * @param mCurrentUserID sets current ID (0)
     */
    public void setCurrentUserID(String mCurrentUserID){
        this.mCurrentUserID=mCurrentUserID;

    }


    /****************************************************************************
     * getCurrentUserID()
     * Used throughout the app to get mCurrentUserID
     * The default user id is set at 0, but in case we ever decide to add the ability
     * to chose between different users this user id must remain
     * @return current ID (0)
     */
    public String getCurrentUserID(){
        return this.mCurrentUserID;
    }


    /****************************************************************************
     * setCurrentGroup_Section_Phase(String group_id, String phase_id,
     * String section_id, String current_level){
     * is very important for the app must stay
     * @param group_id group user is current in
     * @param phase_id phase user is current in
     * @param section_id section user is current in. referring to icon in Launch_Platform
     * @param current_level current level of user associated with group and phase
     */
    public void setCurrentGroup_Section_Phase(String group_id, String phase_id,
                                              String section_id, String current_level){
        mCurrentGroupSectionPhase= new HashMap<>();
        mCurrentGroupSectionPhase.put("group_id",group_id);
        mCurrentGroupSectionPhase.put("phase_id",phase_id);
        mCurrentGroupSectionPhase.put("section_id",section_id);
        mCurrentGroupSectionPhase.put("current_level",current_level);


    }
    
    /**
     * @link void setCurrentGroup_Section_Phase(String group_id, String phase_id,
     *                              String section_id, String current_level)
     * @return data of current group, phase, section and related level
     */
    public HashMap<String,String> getCurrentGroup_Section_Phase(){
        return this.mCurrentGroupSectionPhase;
    }


    /****************************************************************************
     * void setAppUserCurrentGPL(ArrayList<ArrayList<String>> appUserCurrentGPL)
     * This is used in Activities_Platform to set a separate location of the GPL
     * While ever time and update is done this is called the purpose for use
     * in CS01,CS02 alone  as while those are syllable activities they are 
     * different phases then the RS
     * @param appUserCurrentGPL data set ofr current user GPL (group, phase, level)
     */
    public void setAppUserCurrentGPL(ArrayList<HashMap<String,String>> appUserCurrentGPL){
        mAppUserCurrentGPL=new ArrayList<>(appUserCurrentGPL);
    }

    /****************************************************************************
     *  ArrayList<ArrayList<String>> getAppUserCurrentGPL()
     *  This is used only in ActivityCS01 and ActivityCS02 as while they have the same group
     *  as the RS syllable activities they are using different phases
     * @return users current GPL (group, phase, level)
     */
     public ArrayList<HashMap<String,String>> getAppUserCurrentGPL(){
            return  mAppUserCurrentGPL;
     }



}
