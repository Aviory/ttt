package com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Parcelable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;

import com.llc111minutes.gameday.sticks.team.universal.sticker.RectCoordinate;

public interface ContainerInterface extends Parcelable{

    // coordinates on Canvas
    float getX();
    float getY();
    void setX(float x);
    void setY(float y);
    void offsetTo(float x, float y);

    @FloatRange(from = 0.0F, to = 360.0F)
    float getRotate();
    void setRotate(float rotate);

    float getScale();
    void setScale(float scale);

    float getDiffDiagonal(float x3, float y3);
    void setCoord(float x3, float y3, float diff);

    void addContent(ContentInterface content);
    ContentInterface getContent();

    // Возвращаем родителя;
    ParentInterface getParent();
    // Устанавливаем родителя
    void setParent(ParentInterface parent);

    RectCoordinate getRectCoordinate();

    // возвращает является ли вьюха выбранной
    boolean isSelected();
    // устанавливаем вьюху выбранной
    void setIsSelected(boolean selected);
    boolean isActive();
    void setActive(boolean move);
    // in motion
    boolean isMotion();
    void setMotion(boolean motion);

    // возвращает центр вьюхи по оси Х
    float absoluteCenterX();
    // возвращает центр вьюхи по оси Y
    float absoluteCenterY();
    // возвращает центр вьюхи
    PointF absoluteCenter();
    // переносит центр вьюхи в точку
    void moveCenterTo(PointF moveToCenter);
    // рисуем себя сами на канвасе
    void draw(@NonNull Canvas canvas, @NonNull Paint paintBorder);
    // Перерисовать себя
    void invalidate();

    void drawSelectedBg(Canvas canvas, @NonNull Paint paintBorder);
    // возвращаем ширину
    float getWidth();
    // возвращаем высоту
    float getHeight();
    // устанавливаем ширину
    void setWidth(float width);
    // устанавливаем высоту
    void setHeight(float height);
    // очищаем ресурсы
    void release();

    void onTap(Context context);
    void onDoubleTap(Context context);

    void init(Context context);

    void changeSize(float width, float height);

    boolean contains(float x, float y);
    ContentListenerInterface getChangeContentListener();
}
