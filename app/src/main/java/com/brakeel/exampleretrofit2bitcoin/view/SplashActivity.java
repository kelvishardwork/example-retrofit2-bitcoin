package com.brakeel.exampleretrofit2bitcoin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Classe Responsável por cuidas da atividade da Primeira Tela do APlicativo, uma SplashScreen
 * Após 20000 milisegundos é chamado a próxima tela
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent i = new Intent(SplashActivity.this, TickerActivity.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
