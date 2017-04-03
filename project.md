# com.dataagg:DAFramework:0.1
DAFramework

## com.dataagg:commons:0.1
公用实体类,VO及常用工具类

+ compile 'org.springframework.security:spring-security-core:4.2.2.RELEASE'
+ compile 'com.google.code.gson:gson:2.8.0'
+ compile 'org.nutz:nutz:1.r.60'
+ compile 'mysql:mysql-connector-java:5.1.41'

## com.dataagg:service-center:0.1
服务注册及发现

+ testCompile 'org.springframework.boot:spring-boot-starter-test'
+ compile 'org.springframework.cloud:spring-cloud-netflix-eureka-server'

## com.dataagg:api-gateway:0.1
API网关

+ testCompile 'org.springframework.boot:spring-boot-starter-test'
+ compile 'org.springframework.cloud:spring-cloud-netflix-eureka-client'
+ compile 'org.springframework.cloud:spring-cloud-starter-zuul'
+ compile 'org.springframework.boot:spring-boot-starter-security'
+ compile 'org.springframework.security.oauth:spring-security-oauth2'
+ compile 'org.springframework.cloud:spring-cloud-security:1.1.3.RELEASE'

## com.dataagg:security:0.1
权限检查服务

+ testCompile 'org.springframework.boot:spring-boot-starter-test'
+ compile 'org.springframework.boot:spring-boot-starter-security'
+ compile 'org.springframework.security.oauth:spring-security-oauth2'
+ compile 'org.springframework.cloud:spring-cloud-starter-feign'
+ commons
+ compile 'org.springframework.boot:spring-boot-starter-web'
+ compile 'mysql:mysql-connector-java:5.1.41'
+ compile 'org.nutz:nutz:1.r.60'
+ compile 'org.springframework.boot:spring-boot-starter-jdbc'

## com.dataagg:account:0.1
账户服务

+ testCompile 'org.springframework.boot:spring-boot-starter-test'
+ compile 'org.springframework.boot:spring-boot-starter-security'
+ compile 'org.springframework.security.oauth:spring-security-oauth2'
+ compile 'org.springframework.cloud:spring-cloud-security:1.1.3.RELEASE'
+ compile 'org.springframework.cloud:spring-cloud-starter-feign'
+ commons
+ compile 'org.springframework.boot:spring-boot-starter-web'
+ compile 'mysql:mysql-connector-java:5.1.41'
+ compile 'org.nutz:nutz:1.r.60'
+ compile 'org.springframework.boot:spring-boot-starter-jdbc'

# 项目依赖
```graphLR
commons[commons]
service-center[service-center]
api-gateway[api-gateway]
security[security]
security-->commons[commons]
account[account]
account-->commons[commons]
```
