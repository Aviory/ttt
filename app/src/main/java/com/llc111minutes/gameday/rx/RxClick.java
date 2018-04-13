package com.llc111minutes.gameday.rx;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.llc111minutes.gameday.interfaces.ViewSubscriptionManager;

import java.util.concurrent.TimeUnit;

import rx.Subscription;

public class RxClick {
    // Delay after click
    private static final long TIMEOUT = 500;

    public static void addDelayOnPressedListener(ViewSubscriptionManager manager, View view) {
        Subscription subscription = RxView.clicks(view)
                .throttleFirst(TIMEOUT, TimeUnit.MICROSECONDS)
                .subscribe(aVoid -> {
                    manager.onClickListener().onClick(view);
                });
        manager.addSubscription(subscription);
    }

    public static void addDelayOnPressedListener(ViewSubscriptionManager manager, final int id) {
        View view = manager.getRootView().findViewById(id);
        Subscription subscription = RxView.clicks(view)
                .throttleFirst(TIMEOUT, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    manager.onClickListener().onClick(
                            manager.getRootView().findViewById(id));
                });
        manager.addSubscription(subscription);
    }
}