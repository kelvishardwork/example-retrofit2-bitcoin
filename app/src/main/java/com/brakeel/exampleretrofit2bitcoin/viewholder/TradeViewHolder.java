package com.brakeel.exampleretrofit2bitcoin.viewholder;

/**
 * Created by Kelvis Borges on 05/02/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.brakeel.exampleretrofit2bitcoin.R;
import com.brakeel.exampleretrofit2bitcoin.entity.Trade;

import java.util.Date;

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
        // long dateLong = Long.parseLong(String.valueOf(trade.getDate()));
        //Date date = new Date(dateInt);  long time = cursor.getLong(cursor.getColumnIndex("DT_NASC"));
        long time = 1517862723;
        Date dtNasc = new Date();
        dtNasc.setTime(time);
        dtNasc.getTime();

        //DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
       // String reportDate = df.format(dtNasc);


        // Altera Valores
        this.tvDate.setText(String.valueOf(dtNasc));
        this.tvPrice.setText("R$ " + String.valueOf(trade.getPrice()));
        this.tvAmount.setText("Qtde: " + String.valueOf(trade.getAmount()));
        this.tvTid.setText("TID: " + String.valueOf(trade.getTid()));
        this.tvType.setText("Tipo: " + (String.valueOf(trade.getType()).equals("buy") ? "Comprar" : "Vender"));
    }

}

