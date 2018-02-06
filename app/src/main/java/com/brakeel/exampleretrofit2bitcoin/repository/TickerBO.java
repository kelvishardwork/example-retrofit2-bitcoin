package com.brakeel.exampleretrofit2bitcoin.repository;

import android.app.Activity;

import com.brakeel.exampleretrofit2bitcoin.entity.Ticker;

/**
 * Created by Kelvis Borges on 06/02/2018.
 */

public class TickerBO {

    private BTCRepository btcRepository;

    public TickerBO(Activity activity) {
        btcRepository = new BTCRepository(activity);
    }

    public void insert(Ticker ticker) {
        btcRepository.truncateTicker();
        btcRepository.addTicker(ticker);
    }

    public Ticker getTicker() {
        Ticker ticker = btcRepository.getTicker();
        return ticker;
    }

}
