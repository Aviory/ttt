package com.llc111minutes.gameday.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.MenuItemTemplate;
import com.llc111minutes.gameday.model.Template;
import com.llc111minutes.gameday.ui.widget.LatoHeavyTextView;
import com.llc111minutes.gameday.ui.widget.LatoTextView;
import com.llc111minutes.gameday.util.UiUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marina on 30.08.16.
 */

public class RecyclerMenuAdapter extends RecyclerView.Adapter<RecyclerMenuAdapter.ViewHolder> {

    List<MenuItemTemplate> mListMenuItem;
    List<Template> mTemplateList;
    public static boolean mVarietyItems;
    Bitmap mBitmap;
    Context mContext;
    int mHeightTemplate;
    private static final String TAG = "MyActivity";

    public RecyclerMenuAdapter(List<MenuItemTemplate> list, List<Template> templateList,
                               Bitmap bitmap, Context context, boolean variety, int heightTemplate) {
        this.mListMenuItem = list;
        this.mTemplateList = templateList;
        this.mBitmap = bitmap;
        this.mContext = context;
        this.mVarietyItems = variety;
        this.mHeightTemplate = heightTemplate;
    }

    @Override
    public RecyclerMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerMenuAdapter.ViewHolder holder, int position) {
        holder.mLayoutTemplate.getLayoutParams().height = 55;//mHeightTemplate
        holder.mLayoutTemplate.getLayoutParams().width = 55;
        holder.mTextMenuItem.getLayoutParams().width = 55;
        holder.mImageMenuItem.setImageBitmap(mBitmap);
        if (!mVarietyItems) {
            holder.mTextMenuItem.setText(mListMenuItem.get(position).getName());
            Picasso.with(mContext).load(Const.BASE_URL +
                    UiUtils.getUriDependingDensityDpi(mListMenuItem.get(position).getPreview(), mContext)).
                    into(holder.mImagePreviewTemplates);
        } else {
            if (position == 0) {
                  holder.mTextMenuItem.setText(mTemplateList.get(position).getName());
                holder.mViewBackGround.setVisibility(View.VISIBLE);
                holder.mTextBack.setVisibility(View.VISIBLE);
                holder.mImageBack.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(Const.BASE_URL +
                        UiUtils.getUriDependingDensityDpi(mTemplateList.get(position).getPreview(), mContext)).
                        into(holder.mImagePreviewTemplates);
                Log.d(TAG, "holder: "+holder.toString());

                Log.d(TAG, "mLayoutTemplate: "+holder.mLayoutTemplate.toString());
            } else {
                holder.mTextMenuItem.setText(mTemplateList.get(position).getName());
                Picasso.with(mContext).load(Const.BASE_URL +
                        UiUtils.getUriDependingDensityDpi(mTemplateList.get(position).getPreview(), mContext)).
                        into(holder.mImagePreviewTemplates);
            }
        }
    }

    @Override
    public int getItemCount() {
        return !mVarietyItems ? mListMenuItem.size() : mTemplateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_menu)
        ImageView mImageMenuItem;
        @BindView(R.id.txt_meni_item)
        LatoTextView mTextMenuItem;
        @BindView(R.id.img_preview_templates)
        ImageView mImagePreviewTemplates;
        @BindView(R.id.view_background)
        View mViewBackGround;
        @BindView(R.id.txt_back)
        LatoHeavyTextView mTextBack;
        @BindView(R.id.img_back)
        ImageView mImageBack;
        @BindView(R.id.frame_layout_template)
        FrameLayout mLayoutTemplate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
