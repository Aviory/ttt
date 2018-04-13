package com.llc111minutes.gameday.api;

import com.llc111minutes.gameday.Const;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marina on 30.08.16.
 */
public class ApiManager {
    private static Retrofit sRetrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // apply your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptor
        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder().
                    baseUrl(Const.BASE_URL).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    client(httpClient.build()).
                    build();
        }
        return sRetrofit;
    }
}