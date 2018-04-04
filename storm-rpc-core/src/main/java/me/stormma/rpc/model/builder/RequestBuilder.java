package me.stormma.rpc.model.builder;

import me.stormma.rpc.model.Request;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class RequestBuilder {

    private Request request = new Request();

    public RequestBuilder requestId(String requestId) {
        request.setRequestId(requestId);
        return this;
    }

    public RequestBuilder interfaceName(String interfaceClass) {
        request.setInterfaceName(interfaceClass);
        return this;
    }

    public RequestBuilder methodName(String methodName) {
        request.setMethodName(methodName);
        return this;
    }

    public RequestBuilder paramTypes(Class<?>[] paramTypes) {
        request.setParamTypes(paramTypes);
        return this;
    }

    public RequestBuilder paramValves(Object[] paramValves) {
        request.setParamValves(paramValves);
        return this;
    }

    public RequestBuilder version(String version) {
        request.setVersion(version);
        return this;
    }

    public RequestBuilder startTime(long startTime) {
        request.setStartTime(startTime);
        return this;
    }

    public Request build() {
        return request;
    }
}
