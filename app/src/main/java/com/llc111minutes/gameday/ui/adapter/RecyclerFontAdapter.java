package com.llc111minutes.gameday.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.ui.widget.owner.ChangeFontsView;
import com.llc111minutes.gameday.ui.widget.owner.CustomFontTextView;
import com.llc111minutes.gameday.util.FontsUtil;
import com.llc111minutes.gameday.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

public class RecyclerFontAdapter extends RecyclerView.Adapter<RecyclerFontAdapter.FontViewHolder> {
    private ChangeFontsView mChangeFontsView;
    private FontsUtil.Font mFont;
    private RecyclerView mRecyclerView;
    private HashMap<FontsUtil.Font,Boolean> enableFonts;

    public RecyclerFontAdapter(ChangeFontsView changeFontsView) {
        this.mChangeFontsView = changeFontsView;
        enableFonts = FontsUtil.getEnableFonts();
    }

    @Override
    public FontViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_font, parent, false);
        return new FontViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FontViewHolder holder, int position) {
        if (mFont == null) return;
        holder.mTextView.setTypeface(FontsUtil.get(holder.mTextView.getContext(),
                FontsUtil.Font.values()[position].getId()));
        if (mFont.equals(FontsUtil.Font.values()[position])) {
            holder.vCircle.setVisibility(View.VISIBLE);
            holder.mTextView.setTextColor(Color.RED);

            enableFonts.put(mFont, false);
        } else {
            holder.mTextView.setTextColor(Color.WHITE);
            holder.vCircle.setVisibility(View.INVISIBLE);
        }
        for(Map.Entry<FontsUtil.Font,Boolean> entry : enableFonts.entrySet()) {
            FontsUtil.Font key = entry.getKey();
            Boolean value = entry.getValue();
            if(key.equals(FontsUtil.Font.values()[position])){
                holder.vCircle.setVisibility(View.VISIBLE);
            }
        }
//        if(mFont.equals(FontsUtil.Font.values()[position])){
//            holder.vCircle.setVisibility(View.VISIBLE);
//        }else{
//            holder.vCircle.setVisibility(View.INVISIBLE);
//        }

        holder.mTextView.setOnClickListener(v -> {
            FontsUtil.Font tmp =
                    FontsUtil.Font.values()[mRecyclerView.getChildAdapterPosition((View) v.getParent())];
            enableFonts.remove(mFont);
            mChangeFontsView.setFont(tmp);
            mFont = tmp;
            notifyDataSetChanged();
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
        mRecyclerView.scrollToPosition(mFont.ordinal());
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mRecyclerView = null;
    //    enableFonts.replace(mFont, false, true);
    }

    @Override
    public int getItemCount() {
        return FontsUtil.Font.values().length;
    }

    public void setFont(FontsUtil.Font mFont) {
        this.mFont = mFont;
        boolean fontPresent = false;
        for(Map.Entry<FontsUtil.Font,Boolean> entry : enableFonts.entrySet()) {
            FontsUtil.Font key = entry.getKey();
            if(key.equals(mFont)){
                entry.setValue(false);
                fontPresent = true;
                break;
            }
        }
        if(!fontPresent)
            enableFonts.put(mFont, false);
        LogUtil.info(this, "setFontt");
        notifyDataSetChanged();
    }

    static class FontViewHolder extends RecyclerView.ViewHolder {
        CustomFontTextView mTextView;
        View vCircle;

        FontViewHolder(View v) {
            super(v);
            mTextView = (CustomFontTextView) v.findViewById(R.id.tvCustomFont);
            vCircle = v.findViewById(R.id.v_circle);
        }
    }
}
//todo fiks two points one font
