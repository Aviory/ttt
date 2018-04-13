
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shadow implements Parcelable {

    @SerializedName("x")
    @Expose
    private Integer x;
    @SerializedName("y")
    @Expose
    private Integer y;
    @SerializedName("color")
    @Expose
    private Color color;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Shadow{" +
                "x=" + x +
                ", y=" + y +
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
        dest.writeParcelable(this.color, flags);
    }

    public Shadow() {
    }

    protected Shadow(Parcel in) {
        this.x = (Integer) in.readValue(Integer.class.getClassLoader());
        this.y = (Integer) in.readValue(Integer.class.getClassLoader());
        this.color = in.readParcelable(Color.class.getClassLoader());
    }

    public static final Parcelable.Creator<Shadow> CREATOR = new Parcelable.Creator<Shadow>() {
        @Override
        public Shadow createFromParcel(Parcel source) {
            return new Shadow(source);
        }

        @Override
        public Shadow[] newArray(int size) {
            return new Shadow[size];
        }
    };
}
