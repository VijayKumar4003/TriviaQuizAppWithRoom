package com.infowithvijay.triviaquizappwithroom;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class MusicController extends Application {


    private static Context mContext;
    private static MediaPlayer player;
    public static Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        setContext(getApplicationContext());

        player = new MediaPlayer();

        mediaPlayerIntilizer();

    }

    private void mediaPlayerIntilizer() {

        try{
            player = MediaPlayer.create(getAppContext(),R.raw.background_music);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setLooping(true);
            player.setVolume(1f,1f);

        }catch (IllegalStateException e){
            e.printStackTrace();
        }



    }

    private static void setContext(Context context){
        mContext = context;

    }

    private static Context getAppContext(){
        return mContext;
    }

    public static void playSound(){

        try {

            if (SettingPreference.getMusicEnableDisable(mContext) && !player.isPlaying()) {
                player.start();
            }
        }
        catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    public static void StopSound(){
        if (player.isPlaying()){
            player.pause();
        }
    }

}
