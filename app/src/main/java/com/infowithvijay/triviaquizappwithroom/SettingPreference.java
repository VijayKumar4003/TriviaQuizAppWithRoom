package com.infowithvijay.triviaquizappwithroom;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;


public class SettingPreference {

    public static final String SHOW_MUSIC_ONOFF = "show_music_enable_disable";


    public static void setMusicEnableDisable(Context context,Boolean result){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putBoolean(SHOW_MUSIC_ONOFF,result);
        prefEditor.commit();

    }

    public static boolean getMusicEnableDisable(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(SHOW_MUSIC_ONOFF,Constants.DEFAULT_MUSIC_SETTING);
    }


}
