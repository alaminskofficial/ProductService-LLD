# Product Management System

This is a product management system built using Java, SQL, Spring Boot, and Maven.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 8 or higher
- Maven
- SQL ,NoSQL database

### Installing

1. Clone the repository
```bash
git clone https://github.com/alaminskofficial/ProductService-LLD.git

```

api postman collection link :
```bash
https://api.postman.com/collections/33330094-f3166d77-7483-4ab3-9038-a460016d2a93?access_key=PMAT-01HX1KY257FWSGSJ7BVSASDRQF
```

2. Navigate to the project directory
```bash
cd product-management-system
```
3.install the maven dependencies
```bash
mvn clean install
```
4. Run the application
```bash
mvn spring-boot:run
```
5. The application will be accessible at `http://localhost:8080`


**API Endpoints**

The application provides the following endpoints:  

GET /product: Test endpoint, returns a greeting from the product service.

GET /getproducts?pageNo={pageNo}&pageSize={pageSize}: Returns a paginated list of products sorted by price.

GET /products: Returns a list of all products whose name starts with "S".

GET /products/search?name={name}: Returns a product with the given name.

GET /product/{id}: Returns a single product with the given ID.

GET /products/categories: Returns a list of all product categories.

GET /products/category/{id}: Returns a list of all products in the given category.

POST /products: Adds a new product.

GET /products/{productId}/reviews : to retrieve reviews for a product

POST /products/{productId}/reviews : add a review with image upload Using MongoDB GridFS 

GET /image/{fileId} : to download the review image


**Built With**
Java
Spring Boot
Maven
SQL
Postman







**Authors**

Sk Alamin
