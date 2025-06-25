-- Insert Hotels
INSERT INTO hotels (id, hotel_name, location, room_service, breakfast, number_of_stars, available_rooms, total_rooms, price_adult, price_child)
SELECT * FROM (
    VALUES
    ('HT001', 'Skyline Suites NYC', 'New York', TRUE, TRUE, 5, 5, 5, 220.00, 140.00),
    ('HT002', 'Colosseum Comfort', 'Rome', TRUE, TRUE, 4, 6, 6, 150.00, 90.00),
    ('HT003', 'Barcelona Breeze Hotel', 'Barcelona', TRUE, TRUE, 4, 4, 4, 130.00, 85.00),
    ('HT004', 'Lisbon Light Inn', 'Lisbon', TRUE, TRUE, 3, 5, 5, 110.00, 70.00),
    ('HT005', 'Berlin Central Stay', 'Berlin', TRUE, FALSE, 4, 4, 4, 120.00, 75.00),
    ('HT006', 'Tokyo Garden Palace', 'Tokyo', TRUE, TRUE, 5, 7, 7, 300.00, 200.00),
    ('HT007', 'Dublin Dock Hotel', 'Dublin', FALSE, TRUE, 3, 4, 4, 95.00, 60.00),
    ('HT008', 'Buenos Aires Plaza', 'Buenos Aires', TRUE, TRUE, 4, 6, 6, 180.00, 110.00),
    ('HT009', 'Amsterdam Canalside Inn', 'Amsterdam', FALSE, TRUE, 4, 5, 5, 140.00, 90.00),
    ('HT010', 'Geneva Lakes Hotel', 'Geneva', TRUE, TRUE, 5, 4, 4, 200.00, 130.00)
) AS h(id, hotel_name, location, room_service, breakfast, number_of_stars, available_rooms, total_rooms, price_adult, price_child)
WHERE NOT EXISTS (
    SELECT 1 FROM hotels WHERE hotels.id = h.id
);