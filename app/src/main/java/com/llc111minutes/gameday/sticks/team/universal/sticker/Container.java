package com.llc111minutes.gameday.sticks.team.universal.sticker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContainerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContentInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContentListenerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ParentInterface;
import com.llc111minutes.gameday.ui.widget.LatoBlackTextView;
import com.llc111minutes.gameday.util.GeometryUtils;

import java.util.Arrays;

public class Container implements ContainerInterface, Parcelable {

    private float width = 100;
    private float height = 100;
    private float rotate;
    private float scale = 1.0f;
    //private float x;
    //private float y;
    private boolean active;
    private boolean motion;
    private boolean selected;
    private ContentInterface content;

    //private RectCoordinate rectCoordinate;
    private ParentInterface parent;
    /**
     * transformation matrix for the entity
     */
    protected final Matrix matrix = new Matrix();
    protected RectF rectF;
    protected RectF rectNew = new RectF();
    protected PointF righPoint = new PointF();
    protected final Path path = new Path();
    //protected PathMeasure pMeasure;

    private PointF rightBottomPoint = new PointF();

    //protected final Paint fgPaintSel = new Paint();
    protected float aCoordinates[] = new float[2];
    protected PointF absoluteCenter = new PointF();

    public Container() {
        init();
    }

    public Container(int width, int height) {
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        rectF = new RectF(0, 0, width, height);
    }

    @Override
    public float getX() {
        return rectF.left;
    }

    @Override
    public float getY() {
        return rectF.top;
    }

    @Override
    public void setX(float x) {
        rectF.offsetTo(x, rectF.top);
    }

    @Override
    public void setY(float y) {
        rectF.offsetTo(rectF.left, y);
    }

    @Override
    public void offsetTo(float x, float y) {
        rectF.offsetTo(x, y);
    }

    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        if (rotate > 360f) {
            rotate = rotate - 360f;
        } else if (rotate < 0) {
            rotate = 360 + rotate;
        }
        this.rotate = rotate;
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public void setScale(float scale) {
        if (scale < 0.15f) return;
        this.scale = scale;
        calculateSize();
    }

    @Override
    public float getDiffDiagonal(float x3, float y3) {
        double diagonalOld = GeometryUtils.getDistance(rectF.left, rectF.top,
                rectF.right, rectF.bottom);
        double diagonalNew = GeometryUtils.getDistance(rectF.left, rectF.top,
                x3, y3);
        return (float) (diagonalNew - diagonalOld);
    }


    @Override
    public void setCoord(float x, float y, float diff) {
    }

    @Override
    public void addContent(ContentInterface content) {
        this.content = content;
        content.setContainer(this);
    }

    @Override
    public ContentInterface getContent() {
        return content;
    }

    @Override
    public ParentInterface getParent() {
        return parent;
    }

    @Override
    public void setParent(ParentInterface parent) {
        this.parent = parent;
    }

    @Override
    public RectCoordinate getRectCoordinate() {
        return null;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setIsSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean move) {
        active = move;
    }

    @Override
    public boolean isMotion() {
        return motion;
    }

    @Override
    public void setMotion(boolean motion) {
        this.motion = motion;
    }

    @Override
    public float absoluteCenterX() {
        return rectF.centerX();
    }

    @Override
    public float absoluteCenterY() {
        return rectF.centerY();
    }

    @Override
    public PointF absoluteCenter() {
        return new PointF(absoluteCenterX(), absoluteCenterY());
    }

    @Override
    public void moveCenterTo(PointF moveToCenter) {

    }

    private void calculateSize() {
        matrix.reset();
        matrix.preScale(scale, scale, rectF.centerX(), rectF.centerY());
        matrix.preRotate(rotate, rectF.centerX(), rectF.centerY());
        matrix.mapRect(rectNew, rectF);

        if (active) {
            float[] points = {rectF.left - getParent().getMarginControl() / scale,
                    rectF.top - getParent().getMarginControl() / scale,
                    rectF.right + getParent().getMarginControl() / scale,
                    rectF.bottom - rectF.height() - getParent().getMarginControl() / scale,
                    rectF.right + getParent().getMarginControl() / scale,
                    rectF.bottom + getParent().getMarginControl() / scale,
                    rectF.right, rectF.bottom};
            matrix.mapPoints(points);

            getParent().setCoordinatesDelPoint(points[0], points[1]);
            getParent().setCoordinatesAddPoint(points[2], points[3]);
            getParent().setCoordinatesResizePoint(points[4], points[5]);
            righPoint.set(points[6], points[7]);
            absoluteCenter = absoluteCenter();
        }

        path.reset();
        path.addRect(rectF, Path.Direction.CW);
        path.transform(matrix);
    }

