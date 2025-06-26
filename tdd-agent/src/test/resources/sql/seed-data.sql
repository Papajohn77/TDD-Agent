INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174000', 'PENDING', 199.99, 'EUR', NOW() - INTERVAL '10 days', NOW() - INTERVAL '10 days';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174001', 'CONFIRMED', 299.99, 'EUR', NOW() - INTERVAL '9 days', NOW() - INTERVAL '8 days';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174002', 'SHIPPED', 149.99, 'EUR', NOW() - INTERVAL '8 days', NOW() - INTERVAL '6 days';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174003', 'DELIVERED', 399.99, 'EUR', NOW() - INTERVAL '7 days', NOW() - INTERVAL '5 days';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174004', 'CANCELED', 99.99, 'EUR', NOW() - INTERVAL '6 days', NOW() - INTERVAL '6 days';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174005', 'PENDING', 249.99, 'EUR', NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174006', 'CONFIRMED', 599.99, 'EUR', NOW() - INTERVAL '4 days', NOW() - INTERVAL '3 days';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174007', 'SHIPPED', 799.99, 'EUR', NOW() - INTERVAL '3 days', NOW() - INTERVAL '2 days';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174008', 'DELIVERED', 899.99, 'EUR', NOW() - INTERVAL '2 days', NOW() - INTERVAL '1 day';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174009', 'PENDING', 199.99, 'EUR', NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day';
