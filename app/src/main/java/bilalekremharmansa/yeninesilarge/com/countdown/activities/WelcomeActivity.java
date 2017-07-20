package bilalekremharmansa.yeninesilarge.com.countdown.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bilalekremharmansa.yeninesilarge.com.countdown.Player;
import bilalekremharmansa.yeninesilarge.com.countdown.R;


public class WelcomeActivity extends AppCompatActivity{

    Logger log = LoggerFactory.getLogger(WelcomeActivity.class);


    private boolean gameMode = false; // if variable set true word game, if its false number game
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        player = new Player();

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMode = findViewById(R.id.togBtnGameMode).isSelected();

                EditText editTxtUsername = (EditText)findViewById(R.id.editTxtUsername);
                player.setName(editTxtUsername.getText().toString());

                Intent intent = new Intent(WelcomeActivity.this,SetupNumberGameActivity.class);
                intent.putExtra(SetupNumberGameActivity.EXTRA_PLAYER, player);
                intent.putExtra(SetupNumberGameActivity.EXTRA_GAME_MODE, gameMode);
                log.info("userLogin", "Kullanıcı giriş yaptı : " + player.toString());
                startActivity(intent);
            }
        });

    }


}
