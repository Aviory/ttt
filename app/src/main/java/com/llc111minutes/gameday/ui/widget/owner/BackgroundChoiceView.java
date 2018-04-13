package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.events.Events;
import com.llc111minutes.gameday.interfaces.Callback;
import com.llc111minutes.gameday.interfaces.FilterMenuOpetation;
import com.llc111minutes.gameday.interfaces.ViewSubscriptionManager;
import com.llc111minutes.gameday.model.Overlay;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.ui.adapter.RecyclerOverlayAdapter;
import com.llc111minutes.gameday.ui.widget.change_color_views.ColorChooser;
import com.llc111minutes.gameday.ui.widget.change_color_views.ColorUtil;
import com.llc111minutes.gameday.util.AnimUtil;
import com.llc111minutes.gameday.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Yurii on 3/29/17.
 */

public class BackgroundChoiceView extends RelativeLayout implements ViewSubscriptionManager,
    View.OnClickListener, ColorChooser.OnColorChange {

    private CompositeSubscription compositeSubscription;
    protected boolean isBind = false;
    private RecyclerView recyclerView;
    private FrameLayout flContainer;
    private long oldOverlayId = -1;
    private int oldColor, chooseColor;

    protected TextView tvStyle;
    protected TextView tvColor;

    public BackgroundChoiceView(Context context) {
        super(context);
        init();
    }

    public BackgroundChoiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackgroundChoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflate(getContext(), getLayout(), this);
        setId(R.id.background_choice);
        setBackgroundResource(R.color.color_black);
        setClickable(true);
        setFocusable(true);

        tvStyle = (TextView) findViewById(R.id.tv_first);
        tvStyle.setText("Style");
        tvColor = (TextView) findViewById(R.id.tv_second);
        tvColor.setText("Color");

        tvStyle.setTextColor(Color.RED);

        recyclerView = new RecyclerView(getContext());
        LinearLayoutManager llm = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        flContainer = (FrameLayout) findViewById(R.id.flContainer);
        flContainer.setVisibility(INVISIBLE);
        flContainer.setBackgroundResource(R.color.color_black);
        ((ViewGroup) findViewById(R.id.flForRecycler)).addView(recyclerView);
    }

    private void initRecycler() {
        RecyclerOverlayAdapter overlayAdapter = new RecyclerOverlayAdapter();
        overlayAdapter.setSourceBitmap(getFilterMenuOperation().getSourceBitmap());
        overlayAdapter.setOverlays(getFilterMenuOperation().getPack().getAllOverlays());
        overlayAdapter.setActiveTemplate(getFilterMenuOperation().getTemplate());
        overlayAdapter.setActiveOverlayId(getFilterMenuOperation().getOverlayId());
        Callback<Integer> choiceCallback = new Callback<Integer>() {
            @Override
            public void onResult(Integer position) {
                if (position == 0) {
                    oldOverlayId = -1;
                    getFilterMenuOperation().setOverlay(null);
                } else {
                    getFilterMenuOperation().setOverlay(
                            getFilterMenuOperation().getPack().getAllOverlays().get(position-1));
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        overlayAdapter.setCallback(choiceCallback);
        //adapterLayout.setCallback(this);
        recyclerView.setAdapter(overlayAdapter);
    }

    private FilterMenuOpetation getFilterMenuOperation(){
        return EditFlowLayout.getFilterMenuOperation(this);
    }

    public int getLayout() {
        return R.layout.view_change_layout;
    }

    public void removeMain() {
        AnimUtil.detachAlpha(this);
    }

    @Override
    public OnClickListener onClickListener() {
        return this;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        createSubscripion();
        onStart();
        initRecycler();
        oldOverlayId = getFilterMenuOperation().getOverlayId();
        oldColor = getFilterMenuOperation().getOverlayColorFilter();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unsubscribeAndDestroy();
        onStop();
    }

    @Override
    public void createSubscripion() {
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void unsubscribeAndDestroy() {
        compositeSubscription.unsubscribe();
    }

    @Override
    public void unsubscribe(Subscription subscription) {
        compositeSubscription.remove(subscription);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        LogUtil.info(this, "onRestoreInstanceState");
        if (compositeSubscription == null) {
            createSubscripion();
            onStart();
        }
    }

    public void removeSumSelf() {
        onStop();
        removeMain();
    }

    public void onStart(){
        isBind = true;
        RxClick.addDelayOnPressedListener(this, R.id.tv_second);
        RxClick.addDelayOnPressedListener(this, R.id.tv_first);
        RxClick.addDelayOnPressedListener(this, R.id.vClear);
        RxClick.addDelayOnPressedListener(this, R.id.vAccept);
    }

    public void onStop(){
        isBind = false;
    }

    @Override
    public void onClick(View view) {
        LogUtil.info(this, "View: " + view);
        switch (view.getId()) {
            case R.id.vClear:
                if (oldOverlayId>0) {
                    Overlay overlay = null;
                    for (Overlay o : getFilterMenuOperation().getPack().getAllOverlays()) {
                        if (o.getId() == oldOverlayId) {
                            overlay = o;
                            break;
                        }
                    }
                    if (overlay !=null) {
                        getFilterMenuOperation().setOverlay(overlay);
                    }
                } else {
                    getFilterMenuOperation().setOverlay(null);
                }
                getFilterMenuOperation().setOverlayColorFilter(oldColor);
                EventBus.getDefault().post(new Events.ChangeParentViewStatus(false));
                break;
            case R.id.vAccept:
                removeSumSelf();
                EventBus.getDefault().post(new Events.ChangeParentViewStatus(false));
                if (chooseColor!=0) {
                    getFilterMenuOperation().setOverlayColorFilter(chooseColor);
                    ColorUtil.instance().addNewColor(chooseColor);
                }
                break;
            case R.id.tv_first:
                View v = flContainer.findViewById(R.id.color_choose);
                if (v != null) {
                    flContainer.removeView(v);
                    flContainer.setVisibility(INVISIBLE);
                    tvStyle.setTextColor(Color.RED);
                    tvColor.setTextColor(Color.WHITE);
                }
                break;
            case R.id.tv_second:
                if (flContainer.findViewById(R.id.color_choose) == null) {
                    ColorChooser colorChooser = new ColorChooser(getContext());
                    colorChooser.setStartColor(999999);
                    colorChooser.setBackgroundResource(R.color.color_black);
//                    flContainer.removeAllViews();
                    flContainer.addView(colorChooser);
                    colorChooser.setListener(this);
                    flContainer.setVisibility(VISIBLE);
                }
                tvStyle.setTextColor(Color.WHITE);
                tvColor.setTextColor(Color.RED);
                break;
        }
    }

    @Override
    public void setColor(int color) {
        LogUtil.info(this, "Choose color: " + color);
        chooseColor = color;
        getFilterMenuOperation().setOverlayColorFilter(color);
        /*ColorUtil.instance().addNewColor(color);
        LogUtil.info(this, "Colors 0: " + ColorUtil.instance().getColorFromCollections(0)
                + " color 1: " + ColorUtil.instance().getColorFromCollections(1));*/
    }
}
