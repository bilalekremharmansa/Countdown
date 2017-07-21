package bilalekremharmansa.yeninesilarge.com.countdown.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import bilalekremharmansa.yeninesilarge.com.countdown.R;
import bilalekremharmansa.yeninesilarge.com.countdown.game.NumberGame;

public class NumberGameCardListActivity extends AppCompatActivity implements NumberGameCardListFragment.NumbersListListener {

    public static final String EXTRA_LARGE_NUMBERS = "largeNumbers";

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
    public void numbersListReady(List<Integer> numbersList) {
        Intent intent = new Intent(this, NumberGameActivity.class);
        intent.putIntegerArrayListExtra(NumberGameActivity.EXTRA_NUMBERS_LIST, (ArrayList<Integer>) numbersList);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
