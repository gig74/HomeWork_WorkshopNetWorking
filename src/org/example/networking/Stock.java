package org.example.networking;

public class Stock {
    private String date;
    private float close;

    public Stock() {
    }

    public Stock(String date, float close) {
        this.date = date;
        this.close = close;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "date='" + date + '\'' +
                ", close='" + close + '\'' +
                '}' + "\n";
    }
}
