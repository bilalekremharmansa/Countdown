package com.bilalekremharmansa.countdown.game;


import android.os.Parcel;
import android.os.Parcelable;

import com.bilalekremharmansa.countdown.customcomponents.NGExpressionAdapter;
import com.bilalekremharmansa.countdown.webapi.APINumberGame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bilalekremharmansa on 17.7.2017.
 */

public class NumberGame extends CountdownGame implements Parcelable {

    private Logger log = LoggerFactory.getLogger(NumberGame.class);
    private static final String LOG_TAG = "NumberGameLOGTag";

    private List<NGExpression> expressionList;

    public static int target;

    private List<Wrapper> wrapperList;


    private List<List<String>> solutionList;

    private GameOverListener gameOverListener;

    public interface GameOverListener {
        void onGameOver(int score);
    }


    public NumberGame(List<Integer> numbersList) {
        initWrapper(numbersList);
        this.target = 125;

        this.expressionList = new ArrayList<>();

    }

    public NumberGame() {

    }

    public NumberGame(List<Integer> numbersList, int target) {
        initWrapper(numbersList);

        NumberGame.target = target;

        this.expressionList = new ArrayList<>(5);

    }


    public void restoreNumberGame(NGExpressionAdapter expressionAdapter, MementoNumberGame memento) {
        this.wrapperList = new ArrayList<>(memento.getWrapperList());
        this.setExpressionList(expressionAdapter, memento.getExpressionList());

    }

    public void initWrapper(List<Integer> numbersList) {
        this.wrapperList = new ArrayList<>(numbersList.size());
        for (int i : numbersList) {
            wrapperList.add(new Wrapper(i));
        }
    }

    public Wrapper getWrapperByID(int wrapperID) {
        for (Wrapper wrapper : wrapperList) {
            if (wrapperID == wrapper.getIdOfButton())
                return wrapper;
        }
        return null;
    }

    public void onResultFound() {
        gameOverListener.onGameOver(score(target, target));
    }

    public void onGameOver() {
        int higherScore = 0;
        int score;
        for (Wrapper wrapper : wrapperList) {
            // if(wrapper.getState() <= 2){
            score = score(wrapper.getValue(), target);
            higherScore = score > higherScore ? score : higherScore;
            // }
        }

        gameOverListener.onGameOver(higherScore);
    }

    public void setGameOverListener(GameOverListener gameOverListener) {
        this.gameOverListener = gameOverListener;
    }

    private int score(int calculated, int target) {
        int away = Math.abs(target - calculated);

        /*if(away == 0) scorePoint = 10;
        else if( away < 6 ) scorePoint = 7;
        else if( away < 11 ) scorePoint = 5;*/

        //return score ;
        return (away == 0) ? 10 : (away < 6) ? 7 : (away < 11) ? 5 : 0;
    }



    public List<Wrapper> getWrapperList() {
        return wrapperList;
    }

    public boolean createExpresssion(NGExpressionAdapter expressionAdapter, Wrapper firstWrapper) {
        NGExpression expression;
        if (!expressionList.isEmpty()) {
            expression = expressionList.get(expressionList.size() - 1);

            if (!expression.isDone()) {
                return false;
            }

            MementoCaretaker.getInstance().saveMemento(MementoNumberGame.MementoMode.EXP_FIRST, this);
        }

        expression = new NGExpression(firstWrapper);
        firstWrapper.setState(2);

        expression.setVisibility(NGExpression.Visibility.FIRST);

        expressionList.add(expression);

        log.info(LOG_TAG, "Expression oluşturuldu");
        expressionAdapter.notifyItemInserted(expressionList.size() - 1);
        return true;
    }

    public void updateExpression(NGExpressionAdapter expressionAdapter, Wrapper newWrapper) {
        NGExpression expression = null;
        if (!expressionList.isEmpty()) {
            expression = expressionList.get(expressionList.size() - 1);

            if (expression.isDone())
                return;
        }

        if (expression.getOperator() == NGExpression.DEFAULT_CHAR_VALUE) {
            Wrapper oldWrapper = expression.getFirstWrapper();
            oldWrapper.setState(1);

            expression.setFirstWrapper(newWrapper);
            newWrapper.setState(2);
        } else {
            int result = NumberGameUtil.evaluateExpression(expression.getFirstWrapper().getValue(),
                    newWrapper.getValue(), expression.getOperator());

            if (result == -1)
                return;

            MementoCaretaker.getInstance().saveMemento(MementoNumberGame.MementoMode.EXP_SECOND, this);

            Wrapper resultWrapper = new Wrapper(result);
            wrapperList.add(resultWrapper);

            expression.setSecondWrapper(newWrapper);
            expression.setResultWrapper(resultWrapper);

            expression.setVisibility(NGExpression.Visibility.FIRST_OPERATOR_SECOND_RESULT_ACTIVE);

            expression.getFirstWrapper().setState(4);
            expression.getSecondWrapper().setState(4);

            expression.setDone(true);

            if (result == NumberGame.target) {
                onResultFound();
            } else if (getWrapperList().size() == 10) {
                onGameOver();
            }

        }

        log.info(LOG_TAG, "Expression güncellendi");
        expressionAdapter.notifyItemChanged(expressionList.size() - 1);
    }

    public void updateExpression(NGExpressionAdapter expressionAdapter, char operator) {
        NGExpression expression = null;
        if (!expressionList.isEmpty()) {
            expression = expressionList.get(expressionList.size() - 1);

            if (expression.isDone())
                return;
        } else {
            return;
        }

        if (expression.getSecondWrapper() == null) {
            if (expression.getOperator() == NGExpression.DEFAULT_CHAR_VALUE)
                MementoCaretaker.getInstance().saveMemento(MementoNumberGame.MementoMode.EXP_OPERATOR, this);

            expression.setOperator(operator);

            expression.setVisibility(NGExpression.Visibility.FIRST_OPERATOR);

            log.info(LOG_TAG, "Expression güncellendi");
            expressionAdapter.notifyItemChanged(expressionList.size() - 1);
        }

    }

    public List<NGExpression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(NGExpressionAdapter expressionAdapter, List<NGExpression> expressionList) {
        this.expressionList = expressionList;
        expressionAdapter.setNgExpressionList(expressionList);
        expressionAdapter.notifyDataSetChanged();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeList(this.expressionList);
        dest.writeList(this.wrapperList);
        dest.writeList(this.solutionList);

    }

    public static void setTarget(int target) {
        NumberGame.target = target;
    }

    public int getTarget() {
        return target;
    }

    protected NumberGame(Parcel in) {

        this.expressionList = new ArrayList<>();
        in.readList(this.expressionList, NGExpression.class.getClassLoader());
        this.wrapperList = new ArrayList<>();
        in.readList(this.wrapperList, Wrapper.class.getClassLoader());
        this.solutionList = new ArrayList<>();
        in.readList(this.solutionList, List.class.getClassLoader());
        this.gameOverListener = in.readParcelable(GameOverListener.class.getClassLoader());
    }

    public static final Creator<NumberGame> CREATOR = new Creator<NumberGame>() {
        @Override
        public NumberGame createFromParcel(Parcel source) {
            return new NumberGame(source);
        }

        @Override
        public NumberGame[] newArray(int size) {
            return new NumberGame[size];
        }
    };
}
