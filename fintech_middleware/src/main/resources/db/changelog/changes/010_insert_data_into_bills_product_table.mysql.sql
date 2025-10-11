-- Insert DATA and CABLE category bill products
INSERT INTO bill_products (category, biller, name, amount, description, active, created_at, updated_at) VALUES
('DATA', 'MTN', 'MTN Data 1GB', 500.00,  'MTN Data Bundle 1GB', TRUE, NOW(), NOW()),
('DATA', 'AIRTEL', 'Airtel Data 2GB', 800.00, 'Airtel Data Bundle 2GB', TRUE, NOW(), NOW()),
('DATA', 'GLO', 'Glo Data 1.5GB', 600.00, 'Glo Data Bundle 1.5GB', TRUE, NOW(), NOW()),
('DATA', 'NINEMOBILE', '9mobile Data 2GB', 900.00, '9mobile Data Bundle 2GB', TRUE, NOW(), NOW()),
('CABLE', 'DSTV', 'DSTV Compact', 7000.00, 'DSTV Compact Bouquet', TRUE, NOW(), NOW()),
('CABLE', 'GOTV', 'GOTV Max', 3600.00, 'GOTV Max Bouquet', TRUE, NOW(), NOW()),
('CABLE', 'STARTIMES', 'Startimes Basic', 1200.00, 'Startimes Basic Bouquet', TRUE, NOW(), NOW());