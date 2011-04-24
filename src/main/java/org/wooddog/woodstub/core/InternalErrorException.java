package org.wooddog.woodstub.core;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 23-04-11
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class InternalErrorException extends RuntimeException {
    public InternalErrorException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public InternalErrorException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public InternalErrorException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public InternalErrorException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
