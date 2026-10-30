-- ==============================================================
-- Seed data for Query Performance Lab
-- Use it for creating mock data for testing
-- Generates:
--   10,000  customers
--   20,000  accounts  (2 per customer on average)
--   100,000 transactions (skewed distribution across accounts)
--
-- Deterministic: uses md5 hashing on sequential IDs for
-- repeatable pseudo-random values. Re-running against a
-- fresh database always produces the same dataset.
-- ============================================================

-- ---------- 1. Customers (10,000) ----------

INSERT INTO customer (name, email, registered_at)
SELECT
    'Customer_' || s.id,
    'customer_' || s.id || '@performancelab.dev',
    -- Spread registrations over ~2 years before 2025-01-01
    TIMESTAMP WITH TIME ZONE '2023-01-01 00:00:00 UTC'
        + (INTERVAL '1 second' * (s.id * 63113))  -- ~730 days spread
FROM generate_series(1, 10000) AS s(id);


-- ---------- 2. Accounts (20,000) ----------
-- Each customer gets exactly 2 accounts.
-- First account is always SAVINGS, second cycles through CURRENT and CREDIT.

INSERT INTO account (customer_id, type, status, created_at)
SELECT
    c.id,
    'SAVINGS',
    CASE
        WHEN c.id % 20 = 0 THEN 'CLOSED'
        WHEN c.id % 10 = 0 THEN 'SUSPENDED'
        ELSE 'ACTIVE'
    END,
    c.registered_at + INTERVAL '1 day'
FROM customer c;

INSERT INTO account (customer_id, type, status, created_at)
SELECT
    c.id,
    CASE WHEN c.id % 2 = 0 THEN 'CURRENT' ELSE 'CREDIT' END,
    CASE
        WHEN c.id % 25 = 0 THEN 'CLOSED'
        WHEN c.id % 12 = 0 THEN 'SUSPENDED'
        ELSE 'ACTIVE'
    END,
    c.registered_at + INTERVAL '7 days'
FROM customer c;


-- ---------- 3. Transactions (100,000) ----------
-- Distribution strategy:
--   The sender account is chosen with a power-law skew so that
--   a few accounts generate the majority of transactions.
--   We use  (s % total_accounts)^0.6  style mapping via md5 hash
--   to keep it deterministic yet skewed.
--
-- Status distribution:
--   ~80 % COMPLETED, ~12 % PENDING, ~8 % FAILED
--
-- Timestamps span ~2 years (2023-03-01 to 2025-02-28).
--
-- Amounts range from 1.00 to 50,000.00.

INSERT INTO transaction (sender_account_id, receiver_account_id, type, amount, status, created_at)
SELECT
    sender_id,
    receiver_id,
    tx_type,
    tx_amount,
    tx_status,
    tx_timestamp
FROM (
    SELECT
        -- Skewed sender: low-ID accounts get disproportionately more transactions.
        -- floor(20000 * (hash/max_hash)^2.5) + 1  concentrates picks toward lower IDs.
        GREATEST(1,
            LEAST(20000,
                FLOOR(20000.0 * POWER(
                    ( ('x' || SUBSTRING(md5(s.id::text || 'sender'), 1, 8))::bit(32)::bigint::numeric
                      / 4294967295.0
                    ), 2.5
                ))::int + 1
            )
        ) AS sender_id,

        -- Receiver: uniform distribution but guaranteed different from sender.
        -- We pick from [1..19999] and shift up by 1 if >= sender to avoid self-transfer.
        (CASE
            WHEN uniform_recv >= sender_raw THEN uniform_recv + 1
            ELSE uniform_recv
        END) AS receiver_id,

        -- Transaction type: TRANSFER, PAYMENT, WITHDRAWAL, DEPOSIT
        CASE (s.id % 4)
            WHEN 0 THEN 'TRANSFER'
            WHEN 1 THEN 'PAYMENT'
            WHEN 2 THEN 'WITHDRAWAL'
            ELSE 'DEPOSIT'
        END AS tx_type,

        -- Amount: 1.00 – 50,000.00, skewed toward smaller amounts
        ROUND(
            (1.0 + 49999.0 * POWER(
                ('x' || SUBSTRING(md5(s.id::text || 'amount'), 1, 8))::bit(32)::bigint::numeric
                / 4294967295.0
            , 2.0))::numeric
        , 4) AS tx_amount,

        -- Status: ~80% COMPLETED, ~12% PENDING, ~8% FAILED
        CASE
            WHEN status_hash < 0.80 THEN 'COMPLETED'
            WHEN status_hash < 0.92 THEN 'PENDING'
            ELSE 'FAILED'
        END AS tx_status,

        -- Timestamp: spread over ~2 years (2023-03-01 to 2025-02-28)
        TIMESTAMP WITH TIME ZONE '2023-03-01 00:00:00 UTC'
            + (INTERVAL '1 second' * FLOOR(
                63072000.0 *  -- ~2 years in seconds
                (('x' || SUBSTRING(md5(s.id::text || 'ts'), 1, 8))::bit(32)::bigint::numeric
                 / 4294967295.0)
            )) AS tx_timestamp

    FROM generate_series(1, 100000) AS s(id),
    LATERAL (
        SELECT
            -- Pre-compute sender raw for reuse
            GREATEST(1,
                LEAST(20000,
                    FLOOR(20000.0 * POWER(
                        ( ('x' || SUBSTRING(md5(s.id::text || 'sender'), 1, 8))::bit(32)::bigint::numeric
                          / 4294967295.0
                        ), 2.5
                    ))::int + 1
                )
            ) AS sender_raw,

            -- Uniform receiver in [1..19999]
            GREATEST(1,
                LEAST(19999,
                    FLOOR(19999.0 *
                        (('x' || SUBSTRING(md5(s.id::text || 'receiver'), 1, 8))::bit(32)::bigint::numeric
                         / 4294967295.0)
                    )::int + 1
                )
            ) AS uniform_recv,

            -- Status hash [0..1)
            ('x' || SUBSTRING(md5(s.id::text || 'status'), 1, 8))::bit(32)::bigint::numeric
                / 4294967295.0 AS status_hash
    ) AS helpers
) AS txn;
