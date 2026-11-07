# Aim of the experiment
To measure the cost of searching by a non-indexed column and compare it with the cost of an indexed lookup.

## Test query

```sql
SELECT * FROM appuser
WHERE email = 'user25000@example.com';
```

## Observed result
- Execution time: approximately 20.751 ms
- Execution plan: Sequential Scan
- Rows removed by filter: 49,999

## What we learned
Without a suitable index, PostgreSQL had to inspect the table rows to find the matching email. This is a classic example of a full-table scan on a non-indexed filter.

## Conclusion
- Searching by a non-indexed column can be much slower than an indexed lookup.
- The exact runtime depends on table size, hardware, cache state, and query shape.
- This experiment shows why filtering on a column that is not indexed is expensive for a large dataset.
