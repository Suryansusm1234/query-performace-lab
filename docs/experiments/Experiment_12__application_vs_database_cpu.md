# Aim of the experiment
To compare CPU pressure on the application server and the database server during the 100-VU no-think-time test.

## Observed maximum CPU utilisation
| Component | Maximum CPU |
| --------- | ----------- |
| Application EC2 | 98% |
| Amazon RDS | 27.6% |

The values are maximums observed during the test, not averages.

## What we learned
- The application host was substantially more CPU-pressured than the database server in this test.
- This made the application tier the most likely place to investigate first.
- However, CPU alone does not identify the exact limiting component.
- More evidence would be needed, such as JVM GC, Tomcat thread usage, HikariCP pool health, generator CPU, and RDS memory and connection metrics.

## Conclusion
This experiment shows how useful it is to compare application and database resource utilisation instead of focusing only on request throughput. It supports a more balanced performance investigation.
