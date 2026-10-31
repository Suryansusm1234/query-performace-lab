# Domain Model Specification

The system models a simplified financial transaction system designed for database workload experimentation.

## 1. Entity-Relationship Summary

```
+------------------------+          +------------------------+
|        Customer        |          |        Account         |
+------------------------+          +------------------------+
| PK customer_id         |<---------| PK account_id          |
|    full_name           | 1      * | FK customer_id         |
|    email (UNIQUE)      |          |    account_number (UQ) |
|    created_at          |          |    status              |
+------------------------+          |    created_at          |
                                    +------------------------+
                                       ^            ^
                                       | (sender)   | (receiver)
                                    1  |            |  1
                                    +--+------------+--+
                                    |      Transaction |
                                    +------------------+
                                    | PK transaction_id|
                                    | FK sender_acct_id|
                                    | FK rcvr_acct_id  |
                                    |    amount        |
                                    |    status        |
                                    |    created_at    |
                                    +------------------+
```

---

## 2. Entity Definitions & Invariants

### 2.1 Customer (`Customer`)
Represents an individual or entity using the platform.

* **Attributes:**
  * `customerId` (UUID or BIGSERIAL, Primary Key): Unique surrogate identifier.
  * `fullName` (VARCHAR(100), NOT NULL): Customer name.
  * `email` (VARCHAR(255), UNIQUE, NOT NULL): Email address for lookup and uniqueness validation.
  * `createdAt` (TIMESTAMPTZ, NOT NULL): Registration timestamp.
* **Invariants & Rules:**
  * Email must be unique across the entire database.
  * Deleting a customer must be restricted if associated accounts exist.

### 2.2 Account (`Account`)
Represents a customer's financial account.

* **Attributes:**
  * `accountId` (UUID or BIGSERIAL, Primary Key): Unique account identifier.
  * `customerId` (UUID or BIGSERIAL, Foreign Key, NOT NULL): Reference to owning customer.
  * `accountNumber` (VARCHAR(34), UNIQUE, NOT NULL): Business identifier (e.g., IBAN or structured account code).
  * `status` (VARCHAR(20), NOT NULL): Account state (`ACTIVE`, `FROZEN`, `CLOSED`).
  * `createdAt` (TIMESTAMPTZ, NOT NULL): Account creation timestamp.
* **Invariants & Rules:**
  * Every account must link to a valid `Customer`.
  * Target relationship: Average of ~2 accounts per customer in baseline test sets.

### 2.3 Transaction (`Transaction`)
Represents a funds transfer between two distinct accounts.

* **Attributes:**
  * `transactionId` (UUID or BIGSERIAL, Primary Key): Unique transaction identifier.
  * `senderAccountId` (UUID or BIGSERIAL, Foreign Key, NOT NULL): Source account.
  * `receiverAccountId` (UUID or BIGSERIAL, Foreign Key, NOT NULL): Destination account.
  * `amount` (NUMERIC(19,4), NOT NULL): Transfer monetary value.
  * `status` (VARCHAR(20), NOT NULL): Status (`PENDING`, `COMPLETED`, `FAILED`).
  * `createdAt` (TIMESTAMPTZ, NOT NULL): Transaction execution timestamp.
* **Invariants & Rules:**
  * **Self-Transfer Constraint:** `sender_account_id != receiver_account_id`.
  * **Positive Value Constraint:** `amount > 0.0000`.
  * **Precision Standard:** Monetary values must use PostgreSQL `NUMERIC(19,4)` to prevent floating-point rounding errors.
  * Account transaction history queries must evaluate both sender and receiver roles to present a unified ledger view.