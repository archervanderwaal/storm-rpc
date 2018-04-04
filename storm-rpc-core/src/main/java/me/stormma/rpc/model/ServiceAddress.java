package me.stormma.rpc.model;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ServiceAddress {

    private String host;

    private int port;

    public ServiceAddress(String host, int port) {
        this.host = host;
        this.port = port;
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

    @Override
    public String toString() {
        return "ServiceAddress{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
