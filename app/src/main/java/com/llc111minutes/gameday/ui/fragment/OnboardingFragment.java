package com.llc111minutes.gameday.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.common.BaseActivity;
import com.llc111minutes.gameday.common.BaseFragment;
import com.llc111minutes.gameday.ui.widget.LatoMediumTextView;

import butterknife.BindView;

public class OnboardingFragment extends BaseFragment {

    private final static String RES_TXT = "com.matchday.android.ui.fragment.txt";
    private final static String RES_IMG = "com.matchday.android.ui.fragment.img";

    @BindView(R.id.tvDescription)
    protected LatoMediumTextView tvDescription;
    @BindView(R.id.img)
    protected ImageView img;
    @BindView(R.id.spaceOne)
    protected View spaceOne;
    @BindView(R.id.spaceTwo)
    protected View spaceTwo;
    @BindView(R.id.spaceOneGone)
    protected View spaceOneGone;
    @BindView(R.id.spaceTwoGone)
    protected View spaceTwoGone;

    public static OnboardingFragment instance(int txtRes, int imgRes) {
        Bundle args = new Bundle();
        args.putInt(RES_TXT, txtRes);
        args.putInt(RES_IMG, imgRes);
        OnboardingFragment onboardingFragment = new OnboardingFragment();
        onboardingFragment.setArguments(args);
        return onboardingFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null) {
            tvDescription.setText(getArguments().getInt(RES_TXT, R.string.create));
            int res = getArguments().getInt(RES_IMG, -1);
            if (res>0) {
                img.setImageResource(res);
            } else {
                img.setVisibility(View.GONE);
                spaceOne.setVisibility(View.GONE);
                spaceTwo.setVisibility(View.GONE);
                spaceOneGone.setVisibility(View.VISIBLE);
                spaceTwoGone.setVisibility(View.VISIBLE);
                tvDescription.setTextSize(30);
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_onboarding_new;
    }

    @Override
    public BaseActivity getAct() {
        return (BaseActivity) getActivity();
    }

}
