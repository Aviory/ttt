package com.llc111minutes.gameday.ui.fragment.dialog;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.ui.adapter.PagerView;
import com.llc111minutes.gameday.ui.widget.LatoBlackTextView;
import com.llc111minutes.gameday.ui.widget.owner.CustomFontTextView;
import com.llc111minutes.gameday.util.Storage;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

/**
 * This class is a dialog for editing text  of the template
 */
public class EditingTipsDialogFragment extends SupportBlurDialogFragment {
    @BindView(R.id.tvEnter_tips)
    protected LatoBlackTextView tvEnter_tips;
    @BindView(R.id.tvSkip_tips)
    protected CustomFontTextView tvSkip_tips;
    @BindView(R.id.llIndication_tips)
    protected LinearLayout llIndication_tips;
    @BindView(R.id.pager)
    ViewPager mViewPager;

    public EditingTipsDialogFragment() {
    }

    public static EditingTipsDialogFragment newInstance() {
        return new EditingTipsDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_editing_tips, container, false);
        ButterKnife.bind(this, rootView);
        rootView.findViewById(R.id.container_editing_tips).setBackgroundResource(R.color.transparent);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
      }

    private void initListeners() {
        tvSkip_tips.setOnClickListener(v -> dismiss());
        tvEnter_tips.setOnClickListener(v -> {
            dismiss();
            Storage.instance(getContext()).setFirstOpeningFragment();
        });
        mViewPager.setAdapter(new PagerView(getContext()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 2:
                        tvSkip_tips.setAlpha(1 - positionOffset);
                        tvEnter_tips.setAlpha(positionOffset);
                        break;
                }

                for (int i = 0; i < llIndication_tips.getChildCount(); i++) {
                    float offset = 1 - positionOffset;
                    if (offset < 0.4F) {
                        offset = 0.4F;
                    }
                    if (i == position) {
                        llIndication_tips.getChildAt(i).setAlpha(offset);
                    } else {
                        llIndication_tips.getChildAt(i).setAlpha(0.4f);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    tvSkip_tips.setVisibility(View.INVISIBLE);
                    tvEnter_tips.setVisibility(View.VISIBLE);
                } else {
                    tvSkip_tips.setVisibility(View.VISIBLE);
                    tvEnter_tips.setVisibility(View.INVISIBLE);
                }
                for (int i = 0; i < llIndication_tips.getChildCount(); i++) {
                    if (i == position) {
                        llIndication_tips.getChildAt(i).setAlpha(1F);
                    } else {
                        llIndication_tips.getChildAt(i).setAlpha(0.4F);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
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
}