package me.stormma.rpc.spring.boot;

import me.stormma.rpc.proxy.CglibProxy;
import me.stormma.rpc.proxy.DefaultProxy;
import me.stormma.rpc.proxy.Proxy;
import me.stormma.rpc.registry.ServiceDiscover;
import me.stormma.rpc.registry.zk.ZookeeperServiceDiscover;
import me.stormma.rpc.spring.boot.annotation.EnableStormRpcConfiguration;
import me.stormma.rpc.spring.boot.annotation.Reference;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Configuration
@ConditionalOnClass(Reference.class)
//@ConditionalOnBean(EnableStormRpcConfiguration.class)
@EnableConfigurationProperties(StormRpcProperties.class)
public class StormRpcConsumerAutoConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private StormRpcProperties stormRpcProperties;

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                Class<?> clazz = AopUtils.isAopProxy(bean) ? AopUtils.getTargetClass(bean) : bean.getClass();
                for (Field field : clazz.getDeclaredFields()) {
                    Reference reference = field.getAnnotation(Reference.class);
                    if (reference != null) {
                        Class<?> interfaceClass = reference.interfaceClass();
                        String version = reference.version();
                        ServiceDiscover serviceDiscover = new ZookeeperServiceDiscover(stormRpcProperties
                                .getDiscover());
                        Proxy proxy = stormRpcProperties.getProxy().equals(StormRpcProperties.Proxy.JDK)
                                ? new DefaultProxy(serviceDiscover) : new CglibProxy(serviceDiscover);
                        Object proxyBean = proxy.createProxy(interfaceClass, version);
                        field.setAccessible(true);
                        try {
                            field.set(bean, proxyBean);
                        } catch (IllegalAccessException e) {
                            throw new BeanCreationException(beanName, e);
                        }
                    }
                }
                return bean;
            }

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
    }
}
