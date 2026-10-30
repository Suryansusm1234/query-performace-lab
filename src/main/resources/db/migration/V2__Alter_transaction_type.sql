ALTER TABLE transaction
    ADD CONSTRAINT chk_transaction_type CHECK (type IN ('TRANSFER', 'PAYMENT', 'WITHDRAWAL', 'DEPOSIT'));