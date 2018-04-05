package me.stormma.rpc.spring.boot.annotation;

import java.lang.annotation.*;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableStormRpcConfiguration {
}
