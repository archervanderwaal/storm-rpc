package me.stormma.rpc.registry;

import me.stormma.rpc.model.ServerInfo;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface ServiceRegistry {

    /**
     * @param serviceName service name
     * @param serverInfo server info
     */
    void register(String serviceName, ServerInfo serverInfo);

    void shutdown();
}
