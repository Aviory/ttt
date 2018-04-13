package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HeadlineTitle extends StickChangeData implements Parcelable {

    private String name;
    private int id;

    public HeadlineTitle(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
    }

    public HeadlineTitle() {
    }

    protected HeadlineTitle(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<HeadlineTitle> CREATOR = new Creator<HeadlineTitle>() {
        @Override
        public HeadlineTitle createFromParcel(Parcel source) {
            return new HeadlineTitle(source);
        }

        @Override
        public HeadlineTitle[] newArray(int size) {
            return new HeadlineTitle[size];
        }
    };
}
