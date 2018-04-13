package com.llc111minutes.gameday.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.llc111minutes.gameday.util.TypefaceUtils;

/**
 *  Created by marina on 19.08.16.
 */
public class LatoHeavyTextView extends LatoTextView {
    public LatoHeavyTextView(Context context) {
        super(context);
    }

    public LatoHeavyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LatoHeavyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Typeface getFont() {
        return TypefaceUtils.getLatoHeavyTypeface(getContext());
    }
}
