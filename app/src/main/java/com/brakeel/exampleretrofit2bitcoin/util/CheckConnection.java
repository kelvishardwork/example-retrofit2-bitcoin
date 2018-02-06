package com.brakeel.exampleretrofit2bitcoin.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Classe Responsável por Checar a Conexão com Internet
 */
public class CheckConnection {
    private Context context;

    /**
     * Constructor
     * @param context recebe um contexto da activity que vai utliza-lo
     */
    public CheckConnection(Context context) {
        this.context = context;
    }

    /**
     * Método Reponsavel por checar e verificar os tipos de conexão e se estão com acesso a internet
     * @param context
     * @return
     */
    public boolean internetConnectionExists(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();

            // Se não existe nenhum tipo de conexão retorna false
            if (netInfo == null) {
                return false;
            }
            int netType = netInfo.getType();
            // Verifica se a conexão é do tipo WiFi ou Mobile e
            // retorna true se estiver conectado ou false em
            // caso contrário
            if (netType == ConnectivityManager.TYPE_WIFI ||
                    netType == ConnectivityManager.TYPE_MOBILE) {
                return netInfo.isConnected();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}