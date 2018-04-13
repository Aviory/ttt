package com.llc111minutes.gameday.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by marina on 19.08.16.
 */
public abstract class LatoTextView extends android.support.v7.widget.AppCompatTextView {
    public LatoTextView(Context context) {
        super(context);
        init();
    }

    public LatoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected abstract Typeface getFont();

    private void init() {
        setTypeface(getFont());
    }
}
