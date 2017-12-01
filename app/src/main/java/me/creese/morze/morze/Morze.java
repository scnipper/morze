package me.creese.morze.morze;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import me.creese.morze.activity.FlashingActivity;


/**
 * Created by yoba2 on 27.11.2017.
 */

public class Morze implements Serializable {
    public static final String EXTRA = "Morze";

    private static final int DOT = 0x2e;
    private static final int LINE = 0x2d;
    private static final int SPACE = 0x20;
    private final ArrayList<Integer> signals;
    private HashMap<Byte, String> hashMap;

    public Morze(SoundMorze soundMorze) {

        signals = new ArrayList<Integer>();
        generateHashMap();
    }

    private void generateHashMap() {
        hashMap = new HashMap<>();
        hashMap.put(Alphabet.A_CODE, Alphabet.A);
        hashMap.put(Alphabet.B_CODE, Alphabet.B);
        hashMap.put(Alphabet.C_CODE, Alphabet.C);
        hashMap.put(Alphabet.D_CODE, Alphabet.D);
        hashMap.put(Alphabet.E_CODE, Alphabet.E);
        hashMap.put(Alphabet.F_CODE, Alphabet.F);
        hashMap.put(Alphabet.G_CODE, Alphabet.G);
        hashMap.put(Alphabet.H_CODE, Alphabet.H);
        hashMap.put(Alphabet.I_CODE, Alphabet.I);
        hashMap.put(Alphabet.J_CODE, Alphabet.J);
        hashMap.put(Alphabet.K_CODE, Alphabet.K);
        hashMap.put(Alphabet.L_CODE, Alphabet.L);
        hashMap.put(Alphabet.M_CODE, Alphabet.M);
        hashMap.put(Alphabet.N_CODE, Alphabet.N);
        hashMap.put(Alphabet.O_CODE, Alphabet.O);
        hashMap.put(Alphabet.P_CODE, Alphabet.P);
        hashMap.put(Alphabet.Q_CODE, Alphabet.Q);
        hashMap.put(Alphabet.R_CODE, Alphabet.R);
        hashMap.put(Alphabet.S_CODE, Alphabet.S);
        hashMap.put(Alphabet.T_CODE, Alphabet.T);
        hashMap.put(Alphabet.U_CODE, Alphabet.U);
        hashMap.put(Alphabet.V_CODE, Alphabet.V);
        hashMap.put(Alphabet.W_CODE, Alphabet.W);
        hashMap.put(Alphabet.X_CODE, Alphabet.X);
        hashMap.put(Alphabet.Y_CODE, Alphabet.Y);
        hashMap.put(Alphabet.Z_CODE, Alphabet.Z);

    }

    public void parseString(String stringMorze) {
        //parseToMorze(stringMorze);
        signals.clear();
        for (byte b : stringMorze.getBytes()) {
            if (b == DOT) signals.add(Settings.LENGTH_DOT);
            if (b == LINE) signals.add(Settings.LENGTH_LINE);
            if (b == SPACE) {
                signals.add(1);
                signals.add(1);
                signals.add(1);
            }
            if (b == Alphabet.SPACE_WORD) {
                signals.add(1);
                signals.add(1);
                signals.add(1);
                signals.add(1);
                signals.add(1);
                signals.add(1);
                signals.add(1);
            }
        }
    }

    public String parseToMorze(String stringMorze) {
        stringMorze = stringMorze.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (byte b : stringMorze.getBytes()) {
            if (b == SPACE) sb.append(Alphabet.SPACE_WORD);
            sb.append(hashMap.get(b));
            sb.append(Alphabet.SPACE);
        }
        return sb.toString();
    }

    public void run(CameraWork cameraWork) {
        if (cameraWork.isFlashOn()) {
            cameraWork.turnOffFlash();
        }
        Thread thread = new Thread(() -> {

            for (Integer signal : signals) {

                if (signal != 1) {
                    cameraWork.turnOnFlash();

                    try {
                        Thread.sleep(signal);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cameraWork.turnOffFlash();
                }

                try {
                    // if(cameraWork.isFlashOn())
                    Thread.sleep(Settings.LENGTH_DOT);
                    //else Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cameraWork.turnOffFlash();

        });
        thread.start();
    }

    public void run(SoundMorze soundMorze) {
        Thread thread = new Thread(() -> {

            for (Integer signal : signals) {
                if (signal != 1) {
                    soundMorze.playDot();
                    //  if (signal == Settings.LENGTH_LINE) soundMorze.playLine();
                }



                try {
                    Thread.sleep(signal);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                soundMorze.stopDot();
                try {
                    Thread.sleep(Settings.LENGTH_DOT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }

    public void run(FlashingActivity flashingActivity,DrawFlashView view) {
        Thread thread = new Thread(() -> {

            for (Integer signal : signals) {

                if (signal != 1) {
                    flashingActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.drawFlash();
                        }
                    });

                    //  if (signal == Settings.LENGTH_LINE) soundMorze.playLine();
                }



                try {
                    Thread.sleep(signal);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flashingActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.drawBlack();
                    }
                });

                try {
                    Thread.sleep(Settings.LENGTH_DOT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            flashingActivity.finish();

        });
        thread.start();
    }
}
