# Architecture Overview

## 1. System Vision & Objective
`Query Performance Lab` (`query-performance-lab`) is a backend engineering project designed to investigate, measure, and optimize database query performance through reproducible experiments using PostgreSQL and a Spring Boot application.

Rather than acting as a standard CRUD application, this system serves as an evidence-based performance laboratory simulating a financial transaction platform (customers, accounts, and transactions). The primary goal is to demonstrate practical mastery of:
- SQL query execution and optimization.
- Database indexing and composite index strategies.
- Query planning using `EXPLAIN` and `EXPLAIN (ANALYZE, BUFFERS)`.
- PostgreSQL buffer activity and execution timing.
- Pagination strategies (Offset vs. Keyset/Cursor).
- Aggregation and analytical query optimization.
- Database schema constraints and transaction management under concurrency.
- End-to-end HTTP API performance under load.

## 2. High-Level Architecture & Layering

The application follows a clean, layered monolithic architecture designed to separate concerns while minimizing artificial performance overhead:

```
+-------------------------------------------------------------+
|                      Clients / k6 Load                       |
+-------------------------------------------------------------+
                               |
                               v (HTTP / REST)
+-------------------------------------------------------------+
|                     REST API Controllers                    |
|                (Spring Web / Bean Validation)               |
+-------------------------------------------------------------+
                               |
                               v
+-------------------------------------------------------------+
|                        Service Layer                        |
|             (Business Logic / Transaction Bounds)           |
+-------------------------------------------------------------+
                               |
                               v
+-------------------------------------------------------------+
|                     Persistence Layer                       |
|           (Spring Data JPA / Hibernate / Direct SQL)        |
+-------------------------------------------------------------+
                               |
                               v (JDBC Driver)
+-------------------------------------------------------------+
|                      PostgreSQL Database                    |
|             (Flyway Migrations / Query Engine)              |
+-------------------------------------------------------------+
```

### Layer Responsibilities
1. **REST Controller Layer:** Handles HTTP request parsing, DTO binding, request validation (`jakarta.validation`), and HTTP response mapping. Exposes performance test vectors.
2. **Service Layer:** Encapsulates transaction boundaries (`@Transactional`), business rules, and analytical aggregate logic.
3. **Persistence Layer:** Uses Spring Data JPA and Hibernate for mapping baseline entity operations, while allowing native queries and projection hooks for execution plan analyses.
4. **Database (PostgreSQL):** Authoritative source of data integrity, foreign key constraints, indexes, check constraints, and physical page/buffer management.

## 3. System Boundaries & Non-Goals

To maintain a laser focus on core database performance and system correctness, explicit architectural boundaries are enforced:

### In Scope
- Monolithic Spring Boot application backed by a single PostgreSQL database.
- Database migrations owned strictly by Flyway.
- Offline SQL-based seed generation for bulk datasets.
- Detailed performance profiling via PostgreSQL plan analysis and k6 load testing.

### Explicitly Out of Scope (Initial Phase)
- **No Microservices:** Avoid distributed network overhead during baseline query investigations.
- **No Caching Layers (Redis/Memcached):** Database performance must be measured directly against the disk/buffer cache without application-level or distributed caching hiding bottlenecks.
- **No Message Brokers (Kafka/RabbitMQ):** Asynchronous transaction queues introduce non-deterministic timing variables during early query analysis.
- **No Complex Orchestration (Kubernetes/Service Meshes):** Local Docker Compose or direct host environments provide predictable CPU/RAM allocation for baseline testing.

## 4. Definition of Success Criteria

The architectural setup is deemed successful when:
1. Realistic benchmark datasets can be generated reproducibly via bulk SQL.
2. Database execution plans (`EXPLAIN (ANALYZE, BUFFERS)`) accurately reflect index usage and page reads.
3. Index strategies and pagination techniques yield measurable, documentable performance variations under equal conditions.
4. Concurrency testing proves database consistency and correctness (e.g., preventing lost updates or corrupted account balances).
5. All optimization claims are backed by structured, reproducible evidence.