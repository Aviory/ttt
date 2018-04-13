
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team implements Parcelable {

    @SerializedName("logo")
    @Expose
    private Logo logo;
    @SerializedName("title")
    @Expose
    private Title title;
    public Logo getLogo() {
        return logo;
    }
    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Team{" +
                "logo=" + logo +
                ", title=" + title +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.logo, flags);
        dest.writeParcelable(this.title, flags);
    }

    public Team() {
    }

    protected Team(Parcel in) {
        this.logo = in.readParcelable(Logo.class.getClassLoader());
        this.title = in.readParcelable(Title.class.getClassLoader());
    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}