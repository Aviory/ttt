package com.llc111minutes.gameday.ui.fragment.dialog;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.api.ApiManager;
import com.llc111minutes.gameday.api.ApiService;
import com.llc111minutes.gameday.model.HeadlineTitle;
import com.llc111minutes.gameday.model.HeadlinesRetrofit;
import com.llc111minutes.gameday.model.Pack;
import com.llc111minutes.gameday.model.Stadias;
import com.llc111minutes.gameday.model.StickChangeData;
import com.llc111minutes.gameday.model.TeamInfo;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContainerInterface;
import com.llc111minutes.gameday.ui.activity.MainActivity;
import com.llc111minutes.gameday.ui.adapter.setRecyclerChangeItemListAdapter;
import com.llc111minutes.gameday.ui.widget.ClearFocusRecyclerView;
import com.llc111minutes.gameday.ui.widget.owner.CustomFontTextView;
import com.llc111minutes.gameday.util.LogUtil;
import com.llc111minutes.gameday.util.Storage;
import com.llc111minutes.gameday.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

/**
 * This class is a dialog for filling in the text and / or image of the template
 */
public class ChangeItemDialogFragment extends SupportBlurDialogFragment implements /*SearchView.OnQueryTextListener, */setRecyclerChangeItemListAdapter.OnItemClickListener, TextWatcher {

    public static final String FILE_NAME_SHP_TEAM = "selectedTeam";
    public static final String FILE_NAME_SHP_STADIAS = "selectedStadias";
    public static final String FILE_NAME_SHP_HEADLINES = "select HeadlinesRetrofit";
    public static final String ARG_SELECTED = "select container";
    public static final String ARG_TEAM_INFO = "select com.llc111minutes.gameday.sticks.team info";

    @BindView(R.id.tv_title)
    protected TextView mTitleView;
    @BindView(R.id.rv_list_team)
    protected ClearFocusRecyclerView mRecyclerViewSearch;
    @BindView(R.id.et_search_text)
    protected EditText mSearchText;
    @BindView(R.id.img_search)
    protected ImageView mSearchImage;
    @BindView(R.id.tv_cancel)
    protected CustomFontTextView mButtonCancel;
    @BindView(R.id.rv_list_tv)
    protected TextView tvNoSearchResul;

    ApiService apiService;

    setRecyclerChangeItemListAdapter mAdapterSearch;
    List<? extends StickChangeData> mListItemInfoSearch = null;
    List<? extends StickChangeData> mListItemInfoRecently = null;

    private ContainerInterface mSelected;
    private String mUseSharedFile;
    private String stringSearchRequest = "";

    public ChangeItemDialogFragment() {
    }

