package me.stormma.rpc.model;

import java.io.Serializable;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class Response implements Serializable {

    private String requestId;

    private Object result;

    private int errorCode;

    private long requestTime;

    private String errorMsg;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "requestId='" + requestId + '\'' +
                ", result=" + result +
                ", errorCode=" + errorCode +
                ", requestTime=" + requestTime +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
