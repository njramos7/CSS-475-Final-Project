#!/bin/bash

JAR="lib/postgresql-42.7.3.jar"
OUT_DIR="out"
MAIN_CLASS="com.salesanalytics.driver.Driver"

if [ ! -f "$JAR" ]; then
  echo "ERROR: $JAR not found."
  exit 1
fi

echo "Compiling..."
mkdir -p "$OUT_DIR"
javac -cp "$JAR" -d "$OUT_DIR" *.java
echo "Compile successful."

echo "Starting Sales Analytics System..."
java -cp "$OUT_DIR:$JAR" "$MAIN_CLASS"
