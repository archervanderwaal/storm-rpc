package me.stormma.rpc.registry.zk;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import me.stormma.constants.Constants;
import me.stormma.exception.NotFoundAvailableServerInRegistry;
import me.stormma.exception.NotFoundServiceInRegistryException;
import me.stormma.rpc.registry.LoadBalance;
import me.stormma.rpc.registry.RegistryUtils;
import me.stormma.rpc.registry.ServiceDiscover;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ZookeeperServiceDiscover implements ServiceDiscover {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperServiceDiscover.class);

    private String zkServer;

    private static final int ZK_SESSION_TIMEOUT = 5000;

    private static final int ZK_CONNECTION_TIMEOUT = 1000;

    public ZookeeperServiceDiscover(String zkServer) {
        this.zkServer = zkServer;
    }

    @Override
    public String discover(String serviceName) throws Exception {
        ZkClient zkClient = new ZkClient(zkServer, ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIMEOUT);
        String servicePath = RegistryUtils.getRemoteServicePath(serviceName);
        if (Strings.isNullOrEmpty(servicePath) || !zkClient.exists(servicePath)) {
            throw new NotFoundServiceInRegistryException(String
                    .format("not found service <%s> in zookeeper registry <%s>", serviceName, zkServer));
        }
        List<String> serverNames = zkClient.getChildren(servicePath);
        if (Objects.isNull(serverNames) || serverNames.isEmpty()) {
            throw new NotFoundAvailableServerInRegistry(String
                    .format("not found available server for service <%s> in zookeeper registry <%s>"
                            , serviceName, zkServer));
        }
        /** serverName as server-0000000001, server-000000002 it's data is server addree + '$' + weight
         * such as:
         * server-00000001 -> 107.182.180.189:52057$3, 107.182.180.189 this server weight is 3.
         */
        Map<String, Integer> serverWeights = Maps.newHashMap();
        for (String serverName : serverNames) {
            String data = zkClient.readData(servicePath + "/" + serverName);
            String serverAddress = data.split(Constants.DEFAULT_SERVER_ADDRESS_WITH_WEIGHT_CUTTING_CHARACTER)[0];
            String weight = data.split(Constants.DEFAULT_SERVER_ADDRESS_WITH_WEIGHT_CUTTING_CHARACTER)[1];
            serverWeights.put(serverAddress, Integer.parseInt(weight));
        }
        String serverAddress = LoadBalance.randomWeightPolicyLoadBalance(serverWeights);
        LOGGER.info(String.format("discover service <%s> in registry, use server <%s> to handle service"
                                            , serviceName, serverAddress));
        zkClient.close();
        return serverAddress;
    }
}
