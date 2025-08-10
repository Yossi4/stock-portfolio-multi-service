package com.yossi.stockportfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    
    // Find a stock by its ID (this is provided by JpaRepository automatically)
    Optional<Stock> findById(String id);
    
    // Find stocks by their company name (if you'd like to query by a specific attribute)
    List<Stock> findByCompanyName(String companyName);
    
    // Find stocks by their symbol (if you'd like to query by symbol)
    List<Stock> findBySymbol(String symbol);
    
    // You can add more custom queries here if necessary
    // For example, find all stocks with a price greater than a certain value
    List<Stock> findByPriceGreaterThan(Double price);
    
    // You can also use @Query annotation for more complex queries if needed:
    // @Query("SELECT s FROM Stock s WHERE s.price > ?1")
    // List<Stock> findStocksByPriceGreaterThan(Double price);
}
