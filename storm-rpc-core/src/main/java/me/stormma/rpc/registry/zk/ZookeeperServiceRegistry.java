package me.stormma.rpc.registry.zk;

import me.stormma.rpc.model.ServerInfo;
import me.stormma.rpc.registry.RegistryUtils;
import me.stormma.rpc.registry.ServiceRegistry;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ZookeeperServiceRegistry implements ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperServiceRegistry.class);

    private final ZkClient zkClient;

    private static final int ZK_SESSION_TIMEOUT = 5000;
    private static final int ZK_CONNECTION_TIMEOUT = 1000;

    public ZookeeperServiceRegistry(String zkServer) {
        this.zkClient = new ZkClient(zkServer, ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("zk <{}> connected", zkServer);
    }

    @Override
    public void register(String serviceName, ServerInfo serverInfo) {
        String serviceParentPath = RegistryUtils.getServiceParentPath();
        if (!zkClient.exists(serviceParentPath)) {
            zkClient.createPersistent(serviceParentPath, true);
        }
        String servicePath = RegistryUtils.getRemoteServicePath(serviceName);
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
        }
        String dummyServerPath = RegistryUtils.getServerPath(serviceName);
        String serverNodeData = RegistryUtils.getServerNodeData(serverInfo);
        String trueServerPath = zkClient.createEphemeralSequential(dummyServerPath, serverNodeData);
        // registered, /server-0000000001 -> xxx.xxx.xxx.xxx
    }

    @Override
    public void shutdown() {
        this.zkClient.close();
    }
}
