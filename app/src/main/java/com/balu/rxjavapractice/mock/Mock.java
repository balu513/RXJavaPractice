package com.balu.rxjavapractice.mock;

import com.balu.rxjavapractice.model.weather.Location;
import com.balu.rxjavapractice.model.weather.Weather;

import java.util.ArrayList;
import java.util.List;

public class Mock {

    private static Mock instance;

    public static Mock getInstance(){
        if(instance == null)
            instance = new Mock();
        return instance;
    }


    public Weather getWeather(){
        final Weather weather = new Weather();
        Location location = new Location();
        location.setCountry("IN");
        location.setName("Hyd");
        weather.setLocation(location);
        return weather;
    }

    public List<Weather> getWeatherList(){
        ArrayList<Weather> list = new ArrayList<Weather>();
        for (int i = 0; i < 10; i++) {
            final Weather weather = new Weather();
            Location location = new Location();
            location.setCountry("IN"+i);
            location.setName("Hyd"+i);
            location.setLat(i+"");
            weather.setLocation(location);
            list.add(weather);
        }
        final Weather weather1 = new Weather();
        Location location1 = new Location();
        location1.setCountry("India");
        location1.setName("Visakhapatnam");
        weather1.setLocation(location1);
        list.add(weather1);
        list.add(weather1);
        list.add(weather1);

        return list;
    }
}
