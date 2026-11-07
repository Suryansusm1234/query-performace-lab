**Title & Date:** Use Java, Spring Boot and PostgreSQL for the application  
**Date:** 2026-11-07  
**Status:** Accepted

## Context

The project required a backend framework and relational database capable of supporting API development, relational data access and query-performance experiments. The stack also needed to demonstrate relevant Java backend skills.

## Options Considered

1. Java with Spring Boot and PostgreSQL.
2. Node.js with Express and PostgreSQL.
3. Java with Spring Boot and a NoSQL database.

## Decision

Choose Java with Spring Boot and PostgreSQL.

Spring Boot provides the application framework and API layer, while PostgreSQL provides relational storage and the ability to inspect query execution plans, indexes and buffer statistics.

## Consequences & Trade-offs

- Demonstrates Java backend development and relational database knowledge.
- Provides access to PostgreSQL tools such as `EXPLAIN ANALYZE`.
- Requires understanding ORM behaviour and SQL execution rather than relying entirely on the framework.
- Adds the usual complexity of maintaining an application and a separate database.