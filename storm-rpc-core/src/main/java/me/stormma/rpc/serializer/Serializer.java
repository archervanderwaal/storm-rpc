package me.stormma.rpc.serializer;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface Serializer {

    byte[] serialize(Object data);

    <T> T deserialize(byte[] dataBytes);
}
