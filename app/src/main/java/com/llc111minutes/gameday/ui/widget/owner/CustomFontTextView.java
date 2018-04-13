package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.util.FontsUtil;

public class CustomFontTextView extends TextView {

    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontView);
            int fontId = a.getInt(R.styleable.FontView_fontName, -1);
            if (fontId<0) {
                fontId = a.getInt(R.styleable.FontView_fontId, 1000);
            }
            Typeface myTypeface = FontsUtil.get(getContext(), fontId);
//            LogUtil.error("fontId="+fontId);
            setTypeface(myTypeface);
            a.recycle();
        } else {
            //apply font by default
            setTypeface(FontsUtil.getDefaultFont(getContext()));
        }
    }

}
