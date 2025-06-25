-- Create the roles, user_roles and users tables
CREATE TABLE IF NOT EXISTS roles
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS users
(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    username VARCHAR(20),
    email VARCHAR(50),
    password VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    role_id INT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

-- Create the tables related to hotel
CREATE TABLE IF NOT EXISTS hotels
(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    hotel_name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    room_service BOOLEAN NOT NULL,
    breakfast BOOLEAN NOT NULL,
    number_of_stars INT NOT NULL,
    available_rooms INT NOT NULL,
    total_rooms INT NOT NULL,
    price_adult DECIMAL(10, 2) NOT NULL,
    price_child DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS rooms
(
    id BIGSERIAL PRIMARY KEY,
    hotel_id VARCHAR(255) NOT NULL,
    type VARCHAR(25) NOT NULL CHECK (type IN('Economy', 'Standard',
                                                 'Deluxe', 'Family',
                                                 'Suite', 'Executive')),
    price DECIMAL(10, 2) NOT NULL,
    from_date TIMESTAMP NOT NULL,
    to_date TIMESTAMP NOT NULL,
    is_available BOOLEAN NOT NULL
);

-- Create the bookings table
CREATE TABLE IF NOT EXISTS bookings
(
    id BIGSERIAL PRIMARY KEY,
    package_booking_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    hotel_id VARCHAR(255) NOT NULL,
    from_date TIMESTAMP NOT NULL,
    to_date TIMESTAMP NOT NULL,
    num_adults INT NOT NULL,
    num_children INT NOT NULL,
    total_price DECIMAL(10, 2),
    status VARCHAR(25) NOT NULL CHECK (status IN ('BOOKING_STATUS_CONFIRMED',
                                                     'BOOKING_STATUS_CANCELLED'))
);

-- Create the visitors table
CREATE TABLE IF NOT EXISTS visitors
(
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(25) NOT NULL CHECK (type IN ('VISITOR_TYPE_ADULT',
                                                     'VISITOR_TYPE_CHILD')),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    sex_type VARCHAR(30) NOT NULL CHECK (sex_type IN ('SEX_TYPE_MALE',
                                                             'SEX_TYPE_FEMALE',
                                                             'SEX_TYPE_OTHER')),
    room_id BIGINT NOT NULL,
    reservation_id BIGINT NOT NULL
);

-- Alter tables with their bindings for users
ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);
ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);
ALTER TABLE roles
    ADD CONSTRAINT uc_roles_name UNIQUE (name);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES users (id);

-- Alter tables for bookings and passengers
ALTER TABLE rooms
    ADD CONSTRAINT fk_rooms_on_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id);

ALTER TABLE bookings
    ADD CONSTRAINT fk_bookings_on_user FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE bookings
    ADD CONSTRAINT fk_bookings_on_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id);

ALTER TABLE visitors
    ADD CONSTRAINT fk_visitors_on_room FOREIGN KEY (room_id) REFERENCES rooms(id);
ALTER TABLE visitors
    ADD CONSTRAINT fk_visitors_on_hotel FOREIGN KEY (reservation_id) REFERENCES bookings(id);
