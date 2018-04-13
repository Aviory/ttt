package com.llc111minutes.gameday.interfaces;

/**
 * Created by NewUser on 12/20/16.
 */

public interface ChildViewInterface {
    void setActive(boolean isActive);
    boolean isActive();
    void upscale(float difference);
    void downscale(float difference);
    void increaseAngle(float difference);
    void reduceAngle(float difference);
}
