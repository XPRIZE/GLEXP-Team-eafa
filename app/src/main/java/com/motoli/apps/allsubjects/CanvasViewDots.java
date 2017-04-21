package com.motoli.apps.allsubjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 6/29/2016.
 */
public class CanvasViewDots extends View {

    public int width;
    public int height;
    private Path mPath;
    Context mContext;
    private Paint mPaint;
    private int mNumberOfDots=1;

    private int mColor;


    public CanvasViewDots(Context c, AttributeSet attrs) {
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
        //mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        ///mCanvas = new Canvas(mBitmap);
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<2;i++) {
            switch (mColor) {
                default:
                case 0: 
                       mPaint.setColor(ContextCompat.getColor(mContext,R.color.dot_color));
                    break;
                case 1: 
                    mPaint.setColor(ContextCompat.getColor(mContext,R.color.shape_color_green));
                    break;
                case 2: 
                    mPaint.setColor(ContextCompat.getColor(mContext,R.color.shape_color_red));
                    break;
                case 3:
                    mPaint.setColor(ContextCompat.getColor(mContext,R.color.reg_white));
                    break;
                case 4:
                    mPaint.setColor(ContextCompat.getColor(mContext,R.color.dot_color_fade));
                    break;
                case 5:
                    mPaint.setColor(ContextCompat
                            .getColor(mContext,R.color.shape_color_orange_qn03));
                    break;
            }
            mPaint.setStyle(Paint.Style.FILL);
            float mViewSize ;
            if (getHeight() < getWidth()) {
                mViewSize = getHeight() / 7f;
            } else {
                mViewSize = getWidth() / 7f;
            }

            switch (mNumberOfDots) {
                default:
                case 0:
                    clearCanvas();
                    break;
                case 1: 
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, mViewSize, mPaint);
                    break;
                case 2: 
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 6, getHeight() - (getHeight() / 6), mViewSize, mPaint);
                    break;
                case 3: 
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 6, getHeight() - (getHeight() / 6), mViewSize, mPaint);
                    break;
                case 4: 
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()- getWidth() / 6, getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()/ 6,  getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() - getWidth() / 6, getHeight() - (getHeight() / 6), mViewSize, mPaint);
                    break;
                case 5: 
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()- getWidth() / 6, getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()/ 6,  getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() - getWidth() / 6, getHeight() - (getHeight() / 6), mViewSize, mPaint);
                    break;
                case 6: 
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 6, getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    break;
                
                case 7:
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() /2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 6, getHeight()-(getHeight() /6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2,getHeight()-(getHeight() /6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight()-(getHeight() /6),mViewSize, mPaint);
                    break;
                case 8:
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 6, getHeight() / 2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 6, getHeight()-(getHeight() /6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2,getHeight()-(getHeight() /6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight()-(getHeight() /6),mViewSize, mPaint);
                    break;
                case 9:
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 6, getHeight() / 2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() /2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 6, getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    break;
                
            }
        }//end for
    }





    public void clearCanvas() {
        mColor=3;
        mPath.reset();
        invalidate();
    }

    
    public void setColor(int mColor){
        this.mColor=mColor;
    }



    public void setNumberOfDots(int mNumberOfDots){
        this.mNumberOfDots=mNumberOfDots;
    }






}