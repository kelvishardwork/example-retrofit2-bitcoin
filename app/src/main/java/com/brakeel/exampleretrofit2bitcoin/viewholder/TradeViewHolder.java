package com.brakeel.exampleretrofit2bitcoin.viewholder;

/**
 * Created by Kelvis Borges on 05/02/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.brakeel.exampleretrofit2bitcoin.R;
import com.brakeel.exampleretrofit2bitcoin.entity.Trade;

import static com.brakeel.exampleretrofit2bitcoin.util.DateConvert.epochTimeToData;
import static com.brakeel.exampleretrofit2bitcoin.util.PriceConvert.priceCurrency;

/**
 * Responsavel por fazer as manipulacoes de elementos de interface
 */
public class TradeViewHolder extends RecyclerView.ViewHolder {
    // Elementos da interface
    private TextView tvDate;
    private TextView tvPrice;
    private TextView tvAmount;
    private TextView tvTid;
    private TextView tvType;

    public TradeViewHolder(View itemView) {
        super(itemView);
        this.tvDate = (TextView) itemView.findViewById(R.id.tvDateTrade);
        this.tvPrice = (TextView) itemView.findViewById(R.id.tvPriceTrade);
        this.tvAmount = (TextView) itemView.findViewById(R.id.tvAmountTrade);
        this.tvTid = (TextView) itemView.findViewById(R.id.tvTidTrade);
        this.tvType = (TextView) itemView.findViewById(R.id.tvTypeTrade);
    }

    public void bindData(final Trade trade) {
        //long dateTime = 1517862723L;
        this.tvDate.setText(epochTimeToData(trade.getDate()));
        this.tvPrice.setText(priceCurrency(trade.getPrice()));
        this.tvAmount.setText("Qtde: " + String.valueOf(trade.getAmount()));
        this.tvTid.setText("TID: " + String.valueOf(trade.getTid()));
        this.tvType.setText("Tipo: " + (String.valueOf(trade.getType()).equals("buy") ? "Comprar" : "Vender"));
    }





}

