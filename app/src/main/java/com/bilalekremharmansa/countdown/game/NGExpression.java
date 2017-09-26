package com.bilalekremharmansa.countdown.game;

/**
 * Created by bilalekremharmansa on 24.9.2017.
 */

public class NGExpression {
    public static final char DEFAULT_CHAR_VALUE = '\u0000';

    private Wrapper firstWrapper;
    private char operator; //'\u0000' as default
    private Wrapper secondWrapper;
    private Wrapper resultWrapper;

    private Visibility visibility;

    private boolean isDone;

    public enum Visibility {
        NONE, FIRST, FIRST_OPERATOR, FIRST_OPERATOR_SECOND_RESULT_ACTIVE, FIRST_OPERATOR_SECOND_RESULT_PASSIVE
    }

    public NGExpression(Wrapper firstWrapper) {
        this.firstWrapper = firstWrapper;
        operator = DEFAULT_CHAR_VALUE;
    }

    public NGExpression(NGExpression expression) {
        this.firstWrapper = expression.getFirstWrapper();
        this.operator = expression.getOperator();
        this.secondWrapper = expression.getSecondWrapper();
        this.resultWrapper = expression.getResultWrapper();
        this.visibility = expression.getVisibility();
        this.isDone = expression.isDone();
    }

    public Wrapper getFirstWrapper() {
        return firstWrapper;
    }

    public char getOperator() {
        return operator;
    }

    public Wrapper getSecondWrapper() {
        return secondWrapper;
    }

    public Wrapper getResultWrapper() {
        return resultWrapper;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setFirstWrapper(Wrapper firstWrapper) {
        this.firstWrapper = firstWrapper;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public void setSecondWrapper(Wrapper secondWrapper) {
        this.secondWrapper = secondWrapper;
    }

    public void setResultWrapper(Wrapper resultWrapper) {
        this.resultWrapper = resultWrapper;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public boolean isDone() {
        return isDone;
    }
}
