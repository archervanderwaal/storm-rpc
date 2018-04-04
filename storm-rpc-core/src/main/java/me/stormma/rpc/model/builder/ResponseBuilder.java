package me.stormma.rpc.model.builder;

import me.stormma.rpc.model.Response;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class ResponseBuilder {

    private Response response = new Response();

    public ResponseBuilder requestId(String requestId) {
        response.setRequestId(requestId);
        return this;
    }

    public ResponseBuilder result(Object result) {
        response.setResult(result);
        return this;
    }

    public ResponseBuilder errorCode(int errorCode) {
        response.setErrorCode(errorCode);
        return this;
    }

    public ResponseBuilder requestTime(long requestTime) {
        response.setRequestTime(requestTime);
        return this;
    }

    public ResponseBuilder errorMsg(String errorMsg) {
        response.setErrorMsg(errorMsg);
        return this;
    }

    public Response build() {
        return response;
    }
}
