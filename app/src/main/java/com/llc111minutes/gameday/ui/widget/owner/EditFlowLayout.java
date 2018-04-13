package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.llc111minutes.gameday.interfaces.FilterMenuOpetation;
import com.llc111minutes.gameday.util.LogUtil;

public class EditFlowLayout extends RelativeLayout {
    private FilterMenuOpetation filterMenuOpetation;

    public EditFlowLayout(Context context) {
        super(context);
    }

    public EditFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFilterMenuOpetation(FilterMenuOpetation filterMenuOpetation) {
        this.filterMenuOpetation = filterMenuOpetation;
    }

    public FilterMenuOpetation getFilterMenuOpetation() {
        return filterMenuOpetation;
    }

    public static FilterMenuOpetation getFilterMenuOperation(View v) {
        while (v!=null) {
            //LogUtil.info(this, "Parent: "+v.getClass().getCanonicalName());
            if (v.getParent() instanceof View) {
                v = (View) v.getParent();
                if (v instanceof EditFlowLayout) {
                    LogUtil.info(v, "EditFlowLayout");
                    FilterMenuOpetation filterMenuOpetation = ((EditFlowLayout)v).getFilterMenuOpetation();
                    LogUtil.info(v, "FilterMenuOperation: " + filterMenuOpetation);
                    return filterMenuOpetation;
                }
            } else {
                v = null;
            }
        }
        return null;
    }

}