package bilalekremharmansa.yeninesilarge.com.countdown.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bilalekremharmansa.yeninesilarge.com.countdown.Player;
import bilalekremharmansa.yeninesilarge.com.countdown.R;
import bilalekremharmansa.yeninesilarge.com.countdown.game.NumberGame;

public class NumberGameActivity extends AppCompatActivity {
    public static final String EXTRA_NUMBERS_LIST = "numbersList";

    private Player player;

    private NumberGame numberGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game);

        numberGame = new NumberGame(getIntent().getIntegerArrayListExtra(EXTRA_NUMBERS_LIST));

        TextView txtTargetNumber = (TextView) findViewById(R.id.txtTargetNumber);
        txtTargetNumber.setText(String.valueOf(numberGame.getTarget()));

        initiliazeLinearLayoutNumbers(R.id.linearLayoutNumbers, numberGame.getNumbersList());

    }

    private void initiliazeLinearLayoutNumbers(int layoutID, List<Integer> numbersList) {
        LinearLayout linearLayoutNumbers = (LinearLayout) findViewById(layoutID);

        for (int i : numbersList) {
            Button btn = new Button(this);
            btn.setText(String.valueOf(i));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            linearLayoutNumbers.addView(btn, params);

        }
    }

    private void setOperatorsEnabled(boolean isEnable) {
        int[] btnIDs = new int[]{R.id.btnOperatorPlus, R.id.btnOperatorMinus, R.id.btnOperatorTimes, R.id.btnOperatorObelus};

        for (int ID : btnIDs) {
            Button btnOperators = (Button) findViewById(ID);
            btnOperators.setEnabled(isEnable);
        }
    }

    private void setNumbersEnabled(String text, boolean isEnable) {
        LinearLayout ln = (LinearLayout) findViewById(R.id.linearLayoutNumbers);
        for (int i = 0; i < ln.getChildCount(); i++) {
            Button temp = (Button) ln.getChildAt(i);
            if (!temp.getText().equals(text)) {
                temp.setEnabled(isEnable);
            }

        }

    }


}
