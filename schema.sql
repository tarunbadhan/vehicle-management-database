CREATE TABLE Owner (
    owner_id INT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(200)
);

CREATE TABLE Vehicle (
    vehicle_id INT PRIMARY KEY,
    owner_id INT NOT NULL,
    plate_number VARCHAR(20) UNIQUE NOT NULL,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT,
    color VARCHAR(30),
    FOREIGN KEY (owner_id) REFERENCES Owner(owner_id)
);

CREATE TABLE ServiceRecord (
    service_id INT PRIMARY KEY,
    vehicle_id INT NOT NULL,
    service_date DATE NOT NULL,
    service_type VARCHAR(100) NOT NULL,
    cost DECIMAL(10,2),
    notes VARCHAR(255),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
);
