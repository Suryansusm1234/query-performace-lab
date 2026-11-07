# Aim of the experiment
To understand how a database responds when it performs a lookup using the table's primary key, and how the first call differs from later warmed-cache calls.

> *Note: All timings are database time, not the full HTTP response time.*

## Test query

```sql
select c1_0.id,c1_0.email,c1_0.name,c1_0.registered_at from customer c1_0 where c1_0.id=?
```

| Run.no | Execution time (ms) | Planning time (ms) | Shared hit | Shared read | Scan type |
| ------ | ------------------ | ----------------- | ---------- | ----------- | --------- |
| 1      | 1.347              | 5.488             | 3          | 3           | index     |
| 2      | 0.552              | 0.781             | 3          | 0           | index     |
| 3      | 0.035              | 0.069             | 3          | 0           | index     |
| 4      | 0.034              | 0.071             | 3          | 0           | index     |
| 5      | 0.036              | 0.081             | 3          | 0           | index     |
| 6      | 0.037              | 0.072             | 3          | 0           | index     |
| 7      | 0.037              | 0.074             | 3          | 0           | index     |
| 8      | 0.035              | 0.071             | 3          | 0           | index     |
| 9      | 0.034              | 0.068             | 3          | 0           | index     |
| 10     | 0.033              | 0.072             | 3          | 0           | index     |

## Observation

| Measurement | Cold/initial run | Warmed-up runs |
| ----------- | ---------------- | -------------- |
| Execution time | 1.347 ms | Median: 0.0355 ms |
| Shared hit | 3 | 3 |
| Shared read | 3 | 0 |
| Scan type | index | index |

## Conclusion
- PostgreSQL can use the primary-key index to find a single row efficiently.
- The first request may be slower because the required data pages need to be read into the shared buffer cache.
- Once the relevant pages are cached, repeated lookups become much faster.
- This is a strong example of warm-cache and index-based lookup behaviour in PostgreSQL.