    @Override
    public void draw(@NonNull Canvas canvas, @Nullable Paint paintBorder) {
        if (paintBorder != null) {
            drawBorder(canvas, paintBorder);
        }

        if (content != null) {
            content.drawContent(canvas, path, rectF);
        }
    }

    public void drawBorder(Canvas canvas, Paint paint) {
        canvas.drawPath(path, paint);
    }

    @Override
    public void invalidate() {
        calculateSize();
        if (content != null && content.getContainer() != null) {
            getContent().invalidate();
        }
    }

    @Override
    public void drawSelectedBg(Canvas canvas, Paint paint) {
        drawBorder(canvas, paint);
    }

    @Override
    public float getWidth() {
        return rectF.width();
    }

    @Override
    public float getHeight() {
        return rectF.height();
    }

    @Override
    public void setWidth(float width) {
        rectF.inset((rectF.width() - width) * 0.5f, 0);
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        rectF.offset(0, height - this.height);
        this.height = height;
    }

    @Override
    public void release() {

    }

    @Override
    public void onTap(Context context) {
        if (content != null) {
            content.oneTap(context);
        }
    }

    @Override
    public void onDoubleTap(Context context) {
        if (content != null) {
            content.doubleTap(context);
        }
    }

    @Override
    public void init(Context context) {
        if (content != null) {
            content.init(context);
            content.changeParentSize(rectNew.width(), rectNew.height());
        }
    }

    @Override
    public void changeSize(float width, float height) {
        rectF.inset((getWidth() - width) * 0.5f, (getHeight() - height) * 0.5f);
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean contains(float x, float y) {
        return rectNew.contains(x, y);
    }

    @Override
    public ContentListenerInterface getChangeContentListener() {
        return parent.getChangeContentInterface();
    }

    public Container clone() {
        Container clone = new Container();
        clone.setWidth(getWidth());
        clone.setHeight(getHeight());
        clone.setX(getX());
        clone.setY(getY());
        clone.addContent(getContent());
        return clone;
    }

    @Override
    public String toString() {
        return "Container{" +
                "width=" + width +
                ", height=" + height +
                ", rotate=" + rotate +
                ", x=" + rectF.left +
                ", y=" + rectF.top +
                ", active=" + active +
                ", selected=" + selected +
                ", content=" + content +
                //", rectCoordinate=" + null +
                ", parent=" + parent +
                ", matrix=" + matrix +
                ", rectF=" + rectF +
                //", path=" + path +
                // ", pMeasure=" + pMeasure +
                ", aCoordinates=" + Arrays.toString(aCoordinates) +
                ", absoluteCenter=" + absoluteCenter +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.width);
        dest.writeFloat(this.height);
        dest.writeFloat(this.rotate);
        dest.writeFloat(this.scale);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
        dest.writeByte(this.motion ? (byte) 1 : (byte) 0);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.content, flags);
        dest.writeParcelable(this.rectF, flags);
        dest.writeParcelable(this.rectNew, flags);
        dest.writeParcelable(this.righPoint, flags);
        dest.writeParcelable(this.rightBottomPoint, flags);
        dest.writeFloatArray(this.aCoordinates);
        dest.writeParcelable(this.absoluteCenter, flags);

    }

    protected Container(Parcel in) {
        this.width = in.readFloat();
        this.height = in.readFloat();
        this.rotate = in.readFloat();
        this.scale = in.readFloat();
        this.active = in.readByte() != 0;
        this.motion = in.readByte() != 0;
        this.selected = in.readByte() != 0;
        this.content = in.readParcelable(ContentInterface.class.getClassLoader());
        this.rectF = in.readParcelable(RectF.class.getClassLoader());
        this.rectNew = in.readParcelable(RectF.class.getClassLoader());
        this.righPoint = in.readParcelable(PointF.class.getClassLoader());
        this.rightBottomPoint = in.readParcelable(PointF.class.getClassLoader());
        this.aCoordinates = in.createFloatArray();
        this.absoluteCenter = in.readParcelable(PointF.class.getClassLoader());
    }

    public static final Creator<Container> CREATOR = new Creator<Container>() {
        @Override
        public Container createFromParcel(Parcel source) {
            return new Container(source);
        }

        @Override
        public Container[] newArray(int size) {
            return new Container[size];
        }
    };
}
