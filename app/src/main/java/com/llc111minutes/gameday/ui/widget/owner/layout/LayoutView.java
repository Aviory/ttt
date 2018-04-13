package com.llc111minutes.gameday.ui.widget.owner.layout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.events.Events;
import com.llc111minutes.gameday.interfaces.Callback;
import com.llc111minutes.gameday.interfaces.Callback2;
import com.llc111minutes.gameday.model.ViewData;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.ui.adapter.RecyclerLayoutAdapter;
import com.llc111minutes.gameday.ui.adapter.RecyclerLayoutFlowAdapter;
import com.llc111minutes.gameday.ui.widget.owner.AddElementView;
import com.llc111minutes.gameday.ui.widget.owner.AddTeamLogo;
import com.llc111minutes.gameday.ui.widget.owner.SubscribeViewFrameLayout;
import com.llc111minutes.gameday.util.AnimUtil;
import com.llc111minutes.gameday.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by NewUser on 12/29/16.
 */

public class LayoutView extends SubscribeViewFrameLayout implements Callback<Integer> {

    private final static String ORIGINAL_VIEWS = "com.matchday.android.ui.widget.owner.layout.originalviews";

    private RecyclerView recyclerView, recyclerFlow;
    private RecyclerLayoutAdapter adapterLayout;
    private RecyclerLayoutFlowAdapter recyclerLayoutFlowAdapter;
    private LinearLayoutManager llm, llmFlow;
    private ArrayList<ViewData> originalViews = new ArrayList<>();
    private boolean isAnim;
    private int mPosition;
    protected View tvClearAll, tvAddElements;


    private final static int DURATION = 300;

    public LayoutView(Context context) {
        super(context);
        init();
    }

    public LayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public int getLayout() {
        return R.layout.view_layout_menu;
    }