    public static ChangeItemDialogFragment newInstance(ContainerInterface container) {
        ChangeItemDialogFragment fragment = new ChangeItemDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SELECTED, container);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelected = getArguments().getParcelable(ARG_SELECTED);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_teams, container, false);
        ButterKnife.bind(this, rootView);
        mRecyclerViewSearch.setEditText(mSearchText);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiService = ApiManager.getClient().create(ApiService.class);
        mAdapterSearch = new setRecyclerChangeItemListAdapter(this);
        mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewSearch.setAdapter(mAdapterSearch);

        setListener();
        getListAndRefreshAdapter();
    }

    private void setListener() {
        mAdapterSearch.setOnDataChangeListener(() -> getData());

        tvNoSearchResul.setVisibility(View.INVISIBLE);
        mSearchText.addTextChangedListener(this);

        mSearchText.setOnFocusChangeListener((v, hasFocus) -> {
            LogUtil.info(this, "FOCUS EditTxt: " + hasFocus);
            mSearchImage.setImageResource(hasFocus ? R.drawable.search_rad : R.drawable.search_gray);
            if (!hasFocus) {
                UiUtils.hideKeyboard(v);
            }
        });

        mButtonCancel.setOnClickListener(v -> {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
            dismiss();
        });

    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        Window window = getDialog().getWindow();
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        ColorDrawable dialogColor = new ColorDrawable(Color.BLACK);
        dialogColor.setAlpha(100);
        window.setBackgroundDrawable(dialogColor);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.TOP;
        window.setAttributes(lp);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private void getListAndRefreshAdapter() {
        Pack p = ((MainActivity) getActivity()).getPack();
        switch (mSelected.getContent().getType()) {
            case TEAM1:
            case TEAM2:
            case TEAM1LOGO:
            case TEAM2LOGO:
                mTitleView.setText(R.string.add_logo);
                mUseSharedFile = FILE_NAME_SHP_TEAM;
                mListItemInfoRecently = Storage.instance(getContext())
                        .getListItemInfo(TeamInfo.class, mUseSharedFile);
                break;
            case PLACE:
                mTitleView.setText(R.string.add_place);
                mUseSharedFile = FILE_NAME_SHP_STADIAS;
                mListItemInfoRecently = Storage.instance(getContext())
                        .getListItemInfo(Stadias.class, mUseSharedFile);
                break;
            case SEPARATOR_TITLE:
            case HEADLINE:
                mTitleView.setText(R.string.add_headline);
                mUseSharedFile = FILE_NAME_SHP_HEADLINES;
                mAdapterSearch.setDataSearch(p.getAllHeadlines());
                mListItemInfoRecently = Storage.instance(getContext())
                        .getListItemInfo(HeadlineTitle.class, mUseSharedFile);
                break;
        }
        mAdapterSearch.setDataRecently(mListItemInfoRecently);
    }

    private void getData() {
        switch (mSelected.getContent().getType()) {
            case TEAM1:
            case TEAM2:
            case TEAM1LOGO:
            case TEAM2LOGO:
                Call<List<TeamInfo>> call;
                if (stringSearchRequest.equals("")) {
                    mAdapterSearch.setDataRecently(mListItemInfoRecently);
                    LogUtil.info(this,"setDataRecently" +String.valueOf(mListItemInfoRecently));
                    call = apiService.getPageTeams(mAdapterSearch.getCountPage());
                } else {
                    call = apiService.getSearchTeams(stringSearchRequest, mAdapterSearch.getCountPage());
                }

                call.enqueue(new Callback<List<TeamInfo>>() {
                    @Override
                    public void onResponse(Call<List<TeamInfo>> call, Response<List<TeamInfo>> response) {
                        List<TeamInfo> tmpList = response.body();
                        if(tmpList.size()==0 && mAdapterSearch.getCountPage() == 1 && mAdapterSearch.getItemCount()==0){
                            tvNoSearchResul.setVisibility(View.VISIBLE);
                        }else {
                            tvNoSearchResul.setVisibility(View.INVISIBLE);
                        }
                        mAdapterSearch.setDataSearch(tmpList);
                    }

                    @Override
                    public void onFailure(Call<List<TeamInfo>> call, Throwable t) {

                    }
                });
                break;
            case PLACE:
            case USER_PLACE:
                Call<List<Stadias>> callStadias;
                if (stringSearchRequest.equals("")) {
                    mAdapterSearch.setDataRecently(mListItemInfoRecently);
                    callStadias = apiService.getPageStadias(mAdapterSearch.getCountPage());
                } else {
                    callStadias = apiService.getSearchStadias(stringSearchRequest, mAdapterSearch.getCountPage());
                }

                callStadias.enqueue(new Callback<List<Stadias>>() {
                    @Override
                    public void onResponse(Call<List<Stadias>> call, Response<List<Stadias>> response) {
                        List<Stadias> tmpList = response.body();
                        mAdapterSearch.setDataSearch(tmpList);
                    }

                    @Override
                    public void onFailure(Call<List<Stadias>> call, Throwable t) {

                    }
                });
                break;
            case SEPARATOR_TITLE:
                LogUtil.info(this, "SEPARATOR_TITLE");
                break;
            case HEADLINE:
            case USER_HEADLINE:
               // mAdapterSearch.setDataSearch(new ArrayList<>());

//                Observable<HeadlinesRetrofit> callHeadlines;
//                callHeadlines = apiService.getHeadlines();

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mAdapterSearch.clearData();

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        stringSearchRequest = String.valueOf(s);
        getData();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private List<? extends StickChangeData> filter(List<? extends StickChangeData> models, String query) {
        query = query.toLowerCase();
        final List<StickChangeData> filteredModelList = new ArrayList<>();
        for (StickChangeData model : models) {
            String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onItemClick(StickChangeData item) {
        saveToSharedPreference(mUseSharedFile, item);
        Intent intent = new Intent();
        intent.putExtra(ARG_TEAM_INFO, item);
        intent.putExtra(ARG_SELECTED, mSelected);
       // mSelected.getContent().setDefault(false);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }

    private void saveToSharedPreference(String type, StickChangeData item) {
        Storage storage = Storage.instance(getContext());
        storage.addStickChangeData(type, item);
        /*int key;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(mUseSharedFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int count = sharedPreferences.getAll().size();
        key = count >= 4 ? 4 : count + 1;
        LogUtil.info(this, "KEY & Count:" + key + ", " + count);
        editor.putInt(String.valueOf(key), id);
        editor.apply();*/
    }


}