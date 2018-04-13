package com.llc111minutes.gameday.ui.fragment.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContainerInterface;
import com.llc111minutes.gameday.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class DataPickerFragment extends SupportBlurDialogFragment implements View.OnClickListener {
    public static final String TAG_FRAGMENT = "DataPickerFragment";
    /**
     * The field for defining the type of dialog (date = 0, time = 1)
     */
    private int STATE_DIAOG;
    @BindView(R.id.tv_save)
    protected TextView mSaveButton;
    @BindView(R.id.tv_cancel)
    protected TextView mCancelButton;
    @BindView(R.id.tv_mode)
    protected TextView mModeText;
    @BindView(R.id.dp_datePicker)
    protected DatePicker mDataPicker;
    @BindView(R.id.tp_timePicker)
    protected TimePicker mTimePicker;


    public static final String ARG_SELECTED = "selected";
    public static final String RESPONSE_STRING = "response string";

    private ContainerInterface mSelected;

    private final Calendar mCalendar = Calendar.getInstance();
    private SimpleDateFormat mDateFormat = null;


    public DataPickerFragment() {
    }

    public static DialogFragment newInstance(ContainerInterface selected) {
        DataPickerFragment fragment = new DataPickerFragment();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_time_data_picker, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSaveButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        switch (mSelected.getContent().getType()) {
            case DATE:
                initDataDialog();
                break;
            case USER_DATE:
                initDataDialog();
                break;
            case TIME:
                initTimeDialog();
                break;
            case USER_TIME:
                initTimeDialog();
                break;
            default:
        }
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.getAttributes().windowAnimations = R.style.DialogAnimation;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void initTimeDialog() {
        STATE_DIAOG = 1;

        if (mDataPicker.getVisibility() == View.VISIBLE) {
            mDataPicker.setVisibility(View.GONE);
        }
        mTimePicker.setVisibility(View.VISIBLE);
        mModeText.setText(R.string.time);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        } else {
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(minute);
        }
    }

    private void initDataDialog() {
        STATE_DIAOG = 0;
        if (mTimePicker.getVisibility() == View.VISIBLE) {
            mTimePicker.setVisibility(View.GONE);
        }
        mDataPicker.setVisibility(View.VISIBLE);
        mModeText.setText(R.string.date);
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        mDataPicker.init(year, month, day, null);
    }

    public void onDateSet() {
        mDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(mDataPicker.getYear(), mDataPicker.getMonth(), mDataPicker.getDayOfMonth());
        Intent intent = new Intent();
        intent.putExtra(RESPONSE_STRING, mDateFormat.format(newCalendar.getTime()));
        intent.putExtra(ARG_SELECTED, mSelected);

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }


    public void onTimeSet() {
        mDateFormat = (SimpleDateFormat) SimpleDateFormat.getTimeInstance();
        int hour;
        int minutes;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = mTimePicker.getHour();
            minutes = mTimePicker.getMinute();
        } else {
            hour = mTimePicker.getCurrentHour();
            minutes = mTimePicker.getCurrentMinute();
        }
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(Calendar.HOUR_OF_DAY, hour);
        newCalendar.set(Calendar.MINUTE, minutes);

        LogUtil.info(this, "New Time String" + mDateFormat.format(newCalendar.getTime()));

        Intent intent = new Intent();
        intent.putExtra(RESPONSE_STRING, mDateFormat.format(newCalendar.getTime()));
        intent.putExtra(ARG_SELECTED, mSelected);

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                this.dismiss();
                break;
            case R.id.tv_save:
                if (STATE_DIAOG == 0) {
                    onDateSet();
                } else if (STATE_DIAOG == 1) {
                    onTimeSet();
                }
                break;
        }

    }
}
