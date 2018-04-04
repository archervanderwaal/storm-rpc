package me.stormma.annoation;

import java.lang.annotation.*;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Provider {
    Class<?> interfaceClass();

    String version() default "";
}
