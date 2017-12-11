package me.creese.morze.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import me.creese.morze.R;
import me.creese.morze.constants.Settings;


public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);




        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Settings.LENGTH = sharedPreferences.getInt("seekBar",100);
        Settings.IS_FLASHING_CAMERA = sharedPreferences.getBoolean("flash",true);
        Settings.IS_FLASHING_SCREEN = sharedPreferences.getBoolean("screen",false);
        Settings.IS_PLAY_SOUND = sharedPreferences.getBoolean("sound",false);
    }
}
