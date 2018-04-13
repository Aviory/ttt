package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.enums.FILTER;
import com.llc111minutes.gameday.ui.adapter.RecyclerEditAdapter;
import com.llc111minutes.gameday.util.AnimUtil;
import com.llc111minutes.gameday.util.EditFilterUtils;
import com.llc111minutes.gameday.util.LogUtil;

public class AdjustView extends SubscribeViewFrameLayout {
    private RecyclerView recyclerView;
    private RecyclerEditAdapter recyclerEditAdapter;

    public AdjustView(Context context) {
        super(context);
        init();
    }

    public AdjustView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AdjustView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public int getLayout() {
        return R.layout.view_adjust_menu;
    }

    @Override
    public void init() {
        super.init();
        setId(R.id.adjustView);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerEditAdapter = new RecyclerEditAdapter();
        recyclerEditAdapter.setViewSubscriptionManager(this);
        recyclerView.setAdapter(recyclerEditAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llFilter:
                LogUtil.info(this, "Position: " + view.getTag().toString());
                addSeekBar(Integer.parseInt(view.getTag().toString()));
                break;
            case R.id.imgApply:
                recyclerEditAdapter.notifyDataSetChanged();
        }
    }

    private void addSeekBar(int filterId) {
        FilterSeekBar filterSeekBar = new FilterSeekBar(getContext());
        FILTER filter = FILTER.getFilterById(filterId);

        EditFilterUtils.setCurrentFilter(filterId);

        if (filterMenuOpetation.getFilter(filter)!=null) {
            filter = filterMenuOpetation.getFilter(filter);
        } else {
            filterMenuOpetation.addFilter(filter);
        }
        filterSeekBar.setFilter(filter, recyclerEditAdapter);
        AnimUtil.addView(((ViewGroup)getParent().getParent()), filterSeekBar);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setId(R.id.adjustView);
        if (recyclerEditAdapter == null) {
            recyclerEditAdapter = new RecyclerEditAdapter();
            recyclerEditAdapter.setViewSubscriptionManager(this);
            recyclerView.setAdapter(recyclerEditAdapter);
        } else {
            recyclerEditAdapter.setViewSubscriptionManager(this);
            recyclerEditAdapter.notifyDataSetChanged();
        }
    }

}


