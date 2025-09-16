[Setup]
AppName=MyCMD
AppVersion=1.0
DefaultDirName={pf}\MyCMD
DefaultGroupName=MyCMD
OutputBaseFilename=MyCMD-Installer
SetupIconFile=icons\mycmd.ico

[Files]
Source: "dist\bin\MyCMD.exe"; DestDir: "{app}\bin"
Source: "dist\lib\dependencies.jar"; DestDir: "{app}\lib"
Source: "dist\icons\mycmd.ico"; DestDir: "{app}\icons"

[Icons]
Name: "{group}\MyCMD"; Filename: "{app}\bin\MyCMD.exe"
Name: "{commondesktop}\MyCMD"; Filename: "{app}\bin\MyCMD.exe"

[Run]
Filename: "{app}\bin\MyCMD.exe"; Description: "Launch MyCMD"; Flags: nowait postinstall skipifsilent
