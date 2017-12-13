package me.creese.morze.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.creese.morze.R;
import me.creese.morze.fragments.KeyFragment;
import me.creese.morze.fragments.MainFragment;
import me.creese.morze.morze.CameraMorze;
import me.creese.morze.morze.Morze;
import me.creese.morze.morze.SoundMorze;
import me.creese.morze.util.ResizeWidthAnimation;

public class MainActivity extends FragmentActivity {

    /** идентификатор первого фрагмента. */
    public static final int FRAGMENT_ONE = 0;

    /** идентификатор второго. */
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENTS = 2;
    /** адаптер фрагментов. */
    private FragmentPagerAdapter _fragmentPagerAdapter;
    /** список фрагментов для отображения. */
    private final List<Fragment> _fragments = new ArrayList<Fragment>();
    /** сам ViewPager который будет все это отображать. */
    private ViewPager _viewPager;
    private SoundMorze soundMorze;
    private Morze morze;
    private CameraMorze camerMorze;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tabhost_activity);

        soundMorze = new SoundMorze(this);
        morze = new Morze();
        camerMorze = new CameraMorze(this);
        getPermissonCamera();

        MainFragment m = new MainFragment();
        m.setMorze(morze);
        m.setSoundMorze(soundMorze);
        m.setStartActivity(this);
        m.setCameraMorze(camerMorze);

        KeyFragment k = new KeyFragment();
        k.setMorze(morze);
        k.setSoundMorze(soundMorze);
        k.setStartActivity(this);



        _fragments.add(FRAGMENT_ONE, m);
        _fragments.add(FRAGMENT_TWO, k);
        _fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {

                return FRAGMENTS;
            }

            @Override
            public Fragment getItem(final int position) {

                return _fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(final int position) {

                switch (position) {
                    case FRAGMENT_ONE:
                        return "Title One";
                    case FRAGMENT_TWO:
                        return "Title Two";
                    default:
                        return null;
                }
            }
        };
        _viewPager = findViewById(R.id.pager);
        _viewPager.setAdapter(_fragmentPagerAdapter);
        _viewPager.setCurrentItem(0);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void checkToShowHelpAnim() {
        View blackScreenHelp = findViewById(R.id.black_help_screen);
        blackScreenHelp.setVisibility(View.VISIBLE);
        ResizeWidthAnimation anim = new ResizeWidthAnimation(blackScreenHelp, 800);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                blackScreenHelp.animate().alpha(0).setDuration(500).start();
                blackScreenHelp.animate().setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        blackScreenHelp.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim.setDuration(500);
        blackScreenHelp.startAnimation(anim);
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
    public void onDestroy() {
        super.onDestroy();
        camerMorze.cameraRelease();
        soundMorze.release();
    }



    public void showNotification(CharSequence text, TextView notification) {

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
                checkToShowHelpAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        notification.startAnimation(upAnimation);
    }
}
