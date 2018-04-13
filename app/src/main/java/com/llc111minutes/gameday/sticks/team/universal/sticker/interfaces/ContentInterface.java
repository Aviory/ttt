package com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Parcelable;

import com.llc111minutes.gameday.model.enums.ContentType;

public interface ContentInterface extends Parcelable{
    void init(Context context);
    void oneTap(Context context);
    void doubleTap(Context context);
    void changeParentSize(float width, float height);
    void setContainer(ContainerInterface container);
    ContainerInterface getContainer();
    void drawContent(Canvas canvas, Path path, RectF rectF);
    void release();

    boolean isChangeWidth(float width);
    boolean isChangeHeight(float height);

    void setMutable(boolean mutable);

    boolean isDefault();

    void setType(ContentType type);
    ContentType getType();

    void setData(String data);
    String getData();
    void setContentColor(int color);
    int getContentColor();
    void invalidate();
    void invalidate(boolean change);

    void setDefault(boolean b);
}