#!/bin/bash
echo "=== Building MyCMD for Linux ==="

mvn clean package

rm -rf dist
mkdir -p dist/bin dist/lib dist/icons

cp target/MyCMD-1.0.jar dist/lib/dependencies.jar
cp icons/mycmd.ico dist/icons/mycmd.ico

# Example jpackage usage
jpackage \
  --name MyCMD \
  --input dist/lib \
  --main-jar dependencies.jar \
  --main-class com.mycmd.App \
  --icon icons/mycmd.ico \
  --type deb \
  --dest dist

echo "Build complete. Installer is in dist/"
