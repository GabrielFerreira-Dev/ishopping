# 📦 Event-Driven Microservices Architecture – Java

## 📖 Overview

This repository presents a **distributed system based on microservices**, developed in **Java with Spring Boot**, designed to demonstrate **backend architectural expertise** in a real-world scenario.

The system models a complete **order lifecycle**, including product and client management, order creation, payment processing, invoicing, and logistics. The architecture follows an **Event-Driven Architecture (EDA)** using **Apache Kafka**, prioritizing **loose coupling**, **scalability**, and **eventual consistency**.

This project was intentionally built to reflect **industry-grade architectural decisions** and is suitable for technical interviews focused on backend engineering and distributed systems.

---

## 🎯 Project Goals

- Demonstrate practical knowledge of **microservices architecture**
- Apply **event choreography** instead of centralized orchestration (Saga without orchestrator)
- Showcase **asynchronous communication** using Kafka
- Enforce **database-per-service** isolation
- Integrate with external systems (payment gateway simulation)
- Apply **automated testing** and **CI pipelines**

---

## 🏗️ Architecture Overview

![Architecture Diagram](docs/architecture-flow.jpg)

The system is composed of independent microservices that communicate through **domain events**. Each service reacts to events of interest, without a central orchestrator, ensuring autonomy and resilience.

---

## 🧩 Microservices Summary

| Microservice | Main Responsibility | Persistence | Communication |
|-------------|---------------------|------------|---------------|
| Clients | Client management | PostgreSQL | REST |
| Products | Product catalog | PostgreSQL | REST |
| Orders | Order lifecycle and payments | PostgreSQL | REST + Kafka |
| Invoicing | Invoice generation and storage | MinIO | Kafka + REST |
| Logistic | Shipping and tracking | — | Kafka |

Each microservice owns its data and schema, following the **Database per Service** pattern.

---

## 🔄 Order Lifecycle (High-Level Flow)

1. Order is created via Web or Mobile client
2. Payment transaction is initiated
3. External payment provider returns payment status via callback
4. Orders service publishes **Order Paid** event
5. Invoicing service consumes the event and generates invoice
6. Invoicing service publishes **Order Invoiced** event
7. Logistic service consumes the event and generates tracking code
8. Logistic service publishes **Order Shipped** event

---

## 📡 Kafka Topics

- `ishopping.orders-paid`
- `ishopping.orders-invoiced`
- `ishopping.orders-shipped`

Each topic represents a **domain event**, consumed only by services interested in that event.

---

## 📄 Event Contracts (Examples)

### Order Paid
```json
{
  "orderId": 5,
  "status": "PAID",
  "total": 50.00
}
```

### Order Invoiced
```json
{
  "orderId": 5,
  "urlInvoice": "http://localhost:9000/invoice_order_5.pdf",
  "status": "INVOICED"
}
```

### Order Shipped
```json
{
  "orderId": 5,
  "trackingCode": "AB12345678BR",
  "status": "SHIPPED"
}
```

---

## 🗄️ Order Status Lifecycle

- `PLACED`
- `PAID`
- `INVOICED`
- `PREPARING_SHIPPING`
- `SHIPPED`
- `ERROR_PAYMENT`

---

## 🏗️ Infrastructure Composition

Infrastructure is intentionally **modularized** and does not rely on a single docker-compose file.

### Infrastructure Modules

| Module | Description |
|------|-------------|
| `services/database` | PostgreSQL with isolated databases per service |
| `services/broker` | Kafka, Zookeeper and Kafka UI |
| `services/bucket` | MinIO object storage for invoices |

All infrastructure components must be started before running the microservices.

---

## ▶️ Running the Infrastructure

Each infrastructure module must be started independently:

```bash
cd services/database && docker compose up -d
cd services/broker && docker compose up -d
cd services/bucket && docker compose up -d
```

Useful URLs:
- Kafka UI: `http://localhost:8090`
- MinIO Console: `http://localhost:9001`

---

## 🧪 Testing Strategy

The project includes **unit tests** to ensure correctness of business rules and domain logic.

- Testing framework: **JUnit 5**
- Mocking: **Mockito**

Tests focus on:
- Domain services
- Validation rules
- Error scenarios

Infrastructure dependencies are mocked, ensuring fast and deterministic test execution.

---

## 🔁 Continuous Integration (CI)

A **GitHub Actions** pipeline is configured to run on every push and pull request, executing:

- Project build
- Unit tests
- Code validation

This guarantees early feedback and prevents regressions.

---

## 🚀 Future Improvements / Technical Roadmap

The following enhancements were intentionally documented as future steps:

- Dead Letter Queue (DLQ) for Kafka consumers
- Retry policies with exponential backoff
- Event versioning
- Idempotent event processing
- Distributed tracing (OpenTelemetry)
- Centralized logging
- Security hardening between services (OAuth2 / mTLS)

These improvements represent a natural evolution toward a production-grade system.

---

## 🧠 Final Notes

This project was designed to reflect **real-world backend engineering challenges**, emphasizing architectural clarity, scalability, and maintainability. It serves both as a learning platform and as a **technical portfolio artifact** for software engineering interviews.
