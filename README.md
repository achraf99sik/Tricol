# 🧵 Tricol – Supplier Orders & Stock Management API

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

### Fournisseur
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Identifier |
| societe | String | Company name |
| adresse | String | Address |
| contact | String | Contact person |
| email | String | Email |
| telephone | String | Phone |
| ville | String | City |
| ice | String | ICE (company ID) |

### Produit
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Identifier |
| nom | String | Product name |
| description | String | Description |
| prixUnitaire | BigDecimal | Unit price |
| categorie | String | Category |
| stockActuel | Integer | Current available stock |

### CommandeFournisseur
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Identifier |
| dateCommande | LocalDate | Order date |
| statut | Enum | EN_ATTENTE / VALIDÉE / LIVRÉE / ANNULÉE |
| montantTotal | BigDecimal | Total order cost |
| fournisseur | Fournisseur | Linked supplier |
| produits | List<Produit> | Ordered products |

### MouvementStock
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Identifier |
| type | Enum | ENTREE / SORTIE / AJUSTEMENT |
| quantite | Integer | Quantity moved |
| dateMouvement | LocalDateTime | Movement date |
| produit | Produit | Related product |
| commande | CommandeFournisseur | Related order |

---

## ⚙️ Technologies

| Category | Tools / Frameworks |
|-----------|--------------------|
| Backend | Spring Boot 3, Java 17 |
| ORM | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Migrations | Liquibase |
| Mapping | MapStruct |
| Validation | Jakarta Validation |
| Documentation | Swagger / OpenAPI |
| Build Tool | Maven |

---

## 🔧 Setup & Installation

### 1. Prerequisites
- Java 17+  
- Maven  
- PostgreSQL
- MapStruct
- Liquibase

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
| GET | `/api/suppliers` | List suppliers (paginated) |
| GET | `/api/suppliers/{id}` | Get supplier details |
| POST | `/api/suppliers` | Create supplier |
| PUT | `/api/suppliers/{id}` | Update supplier |
| DELETE | `/api/suppliers/{id}` | Delete supplier |

### Products
| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/api/products` | List products |
| GET | `/api/products/{id}` | Get product details |
| POST | `/api/products` | Create product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

### Supplier Orders
| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/api/orders` | List supplier orders |
| GET | `/api/orders/{id}` | Get order details |
| POST | `/api/orders` | Create order |
| PUT | `/api/orders/{id}` | Update order (status/products) |
| DELETE | `/api/orders/{id}` | Cancel order |

### Stock Movements
| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/api/stock-movements` | Filter movements by product, type, or order |

---

## 🤝 Contributing

Contributions, suggestions, and improvements are always welcome.  
Please open an issue or submit a pull request.

---

## 📄 License
[MIT License](https://choosealicense.com/licenses/mit/)
