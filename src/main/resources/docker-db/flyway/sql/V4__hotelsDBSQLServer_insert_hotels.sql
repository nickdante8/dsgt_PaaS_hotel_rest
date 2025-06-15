-- Insert Hotels
INSERT INTO hotels (id, hotel_name, location, room_service, breakfast, number_of_stars, available_rooms, total_rooms, price_adult, price_child)
SELECT * FROM (
    VALUES
    (1, 'Skyline Suites NYC', 'New York', 1, 1, 5, 5, 5, 220.00, 140.00),
    (2, 'Colosseum Comfort', 'Rome', 1, 1, 4, 6, 6, 150.00, 90.00),
    (3, 'Barcelona Breeze Hotel', 'Barcelona', 1, 1, 4, 4, 4, 130.00, 85.00),
    (4, 'Lisbon Light Inn', 'Lisbon', 1, 1, 3, 5, 5, 110.00, 70.00),
    (5, 'Berlin Central Stay', 'Berlin', 1, 0, 4, 4, 4, 120.00, 75.00),
    (6, 'Tokyo Garden Palace', 'Tokyo', 1, 1, 5, 7, 7, 300.00, 200.00),
    (7, 'Dublin Dock Hotel', 'Dublin', 0, 1, 3, 4, 4, 95.00, 60.00),
    (8, 'Buenos Aires Plaza', 'Buenos Aires', 1, 1, 4, 6, 6, 180.00, 110.00),
    (9, 'Amsterdam Canalside Inn', 'Amsterdam', 0, 1, 4, 5, 5, 140.00, 90.00),
    (10, 'Geneva Lakes Hotel', 'Geneva', 1, 1, 5, 4, 4, 200.00, 130.00)
) AS h(id, hotel_name, location, room_service, breakfast, number_of_stars, available_rooms, total_rooms, price_adult, price_child)
WHERE NOT EXISTS (
    SELECT 1 FROM hotels WHERE hotels.id = h.id
);