# com.dataagg:DAFramework:0.1
DAFramework

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

+ compile 'org.springframework.cloud:spring-cloud-netflix-eureka-client'
+ compile 'org.springframework.boot:spring-boot-starter-web'
+ compile 'org.springframework.boot:spring-boot-starter-security'
+ compile 'org.springframework.cloud:spring-cloud-starter-feign'
+ compile 'org.springframework.security.oauth:spring-security-oauth2'
+ compile 'org.mybatis.spring.boot:mybatis-spring-boot-starter:1.2.0'
+ compile 'mysql:mysql-connector-java:5.1.9'
+ compile 'org.nutz:nutz:1.r.60'
+ testCompile 'org.springframework.boot:spring-boot-starter-test'

## com.dataagg:account:0.1
account service

+ compile 'org.springframework.cloud:spring-cloud-netflix-eureka-client'
+ compile 'org.springframework.boot:spring-boot-starter-web'
+ compile 'org.springframework.boot:spring-boot-starter-security'
+ compile 'org.springframework.security.oauth:spring-security-oauth2'
+ compile 'org.springframework.cloud:spring-cloud-starter-feign'
+ compile 'org.springframework.cloud:spring-cloud-security:1.1.3.RELEASE'
+ compile 'org.mybatis.spring.boot:mybatis-spring-boot-starter:1.2.0'
+ compile 'mysql:mysql-connector-java:5.1.9'
+ compile 'com.baomidou:mybatis-plus:2.0.1'
+ testCompile 'org.springframework.boot:spring-boot-starter-test'

