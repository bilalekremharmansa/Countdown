package com.bilalekremharmansa.countdown.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bilalekremharmansa.countdown.R;

public class NGResultScreenActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "GAME_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngresult_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int score = getIntent().getIntExtra(EXTRA_SCORE, -1);

        TextView textView = findViewById(R.id.txt_total_score);
        textView.setText("Puanınız " + score);//todo use string resource

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_people:
                Toast.makeText(this, "FAVORITE", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "SETTINGS", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
