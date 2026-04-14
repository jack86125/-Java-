package com.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 学生成绩管理系统 - 主启动类
 */
@SpringBootApplication
@MapperScan("com.system.mapper")
public class StudentScoreSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentScoreSystemApplication.class, args);
        System.out.println("========================================");
        System.out.println("学生成绩管理系统启动成功！");
        System.out.println("接口文档地址: http://localhost:8080/api/doc.html");
        System.out.println("========================================");
    }

}
