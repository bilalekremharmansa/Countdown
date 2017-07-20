package bilalekremharmansa.yeninesilarge.com.countdown.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bilalekremharmansa.yeninesilarge.com.countdown.Player;
import bilalekremharmansa.yeninesilarge.com.countdown.R;

public class NumberGameActivity extends AppCompatActivity {

    private boolean gameMode;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game);


    }
}
