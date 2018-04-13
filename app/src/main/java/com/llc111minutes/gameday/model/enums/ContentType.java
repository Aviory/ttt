package com.llc111minutes.gameday.model.enums;


import com.llc111minutes.gameday.R;

public enum ContentType {
    HEADLINE(0, true, R.string.headline),
    TEAM1(1, true, R.string.team),
    TEAM1LOGO(2, true, R.string.logo),
    TEAM2(3, true, R.string.team),
    TEAM2LOGO(4, true, R.string.logo),
    SEPARATOR_TITLE(5, true, R.string.vs),
    PLACE(6, true, R.string.place),
    USER_HEADLINE(7, false, R.string.headline),
    USER_TEAM(8, false, R.string.team),
    USER_TEAMLOGO(9, false, R.string.logo),
    USER_PLACE(10, false, R.string.place),
    DATE(11, true, R.string.date),
    TIME(12, true, R.string.time),
    USER_DATE(13, false, R.string.date),
    USER_TIME(14, false, R.string.time);

    private int nameStringRes;
    private int index;
    private boolean isTemplate;

    ContentType(int index, boolean isTemplate, int nameStringResource) {
        this.index = index;
        this.isTemplate = isTemplate;
        this.nameStringRes = nameStringResource;
    }

    public int getIndex() {
        return index;
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public int getNameStringRes() {
        return nameStringRes;
    }

}

