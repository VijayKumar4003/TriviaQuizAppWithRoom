package com.infowithvijay.triviaquizappwithroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import static com.infowithvijay.triviaquizappwithroom.MusicController.StopSound;

public class Settings extends AppCompatActivity {

    private Context mContext;
    private Switch mMusicCheckBox;
    private Button ok_btn;
    private boolean isMusicOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mContext = Settings.this;
        MusicController.currentActivity = this;

        initViews();

    }


    private void initViews(){

        mMusicCheckBox = findViewById(R.id.music_checkbox);
        mMusicCheckBox.setChecked(true);
        ok_btn = findViewById(R.id.bt_ok);

        populateMusic();

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPlayScreen();
            }
        });



    }

    private void goToPlayScreen() {

        Intent intentSetting = new Intent(Settings.this,PlayScreen.class);
        startActivity(intentSetting);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentSetting = new Intent(Settings.this,PlayScreen.class);
        startActivity(intentSetting);
    }

    public void viewClickHandler(View view) {

        switch (view.getId()){

            case R.id.music_checkbox:

               switchMusicEnableDisable();

                break;

        }

    }

    private void switchMusicEnableDisable() {

        isMusicOn = !isMusicOn;
        if (isMusicOn){
            SettingPreference.setMusicEnableDisable(mContext,true);
            MusicController.playSound();
        }else {
            SettingPreference.setMusicEnableDisable(mContext,false);
            StopSound();
        }

        populateMusic();

    }

    private void populateMusic() {

        if (SettingPreference.getMusicEnableDisable(mContext)){
            MusicController.playSound();
            mMusicCheckBox.setChecked(true);
        }else {
            StopSound();
            mMusicCheckBox.setChecked(false);
        }

        isMusicOn = SettingPreference.getMusicEnableDisable(mContext);

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
