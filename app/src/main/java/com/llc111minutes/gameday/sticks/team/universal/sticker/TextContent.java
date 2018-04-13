package com.llc111minutes.gameday.sticks.team.universal.sticker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.llc111minutes.gameday.ui.fragment.dialog.EditTextDialogFragment;
import com.llc111minutes.gameday.util.FontsUtil;
import com.llc111minutes.gameday.util.LogUtil;

public class TextContent extends Content implements EditTextDialogFragment.OnTextLayerCallback,
        Parcelable {
    private final int SHADOW_ALPHA = 150;
    private int mStyle;
    private String contentText = "", tmpText;
    private String oldText = "";
    private float textSize = 10;
    private EditTextDialogFragment textEditorDialogFragment;
    private boolean isEdit;
    protected final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean alpha;
    private Handler handler;
    private final Rect bounds = new Rect();
    private Point textPoint = new Point();
    private PointF offset = new PointF();
    // true - change font to size, false - change size from font
    private FontsUtil.Font font = FontsUtil.Font.LatoBlack;
    private Typeface typeface;
    private PointF contentSize = new PointF();
    private boolean mStateShadow;
    private boolean mStateBackground;
    private boolean mStateWrapper;
    private float margin = 20;

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getTextSize() {
        return textSize;
    }

    @Override
    public void init(Context context) {
        typeface = FontsUtil.get(context, font);
        paint.setTypeface(typeface);
    }

    @Override
    public void oneTap(Context context) {

    }

    @Override
    public void invalidate() {
        if (getContainer().getWidth() * getContainer().getScale() != contentSize.x
                || getContainer().getHeight() * getContainer().getScale() != contentSize.y
                || !contentText.equals(oldText)) {
            changeParentSize(getContainer().getWidth() * getContainer().getScale(), getContainer().getHeight() * getContainer().getScale());
            changeContent();
            contentSize.set(getContainer().getWidth() * getContainer().getScale(), getContainer().getHeight() * getContainer().getScale());
            oldText = contentText;
        }
    }

    @Override
    public void invalidate(boolean change) {
        if (change) {
            changeContent();
            changeParentSize(getContainer().getWidth() * getContainer().getScale(), getContainer().getHeight() * getContainer().getScale());
            changeContent();
        }
    }


    @Override
    public void doubleTap(Context context) {

    }

    @Override
    public void changeParentSize(float width, float height) {
        if (isEdit) return;

        /*if (typeface != null) {
            paint.setTypeface(typeface);
        }*/

        if (isMutable) {
            changeSizeMutable(width, height);
        } else {
            changeSizeImmutable(width, height);
        }
    }

    private void changeSizeMutable(float width, float height) {
        paint.setTextSize(textSize);
        paint.getTextBounds(tmpText, 0, tmpText.length(), bounds);
        int textWidth = bounds.width();
        int textHigh = bounds.height();

        //width = width - getMargin();//margin;// * 2;
        //height = height - getMargin();//margin;// * 2;

        if (width < textWidth || height < textHigh) {
            while (width < textWidth || height < textHigh) {
                textSize--;
                paint.setTextSize(textSize);
                paint.getTextBounds(tmpText, 0, tmpText.length(), bounds);
                textWidth = bounds.width();
                textHigh = bounds.height();
                if (textSize < 20) return;
            }
        } else if (width > textWidth) {
            while (width > textWidth && height > textHigh + getMargin() /*margin*/) {
                textSize++;
                paint.setTextSize(textSize);
                paint.getTextBounds(tmpText, 0, tmpText.length(), bounds);
                textWidth = bounds.width();
                textHigh = bounds.height();
            }
            //textSize--;
        }
    }

    private void changeSizeImmutable(float width, float height) {
        paint.setTextSize(textSize);
        paint.getTextBounds(tmpText, 0, tmpText.length(), bounds);
        int textWidth = bounds.width();
        int textHeight = bounds.height();
        float mWidth = textWidth;// + getMargin();//margin;// * 2;
        float mHeight = textHeight + getMargin();//margin;// * 2;

        if (width != mWidth || height != mHeight) {
            //LogUtil.info(this, "Old width: "+width + " old height: "+height + " new width: "+mWidth + " new height: "+mHeight);
            getContainer().changeSize(mWidth, mHeight);
            getContainer().invalidate();
        }
    }


    private void changeContent() {
        paint.setTextSize(textSize);
        paint.setTypeface(typeface);
        paint.getTextBounds(contentText, 0, contentText.length(), bounds);
        int textWidth = bounds.width();//paint.measureText(contentText);
        paint.getTextBounds("T", 0, 1, bounds);
        int textHigh = bounds.height();

        textPoint.set(textWidth, textHigh);

        if (getContainer() != null) {
            float offsetX = (getContainer().getWidth() * getContainer().getScale()) / 2 - textWidth / 2;
            float offsetY = (getContainer().getHeight() * getContainer().getScale()) / 2 + textHigh / 2;
            offset.set(offsetX, offsetY);
        }
    }

    @Override
    public void drawContent(Canvas canvas, Path path, RectF rectF) {

        if (mStateBackground) applyBackground(canvas, path);
        if (mStateShadow) applyShadow(canvas, path);
        canvas.drawTextOnPath(contentText, path, offset.x, offset.y, paint);
        if (mStateWrapper) applyWrapper(canvas, path);

        if (isEdit) {
            if (alpha) {
                paint.setAlpha(0);
            } else {
                paint.setAlpha(255);
            }
            alpha = !alpha;
            canvas.drawTextOnPath("|", path, offset.x + textPoint.x, offset.y, paint);

            paint.setAlpha(255);
        }
    }

    @Override
    public void textChanged(@NonNull String text) {
        if (TextUtils.isEmpty(text)/* && text.length() > Const.MAXIMUM_CHARS*/) return;
        float oldWidth = paint.measureText(tmpText, 0, tmpText.length());
        contentText = text;
        tmpText = "_" + contentText + "_";
        float newWidth = paint.measureText(tmpText, 0, tmpText.length());

        float differentWidth = newWidth - oldWidth;

        getContainer().setWidth(getContainer().getWidth() + differentWidth / getContainer().getScale());
        getContainer().invalidate();

        getContainer().getParent().notifyView();
        if (getContainer().getChangeContentListener() != null) {
            getContainer().getChangeContentListener().onChanged(text);
        }
    }

    @Override
    public void release() {
        if (textEditorDialogFragment != null) {
            textEditorDialogFragment.dismiss();
            textEditorDialogFragment = null;
        }
    }

    @Override
    public boolean isDefault() {
        return isDefault;
    }

    @Override
    public void setData(String data) {
        this.contentText = data;
        this.tmpText = "_" + data + "_";
    }

    @Override
    public String getData() {
        return contentText;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    // Cursor blink animation
    private Runnable cursorAnimation = new Runnable() {
        public void run() {
            getContainer().invalidate();
            getContainer().getParent().notifyView();
            if (isEdit) {
                handler.postDelayed(cursorAnimation, 1000);
            }
        }
    };


    @Override
    public void setContentColor(int color) {
        super.setContentColor(color);
        paint.setColor(getContentColor());
        if (getContainer() != null) {
            getContainer().invalidate();
            getContainer().getParent().notifyView();
        }
    }

    @Override
    public String toString() {
        return "TextContent{" +
                "contentText='" + contentText + '\'' +
                ", textSize=" + textSize +
                ", isEdit=" + isEdit +
                ", contentColor=" + getContentColor() +
                ", isMutable=" + isMutable +
                ", font=" + font +
                ", typeface=" + typeface +
                '}';
    }

    /*private void setFont() {
        paint.setTypeface(typeface);
        contentSize.set(-1, -1);
        invalidate();
        if (getContainer() != null) {
            getContainer().invalidate();
            getContainer().getParent().notifyView();
        }
    }*/

    public void setFont(FontsUtil.Font font) {
        this.font = font;
        LogUtil.info(this, "Change font: " + this.font);
    }

    public void setFont(String fontName) {
        font = FontsUtil.Font.getFontFromFileName(fontName);
        if (font == null) {
            font = FontsUtil.Font.LatoBlack;
        }
        changeContent();
    }

    public FontsUtil.Font getFont() {
        return font;
    }

    public void setTypeFace(Typeface typeface, int style) {
        this.typeface = typeface;
        mStyle = style;
    }

    public Typeface getTypeface(){
        return typeface;
    }

    public int getStyle() {
        return mStyle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.contentText);
        dest.writeString(this.tmpText);
        dest.writeString(this.oldText);
        dest.writeFloat(this.textSize);
        dest.writeByte(this.isEdit ? (byte) 1 : (byte) 0);
        dest.writeByte(this.alpha ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.textPoint, flags);
        dest.writeParcelable(this.offset, flags);
        dest.writeInt(this.font == null ? -1 : this.font.ordinal());
        dest.writeParcelable(this.contentSize, flags);
    }

    public TextContent() {
    }

    protected TextContent(Parcel in) {
        this.contentText = in.readString();
        this.tmpText = in.readString();
        this.oldText = in.readString();
        this.textSize = in.readFloat();
        this.isEdit = in.readByte() != 0;
        this.alpha = in.readByte() != 0;
        this.textPoint = in.readParcelable(Point.class.getClassLoader());
        this.offset = in.readParcelable(PointF.class.getClassLoader());
        int tmpFont = in.readInt();
        this.font = tmpFont == -1 ? null : FontsUtil.Font.values()[tmpFont];
        this.contentSize = in.readParcelable(PointF.class.getClassLoader());
    }

    public static final Creator<TextContent> CREATOR = new Creator<TextContent>() {
        @Override
        public TextContent createFromParcel(Parcel source) {
            return new TextContent(source);
        }

        @Override
        public TextContent[] newArray(int size) {
            return new TextContent[size];
        }
    };


    public void setShadow() {
        mStateShadow = !mStateShadow;
    }

    public boolean getShadow() {
        return mStateShadow;
    }

    private void applyShadow(Canvas canvas, Path path) {
        Paint shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.set(paint);
        shadowPaint.setColor(Color.BLACK);
        shadowPaint.setAlpha(SHADOW_ALPHA);
        canvas.drawTextOnPath(contentText, path, offset.x + 10, offset.y + 10, shadowPaint);
    }

    public void setBackground() {
        mStateBackground = !mStateBackground;
    }

    public boolean getBackground() {
        return mStateBackground;
    }

    public void applyBackground(Canvas canvas, Path path) {
        Paint paintTmp = new Paint();
        paintTmp.set(paint);
        float[] col = new float[3];
        Color.colorToHSV(paint.getColor(), col);
        paintTmp.setColor(col[2] < 1 ? Color.WHITE : Color.BLACK);
        paintTmp.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paintTmp);
    }

    public void setWrapper() {
        mStateWrapper = !mStateWrapper;
    }

    public boolean getWrapper() {
        return mStateWrapper;
    }

    private void applyWrapper(Canvas canvas, Path path) {
        Paint paintTmp = new Paint();
        paintTmp.set(paint);
        paintTmp.setStyle(Paint.Style.STROKE);
        float[] col = new float[3];
        Color.colorToHSV(paint.getColor(), col);
        paintTmp.setColor(col[2] < 1 ? Color.WHITE : Color.BLACK);
        paintTmp.setStrokeJoin(Paint.Join.ROUND);
        paintTmp.setStrokeWidth(2);
        canvas.drawTextOnPath(contentText, path, offset.x, offset.y, paintTmp);
    }


}
