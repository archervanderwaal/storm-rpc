package me.stormma.rpc.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.stormma.rpc.model.Request;
import me.stormma.rpc.model.Response;
import me.stormma.rpc.model.builder.ResponseBuilder;
import me.stormma.rpc.utils.ServiceNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author stormma stormmaybin@gmail.com
 */
@ChannelHandler.Sharable
public class StormRpcServerHandler extends SimpleChannelInboundHandler<Request> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StormRpcServerHandler.class);

    /**
     Get all the services of the project module, and save it as a map. The key of the map is the service name
     (if there is no version information, the service name is the interface name, if there is version information,
     the service name is the interface 'name.version'), the value of the map is the service implementation
     class instance object*/
    private final ConcurrentHashMap<String, Object> providersBean;

    public StormRpcServerHandler(ConcurrentHashMap<String, Object> providersBean) {
        this.providersBean = providersBean;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        String methodName = request.getMethodName();
        Object[] paramValues = request.getParamValves();
        Class<?>[] paramTypes = request.getParamTypes();
        String requestId = request.getRequestId();
        String interfaceClass = request.getInterfaceName();
        String version = request.getVersion();
        long startTime = request.getStartTime();

        Object providerBean = providersBean.get(ServiceNameUtils.getServiceName(Class.forName(interfaceClass), version));
        Method method = providerBean.getClass().getMethod(methodName, paramTypes);
        method.setAccessible(true);
        try {
            // reflect invoke this method
            Object result = method.invoke(providerBean, paramValues);
            ResponseBuilder builder = new ResponseBuilder();
            Response response = builder
                    .requestId(requestId)
                    .result(result)
                    .requestTime(System.currentTimeMillis() - startTime)
                    .errorCode(0)
                    .build();
            ctx.pipeline().writeAndFlush(response);
        } catch (Exception e) {
            ResponseBuilder builder = new ResponseBuilder();
            Response response = builder
                    .requestId(requestId)
                    .result(null)
                    .requestTime(System.currentTimeMillis() - startTime)
                    .errorCode(1)
                    .errorMsg(e.getMessage())
                    .build();
            LOGGER.error("handle remote request {} failed! {}", request, e);
            ctx.pipeline().writeAndFlush(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("exception on: {}", ctx.channel(), cause);
        ctx.channel().close();
        ctx.close();
    }
}
