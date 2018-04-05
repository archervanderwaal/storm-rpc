package me.stormma.rpc.spring.boot;

import me.stormma.rpc.registry.ServiceRegistry;
import me.stormma.rpc.registry.zk.ZookeeperServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Configuration
@EnableConfigurationProperties(StormRpcProperties.class)
public class StormRpcZookeeperRegistryAutoConfiguration {
    @Autowired
    private StormRpcProperties stormRpcProperties;

    @Bean
    @ConditionalOnProperty(prefix = "spring.storm.rpc", name = "server", havingValue = "true")
    public ServiceRegistry serviceRegistry() {
        return new ZookeeperServiceRegistry(stormRpcProperties.getRegistry());
    }
}
