
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place implements Parcelable {

    @SerializedName("default_text")
    @Expose
    private String defaultText;
    @SerializedName("x")
    @Expose
    private Double x;
    @SerializedName("y")
    @Expose
    private Double y;
    @SerializedName("font")
    @Expose
    private Font font;

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }


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

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public String toString() {
        return "Place{" +
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
        dest.writeValue(this.x);
        dest.writeValue(this.y);
        dest.writeParcelable(this.font, flags);
    }

    public Place() {
    }

    protected Place(Parcel in) {
        this.defaultText = in.readString();
        this.x = (Double) in.readValue(Double.class.getClassLoader());
        this.y = (Double) in.readValue(Double.class.getClassLoader());
        this.font = in.readParcelable(Font.class.getClassLoader());
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
