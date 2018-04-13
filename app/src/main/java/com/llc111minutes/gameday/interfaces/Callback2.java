package com.llc111minutes.gameday.interfaces;

public interface Callback2<T1, T2> {
    void onResult(T1 t1, T2 t2);
    void onError(T1 t1, Throwable e);
}