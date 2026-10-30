-- Customer table
CREATE TABLE customer
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    registered_at TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- Account table
CREATE TABLE account (
    id          BIGSERIAL       PRIMARY KEY,
    customer_id BIGINT          NOT NULL REFERENCES customer(id),
    type        VARCHAR(50)     NOT NULL CHECK (type IN ('SAVINGS', 'CURRENT', 'CREDIT')),
    status      VARCHAR(50)     NOT NULL,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

-- Transaction table
CREATE TABLE transaction (
    id          BIGSERIAL       PRIMARY KEY,
    sender_account_id BIGINT NOT NULL REFERENCES account(id),
    receiver_account_id BIGINT NOT NULL REFERENCES account(id),
    type        VARCHAR(50)     NOT NULL,
    amount      NUMERIC(19, 4)  NOT NULL,
    status      VARCHAR(50)     NOT NULL,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);
