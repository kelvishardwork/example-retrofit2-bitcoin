package com.brakeel.exampleretrofit2bitcoin.main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.brakeel.exampleretrofit2bitcoin.R;
import com.brakeel.exampleretrofit2bitcoin.api.ApiClient;
import com.brakeel.exampleretrofit2bitcoin.api.ApiService;
import com.brakeel.exampleretrofit2bitcoin.api.Ticker;
import com.brakeel.exampleretrofit2bitcoin.entity.DigitalCurrency;
import com.brakeel.exampleretrofit2bitcoin.entity.TypeMethod;
import com.brakeel.exampleretrofit2bitcoin.util.TickerDeserialize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TickerActivity extends AppCompatActivity {
    private static final String TAG = "TickerActivity";
    private Ticker tickerModel;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Cotações do Bitcoin");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_bitcoin);

        context = getApplicationContext();
        Gson gson = new GsonBuilder().registerTypeAdapter(Ticker.class, new TickerDeserialize()).create();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<Ticker> requestTicker = service.getTicker(String.valueOf(DigitalCurrency.BTC), String.valueOf(TypeMethod.ticker));
        //Call<Ticker> requestTicker = service.getTicker();

        requestTicker.enqueue(new Callback<Ticker>() {
            @Override
            public void onResponse(Call<Ticker> call, Response<Ticker> response) {
                Ticker t = response.body();
                if (t != null)
                    tickerModel = t;
                Log.i(TAG, "Body: " + String.valueOf(response.body()));
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
            }

            @Override
            public void onFailure(Call<Ticker> call, Throwable t) {

                Toast.makeText(context, "Por favor, ative a conexão à internet.", Toast.LENGTH_LONG).show();
            }
        });


        //String btc = String.valueOf(DigitalCurrency.BTC);


    }


}