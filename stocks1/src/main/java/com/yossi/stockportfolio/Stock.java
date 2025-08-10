package com.yossi.stockportfolio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "stocks1")
public class Stock {
    @Id // Marks the id as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    
    @Column(name = "company_name")
    @JsonProperty("name") // Maps JSON 'name' to 'companyName'
    private String companyName;

    @Column(name = "symbol")
    private String symbol;


    @Column(name = "price")
    @JsonProperty("purchase price") // Maps JSON 'purchasePrice' to 'price'
    private Double price;
        
    
    @Column(name = "number_of_shares")
    @JsonProperty("shares") // Maps JSON 'shares' to 'numberOfShares'
    private Integer numberOfShares;
    
    @Column(name = "purchase_date")
    @JsonProperty("purchase date") // Maps JSON 'purchaseDate' to 'date'
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") 
    private LocalDate date;

    // Getters and Setters for all fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(Integer numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                ", numberOfShares=" + numberOfShares +
                ", date=" + date +
                '}';
    }
}
