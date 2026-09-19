#!/bin/bash
set -e


REPO_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
OUT_DIR="$REPO_ROOT/output"

echo "==> Building and installing structSimV1 into the local Maven repository..."
mvn -q -f "$REPO_ROOT/structSimV1/pom.xml" install -DskipTests

echo "==> Creating output directories..."
mkdir -p "$OUT_DIR/results" "$OUT_DIR/simulator"

echo "==> Running the example simulation..."
# Run from repo root so output/ resolves here.
cd "$REPO_ROOT"
mvn -q -f "$REPO_ROOT/examples/simple/pom.xml" compile exec:java

echo ""
echo "Done! Results written to $OUT_DIR/results/SummaryFile.txt"
echo ""
cat "$OUT_DIR/results/SummaryFile.txt"
