package com.llc111minutes.gameday.ui.adapter;

import android.view.View;

import com.llc111minutes.gameday.events.Events;
import com.llc111minutes.gameday.interfaces.Callback;
import com.llc111minutes.gameday.interfaces.Callback2;
import com.llc111minutes.gameday.model.Template;
import com.llc111minutes.gameday.ui.adapter.holders.LayoutViewHolder;
import com.llc111minutes.gameday.util.LogUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yurii on 3/6/17.
 */

public class RecyclerLayoutFlowAdapter extends RecyclerSimpleViewAdapter {

    private ArrayList<Template> templates;
    private Callback<Integer> callback;
    private Callback2<RecyclerLayoutFlowAdapter, Integer> callbackBind;
    private int index = -1;
    private String name;
    private int idActiveTemplate = -1;

    public RecyclerLayoutFlowAdapter() {
        templates = new ArrayList<>();
    }

    public void setTemplates(List<Template> templates) {
        this.templates = new ArrayList<>(templates);
        this.templates.add(0, templates.get(0));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCallbackBind(Callback2<RecyclerLayoutFlowAdapter, Integer> callbackBind) {
        this.callbackBind = callbackBind;
    }

    public void setIndex(int index) {
        this.index = index;
        LogUtil.info("Index: " + index);
    }

    @Override
    public void onBindViewHolder(LayoutViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //LogUtil.info(this, "Bind position: " + position + " time: "+System.currentTimeMillis());
        if (position == 0) {
            holder.flBack.setVisibility(View.VISIBLE);
        } else {
            holder.flBack.setVisibility(View.INVISIBLE);
        }

        if (callbackBind!=null && position==index) {
            if (holder.smallTouchView.getWidth() > 0 && holder.smallTouchView.getHeight()>0) {
                if (callbackBind!=null) {
                    callbackBind.onResult(RecyclerLayoutFlowAdapter.this, position);
                    callbackBind = null;
                }
            } else {
                holder.smallTouchView.setCallback2(new Callback2() {
                    @Override
                    public void onResult(Object o, Object o2) {
                        if (callbackBind != null) {
                            callbackBind.onResult(RecyclerLayoutFlowAdapter.this, position);
                            callbackBind = null;
                        }
                    }

                    @Override
                    public void onError(Object o, Throwable e) {

                    }
                });
            }
        }
    }

    public boolean isSelected(int position){
        if (position == 0) return false;
        return selected > 0 && selected == position ||
                idActiveTemplate > 0 && idActiveTemplate == templates.get(position).getId();
    }

    public void setTemplates(ArrayList<Template> templates) {
        this.templates = templates;
    }

    @Override
    protected Template getTemplateFromPosition(int position) {
        return templates.get(position);
    }

    @Override
    protected String getTemplateNameFromPosition(int position) {
        if (position == 0) return name;
        else return templates.get(position).getName();
    }

    @Override
    public void clear() {
        templates.clear();
        selected = -1;
        idActiveTemplate = -1;
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }

    public void setCallback(Callback<Integer> callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        notifyDataSetChanged();
        if (callback != null) {
            callback.onResult(selected);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events.ClearTemplate event) {
        selected = -1;
        idActiveTemplate = -1;
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events.ActionSetTemplate actionSetTemplate) {
        idActiveTemplate = actionSetTemplate.getTemplate().getId();
        notifyDataSetChanged();
    }

    public void setIdActiveTemplate(int idActiveTemplate) {
        this.idActiveTemplate = idActiveTemplate;
    }
}
