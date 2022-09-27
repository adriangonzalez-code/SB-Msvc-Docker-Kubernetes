package com.driagon.springcloud.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MscvCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MscvCursosApplication.class, args);
    }

}
