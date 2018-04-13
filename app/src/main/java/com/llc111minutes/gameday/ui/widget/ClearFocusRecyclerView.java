package com.llc111minutes.gameday.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * Created by jack on 14.04.17.
 */

public class ClearFocusRecyclerView extends RecyclerView{

    private EditText editText;

    public ClearFocusRecyclerView(Context context) {
        this(context, null);
    }

    public ClearFocusRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearFocusRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        //editText = new EditText(context);
        //addView(editText);
        setFocusable(true);
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (editText.isFocused()) {
            editText.clearFocus();
            requestFocus();
        }
        return super.onTouchEvent(e);
    }
}
