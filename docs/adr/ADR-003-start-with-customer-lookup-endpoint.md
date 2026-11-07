**Title & Date:** Use customer lookup as the initial API performance workload  
**Date:** 2026-11-07  
**Status:** Accepted

## Context

The project needed an initial endpoint to connect the Spring Boot application to PostgreSQL and establish a measurable workload. Waiting until every proposed endpoint was implemented would delay the first experiments without necessarily improving their value.

## Options Considered

1. Complete all planned endpoints before starting performance experiments.
2. Start with a simple customer lookup endpoint and expand only if an experiment requires it.
3. Begin with a complex transaction or analytics endpoint.

## Decision

Choose option 2.

Start with:

`GET /api/customers/{id}`

This provides a straightforward primary-key lookup that can be used to investigate database access, API response time and concurrent request behaviour.

## Consequences & Trade-offs

- Allows experiments to begin early.
- Keeps the initial workload easy to understand.
- Does not represent the performance characteristics of every possible API.
- More complex endpoints would require separate experiments if they became necessary.