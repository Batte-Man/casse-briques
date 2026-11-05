@echo off
REM Compile le jeu et produit casse-briques.jar (JDK 17+ requis)
if exist build rmdir /s /q build
mkdir build\classes
dir /s /b src\*.java > build\sources.txt
javac -encoding UTF-8 --release 17 -d build\classes @build\sources.txt || exit /b 1
xcopy /e /i /q src\fonts build\classes\fonts >nul
jar --create --file casse-briques.jar --main-class Main -C build\classes .
echo OK : lancer avec  java -jar casse-briques.jar
