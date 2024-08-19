#!/bin/bash

cmd=$1
db_username=$2
db_password=$3

# start docker
sudo systemctl status docker > /dev/null || systemctl start docker

docker container inspect jrvs-psql > /dev/null
container_status=$?

case $cmd in
  create)

    if [ $container_status -eq 0 ]; then
      echo 'Container already exists'
      exit 1
    fi

    if [ $# -ne 3 ]; then
      echo 'Create requires username and password'
      exit 1
    fi
    
    # create volume pgdata
    docker volume create pgdata
    
    docker run --name jrvs_psql -e POSTGRES_PASSWORD=$db_password -e POSTGRES_USER=$db_username -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
    exit $?
    ;;

  start|stop)

    if [ $container_status -eq 1 ]; then
      echo 'Container has not been created'
      exit 1
    fi

    docker container $cmd jrvs-psql
    exit $?
    ;;

  *)
    echo 'Illegal command'
    echo 'Commands: start|stop|create'
    exit 1
    ;;
esac