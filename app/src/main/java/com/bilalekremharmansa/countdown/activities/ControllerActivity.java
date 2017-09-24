package com.bilalekremharmansa.countdown.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


import com.bilalekremharmansa.countdown.R;
import com.bilalekremharmansa.countdown.webapi.APIController;
import com.bilalekremharmansa.countdown.webapi.APINumberGame;

public class ControllerActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        findViewById(R.id.btnGetGame).setOnClickListener(this);
        findViewById(R.id.btnGetGames).setOnClickListener(this);
        findViewById(R.id.btnInsert).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        APIController controller = new APIController();


    }
}
