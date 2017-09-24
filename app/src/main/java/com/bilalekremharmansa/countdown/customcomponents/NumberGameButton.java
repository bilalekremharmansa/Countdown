package com.bilalekremharmansa.countdown.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;


/**
 * Created by bilalekremharmansa on 19.7.2017.
 */

public class NumberGameButton extends android.support.v7.widget.AppCompatButton {

    private int value;
    private boolean isPicked;


    private boolean isCalculatedButton;


    public NumberGameButton(Context context) {
        super(context);
    }


    public NumberGameButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberGameButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setBackgroundDefault() {
        Drawable buttonBackground = getBackground();
        if (buttonBackground != null) {
            buttonBackground.setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            setBackground(buttonBackground);
        }
    }

    private void setBackgroundPicked() {
        Drawable buttonBackground = getBackground();
        if (buttonBackground != null) {
            buttonBackground.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            setBackground(buttonBackground);
        }
    }

    public int getValue() {
        return value;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;

        if (isPicked) {
            setBackgroundPicked();
        } else {
            setBackgroundDefault();
        }


    }

    public NumberGameButton setValue(int value) {
        this.value = value;
        return this;
    }


    public boolean isCalculatedButton() {
        return isCalculatedButton;
    }

    public void setCalculatedButton(boolean calculatedButton) {
        isCalculatedButton = calculatedButton;
    }
}