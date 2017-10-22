package com.bilalekremharmansa.countdown.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bilalekremharmansa.countdown.game.MementoCaretaker;
import com.bilalekremharmansa.countdown.game.MementoNumberGame;
import com.bilalekremharmansa.countdown.game.NGExpression;
import com.bilalekremharmansa.countdown.game.NumberGame;
import com.bilalekremharmansa.countdown.game.Wrapper;
import com.bilalekremharmansa.countdown.webapi.APINumberGame;

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

        getSupportActionBar().setHomeButtonEnabled(true);


        if (savedInstanceState != null) {
            numberGame = savedInstanceState.getParcelable(EXTRA_NUMBERS_GAME);
            // expressionAdapter.notifyDataSetChanged();
        } else if (getIntent().hasExtra(EXTRA_NUMBERS_LIST)) {
            List<Integer> numbersList = getIntent().getIntegerArrayListExtra(EXTRA_NUMBERS_LIST);
            numberGame = new NumberGame(numbersList);
            Toast.makeText(getApplicationContext(), "Offline Mod", Toast.LENGTH_LONG).show();
        } else if (getIntent().hasExtra(EXTRA_NUMBERS_GAME)) {
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

        initiliazeLinearLayoutNumbers(R.id.linearLayoutNumbers, numberGame.getWrapperList());


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


    private void initiliazeLinearLayoutNumbers(int layoutID, List<Wrapper> wrapperList) {
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
        //hesaplamalar yapılcak burada.

        if (score == 0) {
            Toast.makeText(getApplicationContext(), "Cevab bulunamadı, 0 puan", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cevabı buldunuz, " + score + " puan", Toast.LENGTH_LONG).show();
        }

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
}
