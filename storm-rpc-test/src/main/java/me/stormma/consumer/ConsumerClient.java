package me.stormma.consumer;

import me.stormma.api.IHelloService;
import me.stormma.rpc.proxy.DefaultProxy;
import me.stormma.rpc.registry.ServiceDiscover;
import me.stormma.rpc.registry.zk.ZookeeperServiceDiscover;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ConsumerClient {
    public static void main(String[] args) {
        String zkServer = "139.199.27.243:2181";
        ServiceDiscover serviceDiscover = new ZookeeperServiceDiscover(zkServer);
        DefaultProxy proxy = new DefaultProxy(serviceDiscover);
        IHelloService helloService = proxy.createProxy(IHelloService.class, "1.0.0");
        System.out.println(helloService.sayHello("stormma"));
        System.exit(0);
    }
}
