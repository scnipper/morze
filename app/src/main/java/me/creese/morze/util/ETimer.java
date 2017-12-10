package me.creese.morze.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yoba2 on 09.12.2017.
 */

public class ETimer {
    private Runnable shedule;
    private long delay;
    private int period;
    private Timer timer;
    private TimerTask timerTask;

    public ETimer() {

    }
    public void setShedule(Runnable shedule) {
        this.shedule = shedule;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
    public void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                shedule.run();
            }
        };
        timer.schedule(timerTask,delay);
    }
}
