package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.enums.ContentType;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.sticks.team.universal.sticker.Container;
import com.llc111minutes.gameday.sticks.team.universal.sticker.Placer;
import com.llc111minutes.gameday.sticks.team.universal.sticker.TextContent;
import com.llc111minutes.gameday.ui.widget.owner.layout.LayoutView;
import com.llc111minutes.gameday.util.AnimUtil;
import com.llc111minutes.gameday.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddElementView extends SubscribeViewFrameLayout {

    @BindView(R.id.tvBack)
    protected ImageView tvBack;
    @BindView(R.id.tvTitleLogo)
    protected TextView tvTitleLogo;
    @BindView(R.id.tvText)
    protected TextView tvText;
    @BindView(R.id.tvDate)
    protected TextView tvDate;
    @BindView(R.id.tvTime)
    protected TextView tvTime;
    @BindView(R.id.tvPlace)
    protected TextView tvPlace;

    public AddElementView(Context context) {
        super(context);
        init();
    }

    public AddElementView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddElementView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public int getLayout() {
        return R.layout.view_add_element;
    }

    @Override
    public void init() {
        super.init();
        setId(R.id.view_add_element);
        setClickable(true);
        setFocusable(false);
        ButterKnife.bind(this);
    }

    @Override
    public void onStart() {
        if (isBind) return;
        setId(R.id.view_add_element);
        initRxClick();
        isBind = true;
    }

    /*@Override
    public void onEnd() {
        isBind = false;
    }*/

    private void initRxClick() {
        RxClick.addDelayOnPressedListener(this, tvBack);
        RxClick.addDelayOnPressedListener(this, tvTitleLogo);
        RxClick.addDelayOnPressedListener(this, tvText);
        RxClick.addDelayOnPressedListener(this, tvDate);
        RxClick.addDelayOnPressedListener(this, tvTime);
        RxClick.addDelayOnPressedListener(this, tvPlace);
    }

    @Override
    public void onClick(View view) {
        Container container = null;
        switch (view.getId()) {
            case R.id.tvBack:
                LayoutView lV = (LayoutView) getParent().getParent().getParent();
                if (lV.isAddedTeamLogo()) lV.removeTeamLogo();
                removeSumSelf();
                break;
            case R.id.tvText:
                container = createContainer(ContentType.USER_HEADLINE,
                        getContext().getString(R.string.text));
                break;
            case R.id.tvTitleLogo:
                LogUtil.info(this, "Parent: " + getParent());
                LogUtil.info(this, "Parent: " + getParent().getParent());
                LogUtil.info(this, "Parent: " + getParent().getParent().getParent());
                LayoutView layoutView = (LayoutView) getParent().getParent().getParent();
                if (!layoutView.isAddedTeamLogo()) {
                    tvTitleLogo.setTextColor(Color.RED);
                    layoutView.addTeamLogo();
                } else {
                    tvTitleLogo.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                    layoutView.removeTeamLogo();
                }
                break;
            case R.id.tvDate:
                container = createContainer(ContentType.USER_DATE,
                        getContext().getString(R.string.date));
                break;
            case R.id.tvTime:
                container = createContainer(ContentType.USER_TIME,
                        getContext().getString(R.string.time));
                break;
            case R.id.tvPlace:
                container = createContainer(ContentType.USER_PLACE,
                        getContext().getString(R.string.place));
                break;
        }

        if (container != null) {
            filterMenuOpetation.getParentView().addContainer(container);
            filterMenuOpetation.getParentView().setActiveContainer(container);

            float x = filterMenuOpetation.getParentView().getWidth() / 2;
            float y = filterMenuOpetation.getParentView().getHeight() / 2;
            container.getContent().setMutable(true);
            Placer.placeStickerAtCenter(x, y, container);
        }
    }

    private Container createContainer(ContentType type, String text) {
        Container container = new Container(200, 100);
        TextContent textContent = new TextContent();
        textContent.setMutable(false);
        textContent.setData(text);
        textContent.setType(type);
        textContent.setContentColor(Color.WHITE);
        textContent.setTextSize(Placer.getFontSize(Const.DEFAULT_TEXT_SIZE,
                filterMenuOpetation.getParentView()));
        container.addContent(textContent);
        return container;
    }

    @Override
    public void removeMain() {
        AnimUtil.detachAlpha(this);
    }
}
