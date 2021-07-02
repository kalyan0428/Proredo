package com.sport.playsqorr.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

public class Checkbox extends AppCompatCheckBox {


    public Checkbox(Context context) {
        super(context);
        init();
    }

    public Checkbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Checkbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Exo_Regular.ttf");
            setTypeface(tf);
        }
    }
}
