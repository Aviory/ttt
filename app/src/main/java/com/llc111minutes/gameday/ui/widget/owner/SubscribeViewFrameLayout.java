package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.llc111minutes.gameday.interfaces.ViewSubscriptionManager;
import com.llc111minutes.gameday.util.LogUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Yurii on 12/30/16.
 */

public abstract class SubscribeViewFrameLayout extends InterfaceFrameOwner implements View.OnClickListener,
        ViewSubscriptionManager {
    private CompositeSubscription compositeSubscription;
    protected boolean isBind = false;

    public SubscribeViewFrameLayout(Context context) {
        super(context);
        LogUtil.info("SubscribeViewFrameLayout");
    }

    public SubscribeViewFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SubscribeViewFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract int getLayout();

    public void init(){
        inflate(getContext(), getLayout(), this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        createSubscripion();
        onStart();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unsubscribeAndDestroy();
        onStop();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public OnClickListener onClickListener() {
        return this;
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
        }
        onStart();
    }

    public void removeSumSelf() {
        onStop();
        removeMain();
    }

    public void removeMain(){
        ((ViewGroup)getParent()).removeView(this);
    }

    public void onStart(){
        isBind = true;
    }
    public void onStop(){
        isBind = false;
    }
}
