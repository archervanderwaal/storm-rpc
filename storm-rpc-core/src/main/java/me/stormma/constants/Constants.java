package me.stormma.constants;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class Constants {

    /**storm-rpc default port*/
    public static final Integer DEFAULT_PORT = 52057;

    /**default host*/
    public static final String DEFAULT_HOST = "127.0.0.1";

    /**default registry*/
    public static final String DEFAULT_REGISTRY = "127.0.0.1:2181";

    /**default discover*/
    public static final String DEFAULT_DISCOVER = "127.0.0.1:2181";

    /**default server weight*/
    public static final Integer SERVER_DEFAULT_WEIGHT = 1;

    /**default server address wight weight separator, such as 107.182.180.189:52057$3*/
    public static final String DEFAULT_SERVER_ADDRESS_WITH_WEIGHT_SEPARATOR = "$";

    /**default server address wight weight cutting character, as \\$*/
    public static final String DEFAULT_SERVER_ADDRESS_WITH_WEIGHT_CUTTING_CHARACTER = "\\$";
}
