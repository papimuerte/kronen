# gRPC Microservice Documentation

## Overview
This gRPC microservice provides inventory management functionalities, allowing clients to check product availability and update inventory levels in a seamless and efficient manner.

## Features
- **Product Availability Check:** Clients can verify whether a product is in stock and retrieve the available quantity.
- **Inventory Update:** The service allows updating product stock levels when items are sold or restocked.
- **High Performance:** Uses gRPC for efficient, low-latency communication.
- **Secure & Scalable:** Integrates security configurations and supports future scalability.

## Setup & Running the Service
### 1. Compile the Project
Before starting the server, ensure the project is properly compiled by running:
```sh
mvn clean compile
```

### 2. Start the gRPC Server
Once compiled, start the gRPC server using:
```sh
mvn spring-boot:run
```
The server will start on port `9090` and be ready to accept requests.

## How It Works
1. **Client Request:** A client sends a gRPC request to check product availability or update inventory.
2. **Processing Logic:** The service retrieves or updates product data from an external data source.
3. **Response Handling:** The service returns the requested data or an error message if the product is not found.

## Usage
### 1. Checking Product Availability
A client can check the availability of a product by sending a request with a `productId` and `quantity`:
```sh
grpcurl --plaintext -d '{"productId":"J001","quantity":10}' localhost:9090 com.grpc.InventoryService/CheckAvailability
```
Response Example:
```json
{
  "productId": "J001",
  "available": true,
  "availableQuantity": 50
}
```

### 2. Updating Inventory
A client can update inventory levels by specifying the `productId` and the quantity to deduct:
```sh
grpcurl --plaintext -d '{"productId":"J001","quantity":5}' localhost:9090 com.grpc.InventoryService/UpdateInventory
```
Response Example:
```json
{
  "productId": "J001",
  "success": true,
  "message": "Inventory updated successfully"
}
```

## Conclusion
This gRPC microservice provides a robust and efficient way to manage product inventory. It is optimized for speed, scalability, and ease of integration with existing inventory management systems.

