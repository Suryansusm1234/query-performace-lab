**Title & Date:** Deploy the application and database on separate AWS services  
**Date:** 2026-11-07  
**Status:** Accepted

## Context

The project needed a remote environment in which the Spring Boot application could receive requests while PostgreSQL handled database operations. Separating the application and database also made it possible to observe their resource utilisation independently.

## Options Considered

1. Run the application and PostgreSQL on the same EC2 instance.
2. Run both on separate EC2 instances.
3. Run the application on EC2 and use Amazon RDS for PostgreSQL.

## Decision

Choose option 3.

The application was deployed on an EC2 instance, while PostgreSQL was hosted on Amazon RDS. A separate EC2 instance was used as the k6 load generator.

## Consequences & Trade-offs

- Separates application compute from database hosting.
- Makes application and database CPU utilisation independently observable.
- Provides a more realistic multi-component deployment arrangement than running everything on one machine.
- Introduces network communication between services.
- Adds infrastructure configuration, security-group management and AWS costs.
- Requires careful control of database and application network access.

**Note:** The deployment used the recorded EC2 and RDS instance types documented in the project notes. Exact instance specifications should be verified against the AWS console before publication.