-- Create databases for all services
CREATE DATABASE demoapp_auth;
CREATE DATABASE demoapp_user;
CREATE DATABASE demoapp_otp;
CREATE DATABASE demoapp_admin;
CREATE DATABASE demoapp_menu;
CREATE DATABASE demoapp_order;
CREATE DATABASE demoapp_delivery;
CREATE DATABASE demoapp_owner;
CREATE DATABASE demoapp_subscription;

-- Grant privileges to postgres user
GRANT ALL PRIVILEGES ON DATABASE demoapp_auth TO postgres;
GRANT ALL PRIVILEGES ON DATABASE demoapp_user TO postgres;
GRANT ALL PRIVILEGES ON DATABASE demoapp_otp TO postgres;
GRANT ALL PRIVILEGES ON DATABASE demoapp_admin TO postgres;
GRANT ALL PRIVILEGES ON DATABASE demoapp_menu TO postgres;
GRANT ALL PRIVILEGES ON DATABASE demoapp_order TO postgres;
GRANT ALL PRIVILEGES ON DATABASE demoapp_delivery TO postgres;
GRANT ALL PRIVILEGES ON DATABASE demoapp_owner TO postgres;
GRANT ALL PRIVILEGES ON DATABASE demoapp_subscription TO postgres; 