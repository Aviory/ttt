package com.llc111minutes.gameday.util;

import android.util.Log;

/**
 * Created by NewUser on 12/8/16.
 */

public class LogUtil {
    public static void info(Object o, String message){
        if (o instanceof String) {
            Log.d(o.toString(), message);
        } else {
            Log.d(o.getClass().getName(), message);
        }
    }

    public static void info(String message){
        Log.d("Suka: ", message);
    }
}
