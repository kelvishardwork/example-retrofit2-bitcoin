package com.brakeel.exampleretrofit2bitcoin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kelvis Borges on 06/02/2018.
 */

public class DateConvert {
    public static String epochTimeToData(long epochTime) {
        epochTime *= 1000;
        Date date = new Date(epochTime);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return df.format(date);
    }
}
