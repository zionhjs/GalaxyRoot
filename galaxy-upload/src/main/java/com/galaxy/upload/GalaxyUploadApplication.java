package com.galaxy.upload;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//开启DiscoveryClient的功能
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.galaxy"})
@MapperScan("com.galaxy.upload.module.mapper")
@EnableScheduling     //开启定时器
@ServletComponentScan //该注解可以自动注入@WebServlet，@WebFilter，@WebListener。否则在过滤器中无法获取到自动注入得bean
public class GalaxyUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyUploadApplication.class, args);
    }

}
