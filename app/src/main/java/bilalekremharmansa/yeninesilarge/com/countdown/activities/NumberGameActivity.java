package bilalekremharmansa.yeninesilarge.com.countdown.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bilalekremharmansa.yeninesilarge.com.countdown.CustomViewGroupExpression;
import bilalekremharmansa.yeninesilarge.com.countdown.NumberGameButton;
import bilalekremharmansa.yeninesilarge.com.countdown.Player;
import bilalekremharmansa.yeninesilarge.com.countdown.R;
import bilalekremharmansa.yeninesilarge.com.countdown.game.NumberGame;
import bilalekremharmansa.yeninesilarge.com.countdown.game.NumberGameExpression;
import bilalekremharmansa.yeninesilarge.com.countdown.game.NumberGameUtil;

public class NumberGameActivity extends AppCompatActivity implements CustomViewGroupExpression.ButtonStateChangedListener {
    public static final String EXTRA_NUMBERS_LIST = "numbersList";

    private Player player;

    private NumberGame numberGame;

    private LinearLayout linearLayoutExpressions;
    private List<Integer> buttonIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game);

        numberGame = new NumberGame(getIntent().getIntegerArrayListExtra(EXTRA_NUMBERS_LIST));
        buttonIDs = new ArrayList<>(numberGame.getNumbersList().size());

        TextView txtTargetNumber = (TextView) findViewById(R.id.txtTargetNumber);
        txtTargetNumber.setText(String.valueOf(numberGame.getTarget()));

        initiliazeLinearLayoutNumbers(R.id.linearLayoutNumbers, numberGame.getNumbersList(), numberGame.getNumbersState());

        linearLayoutExpressions = (LinearLayout) findViewById(R.id.linearLayoutExpressions);


    }

    private void initiliazeLinearLayoutNumbers(int layoutID, List<Integer> numbersList, byte[] numbersState) {
        LinearLayout linearLayoutNumbers = (LinearLayout) findViewById(layoutID);
        linearLayoutNumbers.removeAllViewsInLayout();

        int index = 0;
        for (int i : numbersList) {
            NumberGameButton btn = new NumberGameButton(this, index);
            int id = View.generateViewId();
            buttonIDs.add(id);
            btn.setId(id);
            btn.setText(String.valueOf(i));

            numbersState[index++] = 1;
            setStatusOfNumberButton(btn, (byte) 1);

            btn.setOnClickListener(numbersListener);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            linearLayoutNumbers.addView(btn, params);

        }
    }

    @Override
    public void onResultButtonClickListener(int resultValue) {
        LinearLayout linearLayoutNumbers = (LinearLayout) findViewById(R.id.linearLayoutNumbers);
        List<Integer> numbersList = numberGame.getNumbersList();
        byte[] numbersState = numberGame.getNumbersState();

        int sizeOfNumbersList = numbersList.size();
        numbersList.add(resultValue);

        NumberGameButton btn = new NumberGameButton(this, sizeOfNumbersList);
        int id = View.generateViewId();
        buttonIDs.add(id);
        btn.setId(id);
        btn.setText(String.valueOf(resultValue));

        numbersState[sizeOfNumbersList] = 1;
        setStatusOfNumberButton(btn, (byte) 1);

        btn.setOnClickListener(numbersListener);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        linearLayoutNumbers.addView(btn, params);



    }

    private View.OnClickListener numbersListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NumberGameButton b = (NumberGameButton) v;
            List<NumberGameExpression> expressionList = numberGame.getExpressionList();
            NumberGameExpression expression;
            if (expressionList.isEmpty()) {
                expression = new NumberGameExpression(b.getIdOfNumbersList());
                expressionList.add(expression);

                CustomViewGroupExpression expressionLayout = new CustomViewGroupExpression(NumberGameActivity.this, null, expression);
                expressionLayout.setButtonStateChangedListener(NumberGameActivity.this);

                linearLayoutExpressions.addView(expressionLayout);


            } else {
                expression = expressionList.get(expressionList.size() - 1);
                if (expression.isDone()) {
                    expression = new NumberGameExpression(b.getIdOfNumbersList());
                    expressionList.add(expression);

                    CustomViewGroupExpression expressionLayout = new CustomViewGroupExpression(NumberGameActivity.this, null, expression);
                    expressionLayout.setButtonStateChangedListener(NumberGameActivity.this);


                    linearLayoutExpressions.addView(expressionLayout);
                } else {

                    // expression.updateExpression(b.getIdOfNumbersList() , Integer.valueOf(b.getText().toString()));
                }

            }
            expression.updateExpression(b.getIdOfNumbersList(), Integer.valueOf(b.getText().toString()));
        }
    };

    @Override
    public void onValueChange(int oldIndex, int newIndex, NumberGameExpression expression) {
        byte[] numbersState = numberGame.getNumbersState();
        if (expression.isDone()) {
            numbersState[newIndex] = 3;
            //buradaki oldIndex hesaplama bittiği için ilk değeri temsil ediyor.
            oldIndex = expression.getFirstNumberIndex();
            numbersState[oldIndex] = 3;
        } else {
            numbersState[oldIndex] = 1;
            numbersState[newIndex] = 2;
        }

        updateNumbersFromState(oldIndex, newIndex);

    }


    public void updateNumbersFromState(Integer oldIndex, Integer newIndex) {
        byte[] numbersState = numberGame.getNumbersState();


        if (oldIndex != -1) {
            Button btnOld = (Button) findViewById(buttonIDs.get(oldIndex));
            setStatusOfNumberButton(btnOld, numbersState[oldIndex]);
        }
        Button btnNew = (Button) findViewById(buttonIDs.get(newIndex));
        setStatusOfNumberButton(btnNew, numbersState[newIndex]);
    }

    void setStatusOfNumberButton(Button b, byte state) {
        switch (state) {
            case 1:
                b.setEnabled(true);
                b.setVisibility(View.VISIBLE);
                break;
            case 2:
                b.setEnabled(false);
                b.setVisibility(View.VISIBLE);
                break;
            case 3:
                b.setVisibility(View.GONE);
                break;
        }

    }

    public void onClickOperator(View v) {
        Button b = (Button) v;
        List<NumberGameExpression> expressionList = numberGame.getExpressionList();
        if (expressionList != null && !expressionList.isEmpty()) {
            NumberGameExpression expression = expressionList.get(expressionList.size() - 1);
            expression.updateExpression(b.getText().toString());
        }
    }


}
