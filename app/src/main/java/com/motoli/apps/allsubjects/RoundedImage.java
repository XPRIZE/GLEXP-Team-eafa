package com.motoli.apps.allsubjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/13/2015.
 */
public class RoundedImage extends AppCompatImageView {

    private Path roundedPath;
    private int rounded;

    public RoundedImage(Context context) {
        super(context);
        init();
    }

    public RoundedImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedImage(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setRounded(30);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            roundedPath = new Path();
            roundedPath.addRoundRect(new RectF(0, 0, w, h),
                    rounded, rounded, Path.Direction.CW);
        }
    }


    public void setRounded(int rounded) {
        this.rounded = rounded;
        roundedPath = new Path();
        roundedPath.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                rounded, rounded, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(roundedPath);
        super.onDraw(canvas);
    }
}