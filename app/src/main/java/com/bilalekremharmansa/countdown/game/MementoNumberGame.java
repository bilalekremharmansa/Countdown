package com.bilalekremharmansa.countdown.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bilalekremharmansa on 19.7.2017.
 */

public final class MementoNumberGame {

    private final MementoMode mementoMode;
    private final List<Wrapper> wrapperList;
    private final List<NumberGameExpression> expressionList;

    public enum MementoMode {
        CONSTANT, EXP_FIRST, EXP_OPERATOR, EXP_SECOND, RESULT_BUTTON;
    }

    public MementoNumberGame(MementoMode mementoMode, List<Wrapper> wrapperList, List<NumberGameExpression> expressionList) {
        this.mementoMode = mementoMode;
        this.wrapperList = new ArrayList<>(wrapperList.size());
        for (Wrapper wrapper : wrapperList) {
            this.wrapperList.add(new Wrapper(wrapper));
        }
        this.expressionList = new ArrayList<>(expressionList.size());
        for (NumberGameExpression e : expressionList) {
            this.expressionList.add(new NumberGameExpression(e));
        }
    }


    public List<Wrapper> getWrapperList() {
        return wrapperList;
    }

    public List<NumberGameExpression> getExpressionList() {
        return expressionList;
    }

    public MementoMode getMementoMode() {
        return mementoMode;
    }
}
