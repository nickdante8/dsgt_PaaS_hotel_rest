-- Create the database if it doesn't exist
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'hotels_db')
BEGIN
    CREATE DATABASE hotels_db;
END
GO
