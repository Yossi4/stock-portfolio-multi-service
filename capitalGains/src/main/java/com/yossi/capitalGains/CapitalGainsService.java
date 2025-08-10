package com.yossi.capitalGains;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CapitalGainsService {

    private final RestTemplate restTemplate;

    public CapitalGainsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    // New method to fetch all stocks
    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();

        // Fetch stocks from stocks1
        try {
            Stock[] stocks1 = restTemplate.getForObject("http://stocks1a:8000/stocks", Stock[].class);
            if (stocks1 != null) {
                for (Stock stock : stocks1) {
                    stock.setPortfolio(1); // Mark portfolio as stocks1
                    stocks.add(stock);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching data from stocks1: " + e.getMessage());
        }

        // Fetch stocks from stocks2
        try {
            Stock[] stocks2 = restTemplate.getForObject("http://stocks2:8000/stocks", Stock[].class);
            if (stocks2 != null) {
                for (Stock stock : stocks2) {
                    stock.setPortfolio(2); // Mark portfolio as stocks2
                    stocks.add(stock);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching data from stocks2: " + e.getMessage());
        }

        return stocks;
    }
    

    // New method to calculate capital gains for a given list of stocks
    public double calculateCapitalGains(List<Stock> stocks) {
        double totalCapitalGains = 0.0;

        for (Stock stock : stocks) {
            double currentValue = getCurrentStockValue(stock.getId(), stock.getPortfolio());
            double gain = currentValue - (stock.getPrice() * stock.getNumberOfShares());
            totalCapitalGains += gain;
        }

        return totalCapitalGains;
    }




    public double getCapitalGains() {
        // Fetch stocks from both services
        List<Stock> stocks = new ArrayList<>();

        // Fetch stocks from stocks1
        try {
            Stock[] stocks1 = restTemplate.getForObject("http://stocks1a:8000/stocks", Stock[].class);
            if (stocks1 != null) {
                for (Stock stock : stocks1) {
                    stock.setPortfolio(1); // Populating the portfolio field
                }
                stocks.addAll(Arrays.asList(stocks1));
            }
        } catch (Exception e) {
            System.err.println("Error fetching data from stocks1: " + e.getMessage());
        }

        // Fetch stocks from stocks2
        try {
            Stock[] stocks2 = restTemplate.getForObject("http://stocks2:8000/stocks", Stock[].class);
            if (stocks2 != null) {
                for (Stock stock : stocks2) {
                    stock.setPortfolio(2); // Populating the portfolio field
                }
                stocks.addAll(Arrays.asList(stocks2));
            }
        } catch (Exception e) {
            System.err.println("Error fetching data from stocks2: " + e.getMessage());
        }

        // Calculate total capital gains (placeholder logic)
        double totalCapitalGains = 0.0;
        for (Stock stock : stocks) {
            // Fetch the current value using stock ID
            double currentValue = getCurrentStockValue(stock.getId(), stock.getPortfolio());
        
            // Log the values to check them
            System.out.println("Stock ID: " + stock.getId());
            System.out.println("Purchase Price: " + stock.getPrice());
            System.out.println("Current Stock Value: " + currentValue);
            System.out.println("Number of Shares: " + stock.getNumberOfShares());
            System.out.println("Portfolio: " + stock.getPortfolio());
            System.out.println("");

        
            // Calculate gain
            double gain = currentValue - (stock.getPrice() * stock.getNumberOfShares());
            System.out.println("Capital Gain: " + gain);
            totalCapitalGains += gain;
        }
        

        return totalCapitalGains;
    }

    private double getCurrentStockValue(String id, int portfolio) {
        double stockValue = 0.0;
    
        if(portfolio == 1)
        {
        // Fetch stock value from stocks1 (port 5001)
        try {
            StockValueResponse response1 = restTemplate.getForObject("http://stocks1a:8000/stock-value/" + id, StockValueResponse.class);
            if (response1 != null) {
                System.out.println("Source: stocks1");
                System.out.println("Symbol: " + response1.getSymbol());
                System.out.println("Stock Value: " + response1.getStockValue());
                System.out.println("");

                stockValue += response1.getStockValue(); // Add stock value from stocks1
            } else {
                System.err.println("No valid response from stocks1 for stock ID " + id);
            }
        } catch (Exception e) {
            System.err.println("Error fetching data from stocks1 for stock ID " + id + ": " + e.getMessage());
        }
        }
        
       else
       {
        // Fetch stock value from stocks2 (port 5002)
        try {
            StockValueResponse response2 = restTemplate.getForObject("http://stocks2:8000/stock-value/" + id, StockValueResponse.class);
            if (response2 != null) {
                System.out.println("Source: stocks2");
                System.out.println("Symbol: " + response2.getSymbol());
                System.out.println("Stock Value: " + response2.getStockValue());
                System.out.println("");

                stockValue += response2.getStockValue(); // Add stock value from stocks2
            } else {
                System.err.println("No valid response from stocks2 for stock ID " + id);
            }
        } catch (Exception e) {
            System.err.println("Error fetching data from stocks2 for stock ID " + id + ": " + e.getMessage());
        }
       }
    
        // Return the combined stock value from both services
        return stockValue;
    }
    




   
   
   
   
   
   
    // ***2. Add a new method to handle portfolio-based filtering in the service
    public double getCapitalGainsForPortfolio(String portfolio) {
    // Fetch stocks from both services
    List<Stock> stocks = new ArrayList<>();

    if (portfolio.equals("stocks1")) 
    {
        // Fetch stocks from stocks1
        try {
          Stock[] stocks1 = restTemplate.getForObject("http://stocks1a:8000/stocks", Stock[].class);
            if (stocks1 != null) {
                for (Stock stock : stocks1) {
                 stock.setPortfolio(1); // Populating the portfolio field
                }
                stocks.addAll(Arrays.asList(stocks1));
            }
        } catch (Exception e) {
            System.err.println("Error fetching data from stocks1: " + e.getMessage());
        }    
    }
    
   else
   {
     // Fetch stocks from stocks2
     try {
        Stock[] stocks2 = restTemplate.getForObject("http://stocks2:8000/stocks", Stock[].class);
        if (stocks2 != null) {
            for (Stock stock : stocks2) {
                stock.setPortfolio(2); // Populating the portfolio field
            }
            stocks.addAll(Arrays.asList(stocks2));
        }
    } catch (Exception e) {
        System.err.println("Error fetching data from stocks2: " + e.getMessage());
    }
   }


  

    // Calculate total capital gains (same logic as before, but now for a filtered list)
    double totalCapitalGains = 0.0;
    for (Stock stock : stocks) {
        double currentValue = getCurrentStockValue(stock.getId(), stock.getPortfolio()); //*********** */
        double gain = currentValue - (stock.getPrice() * stock.getNumberOfShares());
        totalCapitalGains += gain;
    }

    return totalCapitalGains;
}





public double getCapitalGainsWithSharesGreaterThan(Integer numsharesgt) {
    // Fetch stocks from both services
    List<Stock> stocks = getAllStocks();

    // Filter stocks by number of shares
    if (numsharesgt != null) {
        stocks.removeIf(stock -> stock.getNumberOfShares() <= numsharesgt);
    }

    // Calculate capital gains
    return calculateCapitalGains(stocks);
}

public double getCapitalGainsWithSharesLessThan(Integer numsharesgt) {
    // Fetch stocks from both services
    List<Stock> stocks = getAllStocks();

    // Filter stocks by number of shares
    if (numsharesgt != null) {
        stocks.removeIf(stock -> stock.getNumberOfShares() >= numsharesgt);
    }

    // Calculate capital gains
    return calculateCapitalGains(stocks);
}


public double getCapitalGainsForPortfolioWithSharesGreaterThan(String portfolio, Integer numsharesgt) {
    // Fetch stocks from the specified portfolio
    List<Stock> stocks = getStocksForPortfolio(portfolio);

    // Filter stocks by number of shares
    if (numsharesgt != null) {
        stocks.removeIf(stock -> stock.getNumberOfShares() <= numsharesgt);
    }

    // Calculate capital gains
    return calculateCapitalGains(stocks);
}

public double getCapitalGainsForPortfolioWithSharesLessThan(String portfolio, Integer numsharesgt) {
    // Fetch stocks from the specified portfolio
    List<Stock> stocks = getStocksForPortfolio(portfolio);

    // Filter stocks by number of shares
    if (numsharesgt != null) {
        stocks.removeIf(stock -> stock.getNumberOfShares() >= numsharesgt);
    }

    // Calculate capital gains
    return calculateCapitalGains(stocks);
}

private List<Stock> getStocksForPortfolio(String portfolio) {
    List<Stock> stocks = new ArrayList<>();
    if ("stocks1".equals(portfolio)) {
        // Fetch stocks from stocks1
        stocks.addAll(getStocksFromService("http://stocks1a:8000/stocks", 1));
    } else if ("stocks2".equals(portfolio)) {
        // Fetch stocks from stocks2
        stocks.addAll(getStocksFromService("http://stocks2:8000/stocks", 2));
    }
    return stocks;
}


private List<Stock> getStocksFromService(String url, int portfolioId) {
    List<Stock> stocks = new ArrayList<>();
    try {
        Stock[] stocksArray = restTemplate.getForObject(url, Stock[].class);
        if (stocksArray != null) {
            for (Stock stock : stocksArray) {
                stock.setPortfolio(portfolioId); // Mark the portfolio
                stocks.add(stock);
            }
        }
    } catch (Exception e) {
        System.err.println("Error fetching data from service: " + e.getMessage());
    }
    return stocks;
}

    
    
    
}
