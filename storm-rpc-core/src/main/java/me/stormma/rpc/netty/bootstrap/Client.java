package me.stormma.rpc.netty.bootstrap;

import me.stormma.rpc.model.Request;
import me.stormma.rpc.model.Response;

/**
 * @author stormma stormmaybin@gmail.com
 */
public interface Client {

    Response sendRequest(Request request) throws InterruptedException;
}
