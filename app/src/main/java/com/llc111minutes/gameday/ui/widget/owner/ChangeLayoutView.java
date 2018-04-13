package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.sticks.team.universal.sticker.TextContent;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContainerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.SelectEventListener;
import com.llc111minutes.gameday.ui.widget.change_color_views.CircleView;
import com.llc111minutes.gameday.ui.widget.change_color_views.ColorChooser;
import com.llc111minutes.gameday.ui.widget.change_color_views.CustomColorPickerView;
import com.llc111minutes.gameday.util.AnimUtil;
import com.llc111minutes.gameday.util.FontsUtil;
import com.llc111minutes.gameday.util.LogUtil;

public class ChangeLayoutView extends RelativeLayout implements View.OnClickListener, ColorChooser.OnColorChange, ChangeFontsView.OnFontChange {
    private SelectEventListener mActionListener;

    enum STATE {FONT, COLOR}

    private STATE myState = STATE.FONT;
    private FrameLayout flContainer;
    private CustomFontTextView vFont;
    private CustomFontTextView vColor;

    private ChangeFontsView mChangeFontsView;
    private ColorChooser mColorChooser;
    private ContainerInterface mContainer;
    private int oldColor;
    private boolean isStarted = false;

    public ChangeLayoutView(Context context) {
        super(context);
        init();
    }

    public ChangeLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChangeLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setId(R.id.change_layout_view);
        inflate(getContext(), R.layout.view_change_layout, this);

        flContainer = (FrameLayout) findViewById(R.id.flContainer);

        findViewById(R.id.vClear).setOnClickListener(this);
        findViewById(R.id.vAccept).setOnClickListener(this);
        vFont = (CustomFontTextView) findViewById(R.id.tv_first);
        vFont.setOnClickListener(this);
        vColor = (CustomFontTextView) findViewById(R.id.tv_second);
        vColor.setOnClickListener(this);

        setBackgroundResource(R.color.color_black);
        setClickable(true);
        setFocusable(true);
    }

    public void initState() {
        switch (myState) {
            case FONT:
                mChangeFontsView = null;
                mChangeFontsView = new ChangeFontsView(getContext());
                mChangeFontsView.setListener(this);

                FontsUtil.Font font = ((TextContent) mContainer.getContent()).getFont();
                mChangeFontsView.setStartFont(font);
                int style = ((TextContent) mContainer.getContent()).getStyle();
                mChangeFontsView.setStartStyle(style);
                mChangeFontsView.initRightGroupButton(font, style);
                mChangeFontsView.initLeftGroup((TextContent) mContainer.getContent());

                flContainer.removeAllViews();
                flContainer.addView(mChangeFontsView);
                vFont.setTextColor(Color.RED);
                vColor.setTextColor(Color.WHITE);
                break;
            case COLOR:
                mColorChooser = null;
                mColorChooser = new ColorChooser(getContext());
                mColorChooser.setListener(this);
                mColorChooser.setStartColor(mContainer.getContent().getContentColor());
                flContainer.removeAllViews();
                flContainer.addView(mColorChooser);
                vFont.setTextColor(Color.WHITE);
                vColor.setTextColor(Color.RED);
                break;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isStarted) return;
        isStarted = true;
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        startAnimation(fadeIn);

        initState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        if (isStarted) return;
        isStarted = true;
        initState();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isStarted = false;
    }

    public ContainerInterface getContainer() {
        return mContainer;
    }

    public void setContainerToStick(ContainerInterface containerToStick) {
        mContainer = containerToStick;
        oldColor = containerToStick.getContent().getContentColor();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_second:
                if (!myState.equals(STATE.COLOR)) {
                    myState = STATE.COLOR;
                    initState();
                }
                break;
            case R.id.tv_first:
                if (!myState.equals(STATE.FONT)) {
                    myState = STATE.FONT;
                    initState();
                }
                break;
            case R.id.vAccept:
                //

                if (mColorChooser != null) {
                    View v = mColorChooser.getActiveChild();
                    if (v instanceof CircleView) {
                        // mActionListener.unSelected(mContainer);
                        AnimUtil.detachAlpha(this);
                        LogUtil.info(this, "Circle!!!!!");
                    } else if (v instanceof CustomColorPickerView) {
                        ((ViewGroup) v.getParent()).removeView(v);
                        LogUtil.info(this, "Picker!!!!!");
                        CircleView circleView = (CircleView) mColorChooser.getActiveChild();
                        circleView.revisionColors(mContainer.getContent().getContentColor());
                    }
                } else {
                    AnimUtil.detachAlpha(this);
                    LogUtil.info(this, "mColorChooser - null!!!!!");
                }
                break;
            case R.id.vClear:
                if (mColorChooser != null) setColor(oldColor);
                if (mChangeFontsView != null) {
                    LogUtil.info(this, "Old font: " + mChangeFontsView.getStartFont());
                    setFont(mChangeFontsView.getStartFont());
                    setTypeFace(mChangeFontsView.getStartFont().getId(), mChangeFontsView.getStartStyle());
                }
                mActionListener.unSelected(mContainer);
                AnimUtil.detachAlpha(this);
                break;
        }
    }

    /**
     * Callback from the ColorChooser view to apply to the text in template
     *
     * @param color
     */
    @Override
    public void setColor(int color) {
        mContainer.getContent().setContentColor(color);
    }

    @Override
    public void setFont(FontsUtil.Font font) {
        if (mContainer.getContent() instanceof TextContent) {
            LogUtil.info(this, "Change font: " + font);
            TextContent textContent = ((TextContent) mContainer.getContent());
            textContent.setFont(font);
            textContent.init(getContext());
            textContent.invalidate(true);
            textContent.getContainer().getParent().notifyView();
            textContent = null;
        }
    }

    @Override
    public void setTypeFace(int id, int style) {
        if (mContainer.getContent() instanceof TextContent) {
            TextContent textContent = ((TextContent) mContainer.getContent());
            textContent.setTypeFace(FontsUtil.get(getContext(), id, style), style);
            textContent.invalidate(true);
            textContent.getContainer().getParent().notifyView();
        }
    }

    @Override
    public void setShadowFont() {
        if (mContainer.getContent() instanceof TextContent) {
            TextContent textContent = ((TextContent) mContainer.getContent());
            textContent.setShadow();
            textContent.invalidate(true);
            textContent.getContainer().getParent().notifyView();
        }
    }

    @Override
    public void setBackgroundFont() {
        if (mContainer.getContent() instanceof TextContent) {
            TextContent textContent = ((TextContent) mContainer.getContent());
            textContent.setBackground();
            textContent.invalidate(true);
            textContent.getContainer().getParent().notifyView();
        }
    }

    @Override
    public void setWrapperFont() {
        if (mContainer.getContent() instanceof TextContent) {
            TextContent textContent = ((TextContent) mContainer.getContent());
            textContent.setWrapper();
            textContent.invalidate(true);
            textContent.getContainer().getParent().notifyView();
        }
    }

    /**
     * Set Listener for action to apply or close this View
     *
     * @param selectEventListener - {@link SelectEventListener}
     */
    public void setListenerAction(SelectEventListener selectEventListener) {
        mActionListener = selectEventListener;
    }
}
