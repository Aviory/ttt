
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metainfo implements Parcelable {

    @SerializedName("areIconsShown")
    @Expose
    private Boolean areIconsShown;
    @SerializedName("team1")
    @Expose
    private Team team;
    @SerializedName("team2")
    @Expose
    private Team team2;
    @SerializedName("separator_title")
    @Expose
    private SeparatorTitle separatorTitle;
    @SerializedName("separator_line")
    @Expose
    private SeparatorLine separatorLine;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("location")
    @Expose
    private Place place;
    @SerializedName("headline")
    @Expose
    private Headline headline;
    @SerializedName("time")
    @Expose
    private Time time;

    public Boolean getAreIconsShown() {
        return areIconsShown;
    }

    public void setAreIconsShown(Boolean areIconsShown) {
        this.areIconsShown = areIconsShown;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public SeparatorTitle getSeparatorTitle() {
        return separatorTitle;
    }

    public void setSeparatorTitle(SeparatorTitle separatorTitle) {
        this.separatorTitle = separatorTitle;
    }

    public SeparatorLine getSeparatorLine() {
        return separatorLine;
    }

    public void setSeparatorLine(SeparatorLine separatorLine) {
        this.separatorLine = separatorLine;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Place getPlace() {
        return place;
    }


    public void setPlace(Place place) {
        this.place = place;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Metainfo{" +
                "areIconsShown=" + areIconsShown +
                ", com.llc111minutes.gameday.sticks.team=" + team +
                ", team2=" + team2 +
                ", separatorTitle=" + separatorTitle +
                ", separatorLine=" + separatorLine +
                ", date=" + date +
                ", place=" + place +
                ", headline=" + headline +
                ", time=" + time +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.areIconsShown);
        dest.writeParcelable(this.team, flags);
        dest.writeParcelable(this.team2, flags);
        dest.writeParcelable(this.separatorTitle, flags);
        dest.writeParcelable(this.separatorLine, flags);
        dest.writeParcelable(this.date, flags);
        dest.writeParcelable(this.place, flags);
        dest.writeParcelable(this.headline, flags);
        dest.writeParcelable(this.time, flags);
    }

    public Metainfo() {
    }

    protected Metainfo(Parcel in) {
        this.areIconsShown = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.team = in.readParcelable(Team.class.getClassLoader());
        this.team2 = in.readParcelable(Team.class.getClassLoader());
        this.separatorTitle = in.readParcelable(SeparatorTitle.class.getClassLoader());
        this.separatorLine = in.readParcelable(SeparatorLine.class.getClassLoader());
        this.date = in.readParcelable(Date.class.getClassLoader());
        this.place = in.readParcelable(Place.class.getClassLoader());
        this.headline = in.readParcelable(Headline.class.getClassLoader());
        this.time = in.readParcelable(Time.class.getClassLoader());
    }

    public static final Parcelable.Creator<Metainfo> CREATOR = new Parcelable.Creator<Metainfo>() {
        @Override
        public Metainfo createFromParcel(Parcel source) {
            return new Metainfo(source);
        }

        @Override
        public Metainfo[] newArray(int size) {
            return new Metainfo[size];
        }
    };
}
