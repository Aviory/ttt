package com.llc111minutes.gameday.ui.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.Overlay;
import com.llc111minutes.gameday.model.Template;
import com.llc111minutes.gameday.sticks.team.universal.sticker.Placer;
import com.llc111minutes.gameday.sticks.team.universal.sticker.SmallTouchView;
import com.llc111minutes.gameday.ui.adapter.holders.LayoutViewHolder;
import com.llc111minutes.gameday.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Yurii on 3/6/17.
 */

public abstract class RecyclerSimpleViewAdapter extends RecyclerView.Adapter<LayoutViewHolder>
        implements View.OnClickListener {
    protected int selected = -1;
    protected Bitmap mSourceBitmap;
    protected List<Overlay> overlays;
    protected RecyclerView recyclerView;

    public void setSourceBitmap(Bitmap mSourceBitmap) {
        this.mSourceBitmap = mSourceBitmap;
    }

    public void setOverlays(List<Overlay> overlays) {
        this.overlays = overlays;
    }

    @Override
    public LayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_layout_preview, parent, false);
        return new LayoutViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LayoutViewHolder holder, int position) {
        //holder.llContainer.setTag("" + position);
        holder.llContainer.setOnClickListener(this);
        holder.imageView.setImageBitmap(mSourceBitmap);
        if (isSelected(position)) {
            holder.imageView.setBackgroundResource(R.drawable.shape_red_border);
        } else {
            holder.imageView.setBackgroundResource(0);
        }
        holder.tvName.setText(getTemplateNameFromPosition(position));
        holder.smallTouchView.setTag("" + position);
        if (holder.smallTouchView.getWidth()==0) {
            holder.smallTouchView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                    view.removeOnLayoutChangeListener(this);
                    Integer pos = Integer.parseInt(view.getTag().toString());
                    placeToSmallTouchView(view, pos);
                }
            });
        } else {
            placeToSmallTouchView(holder.smallTouchView, position);
        }
    }

    public abstract boolean isSelected(int position);

    protected abstract Template getTemplateFromPosition(int position);

    protected abstract String getTemplateNameFromPosition(int position);

    private void placeToSmallTouchView(View view, int position) {
        SmallTouchView smallTouchView = (SmallTouchView) view;
        Placer.clearPanel(smallTouchView);
        Template tmpTemplate = getTemplateFromPosition(position);
        Placer.placeTemplate(tmpTemplate, smallTouchView);
        if (tmpTemplate!=null) {
            Placer.setOverlay(overlays, tmpTemplate.getOverlayId(), smallTouchView);
        }
        smallTouchView.notifyView();
    }

    public abstract void clear();

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        LogUtil.info("Click: " + view);
        //selected = Integer.parseInt(view.getTag().toString());
        selected = recyclerView.getChildAdapterPosition(view);
    }

}
