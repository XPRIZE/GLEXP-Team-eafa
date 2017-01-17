package com.motoli.apps.allsubjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CanvasView extends View {

    public int width;
    private Bitmap mBitmap;
    private Path mPath;
    Context context;
    private Paint mPaint;
    private int mType=0;

    private int mColor;


    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        mColor=0;

        mPath = new Path();

        String id= attrs.getIdAttribute();
        mType=Integer.parseInt((String)getTag());

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
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();

        for(int i=0;i<2;i++) {
            switch (mColor) {
                default:
                case 0: {
                    if(i==0) {
                        mPaint.setColor(Color.WHITE);
                    }else{
                        mPaint.setColor(getResources().getColor(R.color.darkGray));
                    }
                    break;
                }
                case 1: {
                    mPaint.setColor(getResources().getColor(R.color.shape_color_green));
                    break;
                }
                case 2: {
                    mPaint.setColor(getResources().getColor(R.color.shape_color_blue));
                    break;
                }
                case 3: {
                    mPaint.setColor(getResources().getColor(R.color.shape_color_red));
                    break;
                }
                case 4: {
                    mPaint.setColor(getResources().getColor(R.color.shape_color_yellow));
                    break;
                }
                case 5: {
                    mPaint.setColor(getResources().getColor(R.color.shape_color_pink));
                    break;
                }
                case 6: {
                    mPaint.setColor(getResources().getColor(R.color.shape_color_orange));
                    break;
                }
            }
            if(i==0) {
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            }else{
                mPaint.setStyle(Paint.Style.STROKE);
            }

            switch (mType) {

                case 2: {
                /*
                    Line
                 */
                    mPaint.setStrokeWidth(10);
                    canvas.drawLine(getWidth() / 2, (getHeight() /10),
                            getWidth() / 2 , getBottom() - (getHeight() / 4), mPaint);
                    break;
                }
                case 3: {
                /*
                    Rectangle
                 */

                    float mViewSize = 110f;
                    if (getHeight() < getWidth()) {
                        mViewSize = (getHeight() / 2.5f);
                    } else {
                        mViewSize = getWidth() / 2.5f;
                    }


                    canvas.drawRect((getWidth() / 2) - mViewSize,
                            (getHeight() / 2) - mViewSize + (mViewSize / 2),
                            (getWidth() / 2) + mViewSize,
                            (getHeight() / 4) + mViewSize + (mViewSize / 2),
                            mPaint);

                    break;
                }

                case 7:{
                    clearCanvas();
                    break;
                }
                case 4: {
                /*
                    square
                 */
                    float mViewSize = 110f;
                    if (getHeight() < getWidth()) {
                        mViewSize = (getHeight() / 2.5f);
                    } else {
                        mViewSize = getWidth() / 2.5f;
                    }


                    canvas.drawRect((getWidth() / 2) - mViewSize, (getHeight() / 2) - mViewSize,
                            (getWidth() / 2) + mViewSize, (getHeight() / 2) + mViewSize, mPaint);
                    break;
                }
                default:
                case 1: {
                /*
                    Circle
                 */
                    float mViewSize = 0;
                    if (getHeight() < getWidth()) {
                        mViewSize = getHeight() / 2.2f;
                    } else {
                        mViewSize = getWidth() / 2.2f;
                    }
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, mViewSize, mPaint);
                    break;
                }
                case 6: {
                /*
                    Star
                 */

                    int mViewSize = 0;
                    if (getHeight() < getWidth()) {
                        mViewSize = getHeight();
                    } else {
                        mViewSize = getWidth();
                    }
                    mPath = createStarBySyze(mViewSize, 5);
                    if(i==0) {
                        canvas.rotate(-18, mViewSize / 1.5F, mViewSize / 2.7F);
                    }
                    canvas.drawPath(mPath, mPaint);
                    break;
                }
                case 5: {
                /*
                    Triangle
                 */
                    float mViewSize = 110f;
                    if (getHeight() < getWidth()) {
                        mViewSize = (getHeight() / 2.5f);
                    } else {
                        mViewSize = getWidth() / 2.5f;
                    }


                    mPath.moveTo((getWidth() / 2) - mViewSize, (getHeight() / 2) + mViewSize);
                    mPath.lineTo(getWidth() / 2, (getHeight() / 2) - mViewSize);
                    mPath.lineTo((getWidth() / 2) + mViewSize, (getHeight() / 2) + mViewSize);
                    mPath.lineTo((getWidth() / 2) - mViewSize, (getHeight() / 2) + mViewSize);
                    canvas.drawPath(mPath, mPaint);
                    break;
                }
            }
        }//end for
    }


    private Path createStarBySyze(float width, int steps) {
        float halfWidth = width / 2.0F;
        float bigRadius = halfWidth;
        float radius = halfWidth / 2.7F;
        float degreesPerStep = (float) Math.toRadians(360.0F / (float) steps);
        float halfDegreesPerStep = degreesPerStep / 2.0F;
        Path ret = new Path();
        //ret.setFillType(Path.FillType.EVEN_ODD);
        float max = (float) (2.0F* Math.PI);
        ret.moveTo(width,halfWidth);
        for (double step = 0; step < max; step += degreesPerStep) {
            ret.lineTo((float)(halfWidth + bigRadius * Math.cos(step)), (float)(halfWidth + bigRadius * Math.sin(step)));
            ret.lineTo((float)(halfWidth + radius * Math.cos(step + halfDegreesPerStep)), (float)(halfWidth + radius * Math.sin(step + halfDegreesPerStep)));
        }

        ret.close();
        return ret;
    }



    public void clearCanvas() {
        mPath.reset();
        mPaint.reset();
        invalidate();
    }

    // when ACTION_UP stop touch



    public void setType(int mType){
        this.mType=mType;
    }


    public void setColor(int mColor){
        this.mColor=mColor;
    }



}