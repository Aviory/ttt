package com.llc111minutes.gameday.ui.fragment.dialog;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContainerInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

/**
 * This class is a dialog for editing text  of the template
 */
public class EditTextDialogFragment extends SupportBlurDialogFragment implements View.OnClickListener {

    public static final String ARG_SELECTED = "container";
    public static final String TAG_FRAGMENT = "EditTextFragment";
    public static final String RESPONSE_STRING = "response string";
    @BindView(R.id.tv_save)
    protected TextView mButtonSaveView;
    @BindView(R.id.tv_cancel)
    protected TextView mButtonCancelView;
    @BindView(R.id.et_edit_field)
    protected EditText mTextField;

    private ContainerInterface mSelected;

    public EditTextDialogFragment() {
    }

    public static EditTextDialogFragment newInstance(ContainerInterface selected) {
        EditTextDialogFragment fragment = new EditTextDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SELECTED, selected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelected = getArguments().getParcelable(ARG_SELECTED);
        }
    }

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 10;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_edit_text, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        mTextField.setText(mSelected.getContent().getData());
        mTextField.setSelection(mTextField.getText().length());

    }

    private void initListeners() {
        mButtonCancelView.setOnClickListener(this);
        mButtonSaveView.setOnClickListener(this);
        mTextField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        Window window = getDialog().getWindow();
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        ColorDrawable dialogColor = new ColorDrawable(Color.BLACK);
        dialogColor.setAlpha(100);
        window.setBackgroundDrawable(dialogColor);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.TOP;
        window.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                textChanged(String.valueOf(mTextField.getText()));
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    private void textChanged(String s) {

        Intent intent = new Intent();
        intent.putExtra(RESPONSE_STRING, s);
        intent.putExtra(ARG_SELECTED, mSelected);

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }

    public interface OnTextLayerCallback {
        void textChanged(@NonNull String text);
    }
}