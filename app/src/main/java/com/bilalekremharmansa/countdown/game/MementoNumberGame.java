package com.bilalekremharmansa.countdown.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bilalekremharmansa on 19.7.2017.
 */

public final class MementoNumberGame {

    private final MementoMode mementoMode;
    private final List<Wrapper> wrapperList;
    private final List<NGExpression> expressionList;

    public enum MementoMode {
        CONSTANT, EXP_FIRST, EXP_OPERATOR, EXP_SECOND, RESULT_BUTTON;
    }

    public MementoNumberGame(MementoMode mementoMode, List<Wrapper> wrapperList, List<NGExpression> expressionList) {
        this.mementoMode = mementoMode;
        this.wrapperList = new ArrayList<>(wrapperList.size());
        for (Wrapper wrapper : wrapperList) {
            this.wrapperList.add(new Wrapper(wrapper));
        }
        this.expressionList = new ArrayList<>(expressionList.size());
        for (NGExpression e : expressionList) {
            this.expressionList.add(new NGExpression(e));
        }
    }


    public List<Wrapper> getWrapperList() {
        return wrapperList;
    }

    public List<NGExpression> getExpressionList() {
        return expressionList;
    }

    public MementoMode getMementoMode() {
        return mementoMode;
    }
}
