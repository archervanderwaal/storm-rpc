package me.stormma.storm.rpc.spring.boot.starter.test;

import me.stormma.rpc.spring.boot.annotation.EnableStormRpcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableStormRpcConfiguration
public class StormRpcSpringBootStarterTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(StormRpcSpringBootStarterTestApplication.class, args);
	}
}
