package me.creese.morze.morze;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import me.creese.morze.activity.FlashingActivity;
import me.creese.morze.constants.Alphabet;
import me.creese.morze.constants.Settings;
import me.creese.morze.exception.NoFindCharacterException;
import me.creese.morze.views.DrawFlashView;

import static me.creese.morze.constants.Alphabet.cirilic;


/**
 * Created by yoba2 on 27.11.2017.
 */

public class Morze implements Serializable {
    public static final String EXTRA = "Morze";

    private static final int DOT = 0x2e;
    private static final int LINE = 0x2d;
    private static final int SPACE = 0x20;
    private final ArrayList<Integer> signals;
    private HashMap<Character, String> hashMap;

    public Morze(SoundMorze soundMorze) {

        signals = new ArrayList<Integer>();
        generateHashMap();
        initCirilicAlphbet();
    }

    public HashMap<Character, String> getHashMap() {
        return hashMap;
    }

    private void initCirilicAlphbet() {
        cirilic.put('А',Alphabet.A);
        cirilic.put('Б',Alphabet.B);
        cirilic.put('В',Alphabet.W);
        cirilic.put('Г',Alphabet.G);
        cirilic.put('Д',Alphabet.D);
        cirilic.put('Е',Alphabet.E);
        cirilic.put('Ё',Alphabet.E);
        cirilic.put('Ж',Alphabet.V);
        cirilic.put('З',Alphabet.Z);
        cirilic.put('И',Alphabet.I);
        cirilic.put('Й',Alphabet.J);
        cirilic.put('К',Alphabet.K);
        cirilic.put('Л',Alphabet.L);
        cirilic.put('М',Alphabet.M);
        cirilic.put('Н',Alphabet.N);
        cirilic.put('О',Alphabet.O);
        cirilic.put('П',Alphabet.P);
        cirilic.put('Р',Alphabet.R);
        cirilic.put('С',Alphabet.S);
        cirilic.put('Т',Alphabet.T);
        cirilic.put('У',Alphabet.U);
        cirilic.put('Ф',Alphabet.F);
        cirilic.put('Х',Alphabet.H);
        cirilic.put('Ц',Alphabet.C);
        cirilic.put('Ч',"---.");
        cirilic.put('Ш',"----");
        cirilic.put('Щ',Alphabet.Q);
        cirilic.put('Ъ',"--.--");
        cirilic.put('Ы',Alphabet.Y);
        cirilic.put('Ь',Alphabet.X);
        cirilic.put('Э',"..-..");
        cirilic.put('Ю',"..--");
        cirilic.put('Я',".-.-");
    }
    private void generateHashMap() {
        hashMap = new HashMap<>();
        hashMap.put((char) Alphabet.A_CODE, Alphabet.A);
        hashMap.put((char) Alphabet.B_CODE, Alphabet.B);
        hashMap.put((char) Alphabet.C_CODE, Alphabet.C);
        hashMap.put((char) Alphabet.D_CODE, Alphabet.D);
        hashMap.put((char) Alphabet.E_CODE, Alphabet.E);
        hashMap.put((char) Alphabet.F_CODE, Alphabet.F);
        hashMap.put((char) Alphabet.G_CODE, Alphabet.G);
        hashMap.put((char) Alphabet.H_CODE, Alphabet.H);
        hashMap.put((char) Alphabet.I_CODE, Alphabet.I);
        hashMap.put((char) Alphabet.J_CODE, Alphabet.J);
        hashMap.put((char) Alphabet.K_CODE, Alphabet.K);
        hashMap.put((char) Alphabet.L_CODE, Alphabet.L);
        hashMap.put((char) Alphabet.M_CODE, Alphabet.M);
        hashMap.put((char) Alphabet.N_CODE, Alphabet.N);
        hashMap.put((char) Alphabet.O_CODE, Alphabet.O);
        hashMap.put((char) Alphabet.P_CODE, Alphabet.P);
        hashMap.put((char) Alphabet.Q_CODE, Alphabet.Q);
        hashMap.put((char) Alphabet.R_CODE, Alphabet.R);
        hashMap.put((char) Alphabet.S_CODE, Alphabet.S);
        hashMap.put((char) Alphabet.T_CODE, Alphabet.T);
        hashMap.put((char) Alphabet.U_CODE, Alphabet.U);
        hashMap.put((char) Alphabet.V_CODE, Alphabet.V);
        hashMap.put((char) Alphabet.W_CODE, Alphabet.W);
        hashMap.put((char) Alphabet.X_CODE, Alphabet.X);
        hashMap.put((char) Alphabet.Y_CODE, Alphabet.Y);
        hashMap.put((char) Alphabet.Z_CODE, Alphabet.Z);


        hashMap.put('1', Alphabet._1);
        hashMap.put('2', Alphabet._2);
        hashMap.put('3', Alphabet._3);
        hashMap.put('4', Alphabet._4);
        hashMap.put('5', Alphabet._5);
        hashMap.put('6', Alphabet._6);
        hashMap.put('7', Alphabet._7);
        hashMap.put('8', Alphabet._8);
        hashMap.put('9', Alphabet._9);
        hashMap.put('0', Alphabet._0);



        hashMap.put('.', Alphabet.dot);
        hashMap.put('!', Alphabet.exclamation);
        hashMap.put('?', Alphabet.question);
        hashMap.put(',', Alphabet.comma);
        hashMap.put(':', Alphabet.colon);
        hashMap.put(';', Alphabet.semicolon);
        hashMap.put(')', Alphabet.bracket);
        hashMap.put('(', Alphabet.bracket);
        hashMap.put((char) 39, Alphabet.apostrophe);
        hashMap.put('"', Alphabet.quotes);
        hashMap.put('-', Alphabet.dash);
        hashMap.put('/', Alphabet.slash);
        hashMap.put('@', Alphabet.at);



    }

    public void parseString (String stringMorze) {
        //parseT0Morze(stringMorze);
        signals.clear();
        for (byte b : stringMorze.getBytes()) {
            if (b == DOT) signals.add(Settings.LENGTH);
            if (b == LINE) signals.add(Settings.LENGTH*3);
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
        String str;
        String ch;

        for (int i = 0;i<stringMorze.length();i++) {
           // System.out.println(b);
            if (stringMorze.charAt(i) == SPACE) sb.append(Alphabet.SPACE_WORD);
            else {
                str = hashMap.get(stringMorze.charAt(i));
                if (str != null) {
                    sb.append(str);
                } else {
                    ch = Alphabet.cirilic.get(stringMorze.charAt(i));
                    if (ch != null) {
                        sb.append(ch);
                    } else throw new NoFindCharacterException();
                }
                sb.append(Alphabet.SPACE);
            }
        }
        return sb.toString();
    }

    public void run(CameraMorze cameraWork) {
        if (cameraWork.isFlashOn()) {
            cameraWork.turnOffFlash();
        }
        Thread thread = new Thread(() -> {

            for (Integer signal : signals) {
                System.out.println(signal);
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
                    Thread.sleep(Settings.LENGTH);
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
                }
                try {
                    Thread.sleep(signal);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                soundMorze.stopDot();
                try {
                    Thread.sleep(Settings.LENGTH);
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
                    Thread.sleep(Settings.LENGTH);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            flashingActivity.finish();

        });
        thread.start();
    }
}
