package com.brakeel.exampleretrofit2bitcoin.entity;

/**
 * Classe Model de Ticker, que vai definir as informações com o resumo das últimas 24 horas de negociações.
 */
public class Ticker {

    private Double high;
    private Double low;
    private Double vol;
    private Double last;
    private Double buy;
    private Double sell;
    private int date;

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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

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
}
