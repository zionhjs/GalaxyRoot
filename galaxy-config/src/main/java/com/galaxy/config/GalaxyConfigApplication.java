package com.galaxy.config;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableConfigServer //声明这是一个config-server
@SpringCloudApplication
public class GalaxyConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyConfigApplication.class, args);
    }
}
