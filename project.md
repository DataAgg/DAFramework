# com.dataagg:DAFramework:0.1
DAFramework

## com.dataagg:commons:0.1
公用实体类,VO及常用工具类

+ compile 'org.springframework.security:spring-security-core:4.2.1.RELEASE'
+ compile 'mysql:mysql-connector-java:5.1.9'
+ compile 'org.nutz:nutz:1.r.60'

## com.dataagg:service-center:0.1
Service serviceCenter and service discovery

+ compile 'org.springframework.cloud:spring-cloud-netflix-eureka-server'
+ compile 'org.springframework.cloud:spring-cloud-starter-feign'
+ testCompile 'org.springframework.boot:spring-boot-starter-test'

## com.dataagg:apigateway:0.1
api apigateway

+ compile 'org.springframework.cloud:spring-cloud-netflix-eureka-client'
+ compile 'org.springframework.cloud:spring-cloud-starter-zuul'
+ testCompile 'org.springframework.boot:spring-boot-starter-test'

## com.dataagg:security:0.1
auth service

+ commons
+ compile 'org.springframework.boot:spring-boot-starter-jdbc'
+ compile 'org.springframework.cloud:spring-cloud-netflix-eureka-client'
+ compile 'org.springframework.boot:spring-boot-starter-web'
+ compile 'org.springframework.boot:spring-boot-starter-security'
+ compile 'org.springframework.security.oauth:spring-security-oauth2'
+ compile 'org.springframework.cloud:spring-cloud-starter-feign'
+ testCompile 'org.springframework.boot:spring-boot-starter-test'

## com.dataagg:EAccount:0.1
EAccount service

+ commons
+ compile 'org.springframework.boot:spring-boot-starter-jdbc'
+ compile 'org.springframework.cloud:spring-cloud-netflix-eureka-client'
+ compile 'org.springframework.boot:spring-boot-starter-web'
+ compile 'org.springframework.boot:spring-boot-starter-security'
+ compile 'org.springframework.security.oauth:spring-security-oauth2'
+ compile 'org.springframework.cloud:spring-cloud-starter-feign'
+ compile 'org.springframework.cloud:spring-cloud-security:1.1.3.RELEASE'
+ testCompile 'org.springframework.boot:spring-boot-starter-test'

