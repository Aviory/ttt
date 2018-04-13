package com.llc111minutes.gameday.ui.widget.change_color_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.util.LogUtil;

public class CircleView extends View {

    OnChangeColor mListener;

    Paint paint;
    Bitmap bitmapAddColor, bitmapChoose;
    int width, height, radius;
    int padding = 16;
    private final int count = 12;
    int vWidth;
    int vHeight;
    int indexX, indexY = 0;
    int indexPressed = 1;
    private int selected = 1;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmapAddColor, vWidth / 2 - bitmapAddColor.getWidth() / 2, vHeight / 2 - bitmapAddColor.getHeight() / 2, null);

        for (int i = 1; i <= count; i++) {
            paint.setColor(ColorUtil.instance().getColorFromCollections(i - 1));
            if (i < count / 2) {
                indexX = i;
                indexY = 1;
            } else {
                indexX = i - count / 2;
                indexY = 2;
            }
            canvas.drawCircle(vWidth * indexX + vWidth / 2, vHeight * indexY - vHeight / 2, radius, paint);
        }

        if (indexPressed == 0) {
            if (mListener != null) {
                addPickerToParent();
            }
        } else {
            if (selected != indexPressed) {
                selected = indexPressed;
                if (mListener != null) {
                    mListener.changeColor(ColorUtil.instance().getColorFromCollections(selected - 1));
                }
            }
            indexX = selected >= count / 2 ? vWidth * (selected - count / 2) : vWidth * selected;
            indexY = selected >= count / 2 ? vHeight + padding : padding;
            //    canvas.drawBitmap(bitmapChoose, indexX + padding, indexY + padding, null);
            canvas.drawBitmap(bitmapChoose, indexX + (vWidth - bitmapChoose.getWidth()) / 2, indexY - padding + (vHeight - bitmapChoose.getHeight()) / 2, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            width = getWidth();
            height = getHeight();

            vWidth = width / 6;
            vHeight = height / 2;

            radius = Math.min(vHeight, vWidth) / 2 - padding;

            bitmapAddColor = getBitmap(R.drawable.ic_add_color);
            bitmapAddColor = Bitmap.createScaledBitmap(bitmapAddColor, radius * 2, radius * 2, true);

            bitmapChoose = getBitmap(R.drawable.ic_apply_check);
            bitmapChoose = Bitmap.createScaledBitmap(bitmapChoose, bitmapChoose.getWidth() - padding, bitmapChoose.getHeight() - padding, true);
        }
    }

    private void addPickerToParent() {
        ViewGroup view;
        if ((view = (ViewGroup) getParent()) instanceof ColorChooser) {
            // view.removeView(this);
            view.addView(getPicker());
        }
    }

    public CustomColorPickerView getPicker() {
        CustomColorPickerView picker = new CustomColorPickerView(getContext());
        picker.setListener(color -> {
            mListener.changeColor(color);
       });
        return picker;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                indexX = (int) (event.getX() / vWidth);
                indexY = (int) (event.getY() / vHeight);
                indexPressed = indexY > 0 ? indexX + count / 2 : indexX;
                invalidate();
        }
        return true;
    }

    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public interface OnChangeColor {
        void changeColor(int color);
    }

    /**
     * Definition listener to transfer color to the top of the hierarchy
     *
     * @param startColor color for initial picker
     * @param onPressed  the touch listener to attach to this view
     */
    public void setOnListener(int startColor, OnChangeColor onPressed) {
        revisionColors(startColor);
        mListener = onPressed;
    }

    /**
     * Checking the Color in the Collection
     * Checking the Color in the Collection
     * If there is no color, then adds to the collection and makes it active
     *
     * @param startColor - start color of content
     */
    public void revisionColors(int startColor) {
        ColorUtil.instance();
        boolean enableColor = false;
        for (int i = 1; i < count & !enableColor; i++) {
            LogUtil.info(this, "startColor: " + startColor + ", in array: " + ColorUtil.instance().getColorFromCollections(i));
            if (ColorUtil.instance().getColorFromCollections(i) == startColor) {
                indexPressed = i + 1;
                enableColor = true;
                break;
            }
        }
        if (!enableColor) {
            ColorUtil.instance().addNewColor(startColor);
            indexPressed=1;
        }
        invalidate();
    }
}
