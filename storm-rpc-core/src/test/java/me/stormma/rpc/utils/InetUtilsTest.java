package me.stormma.rpc.utils;

import org.junit.Test;

import java.net.UnknownHostException;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class InetUtilsTest {

    @Test
    public void testGetLocalHost() throws UnknownHostException {
        System.out.println(InetUtils.getLocalHost());
    }
}
