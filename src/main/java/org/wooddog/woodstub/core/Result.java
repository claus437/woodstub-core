package org.wooddog.woodstub.core;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public class Result {
    private String type;
    private Object value;
    private Throwable exception;
    private boolean skip;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObject() {
        return value;
    }

    public int getInt() {
        return 0;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }
}
