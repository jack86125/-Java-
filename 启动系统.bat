@echo off
title Student Score Management System Launcher
color 0A

echo ========================================
echo    Student Score Management System
echo ========================================
echo.

echo [1/3] Starting frontend service...
cd /d "%~dp0student-score-web"
start "Frontend - Vue" cmd /k "npm run dev"
echo Frontend is starting...
echo.

echo [2/3] Please start backend manually in IntelliJ IDEA:
echo   1. Open student-score-system project in IDEA
echo   2. Run StudentScoreSystemApplication.java
echo   3. Wait for "Started StudentScoreSystemApplication" message
echo.

echo [3/3] Opening browser in 15 seconds...
timeout /t 15 >nul
start http://localhost:5173
echo.

echo ========================================
echo    System Started!
echo ========================================
echo.
echo Frontend: http://localhost:5173
echo Backend:  http://localhost:8080
echo API Docs: http://localhost:8080/doc.html
echo.
echo Default admin account:
echo   Username: admin
echo   Password: 123456
echo.
echo Note: Make sure backend is running in IDEA
echo.
pause
