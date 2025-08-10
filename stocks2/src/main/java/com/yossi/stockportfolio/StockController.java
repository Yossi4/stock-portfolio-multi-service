package com.yossi.stockportfolio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StockController {
    private List<Stock> stocks = new ArrayList<>();
    private long counter = 1;
    @Autowired
    private StockRepository stockRepository;

   // NEW: GET stock by ID
   @GetMapping("/stocks/{id}")
   public ResponseEntity<Stock> getStockById(@PathVariable("id") String id) {
       Optional<Stock> stock = stockRepository.findById(id);
       if (stock.isPresent()) {
           return new ResponseEntity<>(stock.get(), HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }

   @PostMapping("/stocks")
    public ResponseEntity<Stock> createStock(@RequestBody @Valid Stock stock) {
    try {
        System.out.println("POST request received with payload: " + stock);

        // Validate required fields
        if (stock.getSymbol() == null || stock.getPrice() == null || stock.getNumberOfShares() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }

        // Set optional fields if not provided
        if (stock.getCompanyName() == null) {
            stock.setCompanyName("Unknown Company");  // Default value, or fetch from external source
        }

        if (stock.getDate() == null) {
            stock.setDate(LocalDate.now()); // Set today's date if not provided
        }

        if (stock.getDate() == null) {
            stock.setDate(LocalDate.now()); // Set today's date if not provided
        }

        // Assign a unique ID (auto-generated in the DB, but you can set it here for reference)
        String idString = counter++ + "";
        stock.setId(idString);

        // Save the stock to the DB (assuming stockRepository is properly configured)
        Stock savedStock = stockRepository.save(stock);

        return new ResponseEntity<>(savedStock, HttpStatus.CREATED); // 201 Created
    } catch (Exception e) {
        System.err.println("Error occurred while processing the POST request: " + e.getMessage());
        e.printStackTrace(); // Log the exception for debugging
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }
}

   
   





   // GET all stocks
   @GetMapping("/stocks")
   public ResponseEntity<List<Stock>> getAllStocks() {
       try {
           List<Stock> stocks = stockRepository.findAll();
           if (stocks.isEmpty()) {
               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
           }
           return new ResponseEntity<>(stocks, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   // PUT (update) a stock
   @PutMapping("/stocks/{id}")
   public ResponseEntity<Stock> updateStock(@PathVariable("id") String id, @RequestBody @Valid Stock stock) {
       Optional<Stock> existingStockOpt = stockRepository.findById(id);

       if (existingStockOpt.isPresent()) {
           Stock existingStock = existingStockOpt.get();

           // Update fields
           existingStock.setCompanyName(stock.getCompanyName());
           existingStock.setSymbol(stock.getSymbol());
           existingStock.setPrice(stock.getPrice());
           existingStock.setNumberOfShares(stock.getNumberOfShares());
           existingStock.setDate(stock.getDate());

           // Save the updated stock back to the database
           stockRepository.save(existingStock);
           return new ResponseEntity<>(existingStock, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }

   // DELETE a stock by ID
   @DeleteMapping("/stocks/{id}")
   public ResponseEntity<Void> deleteStock(@PathVariable("id") String id) {
       Optional<Stock> stockOpt = stockRepository.findById(id);

       if (stockOpt.isPresent()) {
           stockRepository.delete(stockOpt.get());
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }

   // NEW: GET stock value by ID
   @GetMapping("/stock-value/{id}")
   public ResponseEntity<Map<String, Object>> getStockValue(@PathVariable("id") String id) {
       try {
           Optional<Stock> stockOpt = stockRepository.findById(id);
           if (stockOpt.isPresent()) {
               Stock stock = stockOpt.get();
               Map<String, Object> stockPriceData = NinjaApiService.getStockPrice(stock.getSymbol());
               double stockValue = (double) stockPriceData.get("price") * stock.getNumberOfShares();

               Map<String, Object> response = new HashMap<>();
               response.put("symbol", stock.getSymbol());
               response.put("ticker", stockPriceData.get("price"));
               response.put("stock value", stockValue);

               return new ResponseEntity<>(response, HttpStatus.OK);
           } else {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
       } catch (Exception e) {
           return new ResponseEntity<>(Map.of("error", "Server error"), HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   // NEW: GET total portfolio value
   @GetMapping("/portfolio-value")
   public ResponseEntity<Map<String, Object>> getPortfolioValue() {
       try {
           double totalValue = 0;
           List<Stock> stocks = stockRepository.findAll();

           for (Stock stock : stocks) {
               Map<String, Object> stockPriceData = NinjaApiService.getStockPrice(stock.getSymbol());
               totalValue += (double) stockPriceData.get("price") * stock.getNumberOfShares();
           }

           Map<String, Object> response = new HashMap<>();
           response.put("date", LocalDate.now().toString());
           response.put("portfolio value", totalValue);

           return new ResponseEntity<>(response, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(Map.of("error", "Server error"), HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   @GetMapping("/kill")
    public ResponseEntity<String> killService() {
        System.exit(1); // Exits the application with status 1
        return ResponseEntity.ok("Service is shutting down..."); // This will "never" be reached
    }


}
