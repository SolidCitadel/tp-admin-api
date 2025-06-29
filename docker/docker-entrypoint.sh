#!/bin/sh

echo "wait db"
dockerize -wait tcp://${DB_HOST:-db}:3306 -timeout 20s

echo "start app"
java -jar ./app.jar
