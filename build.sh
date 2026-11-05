#!/usr/bin/env sh
# Compile le jeu et produit casse-briques.jar (JDK 17+ requis)
set -e
rm -rf build && mkdir -p build/classes
find src -name '*.java' > build/sources.txt
javac -encoding UTF-8 --release 17 -d build/classes @build/sources.txt
cp -r src/fonts build/classes/
jar --create --file casse-briques.jar --main-class Main -C build/classes .
echo "OK : lancer avec  java -jar casse-briques.jar"
