@echo off
chcp 65001 >nul
echo =============================================
echo 学生成绩管理系统 - 数据库初始化脚本
echo =============================================
echo.

set MYSQL_PATH=D:\MySQL\MySQL Server 8.0\bin\mysql.exe
set DB_USER=root

echo 请输入MySQL root密码：
set /p DB_PASSWORD=

echo.
echo 正在创建数据库...
"%MYSQL_PATH%" -u %DB_USER% -p%DB_PASSWORD% < 01_create_database.sql
if %errorlevel% neq 0 (
    echo 创建数据库失败！
    pause
    exit /b 1
)
echo ✓ 数据库创建成功

echo.
echo 正在创建数据表...
"%MYSQL_PATH%" -u %DB_USER% -p%DB_PASSWORD% < 02_create_tables.sql
if %errorlevel% neq 0 (
    echo 创建数据表失败！
    pause
    exit /b 1
)
echo ✓ 数据表创建成功

echo.
echo 正在初始化数据...
"%MYSQL_PATH%" -u %DB_USER% -p%DB_PASSWORD% < 03_init_data.sql
if %errorlevel% neq 0 (
    echo 初始化数据失败！
    pause
    exit /b 1
)
echo ✓ 数据初始化成功

echo.
echo =============================================
echo 数据库初始化完成！
echo =============================================
echo.
echo 数据库名称: student_score_system
echo.
echo 默认账号密码（所有密码都是：123456）：
echo 管理员: admin / 123456
echo 教师: teacher001 / 123456
echo 学生: student001 / 123456
echo.
echo =============================================
pause
