package com.bilalekremharmansa.countdown.game;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bilalekremharmansa on 24.9.2017.
 */

public class NGExpression implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.firstWrapper, flags);
        dest.writeInt(this.operator);
        dest.writeParcelable(this.secondWrapper, flags);
        dest.writeParcelable(this.resultWrapper, flags);
        dest.writeInt(this.visibility == null ? -1 : this.visibility.ordinal());
        dest.writeByte(this.isDone ? (byte) 1 : (byte) 0);
    }

    protected NGExpression(Parcel in) {
        this.firstWrapper = in.readParcelable(Wrapper.class.getClassLoader());
        this.operator = (char) in.readInt();
        this.secondWrapper = in.readParcelable(Wrapper.class.getClassLoader());
        this.resultWrapper = in.readParcelable(Wrapper.class.getClassLoader());
        int tmpVisibility = in.readInt();
        this.visibility = tmpVisibility == -1 ? null : Visibility.values()[tmpVisibility];
        this.isDone = in.readByte() != 0;
    }

    public static final Creator<NGExpression> CREATOR = new Creator<NGExpression>() {
        @Override
        public NGExpression createFromParcel(Parcel source) {
            return new NGExpression(source);
        }

        @Override
        public NGExpression[] newArray(int size) {
            return new NGExpression[size];
        }
    };
}
