package com.llc111minutes.gameday.sticks.team.universal.sticker;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.llc111minutes.gameday.util.LogUtil;

/**
 * Created by Yurii on 2/15/17.
 */

public class DemoParentView extends ParentView {
    private float borderWidth = -1f;

    public DemoParentView(Context context) {
        super(context);
    }

    public DemoParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void drawParentBorder(Canvas canvas) {
        if (borderWidth>0) {
            //super.drawParentBorder(canvas);
            float oldWidth = paintBorder.getStrokeWidth();
            LogUtil.info(this, "oldWidth: " + borderWidth);
            paintBorder.setStrokeWidth(borderWidth);
            drawPointOnRect(canvas, paintBorder, getLeft(), getTop(), getRight(), getBottom());
            canvas.drawRect(getLeft(), getTop(), getRight()-oldWidth, getBottom()-oldWidth, paintBorder);
            paintBorder.setStrokeWidth(oldWidth);
        }
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }
}