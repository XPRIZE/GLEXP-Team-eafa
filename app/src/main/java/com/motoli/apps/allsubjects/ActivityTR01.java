package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 6/29/2016.
 */
public class ActivityTR01 extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private ActivityTR01DoodleCanvas mCustomCanvas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);

        setContentView(R.layout.activity_tr01);
        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));

        appData.addToClassOrder(28);
        appData.setActivityType(4);
        mCustomCanvas = (ActivityTR01DoodleCanvas) findViewById(R.id.canvasArea);
        mCustomCanvas.setPaintColor(10);
        mCustomCanvas.setPaintBrush(1);
        findViewById(R.id.brushMedium).setAlpha(1.0f);

        ((ImageView) findViewById(R.id.buttonBlack)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_black));
        ((ImageView) findViewById(R.id.buttonWhite)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_white));
        ((ImageView) findViewById(R.id.buttonBlue)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_blue));
        ((ImageView) findViewById(R.id.buttonLightBlue)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_light_blue));
        ((ImageView) findViewById(R.id.buttonRed)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_red));
        ((ImageView) findViewById(R.id.buttonOrange)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_orange));
        ((ImageView) findViewById(R.id.buttonYellow)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_yellow));
        ((ImageView) findViewById(R.id.buttonGreen)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_green));
        ((ImageView) findViewById(R.id.buttonPurple)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_purple));
        ((ImageView) findViewById(R.id.buttonPink)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_pink));

        ((ImageView) findViewById(R.id.buttonGreen)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_green_selected));
        findViewById(R.id.buttonGreen).setAlpha(1.0f);
        setUpListeners();

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    public void clickPaintColor(View view) {
        mCustomCanvas.setPaintColor((Integer.parseInt(view.getTag().toString())));
        findViewById(R.id.buttonBlack).setAlpha(0.6f);
        findViewById(R.id.buttonWhite).setAlpha(0.6f);
        findViewById(R.id.buttonBlue).setAlpha(0.6f);
        findViewById(R.id.buttonLightBlue).setAlpha(0.6f);
        findViewById(R.id.buttonRed).setAlpha(0.6f);
        findViewById(R.id.buttonOrange).setAlpha(0.6f);
        findViewById(R.id.buttonYellow).setAlpha(0.6f);
        findViewById(R.id.buttonGreen).setAlpha(0.6f);
        findViewById(R.id.buttonPurple).setAlpha(0.6f);
        findViewById(R.id.buttonPink).setAlpha(0.6f);


        ((ImageView) findViewById(R.id.buttonBlack)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_black));
        ((ImageView) findViewById(R.id.buttonWhite)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_white));
        ((ImageView) findViewById(R.id.buttonBlue)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_blue));
        ((ImageView) findViewById(R.id.buttonLightBlue)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_light_blue));
        ((ImageView) findViewById(R.id.buttonRed)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_red));
        ((ImageView) findViewById(R.id.buttonOrange)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_orange));
        ((ImageView) findViewById(R.id.buttonYellow)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_yellow));
        ((ImageView) findViewById(R.id.buttonGreen)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_green));
        ((ImageView) findViewById(R.id.buttonPurple)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_purple));
        ((ImageView) findViewById(R.id.buttonPink)).setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.circle_color_pink));

        switch(Integer.parseInt(view.getTag().toString())){
            default:
            case 1:
                findViewById(R.id.buttonRed).setAlpha(1.0f);
                ((ImageView) findViewById(R.id.buttonRed)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.circle_color_red_selected));
                break;
            case 2:
                findViewById(R.id.buttonOrange).setAlpha(1.0f);
                ((ImageView) findViewById(R.id.buttonOrange)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.circle_color_orange_selected));
                break;
            case 3:
                findViewById(R.id.buttonYellow).setAlpha(1.0f);
                ((ImageView) findViewById(R.id.buttonYellow)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.circle_color_yellow_selected));
                break;
            case 4:
                findViewById(R.id.buttonGreen).setAlpha(1.0f);
                ((ImageView) findViewById(R.id.buttonGreen)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.circle_color_green_selected));
                break;
            case 5:
                findViewById(R.id.buttonLightBlue).setAlpha(1.0f);
                ((ImageView) findViewById(R.id.buttonLightBlue)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.circle_color_light_blue_selected));
                break;
            case 6:
                findViewById(R.id.buttonBlue).setAlpha(1.0f);
                ((ImageView) findViewById(R.id.buttonBlue)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.circle_color_blue_selected));
                break;
            case 7:
                findViewById(R.id.buttonPurple).setAlpha(1.0f);
                ((ImageView) findViewById(R.id.buttonPurple)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.circle_color_purple_selected));
                break;
            case 8:
                findViewById(R.id.buttonPink).setAlpha(1.0f);
                ((ImageView) findViewById(R.id.buttonPink)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.circle_color_pink_selected));
                break;
            case 9:
                findViewById(R.id.buttonBlack).setAlpha(1.0f);
                ((ImageView) findViewById(R.id.buttonBlack)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.circle_color_black_selected));
                break;
            case 10:
                findViewById(R.id.buttonWhite).setAlpha(1.0f);
                ((ImageView) findViewById(R.id.buttonWhite)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.circle_color_white_selected));
                break;
        }
    }

    public void changeBrush(View view){
        mCustomCanvas.setPaintBrush(Integer.parseInt(view.getTag().toString()));
        findViewById(R.id.brushSmall).setAlpha(0.6f);
        findViewById(R.id.brushMedium).setAlpha(0.6f);
        findViewById(R.id.brushLarge).setAlpha(0.6f);
        switch (Integer.parseInt(view.getTag().toString())) {
            default:
            case 0:
                findViewById(R.id.brushSmall).setAlpha(1.0f);
                break;
            case 1:
                findViewById(R.id.brushMedium).setAlpha(1.0f);
                break;
            case 2:
                findViewById(R.id.brushLarge).setAlpha(1.0f);
                break;
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////

    protected void setUpListeners() {
        super.setUpListeners();

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               mCustomCanvas.clear();
            }
        });


    }
    ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
