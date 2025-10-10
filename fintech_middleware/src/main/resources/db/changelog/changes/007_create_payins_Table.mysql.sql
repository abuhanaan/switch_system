CREATE TABLE payins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(32),
    reference CHAR(36),
    account_id BIGINT NOT NULL,
    sender_account_id BIGINT,
    sender_bank_id BIGINT,
    sender_acct_number VARCHAR(30),
    beneficiary_name VARCHAR(100),
    amount DECIMAL(19,2),
    narration VARCHAR(255),
    status VARCHAR(32) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    FOREIGN KEY (account_id) REFERENCES accounts(id),
    FOREIGN KEY (sender_account_id) REFERENCES accounts(id),
    FOREIGN KEY (sender_bank_id) REFERENCES bank(id)
);
