package com.motoli.apps.allsubjects;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 10/8/2016.
 */

public class ActivityWD02New extends ActivitiesMasterParent
        implements LoaderManager.LoaderCallbacks {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_wd02);
        appData.addToClassOrder(5);

        mCurrentGSP=new HashMap<>(appData.getCurrentGroup_Section_Phase());

        findViewById(R.id.activityMainPart)
                .setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.activityMainPart)
                .setAnimation(AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.fade_in));
        mBeingValidated=true;


        ((TextView) findViewById(R.id.word1)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word2)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word3)).setTypeface(appData.getCurrentFontType());
        ((TextView) findViewById(R.id.word4)).setTypeface(appData.getCurrentFontType());


        clearActivity();
        setUpListeners();
       // setupFrameListens();

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////



    private void clearActivity(){






        ((TextView) findViewById(R.id.word1)).setText("");
        ((TextView) findViewById(R.id.word2)).setText("");
        ((TextView) findViewById(R.id.word3)).setText("");
        ((TextView) findViewById(R.id.word4)).setText("");

        ((TextView) findViewById(R.id.word1)).setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word2)).setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word3)).setTextColor(ContextCompat.getColor(this,R.color.normalBlack));
        ((TextView) findViewById(R.id.word4)).setTextColor(ContextCompat.getColor(this,R.color.normalBlack));


        ((ImageView) findViewById(R.id.btnValidate)).setImageResource(R.drawable.btn_validate_off);

        clearFrames();

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void clearFrames(){
        //clear frames
        ((ImageView)findViewById(R.id.frame1)).setImageResource(R.drawable.frm_wd02_off);
        ((ImageView)findViewById(R.id.frame2)).setImageResource(R.drawable.frm_wd02_off);
        ((ImageView)findViewById(R.id.frame3)).setImageResource(R.drawable.frm_wd02_off);
        ((ImageView)findViewById(R.id.frame4)).setImageResource(R.drawable.frm_wd02_off);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader loader) {

    }
}
