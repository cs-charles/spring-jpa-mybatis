package com.orange.spring.jpabatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(value = {"com.orange.spring.jpabatis.mapper"})
public class SpringJpabatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpabatisApplication.class, args);
    }

}
