package com.yossi.capitalGains;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StockValueResponse {
    private String symbol;
    
    private double ticker; // If you need to use this later

    @JsonProperty("stock value")  // This maps to "stock value" in JSON
    private double stockValue; // The field for the stock value

    // Getters and setters
    public double getStockValue() {
        return stockValue;
    }

    public void setStockValue(double stockValue) {
        this.stockValue = stockValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getTicker() {
        return ticker;
    }

    public void setTicker(double ticker) {
        this.ticker = ticker;
    }



    @Override
    public String toString() {
        return "StockValueResponse{" +
                "symbol='" + symbol + '\'' +
                ", stockValue=" + stockValue +
                ", ticker=" + ticker +
                '}';
    }
}


