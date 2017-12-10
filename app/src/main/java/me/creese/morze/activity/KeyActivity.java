package me.creese.morze.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.creese.morze.R;
import me.creese.morze.constants.Settings;
import me.creese.morze.morze.Morze;
import me.creese.morze.morze.SoundMorze;
import me.creese.morze.util.ETimer;
import me.creese.morze.views.SymbolWait;

/**
 * Created by yoba2 on 08.12.2017.
 */

public class KeyActivity extends Activity implements View.OnTouchListener {
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


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_layout);
        Button keyButton = findViewById(R.id.keyButton);
        //keyButton.setOnClickListener(this);
        keyButton.setOnTouchListener(this);
        deltaArray = new ArrayList<>();
        soundMorze = new SoundMorze(this);
        textField = findViewById(R.id.textToMorze);
        stringMorze = new StringBuilder();
        symbolWait = findViewById(R.id.symbol_wait);
        symbolWait.setVisibility(View.INVISIBLE);


    }


    @Override
    protected void onStart() {
        super.onStart();
        morze = (Morze) getIntent().getSerializableExtra(Morze.EXTRA);
        reverse(morze.getHashMap());
        timer = new ETimer();
        timer.setDelay(Settings.LENGTH_DOT*10);
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
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            soundMorze.playDot();
            timeDown = System.currentTimeMillis();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            soundMorze.stopDot();
            //System.out.println(System.currentTimeMillis() - timeDown);
            if (System.currentTimeMillis() - timeDown <= Settings.LENGTH_DOT) {
                stringMorze.append(".");
            } else {
                stringMorze.append("-");
            }
            System.out.println(reverseMap.get(stringMorze.toString()));
            timer.startTimer();
            symbolWait.setVisibility(View.VISIBLE);
            symbolWait.setSymbol(reverseMap.get(stringMorze.toString()),Settings.LENGTH_DOT*10);



        }
        return false;
    }



    public void reverse(Map<Character,String> map) {
        reverseMap = new HashMap<>();
        for(Map.Entry<Character,String> entry : map.entrySet())
            reverseMap.put(entry.getValue(), entry.getKey());
    }
}


