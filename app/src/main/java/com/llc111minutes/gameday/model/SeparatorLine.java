
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeparatorLine implements Parcelable {

    @SerializedName("x")
    @Expose
    private Double x;
    @SerializedName("y")
    @Expose
    private Double y;
    @SerializedName("h")
    @Expose
    private Double h;
    @SerializedName("w")
    @Expose
    private Double w;
    @SerializedName("color")
    @Expose
    private Color color;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getH() {
        return h;
    }

    public void setH(Double h) {
        this.h = h;
    }

    public Double getW() {
        return w;
    }

    public void setW(Double w) {
        this.w = w;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "SeparatorLine{" +
                "x=" + x +
                ", y=" + y +
                ", h=" + h +
                ", w=" + w +
                ", color=" + color +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.x);
        dest.writeValue(this.y);
        dest.writeValue(this.h);
        dest.writeValue(this.w);
        dest.writeParcelable(this.color, flags);
    }

    public SeparatorLine() {
    }

    protected SeparatorLine(Parcel in) {
        this.x = (Double) in.readValue(Double.class.getClassLoader());
        this.y = (Double) in.readValue(Double.class.getClassLoader());
        this.h = (Double) in.readValue(Double.class.getClassLoader());
        this.w = (Double) in.readValue(Double.class.getClassLoader());
        this.color = in.readParcelable(Color.class.getClassLoader());
    }

    public static final Parcelable.Creator<SeparatorLine> CREATOR = new Parcelable.Creator<SeparatorLine>() {
        @Override
        public SeparatorLine createFromParcel(Parcel source) {
            return new SeparatorLine(source);
        }

        @Override
        public SeparatorLine[] newArray(int size) {
            return new SeparatorLine[size];
        }
    };
}
