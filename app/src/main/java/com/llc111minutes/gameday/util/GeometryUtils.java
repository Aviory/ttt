package com.llc111minutes.gameday.util;

import android.graphics.PointF;

public class GeometryUtils {
    /**
     * The method calculates the distance between points
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double getDistance (float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static float getAngle(PointF center, float x, float y) {
        return (float) Math.toDegrees(Math.atan2(x - center.x, y - center.y));
    }
}
