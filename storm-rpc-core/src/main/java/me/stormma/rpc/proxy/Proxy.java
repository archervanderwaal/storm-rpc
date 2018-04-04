package me.stormma.rpc.proxy;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface Proxy {

    <T> T createProxy(final Class<?> interfaceClass, final String version);

    <T> T createProxy(final Class<?> interfaceClass);
}
