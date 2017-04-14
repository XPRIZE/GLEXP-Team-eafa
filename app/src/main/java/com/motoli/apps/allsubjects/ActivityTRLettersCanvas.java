package com.motoli.apps.allsubjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 7/13/2016.
 *
 * Handlers letter tracing.
 * Needs some attentive work to clean up code.
 * Uses canvas and ideas from other project to map location of finger to a diagram of the letter
 * the user is attempting to write. if it matches it remains green. if it is wrong or too far
 * outside of bounds the response is red. the user can reattempt many times.
 * There are no levels for this activity.
 */
public class ActivityTRLettersCanvas extends View   {

    private Path mPath;
    List<Point> mPoints;
    public static final int BAD = 1;

    private int mainViewDesiredHeight;
    private int mainViewDesiredWidth;
    private boolean mClear=false;
    private boolean mCorrect;


    private Paint mLinePaintIn;
    private Paint mLinePaintOut;


    private Bitmap mBitmap;

    private float mX, mY;

    private static final float TOLERANCE = 8;

    float x1, x2, x3, y1, y2, y3;


    OnChildSizeChanged fragment;

    public ActivityTRLettersCanvas(Context context) {
        super(context);
    }
    public ActivityTRLettersCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ActivityTRLettersCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /////////////////////////////////////////////////////////////////////////////////////////


    private void init(){
        mPath = new Path();
        this.mPoints = new ArrayList();


        mLinePaintIn = new Paint();
        mLinePaintIn.setAntiAlias(true);
        mLinePaintIn.setColor(Color.rgb(8,101,177));//GREEN);
        mLinePaintIn.setStyle(Paint.Style.STROKE);
        mLinePaintIn.setStrokeJoin(Paint.Join.ROUND);
        mLinePaintIn.setStrokeCap(Paint.Cap.ROUND);
        mLinePaintIn.setStrokeWidth(150f);

        mLinePaintOut = new Paint();
        mLinePaintOut.setAntiAlias(true);
        mLinePaintOut.setColor(Color.RED);
        mLinePaintOut.setStyle(Paint.Style.STROKE);
        mLinePaintOut.setStrokeJoin(Paint.Join.ROUND);
        mLinePaintOut.setStrokeCap(Paint.Cap.ROUND);
        mLinePaintOut.setStrokeWidth(150f);

        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.rgb(8,101,177));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10f);

        Paint mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setStrokeWidth(10f);
        mTextPaint.setTextSize(400);


    }

    private interface OnChildSizeChanged {

    }

    public boolean drawTrace(Canvas canvas) {
        canvas.getWidth();
        boolean mIsIn=true;
        for (int i = 0; i < this.mPoints.size() - 1; i += BAD) {
            Point a =  this.mPoints.get(i);
            Point b = this.mPoints.get(i + BAD);
            if(a.start){
                mPath.moveTo(a.f10x, a.f11y);
                mX = a.f10x;
                mY = a.f11y;
            }else if ( !a.end) {

                float dx = Math.abs(a.f10x - mX);
                float dy = Math.abs(a.f11y - mY);
                if (dx >= TOLERANCE || dy >= TOLERANCE) {
                    mPath.quadTo(mX, mY, (a.f10x + mX) / 2, (a.f11y + mY) / 2);
                    mX = a.f10x;
                    mY = a.f11y;
                }

                Paint paint;
                float f = a.f10x;
                float f2 = a.f11y;
                float f3 = b.f10x;
                float f4 = b.f11y;
                if (b.isIn()) {
                    paint = this.mLinePaintIn;
                    mIsIn=true;
                } else {
                    paint = this.mLinePaintOut;
                    mIsIn=false;
                }
                canvas.drawLine(f, f2, f3, f4, paint);

            }else{
                mPath.lineTo(mX, mY);
            }
        }
        return mIsIn;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mClear){
            Paint mTempPaint = new Paint();
            mTempPaint.setColor(Color.TRANSPARENT);
            mBitmap.eraseColor(Color.TRANSPARENT);
            mCorrect=true;
            this.mPoints.clear();
            mPath.reset();

            mPath = new Path();
            canvas.drawBitmap(mBitmap, 0, 0, mTempPaint);
            mClear=false;
        }else {
            drawTrace(canvas);
            Log.d(Constants.LOGCAT, "paint mask");
        }

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

    }

    public void clearCanvas() {
        mClear=true;
        invalidate();
    }


    public int getHotspotColor (int x, int y) {
        ImageView img = (ImageView) getRootView().findViewById (R.id.baseImage);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        Log.d(Constants.LOGCAT,"Base X: "+x+" /n/t Base Y: "+y);
        int mHotSpotReturn;
        if(y >= hotspots.getHeight() || y <0){
            mHotSpotReturn=0;
        }else{
            mHotSpotReturn = hotspots.getPixel(x, y);
        }

        return mHotSpotReturn;
    }

    public boolean matchToBlack (int mTouchColor) {
        return mTouchColor!=Color.BLACK;
    } // end match


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();

        float touchY = event.getY();

        boolean symbolContainsPoint;
        int mTouchColor;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.x1 = touchX;
                this.y1 = touchY;

                if(mCorrect){

                    mTouchColor = getHotspotColor (Math.round(x1), Math.round(y1));

                    mCorrect=matchToBlack (mTouchColor);
                    symbolContainsPoint=mCorrect; //true
                } else {
                    symbolContainsPoint=false;
                }

                this.mPoints.add(new Point(this.x1, this.y1, true, false, symbolContainsPoint));

                break;
            case MotionEvent.ACTION_MOVE:
                this.x2 = touchX;
                this.y2 = touchY;
                if(mCorrect){
                    mTouchColor = getHotspotColor (Math.round(x2), Math.round(y2));
                    mCorrect=matchToBlack (mTouchColor);

                    symbolContainsPoint=mCorrect;
                } else {
                    symbolContainsPoint=false;
                }

                this.mPoints.add(new Point(this.x2, this.y2, symbolContainsPoint));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                this.x3 = touchX;
                this.y3 = touchY;
                if (this.x3 == this.x1 || this.y3 == this.y1) {
                    this.x3 += DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
                }



                this.mPoints.add(new Point(this.x3, this.y3, false, true, true));

                invalidate();
                break;
        }


        return true;
    }




    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == 1073741824) {
            width = widthSize;
        } else if (widthMode == ExploreByTouchHelper.INVALID_ID) {
            width = Math.min(this.mainViewDesiredWidth, widthSize);
        } else {
            width = this.mainViewDesiredWidth;
        }
        if (heightMode == 1073741824) {
            height = heightSize;
        } else if (heightMode == ExploreByTouchHelper.INVALID_ID) {
            height = Math.min(this.mainViewDesiredHeight, heightSize);
        } else {
            height = this.mainViewDesiredHeight;
        }




        setMeasuredDimension(width, height);
    }


    public void clear() {
        this.mPoints.clear();
        invalidate();
    }




}
