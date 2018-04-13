package com.llc111minutes.gameday.ui.adapter;

import android.view.View;

import com.llc111minutes.gameday.events.Events;
import com.llc111minutes.gameday.interfaces.Callback;
import com.llc111minutes.gameday.model.MenuItemTemplate;
import com.llc111minutes.gameday.model.Template;
import com.llc111minutes.gameday.ui.adapter.holders.LayoutViewHolder;
import com.llc111minutes.gameday.util.LogUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class RecyclerLayoutAdapter extends RecyclerSimpleViewAdapter {
    private List<MenuItemTemplate> mMenuItemTemplateList;
    private Callback<Integer> callback;
    private Template activeTemplate;

    public RecyclerLayoutAdapter() {
        mMenuItemTemplateList = new ArrayList<>();
    }

    @Override
    protected Template getTemplateFromPosition(int position) {
        return mMenuItemTemplateList.get(position).getTemplates().get(0);
    }

    @Override
    protected String getTemplateNameFromPosition(int position) {
        return mMenuItemTemplateList.get(position).getName();
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isSelected(int position) {
        LogUtil.info(this, "" + mMenuItemTemplateList.get(position).getId() + " || " + activeTemplate);
        return activeTemplate != null &&
                mMenuItemTemplateList.get(position).getId().equals(activeTemplate.getTemplateGroupId());
    }

    @Override
    public int getItemCount() {
        return mMenuItemTemplateList.size();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        //notifyDataSetChanged();

        if (callback != null) {
            callback.onResult(selected);
        }
    }

    @Override
    public void onBindViewHolder(LayoutViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.flBack.setVisibility(View.INVISIBLE);
    }

    public void setCallback(Callback<Integer> callback) {
        this.callback = callback;
    }

    public void setmMenuItemTemplateList(List<MenuItemTemplate> mMenuItemTemplateList) {
        this.mMenuItemTemplateList = mMenuItemTemplateList;
    }

    public void setActiveTemplate(Template activeTemplate) {
        this.activeTemplate = activeTemplate;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events.ClearTemplate event) {
        setActiveTemplate(null);
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events.ActionSetTemplate activeTemplate) {
        setActiveTemplate(activeTemplate.getTemplate());
        notifyDataSetChanged();
    }
}
