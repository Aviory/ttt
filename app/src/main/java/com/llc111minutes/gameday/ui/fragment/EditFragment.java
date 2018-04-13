package com.llc111minutes.gameday.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.jakewharton.rxbinding.widget.RxSeekBar;
import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.common.BaseActivity;
import com.llc111minutes.gameday.common.BaseFragment;
import com.llc111minutes.gameday.events.Events;
import com.llc111minutes.gameday.interfaces.BackEventInterface;
import com.llc111minutes.gameday.interfaces.Callback;
import com.llc111minutes.gameday.interfaces.CropInterface;
import com.llc111minutes.gameday.interfaces.FilterMenuOpetation;
import com.llc111minutes.gameday.model.Logo;
import com.llc111minutes.gameday.model.Overlay;
import com.llc111minutes.gameday.model.Pack;
import com.llc111minutes.gameday.model.Place;
import com.llc111minutes.gameday.model.Preview;
import com.llc111minutes.gameday.model.StickChangeData;
import com.llc111minutes.gameday.model.Template;
import com.llc111minutes.gameday.model.enums.ContentType;
import com.llc111minutes.gameday.model.enums.FILTER;
import com.llc111minutes.gameday.model.enums.GlobalState;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.sticks.team.universal.sticker.Container;
import com.llc111minutes.gameday.sticks.team.universal.sticker.ImageContent;
import com.llc111minutes.gameday.sticks.team.universal.sticker.ParentView;
import com.llc111minutes.gameday.sticks.team.universal.sticker.Placer;
import com.llc111minutes.gameday.sticks.team.universal.sticker.TextContent;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContainerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContentListenerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.SelectEventListener;
import com.llc111minutes.gameday.ui.activity.MainActivity;
import com.llc111minutes.gameday.ui.fragment.dialog.ChangeItemDialogFragment;
import com.llc111minutes.gameday.ui.fragment.dialog.DataPickerFragment;
import com.llc111minutes.gameday.ui.fragment.dialog.EditTextDialogFragment;
import com.llc111minutes.gameday.ui.fragment.dialog.EditingTipsDialogFragment;
import com.llc111minutes.gameday.ui.widget.owner.AddElementView;
import com.llc111minutes.gameday.ui.widget.owner.AdjustView;
import com.llc111minutes.gameday.ui.widget.owner.BackgroundChoiceView;
import com.llc111minutes.gameday.ui.widget.owner.ChangeLayoutView;
import com.llc111minutes.gameday.ui.widget.owner.CustomFontTextView;
import com.llc111minutes.gameday.ui.widget.owner.EditFlowLayout;
import com.llc111minutes.gameday.ui.widget.owner.FilterView;
import com.llc111minutes.gameday.ui.widget.owner.ListChoiseTemplates;
import com.llc111minutes.gameday.ui.widget.owner.SelectorOfThree;
import com.llc111minutes.gameday.ui.widget.owner.layout.LayoutView;
import com.llc111minutes.gameday.util.AnimUtil;
import com.llc111minutes.gameday.util.BitmapUtils;
import com.llc111minutes.gameday.util.FileUtil;
import com.llc111minutes.gameday.util.FilterUtil;
import com.llc111minutes.gameday.util.FontsUtil;
import com.llc111minutes.gameday.util.LogUtil;
import com.llc111minutes.gameday.util.MathUtils;
import com.llc111minutes.gameday.util.Storage;
import com.llc111minutes.gameday.util.TypefaceUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class EditFragment extends BaseFragment implements CropInterface, BackEventInterface,
        SelectorOfThree.OnItemSelectedListener, FilterMenuOpetation, SelectEventListener,
        ContentListenerInterface {
    private final static String BIG_FILTER = "com.matchday.android.ui.fragment.global.filter";
    private final static String ARRAY_FILTERS = "com.matchday.android.ui.fragment.array.filters";
    private final static String SELECTED_ITEM = "com.matchday.android.ui.fragment.selected.item";
    private final static String SAVE_CONTAINERS = "com.matchday.android.ui.fragment.containers";
    private final static String SAVE_OVERLAY = "com.matchday.android.ui.fragment.overlay";
    private final static String SAVE_USER_TEXTS = "com.matchday.android.ui.fragment.map.texts";
    private static final int REQUEST_CODE = 100;
    private static final int REQUEST_CODE_DATA_PICKER = 200;
    private static final int REQUEST_CODE_EDIT_TEXT = 300;

    @BindView(R.id.surfaceView)
    protected ImageView surfaceView;
    @BindView(R.id.parentPanel)
    protected ParentView parentView;
    @BindView(R.id.selector)
    protected SelectorOfThree selector;
    @BindView(R.id.rlChangeContainer)
    protected RelativeLayout rlChangeContainer;
    @BindView(R.id.flChangeContainer)
    protected FrameLayout flChangeContainer;
    @BindView(R.id.tvMode)
    protected CustomFontTextView tvMode;
    @BindView(R.id.tvNew)
    protected CustomFontTextView tvNew;
    @BindView(R.id.tvNext)
    protected CustomFontTextView tvNext;
    @BindView(R.id.editFlowLayout)
    protected EditFlowLayout editFlowLayout;
    @BindView(R.id.flContent)
    protected FrameLayout flContent;
    @BindView(R.id.tv_brand)
    protected CustomFontTextView mBrandTv;

    // Saving data
    private FilterUtil.FILTER bigFilter;
    private ArrayList<FILTER> filters;

    private FILTER mFilter;
    private Bitmap sourceBitmap, filteredBitmap, overlayBitmap;
    private HashMap<ContentType, String> mapSavingValues = new HashMap<>();

    private Subscription subscriptionSeekBar;
    private Observable<Integer> seekbarObservable;
    private Template mActiveTemplate;
    private int mOverlayId = -1;

    public static Bundle getBundle(String fileName) {
        Bundle args = new Bundle();
        args.putString(Const.BITMAP, fileName);
        return args;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filters = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAct().setGlobalState(GlobalState.EDIT);
        RxClick.addDelayOnPressedListener(this, tvNew);
        RxClick.addDelayOnPressedListener(this, tvNext);
        selector.setOnItemSelectedListener(this);
        Callback<Integer> changeContentCallBack = new Callback<Integer>() {
            @Override
            public void onResult(Integer integer) {
                EventBus.getDefault().post(new Events.ChangeContentEvent(integer));
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        parentView.setChangeContentCallback(changeContentCallBack);
        Callback<Boolean> changeParentViewStatus = new Callback<Boolean>() {
            @Override
            public void onResult(Boolean status) {
                LogUtil.info(this, "changeParentViewStatus: " + status);
                BackgroundChoiceView backgroundChoiceView =
                        (BackgroundChoiceView) rlChangeContainer.findViewById(R.id.background_choice);
                if (status) {
                    if (backgroundChoiceView == null) {
                        backgroundChoiceView = new BackgroundChoiceView(getContext());
                        AnimUtil.attachAlpha(rlChangeContainer, backgroundChoiceView);
                    }
                } else {
                    if (backgroundChoiceView != null) {
                        AnimUtil.detachAlpha(backgroundChoiceView);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        parentView.setChangeParentViewStatusCallback(changeParentViewStatus);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_edit;
    }

    @Override
    public MainActivity getAct() {
        return (MainActivity) getActivity();
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            getFragmentManager().popBackStack();
            return;
        }
        LogUtil.info(this, "setSourceBitmap: " + bitmap.toString());
        Observable.just(bitmap)
                .subscribeOn(Schedulers.newThread())
                .map(bitmap1 -> BitmapUtils.compressBitmap(bitmap1, 1000, true))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.info(this, "onCompleted");
                        if (bigFilter != null) {
                            applyBigFilter(bigFilter);
                        } else {
                            notifyFilters();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.info(this, "Error: " + e.getLocalizedMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        sourceBitmap = bitmap;
                        /*if (filterView != null)
                            filterView.setBitmap(sourceBitmap);
                        if (layoutView != null)
                            layoutView.setSourceBitmap(sourceBitmap);*/
                        surfaceView.setImageBitmap(sourceBitmap);
                        selector.selectEvent(selector.getSelected());
                    }
                });
    }

    @Override
    public void onItemSelected(View view, int id) {
        LogUtil.info(this, "onItemSelected: " + selector.getText());
        tvMode.setText(selector.getText());
        switch (id) {
            case 0:
                LayoutView layoutView = (LayoutView) flChangeContainer.findViewById(R.id.layout);
                if (layoutView == null) {
                    flChangeContainer.removeAllViews();
                    layoutView = new LayoutView(getContext());
                    flChangeContainer.addView(layoutView);
                }
                break;
            case 1:
                FilterView filterView = (FilterView) flChangeContainer.findViewById(R.id.filter);
                if (filterView == null) {
                    flChangeContainer.removeAllViews();
                    filterView = new FilterView(getContext());
                    flChangeContainer.addView(filterView);
                }
                break;
            case 2:
                AdjustView adjustView = (AdjustView) flChangeContainer.findViewById(R.id.adjustView);
                if (adjustView == null) {
                    adjustView = new AdjustView(getContext());
                    flChangeContainer.removeAllViews();
                    flChangeContainer.addView(adjustView);

                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        applyStileForBrand();
        editFlowLayout.setFilterMenuOpetation(this);
        return v;
    }

    private void applyStileForBrand() {
        mBrandTv.setClickable(true);
        mBrandTv.setOnClickListener(this);
        applyPurchaseBrandLogo();
    }

    /**
     * Method testing buy Purchase
     */
    Storage storage;
    public void applyPurchaseBrandLogo() {
         storage = Storage.instance(getContext());
        if (storage.getPurchaseState()) {
            mBrandTv.setVisibility(View.GONE);
        } else {
            mBrandTv.setText(applySpanInText(R.string.gamedayapp_hashtag));
        }
    }

    /**
     * This method produces text formatting for the brand
     */
    @NonNull
    private SpannableStringBuilder applySpanInText(@StringRes int text) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(getString(text));
        int color = ContextCompat.getColor(getContext(), R.color.red);
        spannable.setSpan(new ForegroundColorSpan(color), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(color), 8, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBrandTv.setTypeface(TypefaceUtils.getLatoBlackTypeface(getContext()));
        return spannable;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.info(this, "onResume, selector: " + selector.getSelected());
        if (getArguments() != null) {
            String fileName = getArguments().getString(Const.BITMAP);
            File image = new File(fileName);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            setBitmap(bitmap);
        } else {
            onBack();
        }
        parentView.setSelectEventListener(this);
        actionEditingTips();

    }

    /**
     * The method of checking the first start. To display a EditingTipsDialog
     */
    private void actionEditingTips() {
        Storage storage = Storage.instance(getContext());
        if (storage.isFirstOpeningFragment()) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(400);
                    getEditingTipsDialog();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        parentView.setSelectEventListener(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvNew:
                AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom)
                        .setCancelable(false)
                        .setTitle(R.string.dialog_alert_discard_title)
                        .setMessage(R.string.dialog_alert_discard_message)
                        .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.dismiss())
                        .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                            FragmentManager manager = getFragmentManager();
                            while (manager.getBackStackEntryCount() > 0) {
                                manager.popBackStackImmediate();
                            }
                        })
                        .create();
                alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                alertDialog.show();
                alertDialog.getWindow().getDecorView().setSystemUiVisibility(
                        getAct().getWindow().getDecorView().getSystemUiVisibility());
                alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                break;
            case R.id.tvNext:
                parentView.setPreview(true);
                parentView.invalidate();

                Bitmap bitmap = BitmapUtils.getBitmapFromView(flContent);
                Observable.just(bitmap)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .map(bitmap1 -> {
                            try {
                                return FileUtil.saveBitmapToFile(getActivity(), bitmap1, "share_file");
                            } catch (IOException e) {
                                e.printStackTrace();
                                //TODO File error
                                return null;
                            }

                        })
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String fileName) {
                                BaseActivity.addFragment(getAct(), ShareFragment.class, R.id.coordinator_layout,
                                        ShareFragment.getBundle(fileName), true, true, true);
                            }
                        });
                break;
            case R.id.tv_brand:
                AlertDialog alertDialogPurchase = new AlertDialog.Builder(getAct(), R.style.AlertDialogCustom)
                        .setCancelable(false)
                        .setMessage(R.string.dialog_alert_purchase)
                        .setNegativeButton(R.string.no, (dialogInterface, which) -> dialogInterface.dismiss())
                        .setPositiveButton(R.string.yes, (dialogInterface, which) -> {
                            getAct().makePurchase();
                            Toast.makeText(getAct(), R.string.nav_purchase, Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        })
                        .create();
                alertDialogPurchase.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                alertDialogPurchase.show();
                alertDialogPurchase.getWindow().getDecorView().setSystemUiVisibility(
                        getAct().getWindow().getDecorView().getSystemUiVisibility());
                alertDialogPurchase.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                break;
        }
    }

    @Override
    public void appFilter(FilterUtil.FILTER filter) {
        bigFilter = filter;
        applyBigFilter(bigFilter);
    }

    private void applyBigFilter(FilterUtil.FILTER filter) {
        Observable.just(filter)
                .map(FilterUtil::getFilter)
                .map(gpuImageFilter -> {
                    if (gpuImageFilter == null) {
                        return sourceBitmap;
                    }
                    GPUImage gpuImage = new GPUImage(getContext());
                    gpuImage.setFilter(gpuImageFilter);
                    return gpuImage.getBitmapWithFilterApplied(sourceBitmap);
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {
                        notifyFilters();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        filteredBitmap = bitmap;
                        surfaceView.setImageBitmap(bitmap);
                    }
                });
    }

    @Override
    public void addFilter(FILTER filter) {
        filters.add(filter);
    }

    @Override
    public boolean isFilterAdded(FILTER filter) {
        return filters.contains(filter);
    }

    @Override
    public FILTER getFilter(FILTER filter) {
        for (FILTER f : filters) {
            if (f.getId() == filter.getId()) return f;
        }
        return null;
    }

    @Override
    public void removeFilter(FILTER filter) {
        filters.remove(filter);
    }

    @Override
    public void notifyFilters() {
        if (filters.size() == 0) {
            if (filteredBitmap == null) {
                surfaceView.setImageBitmap(sourceBitmap);
            } else {
                surfaceView.setImageBitmap(filteredBitmap);
            }
            return;
        }

        GPUImage gpuImage = new GPUImage(getContext());

        addSubscription(
                Observable.just(gpuImage)
                        .subscribeOn(Schedulers.computation())
                        .map(gpuImage1 -> {
                            Bitmap sBitmap;

                            if (filteredBitmap == null) {
                                sBitmap = sourceBitmap.copy(sourceBitmap.getConfig(), true);
                            } else {
                                sBitmap = filteredBitmap.copy(sourceBitmap.getConfig(), true);
                            }

                            for (FILTER f : filters) {
                                FILTER.apply(f, gpuImage1);
                                sBitmap = gpuImage1.getBitmapWithFilterApplied(sBitmap);
                            }
                            return sBitmap;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Bitmap>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Bitmap bitmap) {
                                surfaceView.setImageBitmap(bitmap);
                            }
                        }));
    }

    Func1<Integer, Float> mSeekFunc = new Func1<Integer, Float>() {
        @Override
        public Float call(Integer integer) {
            return MathUtils.range(integer, mFilter.getMin(), mFilter.getMax());
        }
    };

    Func1<Float, Bitmap> bitmapFunc = new Func1<Float, Bitmap>() {
        @Override
        public Bitmap call(Float current) {
            mFilter.setCurrent(current);
            Bitmap sBitmap;
            if (filteredBitmap == null) {
                sBitmap = sourceBitmap.copy(sourceBitmap.getConfig(), true);
            } else {
                sBitmap = filteredBitmap.copy(sourceBitmap.getConfig(), true);
            }

            LogUtil.info(this, "Filters: " + filters.size());
            GPUImage gpuImage = new GPUImage(getContext());
            for (FILTER f : filters) {
                LogUtil.info(f, "apply");
                FILTER.apply(f, gpuImage);
                sBitmap = gpuImage.getBitmapWithFilterApplied(sBitmap);
            }
            return sBitmap;
        }
    };

    @Override
    public void subscribeSeekBarChange(SeekBar seekBar, FILTER filter) {
        this.mFilter = filter;
        seekbarObservable = RxSeekBar.userChanges(seekBar);
        subscriptionSeekBar = seekbarObservable
                .debounce(20, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(mSeekFunc)
                .subscribeOn(Schedulers.computation())
                .map(bitmapFunc)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.info(this, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        surfaceView.setImageBitmap(bitmap);
                    }
                });
        addSubscription(subscriptionSeekBar);
    }

    @Override
    public void unsubscribeSeekBarChange() {
        subscriptionSeekBar.unsubscribe();
        unsubscribe(subscriptionSeekBar);
        seekbarObservable = null;
        subscriptionSeekBar = null;
    }

    @Override
    public void setTemplate(Template template) {
        LogUtil.info(this, "set template: " + template.getTemplateGroupId());
        mActiveTemplate = template;
        mOverlayId = template.getOverlayId();
        parentView.setColorForFilter(Integer.MIN_VALUE);

        FontsUtil.setEnableFonts(new HashMap<>());

        EventBus.getDefault().post(new Events.ActionSetTemplate(template));

        ArrayList<ContainerInterface> userContainers = new ArrayList<>();
        saveUserDataFromParentPanel(userContainers);

        restoreUserEnteredDataToTemplate(template);

        placeTemplateToPanel(template);
        addCustomUserContents(userContainers);

        userContainers.clear();
        userContainers = null;
    }

    @Override
    public Template getTemplate() {
        return mActiveTemplate;
    }

    private void addCustomUserContents(ArrayList<ContainerInterface> userContainer) {
        for (int i = 0; i < userContainer.size(); i++) {
            parentView.addContainer(userContainer.get(i));
        }
    }

    private void placeTemplateToPanel(Template template) {
        Placer.clearPanel(parentView);
        Placer.setMapSavingValues(mapSavingValues);
        Placer.placeTemplate(template, parentView);
        // set overlay
        mOverlayId = template.getOverlayId();
        Placer.setOverlay(getAct().getPack().getAllOverlays(), mOverlayId, parentView);
    }

    private void saveUserDataFromParentPanel(ArrayList<ContainerInterface> userContainer) {
        for (int i = 0; i < parentView.getContainers().size(); i++) {
            LogUtil.info(this, "saveUserDataFromParentPanel:"+String.valueOf(parentView.getContainers().size()));
            ContainerInterface container = parentView.getContainers().get(i);
            if (container.getContent().getType().isTemplate() && !container.getContent().isDefault()) {

                    mapSavingValues.put(container.getContent().getType(), container.getContent().getData());

            } else if (!container.getContent().getType().isTemplate()) {
                userContainer.add(container);
            }
        }
    }

    private void restoreUserEnteredDataToTemplate(Template mTemplate) {
        if (mTemplate.getMetainfo() != null) {
            if (mTemplate.getMetainfo().getHeadline() != null
                    && mapSavingValues.get(ContentType.HEADLINE) != null) {
                mTemplate.getMetainfo().getHeadline().setDefaultText(
                        mapSavingValues.get(ContentType.HEADLINE));
            }
            if (mTemplate.getMetainfo().getSeparatorTitle() != null
                    && mapSavingValues.get(ContentType.SEPARATOR_TITLE) != null) {
                mTemplate.getMetainfo().getSeparatorTitle().setDefaultText(
                        mapSavingValues.get(ContentType.SEPARATOR_TITLE));
            }
            if (mTemplate.getMetainfo().getPlace() != null &&
                    mapSavingValues.get(ContentType.PLACE) != null) {
                mTemplate.getMetainfo().getPlace().setDefaultText(
                        mapSavingValues.get(ContentType.PLACE));
            }
            if (mTemplate.getMetainfo().getTeam().getTitle() != null &&
                    mapSavingValues.get(ContentType.TEAM1) != null) {
                mTemplate.getMetainfo().getTeam().getTitle().setDefaultText(
                        mapSavingValues.get(ContentType.TEAM1));
            }
            if (mTemplate.getMetainfo().getTeam2().getTitle() != null &&
                    mapSavingValues.get(ContentType.TEAM2) != null) {
                mTemplate.getMetainfo().getTeam2().getTitle().setDefaultText(
                        mapSavingValues.get(ContentType.TEAM2));
            }
            if (mTemplate.getMetainfo().getTeam().getLogo() != null &&
                    mapSavingValues.get(ContentType.TEAM1LOGO) != null) {
            }
            if (mTemplate.getMetainfo().getTeam2().getLogo() != null &&
                    mapSavingValues.get(ContentType.TEAM2LOGO) != null) {
            }
        }
    }

    @Override
    public Bitmap getSourceBitmap() {
        if (sourceBitmap == null) {
            sourceBitmap = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.image_background);
        }

        if (rlChangeContainer.getWidth() == 0) {
            return Bitmap.createScaledBitmap(sourceBitmap,
                    100, 100, false);
        } else {
            return Bitmap.createScaledBitmap(sourceBitmap,
                    rlChangeContainer.getHeight() / 4, rlChangeContainer.getHeight() / 4, false);
        }
    }

    @Override
    public Bitmap getBitmapWithBigFilter() {
        return filteredBitmap;
    }

    @Override
    public void clearAll() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom)
                .setCancelable(false)
                .setMessage(R.string.dialog_alert_delete_all_element)
                .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    mActiveTemplate = null;
                    parentView.clear();
                    EventBus.getDefault().post(new Events.ClearTemplate());
                })
                .create();
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        alertDialog.show();
        alertDialog.getWindow().getDecorView().setSystemUiVisibility(
                getAct().getWindow().getDecorView().getSystemUiVisibility());
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    @Override
    public void eventAddCustomContainer() {

    }

    @Override
    public Pack getPack() {
        return getAct().getPack();
    }

    @Override
    public ParentView getParentView() {
        return parentView;
    }

    @Override
    public void onBackEvent() {
        //LogUtil.info(this, "Adjust: "+adjustView.isShown());

        /*if ((adjustView != null && adjustView.isShown()) || (filterView != null && filterView.isShown())) {
            selector.selectEvent(0);
            return;
        }*/

        // Find and remove AddCustomView

        if (flChangeContainer.findViewById(R.id.listChoiceTemplates) != null) {
            ListChoiseTemplates listChoiseTemplates = (ListChoiseTemplates)
                    flChangeContainer.findViewById(R.id.listChoiceTemplates);
            listChoiseTemplates.remove();
            return;
        }

        if (flChangeContainer.findViewById(R.id.view_add_element) != null) {
            AddElementView addElementView = (AddElementView)
                    flChangeContainer.findViewById(R.id.view_add_element);
            addElementView.removeSumSelf();
            return;
        }

        if (editFlowLayout.findViewById(R.id.customHorizontalScrollView) != null) {
            View v = editFlowLayout.findViewById(R.id.customHorizontalScrollView);
            LogUtil.info(this, "View: " + editFlowLayout.findViewById(R.id.customHorizontalScrollView));
            ViewGroup viewGroup = (ViewGroup) v.getParent();
            LogUtil.info(this, "My father: " + viewGroup);
            viewGroup.removeView(v);
            return;
        }
        getAct().getSupportFragmentManager().popBackStack();
    }

    @Override
    protected void saveValue(Bundle outState) {
        if (selector != null) outState.putInt(SELECTED_ITEM, selector.getSelected());
        if (bigFilter != null) {
            outState.putSerializable(BIG_FILTER, bigFilter);
        }
        if (filters != null) {
            outState.putSerializable(ARRAY_FILTERS, filters);
        }
        if (parentView != null && parentView.getContainers() != null && parentView.getContainers().size() > 0) {
            outState.putInt(SAVE_CONTAINERS, parentView.getContainers().size());
            for (int i = 0; i < parentView.getContainers().size(); i++) {
                outState.putParcelable(SAVE_CONTAINERS + i, parentView.getContainers().get(i));
            }
        }
        if (mOverlayId > 0) {
            outState.putInt(SAVE_OVERLAY, mOverlayId);
        }
        if (mapSavingValues != null) {
            outState.putSerializable(SAVE_USER_TEXTS, mapSavingValues);

        }
        LogUtil.info(this, "saveValue: " + selector.getSelected());
        super.saveValue(outState);
    }

    @Override
    protected void restoreValue(Bundle outState) {
        super.restoreValue(outState);
        LogUtil.info(this, "Restore: " + outState.getInt(SELECTED_ITEM, 0));
        selector.setSelected(outState.getInt(SELECTED_ITEM, 0));
        bigFilter = (FilterUtil.FILTER) outState.getSerializable(BIG_FILTER);
        filters = (ArrayList<FILTER>) outState.getSerializable(ARRAY_FILTERS);
        if (filters == null) {
            filters = new ArrayList<>();
        } else {
            LogUtil.info(this, "Filters restored: " + filters);
            //TODO restore filters needed
            for (FILTER f : filters) {
                LogUtil.info(this, "Filter: " + f.name() + " value: " + f.getCurrent());
            }
        }
        mapSavingValues = (HashMap<ContentType, String>) outState.getSerializable(SAVE_USER_TEXTS);
        if (mapSavingValues == null) mapSavingValues = new HashMap<>();
        int sizeContainers = outState.getInt(SAVE_CONTAINERS, 0);
        parentView.clear();
        for (int i = 0; i < sizeContainers; i++) {
            Container container = outState.getParcelable(SAVE_CONTAINERS + i);
            parentView.addContainer(container);
            LogUtil.info("Restore: " + i, "Container: " + container);
        }
        mOverlayId = outState.getInt(SAVE_OVERLAY, 0);
        if (mOverlayId > 0) {
            parentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                    Placer.setOverlay(getAct().getPack().getAllOverlays(), mOverlayId, parentView);
                    parentView.removeOnLayoutChangeListener(this);
                }
            });
        }
    }

    @Override
    public boolean isEndingPressed() {
        return false;
    }

    @Override
    public void onSelected(ContainerInterface container) {
        LogUtil.info(this, "on selected: " + container.getContent().getClass().getCanonicalName());
        if (container.getContent() instanceof TextContent) {
            parentView.setContentListener(this);
            ChangeLayoutView changeLayoutView = (ChangeLayoutView) rlChangeContainer.findViewById(R.id.change_layout_view);
            if (changeLayoutView == null) {
                changeLayoutView = new ChangeLayoutView(getContext());
                changeLayoutView.setContainerToStick(container);
                changeLayoutView.setListenerAction(this);
                // rlChangeContainer.removeAllViews();
                rlChangeContainer.addView(changeLayoutView);
            }
            changeLayoutView.initState();
        }
        tvMode.setText(container.getContent().getType().getNameStringRes());
    }

    @Override
    public void reSelected(ContainerInterface container) {
        LogUtil.info(this, "reSelected: " + container);
        if (container.getContent() instanceof TextContent) {
            ChangeLayoutView changeLayoutView = (ChangeLayoutView) rlChangeContainer.findViewById(R.id.change_layout_view);
            if (changeLayoutView == null) {
                onSelected(container);
                return;
            }
            changeLayoutView.setContainerToStick(container);
            tvMode.setText(container.getContent().getType().getNameStringRes());
        } else {
            unSelected(container);
        }
    }

    @Override
    public void unSelected(ContainerInterface container) {
        LogUtil.info(this, "unselected: " + container.getContent().getClass().getCanonicalName());
        parentView.setContentListener(null);
        rlChangeContainer.removeView(rlChangeContainer.findViewById(R.id.change_layout_view));
        tvMode.setText(R.string.background);
    }

    @Override
    public void setTextToTitle(String text) {
        //      tvMode.setText(text);
    }

    @Override
    public int getOverlayId() {
        return mOverlayId;
    }

    @Override
    public void setOverlay(Overlay overlay) {
        if (overlay != null) {
            this.mOverlayId = (int) overlay.getId();
        } else {
            this.mOverlayId = -1;
        }
        Placer.setOverlay(parentView, overlay);
        parentView.notifyView();
    }

    @Override
    public void setOverlayColorFilter(int color) {
        parentView.setColorForFilter(color);
        Placer.setOverlay(getPack().getAllOverlays(), mOverlayId, parentView);
        parentView.notifyView();
    }

    @Override
    public int getOverlayColorFilter() {
        return parentView.getColorForFilter();
    }

    @Override
    public void showDialogEdit(ContainerInterface selected) {
        if (selected.getContent() instanceof ImageContent) return;
        LogUtil.info(this, "SHOW DIALOG");
        Fragment fragment = this.getFragmentManager().findFragmentByTag(DialogFragment.class.getSimpleName());
        if (fragment != null) return;
        DialogFragment textEditorDialogFragment = EditTextDialogFragment.newInstance(selected);
        textEditorDialogFragment.setTargetFragment(this, REQUEST_CODE_EDIT_TEXT);
        textEditorDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogStyle_FullScreen);
        textEditorDialogFragment.show(getFragmentManager(), DialogFragment.class.getSimpleName());
    }

    @Override
    public void showDialog(ContainerInterface selected) {
        Fragment fragment = this.getFragmentManager().findFragmentByTag(DialogFragment.class.getSimpleName());
        if (fragment != null) return;
        DialogFragment dialogFragment;
        switch (selected.getContent().getType()) {
            case DATE:
                dialogFragment = DataPickerFragment.newInstance(selected);
                dialogFragment.setTargetFragment(this, REQUEST_CODE_DATA_PICKER);
                break;
            case USER_DATE:
                dialogFragment = DataPickerFragment.newInstance(selected);
                dialogFragment.setTargetFragment(this, REQUEST_CODE_DATA_PICKER);
                break;
            case TIME:
                dialogFragment = DataPickerFragment.newInstance(selected);
                dialogFragment.setTargetFragment(this, REQUEST_CODE_DATA_PICKER);
                break;
            case USER_TIME:
                dialogFragment = DataPickerFragment.newInstance(selected);
                dialogFragment.setTargetFragment(this, REQUEST_CODE_DATA_PICKER);
                break;
            default:
                dialogFragment = ChangeItemDialogFragment.newInstance(selected);
                dialogFragment.setTargetFragment(this, REQUEST_CODE);
        }
        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogStyle_FullScreen);
        dialogFragment.show(getFragmentManager(), DialogFragment.class.getSimpleName());
    }

    private void getEditingTipsDialog() {
        Fragment fragment = this.getFragmentManager().findFragmentByTag(EditingTipsDialogFragment.class.getSimpleName());
        if (fragment != null) return;
        DialogFragment dialogFragment;
        dialogFragment = EditingTipsDialogFragment.newInstance();
        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogStyle_FullScreen);
        dialogFragment.show(getFragmentManager(), EditingTipsDialogFragment.class.getSimpleName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<ContainerInterface> list = parentView.getContainers();
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case REQUEST_CODE:
                        StickChangeData teamInfo = data.getParcelableExtra(ChangeItemDialogFragment.ARG_TEAM_INFO);
                        ContainerInterface selected = data.getParcelableExtra(ChangeItemDialogFragment.ARG_SELECTED);
                        if (selected.getContent() instanceof ImageContent) {
                            LogUtil.info(this, "icon: mapSavingValues: "+selected.getContent().getType());
                            mapSavingValues.put(selected.getContent().getType(), Const.BASE_URL + teamInfo.getImage().getUrl());
                        }
                        for (ContainerInterface container : list) {
                            if (container == selected) {
                                if (container.getContent() instanceof ImageContent) {
                                    container.getContent().setData(Const.BASE_URL + teamInfo.getImage().getUrl());
                                    container.getContent().setDefault(false);
                                    container.init(getContext());
                                    container.invalidate();

                                    ContentType contentType = null;
                                    switch (selected.getContent().getType()) {
                                        case TEAM1LOGO:
                                            contentType = ContentType.TEAM1;
                                            break;
                                        case TEAM2LOGO:
                                            contentType = ContentType.TEAM2;
                                            break;
                                    }

                                    if (contentType != null) {

                                        for (ContainerInterface containerInterface : parentView.getContainers()) {
                                            if (containerInterface.getContent().getType().equals(contentType)) {
                                                containerInterface.getContent().setData(teamInfo.getName());//textChanged
                                                containerInterface.getContent().setDefault(false);
                                                containerInterface.getContent().invalidate();
                                                containerInterface.invalidate();
                                                break;
                                            }
                                        }

                                    }

                                } else if (container.getContent() instanceof TextContent) {
                                    ((TextContent) container.getContent()).textChanged(teamInfo.getName());
                                    container.getParent().notifyView();
                                    container.getContent().setDefault(false);
                                    container.getContent().invalidate();
                                    container.invalidate();


                                    ContentType contentType = null;
                                    switch (selected.getContent().getType()) {
                                        case TEAM1:
                                            contentType = ContentType.TEAM1LOGO;
                                            break;
                                        case TEAM2:
                                            contentType = ContentType.TEAM2LOGO;
                                            break;
                                    }

                                    if (contentType != null) {
                                        for (ContainerInterface containerInterface : parentView.getContainers()) {
                                            if (containerInterface.getContent().getType().equals(contentType)) {
                                                containerInterface.getContent().
                                                        setData(Const.BASE_URL + teamInfo.getImage().getUrl());
                                                containerInterface.getContent().setDefault(false);
                                                containerInterface.init(getContext());
                                                containerInterface.invalidate();
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        parentView.notifyView();
                        break;

                    case REQUEST_CODE_DATA_PICKER:
                        String dataString = data.getStringExtra(DataPickerFragment.RESPONSE_STRING);
                        selected = data.getParcelableExtra(DataPickerFragment.ARG_SELECTED);
                        for (ContainerInterface container : list) {
                            LogUtil.info(this, "Catch " + (container == selected));
                            if (container == selected) {
                                ((TextContent) container.getContent()).textChanged(dataString);
                                container.getContent().setDefault(false);
                                container.init(getContext());
                                container.invalidate();
                                break;
                            }
                        }

                    case REQUEST_CODE_EDIT_TEXT:
                        String textString = data.getStringExtra(EditTextDialogFragment.RESPONSE_STRING);
                        selected = data.getParcelableExtra(EditTextDialogFragment.ARG_SELECTED);
                        for (ContainerInterface container : list) {
                            if (container == selected) {
                                ((TextContent) container.getContent()).textChanged(textString);
                                container.getContent().setDefault(false);
                                container.init(getContext());
                                container.invalidate();
                                break;
                            }
                        }
                    default:
                }
            }
        }
    }

    @Override
    public void onChanged(String content) {
        setTextToTitle(content);
    }

    public void setOverlayBitmap(Bitmap overlayBitmap) {
        this.overlayBitmap = overlayBitmap;
    }
}