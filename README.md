# 🧵 Tricol – Supplier Orders & Stock Management API

[![code coverage](https://img.shields.io/badge/coverage-64%25-red)](jacoco/index.html) 
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java](https://img.shields.io/badge/java-17-blue.svg)](https://www.java.com)

## 📘 Overview

**Tricol** is a Spring Boot–based REST API designed for managing **suppliers**, **supplier orders**, and **inventory movements**.
It provides a complete digital solution for supplier relationship management, purchase tracking, and real-time stock valuation using **FIFO** method.

---

## 🚀 Features

### 🧾 Supplier Management
- Create, update, delete, and view suppliers
- Fields: company, address, contact, email, phone, city, ICE

### 📦 Product Management
- Manage product catalog and categories
- Fields: name, description, unit price, category, current stock

### 🧺 Supplier Orders
- Create and manage supplier orders
- Associate orders with suppliers and multiple products
- Automatically calculate total order amount
- Statuses: `EN_ATTENTE`, `VALIDÉE`, `LIVRÉE`, `ANNULÉE`

### 📊 Stock Movements & Valuation
- Automatic stock entries upon order delivery
- Track entries, exits, and adjustments
- Real-time stock quantity updates
- Configurable valuation methods:
  - **FIFO (First In, First Out)**
  - **CUMP (Weighted Average Cost)**

### 🔎 Pagination & Filtering
- Supported on all main entities (suppliers, products, orders)
- Query parameters:
  ```
  ?page=0&size=10&sort=company,asc
  ```
- Responses include total elements and total pages

---

## 🧱 Architecture

Layered architecture following clean design principles:

| Layer | Responsibility |
|-------|----------------|
| Controller | Exposes REST endpoints |
| Service | Business logic layer |
| Repository | Data access via Spring Data JPA |
| DTO & Mapper | Data transfer using MapStruct |
| Liquibase | Database migrations management |

---

## 🗄️ Data Model (Simplified)

### Supplier
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Identifier |
| company | String | Company name |
| address | String | Address |
| contact | String | Contact person |
| email | String | Email |
| phone | String | Phone |
| city | String | City |
| ice | int | ICE (company ID) |
| createdAt | LocalDateTime | Creation timestamp |

### Product
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Identifier |
| name | String | Product name |
| description | String | Description |
| quantity | int | Available stock |
| unitPrice | double | Unit price |
| category | String | Category |
| createdAt | LocalDateTime | Creation timestamp |

### SupplierOrder
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Identifier |
| orderDate | Date | Order date |
| status | String | EN_ATTENTE / VALIDÉE / LIVRÉE / ANNULÉE |
| totalAmount | double | Total order cost |
| supplier | Supplier | Linked supplier |
| products | List<Product> | Ordered products |
| createdAt | LocalDateTime | Creation timestamp |

### StockMovement
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Identifier |
| type | Enum | ENTREE / SORTIE / AJUSTEMENT |
| quantity | int | Quantity moved |
| date | Date | Movement date |
| supplierOrder | SupplierOrder | Related order |
| createdAt | LocalDateTime | Creation timestamp |

---

## ⚙️ Technologies

| Category | Tools / Frameworks | Version |
|-----------|--------------------|---------|
| Backend | Spring Boot | 3.5.7 |
| Language | Java | 17 |
| ORM | Spring Data JPA, Hibernate | 3.5.7 |
| Database | PostgreSQL | Managed by Spring Boot |
| Test Database | H2 | Managed by Spring Boot |
| Migrations | Liquibase | Managed by Spring Boot |
| Mapping | MapStruct | 1.6.3 |
| Validation | Jakarta Validation | 3.5.7 |
| API Documentation | SpringDoc OpenAPI (Swagger) | 2.8.13 |
| Code Generation | Lombok | 1.18.42 |
| Build Tool | Maven | 3.9.11 |
| Code Coverage | Jacoco | 0.8.14 |

---

## 🔧 Setup & Installation

### 1. Prerequisites
- Java 17+
- Maven
- PostgreSQL

### 2. Database Configuration
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tricol
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.liquibase.change-log=classpath:db/changelog/changelog-master.xml
```

### 3. Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Access Swagger UI
```
http://localhost:8080/swagger-ui.html
```

---

## 📡 API Endpoints

### Suppliers
| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/api/v1/suppliers` | List suppliers (paginated) |
| GET | `/api/v1/suppliers/{id}` | Get supplier details |
| POST | `/api/v1/suppliers` | Create supplier |
| PUT | `/api/v1/suppliers/{id}` | Update supplier |
| DELETE | `/api/v1/suppliers/{id}` | Delete supplier |

### Products
| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/api/v1/products` | List products |
| GET | `/api/v1/products/{id}` | Get product details |
| POST | `/api/v1/products` | Create product |
| PUT | `/api/v1/products/{id}` | Update product |
| DELETE | `/api/v1/products/{id}` | Delete product |

### Supplier Orders
| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/api/v1/orders` | List supplier orders |
| GET | `/api/v1/orders/{id}` | Get order details |
| POST | `/api/v1/orders` | Create order |
| PUT | `/api/v1/orders/{id}` | Update order (status/products) |
| DELETE | `/api/v1/orders/{id}` | Cancel order |

### Stock Movements
| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/api/v1/stock-movements` | Filter movements by product, type, or order |

---

## 🧪 Testing

The project uses **JUnit 5** and **Mockito** for unit and integration testing.

Tests run against an in-memory **H2 database** to ensure isolation and avoid impacting the development database.

### Running Tests

To run the full test suite, execute the following Maven command:

```bash
mvn clean install
```

### Code Coverage

**JaCoCo** is used to generate code coverage reports. the report can be found at:

```
jacoco/index.html
```

---

## 🤝 Contributing

Contributions, suggestions, and improvements are always welcome.
Please open an issue or submit a pull request.

---

## 📄 License
[MIT License](https://choosealicense.com/licenses/mit/)