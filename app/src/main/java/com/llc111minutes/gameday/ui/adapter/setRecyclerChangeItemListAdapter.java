package com.llc111minutes.gameday.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.StickChangeData;
import com.llc111minutes.gameday.ui.widget.owner.CustomFontTextView;
import com.llc111minutes.gameday.util.LogUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;


public class setRecyclerChangeItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private setRecyclerChangeItemListAdapter.OnItemClickListener mListener;


    private setRecyclerChangeItemListAdapter.OnDataChangeListener mDataChangeListener;

    final private int ITEM = 1;
    final private int END_ITEM = 0;

    int countRecently = 0;
    int countPage = 1;

    private List<StickChangeData> mListStickInfo = new ArrayList<>();
    private boolean endPage = false;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_team_info, parent, false);
            view.setBackgroundColor(Color.TRANSPARENT);
            return new setRecyclerChangeItemListAdapter.ViewHolderItem(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_end_list, parent, false);
            view.setBackgroundColor(Color.TRANSPARENT);
            return new setRecyclerChangeItemListAdapter.ViewHolderProgress(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) { //Если 0 позиция
            LogUtil.info(this, "YOUP1");
            if (countRecently != 0) { //если есть Recently то установить на 0 позиции Хидер Recently
                LogUtil.info(this, "YOUP11");
                ((ViewHolderItem) holder).tvHeader.setVisibility(View.VISIBLE);
                ((ViewHolderItem) holder).tvHeader.setText(R.string.recently_used);
            } else { // Иначе на 0 позиции Хидер Search
                LogUtil.info(this, "YOUP12");
                if (holder instanceof ViewHolderItem) { // Кастыль - чтобы не падало
                    LogUtil.info(this, "YOUP121");
                    ((ViewHolderItem) holder).tvHeader.setVisibility(View.VISIBLE);
                    ((ViewHolderItem) holder).tvHeader.setText(R.string.search_result);
                }
            }
        }
        if (countRecently != 0 && position == countRecently) { // если есть Recently и позиция равна их количеству то поставить Хидер Search HO
            LogUtil.info(this, "YOUP2");
            if (mListStickInfo.size() > countRecently) {
                LogUtil.info(this, "YOUP21");
                ((ViewHolderItem) holder).tvHeader.setText(R.string.search_result);
                ((ViewHolderItem) holder).tvHeader.setVisibility(View.VISIBLE);
            }
        }
        //если холдер от итема то заполнить данными из листа
        if (holder instanceof setRecyclerChangeItemListAdapter.ViewHolderItem) {
            if (((ViewHolderItem) holder).tvHeader.getVisibility() == View.VISIBLE) {   //если поле видемо НО
                if (position != 0 && position != countRecently) {                       // не должно отображаться то выключить
                    ((ViewHolderItem) holder).tvHeader.setVisibility(View.GONE);
                }
            }
            LogUtil.info(this, "YOUP3");
            ((ViewHolderItem) holder).bind(mListStickInfo.get(position), mListener);
        }
        if (holder instanceof ViewHolderProgress) {
            LogUtil.info(this, "YOUP4");

//            ((ViewHolderItem) holder).tvHeader.setVisibility(View.VISIBLE);
//            ((ViewHolderItem) holder).tvHeader.setText(R.string.no_search_result);
            mDataChangeListener.onDataChange(); //прошу новые данные
        }
    }

    //поличил новые данные - необходимо добавить в конец
    public void setDataSearch(List<? extends StickChangeData> list) {
        if (list.size() == 0) { //елси пришел лист с 0 данных означает что данные закончились и програсс бар не нужен.
            endPage = true;
            notifyDataSetChanged();
            return;
        }
        mListStickInfo.addAll(list);
        countPage++;
        notifyDataSetChanged();
    }

    // поличил данные Recently- необходимо добавить в начало
    public void setDataRecently(List<? extends StickChangeData> list) {
        if (countRecently != 0) return;
        countRecently = list.size();
        mListStickInfo.addAll(0, list);
    }

    /**
     * для получения количества отображенных страниц
     *
     * @return -количесто !отображенных! страниц
     */

    public int getCountPage() {
        return countPage;
    }

    /**
     * очистить кол-во страниц, очистить лист данных, очистить флаг конца
     */

    public void clearData() {
        mListStickInfo.clear();
        countRecently = 0;
        countPage = 1;
        endPage = false;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int res;
        if (mListStickInfo == null) {
            return 0;
        }
        res = mListStickInfo.size();
        if (!endPage) ++res;
        return res;
    }

    @Override
    public int getItemViewType(int position) {
        if (!endPage) {
            return position == getItemCount() - 1 ? END_ITEM : ITEM;
        } else {
            return ITEM;
        }
    }


    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        mDataChangeListener = onDataChangeListener;
    }

    public setRecyclerChangeItemListAdapter(OnItemClickListener listener) {
        mListener = listener;
    }


    class ViewHolderItem extends RecyclerView.ViewHolder {
        @BindView(R.id.img_logo)
        ImageView mLogo;
        @BindView(R.id.tv_name_team)
        CustomFontTextView mTeamName;
        @BindView(R.id.item_container)
        ViewGroup viewGroup;
        @BindView(R.id.tv_header)
        TextView tvHeader;

        public ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(StickChangeData stickChange, OnItemClickListener mListener) {
            if (stickChange.getImage() != null) {
                Picasso.with(mLogo.getContext())
                        .load(Const.BASE_URL + stickChange.getImage().getUrl())
                        .into(mLogo);
            } else {
                mLogo.setVisibility(View.GONE);
            }
            mTeamName.setText(stickChange.getName());

            viewGroup.setOnClickListener(v -> mListener.onItemClick(stickChange));
        }
    }

    class ViewHolderProgress extends RecyclerView.ViewHolder {

        public ViewHolderProgress(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(StickChangeData item);
    }

    public interface OnDataChangeListener {
        void onDataChange();
    }
}
