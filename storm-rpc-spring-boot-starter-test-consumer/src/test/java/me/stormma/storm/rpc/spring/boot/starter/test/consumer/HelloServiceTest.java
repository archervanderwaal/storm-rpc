package me.stormma.storm.rpc.spring.boot.starter.test.consumer;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class HelloServiceTest {

    @Test
    public void test() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("me.stormma.storm.rpc.spring.boot.starter.test.consumer.consumer.HelloServiceTest");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println(field);
        }
    }
}
