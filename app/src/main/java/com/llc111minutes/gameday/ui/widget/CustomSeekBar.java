package com.llc111minutes.gameday.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.EditFilter;
import com.llc111minutes.gameday.model.enums.Anchor;
import com.llc111minutes.gameday.util.EditFilterUtils;
import com.llc111minutes.gameday.util.LogUtil;

public class CustomSeekBar extends SeekBar {

    private int magnet;
    private float mAnchorWidth;
    private int mAnchorColor;
    private int mAnchorX;
    private Paint paint;
    private Paint rPaint;
    private int mAnchorProgress;
    private OnSeekBarChangeListener mListener;
    private int grayProgress;
    private int redProgress;

    public CustomSeekBar(Context context) {
        super(context);
        init();
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSeekBar);
        mAnchorWidth = attributes.getDimension(R.styleable.CustomSeekBar_anchor_width, 1);
        mAnchorColor = attributes.getColor(R.styleable.CustomSeekBar_anchor_color, Color.GRAY);
        magnet = attributes.getInt(R.styleable.CustomSeekBar_magnet_percent, 0);
        setPadding(
                getPaddingLeft(),
                getPaddingTop() + (int) (getHeight() + mAnchorWidth),
                getPaddingRight(),
                getPaddingBottom()
        );
        attributes.recycle();
        init();
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        grayProgress = getPaddingLeft();

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(4);
        rPaint = new Paint();
        rPaint.setStyle(Paint.Style.FILL);
        rPaint.setColor(Color.RED);
        rPaint.setStrokeWidth(4);
        invalidate();
    }

    public void setAnchor(int progress) {
        mAnchorProgress = progress;
        float seekBarWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float absolutePosProgress = seekBarWidth / getMax() * progress;
        mAnchorX = (int) (absolutePosProgress + getPaddingLeft());
        invalidate();
    }
    private void grayLine(int progress){
        progress-=mAnchorWidth / 2;
        float seekBarWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float absolutePosProgress = seekBarWidth / getMax() * progress;
        grayProgress = (int) (absolutePosProgress + getPaddingLeft());
        invalidate();
    }
    private void redLine(int progress){
        progress+=mAnchorWidth / 2;
        float seekBarWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float absolutePosProgress = seekBarWidth / getMax() * progress;
        redProgress = (int) (absolutePosProgress + getPaddingLeft());
        invalidate();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(redProgress!=0)
        canvas.drawLine(redProgress, getHeight()/2+getPaddingTop()/2, getWidth()/2, getHeight()/2+getPaddingTop()/2,rPaint);
        canvas.drawLine(getPaddingRight(), getHeight()/2+getPaddingTop()/2, grayProgress, getHeight()/2+getPaddingTop()/2,paint);
        if(getProgress()<47 || getProgress()>53)
        canvas.drawCircle(mAnchorX, mAnchorWidth / 2+getPaddingTop(), mAnchorWidth / 2, paint);
    }

    public void lineInvalidate(int progress){
        if(progress<=50-mAnchorWidth / 2){
            redLine(progress);
        }
        if(progress <= 50 && progress>=mAnchorWidth / 2) {
            grayLine(progress);
        }
        if(progress > mAnchorProgress + magnet){
            grayLine(50+(int)mAnchorWidth / 2);
        }
    }

    @Override
    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
        mListener = l;
        super.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    lineInvalidate(progress);

                    if (progress >= mAnchorProgress - magnet && progress <= mAnchorProgress + magnet) {
                        setProgress(mAnchorProgress);
                        grayLine(mAnchorProgress);
                    }
                    invalidate();
                }
                mListener.onProgressChanged(seekBar, getProgress(), fromUser);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mListener.onStartTrackingTouch(seekBar);
                redProgress = mAnchorX;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mListener.onStopTrackingTouch(seekBar);
            }
        });
    }
}