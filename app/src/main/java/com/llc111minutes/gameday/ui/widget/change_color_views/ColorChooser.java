package com.llc111minutes.gameday.ui.widget.change_color_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.util.LogUtil;

public class ColorChooser extends FrameLayout {

    private OnColorChange mListener;
    private CircleView mCircleView;
    private int mColorStart;

    public ColorChooser(Context context) {
        super(context);
        init(null);
    }

    public ColorChooser(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ColorChooser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(param);
        mCircleView = new CircleView(getContext());
    }

    public void setStartColor(int startColor) {
        mColorStart = startColor;
        mCircleView.setOnListener(mColorStart, new CircleView.OnChangeColor() {
            @Override
            public void changeColor(int color) {
                LogUtil.info(this, "Catch COLOR: " + color);
                mListener.setColor(color);
            }
        });
        addView(mCircleView);
        setId(R.id.color_choose);
    }


    public View getActiveChild() {
       for(int i = getChildCount(); i >=0; i--){
           View v = getChildAt(i);
           LogUtil.info(this,"V: "+ v);
           if(v instanceof CustomColorPickerView || v instanceof CircleView){
               return v;
           }
       }
       return null;
    }

    public interface OnColorChange {
        void setColor(int color);
    }

    public void setListener(OnColorChange listener) {
        mListener = listener;
    }
}