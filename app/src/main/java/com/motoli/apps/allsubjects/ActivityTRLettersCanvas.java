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
import com.motoli.apps.allsubjects.Model.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 7/13/2016.
 */
public class ActivityTRLettersCanvas extends View   {

    //drawing path
    private Path mPath;
    private Path mTextPath;
    List<Point> mPoints;
    public static final int BAD = 1;
    public static final int GOOD = 2;

    Symbol symbol;
    SymbolMask symbolMask;
    private int mainViewDesiredHeight;
    private int mainViewDesiredWidth;
    private boolean mClear=false;
    private boolean mCorrect;
    //defines what to draw
    private Paint canvasPaint;

    //defines how to draw
    private Paint mPaint;
    private Paint mTextPaint;
    Paint mLinePaintIn;
    Paint mLinePaintOut;

    //initial color
    private int paintColor = 0xFF660000;

    //canvas - holding pen, holds your drawings
    //and transfers them to the view
    private Canvas mCanvas;

    //canvas bitmap
    private Bitmap mBitmap;
    private Bitmap myCanvasBitmap;

    Canvas myCanvas = null;

    Matrix identityMatrix;


    private boolean mFirstDraw=true;
    private boolean userInteraction;
    //brush size
    private float currentBrushSize, lastBrushSize;
    private float mX, mY;

    private static final float TOLERANCE = 8;

    public float touchX;
    public float touchY;

    private Motoli_Application mAppData;

    float x1, x2, x3, y1, y2, y3;


    OnChildSizeChanged fragment;

    public ActivityTRLettersCanvas(Context context) {
        super(context);
    }
    public ActivityTRLettersCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mAppData=((Motoli_Application) context.getApplicationContext());
        init();


    }
    public ActivityTRLettersCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    public Bitmap getCanvasBitmap(){

        return myCanvasBitmap;

    }


    private void init(){
        mPath = new Path();
        this.mPoints = new ArrayList();

        mTextPath = new Path();

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

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.rgb(8,101,177));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10f);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setStrokeWidth(10f);
        mTextPaint.setTextSize(400);


    }

    public interface OnChildSizeChanged {
        void onChildSizeChanged(int i, int i2);

        void onLetterCompleted();
    }
    public void setFragment(OnChildSizeChanged fragment) {
        this.fragment = fragment;
    }

    public boolean drawTrace(Canvas canvas) {
        canvas.getWidth();
        boolean mIsIn=true;
        for (int i = 0; i < this.mPoints.size() - 1; i += BAD) {
            Point a = (Point) this.mPoints.get(i);
            Point b = (Point) this.mPoints.get(i + BAD);
            if(a.start){
                mPath.moveTo(a.f10x, a.f11y);
                mX = a.f10x;
                mY = a.f11y;
            }else if ( !a.end) {

            //}else if (!a.start && !a.end) {

                float dx = Math.abs(a.f10x - mX);
                float dy = Math.abs(a.f11y - mY);
                if (dx >= TOLERANCE || dy >= TOLERANCE) {
                    mPath.quadTo(mX, mY, (a.f10x + mX) / 2, (a.f11y + mY) / 2);
                    mX = a.f10x;
                    mY = a.f11y;
                }

/* */
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
        /*
        Paint paint;
        if(drawTrace(canvas)){
            paint = this.mLinePaintIn;
        }else {
            paint = this.mLinePaintOut;
        }
        canvas.drawPath(mPath,paint);

        mFirstDraw=false;
        Log.d(Constants.LOGCAT, "paint mask");
        if(symbolMask!=null) {

        }
        canvas.drawPath(mPath, paint);


        drawTrace(canvas);
        canvas.drawBitmap(myCanvasBitmap, identityMatrix, null);
        */
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(mBitmap);



    }

    public void setSymbol(Symbol sym) {
        this.mPoints.clear();
        this.symbol = sym;
        invalidate();
    }

    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas() {
        mClear=true;
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);
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
        // return hotspots.getPixel(x, y);

        return mHotSpotReturn;
    }

    public boolean matchToBlack (int mTouchColor) {
        if(mTouchColor!=Color.BLACK){
            return false;
        }
        return true;
    } // end match


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //    public boolean onTouch(View view, MotionEvent event){

        float touchX = event.getX();

        float touchY = event.getY();

        boolean ret = this.userInteraction;
        boolean symbolContainsPoint = true;
        int mTouchColor;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.x1 = touchX;
                this.y1 = touchY;
               //mTouchColor = getHotspotColor (Math.round(x1), Math.round(y1));
                //int tolerance = 25;

               //if (matchToBlack (mTouchColor)) {
                if(mCorrect){

                    mTouchColor = getHotspotColor (Math.round(x1), Math.round(y1));

                    mCorrect=matchToBlack (mTouchColor);
                    // Do the action associated with the RED region
                    symbolContainsPoint=mCorrect; //true
                    // nextImage = R.drawable.p2_ship_alien;
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

                    symbolContainsPoint=mCorrect; //true
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

                if(mCorrect){
                    mTouchColor = getHotspotColor (Math.round(x3), Math.round(y3));

                    symbolContainsPoint=mCorrect; //true
                    // nextImage = R.drawable.p2_ship_alien;
                } else {
                    symbolContainsPoint=false;
                }

                this.mPoints.add(new Point(this.x3, this.y3, false, true, symbolContainsPoint));

                invalidate();
                break;
        }


        return true;
    }



    void setDesiredHeight(float desiredHeight) {
        this.mainViewDesiredHeight = (int) desiredHeight;
    }

    void setDesiredWidth(float desiredWidth) {
        this.mainViewDesiredWidth = (int) desiredWidth;
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



        if(width>0) {
            myCanvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            myCanvas = new Canvas();
            myCanvas.setBitmap(myCanvasBitmap);

            identityMatrix = new Matrix();
        }
        setMeasuredDimension(width, height);
    }


    public void setSymbolMask(SymbolMask symbolMask) {

        this.symbolMask = symbolMask;
    }

    public void clear() {
        this.mPoints.clear();
        invalidate();
    }


    public boolean checkResult() {

        throw new UnsupportedOperationException("Method not decompiled: " +
                "com.motoli.apps.allsubjects.ActivityTRShapesCanvas.checkResult():boolean");
    }

}
