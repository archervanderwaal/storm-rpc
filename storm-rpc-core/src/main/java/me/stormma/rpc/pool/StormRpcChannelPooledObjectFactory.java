package me.stormma.rpc.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.stormma.rpc.netty.bootstrap.RpcClient;
import me.stormma.rpc.netty.codec.Decoder;
import me.stormma.rpc.netty.codec.Encoder;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class StormRpcChannelPooledObjectFactory extends BasePooledObjectFactory<Channel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StormRpcChannelPooledObjectFactory.class);

    private final String host;

    private final String port;

    public StormRpcChannelPooledObjectFactory(String host, String port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Channel create() throws Exception {
        return null;
    }

    @Override
    public PooledObject<Channel> wrap(Channel channel) {
        return new DefaultPooledObject<>(channel);
    }

    @Override
    public boolean validateObject(PooledObject<Channel> p) {
        return p.getObject().isActive();
    }

    @Override
    public void destroyObject(PooledObject<Channel> p) throws Exception {
        p.getObject().close().addListener((future -> {
            LOGGER.debug("channel close finish!");
        }));
    }

    private Channel getChannel() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup(1))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new Encoder())
                                .addLast(new Decoder());
//                                .addLast(RpcClient.this);
                    }
                });
        return null;
    }
}
