package me.stormma.rpc.model;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ServerInfo {

    private String host;

    private int port;

    private int weight;

    public ServerInfo(String host, int port, int weight) {
        this.host = host;
        this.port = port;
        this.weight = weight;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                '}';
    }
}
