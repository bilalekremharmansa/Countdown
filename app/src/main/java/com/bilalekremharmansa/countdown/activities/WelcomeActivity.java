package com.bilalekremharmansa.countdown.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.bilalekremharmansa.countdown.Player;
import com.bilalekremharmansa.countdown.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WelcomeActivity extends AppCompatActivity {

    Logger log = LoggerFactory.getLogger(WelcomeActivity.class);


    private boolean gameMode = false; // if variable set true, the game is word game. If its false number game
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        player = new Player();

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTxtUsername = (EditText) findViewById(R.id.editTxtUsername);
                player.setName(editTxtUsername.getText().toString());

                Intent intent = new Intent(WelcomeActivity.this, SetupNumberGameActivity.class);
                intent.putExtra(SetupNumberGameActivity.EXTRA_PLAYER, player);
                log.info("userLogin", "Kullanıcı giriş yaptı : " + player.toString());
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_sign_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(WelcomeActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(WelcomeActivity.this, AuthenticationActivity.class));
                                finish();
                            }
                        });

            }
        });

    }


}
