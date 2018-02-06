package com.brakeel.exampleretrofit2bitcoin.api;

import com.brakeel.exampleretrofit2bitcoin.entity.Ticker;
import com.brakeel.exampleretrofit2bitcoin.entity.Trade;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiService {
    /**
     * Esse return um Ticker (resumo de operações executadas, o último preço, maior, menor,volume em 24hrs).
     *
     * @param coin   é responsavel por escolher o tipo de Moeda, temos três nesse projeto é o BTC
     * @param method é reponsavel por escolher o tipo de método, temos três, nesse método vamos utilizar o ticker
     * @return
     */
    @GET("{coin}/{method}/")
    Call<Ticker> getTicker(@Path("coin") String coin, @Path("method") String method);

    /**
     * Esse return um Trade (Histórico de negociações realizadas).
     *
     * @param coin   é responsavel por escolher o tipo de Moeda, temos três nesse projeto é o BTC
     * @param method é reponsavel por escolher o tipo de método, nesse método vamos utilizar o trade
     * @return
     */
    @GET("{coin}/{method}/")
    Call<ArrayList<Trade>> getListTrades(@Path("coin") String coin, @Path("method") String method);
}
