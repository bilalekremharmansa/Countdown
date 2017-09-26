package com.bilalekremharmansa.countdown.customcomponents;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bilalekremharmansa.countdown.R;
import com.bilalekremharmansa.countdown.game.NGExpression;

import java.util.List;

/**
 * Created by bilalekremharmansa on 24.9.2017.
 */


@SuppressWarnings("WrongConstant")
public class NGExpressionAdapter extends RecyclerView.Adapter<NGExpressionAdapter.ViewHolder> {
    private List<NGExpression> ngExpressionList;

    private ResultButtonListener resultButtonListener;

    public interface ResultButtonListener {
        void onResultButtonClickListener(final int position);
    }

    public NGExpressionAdapter(final List<NGExpression> ngExpressionList) {
        this.ngExpressionList = ngExpressionList;
    }

    @Override
    public NGExpressionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_number_game_expression, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(final NGExpressionAdapter.ViewHolder holder, final int position) {
        final NGExpression expression = ngExpressionList.get(position);

        TextView txtFirstNumber = holder.txtFirstNumber;
        TextView txtOperator = holder.txtOperator;
        TextView txtSecondNumber = holder.txtSecondNumber;
        holder.txtEqual.setText("=");
        Button btnResultNumber = holder.btnResult;

        if (expression.getFirstWrapper() != null) {
            txtFirstNumber.setText(String.valueOf(expression.getFirstWrapper().getValue()));
        }
        if (expression.getOperator() != NGExpression.DEFAULT_CHAR_VALUE) {
            txtOperator.setText(String.valueOf(expression.getOperator()));
        }
        if (expression.getSecondWrapper() != null) {
            txtSecondNumber.setText(String.valueOf(expression.getSecondWrapper().getValue()));
        }
        if (expression.getResultWrapper() != null) {
            btnResultNumber.setText(String.valueOf(expression.getResultWrapper().getValue()));
        }

        setVisibilities(holder, expression.getVisibility());

        holder.btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultButtonListener != null)
                    resultButtonListener.onResultButtonClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ngExpressionList.size();
    }

    private void setVisibilities(NGExpressionAdapter.ViewHolder holder, NGExpression.Visibility visibility) {
        TextView txtFirstNumber = holder.txtFirstNumber;
        TextView txtOperator = holder.txtOperator;
        TextView txtSecondNumber = holder.txtSecondNumber;
        TextView txtEqual = holder.txtEqual;
        Button btnResult = holder.btnResult;

        switch (visibility) {
            case NONE:
                txtFirstNumber.setVisibility(View.INVISIBLE);
                txtOperator.setVisibility(View.INVISIBLE);
                txtSecondNumber.setVisibility(View.INVISIBLE);
                txtEqual.setVisibility(View.INVISIBLE);
                btnResult.setVisibility(View.INVISIBLE);
            case FIRST:
                txtFirstNumber.setVisibility(View.VISIBLE);
                txtOperator.setVisibility(View.INVISIBLE);
                txtSecondNumber.setVisibility(View.INVISIBLE);
                txtEqual.setVisibility(View.INVISIBLE);
                btnResult.setVisibility(View.INVISIBLE);
                break;
            case FIRST_OPERATOR:
                txtFirstNumber.setVisibility(View.VISIBLE);
                txtOperator.setVisibility(View.VISIBLE);
                txtSecondNumber.setVisibility(View.INVISIBLE);
                txtEqual.setVisibility(View.INVISIBLE);
                btnResult.setVisibility(View.INVISIBLE);
                break;
            case FIRST_OPERATOR_SECOND_RESULT_ACTIVE:
                txtFirstNumber.setVisibility(View.VISIBLE);
                txtOperator.setVisibility(View.VISIBLE);
                txtSecondNumber.setVisibility(View.VISIBLE);
                txtEqual.setVisibility(View.VISIBLE);
                btnResult.setVisibility(View.VISIBLE);
                btnResult.setEnabled(true);
                break;
            case FIRST_OPERATOR_SECOND_RESULT_PASSIVE:
                txtFirstNumber.setVisibility(View.VISIBLE);
                txtOperator.setVisibility(View.VISIBLE);
                txtSecondNumber.setVisibility(View.VISIBLE);
                txtEqual.setVisibility(View.VISIBLE);
                btnResult.setVisibility(View.VISIBLE);
                btnResult.setEnabled(false);
                break;
        }
    }

    public void setResultButtonListener(ResultButtonListener resultButtonListener) {
        this.resultButtonListener = resultButtonListener;
    }

    public void setNgExpressionList(List<NGExpression> ngExpressionList) {
        this.ngExpressionList = ngExpressionList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        private TextView txtFirstNumber;
        private TextView txtOperator;
        private TextView txtSecondNumber;
        private TextView txtEqual;
        private Button btnResult;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;

            this.txtFirstNumber = (TextView) cardView.findViewById(R.id.txt_first_number);
            this.txtOperator = (TextView) cardView.findViewById(R.id.txt_operator);
            this.txtSecondNumber = (TextView) cardView.findViewById(R.id.txt_second_number);
            this.txtEqual = (TextView) cardView.findViewById(R.id.txt_equal);
            this.btnResult = (Button) cardView.findViewById(R.id.btn_result);
        }
    }
}
