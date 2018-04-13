package com.llc111minutes.gameday.api;

import com.llc111minutes.gameday.model.HeadlineTitle;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesAdapter {
    public static List<HeadlineTitle> transform(List<String> list){
        ArrayList<HeadlineTitle> headlines = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            headlines.add(new HeadlineTitle(list.get(i), i));
        }
        return headlines;
    }
}