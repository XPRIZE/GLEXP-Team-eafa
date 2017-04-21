package com.motoli.apps.allsubjects;


import android.content.Context;
import android.util.AttributeSet;
//import android.widget.ImageView;
import android.support.v7.widget.AppCompatImageView;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 *
 * This class take and ImageView and squares it.
 */
public class GridViewItem extends AppCompatImageView {

	 public GridViewItem(Context context) {
	        super(context);
	    }

	    public GridViewItem(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }

	    public GridViewItem(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	    }

	    @Override
	    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	    }

}
