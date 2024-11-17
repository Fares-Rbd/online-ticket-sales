# Online Ticket Sales Platform

An **event ticket sales platform** built using a **microservices architecture** with Spring Boot and Spring Cloud. This application enables users to purchase tickets for various events, manage event categories, and handle ticket distribution seamlessly.

---

## **Class Diagram**

![image](https://github.com/user-attachments/assets/6649dbb0-6864-4780-b8b6-28275219bc0e)


---

## **Features**
- **User Management**:
  - CRUD operations for managing users.
  - Validate users for ticket purchases.
  
- **Event and Category Management**:
  - Create and manage events.
  - Categorize events into multiple categories.
  - Periodic tasks to list events by category.

- **Ticket Management**:
  - Issue tickets to users for specific events.
  - Enforce rules for ticket availability.
  - Calculate event revenue based on ticket sales.
  - Identify the most active user.

---

## **Microservices Overview**
The platform is divided into five microservices:

### 1. **User Management Service** (`user-management`)
- Manages user details (e.g., name, age group).
- APIs to create, retrieve, and validate users.
  
### 2. **Event and Category Management Service** (`event-management`)
- Manages events and their categories.
- Provides APIs for event creation and category association.
- Includes periodic scheduling for event categorization.

### 3. **Ticket Management Service** (`ticket-management`)
- Issues tickets to users for events.
- Handles ticket-related validations and revenue calculations.
- Manages seat availability and logs exceptions.

### 4. **Eureka Server** (`discovery-server`)
- Service registry for discovering microservices.

### 5. **Config Server** (`config-server`)
- Centralized configuration management for all microservices.

---

## **Technologies Used**
- **Spring Boot**: Core framework for building microservices.
- **Spring Cloud**:
  - Eureka (Service Discovery)
  - Config Server (Centralized Configuration)
  - OpenFeign (Inter-service communication)
- **Spring Data JPA**: ORM and database interaction.
- **H2/MySQL/PostgreSQL**: Database layers for services.
- **Spring AOP**: For logging and cross-cutting concerns.
- **Spring Scheduler**: For periodic tasks.
- **Maven**: Build and dependency management.
- **Lombok**: To reduce boilerplate code.
- **Docker** (Future Scope): Containerization.

---

## **Architecture**
The application uses a **microservices architecture** where each service is independently deployable and communicates through REST APIs or event-driven messaging.

### **Service Relationships**
- **User Management** ↔ **Ticket Management**:
  - `Ticket Management` uses `User Management` to validate and fetch user details.
  
- **Event Management** ↔ **Ticket Management**:
  - `Ticket Management` checks and updates event availability through `Event Management`.

### **Inter-Service Communication**
- **REST API** using OpenFeign.
- **Service Discovery** through Eureka Server.

---

## **Setup and Installation**
### Prerequisites
- **Java 17+**
- **Maven**
- **MySQL/PostgreSQL** (or H2 for testing)
- **Docker** (Optional)

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/online-ticket-sales.git
   cd online-ticket-sales
   ```

2. Set up the Config Server:
   - Navigate to the `config-server` folder.
   - Run the server:
     ```bash
     mvn spring-boot:run
     ```

3. Start the Eureka Server:
   - Navigate to the `discovery-server` folder.
   - Run the server:
     ```bash
     mvn spring-boot:run
     ```

4. Start each microservice:
   - For each service (`user-management`, `event-management`, `ticket-management`), navigate to the respective folder and run:
     ```bash
     mvn spring-boot:run
     ```

5. Access the application:
   - **Eureka Dashboard**: `http://localhost:8761`
   - APIs: Swagger UI (e.g., `http://localhost:<service-port>/swagger-ui.html`).

---

## **API Endpoints**
### **User Management**
| Method | Endpoint          | Description           |
|--------|-------------------|-----------------------|
| POST   | `/users`          | Create a new user.    |
| GET    | `/users/{id}`     | Fetch user by ID.     |
| GET    | `/users`          | List all users.       |

### **Event Management**
| Method | Endpoint              | Description                |
|--------|-----------------------|----------------------------|
| POST   | `/events`             | Create a new event.        |
| GET    | `/events/{id}`        | Fetch event by ID.         |
| GET    | `/categories`         | List all categories.       |

### **Ticket Management**
| Method | Endpoint                          | Description                         |
|--------|-----------------------------------|-------------------------------------|
| POST   | `/tickets`                        | Add a ticket for an event.          |
| GET    | `/tickets`                        | List all tickets.                   |
| GET    | `/revenues`                       | Calculate event revenue.            |
| GET    | `/users/most-active`              | Identify the most active user.      |

---

## **Future Improvements**
- Implement Docker and Kubernetes for containerization and orchestration.
- Add RabbitMQ/Kafka for event-driven communication.
- Use Prometheus and Grafana for monitoring.
- Implement security with OAuth2/Keycloak.
