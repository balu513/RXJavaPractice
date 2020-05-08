package com.balu.rxjavapractice.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import com.balu.rxjavapractice.FlatMapDemoActivity;
import com.balu.rxjavapractice.R;
import com.balu.rxjavapractice.mock.Mock;
import com.balu.rxjavapractice.model.weather.Weather;
import com.balu.rxjavapractice.repository.Repository;
import com.balu.rxjavapractice.ui.RxOperatorsActivity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private CompositeDisposable disposables = new CompositeDisposable();
    androidx.appcompat.widget.SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        searchView = root.findViewById(R.id.et_search);
        root.findViewById(R.id.tv_rx_op).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RxOperatorsActivity.class));
            }
        });
        root.findViewById(R.id.tv_flatmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flatMap();
            }
        });
        rxOperators();
        return root;
    }

    private void rxOperators() {
//        fromIterable();
//        fromCallable();
//        future();
//        filter();
//          filter2();
//        distinct();
//        map();
//        buffer();
//        debounce();  -> with RX binding need to see

        //flatMap();

    }

    private void flatMap() {
        startActivity(new Intent(getActivity(), FlatMapDemoActivity.class));

    }

    private void debounce() {
        //Search query
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(newText);
                        }
                        return false;
                    }
                });
            }
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "Debounce: " + s);
                        //API CALL

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void buffer() {
        Observable.fromIterable(Mock.getInstance().getWeatherList()).map(new Function<Weather, Integer>() {
            @Override
            public Integer apply(Weather weather) throws Exception {
                try {
                    return Integer.parseInt(weather.getLocation().getLat());
                } catch (Exception e) {
                    return 999;
                }
            }
        }).buffer(4)

                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d(TAG + " Map", integers.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void map() {
        Observable.fromIterable(Mock.getInstance().getWeatherList()).map(new Function<Weather, Integer>() {
            @Override
            public Integer apply(Weather weather) throws Exception {
                try {
                    return Integer.parseInt(weather.getLocation().getLat());
                } catch (Exception e) {
                    return 999;
                }
            }
        })

                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG + " Map", integer + "");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // distinct(new Function<Weather, String>()   from the weather object based on our condition only distinct records will emit
    private void distinct() {
        Observable.fromIterable(Mock.getInstance().getWeatherList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinct(new Function<Weather, String>() {
                    @Override
                    public String apply(Weather weather) throws Exception {
                        return weather.getLocation().getName();
                    }
                }).subscribe(new Observer<Weather>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Weather weather) {
                Log.d(TAG + "distinct", weather.toString() + "  name: " + weather.getLocation().getName() + "  \nLAT: " +
                        weather.getLocation().getLat());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void filter2() {
        List<Weather> weatherList = Mock.getInstance().getWeatherList();
        Observable.fromIterable(weatherList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Weather>() {
                    @Override
                    public boolean test(Weather weather) throws Exception {
                        return Integer.parseInt(weather.getLocation().getLat()) % 2 == 0;
                    }
                })
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Weather weather) {
                        Log.d(TAG + "filter", weather.toString() + "  name: " + weather.getLocation().getName() + "  LAT: " +
                                weather.getLocation().getLat());
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
    private void filter() {
        final List<Weather> weatherList = Mock.getInstance().getWeatherList();
        Observable.create(new ObservableOnSubscribe<Weather>() {
            @Override
            public void subscribe(ObservableEmitter<Weather> emitter) throws Exception {
                for (Weather weather : weatherList) {
                    if (!emitter.isDisposed())
                        emitter.onNext(weather);
                }
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        }).filter(new Predicate<Weather>() {
            @Override
            public boolean test(Weather weather) throws Exception {
                return weather.getLocation().getName().equals("Hyd8") || weather.getLocation().getName().equals("Hyd4");
            }
        }).subscribe(new Observer<Weather>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Weather weather) {
                Log.d(TAG + "filter", weather.toString() + "");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void future() {
        try {
            new Repository().makeFuturQueryOnWeather().get().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Weather>() {
                        @Override
                        public void accept(Weather weather) throws Exception {
                            Log.d(TAG + "Future", weather.toString());
                        }
                    });
        } catch (Exception e) {

        }
    }

    //Some block of code need to execute in background and result in Main Thread.
    private void fromCallable() {
        Single.fromCallable(new Callable<Weather>() {
            @SuppressLint("CheckResult")
            @Override
            public Weather call() throws Exception {
                List<Weather> weatherList = Mock.getInstance().getWeatherList();
                return weatherList.get(0);
            }
        }).subscribe(new Consumer<Weather>() {
            @Override
            public void accept(Weather weather) throws Exception {
                Log.d(TAG + "Callable", weather.getLocation().getName());
            }
        });
    }

    private void fromIterable() {
        List<Weather> weatherList = Mock.getInstance().getWeatherList();
        Observable.fromIterable(weatherList).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Weather>() {
                    @Override
                    public void accept(Weather weather) throws Exception {
                        Log.d(TAG, weather.getLocation().getName());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
