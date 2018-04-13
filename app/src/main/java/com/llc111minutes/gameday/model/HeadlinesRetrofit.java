package com.llc111minutes.gameday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeadlinesRetrofit {

    @SerializedName("headlines")
    @Expose
    private List<String> headlines = null;

    public List<String> getHeadlines() {
        return headlines;
    }

    public void setHeadlines(List<String> headlines) {
        this.headlines = headlines;
    }

}
