package com.sport.playsqorr.utilities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.sport.playsqorr.R;

import java.util.ArrayList;

public class PresetValueButtonHow extends RelativeLayout implements RadioCheckable {
    public static final int DEFAULT_TEXT_COLOR = 0;
    private boolean mChecked;
    private Drawable mInitialBackgroundDrawable;
    private ArrayList<OnCheckedChangeListener> mOnCheckedChangeListeners = new ArrayList<>();
    private OnClickListener mOnClickListener;
    /* access modifiers changed from: private */
    public OnTouchListener mOnTouchListener;
    private int mPressedTextColor;
    private String mUnit;
    private int mUnitTextColor;
    private TextView mUnitTextView;
    private String mValue;
    private int mValueTextColor;
    private TextView mValueTextView;

    private final class TouchListener implements OnTouchListener {
        private TouchListener() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    PresetValueButtonHow.this.onTouchDown(event);
                    break;
                case 1:
                    PresetValueButtonHow.this.onTouchUp(event);
                    break;
            }
            if (PresetValueButtonHow.this.mOnTouchListener != null) {
                PresetValueButtonHow.this.mOnTouchListener.onTouch(v, event);
            }
            return true;
        }
    }

    @RequiresApi(api = 23)
    public PresetValueButtonHow(Context context) {
        super(context);
        setupView();
    }

    @RequiresApi(api = 23)
    public PresetValueButtonHow(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setupView();
    }

    @TargetApi(23)
    @RequiresApi(api = 11)
    public PresetValueButtonHow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        setupView();
    }

    @SuppressLint({"NewApi"})
    @RequiresApi(api = 21)
    public PresetValueButtonHow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
        setupView();
    }


    private void parseAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PresetValueButton, 0, 0);
        Resources resources = getContext().getResources();
        try {
            this.mValue = a.getString(3);
            this.mUnit = a.getString(1);
            this.mValueTextColor = a.getColor(4, resources.getColor(R.color.black));
            this.mPressedTextColor = a.getColor(0, -1);
            this.mUnitTextColor = a.getColor(2, resources.getColor(R.color.black));
        } finally {
            a.recycle();
        }
    }

    @RequiresApi(api = 23)
    private void setupView() {
        inflateView();
        bindView();
        setCustomTouchListener();
    }

    /* access modifiers changed from: protected */
    public void inflateView() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_preset_button, this, true);
        this.mValueTextView = (TextView) findViewById(R.id.text_view_value);
        this.mUnitTextView = (TextView) findViewById(R.id.text_view_unit);
        this.mInitialBackgroundDrawable = getBackground();
    }

    /* access modifiers changed from: protected */
    @RequiresApi(api = 23)
    public void bindView() {
        if (this.mUnitTextColor != 0) {
            this.mUnitTextView.setTextColor(this.mUnitTextColor);
            this.mValueTextView.setCompoundDrawableTintList(ColorStateList.valueOf(this.mUnitTextColor));
        }
        if (this.mValueTextColor != 0) {
            this.mValueTextView.setCompoundDrawableTintList(ColorStateList.valueOf(this.mUnitTextColor));
        }
        this.mUnitTextView.setText(this.mUnit);

        if (this.mUnit.equals("Baseball")) {
            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseball, 0, 0, 0);
        }
       /* if (this.mUnit.equals("MLB")) {
            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseball, 0, 0, 0);
        }*/


        if (this.mUnit.equals("Basketball")) {
            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_basketball, 0, 0, 0);
        }

        if (this.mUnit.equals("Soccer")) {
            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_soccer, 0, 0, 0);
        }

//        if (this.mUnit.equals("Soccer")) {
//            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_soccer, 0, 0, 0);
//        }

        if (this.mUnit.equals("Football")) {
            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_am_football, 0, 0,  0);
        }

        if (this.mUnit.equals("Motor")) {
            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_nascar_h, 0, 0, 0);
//            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_nascar_h, 0, 0, 0);
        }

        if (this.mUnit.equals("Hockey")) {
            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_hockey, 0, 0, 0);
        }

        if (this.mUnit.equals("Golf")) {
            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_golf, 0, 0, 0);
        }


/*
        if (this.mUnit.equals("NBA")) {
            this.mValueTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_basketball, 0, 0, 0);
        }
*/

    }

    public void setOnClickListener(@Nullable OnClickListener l) {
        this.mOnClickListener = l;
    }

    /* access modifiers changed from: protected */
    public void setCustomTouchListener() {
        super.setOnTouchListener(new TouchListener());
    }

    public OnTouchListener getOnTouchListener() {
        return this.mOnTouchListener;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    /* access modifiers changed from: private */
    @TargetApi(23)
    public void onTouchDown(MotionEvent motionEvent) {
        setChecked(true);
    }

    /* access modifiers changed from: private */
    public void onTouchUp(MotionEvent motionEvent) {
        if (this.mOnClickListener != null) {
            this.mOnClickListener.onClick(this);
        }
    }

    @RequiresApi(api = 23)
    public void setCheckedState() {
        setBackgroundResource(R.drawable.background_shape_preset_button__pressed);
        this.mValueTextView.setCompoundDrawableTintList(ColorStateList.valueOf(this.mPressedTextColor));
        this.mUnitTextView.setTextColor(this.mPressedTextColor);
    }

    @RequiresApi(api = 23)
    public void setNormalState() {
        setBackgroundDrawable(this.mInitialBackgroundDrawable);
        this.mUnitTextView.setTextColor(this.mUnitTextColor);
        this.mValueTextView.setCompoundDrawableTintList(ColorStateList.valueOf(this.mUnitTextColor));
    }

    public String getValue() {
        return this.mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }

    public String getUnit() {
        return this.mUnit;
    }

    public void setUnit(String unit) {
        this.mUnit = unit;
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    @RequiresApi(api = 23)
    public void setChecked(boolean checked) {
        if (this.mChecked != checked) {
            this.mChecked = checked;
            if (!this.mOnCheckedChangeListeners.isEmpty()) {
                for (int i = 0; i < this.mOnCheckedChangeListeners.size(); i++) {
                    ((OnCheckedChangeListener) this.mOnCheckedChangeListeners.get(i)).onCheckedChanged(this, this.mChecked);
                }
            }
            if (mChecked) {
                setCheckedState();
            } else {
                setNormalState();
            }
        }
    }

    @RequiresApi(api = 23)
    public void toggle() {
        setChecked(!this.mChecked);
    }

    public void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListeners.add(onCheckedChangeListener);
    }

    public void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListeners.remove(onCheckedChangeListener);
    }
}
