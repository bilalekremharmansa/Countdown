package com.bilalekremharmansa.countdown.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bilalekremharmansa.countdown.Player;
import com.bilalekremharmansa.countdown.R;
import com.bilalekremharmansa.countdown.customcomponents.NGExpressionAdapter;
import com.bilalekremharmansa.countdown.customcomponents.NumberGameButton;
import com.bilalekremharmansa.countdown.db.CountdownDBHelper;
import com.bilalekremharmansa.countdown.db.NGDatabaseContract;
import com.bilalekremharmansa.countdown.game.MementoCaretaker;
import com.bilalekremharmansa.countdown.game.MementoNumberGame;
import com.bilalekremharmansa.countdown.game.NGExpression;
import com.bilalekremharmansa.countdown.game.NumberGame;
import com.bilalekremharmansa.countdown.game.Wrapper;

import java.util.List;

public class NumberGameActivity extends AppCompatActivity implements NumberGame.GameOverListener, Wrapper.WrapperStateListener {
    public static final String EXTRA_NUMBERS_LIST = "numbersList";
    public static final String EXTRA_NUMBERS_GAME = "numbersGame";

    private Player player;

    private NumberGame numberGame;

    private LinearLayout linearLayoutNumbers;
    private RecyclerView recyclerView;
    private NGExpressionAdapter expressionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            numberGame = savedInstanceState.getParcelable(EXTRA_NUMBERS_GAME);
            // expressionAdapter.notifyDataSetChanged();
        } else if (getIntent().hasExtra(EXTRA_NUMBERS_LIST)) {
            List<Integer> numbersList = getIntent().getIntegerArrayListExtra(EXTRA_NUMBERS_LIST);
            numberGame = new NumberGame(numbersList);
            Toast.makeText(getApplicationContext(), "Offline Mod", Toast.LENGTH_LONG).show();
        } else if (getIntent().hasExtra(EXTRA_NUMBERS_GAME)) {
            //online mod
            NumberGame numbersGame = getIntent().getParcelableExtra(EXTRA_NUMBERS_GAME);
            numberGame = numbersGame;
            Toast.makeText(getApplicationContext(), "Online Mod", Toast.LENGTH_LONG).show();
        }

        linearLayoutNumbers = (LinearLayout) findViewById(R.id.linearLayoutNumbers);

        expressionAdapter = new NGExpressionAdapter(numberGame.getExpressionList());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_expression);
        recyclerView.setAdapter(expressionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expressionAdapter.setResultButtonListener(new NGExpressionAdapter.ResultButtonListener() {
            @Override
            public void onResultButtonClickListener(final int position) {
                MementoCaretaker.getInstance().saveMemento(MementoNumberGame.MementoMode.RESULT_BUTTON, numberGame);

                NGExpression expression = numberGame.getExpressionList().get(position);
                expression.setVisibility(NGExpression.Visibility.FIRST_OPERATOR_SECOND_RESULT_PASSIVE);

                Wrapper resultWrapper = expression.getResultWrapper();

                LinearLayout linearLayoutNumbers = (LinearLayout) findViewById(R.id.linearLayoutNumbers);

                NumberGameButton btn = new NumberGameButton(NumberGameActivity.this);
                btn.setWrapper(resultWrapper);
                btn.setCalculatedButton(true);
                btn.setOnClickListener(numbersListener);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                linearLayoutNumbers.addView(btn, params);

                expressionAdapter.notifyItemChanged(position);
            }
        });


        TextView txtTargetNumber = (TextView) findViewById(R.id.txtTargetNumber);
        txtTargetNumber.setText(String.valueOf(numberGame.getTarget()));

        initiliazeLinearLayoutNumbers(numberGame.getWrapperList());


        numberGame.setGameOverListener(this);
        Wrapper.setListener(this);

        if (savedInstanceState == null) {
            MementoCaretaker.getInstance().clearStack();
            MementoCaretaker.getInstance().saveMemento(MementoNumberGame.MementoMode.CONSTANT, numberGame);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(EXTRA_NUMBERS_GAME, numberGame);

    }

    @Override
    public void onBackPressed() {
        onClickUndo();
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


    private void initiliazeLinearLayoutNumbers(List<Wrapper> wrapperList) {
        //numbersListe göre linearLayoutNumbers ı oluşturuyor.
        linearLayoutNumbers.removeAllViewsInLayout();

        for (Wrapper wrapper : wrapperList) {
            NumberGameButton btn = new NumberGameButton(this);
            btn.setWrapper(wrapper);
            btn.setOnClickListener(numbersListener);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            linearLayoutNumbers.addView(btn, params);
        }

        linearLayoutNumbers.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                linearLayoutNumbers.getViewTreeObserver().removeOnPreDrawListener(this);
                linearLayoutNumbers.setMinimumHeight(linearLayoutNumbers.getHeight());
                return false;
            }
        });


    }

    @Override
    public void onGameOver(int score) {
        Intent intent = new Intent(this, NGResultScreenActivity.class);
        intent.putExtra(NGResultScreenActivity.EXTRA_SCORE, score);
        new GameTask().execute(numberGame);
        startActivity(intent);
    }

    public void onClickGameOver(View v) {
        numberGame.onGameOver();
    }

    private View.OnClickListener numbersListener = new View.OnClickListener() {
        //linearLayoutNumbers taki butonların listenerı
        @Override
        public void onClick(View v) {
            NumberGameButton b = (NumberGameButton) v;

            boolean isCreated = numberGame.createExpresssion(expressionAdapter, b.getWrapper());

            if (!isCreated) {
                numberGame.updateExpression(expressionAdapter, b.getWrapper());
            }
        }
    };


    public void onResultButtonClickListener(Wrapper resultWrapper) {
        MementoCaretaker.getInstance().saveMemento(MementoNumberGame.MementoMode.RESULT_BUTTON, numberGame);

        LinearLayout linearLayoutNumbers = (LinearLayout) findViewById(R.id.linearLayoutNumbers);

        NumberGameButton btn = new NumberGameButton(this);
        btn.setWrapper(resultWrapper);
        btn.setCalculatedButton(true);
        btn.setOnClickListener(numbersListener);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        linearLayoutNumbers.addView(btn, params);
    }

    public void undoMemento(MementoNumberGame memento) {

        NGExpression expression;

        switch (memento.getMementoMode()) {
            case CONSTANT:
            case EXP_FIRST:
                expression = numberGame.getExpressionList().get(numberGame.getExpressionList().size() - 1);

                expression.getFirstWrapper().setState(1);
                // numberGame.deleteExpression(recyclerView,expressionAdapter);
                numberGame.restoreNumberGame(expressionAdapter, memento);
                break;

            case EXP_OPERATOR:

                numberGame.restoreNumberGame(expressionAdapter, memento);
                //  numberGame.updateExpression(expressionAdapter,NGExpression.DEFAULT_CHAR_VALUE);
                // numberGame.updateExpression(expressionAdapter,NGExpression.Visibility.FIRST);
                break;

            case EXP_SECOND:

                expression = numberGame.getExpressionList().get(numberGame.getExpressionList().size() - 1);

                expression.getSecondWrapper().setState(1);


                numberGame.restoreNumberGame(expressionAdapter, memento);
                break;
            case RESULT_BUTTON:

                final int linearLayoutNumbersChildCount = linearLayoutNumbers.getChildCount();
                for (int i = 0; i < linearLayoutNumbersChildCount; i++) {
                    NumberGameButton btn = (NumberGameButton) linearLayoutNumbers.getChildAt(i);

                    if (btn.isCalculatedButton()) {
                        linearLayoutNumbers.removeViewAt(i);
                        break;
                    }
                }

                numberGame.restoreNumberGame(expressionAdapter, memento);
                break;

        }


    }

    void setStatusOfNumberButton(View v, int state) {
        switch (state) {
            case 1:
                v.setEnabled(true);
                v.setVisibility(View.VISIBLE);
                break;
            case 2:
                v.setEnabled(false);
                v.setVisibility(View.VISIBLE);
                break;
            case 3:
                v.setVisibility(View.INVISIBLE);
                break;
            case 4:
                v.setVisibility(View.GONE);
                break;
        }

    }

    public void onClickOperator(View v) {
        Button b = (Button) v;
        numberGame.updateExpression(expressionAdapter, b.getText().toString().charAt(0));
    }

    public void onClickUndo() {
        if (!numberGame.getExpressionList().isEmpty()) {

            undoMemento(MementoCaretaker.getInstance().restoreMemento());

        } else {
            super.onBackPressed();
        }

    }


    @Override
    public void onExpressionUpdate(int buttonID, int state) {
        View v = findViewById(buttonID);
        setStatusOfNumberButton(v, state);
    }


    private class GameTask extends AsyncTask<NumberGame, Void, Boolean> {

        @Override
        protected Boolean doInBackground(NumberGame... numberGames) {
            NumberGame numberGame = numberGames[0];

            int[] numbers = new int[6];
            int numberOfLargeNumbers = 0;
            for (int i = 0; i < numbers.length; i++) {
                Wrapper w = numberGame.getWrapperList().get(i);

                numbers[i] = w.getValue();
                if (w.getValue() >= 25) numberOfLargeNumbers++;
            }

            List<NGExpression> expressionList = numberGame.getExpressionList();

            ContentValues valuesGame = new ContentValues();
            valuesGame.put(NGDatabaseContract.GameEntry.COLUMN_NUMBER_OF_LARGE_NUMS,
                    numberOfLargeNumbers);
            valuesGame.put(NGDatabaseContract.GameEntry.COLUMN_NUMBER_ONE, numbers[0]);
            valuesGame.put(NGDatabaseContract.GameEntry.COLUMN_NUMBER_TWO, numbers[1]);
            valuesGame.put(NGDatabaseContract.GameEntry.COLUMN_NUMBER_THREE, numbers[2]);
            valuesGame.put(NGDatabaseContract.GameEntry.COLUMN_NUMBER_FOUR, numbers[3]);
            valuesGame.put(NGDatabaseContract.GameEntry.COLUMN_NUMBER_FIVE, numbers[4]);
            valuesGame.put(NGDatabaseContract.GameEntry.COLUMN_NUMBER_SIX, numbers[5]);
            valuesGame.put(NGDatabaseContract.GameEntry.COLUMN_TARGET, numberGame.getTarget());
            valuesGame.put(NGDatabaseContract.GameEntry.COLUMN_PLAYED_DATETIME,
                    System.currentTimeMillis());
            valuesGame.put(NGDatabaseContract.GameEntry.COLUMN_IS_ACTIVE, 1);

            SQLiteOpenHelper helper = new CountdownDBHelper(getApplicationContext());
            SQLiteDatabase db = helper.getWritableDatabase();


            try {
                db.beginTransaction();

                db.insert(
                        NGDatabaseContract.GameEntry.TABLE_NAME,
                        null,
                        valuesGame);

                //select seq from sqlite_sequence where name="table_name"
                Cursor cursor = db.query(
                        "SQLITE_SEQUENCE",
                        new String[]{"seq"},
                        "name = ? ",
                        new String[]{NGDatabaseContract.GameEntry.TABLE_NAME},
                        null,
                        null,
                        null
                );

                int gameID = -1;
                if (cursor.moveToFirst()) {
                    gameID = cursor.getInt(0);

                }

                String[] columnsSolution = {
                        NGDatabaseContract.SolutionEntry.COLUMN_STEP_ONE,
                        NGDatabaseContract.SolutionEntry.COLUMN_STEP_TWO,
                        NGDatabaseContract.SolutionEntry.COLUMN_STEP_THREE,
                        NGDatabaseContract.SolutionEntry.COLUMN_STEP_FOUR,
                        NGDatabaseContract.SolutionEntry.COLUMN_STEP_FIVE,
                };

                if (gameID != -1) {
                    ContentValues valuesSolution = new ContentValues();
                    for (int i = 0; i < expressionList.size(); i++) {
                        valuesSolution.put(columnsSolution[i], expressionList.get(0).toString());
                    }

                    db.insert(
                            NGDatabaseContract.SolutionEntry.TABLE_NAME,
                            "",
                            valuesSolution);
                }


                db.setTransactionSuccessful();
                db.endTransaction();

                cursor.close();
            } catch (Exception ex) {
                Log.e("Database accces", "error", ex);
                return false;
            }

            db.close();
            helper.close();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean) {
                Toast.makeText(getApplicationContext(), "Yay, succesfully inserted into database.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Somethhing went wrong while accessing database", Toast.LENGTH_LONG).show();
            }
            Log.d("AsyncDatabase", "Result is " + aBoolean);
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            //todo
            super.onCancelled(aBoolean);
        }

        /* String[] columnsGame={
                    NGDatabaseContract.GameEntry.COLUMN_NUMBER_OF_LARGE_NUMS,
                    NGDatabaseContract.GameEntry.COLUMN_NUMBER_ONE,
                    NGDatabaseContract.GameEntry.COLUMN_NUMBER_TWO,
                    NGDatabaseContract.GameEntry.COLUMN_NUMBER_THREE,
                    NGDatabaseContract.GameEntry.COLUMN_NUMBER_FOUR,
                    NGDatabaseContract.GameEntry.COLUMN_NUMBER_FIVE,
                    NGDatabaseContract.GameEntry.COLUMN_NUMBER_SIX,
                    NGDatabaseContract.GameEntry.COLUMN_TARGET,
                    NGDatabaseContract.GameEntry.COLUMN_PLAYED_DATETIME
            };*/
    }
}
