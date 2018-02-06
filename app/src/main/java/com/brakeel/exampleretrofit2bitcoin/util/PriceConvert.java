package com.brakeel.exampleretrofit2bitcoin.util;

import java.text.NumberFormat;

/**
 * Created by Kelvis Borges on 06/02/2018.
 */

public class PriceConvert {
    public static String priceCurrency(Double price) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String formatado = nf.format(price);
        return formatado;
    }
}
