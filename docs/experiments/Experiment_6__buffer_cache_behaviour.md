# Aim of the experiment
To understand why buffer statistics such as `shared hit` and `shared read` change between repeated executions of the same query.

## Test query
One customer primary-key lookup was tested using `EXPLAIN (ANALYZE, BUFFERS)`.

```sql
SELECT c1_0.id, c1_0.email, c1_0.name, c1_0.registered_at
FROM customer c1_0
WHERE c1_0.id = ?;
```

## Observed result

```text
Index Scan using customer_pkey on customer
Index Cond: (id = 15)
Buffers: shared hit=3 read=3
Planning Time: 5.488 ms
Execution Time: 1.347 ms
```

On later executions, the query stabilized around 0.033–0.037 ms, and the median of runs 3–10 was about 0.0355 ms.

## What we learned
- `shared hit` means PostgreSQL found the needed page in the shared buffer cache.
- `shared read` means PostgreSQL had to read pages into shared buffers.
- A shared read does not always mean a physical disk read; the OS page cache may satisfy the request.
- The first run can be slower than later warmed-cache executions.

## Conclusion
Repeated database timings are not identical because the database cache state changes between runs. Understanding buffer statistics is important when interpreting query timings and benchmarking.
