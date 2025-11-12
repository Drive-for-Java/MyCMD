@echo off
setlocal

echo ::Info::  Building MyCMD for Windows...

REM --- Get absolute path of this script and move to project root ---
set "SCRIPT_DIR=%~dp0"
cd /d "%SCRIPT_DIR%\.."

REM --- Check if Maven is available ---
where mvn >nul 2>&1
if errorlevel 1 (
    echo ::Error:: Maven not found in PATH. Please install Maven first.
    pause
    exit /b 1
)

REM --- Run the Maven build ---
echo ::Info:: Building with Maven...
call mvn clean package
if errorlevel 1 (
    echo ::Error:: Maven build failed.
    pause
    exit /b 1
)

REM --- Ensure dist/dependencies directory exists ---
set "DIST_DIR=%SCRIPT_DIR%dist\dependencies"
if not exist "%DIST_DIR%" mkdir "%DIST_DIR%"

REM --- Copy the generated JAR to output folder ---
copy /y "target\MyCMD-GUI*.jar" "%DIST_DIR%\MyCMD-GUI.jar" >nul
if errorlevel 1 (
    echo ::Error:: Copy failed. Check if target JAR exists.
    dir /b target\*.jar
    pause
    exit /b 1
)

REM --- Ensure dist/icon directory exists ---
set "ICO_DIR=%SCRIPT_DIR%dist\icon"
if not exist "%ICO_DIR%" mkdir "%ICO_DIR%"

REM --- Copy the ico file to output folder ---
copy /y "icons\mycmd.ico" "%ICO_DIR%" >nul
if errorlevel 1 (
    echo ::Error:: Copy failed. Check if target ico file exists.
    echo Available icons:
    dir /b icons\*.ico
    pause
    exit /b 1
)

REM --- Ensure bin output directory exists ---
set "BIN_DIR=%SCRIPT_DIR%dist\bin"
if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"

REM ==================================================================
REM === Launch4j silent EXE creation section =========================
REM ==================================================================

echo ::Info:: Creating Windows EXE using Launch4j...

REM --- Launch4j exe path (modify if installed elsewhere) ---
set "LAUNCH4J_EXE=C:\Program Files (x86)\Launch4j\launch4j.exe"

REM --- Paths for config and output ---
set "CONFIG_FILE=%DIST_DIR%\launch4jConfig.xml"
set "JAR_FILE=%DIST_DIR%\MyCMD-GUI.jar"
set "ICO_FILE=%ICO_DIR%\mycmd.ico"
set "EXE_FILE=%BIN_DIR%\MyCMD-GUI.exe"

REM --- Verify Launch4j executable exists ---
if not exist "%LAUNCH4J_EXE%" (
    echo ::Error:: Launch4j executable not found at "%LAUNCH4J_EXE%".
    echo Please install Launch4j or update LAUNCH4J_EXE path in this script.
    pause
    exit /b 1
)

REM --- Regenerate Launch4j config XML dynamically ---
(
    echo ^<?xml version="1.0" encoding="UTF-8"?^>
    echo ^<launch4jConfig^>
    echo   ^<dontWrapJar^>false^</dontWrapJar^>
    echo   ^<headerType^>gui^</headerType^>
    echo   ^<jar^>%JAR_FILE%^</jar^>
    echo   ^<outfile^>%EXE_FILE%^</outfile^>
    echo   ^<errTitle^>MyCMD-GUI Error^</errTitle^>
    echo   ^<cmdLine^>^</cmdLine^>
    echo   ^<chdir^>.^</chdir^>
    echo   ^<priority^>normal^</priority^>
    echo   ^<downloadUrl^>^</downloadUrl^>
    echo   ^<supportUrl^>^</supportUrl^>
    echo   ^<stayAlive^>false^</stayAlive^>
    echo   ^<restartOnCrash^>false^</restartOnCrash^>
    echo   ^<manifest^>^</manifest^>
    echo   ^<icon^>%ICO_FILE%^</icon^>
    echo   ^<jre^>
    echo     ^<path^>%%JAVA_HOME%%;%%PATH%%^</path^>
    echo     ^<requiresJdk^>false^</requiresJdk^>
    echo     ^<requires64Bit^>false^</requires64Bit^>
    echo     ^<minVersion^>17</minVersion^>
    echo     ^<maxVersion^>^</maxVersion^>
    echo   ^</jre^>
    echo ^</launch4jConfig^>
) > "%CONFIG_FILE%"

REM --- Run Launch4j executable silently ---
"%LAUNCH4J_EXE%" "%CONFIG_FILE%" >nul 2>&1
if errorlevel 1 (
    echo ::Error:: Launch4j build failed!
    echo If Launch4j prints no console output, run the command manually to see details:
    echo    "%LAUNCH4J_EXE%" "%CONFIG_FILE%"
    pause
    exit /b %errorlevel%
)

echo.
echo ::Success:: Build complete!
echo ::Info:: Files located at:
echo    - JAR: scripts\dist\dependencies\MyCMD-GUI.jar
echo    - ICO: scripts\dist\icon\mycmd.ico
echo    - EXE: scripts\dist\bin\MyCMD-GUI.exe
echo.
pause
endlocal
