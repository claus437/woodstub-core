package org.wooddog.woodstub.core.runtime;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 28-04-11
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public interface Stub {
    void setParameters(String[] names, Object[] values);

    void execute() throws Throwable;

    Object getResult();
}
