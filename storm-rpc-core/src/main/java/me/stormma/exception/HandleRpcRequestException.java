package me.stormma.exception;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class HandleRpcRequestException extends Exception {

    public HandleRpcRequestException() {
    }

    public HandleRpcRequestException(String message) {
        super(message);
    }

    public HandleRpcRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandleRpcRequestException(Throwable cause) {
        super(cause);
    }

    public HandleRpcRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
