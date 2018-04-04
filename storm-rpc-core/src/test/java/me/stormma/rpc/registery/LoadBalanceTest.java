package me.stormma.rpc.registery;

import com.google.common.collect.Maps;
import me.stormma.rpc.registry.LoadBalance;
import org.junit.Test;

import java.util.Map;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class LoadBalanceTest {

    @Test
    public void testRandomWeightPolicyLoadBalance() {
        Map<String, Integer> serverWeights = Maps.newHashMap();
        serverWeights.put("107.182.180.189", 3);
        serverWeights.put("stormma.me", 5);
        serverWeights.put("127.0.0.1", 4);
        System.out.println(LoadBalance.randomWeightPolicyLoadBalance(serverWeights));
    }
}
