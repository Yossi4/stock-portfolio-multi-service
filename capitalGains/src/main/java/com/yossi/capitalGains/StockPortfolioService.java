package com.yossi.capitalGains;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class StockPortfolioService {

    private final RestTemplate restTemplate;

    public StockPortfolioService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch all stocks from both stocks1 and stocks2 services
    public List<Stock> getAllStocks() {
        // Fetch stocks from stocks1 (port 5001)
        List<Stock> stocksFromStocks1 = fetchStocks("http://stocks1:5001/stocks");

        // Fetch stocks from stocks2 (port 5002)
        List<Stock> stocksFromStocks2 = fetchStocks("http://stocks2:5002/stocks");

        // Combine both lists
        stocksFromStocks1.addAll(stocksFromStocks2);

        return stocksFromStocks1;
    }

    // Helper method to fetch stocks from a given stock service
    private List<Stock> fetchStocks(String url) {
        try {
            return restTemplate.getForObject(url, List.class);
        } catch (Exception e) {
            System.err.println("Error fetching stocks from " + url + ": " + e.getMessage());
            return List.of();  // Return an empty list if an error occurs
        }
    }

    // Get current stock value by stock ID (the stock value API needs to be fetched)
    public double getCurrentStockValue(String stockId) {
        try {
            // Fetch stock value from the stocks1 service using the correct model class
            StockValueResponse response = restTemplate.getForObject(
                "http://stocks1:5001/stocks/stock-value/" + stockId, StockValueResponse.class);

            // Check response for stocks1
            if (response != null) {
                return response.getStockValue();
            }

            // If not found in stocks1, try stocks2
            response = restTemplate.getForObject(
                "http://stocks2:5002/stocks/stock-value/" + stockId, StockValueResponse.class);

            return (response != null) ? response.getStockValue() : 0.0;
        } catch (Exception e) {
            System.err.println("Error fetching stock value for ID " + stockId + ": " + e.getMessage());
            return 0.0;  // Default to 0.0 in case of failure
        }
    }
}
