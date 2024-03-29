
package com.motoli.apps.allsubjects;


import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 1/30/2016
 *
 */
public class ActivityOP08 extends ActivityOPRoot {

    private boolean mCreateFirstNumber;
    private boolean mAllowAction;
    private String mFirstNumber;
    private String mSecondNumber;
    private String mResultNumber;
    private int mMathType;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_op8);
        appData.addToClassOrder(5);

        restartActivity();

        fadeInOrOutScreenInActivity(true);
        mCurrentGSP = new HashMap<>(appData.getCurrentGroup_Section_Phase());
        mInstructionAudio = "info_op08";
        long mAudionDuration=0;//playInstructionAudio();
        mAudioHandler.postDelayed(proceedActivity,mAudionDuration);
    }

    public void clickNumber1(View view){
        if(mAllowAction) {
            mAllowAction = false;
            replaceFirstZero();
            if (mCreateFirstNumber && mFirstNumber.length() <= 2) {
                mFirstNumber += "1";
                setFirstNumber();
            } else if (!mCreateFirstNumber && mSecondNumber.length() <= 2) {
                mSecondNumber += "1";
                setSecondNumber();
            }
            mAllowAction = true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickNumber2(View view){
        if(mAllowAction) {
            mAllowAction = false;
            replaceFirstZero();
            if (mCreateFirstNumber && mFirstNumber.length() <= 2) {
                mFirstNumber += "2";
                setFirstNumber();
            } else if (!mCreateFirstNumber && mSecondNumber.length() <= 2) {
                mSecondNumber += "2";
                setSecondNumber();
            }
            mAllowAction = true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickNumber3(View view){
        if(mAllowAction) {
            mAllowAction = false;
            replaceFirstZero();
            if (mCreateFirstNumber && mFirstNumber.length() <= 2) {
                mFirstNumber += "3";
                setFirstNumber();
            } else if (!mCreateFirstNumber && mSecondNumber.length() <= 2) {
                mSecondNumber += "3";
                setSecondNumber();
            }
            mAllowAction = true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickNumber4(View view){
        if(mAllowAction) {
            mAllowAction = false;
            replaceFirstZero();
            if (mCreateFirstNumber && mFirstNumber.length() <= 2) {
                mFirstNumber += "4";
                setFirstNumber();
            } else if (!mCreateFirstNumber && mSecondNumber.length() <= 2) {
                mSecondNumber += "4";
                setSecondNumber();
            }
            mAllowAction = true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickNumber5(View view){
        if(mAllowAction) {
            mAllowAction = false;
            replaceFirstZero();
            if (mCreateFirstNumber && mFirstNumber.length() <= 2) {
                mFirstNumber += "5";
                setFirstNumber();
            } else if (!mCreateFirstNumber && mSecondNumber.length() <= 2) {
                mSecondNumber += "5";
                setSecondNumber();
            }
            mAllowAction = true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickNumber6(View view){
        if(mAllowAction) {
            mAllowAction = false;
            replaceFirstZero();
            if (mCreateFirstNumber && mFirstNumber.length() <= 3) {
                mFirstNumber += "6";
                setFirstNumber();
            } else if (!mCreateFirstNumber && mSecondNumber.length() <= 2) {
                mSecondNumber += "6";
                setSecondNumber();
            }
            mAllowAction = true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickNumber7(View view){
        if(mAllowAction) {
            mAllowAction = false;
            replaceFirstZero();
            if (mCreateFirstNumber && mFirstNumber.length() <= 2) {
                mFirstNumber += "7";
                setFirstNumber();
            } else if( !mCreateFirstNumber && mSecondNumber.length() <= 2) {
                mSecondNumber += "7";
                setSecondNumber();
            }
            mAllowAction = true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickNumber8(View view){
        if(mAllowAction) {
            mAllowAction = false;
            replaceFirstZero();
            if (mCreateFirstNumber && mFirstNumber.length() <= 2) {
                mFirstNumber += "8";
                setFirstNumber();
            } else if (!mCreateFirstNumber && mSecondNumber.length() <= 2) {
                mSecondNumber += "8";
                setSecondNumber();
            }
            mAllowAction = true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickNumber9(View view){
        if(mAllowAction) {
            mAllowAction = false;
            replaceFirstZero();
            if (mCreateFirstNumber && mFirstNumber.length() <= 2) {
                mFirstNumber += "9";
                setFirstNumber();
            } else if (!mCreateFirstNumber && mSecondNumber.length() <= 2) {
                mSecondNumber += "9";
                setSecondNumber();
            }
            mAllowAction = true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickBackspace(View view){
        if(mAllowAction) {
            mAllowAction = false;
            if (mCreateFirstNumber && mFirstNumber.length() <= 2) {
                if(mFirstNumber.length()>=1) {
                    mFirstNumber=mFirstNumber.substring(0,mFirstNumber.length()-1);
                    if(mFirstNumber.length()==0){
                        mFirstNumber="0";
                    }
                }
                setFirstNumber();
            } else if (mSecondNumber.length() <= 2) {
                if(mSecondNumber.length()>=1) {
                    mSecondNumber=mSecondNumber.substring(0,mSecondNumber.length()-1);
                    if(mSecondNumber.length()==0){
                        mSecondNumber="0";
                    }
                }
                setSecondNumber();
            }
            mAllowAction = true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickNumber0(View view){

        replaceFirstZero();
        if(mAllowAction && mCreateFirstNumber && mFirstNumber.length() <= 2){
            mAllowAction=false;
            mFirstNumber+="0";
            setFirstNumber();
        }else if(!mCreateFirstNumber && mSecondNumber.length() <= 2){
            mSecondNumber+="0";
            setSecondNumber();
        }
        mAllowAction=true;
    }
    ////////////////////////////////////////////////////////////////////////
    private void replaceFirstZero(){
        if(mCreateFirstNumber) {
            if (mFirstNumber.equals("0")) {
                mFirstNumber = "";
            }
        }else{
            if (mSecondNumber.equals("0")) {
                mSecondNumber = "";
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////
    private void setFirstNumber(){
        ((TextView) findViewById(R.id.firstNumber)).setText(mFirstNumber);
    }
    ////////////////////////////////////////////////////////////////////////
    private void setSecondNumber(){
        ((TextView) findViewById(R.id.secondNumber)).setText(mSecondNumber);
    }
    ////////////////////////////////////////////////////////////////////////
    public void resetMath(View view){
        restartActivity();
    }
    ///////////////////////////////////////////////////////////////////////
    private void restartActivity(){
        mCreateFirstNumber=true;
        mAllowAction=false;
        mFirstNumber="";
        mSecondNumber="";
        mResultNumber="";
        mMathType=0; //0:addition, 1:subtraction, 2:multiplication
        adjustSymbols();
        ((TextView) findViewById(R.id.mathSymbol)).setText("");
        findViewById(R.id.mathSymbol).setAlpha(0.0f);
        ((TextView) findViewById(R.id.firstNumber)).setText("0");
        ((TextView) findViewById(R.id.secondNumber)).setText("0");
        ((TextView) findViewById(R.id.resultNumber)).setText("");
        findViewById(R.id.equalSymbol).setAlpha(0.0f);
        mAudioHandler.postDelayed(proceedActivity,0);
    }
    ////////////////////////////////////////////////////////////////////////
    private void adjustSymbols(){
        if(mCreateFirstNumber){
            findViewById(R.id.minus).setAlpha(1.0f);
            findViewById(R.id.multiplication).setAlpha(1.0f);
            findViewById(R.id.plus).setAlpha(1.0f);
            findViewById(R.id.equal).setAlpha(0.3f);
        }else {
            findViewById(R.id.minus).setAlpha(0.3f);
            findViewById(R.id.multiplication).setAlpha(0.3f);
            findViewById(R.id.plus).setAlpha(0.3f);
            findViewById(R.id.equal).setAlpha(1.0f);
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickPlus(View view){
        if(mAllowAction && mCreateFirstNumber){
            mAllowAction=false;
            mCreateFirstNumber=false;
            mMathType=0;
            adjustSymbols();
            ((TextView) findViewById(R.id.mathSymbol)).setText(
                    getResources().getString(R.string.stringPlus));
            findViewById(R.id.mathSymbol).setAlpha(1.0f);
            mSecondNumber="0";
            ((TextView) findViewById(R.id.secondNumber)).setText(mSecondNumber);
            mAllowAction=true;
        }

    }
    ////////////////////////////////////////////////////////////////////////
    public void clickMinus(View view){
        if(mAllowAction && mCreateFirstNumber){
            mAllowAction=false;
            mCreateFirstNumber=false;
            mMathType=1;
            adjustSymbols();
            ((TextView) findViewById(R.id.mathSymbol)).setText(
                    getResources().getString(R.string.stringMinus));
            findViewById(R.id.mathSymbol).setAlpha(1.0f);
            mSecondNumber="0";
            ((TextView) findViewById(R.id.secondNumber)).setText(mSecondNumber);
            mAllowAction=true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickTimes(View view){
        if(mAllowAction && mCreateFirstNumber){
            mAllowAction=false;
            mCreateFirstNumber=false;
            mMathType=2;
            adjustSymbols();
            ((TextView) findViewById(R.id.mathSymbol)).setText(
                    getResources().getString(R.string.stringMultiplication));
            findViewById(R.id.mathSymbol).setAlpha(1.0f);
            mSecondNumber="0";
            ((TextView) findViewById(R.id.secondNumber)).setText(mSecondNumber);
            mAllowAction=true;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    public void clickEqual(View view){
        if(mAllowAction && !mCreateFirstNumber){
            mAllowAction=false;
            findViewById(R.id.equal).setAlpha(0.3f);

            int mResult;

            switch(mMathType){
                default:
                case 0:
                    mResult= Integer.parseInt(mFirstNumber)+Integer.parseInt(mSecondNumber);
                    break;
                case 1:
                    mResult= Integer.parseInt(mFirstNumber)-Integer.parseInt(mSecondNumber);
                    break;
                case 2:
                    mResult= Integer.parseInt(mFirstNumber)*Integer.parseInt(mSecondNumber);
                    break;
            }

            DecimalFormat formatter = new DecimalFormat("#######");
            mResultNumber = formatter.format(mResult);

           findViewById(R.id.equalSymbol).setAlpha(1.0f);
            ((TextView) findViewById(R.id.resultNumber)).setText(mResultNumber);

        }
    }
    ////////////////////////////////////////////////////////////////////////


    private Runnable proceedActivity = new Runnable() {
        @Override
        public void run() {
            mAllowAction=true;
        }
    };

}
