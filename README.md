# 💻 MyCMD – A Custom Command Prompt in Java

![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Java](https://img.shields.io/badge/java-17+-blue)
![License](https://img.shields.io/badge/license-Apache-orange)
![Platform](https://img.shields.io/badge/platform-Windows%20%7C%20Linux-lightgrey)

**MyCMD** is a fully custom **command prompt** implemented in Java.  
It mimics the behavior of Windows `cmd.exe` with commands like `dir`, `cd`, `echo`, `mkdir`, `del`, and more — plus it has its own installer.

---

## 📂 Project Structure
```
MyCMD/
 ┣ README.md                  # Project description, badges, usage
 ┣ pom.xml                    # Maven build config
 ┣ src/
 ┃ ┗ main/
 ┃    ┗ java/
 ┃       ┗ com/
 ┃          ┗ mycmd/
 ┃             ┣ App.java          # Main entry point (starts the shell)
 ┃             ┣ ShellContext.java # Stores current working dir, state
 ┃             ┣ Command.java      # Interface for all commands
 ┃             ┗ commands/         # All custom command classes
 ┃                ┣ DirCommand.java
 ┃                ┣ CdCommand.java
 ┃                ┣ EchoCommand.java
 ┃                ┣ MkdirCommand.java
 ┃                ┣ RmdirCommand.java
 ┃                ┣ CopyCommand.java
 ┃                ┣ DelCommand.java
 ┃                ┣ TypeCommand.java
 ┃                ┣ ClsCommand.java
 ┃                ┣ HelpCommand.java
 ┃                ┣ ExitCommand.java
 ┃                ┗ VersionCommand.java
 ┣ icons/
 ┃ ┗ mycmd.ico              # App/installer icon
 ┣ scripts/
 ┃ ┣ build-windows.bat      # Build + package EXE + run installer script
 ┃ ┗ build-linux.sh         # Build + package for Linux (deb/rpm)
 ┣ installer/
 ┃ ┗ mycmd.iss              # Inno Setup script for Windows installer
 ┗ dist/                    # Output folder (auto-created after build)
    ┣ bin/
    ┃ ┗ MyCMD.exe           # Wrapped executable (via Launch4j or jpackage)
    ┣ lib/
    ┃ ┗ dependencies.jar    # Extra JAR dependencies (if any)
    ┗ setup.exe             # Generated installer (from Inno Setup)
```


---

## 🚀 Features

- ✅ Windows-like commands (`dir`, `cd`, `mkdir`, `del`, `copy`, `type`, `cls`)  
- ✅ Extensible via Java classes (easy to add new commands)  
- ✅ Packaged into `.exe` using **Launch4j**  
- ✅ Installer wizard using **Inno Setup**  
- ✅ Works on **Windows** and **Linux** (via `jpackage`)  

---

## 📦 Requirements

To build MyCMD you need:

- [Java JDK 17+](https://adoptium.net/)  
- [Apache Maven](https://maven.apache.org/)  
- [Launch4j](https://launch4j.sourceforge.net/) (for `.exe` wrapping)  
- [Inno Setup](https://jrsoftware.org/isinfo.php) (for Windows installer)  

*(Linux users can use `jpackage` instead of Launch4j/Inno Setup.)*

---

## 🛠️ Build Instructions

### 🔹 Windows
```bash
scripts\build-windows.bat
```
👆🏻 This will:

- Build the JAR with Maven

- Wrap it into an .exe with Launch4j

- Generate a setup.exe installer with Inno Setup

### Final Output 
```
dist/
 ┣ bin/MyCMD.exe
 ┣ lib/dependencies.jar
 ┗ setup.exe
```
### Linux 
```
chmod +x scripts/build-linux.sh
./scripts/build-linux.sh
```
This will build a `.deb` or `.rpm` package using `jpackage`.

## 🖥️ Usage

After installation, run:
```
MyCMD
```

You can now use commands like:
```
> dir
> cd ..
> mkdir test
> echo Hello World!
> type file.txt
> del file.txt
> help
> exit
```

## 🧩 Extending MyCMD
> [!NOTE]
> There are many commands left like `ls` and `Ping Command`

### 🤝 Contributing

- Fork the repo (or request an invite if it’s in the org)

- Create a feature branch

- Commit your changes

- Open a Pull Request

## 📜 License

Licensed under the Apache License
You are free to use, modify, and distribute.
