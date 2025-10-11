CREATE TABLE bill_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(32) NOT NULL,
    biller VARCHAR(32) NOT NULL,
    name VARCHAR(100) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    description VARCHAR(255),
    active BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
);