package me.creese.morze.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
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

        Preference preferenceRate = findPreference("rate");
        preferenceRate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String url;
                try {
                    // Check whether Google Play store is installed or not:
                    SettingsActivity.this.getPackageManager().getPackageInfo(
                            "com.android.vending", 0);

                    url = "market://details?id=" + SettingsActivity.this.getPackageName();
                } catch (final Exception e) {
                    url = "https://play.google.com/store/apps/details?id="
                            + SettingsActivity.this.getPackageName();
                }

                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                startActivity(intent);
                return false;
            }
        });
        Preference prefAbout = findPreference("about");
        prefAbout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5169730328249434569"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /*if(key.equals("rate")) {
            Toast.makeText(this,"LOL",Toast.LENGTH_SHORT).show();
        }*/
        Settings.LENGTH = sharedPreferences.getInt("seekBar",100);
        Settings.IS_FLASHING_CAMERA = sharedPreferences.getBoolean("flash",true);
        Settings.IS_FLASHING_SCREEN = sharedPreferences.getBoolean("screen",false);
        Settings.IS_PLAY_SOUND = sharedPreferences.getBoolean("sound",false);
    }
}
