package me.stormma.rpc.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class LoadBalance {

    /**
     * service map: key: server address, value: weight
     */
    public static String randomWeightPolicyLoadBalance(Map<String, Integer> serverWeights) {
        List<String> servers = Lists.newArrayList(serverWeights.keySet());
        if (servers.size() == 0) return null;
        int length = servers.size();
        int totalWeight = 0;
        boolean isUniqueWeight = false;
        for (int i = 0; i < length; i++) {
            int weight = serverWeights.get(servers.get(i));
            totalWeight += weight;
            if (!isUniqueWeight && i > 0 && (isUniqueWeight = (weight != serverWeights.get(servers.get(i - 1))))) ;
        }
        if (totalWeight > 0 && isUniqueWeight) {
            int randomWeight = ThreadLocalRandom.current().nextInt(totalWeight);
            for (int i = 0; i < length; i++) {
                randomWeight -= serverWeights.get(servers.get(i));
                if (randomWeight < 0) {
                    return servers.get(i);
                }
            }
        }
        return servers.get(ThreadLocalRandom.current().nextInt(length));
    }
}
