# 学生成绩管理系统 - 后端项目

## 项目简介

基于 Spring Boot 3.2.0 + MyBatis-Plus 的学生成绩管理系统后端服务。

## 技术栈

- **Java**: 17+ (推荐使用Java 17或Java 21)
- **Spring Boot**: 3.2.0
- **数据库**: MySQL 8.0.43
- **ORM框架**: MyBatis-Plus 3.5.5
- **API文档**: Knife4j 4.4.0
- **JWT**: java-jwt 4.4.0
- **工具类**: Hutool 5.8.25, Lombok 1.18.30

## 重要提示：Java版本兼容性问题

### 问题说明
您的系统当前使用的是 **Java 24**，但Maven编译器插件与Java 24存在兼容性问题，导致编译失败。

### 解决方案（三选一）

#### 方案一：安装Java 17或Java 21（推荐）

1. 下载并安装Java 17 LTS或Java 21 LTS
   - Java 17: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
   - Java 21: https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html

2. 设置JAVA_HOME环境变量指向Java 17或21的安装目录

3. 重新运行Maven命令

#### 方案二：使用IDE运行（最简单）

1. 使用IntelliJ IDEA或Eclipse打开项目
2. IDE会自动处理Java版本兼容性问题
3. 直接运行主类 `StudentScoreSystemApplication`

#### 方案三：跳过编译，直接打包运行

```bash
cd student-score-system
mvn clean package -DskipTests spring-boot:run
```

## 项目结构

```
student-score-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── system/
│   │   │           ├── StudentScoreSystemApplication.java  # 主启动类
│   │   │           ├── controller/      # 控制器层
│   │   │           │   ├── LoginController.java
│   │   │           │   ├── StudentController.java
│   │   │           │   ├── TeacherController.java
│   │   │           │   ├── CourseController.java
│   │   │           │   ├── ScoreController.java
│   │   │           │   └── ClassController.java
│   │   │           ├── service/         # 服务层
│   │   │           │   ├── SysUserService.java
│   │   │           │   ├── StudentService.java
│   │   │           │   ├── TeacherService.java
│   │   │           │   ├── CourseService.java
│   │   │           │   ├── ScoreService.java
│   │   │           │   ├── ClassService.java
│   │   │           │   └── impl/        # 服务实现类
│   │   │           ├── mapper/          # 数据访问层
│   │   │           ├── entity/          # 实体类
│   │   │           ├── dto/             # 数据传输对象
│   │   │           ├── vo/              # 视图对象
│   │   │           ├── common/          # 公共类
│   │   │           ├── config/          # 配置类
│   │   │           ├── exception/       # 异常处理
│   │   │           ├── interceptor/     # 拦截器
│   │   │           └── utils/           # 工具类
│   │   └── resources/
│   │       ├── application.yml          # 配置文件
│   │       └── mapper/                  # MyBatis XML
│   └── test/                            # 测试代码
├── pom.xml                              # Maven配置
└── README.md                            # 项目说明
```

## 数据库配置

### 1. 确保MySQL服务已启动

### 2. 执行数据库初始化脚本

进入 `database` 目录，执行以下SQL脚本：

```bash
cd ../database
mysql -u root -p123456 --default-character-set=utf8mb4 < 01_create_database.sql
mysql -u root -p123456 --default-character-set=utf8mb4 < 02_create_tables.sql
mysql -u root -p123456 --default-character-set=utf8mb4 < 03_init_data.sql
```

或者双击运行 `init_database.bat` 批处理文件。

