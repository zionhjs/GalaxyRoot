package com.galaxy.ucenter;


import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//开启DiscoveryClient的功能
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.galaxy"})
@MapperScan("com.galaxy.ucenter.module.mapper")
public class GalaxyUcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyUcenterApplication.class, args);
    }

}
