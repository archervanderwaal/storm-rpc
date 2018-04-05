## storm-rpc

### 快速开始(使用Spring Boot)

#### Api
接口`IHelloService.java`

```java
package me.stormma.api;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface IHelloService {
    String sayHello(String name);
}
```

服务接口`pom`依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>storm-rpc-spring-boot-starter-test-api</artifactId>
</project>
```

#### Provider
服务提供者实现`HelloService.java`

```java
package me.stormma.storm.rpc.spring.boot.starter.test.provider.provider;

import me.stormma.annoation.Provider;
import me.stormma.api.IHelloService;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Provider(interfaceClass = IHelloService.class, version = "2.0.0")
public class HelloService implements IHelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name + "!";
    }
}
```

服务提供者`pom`依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>me.stormma</groupId>
	<artifactId>storm-rpc-spring-boot-starter-test-provider</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>storm-rpc-spring-boot-starter-test-provider</name>
	<description>storm-rpc-spring-boot-starter-test-provider</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.0.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>me.stormma</groupId>
			<artifactId>storm-rpc-spring-boot-starter</artifactId>
			<version>1.0.0</version>
		</dependency>
        <dependency>
            <groupId>me.stormma</groupId>
            <artifactId>storm-rpc-spring-boot-starter-test-api</artifactId>
            <version>1.0.0</version>
        </dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
```

服务提供者启动类`StormRpcSpringBootStarterTestProviderApplication.java`

```java
package me.stormma.storm.rpc.spring.boot.starter.test.provider;

import me.stormma.rpc.spring.boot.annotation.EnableStormRpcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableStormRpcConfiguration
public class StormRpcSpringBootStarterTestProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(StormRpcSpringBootStarterTestProviderApplication.class, args);
	}
}
```

服务提供者配置文件`application.properties`
```properties
spring.storm.rpc.server=true
spring.storm.rpc.host=127.0.0.1
spring.storm.rpc.port=52057
spring.storm.rpc.weight=3
spring.storm.rpc.registry=139.199.27.243:2181
spring.storm.rpc.basePackage=me.stormma.storm.rpc.spring.boot.starter.test.provider
spring.storm.rpc.name=provider
```
#### Consumer

服务消费者测试类`HelloServiceTest.java`
```java
package me.stormma.storm.rpc.spring.boot.starter.test.consumer.consumer;

import me.stormma.api.IHelloService;
import me.stormma.rpc.spring.boot.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stormma stormmaybin@gmail.com
 */
@RestController
public class HelloServiceTest {

    @Reference(interfaceClass = IHelloService.class, version = "2.0.0")
    private IHelloService helloService;

    public int a = 0;

    @GetMapping("/say/hello/{name}")
    public String test(@PathVariable String name) {
        return helloService.sayHello(name);
    }
}
```

服务消费者`pom`依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>me.stormma</groupId>
	<artifactId>storm-rpc-spring-boot-starter-test-consumer</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>storm-rpc-spring-boot-starter-test-consumer</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.0.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>me.stormma</groupId>
			<artifactId>storm-rpc-spring-boot-starter-test-api</artifactId>
			<version>1.0.0</version>
		</dependency>

        <dependency>
            <groupId>me.stormma</groupId>
            <artifactId>storm-rpc-spring-boot-starter</artifactId>
			<version>1.0.0</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
```

服务消费者启动类`StormRpcSpringBootStarterTestConsumerApplication.java`

```java
package me.stormma.storm.rpc.spring.boot.starter.test.consumer;

import me.stormma.rpc.spring.boot.annotation.EnableStormRpcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableStormRpcConfiguration
public class StormRpcSpringBootStarterTestConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StormRpcSpringBootStarterTestConsumerApplication.class, args);
	}
}
```

服务消费者配置文件`application.properties`

```properties
spring.storm.rpc.discover=139.199.27.243:2181
spring.storm.rpc.name=consumer
server.port=8090
```
### 快速开始(原生)

#### Api

服务接口`IHelloService.java`

```java
package me.stormma.api;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface IHelloService {

    String sayHello(String name);
}
```

#### Provider
服务提供者实现`HelloService.java`

```java
package me.stormma.provider;

import me.stormma.annoation.Provider;
import me.stormma.api.IHelloService;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Provider(interfaceClass = IHelloService.class, version = "1.0.0")
public class HelloService implements IHelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
```

服务提供者启动类`ServerBootstrap.java`

```java
package me.stormma.provider;

import me.stormma.constants.Constants;
import me.stormma.rpc.model.ServerInfo;
import me.stormma.rpc.netty.bootstrap.RpcServer;
import me.stormma.rpc.registry.ServiceRegistry;
import me.stormma.rpc.registry.zk.ZookeeperServiceRegistry;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ServerBootstrap {
    public static void main(String... args) throws Exception {
        String zkServer = "139.199.27.243:2181";
        ServiceRegistry serviceRegistry = new ZookeeperServiceRegistry(zkServer);
        ServerInfo serverInfo = new ServerInfo(Constants.DEFAULT_HOST, Constants.DEFAULT_PORT
                , Constants.SERVER_DEFAULT_WEIGHT);
        RpcServer rpcServer = new RpcServer(serviceRegistry, serverInfo);
        rpcServer.start("me.stormma.provider");
    }
}
```

#### Consumer
服务消费者实现类`ConsumerClient.java`

```java
package me.stormma.consumer;

import me.stormma.api.IHelloService;
import me.stormma.rpc.proxy.DefaultProxy;
import me.stormma.rpc.registry.ServiceDiscover;
import me.stormma.rpc.registry.zk.ZookeeperServiceDiscover;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ConsumerClient {
    public static void main(String[] args) {
        String zkServer = "139.199.27.243:2181";
        ServiceDiscover serviceDiscover = new ZookeeperServiceDiscover(zkServer);
        DefaultProxy proxy = new DefaultProxy(serviceDiscover);
        IHelloService helloService = proxy.createProxy(IHelloService.class, "1.0.0");
        System.out.println(helloService.sayHello("stormma"));
        System.exit(0);
    }
}
```

### storm-rpc技术依赖

1. zookeeper, 使用zookeeper进行服务注册与发现(服务名称作为服务提供者启动之后创建zk持久性节点保存, 服务对应的地址创建为临时性节点的内容, 实现动态
发现服务宕机和上线, 临时性节点表示为'host:port$weight', host:port代表服务地址, weight表示为该服务地址的权重, 用来实现负载均衡, 负载均衡算法参考
dubbo的负载均衡算法, 基于权重的负载均衡算法)

2. netty, 使用netty进行服务之间的网络通信, netty的使用参考<<Netty in action>>这本书。

3. kryo, 使用kryo进行序列化和反序列化。

4. 动态代理(本项目提供了原生JDK动态代理和CGLIB来实现的动态代理, 用户可以自行配置使用哪种代理方式), 具体工作流程如: 重写BeanPostProcess来实现
解析Bean的字段是否包含`Reference`注解, 如果包含就创建个代理对象注入。

### storm-rpc功能点

1. 服务注册和发现
2. 负载均衡
3. 集成spring boot
4. 远程调用
5. 断线重连(后续增加)


### 后记
storm-rpc从开始开发到一个版本的推出前前后后经历了一周多, 实现借鉴了dubbo的很多代码, 期待你的加入能让storm-rpc走向完善。


