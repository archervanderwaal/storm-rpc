package me.stormma.rpc.registry;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface ServiceRegistry {

    /**
     * @param serviceName service name
     * @param address service address
     */
    void register(String serviceName, String address);
}
