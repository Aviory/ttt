
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Overlay implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("color")
    @Expose
    private Color color;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("small")
    @Expose
    private String small;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    @Override
    public String toString() {
        return "Overlay{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", original='" + original + '\'' +
                ", medium='" + medium + '\'' +
                ", small='" + small + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.color, flags);
        dest.writeString(this.original);
        dest.writeString(this.medium);
        dest.writeString(this.small);
    }

    public Overlay() {
    }

    protected Overlay(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.color = in.readParcelable(Color.class.getClassLoader());
        this.original = in.readString();
        this.medium = in.readString();
        this.small = in.readString();
    }

    public static final Parcelable.Creator<Overlay> CREATOR = new Parcelable.Creator<Overlay>() {
        @Override
        public Overlay createFromParcel(Parcel source) {
            return new Overlay(source);
        }

        @Override
        public Overlay[] newArray(int size) {
            return new Overlay[size];
        }
    };
}
