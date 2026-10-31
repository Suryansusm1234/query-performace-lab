# Guidelines for AI Agent

## 1. Core Engineering Principles
* **Understand Before Changing:** Always inspect existing code, configurations, schemas, and tests before modifying them. Never assume a file or component is present or absent without verifying.
* **Measure Before Optimizing:** Never add indexes, caching, or performance mechanisms speculatively. Establish a baseline, identify bottlenecks through data/plans, and apply optimizations only with defensible justification.
* **Correctness Before Performance:** Performance gains must never compromise data integrity, concurrency correctness, or application behavior.
* **Prefer Simplicity:** Use the simplest solution that meets current requirements. Avoid premature abstraction, unnecessary dependencies, or unrequested architectural complexity.
* **Preserve Existing Work:** Make small, reviewable, and independent changes. Never overwrite working code or make unrelated modifications.
* **Honesty with Evidence:** Never fabricate execution plans, benchmark metrics, test outcomes, or implementation statuses. Clearly distinguish between proposed, implemented, tested, and measured facts.

## 2. Safety & Destructive Operations
* **Explicit Authorization Required:** Never execute destructive actions (such as dropping databases, truncating tables, deleting user data, resetting migration history, or force-resetting Git state) without explicit user authorization.
* **Secret Protection:** Never hardcode or commit database credentials, API keys, access tokens, or sensitive configurations. Do not expose secrets in logs, terminal outputs, or documentation.
* **Non-Automatic Seed Execution:** Data generators or bulk seed scripts must never run automatically upon application startup. They require explicit, deliberate invocation.

## 3. Database Schema & Migration Rules
* **Immutability of Applied Migrations:** Never modify an already-applied schema migration file. Implement subsequent database changes via new versioned migrations.
* **Schema vs. Data Separation:** Keep schema migrations strictly separate from bulk benchmark or seed data generation scripts.
* **Strict Schema Validation:** Configure ORMs to validate entity mappings against the existing database schema rather than automatically generating or destructive-modifying tables (`ddl-auto=validate` or equivalent).
* **Referential Integrity:** Enforce critical data invariants through database constraints (foreign keys, unique constraints, check constraints, `NOT NULL`) rather than relying solely on application-layer validation.

## 4. Benchmarking & Performance Methodology
* **Evidence-Based Claims:** Every performance optimization must be backed by reproducible measurements (e.g., execution plans, timing metrics, buffer activity, or load test results).
* **Fair Comparisons:** When comparing performance strategies, keep datasets, configurations, and test environments constant.
* **Isolate Latency Layers:** Distinguish low-level database execution time from end-to-end client response latency.

## 5. Documentation & Artifacts
* **Record Significant Decisions:** Create Architecture Decision Records (ADRs) for major architectural, data integrity, or performance strategy decisions.
* **Keep Documentation Synchronized:** Update documentation whenever an implementation materially changes behavior. Do not claim a feature is verified unless supported by repository code or test evidence.

## 6. AI-Assisted Development Workflow
For each significant task, follow this systematic process:
1. **Understand:** Restate the goal and inspect relevant implementation files.
2. **Plan:** Outline the intended approach and files to modify. Discuss trade-offs for architectural or schema changes.
3. **Implement:** Write clean, minimal changes achieving the goal without unrelated refactoring.
4. **Verify:** Run applicable tests, checks, or analysis tools and inspect the diff.
5. **Report:** Summarize what changed, files affected, verification results, and any remaining risks or follow-up tasks.