
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.llc111minutes.gameday.model.enums.Anchor;

public class Title implements Parcelable {


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
    @SerializedName("anchor_point")
    @Expose
    private Anchor archor;

    public Anchor getArchor() {
        return archor;
    }

    public void setArchor(Anchor archor) {
        this.archor = archor;
    }

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
        return "Title{" +
                "defaultText='" + defaultText + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", font=" + font +
                ", archor=" + archor +
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
        dest.writeInt(this.archor == null ? -1 : this.archor.ordinal());
    }

    public Title() {
    }

    protected Title(Parcel in) {
        this.defaultText = in.readString();
        this.x = (Double) in.readValue(Double.class.getClassLoader());
        this.y = (Double) in.readValue(Double.class.getClassLoader());
        this.font = in.readParcelable(Font.class.getClassLoader());
        int tmpArchor = in.readInt();
        this.archor = tmpArchor == -1 ? null : Anchor.values()[tmpArchor];
    }

    public static final Parcelable.Creator<Title> CREATOR = new Parcelable.Creator<Title>() {
        @Override
        public Title createFromParcel(Parcel source) {
            return new Title(source);
        }

        @Override
        public Title[] newArray(int size) {
            return new Title[size];
        }
    };
}
