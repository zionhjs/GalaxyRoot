package com.galaxy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringCloudApplication
@EnableZuulProxy
//com.wanwan为包最大的路径,如果不加的话，其他的将无法注入，就是默认扫描自己的那个包下面的
@ComponentScan(basePackages = {"com.galaxy"})
public class GatewayGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayGatewayApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许cookies跨域
        config.addAllowedOrigin("*");// 允许向该服务器提交请求的URI，*表示全部允许。
        config.addAllowedHeader("*");// 允许访问的头信息,*表示全部
        config.setMaxAge(3600L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.addAllowedMethod("*");// 允许提交请求的方法，*表示全部允许，也可以单独设置GET、PUT等
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
