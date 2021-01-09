package com.infowithvijay.triviaquizappwithroom;

import android.content.Context;
import android.media.MediaPlayer;

public class PlaySound {

    private Context mContext;
    private MediaPlayer mediaPlayer;


    public PlaySound(Context mContext){
        this.mContext = mContext;
    }


    public void seAudioforAnswers(int flag){

        switch (flag){

            case 1:
                int correctAudio = R.raw.correct;
                playMusic(correctAudio);
                break;

            case 2:
                int wrongAudio = R.raw.wrong;
                playMusic(wrongAudio);
                break;

            case 3:
                int timerAudio = R.raw.times_up_sound;
                playMusic(timerAudio);
                break;

        }


    }


    private void playMusic(int audiofile){

        mediaPlayer = MediaPlayer.create(mContext,audiofile);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });

    }

}
