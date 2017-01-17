package com.motoli.apps.allsubjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MathOperationDots extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    Context context;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    private int mNumberOfDots=1;

    private int mMaxDots=26;
    private int mSize;
    private int mColor;
    private boolean mAllowTens=false;

    private boolean mAllowTextNumbers=false;


    public MathOperationDots(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        mColor=0;
        mSize=0;
        mPath = new Path();

        String id= attrs.getIdAttribute();
        mNumberOfDots=Integer.parseInt((String)getTag());

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    public Bitmap getImage (int id, int width, int height) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), id);
        Bitmap img = Bitmap.createScaledBitmap(bmp, width, height, true);
        bmp.recycle();
        return img;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        float mWidth=getWidth();
        float mHeight=getHeight();
        
        
        for(int i=0;i<2;i++) {
            switch (mColor) {
                default:
                case 0: {
                    //value is 1
                    mPaint.setColor(getResources().getColor(R.color.math_operation_blue_dot));
                    break;
                }
                case 1: {
                    //value is 25
                    mPaint.setColor(getResources().getColor(R.color.math_operation_green_dot));
                    break;
                }
                case 2: {
                    //value
                    mPaint.setColor(getResources().getColor(R.color.math_operation_orange_dot));
                    break;
                }
                case 3:{
                    mPaint.setColor(getResources().getColor(R.color.math_operation_pink_dot));
                    break;
                }
                case 4:{
                    mPaint.setColor(getResources().getColor(R.color.math_operation_red_dot));
                    break;
                }
                case 5:{
                    mPaint.setColor(getResources().getColor(R.color.math_operation_yellow_dot));
                    break;
                }
                case 6:{
                    mPaint.setColor(
                            getResources().getColor(R.color.math_operation_transparent_dot));
                    break;
                }
                case 7: {
                    //value is 1
                    mPaint.setColor(getResources().getColor(R.color.math_operation_blue_dot));
                  //  mPaint.setColor(getResources().getColor(R.color.dot_color_fade));
                    break;
                }
                case 8:{
                    mPaint.setColor(getResources().getColor(R.color.incorrect_red));
                    break;
                }
                case 9:{
                    mPaint.setColor(getResources().getColor(R.color.correct_green));
                    break;
                }

            }
            mPaint.setStyle(Paint.Style.FILL);

            float mViewSize;
            if (mHeight < getWidth()) {
                mViewSize = mHeight / 12f;
            } else {
                mViewSize = mWidth / 12f;
            }

            if(mNumberOfDots<1) {
                clearCanvas();
            }else{
                int mColumnCount = 10;
                int mDivide = 95;

                //if(mAllowTens){
                    int mTens=mNumberOfDots / 10;
                    int mOnes=mNumberOfDots % 10;

                    Log.d(Constants.LOGCAT, "mTens: "+mTens+" | mOnes: "+mOnes);

                    int mRow = 9;
                    int mColumn = 0;
                    mPaint.setColor(getResources().getColor(R.color.math_operation_tens));
                    for (int j = 1; j <= mTens; j++) {
                        mPaint.setColor(getResources().getColor(R.color.math_operation_tens));

                        /*
                        canvas.drawRect((mColumn * (mWidth / mColumnCount) + mWidth
                                / (mColumnCount * 2))-mViewSize,
                                (mRow * (mHeight / mColumnCount) + mHeight
                                        / (mColumnCount * 2))-mViewSize,
                                (mColumn * (mWidth / mColumnCount) + mWidth
                                        / (mColumnCount * 2))+mViewSize,
                                (mRow * (mHeight / mColumnCount) + mHeight
                                        / (mColumnCount * 2))+mViewSize,
                                mPaint);
*/

                        float mRectLeft= mColumn * (mWidth / 10)  + (mWidth / mDivide);
                        float mRectTop = (mHeight / mDivide);
                        float mRectRight =  (mColumn + 1) * (mWidth / 10)  - (mWidth / mDivide);
                        float mRectBottom =  (mRow + 1) * (mHeight / 10)  - (mHeight / mDivide);

                        canvas.drawRect(mRectLeft, mRectTop,mRectRight,mRectBottom,mPaint);


                        //if(mAllowTextNumbers) {
                            mPaint.setColor(getResources().getColor(R.color.regularBlack));
                            mPaint.setTextSize(20);
                            /*

                            canvas.drawText("10",
                                    (mColumn * (mWidth / 10)  + (mWidth / mDivide)) +
                                            (((mColumn + 1) * (mWidth / 10)  - (mWidth / mDivide) -
                                            mColumn * (mWidth / 10)  + (mWidth / mDivide))/10),
                                    (mHeight/2)-(mHeight / mDivide) + (mWidth / (mDivide-40)),
                                    mPaint);
                        */
                      //  }


                            mColumn++;

                    }

                    mRow = 9;

                    mPaint.setColor(getResources().getColor(R.color.math_operation_blue_dot));

                    for (int j = 1; j <= mOnes; j++) {
                        mPaint.setColor(getResources().getColor(R.color.math_operation_blue_dot));


                        float mRectLeft= mColumn * (mWidth / 10)  + (mWidth / mDivide);
                        float mRectTop = mRow * (mHeight / 10)  + (mHeight / mDivide);
                        float mRectRight =  (mColumn + 1) * (mWidth / 10)  - (mWidth / mDivide);
                        float mRectBottom =  (mRow + 1) * (mHeight / 10)  - (mHeight / mDivide);


                        canvas.drawRect(mRectLeft, mRectTop,mRectRight,mRectBottom,mPaint);
/*
                        canvas.drawCircle(mColumn * (mWidth / mColumnCount) + mWidth
                                / (mColumnCount * 2),
                                mRow * (mHeight / mColumnCount) + mHeight
                                        / (mColumnCount * 2), mViewSize, mPaint);
*/
                        if(mAllowTextNumbers) {
                            mPaint.setColor(getResources().getColor(R.color.regularBlack));
                            mPaint.setTextSize(40);
                            /*
                            canvas.drawText("1", (mColumn * (mWidth / mColumnCount) + mWidth
                                            / (mColumnCount * 2)) - (mViewSize / 2),
                                    (mRow * (mHeight / mColumnCount) + mHeight
                                            / (mColumnCount * 2)) + (mViewSize / 2), mPaint);
                            */
                        }


                        if (mRow == 0) {
                            mColumn++;
                            mRow = 9;
                        } else {
                            mRow--;
                        }
                    }
                    /*
                }else {


                    int mRow = 9;
                    int mColumn = 0;
                    for (int j = 1; j <= mNumberOfDots; j++) {


                        float mRectLeft= mColumn * (mWidth / 10)  + (mWidth / mDivide);
                        float mRectTop = mRow * (mHeight / 10)  + (mHeight / mDivide);
                        float mRectRight =  (mColumn + 1) * (mWidth / 10)  - (mWidth / mDivide);
                        float mRectBottom =  (mRow + 1) * (mHeight / 10)  - (mHeight / mDivide);

                        /*
                        float mRectLeft= mColumn * (mWidth / 5)  + (mWidth / mDivide);
                        float mRectTop = mRow * (mHeight / 5)  + (mHeight / mDivide);
                        float mRectRight =  (mColumn + 1) * (mWidth / 5)  - (mWidth / mDivide);
                        float mRectBottom =  (mRow + 1) * (mHeight / 5)  - (mHeight / mDivide);
*/
 //                       canvas.drawRect(mRectLeft, mRectTop,mRectRight,mRectBottom,mPaint);

/*
                        canvas.drawCircle(mColumn * (mWidth / mColumnCount) + mWidth
                                / (mColumnCount * 2),
                                mRow * (mHeight / mColumnCount) + mHeight
                                        / (mColumnCount * 2), mViewSize, mPaint);
*/
                    /*
                        if (mRow == 0) {
                            mColumn++;
                            mRow = 9;
                        } else {
                            mRow--;
                        }

                    }
                }
            */

            }
        }//end for
    }





    public void clearCanvas() {
        mPath.reset();
        invalidate();
    }

    public void setColor(int mColor){
        this.mColor=mColor;
    }

    public void setAllowTextNumbers(boolean mAllowTextNumbers){
        this.mAllowTextNumbers=mAllowTextNumbers;
    }
    
    public void setAllowTens(boolean mAllowTens){
        this.mAllowTens=mAllowTens;
    }



    public void setMaxDots(int mMaxDots){ this.mMaxDots=mMaxDots; }


    public void setNumberOfDots(int mNumberOfDots){
        this.mNumberOfDots=mNumberOfDots;
    }


    public void setUpDots(int mNumberOfDots){
        this.mNumberOfDots=mNumberOfDots;
    }





}