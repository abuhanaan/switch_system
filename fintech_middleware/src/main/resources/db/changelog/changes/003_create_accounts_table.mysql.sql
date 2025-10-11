CREATE TABLE accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL UNIQUE,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    transaction_pin VARCHAR(10),
    account_type VARCHAR(32) NOT NULL,
    balance DECIMAL(19,2) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);
