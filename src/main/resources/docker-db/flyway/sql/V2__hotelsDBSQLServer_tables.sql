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
    hotel_name          NVARCHAR(255) NOT NULL,
    location            NVARCHAR(255) NOT NULL,
    room_service        BIT NOT NULL,
    breakfast           BIT NOT NULL,
    number_of_stars     INT NOT NULL,
    available_rooms     INT NOT NULL,
    total_rooms         INT NOT NULL,
    price_adult         DECIMAL(10, 2) NOT NULL,
    price_child         DECIMAL(10, 2) NOT NULL,
    CONSTRAINT pk_hotels PRIMARY KEY (id)
);

IF OBJECT_ID('rooms', 'U') IS NULL
CREATE TABLE rooms
(
    id          BIGINT, --IDENTITY(1,1) NOT NULL,
    hotel_id    NVARCHAR(255) NOT NULL,
    type        NVARCHAR(25) NOT NULL CHECK (type IN('NUMBER_TYPE_SUIT',
    'NUMBER_TYPE_SINGLE', 'NUMBER_TYPE_DOUBLE')),
    price       DECIMAL(10, 2) NOT NULL,
    from_date   DATETIME2 NOT NULL,
    to_date     DATETIME2 NOT NULL,
    is_available   BIT NOT NULL,
    CONSTRAINT pk_rooms PRIMARY KEY (id)
);

-- Create the reservations table
IF OBJECT_ID('reservations', 'U') IS NULL
CREATE TABLE reservations
(
    id                  BIGINT IDENTITY(1,1) NOT NULL,
    package_booking_id  NVARCHAR(255) NOT NULL,
    user_id             NVARCHAR(255) NOT NULL,
    hotel_id            NVARCHAR(255) NOT NULL,
    from_date           DATETIME2 NOT NULL,
    to_date             DATETIME2 NOT NULL,
    num_adults          INT NOT NULL,
    num_children        INT NOT NULL,
    total_price         DECIMAL(10, 2),
    status              NVARCHAR(25) NOT NULL CHECK (status IN ('RESERVATION_STATUS_CONFIRMED', 'RESERVATION_STATUS_CANCELLED', 'RESERVATION_STATUS_REJECTED')),
    CONSTRAINT pk_bookings PRIMARY KEY (id)
);

-- Create the visitors table
IF OBJECT_ID('visitors', 'U') IS NULL
CREATE TABLE visitors
(
    id                  BIGINT IDENTITY(1,1) NOT NULL,
    type                NVARCHAR(25) NOT NULL CHECK (type IN ('VISITOR_TYPE_ADULT', 'VISITOR_TYPE_CHILD')),
    first_name          NVARCHAR(100) NOT NULL,
    last_name           NVARCHAR(100) NOT NULL,
    birth_date          DATE NOT NULL,
    sex_type            NVARCHAR(30) NOT NULL CHECK (sex_type IN ('SEX_TYPE_MALE', 'SEX_TYPE_FEMALE', 'SEX_TYPE_OTHER')),
    room_id             BIGINT NOT NULL,
    reservation_id      BIGINT NOT NULL,
    CONSTRAINT pk_visitors PRIMARY KEY (id)
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
ALTER TABLE rooms
    ADD CONSTRAINT fk_rooms_on_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id);

-- Alter tables for bookings and passengers
ALTER TABLE reservations
    ADD CONSTRAINT fk_reservations_on_user FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE reservations
    ADD CONSTRAINT fk_reservation_on_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id);

ALTER TABLE visitors
    ADD CONSTRAINT fk_visitors_on_room FOREIGN KEY (room_id) REFERENCES rooms(id);
ALTER TABLE visitors
    ADD CONSTRAINT fk_visitors_on_hotel FOREIGN KEY (reservation_id) REFERENCES reservations(id);
