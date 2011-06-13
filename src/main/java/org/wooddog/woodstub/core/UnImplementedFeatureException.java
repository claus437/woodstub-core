package org.wooddog.woodstub.core;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 04-06-11
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class UnImplementedFeatureException extends WoodStubException {
    public UnImplementedFeatureException() {
        super();
    }

    public UnImplementedFeatureException(String message) {
        super(message);
    }

    public UnImplementedFeatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnImplementedFeatureException(Throwable cause) {
        super(cause);
    }
}
