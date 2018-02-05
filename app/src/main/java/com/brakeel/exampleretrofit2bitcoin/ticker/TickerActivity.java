package com.brakeel.exampleretrofit2bitcoin.ticker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brakeel.exampleretrofit2bitcoin.MainActivity;
import com.brakeel.exampleretrofit2bitcoin.R;
import com.brakeel.exampleretrofit2bitcoin.api.ApiClient;
import com.brakeel.exampleretrofit2bitcoin.api.ApiService;
import com.brakeel.exampleretrofit2bitcoin.entity.Ticker;
import com.brakeel.exampleretrofit2bitcoin.entity.DigitalCurrency;
import com.brakeel.exampleretrofit2bitcoin.entity.TypeMethod;
import com.brakeel.exampleretrofit2bitcoin.repository.TickerRepository;
import com.brakeel.exampleretrofit2bitcoin.util.TickerDeserialize;
import com.brakeel.exampleretrofit2bitcoin.viewholder.TickerViewHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TickerActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private static final String TAG = "TickerActivity";
    private Ticker tickerModel;
    private Context context;
    private TickerRepository tickerRepository;
    private Gson gson;
    private RelativeLayout lytLoading;
    private TickerViewHolder tickerViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Cotações do Bitcoin");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_bitcoin);
        tickerModel = new Ticker();
        tickerRepository = new TickerRepository(this);

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

        requestAPI(gson);
        mViewHolder.row_ticker_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TickerActivity.this, MainActivity.class));
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
                Ticker t = response.body();
                if (!response.isSuccessful()) {
                    switch (response.code()) {
                        case ApiClient.UNAUTHORIZED:
                            Log.i(TAG, "UNAUTHORIZED " + response.code());
                            break;
                        case ApiClient.FORBIDDEN:
                            Log.i(TAG, "FORBIDDEN " + response.code());
                            break;
                        case ApiClient.NOT_FOUND:
                            Log.i(TAG, "NOT_FOUND " + response.code());
                            break;
                        case ApiClient.UNPROCESSABLE_ENTITY:
                            Log.i(TAG, "UNPROCESSABLE_ENTITY " + response.code());
                            break;
                        case ApiClient.INTERNAL_SERVER_ERROR:
                            Log.i(TAG, "INTERNAL_SERVER_ERROR " + response.code());
                            break;
                        case ApiClient.INTERNET_NOT_AVAILABLE:
                            Log.i(TAG, "INTERNET_NOT_AVAILABLE " + response.code());
                            break;
                    }

                } else {
                    if (t != null) {
                        Log.i(TAG, "Body: " + String.valueOf(response.body()));
                        tickerModel = t;
                        tickerRepository.addTicker(t);
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
        this.mViewHolder.tvTickerLast.setText("Último: " + String.valueOf(this.tickerModel.getLast()));
        this.mViewHolder.tvTickerHigh.setText("Maior: " + String.valueOf(this.tickerModel.getHigh()));
        this.mViewHolder.tvTickerLow.setText("Menor: " + String.valueOf(this.tickerModel.getLow()));
        this.mViewHolder.tvTickerVol.setText("Vol. 24hs: " + String.valueOf(this.tickerModel.getVol()));
        this.mViewHolder.tvDate.setText("Data: " + String.valueOf(this.tickerModel.getDate()));
    }

    @Override
    public void onClick(View v) {

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
