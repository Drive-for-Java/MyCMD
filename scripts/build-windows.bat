@echo off
echo === Building MyCMD for Windows ===

REM Clean and build jar
mvn clean package

REM Create dist structure
rmdir /s /q dist
mkdir dist\bin
mkdir dist\lib
mkdir dist\icons

REM Copy jar + icon
copy target\MyCMD-1.0.jar dist\lib\dependencies.jar
copy icons\mycmd.ico dist\icons\mycmd.ico

echo Build complete. Use Launch4j + Inno Setup to package installer.
