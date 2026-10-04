@echo off
javac -cp "lib\*;target\classes" TestDB.java
java -cp "lib\*;target\classes;." TestDB
