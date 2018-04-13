package com.llc111minutes.gameday.ui.widget.change_color_views;

import android.graphics.Color;

import java.util.ArrayList;

public class ColorUtil {
    private static ColorUtil colorUtil;

    private ArrayList<Integer> colors;

    public static ColorUtil instance() {
        if (colorUtil == null) {
            colorUtil = new ColorUtil();
       }
        return colorUtil;
    }

    private ColorUtil() {
        colors = new ArrayList<>();
        colors.add(Color.rgb(255, 255, 255));
        colors.add(Color.rgb(60, 3, 5));
        colors.add(Color.rgb(249, 216, 83));
        colors.add(Color.rgb(0, 78, 136));
        colors.add(Color.rgb(0, 196, 158));
        colors.add(Color.rgb(0, 178, 170));
        colors.add(Color.rgb(0, 156, 197));
        colors.add(Color.rgb(197, 44, 43));
        colors.add(Color.rgb(204, 7, 37));
        colors.add(Color.rgb(3, 16, 60));
        colors.add(Color.rgb(55, 55, 55)); //random
        colors.add(Color.rgb(77, 77, 77)); //12
    }

    public int getColorFromCollections(int index) {
        if (index >= colors.size()) {
            return Color.rgb(255, 255, 255);
        }
        return colors.get(index);
    }

    public void addNewColor(int color) {
        for (int i = 0; i < colors.size(); i++) {
            if (color == colors.get(i)) {
                int tmp = colors.get(0);
                colors.set(0, color);
                colors.set(i, tmp);
                return;
            }
        }
        colors.add(0, color);
        colors.remove(colors.size() - 1);
    }


}