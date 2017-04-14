package com.motoli.apps.allsubjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.List;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 7/13/2016.
 */
public class ActivityTR01DoodleCanvas extends View   {


    private Path drawPath;
    private Paint drawPaint, canvasPaint;

    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private Context mContext;


    public ActivityTR01DoodleCanvas(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext=mContext;
        init();
    }

    /////////////////////////////////////////////////////////////////////////////////////////


    private void init(){
        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_black));
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(getResources().getInteger(R.integer.small_brush_size));
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    public void setPaintBrush(int mBrush){

        if(mBrush==1){
            drawPaint.setStrokeWidth(getResources().getInteger(R.integer.medium_brush_size));
        }else if(mBrush==2){
            drawPaint.setStrokeWidth(getResources().getInteger(R.integer.large_brush_size));
        }else{
            drawPaint.setStrokeWidth(getResources().getInteger(R.integer.small_brush_size));
        }
        /* */
    }

    public void setPaintColor(int mColor){
        invalidate();

        switch(mColor){
            default:
            case 1:
                drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_red));
                break;
            case 2:
                drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_orange));
                break;
            case 3:
                drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_yellow));
                break;
            case 4:
                drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_green));
                break;
            case 5:
                drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_light_blue));
                break;
            case 6:
                drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_blue));
                break;
            case 7:
                drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_purple));
                break;
            case 8:
                drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_pink));
                break;
            case 9:
                drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_black));
                break;
            case 10:
                drawPaint.setColor(ContextCompat.getColor(mContext,R.color.doodle_white));
                break;
        }
    }



    public void clear() {
        drawCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
    }



}
