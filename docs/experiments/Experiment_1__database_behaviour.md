# Aim of the experiment
To understand the initial behaviour of a database and change in the execution time

> *Note: All the timings are database time not the time for the complete request response cycle*


**Test query**

```sql
select c1_0.id,c1_0.email,c1_0.name,c1_0.registered_at from customer c1_0 where c1_0.id=?
```

| Run.no | Execution time(ms) | Planning time(ms) | Shared hit | Shared Read | Scan type |
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


| Measurement    | Cold/initial run | Warmed-up runs    |
| -------------- | ---------------- | ----------------- |
| Execution time | 1.347 ms         | Median: 0.0355 ms |
| Shared hit     | 3                | 3                 |
| Shared run     | 3                | 0                 |
| Scan Type      | index            | index             |

## Conclusion 
- The PostgreSQL database creates index for any field which is unique by it's own.
- The initial run usually takes a more time as compared to the runs afterwards.
- Once a page is in shared hit the execution speed improves over time.