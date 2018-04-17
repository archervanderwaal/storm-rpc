package me.stormma.rpc.pool;

import io.netty.channel.Channel;
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
}
