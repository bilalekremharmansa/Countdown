package com.bilalekremharmansa.countdown.game;

import android.view.View;

/**
 * Created by bilalekremharmansa on 18.8.2017.
 */

public class Wrapper {
    private int value;
    private int state; //1 visible-enable, 2visible-disable , 3 gone, 4-invisible
    private int idOfButton;

    private static WrapperStateListener listener;

    public interface WrapperStateListener {
        void onExpressionUpdate(int buttonID, int state);
    }

    public Wrapper(int number) {
        this.value = number;
        this.state = 1;
        this.idOfButton = View.generateViewId();
    }

    public Wrapper(Wrapper wrapper) {
        this.value = wrapper.getValue();
        this.state = wrapper.getState();
        this.idOfButton = wrapper.getIdOfButton();
    }


    public int getValue() {
        return value;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if (state >= 0 && state <= 4) {
            this.state = state;
            listener.onExpressionUpdate(idOfButton, state);
        }
    }

    public int getIdOfButton() {
        return idOfButton;
    }

    public static void setListener(WrapperStateListener listener) {
        Wrapper.listener = listener;
    }
}
