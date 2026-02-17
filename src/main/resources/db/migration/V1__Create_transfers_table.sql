CREATE TABLE transfers (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    origin_account VARCHAR(255) NOT NULL,
    destination_account VARCHAR(255) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    exchange_rate DOUBLE PRECISION NOT NULL
);