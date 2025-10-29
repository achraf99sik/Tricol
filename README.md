# Tricol

This is a simple Spring MVC web application for managing suppliers. It provides a RESTful API for CRUD operations on suppliers.

## Technologies Used

*   **Backend:**
    *   Java 17
    *   Spring Framework 6.2.11
    *   Spring Data JPA 3.2.5
    *   Hibernate 6.4.4.Final
    *   PostgreSQL
*   **Build Tool:**
    *   Maven

## Features

*   **CRUD Operations:** Create, Read, Update, and Delete suppliers.
*   **RESTful API:** Exposes a RESTful API for managing suppliers.
*   **Sorting and Filtering:** Supports sorting and filtering of suppliers.

## Setup and Installation

1.  **Prerequisites:**
    *   Java 17
    *   Maven
    *   PostgreSQL

2.  **Database Setup:**
    *   Create a PostgreSQL database named `tricol`.
    *   Update the database credentials in `src/main/resources/application.properties`. You will need to create this file. Here is an example:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/tricol
        spring.datasource.username=your_username
        spring.datasource.password=your_password
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        ```

3.  **Build the Project:**
    ```bash
    mvn clean install
    ```

4.  **Run the Application:**
    *   Deploy the generated `Tricol.war` file to a servlet container like Tomcat.
    *   Alternatively, you can run the application using the Smart Tomcat plugin in IntelliJ IDEA.

## API Endpoints

### Suppliers API

| Method | Endpoint              | Description                     |
| ------ | --------------------- | ------------------------------- |
| GET    | `/api/suppliers`      | Get paginated list of suppliers |
| GET    | `/api/suppliers/{id}` | Get supplier details            |
| POST   | `/api/suppliers`      | Create new supplier             |
| PUT    | `/api/suppliers/{id}` | Update supplier                 |
| DELETE | `/api/suppliers/{id}` | Delete supplier                 |

### Products API

| Method | Endpoint             | Description                    |
| ------ | -------------------- | ------------------------------ |
| GET    | `/api/products`      | Get paginated list of products |
| GET    | `/api/products/{id}` | Get product details            |
| POST   | `/api/products`      | Create new product             |
| PUT    | `/api/products/{id}` | Update product                 |
| DELETE | `/api/products/{id}` | Delete product                 |

### Supplier Orders API

| Method | Endpoint           | Description                                 |
| ------ | ------------------ | ------------------------------------------- |
| GET    | `/api/orders`      | Get paginated list of orders                |
| GET    | `/api/orders/{id}` | Get order details                           |
| POST   | `/api/orders`      | Create new supplier order (with products)   |
| PUT    | `/api/orders/{id}` | Update order (status, products, quantities) |
| DELETE | `/api/orders/{id}` | Cancel/delete order                         |

### Stock Movements API

| Method | Endpoint               | Description                                             |
| ------ | ---------------------- | ------------------------------------------------------- |
| GET    | `/api/stock-movements` | Get stock movements with filters (product, type, order) |


## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](httpsen.choosealicense.com/licenses/mit/)
