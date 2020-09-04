package com.baizhi.lmm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.baizhi.lmm.dao")
@SpringBootApplication
public class YingxLmmApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxLmmApplication.class, args);
    }
}
