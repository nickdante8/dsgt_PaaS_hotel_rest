# dsgt_PaaS_hotel_rest

## 📦 Features

✅ Local SQL Server container (Developer Edition)  
✅ Database migrations with Flyway  
✅ Sample data seeding  
✅ Easy integration with IntelliJ or other IDEs

---

## 🔧 Prerequisites

- [Docker](https://docs.docker.com/get-docker/) installed
- [Docker Compose](https://docs.docker.com/compose/) installed
- (Optional) IntelliJ IDEA with Database Plugin

---

## 🗂️ Project Structure
```
project/src/main/resources
│
├── docker-db/
│ │── flyway/sql/
│ │ └── V1__init.sql # Sample migration file
│ │ └── V2__init.sql # Sample migration file
│ │ └── V3__init.sql # Sample migration file
│ │── init-db/
│ │ └── init-db.sh # Bash script to create db if it doesn't exist
│ │── docker-compose.yml # Defines services: sqlserver, flyway
│ └── docker-start.sh # Bash script to create and run containers in sequence
├── postman/
│ └── Travel.postman_collection.json # Postman configuration sample to access and test API
└─ application.properties # Project configuration
```

---

## 🚀 Usage

### 1️⃣ Build and Start Containers

```bash
chmod +x ./src/main/resources/docker-db/docker-start.sh
./src/main/resources/docker-db/docker-start.sh
```

This script:
 - Creates containers
 - Starts SQL Server

Note: 
 - Flyway migration is started manually

### 2️⃣ Access SQL Server with DBeaver
 - Port: 10433 (localhost docker container)
 - Username: sa
 - Password: Hotels_Password1!
 - Database: hotels_db

---

## 📄 Customizing Migrations

Add new .sql migration files in flyway/sql/.
Example: V4__add_new_table.sql

Flyway will pick them up automatically on container `flyway-migrations` start.