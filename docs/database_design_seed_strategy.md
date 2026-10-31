# Database Design & Seed Strategy

## 1. Schema Management (Flyway)

All structural changes to the database must be managed strictly using versioned Flyway migrations located in `src/main/resources/db/migration/`.

### Migration Rules
* File Naming Convention: `V<Version>__<Description>.sql` (e.g., `V1__create_initial_schema.sql`).
* Existing migrations that have been applied to shared or production environments must never be modified directly. Always append a new migration script for schema evolution.
* Primary keys, Foreign Keys, `NOT NULL`, `CHECK`, and `UNIQUE` constraints must be established directly within migration scripts.


## 2. Dataset Growth Plan

To evaluate query scaling curves, experiments will run across multiple target dataset stages:

| Target Stage | Customers | Accounts | Transactions |
| :--- | :--- | :--- | :--- |
| **Initial Development** | 10,000 | 20,000 | 100,000 |
| **Stage 1 (First Optimization)** | 10,000 | 20,000 | 500,000 |
| **Stage 2 (Benchmark Scale)** | 50,000 | 100,000 | 1,000,000 |
| **Stage 3 (Stress Testing)** | 100,000 | 200,000 | 5,000,000 |

## 3. Bulk Seed Generation

HTTP-based row generation is prohibited due to network and application overhead. Bulk seed generation must be executed directly via SQL utility scripts located at:
`src/test/resources/db/seed/generate_test_data.sql`

### 3.1 Seed Script Generator Requirements
1. **Deterministic & Bulk Driven:** Must leverage PostgreSQL internal sequence generation (`generate_series()`) to achieve high generation throughput.
2. **Realistic Skew:** Data distribution must reflect real-world activity:
   * Timestamps distributed across a multi-year window (~2 years).
   * Transaction counts skewed using non-uniform distributions (e.g., 5% high-volume accounts, majority low-volume accounts).
3. **Data Integrity:** All generated records must satisfy FK, positive amount, status, and distinct account constraints.

### 3.2 Seed Execution & Verification Procedure

```sql
-- Verification SQL Script
SELECT 'customers' AS entity, COUNT(*) FROM customers
UNION ALL
SELECT 'accounts', COUNT(*) FROM accounts
UNION ALL
SELECT 'transactions', COUNT(*) FROM transactions;

-- Integrity Verification Rules
SELECT COUNT(*) FROM transactions WHERE amount <= 0; -- Must return 0
SELECT COUNT(*) FROM transactions WHERE sender_account_id = receiver_account_id; -- Must return 0
```