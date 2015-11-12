package com.motoli.apps.allsubjects;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */

import com.motoli.apps.allsubjects.R.raw;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import java.lang.reflect.Field;


public class Audio_Functions {

        private boolean allowSFX=true;
        private float maxVolume=0;
        private  String gSecondInstructionPart;

        MediaPlayer mp;
        Context context;

        public Audio_Functions(Context ct, boolean allowSFX, float maxVolume){
            this.context = ct;

            this.allowSFX=allowSFX;
            this.maxVolume=maxVolume;

            mp=new MediaPlayer();
            //mp.p
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);


        }

        /////////////////////////////////////////////////////////////////////////

        public void stopAudio(){

            try {

                if(mp!=null) {
                    mp.pause();
                    if (mp.isPlaying()) {
                        mp.stop();
                        mp.reset();
                        mp.release();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /////////////////////////////////////////////////////////////////////////

        protected long playSFXAudio(String sfxAudio){

            long audioDuration=0;
            if(allowSFX){
                sfxAudio = sfxAudio.replace(".mp3", "");
                int checkExistence = context.getResources().getIdentifier(sfxAudio, "raw", context.getPackageName());


                if(sfxAudio!=null && !sfxAudio.isEmpty()  && checkExistence!=0 ){

                    try {
                        Class<raw> res = R.raw.class;
                        Field field;
                        field = res.getField(sfxAudio);
                        int audioID = field.getInt(null);
                        //mp.reset();
                        mp = MediaPlayer.create(context, audioID);
                        mp.setVolume(maxVolume,maxVolume);
                        mp.start();
                        audioDuration=mp.getDuration();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mp.setOnCompletionListener(new OnCompletionListener() {

                        public void onCompletion(MediaPlayer mp) {
                            mp.stop();
                            mp.reset();
                        }
                    });
                }
            }
            return audioDuration;
        }

        /////////////////////////////////////////////////////////////////////////

        public long playGeneralAudio(String sfxAudio){

            long audioDuration=0;

            sfxAudio = sfxAudio.replace(".mp3", "");
            int checkExistence = context.getResources().getIdentifier(sfxAudio, "raw", context.getPackageName());


            if(sfxAudio!=null && !sfxAudio.isEmpty()  && checkExistence!=0 ){

                try {
                    Class<raw> res = R.raw.class;

                    int audioID = res.getField(sfxAudio).getInt(null);
                    //mp.reset();
                    mp = MediaPlayer.create(context, audioID);
                    mp.setVolume(maxVolume,maxVolume);
                    mp.start();
                    audioDuration=mp.getDuration();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                mp.setOnCompletionListener(new OnCompletionListener() {

                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        mp.reset();
                    }
                });
            }

            return audioDuration;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        protected long playInstructionAudio(String instructAudio, String secondInstructionPart){
            long audioDuration=0;
            instructAudio = instructAudio.replace(".mp3", "");
            int checkExistence = context.getResources().getIdentifier(instructAudio, "raw", context.getPackageName());
            gSecondInstructionPart=secondInstructionPart;

            if(instructAudio!=null && !instructAudio.isEmpty()  && checkExistence!=0 ){

                try {
                    Class<raw> res = R.raw.class;
                    Field field;
                    int audioID;

                    if(!gSecondInstructionPart.equals("")){
                        gSecondInstructionPart=gSecondInstructionPart.replace(".mp3", "");
                        field = res.getField(gSecondInstructionPart);
                        audioID = field.getInt(null);


                        mp = MediaPlayer.create(context, audioID);
                    //	mp.setVolume(100f, rightVolume)
                        audioDuration=mp.getDuration();

                        mp.stop();
                        mp.reset();

                    }

                    field = res.getField(instructAudio);
                    audioID = field.getInt(null);
                    //mp.reset();
                    mp = MediaPlayer.create(context, audioID);
                    audioDuration=audioDuration+mp.getDuration();
                    mp.setVolume(maxVolume,maxVolume);
                    mp.start();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                mp.setOnCompletionListener(new OnCompletionListener() {

                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        mp.reset();
                        if(!gSecondInstructionPart.equals("")){
                            gSecondInstructionPart=gSecondInstructionPart.replace(".mp3", "");

                            playGeneralAudio(gSecondInstructionPart);
                        }
                    }
                });
            }
            return audioDuration;
        }

}

