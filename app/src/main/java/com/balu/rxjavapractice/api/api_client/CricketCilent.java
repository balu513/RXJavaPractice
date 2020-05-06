package com.balu.rxjavapractice.api.api_client;

import com.balu.rxjavapractice.api.CricetApiInterface;
import com.balu.rxjavapractice.constants.Consts;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CricketCilent {

    public static CricetApiInterface provideCricApiClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        CricetApiInterface cricetApiInterface = new Retrofit.Builder().baseUrl(Consts.Network.BASE_URL_CRIC)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build().create(CricetApiInterface.class);

        return cricetApiInterface;
    }
}
