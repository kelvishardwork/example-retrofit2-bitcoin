package com.brakeel.exampleretrofit2bitcoin.repository;

import android.app.Activity;

import com.brakeel.exampleretrofit2bitcoin.entity.Trade;

import java.util.ArrayList;

/**
 * Classe responsavel pela lógica de negócio do Trade, nesse caso, somente para banco (repository)
 */
public class TradeBO {
    private BTCRepository btcRepository;

    /**
     * Constructor já instancia um BTCRepository pra poder interferir na lógica
     *
     * @param activity
     */
    public TradeBO(Activity activity) {
        btcRepository = new BTCRepository(activity);
    }

    /**
     * Método responsavel por solicitar um truncate na tabela, após isso faz o pedido pra inserir os dados
     *
     * @param trade Recebe uma lista como parametro pra começar realizar o cadastro de registro no repository
     */
    public void insertList(ArrayList<Trade> trade) {
        ArrayList<Trade> trades = trade;
        btcRepository.truncateTrade();
        for (Trade t : trades)
            btcRepository.addTrade(t);
    }


    /***
     * Método responsavel por Buscar os dados do repository da tabela Trades
     * @return
     */
    public ArrayList<Trade> getList() {
        ArrayList<Trade> trades = btcRepository.getAllTrades();
        return trades;
    }


}
