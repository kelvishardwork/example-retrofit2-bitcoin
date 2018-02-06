package com.brakeel.exampleretrofit2bitcoin.ticker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brakeel.exampleretrofit2bitcoin.R;
import com.brakeel.exampleretrofit2bitcoin.api.ApiService;
import com.brakeel.exampleretrofit2bitcoin.entity.DigitalCurrency;
import com.brakeel.exampleretrofit2bitcoin.entity.Ticker;
import com.brakeel.exampleretrofit2bitcoin.entity.TypeMethod;
import com.brakeel.exampleretrofit2bitcoin.repository.TickerBO;
import com.brakeel.exampleretrofit2bitcoin.trade.TradeActivity;
import com.brakeel.exampleretrofit2bitcoin.util.ApiClient;
import com.brakeel.exampleretrofit2bitcoin.util.TickerDeserialize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.brakeel.exampleretrofit2bitcoin.util.DateConvert.epochTimeToData;
import static com.brakeel.exampleretrofit2bitcoin.util.PriceConvert.priceCurrency;

public class TickerActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    private static final String TAG = "TickerActivity";
    private Ticker tickerModel;
    private Context context;
    private TickerBO tickerBO;
    private Gson gson;
    private RelativeLayout lytLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Resumo de Cotações");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_bitcoin);
        tickerModel = new Ticker();
        tickerBO = new TickerBO(this);

        this.mViewHolder.row_ticker_list = findViewById(R.id.row_ticker_list);
        this.mViewHolder.tvTickerLast = (TextView) findViewById(R.id.tvTickerLast);
        this.mViewHolder.tvTickerHigh = (TextView) findViewById(R.id.tvTickerHigh);
        this.mViewHolder.tvTickerLow = (TextView) findViewById(R.id.tvTickerLow);
        this.mViewHolder.tvTickerVol = (TextView) findViewById(R.id.tvTickerVol);
        this.mViewHolder.tvDate = (TextView) findViewById(R.id.tvDate);

        context = getApplicationContext();
        gson = new GsonBuilder().registerTypeAdapter(Ticker.class, new TickerDeserialize()).create();
        lytLoading = findViewById(R.id.lytLoading);
        setOffLoading();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestAPI(gson);
        mViewHolder.row_ticker_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TickerActivity.this, TradeActivity.class));
            }
        });
    }

    private void requestAPI(Gson gson) {
        setOnLoading();
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<Ticker> requestTicker = service.getTicker(String.valueOf(DigitalCurrency.BTC), String.valueOf(TypeMethod.ticker));
        requestTicker.enqueue(new Callback<Ticker>() {
            @Override
            public void onResponse(Call<Ticker> call, Response<Ticker> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "SEM SUCESSO " + response.code());
                    switch (response.code()) {
                        case ApiClient.UNAUTHORIZED:
                            Toast.makeText(context, R.string.unauthorized, Toast.LENGTH_SHORT).show();
                            break;
                        case ApiClient.FORBIDDEN:
                            Toast.makeText(context, R.string.forbidden, Toast.LENGTH_SHORT).show();
                            break;
                        case ApiClient.NOT_FOUND:
                            Toast.makeText(context, R.string.not_Found, Toast.LENGTH_SHORT).show();
                            break;
                        case ApiClient.UNPROCESSABLE_ENTITY:
                            Toast.makeText(context, R.string.unprocessable_entity, Toast.LENGTH_SHORT).show();
                            break;
                        case ApiClient.INTERNAL_SERVER_ERROR:
                            Toast.makeText(context, R.string.internal_server_error, Toast.LENGTH_SHORT).show();
                            break;
                        case ApiClient.INTERNET_NOT_AVAILABLE:
                            Toast.makeText(context, R.string.internet_not_available, Toast.LENGTH_SHORT).show();
                            break;
                    }

                } else {
                    Ticker t = response.body();
                    if (t != null) {
                        tickerBO.insert(t);
                        tickerModel = tickerBO.getTicker();
                        Log.i(TAG, "Banco Ticker : " + tickerBO.getTicker().toString());
                        setOffLoading();
                        setData();
                    }
                }
            }

            @Override
            public void onFailure(Call<Ticker> call, Throwable t) {
                setOffLoading();
                Toast.makeText(getApplicationContext(), "Erro ao realizar consulta. Verifique sua conexão a internet!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData() {
        this.mViewHolder.tvTickerLast.setText("Último: " + priceCurrency(this.tickerModel.getLast()));
        this.mViewHolder.tvTickerHigh.setText("Maior: " + priceCurrency(this.tickerModel.getHigh()));
        this.mViewHolder.tvTickerLow.setText("Menor: " + priceCurrency(this.tickerModel.getLow()));
        this.mViewHolder.tvTickerVol.setText("Vol. 24hs: " + priceCurrency(this.tickerModel.getVol()));
        this.mViewHolder.tvDate.setText("Data: " + epochTimeToData(this.tickerModel.getDate()));
    }

    private static class ViewHolder {
        TextView tvTickerLast;
        TextView tvTickerHigh;
        TextView tvTickerLow;
        TextView tvTickerVol;
        TextView tvDate;
        View row_ticker_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                requestAPI(gson);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setOffLoading() {
        lytLoading.setVisibility(View.GONE);
    }

    public void setOnLoading() {
        lytLoading.setVisibility(View.VISIBLE);
    }
}
