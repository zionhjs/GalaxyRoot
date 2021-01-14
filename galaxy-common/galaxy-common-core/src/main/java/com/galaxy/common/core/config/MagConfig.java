package com.galaxy.common.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


/**
 * 获取项目公用得配置信息类
 */
@Component
//@ConfigurationProperties(prefix = "wantu")
@RefreshScope //该注解实现不重启服务也可以动态获取配置中心数据
public class MagConfig {

    @Value("${galaxy.notCheckLoginUrl}")
    private String notCheckLoginUrl;

	public String getNotCheckLoginUrl() {
		return notCheckLoginUrl;
	}

	public void setNotCheckLoginUrl(String notCheckLoginUrl) {
		this.notCheckLoginUrl = notCheckLoginUrl;
	}
}
