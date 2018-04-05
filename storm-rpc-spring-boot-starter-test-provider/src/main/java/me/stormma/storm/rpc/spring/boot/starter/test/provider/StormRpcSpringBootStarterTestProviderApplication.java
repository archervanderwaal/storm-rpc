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
