# DAFramework --  云南数聚基础框架
DAFramework是云南数聚科技开源的微服务基础框架, 基于Spring-boot, Spring-security, Spring-cloud， Spring-data和Docker构建. 这是一个Spring-cloud的脚手架项目, 提供一些基础服务, 帮助更多的开发人员更快速地构建他们的项目. DAFramework源于[PiggyMetrics](https://github.com/sqshq/PiggyMetrics)项目.

![基础组件](https://watano.gitbooks.io/daframework/content/images/componets.png "基础组件")

## 为何选择DAFramework
---
+ 基于Spring-boot, Spring-MVC, Spring-security和Spring-cloud构建, 减少用户的学习成本;
+ 基于Docker构建, 帮助开发人员快速实施DevOps;
+ 完整的 [微服务体系结构模式(Microservice Architecture Pattern)](http://martinfowler.com/microservices/)实现, 更先进的架构设计;
+ 模块化设计，层次结构清晰, 封装重用业务组件, 减少二次开发; 包含服务注册发现、配置中心、智能路由、负载均衡、性能监控、缓存、用户角色权限控制等；
+ 完整的代码生成工具，帮助快速开发新功能，减少重复coding工作；
+ 后端输出纯JSON（可配置为其他格式：XML，protobuf等），方便前后端分离;

## 基础组件
---
+ 配置中心: Spring-cloud config server和client
+ 服务注册发现: Spring-cloud NetFlix Eureka
+ 熔断器: Spring-cloud NetFlix Hystrix客户端和Hystrix Dashboard
+ 客户端负载均衡：Spring-cloud NetFlix Ribbon
+ 智能路由: Spring-cloud NetFlix Zuul
+ REST client: Spring-cloud NetFlix Feign
+ 监控系统: Spring-cloud NetFlix Turbine
+ 权限检查: Spring-cloud Security
+ 消息队列代理: Spring-cloud Bus
+ 数据持久化访问: Spring-Data和[NutDao](https://nutzam.com/core/dao/basic_operations.html)
+ 前端技术: ES2015/TypeScript、LESS、VueJs、Vue-router、[vux](https://github.com/airyland/vux)、echarts、axios和EsLint
+ 前端组件：[Element](https://element.eleme.io)、[Mint-UI](https://github.com/ElemeFE/mint-ui)和Bootstrap4 grid.css
+ 构建工具: [Gradle](https://gradle.org/)和[Cooking](http://cookingjs.github.io/)
+ 容器技术: [Docker](http://docker.com/)

## 环境搭建
---
### 后端部分
1. 安装gradle, 去[官网](https://gradle.org/gradle-download/)下载v3.x版本,并安装;
2. 在命令行中进入DAFramework目录, 执行以下命令编译打包:
```shell
cd DAFramework
gradle bootrepackage
```
3. 进入codegen目录, 执行以下命令启动Docker容器:
```shell
cd codegen
docker-compose up
```
4. 在浏览器中访问系统:
- http://DOCKER-HOST:80 - 通过API Gateway访问系统功能
- http://DOCKER-HOST:8761 - 访问Eureka Dashboard
- http://DOCKER-HOST:9000/hystrix - 访问Hystrix Dashboard
- http://DOCKER-HOST:8989 - 访问监控系统Turbine
- http://DOCKER-HOST:15672 - RabbitMq management (默认登陆名/密码: guest/guest)

### 前端部分
1. 安装nodejs, 去[官网](https://nodejs.org)下载v7.x版本NodeJs,并安装;
1. 在命令行下安装cnpm:
```shell
npm install -g cnpm --registry=https://registry.npm.taobao.org
```
1. 进入web目录,安装相关全局工具和相关依赖:
```shell
cd web
cnpm install -g vue-cli webpack eslint gulp cooking-cli
cnpm install
```
1. 运行dev开发模式
```shell
npm run dev
```
1. 运行lint检查代码文件(注:dev模式自动检查,build是也检查,lint一般只是快速检查时使用)
```shell
npm run lint
```
1. 运行build工具编译生成静态文件(发布时使用)
```shell
npm run build
```

## 关于我们
+ [官网](https://dataagg.github.io/)
+ [团队博客](https://dataagg.github.io/)
+ [文档](https://watano.gitbooks.io/daframework/content/)
