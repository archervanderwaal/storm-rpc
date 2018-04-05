package me.stormma.rpc.spring.boot.annotation;

import java.lang.annotation.*;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reference {

    Class<?> interfaceClass();

    String version() default "";
}
