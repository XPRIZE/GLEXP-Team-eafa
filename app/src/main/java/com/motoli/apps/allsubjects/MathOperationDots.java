package com.motoli.apps.allsubjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 6/29/2016.
 */
public class MathOperationDots extends View {

    public int width;
    public int height;
    private Path mPath;
    Context mContext;
    private Paint mPaint;
    private int mNumberOfDots=1;

  
    private int mColor;

    private boolean mAllowTextNumbers=false;


    public MathOperationDots(Context c, AttributeSet attrs) {
        super(c, attrs);
        mContext = c;

        mColor=0;
        mPath = new Path();

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
         //  mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
       // mCanvas = new Canvas(mBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float mWidth=getWidth();
        float mHeight=getHeight();
        
        for(int i=0;i<2;i++) {
            switch (mColor) {
                default:
                case 0:
                    mPaint.setColor(
                            ContextCompat.getColor(mContext,R.color.math_operation_blue_dot));
                    break;
                case 1:
                    mPaint.setColor(
                            ContextCompat.getColor(mContext,R.color.math_operation_green_dot));
                    break;
                case 2:
                    mPaint.setColor(
                            ContextCompat.getColor(mContext,R.color.math_operation_orange_dot));
                    break;
                case 3:
                    mPaint.setColor(
                            ContextCompat.getColor(mContext,R.color.math_operation_pink_dot));
                    break;
                case 4:
                    mPaint.setColor(
                            ContextCompat.getColor(mContext,R.color.math_operation_red_dot));
                    break;
                case 5:
                    mPaint.setColor(
                            ContextCompat.getColor(mContext,R.color.math_operation_yellow_dot));
                    break;
                case 6:
                    mPaint.setColor(ContextCompat.getColor(
                            mContext,R.color.math_operation_transparent_dot));
                    break;
                case 7:
                    mPaint.setColor(
                            ContextCompat.getColor(mContext,R.color.math_operation_blue_dot));
                    break;
                case 8:
                    mPaint.setColor(ContextCompat.getColor(mContext,R.color.incorrect_red));
                    break;
                case 9:
                    mPaint.setColor(ContextCompat.getColor(mContext,R.color.correct_green));
                    break;
            }
            mPaint.setStyle(Paint.Style.FILL);


            if(mNumberOfDots<1) {
                clearCanvas();
            }else{
                int mDivide = 95;

                int mTens=mNumberOfDots / 10;
                int mOnes=mNumberOfDots % 10;

                Log.d(Constants.LOGCAT, "mTens: "+mTens+" | mOnes: "+mOnes);

                int mRow = 9;
                int mColumn = 0;
                mPaint.setColor(ContextCompat.getColor(mContext,R.color.math_operation_tens));
                for (int j = 1; j <= mTens; j++) {
                    mPaint.setColor(ContextCompat.getColor(mContext,R.color.math_operation_tens));

                    float mRectLeft= mColumn * (mWidth / 10)  + (mWidth / mDivide);
                    float mRectTop = (mHeight / mDivide);
                    float mRectRight =  (mColumn + 1) * (mWidth / 10)  - (mWidth / mDivide);
                    float mRectBottom =  (mRow + 1) * (mHeight / 10)  - (mHeight / mDivide);

                    canvas.drawRect(mRectLeft, mRectTop,mRectRight,mRectBottom,mPaint);

                        mPaint.setColor(ContextCompat.getColor(mContext,R.color.regularBlack));
                        mPaint.setTextSize(20);

                        mColumn++;
                }

                mRow = 9;

                mPaint.setColor(ContextCompat.getColor(mContext,R.color.math_operation_blue_dot));

                for (int j = 1; j <= mOnes; j++) {
                    mPaint.setColor(ContextCompat.getColor(mContext,R.color.math_operation_blue_dot));

                    float mRectLeft= mColumn * (mWidth / 10)  + (mWidth / mDivide);
                    float mRectTop = mRow * (mHeight / 10)  + (mHeight / mDivide);
                    float mRectRight =  (mColumn + 1) * (mWidth / 10)  - (mWidth / mDivide);
                    float mRectBottom =  (mRow + 1) * (mHeight / 10)  - (mHeight / mDivide);

                    canvas.drawRect(mRectLeft, mRectTop,mRectRight,mRectBottom,mPaint);

                    if(mAllowTextNumbers) {
                        mPaint.setColor(ContextCompat.getColor(mContext,R.color.regularBlack));
                        mPaint.setTextSize(40);
                    }


                    if (mRow == 0) {
                        mColumn++;
                        mRow = 9;
                    } else {
                        mRow--;
                    }
                }
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

    public void setNumberOfDots(int mNumberOfDots){
        this.mNumberOfDots=mNumberOfDots;
    }


    public void setUpDots(int mNumberOfDots){
        this.mNumberOfDots=mNumberOfDots;
    }





}