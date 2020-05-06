package com.balu.rxjavapractice.api;



import com.balu.rxjavapractice.model.covid.CovidIndiaSummary;
import com.balu.rxjavapractice.model.covid.CovidWorldSummary;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidApiInterface {

    @GET("summary")
    Single<CovidWorldSummary> getCovidSummary();

    @GET("data.json")
    Single<CovidIndiaSummary> getCovidSummaryForINIDA();


    //https://www.mohfw.gov.in/dashboard/data/data.json
    //https://api.covid19api.com/summary

}
