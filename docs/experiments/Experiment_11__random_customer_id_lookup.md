# Aim of the experiment
To test customer lookups using different IDs instead of repeatedly requesting the same customer.

## Test configuration
- 20 virtual users
- No sleep between requests
- Random IDs generated between 1 and 10,000
- Check accepted either HTTP 200 or 404

## Observed result
| Metric | Result |
| ------ | ------ |
| Total requests | 455,665 |
| Successful checks | 100% |
| Throughput | 3,797.19 req/s |
| Average HTTP duration | 2.33 ms |
| Minimum | 0.617 ms |
| Median | 1.87 ms |
| p90 | 3.94 ms |
| p95 | 4.94 ms |
| Maximum | 153.63 ms |
| Maximum virtual users | 20 |

## What we learned
- Random lookups exercise different database pages and cache behaviour.
- Repeated requests to one ID behave differently from requests across a broad ID range.
- A 100% pass result here means the API responded with either 200 or 404 as allowed by the check, not necessarily that every request matched an existing customer.

## Conclusion
This experiment highlights that workload pattern matters. Randomized access is useful for testing cache behaviour, request variability, and general system responsiveness under mixed lookup conditions.
