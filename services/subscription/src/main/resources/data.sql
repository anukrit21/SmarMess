INSERT INTO subscription_plans (name, description, price, currency, duration_in_months)
VALUES 
    ('Basic Plan', 'Basic subscription plan with essential features', 9.99, 'USD', 1),
    ('Premium Plan', 'Premium subscription plan with advanced features', 19.99, 'USD', 1),
    ('Enterprise Plan', 'Enterprise subscription plan with all features', 49.99, 'USD', 1)
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    description = VALUES(description),
    price = VALUES(price),
    currency = VALUES(currency),
    duration_in_months = VALUES(duration_in_months); 