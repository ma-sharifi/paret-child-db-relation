#!/bin/sh
set -e

echo "=== Waiting for app at $BASE_URL ==="
until curl -sf "$BASE_URL/api/parents" > /dev/null 2>&1; do
  echo "  app not ready, retrying in 3s..."
  sleep 3
done

echo "=== App is ready. Running Bruno tests ==="
bru run --env docker
