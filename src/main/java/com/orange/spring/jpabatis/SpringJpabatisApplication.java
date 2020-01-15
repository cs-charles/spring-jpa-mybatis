package com.orange.spring.jpabatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.orange.spring.jpabatis.mapper"})
public class SpringJpabatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpabatisApplication.class, args);
    }

}
