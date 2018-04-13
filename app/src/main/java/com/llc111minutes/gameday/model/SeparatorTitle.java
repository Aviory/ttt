package com.llc111minutes.gameday.model;

/**
 * Created by marina on 07.09.16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeparatorTitle implements Parcelable {

    @SerializedName("default_text")
    @Expose
    private String defaultText;
    @SerializedName("x")
    @Expose
    private double x;
    @SerializedName("y")
    @Expose
    private double y;
    @SerializedName("font")
    @Expose
    private Font font;

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public String toString() {
        return "SeparatorTitle{" +
                "defaultText='" + defaultText + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", font=" + font +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.defaultText);
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
        dest.writeParcelable(this.font, flags);
    }

    public SeparatorTitle() {
    }

    protected SeparatorTitle(Parcel in) {
        this.defaultText = in.readString();
        this.x = in.readDouble();
        this.y = in.readDouble();
        this.font = in.readParcelable(Font.class.getClassLoader());
    }

    public static final Parcelable.Creator<SeparatorTitle> CREATOR = new Parcelable.Creator<SeparatorTitle>() {
        @Override
        public SeparatorTitle createFromParcel(Parcel source) {
            return new SeparatorTitle(source);
        }

        @Override
        public SeparatorTitle[] newArray(int size) {
            return new SeparatorTitle[size];
        }
    };
}
