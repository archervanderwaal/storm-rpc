package me.stormma.rpc.netty.bootstrap;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface Server {

    void start(String basePackage);

    void close();
}
