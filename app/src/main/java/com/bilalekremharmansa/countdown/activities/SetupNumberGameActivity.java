package com.bilalekremharmansa.countdown.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bilalekremharmansa.countdown.R;
import com.bilalekremharmansa.countdown.webapi.APIController;
import com.bilalekremharmansa.countdown.webapi.APINumberGame;

public class SetupNumberGameActivity extends AppCompatActivity implements SetupNumberGameFragment.SetupNumberGameListener, APIController.ControllerListener {

    public static final String EXTRA_PLAYER = "player";
    public static final String EXTRA_GAME_MODE = "gameMode";

    private int numberOfLargeNumbers = 0;

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
            this.numberOfLargeNumbers = numberOfLarge;
            if (isOnline()) {
                APIController controller = new APIController();
                controller.setListener(this);
                //TODO lastGameID here
                controller.get(numberOfLarge, 1);
            } else {
                offline();
            }
        }
    }

    @Override
    public void online(APINumberGame numberGame) {
        Intent intent = new Intent(this, NumberGameActivity.class);
        intent.putExtra(NumberGameActivity.EXTRA_NUMBERS_GAME, numberGame);
        startActivity(intent);
    }

    @Override
    public void offline() {
        Intent intent = new Intent(this, NumberGameCardListActivity.class);
        intent.putExtra(NumberGameCardListActivity.EXTRA_LARGE_NUMBERS, this.numberOfLargeNumbers);
        startActivity(intent);
    }

    //ICMP
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
