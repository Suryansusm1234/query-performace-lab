# Aim of the experiment
To observe application behaviour when virtual users continuously send requests without an intentional pause.

## Test configuration
- 100 virtual users
- 2-minute test
- No `sleep()` between requests

## Observed result
| Metric | Result |
| ------ | ------ |
| Total requests | 532,808 |
| Successful checks | 100% |
| Throughput | 4,439.54 req/s |
| Average HTTP duration | 22.42 ms |
| Median | 19.81 ms |
| p90 | 54.64 ms |
| p95 | 62.61 ms |
| Maximum | 483.43 ms |
| Maximum application CPU | 98% |

## What we learned
- Removing the think time substantially increased throughput.
- The application CPU reached 98%, indicating strong CPU pressure on the app host.
- Average latency and p95 latency rose compared with the paced test.
- This suggests the application tier was under greater stress, although CPU usage alone does not prove the exact bottleneck.

## Conclusion
This experiment shows how pressure changes when the system is asked to handle a sustained, no-think-time load. It helps demonstrate the relationship between concurrency, latency, and resource saturation.
