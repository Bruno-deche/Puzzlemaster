@echo off
echo 📦 Compilazione del file Java...
cd /d %~dp0\src\test\java

javac test\DBConnectionTest.java
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Errore durante la compilazione. Verifica il codice.
    pause
    exit /b
)

echo 🚀 Avvio connessione al database...
java -cp ".;C:\Users\bipbo\.m2\repository\com\mysql\mysql-connector-j\8.0.33\mysql-connector-j-8.0.33.jar" test.DBConnectionTest

echo ✅ Test completato.
pause
