package me.stormma.rpc.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.stormma.rpc.serializer.KryoSerializer;
import me.stormma.rpc.serializer.Serializer;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class Encoder extends MessageToByteEncoder<Object> {

    private Serializer serializer = KryoSerializer.getSerializer();

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] response = serializer.serialize(msg);
        int length = response.length;
        out.writeInt(length);
        out.writeBytes(response);
    }
}
