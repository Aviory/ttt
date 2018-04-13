package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yurii on 2/20/17.
 */

public class Overlays implements Parcelable {
    @SerializedName("overlays")
    @Expose
    private List<Overlay> overlays = null;

    public List<Overlay> getOverlays() {
        return overlays;
    }

    public void setOverlays(List<Overlay> overlays) {
        this.overlays = overlays;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.overlays);
    }

    public Overlays() {
    }

    protected Overlays(Parcel in) {
        this.overlays = in.createTypedArrayList(Overlay.CREATOR);
    }

    public static final Parcelable.Creator<Overlays> CREATOR = new Parcelable.Creator<Overlays>() {
        @Override
        public Overlays createFromParcel(Parcel source) {
            return new Overlays(source);
        }

        @Override
        public Overlays[] newArray(int size) {
            return new Overlays[size];
        }
    };
}
