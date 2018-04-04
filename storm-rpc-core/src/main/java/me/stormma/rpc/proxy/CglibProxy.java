package me.stormma.rpc.proxy;

import com.google.common.base.Strings;
import me.stormma.exception.HandleRpcRequestException;
import me.stormma.exception.NotFoundRegistryException;
import me.stormma.exception.NotFoundServiceInRegistryException;
import me.stormma.rpc.model.Request;
import me.stormma.rpc.model.Response;
import me.stormma.rpc.model.builder.RequestBuilder;
import me.stormma.rpc.netty.bootstrap.Client;
import me.stormma.rpc.netty.bootstrap.RpcClient;
import me.stormma.rpc.registry.ServiceDiscover;
import me.stormma.rpc.utils.ServiceNameUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class CglibProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(CglibProxy.class);

    private final ServiceDiscover serviceDiscover;

    public CglibProxy(ServiceDiscover serviceDiscover) {
        this.serviceDiscover = serviceDiscover;
    }

    public <T> T createProxy(Class<?> interfaceClass, String version) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(interfaceClass);
        enhancer.setCallback(new Interceptor(serviceDiscover, interfaceClass, version));
        return (T) enhancer.create();
    }

    public <T> T createProxy(Class<?> interfaceClass) {
        return createProxy(interfaceClass, null);
    }

    static class Interceptor implements MethodInterceptor {
        private final ServiceDiscover serviceDiscover;

        private final Class<?> interfaceClass;

        private final String version;

        private Interceptor(ServiceDiscover serviceDiscover, Class<?> interfaceClass, String version) {
            this.serviceDiscover = serviceDiscover;
            this.interfaceClass = interfaceClass;
            this.version = version;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            RequestBuilder builder = new RequestBuilder();
            builder.requestId(UUID.randomUUID().toString())
                    .interfaceName(interfaceClass.getName())
                    .methodName(method.getName())
                    .startTime(System.currentTimeMillis())
                    .paramTypes(method.getParameterTypes())
                    .paramValves(objects);
            if (!Strings.isNullOrEmpty(version)) {
                builder.version(version);
            }
            Request request = builder.build();
            if (!Objects.isNull(serviceDiscover)) {
                String serviceName = ServiceNameUtils.getServiceName(interfaceClass, version);
                String serverAddress = serviceDiscover.discover(serviceName);
                if (Strings.isNullOrEmpty(serverAddress)) {
                    LOGGER.error(String.format("not found service <%s> in registry", serviceName));
                    throw new NotFoundServiceInRegistryException(String
                            .format("not found service <%s> in registry", serviceName));
                }
                String host = serverAddress.split(":")[0];
                int port = Integer.parseInt(serverAddress.split(":")[1]);
                Client rpcClient = new RpcClient(host, port);
                Response response = rpcClient.sendRequest(request);
                if (Objects.isNull(response)) {
                    throw new HandleRpcRequestException(String.format("handle request <%s> failed!", request));
                }
                response.setRequestTime(System.currentTimeMillis() - request.getStartTime());
                return response.getResult();
            }
            LOGGER.error("not found zookeeper registry!");
            throw new NotFoundRegistryException("not found zookeeper registry!");
        }
    }
}
