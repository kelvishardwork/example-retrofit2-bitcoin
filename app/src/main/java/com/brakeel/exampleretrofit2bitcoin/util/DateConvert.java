package com.brakeel.exampleretrofit2bitcoin.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Classe Responsável por Converter os EpochTime em DateTime
 */
public class DateConvert {
    public static String epochTimeToData(int time) {
        long epochTime = time;
        epochTime *= 1000;
        Date date = new Date(epochTime);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return df.format(date);
    }
}
