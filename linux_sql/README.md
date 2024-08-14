# Linux Cluster Monitoring Agent
Automated logging of hardware specs and usage of Linux Cluster nodes using Docker, PostgreSQL, and Bash scripting.

# Introduction
<!-- (about 100-150 words)
Discuss the design of the project. What does this project/product do? Who are the users? What are the technologies you have used? (e.g. bash, docker, git, etc..) -->
Linux administrators often require monitoring the resource usage (e.g. CPU, memory, and disk) of each cluster node in real time, for the purpose of logging and resource planning. Thankfully, it is easy to create Bash scripts to automate such process. The <em>Linux Cluster Monitoring Agent ™</em> leverages Docker to provision a Postgres Database to support automated logging and admin querying using SQL. System info is captured and parsed using ```vmstat```, ```cpuinfo```, ```meminfo```, and ```df```. ```crontab``` is used to automatically update the <em>host usage</em> table every minute via SQL. The scripts are tested on a remote GCP server running on Rocky Linux®.


# Quick Start
Clone the repository to your local linux machine using the following command or by clicking on the "<span style="color: green;"><> Code</span>" button to the top-right and choosing to download ZIP.
```
git clone https://github.com/jarviscanada/jarvis_data_eng_TianjianGao
```

Change your current directory to the scripts directory:
```
cd /jarvis_data_eng_TianjianGao/linux_sql/scripts
```

<!-- - Start a psql instance using psql_docker.sh -->
Start a PostgreSQL (PSQL) Docker instance using the <em>psql_docker.sh</em> script. Note that Docker and PSQL must be installed on your system. [Install Docker](https://docs.docker.com/engine/install/) [Install PSQL](https://www.postgresql.org/download/)
```
# update exec permission for the script
chmod +x psql_docker.sh 

# execute the script. cmd={create|start|stop}. default db_username=postgres and db_password=password
./psql_docker.sh <cmd> <db_username> <db_password> 
```

<!-- - Create tables using ddl.sql -->
If not already the <em>ddl.sql</em> script under the <em>sql</em> directory to initialize the <em>host_info</em> table for storing the system specs (CPU model, speed, memory info, etc.) and <em>host_usage</em> table for logging the system usage.

Then, insert hardware specs data into the DB using host_info.sh
```
# update exec permission for the script
chmod +x host_info.sh 

# execute the script. cmd={create|start|stop}. default db_username=postgres and db_password=password
./host_info.sh <psql_host> <psql_port> <db_name> <db_username> <db_password>
```

- Insert hardware usage data into the DB using host_usage.sh
```
Code
```

- Crontab setup
```
Code
```

# Implemenation
Discuss how you implement the project.
## Architecture
Draw a cluster diagram with three Linux hosts, a DB, and agents (use draw.io website). Image must be saved to the `assets` directory.

## Scripts
Shell script description and usage (use markdown code block for script usage)
- psql_docker.sh
- host_info.sh
- host_usage.sh
- crontab
- queries.sql (describe what business problem you are trying to resolve)

## Database Modeling
Describe the schema of each table using markdown table syntax (do not put any sql code)
- `host_info`
- `host_usage`

# Test
How did you test your bash scripts DDL? What was the result?

# Deployment
How did you deploy your app? (e.g. Github, crontab, docker)

# Improvements
- Create a Bash script to automate installing Docker and PostgreSQL on popular Linux distros such as Ubuntu, RHEL, and Debian.
- Use functions in bash scripts to improve the reusability and readability of the scripts.
- Create a script to set or change the interval of the scheduled task in ```crontab```.