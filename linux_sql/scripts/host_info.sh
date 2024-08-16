#!/bin/bash

# Setup and validate arguments
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

# Save machine specs and current machine hostname to variables
hostname=$(hostname -f)
lscpu_out=`lscpu`
cpu_info=$(cat "/proc/cpuinfo")
mem_info=$(cat "/proc/meminfo")

cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | grep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | grep "Model name" | awk -F ': ' '{print $2}' | xargs)
cpu_mhz=$(echo "$cpu_info" | grep "cpu MHz" | awk '{print int($4)}' | tail -n1 | xargs) 
l2_cache=$(echo "$lscpu_out" | grep "^L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(echo "$mem_info" | grep "MemTotal" | awk '{print $2}' | xargs)
timestamp=$(vmstat -t | awk '{print $18, $19}' | tail -n1 | xargs)

# Create the sql
insert_stmt=$(cat <<EOF
DO \$\$
BEGIN
IF NOT EXISTS (SELECT 1 FROM host_info WHERE hostname='$hostname') THEN
  INSERT INTO host_info (
    id,
    hostname,
    cpu_number,
    cpu_architecture,
    cpu_model,
    cpu_mhz,
    l2_cache,
    "timestamp",
    total_mem
  ) VALUES (
    (SELECT COALESCE(MAX(id), 0)+1 FROM host_info),
    '$hostname',
    $cpu_number,
    '$cpu_architecture',
    '$cpu_model',
    $cpu_mhz,
    $l2_cache,
    '$timestamp',
    $total_mem
  );
ELSE
  RAISE NOTICE 'Hostname already exists. No new row inserted.';
END IF;
END \$\$;
EOF
)

export PGPASSWORD=$psql_password

# Update the host_info table
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

# Exit with the correct code
exit $?