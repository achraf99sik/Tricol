CREATE TABLE IF NOT EXISTS suppliers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company VARCHAR(255),
    address VARCHAR(255),
    contact VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    city VARCHAR(100),
    ice INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);