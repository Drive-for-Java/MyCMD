@echo off
setlocal
set MYCMD_LAUNCHED=true

:: Hide CMD window by using javaw instead of java
javaw -jar target\MyCMD-GUI-1.0-SNAPSHOT.jar

endlocal
exit
