# Aim of the experiment
To measure the response time of the Spring Boot API independently of the database execution time.

## Endpoint under test

```http
GET /api/customers/{id}
```

We used `curl` to measure HTTP time-to-first-byte (TTFB) across repeated sequential requests.

## Observed result
| Metric | Result |
| ------ | ------ |
| Number of requests | 50 |
| Average TTFB | 11.41 ms |
| Median TTFB | 11.18 ms |
| p95 TTFB | 16.46 ms |
| Minimum | 6.76 ms |
| Maximum | 19.04 ms |

The first response was around 1.7 seconds, while subsequent responses were generally around 20–70 ms in the earlier observations.

## What we learned
- The first request may include startup or connection costs.
- HTTP TTFB includes application-layer processing and network time, not just SQL execution.
- Database time and API response time are not the same thing.
- A basic sequential test is useful for measuring average latency, but it does not show concurrency or capacity under load.

## Conclusion
This experiment demonstrates why API response time must be interpreted in the context of the whole request path, including the application framework, network, and database.
