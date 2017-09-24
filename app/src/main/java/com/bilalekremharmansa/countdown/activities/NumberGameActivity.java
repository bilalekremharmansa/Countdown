package com.bilalekremharmansa.countdown.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.bilalekremharmansa.countdown.customcomponents.CustomViewExpression;
import com.bilalekremharmansa.countdown.customcomponents.NumberGameButton;
import com.bilalekremharmansa.countdown.Player;
import com.bilalekremharmansa.countdown.R;
import com.bilalekremharmansa.countdown.game.MementoCaretaker;
import com.bilalekremharmansa.countdown.game.MementoNumberGame;
import com.bilalekremharmansa.countdown.game.NumberGame;
import com.bilalekremharmansa.countdown.game.NumberGameExpression;
import com.bilalekremharmansa.countdown.game.Wrapper;
import com.bilalekremharmansa.countdown.webapi.APINumberGame;

public class NumberGameActivity extends AppCompatActivity implements CustomViewExpression.ButtonStateChangedListener, NumberGame.GameOverListener, Wrapper.WrapperStateListener {
    public static final String EXTRA_NUMBERS_LIST = "numbersList";
    public static final String EXTRA_NUMBERS_GAME = "numbersGame";

    private Player player;

    private NumberGame numberGame;

    private LinearLayout linearLayoutExpressions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game);

        if (savedInstanceState != null) {
            numberGame = savedInstanceState.getParcelable(EXTRA_NUMBERS_GAME);
            initiliazeExpressions(R.id.linearLayoutExpressions, numberGame.getExpressionList());
        } else if (getIntent().hasExtra(EXTRA_NUMBERS_LIST)) {
            List<Integer> numbersList = getIntent().getIntegerArrayListExtra(EXTRA_NUMBERS_LIST);
            numberGame = new NumberGame(numbersList);
            Toast.makeText(getApplicationContext(), "Offline Mod", Toast.LENGTH_LONG).show();
        } else if (getIntent().hasExtra(EXTRA_NUMBERS_GAME)) {
            APINumberGame numbersGame = getIntent().getParcelableExtra(EXTRA_NUMBERS_GAME);
            numberGame = new NumberGame(numbersGame);
            Toast.makeText(getApplicationContext(), "Online Mod", Toast.LENGTH_LONG).show();
        }

        TextView txtTargetNumber = (TextView) findViewById(R.id.txtTargetNumber);
        txtTargetNumber.setText(String.valueOf(numberGame.getTarget()));

        initiliazeLinearLayoutNumbers(R.id.linearLayoutNumbers, numberGame.getWrapperList());

        linearLayoutExpressions = (LinearLayout) findViewById(R.id.linearLayoutExpressions);

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
        linearLayoutExpressions.removeAllViews();
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

    private void initiliazeExpressions(int layoutID, List<NumberGameExpression> expressionList) {
        final LinearLayout linearLayoutExpressions = (LinearLayout) findViewById(layoutID);
        for (NumberGameExpression expression : expressionList) {
            linearLayoutExpressions.addView(expression.getCustomViewExpression());
        }
    }


    private void initiliazeLinearLayoutNumbers(int layoutID, List<Wrapper> wrapperList) {
        //numbersListe göre linearLayoutNumbers ı oluşturuyor.
        final LinearLayout linearLayoutNumbers = (LinearLayout) findViewById(layoutID);

        linearLayoutNumbers.removeAllViewsInLayout();

        for (Wrapper wrapper : wrapperList) {
            NumberGameButton btn = new NumberGameButton(this);
            btn.setId(wrapper.getIdOfButton());
            btn.setText(String.valueOf(wrapper.getValue()));
            btn.setOnClickListener(numbersListener);

            setStatusOfNumberButton(btn, wrapper.getState());

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
            List<NumberGameExpression> expressionList = numberGame.getExpressionList();
            NumberGameExpression expression = null;
            //expressionList boş ise veya son expression bitmiş ise yeni bi tane oluşturuluyor,
            // halihazırda var ise update ediliyor.

            if (expressionList.isEmpty() || (expression = expressionList.get(expressionList.size() - 1)).isDone()) {
                expression = createAnExpression(b.getId());
            } else if (expression.getOperator() != NumberGameExpression.DEFAULT_CHAR_VALUE) {

            }

            expression.updateUI(numberGame, b.getId(), Integer.valueOf(b.getText().toString()));
        }
    };

    public NumberGameExpression createAnExpression(int firstWrapperButtonID) {
        //yeni bir expression oluşturuluyor
        List<NumberGameExpression> expressionList = numberGame.getExpressionList();
        if (!expressionList.isEmpty()) {
            //mementoya kaydediyoruz.
            MementoCaretaker.getInstance().saveMemento(MementoNumberGame.MementoMode.EXP_FIRST, numberGame);
        }

        NumberGameExpression expression = new NumberGameExpression(this, firstWrapperButtonID);
        expressionList.add(expression);

        linearLayoutExpressions.addView(expression.getCustomViewExpression());
        return expression;
    }

    @Override
    public void onResultOfExpressionClickedListener(int customViewExpressionID, int resultValue) {
        //expressiondaki button tıklanınca üstteki liste çıkması.
        MementoCaretaker.getInstance().saveMemento(MementoNumberGame.MementoMode.RESULT_BUTTON, numberGame);

        LinearLayout linearLayoutNumbers = (LinearLayout) findViewById(R.id.linearLayoutNumbers);

        List<Wrapper> wrapperList = numberGame.getWrapperList();

        Wrapper resultWrapper = new Wrapper(resultValue);

        NumberGameButton btn = new NumberGameButton(this);
        btn.setId(resultWrapper.getIdOfButton());
        btn.setCalculatedButton(true);
        btn.setText(String.valueOf(resultValue));
        btn.setOnClickListener(numbersListener);

        wrapperList.add(resultWrapper);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        linearLayoutNumbers.addView(btn, params);


        for (NumberGameExpression expression : numberGame.getExpressionList()) {
            if (expression.getIdOfExpressionLayout() == customViewExpressionID) {
                expression.setResultWrapperButtonID(resultWrapper.getIdOfButton());
            }
        }

    }

    public void undoMemento(MementoNumberGame memento) {
        List<NumberGameExpression> expressionList = numberGame.getExpressionList();

        NumberGameExpression expression;
        CustomViewExpression customViewExpression;

        switch (memento.getMementoMode()) {
            case CONSTANT:
            case EXP_FIRST:
                expression = expressionList.get(expressionList.size() - 1);
                int layoutID = expression.getIdOfExpressionLayout();
                customViewExpression = (CustomViewExpression) findViewById(layoutID);

                for (Wrapper wrapper : numberGame.getWrapperList()) {
                    if (wrapper.getIdOfButton() == expression.getFirstWrapperButtonID()) {
                        View btn = findViewById(wrapper.getIdOfButton());
                        setStatusOfNumberButton(btn, 1);
                        break;
                    }
                }

                numberGame.restoreNumberGame(memento);
                ((ViewManager) customViewExpression.getParent()).removeView(customViewExpression);
                break;

            case EXP_OPERATOR:
                expression = expressionList.get(expressionList.size() - 1);
                expression.updateUI(numberGame, String.valueOf(NumberGameExpression.DEFAULT_CHAR_VALUE));
                // setStatusOfNumberButton(customViewExpression.getTxtOperator(),(byte)3);

                numberGame.restoreNumberGame(memento);
                break;

            case EXP_SECOND:
                expression = expressionList.get(expressionList.size() - 1);
                int layID = expression.getIdOfExpressionLayout();
                customViewExpression = (CustomViewExpression) findViewById(layID);

                int secondWrapperButtonID = expression.getSecondWrapperButtonID();
                expression.setSecondWrapperButtonID(-1);
                customViewExpression.getTxtSecondNumber().setText("");
                customViewExpression.getBtnResult().setText("");

                setStatusOfNumberButton(customViewExpression.getTxtSecondNumber(), 3);
                setStatusOfNumberButton(customViewExpression.getTxtEqual(), 3);
                setStatusOfNumberButton(customViewExpression.getBtnResult(), 3);

                for (Wrapper wrapper : numberGame.getWrapperList()) {

                    if (wrapper.getIdOfButton() == secondWrapperButtonID) {
                        View btn = findViewById(wrapper.getIdOfButton());
                        setStatusOfNumberButton(btn, 1);
                        break;
                    }
                }

                numberGame.restoreNumberGame(memento);
                break;
            case RESULT_BUTTON:

                List<Wrapper> wrapperList = numberGame.getWrapperList();

                int resultButtonID = -1;

                Wrapper wrapper = wrapperList.get(wrapperList.size() - 1);
                NumberGameButton btn = (NumberGameButton) findViewById(wrapper.getIdOfButton());
                if (btn.isCalculatedButton()) {
                    ((ViewManager) btn.getParent()).removeView(btn);
                    resultButtonID = wrapper.getIdOfButton();
                }


                for (NumberGameExpression exp : numberGame.getExpressionList()) {
                    if (resultButtonID == exp.getResultWrapperButtonID()) {
                        setStatusOfNumberButton(exp.getCustomViewExpression().getBtnResult(), 1);
                    }
                }

                numberGame.restoreNumberGame(memento);
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
        List<NumberGameExpression> expressionList = numberGame.getExpressionList();
        if (expressionList != null && !expressionList.isEmpty()) {
            NumberGameExpression expression = expressionList.get(expressionList.size() - 1);
            expression.updateUI(numberGame, b.getText().toString());
        }
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
