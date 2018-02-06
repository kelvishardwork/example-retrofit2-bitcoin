package com.brakeel.exampleretrofit2bitcoin.repository;

import android.app.Activity;

import com.brakeel.exampleretrofit2bitcoin.entity.Ticker;

/**
 * Classe responsavel pela lógica de negócio do Ticker, nesse caso, somente para banco (repository)
 */
public class TickerBO {

    private BTCRepository btcRepository;

    /**
     * Constructor já instancia um BTCRepository pra poder interferir na lógica
     *
     * @param activity
     */
    public TickerBO(Activity activity) {
        btcRepository = new BTCRepository(activity);
    }

    /**
     * Método responsavel por solicitar um truncate na tabela, após isso faz o pedido pra inserir os dados
     *
     * @param ticker
     */
    public void insert(Ticker ticker) {
        btcRepository.truncateTicker();
        btcRepository.addTicker(ticker);
    }

    /***
     * Método responsavel por Buscar os dados do repository da tabela Ticker
     * @return
     */
    public Ticker getTicker() {
        Ticker ticker = btcRepository.getTicker();
        return ticker;
    }

}
