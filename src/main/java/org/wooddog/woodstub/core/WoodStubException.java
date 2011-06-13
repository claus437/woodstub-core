package org.wooddog.woodstub.core;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 04-06-11
 * Time: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class WoodStubException extends RuntimeException {
    public WoodStubException() {
        super();
    }

    public WoodStubException(String message) {
        super(message);
    }

    public WoodStubException(String message, Throwable cause) {
        super(message, cause);
    }

    public WoodStubException(Throwable cause) {
        super(cause);
    }
}
