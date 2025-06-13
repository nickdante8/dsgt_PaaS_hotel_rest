#!/bin/bash

# Start sqlserver and sleep for 20s to make sure is configured and started
/opt/mssql/bin/sqlservr &
sleep 20

# List current files in folder
ls -la /init-db

# Wait for SQL Server to start
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "Hotels_Password1!" -Q "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'hotels_db') CREATE DATABASE hotels_db;"
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "Hotels_Password1!" -Q "SELECT name FROM sys.databases;"

# Give message of finished process
echo "Start up is done!"
