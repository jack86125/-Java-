-- =============================================
-- 学生成绩管理系统 - 数据库创建脚本
-- =============================================

-- 删除数据库（如果存在）
DROP DATABASE IF EXISTS student_score_system;

-- 创建数据库
CREATE DATABASE student_score_system
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE student_score_system;

-- 设置时区
SET time_zone = '+8:00';
