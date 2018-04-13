package com.llc111minutes.gameday.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.common.BaseActivity;
import com.llc111minutes.gameday.ui.fragment.OnboardingFragment;
import com.llc111minutes.gameday.util.LogUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.fabric.sdk.android.Fabric;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.viewPager)
    protected ViewPager viewPager;
    @BindView(R.id.tvSkip)
    protected View tvSkip;
    @BindView(R.id.tvEnter)
    protected View tvEnter;
    @BindView(R.id.llIndication)
    protected LinearLayout llIndication;
    @BindView(R.id.flContent)
    protected FrameLayout flContent;

    private CompositeSubscription subscription;
    private Subscription publishSubject;
    private ScreenSlidePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
    }

    @Override
    public SplashActivity getAct() {
        return this;
    }

    @Override
    public int getLayout() {
        if (getStorage().isFirstOpeningApp()) {
            return R.layout.acivity_splash;
        } else {
            return -1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscription = new CompositeSubscription();

        if (getStorage().isFirstOpeningApp()) {
            startViewPager();
            setOnClickListeners();
            coordinatorLayout.setBackgroundResource(R.drawable.image_background);
        }

        Subscriber subscriber = new Subscriber() {
            @Override
            public void onCompleted() {
                LogUtil.info(this, "onCompleted");
                if (!isActivityPaused()) {
                    LogUtil.info(this, "Activity is no paused.");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                LogUtil.info(this, "onNext, "+o);
                if (getStorage().isFirstOpeningApp()) {
                    flContent.setVisibility(View.VISIBLE);
                } else {
                    startNextActivity();
                }
            }
        };

        publishSubject = PublishSubject.timer(Const.SPLASH_DELAY_SECOND, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        subscription.add(publishSubject);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (subscription!=null) {
            subscription.unsubscribe();
        }
    }

    private void setOnClickListeners() {
        tvEnter.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
    }

    private void startViewPager(){
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 2:
                        tvSkip.setAlpha(1-positionOffset);
                        tvEnter.setAlpha(positionOffset);
                        break;
                }

                for (int i = 0; i < llIndication.getChildCount(); i++) {
                    float offset = 1-positionOffset;
                    if (offset<0.4F) {
                        offset = 0.4F;
                    }
                    if (i==position) {
                        llIndication.getChildAt(i).setAlpha(offset);
                    } else {
                        llIndication.getChildAt(i).setAlpha(0.4f);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 2:
                        tvSkip.setVisibility(View.VISIBLE);
                        tvEnter.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        tvSkip.setVisibility(View.INVISIBLE);
                        tvEnter.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < llIndication.getChildCount(); i++) {
                    if (i==position) {
                        llIndication.getChildAt(i).setAlpha(1F);
                    } else {
                        llIndication.getChildAt(i).setAlpha(0.4F);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvEnter:
            case R.id.tvSkip:
                getStorage().setFirstOpeningApp();
                startNextActivity();
        }
    }

    private void startNextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        startActivity(intent);
        finish();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return OnboardingFragment.instance(R.string.first_splash_sentences,
                        R.drawable.one_logo);
                case 1: return OnboardingFragment.instance(R.string.second_splash_sentences,
                        R.drawable.one_logo);
                case 2: return OnboardingFragment.instance(R.string.third_splash_sentences,
                        R.drawable.one_logo);
                case 3: return OnboardingFragment.instance(R.string.fourth_splash_sentences,
                        -1);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}