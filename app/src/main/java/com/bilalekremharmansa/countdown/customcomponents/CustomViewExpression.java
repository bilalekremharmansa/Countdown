package com.bilalekremharmansa.countdown.customcomponents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilalekremharmansa.countdown.R;
import com.bilalekremharmansa.countdown.game.NumberGame;
import com.bilalekremharmansa.countdown.game.NumberGameExpression;

/**
 * Created by bilalekremharmansa on 24.7.2017.
 */

public class CustomViewExpression extends LinearLayout implements View.OnClickListener {
    private LinearLayout linearLayout = null;
    private TextView txtFirstNumber;
    private TextView txtOperator;
    private TextView txtSecondNumber;
    private TextView txtEqual;
    private Button btnResult;
    private Context mContext;

    private ButtonStateChangedListener listener;


    public interface ButtonStateChangedListener {
        void onResultOfExpressionClickedListener(int customViewExpressionID, int resultValue);
    }


    public CustomViewExpression(Context context) {
        super(context);
        mContext = context;
    }

    public CustomViewExpression(Context context, NumberGameExpression expression) {
        super(context);
        mContext = context;


        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);

        linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.custom_view_group_expression, this, true);

        txtFirstNumber = (TextView) linearLayout.findViewById(R.id.txtFirstNumber);
        txtFirstNumber.setVisibility(View.INVISIBLE);

        txtOperator = (TextView) linearLayout.findViewById(R.id.txtOperator);
        txtOperator.setVisibility(View.INVISIBLE);

        txtSecondNumber = (TextView) linearLayout.findViewById(R.id.txtSecondNumber);
        txtSecondNumber.setVisibility(View.INVISIBLE);

        txtEqual = (TextView) linearLayout.findViewById(R.id.txtEqual);
        txtEqual.setVisibility(View.INVISIBLE);

        btnResult = (Button) linearLayout.findViewById(R.id.btnResult);

        btnResult.setVisibility(View.INVISIBLE);
        btnResult.setOnClickListener(this);

        listener = (ButtonStateChangedListener) mContext;

    }

    @Override
    public void onClick(View v) {
        Button b = ((Button) v);
        int resultValue = Integer.valueOf(b.getText().toString());
        listener.onResultOfExpressionClickedListener(this.getId(), resultValue);
        b.setEnabled(false);
    }


    public void setStatusOfView(View v, int state) {
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


    public void setButtonStateChangedListener(ButtonStateChangedListener listener) {
        this.listener = listener;
    }

    public TextView getTxtSecondNumber() {
        return txtSecondNumber;
    }

    public Button getBtnResult() {
        return btnResult;
    }

    public TextView getTxtFirstNumber() {
        return txtFirstNumber;
    }

    public TextView getTxtOperator() {
        return txtOperator;
    }

    public TextView getTxtEqual() {
        return txtEqual;
    }
}
