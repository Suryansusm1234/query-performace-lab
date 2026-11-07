# Aim of the experiment
To observe how the application behaves as the number of concurrent virtual users increases in stages.

## Test configuration
- 5, 20, 50, and 100 virtual users were used in stages
- `sleep(0.1)` was inserted between iterations

## Observed result
| Metric | Result |
| ------ | ------ |
| Total requests | 170,319 |
| Successful checks | 100% |
| Average throughput | ~354.8 req/s |
| Average HTTP duration | 3.42 ms |
| Median HTTP duration | 2.05 ms |
| p90 | 3.99 ms |
| p95 | 6.07 ms |
| Maximum HTTP duration | 686.61 ms |
| Maximum application CPU | 21% |

## What we learned
- The application handled the staged workload successfully.
- The average throughput includes ramp-up and cooldown periods, so it should not be mistaken for steady-state throughput at 100 VUs.
- Latency percentiles and maximum values matter, because the median remains low while rare spikes can be much higher.

## Conclusion
This staged load test shows the application remains stable under increasing concurrent load, but the result should be viewed as an aggregate sample, not a steady-state capacity benchmark.
