package me.stormma.storm.rpc.spring.boot.starter.test.provider.provider;

import me.stormma.annoation.Provider;
import me.stormma.api.IHelloService;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Provider(interfaceClass = IHelloService.class, version = "2.0.0")
public class HelloService implements IHelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name + "!";
    }
}
