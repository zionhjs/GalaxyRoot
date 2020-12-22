package com.galaxy.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

//开启DiscoveryClient的功能
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.galaxy"})
@MapperScan("com.galaxy.cms.module.mapper")
public class GalaxyCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyCmsApplication.class, args);
    }

}
