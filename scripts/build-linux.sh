#!/bin/bash
echo "🏗️ Building MyCMD for Linux..."

# Clean and package with Maven Wrapper
./mvnw clean package

# Move JAR to dist folder
mkdir -p dist
cp target/MyCMD-GUI*.jar dist/MyCMD-GUI.jar

echo "✅ Build complete! File located in dist/MyCMD-GUI.jar"
