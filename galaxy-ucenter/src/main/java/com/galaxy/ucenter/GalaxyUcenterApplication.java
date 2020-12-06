package com.galaxy.ucenter;


import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.galaxy"})
@MapperScan("com.galaxy.ucenter.module.mapper")
public class GalaxyUcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyUcenterApplication.class, args);
    }

}
