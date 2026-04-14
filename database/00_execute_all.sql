-- =============================================
-- 学生成绩管理系统 - 完整执行脚本
-- 执行顺序：
-- 1. 创建数据库
-- 2. 创建数据表
-- 3. 初始化数据
-- =============================================

SOURCE 01_create_database.sql;
SOURCE 02_create_tables.sql;
SOURCE 03_init_data.sql;

-- 显示所有表
SHOW TABLES;

-- 验证数据
SELECT '=== 用户表数据 ===' AS '';
SELECT id, username, real_name, role, status FROM sys_user;

SELECT '=== 班级表数据 ===' AS '';
SELECT id, class_no, class_name, grade, major, student_count FROM class;

SELECT '=== 教师表数据 ===' AS '';
SELECT id, teacher_no, name, gender, department FROM teacher;

SELECT '=== 学生表数据 ===' AS '';
SELECT id, student_no, name, gender, class_id FROM student;

SELECT '=== 课程表数据 ===' AS '';
SELECT id, course_no, course_name, credit, hours, teacher_id FROM course;

SELECT '=== 成绩表数据 ===' AS '';
SELECT COUNT(*) AS total_scores FROM score;

SELECT '=== 数据库初始化完成 ===' AS '';
