#!/bin/bash

docker run --restart always -d -p 33306:3306 -e MYSQL_ROOT_PASSWORD=1234 --name mysql mysql
