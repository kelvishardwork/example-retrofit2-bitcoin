package com.brakeel.exampleretrofit2bitcoin.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Kelvis Borges on 05/02/2018.
 */

public interface ApiService {

/*
    @GET("{coin}/{method}/")
    Call<Ticker> getTicker(@Path("coin") String coin, @Path("method") String method);
*/

    @GET("ticker")
    Call<Ticker> getTicker();


}
