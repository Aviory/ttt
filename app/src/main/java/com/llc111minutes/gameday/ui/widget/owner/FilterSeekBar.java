package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.enums.FILTER;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.ui.adapter.RecyclerEditAdapter;
import com.llc111minutes.gameday.ui.widget.CustomSeekBar;
import com.llc111minutes.gameday.util.AnimUtil;
import com.llc111minutes.gameday.util.LogUtil;

public class FilterSeekBar extends SubscribeViewFrameLayout {
    private CustomSeekBar seekBar;
    private FILTER filter;
    private float oldFilterData;
    private RecyclerEditAdapter recyclerEditAdapter;

    public FilterSeekBar(Context context) {
        super(context);
        init();
    }

    public FilterSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FilterSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void init() {
        super.init();
        seekBar = (CustomSeekBar) findViewById(R.id.custom_seekbar_filter);
    }

    @Override
    public int getLayout() {
        return R.layout.view_seekbar_filter_custom;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mInit();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        mInit();
    }

    private void mInit() {
        setId(R.id.filterSeekBar);
        LogUtil.info(this, "" + getRootView());
        RxClick.addDelayOnPressedListener(this, R.id.imgApply);
        RxClick.addDelayOnPressedListener(this, R.id.imgCancel);

        filterMenuOpetation.subscribeSeekBarChange(seekBar, filter);
    }

    @Override
    protected void onDetachedFromWindow() {
        filterMenuOpetation.unsubscribeSeekBarChange();
        if (filter.getCurrent() == filter.getStart()) {
            if (filterMenuOpetation.isFilterAdded(filter)) {
                filterMenuOpetation.removeFilter(filter);
            }
        }
        filterMenuOpetation.notifyFilters();
        if(recyclerEditAdapter!=null){
            recyclerEditAdapter.notifyDataSetChanged();
        }
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgApply:

                break;
            case R.id.imgCancel:
                filter.setCurrent(oldFilterData);
        }
        AnimUtil.removeView((ViewGroup) getParent(), this);
    }

    public void setFilter(FILTER filter, RecyclerEditAdapter recyclerEditAdapter) {
        this.recyclerEditAdapter = recyclerEditAdapter;
        this.filter = filter;
        this.oldFilterData = filter.getCurrent();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed && right != 0) {
            if (filter.getCurrent() == Float.MIN_VALUE) {
                filter.setCurrent(filter.getStart());
            }
            int progress = getProgressFromFilter(filter.getCurrent());
            int anchor = getProgressFromFilter(filter.getStart());
            seekBar.setProgress(progress);
            seekBar.setAnchor(anchor);
            seekBar.lineInvalidate(progress);
        }
    }

    private int getProgressFromFilter(float param) {

        float difference = filter.getMax() - filter.getMin();
        float percent = difference / 100;
        float p = (Math.abs(param - filter.getMin())) / percent;
        return (int) p;
    }

}
