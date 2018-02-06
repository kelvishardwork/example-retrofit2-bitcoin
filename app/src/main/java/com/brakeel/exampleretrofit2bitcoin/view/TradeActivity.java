package com.brakeel.exampleretrofit2bitcoin.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.brakeel.exampleretrofit2bitcoin.R;
import com.brakeel.exampleretrofit2bitcoin.api.ApiService;
import com.brakeel.exampleretrofit2bitcoin.entity.DigitalCurrency;
import com.brakeel.exampleretrofit2bitcoin.entity.Trade;
import com.brakeel.exampleretrofit2bitcoin.entity.TypeMethod;
import com.brakeel.exampleretrofit2bitcoin.repository.TradeBO;
import com.brakeel.exampleretrofit2bitcoin.adapter.TradeListAdapter;
import com.brakeel.exampleretrofit2bitcoin.util.ApiClient;
import com.brakeel.exampleretrofit2bitcoin.util.CheckConnection;

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
    private TradeBO tradeBO;
    TradeListAdapter tradeListAdapter;
    Context context;
    private CheckConnection checkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Histórico de Cotações");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true); //Ativar o botão
        this.context = this;
        tradeModel = new Trade();
        tradeBO = new TradeBO(this);
        checkConnection = new CheckConnection(this);
        lytLoading = findViewById(R.id.lytLoadingTrade);

        // Obtendo a recycleview
        this.mViewHolder.recyclerTrades = (RecyclerView) this.findViewById(R.id.rvTrades);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setOffLoading();
        // Chama o método estatico para checar a conexão da internet
        if (checkConnection.internetConnectionExists(context)) {
            requestAPI();
        } else {
            Toast.makeText(context, "Por favor, ative a conexão à internet.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Chamada da API com Retrofit
     */
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
                        tradeBO.insertList(trades);
                        Log.i(TAG, "Banco TRADE : " + tradeBO.getList().toString());

                        // Definindo adapter
                        tradeListAdapter = new TradeListAdapter(tradeBO.getList());
                        mViewHolder.recyclerTrades.setAdapter(tradeListAdapter);
                        //  Definir layout
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        mViewHolder.recyclerTrades.setLayoutManager(linearLayoutManager);
                        setOffLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Trade>> call, Throwable t) {
                setOffLoading();
                Toast.makeText(context, "Por favor, ative a conexão à internet.", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla o menu para habilitar os icones superiores
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Cuida da identificação dos cliques
        switch (item.getItemId()) {
            case R.id.refresh:
                if (checkConnection.internetConnectionExists(context)) {
                    requestAPI();
                } else {
                    Toast.makeText(context, "Por favor, para atualizar os dados ative a conexão à internet.", Toast.LENGTH_LONG).show();
                }
                break;
            case android.R.id.home:  //ID do seu icone de voltar tela (gerado automaticamente pelo android)
                startActivity(new Intent(this, TickerActivity.class)); //O efeito ao ser pressionado
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Classe ViewHolder
     */
    private static class ViewHolder {
        RecyclerView recyclerTrades;
    }

    /**
     * Método responsável por Ausentar o progressbar
     */
    public void setOffLoading() {
        lytLoading.setVisibility(View.GONE);
    }

    /**
     * Método responsável por Ativar a visualizacao do Progressbar
     */
    public void setOnLoading() {
        lytLoading.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        // Chama a activity anterior quando voltar dessa tela
        startActivity(new Intent(TradeActivity.this, TickerActivity.class));
    }

}
