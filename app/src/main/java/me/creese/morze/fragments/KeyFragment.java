package me.creese.morze.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.creese.morze.R;
import me.creese.morze.activity.MainActivity;
import me.creese.morze.constants.Settings;
import me.creese.morze.morze.Morze;
import me.creese.morze.morze.SoundMorze;
import me.creese.morze.util.ETimer;
import me.creese.morze.views.SymbolWait;

/**
 * Created by yoba2 on 08.12.2017.
 */

public class KeyFragment extends Fragment implements View.OnTouchListener {
    private SoundMorze soundMorze;
    private long timeDown;
    private ArrayList<Long> deltaArray;
    private EditText textField;
    private Long dot;
    private Long line;
    private boolean findDotAndLine;
    private StringBuilder stringMorze;
    private Morze morze;
    private ETimer timer;
    private char currentChar;
    private Object mapInversed;
    private HashMap<String, Character> reverseMap;
    private SymbolWait symbolWait;
    private Context context;
    private MainActivity startActivity;
    private Character symbol;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.key_layout, container, false);
        context = view.getContext();
        Button keyButton = view.findViewById(R.id.keyButton);
        //keyButton.setOnClickListener(this);
        keyButton.setOnTouchListener(this);
        deltaArray = new ArrayList<>();
        textField = view.findViewById(R.id.textToMorze);
        stringMorze = new StringBuilder();
        symbolWait = view.findViewById(R.id.symbol_wait);
        symbolWait.setVisibility(View.INVISIBLE);


        reverse(morze.getHashMap());
        timer = new ETimer();
        timer.setDelay(Settings.LENGTH*10);
        timer.setShedule(new Runnable() {
            @Override
            public void run() {
                startActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (symbol != null) {
                            textField.append(symbol+"");
                        }

                        symbolWait.setVisibility(View.INVISIBLE);
                        stringMorze = new StringBuilder();
                    }
                });
            }
        });

        return view;
    }

    /*
    @Override
    public void onStart() {
        super.onStart();
        morze = (Morze) getIntent().getSerializableExtra(Morze.EXTRA);
        reverse(morze.getHashMap());
        timer = new ETimer();
        timer.setDelay(Settings.LENGTH*10);
        timer.setShedule(new Runnable() {
            @Override
            public void run() {
                KeyActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Character symbol = reverseMap.get(stringMorze.toString());
                        if (symbol != null) {
                            textField.append(symbol+"");
                        }
                        symbolWait.setVisibility(View.INVISIBLE);
                        stringMorze = new StringBuilder();
                    }
                });
            }
        });
    }*/

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            soundMorze.playDot();
            timeDown = System.currentTimeMillis();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            soundMorze.stopDot();
            //System.out.println(System.currentTimeMillis() - timeDown);
            if (System.currentTimeMillis() - timeDown <= Settings.LENGTH) {
                stringMorze.append(".");
            } else {
                stringMorze.append("-");
            }
            Character tmp = reverseMap.get(stringMorze.toString());

            if (tmp != null) {
                symbol = tmp;
                timer.startTimer();
                symbolWait.setVisibility(View.VISIBLE);
                symbolWait.setSymbol(reverseMap.get(stringMorze.toString()),Settings.LENGTH*10);
            }




        }
        return false;
    }



    public void reverse(Map<Character,String> map) {
        reverseMap = new HashMap<>();
        for(Map.Entry<Character,String> entry : map.entrySet())
            reverseMap.put(entry.getValue(), entry.getKey());
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
}


