package com.llc111minutes.gameday.ui.widget.change_color_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.util.BitmapUtils;

public class BackgroundQuad extends View {

    private OnColorGradientChangedListener mListener;

    final private float circleRadius = 32;
    final private int circlePadding = 5;

    private Paint paint;


    final float[] color = {1.f, 1.f, 1.f};
    int rgbB = Color.BLUE;

    float circleX = circleRadius;
    float circleY = circleRadius;
    int width, height;
    public int pixel;

    public BackgroundQuad(Context context) {
        this(context, null);
    }

    public BackgroundQuad(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public BackgroundQuad(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        // setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.1f));
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_black));
        color[0] = 360.f - 360.f / getMeasuredHeight() * 100;
        if (color[0] == 360.f) color[0] = 0.f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (paint == null) {
            paint = new Paint();
        }
        // Set Type gradient background
        Shader luar = new LinearGradient(
                0.f,
                0.f,
                0.f,
                this.getMeasuredHeight() - circleRadius + circlePadding / 2,
                0xffffffff, 0xff000000, Shader.TileMode.CLAMP);
        Shader dalam = new LinearGradient(
                circleRadius - circlePadding / 2,
                circleRadius - circlePadding / 2,
                this.getMeasuredWidth() - circleRadius + circlePadding / 2,
                this.getMeasuredHeight() - circleRadius + circlePadding / 2,
                0xffffffff, rgbB, Shader.TileMode.CLAMP);
        ComposeShader shader = new ComposeShader(luar, dalam, PorterDuff.Mode.OVERLAY);
        paint.setShader(shader);
        canvas.drawRect(
                circleRadius - circlePadding / 2,
                circleRadius - circlePadding / 2,
                this.getMeasuredWidth() - circleRadius + circlePadding / 2,
                this.getMeasuredHeight() - circleRadius + circlePadding / 2,
                paint);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circlePadding);
        canvas.drawCircle(circleX, circleY, circleRadius - circlePadding / 2, circlePaint);
    }

    public void setMyColor(int color) {
        rgbB = color;
        invalidate();
    }

    //////////////////////////////////////////////////


    public void setImageColor(int c) {
        pixel = c;
        invalidate();
    }

    /////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
            }
            case MotionEvent.ACTION_MOVE: {
                circleX = event.getX();
                circleY = event.getY();

                if (circleX < circleRadius) {                              //top_left
                    circleX = circleRadius;
                    if (circleY < circleRadius) {
                        circleY = circleRadius;
                    } else if (circleY > height - circleRadius) {
                        circleY = height - circleRadius;
                    }
                } else if (circleY < circleRadius) {                         //top_right
                    circleY = circleRadius;
                    if (circleX > width - circleRadius) {
                        circleX = width - circleRadius;
                    }
                } else if (circleX > width - circleRadius) {              //bottom_right
                    circleX = width - circleRadius;
                    if (circleY > height - circleRadius) {
                        circleY = height - circleRadius;
                    }
                } else if (circleY > height - circleRadius) {              //bottom_left
                    circleY = height - circleRadius;
                    if (circleX < circleRadius) {
                        circleX = circleRadius;
                        circleY = height - circleRadius;
                    }
                }
                mListener.colorGradientChanged(getColorFromPixel());
                invalidate();
            }
            break;
        }
        return true;
    }

    public int getColorFromPixel() {
        Bitmap bitmap = BitmapUtils.getBitmapFromView(this);
        if (bitmap != null) {
            pixel = bitmap.getPixel((int) circleX, (int) circleY);
        }
        return pixel;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            width = getWidth();
            height = getHeight();
        }
    }

    public void setListener(CustomColorPickerView customColorPickerView) {
        mListener = customColorPickerView;
    }

    /**
     * interface for return change color
     */
    public interface OnColorGradientChangedListener {
        void colorGradientChanged(int color);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mListener == null)
            mListener = (OnColorGradientChangedListener) getParent();
    }

    @Override
    protected void onDetachedFromWindow() {
        mListener = null;
        super.onDetachedFromWindow();
    }
}
