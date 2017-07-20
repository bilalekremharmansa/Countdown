package bilalekremharmansa.yeninesilarge.com.countdown.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

import bilalekremharmansa.yeninesilarge.com.countdown.R;

public class SetupNumberGameActivity extends AppCompatActivity implements SetupNumberGameFragment.SetupNumberGameListener {

    public static final String EXTRA_PLAYER = "player";
    public static final String EXTRA_GAME_MODE = "gameMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_number_game);


    }


    @Override
    public void getCards(int numberOfLarge) {
        // SetupNumberGameFragment frag = (SetupNumberGameFragment) getSupportFragmentManager().findFragmentById(R.id.frag_setup);
        if (findViewById(R.id.frag_container_card_list) != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frag_setup, new NumberGameCardListFragment().setNumberOfLargeNums(numberOfLarge));

            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        } else {
            Intent intent = new Intent(this, NumberGameCardListActivity.class);
            intent.putExtra(NumberGameCardListActivity.EXTRA_LARGE_NUMBERS, numberOfLarge);
            startActivity(intent);
        }
    }
}
