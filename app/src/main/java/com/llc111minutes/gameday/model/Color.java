package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Color implements Parcelable {

    @SerializedName("red")
    @Expose
    private String red;
    @SerializedName("green")
    @Expose
    private String green;
    @SerializedName("blue")
    @Expose
    private String blue;
    @SerializedName("alpha")
    @Expose
    private String alpha;

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getBlue() {
        return blue;
    }


    public void setBlue(String blue) {
        this.blue = blue;
    }

    public String getAlpha() {
        return alpha;
    }


    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public static int createColorInt(Color color){
        int red = Integer.parseInt(color.getRed());
        int green = Integer.parseInt(color.getGreen());
        int blue = Integer.parseInt(color.getBlue());
        int alpha = (int) (Float.parseFloat(color.getAlpha()) * 255);
        return android.graphics.Color.argb(alpha, red, green, blue);
    }

    @Override
    public String toString() {
        return "Color{" +
                "red='" + red + '\'' +
                ", green='" + green + '\'' +
                ", blue='" + blue + '\'' +
                ", alpha='" + alpha + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.red);
        dest.writeString(this.green);
        dest.writeString(this.blue);
        dest.writeString(this.alpha);
    }

    public Color() {
    }

    protected Color(Parcel in) {
        this.red = in.readString();
        this.green = in.readString();
        this.blue = in.readString();
        this.alpha = in.readString();
    }

    public static final Parcelable.Creator<Color> CREATOR = new Parcelable.Creator<Color>() {
        @Override
        public Color createFromParcel(Parcel source) {
            return new Color(source);
        }

        @Override
        public Color[] newArray(int size) {
            return new Color[size];
        }
    };
}
