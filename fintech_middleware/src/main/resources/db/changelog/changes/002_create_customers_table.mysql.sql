CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id CHAR(36) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    bvn VARCHAR(11) NOT NULL,
    nin VARCHAR(20) NOT NULL,
    id_validated BOOLEAN NOT NULL,
    address VARCHAR(255),
    onboarding_status VARCHAR(32),
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
