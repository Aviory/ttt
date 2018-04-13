
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Logo implements Parcelable {

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

    @Override
    public String toString() {
        return "Logo{" +
                "x=" + x +
                ", y=" + y +
                ", h=" + h +
                ", w=" + w +
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
    }

    public Logo() {
    }

    protected Logo(Parcel in) {
        this.x = (Double) in.readValue(Double.class.getClassLoader());
        this.y = (Double) in.readValue(Double.class.getClassLoader());
        this.h = (Double) in.readValue(Double.class.getClassLoader());
        this.w = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Logo> CREATOR = new Parcelable.Creator<Logo>() {
        @Override
        public Logo createFromParcel(Parcel source) {
            return new Logo(source);
        }

        @Override
        public Logo[] newArray(int size) {
            return new Logo[size];
        }
    };
}
