#!/bin/bash

cd ..
PROJECT_ROOT=`pwd`
GRADLE_PATH="$PROJECT_ROOT/gradlew"
JAR_FILE=$(find "$PROJECT_ROOT/build/libs" -type f -name "keytree*.jar" | head -n 1)

if [ -f "$GRADLE_PATH" ]; then
    chmod +x "$GRADLE_PATH"
    $GRADLE_PATH clean bootJar
else
    echo "Error: ${GRADLE_PATH} does not exist."
fi

PORT=8080
PID=$(lsof -t -i :$PORT)
if [ ! -z "$PID" ]; then
    echo "Terminating the Spring application running on port $PORT"
    kill -9 $PID
fi

if [ -f "$JAR_FILE" ]; then
    java -jar $JAR_FILE
else
    echo "Error: ${JAR_FILE} does not exist."
fi
