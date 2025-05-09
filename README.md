# 🛒 Order Service - E-Commerce Microservices System

The **Order Service** is a core component of the E-Commerce system built using **Spring Boot Microservices** architecture. It handles order placement, product validation, payment coordination, and order confirmation using **asynchronous communication** via **Kafka** and implements the **Saga pattern** for transaction consistency.

---

## 🧩 Architecture Overview

- 🔗 Receives order requests from API Gateway (`/orders`)
- 📦 Communicates with the **Product Service** to validate product availability
- 💳 Sends payment request asynchronously to the **Payment Service**
- ✅ Upon successful payment, sends order confirmation to **Notification Service**
- 🔁 Uses **Kafka** as message broker and **Saga Pattern** to ensure consistency
- 🛰️ Integrated with Eureka, Config Server, Kafka, and Zipkin for observability and configuration

---

## ⚙️ Technologies Used

- Java 17, Spring Boot 3
- Spring Web, Spring Data JPA
- Kafka (Apache Kafka for event-driven messaging)
- PostgreSQL or MongoDB
- Eureka Discovery Client
- Spring Cloud Config
- Zipkin for distributed tracing
- Docker (optional for containerization)

---

## 📚 Core Features

| Feature                 | Description                                                              |
|------------------------|--------------------------------------------------------------------------|
| Place Order            | Accepts new orders through the API Gateway                              |
| Check Product Stock    | Calls the Product Service to check product availability                 |
| Initiate Payment       | Sends Kafka event to Payment Service asynchronously                     |
| Confirm Order          | After payment, publishes confirmation to Notification Service via Kafka |
| Order Persistence      | Saves order details to its own database                                 |
| Saga Pattern           | Ensures consistency across services in a distributed transaction         |

---

## 🔌 REST API Endpoint
.....
