package me.stormma.rpc.netty.bootstrap;

import me.stormma.annoation.Provider;
import me.stormma.rpc.model.ServiceAddress;
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

    private Map<String, Object> providerBeans = new ConcurrentHashMap<>();

    private final ServiceRegistry serviceRegistry;

    private final ServiceAddress address;

    public RpcServer(ServiceRegistry serviceRegistry, ServiceAddress address) {
        this.serviceRegistry = serviceRegistry;
        this.address = address;
    }

    @Override
    public void start(String basePackage) {
        registerProviderBean2Map(basePackage);
    }

    @Override
    public void close() {

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
        String address = ServiceNameUtils.getServiceAddress();
    }
}
