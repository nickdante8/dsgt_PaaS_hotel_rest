-- Create the database if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'hotels_db') THEN
        CREATE DATABASE hotels_db;
    END IF;
END
$$