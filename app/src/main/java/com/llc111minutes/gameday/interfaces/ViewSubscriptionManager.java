package com.llc111minutes.gameday.interfaces;

import android.view.View;

public interface ViewSubscriptionManager extends SubscriptionManager{
    View.OnClickListener onClickListener();
    View getRootView();
}