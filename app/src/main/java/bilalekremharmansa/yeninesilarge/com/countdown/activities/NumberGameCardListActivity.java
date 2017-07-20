package bilalekremharmansa.yeninesilarge.com.countdown.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bilalekremharmansa.yeninesilarge.com.countdown.R;
import bilalekremharmansa.yeninesilarge.com.countdown.game.NumberGame;

public class NumberGameCardListActivity extends AppCompatActivity implements NumberGameCardListFragment.OnCardClickedListener {

    public static final String EXTRA_LARGE_NUMBERS = "largeNumbers";
    public static final String EXTRA_NUMBER_GAME = "numberGame";

    private int numbersOfLarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game_card_list);

        NumberGameCardListFragment fragment = (NumberGameCardListFragment) getSupportFragmentManager().
                findFragmentById(R.id.frag_card_list);
        numbersOfLarge = getIntent().getIntExtra(EXTRA_LARGE_NUMBERS, -1);
        fragment.setNumberOfLargeNums(numbersOfLarge);


    }

    @Override
    public void onClick(int index) {


    }
}
