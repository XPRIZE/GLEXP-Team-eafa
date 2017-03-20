package com.motoli.apps.allsubjects;
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
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;

public class Motoli_Application extends Application {

    private static final String ADMIN_PASSWORD="xprize";

    private  Typeface mNumberFontTypeface;

    private int mTracingType=0;

    private  Typeface mCurrentFontType;
    private  Typeface mCurrentFontTypeBold;
    private static Model mModel;

    private boolean mIsAdminEnabled=false;

    private int mDatabaseVersion=0;

    private boolean mAllowActivityText=true;

    private String mCurrentUserID;

    private String mCurrentActivityName;

    private int mUpdateType=0;

    private ArrayList<HashMap<String,String>> mAppUserCurrentGPL;
    
    private HashMap<String,String> mCurrentBook;
    private HashMap<String, String> mCurrentGroupSectionPhase;
    private ArrayList<String>  mCurrentActivity;

    private ArrayList<Integer> mClassOrder;

    private int mCurrentSection=0;

    private int mCurrentVideoID=0;
    private static AssetManager mAssetManager;


    private int mActivityType=0;

    private HashMap<String,String> mCanvasList;


    static{
        mAssetManager = null;
    }


    public Motoli_Application() {
        Log.d(Constants.LOGCAT, "Starting Motoli Application");

    }


    @Override
    public void onCreate() {
        super.onCreate();
        mClassOrder=new ArrayList<Integer>();
        mCurrentActivityName="SplashPage";

        /**
         * The fonts are statically set here. Currently the Italic and the Italic Bold are 
         * set but never used in the app. Even despite that being unused data it 
         * must remain in case it is ever in use.
         */
        mAssetManager = getAssets();
        //mModel = new Model(mAssetManager);

        mCurrentFontType=Typeface.createFromAsset(getAssets(),
                "fonts/Convergence-Regular.ttf");
//               "fonts/Viga-Regular.ttf");/* "fonts/AndikaNewBasic.ttf");*/
        mCurrentFontTypeBold=Typeface.createFromAsset(getAssets(), 
                "fonts/AndikaNewBasic-B.ttf");
        mNumberFontTypeface=Typeface.createFromAsset(getAssets(),
                "fonts/primer_print_bold.ttf"); //this is for the numbers


        mCurrentSection=0;
        Log.d(Constants.LOGCAT, "Creating Motoli Application");
    }

    public boolean inputAdminPassword(String mPassword){
        if(mPassword.equals(ADMIN_PASSWORD)){
            mIsAdminEnabled=true;
            return true;
        }else{
            mIsAdminEnabled=false;
            return false;
        }
    }

    public boolean isAdminEnabled(){
        return mIsAdminEnabled;
    }
    public int getTracingType(){
        return mTracingType;
    }

    public void setTracingType(int mTracingType){
        this.mTracingType=mTracingType;
    }

    public void placeCanvasList(HashMap<String,String> mCanvasList){
        this.mCanvasList=mCanvasList;
    }

    public boolean getAllowActivityText(){
        return mAllowActivityText;
    }


    public void setAllowActivityText(boolean mAllowActivityText){
        this.mAllowActivityText=mAllowActivityText;
    }


    public HashMap<String,String> getCanvasList(){
        return mCanvasList;
    }

    public int getDatabaseVersion(){
        return mDatabaseVersion;
    }

    public void setDatabaseVersion(int mDatabaseVersion){
        this.mDatabaseVersion = mDatabaseVersion;
    }

    public void setUpdateType(int mUpdateType){
        this.mUpdateType=mUpdateType;
    }



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
     * @param mSection
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
     * @param mActivityName
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
     * @param mCurrentVideoID
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
     * Typeface getCurrentFontTypeBold()
     * This is set in static but the font is needed throughout the app
     * @return
     */
    public Typeface getCurrentFontTypeBold(){
        return this.mCurrentFontTypeBold;
    }


    /****************************************************************************
     * Typeface getCurrentFontTypeQuivira()
     * This is set in static but the font is needed for UNICODE reasons
     * @return
     */


    public Typeface getNumberFontTypeface(){
        return this.mNumberFontTypeface;
    }



    /***************************************************************************
     *
     * @return
     */

    public static Model getModel() {

        return mModel;
    }
/* */
    /****************************************************************************
     * addToClassOrder(Integer classNumber)
     * This is used to help the app move to correct activities.
     * Each activity sets this to a stack list
     * @param classNumber
     */
     public void addToClassOrder(Integer classNumber){

        mClassOrder.add(classNumber);
    }

    /****************************************************************************
     * void removeLastClassOrder()
     * This is used to make sure the video activities move to the correct location when the
     * back button is pressed
     */
     public void removeLastClassOrder(){
        mClassOrder.remove(mClassOrder.size() - 1);
    }

