package me.stormma.rpc.spring.boot;

import me.stormma.rpc.model.ServerInfo;
import me.stormma.rpc.netty.bootstrap.RpcServer;
import me.stormma.rpc.registry.ServiceRegistry;
import me.stormma.rpc.spring.boot.annotation.EnableStormRpcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Configuration
@ConditionalOnClass(EnableStormRpcConfiguration.class)
@EnableConfigurationProperties(StormRpcProperties.class)
@AutoConfigureAfter(StormRpcZookeeperRegistryAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spring.storm.rpc", name = "server", havingValue = "true")
public class StormRpcServerAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(StormRpcServerAutoConfiguration.class);

    @Autowired
    private ServiceRegistry serviceRegistry;

    @Autowired
    private StormRpcProperties stormRpcProperties;

    @Bean
    @ConditionalOnMissingBean
    public RpcServer rpcServer() {
        ServerInfo serverInfo = new ServerInfo(stormRpcProperties.getHost(), stormRpcProperties.getPort()
                        , stormRpcProperties.getWeight());
        RpcServer rpcServer = new RpcServer(serviceRegistry, serverInfo);
        Thread rpcServerThread = new Thread(() -> {
            try {
                rpcServer.start(stormRpcProperties.getBasePackage());
            } catch (InterruptedException e) {
                e.printStackTrace();
                LOGGER.error("storm-rpc-server start failed!");
                System.exit(0);
            }
        }, "storm-rpc-server");
        rpcServerThread.start();
        return rpcServer;
    }
}
