package com.bilalekremharmansa.countdown.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by bilalekremharmansa on 18.8.2017.
 */

public class Wrapper implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.value);
        dest.writeInt(this.state);
        dest.writeInt(this.idOfButton);
    }

    protected Wrapper(Parcel in) {
        this.value = in.readInt();
        this.state = in.readInt();
        this.idOfButton = in.readInt();
    }

    public static final Creator<Wrapper> CREATOR = new Creator<Wrapper>() {
        @Override
        public Wrapper createFromParcel(Parcel source) {
            return new Wrapper(source);
        }

        @Override
        public Wrapper[] newArray(int size) {
            return new Wrapper[size];
        }
    };
}
