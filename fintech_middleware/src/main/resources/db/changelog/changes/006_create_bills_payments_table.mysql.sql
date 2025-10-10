CREATE TABLE bill_payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    product_id BIGINT,
    biller VARCHAR(50),
    category VARCHAR(50),
    amount DECIMAL(19,2) NOT NULL,
    beneficiary VARCHAR(100) NOT NULL,
    transaction_reference VARCHAR(100) NOT NULL UNIQUE,
    narration VARCHAR(100),
    status VARCHAR(32) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    FOREIGN KEY (account_id) REFERENCES accounts(id),
    FOREIGN KEY (product_id) REFERENCES bill_products(id)
);
