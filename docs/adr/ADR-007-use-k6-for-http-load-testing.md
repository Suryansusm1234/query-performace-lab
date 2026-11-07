**Title & Date:** Use k6 to measure API performance under concurrent workloads  
**Date:** 2026-11-07  
**Status:** Accepted

## Context

A successful API request in a sequential test does not demonstrate how an application behaves when multiple clients send requests concurrently.

The project needed a repeatable way to measure throughput, response latency and HTTP check results under different workloads.

## Options Considered

1. Use sequential requests with curl only.
2. Use k6 to simulate concurrent virtual users.
3. Rely on infrastructure CPU metrics without generating controlled HTTP workloads.

## Decision

Choose k6 for concurrent HTTP load testing, while retaining curl for basic response-time measurements.

The tests explored:

- Staged virtual-user increases from 5 to 100.
- 100 virtual users with a 100 ms think time.
- 100 virtual users without think time.
- Random customer-ID lookups with 20 virtual users.

Record throughput, average latency, median, p90, p95, maximum latency and check results.

## Consequences & Trade-offs

- Makes concurrent API behaviour measurable.
- Allows comparison of different request patterns and pacing strategies.
- Results depend on the virtual-user count, think time, endpoint, data distribution and test infrastructure.
- The load generator itself can become a limiting factor.
- Passing checks does not necessarily mean every request returned the same business outcome, particularly when the test accepts both HTTP 200 and 404.
- Throughput from different configurations must not be compared without accounting for workload differences.