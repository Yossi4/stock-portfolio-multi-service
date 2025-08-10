# Stock Portfolio Service - Cloud Computing

This project extends the Stock Portfolio Service from Assignment #1 by introducing high availability, persistence, multiple services, and 
orchestration using Docker Compose. The architecture includes two instances of the stock service, a capital gains service, a MySQL database for 
persistence, and an NGINX reverse proxy for routing and load balancing.

---

## Assignment Overview

- Use Docker Compose to build an application composed of 5 services:
  - **stocks1**: First instance of the stock portfolio service  
  - **stocks1-b**: Second instance of the stocks1 service (for load balancing)  
  - **stocks2**: Second independent stock portfolio service instance  
  - **capital-gains**: A service that calculates capital gains across portfolios  
  - **MySQL database**: Persistent storage for the stock services  
  - **NGINX**: Reverse proxy handling routing and load balancing  

- Both stocks services persist data in MySQL and recover gracefully from failures using Docker Compose's restart policy.
- NGINX routes requests appropriately and implements weighted round-robin load balancing for the two stocks1 instances.
- The capital gains service queries the stocks services to calculate capital gains based on user requests.

---

## Architecture

- Two stocks1 service containers (`stocks1-a` and `stocks1-b`) share the same MySQL database collection and serve requests with load balancing:
  - For every 3 requests to `stocks1-a`, 1 request goes to `stocks1-b`.
- One stocks2 service container with its own MySQL database collection, running independently.
- The capital-gains service queries both stocks services to compute capital gains.
- NGINX reverse proxy:
  - Listens on host port 80.
  - Routes `/stocks1` and `/stocks2` requests to the appropriate service.
  - Load balances GET requests for stocks1 services.
  - Only routes specific paths (no forwarding of `/stock-value` or `/portfolio-value`).
- Direct requests to stocks1 (ports 5001, 5002) and stocks2 (port 5003) do not go through NGINX.

---

## Services and Ports

| Service         | Description                       | Host Port | Notes                                  |
|-----------------|---------------------------------|-----------|----------------------------------------|
| stocks1-a       | First stocks1 instance           | 5001      | Shares DB with stocks1-b                |
| stocks1-b       | Second stocks1 instance          | 5002      | Load balanced by NGINX                  |
| stocks2          | Independent stocks service       | 5003      | Own DB collection                       |
| capital-gains   | Capital gains calculation service| 5004      | No persistence, queries stocks services|
| MySQL           | Database service                 | 3306      | Stores persistent stock data            |
| NGINX           | Reverse proxy                   | 80        | Routes and load balances requests       |

---

## Capital Gains Service

- Endpoint: `GET /capital-gains`
- Returns total capital gain:  
  `capital gain = current stock value - purchase price`  
  (stock value = number of shares × current share price)
- Supports query strings:  
  - `portfolio=stocks1` or `portfolio=stocks2` to filter portfolios  
  - `numsharesgt=<int>` and/or `numshareslt=<int>` to filter by number of shares  
- Examples:  
  - `/capital-gains?numsharesgt=10`  
  - `/capital-gains?portfolio=stocks2`  
  - `/capital-gains?portfolio=stocks1&numsharesgt=10&numshareslt=20`

---

## Docker Compose Features

- Starts all services in the correct order.
- Implements restart policies to auto-recover stocks services on failure.
- Stocks services expose `/kill` endpoint for testing auto-restart.
- Uses Docker networking for inter-service communication via service names.
- Loads NGINX with configuration for routing and weighted round robin load balancing.

---

## Notes on Implementation

- **Java** is used for all services (stocks and capital gains).  
- **MySQL** is used as the persistent database backend (not MongoDB).  
- Services connect to MySQL via Docker network and appropriate ports.  
- NGINX configuration uses weighted round robin: 3 requests to `stocks1-a` for every 1 to `stocks1-b`.  
- Requests from the capital gains service to stocks1 go to `stocks1-a`.

---

## Running the Application

1. Make sure Docker and Docker Compose are installed on your machine.  
2. Place all service Dockerfiles, source code, and `docker-compose.yml` in your project directory.  
3. Build and start all services:  
   ```bash
   docker-compose up --build

