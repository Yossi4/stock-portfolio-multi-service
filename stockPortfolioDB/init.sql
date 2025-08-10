-- Create the database if it doesn't already exist
CREATE DATABASE IF NOT EXISTS stockPortfolioDB;

-- Grant privileges to the user
GRANT ALL PRIVILEGES ON stockPortfolioDB.* TO 'stockuser'@'%';
FLUSH PRIVILEGES;

-- Create the stock table for stocks1 if it doesn't exist
CREATE TABLE IF NOT EXISTS stockPortfolioDB.stocks1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255),
    date DATE,
    number_of_shares INT,
    price DOUBLE,
    symbol VARCHAR(255),
    purchase_date DATE
);

-- Create the stock table for stocks2 if it doesn't exist
CREATE TABLE IF NOT EXISTS stockPortfolioDB.stocks2 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255),
    date DATE,
    number_of_shares INT,
    price DOUBLE,
    symbol VARCHAR(255),
    purchase_date DATE
);