    /****************************************************************************
     * void  setClassesForLaunch()
     * Used when the app starts in order to allow proper movement with back button
     * This moves past the stack for users currently and goes right to Launch_Platform order
     */
    public void setClassesForLaunch(){
        mClassOrder.clear();
        mClassOrder=new ArrayList<Integer>();
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
        mClassOrder=new ArrayList<Integer>();
        mClassOrder.add(0);
        mClassOrder.add(1);
        mClassOrder.add(2);
        mClassOrder.add(3);
    }

    /****************************************************************************
     * int getPreviousClass()
     * Returns previous class order from stack in order to ensure that the core 0-3
     * is set based on other activities. Again this makes sure that movement
     * between activities and leaving of the app is correct.
     * @return
     */
    public int getPreviousClass(){
        int mClassOrderSize=mClassOrder.size();
        int mPreviousClassNumber=0;
        if(mClassOrderSize-2<0){
            mClassOrder=new ArrayList<Integer>();
            mClassOrder.add(0);
            mClassOrder.add(1);
            mClassOrder.add(2);
            mPreviousClassNumber = mClassOrder.get(0);
        }else {
            mPreviousClassNumber = mClassOrder.get(mClassOrderSize - 2);
            mClassOrder.remove(mClassOrderSize-1);
        }

        mClassOrderSize=mClassOrder.size();
        mClassOrder.remove(mClassOrderSize - 1);
        return mPreviousClassNumber;
    }


    /****************************************************************************
     * ArrayList<String> getCurrentActivity()
     * Used in almost every game type activity to help with adding the points
     * to the database based upon the users guesses
     * @return
     */
    public ArrayList<String> getCurrentActivity(){
        return mCurrentActivity;
    }

    /****************************************************************************
     * void setCurrentActivity(ArrayList<String> mCurrentActivity){
     * This is set in Launch_Platform in goto_activity(String activity_id).
     * This is used by the gaming activates to ad points based upon users guesses
     * @param mCurrentActivity
     */
     public void setCurrentActivity(ArrayList<String> mCurrentActivity){
        this.mCurrentActivity=mCurrentActivity;
    }

    /****************************************************************************
     * ArrayList<String> getCurrentBook(){
     * Used in ActivityBK02Book to get which current book was chosen in
     * ActivityBK02BookList
     * @return
     */
     public HashMap<String, String> getCurrentBook(){
        return mCurrentBook;
    }

    /****************************************************************************
     *  void setCurrentBook(ArrayList<String> mCurrentBook)
     *  This is used in ActivityBK02BookList to set the mCurrentBook
     *  to be used in ActivityBK02Book
     * @param mCurrentBook
     */
     public void setCurrentBook(HashMap<String,String> mCurrentBook){
        this.mCurrentBook=new HashMap<>(mCurrentBook);
    }


    /****************************************************************************
     * setCurrentUserID(String mCurrentUserID)
     * This is to set the mCurrentUserID to be used throughout the app.
     * The default is current 0 but in case we ever decide to add the ability
     * to chose between different users this user id must remain
     * @param mCurrentUserID
     */
    public void setCurrentUserID(String mCurrentUserID){
        this.mCurrentUserID=mCurrentUserID;

    }


    /****************************************************************************
     * getCurrentUserID()
     * Used throughout the app to get mCurrentUserID
     * The default user id is set at 0, but in case we ever decide to add the ability
     * to chose between different users this user id must remain
     * @return
     */
    public String getCurrentUserID(){
        return this.mCurrentUserID;
    }


    /****************************************************************************
     * setCurrentGroup_Section_Phase(String group_id, String phase_id,
     * String section_id, String current_level){
     * is very important for the app must stay
     * @param group_id
     * @param phase_id
     * @param section_id
     * @param current_level
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
     /****************************************************************************
     * @return
     */
    public HashMap<String,String> getCurrentGroup_Section_Phase(){
        return this.mCurrentGroupSectionPhase;
    }


    /****************************************************************************
     * void setAppUserCurrentGPL(ArrayList<ArrayList<String>> appUserCurrentGPL)
     * This is used in Activities_Platform to set a seperate location of the GPL
     * While ever time and update is done this is called the purpose for use
     * in CS01,CS02 alone  as while those are syllable activities they are 
     * different phases then the RS
     * @param appUserCurrentGPL
     */
    public void setAppUserCurrentGPL(ArrayList<HashMap<String,String>> appUserCurrentGPL){
        mAppUserCurrentGPL=new ArrayList<>(appUserCurrentGPL);
    }

    /****************************************************************************
     *  ArrayList<ArrayList<String>> getAppUserCurrentGPL()
     *  This is used only in ActivityCS01 and ActivityCS02 as while they have the same group
     *  as the RS syllable activities they are using different phases
     * @return
     */
     public ArrayList<HashMap<String,String>> getAppUserCurrentGPL(){
            return  mAppUserCurrentGPL;
     }



}
