# Aim of the experiment
To compare the same email lookup before and after creating an index on the email column.

## SQL used

```sql
CREATE INDEX appuser_email_idx
ON appuser(email);
```

Then:

```sql
SELECT * FROM appuser
WHERE email = 'user25000@example.com';
```

## Observed result
| Measurement | Result |
| ----------- | ------ |
| Before index | approximately 20.751 ms |
| After index | approximately 0.047 ms |
| Plan after index | Index Scan |

## What we learned
A suitable index dramatically reduced the observed lookup time for this dataset. This is a clear example of the benefit of an index for selective equality searches.

## Conclusion
- Indexed equality lookup on a unique-ish or selective field can be substantially faster than a sequential scan.
- The improvement is most visible when the filter is highly selective and the index allows the database to narrow the search efficiently.
- This demonstrates the practical value of indexing for common lookup queries.
