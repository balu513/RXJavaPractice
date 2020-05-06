package com.balu.rxjavapractice.api;



import com.balu.rxjavapractice.model.cricket.Matches;
import com.balu.rxjavapractice.model.cricket.OldMatches;
import com.balu.rxjavapractice.model.cricket.PlayerBio;
import com.balu.rxjavapractice.model.cricket.PlayerFinder;
import com.balu.rxjavapractice.model.cricket.ScoreCard;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CricetApiInterface {


    @GET("matches")
    public Single<Matches> getUpcomongMatches(@Query("apikey") String apiKey);

    @GET("cricket")
    public Single<OldMatches> getOldMatches(@Query("apikey") String apiKey);

    @GET("fantasySummary")
    public Maybe<ScoreCard> getFullScoreCard(@Query("apikey") String apiKey, @Query("unique_id") String unique_id);




    @GET("playerStats")
    public Single<PlayerBio> getBioInformation(@Query("apikey") String apiKey, @Query("pid") String pid);

    @GET("playerFinder")
    public Single<PlayerFinder> getPlayersBySearch(@Query("apikey") String apiKey, @Query("name") String name);


}
