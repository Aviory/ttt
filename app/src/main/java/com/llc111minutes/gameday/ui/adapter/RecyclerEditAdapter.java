package com.llc111minutes.gameday.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.interfaces.ViewSubscriptionManager;
import com.llc111minutes.gameday.model.EditFilter;
import com.llc111minutes.gameday.model.enums.FILTER;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.ui.widget.LatoBlackTextView;
import com.llc111minutes.gameday.util.EditFilterUtils;
import com.llc111minutes.gameday.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerEditAdapter extends RecyclerView.Adapter<RecyclerEditAdapter.ViewHolder> {
    private ViewSubscriptionManager viewSubscriptionManager;

    public RecyclerEditAdapter() {
    }

    public void setViewSubscriptionManager(ViewSubscriptionManager viewSubscriptionManager) {
        this.viewSubscriptionManager = viewSubscriptionManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageFilter.setImageResource(FILTER.getFilterById(position).getIconId());
        holder.textFilter.setText(FILTER.getFilterById(position).getNameId());
        holder.mView.setTag("" + position);
        RxClick.addDelayOnPressedListener(viewSubscriptionManager, holder.mView);

        if(FILTER.getFilterById(position).getCurrent()==Float.MIN_VALUE || FILTER.getFilterById(position).getCurrent()==FILTER.getFilterById(position).getStart()){
            holder.vCircle.setVisibility(View.INVISIBLE);
        }else{
            holder.vCircle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return FILTER.values().length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        @BindView(R.id.img_filter)
        ImageView imageFilter;
        @BindView(R.id.txt_filter)
        LatoBlackTextView textFilter;
        @BindView(R.id.v_circle_filter)
        View vCircle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }
}
