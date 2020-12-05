package com.galaxy.ucenter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.galaxy"})
public class GalaxyUcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyUcenterApplication.class, args);
    }

}
