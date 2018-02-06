package com.brakeel.exampleretrofit2bitcoin.trade;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.brakeel.exampleretrofit2bitcoin.R;
import com.brakeel.exampleretrofit2bitcoin.util.ApiClient;
import com.brakeel.exampleretrofit2bitcoin.api.ApiService;
import com.brakeel.exampleretrofit2bitcoin.entity.DigitalCurrency;
import com.brakeel.exampleretrofit2bitcoin.entity.Trade;
import com.brakeel.exampleretrofit2bitcoin.entity.TypeMethod;
import com.brakeel.exampleretrofit2bitcoin.trade.adapter.TradeListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TradeActivity extends AppCompatActivity {

    ViewHolder mViewHolder = new ViewHolder();
    private static final String TAG = "TradeActivity";
    private Trade tradeModel;
    private RelativeLayout lytLoading;
    private ArrayList<Trade> trades;
    TradeListAdapter tradeListAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Histórico de Cotações");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_bitcoin);
        this.mContext = this;
        tradeModel = new Trade();
        lytLoading = findViewById(R.id.lytLoadingTrade);

        // Obtendo a recycleview
        this.mViewHolder.recyclerTrades = (RecyclerView) this.findViewById(R.id.rvTrades);

        setOffLoading();
        requestAPI();


    }

    private void requestAPI() {
        setOnLoading();
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ArrayList<Trade>> reqListTrade = service.getListTrades(String.valueOf(DigitalCurrency.BTC), String.valueOf(TypeMethod.trades));
        reqListTrade.enqueue(new Callback<ArrayList<Trade>>() {
            @Override
            public void onResponse(Call<ArrayList<Trade>> call, Response<ArrayList<Trade>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Não foram encontrados resultados", Toast.LENGTH_LONG).show();
                } else {
                    trades = response.body();
                    if (!trades.isEmpty()) {
                        Toast.makeText(getApplicationContext(), " Dados Encontrados ", Toast.LENGTH_LONG).show();
                        for (Trade t : trades) {
                            Log.i(TAG, t.getTid() + " - Preço: " + t.getPrice());
                        }

                        // Definindo adapter
                        tradeListAdapter = new TradeListAdapter(trades);
                        mViewHolder.recyclerTrades.setAdapter(tradeListAdapter);
                        // 3 - Definir layout
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                        mViewHolder.recyclerTrades.setLayoutManager(linearLayoutManager);

                        //tickerModel = t;
                        //tickerRepository.addTicker(t);
                        setOffLoading();
                        //setData();
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Trade>> call, Throwable t) {
                setOffLoading();
                Toast.makeText(getApplicationContext(), " Erro ", Toast.LENGTH_LONG).show();
            }
        });

    }

    private static class ViewHolder {
        RecyclerView recyclerTrades;
    }

    public void setOffLoading() {
        lytLoading.setVisibility(View.GONE);
    }

    public void setOnLoading() {
        lytLoading.setVisibility(View.VISIBLE);
    }
}
