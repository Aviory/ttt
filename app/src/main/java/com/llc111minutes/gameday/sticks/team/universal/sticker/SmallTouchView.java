package com.llc111minutes.gameday.sticks.team.universal.sticker;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.interfaces.Callback2;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContainerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContentListenerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ParentInterface;

import java.util.ArrayList;

/**
 * Created by Yurii on 2/24/17.
 */

public class SmallTouchView extends View implements ParentInterface {

    private Paint paintBorder = new Paint();
    private float marginDott, strokeWidth;
    private float[] dottedArray = new float[2];
    private ArrayList<ContainerInterface> containers = new ArrayList<>();
    private boolean square = false;
    private Callback2 callback2;
    private boolean drawBorder;
    private int colorFilter = Integer.MIN_VALUE;

    public SmallTouchView(Context context) {
        super(context);
        init(context, null);
    }

    public SmallTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SmallTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        setWillNotDraw(false);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SmallTouchView, 0 ,0);
        try {
            square = a.getBoolean(R.styleable.SmallTouchView_square, false);
        } finally {
            a.recycle();
        }

        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setColor(Color.WHITE);
        setFocusable(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWillNotDraw(false);
    }

    @Override
    public void addContainer(@NonNull ContainerInterface containerInterface) {
        containers.add(containerInterface);
        containerInterface.setParent(this);
        containerInterface.init(getContext());
        //notifyView();
    }

    @Override
    public ContainerInterface findEntityAtPoint(float x, float y) {
        return null;
    }

    @Override
    public void notifyView() {
        invalidate();
    }

    @Override
    public PointF getCoordinatesResizePoint() {
        return null;
    }

    @Override
    public void setCoordinatesResizePoint(float x, float y) {

    }

    @Override
    public void setCoordinatesDelPoint(float x, float y) {

    }

    @Override
    public void setCoordinatesAddPoint(float x, float y) {

    }

    @Override
    public int getColorForFilter() {
        return colorFilter;
    }

    @Override
    public void setColorForFilter(int color) {
        this.colorFilter = color;
    }

    @Override
    public float getMarginControl() {
        return 0;
    }

    @Override
    public float getMinContainerWidth() {
        return 0;
    }

    @Override
    public float getMinContainerHeight() {
        return 0;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (square) {
            setMeasuredDimension(getMeasuredHeight()-getPaddingTop(), getMeasuredHeight() - getPaddingTop()); // Snap to width
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            strokeWidth = getWidth()/150;
            paintBorder.setStrokeWidth(strokeWidth);

            marginDott = getWidth() / 40;

            dottedArray[0] = marginDott;
            dottedArray[1] = marginDott;

            PathEffect pe1 = new DashPathEffect(dottedArray, 0);
            //PathEffect pe2 = new CornerPathEffect(marginDott);
            //PathEffect pe3 = new ComposePathEffect(pe1, pe2);

            paintBorder.setPathEffect(pe1);

        }

        if (callback2!=null && right > 0 && bottom > 0) {
            callback2.onResult(this, changed);
            callback2=null;
        }
    }

    public void setCallback2(Callback2 callback2) {
        this.callback2 = callback2;
    }

    @Override
    public void drawParentBorder(Canvas canvas) {
        if (drawBorder)
        canvas.drawRect(strokeWidth/2, strokeWidth/2, canvas.getWidth()-strokeWidth/2,
                canvas.getHeight()-strokeWidth/2, paintBorder);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintBorder.setStrokeWidth(strokeWidth/2);
        for (ContainerInterface containerInterface : containers) {
            containerInterface.draw(canvas, paintBorder);
        }
        paintBorder.setStrokeWidth(strokeWidth);
        drawParentBorder(canvas);
    }

    @Override
    public void setActiveContainer(ContainerInterface activeContainer) {

    }

    @Override
    public void setBorderColor(int color) {

    }

    @Override
    public void setSelectedBorderColor(int color) {

    }

    @Override
    public void setPreview(boolean isPreview) {

    }

    @Override
    public void clear() {
        containers.clear();
    }

    @Override
    public ArrayList<ContainerInterface> getContainers() {
        return containers;
    }

    @Override
    public ContainerInterface getSelectedContainer() {
        return null;
    }

    @Override
    public ContentListenerInterface getChangeContentInterface() {
        return null;
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

}