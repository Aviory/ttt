package com.llc111minutes.gameday.common;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.interfaces.ViewSubscriptionManager;
import com.llc111minutes.gameday.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment implements ViewSubscriptionManager,
        View.OnClickListener {
    protected String TAG = ""+getClass().getName();
    private Unbinder unbinder;
    public CompositeSubscription compositeSubscription;

    public BaseFragment() {
        setArguments(new Bundle());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        createSubscripion();
        return view;
    }

    public abstract int getLayout();

    public abstract <T extends BaseActivity> T getAct();

    public static Fragment getFragmentByTag(AppCompatActivity activity, String tag){
        Fragment fragment = activity.getSupportFragmentManager()
                .findFragmentByTag(tag);
        return fragment;
    }

    public static<T> Fragment addFragment(BaseFragment baseFragment
            , Class<T> clazz
            , @Nullable Bundle args
            , boolean backstack){
        Fragment fragment = baseFragment.getChildFragmentManager().findFragmentByTag(clazz.getCanonicalName());
        if (fragment==null) {
            try {
                Constructor<?> cons = clazz.getConstructor();
                fragment = (Fragment) cons.newInstance();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }

        if (fragment==null) return null;

        FragmentTransaction transaction = baseFragment.getChildFragmentManager()
                .beginTransaction();

        if (backstack) {
            transaction.addToBackStack(clazz.getCanonicalName());
        }
        transaction
                .setCustomAnimations(R.anim.slide_in_up, R.anim.stay
                        , R.anim.stay, R.anim.slide_out_down)
                .addToBackStack(null)
                //.add(R.id.container_in_fragment, fragment, fragment.getClass().getCanonicalName())
                .commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unsubscribeAndDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveValue(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreValue(getArguments());
    }

    @Override
    public void onPause() {
        super.onPause();
        saveValue(getArguments());
    }

    protected void saveValue(Bundle outState){};
    protected void restoreValue(Bundle outState){};

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
        compositeSubscription = null;
    }

    @Override
    public View.OnClickListener onClickListener() {
        return this;
    }

    @Override
    public View getRootView() {
        return getView();
    }

    @Override
    public void unsubscribe(Subscription subscription) {
        if (subscription!=null && !subscription.isUnsubscribed()) {
            compositeSubscription.remove(subscription);
            subscription.unsubscribe();
        }
    }

    public File createImageFile(String name) throws IOException {
        // Create an image file name
        return FileUtil.createImageFile(getActivity(), name);
    }

    public String saveBitmapToFile(Bitmap bitmap, String name) throws IOException {
        File file = createImageFile(name);
        FileOutputStream out = null;
        out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);

       /* Pair<FileOutputStream, Bitmap> pair = new Pair<>(out, bitmap);
        Observable.just(pair)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .doOnNext(pair1 -> {
                })
                .subscribe(new Subscriber<Pair<FileOutputStream, Bitmap>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Pair<FileOutputStream, Bitmap> pair) {
                        pair.second.compress(Bitmap.CompressFormat.JPEG, 50, pair.first);
                    }
                });*/

        return file.getAbsolutePath();
    }

    public void onBack() {
        getAct().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View view) {

    }
}