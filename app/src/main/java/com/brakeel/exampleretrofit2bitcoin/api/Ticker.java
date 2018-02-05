package com.brakeel.exampleretrofit2bitcoin.api;

/**
 * Created by Kelvis Borges on 05/02/2018.
 */

public class Ticker {

    private Double high;
    private Double low;
    private Double vol;
    private Double last;
    private Double buy;
    private Double sell;
    private Integer date;

    @Override
    public String toString() {
        return "Ticker{" +
                "high=" + high +
                ", low=" + low +
                ", vol=" + vol +
                ", last=" + last +
                ", buy=" + buy +
                ", sell=" + sell +
                ", date=" + date +
                '}';
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getVol() {
        return vol;
    }

    public void setVol(Double vol) {
        this.vol = vol;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }
}
