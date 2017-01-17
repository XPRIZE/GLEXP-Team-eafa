package com.motoli.apps.allsubjects;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 4/18/2016.
 */
public class ActivityOPRoot extends ActivitiesMasterParent {

    protected int mIncorrectInRound=0;

    protected int mOperand = 1;
    protected int mCorrectInARow=0;

    private final static int MATH_SPACES=3;
    protected String mUsersAnswer="";
    protected int mNumberButton=0;
    protected boolean mBeingValidated;
    protected boolean mAllowNumberPad;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNumber=0;


    }
    ///////////////////////////////////////////////////////////////////////////////////////////


    public Runnable processNumberButton = new Runnable() {
        @Override
        public void run() {
            switch (mNumberButton){
                default:
                case 0:{
                    if(mOperand==2 || mOperand==3
                            || (mUsersAnswer.length()<MATH_SPACES && mUsersAnswer.length()>=1)
                            || mActivityNumber==2) {
                        mUsersAnswer += "0";
                    }
                    findViewById(R.id.guessNumber0)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalGuessNumber0)!=null) {
                        findViewById(R.id.verticalGuessNumber0)
                                .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
                case 1:{
                    if(mUsersAnswer.length()<MATH_SPACES) {
                        mUsersAnswer += "1";
                    }
                    //mUsersAnswer=mUsersAnswer.replace("0","");
                    findViewById(R.id.guessNumber1)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalGuessNumber1)!=null) {
                    findViewById(R.id.verticalGuessNumber1)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
                case 2:{
                    if(mUsersAnswer.length()<MATH_SPACES) {
                        mUsersAnswer += "2";
                    }
                  // mUsersAnswer=mUsersAnswer.replace("0","");
                    findViewById(R.id.guessNumber2)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalGuessNumber2)!=null) {
                    findViewById(R.id.verticalGuessNumber2)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
                case 3:{
                    if(mUsersAnswer.length()<MATH_SPACES) {
                        mUsersAnswer += "3";
                    }
                   // mUsersAnswer=mUsersAnswer.replace("0","");
                    findViewById(R.id.guessNumber3)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalGuessNumber3)!=null) {
                    findViewById(R.id.verticalGuessNumber3)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
                case 4:{
                    if(mUsersAnswer.length()<MATH_SPACES) {
                        mUsersAnswer += "4";
                    }
                    //mUsersAnswer=mUsersAnswer.replace("0","");
                    findViewById(R.id.guessNumber4)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalGuessNumber4)!=null) {
                    findViewById(R.id.verticalGuessNumber4)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
                case 5:{
                    if(mUsersAnswer.length()<MATH_SPACES) {
                        mUsersAnswer += "5";
                    }
                   // mUsersAnswer=mUsersAnswer.replace("0","");
                    findViewById(R.id.guessNumber5)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalGuessNumber5)!=null) {
                    findViewById(R.id.verticalGuessNumber5)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
                case 6:{
                    if(mUsersAnswer.length()<MATH_SPACES) {
                        mUsersAnswer += "6";
                    }
                    //mUsersAnswer=mUsersAnswer.replace("0","");
                    findViewById(R.id.guessNumber6)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalGuessNumber6)!=null) {
                    findViewById(R.id.verticalGuessNumber6)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
                case 7:{
                    if(mUsersAnswer.length()<MATH_SPACES) {
                        mUsersAnswer += "7";
                    }
                   // mUsersAnswer=mUsersAnswer.replace("0","");
                    findViewById(R.id.guessNumber7)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalGuessNumber7)!=null) {
                    findViewById(R.id.verticalGuessNumber7)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
                case 8:{
                    if(mUsersAnswer.length()<MATH_SPACES) {
                        mUsersAnswer += "8";
                    }
                    //mUsersAnswer=mUsersAnswer.replace("0","");
                    findViewById(R.id.guessNumber8)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalGuessNumber8)!=null) {
                    findViewById(R.id.verticalGuessNumber8)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
                case 9:{
                    if(mUsersAnswer.length()<MATH_SPACES) {
                        mUsersAnswer += "9";
                    }
                  //  mUsersAnswer=mUsersAnswer.replace("0","");
                    findViewById(R.id.guessNumber9)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalGuessNumber9)!=null) {
                    findViewById(R.id.verticalGuessNumber9)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
                case 10: {
                    if(mUsersAnswer.length()>=1) {
                        mUsersAnswer=mUsersAnswer.substring(0,mUsersAnswer.length()-1);
                    }
                    findViewById(R.id.backspace)
                            .setBackgroundResource(R.drawable.number_box_unselected);
                    if(findViewById(R.id.verticalBackspace)!=null) {
                        findViewById(R.id.verticalBackspace)
                                .setBackgroundResource(R.drawable.number_box_unselected);
                    }
                    break;
                }
            }///end switch

            setValidate();

        }
    };


    //////////////////////////////////////////////////////////////////////////////////////////

    public void setValidate() {}

    //////////////////////////////////////////////////////////////////////////////////////////

    protected void resetNumberBoxes() {
        findViewById(R.id.guessNumber0).setBackgroundResource(R.drawable.number_box_unselected);
        findViewById(R.id.guessNumber1).setBackgroundResource(R.drawable.number_box_unselected);
        findViewById(R.id.guessNumber2).setBackgroundResource(R.drawable.number_box_unselected);
        findViewById(R.id.guessNumber3).setBackgroundResource(R.drawable.number_box_unselected);
        findViewById(R.id.guessNumber4).setBackgroundResource(R.drawable.number_box_unselected);
        findViewById(R.id.guessNumber5).setBackgroundResource(R.drawable.number_box_unselected);
        findViewById(R.id.guessNumber6).setBackgroundResource(R.drawable.number_box_unselected);
        findViewById(R.id.guessNumber7).setBackgroundResource(R.drawable.number_box_unselected);
        findViewById(R.id.guessNumber8).setBackgroundResource(R.drawable.number_box_unselected);
        findViewById(R.id.guessNumber9).setBackgroundResource(R.drawable.number_box_unselected);

        if(findViewById(R.id.verticalGuessNumber1)!=null) {

            findViewById(R.id.verticalGuessNumber0)
                    .setBackgroundResource(R.drawable.number_box_unselected);
            findViewById(R.id.verticalGuessNumber1)
                    .setBackgroundResource(R.drawable.number_box_unselected);
            findViewById(R.id.verticalGuessNumber2)
                    .setBackgroundResource(R.drawable.number_box_unselected);
            findViewById(R.id.verticalGuessNumber3)
                    .setBackgroundResource(R.drawable.number_box_unselected);
            findViewById(R.id.verticalGuessNumber4)
                    .setBackgroundResource(R.drawable.number_box_unselected);
            findViewById(R.id.verticalGuessNumber5)
                    .setBackgroundResource(R.drawable.number_box_unselected);
            findViewById(R.id.verticalGuessNumber6)
                    .setBackgroundResource(R.drawable.number_box_unselected);
            findViewById(R.id.verticalGuessNumber7)
                    .setBackgroundResource(R.drawable.number_box_unselected);
            findViewById(R.id.verticalGuessNumber8)
                    .setBackgroundResource(R.drawable.number_box_unselected);
            findViewById(R.id.verticalGuessNumber9)
                    .setBackgroundResource(R.drawable.number_box_unselected);
            findViewById(R.id.verticalBackspace)
                    .setBackgroundResource(R.drawable.number_box_unselected);
        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    public void setUpNumberPad() {
        findViewById(R.id.guessNumber0).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetNumberBoxes();
                if (mOperand==2 || mOperand ==3 || (mUsersAnswer.length() < MATH_SPACES
                        && ((mUsersAnswer.length() > 0 && mActivityNumber != 2)
                        || mActivityNumber == 2))
                        && !mBeingValidated
                        && mAllowNumberPad) {
                    playClickSound();
                    mNumberButton = 0;
                    findViewById(R.id.guessNumber0)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 200);
                }
            }
        });

        findViewById(R.id.guessNumber1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetNumberBoxes();
                if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad
                        && !mUsersAnswer.equals("0")) {
                    playClickSound();
                    mNumberButton = 1;
                    findViewById(R.id.guessNumber1)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 200);
                }
            }
        });

        findViewById(R.id.guessNumber2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetNumberBoxes();
                if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad
                        && !mUsersAnswer.equals("0")) {
                    playClickSound();
                    mNumberButton = 2;
                    findViewById(R.id.guessNumber2)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 200);
                }
            }
        });

        findViewById(R.id.guessNumber3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad
                        && !mUsersAnswer.equals("0")) {
                    playClickSound();
                    mNumberButton = 3;
                    findViewById(R.id.guessNumber3)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 200);
                }
            }
        });

        findViewById(R.id.guessNumber4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetNumberBoxes();
                if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad
                        && !mUsersAnswer.equals("0")) {
                    playClickSound();
                    mNumberButton = 4;
                    findViewById(R.id.guessNumber4)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 200);
                }
            }
        });

        findViewById(R.id.guessNumber5).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetNumberBoxes();
                if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad
                        && !mUsersAnswer.equals("0")) {
                    playClickSound();
                    mNumberButton = 5;
                    findViewById(R.id.guessNumber5)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 200);
                }
            }
        });

        findViewById(R.id.guessNumber6).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetNumberBoxes();
                if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad
                        && !mUsersAnswer.equals("0")) {
                    playClickSound();
                    mNumberButton = 6;
                    findViewById(R.id.guessNumber6)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 200);
                }
            }
        });

        findViewById(R.id.guessNumber7).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetNumberBoxes();
                if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad
                        && !mUsersAnswer.equals("0")) {
                    playClickSound();
                    mNumberButton = 7;
                    findViewById(R.id.guessNumber7)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 200);
                }
            }
        });

        findViewById(R.id.guessNumber8).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetNumberBoxes();
                if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad
                        && !mUsersAnswer.equals("0")) {
                    playClickSound();
                    mNumberButton = 8;
                    findViewById(R.id.guessNumber8)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 200);
                }
            }
        });

        findViewById(R.id.guessNumber9).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetNumberBoxes();
                if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad
                        && !mUsersAnswer.equals("0")) {
                    playClickSound();
                    mNumberButton = 9;
                    findViewById(R.id.guessNumber9)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 200);
                }
            }
        });

        findViewById(R.id.backspace).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetNumberBoxes();
                if (mUsersAnswer.length() > 0 && !mBeingValidated && mAllowNumberPad) {
                    playClickSound();
                    mNumberButton = 10;
                    findViewById(R.id.backspace)
                            .setBackgroundResource(R.drawable.number_box_selected);
                    guessHandler.postDelayed(processNumberButton, 500);
                }
            }
        });

        if(findViewById(R.id.verticalGuessNumber1)!=null) {
            findViewById(R.id.verticalGuessNumber0).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetNumberBoxes();
                    if (mUsersAnswer.length() < MATH_SPACES
                            && ((mUsersAnswer.length() > 0 && mActivityNumber != 2)
                            || mActivityNumber == 2)
                            && !mBeingValidated
                            && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 0;
                        findViewById(R.id.verticalGuessNumber0)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 200);
                    }
                }
            });

            findViewById(R.id.verticalGuessNumber1).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetNumberBoxes();
                    if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 1;
                        findViewById(R.id.verticalGuessNumber1)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 200);
                    }
                }
            });

            findViewById(R.id.verticalGuessNumber2).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetNumberBoxes();
                    if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 2;
                        findViewById(R.id.verticalGuessNumber2)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 200);
                    }
                }
            });

            findViewById(R.id.verticalGuessNumber3).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 3;
                        findViewById(R.id.verticalGuessNumber3)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 200);
                    }
                }
            });

            findViewById(R.id.verticalGuessNumber4).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetNumberBoxes();
                    if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 4;
                        findViewById(R.id.verticalGuessNumber4)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 200);
                    }
                }
            });

            findViewById(R.id.verticalGuessNumber5).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetNumberBoxes();
                    if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 5;
                        findViewById(R.id.verticalGuessNumber5)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 200);
                    }
                }
            });

            findViewById(R.id.verticalGuessNumber6).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetNumberBoxes();
                    if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 6;
                        findViewById(R.id.verticalGuessNumber6)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 200);
                    }
                }
            });

            findViewById(R.id.verticalGuessNumber7).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetNumberBoxes();
                    if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 7;
                        findViewById(R.id.verticalGuessNumber7)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 200);
                    }
                }
            });

            findViewById(R.id.verticalGuessNumber8).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetNumberBoxes();
                    if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 8;
                        findViewById(R.id.verticalGuessNumber8)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 200);
                    }
                }
            });

            findViewById(R.id.verticalGuessNumber9).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetNumberBoxes();
                    if (mUsersAnswer.length() < MATH_SPACES && !mBeingValidated && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 9;
                        findViewById(R.id.verticalGuessNumber9)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 200);
                    }
                }
            });

            findViewById(R.id.verticalBackspace).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetNumberBoxes();
                    if (mUsersAnswer.length() > 0 && !mBeingValidated && mAllowNumberPad) {
                        playClickSound();
                        mNumberButton = 10;
                        findViewById(R.id.verticalBackspace)
                                .setBackgroundResource(R.drawable.number_box_selected);
                        guessHandler.postDelayed(processNumberButton, 500);
                    }
                }
            });
        }
    }
}
