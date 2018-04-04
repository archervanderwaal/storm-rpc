package me.stormma.rpc.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class InetUtils {

    public static String getLocalHost() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress.getHostAddress());
        return inetAddress.getHostAddress();
    }
}
