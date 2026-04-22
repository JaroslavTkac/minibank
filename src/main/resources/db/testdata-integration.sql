-- Reset data for integration-test profile prefill
DELETE FROM processed_event;
DELETE FROM outbox_event;
DELETE FROM transaction;
DELETE FROM account;

-- Seed accounts
INSERT INTO account (id, owner_name, balance, created_date, last_modified_date, created_by, last_modified_by)
VALUES (1, 'Alice Johnson', 1500.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed', 'seed');

INSERT INTO account (id, owner_name, balance, created_date, last_modified_date, created_by, last_modified_by)
VALUES (2, 'Bob Smith', 900.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed', 'seed');

INSERT INTO account (id, owner_name, balance, created_date, last_modified_date, created_by, last_modified_by)
VALUES (3, 'Charlie Brown', 2400.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed', 'seed');

-- Seed transactions
-- status uses enum ordinal by default: PENDING=0, COMPLETED=1, FAILED=2
INSERT INTO transaction (id, debtor_account_id, creditor_account_id, amount, status, created_date, last_modified_date, created_by, last_modified_by)
VALUES (1, 1, 2, 120.00, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed', 'seed');

INSERT INTO transaction (id, debtor_account_id, creditor_account_id, amount, status, created_date, last_modified_date, created_by, last_modified_by)
VALUES (2, 3, 1, 85.25, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'seed', 'seed');

-- Keep identity generators ahead of seeded IDs
ALTER TABLE account ALTER COLUMN id RESTART WITH 4;
ALTER TABLE transaction ALTER COLUMN id RESTART WITH 3;
ALTER TABLE outbox_event ALTER COLUMN id RESTART WITH 1;
ALTER TABLE processed_event ALTER COLUMN id RESTART WITH 1;
