package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.ui.adapter.RecyclerFilterAdapter;
import com.llc111minutes.gameday.util.LogUtil;

public class FilterView extends SubscribeViewFrameLayout {
    private RecyclerView recyclerView;
    private RecyclerFilterAdapter recyclerFilterAdapter;

    public FilterView(Context context) {
        super(context);
        init();
    }

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public int getLayout() {
        return R.layout.view_filter_menu;
    }

    @Override
    public void init() {
        super.init();
        setId(R.id.filter);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //LogUtil.info(this, "newState: " + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerFilterAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /*private void init() {
        inflate(getContext(), R.layout.view_filter_menu, this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //LogUtil.info(this, "newState: " + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerFilterAdapter.notifyDataSetChanged();
                }
            }
        });
    }*/

    /*public Bitmap getBitmap() {
        return filterMenuOpetation.getSourceBitmap();
    }*/

    public void attach(){
        LogUtil.info(this, "onAttachedToWindow");
        setId(R.id.filter);
        if (recyclerFilterAdapter==null) {
            recyclerFilterAdapter = new RecyclerFilterAdapter(filterMenuOpetation.getSourceBitmap());
            recyclerFilterAdapter.setFilterMenuOpetation(filterMenuOpetation);
            recyclerView.setAdapter(recyclerFilterAdapter);
        } else {
            recyclerFilterAdapter.setSourceBitmap(filterMenuOpetation.getSourceBitmap());
        }
        recyclerFilterAdapter.onAttach(getContext());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        attach();
    }

    @Override
    protected void onDetachedFromWindow() {
        LogUtil.info(this, "onDetachedFromWindow");
        if (recyclerFilterAdapter!=null) {
            recyclerFilterAdapter.onDetach();
        }
        super.onDetachedFromWindow();
    }

    public void clearCache(){
        if (recyclerFilterAdapter!=null) {
            recyclerFilterAdapter.onDetach();
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.info(this, "onConfigurationChanged: " + this);
    }
}
