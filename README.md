<p align="center"><a name="readme-top"></a>
  <img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&text=💻+Java+CMD&height=100&section=header"/>

</p>


# 💻 MyCMD – A Custom Command Prompt in Java

![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Java](https://img.shields.io/badge/java-17+-blue)
![License](https://img.shields.io/badge/license-Apache-orange)
![Platform](https://img.shields.io/badge/platform-Windows%20%7C%20Linux-lightgrey)

**MyCMD** is a fully custom **command prompt** implemented in Java.  
It mimics the behavior of Windows `cmd.exe` with commands like `dir`, `cd`, `echo`, `mkdir`, `del`, and more — plus it has its own installer.

## Meet our CMD-builders

<a href="https://github.com/Drive-for-Java/MyCMD/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Drive-for-Java/MyCMD" />
</a>

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

## 🚀 Key Features ✨

<div align="center">

| Feature | Description |
|---------|-------------|
| ✅ **Windows-like Commands** | Supports `dir`, `cd`, `mkdir`, `del`, `copy`, `type`, `cls` |
| ⚡ **Extensible** | Easily add new commands via Java classes |
| 🖥️ **Executable Packaging** | Packaged into `.exe` using **Launch4j** |
| 🛠️ **Installer Wizard** | Simple setup using **Inno Setup** |
| 🌐 **Cross-Platform** | Works on **Windows** and **Linux** (via `jpackage`) |

</div>

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

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create! 💪  

We welcome all kinds of improvements — from fixing bugs 🐛 to adding new commands ⚙️.

### 🪜 Steps to Contribute

1. 🍴 **Fork** the repository  
2. 🌿 **Create a new branch**  
   ```bash
   git checkout -b feature/your-feature-name

## 📜 License

🧾 **Apache License 2.0**  
This project is open-source and available for anyone to use, modify, and share under the terms of the Apache License.

> ✨ Attribution is appreciated but not required.  
> 📎 For complete terms, check the [LICENSE](./LICENSE) file.



 <img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=6,11,20&height=120&section=footer&text=Java,%20Java,%20and%20My%20Java&fontSize=20&fontColor=fff&animation=twinkling"/>


