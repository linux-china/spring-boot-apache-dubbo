Spring Boot With Apache Dubbo
=============================
主要介绍如何在Spring Boot中整合Apache Dubbo的使用，Starter详细介绍在这里：

# Apache Dubbo是什么
Apache Dubbo是Alibaba将Dubbo捐献给Apache的开源产品，详细介绍 http://dubbo.incubator.apache.org/#!/?lang=zh-cn


# Spring Boot Starter特性

* Dubbo Service发布： https://github.com/linux-china/spring-boot-apache-dubbo/blob/master/spring-boot-dubbo-server/src/main/java/org/mvnsearch/uic/UicTemplateImpl.java
* Dubbo Service消费: https://github.com/linux-china/spring-boot-apache-dubbo/blob/master/spring-boot-starter-demo-uic/src/main/java/org/mvnsearch/uic/boot/UicDemoAutoconfiguration.java
* Metrics支持: 目前主要是counter和timer: 请参考 http://micrometer.io/
* Endpoint支持:  /actuator/dubbo， 输出Dubbo相关的配置

# 注册中心-Registry
目前主要是支持Simple Registry(Java), Redis, ZooKeeper和Consul这三个注册中心，主要是这两个服务非常普遍，同时由于Docker的流行，启动这三者个服务也非常简单。

### Redis
使用Redis注册中心，需要将在pom.xml中添加对应的redis客户端，代码如下：

```xml
     <dependency>
         <groupId>redis.clients</groupId>
         <artifactId>jedis</artifactId>
     </dependency>
```

对应的配置项为： spring.dubbo.registry = redis://localhost:6379

###  ZooKeeper
使用ZooKeeper注册中心，需要在pom.xml中添加zookeeper需要的jar包，代码如下：

```xml
     <dependency>
         <groupId>org.apache.curator</groupId>
         <artifactId>curator-framework</artifactId>
         <version>2.12.0</version>
     </dependency>
     <dependency>
         <groupId>com.101tec</groupId>
         <artifactId>zkclient</artifactId>
         <version>0.11</version>
     </dependency>
```

对应的配置项为： spring.dubbo.registry = zookeeper://127.0.0.1:2181

多个zookeeper的配置项为: spring.dubbo.registry = zookeeper://192.168.0.2:2181,192.168.0.3:2181

# 如何测试

* 首先使用IntelliJ IDEA导入项目
* 调用docker-compose启动对应的注册中心: docker-compose up -d
* 启动 DubboRegistryServer
* 启动 SpringBootDubboServerApplication
* 启动 SpringBootDubboClientApplication
* 打开浏览器访问 http://localhost:2080


# Spring DevTools注意事项
由于Spring DevTools采用不一样的classloader的机制，所以会导致Dubbo Consumer Bean无法赋值到指定的@Component上，请使用以下规则：

在 src/main/resources/META-INF/spring-devtools.properties 在添加以下代码进行DevTools的classloader屏蔽：
```properties
restart.exclude.target-classes=/target/classes/
```
关于HotSpot的模式下，相关Java代码调整后理解生效，可以考虑： http://dcevm.github.io/

如果你的应用是纯Dubbo服务，没有涉及到Web页面，不建议你添加spring-devtools，如果添加了后，
可以通过以下配置项关闭livereload服务，这样可以保证不必要的live reload服务启动。
```properties
spring.devtools.livereload.enabled=false
```

# Kotlin support???

```
dubbo<UserService>("0.0.0");
dubbo<UserService>("127.0.0",20800,"0.0.0")
```

# References

* Hessian Chinese: https://github.com/cytle/blog/blob/master/source/_posts/Hessian-2-0%E5%BA%8F%E5%88%97%E5%8C%96%E5%8D%8F%E8%AE%AE%E8%A7%84%E8%8C%83.md
