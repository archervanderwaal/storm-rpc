package me.stormma.rpc.utils;

import org.junit.Test;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class StringUtils {

    @Test
    public void test() {
        String server = "127.0.0.1$1";
        String[] array = server.split("\\$");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
