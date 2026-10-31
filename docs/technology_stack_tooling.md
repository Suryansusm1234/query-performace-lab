# Technology Stack & Environment Configuration

## 1. Primary Technology Stack

| Category | Technology | Version / Specification | Justification |
| :--- | :--- | :--- | :--- |
| **Language** | Java | 21 (LTS) | Modern Java runtime with Virtual Threads capability and performance enhancements. |
| **Framework** | Spring Boot | 3.x (Latest stable compatible with Java 21) | Robust ecosystem for REST services, transaction management, and actuator metrics. |
| **Build Tool** | Gradel | - | Standardized dependency management and build lifecycle. |
| **Web Layer** | Spring Web | Built-in | Servlet-based web environment for REST endpoints. |
| **ORM / Data Access** | Spring Data JPA / Hibernate | Included in Boot starter | Provides Object-Relational Mapping with native query capabilities. |
| **Database** | PostgreSQL | 15+ / 16+ | Advanced RDBMS featuring detailed buffer execution profiling and rich indexing options. |
| **Schema Migration** | Flyway | Flyway Core | Versioned, declarative database schema management. |
| **Validation** | Jakarta Bean Validation | Included in Boot starter | Declarative validation of HTTP inputs prior to persistence. |
| **Monitoring** | Spring Boot Actuator | Included in Boot starter | Exposes application operational metrics and connection pool stats. |

## 2. Testing & Performance Tooling

* **JUnit 5 , Mockito and MockMvc:** Unit and integration testing of business constraints.
* **Testcontainers:** Containerized PostgreSQL instances for reproducible, isolated integration testing.
* **k6:** Open-source load testing tool for scripting HTTP request workloads, throughput measuring, and latency tracking ($p_{50}, p_{90}, p_{95}, p_{99}$).
* **PostgreSQL Engine Utilities:** `EXPLAIN`, `EXPLAIN (ANALYZE, BUFFERS)` for internal execution plan evaluation, buffer hit/read ratios, and node cost estimation.


## 3. Environment & Application Configuration

The baseline configuration is declared in `src/main/resources/application.properties`. 

```properties
# Application Identifier
spring.application.name=query-performance-lab

# Database Connection (Supplied via Environment Variables)
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/query_performance_lab}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
spring.datasource.driver-class-name=org.postgresql.Driver

# Schema Validation (Hibernate MUST NOT alter database structure)
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Flyway Configuration
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

```

### Security & Operational Guidelines
* Database credentials must never be hardcoded or committed to version control. They are injected via `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`.
* The dataset generation process **must never** execute automatically upon application startup.
* Hibernate strategy `ddl-auto` is strictly set to `validate` to guarantee Flyway migration authority.