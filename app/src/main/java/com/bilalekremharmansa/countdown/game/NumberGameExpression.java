package com.bilalekremharmansa.countdown.game;

import android.content.Context;
import android.view.View;

import com.bilalekremharmansa.countdown.customcomponents.CustomViewExpression;

/**
 * Created by bilalekremharmansa on 24.7.2017.
 */

public class NumberGameExpression {
    public static final char DEFAULT_CHAR_VALUE = '\u0000';

    private final int idOfExpressionLayout;
    private int firstWrapperButtonID;
    private char operator; //'\u0000'
    private int secondWrapperButtonID;
    private int resultWrapperButtonID;

    private boolean isDone;

    private final CustomViewExpression customViewExpression;

    private static GameOverListener gameOverListener;

    public interface GameOverListener {
        void onResultFound();

        void onGameOver();
    }

    public NumberGameExpression(Context context, int firstNumberIndex) {
        this.firstWrapperButtonID = firstNumberIndex;
        this.secondWrapperButtonID = -1;

        this.idOfExpressionLayout = View.generateViewId();

        customViewExpression = createCustomViewExpression(context);
    }

    public NumberGameExpression(NumberGameExpression expression) {
        this.firstWrapperButtonID = expression.getFirstWrapperButtonID();
        this.operator = expression.getOperator();
        this.secondWrapperButtonID = expression.getSecondWrapperButtonID();
        this.resultWrapperButtonID = expression.getResultWrapperButtonID();
        this.idOfExpressionLayout = expression.getIdOfExpressionLayout();
        this.isDone = expression.isDone();
        this.customViewExpression = expression.getCustomViewExpression();

    }

    private CustomViewExpression createCustomViewExpression(Context context) {
        CustomViewExpression expressionLayout = new CustomViewExpression(context, this);
        expressionLayout.setId(this.getIdOfExpressionLayout());
        return expressionLayout;
    }

    public void updateUI(NumberGame numberGame, String c) {
        if (secondWrapperButtonID == -1) {
            if (operator == DEFAULT_CHAR_VALUE) {
                MementoCaretaker.getInstance().saveMemento(MementoNumberGame.MementoMode.EXP_OPERATOR, numberGame);
            }

            operator = c.charAt(0);
            customViewExpression.getTxtOperator().setText(c);
            customViewExpression.setStatusOfView(customViewExpression.getTxtOperator(), 1);
        }
    }

    public void updateUI(NumberGame numberGame, final int wrapperButtonID, int value) {
        if (operator == DEFAULT_CHAR_VALUE) {
            int prevFirstWrapperButtonID = firstWrapperButtonID;

            firstWrapperButtonID = wrapperButtonID;
            customViewExpression.getTxtFirstNumber().setText(String.valueOf(value));
            customViewExpression.setStatusOfView(customViewExpression.getTxtFirstNumber(), 1);

            Wrapper wrapper = null;
            if ((wrapper = numberGame.getWrapperByID(prevFirstWrapperButtonID)) != null) {
                wrapper.setState(1);
            }

            if ((wrapper = numberGame.getWrapperByID(firstWrapperButtonID)) != null) {
                wrapper.setState(2);
            }


        } else {
            //memento saves
            int firstValue = -1, secondValue = -1;

            Wrapper wrapper = null;
            if ((wrapper = numberGame.getWrapperByID(firstWrapperButtonID)) != null) {
                firstValue = wrapper.getValue();
            }

            if ((wrapper = numberGame.getWrapperByID(wrapperButtonID)) != null) {
                secondValue = wrapper.getValue();
            }


            int result = NumberGameUtil.evaluateExpression(firstValue, secondValue, this.getOperator());
            if (result == -1) {
                return;
            }

            MementoCaretaker.getInstance().saveMemento(MementoNumberGame.MementoMode.EXP_SECOND, numberGame);

            secondWrapperButtonID = wrapperButtonID;

            customViewExpression.getBtnResult().setText(String.valueOf(result));
            customViewExpression.getTxtSecondNumber().setText(String.valueOf(value));

            customViewExpression.setStatusOfView(customViewExpression.getTxtSecondNumber(), 1);
            customViewExpression.setStatusOfView(customViewExpression.getTxtEqual(), 1);
            customViewExpression.setStatusOfView(customViewExpression.getBtnResult(), 1);

            if ((wrapper = numberGame.getWrapperByID(firstWrapperButtonID)) != null) {
                wrapper.setState(4);
            }
            if ((wrapper = numberGame.getWrapperByID(secondWrapperButtonID)) != null) {
                wrapper.setState(4);
            }

            if (result == NumberGame.target) {
                gameOverListener.onResultFound();
            } else if (numberGame.getWrapperList().size() == 10) {
                gameOverListener.onGameOver();
            }

            isDone = true;
        }
    }

    public CustomViewExpression getCustomViewExpression() {
        return customViewExpression;
    }

    public int getFirstWrapperButtonID() {
        return firstWrapperButtonID;
    }

    public char getOperator() {
        return operator;
    }


    public int getSecondWrapperButtonID() {
        return secondWrapperButtonID;
    }

    public void setSecondWrapperButtonID(int secondWrapperButtonID) {
        this.secondWrapperButtonID = secondWrapperButtonID;
    }

    public boolean isDone() {
        return isDone;
    }


    public int getIdOfExpressionLayout() {
        return idOfExpressionLayout;
    }


    public int getResultWrapperButtonID() {
        return resultWrapperButtonID;
    }

    public void setResultWrapperButtonID(int resultWrapperButtonID) {
        this.resultWrapperButtonID = resultWrapperButtonID;
    }

    public static void setGameOverListener(GameOverListener gameOverListener) {
        NumberGameExpression.gameOverListener = gameOverListener;
    }

    public static GameOverListener GameOverListener() {
        return gameOverListener;
    }

}


