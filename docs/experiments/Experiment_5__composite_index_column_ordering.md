# Aim of the experiment
To investigate how the order of columns in a composite index affects performance when a query mixes equality and range conditions.

## Test index definitions

```sql
CREATE INDEX idx_id_email
ON appuser(id, email);
```

```sql
CREATE INDEX idx_email_id
ON appuser(email, id);
```

## Query pattern
The query included:
- an ID range condition (`id < 25000` or `id >= 25000`)
- an exact email match

## Observed result

| Case | Index | Observed execution time |
| ---- | ----- | ---------------------- |
| `id < 25000` | `(id, email)` | ~1.608 ms |
| `id < 25000` | `(email, id)` | ~0.048 ms |
| `id >= 25000` | `(id, email)` | ~5.216 ms |
| `id >= 25000` | `(email, id)` | ~0.048 ms |

## What we learned
- With `(email, id)`, PostgreSQL can use the equality condition on `email` as the leading index condition, narrowing the search effectively.
- With `(id, email)`, the leading range condition on `id` can prevent the second column from being useful for narrowing the index scan.
- Composite index order matters because it should match the actual predicates and access patterns used by real queries.

## Conclusion
This experiment shows that index design must consider query shape, not just the presence of an index. A well-ordered composite index can dramatically improve performance for mixed equality and range predicates.
