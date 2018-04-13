package com.llc111minutes.gameday.ui.widget.owner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.Template;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.sticks.team.universal.sticker.Placer;
import com.llc111minutes.gameday.sticks.team.universal.sticker.SmallTouchView;
import com.llc111minutes.gameday.util.FileUtil;
import com.llc111minutes.gameday.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yurii on 2/23/17.
 */

public class AddTeamLogo extends SubscribeViewFrameLayout {

    @BindView(R.id.smallOne)
    protected SmallTouchView smallOne;
    @BindView(R.id.smallTwo)
    protected SmallTouchView smallTwo;
    @BindView(R.id.smallThree)
    protected SmallTouchView smallThree;

    public AddTeamLogo(Context context) {
        super(context);
        init();
    }

    public AddTeamLogo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddTeamLogo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void init() {
        super.init();
        ButterKnife.bind(this);
        setClickable(true);
        setFocusable(false);
    }

    protected void setSizeDemoView(SmallTouchView view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = getHeight();
        view.setLayoutParams(params);
    }

    @Override
    public void onStart() {
        if (isBind) return;

        RxClick.addDelayOnPressedListener(this, smallOne);
        RxClick.addDelayOnPressedListener(this, smallTwo);
        RxClick.addDelayOnPressedListener(this, smallThree);

        isBind = true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.info("bla");
        if (changed && getWidth() > 0 && smallOne!=null) {
            addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    removeOnLayoutChangeListener(this);

                    setSizeDemoView(smallOne);
                    setSizeDemoView(smallTwo);
                    setSizeDemoView(smallThree);

                    smallOne.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                            removeOnLayoutChangeListener(this);
                            Placer.placeTemplate(getFirstTemplate(), smallOne);
                        }
                    });

                    smallTwo.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                            removeOnLayoutChangeListener(this);
                            Placer.placeTemplate(getSecondTemplate(), smallTwo);
                        }
                    });

                    smallThree.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                            removeOnLayoutChangeListener(this);
                            Placer.placeTemplate(getThirdTemplate(), smallThree);
                        }
                    });

                }
            });
            /*Container container = new Container();
            ImageContent imageContent = new ImageContent();
            container.addContent(imageContent);
            container.changeSize(100, 100);
            smallOne.addContainer(container);
            smallTwo.addContainer(container);
            smallThree.addContainer(container);
            container.init(getContext());*/
        }
    }

    protected Template getFirstTemplate() {
        return new Gson().fromJson(FileUtil.getStringFromFile(getContext().getAssets(),
                "templates/team_logo/template_team1.json"), Template.class);
        /*Template template = new Template();
        Metainfo metainfo = new Metainfo();
        Team com.llc111minutes.gameday.sticks.team = new Team();
        Logo logo = new Logo();
        logo.setH(0.2d);
        logo.setW(0.3d);
        logo.setX(0.5d);
        logo.setY(0.5d);
        com.llc111minutes.gameday.sticks.team.setLogo(logo);
        metainfo.setTeam(com.llc111minutes.gameday.sticks.team);
        template.setMetainfo(metainfo);
        LogUtil.info(this, "T1: " + new Gson().toJson(template));
        return template;*/
    }

    protected Template getSecondTemplate() {
        return new Gson().fromJson(FileUtil.getStringFromFile(getContext().getAssets(),
                "templates/team_logo/template_team2.json"), Template.class);
        /*Template template = new Template();
        Metainfo metainfo = new Metainfo();
        Team com.llc111minutes.gameday.sticks.team = new Team();
        Logo logo = new Logo();
        logo.setH(0.2d);
        logo.setW(0.3d);
        logo.setX(0.3d);
        logo.setY(0.5d);
        com.llc111minutes.gameday.sticks.team.setLogo(logo);

        Team team2 = new Team();
        Logo logo2 = new Logo();
        logo2.setH(0.2d);
        logo2.setW(0.3d);
        logo2.setX(0.7d);
        logo2.setY(0.5d);
        team2.setLogo(logo2);

        metainfo.setTeam(com.llc111minutes.gameday.sticks.team);
        metainfo.setTeam2(team2);
        template.setMetainfo(metainfo);
        LogUtil.info(this, "T2: " + new Gson().toJson(template));
        return template;*/
    }

    protected Template getThirdTemplate() {
        return new Gson().fromJson(FileUtil.getStringFromFile(getContext().getAssets(),
                "templates/team_logo/template_team3.json"), Template.class);
        /*Template template = new Template();
        Metainfo metainfo = new Metainfo();
        Team com.llc111minutes.gameday.sticks.team = new Team();
        Logo logo = new Logo();
        logo.setH(0.2d);
        logo.setW(0.3d);
        logo.setX(0.25d);
        logo.setY(0.5d);
        com.llc111minutes.gameday.sticks.team.setLogo(logo);

        Team team2 = new Team();
        Logo logo2 = new Logo();
        logo2.setH(0.2d);
        logo2.setW(0.3d);
        logo2.setX(0.75d);
        logo2.setY(0.5d);
        team2.setLogo(logo2);

        SeparatorTitle separatorTitle = new SeparatorTitle();
        separatorTitle.setDefaultText("VS");
        separatorTitle.setX(0.5d);
        separatorTitle.setY(0.5d);
        Font font = new Font();
        font.setSize(0.1f);

        Color color = new Color();
        color.setAlpha("1");
        color.setBlue("255");
        color.setGreen("255");
        color.setRed("255");
        font.setColor(color);
        font.setName("Lato-Black");

        separatorTitle.setFont(font);

        metainfo.setSeparatorTitle(separatorTitle);
        metainfo.setTeam(com.llc111minutes.gameday.sticks.team);
        metainfo.setTeam2(team2);
        template.setMetainfo(metainfo);
        LogUtil.info(this, "T3: " + new Gson().toJson(template));
        return template;*/
    }

    @Override
    public int getLayout() {
        return R.layout.view_add_team_logo;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.smallOne:
                Placer.placeTemplate(getFirstTemplate(), filterMenuOpetation.getParentView());
                break;
            case R.id.smallTwo:
                Placer.placeTemplate(getSecondTemplate(), filterMenuOpetation.getParentView());
                break;
            case R.id.smallThree:
                Placer.placeTemplate(getThirdTemplate(), filterMenuOpetation.getParentView());
                break;
        }
    }
}
