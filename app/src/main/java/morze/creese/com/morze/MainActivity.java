package morze.creese.com.morze;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CameraWork cameraWork;
    private Morze morze;
    private SoundMorze soundMorze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initCamera();
        TextView textToMorze = findViewById(R.id.textToMorze);

        cameraWork = new CameraWork(this);
        soundMorze = new SoundMorze(this);
       // while (!soundMorze.isLoad()) {}
        morze = new Morze(soundMorze);
        getPermissonCamera();


        Button startBtn = findViewById(R.id.flashOnBtn);
        startBtn.setOnClickListener(e -> {
            //System.out.println(morze.parseToMorze("fuck"));
            morze.parseString(morze.parseToMorze(textToMorze.getText().toString()));
            morze.run(soundMorze);
            morze.run(cameraWork);
        });

    }

    @Override
    public void finish() {
        super.finish();
        cameraWork.cameraRelease();
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
