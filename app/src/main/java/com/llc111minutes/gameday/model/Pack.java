package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Pack implements Parcelable {
    private List<MenuItemTemplate> allTemplatesGroups;
    private List<TeamInfo> allTeams;
    private List<Overlay> allOverlays;
    private List<Stadias> allStadias;
    private long timeLoad;
    private List<HeadlineTitle> allHeadlines;

    public List<MenuItemTemplate> getAllTemplatesGroups() {
        return allTemplatesGroups;
    }

    public void setAllTemplatesGroups(List<MenuItemTemplate> allTemplatesGroups) {
        this.allTemplatesGroups = allTemplatesGroups;
    }

    public List<TeamInfo> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<TeamInfo> allTeams) {
        this.allTeams = allTeams;
    }

    public List<Overlay> getAllOverlays() {
        return allOverlays;
    }

    public void setAllOverlays(List<Overlay> allOverlays) {
        this.allOverlays = allOverlays;
    }

    public List<Stadias> getAllStadias() {
        return allStadias;
    }

    public void setAllStadias(List<Stadias> allStadias) {
        this.allStadias = allStadias;
    }

    public List<HeadlineTitle> getAllHeadlines() {
        return allHeadlines;
    }

    public void setAllHeadlines(List<HeadlineTitle> list) {
        this.allHeadlines = list;
    }

    public long getTimeLoad() {
        return timeLoad;
    }

    public void setTimeLoad(long timeLoad) {
        this.timeLoad = timeLoad;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.allTemplatesGroups);
        dest.writeList(this.allTeams);
        dest.writeList(this.allOverlays);
        dest.writeList(this.allStadias);
        dest.writeLong(this.timeLoad);
        dest.writeList(this.allHeadlines);
    }

    public Pack() {
    }

    protected Pack(Parcel in) {
        this.allTemplatesGroups = new ArrayList<MenuItemTemplate>();
        in.readList(this.allTemplatesGroups, MenuItemTemplate.class.getClassLoader());
        this.allTeams = new ArrayList<TeamInfo>();
        in.readList(this.allTeams, TeamInfo.class.getClassLoader());
        this.allOverlays = new ArrayList<Overlay>();
        in.readList(this.allOverlays, Overlay.class.getClassLoader());
        this.allStadias = new ArrayList<Stadias>();
        in.readList(this.allStadias, Stadias.class.getClassLoader());
        this.timeLoad = in.readLong();
        this.allHeadlines = new ArrayList<HeadlineTitle>();
        in.readList(this.allHeadlines,HeadlineTitle.class.getClassLoader());
    }

    public static final Parcelable.Creator<Pack> CREATOR = new Parcelable.Creator<Pack>() {
        @Override
        public Pack createFromParcel(Parcel source) {
            return new Pack(source);
        }

        @Override
        public Pack[] newArray(int size) {
            return new Pack[size];
        }
    };

}