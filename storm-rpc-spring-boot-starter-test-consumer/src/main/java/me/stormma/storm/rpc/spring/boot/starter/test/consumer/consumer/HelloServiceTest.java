package me.stormma.storm.rpc.spring.boot.starter.test.consumer.consumer;

import me.stormma.api.IHelloService;
import me.stormma.rpc.spring.boot.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stormma stormmaybin@gmail.com
 */
@RestController
public class HelloServiceTest {

    @Reference(interfaceClass = IHelloService.class, version = "2.0.0")
    private IHelloService helloService;

    public int a = 0;

    @GetMapping("/say/hello/{name}")
    public String test(@PathVariable String name) {
        return helloService.sayHello(name);
    }
}
