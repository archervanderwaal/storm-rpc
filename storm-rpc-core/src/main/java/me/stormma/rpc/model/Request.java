package me.stormma.rpc.model;

import java.io.Serializable;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class Request implements Serializable {

    private String requestId;

    private String interfaceName;

    private String methodName;

    private Class<?>[] paramTypes;

    private Object[] paramValves;

    private String version;

    private long startTime;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParamValves() {
        return paramValves;
    }

    public void setParamValves(Object[] paramValves) {
        this.paramValves = paramValves;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
