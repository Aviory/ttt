package com.llc111minutes.gameday.sticks.team.universal.sticker;

import android.graphics.Color;

import com.llc111minutes.gameday.model.enums.ContentType;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContainerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContentInterface;

public abstract class Content implements ContentInterface {
    protected ContainerInterface container;
    protected ContentType contentType = ContentType.HEADLINE;
    protected boolean isMutable = true;

    protected boolean isDefault = true;
    private int contentColor = Color.BLACK;

    @Override
    public void setDefault(boolean b) {
        isDefault = b;
    }

    @Override
    public void setMutable(boolean mutable) {
        isMutable = mutable;
    }

    @Override
    public void setContainer(ContainerInterface container) {
        this.container = container;
    }

    @Override
    public ContainerInterface getContainer() {
        return container;
    }

    public boolean isChangeWidth(float width) {
        return getMinWidth() < width;
    }

    @Override
    public boolean isChangeHeight(float height) {
        return getMinHeight() < height;
    }

    @Override
    public void setType(ContentType type) {
        this.contentType = type;
    }

    @Override
    public ContentType getType() {
        return contentType;
    }

    protected float getMinWidth() {
        return getContainer().getParent().getMinContainerWidth();
    }

    protected float getMinHeight() {
        return getContainer().getParent().getMinContainerHeight();
    }

    protected float getMargin() {
        return getContainer().getParent().getMarginControl();
    }

    @Override
    public int getContentColor() {
        return contentColor;
    }

    @Override
    public void setContentColor(int color) {
        this.contentColor = color;
    }
}
