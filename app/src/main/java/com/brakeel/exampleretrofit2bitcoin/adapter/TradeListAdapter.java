package com.brakeel.exampleretrofit2bitcoin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brakeel.exampleretrofit2bitcoin.R;
import com.brakeel.exampleretrofit2bitcoin.entity.Trade;
import com.brakeel.exampleretrofit2bitcoin.viewholder.TradeViewHolder;

import java.util.ArrayList;

/**
 * Adaptador para manipular RecyclerView para a Lista de Trades
 */
public class TradeListAdapter extends RecyclerView.Adapter<TradeViewHolder> {

    private ArrayList<Trade> mListTrades;

    public TradeListAdapter(ArrayList<Trade> trades) {
        this.mListTrades = trades;
    }

    @Override
    public TradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        // Infla o layout da linha e faz uso na listagem
        LayoutInflater inflater = LayoutInflater.from(context);
        View cardView = inflater.inflate(R.layout.row_trade_list, parent, false);
        return new TradeViewHolder(cardView);
    }


    @Override
    public void onBindViewHolder(TradeViewHolder holder, int position) {
        // Obtém item da lista
        Trade trade = this.mListTrades.get(position);
        holder.bindData(trade);
    }

    @Override
    public int getItemCount() {
        return this.mListTrades.size();

    }
}
