@echo off
echo 🏗️  Building MyCMD for Windows...

REM Clean and package using Maven Wrapper
call mvnw.cmd clean package

REM Move output JARs to /dist folder
if not exist dist mkdir dist
copy target\MyCMD-GUI*.jar dist\MyCMD-GUI.jar

echo ✅ Build complete! File located in dist\MyCMD-GUI.jar
pause
