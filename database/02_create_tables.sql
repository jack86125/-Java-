-- =============================================
-- 学生成绩管理系统 - 数据表创建脚本
-- =============================================

USE student_score_system;

-- =============================================
-- 1. 用户表 (sys_user)
-- =============================================
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码（加密存储）',
    real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    role VARCHAR(20) NOT NULL COMMENT '角色：admin-管理员, teacher-教师, student-学生',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用, 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (username),
    INDEX idx_role (role),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- =============================================
-- 2. 班级表 (class)
-- =============================================
DROP TABLE IF EXISTS class;
CREATE TABLE class (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    class_no VARCHAR(50) NOT NULL UNIQUE COMMENT '班级编号',
    class_name VARCHAR(100) NOT NULL COMMENT '班级名称',
    grade VARCHAR(20) NOT NULL COMMENT '年级',
    major VARCHAR(100) COMMENT '专业',
    student_count INT DEFAULT 0 COMMENT '学生人数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_class_no (class_no),
    INDEX idx_grade (grade)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='班级表';

-- =============================================
-- 3. 学生表 (student)
-- =============================================
DROP TABLE IF EXISTS student;
CREATE TABLE student (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    student_no VARCHAR(50) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender VARCHAR(10) COMMENT '性别：男/女',
    birth_date DATE COMMENT '出生日期',
    class_id BIGINT COMMENT '班级ID',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    address VARCHAR(255) COMMENT '地址',
    user_id BIGINT NOT NULL COMMENT '关联用户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_student_no (student_no),
    INDEX idx_name (name),
    INDEX idx_class_id (class_id),
    INDEX idx_user_id (user_id),
    FOREIGN KEY (class_id) REFERENCES class(id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生表';

-- =============================================
-- 4. 教师表 (teacher)
-- =============================================
DROP TABLE IF EXISTS teacher;
CREATE TABLE teacher (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    teacher_no VARCHAR(50) NOT NULL UNIQUE COMMENT '工号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender VARCHAR(10) COMMENT '性别：男/女',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    department VARCHAR(100) COMMENT '所属院系',
    user_id BIGINT NOT NULL COMMENT '关联用户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_teacher_no (teacher_no),
    INDEX idx_name (name),
    INDEX idx_department (department),
    INDEX idx_user_id (user_id),
    FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='教师表';

-- =============================================
-- 5. 课程表 (course)
-- =============================================
DROP TABLE IF EXISTS course;
CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    course_no VARCHAR(50) NOT NULL UNIQUE COMMENT '课程编号',
    course_name VARCHAR(100) NOT NULL COMMENT '课程名称',
    credit DECIMAL(3,1) COMMENT '学分',
    hours INT COMMENT '学时',
    teacher_id BIGINT COMMENT '授课教师ID',
    description TEXT COMMENT '课程描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_course_no (course_no),
    INDEX idx_course_name (course_name),
    INDEX idx_teacher_id (teacher_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程表';

-- =============================================
-- 6. 成绩表 (score)
-- =============================================
DROP TABLE IF EXISTS score;
CREATE TABLE score (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    score DECIMAL(5,2) COMMENT '成绩',
    exam_type VARCHAR(20) COMMENT '考试类型：平时/期中/期末',
    exam_date DATE COMMENT '考试日期',
    semester VARCHAR(20) COMMENT '学期，如：2024-2025-1',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_student_id (student_id),
    INDEX idx_course_id (course_id),
    INDEX idx_semester (semester),
    INDEX idx_exam_type (exam_type),
    UNIQUE KEY uk_student_course_exam (student_id, course_id, exam_type, semester),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='成绩表';

-- =============================================
-- 7. 成绩修改历史表 (score_history)
-- =============================================
DROP TABLE IF EXISTS score_history;
CREATE TABLE score_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    score_id BIGINT NOT NULL COMMENT '成绩ID',
    old_score DECIMAL(5,2) COMMENT '原成绩',
    new_score DECIMAL(5,2) COMMENT '新成绩',
    operator_id BIGINT NOT NULL COMMENT '操作人ID',
    operator_name VARCHAR(50) COMMENT '操作人姓名',
    reason VARCHAR(255) COMMENT '修改原因',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    INDEX idx_score_id (score_id),
    INDEX idx_operator_id (operator_id),
    FOREIGN KEY (score_id) REFERENCES score(id) ON DELETE CASCADE,
    FOREIGN KEY (operator_id) REFERENCES sys_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='成绩修改历史表';

-- =============================================
-- 8. 操作日志表 (operation_log)
-- =============================================
DROP TABLE IF EXISTS operation_log;
CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT COMMENT '操作用户ID',
    username VARCHAR(50) COMMENT '操作用户名',
    operation VARCHAR(50) COMMENT '操作类型',
    method VARCHAR(200) COMMENT '请求方法',
    params TEXT COMMENT '请求参数',
    ip VARCHAR(50) COMMENT 'IP地址',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    INDEX idx_user_id (user_id),
    INDEX idx_operation (operation),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';
