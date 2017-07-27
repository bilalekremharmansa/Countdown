package bilalekremharmansa.yeninesilarge.com.countdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import bilalekremharmansa.yeninesilarge.com.countdown.game.NumberGame;
import bilalekremharmansa.yeninesilarge.com.countdown.game.NumberGameExpression;

/**
 * Created by bilalekremharmansa on 24.7.2017.
 */

public class CustomViewExpression extends LinearLayout implements NumberGameExpression.ValueChangedListener, View.OnClickListener {
    private LinearLayout linearLayout = null;
    private TextView txtFirstNumber;
    private TextView txtOperator;
    private TextView txtSecondNumber;
    private TextView txtEqual;
    private Button btnResult;
    private Context mContext;

    private ButtonStateChangedListener listener;

    @Override
    public void onClick(View v) {
        Button b = ((Button) v);
        int resultValue = Integer.valueOf(b.getText().toString());
        listener.onResultButtonClickListener(resultValue);
        b.setEnabled(false);

    }

    public interface ButtonStateChangedListener {
        void onValueChange(int oldIndex, int newIndex, NumberGameExpression expression);

        void onResultButtonClickListener(int resultValue);
    }

    public CustomViewExpression(Context context) {
        super(context);
        mContext = context;
    }

    public CustomViewExpression(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Expression);

        String firstNumber = a.getString(R.styleable.Expression_first_number);
        String operator = a.getString(R.styleable.Expression_operator);
        String secondNumber = a.getString(R.styleable.Expression_second_number);
        String result = a.getString(R.styleable.Expression_result);

        firstNumber = firstNumber == null ? "" : firstNumber;
        operator = operator == null ? "" : operator;
        secondNumber = secondNumber == null ? "" : secondNumber;
        result = result == null ? "" : result;

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);

        linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.custom_view_group_expression, this, true);

        txtFirstNumber = (TextView) linearLayout.findViewById(R.id.txtFirstNumber);
        txtFirstNumber.setText(firstNumber);


        txtOperator = (TextView) linearLayout.findViewById(R.id.txtOperator);
        txtOperator.setText(operator);

        txtSecondNumber = (TextView) linearLayout.findViewById(R.id.txtSecondNumber);
        txtSecondNumber.setText(secondNumber);

        txtEqual = (TextView) linearLayout.findViewById(R.id.txtEqual);
        txtEqual.setVisibility(GONE);

        btnResult = (Button) linearLayout.findViewById(R.id.btnResult);
        btnResult.setText(result);
        btnResult.setVisibility(View.GONE);
        btnResult.setOnClickListener(this);

        a.recycle();
    }

    private View.OnClickListener numbersListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
        }
    };

    public CustomViewExpression(Context context, @Nullable AttributeSet attrs, NumberGameExpression expression) {
        this(context, attrs);
        expression.setValueChangedListener(this);
    }

    @Override
    public void operatorValueChanged(String c) {
        txtOperator.setText(c);
    }

    @Override
    public void oneOfIndexesValueChanged(NumberGameExpression expression, NumberGameExpression.EnumExpression e, int value, int oldIndex) {
        int newIndex = -1;
        switch (e) {
            case FIRST:
                txtFirstNumber.setText(String.valueOf(value));
                newIndex = expression.getFirstNumberIndex();
                break;
            case SECOND:
                txtSecondNumber.setText(String.valueOf(value));
                newIndex = expression.getSecondNumberIndex();
                txtEqual.setVisibility(VISIBLE);
                String result = String.valueOf(NumberGame.evaluateExpression(expression));
                btnResult.setText(result);
                btnResult.setVisibility(VISIBLE);
                break;
        }
        listener.onValueChange(oldIndex, newIndex, expression);
    }

    public void setButtonStateChangedListener(ButtonStateChangedListener listener) {
        this.listener = listener;
    }


    public TextView getTxtFirstNumber() {
        return txtFirstNumber;
    }

    public void setTxtFirstNumber(TextView txtFirstNumber) {
        this.txtFirstNumber = txtFirstNumber;
    }

    public TextView getTxtOperator() {
        return txtOperator;
    }

    public void setTxtOperator(TextView txtOperator) {
        this.txtOperator = txtOperator;
    }

    public TextView getTxtSecondNumber() {
        return txtSecondNumber;
    }

    public void setTxtSecondNumber(TextView txtSecondNumber) {
        this.txtSecondNumber = txtSecondNumber;
    }

    public Button getBtnResult() {
        return btnResult;
    }

    public void setBtnResult(Button btnResult) {
        this.btnResult = btnResult;
    }

    @SuppressWarnings("unused")
    public void setFirstNumber(String text) {
        txtFirstNumber.setText(text);
    }

    @SuppressWarnings("unused")
    public String getFirstNumber() {
        return txtFirstNumber.getText().toString();
    }


    @SuppressWarnings("unused")
    public void setSecondNumber(String text) {
        txtSecondNumber.setText(text);
    }

    @SuppressWarnings("unused")
    public String getSecondNumber() {
        return txtSecondNumber.getText().toString();
    }


    @SuppressWarnings("unused")
    public void setOperator(String text) {
        txtOperator.setText(text);
    }

    @SuppressWarnings("unused")
    public String getOperator() {
        return txtOperator.getText().toString();
    }


    @SuppressWarnings("unused")
    public void setResult(String text) {
        btnResult.setText(text);
    }

    @SuppressWarnings("unused")
    public String getResult() {
        return btnResult.getText().toString();
    }


}
