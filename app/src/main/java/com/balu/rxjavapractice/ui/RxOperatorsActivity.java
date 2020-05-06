package com.balu.rxjavapractice.ui;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.balu.rxjavapractice.Mock;
import com.balu.rxjavapractice.R;
import com.balu.rxjavapractice.api.api_client.CovidApiClient;
import com.balu.rxjavapractice.api.api_client.CricketCilent;
import com.balu.rxjavapractice.api.api_client.WeatherApiClient;
import com.balu.rxjavapractice.constants.Consts;
import com.balu.rxjavapractice.model.covid.CovidWorldSummary;
import com.balu.rxjavapractice.model.cricket.Matches;
import com.balu.rxjavapractice.model.cricket.PlayerBio;
import com.balu.rxjavapractice.model.cricket.PlayerFinder;
import com.balu.rxjavapractice.model.cricket.ScoreCard;
import com.balu.rxjavapractice.model.weather.Location;
import com.balu.rxjavapractice.model.weather.Weather;

import java.util.ArrayList;
import java.util.List;

public class RxOperatorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_operators);
        Zip();
        flatMap();
        create();

    }


    @SuppressLint("CheckResult")
    public void create() {
        Observable<Weather> weatherObservable = Observable.create(new ObservableOnSubscribe<Weather>() {
            @Override
            public void subscribe(ObservableEmitter<Weather> e) throws Exception {
//Creating an Observable from a single object
                if (!e.isDisposed()) {
                    e.onNext(Mock.getInstance().getWeather());
                    e.onComplete();
                }

// Creating an Observable from a list of objects
//                List<Weather> weatherList = Mock.getInstance().getWeatherList();
//                for (Weather weather:weatherList) {
//                    if(!e.isDisposed()){
//                        e.onNext(weather);
//                    }
//                }
//                if(!e.isDisposed()){
//                    e.onComplete();
//                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        weatherObservable.subscribe(new Observer<Weather>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Weather weather) {
                Log.d("create onNext", weather.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    @SuppressLint("CheckResult")
    // parelell execution
    private void Zip() {
        Single.zip(CricketCilent.provideCricApiClient().getUpcomongMatches(Consts.Cricket.API_KEY_CRICKET),
                CovidApiClient.providesCovidApiClientWorld().getCovidSummary(),
                WeatherApiClient.providesWeatherApiClient().getWeatherByLocation(Consts.Whether.API_KEY_WEATHER, "Hyderabad"),
                new Function3<Matches, CovidWorldSummary, Weather, List<String>>() {
                    @Override
                    public List<String> apply(Matches matches, CovidWorldSummary covidWorldSummary, Weather weather) throws Exception {
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(matches.toString());
                        list.add(covidWorldSummary.toString());
                        list.add(weather.toString());
                        return list;
                    }
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        Log.d("result", strings.toString());
                    }
                });

    }

    // sequentionally executes
    private void flatMap() {
        CricketCilent.provideCricApiClient().getPlayersBySearch(Consts.Cricket.API_KEY_CRICKET, "sachin")
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(new PlayerFinder())
                .flatMap(new Function<PlayerFinder, SingleSource<PlayerBio>>() {
                    @Override
                    public SingleSource<PlayerBio> apply(PlayerFinder playerFinder) throws Exception {
                        Log.d("RX OP:  FlatMap: ", playerFinder.toString());
                        return CricketCilent.provideCricApiClient().getBioInformation(Consts.Cricket.API_KEY_CRICKET,
                                playerFinder.getData().get(0).getPid() + "");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<PlayerBio>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(PlayerBio playerBio) {
                        Log.d("PlayerBio", playerBio.getProfile().toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
