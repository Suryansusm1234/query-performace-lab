# REST API Specification

This document details the HTTP endpoints designed for evaluating query patterns, index selectivity, pagination strategies, and aggregated queries.

---

## Endpoint Summary

| Method | Path | Purpose | Key Query Parameters |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/customers/{id}` | Primary Key Lookup Baseline | N/A |
| `GET` | `/api/accounts/{id}/transactions` | Account History (Pagination & Indexes) | `page`, `size`, `status`, `from`, `to`, `sort` |
| `GET` | `/api/transactions` | Selective Status Filtering | `status`, `page`, `size` |
| `GET` | `/api/analytics/transactions/daily` | Date-range SQL Aggregation | `startDate`, `endDate` |
| `GET` | `/api/analytics/accounts/{id}/summary` | Single-account Aggregation | N/A |
| `POST` | `/api/transactions` | Transaction Writes & Constraints | N/A (JSON Body) |

---

## Endpoint Details

### 1. Customer Lookup
* **`GET /api/customers/{id}`**
* **Purpose:** Serves as a baseline primary-key lookup query to establish minimum application layer and database driver overhead.
* **Success Response (`200 OK`):**
  ```json
  {
    "id": 1001,
    "fullName": "Alice Smith",
    "email": "alice.smith@example.com",
    "createdAt": "2025-01-15T08:30:00Z"
  }
  ```

### 2. Account Transaction History
* **`GET /api/accounts/{id}/transactions`**
* **Purpose:** Retrieves transaction history where the account is either sender or receiver. Primary experimental vector for composite indexes and pagination performance.
* **Query Parameters:**
  * `page` (int, default: `0`): Page index.
  * `size` (int, default: `20`): Page size limit (Max allowed: 100).
  * `status` (string, optional): Filter by `COMPLETED`, `PENDING`, `FAILED`.
  * `from` / `to` (ISO 8601 Timestamp, optional): Time range filter.
  * `sort` (string, default: `createdAt,desc`): Order field and direction.
* **Success Response (`200 OK`):**
  ```json
  {
    "content": [
      {
        "transactionId": 894012,
        "senderAccountId": 204,
        "receiverAccountId": 105,
        "amount": 250.5000,
        "status": "COMPLETED",
        "createdAt": "2026-02-10T14:22:10Z"
      }
    ],
    "page": 0,
    "size": 20,
    "totalElements": 142,
    "totalPages": 8
  }
  ```

### 3. Transaction Filtering
* **`GET /api/transactions`**
* **Purpose:** Evaluates engine scan behavior (Sequential vs. Index Scan) based on low-selectivity filter criteria (e.g., status).
* **Query Parameters:** `status` (required), `page`, `size`.

### 4. Daily Transaction Analytics
* **`GET /api/analytics/transactions/daily`**
* **Purpose:** Benchmarks SQL grouping and date aggregation operations against dynamic filtering.
* **Query Parameters:** `startDate` (YYYY-MM-DD), `endDate` (YYYY-MM-DD).
* **Success Response (`200 OK`):**
  ```json
  [
    {
      "date": "2026-02-01",
      "totalTransactions": 4120,
      "totalVolume": 1054320.7500,
      "successfulCount": 3980
    }
  ]
  ```

### 5. Account Transaction Summary
* **`GET /api/analytics/accounts/{id}/summary`**
* **Purpose:** Evaluates single-account aggregation costs for incoming and outgoing funds.

### 6. Transaction Creation
* **`POST /api/transactions`**
* **Purpose:** Validates write path throughput, database constraint checks, and potential row locking during concurrent writes.
* **Request Body:**
  ```json
  {
    "senderAccountId": 204,
    "receiverAccountId": 105,
    "amount": 100.0000
  }
  ```
* **Success Response (`201 Created`)**