# Aim of the experiment
To measure the application under a fixed 100-VU workload with a short pause between requests.

## Test configuration
- 100 virtual users
- 2-minute test
- `sleep(0.1)` after each request

## Observed result
| Metric | Result |
| ------ | ------ |
| Total requests | 114,679 |
| Successful checks | 100% |
| Throughput | 954.907 req/s |
| Average HTTP duration | 4.18 ms |
| Median | 1.89 ms |
| p90 | 4.04 ms |
| p95 | 6.95 ms |
| Maximum | 797.13 ms |
| Maximum application CPU | 21% |

## What we learned
The 100 ms pause limits how frequently each virtual user sends requests, so this is a paced, closed-loop workload rather than a test that continuously bombards the system.

## Conclusion
This workload helps understand the effect of a moderate, paced load pattern. It is useful for measuring how the application behaves under realistic request spacing, but it does not represent maximum throughput.
