package com.balu.rxjavapractice.api.api_client;



import com.balu.rxjavapractice.api.CovidApiInterface;
import com.balu.rxjavapractice.constants.Consts;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CovidApiClient {

    public static CovidApiInterface providesCovidApiClientWorld(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        CovidApiInterface covidApiInterface = new Retrofit.Builder().baseUrl(Consts.Covid.URL_PREFIX_COVID_WORLD)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(CovidApiInterface.class);
        return covidApiInterface;

    }

    public  static CovidApiInterface providesCovidApiClientIndia(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        CovidApiInterface covidApiInterface = new Retrofit.Builder().baseUrl(Consts.Covid.URL_PREFIX_COVID_IND)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build().create(CovidApiInterface.class);
        return covidApiInterface;

    }
}
