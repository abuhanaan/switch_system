CREATE TABLE payouts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(32),
    reference VARCHAR(100),
    account_id BIGINT NOT NULL,
    beneficiary_account_id BIGINT,
    beneficiary_bank_id BIGINT,
    beneficiary_acct_number VARCHAR(30),
    beneficiary_name VARCHAR(100),
    amount DECIMAL(19,2),
    narration VARCHAR(255),
    status VARCHAR(32) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    FOREIGN KEY (account_id) REFERENCES accounts(id),
    FOREIGN KEY (beneficiary_account_id) REFERENCES accounts(id),
    FOREIGN KEY (beneficiary_bank_id) REFERENCES bank(id)
);