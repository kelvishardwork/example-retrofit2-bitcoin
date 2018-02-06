package com.brakeel.exampleretrofit2bitcoin.entity;

/**
 * Classe Model de Trade, que vai definir um Histórico de negociações realizadas.
 */
public class Trade {

    private int date;
    private Double price;
    private Double amount;
    private Integer tid;
    private String type;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "date=" + date +
                ", price=" + price +
                ", amount=" + amount +
                ", tid=" + tid +
                ", type='" + type + '\'' +
                '}';
    }
}
