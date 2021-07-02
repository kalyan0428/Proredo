package com.sport.playsqorr.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;

import com.sport.playsqorr.R;

import java.util.HashMap;

public class PresetRadioGroup extends LinearLayout {
    /* access modifiers changed from: private */
    public int mCheckedId = -1;
    /* access modifiers changed from: private */
    public RadioCheckable.OnCheckedChangeListener mChildOnCheckedChangeListener;
    /* access modifiers changed from: private */
    public HashMap<Integer, View> mChildViewsMap = new HashMap<>();
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private PassThroughHierarchyChangeListener mPassThroughListener;
    /* access modifiers changed from: private */
    public boolean mProtectFromCheckedChange = false;

    private class CheckedStateTracker implements RadioCheckable.OnCheckedChangeListener {
        private CheckedStateTracker() {
        }

        public void onCheckedChanged(View buttonView, boolean isChecked) {
            if (!PresetRadioGroup.this.mProtectFromCheckedChange) {
                PresetRadioGroup.this.mProtectFromCheckedChange = true;
                if (PresetRadioGroup.this.mCheckedId != -1) {
                    PresetRadioGroup.this.setCheckedStateForView(PresetRadioGroup.this.mCheckedId, false);
                }
                PresetRadioGroup.this.mProtectFromCheckedChange = false;
                PresetRadioGroup.this.setCheckedId(buttonView.getId(), true);
            }
        }
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(int w, int h, float initWeight) {
            super(w, h, initWeight);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams p) {
            super(p);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        /* access modifiers changed from: protected */
        public void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
            if (a.hasValue(widthAttr)) {
                this.width = a.getLayoutDimension(widthAttr, "layout_width");
            } else {
                this.width = -2;
            }
            if (a.hasValue(heightAttr)) {
                this.height = a.getLayoutDimension(heightAttr, "layout_height");
            } else {
                this.height = -2;
            }
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(View view, View view2, boolean z, int i);
    }

    private class PassThroughHierarchyChangeListener implements OnHierarchyChangeListener {
        /* access modifiers changed from: private */
        public OnHierarchyChangeListener mOnHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        public void onChildViewAdded(View parent, View child) {
            if (parent == PresetRadioGroup.this && (child instanceof RadioCheckable)) {
                int id = child.getId();
                if (id == -1) {
                    id = ViewUtils.generateViewId();
                    child.setId(id);
                }
                ((RadioCheckable) child).addOnCheckChangeListener(PresetRadioGroup.this.mChildOnCheckedChangeListener);
                PresetRadioGroup.this.mChildViewsMap.put(Integer.valueOf(id), child);
            }
            if (this.mOnHierarchyChangeListener != null) {
                this.mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        public void onChildViewRemoved(View parent, View child) {
            if (parent == PresetRadioGroup.this && (child instanceof RadioCheckable)) {
                ((RadioCheckable) child).removeOnCheckChangeListener(PresetRadioGroup.this.mChildOnCheckedChangeListener);
            }
            PresetRadioGroup.this.mChildViewsMap.remove(Integer.valueOf(child.getId()));
            if (this.mOnHierarchyChangeListener != null) {
                this.mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }

    public PresetRadioGroup(Context context) {
        super(context);
        setupView();
    }

    public PresetRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = 11)
    public PresetRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = 21)
    public PresetRadioGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
        setupView();
    }

    private void parseAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PresetRadioGroup, 0, 0);
        try {
            this.mCheckedId = a.getResourceId(0, -1);
        } finally {
            a.recycle();
        }
    }

    private void setupView() {
        this.mChildOnCheckedChangeListener = new CheckedStateTracker();
        this.mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(this.mPassThroughListener);
    }

    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        if ((child instanceof RadioCheckable) && ((RadioCheckable) child).isChecked()) {
            this.mProtectFromCheckedChange = true;
            if (this.mCheckedId != -1) {
                setCheckedStateForView(this.mCheckedId, false);
            }
            this.mProtectFromCheckedChange = false;
            setCheckedId(child.getId(), true);
        }
        super.addView(child, index, params);
    }

    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        this.mPassThroughListener.mOnHierarchyChangeListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (this.mCheckedId != -1) {
            this.mProtectFromCheckedChange = true;
            setCheckedStateForView(this.mCheckedId, true);
            this.mProtectFromCheckedChange = false;
            setCheckedId(this.mCheckedId, true);
        }
    }

    /* access modifiers changed from: private */
    public void setCheckedId(@IdRes int id, boolean isChecked) {
        this.mCheckedId = id;
        if (this.mOnCheckedChangeListener != null) {
            this.mOnCheckedChangeListener.onCheckedChanged(this, (View) this.mChildViewsMap.get(Integer.valueOf(id)), isChecked, this.mCheckedId);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void clearCheck() {
        check(-1);
    }

    public void check(@IdRes int id) {
        if (id == -1 || id != this.mCheckedId) {
            if (this.mCheckedId != -1) {
                setCheckedStateForView(this.mCheckedId, false);
            }
            if (id != -1) {
                setCheckedStateForView(id, true);
            }
            setCheckedId(id, true);
        }
    }

    /* access modifiers changed from: private */
    public void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = (View) this.mChildViewsMap.get(Integer.valueOf(viewId));
        if (checkedView == null) {
            checkedView = findViewById(viewId);
            if (checkedView != null) {
                this.mChildViewsMap.put(Integer.valueOf(viewId), checkedView);
            }
        }
        if (checkedView != null && (checkedView instanceof RadioCheckable)) {
            ((RadioCheckable) checkedView).setChecked(checked);
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return this.mOnCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }
}
