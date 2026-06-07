#!/bin/bash
set -e

REPO_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
EXAMPLE_DIR="$REPO_ROOT/examples/simple"
BUILD_DIR="$EXAMPLE_DIR/target/classes"

STRUCT_CLASSES="$REPO_ROOT/structSimV1/target/classes"
LOG4J_JAR="$HOME/.m2/repository/org/apache/logging/log4j/log4j-api/2.24.3/log4j-api-2.24.3.jar"

echo "==> Compiling structSimV1..."
cd "$REPO_ROOT/structSimV1"
mvn compile -q

echo "==> Creating output directories..."
mkdir -p /tmp/structsim-results /tmp/structsim-simulator
mkdir -p "$BUILD_DIR"

echo "==> Compiling example..."
javac -cp "$STRUCT_CLASSES:$LOG4J_JAR" \
  -d "$BUILD_DIR" \
  "$EXAMPLE_DIR/src/main/java/example/Simulation.java"

echo "==> Running simulation..."
java -cp "$BUILD_DIR:$STRUCT_CLASSES:$LOG4J_JAR:$EXAMPLE_DIR/src/main/resources" \
  example.Simulation

echo ""
echo "Done! Results written to /tmp/structsim-results/SummaryFile.txt"
echo ""
cat /tmp/structsim-results/SummaryFile.txt
