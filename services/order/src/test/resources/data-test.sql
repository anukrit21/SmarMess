-- Insert test mess
INSERT INTO mess (id, name, address, phone, email, is_active)
VALUES (1, 'Test Mess', 'Test Address', '1234567890', 'test@mess.com', true);

-- Insert test menu items
INSERT INTO menu (id, name, description, price, category, is_available, mess_id)
VALUES (1, 'Test Item 1', 'Test Description 1', 50.00, 'MAIN', true, 'mess1'),
       (2, 'Test Item 2', 'Test Description 2', 50.00, 'MAIN', true, 'mess1');

-- Insert test order
INSERT INTO orders (id, user_id, mess_id, total_amount, status, order_time)
VALUES (1, 'user1', 'mess1', 100.00, 'PENDING', CURRENT_TIMESTAMP);

-- Insert test order items
INSERT INTO order_items (order_id, menu_item_id)
VALUES (1, 1),
       (1, 2); 