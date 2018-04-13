package com.llc111minutes.gameday.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Yurii on 2/6/17.
 */

public class ActivityUtil {
    public static AppCompatActivity getActivity(Context context) {
        if (context instanceof Activity) {
            return (AppCompatActivity) context;
        }
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (AppCompatActivity) context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

}
