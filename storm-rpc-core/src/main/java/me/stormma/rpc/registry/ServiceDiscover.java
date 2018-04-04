package me.stormma.rpc.registry;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface ServiceDiscover {

    /**
     * @param serviceName service name
     * @return service address
     */
    String discover(String serviceName) throws Exception;
}
