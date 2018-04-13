
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Font implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("size")
    @Expose
    private Float size;
    @SerializedName("color")
    @Expose
    private Color color;
    @SerializedName("shadow")
    @Expose
    private Shadow shadow;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shadow getShadow() {
        return shadow;
    }

    public void setShadow(Shadow shadow) {
        this.shadow = shadow;
    }

    @Override
    public String toString() {
        return "Font{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", color=" + color +
                ", shadow=" + shadow +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.size);
        dest.writeParcelable(this.color, flags);
        dest.writeParcelable(this.shadow, flags);
    }

    public Font() {
    }

    protected Font(Parcel in) {
        this.name = in.readString();
        this.size = (Float) in.readValue(Float.class.getClassLoader());
        this.color = in.readParcelable(Color.class.getClassLoader());
        this.shadow = in.readParcelable(Shadow.class.getClassLoader());
    }

    public static final Parcelable.Creator<Font> CREATOR = new Parcelable.Creator<Font>() {
        @Override
        public Font createFromParcel(Parcel source) {
            return new Font(source);
        }

        @Override
        public Font[] newArray(int size) {
            return new Font[size];
        }
    };
}
