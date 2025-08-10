package com.yossi.stockportfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Service
public class NinjaApiService {

    private static final String API_KEY = "BHqJrsRXM5U9gWlJ1kcmyw==NGjjzS1gU8rFKiGF";
    
    

    private static final String BASE_URL = "https://api.api-ninjas.com/v1/stockprice";

    public static Map<String, Object> getStockPrice(String ticker) throws Exception {
        // Build the URL with the ticker
        String urlString = BASE_URL + "?ticker=" + ticker;
        URL url = new URL(urlString);

        // Open the connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Api-Key", API_KEY);
        connection.setRequestProperty("accept", "application/json");

        // Read the response
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseStream);

        // Extract relevant details from the response
        String stockName = root.path("name").asText();
        double stockPrice = root.path("price").asDouble();
        String exchange = root.path("exchange").asText();
        String currency = root.path("currency").asText();

        // Store the data in a map and return it
        Map<String, Object> stockData = new HashMap<>();
        stockData.put("name", stockName);
        stockData.put("price", stockPrice);
        stockData.put("exchange", exchange);
        stockData.put("currency", currency);

        return stockData;
    }
}


