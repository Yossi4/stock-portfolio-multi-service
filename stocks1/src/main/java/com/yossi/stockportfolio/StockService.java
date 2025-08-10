package com.yossi.stockportfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private NinjaApiService ninjaApiService;

    // Get stock value by ID
    public Map<String, Object> getStockValue(String id) {
        try {
            // Retrieve the stock by ID
            Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

            // Get stock price from NinjaApiService
            Map<String, Object> stockPriceData = ninjaApiService.getStockPrice(stock.getSymbol());

            // Calculate stock value based on price and number of shares
            double stockValue = (double) stockPriceData.get("price") * stock.getNumberOfShares();
            stockPriceData.put("stock value", stockValue);

            return stockPriceData;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get stock value", e);
        }
    }

    // Get the total portfolio value
    public Map<String, Object> getPortfolioValue() {
        double totalValue = 0;
        List<Stock> stocks = stockRepository.findAll();

        for (Stock stock : stocks) {
            try {
                // Get stock price for each stock
                Map<String, Object> stockPriceData = ninjaApiService.getStockPrice(stock.getSymbol());
                totalValue += (double) stockPriceData.get("price") * stock.getNumberOfShares();
            } catch (Exception e) {
                throw new RuntimeException("Failed to calculate portfolio value", e);
            }
        }

        // Return the total portfolio value along with the current date
        return Map.of(
            "date", java.time.LocalDate.now().toString(),
            "portfolio value", totalValue
        );
    }

    // Add a new stock or update an existing stock
    public Stock addOrUpdateStock(Stock stock) {
        return stockRepository.save(stock);
    }

    // Delete a stock by its ID
    public void deleteStock(String id) {
        stockRepository.deleteById(id);
    }
}
