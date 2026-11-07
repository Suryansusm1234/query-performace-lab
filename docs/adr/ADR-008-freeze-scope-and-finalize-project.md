**Title & Date:** Freeze feature development and finalize the project documentation  
**Date:** 2026-11-07  
**Status:** Accepted

## Context

The project had already covered PostgreSQL indexing, query execution plans, API response-time measurements, concurrent load testing and AWS resource observation.

Further development could lead to adding endpoints, increasing virtual users and introducing more infrastructure tooling without a clearly defined engineering question.

The project also needed to be completed and presented as a portfolio project rather than remain an open-ended experiment.

## Options Considered

1. Continue adding endpoints, infrastructure and increasingly large load tests.
2. Expand into advanced monitoring and observability before finalizing.
3. Stop unnecessary feature expansion, document the completed experiments and prepare the repository for applications.

## Decision

Choose option 3.

Freeze the current project scope and focus on:

- Completing the README and experiment documentation.
- Recording important engineering decisions through ADRs.
- Documenting actual test configurations and measured results.
- Explaining limitations and avoiding unsupported performance claims.
- Checking the repository for credentials and other sensitive configuration.
- Reviewing AWS resources and stopping resources that are no longer required to avoid unnecessary costs.

Further work should be driven by a specific problem or a genuine requirement, not by a desire to increase the project's feature count or request-per-second figures.

## Consequences & Trade-offs

- Keeps the project manageable and ready for portfolio presentation.
- Frees time for job applications and interview preparation.
- Leaves some potential improvements and deeper investigations unfinished.
- Prioritises documented engineering understanding over additional infrastructure complexity.
- The project should be presented as a performance investigation and learning project, not as a production-scale system.