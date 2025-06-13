#!/bin/bash

# Get the current directory of the script
current_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo "Script directory is: $current_dir"

# Create the containers but do not start them yet
docker-compose -f "$current_dir"/docker-compose.yml create

# Start the SQL Server container in detached mode
docker-compose -f "$current_dir/docker-compose.yml" up -d sqlserver
