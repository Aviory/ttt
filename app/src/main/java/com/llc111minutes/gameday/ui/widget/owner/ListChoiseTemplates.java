package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.Template;

import java.util.List;

/**
 * Created by Yurii on 3/2/17.
 */

public class ListChoiseTemplates extends HorizontalScrollView implements View.OnClickListener {
    private List<Template> mListTemplate;
    private Bitmap mSourceBitmap;

    public ListChoiseTemplates(Context context) {
        super(context);
        init(context, null);
    }

    public ListChoiseTemplates(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ListChoiseTemplates(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_list_choise_templates, this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setId(R.id.listChoiceTemplates);
    }

    protected void createHolders() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContainer);
        for (int i = 0; i < mListTemplate.size(); i++) {
            View v = inflate(getContext(), R.layout.holder_layout_preview, viewGroup);
            ImageView imgBack = (ImageView) v.findViewById(R.id.imgSource);
            imgBack.setImageBitmap(mSourceBitmap);
            TextView tvName = (TextView) v.findViewById(R.id.tvName);
            tvName.setText(mListTemplate.get(i).getName());
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void setListTemplate(List<Template> mListTemplate) {
        this.mListTemplate = mListTemplate;
    }

    public void setmSourceBitmap(Bitmap mSourceBitmap) {
        this.mSourceBitmap = mSourceBitmap;
    }

    public void remove() {
        ((ViewGroup)getParent()).removeView(this);
    }
}
