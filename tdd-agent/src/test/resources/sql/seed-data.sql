-- Insert test user (password is bcrypt hash of "User123!")
INSERT INTO users (id, email, username, password, created_at, updated_at)
VALUES ('11111111-1111-1111-1111-111111111111', 'user@qnr.com.gr', 'user', '$2a$10$WQhYfk/2fFQ85kQi01Z24uxiFy15ln2zjdFMvIykkeu43B78LINY2', NOW() - INTERVAL '30 days', NOW() - INTERVAL '30 days')
ON CONFLICT (id) DO UPDATE SET 
    email = EXCLUDED.email,
    username = EXCLUDED.username,
    password = EXCLUDED.password,
    updated_at = EXCLUDED.updated_at;

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at, user_id)
SELECT '123e4567-e89b-12d3-a456-426614174000', 'PENDING', 199.99, 'EUR', NOW() - INTERVAL '10 days', NOW() - INTERVAL '10 days', '11111111-1111-1111-1111-111111111111';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at, user_id)
SELECT '123e4567-e89b-12d3-a456-426614174001', 'CONFIRMED', 299.99, 'EUR', NOW() - INTERVAL '9 days', NOW() - INTERVAL '8 days', '11111111-1111-1111-1111-111111111111';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at, user_id)
SELECT '123e4567-e89b-12d3-a456-426614174002', 'SHIPPED', 149.99, 'EUR', NOW() - INTERVAL '8 days', NOW() - INTERVAL '6 days', '11111111-1111-1111-1111-111111111111';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at, user_id)
SELECT '123e4567-e89b-12d3-a456-426614174003', 'DELIVERED', 399.99, 'EUR', NOW() - INTERVAL '7 days', NOW() - INTERVAL '5 days', '11111111-1111-1111-1111-111111111111';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at, user_id)
SELECT '123e4567-e89b-12d3-a456-426614174004', 'CANCELED', 99.99, 'EUR', NOW() - INTERVAL '6 days', NOW() - INTERVAL '6 days', '11111111-1111-1111-1111-111111111111';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at, user_id)
SELECT '123e4567-e89b-12d3-a456-426614174005', 'PENDING', 249.99, 'EUR', NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days', '11111111-1111-1111-1111-111111111111';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at, user_id)
SELECT '123e4567-e89b-12d3-a456-426614174006', 'CONFIRMED', 599.99, 'EUR', NOW() - INTERVAL '4 days', NOW() - INTERVAL '3 days', '11111111-1111-1111-1111-111111111111';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at, user_id)
SELECT '123e4567-e89b-12d3-a456-426614174007', 'SHIPPED', 799.99, 'EUR', NOW() - INTERVAL '3 days', NOW() - INTERVAL '2 days', '11111111-1111-1111-1111-111111111111';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at, user_id)
SELECT '123e4567-e89b-12d3-a456-426614174008', 'DELIVERED', 899.99, 'EUR', NOW() - INTERVAL '2 days', NOW() - INTERVAL '1 day', '11111111-1111-1111-1111-111111111111';

INSERT INTO orders (id, status, total_amount, currency, created_at, updated_at, user_id)
SELECT '123e4567-e89b-12d3-a456-426614174009', 'PENDING', 199.99, 'EUR', NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', '11111111-1111-1111-1111-111111111111';
