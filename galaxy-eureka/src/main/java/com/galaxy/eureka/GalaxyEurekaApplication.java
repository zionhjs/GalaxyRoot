package com.galaxy.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author cjm
 * @date 2020年12月5日
 * 服务中心
 */
@EnableEurekaServer
@SpringBootApplication
public class GalaxyEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyEurekaApplication.class, args);
    }

}
