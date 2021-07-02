package com.sport.playsqorr.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class EditTextPlus extends EditText {
    public EditTextPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextPlus(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Exo_Regular.ttf");
            setTypeface(tf);
        }
    }

}