    @Override
    public void init() {
        super.init();
        setId(R.id.layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerFlow = (RecyclerView) findViewById(R.id.recyclerviewFlow);
        recyclerLayoutFlowAdapter = new RecyclerLayoutFlowAdapter();

        llm = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        llmFlow = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerFlow.setAdapter(recyclerLayoutFlowAdapter);

        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerFlow.setLayoutManager(llmFlow);
        recyclerFlow.setHasFixedSize(true);
    }

    public boolean isAddedTeamLogo() {
        return findViewById(R.id.view_add_title_logo) != null;
    }

    public void addTeamLogo() {
        AddTeamLogo addTeamLogo = new AddTeamLogo(getContext());
        addTeamLogo.setId(R.id.view_add_title_logo);
        addView(addTeamLogo);
        addTeamLogo.setX(recyclerView.getX());
        addTeamLogo.setY(recyclerView.getY());
        addTeamLogo.getLayoutParams().width = recyclerView.getWidth();
        addTeamLogo.getLayoutParams().height = recyclerView.getHeight();
    }

    public void removeTeamLogo() {
        ((AddTeamLogo) findViewById(R.id.view_add_title_logo)).removeSumSelf();
    }

    private void loadData() {
        adapterLayout = new RecyclerLayoutAdapter();
        adapterLayout.setSourceBitmap(getFilterMenuOpetation().getSourceBitmap());
        adapterLayout.setOverlays(getFilterMenuOpetation().getPack().getAllOverlays());
        adapterLayout.setmMenuItemTemplateList(getFilterMenuOpetation().getPack().getAllTemplatesGroups());
        adapterLayout.setActiveTemplate(getFilterMenuOpetation().getTemplate());
        adapterLayout.setCallback(this);
        recyclerView.setAdapter(adapterLayout);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtil.info(this, "onAttachedToWindow");
        loadData();
        tvClearAll = findViewById(R.id.tvClearAll);
        tvAddElements = findViewById(R.id.tvAddElements);

        RxClick.addDelayOnPressedListener(this, tvAddElements);
        RxClick.addDelayOnPressedListener(this, tvClearAll);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        tvClearAll.setEnabled(getFilterMenuOpetation().getParentView().getContainers().size() > 0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events.ChangeContentEvent event) {
        tvClearAll.setEnabled(event.getSize() > 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAddElements:
                ViewGroup flControls = (ViewGroup) findViewById(R.id.flControls);
                if (flControls.findViewById(R.id.view_add_element) == null) {
                    AddElementView addElementView = new AddElementView(getContext());
                    AnimUtil.attachAlpha(flControls, addElementView);
                }
                break;
            case R.id.tvClearAll:
                getFilterMenuOpetation().clearAll();
                break;
        }
    }

    protected View getItemViewFromId(int id) {
        return llm.findViewByPosition(id);
    }

    public boolean isAnim() {
        return isAnim;
    }

    public void setAnim(boolean anim) {
        isAnim = anim;
        findViewById(R.id.btnFocus).setVisibility(anim ? VISIBLE : GONE);
    }

    @Override
    public void onResult(Integer id) {
        if (isAnim()) return;
        setAnim(true);
        mPosition = id;
        adapterLayout.setActiveTemplate(getFilterMenuOpetation().getTemplate());
        startAddAnimation(id);
    }

    private void startEndAnimation() {
        setAnim(true);

        recyclerView.getLayoutManager().onInitializeAccessibilityEvent(AccessibilityEvent.obtain());

        float touchViewOriginalX = 0;
        for (int i = 0; i < originalViews.size(); i++) {
            if (originalViews.get(i).isSelected()) {
                touchViewOriginalX = originalViews.get(i).getOldPoint().x;
                break;
            }
        }

        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList<Animator> animators = new ArrayList<>();

        for (int i = 0; i < llmFlow.findLastVisibleItemPosition() - llmFlow.findFirstCompletelyVisibleItemPosition(); i++) {
            View v = llmFlow.findViewByPosition(llmFlow.findFirstVisibleItemPosition()+i);
            v.setVisibility(VISIBLE);
            ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(v, "x", v.getX(), touchViewOriginalX);
            moveAnimator.setDuration(DURATION);
            animators.add(moveAnimator);
        }

        for (int i = 0; i < originalViews.size(); i++) {
            ViewData vData = originalViews.get(i);
            View v = llm.findViewByPosition(vData.getPosition());
            if (v == null) continue;
            v.setVisibility(VISIBLE);

            ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(v, "x", v.getX(), vData.getOldPoint().x);
            moveAnimator.setDuration(DURATION);
            animators.add(moveAnimator);

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(v, "alpha", 0, 1f);
            alphaAnimator.setDuration(DURATION);
            animators.add(alphaAnimator);

            if (vData.isSelected()) {
                ObjectAnimator backAnimator = ObjectAnimator.ofFloat(v.findViewById(R.id.flBack), "alpha", 0.9f, 0f);
                backAnimator.setDuration(DURATION);
                animators.add(backAnimator);
            }
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                recyclerView.setVisibility(VISIBLE);
                recyclerView.animate().alpha(1).setDuration(DURATION).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                recyclerFlow.setVisibility(INVISIBLE);
                llmFlow = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerFlow.setLayoutManager(llmFlow);
                recyclerLayoutFlowAdapter = new RecyclerLayoutFlowAdapter();
                if (getFilterMenuOpetation().getTemplate() != null) {
                    recyclerLayoutFlowAdapter.setIdActiveTemplate(getFilterMenuOpetation().getTemplate().getId());
                } else {
                    recyclerLayoutFlowAdapter.setIdActiveTemplate(-1);
                }
                recyclerFlow.setAdapter(recyclerLayoutFlowAdapter);
                recyclerLayoutFlowAdapter.notifyDataSetChanged();
                recyclerFlow.invalidateItemDecorations();
                recyclerFlow.invalidate();
                recyclerFlow.requestLayout();
                recyclerFlow.requestDisallowInterceptTouchEvent(false);

                llm.setAutoMeasureEnabled(true);
                llm.requestSimpleAnimationsInNextLayout();
                recyclerView.invalidate();
                recyclerView.requestLayout();

                originalViews.clear();

                setAnim(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.playTogether(animators);
        animatorSet.start();

    }

    private void startAddAnimation(Integer id) {
        recyclerFlow.getLayoutManager().onInitializeAccessibilityEvent(AccessibilityEvent.obtain());
        recyclerLayoutFlowAdapter.notifyDataSetChanged();
        recyclerLayoutFlowAdapter.setName(getFilterMenuOpetation().getPack().getAllTemplatesGroups().get(id).getName());
        recyclerLayoutFlowAdapter.setOverlays(getFilterMenuOpetation().getPack().getAllOverlays());
        recyclerLayoutFlowAdapter.setSourceBitmap(getFilterMenuOpetation().getSourceBitmap());
        if (getFilterMenuOpetation().getTemplate() != null) {
            recyclerLayoutFlowAdapter.setIdActiveTemplate(getFilterMenuOpetation().getTemplate().getId());
        } else {
            recyclerLayoutFlowAdapter.setIdActiveTemplate(-1);
        }
        recyclerLayoutFlowAdapter.setIndex(llm.findLastCompletelyVisibleItemPosition() - llm.findFirstCompletelyVisibleItemPosition());
        recyclerLayoutFlowAdapter.setCallbackBind(new Callback2<RecyclerLayoutFlowAdapter, Integer>() {
            @Override
            public void onResult(RecyclerLayoutFlowAdapter recyclerLayoutFlowAdapter, Integer id) {
                LogUtil.info(this, "LLM: width: " + llmFlow.getWidth() + " height: " + llmFlow.getHeight() );
                recyclerLayoutFlowAdapter.setCallbackBind(null);

                View touchView = getItemViewFromId(mPosition);

                int firstPosition = llm.findFirstVisibleItemPosition();
                int lastPosition = llm.findLastVisibleItemPosition();

                //originalViews = new ArrayList<>();
                ArrayList<View> viewsToStart = new ArrayList<>();
                ArrayList<View> viewsToEnd = new ArrayList<>();

                for (int i = firstPosition; i <= lastPosition; i++) {
                    View v = getItemViewFromId(i);
                    if (i < mPosition) {
                        viewsToStart.add(v);
                    } else if (i > mPosition) {
                        viewsToEnd.add(v);
                    }
                    originalViews.add(new ViewData(new PointF(v.getX(), v.getY()), i, touchView.equals(v)));
                }

                ObjectAnimator startAnimation = ObjectAnimator.ofFloat(recyclerFlow, "x", touchView.getX(),
                        0);

                ObjectAnimator endAnimation = null;

                ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(recyclerView, "alpha", 1f, 0f);

                toStartAllCreateAnimation(touchView, startAnimation, alphaAnimation, endAnimation);
            }

            @Override
            public void onError(RecyclerLayoutFlowAdapter recyclerLayoutFlowAdapter, Throwable e) {

            }
        });

        recyclerLayoutFlowAdapter.setCallback(new Callback<Integer>() {
            @Override
            public void onResult(Integer integer) {
                if (integer == 0) {
                    recyclerLayoutFlowAdapter.setCallback(null);
                    startEndAnimation();
                } else {
                    getFilterMenuOpetation().setTemplate(getFilterMenuOpetation()
                            .getPack()
                            .getAllTemplatesGroups()
                            .get(mPosition)
                            .getTemplates().get(integer - 1));
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
        recyclerLayoutFlowAdapter.setTemplates(getFilterMenuOpetation()
                .getPack()
                .getAllTemplatesGroups()
                .get(mPosition)
                .getTemplates());
        recyclerFlow.setVisibility(VISIBLE);
        recyclerLayoutFlowAdapter.notifyDataSetChanged();

    }

    private void toStartAllCreateAnimation(View touchView,
                                           ObjectAnimator startAnimation,
                                           ObjectAnimator alphaAnimation,
                                           ObjectAnimator endAnimation) {
        if (llmFlow.getItemCount() >= llm.findLastCompletelyVisibleItemPosition() - llm.findFirstCompletelyVisibleItemPosition()) {
            recyclerFlow.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (llmFlow.getItemCount() < llm.findLastCompletelyVisibleItemPosition() - llm.findFirstCompletelyVisibleItemPosition()) return;
                    view.removeOnLayoutChangeListener(this);
                    toAnim(touchView, startAnimation, endAnimation, alphaAnimation);
                }
            });
        } else {
            toAnim(touchView, startAnimation, endAnimation, alphaAnimation);
        }

    }

    private void toAnim(View touchView,
                        ObjectAnimator startAnimation,
                        ObjectAnimator endAnimation,
                        ObjectAnimator alphaAnimation) {
        View flBack = touchView.findViewById(R.id.flBack);
        flBack.setAlpha(0);
        flBack.setVisibility(VISIBLE);
        ObjectAnimator backAlphaAnimator = ObjectAnimator.ofFloat(flBack, "alpha", 0f, 0.9f);

        AnimatorSet animatorSet = new AnimatorSet();

        backAlphaAnimator.setDuration(DURATION);

        if (endAnimation != null) {
            endAnimation.setDuration(DURATION);
            animatorSet.play(startAnimation)
                    .with(endAnimation)
                    .with(alphaAnimation)
                    .with(backAlphaAnimator);
        } else {
            animatorSet.play(startAnimation)
                    .with(alphaAnimation)
                    .with(backAlphaAnimator);
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setVisibility(INVISIBLE);

                        llmFlow.setAutoMeasureEnabled(true);
                        llmFlow.requestSimpleAnimationsInNextLayout();
                        recyclerFlow.invalidate();
                        recyclerFlow.requestLayout();

                        recyclerLayoutFlowAdapter.notifyDataSetChanged();
                        if (getFilterMenuOpetation().getTemplate() != null) {
                            recyclerLayoutFlowAdapter.setIdActiveTemplate(getFilterMenuOpetation().getTemplate().getId());
                        } else {
                            recyclerLayoutFlowAdapter.setIdActiveTemplate(-1);
                        }

                        setAnim(false);
                    }
                }, 5);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();

    }

    class AnimCreateChild {
        private View parentView;
        private View childView;

        public AnimCreateChild(View parentView, View childView) {
            this.parentView = parentView;
            this.childView = childView;
        }

        public void setX(float x) {
            //LogUtil.info("Child: id:" + childView.getX());
            childView.setX(parentView.getX() + x);
        }
    }

    class AnimViewToStart {
        private List<View> views;
        private View touchView;

        public AnimViewToStart(List<View> views, View touchView) {
            this.views = views;
            this.touchView = touchView;
        }

        public void setX(float x) {
            float difference = touchView.getX() - x;
            touchView.setX(x);

            for (int i = 0; i < views.size(); i++) {
                views.get(i).setX(views.get(i).getX() - difference);
            }
        }
    }

    class AnimViewToEnd {
        private List<View> views;

        public AnimViewToEnd(List<View> views) {
            this.views = views;
        }

        public void setX(float x) {
            float difference = views.get(0).getX();
            views.get(0).setX(x);

            for (int i = 1; i < views.size(); i++) {
                views.get(i).setX(views.get(i).getX() + difference);
            }
        }
    }

    class AnimAlpfa {
        List<View> views;

        public AnimAlpfa(List<View> views) {
            this.views = views;
        }

        public void setAlpha(float alpha) {
            for (View view : views) {
                view.setAlpha(alpha);
            }
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    public void removeListChioceTemplates() {
        recyclerView.setVisibility(VISIBLE);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.SUPER_STATE, super.onSaveInstanceState());
        bundle.putParcelableArrayList(ORIGINAL_VIEWS, originalViews);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.originalViews = bundle.getParcelableArrayList(ORIGINAL_VIEWS);
            if (originalViews == null) originalViews = new ArrayList<>();
            state = bundle.getParcelable(Const.SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        tvClearAll.setEnabled(getFilterMenuOpetation().getParentView().getContainers().size() > 0);
    }
}