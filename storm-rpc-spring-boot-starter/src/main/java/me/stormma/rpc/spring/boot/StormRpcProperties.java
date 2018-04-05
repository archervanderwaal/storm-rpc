package me.stormma.rpc.spring.boot;

import me.stormma.constants.Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stormma stormmaybin@gmail.com
 */
@ConfigurationProperties(prefix = "spring.storm.rpc")
public class StormRpcProperties {
    enum Proxy {
        JDK, CGLIB
    }
    /**spring.storm.rpc.host*/
    private String host = Constants.DEFAULT_HOST;

    /**spring.storm.rpc.port*/
    private Integer port = Constants.DEFAULT_PORT;

    /**spring.storm.rpc.weight*/
    private Integer weight = Constants.SERVER_DEFAULT_WEIGHT;

    /**spring.storm.rpc.registry*/
    private String registry = Constants.DEFAULT_REGISTRY;

    /**spring.storm.rpc.discover*/
    private String discover = Constants.DEFAULT_DISCOVER;

    /**spring.storm.rpc.basePackage*/
    private String basePackage = "";

    /**spring.storm.rpc.name*/
    private String name;

    /**spring.storm.rpc.proxy*/
    private Proxy proxy = Proxy.JDK;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscover() {
        return discover;
    }

    public void setDiscover(String discover) {
        this.discover = discover;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public String toString() {
        return "StormRpcProperties{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                ", registry='" + registry + '\'' +
                ", discover='" + discover + '\'' +
                ", basePackage='" + basePackage + '\'' +
                ", name='" + name + '\'' +
                ", proxy=" + proxy +
                '}';
    }
}
