package me.stormma.rpc.registry.zk;

import com.google.common.base.Strings;
import me.stormma.exception.NotFoundAvailableServerInRegistry;
import me.stormma.exception.NotFoundServiceInRegistryException;
import me.stormma.rpc.registry.LoadBalance;
import me.stormma.rpc.registry.RegistryUtils;
import me.stormma.rpc.registry.ServiceDiscover;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ZookeeperServiceDiscover implements ServiceDiscover {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperServiceDiscover.class);

    private final ZkClient zkClient;

    private final String zkServer;

    private static final int ZK_SESSION_TIMEOUT = 5000;

    private static final int ZK_CONNECTION_TIMEOUT = 1000;

    public ZookeeperServiceDiscover(String zkServer) {
        this.zkServer = zkServer;
        this.zkClient = new ZkClient(zkServer, ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("zk <{}> connected", zkServer);
    }

    @Override
    public String discover(String serviceName) throws Exception {
        String servicePath = RegistryUtils.getRemoteServicePath(serviceName);
        if (Strings.isNullOrEmpty(servicePath) || !zkClient.exists(servicePath)) {
            throw new NotFoundServiceInRegistryException(String
                    .format("not found service <%s> in zookeeper registry <%s>", serviceName, zkServer));
        }
        List<String> servers = zkClient.getChildren(servicePath);
        if (Objects.isNull(servers) || servers.isEmpty()) {
            throw new NotFoundAvailableServerInRegistry(String
                    .format("not found available server for service <%s> in zookeeper registry <%s>"
                            , serviceName, zkServer));
        }
        // serverName as server-0000000001, server-000000002 it's data is server address
        String serverName = LoadBalance.randomWeightPolicyLoadBalance(servers);
        String serverAddress = zkClient.readData(servicePath + "/" + serverName);
        LOGGER.info(String.format("discover service <%s> in registry, use server <%s> to handle service"
                                            , serviceName, serverAddress));
        zkClient.close();
        return serverAddress;
    }
}
