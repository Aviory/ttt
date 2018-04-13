package com.llc111minutes.gameday.model;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yurii on 3/13/17.
 */

public class ViewData implements Parcelable {
    private PointF oldPoint, afterChangePoint;
    private int position;
    private boolean isSelected;

    public ViewData(PointF point, int position, boolean isSelected) {
        this.oldPoint = point;
        this.position = position;
        this.isSelected = isSelected;
    }

    public void setAfterChangePoint(PointF afterChangePoint) {
        this.afterChangePoint = afterChangePoint;
    }

    public PointF getOldPoint() {
        return oldPoint;
    }

    public void setOldPoint(PointF oldPoint) {
        this.oldPoint = oldPoint;
    }

    public PointF getAfterChangePoint() {
        return afterChangePoint;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.oldPoint, flags);
        dest.writeParcelable(this.afterChangePoint, flags);
        dest.writeInt(this.position);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected ViewData(Parcel in) {
        this.oldPoint = in.readParcelable(PointF.class.getClassLoader());
        this.afterChangePoint = in.readParcelable(PointF.class.getClassLoader());
        this.position = in.readInt();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<ViewData> CREATOR = new Creator<ViewData>() {
        @Override
        public ViewData createFromParcel(Parcel source) {
            return new ViewData(source);
        }

        @Override
        public ViewData[] newArray(int size) {
            return new ViewData[size];
        }
    };
}
