**Title & Date:** Define Query Performance Lab as a PostgreSQL query-performance investigation project  
**Date:** 2026-11-07  
**Status:** Accepted

## Context

The project needed a clear purpose. It could easily expand into a large backend platform with many endpoints, features, cloud services and infrastructure experiments. That would distract from its primary objective: understanding database query performance and how a Spring Boot application behaves under load.

## Options Considered

1. Build a complete business application with many features.
2. Build a backend focused on PostgreSQL query execution, indexing and performance measurement.
3. Build a general cloud infrastructure and scalability demonstration.

## Decision

Choose option 2.

The project would use a Spring Boot application and PostgreSQL to investigate query execution, indexing strategies and API performance. k6 and AWS would support performance testing and infrastructure observation.

## Consequences & Trade-offs

- Keeps the project focused on backend and database engineering.
- Makes experiments and measured evidence more important than the number of endpoints.
- Limits unrelated business features and infrastructure complexity.
- The project is an engineering investigation, not a production-ready high-throughput platform.