### 3. 修改数据库连接配置（如需要）

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/student_score_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: 123456  # 修改为您的MySQL密码
```

## 运行项目

### 方式一：使用Maven运行（需要Java 17或21）

```bash
cd student-score-system
mvn spring-boot:run
```

### 方式二：使用IDE运行（推荐）

1. 使用IntelliJ IDEA或Eclipse导入项目
2. 等待Maven依赖下载完成
3. 运行主类 `com.system.StudentScoreSystemApplication`

### 方式三：打包后运行

```bash
mvn clean package -DskipTests
java -jar target/student-score-system-1.0.0.jar
```

## 访问项目

### 应用地址
- 后端服务: http://localhost:8080/api

### API文档
- Knife4j文档: http://localhost:8080/api/doc.html

### 默认账号

| 角色 | 用户名 | 密码 | 说明 |
|------|--------|------|------|
| 管理员 | admin | 123456 | 系统管理员 |
| 教师 | teacher001 | 123456 | 张老师 |
| 教师 | teacher002 | 123456 | 李老师 |
| 教师 | teacher003 | 123456 | 王老师 |
| 学生 | student001 | 123456 | 张三 |
| 学生 | student002 | 123456 | 李四 |
| 学生 | student003 | 123456 | 王五 |
| 学生 | student004 | 123456 | 赵六 |
| 学生 | student005 | 123456 | 孙七 |

## API接口说明

### 登录接口
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

### 响应格式
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "username": "admin",
    "realName": "系统管理员",
    "role": "admin",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGc..."
  }
}
```

### 认证说明
除登录接口外，其他接口都需要在请求头中携带Token：

```
Authorization: Bearer {token}
```

## 主要功能模块

### 1. 用户管理
- 用户登录/登出
- 用户信息查询

### 2. 学生管理
- 学生列表查询（分页）
- 学生详情查询
- 添加学生（管理员）
- 修改学生信息（管理员）
- 删除学生（管理员）

### 3. 教师管理
- 教师列表查询（分页）
- 教师详情查询
- 添加教师（管理员）
- 修改教师信息（管理员）
- 删除教师（管理员）

### 4. 课程管理
- 课程列表查询（分页）
- 课程详情查询
- 添加课程（管理员）
- 修改课程信息（管理员）
- 删除课程（管理员）

### 5. 成绩管理
- 成绩列表查询（分页）
- 成绩详情查询
- 添加成绩（教师、管理员）
- 修改成绩（管理员、授课教师）
- 删除成绩（管理员）
- 按学生查询成绩
- 按课程查询成绩
- 成绩修改历史查询

### 6. 班级管理
- 班级列表查询（分页）
- 班级详情查询
- 添加班级（管理员）
- 修改班级信息（管理员）
- 删除班级（管理员）

## 开发说明

### 代码规范
- 使用Lombok简化代码
- 统一使用Result类封装响应结果
- 统一异常处理
- RESTful API设计规范

### 日志配置
日志级别可在 `application.yml` 中配置：

```yaml
logging:
  level:
    com.system: debug
```

### 跨域配置
已配置CORS，允许所有域名跨域访问。生产环境建议修改为指定域名。

## 常见问题

### 1. 编译失败：Java版本不兼容
**问题**: `Fatal error compiling: java.lang.ExceptionInInitializerError`

**解决**: 安装Java 17或Java 21，或使用IDE运行项目

### 2. 数据库连接失败
**问题**: `Communications link failure`

**解决**:
- 检查MySQL服务是否启动
- 检查数据库连接配置是否正确
- 检查防火墙设置

### 3. Lombok不生效
**问题**: 找不到getter/setter方法

**解决**:
- IDE需要安装Lombok插件
- IDEA: Settings -> Plugins -> 搜索Lombok
- Eclipse: 下载lombok.jar并运行安装

### 4. 端口被占用
**问题**: `Port 8080 was already in use`

**解决**:
- 修改 `application.yml` 中的端口号
- 或关闭占用8080端口的程序

## 下一步计划

- [ ] 完善权限控制
- [ ] 增加数据导入导出功能
- [ ] 增加成绩统计图表
- [ ] 增加消息通知功能
- [ ] 编写单元测试
- [ ] 性能优化

## 联系方式

如有问题，请联系系统管理员。
