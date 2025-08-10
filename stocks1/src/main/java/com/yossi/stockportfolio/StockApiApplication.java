package com.yossi.stockportfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.yossi.stockportfolio")
@EnableJpaRepositories(basePackages = "com.yossi.stockportfolio")
@EntityScan("com.yossi.stockportfolio")

public class StockApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockApiApplication.class, args);
    }
}
