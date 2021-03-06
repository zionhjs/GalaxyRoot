#### 模块说明
```
galaxy 
├── galaxy-eureka -- 注册中心[9000]
├── galaxy-eureka -- 配置中心[9200]
└── galaxy-common -- 系统公共模块 
     ├── galaxy-common-core -- 公共工具类核心包
├── galaxy-gateway -- Spring Cloud Gateway网关[9400]	 
├── galaxy-ucenter -- 用户中心[9600]
├── galaxy-uoload -- 上传中心[9800]
├── galaxy-cms  -- 内容中心[9900]
├── galaxy-jms  -- 消息中心[9500]
```


#### 项目说明
1. 端口说明
```bash
9000   注册中心
9200   配置中心 
9400   网关中心
9600   用户中心
9800   上传中心
9900   内容中心
9500   消息中心
```
2. 启动顺序
```bash
(1) GalaxyEurekaApplication  
(2) GalaxyConfigApplication  
(3) GalaxyGatewayApplication 
(4) GalaxyUcenterApplication 
(5) GalaxyUploadApplication 
(6) GalaxyCmsApplication 
(7) GalaxyJmsApplication 
(8) 启动完毕之后在本地浏览器访问 http://localhost:9000 
```

#### 核心依赖 
依赖 | 版本
---|---
Spring Boot |  2.1.3.RELEASE
Spring Cloud | Greenwich.RELEASE
hutool | 4.5.0
Mybatis Plus | 3.1.0 