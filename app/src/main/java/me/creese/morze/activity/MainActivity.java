package me.creese.morze.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import me.creese.morze.R;
import me.creese.morze.exception.NoFindCharacterException;
import me.creese.morze.morze.CameraMorze;
import me.creese.morze.morze.DrawFlashView;
import me.creese.morze.morze.Morze;
import me.creese.morze.morze.SoundMorze;


public class MainActivity extends Activity implements View.OnClickListener{

    private CameraMorze cameraWork;
    private Morze morze;
    private SoundMorze soundMorze;
    private DrawFlashView flashView;
    private TextView notification;
    private EditText textToMorze;
    private ImageView touchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initCamera();
        textToMorze = findViewById(R.id.textToMorze);
        ImageButton btnMorseScreen = findViewById(R.id.btn_get_morse_key);
        notification = findViewById(R.id.notification);
        notification.setVisibility(View.INVISIBLE);
        touchIcon = findViewById(R.id.touch_icon);
        Animation touchAnim = AnimationUtils.loadAnimation(this, R.anim.touch_icon_anim);
        touchIcon.startAnimation(touchAnim);


        final int maxLineLength = 10;


        btnMorseScreen.setOnClickListener(this);
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

        cameraWork = new CameraMorze(this);
        soundMorze = new SoundMorze(this);
        // while (!soundMorze.isLoad()) {}
        morze = new Morze(soundMorze);
        getPermissonCamera();



        ImageButton startBtn = findViewById(R.id.flashOnBtn);
        startBtn.setOnClickListener(this);

    }
    private void showNotification(CharSequence text) {

        notification.setText(text);
        notification.setVisibility(View.INVISIBLE);

        Animation upAnimation = AnimationUtils.loadAnimation(this, R.anim.show_notification);
        upAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                notification.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                notification.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        notification.startAnimation(upAnimation);
    }



    private void startFlashActivity() {
        Intent intent = new Intent(this, FlashingActivity.class);
        intent.putExtra(Morze.EXTRA, morze);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraWork.cameraRelease();
        soundMorze.release();
    }

    public void getPermissonCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        1);

            } else {

                // No explanation needed, we can request the permission.
                //Toast.makeText(getActivity(),"else",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_get_morse_key) {



        }
        if(v.getId() == R.id.flashOnBtn) {
            if(textToMorze.getText().toString().isEmpty()) {
                showNotification(getText(R.string.emty_morze_field));
                return;
            }

            try {
                morze.parseString(morze.parseToMorze(textToMorze.getText().toString()));
            }
            catch (NoFindCharacterException b) {
                showNotification(getText(R.string.fail_char));
                textToMorze.setText("");
                return;
            }


            startFlashActivity();
            morze.run(soundMorze);
            morze.run(cameraWork);
        }
    }
}
