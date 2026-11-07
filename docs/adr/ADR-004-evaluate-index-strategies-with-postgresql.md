**Title & Date:** Evaluate single-column and composite indexes using PostgreSQL  
**Date:** 2026-11-07  
**Status:** Accepted

## Context

We needed to understand how indexes affect query execution and how composite-index column ordering influences queries involving equality and range conditions.

A test dataset containing 50,000 `appuser` rows provided a controlled starting point.

## Options Considered

1. Rely only on primary-key indexes.
2. Add indexes to frequently queried columns without examining query plans.
3. Compare sequential scans, single-column indexes and alternative composite-index orders using measured execution plans.

## Decision

Choose option 3.

The experiments included:

- Primary-key lookup versus sequential scan.
- Email lookup before and after creating an email index.
- Composite index `(id, email)`.
- Composite index `(email, id)`.
- Queries combining email equality with an ID range.

The objective was to observe how index structure and predicate order affected execution.

## Consequences & Trade-offs

- Provides evidence for understanding index selection and column ordering.
- Helps explain why indexes should be designed around actual query patterns.
- Additional indexes consume storage and add write-maintenance overhead.
- Results from the test dataset cannot automatically be generalised to every production workload.
- The experiments do not establish a universal preferred composite-index order.