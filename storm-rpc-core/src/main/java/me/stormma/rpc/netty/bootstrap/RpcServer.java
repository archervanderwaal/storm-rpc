package me.stormma.rpc.netty.bootstrap;

import com.google.common.collect.Maps;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.stormma.annoation.Provider;
import me.stormma.rpc.model.ServerInfo;
import me.stormma.rpc.netty.codec.Decoder;
import me.stormma.rpc.netty.codec.Encoder;
import me.stormma.rpc.netty.handler.StormRpcServerHandler;
import me.stormma.rpc.registry.ServiceRegistry;
import me.stormma.rpc.utils.ServiceNameUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class RpcServer implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

    private Map<String, Object> providerBeans = Maps.newConcurrentMap();

    private final ServiceRegistry serviceRegistry;

    private final ServerInfo serverInfo;

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    public RpcServer(ServiceRegistry serviceRegistry, ServerInfo serverInfo) {
        this.serviceRegistry = serviceRegistry;
        this.serverInfo = serverInfo;
    }

    @Override
    public void start(String basePackage) throws InterruptedException {
        registerProviderBean2Map(basePackage);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new Decoder())
                                .addLast(new Encoder())
                                .addLast(new StormRpcServerHandler((ConcurrentHashMap<String, Object>) providerBeans));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = bootstrap.bind(serverInfo.getHost(), serverInfo.getPort()).sync();
        registerProviderService2Registry();
        LOGGER.debug("start server on port <{}>", serverInfo.getPort());
        future.channel().closeFuture().sync();
        close();
    }

    @Override
    public void close() {
        serviceRegistry.shutdown();
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    private void registerProviderBean2Map(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> providerClass = reflections.getTypesAnnotatedWith(Provider.class);
        for (Class<?> clazz : providerClass) {
            Provider provider = clazz.getAnnotation(Provider.class);
            Class<?> interfaceClass = provider.interfaceClass();
            String version = provider.version();
            String serviceName = ServiceNameUtils.getServiceName(interfaceClass, version);
            Object providerBean;
            try {
                 providerBean = clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            providerBeans.put(serviceName, providerBean);
        }
    }

    private void registerProviderService2Registry() {
        String serviceAddress = ServiceNameUtils.getServiceAddress(this.serverInfo);
        for (String serviceName : providerBeans.keySet()) {
            serviceRegistry.register(serviceName, serverInfo);
        }
    }
}
