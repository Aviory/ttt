package com.llc111minutes.gameday.ui.adapter.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.sticks.team.universal.sticker.SmallTouchView;
import com.llc111minutes.gameday.ui.widget.owner.CustomFontTextView;

/**
 * Created by Yurii on 3/6/17.
 */

public class LayoutViewHolder extends RecyclerView.ViewHolder {
    public CustomFontTextView tvName;
    public View flBack;
    public ImageView imageView;
    public SmallTouchView smallTouchView;
    public View llContainer;

    public LayoutViewHolder(View v) {
        super(v);
        tvName = (CustomFontTextView) v.findViewById(R.id.tvName);
        flBack = v.findViewById(R.id.flBack);
        smallTouchView = (SmallTouchView) v.findViewById(R.id.preview);
        imageView = (ImageView) v.findViewById(R.id.imgSource);
        llContainer = v.findViewById(R.id.llContainer);
    }
}
