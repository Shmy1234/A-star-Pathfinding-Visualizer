#!/usr/bin/env sh
set -e
DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$DIR"
javac $(find pathfinder -name "*.java")
java pathfinder.Game
