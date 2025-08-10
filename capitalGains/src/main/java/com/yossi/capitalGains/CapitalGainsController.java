package com.yossi.capitalGains;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/capital-gains")
public class CapitalGainsController {

    private final CapitalGainsService capitalGainsService;

    @Autowired
    public CapitalGainsController(CapitalGainsService capitalGainsService) {
        this.capitalGainsService = capitalGainsService;
    }

    

    // 1. Modify the controller to accept the portfolio query parameter
@GetMapping
public double getCapitalGains(
        @RequestParam(value = "portfolio", required = false) String portfolio,
        @RequestParam(value = "numsharesgt", required = false) Integer numSharesGt,
        @RequestParam(value = "numshareslt", required = false) Integer numSharesLt) {
    // Check if a portfolio filter is provided, if not, calculate for both portfolios
    if (portfolio != null && numSharesGt != null) {
        return capitalGainsService.getCapitalGainsForPortfolioWithSharesGreaterThan(portfolio, numSharesGt);
    } else if (portfolio != null && numSharesLt != null) {
        return capitalGainsService.getCapitalGainsForPortfolioWithSharesLessThan(portfolio, numSharesLt);
    } else if (portfolio != null) {
        return capitalGainsService.getCapitalGainsForPortfolio(portfolio);
    } else if (numSharesGt != null) {
        return capitalGainsService.getCapitalGainsWithSharesGreaterThan(numSharesGt);
    } else if (numSharesLt != null) {
        return capitalGainsService.getCapitalGainsWithSharesLessThan(numSharesLt);
    } else {
        return capitalGainsService.getCapitalGains();
    }
}

}









    