package com.balu.rxjavapractice.api.api_client;



import com.balu.rxjavapractice.api.WeatherApiInterfa;
import com.balu.rxjavapractice.constants.Consts;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherApiClient {


    public static WeatherApiInterfa providesWeatherApiClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        WeatherApiInterfa weatherApiInterface = new Retrofit.Builder().baseUrl(Consts.Whether.URL_PREFIX_WEATHER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(WeatherApiInterfa.class);

        return weatherApiInterface;
    }
}
