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

- **Monitoring & Resilience**:
  - **Resilience4j** for fault tolerance (Retry, Circuit Breaker).
  - **Prometheus & Grafana** for real-time monitoring of microservices.
  - **Micrometer** integration for exporting metrics.

---

## **Microservices Overview**
The platform is divided into six microservices:

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

### 6. **Monitoring**:
- **Prometheus**: Scrapes metrics exposed by the microservices.
- **Grafana**: Visualizes Prometheus metrics with rich dashboards.

---

## **Technologies Used**
- **Spring Boot**: Core framework for building microservices.
- **Spring Cloud**:
  - Eureka (Service Discovery)
  - Config Server (Centralized Configuration)
  - OpenFeign (Inter-service communication)
- **Resilience4j**: Fault tolerance (Retry, Circuit Breaker).
- **Micrometer**: Metric instrumentation.
- **Prometheus**: Metrics collection and alerting.
- **Grafana**: Visual monitoring dashboards.
- **Spring Data JPA**: ORM and database interaction.
- **MySQL**: Database layers for services.
- **Docker**: Containerization.

---

## **Setup and Installation**
### Prerequisites
- **Java 17+**
- **Maven**
- **Docker**
- **Docker Compose** (Optional for running Prometheus and Grafana).

---

## **Monitoring Setup**
### **Prometheus**
1. Create a `prometheus.yml` file with the following content:
   ```yaml
   global:
     scrape_interval: 15s

   scrape_configs:
     - job_name: 'ticket-service'
       metrics_path: '/actuator/prometheus'
       static_configs:
         - targets: ['host.docker.internal:8083']

     - job_name: 'event-service'
       metrics_path: '/actuator/prometheus'
       static_configs:
         - targets: ['host.docker.internal:8082']

     - job_name: 'user-service'
       metrics_path: '/actuator/prometheus'
       static_configs:
         - targets: ['host.docker.internal:8081']
   ```

2. Run Prometheus with Docker:
   ```bash
   docker run -d \
     -p 9090:9090 \
     -v /path/to/prometheus.yml:/etc/prometheus/prometheus.yml \
     --name prometheus prom/prometheus
   ```

3. Access Prometheus Dashboard:
   - URL: [http://localhost:9090](http://localhost:9090)

---

### **Grafana**
1. Run Grafana with Docker:
   ```bash
   docker run -d \
     -p 3000:3000 \
     --name grafana grafana/grafana
   ```

2. Access Grafana Dashboard:
   - URL: [http://localhost:3000](http://localhost:3000)
   - Default Username: `admin`
   - Default Password: `admin`

3. Add Prometheus as a Data Source in Grafana:
   - Navigate to **Configuration** > **Data Sources**.
   - Add a new Prometheus data source with URL: `http://host.docker.internal:9090` (or `http://localhost:9090` for non-Docker setups).

4. Import Dashboards:
   - Use dashboard templates like:
     - **Micrometer Dashboard** (ID: `4701`).
     - **Spring Boot Actuator Dashboard** (ID: `6756`).

---

## **API Endpoints**
### **User Management**
| Method | Endpoint                        | Description                     |
|--------|---------------------------------|---------------------------------|
| POST   | `http://localhost:8081/api/users`         | Create a new user.             |
| GET    | `http://localhost:8081/api/users/{id}`    | Fetch user by ID.              |
| GET    | `http://localhost:8081/api/users`         | List all users.                |

### **Event Management**
| Method | Endpoint                        | Description                     |
|--------|---------------------------------|---------------------------------|
| POST   | `http://localhost:8082/api/events`        | Create a new event.            |
| GET    | `http://localhost:8082/api/events/{id}`   | Fetch event by ID.             |
| GET    | `http://localhost:8082/api/categories`    | List all categories.           |

### **Ticket Management**
| Method | Endpoint                        | Description                     |
|--------|---------------------------------|---------------------------------|
| POST   | `http://localhost:8083/api/tickets`       | Add a ticket for an event.     |
| GET    | `http://localhost:8083/api/tickets`       | List all tickets.              |
| GET    | `http://localhost:8083/api/revenues`      | Calculate event revenue.       |
| GET    | `http://localhost:8083/api/users/most-active` | Identify the most active user. |

---

## **Future Improvements**
- Add alerting rules in Prometheus for SLA breaches.
- Implement distributed tracing with **Zipkin** or **Jaeger**.
- Introduce **Kafka** for asynchronous communication.
