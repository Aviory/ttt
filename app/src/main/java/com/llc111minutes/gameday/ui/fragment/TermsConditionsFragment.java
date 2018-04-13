package com.llc111minutes.gameday.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.common.BaseFragment;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.ui.activity.MainActivity;
import com.llc111minutes.gameday.util.LogUtil;

import butterknife.BindView;

public class TermsConditionsFragment extends BaseFragment {
    @BindView(R.id.tv_big_message)
    protected WebView mTextView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RxClick.addDelayOnPressedListener(this, R.id.img_back);
        RxClick.addDelayOnPressedListener(this, R.id.tv_cancel);
    }

    @Override
    public void onResume() {
        super.onResume();
        //mTextView.setText(Html.fromHtml(getString(R.string.text_term)));
        mTextView.loadData(getString(R.string.text_term),null, null);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_terms_conditions;
    }

    @Override
    public MainActivity getAct() {
        return (MainActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getAct().onBackPressed();
                break;
            case R.id.tv_cancel:
                LogUtil.info(this, "Cancel");
                getAct().onBackPressed();
                break;
        }
    }
}
