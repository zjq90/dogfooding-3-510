package com.oa.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.oa.backend.mapper")
public class OaBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(OaBackendApplication.class, args);
    }
}
