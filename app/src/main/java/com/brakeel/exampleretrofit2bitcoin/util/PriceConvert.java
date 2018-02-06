package com.brakeel.exampleretrofit2bitcoin.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classe Responsável por Converter os valores numericos em decimais com o tipo de moeda brasileira R$
 */
public class PriceConvert {
    public static String priceCurrency(Double price) {
        String formatado;
        if (price != null) {
            Locale ptBr = new Locale("pt", "BR"); //Locale para o Brasil
            NumberFormat nf = NumberFormat.getCurrencyInstance(ptBr);
            formatado = nf.format(price);
        } else {
            formatado = "";
        }

        return formatado;
    }
}
