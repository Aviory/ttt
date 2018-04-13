package com.llc111minutes.gameday.model;

import android.os.Parcelable;

/**
 * Created by jack on 16.03.17.
 */

public abstract class StickChangeData implements Parcelable {

    StickChangeData() {
    }

    abstract public Image getImage();

    abstract public String getName();

    abstract public Integer getId();

}
