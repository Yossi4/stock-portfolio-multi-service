package com.yossi.stockportfolio;
import java.util.Map;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        try {
            Map<String, Object> stockData = NinjaApiService.getStockPrice("AAPL");
            System.out.println("Stock Name: " + stockData.get("name"));
            System.out.println("Stock Price: " + stockData.get("price"));
            System.out.println("Exchange: " + stockData.get("exchange"));
            System.out.println("Currency: " + stockData.get("currency"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
