package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.sticks.team.universal.sticker.TextContent;
import com.llc111minutes.gameday.ui.adapter.RecyclerFontAdapter;
import com.llc111minutes.gameday.util.FontsUtil;
import com.llc111minutes.gameday.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.BOLD_ITALIC;
import static android.graphics.Typeface.ITALIC;
import static android.graphics.Typeface.NORMAL;

public class ChangeFontsView extends RelativeLayout {
    @BindView(R.id.btn_bold)
    protected ImageButton mBtnBold;
    @BindView(R.id.btn_italic)
    protected ImageButton mBtnItalic;
    @BindView(R.id.btn_bold_italic)
    protected ImageButton mBtnBoldItalic;
    @BindView(R.id.btn_wraper)
    protected ImageButton mBtnWrapper;
    @BindView(R.id.btn_background)
    protected ImageButton mBtnBackground;
    @BindView(R.id.btn_shadow)
    protected ImageButton mBtnShadow;
    private RecyclerView recyclerView;
    private OnFontChange mListener;
    private FontsUtil.Font mOldFont;
    private FontsUtil.Font mFont;
    private RecyclerFontAdapter mFontAdapter;
    private int mOldStyle;

    public ChangeFontsView(Context context) {
        super(context);
        init();
    }

    public ChangeFontsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChangeFontsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_change_font, this);
        ButterKnife.bind(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mFontAdapter = new RecyclerFontAdapter(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundResource(R.color.color_black);
    }

    public void setFont(FontsUtil.Font font) {
        mFont = font;
        initRightGroupButton(font, Typeface.NORMAL);
        if (mListener != null) {
            mListener.setFont(mFont);
        }
    }

    public void setStartFont(FontsUtil.Font startFont) {
        mOldFont = mFont = startFont;
        mFontAdapter.setFont(mFont);
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(mFontAdapter);
        }
    }

    public FontsUtil.Font getStartFont() {
        return mOldFont;
    }

    public void setStartStyle(int style) {
        mOldStyle = style;
    }

    public int getStartStyle() {
        return mOldStyle;
    }

    public void setListener(OnFontChange listener) {
        mListener = listener;
    }

    @OnClick({R.id.btn_bold, R.id.btn_bold_italic, R.id.btn_italic, R.id.btn_shadow, R.id.btn_background, R.id.btn_wraper})
    void onClick(ImageButton v) {
        switch (v.getId()) {
            case R.id.btn_shadow:
                v.setActivated(!v.isActivated());
                mListener.setShadowFont();
                break;
            case R.id.btn_background:
                v.setActivated(!v.isActivated());
                mListener.setBackgroundFont();

                break;
            case R.id.btn_wraper:
                v.setActivated(!v.isActivated());
                mListener.setWrapperFont();
                break;
            case R.id.btn_bold:
                if (v.isActivated()) {
                    setTypeFaceNormal(v);
                } else {
                    setRightGroupActivated(v);
                    mListener.setTypeFace(mFont.getId(), BOLD);
                }
                break;
            case R.id.btn_italic:
                if (v.isActivated()) {
                    setTypeFaceNormal(v);
                } else {
                    setRightGroupActivated(v);
                    mListener.setTypeFace(mFont.getId(), ITALIC);
                }
                break;
            case R.id.btn_bold_italic:
                if (v.isActivated()) {
                    setTypeFaceNormal(v);
                } else {
                    setRightGroupActivated(v);
                    mListener.setTypeFace(mFont.getId(), BOLD_ITALIC);
                }
                break;
            default:
                LogUtil.info(this, " Не Бряк!!!");
        }
    }

    private void setTypeFaceNormal(ImageButton v) {
        v.setActivated(false);
        mListener.setTypeFace(mFont.getId(), NORMAL);
    }

    /**
     * This method is a selector for a group of font styles as well as applying a style to the sticker
     *
     * @param v - change style
     */
    private void setRightGroupActivated(ImageButton v) {
        if (!v.isActivated()) v.setActivated(true);
        if (mBtnBold.isActivated() && v != mBtnBold) mBtnBold.setActivated(false);
        if (mBtnItalic.isActivated() && v != mBtnItalic) mBtnItalic.setActivated(false);
        if (mBtnBoldItalic.isActivated() && v != mBtnBoldItalic) mBtnBoldItalic.setActivated(false);
    }

    /**
     * The method checks for the presence of styles in the font and disables unused
     *
     * @param font - change font
     */
    public void initRightGroupButton(FontsUtil.Font font, int styleNum) {
        mBtnBold.setEnabled(font.getFontBoldAssets() != null);
        mBtnBold.setActivated(false);
        mBtnBoldItalic.setEnabled(font.getFontBoldItalicAssets() != null);
        mBtnBoldItalic.setActivated(false);
        mBtnItalic.setEnabled(font.getFontItaliAssets() != null);
        mBtnItalic.setActivated(false);
        switch (styleNum) {
            case Typeface.BOLD:
                mBtnBold.setActivated(true);
                break;
            case Typeface.BOLD_ITALIC:
                mBtnBoldItalic.setActivated(true);
                break;
            case Typeface.ITALIC:
                mBtnItalic.setActivated(true);
                break;
        }
    }

    public void initLeftGroup(TextContent content) {
        mBtnShadow.setActivated(content.getShadow());
        mBtnBackground.setActivated(content.getBackground());
        mBtnWrapper.setActivated(content.getWrapper());
    }


    interface OnFontChange {
        /**
         * Callback from the ChangeFontsView view to apply to the text in template
         *
         * @param font - font for set
         */
        void setFont(FontsUtil.Font font);

        /**
         * Callback from the ChangeFontsView view to apply to the text in template
         *
         * @param id    - font id for installation
         * @param style - style of font
         */
        void setTypeFace(int id, int style);

        /**
         * Callback from the ChangeFontsView view to apply a shadow to the text in the template
         */
        void setShadowFont();

        /**
         * Callback from the ChangeFontsView view to apply a Background to the text in the template
         */
        void setBackgroundFont();

        /**
         * Callback from the ChangeFontsView view to apply a wrapper to the text in the template
         */
        void setWrapperFont();
    }
}