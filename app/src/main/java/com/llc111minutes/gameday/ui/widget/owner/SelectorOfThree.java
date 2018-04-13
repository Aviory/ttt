package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.llc111minutes.gameday.R;

public class SelectorOfThree extends LinearLayout implements View.OnClickListener{

    private int count = 3;
    private OnItemSelectedListener onItemSelectedListener;
    private int selected = 0;
    private int colorSelected = Color.RED;
    private int colorUnselected = Color.WHITE;
    private TextView[] textViews;

    public SelectorOfThree(Context context) {
        super(context);
    }

    public SelectorOfThree(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectorOfThree(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public String getText(){
        return textViews[selected].getText().toString();
    }

    private void init() {
        inflate(getContext(), R.layout.view_selector_three, this);
        int j = 0;
        textViews = new TextView[count];
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof TextView) {
                textViews[j] = (TextView) getChildAt(i);
                textViews[j].setOnClickListener(this);
                textViews[j].setTextColor(selected == j ? colorSelected : colorUnselected);
                j++;
            }
        }

    }

    @Override
    public void onClick(View view) {
        TextView tv = (TextView) view;
        for (int i = 0; i < textViews.length; i++) {
            if (tv.equals(textViews[i])) {
                selected = i;
            }
        }
        selectEvent(selected);
    }

    private void changeColor(int selected) {
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setTextColor(selected == i ? colorSelected : colorUnselected);
        }
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void selectEvent(int selected){
        changeColor(selected);
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(null, selected);
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(View view, int id);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_black));
        if (onItemSelectedListener!=null) {
            onItemSelectedListener.onItemSelected(this, selected);
        }
    }

    public int getSelected() {
        return selected;
    }
}
