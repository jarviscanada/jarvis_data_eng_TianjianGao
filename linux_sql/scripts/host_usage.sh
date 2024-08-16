#!/bin/bash

# Setup and validate args
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Validate number of args
if [ "$#" -ne 5 ]; then
  echo "Illegal number of parameters"
  exit 1
fi

# Save machine statistics in MB and current machine hostname to variables
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)
meminfo=$(cat "/proc/meminfo")


# Retrieve hardware spec variables
# xargs is a trick to trim leading and trailing white spaces
memory_free=$(echo "$vmstat_mb" | awk '{print $4}' | tail -n1 | xargs)
cpu_idle=$(echo "$vmstat_mb" | awk '{print $15}' | tail -n1 | xargs)
cpu_kernel=$(echo "$vmstat_mb" | awk '{print $14}' | tail -n1 | xargs)
disk_write=$(echo "$vmstat_mb" | awk '{print $9}' | tail -n1 | xargs)
disk_read=$(echo "$vmstat_mb" | awk '{print $10}' | tail -n1 | xargs)
disk_io=$(expr $disk_write + $disk_read)
disk_available=$(df -BM / | awk 'NR==2 {print $4}' | sed 's/M//')

# Current time in `2000-01-01 12:00:00` UTC format
timestamp=$(vmstat -t | awk 'NR==3 {print $18, $19}' | tail -n1 | xargs)

# Subquery to find matching id in host_info table
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')"

# PSQL command: Inserts server usage data into host_usage table
# Note: be careful with double and single quotes
insert_stmt=$(cat <<EOF
INSERT INTO host_usage (
  "timestamp",
  host_id,
  memory_free,
  cpu_idle,
  cpu_kernel,
  disk_io,
  disk_available
) VALUES (
  '$timestamp',
  $host_id,
  $memory_free,
  $cpu_idle,
  $cpu_kernel,
  $disk_io,
  $disk_available
);
EOF
)
    
# Set up env var for pql cmd
export PGPASSWORD=$psql_password

# Insert date into database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?