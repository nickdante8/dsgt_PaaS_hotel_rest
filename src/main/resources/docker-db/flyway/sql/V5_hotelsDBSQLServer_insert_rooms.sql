-- Insert Rooms
INSERT INTO rooms (id, hotel_id, type, price, from_date, to_date, availability)
SELECT * FROM (
    VALUES
    -- Hotel 1 - New York
    (1, 1, 'Deluxe', 220.00, NULL, NULL, TRUE),
    (2, 1, 'Standard', 220.00, NULL, NULL, TRUE),
    (3, 1, 'Suite', 250.00, NULL, NULL, TRUE),
    (4, 1, 'Family', 240.00, NULL, NULL, TRUE),
    (5, 1, 'Executive', 260.00, NULL, NULL, TRUE),

    -- Hotel 2 - Rome
    (6, 2, 'Standard', 150.00, NULL, NULL, TRUE),
    (7, 2, 'Standard', 150.00, NULL, NULL, TRUE),
    (8, 2, 'Deluxe', 165.00, NULL, NULL, TRUE),
    (9, 2, 'Family', 170.00, NULL, NULL, TRUE),
    (10, 2, 'Suite', 180.00, NULL, NULL, TRUE),
    (11, 2, 'Economy', 140.00, NULL, NULL, TRUE),

    -- Hotel 3 - Barcelona
    (12, 3, 'Standard', 130.00, NULL, NULL, TRUE),
    (13, 3, 'Standard', 130.00, NULL, NULL, TRUE),
    (14, 3, 'Deluxe', 145.00, NULL, NULL, TRUE),
    (15, 3, 'Suite', 155.00, NULL, NULL, TRUE),

    -- Hotel 4 - Lisbon
    (16, 4, 'Standard', 110.00, NULL, NULL, TRUE),
    (17, 4, 'Standard', 110.00, NULL, NULL, TRUE),
    (18, 4, 'Deluxe', 125.00, NULL, NULL, TRUE),
    (19, 4, 'Family', 130.00, NULL, NULL, TRUE),
    (20, 4, 'Suite', 135.00, NULL, NULL, TRUE),

    -- Hotel 5 - Berlin
    (21, 5, 'Standard', 120.00, NULL, NULL, TRUE),
    (22, 5, 'Deluxe', 135.00, NULL, NULL, TRUE),
    (23, 5, 'Economy', 110.00, NULL, NULL, TRUE),
    (24, 5, 'Family', 125.00, NULL, NULL, TRUE),

    -- Hotel 6 - Tokyo
    (25, 6, 'Standard', 300.00, NULL, NULL, TRUE),
    (26, 6, 'Standard', 300.00, NULL, NULL, TRUE),
    (27, 6, 'Deluxe', 320.00, NULL, NULL, TRUE),
    (28, 6, 'Deluxe', 320.00, NULL, NULL, TRUE),
    (29, 6, 'Suite', 350.00, NULL, NULL, TRUE),
    (30, 6, 'Suite', 350.00, NULL, NULL, TRUE),
    (31, 6, 'Family', 340.00, NULL, NULL, TRUE),

    -- Hotel 7 - Dublin
    (32, 7, 'Standard', 95.00, NULL, NULL, TRUE),
    (33, 7, 'Deluxe', 105.00, NULL, NULL, TRUE),
    (34, 7, 'Economy', 90.00, NULL, NULL, TRUE),
    (35, 7, 'Family', 100.00, NULL, NULL, TRUE),

    -- Hotel 8 - Buenos Aires
    (36, 8, 'Standard', 180.00, NULL, NULL, TRUE),
    (37, 8, 'Standard', 180.00, NULL, NULL, TRUE),
    (38, 8, 'Deluxe', 195.00, NULL, NULL, TRUE),
    (39, 8, 'Deluxe', 195.00, NULL, NULL, TRUE),
    (40, 8, 'Suite', 210.00, NULL, NULL, TRUE),
    (41, 8, 'Family', 200.00, NULL, NULL, TRUE),

    -- Hotel 9 - Amsterdam
    (42, 9, 'Standard', 140.00, NULL, NULL, TRUE),
    (43, 9, 'Standard', 140.00, NULL, NULL, TRUE),
    (44, 9, 'Deluxe', 155.00, NULL, NULL, TRUE),
    (45, 9, 'Family', 160.00, NULL, NULL, TRUE),
    (46, 9, 'Suite', 165.00, NULL, NULL, TRUE),

    -- Hotel 10 - Geneva
    (47, 10, 'Lake View', 200.00, NULL, NULL, TRUE),
    (48, 10, 'Deluxe', 215.00, NULL, NULL, TRUE),
    (49, 10, 'Suite', 250.00, NULL, NULL, TRUE),
    (50, 10, 'Executive', 260.00, NULL, NULL, TRUE)

) AS r(id, hotel_id, type, price, from_date, to_date, availability)
WHERE NOT EXISTS (
    SELECT 1 FROM rooms WHERE rooms.id = r.id
);