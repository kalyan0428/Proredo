package com.sport.playsqorr.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.sport.playsqorr.R;

public class TextViewPlus extends TextView {

    private static final String sTAG = "TextView";

    /**
     * Constructor
     *
     * @param context Context
     */
    public TextViewPlus(Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setCustomFont(context, attrs);
    }

    /**
     * Constructor
     *
     * @param context  Context
     * @param attrs    AttributeSet
     * @param defStyle Style Def
     */
    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        setCustomFont(context, attrs);
    }

    /**
     * Used to set custom font
     *
     * @param ctx   Context
     * @param attrs AttributeSet
     */
    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        String customFont = a.getString(R.styleable.TextViewPlus_customFont);
//        setCustomFont(ctx, customFont);
        a.recycle();
    }

    /**
     * Used to set custom font
     *
     * @param ctx   Context
     * @param asset String
     * @return true if success else false
     */
    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(sTAG, "Could not get typeface: " + e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }
}


