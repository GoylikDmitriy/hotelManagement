# Hotel Management API

A RESTful API service for managing hotel information. This project is developed using **Java 21** and **Spring Boot**, providing a comprehensive set of operations for managing hotels, their amenities, as well as search and analytical functions.

## About The Project

This API allows you to perform standard CRUD operations and offers advanced features:
*   **Retrieve Information** about hotels (both brief and detailed).
*   **Search for Hotels** based on various parameters (name, brand, city, country, amenities).
*   **Manage Amenities**: add new amenities to a hotel.
*   **Analytical Data**: obtain aggregated statistics (histograms) to analyze the distribution of hotels by city, brand, country, and amenities.

## Features (Endpoints)

All endpoints share the common prefix `/property-view`.

### 1. Hotels
*   **`GET /hotels`** — retrieve a list of all hotels with brief information.
*   **`GET /hotels/{id}`** — retrieve detailed information for a specific hotel (including a list of its amenities).
*   **`POST /hotels`** — create a new hotel.

### 2. Search and Analytics
*   **`GET /search`** — search for hotels by parameters:
    *   `name` — hotel name (partial, case-insensitive match)
    *   `brand` — brand (exact, case-insensitive match)
    *   `city` — city (exact, case-insensitive match)
    *   `country` — country (exact, case-insensitive match)
    *   `amenities` — a list of amenities (the hotel must have **all** specified amenities)
*   **`GET /histogram/{param}`** — get a histogram (count of hotels) grouped by the value of a specific parameter.
    *   The `{param}` can be one of: `brand`, `city`, `country`, `amenities`.

### 3. Amenity Management
*   **`POST /hotels/{id}/amenities`** — add a list of amenities to a specific hotel. If an amenity does not exist in the system, it will be created automatically.

## Technology Stack

*   **Java 21** — core programming language.
*   **Spring Boot 3** — framework for building the application.
*   **Spring Data JPA (Hibernate)** — for database interaction.
*   **Spring Web** — for creating RESTful endpoints.
*   **Liquibase** — for database schema version control and migrations.
*   **H2 Database** — in-memory database for development and testing.
*   **Maven** — build automation and dependency management tool.
*   **Lombok** — to reduce boilerplate code.
*   **Swagger (springdoc-openapi)** — for automatic API documentation generation.

## Getting Started

### Prerequisites
*   **Java 21** (or later) installed.
*   **Maven** installed.

### Installation & Run
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/GoylikDmitriy/hotelManagement.git
    ```
2.  **Navigate to the project directory:**
    ```bash
    cd hotelManagement
    ```
3.  **Run the application using Maven:**
    ```bash
    mvn spring-boot:run
    ```

Once the application is running, it will be available at: `http://localhost:8092`.

### API Docs
*   **Swagger UI** (interactive API documentation): `http://localhost:8092/property-view/swagger-ui.html`
*   **OpenAPI JSON:** `http://localhost:8092/property-view/v3/api-docs`
