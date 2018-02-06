package com.brakeel.exampleretrofit2bitcoin.api;

import com.brakeel.exampleretrofit2bitcoin.entity.Ticker;
import com.brakeel.exampleretrofit2bitcoin.entity.Trade;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Kelvis Borges on 05/02/2018.
 */

public interface ApiService {

    @GET("{coin}/{method}/")
    Call<Ticker> getTicker(@Path("coin") String coin, @Path("method") String method);

    @GET("{coin}/{method}/")
    Call<ArrayList<Trade>>getListTrades(@Path("coin") String coin, @Path("method") String method);
}
