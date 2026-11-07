# Query Performance Lab

A backend engineering project for measuring and optimizing database query performance with reproducible experiments. The application models a financial transaction system—customers, accounts, and transfers—so query behavior can be evaluated against realistic data and workloads.

The project focuses on evidence-based performance work: compare query plans, indexes, pagination approaches, database buffer activity, and HTTP behavior under load. It is a performance laboratory rather than a general-purpose CRUD product.

## What You Can Explore

- Primary-key and email lookups
- Transaction history queries across sender and receiver accounts
- Filtering and aggregation over transaction data
- PostgreSQL index strategies, including composite indexes
- Query plans and buffer statistics with `EXPLAIN (ANALYZE, BUFFERS)`
- Offset versus keyset/cursor pagination strategies
- Database constraints and transaction correctness under concurrent writes
- HTTP throughput and latency under staged load using k6

## Architecture

The application uses a layered monolithic design to keep measurements focused and minimize unrelated network overhead:

1. **REST controllers** parse and validate requests and map HTTP responses.
2. **Services** implement business rules and transaction boundaries.
3. **Persistence** uses Spring Data JPA/Hibernate, with room for native SQL and query-plan experiments.
4. **PostgreSQL** owns durable data, constraints, indexes, and query execution.

Flyway manages schema migrations. Bulk test data is generated directly in SQL rather than through HTTP, avoiding application and network overhead in data generation.

## Technology Stack

- Java 21
- Spring Boot 4.1.2
- Gradle
- Spring Web MVC, Spring Data JPA, Hibernate, Bean Validation, and Actuator
- PostgreSQL 15+
- Flyway
- JUnit 5 and Mockito
- k6 for HTTP load testing

## Requirements

- Java 21
- PostgreSQL 15 or newer
- Git
- Optional: k6 for load testing and Docker for building/running the container

## Getting Started

### 1. Configure PostgreSQL

Create a PostgreSQL database named `query_performance_lab`, or provide a different connection URL through environment variables. The application defaults are:

- URL: `jdbc:postgresql://localhost:5432/query_performance_lab`
- Username: `postgres`
- Password: `postgres`

Set credentials in your environment rather than committing them to the repository:

```bash
export DB_URL="jdbc:postgresql://localhost:5432/query_performance_lab"
export DB_USERNAME="postgres"
export DB_PASSWORD="your-password"
```

Flyway applies versioned schema migrations at application startup. Hibernate is configured to validate the schema rather than modify it.

### 2. Run the application

```bash
git clone https://github.com/Suryansusm1234/query-performace-lab.git
cd query-performace-lab
./gradlew bootRun
```

The REST API listens on port `8080` by default.

### 3. Run tests

```bash
./gradlew test
```

### 4. Build and run with Docker (optional)

```bash
docker build -t query-performance-lab .
docker run --rm -p 8080:8080 \
  -e DB_URL="jdbc:postgresql://host.docker.internal:5432/query_performance_lab" \
  -e DB_USERNAME="postgres" \
  -e DB_PASSWORD="your-password" \
  query-performance-lab
```

When running on Linux or in a container network, replace `host.docker.internal` with the database host reachable from the application container.

## API Overview

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `GET` | `/api/customers/{id}` | Baseline customer primary-key lookup |
| `GET` | `/api/accounts/{id}/transactions` | Account history with pagination and optional status/time filters |
| `GET` | `/api/transactions` | Filter transactions by status |
| `GET` | `/api/analytics/transactions/daily` | Aggregate transactions over a date range |
| `GET` | `/api/analytics/accounts/{id}/summary` | Aggregate incoming and outgoing activity for an account |
| `POST` | `/api/transactions` | Create a transaction and exercise write constraints |

For full parameters and request/response examples, see the [REST API specification](docs/api_specification.md).

## Data and Benchmarking

The domain consists of customers, accounts, and transfers. Database invariants include unique customer emails and account numbers, valid account relationships, positive transaction amounts, and prevention of transfers from an account to itself.

The documented dataset stages range from 100,000 transactions for initial development up to 5,000,000 for stress testing. Generate test data using the SQL seed strategy documented in [`docs/database_design_seed_strategy.md`](docs/database_design_seed_strategy.md); seeding is intentionally not performed automatically at application startup.

A k6 customer lookup load test is provided in `customer-load-test.js`. With the application running and suitable customer data available, run:

```bash
k6 run customer-load-test.js
```

Use PostgreSQL `EXPLAIN` and `EXPLAIN (ANALYZE, BUFFERS)` to inspect plans and buffer behavior. Record benchmark conditions and results so comparisons remain reproducible.

## Documentation

- [Architecture overview](docs/architecture_overview.md)
- [REST API specification](docs/api_specification.md)
- [Database design and seed strategy](docs/database_design_seed_strategy.md)
- [Domain model](docs/domain_model_specification.md)
- [Technology stack and tooling](docs/technology_stack_tooling.md)
- [Workflow roadmap and decision log](docs/workflow_decision_log.md)
- [Experiments](docs/experiments/)
- [Architecture decision records](docs/adr/)

## Scope

The initial design intentionally avoids microservices, application-level caching, message brokers, and complex orchestration. Keeping the system simple helps isolate database behavior and makes baseline performance experiments easier to reproduce.

## License

No license information is currently specified in the repository.
