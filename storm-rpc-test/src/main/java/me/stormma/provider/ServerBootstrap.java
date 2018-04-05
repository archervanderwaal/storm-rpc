package me.stormma.provider;

import me.stormma.constants.Constants;
import me.stormma.rpc.model.ServerInfo;
import me.stormma.rpc.netty.bootstrap.RpcServer;
import me.stormma.rpc.registry.ServiceRegistry;
import me.stormma.rpc.registry.zk.ZookeeperServiceRegistry;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ServerBootstrap {
    public static void main(String... args) throws Exception {
        String zkServer = "139.199.27.243:2181";
        ServiceRegistry serviceRegistry = new ZookeeperServiceRegistry(zkServer);
        ServerInfo serverInfo = new ServerInfo(Constants.DEFAULT_HOST, Constants.DEFAULT_PORT
                , Constants.SERVER_DEFAULT_WEIGHT);
        RpcServer rpcServer = new RpcServer(serviceRegistry, serverInfo);
        rpcServer.start("me.stormma.provider");
    }
}
