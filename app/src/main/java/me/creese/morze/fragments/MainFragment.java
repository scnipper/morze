package me.creese.morze.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.creese.morze.R;
import me.creese.morze.activity.FlashingActivity;
import me.creese.morze.activity.MainActivity;
import me.creese.morze.activity.SettingsActivity;
import me.creese.morze.constants.Settings;
import me.creese.morze.exception.NoFindCharacterException;
import me.creese.morze.morze.CameraMorze;
import me.creese.morze.morze.Morze;
import me.creese.morze.morze.SoundMorze;
import me.creese.morze.views.DrawFlashView;


public class MainFragment extends Fragment implements View.OnClickListener{

    private Morze morze;
    private SoundMorze soundMorze;
    private DrawFlashView flashView;
    private TextView notification;
    private EditText textToMorze;
    private ImageView touchIcon;
    private ImageView logo;
    private RelativeLayout relative;
    private Animation goneLogo;
    private Animation gonFlashBtn;
    private ImageButton startBtn;
    private Animation touchAnim;
    private Context context;
    private MainActivity startActivity;
    private CameraMorze cameraMorze;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_main, container, false);
        context = view.getContext();

        initSetings();
        //initAnim();
        textToMorze = view.findViewById(R.id.textToMorze);
        //ImageButton btnMorseScreen = view.findViewById(R.id.btn_get_morse_key);
        notification = view.findViewById(R.id.notification);
        notification.setVisibility(View.INVISIBLE);
        touchIcon = view.findViewById(R.id.touch_icon);
        touchAnim = AnimationUtils.loadAnimation(context, R.anim.touch_icon_anim);
        touchIcon.startAnimation(touchAnim);
        startActivity.showNotification("Напиши текст который хочешь воспроизвести",notification);


        logo = view.findViewById(R.id.logo);
        relative = view.findViewById(R.id.relativeLayout);



        ImageButton settingsBtn = view.findViewById(R.id.settings_btn);
        settingsBtn.setOnClickListener(this);


        final int maxLineLength = 10;


//        btnMorseScreen.setOnClickListener(this);
        textToMorze.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(s+" "+start+" "+before+" "+count);
                if(count == 15)
                    textToMorze.append("\n");
            }
        });





        startBtn = view.findViewById(R.id.flashOnBtn);
        startBtn.setOnClickListener(this);

        return view;
    }



    private void initSetings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Settings.LENGTH = prefs.getInt("seekBar",100);
        Settings.IS_FLASHING_CAMERA = prefs.getBoolean("flash",true);
        Settings.IS_FLASHING_SCREEN = prefs.getBoolean("screen",false);
        Settings.IS_PLAY_SOUND = prefs.getBoolean("sound",false);
        Settings.print();
    }






    private void startKeyActivity() {
     /*   Intent intent = new Intent(this, KeyActivity.class);
        intent.putExtra(Morze.EXTRA,morze);
        startActivity(intent);*/
    }

    private void startFlashActivity() {
        Intent intent = new Intent(context, FlashingActivity.class);
        intent.putExtra(Morze.EXTRA, morze);
        startActivity(intent);
    }






    @Override
    public void onClick(View v) {
        /*if(v.getId() == R.id.btn_get_morse_key) {



            startKeyActivity();

        }*/
        if(v.getId() == R.id.flashOnBtn) {
            if(textToMorze.getText().toString().isEmpty()) {
                startActivity.showNotification(getText(R.string.emty_morze_field),notification);
                return;
            }

            try {
                morze.parseString(morze.parseToMorze(textToMorze.getText().toString()));
            }
            catch (NoFindCharacterException b) {
                startActivity.showNotification(getText(R.string.fail_char),notification);
                textToMorze.setText("");
                return;
            }


            if(Settings.IS_FLASHING_SCREEN) startFlashActivity();
            if(Settings.IS_PLAY_SOUND) morze.run(soundMorze);
            if(Settings.IS_FLASHING_CAMERA) morze.run(cameraMorze);
        }
        if(v.getId() == R.id.settings_btn) {
            startActivity(new Intent(context,SettingsActivity.class));

        }
    }

    public void setMorze(Morze morze) {
        this.morze = morze;
    }

    public void setSoundMorze(SoundMorze soundMorze) {
        this.soundMorze = soundMorze;
    }

    public void setStartActivity(MainActivity startActivity) {
        this.startActivity = startActivity;
    }

    public void setCameraMorze(CameraMorze cameraMorze) {
        this.cameraMorze = cameraMorze;
    }
}
