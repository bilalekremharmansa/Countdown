package com.bilalekremharmansa.countdown.game;

import android.util.Log;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Created by bilalekremharmansa on 27.7.2017.
 */

public class MementoCaretaker {
    private static final MementoCaretaker ourInstance = new MementoCaretaker();
    public static Stack<MementoNumberGame> mementoStack = new Stack<>();

    public static MementoCaretaker getInstance() {
        return ourInstance;
    }


    private MementoCaretaker() {

    }

    public void saveMemento(MementoNumberGame.MementoMode mementoMode, NumberGame numberGame) {

        saveMemento(mementoMode, numberGame.getWrapperList(), numberGame.getExpressionList());
    }

    private void saveMemento(MementoNumberGame.MementoMode mementoMode, List<Wrapper> wrapperList, List<NGExpression> expressionList) {
        if (mementoMode == MementoNumberGame.MementoMode.CONSTANT) {
            Log.i("MementoMode", "here");
        }
        mementoStack.add(new MementoNumberGame(mementoMode, wrapperList, expressionList));
    }

    public MementoNumberGame restoreMemento() throws EmptyStackException {
        if (mementoStack.peek().getMementoMode() == MementoNumberGame.MementoMode.CONSTANT) {
            MementoNumberGame mementoPeek = mementoStack.peek();
            return new MementoNumberGame(MementoNumberGame.MementoMode.CONSTANT, mementoPeek.getWrapperList(), mementoPeek.getExpressionList());
        } else {
            return mementoStack.pop();
        }

    }

    public void clearStack() {
        if (!mementoStack.isEmpty()) mementoStack.clear();
    }
}
