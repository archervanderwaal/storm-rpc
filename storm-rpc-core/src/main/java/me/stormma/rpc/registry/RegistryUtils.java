package me.stormma.rpc.registry;

import com.google.common.base.Strings;

/**
 * @author stormma stormmaybin@gmail.com
 * ---/     directory structure
 *      |-----/storm-rpc
 *      |---------------/services
 *      |------------------------/test
 *      |-----------------------------/server-0
 *      /-----------------------------/server-1
 *
 */
public class RegistryUtils {

    private static final String ZOOKEEPER_BASE_PATH = "/storm-rpc";

    private static final String SERVICES = "services";

    private static final String SERVER = "server-";

    public static String getRemoteServicePath(String serviceName) {
        if (Strings.isNullOrEmpty(serviceName)) return null;
        // /storm-rpc/services/'serviceName'
        return getServiceParentPath() + "/" + serviceName;
    }

    public static String getServiceParentPath() {
        // /storm-rpc/services
        return ZOOKEEPER_BASE_PATH + "/" + SERVICES;
    }

    public static String getServerPath(String serviceName) {
        if (Strings.isNullOrEmpty(serviceName)) return null;
        // /storm-rpc/services/'serviceName'/server-'number'
        return getRemoteServicePath(serviceName) + "/" + SERVER;
    }
}
