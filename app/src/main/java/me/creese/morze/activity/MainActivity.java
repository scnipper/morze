package me.creese.morze.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.creese.morze.R;
import me.creese.morze.exception.NoFindCharacterException;
import me.creese.morze.morze.CameraMorze;
import me.creese.morze.morze.DrawFlashView;
import me.creese.morze.morze.Morze;
import me.creese.morze.morze.SoundMorze;


public class MainActivity extends Activity {

    private CameraMorze cameraWork;
    private Morze morze;
    private SoundMorze soundMorze;
    private DrawFlashView flashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initCamera();
        TextView textToMorze = findViewById(R.id.textToMorze);
        cameraWork = new CameraMorze(this);
        soundMorze = new SoundMorze(this);
        // while (!soundMorze.isLoad()) {}
        morze = new Morze(soundMorze);
        getPermissonCamera();


        Button startBtn = findViewById(R.id.flashOnBtn);
        startBtn.setOnClickListener(e -> {
            if(textToMorze.getText().toString().isEmpty()) {
                Toast.makeText(this,getText(R.string.emty_morze_field),Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                morze.parseString(morze.parseToMorze(textToMorze.getText().toString()));
            }
            catch (NoFindCharacterException b) {
                Toast.makeText(this,getText(R.string.fail_char),Toast.LENGTH_SHORT).show();
                textToMorze.setText("");
                return;
            }


            startFlashActivity();
            morze.run(soundMorze);
            morze.run(cameraWork);
        });

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


}
