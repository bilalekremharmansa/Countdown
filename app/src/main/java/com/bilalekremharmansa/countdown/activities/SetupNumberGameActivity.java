package com.bilalekremharmansa.countdown.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bilalekremharmansa.countdown.R;
import com.bilalekremharmansa.countdown.game.NumberGame;
import com.bilalekremharmansa.countdown.webapi.APIController;
import com.bilalekremharmansa.countdown.webapi.APINumberGame;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupNumberGameActivity extends AppCompatActivity implements SetupNumberGameFragment.SetupNumberGameListener {

    public static final String EXTRA_PLAYER = "player";


    private int numberOfLargeNumbers = 0;

    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_number_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new SetupNumberGameFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_setup, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        database = FirebaseDatabase.getInstance();
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

            //todo isOnline
            if (isOnline() || true) {
                final DatabaseReference ref = database.getReference();
                ref.child("number-games").child(String.valueOf(numberOfLargeNumbers)).orderByKey().limitToLast(1)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            final FirebaseNumberGame g = d.getValue(FirebaseNumberGame.class);

                            ref.child("solution-list").child(String.valueOf(numberOfLargeNumbers))
                                    .child(d.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot solutions : dataSnapshot.getChildren()){
                                                List<String> solution = (List<String>) solutions.getValue();
                                                g.solutionList.add(solution);
                                            }
                                            online(new NumberGame(g));
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            offline();
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        offline();
                    }
                });
            } else {
                offline();
            }
        }
    }

    public void online(NumberGame numberGame) {
        Intent intent = new Intent(this, NumberGameActivity.class);
        intent.putExtra(NumberGameActivity.EXTRA_NUMBERS_GAME, numberGame);
        startActivity(intent);
    }

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

    public static class FirebaseNumberGame{
        private int ID;

        private List<Integer> numbers;

        private int target;

        private List<List<String>> solutionList = new ArrayList<>();

        public FirebaseNumberGame() {

        }

        public FirebaseNumberGame(List<Integer> numbers, int target) {
            this.numbers = numbers;
            this.target = target;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public List<Integer> getNumbers() {
            return numbers;
        }

        public void setNumbers(List<Integer> numbers) {
            this.numbers = numbers;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public List<List<String>>getSolutionList() {
            return solutionList;
        }

        public void setSolutionList(List<List<String>> solutionList) {
            this.solutionList = solutionList;
        }
    }
}
