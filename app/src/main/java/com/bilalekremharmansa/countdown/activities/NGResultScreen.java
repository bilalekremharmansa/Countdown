package com.bilalekremharmansa.countdown.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bilalekremharmansa.countdown.R;

public class NGResultScreen extends AppCompatActivity {

    public static final String EXTRA_SCORE = "GAME_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngresult_screen);

        int score = getIntent().getIntExtra(EXTRA_SCORE, -1);

        TextView textView = findViewById(R.id.txt_total_score);
        textView.setText("Puanınız " + score);//todo use string resource

    }
}
