package com.galaxy.upload;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//开启DiscoveryClient的功能
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.galaxy"})
@MapperScan("com.galaxy.upload.module.mapper")
public class GalaxyUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyUploadApplication.class, args);
    }

}
