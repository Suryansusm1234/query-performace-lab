**Title & Date:** Use PostgreSQL execution plans and buffer statistics to evaluate queries  
**Date:** 2026-11-07  
**Status:** Accepted

## Context

Execution time alone does not explain why a query is fast or slow. We needed to understand whether PostgreSQL used a sequential scan or index scan, how many rows were examined and how database buffer-cache behaviour affected repeated executions.

## Options Considered

1. Compare queries using execution time alone.
2. Use `EXPLAIN` to inspect the planned execution strategy.
3. Use `EXPLAIN (ANALYZE, BUFFERS)` to inspect the actual execution and buffer activity.

## Decision

Choose option 3.

Use PostgreSQL's:

```sql
EXPLAIN (ANALYZE, BUFFERS)
SELECT ...;
```

Record relevant information such as:

- Scan type and index conditions.
- Actual execution time.
- Rows returned and rows removed by filters.
- Shared buffer hits and reads.

Repeated executions are also useful for observing differences between initial and warm-cache runs.

## Consequences & Trade-offs

- Provides more diagnostic information than execution time alone.
- Helps distinguish query-plan behaviour from cache effects.
- `EXPLAIN ANALYZE` actually executes the query, so it must be used carefully for queries with side effects.
- Results can vary with cache state, concurrent activity and database conditions.
- Measurements should be repeated and interpreted in context rather than treated as universal benchmarks.