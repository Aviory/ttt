package com.llc111minutes.gameday.ui.adapter;

import android.view.View;

import com.llc111minutes.gameday.events.Events;
import com.llc111minutes.gameday.interfaces.Callback;
import com.llc111minutes.gameday.model.Template;
import com.llc111minutes.gameday.sticks.team.universal.sticker.Placer;
import com.llc111minutes.gameday.sticks.team.universal.sticker.SmallTouchView;
import com.llc111minutes.gameday.ui.adapter.holders.LayoutViewHolder;
import com.llc111minutes.gameday.util.LogUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RecyclerOverlayAdapter extends RecyclerSimpleViewAdapter {

    private Template activeTemplate;
    private long overlayId = -1;
    private Callback<Integer> callback;

    @Override
    public boolean isSelected(int position) {
        if (overlayId == -1 && position ==0 ) return true;
        //if (overlayId == -1 && position > 0) return false;
        if (position == 0) return false;

        return overlayId == overlays.get(position-1).getId();
    }

    @Override
    protected Template getTemplateFromPosition(int position) {
        return activeTemplate;
    }

    @Override
    protected String getTemplateNameFromPosition(int position) {
        if (position == 0) return "None";
        return overlays.get(position-1).getName();
    }

    @Override
    public void clear() {
        overlays = null;
    }

    @Override
    public int getItemCount() {
        return overlays.size()+1;
    }

    public void setActiveTemplate(Template activeTemplate) {
        this.activeTemplate = activeTemplate;
    }

    @Override
    public void onBindViewHolder(LayoutViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder.smallTouchView.getWidth() == 0) {
            holder.smallTouchView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    place(holder.smallTouchView, position);
                }
            });
        } else {
            place(holder.smallTouchView, position);
        }
    }

    private void place(SmallTouchView smallTouchView, int position) {
        if (position == 0) {
            Placer.setOverlay(smallTouchView, null);
            smallTouchView.invalidate();
        } else {
            Placer.setOverlay(smallTouchView, overlays.get(position-1));
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        LogUtil.info(this, "Selected position: " + selected);
        if (selected == 0) {
            this.overlayId = -1;
        } else {
            this.overlayId = overlays.get(selected-1).getId();
        }
        if (callback!=null) {
            callback.onResult(selected);
        }
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events.ActionSetTemplate actionSetTemplate) {
        setActiveTemplate(actionSetTemplate.getTemplate());
        notifyDataSetChanged();
    }

    public void setCallback(Callback<Integer> callback) {
        this.callback = callback;
    }

    public void setActiveOverlayId(int activeOverlayId) {
        this.overlayId = activeOverlayId;
    }
}
