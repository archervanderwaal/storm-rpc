package me.stormma.rpc.registry;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class LoadBalance {

    /**
     * service map: key: server address, value: weight
     */
    private static Map<String, Integer> map = new HashMap<>();

    public void add(String server, int weight) {
        map.put(server, weight);
    }

    public void delete(String server) {
        map.remove(server);
    }

    public int size() {
        return map.size();
    }

    public Set<String> servers() {
        return map.keySet();
    }

    public static String randomWeightPolicyLoadBalance(List<String> servers) {
        if (servers.size() == 0) return null;
        int length = servers.size();
        int totalWeight = 0;
        boolean isUniqueWeight = false;
        for (int i = 0; i < length; i++) {
            int weight = map.get(servers.get(i));
            totalWeight += weight;
            if (!isUniqueWeight && i > 0 && (isUniqueWeight = (weight != map.get(servers.get(i - 1))))) ;
        }
        if (totalWeight > 0 && isUniqueWeight) {
            int randomWeight = ThreadLocalRandom.current().nextInt(totalWeight);
            for (int i = 0; i < length; i++) {
                randomWeight -= map.get(servers.get(i));
                if (randomWeight < 0) {
                    return servers.get(i);
                }
            }
        }
        return servers.get(ThreadLocalRandom.current().nextInt(length));
    }
}
