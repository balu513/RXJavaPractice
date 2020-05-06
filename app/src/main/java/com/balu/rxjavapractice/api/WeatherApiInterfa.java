package com.balu.rxjavapractice.api;



import com.balu.rxjavapractice.model.weather.Weather;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiInterfa {
    @GET("current")
    Single<Weather> getWeatherByLocation(@Query("access_key") String access_key, @Query("query") String location);
}
