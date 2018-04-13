package com.llc111minutes.gameday.util;

/**
 * Created by Yurii on 1/11/17.
 */

public class MathUtils {
    public static float range(int percentage, float start, float end) {
        return (end - start) * percentage / 100.0f + start;
    }
}