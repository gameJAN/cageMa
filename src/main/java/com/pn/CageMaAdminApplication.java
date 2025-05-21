package com.pn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//mapper接口扫描器，自动为mapper接口创建代理对象并加入对应的IOC容器
@MapperScan(basePackages = "com.pn.mapper")
@SpringBootApplication
public class CageMaAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(CageMaAdminApplication.class, args);
    }

}
