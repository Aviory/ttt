package com.llc111minutes.gameday.api;

import com.llc111minutes.gameday.model.Pack;
import com.llc111minutes.gameday.util.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Yurii on 2/20/17.
 */

public class Creator {
    private CompositeSubscription compositeSubscription;
    private Callback callback;

    public interface Callback {
        void onError(Throwable e);

        void onCompleted(Pack pack);

        void onFinish();
    }

    public void onStart() {
        compositeSubscription = new CompositeSubscription();

        ApiService service = ApiManager.getClient().create(ApiService.class);
        Subscription subscription =
                Observable.zip(service.getAllTemplatesGroups(), service.getAllOverlays(), service.getHeadlines(),
                        (menuItemTemplates, overlays, headlines) -> {
                            Pack pack = new Pack();
                            pack.setAllOverlays(overlays.getOverlays());
                            pack.setAllTemplatesGroups(menuItemTemplates);
                            pack.setAllHeadlines(HeadlinesAdapter.transform(headlines.getHeadlines()));
                            return pack;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<Pack>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.info(this, "onCompleted");
                                if (callback != null) {
                                    callback.onFinish();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.info(this, e.getMessage());
                                e.printStackTrace();
                                if (callback != null) {
                                    callback.onError(e);
                                }
                            }

                            @Override
                            public void onNext(Pack pack) {
                                pack.setTimeLoad(System.currentTimeMillis());
                                if (callback != null) {
                                    callback.onCompleted(pack);
                                }
                            }
                        });

        compositeSubscription.add(subscription);
    }

    public void onStop() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
        callback = null;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
