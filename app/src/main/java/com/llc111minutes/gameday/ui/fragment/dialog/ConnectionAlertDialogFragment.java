package com.llc111minutes.gameday.ui.fragment.dialog;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.util.TypefaceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This class is a dialog for filling in the text and / or image of the template
 */
public class ConnectionAlertDialogFragment extends DialogFragment {
    public static final String ARG_SELECTED = "select container";
    private OnExitErrorConnection mListener;
    @BindView(R.id.tv_brand_name)
    TextView mBrandName;

    public ConnectionAlertDialogFragment() {
    }

    public static ConnectionAlertDialogFragment newInstance() {
        ConnectionAlertDialogFragment fragment = new ConnectionAlertDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_connection, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        Window window = getDialog().getWindow();
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.TOP;
        window.setAttributes(lp);
        applySpanInText(mBrandName);
    }

    private void applySpanInText(TextView view) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(view.getText());
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = getResources().getColor(R.color.red, null);
        } else {
            color = getResources().getColor(R.color.red);
        }
        spannable.setSpan(new ForegroundColorSpan(color), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setTypeface(TypefaceUtils.getLatoBlackTypeface(getContext()));
        view.setText(spannable);
    }

    @OnClick(R.id.bt_try_again)
    protected void onClick() {
        if (mListener != null) {
            mListener.reConnect();
        }
        dismiss();
    }

    public void setReconnectListener(OnExitErrorConnection listener) {
        mListener = listener;
    }

    public interface OnExitErrorConnection {
        void reConnect();
    }
}