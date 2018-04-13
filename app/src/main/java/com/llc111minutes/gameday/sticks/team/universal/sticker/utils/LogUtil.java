package com.llc111minutes.gameday.sticks.team.universal.sticker.utils;

import android.util.Log;

/**
 * Created by Yurii on 2/3/17.
 */

public class LogUtil {
    public static void info(Object o, String message) {
        Log.d(o.getClass().getSimpleName(), message);
    }

    public static void info (String message) {
        Log.d("Sticker", message);
    }
}