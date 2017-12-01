package me.creese.morze.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import me.creese.morze.R;
import me.creese.morze.morze.Morze;

public class FlashingActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_flashing);
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1;
        getWindow().setAttributes(layout);

        Morze morze = (Morze) getIntent().getSerializableExtra(Morze.EXTRA);
        morze.run(this,findViewById(R.id.flash));

    }
}

