package com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public interface ParentInterface {
    void addContainer(@Nullable ContainerInterface containerInterface);
    ContainerInterface findEntityAtPoint(float x, float y);
    void notifyView();
    PointF getCoordinatesResizePoint();
    void setCoordinatesResizePoint(float x, float y);
    void setCoordinatesDelPoint(float x, float y);
    void setCoordinatesAddPoint(float x, float y);
    int getColorForFilter();
    void setColorForFilter(int color);

    float getMarginControl();
    float getMinContainerWidth();
    float getMinContainerHeight();
    boolean isActive();
    void drawParentBorder(Canvas canvas);
    void setActiveContainer(ContainerInterface activeContainer);

    int getWidth();
    int getHeight();
    Resources getResources();

    void setBorderColor(int color);
    void setSelectedBorderColor(int color);
    void setPreview(boolean isPreview);
    //parentView.setBackgroundResource(android.R.color.transparent);
    void clear();
    Context getContext();
    void setBackground(Drawable drawable);
    void setBackgroundDrawable(Drawable drawable);
    ArrayList<ContainerInterface> getContainers();
    ContainerInterface getSelectedContainer();
    ContentListenerInterface getChangeContentInterface();
}