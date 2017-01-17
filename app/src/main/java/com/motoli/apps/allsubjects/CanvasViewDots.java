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
import android.view.View;

public class CanvasViewDots extends View {

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

    private int mSize;
    private int mColor;


    public CanvasViewDots(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        mColor=0;
        mSize=0;
        // we set a new Path
        mPath = new Path();

        String id= attrs.getIdAttribute();
        // and we set a new Paint with the desired attributes
        //if(getTag())
        mNumberOfDots=Integer.parseInt((String)getTag());

        //mType=
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

        for(int i=0;i<2;i++) {
            switch (mColor) {
                default:
                case 0: {
                       mPaint.setColor(getResources().getColor(R.color.dot_color));
                    break;
                }
                case 1: {
                    mPaint.setColor(getResources().getColor(R.color.shape_color_green));
                    break;
                }
                case 2: {
                    mPaint.setColor(getResources().getColor(R.color.shape_color_red));
                    break;
                }
                case 3:{
                    mPaint.setColor(getResources().getColor(R.color.reg_white));
                    break;
                }
                case 4:{
                    mPaint.setColor(getResources().getColor(R.color.dot_color_fade));

                    break;
                }
                case 5:{
                    mPaint.setColor(getResources().getColor(R.color.shape_color_orange_qn03));

                    break;
                }
            }
            mPaint.setStyle(Paint.Style.FILL);
            float mViewSize = 0;
            if (getHeight() < getWidth()) {
                mViewSize = getHeight() / 7f;
            } else {
                mViewSize = getWidth() / 7f;
            }

            switch (mNumberOfDots) {
                default:
                case 0:{
                    clearCanvas();
                    break;
                }
                case 1: {


                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, mViewSize, mPaint);

                    break;
                }
                case 2: {
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);

                    canvas.drawCircle(getWidth() / 6, getHeight() - (getHeight() / 6), mViewSize, mPaint);

                    break;
                }
                case 3: {
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 6, getHeight() - (getHeight() / 6), mViewSize, mPaint);

                    break;
                }
                case 4: {
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()- getWidth() / 6, getHeight() /6, mViewSize, mPaint);

                    canvas.drawCircle(getWidth()/ 6,  getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() - getWidth() / 6, getHeight() - (getHeight() / 6), mViewSize, mPaint);

                    break;
                }

                case 5: {
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()- getWidth() / 6, getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()/ 6,  getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() - getWidth() / 6, getHeight() - (getHeight() / 6), mViewSize, mPaint);

                    break;
                }
                case 6: {
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);


                    canvas.drawCircle(getWidth() / 6, getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight()-(getHeight() / 6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight()-(getHeight() / 6), mViewSize, mPaint);

                    break;
                }
                case 7:{
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);


                    canvas.drawCircle(getWidth() / 2, getHeight() /2, mViewSize, mPaint);

                    canvas.drawCircle(getWidth() / 6, getHeight()-(getHeight() /6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2,getHeight()-(getHeight() /6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight()-(getHeight() /6),mViewSize, mPaint);


                    break;
                }
                case 8:{
                    canvas.drawCircle(getWidth() / 6, getHeight() / 6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2, getHeight() /6, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /6, mViewSize, mPaint);


                    canvas.drawCircle(getWidth() / 6, getHeight() / 2, mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight() /2, mViewSize, mPaint);

                    canvas.drawCircle(getWidth() / 6, getHeight()-(getHeight() /6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth() / 2,getHeight()-(getHeight() /6), mViewSize, mPaint);
                    canvas.drawCircle(getWidth()-(getWidth() / 6), getHeight()-(getHeight() /6),mViewSize, mPaint);


                    break;
                }
                case 9:{
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
            }
        }//end for
    }





    public void clearCanvas() {
        mColor=3;
        mPath.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);
    }

    public void setColor(int mColor){
        this.mColor=mColor;
    }



    public void setNumberOfDots(int mNumberOfDots){
        this.mNumberOfDots=mNumberOfDots;
    }






}