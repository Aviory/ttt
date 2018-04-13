package com.llc111minutes.gameday.ui.widget.change_color_views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.llc111minutes.gameday.R;

public class CustomColorPickerView extends LinearLayout implements BackgroundQuad.OnColorGradientChangedListener, CustomColorPicker.OnColorChangedListener {

    private CircleView.OnChangeColor mListener;
    BackgroundQuad backgroundQuad;
    CustomColorPicker customColorPicker;

    private int mColor = Color.YELLOW;

    public CustomColorPickerView(Context context) {
        this(context, null);
    }

    public CustomColorPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomColorPickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        inflate(context, R.layout.view_color_picker,this);
        setOrientation(VERTICAL);
        setBackgroundResource(R.color.color_black);
        backgroundQuad = (BackgroundQuad) findViewById(R.id.custom_background);
        customColorPicker = (CustomColorPicker) findViewById(R.id.custom_color_picker);
        backgroundQuad.setListener(this);
        customColorPicker.setListener(this);

        backgroundQuad.setImageColor(backgroundQuad.pixel);
        customColorPicker.setColor(mColor);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        /*backgroundQuad = new BackgroundQuad(getContext());
        customColorPicker = new CustomColorPicker((getContext()));
        addView(backgroundQuad);
        addView(customColorPicker);
        backgroundQuad.setListener(this);
        customColorPicker.setListener(this);

        backgroundQuad.setImageColor(backgroundQuad.pixel);
        customColorPicker.setColor(mColor);*/
    }

    @Override
    public void colorGradientChanged(int color) {
        notifyTopListener(color);

    }

    @Override
    public void colorChanged(int color) {
        backgroundQuad.setMyColor(color);
        notifyTopListener(color);
    }

    /**
     * return color in CircleView
     *
     * @param color - Color
     */
    private void notifyTopListener(int color) {
        if (mListener != null) mListener.changeColor(color);
    }

    /**
     * Definition listener to transfer color to the top of the hierarchy
     *
     * @param listener the touch listener to attach to this view
     */
    public void setListener(CircleView.OnChangeColor listener) {
        mListener = listener;
    }
}
