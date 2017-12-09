package com.bilalekremharmansa.countdown.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.bilalekremharmansa.countdown.R;
import com.bilalekremharmansa.countdown.game.NumberGame;

public class NumberGameCardListActivity extends AppCompatActivity implements NumberGameCardListFragment.NumbersListListener {

    public static final String EXTRA_LARGE_NUMBERS = "largeNumbers";

    private int numbersOfLarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game_card_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragmentCardList = new NumberGameCardListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_setup, fragmentCardList)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        NumberGameCardListFragment fragment = (NumberGameCardListFragment) getSupportFragmentManager().
                findFragmentById(R.id.frag_card_list);
        numbersOfLarge = getIntent().getIntExtra(EXTRA_LARGE_NUMBERS, -1);
        fragment.setNumberOfLargeNums(numbersOfLarge);

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

    @Override
    public void numbersListReady(List<Integer> numbersList) {
        Intent intent = new Intent(this, NumberGameActivity.class);
        intent.putIntegerArrayListExtra(NumberGameActivity.EXTRA_NUMBERS_LIST, (ArrayList<Integer>) numbersList);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
