package com.example.dicegame;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private int gameScore = 0;
    private int rollScore = 0;
    private int countRoll = 0;
    private int[] arr;
    private int[] textViewIDs;
    private List<TextView> textViews = new ArrayList<>();
    private Button button;
    private Button reset;
    private TextView countRollTextView;
    private TextView gameScoreTextView;
    private TextView rollScoreTextView;
    private int min = 1;
    private int max = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        reset = findViewById(R.id.reset);
        countRollTextView = findViewById(R.id.count);
        gameScoreTextView = findViewById(R.id.gameScore);
        rollScoreTextView = findViewById(R.id.rollScore);

        arr = new int[5];

        textViewIDs = new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5};
        for (int resID : textViewIDs) {
            TextView textView = findViewById(resID);
            textViews.add(textView);
        }

        button.setOnClickListener(v -> {
            rollDice();
        });
        reset.setOnClickListener(v -> {
                resetGame();
        });
    }
    private void rollDice(){
        rollScore = 0;
        for (int i = 0; i < textViews.size(); i++) {
            arr[i] = (int)(Math.random() * (max - min + 1)) + min;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }

        for (int key : map.keySet()) {
            int count = map.get(key);
            if (count > 1) {
                rollScore += key * count;
            }
        }
        updateTextView(arr);
        updateRollScore(rollScore);
        updateGameScore(rollScore);
        updateRollCount();
    }

    private void updateTextView(int[] diceResult) {
        for (int i = 0; i < diceResult.length; i++) {
            TextView textView = textViews.get(i);
            textView.setText(String.valueOf(diceResult[i]));
        }
    }

    private void updateRollScore(int newScore){
        rollScoreTextView.setText("Wynik tego losowania: "+newScore);
    }

    private void updateGameScore(int newScore){
        gameScore += newScore;
        gameScoreTextView.setText("Wynik gry: "+gameScore);
    }

    private void updateRollCount(){
        countRoll++;
        countRollTextView.setText("Liczba rzutow: "+countRoll);
    }

    private void resetGame(){
        rollScore = 0;
        gameScore = 0;
        countRoll = -1;
        updateRollScore(rollScore);
        updateGameScore(gameScore);
        updateRollCount();
        for (int i = 0; i < textViews.size(); i++) {
            TextView textView = textViews.get(i);
            textView.setText("?");
        }
    }
}
