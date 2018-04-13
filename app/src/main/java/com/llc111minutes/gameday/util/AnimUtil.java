package com.llc111minutes.gameday.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by NewUser on 12/12/16.
 */

public class AnimUtil {

    public static void attachAlpha(ViewGroup parent, View view) {
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(view.getResources().getInteger(android.R.integer.config_shortAnimTime));
        parent.addView(view);
        view.startAnimation(fadeIn);
    }

    public static void detachAlpha(View view) {
        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(view.getResources().getInteger(android.R.integer.config_shortAnimTime));
        view.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((ViewGroup) view.getParent()).removeView(view);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static void addView(ViewGroup parent, View child) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup
                .LayoutParams(parent.getWidth(), parent.getHeight());
        child.setLayoutParams(layoutParams);
        TranslateAnimation animate = new TranslateAnimation(parent.getWidth(), 0, 0, 0);
        animate.setDuration(child.getResources().getInteger(android.R.integer.config_mediumAnimTime));
        animate.setFillAfter(true);
        animate.setInterpolator(new AccelerateDecelerateInterpolator());
        parent.addView(child);
        child.startAnimation(animate);
    }

    public static void removeView(ViewGroup parent, View child) {
        TranslateAnimation animate = new TranslateAnimation(0, parent.getWidth(), 0, 0);
        animate.setDuration(parent.getResources().getInteger(android.R.integer.config_mediumAnimTime));
        animate.setFillAfter(true);
        animate.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimListener animListener = new AnimListener(parent, child);
        Callback callback = new Callback() {
            @Override
            public void onStart(ViewGroup parent, View child) {

            }

            @Override
            public void onEnd(ViewGroup parent, View child) {
                parent.removeView(child);
            }
        };
        animListener.setCallback(callback);
        animate.setAnimationListener(animListener);
        child.startAnimation(animate);
    }

    public static class AnimListener implements Animation.AnimationListener {

        private ViewGroup parent;
        private View child;
        private Callback callback;

        public ViewGroup getParent() {
            return parent;
        }

        public void setParent(ViewGroup parent) {
            this.parent = parent;
        }

        public View getChild() {
            return child;
        }

        public void setChild(View child) {
            this.child = child;
        }

        public Callback getCallback() {
            return callback;
        }

        public void setCallback(Callback callback) {
            this.callback = callback;
        }

        public AnimListener(ViewGroup parent, View child) {
            this.parent = parent;
            this.child = child;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            if (callback!=null) callback.onStart(parent, child);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (callback!=null) callback.onEnd(parent, child);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    interface Callback<P extends  ViewGroup, V extends View> {
        void onStart(P parent, V child);
        void onEnd(P parent, V child);
    }

}
