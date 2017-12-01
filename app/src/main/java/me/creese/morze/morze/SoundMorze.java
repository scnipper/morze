package me.creese.morze.morze;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import me.creese.morze.R;


/**
 * Created by yoba2 on 27.11.2017.
 */

public class SoundMorze {
    private final Context context;
    private SoundPool mSoundPool;
    private int dot;
    private int line;
    private int loadSound;
    private boolean isLoad;
    private boolean isPlay;
    private int streamID;

    public SoundMorze(Context context) {
        this.context = context;
        initSound();
    }

    private void initSound() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadSOund();
            }
        }).start();

    }

    public void stopDot() {


        mSoundPool.stop(streamID);
        isPlay = false;


    }

    public void playDot() {


        streamID = mSoundPool.play(dot, 1, 1, 1, 0, 1);


    }

   /* public void playLine() {
        mSoundPool.stop(dot);
        mSoundPool.play(dot, 1, 1, 1, 0, 1);
    }*/

    private void loadSOund() {
        dot = mSoundPool.load(context, R.raw.dot, 1);
        //line = mSoundPool.load(context,R.raw.line,1);
        mSoundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            System.out.println("id sound" + sampleId);
            if (status > 0) {

                isLoad = true;

            }
        });
    }

    public boolean isLoad() {
        return isLoad;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }
}
