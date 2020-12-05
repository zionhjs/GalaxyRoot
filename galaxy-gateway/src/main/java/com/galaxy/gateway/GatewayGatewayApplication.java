package com.galaxy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@EnableZuulProxy
//com.wanwan为包最大的路径,如果不加的话，其他的将无法注入，就是默认扫描自己的那个包下面的
@ComponentScan(basePackages = {"com.galaxy"})
public class GatewayGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayGatewayApplication.class, args);
    }

}
