# 🏦 Mini Banking System (Event-Driven)

## 📌 Overview

This project is a backend-focused **mini banking system** designed to simulate real-world financial workflows using modern backend architecture.

The goal is to evolve from a **monolithic application** into an **event-driven microservices system** using Kafka.

---

## 🎯 Objectives

* Build a production-style backend system using:

    * Java + Spring Boot
    * SQL database (PostgreSQL recommended)
    * Kafka (event-driven architecture)
* Understand:

    * Distributed systems fundamentals
    * Event-driven communication
    * Data consistency challenges
    * Failure handling & idempotency
* Apply real-world design patterns and best practices

---

## 🧱 System Architecture (Target State)

### Services:

* **Account Service**
* **Transaction Service**
* **Notification Service**

### Communication:

* REST (initial phase)
* Kafka events (main communication)

### Data:

* Each service owns its own database

---

## 🗺️ Development Phases

---

## ✅ Phase 1 — Monolithic Application

### Description

Build a single Spring Boot application with layered architecture.

### Functional Requirements

#### Account Management

* Create account
* Get account details
* List all accounts

#### Transactions

* Transfer money between accounts
* View transaction history

---

### API Endpoints

#### Account

* `POST /accounts`
* `GET /accounts/{id}`
* `GET /accounts`

#### Transaction

* `POST /transactions/transfer`
* `GET /transactions/{id}`
* `GET /transactions`

---

### Data Model

#### Account

* `id` (Long)
* `ownerName` (String)
* `balance` (Decimal)

#### Transaction

* `id` (Long)
* `fromAccountId` (Long)
* `toAccountId` (Long)
* `amount` (Decimal)
* `status` (PENDING, COMPLETED, FAILED)
* `createdAt` (Timestamp)

---

### Business Rules

* Cannot transfer negative amount
* Cannot transfer to same account
* Must validate account existence
* Must validate sufficient balance
* All operations must be transactional

---

## ⚙️ Phase 2 — Internal Event-Driven Design

### Description

Introduce event-driven thinking using Spring events.

### Requirements

* Create event:

    * `TransactionCreatedEvent`
* Publish event after transaction creation
* Add listener(s):

    * Logging
    * Simulated notification

---

## 🚀 Phase 3 — Kafka Integration

### Description

Replace internal events with Kafka-based communication.

### Requirements

#### Kafka Setup

* Kafka broker (Docker)
* Topic: `transactions`

#### Producer

* Publish `TransactionCreatedEvent`

#### Consumer

* Consume events
* Log or process them

---

## 🔀 Phase 4 — Service Responsibility Separation

### Description

Decouple responsibilities via events.

### Changes

#### Transaction Service

* Creates transaction
* Publishes event

#### Account Service

* Consumes event
* Updates balances

---

### Key Concept

➡️ Eventual consistency replaces DB transactions across services

---

## ⚠️ Phase 5 — Reliability & Failure Handling

### Requirements

#### Idempotency

* Prevent duplicate processing of same transaction

#### Retry Mechanism

* Retry failed message processing

#### Dead Letter Queue (DLQ)

* Store failed messages

---

## 📦 Phase 6 — Microservices Split

### Description

Split monolith into independent services.

### Services

#### account-service

* Manages accounts
* Updates balances via Kafka events

#### transaction-service

* Handles transfers
* Emits events

#### notification-service

* Sends notifications (log/email simulation)

---

### Requirements

* Separate databases per service
* Communication via Kafka only
* No direct DB sharing

---

## 🔍 Phase 7 — Observability

### Requirements

* Structured logging
* Correlation ID for requests
* Log all transactions and events

---

## 🧪 Phase 8 — Testing

### Requirements

* Unit tests for services
* Integration tests using Testcontainers
* Kafka integration tests

---

## 🧰 Tech Stack

* Java 17+
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Apache Kafka
* Docker
* Testcontainers

---

## 📈 Non-Functional Requirements

* Scalability (horizontal scaling ready)
* Fault tolerance
* Data consistency (eventual consistency)
* Maintainability (clean architecture)

---

## 🚫 Out of Scope (for now)

* UI / Frontend
* Authentication / Authorization
* Real payment integrations
* External banking APIs

---

## 💡 Bonus (Optional Enhancements)

* Add Redis caching
* Implement Saga pattern
* Add API Gateway
* Add rate limiting
* Add OpenTelemetry tracing

---

## 🏁 Success Criteria

* System supports money transfer using events
* Services communicate via Kafka
* System handles duplicate and failed messages
* Clean, maintainable architecture

---

## 📚 Learning Outcomes

By completing this project, you should understand:

* Event-driven architecture
* Kafka fundamentals
* Microservices design
* Distributed system challenges
* Backend system reliability patterns

---

## 🛠 How to Run (To Be Filled Later)

```bash
# TODO
```

---

## 📌 Notes

* Start simple, then iterate
* Do NOT jump to microservices too early
* Focus on correctness before optimization
* Simulate failures intentionally

---
