package com.motoli.apps.allsubjects;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 */
 class Audio_Functions extends MediaPlayer{

        private boolean allowSFX=true;
        private float maxVolume=0;
        private String mSecondAudioOutside;
        private MediaPlayer mp;
        Context context;


        Audio_Functions(Context ct, boolean allowSFX, float maxVolume){
            this.context = ct;

            this.allowSFX=allowSFX;
            this.maxVolume=maxVolume;

        }



        /////////////////////////////////////////////////////////////////////////

        void stopAudio(){

            if(mp!=null){
                mp.release();
            }


        }

        /////////////////////////////////////////////////////////////////////////

        long playFeedBackAudio(String sfxAudio){

            long mAudioDuration=0;
            if(allowSFX){
                if(sfxAudio!=null && !sfxAudio.isEmpty()  ){

                    sfxAudio = sfxAudio.replace(".mp3", "");
                    int checkExistence = context.getResources().getIdentifier(sfxAudio, "raw",
                            context.getPackageName());
                    this.stopAudio();
                    if( checkExistence!=0 ) {
                        if (mp != null) {
                            mp.release();
                            mp=null;

                        }
                        mp = new MediaPlayer();
                        //mp.p
                        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mp.setVolume(maxVolume, maxVolume);

                        mp = MediaPlayer.create(context, checkExistence);
                        mAudioDuration = mp.getDuration();
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.stop();

                            }
                        });
                    }
                }else{
                    stopAudio();
                }
            }
            return mAudioDuration;
        }

        /////////////////////////////////////////////////////////////////////////

         long getAudioDuration(String mAudio){

            long mAudioDuration=0;

            mAudio = mAudio.replace(".mp3", "");
            int checkExistence = context.getResources().getIdentifier(mAudio, "raw", context.getPackageName());
           // this.stopAudio();

            if(mAudio!=null && !mAudio.isEmpty()  && checkExistence!=0 ){
                if(mp!=null){
                    mp.release();
                    mp=null;
                }
                mp=new MediaPlayer();
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.setVolume(maxVolume, maxVolume);

                mp = MediaPlayer.create(context, checkExistence);

                mAudioDuration=mp.getDuration();
                if(mp!=null) {
                    if(mp.isPlaying())
                        mp.stop();
                    mp.release();
                    mp=null;
                }

            }else{
                stopAudio();
            }

            return mAudioDuration;
        }

        /////////////////////////////////////////////////////////////////////////////////////

        public long playGeneralAudio(String sfxAudio){

            long mAudioDuration=0;

            if(sfxAudio!=null && !sfxAudio.isEmpty()  ){

                sfxAudio = sfxAudio.replace(".mp3", "");
                int checkExistence = context.getResources().getIdentifier(sfxAudio, "raw", context.getPackageName());
                this.stopAudio();
                if( checkExistence!=0 ) {
                    if (mp != null) {
                        //mp.release();
                        mp = null;
                    }
                    mp = new MediaPlayer();
                    //mp.p
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mp.setVolume(maxVolume, maxVolume);

                    mp = MediaPlayer.create(context, checkExistence);
                    mAudioDuration = mp.getDuration();
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.stop();

                        }
                    });
                }
            }else{
                stopAudio();
            }
 /**/
            return mAudioDuration;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        long playSplitAudio(String mFirstAudio, String mSecondAudio){
            long mAudioDuration=0;
            mFirstAudio = mFirstAudio.replace(".mp3", "");
            int checkExistence = context.getResources().getIdentifier(mFirstAudio,
                    "raw", context.getPackageName());
            mp=new MediaPlayer();
            mSecondAudioOutside=mSecondAudio;
            mSecondAudioOutside = mSecondAudioOutside.replace(".mp3", "");
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if(mFirstAudio!=null && !mFirstAudio.isEmpty()  && checkExistence!=0 ){
                if(!mSecondAudioOutside.equals("") && mSecondAudioOutside!=null) {
                    mAudioDuration = getAudioDuration(mSecondAudioOutside);
                }
                if(mp!=null){
                    mp.release();
                    mp=null;
                }
                mp=new MediaPlayer();
                //mp.p
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.setVolume(maxVolume, maxVolume);

                mp = MediaPlayer.create(context, checkExistence);
                mAudioDuration=mAudioDuration+mp.getDuration();
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();


                        if(!mSecondAudioOutside.equals("")){
                            mSecondAudioOutside=mSecondAudioOutside.replace(".mp3", "");

                            playGeneralAudio(mSecondAudioOutside);
                        }
                    }
                });
            }else{
                stopAudio();
                playGeneralAudio(mSecondAudioOutside);
            }



            return mAudioDuration;
        }

}

