-- Create the roles, user_roles and users tables
IF OBJECT_ID('roles', 'U') IS NULL
CREATE TABLE roles
(
    id INT IDENTITY(1,1) NOT NULL,
    name NVARCHAR(20),
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

IF OBJECT_ID('user_roles', 'U') IS NULL
CREATE TABLE user_roles
(
    role_id INT NOT NULL,
    user_id NVARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

IF OBJECT_ID('users', 'U') IS NULL
CREATE TABLE users
(
    id          NVARCHAR(255) NOT NULL,
    username    NVARCHAR(20),
    email       NVARCHAR(50),
    password    NVARCHAR(120),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- Create the tables related to hotel
IF OBJECT_ID('hotels', 'U') IS NULL
CREATE TABLE hotels
(
    id                  NVARCHAR(255) NOT NULL,
    airline_name        NVARCHAR(255) NOT NULL,
    departure_location  NVARCHAR(255) NOT NULL,
    arrival_location    NVARCHAR(255) NOT NULL,
    departure_date      DATETIME2 NOT NULL ,
    arrival_date        DATETIME2 NOT NULL ,
    price_adult         DECIMAL(10, 2) NOT NULL,
    price_child         DECIMAL(10, 2) NOT NULL,
    price_infant        DECIMAL(10, 2) NOT NULL,
    total_seats         INT NOT NULL,
    available_seats     INT NOT NULL,
    CONSTRAINT pk_hotels PRIMARY KEY (id)
);

IF OBJECT_ID('seats', 'U') IS NULL
CREATE TABLE seats
(
    id          BIGINT IDENTITY(1,1) NOT NULL,
    seat_number NVARCHAR(5) NOT NULL,
    status      NVARCHAR(25) NOT NULL CHECK (status IN('SEAT_STATUS_FREE', 'SEAT_STATUS_BOOKED')),
    hotel_id   NVARCHAR(255) NOT NULL,
    CONSTRAINT pk_seats PRIMARY KEY (id)
);

IF OBJECT_ID('baggages', 'U') IS NULL
CREATE TABLE baggages
(
    id      INT IDENTITY(1,1) NOT NULL,
    type    NVARCHAR(100) NOT NULL,
    price   DECIMAL(10, 2) NOT NULL,
    info    NVARCHAR(MAX),
    CONSTRAINT pk_baggages PRIMARY KEY (id)
);

IF OBJECT_ID('hotels_baggages', 'U') IS NULL
CREATE TABLE hotels_baggages
(
    hotel_id   NVARCHAR(255) NOT NULL,
    baggage_id  INT NOT NULL,
    CONSTRAINT pk_hotels_baggages PRIMARY KEY (hotel_id, baggage_id)
);

-- Create the bookings table
IF OBJECT_ID('bookings', 'U') IS NULL
CREATE TABLE bookings
(
    id                  BIGINT IDENTITY(1,1) NOT NULL,
    package_booking_id  NVARCHAR(255) NOT NULL,
    user_id             NVARCHAR(255) NOT NULL,
    hotel_id           NVARCHAR(255) NOT NULL,
    total_seat_count    INT NOT NULL,
    adult_seat_count    INT NOT NULL,
    child_seat_count    INT NOT NULL,
    infant_seat_count   INT NOT NULL,
    total_price         DECIMAL(10, 2),
    status              NVARCHAR(25) NOT NULL CHECK (status IN ('BOOKING_STATUS_CONFIRMED', 'BOOKING_STATUS_CANCELLED')),
    CONSTRAINT pk_bookings PRIMARY KEY (id)
);

-- Create the passangers table
IF OBJECT_ID('passengers', 'U') IS NULL
CREATE TABLE passengers
(
    id                  BIGINT IDENTITY(1,1) NOT NULL,
    type                NVARCHAR(25) NOT NULL CHECK (type IN ('PASSANGER_TYPE_ADULT', 'PASSANGER_TYPE_CHILD', 'PASSANGER_TYPE_INFANT')),
    first_name          NVARCHAR(100) NOT NULL,
    last_name           NVARCHAR(100) NOT NULL,
    birth_date          DATE NOT NULL,
    sex_type            NVARCHAR(30) NOT NULL CHECK (sex_type IN ('PASSANGER_SEX_TYPE_MALE', 'PASSANGER_SEX_TYPE_FEMALE', 'PASSANGER_SEX_TYPE_OTHER')),
    seat_id             BIGINT NOT NULL,
    baggage_id          INT NOT NULL,
    booking_id          BIGINT NOT NULL,
    CONSTRAINT pk_passengers PRIMARY KEY (id)
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

-- Alter tables with their bindings for the actual content
ALTER TABLE seats
    ADD CONSTRAINT fk_seats_on_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id);

ALTER TABLE hotels_baggages
    ADD CONSTRAINT fk_hotels_baggages_on_hotels FOREIGN KEY (hotel_id) REFERENCES hotels(id);
ALTER TABLE hotels_baggages
    ADD CONSTRAINT fk_hotels_baggages_on_baggages FOREIGN KEY (baggage_id) REFERENCES baggages(id);

-- Alter tables for bookings and passengers
ALTER TABLE bookings
    ADD CONSTRAINT fk_bookings_on_user FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE bookings
    ADD CONSTRAINT fk_bookings_on_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id);

ALTER TABLE passengers
    ADD CONSTRAINT fk_passengers_on_seat FOREIGN KEY (seat_id) REFERENCES seats(id);
ALTER TABLE passengers
    ADD CONSTRAINT fk_passengers_on_baggage FOREIGN KEY (baggage_id) REFERENCES baggages(id);
ALTER TABLE passengers
    ADD CONSTRAINT fk_passengers_on_booking FOREIGN KEY (booking_id) REFERENCES bookings(id);
