# 数据库初始化说明

## 执行方式一：使用MySQL命令行（推荐）

### 步骤1：打开MySQL命令行
在Windows开始菜单中找到 "MySQL 8.0 Command Line Client" 并打开

### 步骤2：输入root密码
输入您的MySQL root密码

### 步骤3：执行SQL脚本
依次执行以下命令：

```sql
SOURCE C:/Users/wende/Desktop/基于spring boot+vue的学生成绩管理系统/database/01_create_database.sql;
SOURCE C:/Users/wende/Desktop/基于spring boot+vue的学生成绩管理系统/database/02_create_tables.sql;
SOURCE C:/Users/wende/Desktop/基于spring boot+vue的学生成绩管理系统/database/03_init_data.sql;
```

### 步骤4：验证数据
```sql
USE student_score_system;
SHOW TABLES;
SELECT COUNT(*) FROM sys_user;
SELECT COUNT(*) FROM student;
SELECT COUNT(*) FROM teacher;
SELECT COUNT(*) FROM course;
SELECT COUNT(*) FROM score;
```

## 执行方式二：使用MySQL Workbench

1. 打开MySQL Workbench
2. 连接到本地MySQL服务器
3. 依次打开并执行以下SQL文件：
   - `01_create_database.sql`
   - `02_create_tables.sql`
   - `03_init_data.sql`

## 执行方式三：使用命令行（一次性执行）

打开Windows命令提示符（CMD），执行：

```bash
cd "C:\Users\wende\Desktop\基于spring boot+vue的学生成绩管理系统\database"
mysql -u root -p < 01_create_database.sql
mysql -u root -p < 02_create_tables.sql
mysql -u root -p < 03_init_data.sql
```

每次执行都需要输入MySQL root密码。

## 数据库信息

- **数据库名称**: `student_score_system`
- **字符集**: `utf8mb4`
- **排序规则**: `utf8mb4_unicode_ci`

## 初始化数据说明

### 默认账号密码（所有密码都是：123456）

#### 管理员账号
- 用户名: `admin`
- 密码: `123456`
- 角色: 管理员

#### 教师账号
- 用户名: `teacher001` / 密码: `123456` / 姓名: 张老师
- 用户名: `teacher002` / 密码: `123456` / 姓名: 李老师
- 用户名: `teacher003` / 密码: `123456` / 姓名: 王老师

#### 学生账号
- 用户名: `student001` / 密码: `123456` / 姓名: 张三
- 用户名: `student002` / 密码: `123456` / 姓名: 李四
- 用户名: `student003` / 密码: `123456` / 姓名: 王五
- 用户名: `student004` / 密码: `123456` / 姓名: 赵六
- 用户名: `student005` / 密码: `123456` / 姓名: 孙七

### 初始化数据包括

- ✅ 1个管理员用户
- ✅ 3个教师用户
- ✅ 5个学生用户
- ✅ 4个班级
- ✅ 3个教师信息
- ✅ 5个学生信息
- ✅ 5门课程
- ✅ 20条成绩记录（示例数据）

## 数据表说明

| 表名 | 说明 | 记录数 |
|------|------|--------|
| sys_user | 系统用户表 | 9 |
| class | 班级表 | 4 |
| student | 学生表 | 5 |
| teacher | 教师表 | 3 |
| course | 课程表 | 5 |
| score | 成绩表 | 20 |
| score_history | 成绩修改历史表 | 0 |
| operation_log | 操作日志表 | 0 |

## 注意事项

1. 执行脚本前请确保MySQL服务已启动
2. 如果数据库已存在，脚本会先删除再重新创建
3. 密码使用BCrypt加密存储
4. 所有时间字段使用东八区时区（+8:00）
