package me.stormma.rpc.netty.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.stormma.rpc.model.Request;
import me.stormma.rpc.model.Response;
import me.stormma.rpc.model.builder.ResponseBuilder;
import me.stormma.rpc.netty.codec.Decoder;
import me.stormma.rpc.netty.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class RpcClient extends SimpleChannelInboundHandler<Response> implements Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClient.class);

    private Response response;

    private final String host;

    private final int port;

    private Request request;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Response sendRequest(Request request) throws InterruptedException {
        this.request = request;
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new Encoder())
                                .addLast(new Decoder())
                                .addLast(RpcClient.this);
                    }
                })
                .option(ChannelOption.TCP_NODELAY, true);
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            Channel channel = future.channel();
            channel.writeAndFlush(request).sync();
            channel.closeFuture().sync();
            return response;
        } finally {
            group.shutdownGracefully();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response response) {
        this.response = response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("calling remote service failed {}", cause);
        //forgery a response for client
        if (Objects.isNull(this.response)) {
            ResponseBuilder builder = new ResponseBuilder();
            this.response = builder
                    .requestId(request.getRequestId())
                    .requestTime(System.currentTimeMillis() - request.getStartTime())
                    .result(null)
                    .errorCode(1)
                    .errorMsg(cause.getMessage())
                    .build();
        }
        ctx.close();
    }

    @Override
    public String toString() {
        return "RpcClient{" +
                "response=" + response +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", request=" + request +
                '}';
    }
}
