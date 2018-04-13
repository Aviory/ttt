package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.llc111minutes.gameday.interfaces.FilterMenuOpetation;
import com.llc111minutes.gameday.util.LogUtil;

/**
 * Created by Yurii on 1/6/17.
 */

public class InterfaceFrameOwner extends FrameLayout {
    FilterMenuOpetation filterMenuOpetation;

    public InterfaceFrameOwner(Context context) {
        super(context);
        LogUtil.info("InterfaceFrameOwner");
    }

    public InterfaceFrameOwner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterfaceFrameOwner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        filterMenuOpetation = EditFlowLayout.getFilterMenuOperation(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        filterMenuOpetation = null;
    }

    public FilterMenuOpetation getFilterMenuOpetation() {
        return filterMenuOpetation;
    }

    public void setFilterMenuOpetation(FilterMenuOpetation filterMenuOpetation) {
        this.filterMenuOpetation = filterMenuOpetation;
    }
}