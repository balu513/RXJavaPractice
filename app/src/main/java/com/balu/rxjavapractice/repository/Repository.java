package com.balu.rxjavapractice.repository;



import com.balu.rxjavapractice.api.api_client.WeatherApiClient;
import com.balu.rxjavapractice.constants.Consts;
import com.balu.rxjavapractice.model.weather.Weather;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;

public class Repository {


    public Future<Observable<Weather>> makeFuturQueryOnWeather(){
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Callable<Observable<Weather>> weatherCallable = new Callable<Observable<Weather>>() {

            @Override
            public Observable<Weather> call() throws Exception {
                return WeatherApiClient.providesWeatherApiClient().getWeatherByLocationAsObserable(Consts.Whether.API_KEY_WEATHER,"Hyderabad");
            }
        };
        return new Future<Observable<Weather>>(){

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                if(mayInterruptIfRunning){
                    executor.shutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executor.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executor.isTerminated();
            }

            @Override
            public Observable<Weather> get() throws ExecutionException, InterruptedException {
                return executor.submit(weatherCallable).get();
            }

            @Override
            public Observable<Weather> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(weatherCallable).get();
            }
        };
    }
}
