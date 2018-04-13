package com.llc111minutes.gameday.interfaces;

/**
 * Created by Yurii on 2/2/17.
 */

public interface Callback<T> {
    void onResult(T t);
    void onError(Throwable e);
}