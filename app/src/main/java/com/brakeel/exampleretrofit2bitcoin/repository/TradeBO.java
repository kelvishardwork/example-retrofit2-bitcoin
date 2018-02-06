package com.brakeel.exampleretrofit2bitcoin.repository;

import android.app.Activity;

import com.brakeel.exampleretrofit2bitcoin.entity.Trade;

import java.util.ArrayList;

/**
 * Created by Kelvis Borges on 06/02/2018.
 */

public class TradeBO {
    private BTCRepository btcRepository;

    public TradeBO(Activity activity) {
        btcRepository = new BTCRepository(activity);
    }

    public void insertList(ArrayList<Trade> trade) {
        ArrayList<Trade> trades = trade;
        btcRepository.truncateTrade();
        for (Trade t : trades)
            btcRepository.addTrade(t);
    }

    public ArrayList<Trade> getList() {
        ArrayList<Trade> trades = btcRepository.getAllTrades();
        return trades;
    }




}
