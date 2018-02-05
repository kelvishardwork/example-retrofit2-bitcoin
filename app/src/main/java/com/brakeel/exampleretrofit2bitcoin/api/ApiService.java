package com.brakeel.exampleretrofit2bitcoin.api;

/**
 * Created by Kelvis Borges on 05/02/2018.
 */

public interface ApiService {

    @GET("{coin}/{method}")
    Call<Ticker> getTicker(@Path("coin") String coin, @Path("method") String method);


}
