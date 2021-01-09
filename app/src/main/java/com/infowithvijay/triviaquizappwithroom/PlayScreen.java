package com.infowithvijay.triviaquizappwithroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.infowithvijay.triviaquizappwithroom.MusicController.StopSound;

public class PlayScreen extends AppCompatActivity {

    private static Context context;

    Button btPlayQuiz,btSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        btPlayQuiz = findViewById(R.id.btPlayQuiz);
        btSettings = findViewById(R.id.btsettings);


        context = getApplicationContext();
        MusicController.currentActivity = this;


        if (SettingPreference.getMusicEnableDisable(context)){
            try {
                MusicController.playSound();
            }catch (IllegalStateException e){
                e.printStackTrace();
            }
        }



        btSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent settingIntent = new Intent(PlayScreen.this,Settings.class);
                startActivity(settingIntent);


            }
        });



        btPlayQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent playquizIntent = new Intent(PlayScreen.this,QuizActivity.class);
                startActivity(playquizIntent);

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StopSound();
        finish();
    }
}
